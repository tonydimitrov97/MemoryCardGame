/**
 * MemoryGame.java
 * Creates a GUI and handles clicking of memory game
 * Date: 5-11-17
 * Java 1125
 * Spring 2017
 * Homework Assignment 5
 * @author Antonio Dimitrov
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MemoryGame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	/*Private variables*/
	private JMenuBar menuBar;
	private JLabel label;
	private ArrayList<Card> cardLineUp = new ArrayList<Card>();
	private Card cardOne = null;
	private Card cardTwo = null;
	private int numMoves = 0;
	private JPanel center;
	private boolean freezeButtons = false;
	private int numCorrect = 0;
	
	/**
	 * Constructor for Memory Game which sets up the GUI
	 */
	public MemoryGame(){
		/*Set up frame*/
		super("Memory Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(800, 600);
		
		/*Add menu bar*/
		menuBar = new JMenuBar();	
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		JMenu gameMenu = new JMenu("Game");
		/*Add sub menus*/
		createFileExit(fileMenu);	
		createRestartMenu(gameMenu);
		createNewGame(gameMenu);
		menuBar.add(fileMenu);
		menuBar.add(gameMenu);
		
		/*Add middle grid*/
		center = new JPanel();
		center.setLayout(new GridLayout(4, 4));
		fillJButtonArray();
		
		/*Add bottom label*/
		label = new JLabel("Number of Moves " + numMoves);
		
		/*Add grid to center and label to south*/
		add(center, BorderLayout.CENTER);
		add(label, BorderLayout.SOUTH);	
		
		/*Display the GUI*/
		setVisible(true);
	}//end Constructor

	/**
	 * Creates the New Game submenu
	 * @param gameMenu
	 */
	public void createNewGame(JMenu gameMenu) {
		JMenuItem newGameMenu = new JMenuItem("New Game");
		newGameMenu.addActionListener(new newGameListener());
		gameMenu.add(newGameMenu);
	}//end createNewGame

	/**
	 * Fills Array with cards (two of each image)
	 */
	public void fillJButtonArray() {
		JButton[] buttonArr = new JButton[16];
		
		/*Adds 16 cards (two of which have same image) to Arraylist*/
		for (int i = 0; i < 16; i++){	
			buttonArr[i] = new JButton();
			Card card = new Card("Card" + ((i/2) +1) + ".png", buttonArr[i]);
			cardLineUp.add(card);
		}//end for
		
		/*Shuffles cards*/
		Collections.shuffle(cardLineUp);
		
		/*Adds cards to JButton Grid*/
		for (int i = 0; i < 16; i++){
			center.add(cardLineUp.get(i).getButton());
			cardLineUp.get(i).flipBack();
			cardLineUp.get(i).getButton().addActionListener
				(new ButtonListener(cardLineUp.get(i)));
		}//end for
	}//end fillJButtonArray

	/**
	 * Creates restart submenu
	 * @param gameMenu
	 */
	public void createRestartMenu(JMenu gameMenu) {
		JMenuItem restartMenu = new JMenuItem("Restart");
		restartMenu.addActionListener(new restartListener());
		gameMenu.add(restartMenu);
	}//end create Restart Menu

	/**
	 * Creates exit submenu
	 * @param fileMenu
	 */
	public void createFileExit(JMenu fileMenu) {
		JMenuItem fileExit = new JMenuItem("Exit");
		fileExit.addActionListener(new exitListener());
		fileMenu.add(fileExit);
	}//end createFileExit
	
	/**
	 * Action Listener for the exit menu
	 * @author Antonio Dimitrov
	 */
	private class exitListener implements ActionListener {

		@Override
		/**
		 * Exits game if yes is clicked, else do nothing
		 */
		public void actionPerformed(ActionEvent arg0) {
			int value = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to exit?");
			if (value == JOptionPane.YES_OPTION)
				System.exit(1125);
		}//end actionPerformed
	}//end exitListener
	
	/**
	 * Action Listener for the card clicking
	 * @author Antonio Dimitrov
	 */
	private class ButtonListener implements ActionListener {

		private Card myCard;
		
		/**
		 * Constructor for card
		 * @param card
		 */
		public ButtonListener(Card card) {
			myCard = card;
		}//end Constructor

		@Override
		/**
		 * Checks both flipped cards when one is flipped, if the same keep 
		 * face up else face both down
		 */
		public void actionPerformed(ActionEvent e){	
			if (!myCard.isUpWards() && !freezeButtons){
				myCard.flipFront();
				myCard.setUpWards(true);
					
				if (cardOne == null)
						cardOne = myCard;
				else{	
					cardTwo = myCard;
						
					if (!(cardOne.getFrontImage().equals
							(cardTwo.getFrontImage()))){	
						/*Pauses screen for two seconds and turns cards over*/
						freezeButtons = true;
						turnCards();
					}
					else{
						cardOne = null;
						numCorrect++;
						/*If correct is 8 (all pairs) add dialogue box*/
						if (numCorrect == 8){
							JOptionPane.showMessageDialog(null,
								"Congratulations!\n Total Number Of Moves: "
										+ numMoves);
							numCorrect = 0;
						}//end if
					}//end else	
						
					label.setText("Number of Moves: " + ++numMoves);
				}//end else
			}//end if
		}//end actionPerformed

		/**
		 * Pauses and turns cards back
		 */
		public void turnCards() {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				/*Flips cards to Back and pauses*/
				@Override
				public void run() {	
					cardTwo.flipBack();
					cardOne.flipBack();
					cardOne = null;
					freezeButtons = false;
				}//end run
			}, 2000);//end Timer
		}//end turnCards
	}//end ButtonListener
	
	/**
	 * Action Listener for the restart menu
	 * @author Antonio Dimitrov
	 */
	private class restartListener implements ActionListener {

		@Override
		/**
		 * Flips cards back and sets boolean values of flipped to false
		 */
		public void actionPerformed(ActionEvent e) {
			/*Loops through grid and sets cards down without changing order*/
			for (int i = 0; i < 16; i++){
				cardLineUp.get(i).flipBack();
				cardLineUp.get(i).setUpWards(false);
				numMoves = 0;
				label.setText("Number of Moves: " + numMoves);
			}//end for
		}//end actionPerformed
	}//end restartListener
	
	/**
	 * Action Listener for the newGame
	 * @author Antonio Dimitrov
	 */
	private class newGameListener implements ActionListener {

		@Override
		/**
		 * Rearranges all of the cards and flips them down
		 */
		public void actionPerformed(ActionEvent e) {
		
			/*Removes Buttons from list*/
			for (int i = 0; i < 16; i++)
				center.remove(cardLineUp.get(i).getButton());
			
			/*Clears Arraylist, makes a new one and resets grid*/
			cardLineUp.clear();	
			fillJButtonArray();
			
			numMoves = 0;
			label.setText("Number of Moves: " + numMoves);
		}//end actionPerformed
	}//end newGameListener
}//end class