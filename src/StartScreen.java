import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class StartScreen extends JFrame {

    public StartScreen() {

        setTitle("Cross Sums");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(null);

        //Hauptpanel
        JPanel mainPanel = new JPanel(new GridLayout());
        mainPanel.setBackground(Color.WHITE);

        //Play button
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Arial", Font.BOLD, 24));
        playButton.setBackground(new Color(200, 200, 200));
        playButton.setPreferredSize(new Dimension(120, 60));
        playButton.addActionListener(this::startGame);

        mainPanel.add(playButton);
        add(mainPanel);
        setVisible(true);
    }

    private void startGame(ActionEvent e) {
        dispose();
        SwingUtilities.invokeLater(()-> {
            UserInterface ui = new UserInterface(tempmain.startLevel(1));
            ui.setVisible(true);
            ui.startTimer();
        });
    }
}
