import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Restaurant implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Attributes
	String restaurantName;
	String restaurantContactNumber;
	String restaurantCity;
		
	// Method: Constructor
	public Restaurant(String restaurantName, String restaurantContactNumber, String restaurantCity) {
		this.restaurantName = restaurantName;
		this.restaurantContactNumber = restaurantContactNumber;
		this.restaurantCity = restaurantCity;
	}
	
	// Method: Create new restaurant
	public static Restaurant createRestaurant(String restaurantName, String restaurantContactNumber, String restaurantCity) {
		return new Restaurant(restaurantName, restaurantContactNumber, restaurantCity);
	}
	
	// Method: Default String version
	public String toString() {
		String output;
		output = restaurantName + "|";
		output += restaurantContactNumber + "|";
		output += restaurantCity + "\n";
		return output;
	}
	
	// Method: Read file and save objects to arrayList
	public static ArrayList<Restaurant> loadRestaurantList(String fileName) {
		
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		try {
			File file = new File(fileName); // creates new file instance
			Scanner scan = new Scanner(file);
			
			while (scan.hasNextLine()) {
				
				String line = scan.nextLine();
				String[] items = line.split("\\|");
				
				String restaurantName = items[0].trim();
				String restaurantContactNumber = items[1].trim();
				String restaurantCity = items[2].trim();
				
				// add new object to array
				Restaurant newRestaurant = new Restaurant(restaurantName, restaurantContactNumber, restaurantCity);
				restaurants.add(newRestaurant);
            	
			}
			scan.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return restaurants;	
	}
	
	// Method: Save to file
	public static void writeNewRestaurantToFile(String fileName, String text, boolean append) {
		
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
	
	// Methods: Getters and setters
	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getRestaurantContactNumber() {
		return restaurantContactNumber;
	}

	public void setRestaurantContactNumber(String restaurantContactNumber) {
		this.restaurantContactNumber = restaurantContactNumber;
	}

	public String getRestaurantCity() {
		return restaurantCity;
	}

	public void setRestaurantCity(String restaurantCity) {
		this.restaurantCity = restaurantCity;
	}

}
