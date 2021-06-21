// classes.entertainments package
package projectfinal.classes.entertainments;

// TvSeries class extends Entertainments
public class TvSeries extends Entertainments{
    
    // Set class variables
    private Integer seasons;
    private Integer episodes;

    // Constructor
    public TvSeries(Integer id,  String title, String genre, Integer year, String status, Integer seasons, Integer episodes) {
        super(id, title, genre, year, status);
        this.seasons = seasons;
        this.episodes = episodes;
    }

    // Seasons getter
    public Integer getSeasons() {
        return seasons;
    }

    // Episodes getter
    public Integer getEpisodes() {
        return episodes;
    }
    
}
