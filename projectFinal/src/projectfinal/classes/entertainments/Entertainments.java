// classes.entertainments package
package projectfinal.classes.entertainments;

// Entertainments class
public class Entertainments {
    
    // Set class variables
    private Integer id;
    private String title;
    private String genre; 
    private Integer year;
    private String status;

    // Constructor
    public Entertainments(Integer id, String title, String genre, Integer year, String status) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.status = status;
    }

    // Id getter
    public Integer getId() {
        return id;
    }
    
    // Title getter
    public String getTitle() {
        return title;
    }

    // Genre getter
    public String getGenre() {
        return genre;
    }

    // Year getter
    public Integer getYear() {
        return year;
    }

    // Status getter
    public String getStatus() {
        return status;
    }
}