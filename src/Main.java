import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/* This program models a simple food ordering and delivery app
 * It allso allows for the addition of restaurants and restaurant items */

public class Main {
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		// load info from text files at start of program
		ArrayList<Customer> customerList = Customer.loadCustomerList("customers.txt");
		ArrayList<Restaurant> restaurantList = Restaurant.loadRestaurantList("restaurants.txt");
		ArrayList<Menu> menuList = Menu.loadMenuList("menus.txt");
		ArrayList<Driver> driverList = Driver.loadDriverList("driver-info.txt");
		ArrayList<Order> orderList = Order.loadOrderList("orders.txt");
		ArrayList<OrderItem> orderedItems = OrderItem.loadOrderItemsList("orderedItems.txt");
		
		// ================================= Main Loop ==================================
		
		boolean quit = false;
		
		while(!quit) {
			printMainMenu();
			int choice = scanner.nextInt();  
			
			switch (choice) {
			
			case 1: 
				addNewOrder(customerList, restaurantList, menuList, driverList, orderList, orderedItems);
				break;
				
			case 2:
				addNewCustomer(customerList);
				break;
				
			case 3:
				addNewRestaurant(restaurantList);
				break;
			
			case 4:
				addNewMenuItem(menuList);
				break;
			
			case 5:
				System.out.println("\nExiting program...");
				quit = true;
				break;
			}
		}
	}

	public static void printMainMenu() {
		System.out.println("\n======================== FOOD QUICK MAIN MENU ========================");
		System.out.println("\n\t1 - Create new Order");
		System.out.println("\t2 - Create new Customer Profile");
		System.out.println("\t3 - Create new Restaurant");
		System.out.println("\t4 - Create new Menu Item");
		System.out.println("\n\t5 - Exit");
		System.out.println("\n=======================================================================");
	}
	
	@SuppressWarnings("unused")
	public static void addNewOrder(ArrayList <Customer>customerList, ArrayList<Restaurant> restaurantList, ArrayList<Menu> menuList, ArrayList<Driver> driverList, ArrayList<Order> orderList, ArrayList<OrderItem> orderedItems) {
		
		// empty starting objects
		Customer currentCustomer = null;
		Restaurant currentRestaurant = null;
		Driver currentDriver = null;
		Order currentOrder = null;
		Menu currentMenuItem = null;
		OrderItem currentOrderItem = null;
		
		// ================================ identify customer =================================
		
		System.out.println("\n*** NEW ORDER ***");
		System.out.println("Please enter your contact number:");
		
		scanner.nextLine();
		String contact = scanner.nextLine();
		
		// customer contact number is used as an unique ID
		int i = 0;
		for (Customer customer: customerList) {
			if (customer.getContactNumber().equals(contact)) {
				currentCustomer = new Customer(customer.getName(), customer.getContactNumber(), customer.getAddress(), customer.getCity(), customer.getEmail());
				System.out.println("\n** Welcome back, " + customer.getName() + "! **");
				i ++;
				break;
			}
		}
		
		// in case of unidentified customer, return to main menu
		if (i == 0) {
			System.out.println("\n** Unknown contact number. Please add new customer before placing order. **");
			return;
		}
		
		
		// ============================== check driver availability ============================
		
		// identify customer city to check driver availability
		String currentCity = currentCustomer.getCity();
		
		// reload driverList to ensure latest version is used
		driverList = Driver.loadDriverList("driver-info.txt");
		
		// assign driver - drivers-info.txt is sorted with lowest loads first 
		int x = 0;
		while (currentDriver == null && x < driverList.size()) {
			if (currentCity.equals(driverList.get(x).driverCity)) {
				currentDriver = driverList.get(x);
			}
			x++; // counter
		}
		
		// if no drivers available in city, return to main menu
		if (currentDriver == null) {
			System.out.println("** Sorry! Our drivers are too far away from you to be able to deliver to your location. **\n\nRedirecting to the main menu...");
			return;
		}	
	
		// update driver load
		currentDriver.setDriverLoad(1 + currentDriver.getDriverLoad());
		
		// replace old file with new file
		File file = new File("driver-info.txt");
		
		if (file.delete()) {
			// create new file
			File newDriverFile = new File("driver-info.txt");
		}
		else {
			System.out.println("Failed to delete driver file.");
		} 
		
		// update driverList sorting after load has been added - sort smallest load first
		driverList.sort((value1, value2) -> value1.getDriverLoad().compareTo(value2.getDriverLoad()));
		
		// write new driverList to text file
		for (Driver driver:driverList) {
			String newText = driver.toString();
			Driver.updateDriverFile("driver-info.txt", newText, true); // true = append
		}
	
		// ================================ select restaurant ===================================
		
		System.out.println("\nPlease select a restaurant: \t\t(enter number)");
		
		for (int j = 0; j < restaurantList.size(); j++) {
			if (currentCity.equals(restaurantList.get(j).getRestaurantCity())) {
				System.out.println((j+1) + " - " + restaurantList.get(j).getRestaurantName());
			}
		}
		
		int userInput = scanner.nextInt()-1;
		
		if (userInput <= restaurantList.size()) {
			currentRestaurant = restaurantList.get(userInput);
		}
		else {
			System.out.println("\nPlease choose only from the available list. Cancelling order.");
			return;
		}	
		
		// =================================== create order =====================================
		
		// generate an order ID
		long orderIndex = 1 + orderList.size();
		
		// ask customer to select menu item/s
		boolean completeOrder = false;
		
		while (!completeOrder) {
			
			// display menu of chosen restaurant
			System.out.println("\nPlease select an item from " + currentRestaurant.getRestaurantName() + "'s menu: \t\t(enter number)");
			
			for (int y = 0 ; y < menuList.size(); y++ ) {
				if (currentRestaurant.restaurantName.equals(menuList.get(y).getRestaurantName())) {
					System.out.println((y+1) + " - " + menuList.get(y).getItemName() + " (R" + menuList.get(y).getPrice() + ")");
				}
			}
			
			scanner.nextLine();
			userInput = scanner.nextInt()-1;
			
			if (userInput <= menuList.size()) {
				currentMenuItem = menuList.get(userInput);
			}
			else {
				System.out.println("\nPlease choose only from the available list.");
				break;
			}	
			
			// get quantity
			System.out.println("\nHow many " + currentMenuItem.itemName + "'s would you like to add to your order? \t\t(enter number)");
			
			scanner.nextLine();
			int quantity = scanner.nextInt();
			
			// capture order items
			currentOrderItem = new OrderItem(orderIndex, currentMenuItem, quantity);
			orderedItems.add(currentOrderItem);
			
			// update text file
			OrderItem.writeOrderItemsListToFile("orderedItems.txt", orderedItems);
			
			// add another item or complete order
			System.out.println("\nWould you like to add another item to your order? \t\t(enter number)");
			System.out.println("1 = Yes \n2 = No");
			
			scanner.nextLine();
			int input = scanner.nextInt();
			
			// finish order
			if (input == 2) {
				completeOrder = true;
			}
		}
		
		// =============================== special instructions ===============================
		
		// default message
		String specialInst = "None";
		
		System.out.println("\nWould you like to add any special preparation instructions to your order? \t\t(enter number)");
		System.out.println("1 = Yes \n2 = No");
		
		scanner.nextLine();
		int special = scanner.nextInt();
		
		if (special == 1) {
			scanner.nextLine();
			System.out.println("\nPlease enter special preparation instructions:");
			specialInst = scanner.nextLine();
		}
		
		// ================================ complete order =====================================
		
		// update Order array list
		currentOrder = new Order(orderIndex, currentCustomer, currentRestaurant, specialInst, currentDriver);
		orderList.add(currentOrder);
		
		// update Order List text file
		Order.writeOrderListToFile("orders.txt", orderList);
		
		// ================================= generate invoice ========== ========================
		
		String invoiceMessage = "Order number " + orderIndex
								+ "\nCustomer: " + currentCustomer.customerName
								+ "\nEmail: " + currentCustomer.customerEmail
								+ "\nPhone number: " + currentCustomer.customerContactNumber
								+ "\nLocation: " + currentCustomer.customerCity
								+ "\n\nYou have ordered the following from " + currentRestaurant.restaurantName + " in " + currentRestaurant.restaurantCity + ":\n\n";
		
		// get details of ordered items  and total price
		double totalPrice = 0;
		
		for (int z = 0; z < orderedItems.size(); z++) {
			if (orderIndex == orderedItems.get(z).getOrderID()) {
				invoiceMessage = invoiceMessage + orderedItems.get(z).orderQty + " x " + orderedItems.get(z).itemName.getItemName() + " (R" + orderedItems.get(z).itemName.price + ")\n";
				totalPrice = totalPrice + (orderedItems.get(z).orderQty * orderedItems.get(z).itemName.price);
			}
		}
		
		invoiceMessage = invoiceMessage
						+ "\nSpecial Instructions: " + specialInst 
						+ "\n\nTotal: R" + totalPrice 
						+ "\n\n" + currentDriver.driverName + " is nearest to the restaurant and will be delivering your order to you at:"
						+ "\n\n" + currentCustomer.customerAddress 
						+ "\n\nIf you need to the contact the restaurant, their number is " + currentRestaurant.restaurantContactNumber + ".";
		 
		try {				
			// create new file to store invoice
			Formatter f1 = new Formatter("Invoice Order#" + orderIndex + ".txt");
			
			// add invoice message text file
			f1.format(invoiceMessage);
			System.out.println("\n** Invoice Generated: Order#" + orderIndex + ".txt **");
			
			// close formatter
			f1.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Error, Invoice could not be generated.");
		}		
	 
		return;
	}
	
	public static ArrayList<Customer> addNewCustomer(ArrayList<Customer> customerList) {

		scanner.nextLine();
		System.out.println("\n========= Create New Customer Profile ==========");
		System.out.println("Enter customer name:");
		String newName = scanner.nextLine();
		System.out.println("Enter contact number:");
		String newContact = scanner.nextLine();
		System.out.println("Enter address:");
		String newAddress = scanner.nextLine();
		System.out.println("Enter city name:");
		String newCity = scanner.nextLine();
		System.out.println("Enter email address:");
		String newEmail = scanner.nextLine();
		
		System.out.println("\nSubmit new Customer Profile?  \t\t(enter number)");
		System.out.println("1 = Yes \n2 = No");
		
		int submit = scanner.nextInt();
		scanner.nextLine();
		
		// cancel new customer profile addition
		if (submit == 2) {
			System.out.println("\n** New Customer Profile Cancelled. **");
			return customerList;
		}
		
		// create new Customer object
		Customer newCustomer = Customer.createCustomer(newName, newContact, newAddress, newCity, newEmail);
		
		// check for duplicates
		int i = 0;
		for (Customer customer: customerList) {
			if (customer.getName().equals(newName)) {
				i++;	
			}
		}
		
		if (i > 0) {
			System.out.println("\n** Customer is already exists. **");	
		}
		else {
			// update array List and write to file
			customerList.add(newCustomer);
			
			String outputText = newCustomer.toString();
			Customer.writeNewCustomerToFile("customers.txt", outputText, true); // true - append to current list
			
			System.out.println("\n** New Customer added: " + newName + " **");
		}
		
		System.out.println("\nUpdated Customer List:");
		for (Customer indivCustomer:customerList) {
			System.out.println(indivCustomer);
		} 	
		
		return customerList;
	}
	
	public static ArrayList<Restaurant> addNewRestaurant(ArrayList<Restaurant> restaurantList) {
		
		scanner.nextLine();
		
		// current Restaurants
		System.out.println("\nCurrent Restaurant List:\n");
		for (Restaurant indivRestaurant:restaurantList) {
			System.out.println(indivRestaurant.getRestaurantName() + " - " + indivRestaurant.getRestaurantCity());
		} 
		
		System.out.println("\n=========== Create New Restaurant ============");
		System.out.println("Enter restaurant name:");
		String newRName = scanner.nextLine();
		System.out.println("Enter contact number:");
		String newRContact = scanner.nextLine();
		System.out.println("Enter city name:");
		String newRCity = scanner.nextLine();
		
		// new restaurant object
		Restaurant newRestaurant = Restaurant.createRestaurant(newRName, newRContact, newRCity);
		
		// check for duplicates - check names and city
		int j = 0;
		
		for (Restaurant restaurant: restaurantList) {
			if (restaurant.restaurantName.equals(newRName)) {
				if (restaurant.restaurantCity.equals(newRCity)) {
					j++;
				}
			}
		}
		
		if (j > 0) {
			System.out.println(newRName + " is already loaded in " + newRCity + ".");
		}
		else {
			restaurantList.add(newRestaurant);
			
			String outputText = newRestaurant.toString();
			Restaurant.writeNewRestaurantToFile("restaurant.txt", outputText, true); // true - append to current list
			
			System.out.println("\n** New " + newRName + " restaurant added in " + newRCity + ". **");
		}
		
		System.out.println("\nUpdated Restaurant List:");
		for (Restaurant indivRestaurant:restaurantList) {
			System.out.println(indivRestaurant);
		} 
		
		return restaurantList; 	
	}
		
	public static ArrayList<Menu> addNewMenuItem(ArrayList<Menu> menuList) {
		
		scanner.nextLine(); 
		
		System.out.println("\n=========== Add New Menu Item ============");
		System.out.println("Enter restaurant name:");
		String newRestaurantName = scanner.nextLine();
		System.out.println("Enter item name:");
		String newItemName = scanner.nextLine();
		System.out.println("Enter item price:");
		double newPrice = scanner.nextDouble();
		//
		scanner.nextLine();
		
		// new menu item object
		Menu newMenuItem = Menu.createMenu(newRestaurantName, newItemName, newPrice);
		
		// check for duplicates - check restaurant name and item name
		int k = 0;
		int l = 0;
		
		for (Menu menuItem: menuList) {
			if (menuItem.restaurantName.equals(newRestaurantName)) {
				k++;
				if (menuItem.itemName.equals(newItemName)) {
					l++;
				}
			}
		}
		
		if (k == 0) {
			System.out.println("Unknown restaurant chosen. Please add new restaurant before creating a menu.");
		}
		else if (k > 0 && l > 0 && k == l) {
			System.out.println(newItemName + " is already loaded in " + newRestaurantName + "'s menu.");
		}
		else if (k > 0 && k > l) {
			menuList.add(newMenuItem);
			
			String outputText = newMenuItem.toString();
			Menu.writeNewMenuItemToFile("menus.txt", outputText, true); // true - append to current list
			
			System.out.println("** " + newItemName + " added to " + newRestaurantName + "'s menu. **");
		}
		else {
			System.out.println("Unexpected error while updating menu. Redirecting back to main menu.");
		}
		
		System.out.println("\nUpdated Menu List:");
		for (Menu indivMenuItem:menuList) {
			System.out.println(indivMenuItem);
		} 
		
		return menuList;
	}
}

