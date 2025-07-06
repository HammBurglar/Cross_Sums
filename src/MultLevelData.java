import java.util.Random;

public class MultLevelData extends LevelData{

    MultLevelData(int levelNumber, int rows, int cols, int[][] gridNumbers,
                  int[] rowSums, int[] colSums) {
        super(levelNumber, rows, cols, gridNumbers, rowSums, colSums);
    }

    MultLevelData(int levelNumber) {
        super(levelNumber);
    }

    @Override
    public int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        if (randomNum == 1 || randomNum == 0) { return randInt(min, max); }
        return randomNum;
    }

    // Generiert ein Raster abhängig vom Level
    @Override
    public void createGrid() {
        rows = (int) Math.floor(levelNumber / 6 + 3);
        cols = (int) Math.floor(levelNumber / 6 + 3);
        rowSums = new int[rows];
        colSums = new int[cols];
        int[][] grid = new int[rows][cols];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                    grid[i][j] = randInt(-10, 10);
            }
        }
        gridNumbers = grid;
    }

    // Generiert zufällige Produkt-Arrays
    @Override
    public void createSums() {
        double[][] rand = new double[rows][cols];
        int rowMul = 1;
        int colMul = 1;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) { rand[i][j] = Math.random(); }
        }

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(rand[i][j] < 0.7) { rowMul *= gridNumbers[i][j]; }
                if(j == cols-1) { rowSums[i] = rowMul; rowMul = 1;}
            }
        }
        for(int j = 0; j < cols; j++) {
            for(int i = 0; i < rows; i++) {
                if(rand[i][j] < 0.7) { colMul *= gridNumbers[i][j]; }
                if(i == rows-1) { colSums[j] = colMul; colMul = 1;}
            }
        }
    }

    // Kontrolliert, ob das Level gelöst wurde (Für Multiplikation)
    @Override
    public boolean isSolved() {
        int colMul = 1;
        int rowMul = 1;
        boolean correct = true;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                rowMul *= gridNumbers[i][j];
                if(j == cols-1) {
                    correct = correct && rowMul == rowSums[i];
                    rowMul = 0;
                }
                if(!correct) {
                    System.out.println("at row " + i);
                    return false;
                }
            }
        }
        for(int j = 0; j < cols; j++) {
            for(int i = 0; i < rows; i++) {
                colMul *= gridNumbers[i][j];
                if(i == rows-1) {
                    correct = correct && colMul == colSums[j];
                    colMul = 0;
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
    @Override
    public void erase(int i, int j) {
        erasedNumbers = new int[rows][cols];
        if (gridNumbers[i][j] != 1) {
            erasedNumbers[i][j] = gridNumbers[i][j];
            gridNumbers[i][j] = 1;
        }
    }

    // Zurückbringen eines Werts, nachdem er radiert wurde
    @Override
    public void write(int i, int j) {
        if (gridNumbers[i][j] == 1) {
            gridNumbers[i][j] = erasedNumbers[i][j];
        }
    }

    // Reset aller Werte (z.B. nach falscher Lösung)
    @Override
    public void resetGrid() {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(erasedNumbers[i][j] != 1 && gridNumbers[i][j] == 0) {
                    gridNumbers[i][j] = erasedNumbers[i][j];
                }
            }
        }
    }
}
