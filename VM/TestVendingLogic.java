package groupAssignment2;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lsmr.vending.Coin;
import org.lsmr.vending.hardware.DisabledException;
import org.lsmr.vending.hardware.PushButton;
import org.lsmr.vending.hardware.VendingMachine;

public class TestVendingLogic {

	private VendingMachine vm;
	private VendingLogic logic;
	private int [] validCoins;
	
	/**
	 * Methid creates a vendingmachine, its logic, and a base setup class
	 */
	private void setup()
	{
	VendingSetup vendset = new VendingSetup();
	 vm = vendset.getVendingMachine();
	 logic  = new VendingLogic(vm);
	 validCoins = vendset.getCoinKinds();
	}
	
	/**
	 * Method tests the get moethd for getting the coin kinds from the vm
	 */
	@Test
	public void test_getVmCoinKinds() {
		setup();
		int[] coinKinds = logic.getVmCoinKinds();
		boolean fail = false;
		for( int i = 0; i<coinKinds.length; i++)
		{
			if (coinKinds[i] != validCoins[i])
			{
				fail = true;
			}
		}
		
		assertFalse(fail);
		
	}
	
	
	/**
	 * Method tests to see if the credit changes when the an invalid coin is inserted
	 */
	@Test
	public void test_invalidCoinInserted()
	{
		
		setup();
		try {
			
			logic.invalidCoinInserted();
		}catch(Exception e)
		{
		fail();	
		}
		//assertFalse(fail);
		assertTrue(compareCurrentMessage("Current Credit: $0.0")||logic.displayWelcome);
	}
	
	/**
	 * Method tests to see if the credit changes correctly when a valid coin is inserted
	 */
	@Test
	public void test_validCoinInserted()
	{
		setup();
		logic.validCoinInserted(new Coin(200));
		assertTrue(logic.getCurrencyValue() == 200);
	}
	/**
	 * Method tests if the logic checks to see if exact change is possible.
	 * NOTE: This test requires the coinReturn in the vm to be properly instanced.
	 * In version 2.0 of VendingMachine, this is not the case.
	 * As a result this method will test to see that it is still true that this bug exists.
	 */
	@Test
	public void test_isExactChangePossible()
	{
		setup();
		if (vm.getCoinReturn() != null)
		{
			try {
				//Add $6.00
				vm.getCoinSlot().addCoin(new Coin(200));
				vm.getCoinSlot().addCoin(new Coin(200));
				vm.getCoinSlot().addCoin(new Coin(200));
				vm.getSelectionButton(1).press(); //which has cost of 250
				assertFalse(logic.isExactChangePossible());
			}
			catch(Exception e)
			{
				System.out.println(e);
				fail();
			}
		}
		else
			assertTrue(logic.isExactChangePossible() == false);
	}
	
	/**
	 * Tests to see that no exception is thrown when the buttons on the machine are pressed
	 */
	@Test
	public void test_determineButtonActionValid()
	{
		boolean failed = false;
		setup();
		try {
		logic.determineButtonAction(vm.getSelectionButton(0));
		}	
		catch(Exception e)
		{
		failed = true;
		}
		assertFalse(failed);
	}
	
	/**
	 * Tests to see that an exception is correctly thrown when an invalid button is pressed
	 */
	@Test
	public void test_determineButtonActionInValid()
	{
		int selectionButtons = 5;
		boolean failed = false;
		setup();
		try {
		logic.determineButtonAction(new PushButton());
		}
		catch(Exception e)
		{
		failed = true;
		}
		assertTrue(failed);
	}
		
	/**
	 * Method tests to see if disableHardware correctly disables the piece in question
	 * 
	 */
	@Test
	public void test_disableHardware()
	{
		try {
			setup();
			boolean enabled = !vm.getCoinSlot().isDisabled();
			logic.disableHardware(vm.getCoinSlot());
			assertTrue(vm.getCoinSlot().isDisabled() != enabled);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	/**
	 * Method tests to see if the hardware is correctly enabled
	 */
	@Test
	public void test_EnableHardware_arbitraryHardware()
	{
		try {
			setup();
			vm.getCoinSlot().disable();
			boolean disabled = !vm.getCoinSlot().isDisabled();
			logic.enableHardware(vm.getCoinSlot());
			assertTrue(vm.getCoinSlot().isDisabled() != disabled);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	/**
	 * Method tests to see if the hardware is correctly enabled
	 */
	@Test
	public void test_EnableHardware_arbitraryHardware_Pop()
	{
		try {
			setup();
			vm.getPopCanRack(0).disable();
			boolean disabled = !vm.getPopCanRack(0).isDisabled();
			logic.enableHardware(vm.getPopCanRack(0));
			assertTrue(vm.getPopCanRack(0).isDisabled() != disabled);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	/**
	 * Method tests to see if the hardware is correctly enabled
	 */
	@Test
	public void test_EnableHardware_arbitraryHardware_Button()
	{
		try {
			setup();
			vm.getSelectionButton(0).disable();
			boolean disabled = !vm.getSelectionButton(0).isDisabled();
			logic.enableHardware(vm.getSelectionButton(0));
			assertTrue(vm.getSelectionButton(0).isDisabled() != disabled);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	/**
	 * Tests to see that all the display calls do not result in an exception
	 */
	@Test
	public void test_DisplayCallsException()
	{
		setup();
		try {
		
			logic.displayPrice(0);
			logic.displayCredit();
			logic.welcomeMessage();
			logic.vendOutOfOrder();
			logic.invalidCoinInserted();
		}
		catch(Exception e)
		{
			fail();
		}
		
	}
	

	/**
	 * Tests that the display is correct
	 */
	@Test
	public void test_welcomeMessage()
	{
		setup();
		logic.welcomeMessage();
		assertTrue(logic.displayWelcome); //Due to time constaints, this variable is public
	}
	
	/**
	 * Tests that the display is correct
	 */
	@Test
	public void test_vendOutOfOrder()
	{
		setup();
		logic.vendOutOfOrder();
		assertTrue(compareCurrentMessage("Out Of Order"));
	}
	
	/**
	 * Tests that the display is correct
	 */
	@Test
	public void displayCredit()
	{
		setup();
		logic.displayCredit();
		assertTrue(compareCurrentMessage("Current Credit: $0.0"));
	}
	
	/**
	 * Tests that the display is correct
	 */
	@Test
	public void test_displayPrice()
	{
		setup();
		logic.displayPrice(0);
		
		//Note that the this is correct! displayPrice calls displayCredit() before it returns
		assertTrue(compareCurrentMessage("Current Credit: $0.0")||logic.displayWelcome);
	}
	
	/**
	 * Tests that the display is correct
	 */
	@Test
	public void test_dispensingMessage()
	{
		setup();
		logic.dispensingMessage();
		assertTrue(compareCurrentMessage("Despensing. Enjoy!"));
	}
	
	/**
	 * Method checks to see if the timer throws any exceptions
	 *TODO fix implementation
	 */
	@Test
	public void test_welcomeMessagetimer()
	{
		try {
			setup();
			logic.welcomeMessageTimer();
		}catch(Exception e)
		{
			//System.out.println("*** : " + e);
			fail();
		}
	}

	/**
	 * Method teststhe right index is found
	 */
	@Test
	public void test_findHardwareIndex_Pop()
	{
		setup();
		assertTrue(logic.findHardwareIndex(vm.getPopCanRack(0))==0);
	}

	/**
	 * Method teststhe right index is found
	 */
	@Test
	public void test_findHardwareIndex_SelectButton()
	{
		setup();
		assertTrue(logic.findHardwareIndex(vm.getSelectionButton(2))==2);
	}
	
	/**
	 * Method checks that the method returns the error flag, which should cause an exception if used as an array index
	 */
	@Test
	public void test_findHardwareIndex_ConfigButton()
	{
		setup();
		assertTrue(logic.findHardwareIndex(vm.getConfigurationPanel().getButton(0))==-1);
	}
	//DetermineActionButton will be used in the future, so for we test that no exceptions are thrown
	
	/**
	 * Method tests to see that this does not throw an exception
	 */
	@Test
	public void test_determineButtonAction_selectionButton()
	{
		try {
		setup();
		logic.determineButtonAction(vm.getSelectionButton(0));
		}catch(Exception e)
		{
			fail();
		}
		assertTrue(true);
	}
	
	/**
	 * Method tests to see that this does not throw an exception
	 */
	@Test
	public void test_determineButtonAction_config() {
		try {
			setup();
			logic.determineButtonAction(vm.getConfigurationPanel().getButton(0));
		}catch(Exception e)
		{
			fail();
		}
		assertTrue(true);
	}
	
	/**
	 * Method tests to see that this does not throw an exception
	 */
	@Test
	public void test_determineButtonAction_config_enter()
	{
		try {
			setup();
			logic.determineButtonAction(vm.getConfigurationPanel().getEnterButton());
		}catch(Exception e)
		{
			fail();
		}
		assertTrue(true);
	}
	
	/**
	 * Method tests to see that this does not throw an exception
	 */
	@Test
	public void test_determineButtonAction_invalid()
	{
		try {
			setup();
			logic.determineButtonAction(new PushButton());
		}catch(Exception e)
		{
			assertTrue(true);
		}
	}
	
	
	/**
	 * Method compares the current message to a string, returns true if they are equal
	 * @param String m, the message to compare to
	 * @return boolean based on the compareTo of the two strings
	 */
	private boolean compareCurrentMessage(String m)
	{
		return (logic.getCurrentMessage().compareTo(m)== 0);
	}
	
	/**
	 * Shows that enabling Safety results in an exception as a result of the CoinReturn bug
	 */
	@Test
	public void test_enableSafety()
	{
		setup();
		try {
			vm.enableSafety();	
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	/* RYAN, added 5 new methods to test that the card payment was working */
	/**
	 * Method which testing paying with mostly coins, and then the rest with a credit card 
	 * @throws DisabledException
	 * @throws EmptyException
	 * @throws CapacityExceededException
	 */
	@Test 
	public void test_payByCard() throws DisabledException, EmptyException, CapacityExceededException
	{
		setup();
		
		Card card = new Card(0, "RBC", 12.00);
		logic.validCoinInserted(new Coin(200));
		logic.validCoinInserted(new Coin(25));
		//Total credit should be 2.25 
		assertEquals(logic.getCurrencyValue(), 225);
		assertEquals(vm.getPopCanRack(1).size(), 10); //full pop can rack before finishing payment 
		
		logic.checkPayByCard(card, 1);
		//paying by card should pay the rest of the price, 0.25 and deduct from the card, then set credit = 0  
		assertEquals(card.getCardBalance(), 11.75, 0.001);
		assertEquals(logic.getCurrencyValue(), 0);
		assertEquals(vm.getPopCanRack(1).size(), 9); //Dispensed one pop can 
	}
	
	/**
	 * Method tests paying the whole amount by credit or debit card 
	 * @throws CapacityExceededException 
	 * @throws EmptyException 
	 * @throws DisabledException 
	 */
	@Test
	public void test_payByCard2() throws DisabledException, EmptyException, CapacityExceededException
	{
		setup();
		
		Card card = new Card(1, "Scotiabank", 1300.00);
		assertEquals(logic.getCurrencyValue(), 0); //credit should be 0
		assertEquals(vm.getPopCanRack(2).size(), 10); //full rack at the beginning
		
		logic.checkPayByCard(card, 2); //buy pop can 2 
		//card balance should go down by the price of the pop, converted to a double value 
		assertEquals(card.getCardBalance(), (1300.00 - (double) vm.getPopKindCost(2) / 100), 0.001); 
		assertEquals(logic.getCurrencyValue(), 0); //credit should be now 0 
		assertEquals(vm.getPopCanRack(2).size(), 9); //dispensed one pop can
	}
	
	/**
	 * Tests a card which is from a non-accepted bank 
	 * @throws CapacityExceededException 
	 * @throws EmptyException 
	 * @throws DisabledException 
	 */
	@Test
	public void test_payByCard3() throws DisabledException, EmptyException, CapacityExceededException
	{
		setup();
		
		Card card = new Card(0, "Barclays", 20.00); //invalid bank 
		logic.checkPayByCard(card, 4);
		assertTrue(compareCurrentMessage("Card not valid")); //display message 
	}
	
	/**
	 * Test the situation where there are coins in the machine, a card has funds > 0, 
	 * but not enough to cover the rest of cost of the pop.
	 * Then use a new card, and the transaction is completed. 
	 * @throws CapacityExceededException 
	 * @throws EmptyException 
	 * @throws DisabledException 
	 */
	@Test
	public void test_payByCard4() throws DisabledException, EmptyException, CapacityExceededException
	{
		setup();
		
		Card card = new Card(0, "CIBC", 0.25); //not enough to cover pop cost with coins 
		logic.validCoinInserted(new Coin(200));
		assertEquals(vm.getPopCanRack(1).size(), 10); //pop rack is full
		assertEquals(logic.getCurrencyValue(), 200); //credit should be 2.00
		assertEquals(vm.getPopKindCost(1), 250); //pop cost is 2.50 
		
		logic.checkPayByCard(card, 1);
		assertEquals(vm.getPopCanRack(1).size(), 10); //pop rack is still full, did not dispense 
		
		Card card1 = new Card(0, "BMO", 100.00); //create a valid card, with funds 
		logic.checkPayByCard(card1, 1);
		assertEquals(card1.getCardBalance(), 99.50, 0.01); 
		assertEquals(vm.getPopCanRack(1).size(), 9); //dispensed one pop can 
	}
	
	/**
	 * Test which checks the turn off card payment feature
	 * @throws CapacityExceededException 
	 * @throws EmptyException 
	 * @throws DisabledException 
	 */
	@Test
	public void test_turnOffCardPayment() throws DisabledException, EmptyException, CapacityExceededException
	{
		setup();
		logic.cardEnabled = false; //turn off card acceptance 
		Card card = new Card(0, "TD", 39.00);
		logic.checkPayByCard(card, 5);
		//Should say credit cards are not accepted message 
		assertTrue(compareCurrentMessage("Credit or debit cards are not accepted"));
		
	}
	
	/*Done*/
	
		/* Cynthia, tested the card usage */

	private CardAcceptorListenerDevice cardListener;
	public void testCardSetUp() {
		VendingSetup vendset = new VendingSetup();
		vm = vendset.getVendingMachine();
		logic = new VendingLogic(vm);
		cardListener = new CardAcceptorListenerDevice(logic);
		logic.registerCardAcceptor(cardListener);
	}
	
	/**
	 * Tests enabling card acceptor
	 * 
	 * @throws CapacityExceededException
	 * @throws EmptyException
	 * @throws DisabledException
	 */
	@Test
	public void testEnablingCardAcceptor() throws DisabledException, EmptyException, CapacityExceededException {

		testCardSetUp();

		logic.enableCardAcceptor();
		assertEquals(true,logic.cardEnabled);
	}
	
	/**
	 * Tests disabling card acceptor
	 * 
	 * @throws CapacityExceededException
	 * @throws EmptyException
	 * @throws DisabledException
	 */
	@Test
	public void testDisablingCardAcceptor() throws DisabledException, EmptyException, CapacityExceededException {

		testCardSetUp();

		logic.disableCardAcceptor();
		assertEquals(false,logic.cardEnabled);
	}

	/**
	 * Tests paying by tapping a card
	 * 
	 * @throws CapacityExceededException
	 * @throws EmptyException
	 * @throws DisabledException
	 */
	@Test
	public void testTappingCard() throws DisabledException, EmptyException, CapacityExceededException {

		testCardSetUp();

		Card card1 = new Card(0, "BMO", 100.00); // create a valid card, with funds
		Card card2 = new Card(0, "CIBC", 0.25); // not enough to cover pop cost with coins
		Card card3 = new Card(0, "Barclays", 20.00); // invalid bank
		
		logic.payByTappingCard(card1, 2); // pay by tapping card
		assertEquals(1,this.cardListener.getTappedCount());	//tapped count = 1
		assertEquals(0,this.cardListener.getRejectedCount());	//rejected count = 0
		assertEquals(logic.getPurchaseSucceeded(),true);//payment succeeded
		
		logic.payByTappingCard(card2, 2);
		assertEquals(2,this.cardListener.getTappedCount());	//tapped count = 2
		assertEquals(0,this.cardListener.getRejectedCount());	//rejected count = 0
		assertEquals(logic.getPurchaseSucceeded(),false);//payment failed
		
		logic.payByTappingCard(card3, 2);
		assertEquals(3,this.cardListener.getTappedCount());	//tapped count = 3
		assertEquals(0,this.cardListener.getRejectedCount());	//rejected count = 0
		assertEquals(logic.getPurchaseSucceeded(),false);//payment failed
			
	}
	
	/**
	 * Tests paying by wiping a card
	 * 
	 * @throws CapacityExceededException
	 * @throws EmptyException
	 * @throws DisabledException
	 */
	@Test
	public void testWipingCard() throws DisabledException, EmptyException, CapacityExceededException {

		testCardSetUp();

		Card card1 = new Card(0, "BMO", 100.00); // create a valid card, with funds
		Card card2 = new Card(0, "CIBC", 0.25); // not enough to cover pop cost with coins
		Card card3 = new Card(0, "Barclays", 20.00); // invalid bank
		
		logic.payByWipingCard(card1, 2); // pay by wiping card
		assertEquals(1,this.cardListener.getWipedCount());	//wiped count = 1
		assertEquals(0,this.cardListener.getRejectedCount());	//rejected count = 0
		assertEquals(logic.getPurchaseSucceeded(),true);//payment succeeded
		
		logic.payByWipingCard(card2, 2);
		assertEquals(2,this.cardListener.getWipedCount());	//wiped count = 2
		assertEquals(0,this.cardListener.getRejectedCount());	//rejected count = 0
		assertEquals(logic.getPurchaseSucceeded(),false);//payment failed
		
		logic.payByWipingCard(card3, 2);
		assertEquals(3,this.cardListener.getWipedCount());	//wiped count = 3
		assertEquals(0,this.cardListener.getRejectedCount());	//rejected count = 0
		assertEquals(logic.getPurchaseSucceeded(),false);//payment failed
			
	}
	
	/**
	 * Tests paying by inserting a card
	 * 
	 * @throws CapacityExceededException
	 * @throws EmptyException
	 * @throws DisabledException
	 */
	@Test
	public void testInsertingCard() throws DisabledException, EmptyException, CapacityExceededException {

		testCardSetUp();

		Card card1 = new Card(0, "BMO", 100.00); // create a valid card, with funds
		Card card2 = new Card(0, "CIBC", 0.25); // not enough to cover pop cost with coins
		Card card3 = new Card(0, "Barclays", 20.00); // invalid bank
		
		logic.payByInsertingCard(card1, 2); // pay by inserting card
		assertEquals(1,this.cardListener.getInsertedCount());	//inserted count = 1
		assertEquals(1,this.cardListener.getRejectedCount());	//rejected count = 1
		assertEquals(logic.getPurchaseSucceeded(),true);//payment succeeded
		
		logic.payByInsertingCard(card2, 2);
		assertEquals(2,this.cardListener.getInsertedCount());	//inserted count = 2
		assertEquals(2,this.cardListener.getRejectedCount());	//rejected count = 2
		assertEquals(logic.getPurchaseSucceeded(),false);//payment failed
		
		logic.payByInsertingCard(card3, 2);
		assertEquals(3,this.cardListener.getInsertedCount());	//inserted count = 3
		assertEquals(3,this.cardListener.getRejectedCount());	//rejected count = 3
		assertEquals(logic.getPurchaseSucceeded(),false);//payment failed
			
	}

	/* Done */
	
		/**
	 * Testing the refunding of change to the customer over multiple transactions 
	 * @throws DisabledException
	 * @throws CapacityExceededException
	 */
	@Test 
	public void test_refundChange() throws DisabledException, CapacityExceededException
	{
		setup();
		vm.getCoinSlot().addCoin(new Coin(200));
		vm.getCoinSlot().addCoin(new Coin(25));
		assertEquals(vm.getCoinReturn().size(), 0);
		logic.refundChange();
		assertEquals(vm.getCoinReturn().size(), 2); //returned two coins 
		
		vm.getCoinSlot().addCoin(new Coin(5));
		vm.getCoinSlot().addCoin(new Coin(25));
		vm.getCoinSlot().addCoin(new Coin(10));
		logic.refundChange();
		assertEquals(vm.getCoinReturn().size(), 5); //size of the coin return adds up from previous turns 
	}
	
	/**
	 * Test which fills the coin return up and makes sure it does not dispense more, diplay message on display 
	 * @throws CapacityExceededException
	 * @throws DisabledException
	 */
	@Test
	public void test_fullRefundChange() throws CapacityExceededException, DisabledException
	{
		setup();
		for (int i = 0; i < 50; i++)
		{
			vm.getCoinReturn().acceptCoin(new Coin(25));
		}
		
		vm.getCoinSlot().addCoin(new Coin(5));
		logic.refundChange();
		assertTrue(compareCurrentMessage("Coin return is full, cannot refund change"));
	}
	
	/**
	 * Test which tests the case where the refund button is pressed and the are no coins in the machine to refund 
	 * @throws CapacityExceededException
	 * @throws DisabledException
	 */
	@Test
	public void test_noCreditToRefund() throws CapacityExceededException, DisabledException
	{
		setup();
		logic.refundChange();
		assertEquals(logic.getCurrencyValue(), 0);
		assertEquals(logic.creditList.size(), 0);
		assertEquals(vm.getCoinReturn().size(), 0);
	}
	
	
	
	
}
