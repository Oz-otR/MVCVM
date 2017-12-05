package Assignment4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.lsmr.vending.Coin;
import org.lsmr.vending.hardware.CapacityExceededException;
import org.lsmr.vending.hardware.DisabledException;
import org.lsmr.vending.hardware.EmptyException;
import org.lsmr.vending.hardware.VendingMachine;

public class Controller {
	static private MyConfigDialog Config;
	static private MyControlDialog Control;
	static private int x, y;
	public static VendingMachine VM;
	private VendingLogic logic;
	private static ConfigPanel cp;
	public static Card card = new Card(0, "N/A", 0);
	public int selection = -1;

	public static void main(String[] args) {
		View theView = new View("VM Simulation");
		MyWindowListener aWindowListener = new MyWindowListener();
		theView.addWindowListener(aWindowListener);

		// theView.setSize(WIDTH,HEIGHT);
		theView.pack();
		theView.setLocationRelativeTo(null);

		// Creates Dialog on the side
		// Dialog = new MyDialog(theModel);
		Config = new MyConfigDialog();
		Control = new MyControlDialog();

		VendingSetup vs = new VendingSetup();
		VM = vs.getVendingMachine();
		VendingLogic logic = new VendingLogic(VM, Control);
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
		Config.setVisible(true);
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
			cp.pressButton(button);
			Config.DisplayField.setText(cp.displayMessage(""));
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
						break;
					case "Valid":
						card = new Card(0, "TD", 500);
						break;
					case "Poor":
						card = new Card(0, "RBC", 0);
						break;
					case "Rich":
						card = new Card(0, "BMO", 10000);
					case "Use":
						if (selection > -1) {
							try {
								logic.payByTappingCard(card, selection);
							} catch (DisabledException | EmptyException | CapacityExceededException e1) {
								System.out.println("Unavailable, Sorry");
								Control.DisplayField.setText("Unavailable, Sorry");
								e1.printStackTrace();
							}
						} else {
							Control.DisplayField.setText("Unavailable, Sorry");
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
				VM.getSelectionButton(selection).press();
			} else {
				String Text = aButton.getText();

				if (null != Text) {
					switch (Text) {
					case "Lock":
						if (VM.getLock().isLocked()) {
							VM.getLock().unlock();
						} else {
							VM.getLock().lock();
						}
						break;
					case "Refund":
						logic.returnChange();
						break;
					case "DeliveryChute":
						VM.getDeliveryChute().removeItems();
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
