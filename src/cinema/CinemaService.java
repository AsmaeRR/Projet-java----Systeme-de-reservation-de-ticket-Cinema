package cinema;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
    public class CinemaService {

        private List<Movie> movies;
        private List<Theater> theaters;
        private List<Showtime> showtimes;
        private List<Customer> customers;
        private List<Reservation> reservations;

        private int nextMovieId = 1;
        private int nextTheaterId = 1;
        private int nextShowtimeId = 1;
        private int nextCustomerId = 1;
        private int nextReservationId = 1;

        public CinemaService() {
            this.movies = new ArrayList<>();
            this.theaters = new ArrayList<>();
            this.showtimes = new ArrayList<>();
            this.customers = new ArrayList<>();
            this.reservations = new ArrayList<>();
        }

        // Movie Management
        public Movie addMovie(Movie movie) {
            movie.setId(nextMovieId++);
            movies.add(movie);
            return movie;
        }

        public List<Movie> getAllMovies() {
            return new ArrayList<>(movies);
        }

        public Movie findMovieById(int id) {
            return movies.stream()
                    .filter(m -> m.getId() == id)
                    .findFirst()
                    .orElse(null);
        }

        public List<Movie> findMoviesByGenre(String genre) {
            return movies.stream()
                    .filter(m -> m.getGenre().equalsIgnoreCase(genre))
                    .collect(Collectors.toList());
        }

        // Theater Management
        public Theater addTheater(Theater theater) {
            theater.setId(nextTheaterId++);
            theaters.add(theater);
            return theater;
        }

        public List<Theater> getAllTheaters() {
            return new ArrayList<>(theaters);
        }

        // Showtime Management
        public Showtime addShowtime(Showtime showtime) {
            showtime.setId(nextShowtimeId++);
            showtimes.add(showtime);
            return showtime;
        }

        public List<Showtime> getAllShowtimes() {
            return new ArrayList<>(showtimes);
        }

        public List<Showtime> getShowtimesByMovie(int movieId) {
            return showtimes.stream()
                    .filter(s -> s.getMovie().getId() == movieId)
                    .collect(Collectors.toList());
        }

        public List<Showtime> getAvailableShowtimes() {
            return showtimes.stream()
                    .filter(s -> s.getAvailableSeats() > 0)
                    .filter(s -> s.getDateTime().isAfter(LocalDateTime.now()))
                    .sorted(Comparator.comparing(Showtime::getDateTime))
                    .collect(Collectors.toList());
        }

        // Customer Management
        public Customer addCustomer(Customer customer) {
            customer.setId(nextCustomerId++);
            customers.add(customer);
            return customer;
        }

        public List<Customer> getAllCustomers() {
            return new ArrayList<>(customers);
        }

        public Customer findCustomerByEmail(String email) {
            return customers.stream()
                    .filter(c -> c.getEmail().equalsIgnoreCase(email))
                    .findFirst()
                    .orElse(null);
        }

        // Reservation Management
        public Reservation createReservation(Customer customer, Showtime showtime, int numberOfSeats) throws Exception {
            if (showtime.getAvailableSeats() < numberOfSeats) {
                throw new Exception("Not enough seats available");
            }

            if (showtime.getDateTime().isBefore(LocalDateTime.now())) {
                throw new Exception("Cannot book a past showtime");
            }

            if (!showtime.reserveSeats(numberOfSeats)) {
                throw new Exception("Error reserving seats");
            }

            Reservation reservation = new Reservation(nextReservationId++, customer, showtime, numberOfSeats);
            reservations.add(reservation);
            return reservation;
        }

        public void cancelReservation(int reservationId) throws Exception {
            Reservation reservation = reservations.stream()
                    .filter(r -> r.getId() == reservationId)
                    .findFirst()
                    .orElseThrow(() -> new Exception("Reservation not found"));

            reservation.cancel();
        }

        public List<Reservation> getCustomerReservations(int customerId) {
            return reservations.stream()
                    .filter(r -> r.getCustomer().getId() == customerId)
                    .collect(Collectors.toList());
        }

        public List<Reservation> getAllReservations() {
            return new ArrayList<>(reservations);
        }

        // Statistics
        public double calculateTotalRevenue() {
            return reservations.stream()
                    .filter(r -> r.getStatus() == Reservation.ReservationStatus.CONFIRMED)
                    .mapToDouble(Reservation::getTotalAmount)
                    .sum();
        }

        public int countActiveReservations() {
            return (int) reservations.stream()
                    .filter(r -> r.getStatus() == Reservation.ReservationStatus.CONFIRMED)
                    .count();
        }
    }


