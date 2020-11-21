package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ImageNoteDao {

    @Insert
    void insert(ImageNote imageNote);

    @Update
    void update(ImageNote imageNote);

    @Delete
    void delete(ImageNote imageNote);

    @Query("DELETE FROM imageNote_table")
    void deleteAllImageNotes();

    @Query("SELECT * FROM imageNote_table ORDER BY priority DESC")
    LiveData<List<ImageNote>> getAllImageNotes();

}
