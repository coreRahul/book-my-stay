/**
 * =========================================================
 * MAIN CLASS - UseCase3InventorySetup
 * =========================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates how a HashMap is used to maintain
 * a centralized inventory of available rooms.
 *
 * @version 3.1
 */

public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("Hotel Inventory System\n");

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nChecking availability for DoubleRoom:");
        System.out.println("Available: " + inventory.getAvailability("DoubleRoom"));

        System.out.println("\nUpdating DoubleRoom availability...");

        inventory.updateAvailability("DoubleRoom", 4);

        System.out.println("\nUpdated Inventory:\n");
        inventory.displayInventory();
    }
}
