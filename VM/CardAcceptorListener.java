package groupAssignment2;

import org.lsmr.vending.hardware.AbstractHardwareListener;

public interface CardAcceptorListener extends AbstractHardwareListener {
	/**
	 * An event announcing that the indicated card has been tapped.
	 * 
	 * @param cardAcceptor
	 *            The device on which the event occurred.
	 * @param card
	 *            The card returned.
	 */
	public void validCardTapped(CardAcceptor cardAcceptor, Card card);

	/**
	 * An event announcing that the indicated card has been wiped.
	 * 
	 * @param cardAcceptor
	 *            The device on which the event occurred.
	 * @param card
	 *            The card returned.
	 */
	public void validCardWiped(CardAcceptor cardAcceptor, Card card);

	/**
	 * An event announcing that the indicated card has been inserted.
	 * 
	 * @param cardAcceptor
	 *            The device on which the event occurred.
	 * @param card
	 *            The card returned.
	 */
	public void validCardInserted(CardAcceptor cardAcceptor, Card card);

	/**
	 * An event announcing that the indicated card has been returned.
	 * 
	 * @param cardAcceptor
	 *            The device on which the event occurred.
	 * @param card
	 *            The card returned.
	 */
	public void cardRejected(CardAcceptor cardAcceptor, Card card);

}
