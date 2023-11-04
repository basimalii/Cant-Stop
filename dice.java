

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


/*
 * @author William Pennell
 * @author Charan Boddu
 */

public class dice extends JButton {
    private int value;
    private boolean selected;

    private ImageIcon[] diceIcons;

    public dice() {
        super();
        Random random = new Random();
        value = random.nextInt(6) + 1;
        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream("resources/dice_" + value + ".png"));
            Image scaledImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaledImg));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(75, 75));
    }

    public int getValue() {
        return value;
    }

    public boolean isPicked() {
        return selected;
    }

    public void selectDice() {
        setBackground(Color.GRAY);
        selected = true;
    }

    public void deselectDice() {
        setBackground(Color.WHITE);
        selected = false;
    }

    public void bust() {
        setBackground(Color.RED);
        selected = true;
    }
}