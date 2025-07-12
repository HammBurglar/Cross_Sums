import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class StartScreen extends JFrame {

    public StartScreen() {

        setTitle("Cross Sums");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);
        setLocationRelativeTo(null);

        //Hauptpanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(10, 20, 100, 50));

        //Play button
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Verdana", Font.BOLD, 24));
        playButton.setBackground(Color.LIGHT_GRAY);
        playButton.setPreferredSize(new Dimension(120, 40));
        playButton.setMaximumSize(new Dimension(120, 40));
        playButton.addActionListener(this::startGame);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Spiel-Erklärung
        JTextArea text = new JTextArea("Ziel: Zeilen und Spalten sollen summiert die Werte an\nden Rändern ergeben.\n\nWie? Mit Radierer Zahlen entfernen, bis alle Gleichungen\nstimmen.\nMit dem Stift kann man die radierten Werte wieder\nzurückbringen.\n\nIn jedem 6ten Level wird Summe durch Produkt ersetzt.");
        text.setBackground(new Color(0, 0,0, 0));
        text.setFont(new Font("Verdana", Font.PLAIN, 15));
        text.setBorder(null);
        text.setEditable(false);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setMaximumSize(new Dimension(430, 300));

        //Logo
        ImageIcon logoImg = new ImageIcon("src/crosssumslogo.png");
        logoImg.setImage(logoImg.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT));
        JLabel logo = new JLabel();
        logo.setIcon(logoImg);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(logo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(text);
        mainPanel.add(playButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        add(mainPanel);
        setVisible(true);
    }

    private void startGame(ActionEvent e) {
        dispose();
        SwingUtilities.invokeLater(()-> {
            UserInterface ui = new UserInterface(tempmain.startLevel(1), 0);
            ui.setVisible(true);
            ui.startTimer();
        });
    }
}
