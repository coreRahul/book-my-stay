/**
 * =========================================================
 * MAIN CLASS - UseCase4RoomSearch
 * =========================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates read-only access to room inventory.
 *
 * @version 4.1
 */

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("Hotel Room Search\n");

        RoomInventory inventory = new RoomInventory();

        RoomSearchService searchService = new RoomSearchService(inventory);

        searchService.searchAvailableRooms();
    }
}