import java.util.Random;

class LevelData {

    int levelNumber;
    int rows;
    int cols;
    int[][] gridNumbers;
    int[] rowSums;
    int[] colSums;
    int[][] erasedNumbers;

    public LevelData(int levelNumber, int rows, int cols, int[][] gridNumbers,
                     int[] rowSums, int[] colSums) {
        this.levelNumber = levelNumber;
        this.rows = rows;
        this.cols = cols;
        this.gridNumbers = gridNumbers;
        this.rowSums = rowSums;
        this.colSums = colSums;
    }

    public LevelData(int levelNumber) {
        this.levelNumber = levelNumber;
    }


    public int getLevelNumber() {
        return levelNumber;
    }
    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public int[][] getGridNumbers() {
        return gridNumbers;
    }
    public int[] getRowSums() {
        return rowSums;
    }
    public int[] getColSums() {
        return colSums;
    }

    public int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        if (randomNum == 0) { return randInt(min, max); }
        return randomNum;
    }

    // Generiert ein Raster abhängig vom Level
    public void createGrid() {
        rows = (int) Math.floor(levelNumber / 6 + 3);
        cols = (int) Math.floor(levelNumber / 6 + 3);
        rowSums = new int[rows];
        colSums = new int[cols];
        int[][] grid = new int[rows][cols];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(levelNumber % 3 == 0) {
                    grid[i][j] = randInt(levelNumber * -9, levelNumber * 2);
                } else {
                    grid[i][j] = randInt(1, levelNumber * 9);
                }
            }
        }
        gridNumbers = grid;
    }

    // Generiert zufällige Summen-Arrays
    public void createSums() {
        double[][] rand = new double[rows][cols];
        int rowSum = 0;
        int colSum = 0;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) { rand[i][j] = Math.random(); }
        }

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(rand[i][j] < 0.7) { rowSum += gridNumbers[i][j]; }
                if(j == cols-1) { rowSums[i] = rowSum; rowSum = 0;}
            }
        }
        for(int j = 0; j < cols; j++) {
            for(int i = 0; i < rows; i++) {
                if(rand[i][j] < 0.7) { colSum += gridNumbers[i][j]; }
                if(i == rows-1) { colSums[j] = colSum; colSum = 0;}
            }
        }
    }

    // Kontrolliert, ob das Level gelöst wurde (Für Addition)
    public boolean isSolved() {
        int colSum = 0;
        int rowSum = 0;
        boolean correct = true;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                rowSum += gridNumbers[i][j];
                if(j == cols-1) {
                    correct = correct && rowSum == rowSums[i];
                    rowSum = 0;
                }
                if(!correct) {
                    System.out.println("at row " + i);
                    return false;
                }
            }
        }
        for(int j = 0; j < cols; j++) {
            for(int i = 0; i < rows; i++) {
                colSum += gridNumbers[i][j];
                if(i == rows-1) {
                    correct = correct && colSum == colSums[j];
                    colSum = 0;
                }
                if(!correct) {
                    System.out.println("at col " + j);
                    return false;
                }
            }
        }
        levelNumber++;
        return true;
    }

    // Radieren eines Werts
    public void erase(int i, int j) {
        erasedNumbers = new int[rows][cols];
        if (gridNumbers[i][j] != 0) {
            erasedNumbers[i][j] = gridNumbers[i][j];
            gridNumbers[i][j] = 0;
        }
    }

    // Zurückbringen eines Werts, nachdem er radiert wurde
    public void write(int i, int j) {
        if (gridNumbers[i][j] == 0) {
            gridNumbers[i][j] = erasedNumbers[i][j];
        }
    }

    // Reset aller Werte (z.B. nach falscher Lösung)
    public void resetGrid() {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(erasedNumbers[i][j] != 0 && gridNumbers[i][j] == 0) {
                    gridNumbers[i][j] = erasedNumbers[i][j];
                }
            }
        }
    }
}

