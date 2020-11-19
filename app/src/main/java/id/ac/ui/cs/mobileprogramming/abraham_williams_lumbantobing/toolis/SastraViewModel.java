package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SastraViewModel extends AndroidViewModel {

    private SastraRepository repository;
    private LiveData<List<Sastra>> allSastra;

    public SastraViewModel(@NonNull Application application) {
        super(application);
        repository = new SastraRepository(application);
        allSastra = repository.getAllSastra();
    }

    public void insert(Sastra sastra){
        repository.insertSastra(sastra);
    }

    public void delete(Sastra sastra){
        repository.deleteSastra(sastra);
    }

    public void update(Sastra sastra){
        repository.updateSastra(sastra);
    }

    public void deleteAllSastra(){
        repository.deleteAllSastra();
    }

    public LiveData<List<Sastra>> getAllSastra() {
        return allSastra;
    }
}
