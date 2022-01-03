import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.Serializable;

public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Attributes
	long orderNumber;
	Customer customerName;
	Restaurant orderRestaurant; 
	String specialInstructions;
	Driver driverName;
	
	// Method: Constructor
	public Order(long orderNumber, Customer customerName, Restaurant orderRestaurant, String specialInstructions, Driver driverName) {
		this.orderNumber = orderNumber;
		this.customerName = customerName;
		this.orderRestaurant = orderRestaurant;
		this.specialInstructions = specialInstructions;
		this.driverName = driverName;
	}
	
	// Method: Default String version
	public String toString() {
		String output;
		output = orderNumber + "|";
		output += customerName + "|";
		output += orderRestaurant + "|";
		output += specialInstructions + "|";
		output += driverName;
		return output;
	}
	
	// Method: Read file and save objects to arrayList
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<Order> loadOrderList(String fileName)  {
		
		ArrayList<Order> orders = new ArrayList<Order>();
		
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
			orders = (ArrayList) is.readObject();
			is.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (EOFException e) {
			// this is fine - end of input reached
		}
		catch (IOException e) {
			//handle exception which is unexpected
			e.printStackTrace();
		}
		
		return orders;	
	}
	
	// Method: Write to text file 
	public static void writeOrderListToFile(String fileName, ArrayList<Order> orders) {
	
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(orders);
			os.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e){
			e.printStackTrace();
	    }
	}
	
	// Method: Getters and Setters
	public long getOrderNumber() {
		return orderNumber;
	}

	public Customer getCustomerName() {
		return customerName;
	}

	public Restaurant getOrderRestaurant() {
		return orderRestaurant;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public Driver getDriverName() {
		return driverName;
	}
	
}	

