import javax.swing.*;
import java.awt.*;


public class UserInterface extends JFrame{
    private enum Toolmode {PEN, ERASER}
    private Toolmode currentToolMode = Toolmode.PEN;
    private JToggleButton toolButton;

    public UserInterface(LevelData levelData) {
        setTitle("Cross Sums");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Return Knopf
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 240));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton backButton = new JButton("←");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.addActionListener(e -> System.out.println("Zurück geklickt"));
        topPanel.add(backButton, BorderLayout.WEST);

        // Level Anzeige
        JLabel levelLabel = new JLabel("Level " + levelData.getLevelNumber());
        levelLabel.setFont(new Font("Arial", Font.BOLD, 24));
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(levelLabel, BorderLayout.CENTER);

        // Timer-Anzeige
        JLabel timerLable = new JLabel("00:00");
        timerLable.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(timerLable, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        //Hauptbereich für Spielinhalt
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Spaltensummen
        JPanel colSumPanel = new JPanel(new GridLayout(1, levelData.getCols(), 5, 5));
        colSumPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 5, 0));
        for (int sum : levelData.getColSums()) {
            colSumPanel.add(createGrayBox(String.valueOf(sum)));
        }
        mainPanel.add(colSumPanel, BorderLayout.NORTH);

        //Zeilensummen
        JPanel gridContainer = new JPanel(new BorderLayout());
        gridContainer.setBorder((BorderFactory.createEmptyBorder(5, 0, 0, 5)));

        JPanel rowsSumsPanel = new JPanel(new GridLayout(levelData.getRows(), 1, 5, 5));
        for (int sum : levelData.getRowSums()) {
            rowsSumsPanel.add(createGrayBox(String.valueOf(sum)));
        }
        gridContainer.add(rowsSumsPanel, BorderLayout.WEST);

        //Weiße Kästchen
        JPanel gridPanel = new JPanel(new GridLayout(levelData.getRows(), levelData.getCols(), 5, 5));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        for (int i = 0; i < levelData.getRows(); i++) {
            for (int j = 0; j < levelData.getCols(); j++) {
                int value = levelData.getGridNumbers()[i][j];
                gridPanel.add(new NumberCell(value));
            }
        }

        gridContainer.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(gridContainer, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        //Tool Schieberegler
        JPanel bottomPanel = new JPanel();
        toolButton = new JToggleButton("Stift");
        toolButton.setFont(new Font("Arial", Font.PLAIN, 16));
        toolButton.setPreferredSize(new Dimension(120, 40));
        toolButton.addActionListener(e -> updateToolButton());
        bottomPanel.add(toolButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private  JPanel createGrayBox(String text) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(200, 200, 200));
        panel.setPreferredSize(new Dimension(40, 40));
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(label);

        return panel;
    }

    private void updateToolButton() {
        if (toolButton.isSelected()) {
            currentToolMode = Toolmode.ERASER;
            toolButton.setText("Radierer");
        } else {
            currentToolMode = Toolmode.PEN;
            toolButton.setText("Stift");
        }
    }

    //Innere Klasse für Zahlenkästchen
    private class NumberCell extends JPanel {
        private final int value;

        public NumberCell(int value) {
            this.value = value;
            setPreferredSize(new Dimension(50, 50));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponents(g);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            FontMetrics fm = g.getFontMetrics();
            String text = String.valueOf(value);
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(text, x, y);
        }
    }
}
