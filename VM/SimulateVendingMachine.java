package groupAssignment2;

import org.lsmr.vending.hardware.VendingMachine;

public class SimulateVendingMachine {
	private static VendingMachine vm;
	private static VendingLogic logic;
	
	public static void main(String[] args) {
		VendingSetup vendset = new VendingSetup();
		vm = vendset.getVendingMachine();
		logic  = new VendingLogic(vm);	
	}
}
