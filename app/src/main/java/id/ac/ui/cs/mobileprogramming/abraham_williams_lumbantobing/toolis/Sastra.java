package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import androidx.room.Entity;

@Entity(tableName = "sastra_table")
public class Sastra extends Note {

    private String cover;
    private String summary;
    private String category;

    public Sastra(String title, String description, int priority, String cover, String summary, String category) {
        super(title, description, priority);
        this.cover = cover;
        this.summary = summary;
        this.category = category;
    }

    public String getCover() {
        return cover;
    }

    public String getSummary() {
        return summary;
    }

    public String getCategory() {
        return category;
    }
}
