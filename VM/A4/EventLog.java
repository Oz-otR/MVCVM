package A4;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class EventLog implements EventLogInterface {
	
	
	private PrintWriter writer;
	
		
	/**
	* Constructor creates an event log file and writes base contents. 
	* File name is "WorkLog.txt", formatted in UTF-8
	* TODO: I suggest that the constructor throws an error rather than catch it so that we dont 
	* not write the event log in some case.
	*/
	public EventLog() {
		try {
			writer = new PrintWriter("WorkLog.txt", "UTF-8");
			writer.println("DATE/TIME \t\t EVENT");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println("trouble creating WorkLog file");
			e.printStackTrace();
		}		
	}
	
	/**
	* Method writs a string to the log on a new line
	* @param String s, the string to be written to the log
	* !!TODO!! writer may not exist if the constructor throws an error
	*/
	public void writeToLog(String s){
		timeStamp();
		writer.println(s + "\n");		
	}
	
	/**
	* Method writes a time stamp in the format yyyy/MM/dd HH:mm:ss to the work log.
	* !!TODO!! writer may not exist if the constructor throws an error
	*/
	public void timeStamp() {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		writer.println(df.format(date) + "\t\t");
	}
	
}