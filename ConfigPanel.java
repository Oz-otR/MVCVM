package A4;
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



public class ConfigPanel implements PushButtonListener, DisplayListener, LockListener{

	
	public VendingMachine vm;	
	public String[] Codes;
	public PushButton[] buttonList;
	public Display display;
	
	public boolean mode1;
	public boolean mode2;
	public boolean mode3;
	public boolean mode4;

	
	public String buttonField;
	public int popCanRackIndex;
	public int newPrice;

	public String newRackName;
	
	public String displayMessage;
	public String displayLog;
	
	public ConfigPanel(VendingMachine vmIn){
		
		buttonList = new PushButton[38];

		this.vm = vmIn;
		
		resetButtonField();
    
    mode1=false;
		mode2=false;
		mode3=false;
		buttonField = "";
		for(int i = 0; i < 37; i++)
			buttonList[i] = vm.getConfigurationPanel().getButton(i);
		
		buttonList[37] = vm.getConfigurationPanel().getEnterButton(); 
		
		display = vm.getConfigurationPanel().getDisplay();
		
		for(int i = 0; i < buttonList.length; i++)
			buttonList[i].register(this);

		vm.getConfigurationPanel().getDisplay().register(this);
	}
	
	public void populateCodes(){	

	}
	
	public void pressButton(char button){
		
		int buttonIndex = (int) button;

		if(buttonIndex >= 97 && buttonIndex <= 122)		
			buttonIndex -= 97;
		
		else if(buttonIndex >= 48 && buttonIndex <=57)		
			buttonIndex -= 22;
		
		else if(buttonIndex == 94)
			buttonIndex = 36;
		
		else if(buttonIndex == 43)
			buttonIndex = 37;
		
		//System.out.println("Pressing button at index: " + buttonIndex );
		
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
		
//		System.out.println("button Pressed: " + buttonPressed(button));
		
		if(buttonPressed(button) == '+'){			

			if(!mode1 && !mode2 && !mode3 && !mode4){
				
				if(buttonField != null){
					
					
					if(buttonField.charAt(0) == 'a')
						
						enableConfigMode();
					
					
					else{
						
						displayMessage("Invalid Command!");		
						
						resetButtonField();
					}
					
				}
				
				else{
					
					displayMessage("Please enter a command!");
				}
				
			}
			
			
			else if (mode1 && !mode2 && !mode3 && !mode4){
				
				
				if(buttonField != null){
					
					try{
						
						popCanRackIndex = Integer.parseInt(buttonField);
						
						displayMessage("Rack number " + popCanRackIndex + " selected for configuration");
						
						mode2 = true;
						
						displayMessage("\nCurrent Name: " +  vm.getPopKindName(popCanRackIndex) + ", Enter new Name: ");
						
						resetButtonField();
						
					}catch(Exception e){
						
						displayMessage("Invalid Command!");

						resetButtonField();
						
					}
					
				}
				
				else{
					
					displayMessage("Please enter a command!");
				}
				
			}
			
			
			else if (mode1 && mode2 && !mode3 && !mode4){
				
				if(buttonField != null){
					
					try{
						
						newRackName = buttonField;
						
						String message = "New name selected: " + newRackName;
						
						message += "\n\nCurrent Price: " +  vm.getPopKindCost(popCanRackIndex) + ", Enter new price: ";
						
						displayMessage(message);
						
						mode3 = true;
						
						resetButtonField();
						

						
					}catch(Exception e){
						
						resetButtonField();
						
						displayMessage("Invalid Command!");					
					}
				}
				else{
					
					displayMessage("Please enter a command!");
					
				}
			}
			
			
			else if (mode1 && mode2 && mode3 && !mode4){
				
				if(buttonField != null){
					
					try{
						
						newPrice = Integer.parseInt(buttonField);
						
						String message = "New price selected: " + newPrice;
						
						message += "\n\nRack Selected: " + popCanRackIndex + "\nNew Name Selected: " + newRackName + "\nNew Price Selected: " + newPrice + "\n";
						
						message += "\nDo you want to make these changes? Press y for YES, n for NO";
						
						displayMessage(message);
						
						mode4 = true;
						
						resetButtonField();
						

						
					}catch(Exception e){
						
						resetButtonField();
						
						displayMessage("Invalid Command!");					
					}
				}
				else{
					
					displayMessage("Please enter a command!");
					
				}
			}
			
			else 
				if(buttonField != null){
					
					if(buttonField.charAt(0) == 'y')
						
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
		
		
		else if(buttonPressed(button) == '^') {
			
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
		
		System.out.println("MODE4: " + mode4);
	}
	
	public void resetButtonField() {
		
		this.buttonField = "";
	}
	
	public void resetConfig() {
		
		mode1 = false;
		
		mode2 = false;
		
		mode3 = false;
		
		mode4 = false;
		
		resetButtonField();
	}
	
	public void saveChanges(){
		
		displayMessage("Save changes? " + buttonField);
		
		ArrayList<Integer> popCanCosts = new ArrayList<Integer>();
		
		ArrayList<String> popCanNames = new ArrayList<String>();
				
		for(int i = 0; i < vm.getNumberOfPopCanRacks(); i++)
			popCanCosts.add(vm.getPopKindCost(i));
		
		for(int i = 0; i < vm.getNumberOfPopCanRacks(); i++)
			popCanNames.add(vm.getPopKindName(i));
		
		popCanCosts.set(popCanRackIndex, newPrice);
		
		popCanNames.set(popCanRackIndex, newRackName);
		
		vm.configure(popCanNames,popCanCosts);
		
		displayMessage("\nChanges Saved!");
		
		resetConfig();
		
	}
	
	public void displayMessage(String inMessage){
		
		displayMessage += "\n" + inMessage;
		
		display.display(displayMessage);
	}
	
	public void enableConfigMode(){

		System.out.println("Config Mode Enabled");
		
		mode1=true;		
		mode2=false;
		
		displayMessage = "\nSelect Pop Rack Number: ";
		
		display.display(displayMessage);
		
		resetButtonField();
		//displayModes();
	}
	
	public Display getDisplay() {
		
		return vm.getConfigurationPanel().getDisplay();
	}

	@Override
	public void messageChange(Display display, String oldMessage, String newMessage) {

		System.out.println(newMessage);

		displayLog += newMessage;
		
	}


	@Override
	public void locked(Lock lock) {
		//Lock listener <------ We need to implement lock
		
	}

	@Override
	public void unlocked(Lock lock) {
		//Lock listener <------ We need to implement lock
		
	}
}
