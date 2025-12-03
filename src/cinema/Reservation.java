package cinema;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private int id;
    private Customer customer;
    private Showtime showtime;
    private int numberOfSeats;
    private double totalAmount;
    private LocalDateTime reservationDate;
    private ReservationStatus status;

    public enum ReservationStatus {
        CONFIRMED, CANCELLED, PENDING
    }

    public Reservation(int id, Customer customer, Showtime showtime, int numberOfSeats) {
        this.id = id;
        this.customer = customer;
        this.showtime = showtime;
        this.numberOfSeats = numberOfSeats;
        this.totalAmount = numberOfSeats * showtime.getPrice();
        this.reservationDate = LocalDateTime.now();
        this.status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status == ReservationStatus.CONFIRMED) {
            this.status = ReservationStatus.CANCELLED;
            showtime.cancelSeats(numberOfSeats);
        }
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Showtime getShowtime() { return showtime; }
    public void setShowtime(Showtime showtime) { this.showtime = showtime; }

    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        this.totalAmount = numberOfSeats * showtime.getPrice();
    }

    public double getTotalAmount() { return totalAmount; }

    public LocalDateTime getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDateTime reservationDate) { this.reservationDate = reservationDate; }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        return "Reservation{" +
                "id=" + id +
                ", customer=" + customer.getFullName() +
                ", movie='" + showtime.getMovie().getTitle() + '\'' +
                ", showtime=" + showtime.getDateTime().format(formatter) +
                ", seats=" + numberOfSeats +
                ", amount=$" + totalAmount +
                ", status=" + status +
                '}';
    }
}
