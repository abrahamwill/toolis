package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "imageNote_table")
public class ImageNote {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String image;

    private int priority;

    public ImageNote(String title, String image, int priority) {
        this.title = title;
        this.image = image;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }


    public int getPriority() {
        return priority;
    }
}
