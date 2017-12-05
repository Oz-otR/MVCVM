package Assignment4;

import java.awt.*;
import javax.swing.*;


public class BitcoinGUIpieces {
	
	VendingLogic logic;
	
	private boolean enableBTC;
	private JButton BTCButton;
	private JTextField BTCUserName;
	private JTextField BTCPin;
	
	private GuiController controller;
	
	
	/**
	 * Creates the required pieces for the BitCoin support. Feel free to tweek the ui elements to fit as you want.
	 * NOTE!! controll **MUST** call logic.bitcoinTransaction(); if action.equals("BTC"); //This is in the listener!
	 * @param logic, The VendingLogic handler fot the vending machine
	 * @param controller, the GuiController for the ui, again: **MUST** call logic.bitcoinTransaction(); if action.equals("BTC");
	 * @param mainFrame, the JFrame that the Bitcoin is part of.
	 * @throws IllegalStateException, if this is called and bitcoin is not enabled in the logic
	 */
	public BitcoinGUIpieces(VendingLogic logic, GuiController controller, JFrame mainFrame) throws IllegalStateException {
		this.logic = logic;
		this.controller = controller;
		enableBTC = logic.getEnabledBTC();
		
		//If this contructor is called and bitcoin is not enabled, this is an illegal state!
		if(enableBTC == false) throw new IllegalStateException("BitCoin not enabled in logic.");
		
	
		//We create the required fields
		BTCButton = new JButton("Scan BTC loaded card");
		BTCUserName = new JTextField("BTC User ID here");
		BTCPin = new JTextField("BTC User Pin here");
		
		//Assign an action command to the bitcoin button
		BTCButton.setActionCommand("BTC");
		
		//Register listeners for each component
		BTCButton.addActionListener(controller);
		BTCUserName.addActionListener(controller);
		BTCPin.addActionListener(controller);
		
		
		//Add it to the view, this assumes that the mainFrame will be shown at some point
		mainFrame.add(BTCButton);
		mainFrame.add(BTCUserName);
		mainFrame.add(BTCPin);
		
	}
	
	/**
	 * Method gets the text in the userNamefield
	 * @return returns the String in the UserName field
	 */
	public String getBTCUser()
	{
		if(enableBTC) {
			return BTCUserName.getText();
		}
		throw new IllegalStateException();
	}
	
	/**
	 * Method gets the text in the BTCPin field
	 * @return returns the String in the BTCPin field
	 */
	public String getBTCPin()
	{
		if(enableBTC) {
			return BTCPin.getText();
		}
		throw new IllegalStateException();
	}
	


}
