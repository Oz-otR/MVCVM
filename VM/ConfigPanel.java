package groupAssignment2;

import org.lsmr.vending.hardware.AbstractHardware;
import org.lsmr.vending.hardware.AbstractHardwareListener;
import org.lsmr.vending.hardware.Display;
import org.lsmr.vending.hardware.DisplayListener;
import org.lsmr.vending.hardware.PushButton;
import org.lsmr.vending.hardware.PushButtonListener;
import org.lsmr.vending.hardware.VendingMachine;
import java.util.ArrayList;


public class ConfigPanel implements PushButtonListener, DisplayListener{
	
	public VendingMachine vm;	
	public String[] Codes;
	public PushButton[] buttonList;
	public Display display;
	
	public boolean mode1;
	public boolean mode2;
	public boolean mode3;
	
	public String buttonField;
	public int popCanRackIndex;
	public int newPrice;
	public String displayMessage;
	
	public ConfigPanel(VendingMachine vmIn){
		
		buttonList = new PushButton[37];
		
		this.vm = vmIn;
		
		for(int i = 0; i < 37; i++){
			buttonList[i] = vm.getConfigurationPanel().getButton(i);
		}
		
		buttonList[37] = vm.getConfigurationPanel().getEnterButton();
		
		for(int i = 0; i < buttonList.length; i++)
			buttonList[i].register(this);
	}
	
	public void populateCodes(){
		
		
	}
	
	public void pressButton(char button){
		
		int buttonIndex = (int) button;

		System.out.println(buttonIndex);

		
		if(buttonIndex >= 97 && buttonIndex <= 122)		
			buttonIndex -= 97;
		
		if(buttonIndex >= 48 && buttonIndex <=57)		
			buttonIndex =- 22;
		
		if(buttonIndex == 94)
			buttonIndex = 36;
		
		System.out.println("button Index: " + buttonIndex);
		
		buttonList[buttonIndex].press();		
	}
	
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
		
	}

	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pressed(PushButton button) {
		
		System.out.println("button Pressed: " + buttonPressed(button));
		
		if(buttonPressed(button) == '+'){			
			
			if(!mode1 && !mode2 && !mode3){
				if(buttonField != null){
					if(buttonField == "a")
						enableConfigMode();
					
					else{
						buttonField = null;
						displayMessage("Invalid Command!");
					}
				}
				else{
					displayMessage("Please enter a command!");
				}			
			}
			
			else if (mode1 && !mode2 && !mode3){
				if(buttonField != null){
					try{
						popCanRackIndex = Integer.parseInt(buttonField);
						displayMessage("Enter new Price: ");
						buttonField = null;
						
					}catch(Exception e){
						buttonField = null;
						displayMessage("Invalid Command!");						
					}
				}
				else{
					displayMessage("Please enter a command!");
				}
			}
			
			else if (mode1 && mode2 && !mode3){
				if(buttonField != null){
					try{
						newPrice = Integer.parseInt(buttonField);
						String message = "Rack Selected: " + popCanRackIndex + "\nPrice Selected: " + newPrice;
						message += "\n Do you want to make these changes? Press y for YES, n for NO";
						displayMessage(message);
						buttonField = null;
						mode3 = true;
						
					}catch(Exception e){
						buttonField = null;
						displayMessage("Invalid Command!");					
					}
				}
				else{
					displayMessage("Please enter a command!");
				}
			}
			
			else 
				if(buttonField != null){
					if(buttonField == "y")
						saveChanges();
					else{
						displayMessage("Discarding changes");
						displayMessage("Select pop can rack number: ");
						mode2 = false;
						mode3 = false;
					}
				}
				else{
					displayMessage("Please enter a command!");
				}				
		}		
	}
	
	public void saveChanges(){
		
		ArrayList popCanCosts = new ArrayList<Integer>();
		ArrayList popCanNames = new ArrayList<String>();
		
		
		for(int i = 0; i < vm.getNumberOfPopCanRacks(); i++)
			popCanCosts.add(vm.getPopKindCost(i));
		
		for(int i = 0; i < vm.getNumberOfPopCanRacks(); i++)
			popCanNames.add(vm.getPopKindName(i));
		
		popCanCosts.set(popCanRackIndex, newPrice);
		
		vm.configure(popCanNames,popCanCosts);
		
	}
	public void displayMessage(String inMessage){
		
		displayMessage += "\n" + inMessage;
		display.display(displayMessage);
	}
	
	public void enableConfigMode(){

		System.out.println("Config Mode Enabled");
		
		mode1=true;
		mode2=false;
		
		displayMessage = "\nSelect Pop Rack Number";
		display.display(displayMessage);
		
		buttonField = null;
		
	}

	@Override
	public void messageChange(Display display, String oldMessage, String newMessage) {
		// TODO Auto-generated method stub
		
	}

}
