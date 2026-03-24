import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
               ", Guest: " + guestName +
               ", Room Type: " + roomType +
               ", Room ID: " + roomId;
    }
}

// Booking Manager
class BookingManager {

    private Map<String, Integer> inventory;
    private Map<String, Reservation> bookings;
    private Stack<String> rollbackStack;

    public BookingManager() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);

        bookings = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    // Create booking
    public void createBooking(String id, String name, String roomType) {
        if (!inventory.containsKey(roomType) || inventory.get(roomType) <= 0) {
            System.out.println("Booking failed: Room not available.");
            return;
        }

        String roomId = roomType.substring(0, 1) + (inventory.get(roomType));
        inventory.put(roomType, inventory.get(roomType) - 1);

        Reservation r = new Reservation(id, name, roomType, roomId);
        bookings.put(id, r);

        System.out.println("Booking successful!");
        System.out.println(r);
    }

    // Cancel booking with rollback
    public void cancelBooking(String reservationId) {

        if (!bookings.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Reservation not found.");
            return;
        }

        Reservation r = bookings.get(reservationId);

        // Push to stack (rollback tracking)
        rollbackStack.push(r.getRoomId());

        // Restore inventory
        String roomType = r.getRoomType();
        inventory.put(roomType, inventory.get(roomType) + 1);

        // Remove booking
        bookings.remove(reservationId);

        System.out.println("Booking cancelled successfully!");
        System.out.println("Rolled back Room ID: " + rollbackStack.peek());
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }

    // Display bookings
    public void displayBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No active bookings.");
            return;
        }

        System.out.println("\nActive Bookings:");
        for (Reservation r : bookings.values()) {
            System.out.println(r);
        }
    }
}

// Main class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingManager manager = new BookingManager();

        System.out.println("=== Booking Cancellation System ===");

        while (true) {
            System.out.println("\n1. Create Booking");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View Bookings");
            System.out.println("4. View Inventory");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Guest Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room Type (Single/Double/Suite): ");
                    String roomType = sc.nextLine();

                    manager.createBooking(id, name, roomType);
                    break;

                case 2:
                    System.out.print("Enter Reservation ID to cancel: ");
                    String cancelId = sc.nextLine();

                    manager.cancelBooking(cancelId);
                    break;

                case 3:
                    manager.displayBookings();
                    break;

                case 4:
                    manager.displayInventory();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
