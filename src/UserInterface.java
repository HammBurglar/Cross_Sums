import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class UserInterface extends JFrame{
    private enum Toolmode {PEN, ERASER}
    private Toolmode currentToolMode = Toolmode.PEN;
    private JToggleButton toolButton;
    private javax.swing.Timer gameTimer;
    private int secondsPassed = 0;
    private JLabel timerLabel;
    private final LevelData levelData;
    int minutes;
    int seconds;
    int totalMinutes = 0;
    int totalSeconds = 0;

    public UserInterface(LevelData levelData) {
        this.levelData = levelData;

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
        backButton.addActionListener(e -> {
            stopTimer();
            dispose();
            new StartScreen().setVisible(true);
        });
        topPanel.add(backButton, BorderLayout.WEST);

        // Level Anzeige
        JLabel levelLabel = new JLabel("Level " + levelData.getLevelNumber());
        levelLabel.setFont(new Font("Arial", Font.BOLD, 24));
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(levelLabel, BorderLayout.CENTER);

        if(levelData.levelNumber % 6 == 0) {
            JLabel mult = new JLabel("Zahlen werden multipliziert!");
            mult.setForeground(Color.RED);
            mult.setHorizontalAlignment(SwingConstants.CENTER);
            topPanel.add(mult, BorderLayout.SOUTH);
        }

        // Timer-Anzeige
        timerLabel = new JLabel("00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(timerLabel, BorderLayout.EAST);

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
                gridPanel.add(new NumberCell(value, i, j));
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

    //Starten und stoppen des timers
    public void startTimer() {
        gameTimer = new javax.swing.Timer(1000, e -> {
            secondsPassed++;
            minutes = secondsPassed / 60;
            seconds = secondsPassed % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        });
        gameTimer.start();
    }
    public void stopTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }
    //Durchschnittliche Zeit, die zum Lösen benötigt wird
    public int averageTime() {
        totalSeconds += seconds + minutes*60;

        return totalSeconds / (levelData.levelNumber-1);
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
        private final int row;
        private final int col;
        private boolean visible = true;

        public NumberCell(int value, int row, int col) {
            this.value = value;
            this.row = row;
            this.col = col;

            setPreferredSize(new Dimension(50, 50));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            setOpaque(true);

            addMouseListener( new MouseAdapter( ) {
                @Override
                public void mouseClicked (MouseEvent e){
                    if (currentToolMode == Toolmode.ERASER) {
                        levelData.erase(row, col);
                        visible = false;
                    } else {
                        levelData.write(row, col);
                        visible = true;
                    }
                    repaint();

                    if (levelData.isSolved()) {
                        stopTimer();
                        if (seconds < 15 && minutes == 0) {
                        JOptionPane.showMessageDialog(
                                UserInterface.this,"Level  in " + seconds +" Sekunden gelöst! Schnell!"+ "\nDurchschnitlich:" + averageTime() + " Sekunden");
                        } else if (seconds < 60 && minutes == 0) {
                            JOptionPane.showMessageDialog(
                                    UserInterface.this,"Level in " + seconds +" Sekunden gelöst!" + "\nDurchschnitlich:" + averageTime() + " Sekunden");
                        } else if (minutes != 0) {
                            JOptionPane.showMessageDialog(
                                    UserInterface.this,"Level in " + minutes + " Minuten und "+ seconds +" Sekunden gelöst." + "\nDurchschnitlich:" + averageTime() + " Sekunden");
                        }
                        startNextLevel();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Color color;
            if (visible) {
                color = Color.BLACK;
            }
                else {
                    color = Color.LIGHT_GRAY;
            }
                super.paintComponent(g);
                g.setColor(color);
                g.setFont(new Font("Arial", Font.BOLD, 18));
                FontMetrics fm = g.getFontMetrics();
                String text = String.valueOf(value);
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g.drawString(text, x, y);
        }
    }

    private void startNextLevel() {
        dispose();

        SwingUtilities.invokeLater(()-> {
            UserInterface ui = new UserInterface(tempmain.startLevel(levelData.levelNumber));
            ui.setVisible(true);
            ui.startTimer();
        });
    }
}
