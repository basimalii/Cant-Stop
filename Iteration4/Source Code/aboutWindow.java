import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Details about this project
 *
 * @author Will
 */

public class aboutWindow extends JFrame{

    private JPanel panel;
    private JLabel label;

    public aboutWindow(){

        panel = new JPanel();
        label = new JLabel("<html>Project for CS2005 - Winter Semester<br/>Created by Group 15<br/>Will Pennell, Charan Boddu, Somaditya Sinha, Syed Basim Ali</html>", SwingConstants.CENTER);

        panel.add(label, BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(new Insets(10,30,10,30)));

        this.add(panel);
        this.setTitle("About");

        this.pack();
        this.setVisible(true);

        setResizable(false);
        setVisible(true);

    }





}