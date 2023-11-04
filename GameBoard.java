

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

/**
 * This java program has been created collaboratively by members of Group 15
 *
 * @author Basim Ali
 * @author Charan Boddu
 * @author William Pennell
 */

public class GameBoard extends JFrame implements ActionListener, MouseListener
{
	// gui components that are contained in this frame:
	private JPanel topPanel, gameBoardPanel, turnPanel, decisionPanel,  bottomPanel;
	private int currentPlayer;

	private JButton saveGameButton;
    private JButton rollDiceButton;
    private JButton passTurnButton;
	private GridSquare[][] gridSquares;	// squares to appear in grid formation in the gameBoardPanel
	private int musSel;
	private Clip clip;
	private Integer[][] pDat;		//2D array representation of players & board
	private int currMarkerCount;

	public static ArrayList<Integer> moves;

	public GameBoard(Integer[][] pDat, int currPlayer)
	{

		this.setTitle("CAN'T STOP!");
		this.setMinimumSize(new Dimension(800,800));
		this.setLocationRelativeTo(null);

		this.pDat = pDat;
		this.currentPlayer = currPlayer;
		
		//ImageIcon icon = new ImageIcon("logo.jpg");
		//this.setIconImage(icon.getImage());
		
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setBackground(Color.black);
		
		gameBoardPanel = new JPanel();
		gameBoardPanel.setLayout(new GridLayout(13, 13, 6, 6));
		gameBoardPanel.setSize(500,500);
		gameBoardPanel.setBackground(Color.black);
				
		// for the top panel:
		saveGameButton = new JButton("SAVE GAME");
		saveGameButton.setBackground(Color.WHITE);
		saveGameButton.setFocusable(false);
		saveGameButton.setOpaque(true);
		saveGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveGame();
			}
		});

		topPanel.add (saveGameButton, BorderLayout.WEST);

		// for the bottom panel:	
		// create the squares and add them to the grid
		gridSquares = new GridSquare[13][13];
		for ( int x = 0; x < 13; x ++)
		{
			for ( int y = 0; y < 13; y ++)
			{
				gridSquares[x][y] = new GridSquare(x, y);
				gridSquares[x][y].setSize(20, 20);
				gridSquares[x][y].setColor(isGameBoardSquare(x,y));
				setGridSqText(x,y);

				gridSquares[x][y].addMouseListener(this);

				gameBoardPanel.add(gridSquares[x][y]);
			}
		}
		
		//JLabel toBeImplementedLabel = new JLabel("TURN INFO PANEL -- TO BE IMPLEMENTED!!");
		//toBeImplementedLabel.setForeground(Color.white);
		turnPanel = new JPanel();
		turnPanel.setBackground(Color.black);
		turnPanel.setLayout(new FlowLayout());
		//turnPanel.add(toBeImplementedLabel);
		
		decisionPanel = new JPanel();
		decisionPanel.setLayout(new FlowLayout());
		
		rollDiceButton = new JButton("ROLL DICE");
		rollDiceButton.addActionListener(this);
		rollDiceButton.setBackground(Color.GREEN);
		rollDiceButton.setOpaque(true);
		
		passTurnButton = new JButton("PASS TURN");
		passTurnButton.addActionListener(this);
		passTurnButton.setBackground(Color.RED);
		passTurnButton.setOpaque(true);
		
		decisionPanel.add(rollDiceButton);
		decisionPanel.add(passTurnButton);
		decisionPanel.setBackground(Color.black);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(gameBoardPanel, BorderLayout.CENTER);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		bottomPanel.add(turnPanel);
		bottomPanel.add(decisionPanel);
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		updateTurnPanel();
		updateBoard();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int userchoice = JOptionPane.showConfirmDialog(gameBoardPanel, "Do you want to return to the main menu? Click No to exit the game.","Confirm Exit", JOptionPane.YES_NO_OPTION);
				if(userchoice == JOptionPane.YES_OPTION) {
					dispose();
					clip.stop();
				}
				else if(userchoice == JOptionPane.NO_OPTION) {
					dispose();
					System.exit(0);
				}
			}
		});
		
		loadConfig();
		playSound(musSel);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	public void setGridSqText(int x, int y) {
	    JLabel textLabel = new JLabel();
	    Font labelFont = new Font("Century Gothic",Font.BOLD, 20);
	    textLabel.setFont(labelFont);
	    
	    Map<List<Integer>, String> textMap = new HashMap<>();
	    textMap.put(Arrays.asList(5, 1), "2");
	    textMap.put(Arrays.asList(4, 2), "3");
	    textMap.put(Arrays.asList(3, 3), "4");
	    textMap.put(Arrays.asList(2, 4), "5");
	    textMap.put(Arrays.asList(1, 5), "6");
	    textMap.put(Arrays.asList(0, 6), "7");
	    textMap.put(Arrays.asList(1, 7), "8");
	    textMap.put(Arrays.asList(2, 8), "9");
	    textMap.put(Arrays.asList(3, 9), "10");
	    textMap.put(Arrays.asList(4, 10), "11");
	    textMap.put(Arrays.asList(5, 11), "12");
	    textMap.put(Arrays.asList(8, 1), "C");
	    textMap.put(Arrays.asList(9, 2), "A");
	    textMap.put(Arrays.asList(10, 3), "N");
	    textMap.put(Arrays.asList(11, 4), "'T");
	    textMap.put(Arrays.asList(11, 8), "S");
	    textMap.put(Arrays.asList(10, 9), "T");
	    textMap.put(Arrays.asList(9, 10), "O");
	    textMap.put(Arrays.asList(8, 11), "P");
	    
	    String text = textMap.getOrDefault(Arrays.asList(x, y), "");
	    textLabel.setText(text);
	    textLabel.setForeground(Color.white);
	    gridSquares[x][y].add(textLabel);
	}

	
	public boolean isGameBoardSquare(int x, int y) 
	{
		     if((x==0)  && ((y==0) || (y==1) || (y==2) || (y==3) || (y==4) || (y==5) || (y==7) || (y==8) || (y==9) || (y==10) || (y==11) || (y==12))) { return false; }
		else if((x==1)  && ((y==0) || (y==1) || (y==2) || (y==3) || (y==4) || (y==8) || (y==9) || (y==10) || (y==11) || (y==12))) { return false; }
		else if((x==2)  && ((y==0) || (y==1) || (y==2) || (y==3) || (y==9) || (y==10) || (y==11) || (y==12))) { return false; }
		else if((x==3)  && ((y==0) || (y==1) || (y==2) || (y==10) || (y==11) || (y==12))) { return false; }
		else if((x==4)  && ((y==0) || (y==1) || (y==11) || (y==12))) { return false; }
		else if((x==5)  && ((y==0) || (y==12))) { return false; }
		else if((x==6)  && ((y==0) || (y==12))) { return false; }
		else if((x==7)  && ((y==0) || (y==12))) { return false; }
		else if((x==8)  && ((y==0) || (y==1) || (y==11) || (y==12))) { return false; }
		else if((x==9)  && ((y==0) || (y==1) || (y==2) || (y==10) || (y==11) || (y==12))) { return false; }
		else if((x==10) && ((y==0) || (y==1) || (y==2) || (y==3) || (y==9) || (y==10) || (y==11) || (y==12))) { return false; }
		else if((x==11) && ((y==0) || (y==1) || (y==2) || (y==3) || (y==4) || (y==8) || (y==9) || (y==10) || (y==11) || (y==12))) { return false; }
		else if((x==12) && ((y==0) || (y==1) || (y==2) || (y==3) || (y==4) || (y==5) || (y==7) || (y==8) || (y==9) || (y==10) || (y==11) || (y==12))) { return false; }
		
		return true;
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
	

	public void actionPerformed(ActionEvent aevt) {
		if(aevt.getSource() == passTurnButton) {
			currMarkerCount = 0;
			endTurn();
			currentPlayer = (currentPlayer + 1);
			if(currentPlayer == 5) {
				currentPlayer = 1;
			}
			updateTurnPanel();
		}

		if(aevt.getSource() == rollDiceButton) {
			moves = new ArrayList<Integer>();
			takeTurn takeTurn1 = new takeTurn(this, pDat);
			moveRunners();
		}
	}
	
	protected void updateTurnPanel() {
	    JLabel turnLabel = new JLabel("TURN INFO PANEL:");
	    turnLabel.setForeground(Color.white);
	    turnPanel.removeAll();
	    turnPanel.add(turnLabel);
	    turnPanel.add(Box.createHorizontalStrut(10));


	    Map<Integer, Color> playerColors = new HashMap<>();
	    playerColors.put(1, Color.MAGENTA);
	    playerColors.put(2, Color.BLUE);
	    playerColors.put(3, Color.ORANGE);
	    playerColors.put(4, Color.GREEN);

	    
	    for (int i = 1; i <= 4; i++) {
	        JPanel colorPanel = new JPanel();
	        colorPanel.setPreferredSize(new Dimension(20, 20));
	        colorPanel.setBackground(playerColors.get(i));
	        if (i == currentPlayer) {
	            colorPanel.setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(Color.white, 2),
	                BorderFactory.createEmptyBorder(5, 5, 5, 5)
	            ));
	            JLabel asteriskLabel = new JLabel("*");
				asteriskLabel.setForeground(Color.white);
	            asteriskLabel.setFont(new Font("Arial", Font.BOLD, 18));
	            colorPanel.setLayout(new GridBagLayout());
	            GridBagConstraints gbc = new GridBagConstraints();
	            gbc.gridx = 0;
	            gbc.gridy = 0;
	            colorPanel.add(asteriskLabel, gbc);
	        }
	        turnPanel.add(colorPanel);
	        turnPanel.add(Box.createHorizontalStrut(5)); 
	    }

	    
	    turnPanel.revalidate();
	    turnPanel.repaint();
	}

	public void playSound(int musSel) {
		try {

			if (musSel != 0){
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("resources/"+musSel+".wav"));
				clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
			}

		} catch(Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}

	private void saveGame() {

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();
		String filename = "cant_stop_" + dateFormat.format(date) + ".csv";

		Path path = Paths.get(System.getProperty("user.home") + "/Desktop/cstopsaves/");
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		File file = new File(System.getProperty("user.home") + "/Desktop/cstopsaves/" + filename);


		//Using a 2D array for storing the current state of the scores.
		Integer [][] scores = pDat;

//		// Initializing all scores to 0
//
//		for (int i = 0; i < scores.length; i++) {
//			for (int j = 0; j < scores[i].length; j++) {
//				scores[i][j] = 0;
//			}
//		}

		try {
			FileWriter fw = new FileWriter(file);

			// To check if this is a can't stop savegame
			fw.write("69420\n");

			// Writing the game state information to the file
			fw.write(currentPlayer + "\n");


			// Writing the 2D array of scores to the file
			for (int i = 0; i < scores.length; i++) {
				for (int j = 0; j < scores[i].length; j++) {
					fw.write(scores[i][j] + ",");
				}
				fw.write("\n");
			}

			fw.close();
			JOptionPane.showMessageDialog(this, "Game saved successfully to Desktop as " + filename, "Save Game", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Failed to save game: " + e.getMessage(), "Save Game Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	protected void updateBoard() {
		for (int y = 0; y < pDat.length; y++) {
			for (int x = 0; x < pDat[y].length; x++) {
				if (y == 1 || y == 11){gridSquares[12-5-x][y].changePlayer(pDat[y][x]);}
				else if (y == 2 || y == 10){gridSquares[12-4-x][y].changePlayer(pDat[y][x]);}
				else if (y == 3 || y == 9){gridSquares[12-3-x][y].changePlayer(pDat[y][x]);}
				else if (y == 4 || y == 8){gridSquares[12-2-x][y].changePlayer(pDat[y][x]);}
				else if (y == 5 || y == 7){gridSquares[12-1-x][y].changePlayer(pDat[y][x]);}
				else if (y == 6){gridSquares[12-x][y].changePlayer(pDat[y][x]);}
			}
		}
	}

	private void moveRunners() {

		int m1 = moves.get(0) + moves.get(1);
		int m2 = moves.get(2) + moves.get(3);
		int strikes = 0;

		Integer[][] pDatO = Arrays.copyOf(pDat, pDat.length);

		int pieceCount = 0;

		for (int y = 0; y < pDat.length; y++) {
			for (int x = 0; x < pDat[y].length; x++) {
				if (Integer.toString(pDat[y][x]).contains(Integer.toString(currentPlayer)) || pDat[y][x] % 5 == 0 && pDat[y][x]  != 0){
					pieceCount += 1;
				}
			}
		}
		currMarkerCount = pieceCount;

		if (pDat[m1-1][pDat[m1-1].length-1] == 0){

			// MOVE 1
			// marker already on board, find index of marker and increase it by one
			if (Arrays.stream(pDat[m1 - 1]).anyMatch(i -> i % 5 == 0 && i != 0)) {

				int i = 0;

				// find index w/ marker location
				for (int p = 0; p < pDat[m1 - 1].length; p++) {
					if (pDat[m1 - 1][p] % 5 == 0 && pDat[m1 - 1][p] != 0) {
						i = p;
					}
				}

				if (i+1 < pDat[m1 - 1].length){
					pDat[m1 - 1][i] -= 5;
					pDat[m1 - 1][i] = pDat[m1 - 1][i] / 10;
					pDat[m1 - 1][i + 1] = pDat[m1 - 1][i + 1] * 10 + 5;
				}
			}

			else if (currMarkerCount >= 3) {

				// marker not on board but player already has pieces on board
				if (Arrays.stream(pDat[m1 - 1]).anyMatch(i -> Integer.toString(i).contains(Integer.toString(currentPlayer)))) {

					int i = 0;

					for (int p = 0; p < pDat[m1 - 1].length; p++) {
						if (Integer.toString(pDat[m1 - 1][p]).contains(Integer.toString(currentPlayer))) {

							i = p;

						}
					}
					if (i+1 < pDat[m1 - 1].length) {
						pDat[m1 - 1][i + 1] = pDat[m1 - 1][i + 1] * 10 + 5;
					}
				}

				else {strikes += 1;}

			}

			else if (currMarkerCount < 3) {
				// first move, marker placed at start
				if (!(Arrays.stream(pDat[m1 - 1]).anyMatch(i -> Integer.toString(i).contains(Integer.toString(currentPlayer))))) {
					pDat[m1 - 1][0] = pDat[m1 - 1][0] * 10 + 5;
				}
			}
		}

		pieceCount = 0;
		// update pieceCount
		for (int y = 0; y < pDat.length; y++) {
			for (int x = 0; x < pDat[y].length; x++) {
				if (Integer.toString(pDat[y][x]).contains(Integer.toString(currentPlayer)) || pDat[y][x] % 5 == 0 && pDat[y][x]  != 0){
					pieceCount += 1;
				}
			}
		}
		currMarkerCount = pieceCount;

		if (pDat[m2-1][pDat[m2-1].length - 1] == 0){

			// MOVE 2
			// marker already on board, find index of marker and increase it by one
			if (Arrays.stream(pDat[m2 - 1]).anyMatch(i -> i % 5 == 0 && i != 0)) {

				int i = 0;

				// find index w/ marker location
				for (int p = 0; p < pDat[m2 - 1].length; p++) {
					if (pDat[m2 - 1][p] % 5 == 0 && pDat[m2 - 1][p] != 0) {
						i = p;
					}
				}

				if (i+1 < pDat[m2 - 1].length) {
					pDat[m2 - 1][i] -= 5;
					pDat[m2 - 1][i] = pDat[m2 - 1][i] / 10;
					pDat[m2 - 1][i + 1] = pDat[m2 - 1][i + 1] * 10 + 5;
				}
			}

			else if (currMarkerCount >= 3) {

				// marker not on board but player already has pieces on board
				if (Arrays.stream(pDat[m2 - 1]).anyMatch(i -> Integer.toString(i).contains(Integer.toString(currentPlayer)))) {

					int i = 0;

					for (int p = 0; p < pDat[m2 - 1].length; p++) {
						if (Integer.toString(pDat[m2 - 1][p]).contains(Integer.toString(currentPlayer))) {

							i = p;

						}
					}
					if (i+1 < pDat[m2 - 1].length) {
						pDat[m2 - 1][i + 1] = pDat[m2 - 1][i + 1] * 10 + 5;
					}
				}

				else {strikes += 1;}

			}

			else if (currMarkerCount < 3) {
				// first move, marker placed at start

				if (!(Arrays.stream(pDat[m2 - 1]).anyMatch(i -> Integer.toString(i).contains(Integer.toString(currentPlayer))))) {
					pDat[m2 - 1][0] = pDat[m2 - 1][0] * 10 + 5;
				}
			}
		}

		System.out.println(currMarkerCount);
		if (strikes >= 2 || pDat == pDatO){bustCurrPlayer();}
		updateBoard();
	}
	private void moveRunnersBACKUP() {

		int m1 = moves.get(0) + moves.get(1);
		int m2 = moves.get(2) + moves.get(3);
		int strikes = 0;

		int pieceCount = 0;

		if (pDat[m1-1][pDat[m1-1].length] == 0){












		}


		for (int y = 0; y < pDat.length; y++) {
			for (int x = 0; x < pDat[y].length; x++) {
				if (Integer.toString(pDat[y][x]).contains(Integer.toString(currentPlayer)) || pDat[y][x] % 5 == 0 && pDat[y][x]  != 0){
					pieceCount += 1;
				}
			}
		}
		currMarkerCount = pieceCount;

		// MOVE 1
		// marker already on board, find index of marker and increase it by one
		if (Arrays.stream(pDat[m1 - 1]).anyMatch(i -> i % 5 == 0 && i != 0)) {

			int i = 0;

			// find index w/ marker location
			for (int p = 0; p < pDat[m1 - 1].length; p++) {
				if (pDat[m1 - 1][p] % 5 == 0 && pDat[m1 - 1][p] != 0) {
					i = p;
				}
			}

			if (i+1 < pDat[m1 - 1].length){
				pDat[m1 - 1][i] -= 5;
				pDat[m1 - 1][i] = pDat[m1 - 1][i] / 10;
				pDat[m1 - 1][i + 1] = pDat[m1 - 1][i + 1] * 10 + 5;
			}
		}

		else if (currMarkerCount >= 3) {

			// marker not on board but player already has pieces on board
			if (Arrays.stream(pDat[m1 - 1]).anyMatch(i -> Integer.toString(i).contains(Integer.toString(currentPlayer)))) {

				int i = 0;

				for (int p = 0; p < pDat[m1 - 1].length; p++) {
					if (Integer.toString(pDat[m1 - 1][p]).contains(Integer.toString(currentPlayer))) {

						i = p;

					}
				}
				if (i+1 < pDat[m1 - 1].length) {
					pDat[m1 - 1][i + 1] = pDat[m1 - 1][i + 1] * 10 + 5;
				}
			}

			else {strikes += 1;}

		}

		else if (currMarkerCount < 3) {
			// first move, marker placed at start
			if (!(Arrays.stream(pDat[m1 - 1]).anyMatch(i -> Integer.toString(i).contains(Integer.toString(currentPlayer))))) {
				pDat[m1 - 1][0] = pDat[m1 - 1][0] * 10 + 5;
			}
		}

		pieceCount = 0;
		// update pieceCount
		for (int y = 0; y < pDat.length; y++) {
			for (int x = 0; x < pDat[y].length; x++) {
				if (Integer.toString(pDat[y][x]).contains(Integer.toString(currentPlayer)) || pDat[y][x] % 5 == 0 && pDat[y][x]  != 0){
					pieceCount += 1;
				}
			}
		}
		currMarkerCount = pieceCount;

		// MOVE 2
		// marker already on board, find index of marker and increase it by one
		if (Arrays.stream(pDat[m2 - 1]).anyMatch(i -> i % 5 == 0 && i != 0)) {

			int i = 0;

			// find index w/ marker location
			for (int p = 0; p < pDat[m2 - 1].length; p++) {
				if (pDat[m2 - 1][p] % 5 == 0 && pDat[m2 - 1][p] != 0) {
					i = p;
				}
			}

			if (i+1 < pDat[m2 - 1].length) {
				pDat[m2 - 1][i] -= 5;
				pDat[m2 - 1][i] = pDat[m2 - 1][i] / 10;
				pDat[m2 - 1][i + 1] = pDat[m2 - 1][i + 1] * 10 + 5;
			}
		}

		else if (currMarkerCount >= 3) {

			// marker not on board but player already has pieces on board
			if (Arrays.stream(pDat[m2 - 1]).anyMatch(i -> Integer.toString(i).contains(Integer.toString(currentPlayer)))) {

				int i = 0;

				for (int p = 0; p < pDat[m2 - 1].length; p++) {
					if (Integer.toString(pDat[m2 - 1][p]).contains(Integer.toString(currentPlayer))) {

						i = p;

					}
				}
				if (i+1 < pDat[m2 - 1].length) {
					pDat[m2 - 1][i + 1] = pDat[m2 - 1][i + 1] * 10 + 5;
				}
			}

			else {strikes += 1;}

		}

		else if (currMarkerCount < 3) {
			// first move, marker placed at start

			if (!(Arrays.stream(pDat[m2 - 1]).anyMatch(i -> Integer.toString(i).contains(Integer.toString(currentPlayer))))) {
				pDat[m2 - 1][0] = pDat[m2 - 1][0] * 10 + 5;
			}
		}

		System.out.println(currMarkerCount);
		if (strikes >= 2){bustCurrPlayer();}
		updateBoard();
	}

	private void endTurn(){

		for (int y = 0; y < pDat.length; y++) {
			for (int x = 0; x < pDat[y].length; x++) {
				if (Integer.toString(pDat[y][x]).contains(Integer.toString(5))){
					if (Arrays.stream(pDat[y]).anyMatch(i -> Integer.toString(i).contains(Integer.toString(currentPlayer)))){
						int i = 0;

						// find index w/ player location
						for (int p = 0; p < pDat[y].length; p++) {
							if (Integer.toString(pDat[y][p]).contains(Integer.toString(currentPlayer))) {
								i = p;
							}
						}

						String s = (Integer.toString(pDat[y][i]).replace(Integer.toString(currentPlayer),"0"));
						pDat[y][i] = Integer.parseInt(s);
					}
					pDat[y][x] = pDat[y][x] - 5 + currentPlayer;
				}
			}
		}

		updateBoard();
	}

	private void bustCurrPlayer(){


		for (int y = 0; y < pDat.length; y++) {
			for (int x = 0; x < pDat[y].length; x++) {
				if (!(pDat[y][x] == 0) && (pDat[y][x] % 5 == 0)){
					pDat[y][x] -= 5;
					pDat[y][x] = pDat[y][x] / 10;
				}
			}
		}

		currMarkerCount = 0;
		currentPlayer = (currentPlayer + 1);
		if(currentPlayer == 5) {
			currentPlayer = 1;
		}
		updateTurnPanel();
	}


	public void mouseClicked(MouseEvent mevt)
	{
		
	}
	
	// not used but must be present to fulfill MouseListener contract
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
