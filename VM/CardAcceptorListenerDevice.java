package groupAssignment2;

import org.lsmr.vending.hardware.AbstractHardware;
import org.lsmr.vending.hardware.AbstractHardwareListener;

public class CardAcceptorListenerDevice implements CardAcceptorListener{

	private VendingLogic logic;
	
	/**
	* Constructor creates the listener and assigns a logic to it
	* @param VendingLogicInterface Logic that the listener interacts with
	* 
	*/
	public CardAcceptorListenerDevice(VendingLogic logic)
	{
		this.logic = logic;
		
	}

	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		logic.enableCardAcceptor();

	}

	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		logic.disableCardAcceptor();

	}

	public void validCardTapped(CardAcceptor cardAcceptor, Card card) {
		

	}

	public void validCardWiped(CardAcceptor cardAcceptor, Card card) {
		// TODO Auto-generated method stub

	}

	public void validCardInserted(CardAcceptor cardAcceptor, Card card) {
		// TODO Auto-generated method stub

	}

	public void cardRejected(CardAcceptor cardAcceptor, Card card) {
		// TODO Auto-generated method stub

	}
}
