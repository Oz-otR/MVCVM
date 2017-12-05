package A4;

import org.lsmr.vending.hardware.VendingMachine;

public class Main {
	
	public static VendingMachine vm;

	public static void main(String[] args) {
		
		VendingSetup vs = new VendingSetup();
		vm = vs.getVendingMachine();
		ConfigPanel cp = new ConfigPanel(vm);
		
		for(int i = 0; i < vm.getNumberOfCoinRacks(); i++) { //before changes
			System.out.print("Index " + i);
			System.out.print("\t\t\tName: " + vm.getPopKindName(i));
			System.out.println("\t\t\tCost: " + vm.getPopKindCost(i));
		}
		
		cp.pressButton('a');
		cp.pressButton('+');
		
		cp.pressButton('1'); //select rack num
		cp.pressButton('+');
		
		cp.displayModes();
		
		cp.pressButton('+');
		
//		cp.pressButton('f'); //select new name
//		cp.pressButton('a');
//		cp.pressButton('n');
//		cp.pressButton('t');
//		cp.pressButton('a');
//		cp.pressButton('+');
		
		
		cp.pressButton('4'); //select new price
		cp.pressButton('5');
		cp.pressButton('0');
		cp.pressButton('+');
		
		cp.pressButton('y'); //final confirmation
		cp.pressButton('+');
		
		for(int i = 0; i < vm.getNumberOfCoinRacks(); i++) { //after changes
			System.out.print("Index " + i);
			System.out.print("\t\t\tName: " + vm.getPopKindName(i));
			System.out.println("\t\t\tCost: " + vm.getPopKindCost(i));
		}
		
//		System.out.println(cp.displayLog); //just prints out the display listener log
		
	}

}