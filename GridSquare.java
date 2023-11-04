

import java.awt.*;
import javax.swing.*;
/**
 * @author Basim Ali
 * @author William Pennell
 */
public class GridSquare extends JPanel
{
    private int xcoord, ycoord;  // location in the grid
	private JLabel textLabel = new JLabel();
	
	// constructor takes the x and y coordinates of this square
	public GridSquare(int xcoord, int ycoord)
	{
		super();
		this.setSize(50,50);
		this.setBackground(Color.red);
		this.xcoord = xcoord;
		this.ycoord = ycoord;
		//this.textLabel = new JLabel(String.valueOf(xcoord) +" "+ String.valueOf(ycoord));
		this.textLabel = new JLabel();
		this.textLabel.setForeground(Color.white);
		Font labelFont = new Font("Century Gothic",Font.PLAIN, 16);
		textLabel.setFont(labelFont);
		this.add(textLabel);
	}
	
    public void setColor(boolean decider)
    {
        Color colour = decider == true ? Color.red : Color.black;
        this.setBackground(colour);
    }

	public void changePlayer(int player)
	{
		if (player == 1){
			setBackground(Color.magenta);
			this.textLabel.setText("1");
			this.textLabel.setForeground(Color.white);
		}
		else if (player == 2){
			setBackground(Color.blue);
			this.textLabel.setText("2");
			this.textLabel.setForeground(Color.white);

		}
		else if (player == 3){
			setBackground(Color.orange);
			this.textLabel.setText("3");
			this.textLabel.setForeground(Color.white);
		}
		else if (player == 4){
			setBackground(Color.green);
			this.textLabel.setText("4");
			this.textLabel.setForeground(Color.white);
		}
		else if (player == 5){
			setBackground(Color.white);
			this.textLabel.setText("R");
			this.textLabel.setForeground(Color.black);
		}

		else if (player == 0){
			setBackground(Color.red);
			this.textLabel.setText("");
			this.textLabel.setForeground(Color.white);
		}

		else if (player > 9) {
			//setBackground(Color.decode("#964B00"));
			setBackground(Color.LIGHT_GRAY);
			String label = Integer.toString(player).replace("0","").replace("5","R");
			this.textLabel.setText(label);


			//String label = "";
			//int firstDigit = Integer.parseInt(Integer.toString(player).substring(0, 1));
			//int secondDigit = Integer.parseInt(Integer.toString(player).substring(1, 2));
			//int thirdDigit = 0;
			//int fourthDigit = 0;
			//int fifthDigit = 0;

			//if (player > 99) {
			//	thirdDigit = Integer.parseInt(Integer.toString(player).substring(2, 3));
			//} else if (player > 999) {
			//	fourthDigit = Integer.parseInt(Integer.toString(player).substring(3, 4));
			//} else if (player > 9999) {
			//	fifthDigit = Integer.parseInt(Integer.toString(player).substring(4, 5));
			//}


			//label.


		}

	}
//    // simple setters and getters
//    public void setXcoord(int value)    { xcoord = value; }
//    public void setYcoord(int value)    { ycoord = value; }
//    public int getXcoord()              { return xcoord; }
//    public int getYcoord()              { return ycoord; }
}

