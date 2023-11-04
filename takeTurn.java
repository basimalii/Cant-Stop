

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Take turn for Cant Stop
 *
 * @author Will
 */

public class takeTurn extends JDialog implements ActionListener
{
    private JPanel dicePanel, comboPanel, buttonPanel, panel;
    public ArrayList<Integer> combo;
    private JButton cButton, rButton;
    private ButtonGroup diceGroup;
    private dice d0,d1,d2,d3;
    private JLabel p1,p2,inLabel;

    public takeTurn(JFrame parent, Integer[][] pDat)
    {

        super(parent, ModalityType.APPLICATION_MODAL);

        combo = new ArrayList<Integer>();

        p1 = new JLabel("Set A:  ...");
        p2 = new JLabel("Set B:  ...");
        p1.setAlignmentX(Component.CENTER_ALIGNMENT);
        p2.setAlignmentX(Component.CENTER_ALIGNMENT);



        dicePanel = new JPanel();
        d0 = new dice();
        d1 = new dice();
        d2 = new dice();
        d3 = new dice();
        dicePanel.setLayout(new BoxLayout(dicePanel, BoxLayout.X_AXIS));
        dicePanel.add(d0);
        dicePanel.add(Box.createRigidArea(new Dimension(3, 0)));
        dicePanel.add(d1);
        dicePanel.add(Box.createRigidArea(new Dimension(3, 0)));
        dicePanel.add(d2);
        dicePanel.add(Box.createRigidArea(new Dimension(3, 0)));
        dicePanel.add(d3);

        d0.addActionListener(this);
        d1.addActionListener(this);
        d2.addActionListener(this);
        d3.addActionListener(this);


        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        cButton = new JButton("Continue");
        rButton = new JButton("Reselect");
        buttonPanel.add(cButton);
        buttonPanel.add(rButton);
        rButton.addActionListener(this);
        cButton.addActionListener(this);

        panel = new JPanel();
        inLabel = new JLabel("Select pairs of dice: ");
        inLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(inLabel);
        panel.add(dicePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 7)));
        panel.add(p1);
        panel.add(p2);
        panel.add(Box.createRigidArea(new Dimension(0, 7)));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(buttonPanel);
        panel.setBorder(new EmptyBorder(new Insets(10,10,10,10)));


        this.add(panel, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);

    }

    private void selReset(){

        combo = new ArrayList<Integer>();

        p1.setText("Set A:  ...");
        p2.setText("Set B:  ...");

        d0.deselectDice();
        d1.deselectDice();
        d2.deselectDice();
        d3.deselectDice();

    }

    public void actionPerformed (ActionEvent aevt)
    {
        Object sel = aevt.getSource();
        if (combo == null || combo.size() < 4){
            if (sel.equals(d0) && !d0.isPicked()) {
                d0.selectDice();
                combo.add(d0.getValue());
            }
            if (sel.equals(d1) && !d1.isPicked()) {
                d1.selectDice();
                combo.add(d1.getValue());
            }
            if (sel.equals(d2) && !d2.isPicked()) {
                d2.selectDice();
                combo.add(d2.getValue());
            }
            if (sel.equals(d3) && !d3.isPicked()) {
                d3.selectDice();
                combo.add(d3.getValue());
            }
            if (combo.size() == 2){p1.setText("Set A:  "+(combo.get(0)+combo.get(1)));}
            if (combo.size() == 4){p2.setText("Set B:  "+(combo.get(2)+combo.get(3)));}
        }
        if (sel.equals(rButton)){selReset();}
        if (sel.equals(cButton) && combo.size() == 4){
            GameBoard.moves = combo;
            this.dispose();
        }
    }
}
