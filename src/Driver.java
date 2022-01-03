import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Attributes
	String driverName;
	String driverCity;
	int driverLoad;
	
	// Method: Constructor
	public Driver(String driverName, String driverCity, int driverLoad) {
		this.driverName = driverName;
		this.driverCity = driverCity;
		this.driverLoad = driverLoad;
	}
	
	// Method: Default toString
	public String toString() {
		String output;
		output = driverName + ",";
		output += driverCity + ",";
		output += driverLoad;
		return output;
	}
	
	// Method: read from file and return an arrayList
	public static ArrayList<Driver> loadDriverList(String fileName) {
		
		ArrayList<Driver> drivers = new ArrayList<>();
		
		try {
			File file = new File(fileName); // creates new file instance
			Scanner scan = new Scanner(file);
			
			while (scan.hasNextLine()) {
				
				String line = scan.nextLine();
				String[] items = line.split(",");
				String driverName = items[0].trim();
				String driverCity = items[1].trim();
				String driverLoadStr = items[2].trim();
				
				// turn string into integer
				int driverLoad;
				
				if (isInteger(driverLoadStr)) {
					driverLoad = Integer.parseInt(driverLoadStr);
				}
				else {
					driverLoad = 0;	// assign numerical value
				}
				
				// add new driver object to array
				Driver newDriver = new Driver(driverName, driverCity, driverLoad);
            	drivers.add(newDriver);
            	
			}
			scan.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// sort smallest load first
		drivers.sort((value1, value2) -> value1.getDriverLoad().compareTo(value2.getDriverLoad()));
		
		return drivers;
	}
	
	// Method: check is string can be converted to an integer
	public static boolean isInteger(String driverLoadStr) {
	  	
		try {
	    	@SuppressWarnings("unused")
			int x = Integer.parseInt(driverLoadStr);
	      	return true; 
		} 
	  	catch (NumberFormatException e) {
	    	return false; // String is not an Integer
		}
	}
	
	// Method: Save to file
	public static void updateDriverFile(String fileName, String text, boolean append) {
		
		try {
			File file2 = new File (fileName);
			FileWriter fw = new FileWriter(file2, append);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(text);
			pw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// Method: Getter & Setters
	public String getDriverName() {
		return driverName;
	}

	public String getDriverCity() {
		return driverCity;
	}

	public Integer getDriverLoad() {
		return driverLoad;
	}

	public void setDriverLoad(Integer driverLoad) {
		this.driverLoad = driverLoad;
	}
	
	
}
