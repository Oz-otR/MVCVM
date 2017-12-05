package Assignment4;
import java.util.ArrayList;
import java.util.Random;

import org.lsmr.vending.hardware.AbstractHardware;
import org.lsmr.vending.hardware.AbstractHardwareListener;


/**
 * @author David Keizer
 * Class BitCoinListener is the listener for the expected bitcoin reception hardware.
 */
public class BitCoinListener implements btcListener {

	//Only thing this listener needs is the logic object.
	private VendingLogic vendinglogic;
	
	/**
	 *  Creates the listener for the BitCoin hardware
	 * @param logic, the VendingLogic object to call.
	 */
	public BitCoinListener(VendingLogic logic)
	{
		vendinglogic = logic;
		
	}

	/**
	 * Creates some temporary hardware that is used in lue of the actual hardware.
	 * @return VendingBTChardware with randomized parameters.
	 */
	public static VendingBTChardware tempCreateHardware() {
		return new VendingBTChardware();
	}
	

	/**
	 * Enables the harware using logic.
	 * As the hardware does not exist, this does nothing for now
	 */
	@Override	
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		//vendinglogic.enableHardware(null);	
	}

	/**
	 * Disables the harware using logic.
	 * As the hardware does not exist, this does nothing for now
	 */
	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		//vendinglogic.disableHardware(null);
		
	}

	/**
	 * Requires the logic to handle an expected bitcoin transaction.
	 */
	@Override
	public void bitcoinTransfer() {
		vendinglogic.bitcoinTransaction();		
	}
	
	

}

/**
 * @author David Keizer
 * Class VendingBTChardware is the expected bitcoin reception hardware.
 * This is use as a temporary placeholder for the real hardware.
 * This randomizes some elements for testing.
 */
class VendingBTChardware extends AbstractHardware<btcListener>{
	
	private ArrayList<String> btcPaymentMethods;
	private int userID;	  	//The current userpin entered
	private int userPin;	//The current userId entered
	private btcListener listener;
	private String paymentType;   //The current payment type chosen
	private double btc;  //The current amount of btc available.
	
	public VendingBTChardware() {
		
		//Set base variables to null for now.
		userID = -1;
		userPin = -1;
		paymentType = null;
		
		
		//Create a list of payments. Example ones are used for now.
		btcPaymentMethods = vmGetBTCPaymentMethods();

	}
	
	
	/**
	 * Creates a list of arbitrary payment methods, as BTC has no banks associated with it.
	 * @return ArrayList<String> of payment methods
	 */
	private ArrayList<String> vmGetBTCPaymentMethods() {
		ArrayList<String> paymentMethods = new ArrayList<String>();
		paymentMethods.add("NFC");
		paymentMethods.add("Direct Transfer");
		paymentMethods.add("Western Union");
		paymentMethods.add("Other");
		return paymentMethods;
	}
	
	/**
	 * Returns last entered userpin
	 * @return int userpin
	 */
	public int getUserpin() {
		return userPin;
	}
	
	/**
	 * returns the list of valid payment methods.
	 * @return ArrayList<String> payment methods.
	 */
	public ArrayList<String> getBTCpaymentMethods() {
		return btcPaymentMethods;
	}
	
	/**
	 * returns the last enetered userID
	 * @return int userID
	 */
	public int getUserID()
	{
		return userID;
	}
	
	/**
	 * Returns the BCT value of the user.
	 * @return double bitcoin
	 */
	public double getBCT()
	{
		return btc;
	}
	
	/**
	 * Returns last chosen payment type
	 * @return String paymenttype
	 */
	public String getPaymentType()
	{
		return paymentType;
	}
	
	//Notifies the listener of a transaction attempt
	public void bitCoinTransaction() {
		listener.bitcoinTransfer();
	}
	
	/**
	 * Charges the user's BTC amount.
	 * @param double charge
	 */
	public void chargeBTC(double charge) {
		btc -= charge;
	}
	
	/**
	 * Creates a random transaction, including a fake userID, pin, and bitcoin amount
	 * Notifies the listener for these variables
	 */
	public void exampleTransaction()
	{
		Random rand = new Random();
		int someUserID = 10000000 + rand.nextInt(999999); //generate an arbitrary user ID.
		int someUserPin = rand.nextInt(999999); //generate an arbitrary user Pin.
		userID = someUserID;
		userPin = someUserPin;
		btc = rand.nextDouble()*0.1; //give the user an arbitrary amount of btc, assuming that they are not rich.
		
		//Choose a random payment type.
		int randPaymentType = rand.nextInt(5);
		if (randPaymentType > btcPaymentMethods.size())
		{
			paymentType = "Invalid type";
		}
		else paymentType = btcPaymentMethods.get(randPaymentType);
		
		bitCoinTransaction();//notify all listeners
	}
	
	
}

/**
 * Base interface for the listener
 * @author David Keizer
 *
 */
interface btcListener extends AbstractHardwareListener{
	public void bitcoinTransfer();
	
}

