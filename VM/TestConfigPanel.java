package groupAssignment2;

import static org.junit.Assert.*;

/**
 * Junit test class for ConfigPanel. 
 * (Not completed yet!)
 * 
 * @author Mahsa Lotfi, Zia Rehman
 */

import org.junit.Test;
import org.lsmr.vending.hardware.VendingMachine;

public class ConfigPanelTest {
	
	private VendingMachine vm;
	public ConfigPanel cp;
	String message = "";
	
	/**
	 * Methid creates a vendingmachine, the ConfigPanel logic and a base setup class
	 */
	private void setup(){
		VendingSetup vendset = new VendingSetup();
		vm = vendset.getVendingMachine();
		cp = new ConfigPanel(vm);
	}
	
	/**
	 * Method to test the pressing button 'a' + Enter will enable the config mode
	 */
	@Test
	public void test_pressConfigMode() {
		
		setup();
		cp.panelEnabled = true;
		cp.pressButton('a');
		cp.pressButton('+');
		assertTrue(cp.rackSelectMode);
		assertFalse(cp.nameSelectMode);
		assertFalse(cp.priceSelectMode);
		assertFalse(cp.saveChangesMode);		
	}
	
	/**
	 * Method to test the entering rack number + Enter works.
	 */
	@Test
	public void test_enterRackNum() {
		
		setup();
		cp.panelEnabled = true;
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		assertTrue(cp.rackSelectMode);
		assertTrue(cp.nameSelectMode);
		assertFalse(cp.priceSelectMode);
		assertFalse(cp.saveChangesMode);		
	}
	
	/**
	 * Method to test if the rack was not entered and just pressing enter doesn't change the modes of config panel.
	 */
	@Test
	public void test_NotEnterRackNum() {
		
		setup();
		cp.panelEnabled = true;
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('+');
		assertTrue(cp.rackSelectMode);
		assertFalse(cp.nameSelectMode);
		assertFalse(cp.priceSelectMode);
		assertFalse(cp.saveChangesMode);		
	}
	
	/**
	 * Method to test entering the price for selected rack will change the stage of config panel.
	 */
	@Test
	public void test_enterName() {
		
		setup();
		cp.panelEnabled = true;
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		cp.pressButton('c');
		cp.pressButton('o');
		cp.pressButton('k');
		cp.pressButton('e');
		cp.pressButton('+');
		assertTrue(cp.rackSelectMode);
		assertTrue(cp.nameSelectMode);
		assertTrue(cp.priceSelectMode);
		assertFalse(cp.saveChangesMode);		
	}	
	
	/**
	 * Method to test if not entering the price will not change the mode of config panel.
	 */
	@Test
	public void test_NotEnterName() {
		
		setup();
		cp.panelEnabled = true;
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		cp.pressButton('+');
		assertTrue(cp.rackSelectMode);
		assertTrue(cp.nameSelectMode);
		assertTrue(cp.priceSelectMode);
		assertFalse(cp.saveChangesMode);		
	}
	
	/**
	 * Method to test if the technician choose not to change the Name of the rack, logic will use the previous one it had.
	 */
	@Test
	public void test_enterPrice() {
		
		setup();
		cp.panelEnabled = true;
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		cp.pressButton('+');
		cp.pressButton('2');
		cp.pressButton('5');
		cp.pressButton('0');
		cp.pressButton('+');
		assertTrue(cp.rackSelectMode);
		assertTrue(cp.nameSelectMode);
		assertTrue(cp.priceSelectMode);
		assertTrue(cp.saveChangesMode);		
	}	
	
	/**
	 * Method to test changing name of the rack.
	 */
	@Test
	public void test_NotEnterPrice() {
		
		setup();
		cp.panelEnabled = true;
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		cp.pressButton('+');
		cp.pressButton('+');
		assertTrue(cp.rackSelectMode);
		assertTrue(cp.nameSelectMode);
		assertTrue(cp.priceSelectMode);
		assertFalse(cp.saveChangesMode);		
	}	
	
	/**
	 * Method to test saving all the changes, and disabling the config panel.
	 */
	@Test
	public void test_savingChanges() {
		setup();
		cp.panelEnabled = true;
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		cp.pressButton('c');
		cp.pressButton('o');
		cp.pressButton('k');
		cp.pressButton('e');
		cp.pressButton('+');
		cp.pressButton('2');
		cp.pressButton('5');
		cp.pressButton('0');
		cp.pressButton('+');
		cp.pressButton('y');
		cp.pressButton('+');
		assertFalse(cp.rackSelectMode);
		assertFalse(cp.nameSelectMode);
		assertFalse(cp.priceSelectMode);
		assertFalse(cp.saveChangesMode);		
		//assertEquals("");
	}
	
	/**
	 * Method to test discarding the changes, and going back to mode1 which is selecting the rack num.
	 */
	@Test
	public void test_DiscardChanges() {
		setup();
		cp.panelEnabled = true;
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		cp.pressButton('c');
		cp.pressButton('o');
		cp.pressButton('k');
		cp.pressButton('e');
		cp.pressButton('+');
		cp.pressButton('2');
		cp.pressButton('5');
		cp.pressButton('0');
		cp.pressButton('+');
		cp.pressButton('n');
		cp.pressButton('+');
		assertTrue(cp.rackSelectMode);
		assertFalse(cp.nameSelectMode);
		assertFalse(cp.priceSelectMode);
		assertFalse(cp.saveChangesMode);
		//assertEquals("");
	}
	
	/**
	 * Method to test if when the config panel is disabled, it will not do anything by pressing the buttons.
	 */
	@Test
	public void test_configDisabled() {
		setup();
		cp.pressButton('a');
		cp.pressButton('+');
		assertFalse(cp.panelEnabled);
		assertFalse(cp.rackSelectMode);
		assertFalse(cp.nameSelectMode);
		assertFalse(cp.priceSelectMode);
		assertFalse(cp.saveChangesMode);
		//assertEquals("");
	}
	
	/**
	 * Method to test pressing disable config button ('^' which is the shift key) in the middle of changing stage.
	 */
	@Test
	public void test_disableConfig() {
		setup();
		cp.panelEnabled = true;
		cp.pressButton('a');
		cp.pressButton('+');
		cp.pressButton('3');
		cp.pressButton('+');
		cp.pressButton('c');
		cp.pressButton('o');
		cp.pressButton('k');
		cp.pressButton('e');
		cp.pressButton('^');
		assertTrue(cp.panelEnabled);
		assertFalse(cp.rackSelectMode);
		assertFalse(cp.nameSelectMode);
		assertFalse(cp.priceSelectMode);
		assertFalse(cp.saveChangesMode);
		//assertEquals("");
	}
}
