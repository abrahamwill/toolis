package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SastraDao {

    @Insert
    void insert(Sastra sastra);

    @Update
    void update (Sastra sastra);

    @Delete
    void delete (Sastra sastra);

    @Query("DELETE FROM sastra_table")
    void deleteAllSastra();

    @Query("SELECT * FROM sastra_table ORDER BY priority DESC")
    LiveData<List<Sastra>> getAllSastra();

}
