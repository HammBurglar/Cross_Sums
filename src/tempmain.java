import javax.swing.*;

public class tempmain {

    // Generieren neuen Levels
    public static LevelData startLevel(int level) {
        LevelData levelData;

        if (level % 6 == 0) {
            levelData = new MultLevelData(level);
        } else {
            levelData = new LevelData(level);
        }

        levelData.createGrid();
        levelData.createSums();
        return levelData;

        /*
        if (level % 4 == 0) {
            MultLevelData levelData = new MultLevelData(level);
            levelData.createGrid();
            levelData.createSums();
            return levelData;
        }
        LevelData levelData = new LevelData(level);
        levelData.createGrid();
        levelData.createSums();
        return levelData; */
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater((StartScreen::new));


        /*
        int[][] gridNumbers = {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
        };
        int[] rowSums = {5, 5, 5, 5, 5};
        int[] colSums = {5, 5, 5, 5, 5};
        LevelData testLevel = new LevelData(4);

        SwingUtilities.invokeLater(() -> new UserInterface(startLevel(16)).setVisible(true)); */
    }
}
