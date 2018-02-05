package groupAssignment2;
import java.awt.*;
import javax.swing.*;

public class GuiView {
	private JFrame mainFrame;
	private JLabel DisplayLabel;
	private JLabel PopOutLabel;
	private JButton PushButton1;
	private JButton PushButton2;
	private JButton PushButton3;
	private JButton PushButton4;
	private JButton PushButton5;
	private JButton PushButton6;
	private JButton CoinButton;
	private JButton GetPopButton;
	private JTextField CoinField;
	private JButton ChangeButton;
	private JCheckBox CreditCard; // current hardware does not support this, currently this button is useless
	private JLabel OOOLight;
	private JLabel ECOLight;
	private JTextArea CoinReturn;

	public GuiView(GuiController controller) {
		
		//Block sets up the window itself, change Gridlayout(y, X) for extra space
		mainFrame = new JFrame("Vending Machine Front-End simulation");
		mainFrame.setSize(600, 500);
		mainFrame.setLayout(new GridLayout(6, 3));
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//Create all the components for the window, and label them
		DisplayLabel = new JLabel("Display here");
		PopOutLabel = new JLabel("No pop vended");
		PushButton1 = new JButton("Pop 1");
		PushButton2 = new JButton("Pop 2");
		PushButton3 = new JButton("Pop 3");
		PushButton4 = new JButton("Pop 4");
		PushButton5 = new JButton("Pop 5");
		PushButton6 = new JButton("Pop 6");
		CoinButton = new JButton("Insert Coin");
		GetPopButton = new JButton("Remove pop");
		ChangeButton = new JButton("Return change");
		CoinField = new JTextField("coin value here");
		CreditCard = new JCheckBox("Connect Credit Card", false);
		OOOLight = new JLabel("ooo off");
		ECOLight = new JLabel("eco off");
		CoinReturn = new JTextArea("Coin return");
		
		//give all the buttons a function
		PushButton1.setActionCommand("pop1");
		PushButton2.setActionCommand("pop2");
		PushButton3.setActionCommand("pop3");
		PushButton4.setActionCommand("pop4");
		PushButton5.setActionCommand("pop5");
		PushButton6.setActionCommand("pop6");
		CoinButton.setActionCommand("insertCoin");
		GetPopButton.setActionCommand("removePop");
		ChangeButton.setActionCommand("returnChange");
		
		//Assign all of the buttons to a listener, the GuiController
		PushButton1.addActionListener(controller);
		PushButton2.addActionListener(controller);
		PushButton3.addActionListener(controller);
		PushButton4.addActionListener(controller);
		PushButton5.addActionListener(controller);
		PushButton6.addActionListener(controller);
		CoinButton.addActionListener(controller);
		CreditCard.addActionListener(controller);
		GetPopButton.addActionListener(controller);
		ChangeButton.addActionListener(controller);
		
		//add all of the components to the window
		mainFrame.add(PushButton1);
		mainFrame.add(PushButton2);
		mainFrame.add(PushButton3);
		mainFrame.add(PushButton4);
		mainFrame.add(PushButton5);
		mainFrame.add(PushButton6);
		mainFrame.add(CoinField);
		mainFrame.add(CoinButton);
		mainFrame.add(ChangeButton);
		mainFrame.add(DisplayLabel);
		mainFrame.add(PopOutLabel);
		mainFrame.add(GetPopButton);
		mainFrame.add(CreditCard);
		mainFrame.add(CoinReturn);
		mainFrame.add(OOOLight);
		mainFrame.add(ECOLight);
		mainFrame.setVisible(true);
	}
	
	//allow the controller to fetch the coin value
	public int getCoinField() {
		return Integer.parseInt(CoinField.getText());
	}
	//allows controller to update display
	public void updateDisplay(String text) {
		DisplayLabel.setText(text);
	}
	//allows controller to update pop dispensed
	public void updatePopOut(String pop) {
		PopOutLabel.setText(pop);
	}
	//allows controller to change out of order light
	public void updateOOOL(String state) {
		OOOLight.setText(state);
	}
	//allows controller to change exact change only light
	public void updateECOL(String state) {
		ECOLight.setText(state);
	}
	//allows controller to change coin return
	public void updateCoinReturn(String coinvalue) {
		CoinReturn.append(coinvalue);
	}
}
