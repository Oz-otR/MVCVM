package A4;

import org.lsmr.vending.hardware.AbstractHardware;
import org.lsmr.vending.hardware.DisabledException;

/**
 * A simple device to accept cards. Verify if the card is valid, Check balance
 * and return the card.
 */
public class CardAcceptor extends AbstractHardware<CardAcceptorListener> {
	
	/**
	 * Instructs the device to take the card as input.
	 * Tap card.
	 * @param card
	 *            The card to be taken as input.
	 * @throws DisabledException
	 *             If the device is disabled.
	 */
	public void tapCard(Card card) throws DisabledException {
		if(isDisabled())
		    throw new DisabledException();
		if(isValid(card)) {
			notifyValidCardTapped(card);
		}
		else {
			this.returnCard(card);
		}
	}
	/**
	 * Instructs the device to take the card as input.
	 * Wipe card.
	 * @param card
	 *            The card to be taken as input.
	 * @throws DisabledException
	 *             If the device is disabled.
	 */
	public void wipeCard(Card card) throws DisabledException {
		if(isDisabled())
		    throw new DisabledException();
		if(isValid(card)) {
			notifyValidCardWiped(card);
		}
		else {
			this.returnCard(card);
		}
	}
	/**
	 * Instructs the device to take the card as input.
	 * Insert card.
	 * @param card
	 *            The card to be taken as input.
	 * @throws DisabledException
	 *             If the device is disabled.
	 */
	public void insertCard(Card card) throws DisabledException {
		if(isDisabled())
		    throw new DisabledException();
		if(isValid(card)) {
			notifyValidCardInserted(card);
		}
		else {
			this.returnCard(card);
		}
	}

	private boolean isValid(Card card) {
		if (card.getCardType() != "Invalid") {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Return the card and notice the device.
	 * 
	 * @param card
	 *            The card to be taken as input.
	 * @throws DisabledException
	 *             If the device is disabled.
	 */
	public void returnCard(Card card) throws DisabledException {
		if(isDisabled())
		    throw new DisabledException();
		this.notifyCardRejected(card);
	}

	private void notifyValidCardTapped(Card card) {
		for (CardAcceptorListener listener : listeners)
			listener.validCardTapped(this, card);
	}

	private void notifyValidCardWiped(Card card) {
		for (CardAcceptorListener listener : listeners)
			listener.validCardWiped(this, card);
	}
	
	private void notifyValidCardInserted(Card card) {
		for (CardAcceptorListener listener : listeners)
			listener.validCardInserted(this, card);
	}
	
	private void notifyCardRejected(Card card) {
		for (CardAcceptorListener listener : listeners)
			listener.cardRejected(this, card);
	}

}