import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
               ", Guest: " + guestName +
               ", Room Type: " + roomType;
    }
}

// Validator class
class BookingValidator {

    private static final Set<String> VALID_ROOM_TYPES =
            new HashSet<>(Arrays.asList("Single", "Double", "Suite"));

    private Map<String, Integer> roomInventory;

    public BookingValidator() {
        roomInventory = new HashMap<>();
        roomInventory.put("Single", 2);
        roomInventory.put("Double", 2);
        roomInventory.put("Suite", 1);
    }

    // Validate booking input
    public void validate(String roomType) throws InvalidBookingException {

        // Check valid room type
        if (!VALID_ROOM_TYPES.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected!");
        }

        // Check availability
        int available = roomInventory.get(roomType);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }
    }

    // Allocate room after validation
    public void allocateRoom(String roomType) {
        roomInventory.put(roomType, roomInventory.get(roomType) - 1);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Availability:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + ": " + roomInventory.get(type));
        }
    }
}

// Main class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingValidator validator = new BookingValidator();

        System.out.println("=== Booking System with Validation ===");

        while (true) {
            System.out.println("\n1. Make Booking");
            System.out.println("2. View Inventory");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    try {
                        System.out.print("Enter Reservation ID: ");
                        String id = sc.nextLine();

                        System.out.print("Enter Guest Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Room Type (Single/Double/Suite): ");
                        String roomType = sc.nextLine();

                        // Validate input
                        validator.validate(roomType);

                        // Allocate room only if valid
                        validator.allocateRoom(roomType);

                        Reservation r = new Reservation(id, name, roomType);

                        System.out.println("Booking successful!");
                        System.out.println(r);

                    } catch (InvalidBookingException e) {
                        // Graceful failure handling
                        System.out.println("Booking Failed: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Unexpected error occurred.");
                    }
                    break;

                case 2:
                    validator.displayInventory();
                    break;

                case 3:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
