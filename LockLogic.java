package A4;

import org.lsmr.vending.hardware.AbstractHardware;
import org.lsmr.vending.hardware.AbstractHardwareListener;
import org.lsmr.vending.hardware.Lock;
import org.lsmr.vending.hardware.LockListener;
import org.lsmr.vending.hardware.VendingMachine;

public class LockLogic implements LockListener {
	
	VendingMachine vm;
	ConfigPanel cp;
	Lock lock;
	
	public LockLogic(VendingMachine inVm, ConfigPanel configPanel) {
		
		this.cp = configPanel;
		
		this.vm = inVm;
		
		this.lock = vm.getLock();
		
		lock.register(this);
		
		this.unlock();

		
	}

	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {

		System.out.println("Configuration Panel active");
		
	}

	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {

		System.out.println("Configuration Panel inactive");
		
	}

	@Override
	public void locked(Lock lock) {
		
		System.out.println("Configuration Panel active");
		
		cp.enablePanel();
	}

	@Override
	public void unlocked(Lock lock) {

		System.out.println("Configuration Panel inactive");
		
		cp.disablePanel();
		
	}
	
	public void lock() {
		
		vm.enableSafety();
		
		lock.lock();
		
	}
	
	public void unlock() {
	
		vm.disableSafety();
		
		lock.unlock();
	}	
	public boolean isLocked(){
		
		return lock.isLocked();		
	}


}