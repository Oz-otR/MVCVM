package Assignment4;

import org.lsmr.vending.hardware.VendingMachine;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		VendingSetup vs = new VendingSetup();
		VendingMachine vmMachine = vs.getVendingMachine();
		MyControlDialog myControlDialog = new MyControlDialog();
		VendingLogic vl = new VendingLogic(vmMachine,myControlDialog);
		
		ConfigPanel cPanel = new ConfigPanel(vmMachine);
		LockLogic lock = new LockLogic(vmMachine, cPanel);
		
		lock.lock();
		
		cPanel.pressButton('p');
	}

}
