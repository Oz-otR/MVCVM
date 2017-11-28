package groupAssignment2;

class EventStub implements EventLogInterface {

	/**
	* Method does nothing. This is to prevent writing a work log when testing
	*/
	@Override
	public void writeToLog(String s) {
	}

	@Override
	public void timeStamp() {
		// TODO Auto-generated method stub
		
	}


}