package A4;

import org.lsmr.vending.hardware.AbstractHardware;
import org.lsmr.vending.hardware.AbstractHardwareListener;

public class CardAcceptorListenerDevice implements CardAcceptorListener {

	private VendingLogic logic;
	private int tappedCount = 0;
	private int wipedCount = 0;
	private int insertedCount = 0;
	private int rejectedCount = 0;

	/**
	 * Constructor creates the listener and assigns a logic to it
	 * 
	 * @param VendingLogicInterface
	 *            Logic that the listener interacts with
	 * 
	 */
	public CardAcceptorListenerDevice(VendingLogic logic) {
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

	@Override
	public void validCardTapped(CardAcceptor cardAcceptor, Card card) {
		logic.getVm().getDisplay().display("Card has been tapped.");
		tappedCount++;
	}

	@Override
	public void validCardWiped(CardAcceptor cardAcceptor, Card card) {
		logic.getVm().getDisplay().display("Card has been wiped.");
		wipedCount++;
	}

	@Override
	public void validCardInserted(CardAcceptor cardAcceptor, Card card) {
		logic.getVm().getDisplay().display("Card has been inserted.");
		insertedCount++;
	}

	@Override
	public void cardRejected(CardAcceptor cardAcceptor, Card card) {
		logic.getVm().getDisplay().display("Card has been returned.");
		rejectedCount++;
	}

	public int getTappedCount() {
		return this.tappedCount;
	}

	public int getWipedCount() {
		return this.wipedCount;
	}

	public int getInsertedCount() {
		return this.insertedCount;
	}

	public int getRejectedCount() {
		return this.rejectedCount;
	}
}
