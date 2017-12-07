package Assignment4;

import  org.junit.Assert.*;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lsmr.vending.Coin;
import org.lsmr.vending.hardware.AbstractHardware;
import org.lsmr.vending.hardware.AbstractHardwareListener;
import org.lsmr.vending.hardware.CapacityExceededException;
import org.lsmr.vending.hardware.CoinReturn;
import org.lsmr.vending.hardware.DisabledException;
import org.lsmr.vending.hardware.PushButton;
public class TestCoinReturnListenerDevice {

	/**
	* Tests to that logic is enabled when asked to be
	*/
	@Test
	public void isEnabled() {
		StubLogic1 logic = new StubLogic1(1);
		logic.hardware.enable();
		assertTrue(logic.enabled);

	}

	
	/**
	* Tests to that logic is disabled when asked to be
	*/
	@Test
	public void isDisabled() {
		StubLogic1 logic = new StubLogic1(1);
		logic.hardware.disable();
		assertFalse(logic.enabled);

	}

	
	/**
	* Tests to that the logic handles valid coins
	* @throws CapacityExceededException, DisabledException
	*/
	@Test
	public void isAccept() throws CapacityExceededException, DisabledException {
		StubLogic1 logic = new StubLogic1(2);
		logic.hardware.acceptCoin(new Coin(5));
	assertEquals(1,logic.dev.deliveredCoinCount);
	assertEquals(5,logic.dev.deliveredCoinValue);



	}
	
	@Test(expected = CapacityExceededException.class)
	public void isExceeded() throws CapacityExceededException, DisabledException {
		StubLogic1 logic = new StubLogic1(1);
		logic.hardware.acceptCoin(new Coin(5));
		logic.hardware.acceptCoin(new Coin(5));

	}

	@Test(expected = DisabledException.class)
	public void isExceptions() throws CapacityExceededException, DisabledException {
		StubLogic1 logic = new StubLogic1(1);
		logic.hardware.disable();
		logic.hardware.acceptCoin(new Coin(5));

	}

}

//Stub for testing the 	Vending logic interface
class StubLogic1 implements VendingLogicInterface {
	public CoinReturnListenerDevice dev;
	public CoinReturn hardware;
	EventStub ev = new EventStub();

	public boolean enabled = true;

	
	/**
	* Creates a logic stub with a coinreceptacle with num capacity
	* @param int num, the capacity of the coin receptacle
	*/
	public StubLogic1(int num) {
		dev = new CoinReturnListenerDevice(this);
		hardware = new CoinReturn(num);
		hardware.register(dev);

	}

	/**
	* getter for event log. 
	* @return EventLotInterface ev
	*/
	@Override
	public EventLogInterface getEventLog() {
		return ev;
	}

	/** 
	* getter for currency value
	* @return always returns 0
	*/
	@Override
	public int getCurrencyValue() {

		return 0;
	}

	/** 
	* The following methods are implemented, but have no direct use for testing.
	* For this reason, they all havae no contents.
	*
	*/
	@Override
	public void welcomeMessageTimer() {

	}

	@Override
	public void welcomeMessage() {

	}

	@Override
	public void vendOutOfOrder() {

	}

	@Override
	public void displayCredit() {

	}

	@Override
	public void displayPrice(int index) {

	}

	@Override
	public void invalidCoinInserted() {

	}

	@Override
	public void validCoinInserted(Coin coin) {

	}

	@Override
	public void dispensingMessage() {

	}

	@Override
	public void returnChange() {

	}

	@Override
	public void determineButtonAction(PushButton button) {

	}

	/**
	* Method overide for returning the harware index. Always returns 0
	* @param AbstractHardware<? extends AbstractHardwareListener> hardware, the hardware to be found
	* @return returns 0. 
	*/
	@Override
	public int findHardwareIndex(AbstractHardware<? extends AbstractHardwareListener> hardware) {

		return 0;
	}

	/**
	 * stub enable/disable, sets enabled flag in stublogic, there is only one
	 * "hardware" stublogic is concerned
	 */
	@Override
	public void disableHardware(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		enabled = false;
	}

	/**
	 * stub enable/disable, sets enabled flag in stublogic, there is only one
	 * "hardware" stublogic is concerned
	 */
	@Override
	public void enableHardware(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		enabled = true;

	}

	@Override
	public void setCurrentMessage(String newMessage) {
		// TODO Auto-generated method stub
		
	}

}