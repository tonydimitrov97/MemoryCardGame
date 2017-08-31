/**
 * Card.java
 * Card object with a front and back side with flips
 * @author Antonio Dimitrov
 */
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Card {
	/*Private variables*/
	private JButton button;
	private String backImage = "default.png";
	private String frontImage;
	ImageIcon back = new ImageIcon(backImage);
	private boolean isUpWards = false;
	
	/*Constructor and setters*/
	/**
	 * Constructor for card
	 * @param myFront
	 * @param myButton
	 */
	public Card(String myFront, JButton myButton){
		this.button = myButton;
		this.frontImage = myFront;	
	}//end constructor
	
	public void setUpWards(boolean isUpWards) {
			this.isUpWards = isUpWards;
	}//send setUpwards
	
	/*Getters*/
	public String getBackImage() {
		return backImage;
	}//end getBackImage

	public JButton getButton() {
		return button;
	}//end getButton

	public boolean isUpWards() {
		return isUpWards;
	}//end isUpwards

	public String getFrontImage() {
		return frontImage;
	}//end getFrontImage
	
	/*Other functions*/
	/**
	 * Resizes image to fit and makes card show the background
	 */
	public void flipBack() {
		ImageIcon icon = new ImageIcon(backImage);
		Image image = icon.getImage();
		Image img = image.getScaledInstance(180, 140,
				java.awt.Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(img));
		isUpWards = false;
	}//end flipBack
	
	/**
	 * Resizes image to fit and makes card show the front
	 */
	public void flipFront() {
		ImageIcon icon = new ImageIcon(frontImage);
		Image image = icon.getImage();
		Image img = image.getScaledInstance(180, 140, 
				java.awt.Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(img));
		isUpWards = true;
	}//end flipFront
}//end Card
