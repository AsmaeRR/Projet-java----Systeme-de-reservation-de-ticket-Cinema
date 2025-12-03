package cinema;

import java.time.LocalDate;

public class Movies {
    private int id;
    private String title;
    private String genre;
    private int duration; // in minutes
    private String director;
    private LocalDate releaseDate;
    private String description;

    public Movies(int id, String title, String genre, int duration, String director, LocalDate releaseDate, String description) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.director = director;
        this.releaseDate = releaseDate;
        this.description = description;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration + " min" +
                ", director='" + director + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}