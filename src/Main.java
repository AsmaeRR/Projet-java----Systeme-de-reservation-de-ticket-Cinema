package cinema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static CinemaService cinemaService = new CinemaService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeData();

        boolean continueRunning = true;
        while (continueRunning) {
            displayMainMenu();
            int choice = readInteger();

            switch (choice) {
                case 1:
                    displayMovies();
                    break;
                case 2:
                    displayAvailableShowtimes();
                    break;
                case 3:
                    makeReservation();
                    break;
                case 4:
                    displayCustomerReservations();
                    break;
                case 5:
                    cancelReservation();
                    break;
                case 6:
                    addCustomer();
                    break;
                case 7:
                    displayStatistics();
                    break;
                case 0:
                    continueRunning = false;
                    System.out.println("Thank you for using our system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   CINEMA TICKET RESERVATION SYSTEM         ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("1. Display all movies");
        System.out.println("2. Display available showtimes");
        System.out.println("3. Make a reservation");
        System.out.println("4. View my reservations");
        System.out.println("5. Cancel a reservation");
        System.out.println("6. Create customer account");
        System.out.println("7. Statistics");
        System.out.println("0. Exit");
        System.out.print("\nYour choice: ");
    }

    private static void displayMovies() {
        System.out.println("\n═══ AVAILABLE MOVIES ═══");
        var movies = cinemaService.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("No movies available.");
        } else {
            for (Movies movie : movies) {
                System.out.println("\n[" + movie.getId() + "] " + movie.getTitle());
                System.out.println("    Genre: " + movie.getGenre());
                System.out.println("    Director: " + movie.getDirector());
                System.out.println("    Duration: " + movie.getDuration() + " minutes");
                System.out.println("    Description: " + movie.getDescription());
            }
        }
    }

    private static void displayAvailableShowtimes() {
        System.out.println("\n═══ AVAILABLE SHOWTIMES ═══");
        var showtimes = cinemaService.getAvailableShowtimes();
        if (showtimes.isEmpty()) {
            System.out.println("No showtimes available.");
        } else {
            for (Showtime showtime : showtimes) {
                System.out.println("\n[" + showtime.getId() + "] " + showtime.getMovie().getTitle());
                System.out.println("    Theater: " + showtime.getTheater().getName());
                System.out.println("    Date/Time: " + showtime.getDateTime());
                System.out.println("    Price: $" + showtime.getPrice());
                System.out.println("    Available seats: " + showtime.getAvailableSeats());
            }
        }
    }

    private static void makeReservation() {
        System.out.println("\n═══ NEW RESERVATION ═══");

        System.out.print("Customer email: ");
        String email = scanner.nextLine();

        Customer customer = cinemaService.findCustomerByEmail(email);
        if (customer == null) {
            System.out.println("Customer not found. Please create an account first.");
            return;
        }

        displayAvailableShowtimes();
        System.out.print("\nShowtime ID: ");
        int showtimeId = readInteger();

        Showtime showtime = cinemaService.getAllShowtimes().stream()
                .filter(s -> s.getId() == showtimeId)
                .findFirst()
                .orElse(null);

        if (showtime == null) {
            System.out.println("Showtime not found.");
            return;
        }

        System.out.print("Number of seats: ");
        int numberOfSeats = readInteger();

        try {
            Reservation reservation = cinemaService.createReservation(customer, showtime, numberOfSeats);
            System.out.println("\n✓ Reservation created successfully!");
            System.out.println(reservation);
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private static void displayCustomerReservations() {
        System.out.println("\n═══ MY RESERVATIONS ═══");
        System.out.print("Customer email: ");
        String email = scanner.nextLine();

        Customer customer = cinemaService.findCustomerByEmail(email);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        var reservations = cinemaService.getCustomerReservations(customer.getId());
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println("\n" + reservation);
            }
        }
    }

    private static void cancelReservation() {
        System.out.println("\n═══ CANCEL RESERVATION ═══");
        System.out.print("Reservation ID: ");
        int reservationId = readInteger();

        try {
            cinemaService.cancelReservation(reservationId);
            System.out.println("✓ Reservation cancelled successfully.");
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private static void addCustomer() {
        System.out.println("\n═══ CREATE CUSTOMER ACCOUNT ═══");
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();

        Customer customer = new Customer(0, lastName, firstName, email, phone, address);
        customer = cinemaService.addCustomer(customer);

        System.out.println("\n✓ Customer created successfully!");
        System.out.println(customer);
    }

    private static void displayStatistics() {
        System.out.println("\n═══ STATISTICS ═══");
        System.out.println("Number of movies: " + cinemaService.getAllMovies().size());
        System.out.println("Number of theaters: " + cinemaService.getAllTheaters().size());
        System.out.println("Number of showtimes: " + cinemaService.getAllShowtimes().size());
        System.out.println("Number of customers: " + cinemaService.getAllCustomers().size());
        System.out.println("Active reservations: " + cinemaService.countActiveReservations());
        System.out.println("Total revenue: $" + cinemaService.calculateTotalRevenue());
    }

    private static void initializeData() {
        // Create theaters
        Theater theater1 = new Theater(0, "Premium Theater", 10, 15);
        Theater theater2 = new Theater(0, "Standard Theater", 12, 20);
        Theater theater3 = new Theater(0, "VIP Theater", 6, 10);
        cinemaService.addTheater(theater1);
        cinemaService.addTheater(theater2);
        cinemaService.addTheater(theater3);

        // Create movies
        Movies movie1 = new Movies(0, "Inception", "Science Fiction", 148,
                "Christopher Nolan", LocalDate.of(2010, 7, 16),
                "A thief who enters dreams to steal secrets");
        Movies movie2 = new Movies(0, "The Godfather", "Drama", 175,
                "Francis Ford Coppola", LocalDate.of(1972, 3, 24),
                "The story of an Italian-American mafia family");
        Movies movie3 = new Movies(0, "Avatar", "Action", 162,
                "James Cameron", LocalDate.of(2009, 12, 18),
                "A paraplegic marine on an alien planet");
        cinemaService.addMovie(movie1);
        cinemaService.addMovie(movie2);
        cinemaService.addMovie(movie3);

        // Create showtimes
        LocalDateTime now = LocalDateTime.now();
        Showtime showtime1 = new Showtime(0, movie1, theater1,
                now.plusDays(1).withHour(14).withMinute(0), 12.50);

        Showtime showtime2 = new Showtime(0, movie1, theater1,
                now.plusDays(1).withHour(20).withMinute(30), 15.00);

        Showtime showtime3 = new Showtime(0, movie2, theater2,
                now.plusDays(2).withHour(18).withMinute(0), 11.00);

        Showtime showtime4 = new Showtime(0, movie3, theater3,
                now.plusDays(2).withHour(21).withMinute(0), 18.50);
        cinemaService.addShowtime(showtime1);
        cinemaService.addShowtime(showtime2);
        cinemaService.addShowtime(showtime3);
        cinemaService.addShowtime(showtime4);

        // Create test customer
        Customer customer1 = new Customer(0, "Smith", "John",
                "john.smith@email.com", "555-1234", "New York");
        cinemaService.addCustomer(customer1);
    }

    private static int readInteger() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}

