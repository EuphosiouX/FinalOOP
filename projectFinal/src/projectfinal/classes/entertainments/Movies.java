// classes.entertainments package
package projectfinal.classes.entertainments;

// Movies class extends Entertainments
public class Movies extends Entertainments{
    
    // Set class variables
    private String director;
    private String duration;

    // Constructor
    public Movies(Integer id, String title, String genre, Integer year, String status, String director, String duration) {
        super(id, title, genre, year, status);
        this.director = director;
        this.duration = duration;
    }

    // Director getter
    public String getDirector() {
        return director;
    }

    // Duration getter
    public String getDuration() {
        return duration;
    }
}