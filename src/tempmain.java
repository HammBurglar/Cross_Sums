import javax.swing.*;

public class tempmain {

    // Generieren neuen Levels
    public static LevelData startLevel(int level) {
        if (level % 4 == 0) {
            MultLevelData levelData = new MultLevelData(level);
            levelData.createGrid();
            levelData.createSums();
            return levelData;
        }
        LevelData levelData = new LevelData(level);
        levelData.createGrid();
        levelData.createSums();
        return levelData;
    }

    public static void main(String[] args) {
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

        SwingUtilities.invokeLater(() -> new UserInterface(startLevel(4)).setVisible(true));
    }
}
