/**
 * =========================================================
 * MAIN CLASS - UseCase6RoomAllocationService
 * =========================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Processes booking requests and assigns rooms safely.
 *
 * @version 6.1
 */

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("Hotel Booking Allocation System\n");

        RoomInventory inventory = new RoomInventory();

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Alice", "SingleRoom"));
        queue.addRequest(new Reservation("Bob", "DoubleRoom"));
        queue.addRequest(new Reservation("Charlie", "SuiteRoom"));
        queue.addRequest(new Reservation("David", "SingleRoom"));

        RoomAllocationService allocationService =
                new RoomAllocationService(inventory, queue);

        allocationService.processBookings();

        System.out.println("Updated Inventory:\n");
        inventory.displayInventory();
    }
}