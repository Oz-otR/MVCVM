package groupAssignment2;
import java.awt.event.*;
import org.lsmr.vending.hardware.*;
import org.lsmr.vending.*;

public class GuiController implements ActionListener{
	private GuiView view;
	private VendingMachine machine;
	private VendingLogic logic;
	
	/**
	 * creates the Gui controller, this will handle any interaction with the view,
	 * it will make direct calls to the hardware to simulate user action
	 * will need to be called in several places in the logic
	 * 
	 * @param machine, the VM passed to the logic
	 */
	public GuiController(VendingMachine machine, VendingLogic logic) {
		this.view = new GuiView(this);
		this.machine = machine;
		this.logic = logic;
	}
	
	
	
	/** test code to made sure the window is working
	public GuiController() {
		view = new GuiView(this);
	} 
	public static void main(String[] args) {
		GuiController demo = new GuiController();
	}
	*/
	
	
	
	/**
	 * Block interprets inputs from the view and takes the appropriate
	 * action on the hardware, the last one will cast the value in the
	 * coin field into a new coin before inserting it to the coin slot
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if(action.equals("pop1")) 
			machine.getSelectionButton(0).press();
		else if(action.equals("pop2")) 
			machine.getSelectionButton(1).press();
		else if(action.equals("pop3")) 
			machine.getSelectionButton(2).press();
		else if(action.equals("pop4")) 
			machine.getSelectionButton(3).press();
		else if(action.equals("pop5")) 
			machine.getSelectionButton(4).press();
		else if(action.equals("pop6")) 
			machine.getSelectionButton(5).press();
		else if(action.equals("insertCoin")) {
			try {
				machine.getCoinSlot().addCoin(new Coin(view.getCoinField()));
			} catch (DisabledException e1) {
				view.updateDisplay("coinslot disabled");
				e1.printStackTrace();
			}
		}
		else if(action.equals("removePop")) {
			machine.getDeliveryChute().removeItems();
			view.updatePopOut("pop removed");
		}
		else if(action.equals("returnChange")) {
			logic.returnChange();
		}
	}
		

	/**
	 * any calls from the logic to update the gui are here
	 * They're mostly intermediaries to the view
	 */
	//update the display, strings only
	public void updateDisplay(String text) {
		view.updateDisplay(text);
	}
	//update pop dispensed display, strings only
	public void updatePopOut(String text) {
		view.updatePopOut(text);
	}
	//update out of order light
	public void updateOOOL(String state) {
		view.updateOOOL(state);
	}
	//update exact change only light
	public void updateECOL(String state) {
		view.updateECOL(state);
	}
	//update coin return
	public void updateCoinReturn(int coinvalue) {
		view.updateCoinReturn(Integer.toString(coinvalue));
	}

}
