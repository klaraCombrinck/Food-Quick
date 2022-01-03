import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;
	
		// Attributes
		String restaurantName;
		String itemName;
		double price;

	// Method: Constructor
	public Menu(String restaurantName, String itemName, double price) {
		this.restaurantName = restaurantName;
		this.itemName = itemName;
		this.price = price;
	}
	
	// Method: Default String version
	public String toString() {
		String output;
		output = restaurantName + "|";
		output += itemName + "|";
		output += price + "\n";
		return output;
	}
	
	// Method: create new MenuItem
	public static Menu createMenu(String restaurantName, String itemName, double price) {
		return new Menu(restaurantName, itemName, price);
	}
	
	// Method: Read file and save objects to arrayList
	public static ArrayList<Menu> loadMenuList(String fileName) {
		
		ArrayList<Menu> menus = new ArrayList<Menu>();
		
		try {
			File file = new File(fileName); // creates new file instance
			Scanner scan = new Scanner(file);
			
			while (scan.hasNextLine()) {
				
				String line = scan.nextLine();
				String[] items = line.split("\\|");
				
				String restaurantName = items[0].trim();
				String itemName = items[1].trim();
				String priceStr = items[2].trim();
				
				// turn string into double
				double itemPrice;
				
				if (isDouble(priceStr)) {
					itemPrice = Double.parseDouble(priceStr);
				}
				else {
					itemPrice = 0;	// assign numerical value
					System.out.println("Item Price could not be read.");
				}
				
				// add new object to array
				Menu newMenuItem = new Menu(restaurantName, itemName, itemPrice);
				menus.add(newMenuItem);
            	
			}
			scan.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return menus;	
	}
	
	// Method: check is string can be converted to an integer
	public static boolean isDouble(String priceStr) {
	  	
		try {
	    	@SuppressWarnings("unused")
			double x = Double.parseDouble(priceStr);
	      	return true; 
		} 
	  	catch (NumberFormatException e) {
	    	return false; // String is not an Integer
		}
	}
	
	// Method: Save to file
	public static void writeNewMenuItemToFile(String fileName, String text, boolean append) {
		
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
	
	// Method: Getters and setters
	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
 