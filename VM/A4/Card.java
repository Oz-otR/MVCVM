package A4;
/**
 * Card class to work with the credit or debit cards 
 */

import java.util.ArrayList;

public class Card 
{
	private double balance;
	private String bank;
	private int cardType;
	//The type of the cards are going to b 0 for a credit card, and 1 for a debit card 
	
	public Card(int type, String banks, double onCard)
	{
		this.balance = onCard;
		this.bank = banks;
		if(type < 0 || type > 1)
			throw new IllegalArgumentException("Wrong type of card, must be credit or debit");
		this.cardType = type;
	}
	
	public String getBankName()
	{
		return bank;
	}
	
	public double getCardBalance()
	{
		return balance;
	}
	
	public String getCardType()
	{
		if(cardType == 0)
			return("Credit");
		else if(cardType == 1)
			return("Debit");
		else 
			return("Invalid");
	}
	
	public static ArrayList<String> getAcceptedBanks()
	{
		return returnAcceptedBanks();
	}
	
	private static ArrayList<String> returnAcceptedBanks()
	{
		ArrayList<String> banks = new ArrayList<String>(); //top 5 banks 
		banks.add("RBC");
		banks.add("BMO");
		banks.add("Scotiabank");
		banks.add("TD");
		banks.add("CIBC");
		return banks;
	}
	
	public void setNewBalance(double newBalance)
	{
		balance = newBalance; //change balance after making a purchase 
	}
}
