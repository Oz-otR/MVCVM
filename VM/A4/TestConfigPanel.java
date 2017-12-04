package A4;


import static org.junit.Assert.*;

/**
 * Junit test class for ConfigPanel. 
 * (Not completed yet!)
 * 
 * @author Mahsa Lotfi, Zia Rehman
 */

import org.junit.Test;
import org.lsmr.vending.hardware.AbstractHardware;
import org.lsmr.vending.hardware.AbstractHardwareListener;
import org.lsmr.vending.hardware.Display;
import org.lsmr.vending.hardware.DisplayListener;
import org.lsmr.vending.hardware.PushButton;
import org.lsmr.vending.hardware.VendingMachine;

public class TestConfigPanel {
	
	private VendingMachine vm;
	public ConfigPanel cp;
	String message = "";
	private DisplayStub dstub;
	
	/**
	 * Methid creates a vendingmachine, the ConfigPanel logic and a base setup class
	 */
	private void setup(){
		VendingSetup vendset = new VendingSetup();
		vm = vendset.getVendingMachine();
		dstub = new DisplayStub(vm);
		cp = new ConfigPanel(vm);
		cp.display.register(dstub);
	}
	
	/**
	 * Method to test the pressing button 'a' + Enter will enable the config mode
	 */
	@Test
	public void test_pressConfigMode() {
		
		setup();
		cp.pressButton('a');
		cp.pressButton('+');
		assertTrue(cp.mode1);
		assertFalse(cp.mode2);
		assertFalse(cp.mode3);
		assertFalse(cp.mode4);		
	}
	
	/**
	 * Method to test the entering rack number + Enter works.
	 */
	@Test
	public void test_enterRackNum() {
		
		setup();
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		assertTrue(cp.mode1);
		assertTrue(cp.mode2);
		assertFalse(cp.mode3);
		assertFalse(cp.mode4);		
	}
	
	/**
	 * Method to test if the rack was not entered and just pressing enter doesn't change the modes of config panel.
	 * and displays message
	 */
	@Test
	public void test_NotEnterRackNum() {
		
		setup();
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('+');
		assertTrue(cp.mode1);
		assertFalse(cp.mode2);
		assertFalse(cp.mode3);
		assertFalse(cp.mode4);		
		assertTrue(dstub.newer.equals("Please enter a command!"));

	}
	
	/**
	 * Method to test entering the price for selected rack will change the stage of config panel.
	 */
	@Test
	public void test_enterPrice() {
		
		setup();
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		cp.pressButton('2');
		cp.pressButton('5');
		cp.pressButton('0');
		cp.pressButton('+');
		assertTrue(cp.mode1);
		assertTrue(cp.mode2);
		assertTrue(cp.mode3);
		assertFalse(cp.mode4);		
	}	
	
	/**
	 * Method to test if not entering the price will not change the mode of config panel.
	 */
	@Test
	public void test_NotEnterPrice() {
		
		setup();
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		cp.pressButton('+');
		assertTrue(cp.mode1);
		assertTrue(cp.mode2);
		assertFalse(cp.mode3);
		assertFalse(cp.mode4);	
		assertTrue(dstub.newer.equals("Please enter a command!"));

	}
	
	/**
	 * Method to test if the technician choose not to change the Name of the rack, logic will use the previous one it had.
	 */
	@Test
	public void test_NotChangeName() {
		
		setup();
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		cp.pressButton('2');
		cp.pressButton('5');
		cp.pressButton('0');
		cp.pressButton('+');
		cp.pressButton('+');
		assertTrue(cp.mode1);
		assertTrue(cp.mode2);
		assertTrue(cp.mode3);
		assertTrue(cp.mode4);		
	}	
	
	/**
	 * Method to test changing name of the rack.
	 */
	@Test
	public void test_ChangeName() {
		
		setup();
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		cp.pressButton('2');
		cp.pressButton('5');
		cp.pressButton('0');
		cp.pressButton('+');
		cp.pressButton('c');
		cp.pressButton('o');
		cp.pressButton('k');
		cp.pressButton('e');
		cp.pressButton('+');
		assertTrue(cp.mode1);
		assertTrue(cp.mode2);
		assertTrue(cp.mode3);
		assertTrue(cp.mode4);		
	}	
	
	/**
	 * Method to test saving all the changes, and disabling the config panel.
	 */
	@Test
	public void test_savingChanges() {
		setup();
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		cp.pressButton('2');
		cp.pressButton('5');
		cp.pressButton('0');
		cp.pressButton('+');
		cp.pressButton('c');
		cp.pressButton('o');
		cp.pressButton('k');
		cp.pressButton('e');
		cp.pressButton('+');
		cp.pressButton('y');
		cp.pressButton('+');
		assertFalse(cp.mode1);
		assertFalse(cp.mode2);
		assertFalse(cp.mode3);
		assertFalse(cp.mode4);			
		//assertEquals("");
	}
	
	/**
	 * Method to test discarding the changes, and going back to mode1 which is selecting the rack num.
	 */
	@Test
	public void test_DiscardChanges() {
		setup();
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		cp.pressButton('2');
		cp.pressButton('5');
		cp.pressButton('0');
		cp.pressButton('+');
		cp.pressButton('c');
		cp.pressButton('o');
		cp.pressButton('k');
		cp.pressButton('e');
		cp.pressButton('+');
		cp.pressButton('n');
		cp.pressButton('+');
		assertTrue(cp.mode1);
		assertFalse(cp.mode2);
		assertFalse(cp.mode3);
		assertFalse(cp.mode4);
		//assertEquals("");
	}
	
	/**
	 * Method to test pressing disable config button ('^' which is the shift key) in the middle of changing stage.
	 */
	@Test
	public void test_disableConfig() {
		setup();
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('^');
		assertFalse(cp.mode1);
		assertFalse(cp.mode2);
		assertFalse(cp.mode3);
		assertFalse(cp.mode4);
		//assertEquals("");
	}
	
	//fail entering number
		@Test
		public void isInvalidPress(){
			
			setup();
			cp.pressButton('a');
			cp.pressButton('+');
			
			cp.pressButton('p');
			cp.pressButton('+');
			assertTrue(dstub.newer.equals("Invalid Command!"));
		}
		
		
		//fail entering congfig mode
		@Test
		public void isInvalidPressforConfigMode(){
			
			setup();
			cp.pressButton('b');
			cp.pressButton('+');
			assertTrue(dstub.newer.equals("Invalid Command!"));
		}
		
		
		/**
		 * At the Config Panel, not even enabled, just press enter should display
		 * "Please enter a command" since there is no command entered
		 */
		@Test
		public void justPressingEnter(){	
			setup();
			cp.pressButton('+');
			assertTrue(dstub.newer.equals("Please enter a command!"));
		}
		
		
		/**
		*
		*Expecting an integer, so it is invalid, checking for message
		 */
		@Test
		public void isInvalidPressMessage(){	
			setup();
			cp.pressButton('a');
			cp.pressButton('+');
			cp.pressButton('3');
			cp.pressButton('+');
			cp.pressButton('b');
			cp.pressButton('+');

			assertTrue(dstub.newer.equals("Invalid Command!"));
		}
		
		@Test
		public void testNameMessage() {
			
			setup();
			cp.pressButton('a');
			cp.pressButton('+');
			cp.pressButton('3');
			cp.pressButton('+');
			cp.pressButton('2');
			cp.pressButton('5');
			cp.pressButton('0');
			cp.pressButton('+');
			cp.pressButton('c');
			cp.pressButton('o');
			cp.pressButton('k');
			cp.pressButton('e');
			cp.pressButton('+');
			cp.pressButton('+');
			
			assertTrue(dstub.newer.equals("Please enter a command!"));

		}	
		
		@Test
		public void checkDisplays() {
			setup();
			assertTrue(cp.getDisplay().equals(vm.getConfigurationPanel().getDisplay()));
		}
		
}
/**
 * Created a stub class to check messages
 */
class DisplayStub implements DisplayListener {
	String newer = "";

	public DisplayStub(VendingMachine vm) {
	}

	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		
	}

	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		
	}

	@Override
	public void messageChange(Display display, String oldMessage, String newMessage) {
		newer = newMessage;
	}
	
}
