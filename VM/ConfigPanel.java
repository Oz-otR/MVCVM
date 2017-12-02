package groupAssignment2;

import org.lsmr.vending.hardware.AbstractHardware;
import org.lsmr.vending.hardware.AbstractHardwareListener;
import org.lsmr.vending.hardware.Display;
import org.lsmr.vending.hardware.DisplayListener;
import org.lsmr.vending.hardware.PushButton;
import org.lsmr.vending.hardware.PushButtonListener;
import org.lsmr.vending.hardware.VendingMachine;

public class ConfigPanel implements PushButtonListener, DisplayListener{
	
	public String[] Codes;
	public PushButton[] buttonList;
	public Display display;
	
	public ConfigPanel(VendingMachine vm){
		
		buttonList = new PushButton[37];
		
		for(int i = 0; i < 37; i++){
			buttonList[i] = vm.getConfigurationPanel().getButton(i);
		}
	}
	
	public void populateCodes(){
		
		
	}
	
	public void pressButton(char button){
		
		int buttonIndex = (int) button;
		
		if(buttonIndex >= 97 && buttonIndex <=122)		
			buttonIndex = buttonIndex - 97;
		
		if(buttonIndex >= 48 && buttonIndex <=57)		
			buttonIndex = buttonIndex - 22;
		
		if(buttonIndex ^)
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageChange(Display display, String oldMessage, String newMessage) {
		// TODO Auto-generated method stub
		
	}

}
