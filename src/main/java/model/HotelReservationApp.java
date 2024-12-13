package model;

import java.util.List;
import java.util.Scanner;

public class HotelReservationApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelService hotelService = new HotelService();

        // Initialisation des hôtels (si nécessaire, ou vous pouvez aussi l'initialiser à partir de la base de données)
        hotelService.initializeHotels();

        System.out.println("Welcome to the Hotel Reservation System!");
        boolean running = true;

        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Search for Hotels");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Collect search criteria
                    System.out.print("Enter city: ");
                    String city = scanner.nextLine();

                    System.out.print("Enter minimum stars (1-5): ");
                    int minStars = scanner.nextInt();

                    System.out.print("Enter maximum price: ");
                    double maxPrice = scanner.nextDouble();

                    System.out.print("Enter number of beds required: ");
                    int beds = scanner.nextInt();

                    // Search for hotels
                    List<Hotel> availableHotels = hotelService.searchHotels(city, minStars, maxPrice, beds);
                    if (availableHotels.isEmpty()) {
                        System.out.println("No hotels found matching your criteria.");
                    } else {
                        System.out.println("Available Hotels:");
                        for (Hotel hotel : availableHotels) {
                            System.out.println(hotel.getName() + " - " + hotel.getAddress() + " - Stars: " + hotel.getStars());
                        }

                        // Optional: Allow user to select a hotel and make a reservation
                        System.out.print("\nWould you like to make a reservation? (yes/no): ");
                        String makeReservation = scanner.nextLine();
                        if (makeReservation.equalsIgnoreCase("yes")) {
                            System.out.print("Enter the hotel name to book: ");
                            String hotelName = scanner.nextLine();
                            Hotel selectedHotel = findHotelByName(availableHotels, hotelName);

                            if (selectedHotel != null) {
                                System.out.print("Enter room number to reserve: ");
                                int roomNumber = scanner.nextInt();

                                // Attempt to reserve a room
                                boolean reserved = hotelService.reserveRoom(selectedHotel, roomNumber);
                                if (reserved) {
                                    System.out.println("Room reserved successfully!");
                                } else {
                                    System.out.println("Sorry, the room is already reserved.");
                                }
                            } else {
                                System.out.println("Hotel not found.");
                            }
                        }
                    }
                    break;
                case 2:
                    System.out.println("Thank you for using the Hotel Reservation System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    // Helper method to find a hotel by its name
    private static Hotel findHotelByName(List<Hotel> hotels, String name) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equalsIgnoreCase(name)) {
                return hotel;
            }
        }
        return null;
    }
}
