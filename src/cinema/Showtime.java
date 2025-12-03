package cinema;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Showtime {
    private int id;
    private Movies movie;
    private Theater theater;
    private LocalDateTime dateTime;
    private double price;
    private int availableSeats;

    public Showtime(int id, Movies movie, Theater theater, LocalDateTime dateTime, double price) {
        this.id = id;
        this.movie = movie;
        this.theater = theater;
        this.dateTime = dateTime;
        this.price = price;
        this.availableSeats = theater.getTotalCapacity();
    }

    public boolean reserveSeats(int numberOfSeats) {
        if (numberOfSeats <= availableSeats) {
            availableSeats -= numberOfSeats;
            return true;
        }
        return false;
    }

    public void cancelSeats(int numberOfSeats) {
        availableSeats += numberOfSeats;
        if (availableSeats > theater.getTotalCapacity()) {
            availableSeats = theater.getTotalCapacity();
        }
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Movies getMovie() { return movie; }
    public void setMovie(Movies movie) { this.movie = movie; }

    public Theater getTheater() { return theater; }
    public void setTheater(Theater theater) { this.theater = theater; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        return "Showtime{" +
                "id=" + id +
                ", movie='" + movie.getTitle() + '\'' +
                ", theater=" + theater.getName() +
                ", dateTime=" + dateTime.format(formatter) +
                ", price=$" + price +
                ", available seats=" + availableSeats + "/" + theater.getTotalCapacity() +
            '}';
}
}
