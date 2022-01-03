import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	
	// Attributes
	String customerName;
	String customerContactNumber; // use as ID
	String customerAddress;
	String customerCity;
	String customerEmail;
	
	// Method: Constructor 
	public Customer(String customerName, String customerContactNumber, String customerAddress, String customerCity, String customerEmail) {
		this.customerName = customerName;
		this.customerContactNumber = customerContactNumber;
		this.customerAddress = customerAddress;
		this.customerCity = customerCity;
		this.customerEmail = customerEmail;
	}
	
	// Method: object in string form
	public String toString() {
		String output;
		output = customerName + "|";
		output += customerContactNumber + "|";
		output += customerAddress + "|";
		output += customerCity + "|";
		output += customerEmail + "\n";
		return output;
	}
	
	// Method: create new Customer
	// Reference: Udemy Java Course Tim Buchalka
	// exposes a public static method that allows creation of a customer without creation of a new separate object in main method
	public static Customer createCustomer(String customerName, String customerContactNumber, String customerAddress, String customerCity, String customerEmail) {
		return new Customer(customerName, customerContactNumber, customerAddress, customerCity, customerEmail);
	}	
	
	// Method: Read file and save objects to arrayList
	public static ArrayList<Customer> loadCustomerList(String fileName) {
		
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		try {
			File file = new File(fileName); // creates new file instance
			Scanner scan = new Scanner(file);
			
			while (scan.hasNextLine()) {
				
				String line = scan.nextLine();
				String[] items = line.split("\\|");
				
				String customerName = items[0].trim();
				String customerContactNumber = items[1].trim();
				String customerAddress = items[2].trim();
				String customerCity = items[3].trim();
				String customerEmail = items[4].trim();
				
				// add new object to array
				Customer newCustomer = new Customer(customerName, customerContactNumber, customerAddress, customerCity, customerEmail);
				customers.add(newCustomer);
            	
			}
			scan.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return customers;	
	}
	
	// Method: Save to file
	public static void writeNewCustomerToFile(String fileName, String text, boolean append) {
		
		try {
			File file1 = new File (fileName);
			FileWriter fw = new FileWriter(file1, append);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(text);
			pw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Methods: Getters
	public String getName() {
		return customerName;
	}

	public String getContactNumber() {
		return customerContactNumber;
	}

	public String getAddress() {
		return customerAddress;
	}

	public String getCity() {
		return customerCity;
	}

	public String getEmail() {
		return customerEmail;
	}
}
