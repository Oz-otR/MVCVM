package Assignment4;

import org.lsmr.vending.hardware.AbstractHardware;
import org.lsmr.vending.hardware.AbstractHardwareListener;
import org.lsmr.vending.hardware.Display;
import org.lsmr.vending.hardware.DisplayListener;
import org.lsmr.vending.hardware.Lock;
import org.lsmr.vending.hardware.LockListener;
import org.lsmr.vending.hardware.PushButton;
import org.lsmr.vending.hardware.PushButtonListener;
import org.lsmr.vending.hardware.VendingMachine;
import java.util.ArrayList;

/**
 * This is a logic class for configuration panel in a Vending Machine. The logic
 * of this panel is based on 5 stages, each stage will be differentiate with
 * others with the modes being true or false. the first stage which all 4 modes
 * are false is the disabled stage where the config panel will not work. By
 * pressing 'a'+Enter, the config panel will go to the second stage which in
 * that stage the config panel is enabled and the technician can choose the rack
 * number. The 3rd stage is choosing the price for the rack selected. 4th stage
 * is changing the name of the rack selected. and the 5th stage is saving the
 * changes and disable the config panel, or discarding the changes and go back
 * to 2nd stage where the technician can choose the rack number. entering shift
 * key will disable the config panel even in the middle of stages. the modes
 * will be changed if the Enter button is pressed, otherwise it stayes in the
 * same mode and saves all the button pressed to the console of the config
 * panel.
 * 
 * @author Zia Rehman, Mahsa Lotfi
 *
 */

public class ConfigPanel implements PushButtonListener, DisplayListener {

	public VendingMachine vm;
	public String[] Codes;
	public static PushButton[] buttonList;
	public Display display;

	public boolean panelEnabled;

	public boolean rackSelectMode;
	public boolean nameSelectMode;
	public boolean priceSelectMode;
	public boolean saveChangesMode;

	public String buttonField;
	public int popCanRackIndex;
	public int newPrice;

	public String newRackName;

	public String displayMessage;
	public String displayLog;
	public MyConfigDialog view;

	/**
	 * Constructor to initialize vending machine and the configuration panel
	 * buttons. And registering listeners for those buttons. It will also initialize
	 * all the modes of config panel to be false so that the config panel will be
	 * disabled at first.
	 * 
	 * @param vmIn
	 */

	public ConfigPanel(VendingMachine vmIn) {
		
		buttonList = new PushButton[38];

		this.vm = vmIn;

		resetButtonField();

		rackSelectMode = false;
		nameSelectMode = false;
		priceSelectMode = false;
		buttonField = "";
		for (int i = 0; i < 37; i++)
			buttonList[i] = vm.getConfigurationPanel().getButton(i);

		buttonList[37] = vm.getConfigurationPanel().getEnterButton();

		display = vm.getConfigurationPanel().getDisplay();

		for (int i = 0; i < buttonList.length; i++)
			buttonList[i].register(this);

		vm.getConfigurationPanel().getDisplay().register(this);
	}

	public void enablePanel() {

		for (PushButton button : buttonList)

			button.enable();

		display.enable();

		panelEnabled = true;

	}

	public void disablePanel() {

		for (PushButton button : buttonList)

			button.disable();

		display.disable();

		panelEnabled = false;

	}
	
	/**
	 * Method to change the button pressed ASCII char index to its related number
	 * index, find and press it.
	 * 
	 * @param button
	 */

	public void pressButton(char button) {

		int buttonIndex = (int) button;

		if (buttonIndex >= 97 && buttonIndex <= 122)
			buttonIndex -= 97;

		else if (buttonIndex >= 48 && buttonIndex <= 57)
			buttonIndex -= 22;

		else if (buttonIndex == 94)
			buttonIndex = 36;

		else if (buttonIndex == 43)
			buttonIndex = 37;
		
		System.out.println(buttonIndex);

		// System.out.println("Pressing button at index: " + buttonIndex );

		buttonList[buttonIndex].press();	
	}

	/**
	 * Method to change the button pressed index to its related ASCII character.
	 * 
	 * @param button,
	 *            PushButton
	 * @return buttonPushed, char
	 */

	public char buttonPressed(PushButton button) {

		int buttonIndex = 0;

		while (!button.equals(buttonList[buttonIndex]))
			buttonIndex++;

		if (buttonIndex >= 0 && buttonIndex <= 25)
			buttonIndex += 97;

		else if (buttonIndex >= 26 && buttonIndex <= 35)
			buttonIndex += 22;

		else if (buttonIndex == 36)
			buttonIndex = 94;

		else if (buttonIndex == 37)
			buttonIndex = 43;

		char buttonPushed = (char) buttonIndex;

		return buttonPushed;

	}

	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		// hardware enable, no functionality here
	}

	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		// hardware disable, no functionality here
	}

	/**
	 * Method to react to every button press. All the changes should be made with
	 * pressing related button plus Enter. The only button that does not need the
	 * Enter button is the shift key which will disable the configuration panel.
	 * pressing any other button without enter will just save that character and
	 * append it to the string showing in the console of config panel, by pressing
	 * Enter all the string that has been saved in console will be saved as its
	 * related area.
	 * 
	 * @param button
	 */

	public void pressed(PushButton button) {

		//if (panelEnabled) {

			if (buttonPressed(button) == '+') {

				if (!rackSelectMode && !nameSelectMode && !priceSelectMode && !saveChangesMode) {

					if (buttonField != null) {

						if (buttonField.charAt(0) == 'a')

							enableConfigMode();

						else {

							displayMessage("Invalid Command!");

							resetButtonField();
						}

					}

					else {

						displayMessage("Please enter a command!");
					}

				}

				else if (rackSelectMode && !nameSelectMode && !priceSelectMode && !saveChangesMode) {

					if (buttonField != null) {

						try {

							popCanRackIndex = Integer.parseInt(buttonField);

							displayMessage("Rack number " + popCanRackIndex + " selected for configuration");

							nameSelectMode = true;

							displayMessage("\nCurrent Name: " + vm.getPopKindName(popCanRackIndex)
									+ ", Enter new Name, or press ENTER to keep the same name: ");

							resetButtonField();

						} catch (Exception e) {

							displayMessage("Invalid Command!");

							resetButtonField();

						}

					}

					else {

						displayMessage("Please enter a command!");
					}

				}

				else if (rackSelectMode && nameSelectMode && !priceSelectMode && !saveChangesMode) {

					if (buttonField != null) {

						try {

							if (buttonField != "")
								newRackName = buttonField;

							else
								newRackName = vm.getPopKindName(popCanRackIndex);

							String message = "Name selected: " + newRackName;

							message += "\n\nCurrent Price: " + vm.getPopKindCost(popCanRackIndex)
									+ ", Enter new price: ";

							displayMessage(message);

							priceSelectMode = true;

							resetButtonField();

						} catch (Exception e) {

							resetButtonField();

							displayMessage("Invalid Command!");
						}
					} else {

						displayMessage("Please enter a command!");

					}
				}

				else if (rackSelectMode && nameSelectMode && priceSelectMode && !saveChangesMode) {

					if (buttonField != null) {

						try {

							newPrice = Integer.parseInt(buttonField);

							if (newPrice % 5 == 0) {

								String message = "Price selected: " + newPrice;

								message += "\n\nRack Selected: " + popCanRackIndex + "\nName Selected: " + newRackName
										+ "\nPrice Selected: " + newPrice + "\n";

								message += "\nDo you want to make these changes? Press y for YES, n for NO";

								displayMessage(message);

								saveChangesMode = true;

								resetButtonField();
							} else {

								String message = "The lowest denomination accepted is 5 cents, please enter a different price,";

								message += "\n\nCurrent Price: " + vm.getPopKindCost(popCanRackIndex)
										+ ", Enter new price: ";

								displayMessage(message);

							}

						} catch (Exception e) {

							resetButtonField();

							displayMessage("Invalid Command!");
						}
					} else {

						displayMessage("Please enter a command!");

					}
				}

				else if (rackSelectMode && nameSelectMode && priceSelectMode && !saveChangesMode) {

					if (buttonField != null) {

						try {

							newPrice = Integer.parseInt(buttonField);

							if (newPrice % 5 == 0) {

								String message = "Price selected: " + newPrice;

								message += "\n\nRack Selected: " + popCanRackIndex + "\nName Selected: " + newRackName
										+ "\nPrice Selected: " + newPrice + "\n";

								message += "\nDo you want to make these changes? Press y for YES, n for NO";

								displayMessage(message);

								saveChangesMode = true;

								resetButtonField();
							} else {

								String message = "The lowest denomination accepted is 5 cents, please enter a different price,";

								message += "\n\nCurrent Price: " + vm.getPopKindCost(popCanRackIndex)
										+ ", Enter new price: ";

								displayMessage(message);

							}

						} catch (Exception e) {

							resetButtonField();

							displayMessage("Invalid Command!");
						}
					} else {

						displayMessage("Please enter a command!");

					}
				}

				else if (buttonField != null) {

					if (buttonField.charAt(0) == 'y')

						saveChanges();

					else {

						displayMessage("Discarding changes");

						displayMessage("Select pop can rack number: ");

						nameSelectMode = false;

						priceSelectMode = false;

					}
				}

				else {

					displayMessage("Please enter a command!");
				}
			}

			else if (buttonPressed(button) == '^') {

				disableConfigMode();
			}

			else {
				buttonField += buttonPressed(button);

				System.out.println("buttonField: " + buttonField);

			}
		//} else

			//System.out.println("Safety must be on to access panel");
	}

	// This is for debugging
	public void displayModes() {

		System.out.println("MODE1: " + rackSelectMode);

		System.out.println("MODE2: " + nameSelectMode);

		System.out.println("MODE3: " + priceSelectMode);

		System.out.println("MODE4: " + saveChangesMode);
	}

	/**
	 * Resets buttonField
	 */
	public void resetButtonField() {

		this.buttonField = "";
	}

	public void resetConfig() {

		rackSelectMode = false;

		nameSelectMode = false;

		priceSelectMode = false;

		saveChangesMode = false;

		resetButtonField();
	}

	/**
	 * Method to save all the changes has been made by technician to the vending
	 * machine, and disabling the config panel.
	 */

	public void saveChanges() {

		displayMessage("Save changes? " + buttonField);

		ArrayList<Integer> popCanCosts = new ArrayList<Integer>();

		ArrayList<String> popCanNames = new ArrayList<String>();

		for (int i = 0; i < vm.getNumberOfPopCanRacks(); i++)
			popCanCosts.add(vm.getPopKindCost(i));

		for (int i = 0; i < vm.getNumberOfPopCanRacks(); i++)
			popCanNames.add(vm.getPopKindName(i));

		popCanCosts.set(popCanRackIndex, newPrice);

		popCanNames.set(popCanRackIndex, newRackName);

		vm.configure(popCanNames, popCanCosts);

		displayMessage("\nChanges Saved!");

		resetConfig();

	}

	/**
	 * Method to display messages that every button press will initiate.
	 * 
	 * @param inMessage
	 */
	public String displayMessage(String inMessage) {

		displayMessage += "\n" + inMessage;

		display.display(displayMessage);
		
		return displayMessage;
	}

	/**
	 * Method to enable the config mode, by setting mode1 to true
	 */
	public void enableConfigMode() {

		System.out.println("Config Mode Enabled");

		rackSelectMode = true;
		nameSelectMode = false;

		displayMessage = "\nSelect Pop Rack Number: ";

		display.display(displayMessage);

		resetButtonField();
		// displayModes();
	}

	/**
	 * Method to disable config mode by setting all the modes to false.
	 */
	public void disableConfigMode() {

		rackSelectMode = false;

		nameSelectMode = false;

		priceSelectMode = false;

		saveChangesMode = false;

		display.display("Configuration Mode is disabled, to enable press 'a' then Enter");

	}

	/**
	 * Method to disable config mode by setting all the modes to false.
	 * 
	 * @param button,
	 *            PushButton
	 * @return buttonPushed, char
	 */
	public Display getDisplay() {

		return vm.getConfigurationPanel().getDisplay();
	}

	/**
	 * Display new message when it is changed, append to displayLog
	 */
	public void messageChange(Display display, String oldMessage, String newMessage) {

		System.out.println(newMessage);

		displayLog += newMessage;

	}
}