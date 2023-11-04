

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Main Menu prototype for cantStop game
 *
 * @author Will
 */

public class mainMenu extends JFrame implements ActionListener
{

    private JPanel logoPanel, buttonPanel, minorButtonPanel, Panel;


    private JLabel logoLabel;
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton helpButton;
    private JButton aboutButton;
    private JButton settingsButton;

    /**
     * Creates the main menu
     */
    public mainMenu()
    {


        // Creates the logo containing panel

        logoPanel = new JPanel();

        logoLabel = new JLabel("CAN'T STOP!", SwingConstants.CENTER);
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 36));

        logoPanel.add(logoLabel);


        // Creates the panel which will contain buttons

        buttonPanel = new JPanel();
        minorButtonPanel = new JPanel();

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        minorButtonPanel.setLayout(new BoxLayout(minorButtonPanel, BoxLayout.X_AXIS));

        newGameButton = new JButton("New Game");
        loadGameButton = new JButton("Load Game");
        helpButton = new JButton("Help");
        settingsButton = new JButton("Settings");
        aboutButton = new JButton("About");

        newGameButton.addActionListener(this);
        loadGameButton.addActionListener(this);
        helpButton.addActionListener(this);
        settingsButton.addActionListener(this);
        aboutButton.addActionListener(this);

        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        helpButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        aboutButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(loadGameButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        minorButtonPanel.add(helpButton);
        minorButtonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        minorButtonPanel.add(aboutButton);
        buttonPanel.add(minorButtonPanel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        buttonPanel.add(settingsButton);


        // Creates a panel which will organize the contents of the other panels neatly

        Panel = new JPanel();
        Panel.setLayout(new BoxLayout(Panel, BoxLayout.Y_AXIS));
        Panel.add(logoPanel);
        Panel.add(Box.createRigidArea(new Dimension(0, 20)));
        Panel.add(buttonPanel);
        Panel.setBorder(new EmptyBorder(new Insets(80,80,80,80)));


        // Adds the panel to and configure JFrame

        this.setTitle("Main Menu");

        this.setLayout(new BorderLayout());
        this.add(Panel, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed (ActionEvent aevt)
    {
        Object sel = aevt.getSource();

        if (sel.equals(newGameButton)){
            setupAGame setupAGame1 = new setupAGame();

        }
        if (sel.equals(loadGameButton)) {
            if (new File(System.getProperty("user.home") + "/Desktop/cstopsaves/").exists()) {
                JFileChooser fc = new JFileChooser(System.getProperty("user.home") + "/Desktop/cstopsaves/");
                int returnVal = fc.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    game gm = new game();
                    try {
                        int tmp = gm.loadGame(file);
                        if (tmp == 1){
                            JOptionPane.showMessageDialog(this, "Invalid Save File", "Invalid Save File", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            else {
                JOptionPane.showMessageDialog(this, "No saved games", "No saved games", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (sel.equals(aboutButton)) {
            aboutWindow aboutWindow1 = new aboutWindow();

        }
        if (sel.equals(helpButton)) {
            try {Desktop.getDesktop().browse(new URL("https://jmac.org/gamelab2011/cant_stop_rules.pdf").toURI());}
            catch (Exception e) {System.out.println("error");}
        }
        if (sel.equals(settingsButton)) {
            settingsWindow settingsWindow = new settingsWindow();
        }

    }
}
