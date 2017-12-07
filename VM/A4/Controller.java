package Assignment4;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.lsmr.vending.Coin;
import org.lsmr.vending.hardware.CapacityExceededException;
import org.lsmr.vending.hardware.DisabledException;
import org.lsmr.vending.hardware.EmptyException;
import org.lsmr.vending.hardware.PushButton;
import org.lsmr.vending.hardware.VendingMachine;

public class Controller {
	static private MyConfigDialog Config;
	static private MyControlDialog Control;
	static private int x, y;
	public static VendingMachine VM;
	private static VendingLogic logic;
	private static ConfigPanel cp;
	public static Card card = new Card(0, "N/A", 0);
	public int selection = -1;
	static private View theView;

	public Controller(View V) {
		Controller.theView = V;
		Config = new MyConfigDialog();
		Control = new MyControlDialog();
		MyGuiButtonListener guiListener = new MyGuiButtonListener();
		theView.setButtonListener(guiListener);

		MyConfigButtonListener aConfigListener = new MyConfigButtonListener();
		Config.setMyConfigButtonListener(aConfigListener);

		MyControlButtonListener aControlListener = new MyControlButtonListener();
		Control.setMyControlButtonListener(aControlListener);

	}

	public static void main(String[] args) {
		theView = new View("VM Simulation");

		Controller aController = new Controller(theView);

		MyWindowListener aWindowListener = new MyWindowListener();
		theView.addWindowListener(aWindowListener);
		// Creates Dialog on the side
		// Dialog = new MyDialog(theModel);

		// theView.setSize(WIDTH,HEIGHT);
		theView.pack();
		theView.setLocationRelativeTo(null);

		VendingSetup vs = new VendingSetup();
		VM = vs.getVendingMachine();
		logic = new VendingLogic(VM, Control);
		cp = new ConfigPanel(VM);

		Config.DisplayField.setText(cp.displayMessage);
		Control.DisplayField.setText(cp.displayMessage);

		System.out.println(theView.SelectionButtons[0].getName());
		// for (int i = 0; i < 8; i++) {
		// theView.SelectionButtons[i].setText("Shite");
		// }

		// M theModel = new M();

		// C thisController = new C(theView, theModel);

		Config.setDefaultCloseOperation(MyConfigDialog.DO_NOTHING_ON_CLOSE);
		Control.setDefaultCloseOperation(MyControlDialog.DO_NOTHING_ON_CLOSE);

		Config.setSize(450, 150);
		Control.setSize(180, 185);
		x = theView.getX() + theView.getWidth();
		y = theView.getHeight() - Config.getHeight();

		Config.setLocation(x, y);
		Config.setUndecorated(true);
		Config.setVisible(false);
		Control.setLocation(x, y - 420);
		Control.setUndecorated(true);
		Control.setVisible(true);

		theView.setVisible(true);
	}

	class MyConfigButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton aButton = (JButton) e.getSource();
			char button = aButton.getText().charAt(0);
			// System.out.println(button);
			cp.pressButton(button);
			// System.out.println(cp.buttonList[19] instanceof PushButton);
			if (cp.buttonField == "")
				Config.DisplayField.setText(cp.displayMessage);
			else
				Config.DisplayField.setText(cp.buttonField);

			// System.out.print("HELLO");
		}
	}

	class MyControlButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton aButton = (JButton) e.getSource();
			if (isNumeric(aButton.getText())) {
				try {
					VM.getCoinSlot().addCoin(new Coin(Integer.valueOf(aButton.getText())));
				} catch (DisabledException e1) {
					e1.printStackTrace();
				}
			} else {
				String Text = aButton.getText();

				if (null != Text) {
					switch (Text) {
					case "Invalid":
						card = new Card(3, "N/A", 0);
						System.out.println(card.getBankName() + card.getCardType() + card.getCardBalance());
						break;
					case "Valid":
						card = new Card(0, "TD", 500);
						System.out.println(card.getBankName() + card.getCardType() + card.getCardBalance());
						break;
					case "Poor":
						card = new Card(0, "RBC", 0);
						System.out.println(card.getBankName() + card.getCardType() + card.getCardBalance());
						break;
					case "Rich":
						card = new Card(0, "BMO", 100);
						System.out.println(card.getBankName() + card.getCardType() + card.getCardBalance());
						break;
					case "Use":
						if (selection > -1) {
							try {
								logic.payByTappingCard(card, selection);
								System.out.println(card.getBankName() + card.getCardType() + card.getCardBalance());
								System.out.println(VM.getDeliveryChute().size());
							} catch (DisabledException | EmptyException | CapacityExceededException e1) {

								System.out.println("Unavailable, Sorry");
								System.out.println(selection);
								System.out.println(card.getBankName() + card.getCardType() + card.getCardBalance());
								Control.DisplayField.setText("Unavailable, Sorry");
								e1.printStackTrace();
							}
						} else {
							Control.DisplayField.setText("Nope");
						}

					default:
						break;
					}

				}
			}
		}
	}

	class MyGuiButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton aButton = (JButton) e.getSource();
			if (isNumeric(aButton.getName())) {
				selection = Integer.valueOf(aButton.getName());
				System.out.println(selection);
				VM.getSelectionButton(selection).press();
			} else {
				String Text = aButton.getText();

				if (null != Text) {
					switch (Text) {
					case "Lock":
						// System.out.println("Hello");
						if (VM.getLock().isLocked()) {
							VM.getLock().unlock();
							VM.enableSafety();
							Config.setVisible(false);
							theView.revalidate();
							// System.out.println("got here");
						} else {
							VM.getLock().lock();
							VM.disableSafety();
							Config.setVisible(true);
							theView.revalidate();
							// System.out.println("got there");
						}

						break;
					case "Refund":
						logic.returnChange();
						break;
					case "Delivery Chute":
						if (VM.getDeliveryChute().size() == 0)
							theView.getChute().setToolTipText("Empty");
						else {
							VM.getDeliveryChute().removeItems();
							System.out.println(VM.getDeliveryChute().size());
							theView.getChute().setToolTipText("Empty");
						}
						System.out.println(VM.getDeliveryChute().size());
						theView.getChute().setBackground(Color.BLACK);
						// System.out.println("got here");
						break;
					default:
						break;
					}
				}

			}
		}
	}

	public boolean isNumeric(String str) {
		try {
			int x = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
