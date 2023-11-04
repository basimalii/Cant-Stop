

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.io.*;

/**
 * Settings prototype for cantStop game
 *
 * Designed to be modular, add additional settings panels to settingsPanel for minimal difficulty in adding global settings.
 * @author Will
 */

public class settingsWindow extends JFrame implements ActionListener {


    private JPanel panel, settingsPanel, savePanel;
    private JPanel musicPanel;
    private int musSel;
    private JRadioButton mute;
    private JRadioButton mus1;
    private JRadioButton mus2;
    private JRadioButton mus3;
    private JRadioButton mus4;
    private JRadioButton mus5;
    private ButtonGroup musicGroup;
    private JButton saveButton;
    private JButton cancelButton;

    public settingsWindow() {

        this.loadConfig();

        // main panels
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.X_AXIS));
        savePanel = new JPanel();
        savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.X_AXIS));

        // settingsPanel sub panels
        musicPanel = new JPanel();
        musicPanel.setLayout(new BoxLayout(musicPanel, BoxLayout.Y_AXIS));

        // savePanel setup
        saveButton = new JButton("Save Changes");
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveButton.addActionListener(this);
        cancelButton = new JButton("Cancel");
        cancelButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        cancelButton.addActionListener(this);
        savePanel.add(saveButton);
        savePanel.add(Box.createRigidArea(new Dimension(3, 0)));
        savePanel.add(cancelButton);

        // musicPanel setup
        mute = new JRadioButton("Mute");
        mus1 = new JRadioButton("Chill");
        mus2 = new JRadioButton("Jazz");
        mus3 = new JRadioButton("Intense");
        mus4 = new JRadioButton("Boring");
        mus5 = new JRadioButton("Hectic    ");
        if (musSel == 0){mute.setSelected(true);}
        else if (musSel == 1){mus1.setSelected(true);}
        else if (musSel == 2){mus2.setSelected(true);}
        else if (musSel == 3){mus3.setSelected(true);}
        else if (musSel == 4){mus4.setSelected(true);}
        else if (musSel == 5){mus5.setSelected(true);}

        musicGroup = new ButtonGroup();

        musicGroup.add(mute);
        musicGroup.add(mus1);
        musicGroup.add(mus2);
        musicGroup.add(mus3);
        musicGroup.add(mus4);
        musicGroup.add(mus5);

        musicPanel.add(mute);
        musicPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        musicPanel.add(mus1);
        musicPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        musicPanel.add(mus2);
        musicPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        musicPanel.add(mus3);
        musicPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        musicPanel.add(mus4);
        musicPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        musicPanel.add(mus5);
        musicPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Music:"));
        musicPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Music:"), new EmptyBorder(0,0,0,0)));

        // adding sub panels to settingsPanel
        settingsPanel.add(musicPanel);

        // all encompassing panel
        panel.add(settingsPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(savePanel);
        panel.setBorder(new EmptyBorder(new Insets(10,30,10,30)));

        // frame setup
        this.setTitle("Settings");

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);

        setResizable(false);
        setVisible(true);

    }


    public void actionPerformed (ActionEvent aevt) {

        Object sel = aevt.getSource();

        if (sel.equals(saveButton)){

            if (mute.isSelected()){musSel = 0;}
            else if (mus1.isSelected()){musSel = 1;}
            else if (mus2.isSelected()){musSel = 2;}
            else if (mus3.isSelected()){musSel = 3;}
            else if (mus4.isSelected()){musSel = 4;}
            else if (mus5.isSelected()){musSel = 5;}

            saveConfig();

            this.dispose();
        }

        else if (sel.equals(cancelButton)){

            this.dispose();

        }
    }


    public void saveConfig() {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            bw.write(musSel + "");


            bw.close();
            System.out.println("Settings saved");
        }

        catch (Exception e) {System.out.println("error");}
    }

    public void loadConfig() {

        try {

            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String l = br.readLine();
            musSel = Integer.parseInt(l);

            br.close();
            System.out.println("Settings loaded");
        }

        catch (FileNotFoundException e) {

            try {

                BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

                musSel = 1;
                bw.write(musSel + "");

                bw.close();
                System.out.println("Defaults created");
            }
            catch (Exception e1) {System.out.println("error");}
        }
        catch (IOException e) {System.out.println("error");}
    }
}