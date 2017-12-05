package A4;
import java.util.ArrayList;
import java.util.Random;

import org.lsmr.vending.hardware.AbstractHardware;
import org.lsmr.vending.hardware.AbstractHardwareListener;

public class BitCoinListener implements btcListener {

	private VendingLogic vendinglogic;
	
	
	public BitCoinListener(VendingLogic logic)
	{
		vendinglogic = logic;
		
	}

	public static VendingBTChardware tempCreateHardware() {
		return new VendingBTChardware();
	}

	
	
	

	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		vendinglogic.enableHardware(null);
		
	}


	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		vendinglogic.disableHardware(null);
		
	}


	@Override
	public void bitcoinTransfer() {
		vendinglogic.bitcoinTransaction();		
	}
	
	

}

class VendingBTChardware extends AbstractHardware<btcListener>{
	private ArrayList<String> btcPaymentMethods;
	private int userID;
	private btcListener listener;
	private String paymentType;
	private double btc;
	
	public VendingBTChardware() {
		userID = -1;
		paymentType = null;
		btcPaymentMethods = vmGetBTCPaymentMethods();
	
		
	}
	
	
	private ArrayList<String> vmGetBTCPaymentMethods() {
		ArrayList<String> paymentMethods = new ArrayList<String>();
		paymentMethods.add("NFC");
		paymentMethods.add("Direct Transfer");
		paymentMethods.add("Western Union");
		paymentMethods.add("Other");
		return paymentMethods;
	}
	
	
	
	public ArrayList<String> getBTCpaymentMethods() {
		return btcPaymentMethods;
	}
	
	public int getUserID()
	{
		return userID;
	}
	
	public double getBCT()
	{
		return btc;
	}
	
	public String getPaymentType()
	{
		return paymentType;
	}
	
	public void bitCoinTransaction() {
		listener.bitcoinTransfer();
	}
	
	public void chargeBTC(double charge) {
		btc -= charge;
	}
	
	public void exampleTransaction()
	{
		Random rand = new Random();
		int someUserID = 10000000 + rand.nextInt(999999); //generate an arbitrary user ID.
		userID = someUserID;
		btc = rand.nextDouble()*0.1; //give the user an arbitrary amount of btc, assuming that they are not rich.
		int randPaymentType = rand.nextInt(5);
		if (randPaymentType > btcPaymentMethods.size())
		{
			paymentType = "Invalid type";
		}
		else paymentType = btcPaymentMethods.get(randPaymentType);
		
		bitCoinTransaction();//notify all listeners
	}
	
	
}

interface btcListener extends AbstractHardwareListener{
	public void bitcoinTransfer();
	
	
}

