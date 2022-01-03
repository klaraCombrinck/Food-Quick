import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class OrderItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Attributes
	long orderID;
	Menu itemName;
	int orderQty;
	
	//Method: Constructor
	public OrderItem (long orderID, Menu itemName, int orderQty) {
		this.orderID = orderID;
		this.itemName = itemName; 
		this.orderQty = orderQty; 
	}
	
	// Method: Default String version
	public String toString() {
		String output;
		output = orderID + " | ";
		output += itemName + " | ";
		output += orderQty;
		return output;
	}
	
	// Method: Read file and save objects to arrayList
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<OrderItem> loadOrderItemsList(String fileName) {
		
		ArrayList<OrderItem> orderitems = new ArrayList<OrderItem>();
		
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
			orderitems = (ArrayList) is.readObject();
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
		return orderitems;	
	}
	
	// Method: Write to text file 
	public static void writeOrderItemsListToFile(String fileName, ArrayList<OrderItem> orders) {
	
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
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
	
	// Method: Getters 
	public long getOrderID() {
		return orderID;
	}

	public Menu getItemName() {
		return itemName;
	}

	public int getOrderQty() {
		return orderQty;
	}
}
