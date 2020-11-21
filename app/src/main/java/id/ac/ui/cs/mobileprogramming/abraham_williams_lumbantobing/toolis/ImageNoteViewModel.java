package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ImageNoteViewModel extends AndroidViewModel {

    private ImageNoteRepository repository;
    private LiveData<List<ImageNote>> allImageNotes;

    public ImageNoteViewModel(@NonNull Application application) {
        super(application);
        repository = new ImageNoteRepository(application);
        allImageNotes = repository.getAllImageNotes();
    }

    public void insert(ImageNote imageNote){
        repository.insertImageNote(imageNote);
    }

    public void delete(ImageNote imageNote){
        repository.deleteImageNote(imageNote);
    }

    public void update(ImageNote imageNote){
        repository.updateImageNote(imageNote);
    }

    public void deleteAllImageNotes(){
        repository.deleteAllImageNotes();
    }

    public LiveData<List<ImageNote>> getAllImageNotes() {
        return allImageNotes;
    }
}
