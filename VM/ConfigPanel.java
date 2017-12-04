

import java.util.ArrayList;

import org.lsmr.vending.hardware.AbstractHardware;
import org.lsmr.vending.hardware.AbstractHardwareListener;
import org.lsmr.vending.hardware.Display;
import org.lsmr.vending.hardware.DisplayListener;
import org.lsmr.vending.hardware.PushButton;
import org.lsmr.vending.hardware.PushButtonListener;
import org.lsmr.vending.hardware.VendingMachine;

/**
 * This is a logic class for configuration panel in a Vending Machine. 
 * The logic of this panel is based on 5 stages, each stage will be differentiate with others with the modes being true 
 * or false. the first stage which all 4 modes are false is the disabled stage where the config panel will not work.
 * By pressing 'a'+Enter, the config panel will go to the second stage which in that stage the config panel is enabled 
 * and the technician can choose the rack number. The 3rd stage is choosing the price for the rack selected. 4th stage 
 * is changing the name of the rack selected. and the 5th stage is saving the changes and disable the config panel, or 
 * discarding the changes and go back to 2nd stage where the technician can choose the rack number.
 * entering shift key will disable the config panel even in the middle of stages. the modes will be changed if the 
 * Enter button is pressed, otherwise it stayes in the same mode and saves all the button pressed to the console 
 * of the config panel. 
 * @author Zia Rehman, Mahsa Lotfi
 *
 */
public class ConfigPanel implements PushButtonListener, DisplayListener{
	
	public VendingMachine vm;	
	public String[] Codes;
	public PushButton[] buttonList;
	public Display display;
	
	//TODO Is there better names for these flags other than "mode"?
	public boolean mode1;// true when config is enabled?
	public boolean mode2;// true when chosing a price?
	public boolean mode3;// true when changing name?
	public boolean mode4;// true when??
	
	public String buttonField;
	public int popCanRackIndex;
	public int newPrice;
	public String displayMessage;
	public String displayLog;
	public String rackName;
	
	/**
	 * Constructor to initialize vending machine and the configuration panel buttons. And registering listeners for 
	 * those buttons. It will also initialize all the modes of config panel to be false so that the config panel will 
	 * be disabled at first.
	 * @param vmIn
	 */
	public ConfigPanel(VendingMachine vmIn){
		
		buttonList = new PushButton[38];		
		this.vm = vmIn;
		
		mode1=false;
		mode2=false;
		mode3=false;
		mode4=false;
		
		buttonField = "";
		
		for(int i = 0; i < 37; i++){
			buttonList[i] = vm.getConfigurationPanel().getButton(i);
		}
		
		buttonList[37] = vm.getConfigurationPanel().getEnterButton();
		display = vm.getConfigurationPanel().getDisplay();
		
		for(int i = 0; i < buttonList.length; i++)
			buttonList[i].register(this);
		
		vm.getConfigurationPanel().getDisplay().register(this);
	}
	
	/**
	 * Method to change the button pressed ASCII char index to its related number index.
	 * @param button
	 */
	public void pressButton(char button){
		
		int buttonIndex = (int) button;

		
		if(buttonIndex >= 97 && buttonIndex <= 122)		
			buttonIndex -= 97;
		
		if(buttonIndex >= 48 && buttonIndex <=57)		
			buttonIndex -= 22;
		
		if(buttonIndex == 94)
			buttonIndex = 36;
		
		if(buttonIndex == 43)
			buttonIndex = 37;
		
		System.out.println("button Index: " + buttonIndex);
		
		buttonList[buttonIndex].press();		
	}
	
	/**
	 * Method to change the button pressed index to its related ASCII character.
	 * @param button, PushButton
	 * @return buttonPushed, char
	 */
	public char buttonPressed(PushButton button){
		
		int buttonIndex = 0;
		
		while(!button.equals(buttonList[buttonIndex]))
			buttonIndex++;
		if(buttonIndex >= 0 && buttonIndex <= 25)
			buttonIndex += 97;
		else if(buttonIndex >= 26 && buttonIndex <= 35)
			buttonIndex += 22;		
		else if(buttonIndex == 36)
			buttonIndex = 94;
		else if(buttonIndex == 37)
			buttonIndex = 43;
		
		char buttonPushed = (char) buttonIndex;
		
		return buttonPushed;			
	}
	
	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		//probably something to do with the lock
	}

	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		//probably something to do with the lock
	}
	
	/**
	 * Method to react to every button press.
	 * All the changes should be made with pressing related button plus Enter. The only button that does not
	 * need the Enter button is the shift key which will disable the configuration panel. pressing any other button
	 * without enter will just save that character and append it to the string showing in the console of config panel,
	 * by pressing Enter all the string that has been saved in console will be saved as its related area.  
	 * @param button
	 */
	@Override
	public void pressed(PushButton button) {
		
		if(buttonPressed(button) == '+'){			
			
			if(!mode1 && !mode2 && !mode3 && !mode4){
				if(!buttonField.equals("")){
					if(buttonField.charAt(0) == 'a'){
						enableConfigMode();
					}
					
					else{
						buttonField = "";
						displayMessage("Invalid Command!");
					}
				}
				else{
					displayMessage("Please enter a command!");
				}			
			}
			
			else if (mode1 && !mode2 && !mode3 && !mode4){
				if(!buttonField.equals("")){
					try{
						popCanRackIndex = Integer.parseInt(buttonField);
						mode2 = true;
						displayMessage("Enter new Price: ");
						buttonField = "";
						
					}catch(Exception e){
						buttonField = "";
						displayMessage("Invalid Command!");						
					}
				}
				else{
					displayMessage("Please enter a command!");
				}
			}
			
			else if (mode1 && mode2 && !mode3 && !mode4){
				if(!buttonField.equals("")){
					try{
						newPrice = Integer.parseInt(buttonField);
						String message = "Rack Selected: " + popCanRackIndex + "\nPrice Selected: " + newPrice;
						message += "\nEnter Pop name: ";
						displayMessage(message);
						buttonField = "";
						mode3 = true;
						
					}catch(Exception e){
						buttonField = "";
						displayMessage("Invalid Command!");					
					}
				}
				else{
					displayMessage	("Please enter a command!");
				}
			}
						
			else if(mode1 && mode2 && mode3 && !mode4){
				if(!buttonField.equals("")){
				//	try{
						rackName = buttonField;
												
			//		}catch(Exception e){
						buttonField = ""; 
						displayMessage("Invalid Command!");					
					}
			//	}
				else{
					rackName = vm.getPopKindName(popCanRackIndex);
				}	
				System.out.println(rackName);
				String message = "Rack Name: " + rackName + "\nRack Selected: " + popCanRackIndex + "\nPrice Selected: " + newPrice;
				message += "\n Do you want to make these changes? Press y for YES, n for NO";
				displayMessage(message);
				buttonField = "";
				mode4 = true;
			}
			else{
				if(!buttonField.equals("")){
					if(buttonField.charAt(0) == 'y')
						saveChanges();
					else{
						displayMessage("Discarding changes");
						displayMessage("Select pop can rack number: ");
						mode2 = false;
						mode3 = false;
						mode4 = false;
					}
				}
				else{
					displayMessage("Please enter a command!");
				}
			}
		}
		else if(buttonPressed(button) == '^') {
			disableConfigMode();
		}
		else {
			buttonField += buttonPressed(button);
			System.out.println("buttonField: " + buttonField);
	
		}
	}
	
	//This is for debugging 
	public void displayModes() {
		
		System.out.println("MODE1: " + mode1);
		System.out.println("MODE2: " + mode2);
		System.out.println("MODE3: " + mode3);
	}
	
	/**
	 * Method to save all the changes has been made by technician to the vending machine, and disabling the config panel.
	 */
	public void saveChanges(){
		
		ArrayList<Integer> popCanCosts = new ArrayList<Integer>();
		ArrayList<String> popCanNames = new ArrayList<String>();
		
		
		for(int i = 0; i < vm.getNumberOfPopCanRacks(); i++)
			popCanCosts.add(vm.getPopKindCost(i));
		
		for(int i = 0; i < vm.getNumberOfPopCanRacks(); i++)
			popCanNames.add(vm.getPopKindName(i));
		
		popCanCosts.set(popCanRackIndex, newPrice);
		popCanNames.set(popCanRackIndex, rackName);
		
		vm.configure(popCanNames,popCanCosts);
		
		System.out.println("Pop Names: "+ popCanNames.toString());
		System.out.println("Pop Costs: "+ popCanCosts);
		
		disableConfigMode();
		
	}
	
	/**
	 * Method to display messages that every button press will initiate.
	 * @param inMessage
	 */
	public void displayMessage(String inMessage){
		
	//	displayMessage += "\n" + inMessage;
		displayMessage = inMessage;
		display.display(displayMessage);
	}
	
	/**
	 * Method to enable the config mode, by setting mode1 to true
	 */
	public void enableConfigMode(){
		displayMessage = "Config Mode Enabled!";
		display.display(displayMessage);
		//System.out.println("Config Mode Enabled");
		
		mode1=true;
		
		displayMessage += "\nSelect Pop Rack Number";
		display.display(displayMessage);
		
		buttonField = "";
		displayModes();
		
	}
	
	/**
	 * Method to disable config mode by setting all the modes to false.
	 */
	public void disableConfigMode(){
		mode1=false;
		mode2=false;
		mode3=false;
		mode4=false;
		display.display("Configuration Mode is disabled, to enable press 'a' then Enter");
		
	}

	public Display getDisplay() {
		return vm.getConfigurationPanel().getDisplay();
	}
	
	@Override
	public void messageChange(Display display, String oldMessage, String newMessage) {
		System.out.println(newMessage);
		displayLog += newMessage;
		
	}

}
