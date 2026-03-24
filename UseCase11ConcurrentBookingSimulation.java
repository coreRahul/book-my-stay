import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking System (Thread-Safe)
class ConcurrentBookingSystem {

    private Map<String, Integer> inventory;
    private Queue<BookingRequest> requestQueue;

    public ConcurrentBookingSystem() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);

        requestQueue = new LinkedList<>();
    }

    // Add booking request (shared queue)
    public synchronized void addRequest(BookingRequest request) {
        requestQueue.add(request);
        System.out.println("Request added: " + request.guestName + " -> " + request.roomType);
    }

    // Process booking request (critical section)
    public synchronized void processRequest() {
        if (requestQueue.isEmpty()) return;

        BookingRequest req = requestQueue.poll();

        if (inventory.containsKey(req.roomType) && inventory.get(req.roomType) > 0) {
            // Allocate room safely
            int available = inventory.get(req.roomType);
            inventory.put(req.roomType, available - 1);

            System.out.println(Thread.currentThread().getName() +
                    " booked " + req.roomType + " for " + req.guestName);
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " failed booking for " + req.guestName + " (No rooms)");
        }
    }

    // Display inventory
    public synchronized void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// Worker Thread
class BookingProcessor extends Thread {

    private ConcurrentBookingSystem system;

    public BookingProcessor(ConcurrentBookingSystem system, String name) {
        super(name);
        this.system = system;
    }

    @Override
    public void run() {
        // Each thread processes multiple requests
        for (int i = 0; i < 3; i++) {
            system.processRequest();

            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Main Class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        ConcurrentBookingSystem system = new ConcurrentBookingSystem();

        // Simulate multiple guest requests
        system.addRequest(new BookingRequest("Rahul", "Single"));
        system.addRequest(new BookingRequest("Ankit", "Single"));
        system.addRequest(new BookingRequest("Priya", "Double"));
        system.addRequest(new BookingRequest("Neha", "Suite"));
        system.addRequest(new BookingRequest("Arjun", "Suite")); // overbooking test

        // Create multiple threads
        BookingProcessor t1 = new BookingProcessor(system, "Thread-1");
        BookingProcessor t2 = new BookingProcessor(system, "Thread-2");

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final state
        system.displayInventory();
    }
}
