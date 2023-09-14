import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 * Set up a game of cantstop
 *
 * @author Will
 */

public class setupAGame extends JFrame implements ActionListener {

    private JPanel panel, playerPanel, aiPanel, optionPanel;
    private JButton startButton;
    private JRadioButton plyr2;
    private JRadioButton plyr3;
    private JRadioButton plyr4;
    private JRadioButton aiEasy;
    private JRadioButton aiHard;
    private ButtonGroup aiDiff;
    private ButtonGroup plyrCount;
    int aiDiffInt, plyrCountInt;

    public setupAGame() {

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.X_AXIS));
        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        aiPanel = new JPanel();
        aiPanel.setLayout(new BoxLayout(aiPanel, BoxLayout.Y_AXIS));

        startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(this);

        plyrCount = new ButtonGroup();
        plyr2 = new JRadioButton("2 players");
        plyr3 = new JRadioButton("3 players");
        plyr4 = new JRadioButton("4 players");
        plyrCount.add(plyr2);
        plyrCount.add(plyr3);
        plyrCount.add(plyr4);
        playerPanel.add(plyr2);
        playerPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        playerPanel.add(plyr3);
        playerPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        playerPanel.add(plyr4);
        playerPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Humans:"), new EmptyBorder(0,0,0,20)));

        aiDiff = new ButtonGroup();
        aiEasy = new JRadioButton("Easy");
        aiHard = new JRadioButton("Hard");
        aiDiff.add(aiEasy);
        aiDiff.add(aiHard);
        aiPanel.add(aiEasy);
        aiPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        aiPanel.add(aiHard);
        aiPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("AI Difficulty:"), new EmptyBorder(0,0,0,20)));

        optionPanel.add(playerPanel);
        optionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        optionPanel.add(aiPanel);

        panel.add(optionPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(startButton);
        panel.setBorder(new EmptyBorder(new Insets(10,60,10,60)));


        this.setTitle("Game setup");

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);

        setResizable(false);
        setVisible(true);



    }


    public void actionPerformed(ActionEvent aevt) {

        Object sel = aevt.getSource();

        if (sel.equals(startButton)){

            if (plyr2.isSelected()){plyrCountInt = 2;}
            else if (plyr3.isSelected()){plyrCountInt = 3;}
            else if (plyr4.isSelected()){plyrCountInt = 4;}

            if (aiEasy.isSelected()){aiDiffInt = 0;}
            else if (aiHard.isSelected()){aiDiffInt = 1;}

            game gm = new game();
            gm.startNewGame();
            this.dispose();

        }
    }
}