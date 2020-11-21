package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ImageNoteRepository {

    private ImageNoteDao imageNoteDao;
    private LiveData<List<ImageNote>> allImageNotes;

    public ImageNoteRepository(Application application){
        ImageNoteDatabase database = ImageNoteDatabase.getInstance(application);
        imageNoteDao = database.imageNoteDao();
        allImageNotes = imageNoteDao.getAllImageNotes();
    }

    public void insertImageNote(ImageNote imageNote) {
        new InsertImageNoteAsyncTask(imageNoteDao).execute(imageNote);
    }

    public void updateImageNote(ImageNote imageNote) {
        new UpdateImageNoteAsyncTask(imageNoteDao).execute(imageNote);
    }

    public void deleteImageNote(ImageNote imageNote) {
        new DeleteImageNoteAsyncTask(imageNoteDao).execute(imageNote);
    }

    public void deleteAllImageNotes() {
        new DeleteAllImageNotesAsyncTask(imageNoteDao).execute();
    }

    public LiveData<List<ImageNote>> getAllImageNotes() {
        return allImageNotes;
    }

    private static class InsertImageNoteAsyncTask extends AsyncTask<ImageNote, Void, Void>{
        private ImageNoteDao imageNoteDao;

        private InsertImageNoteAsyncTask(ImageNoteDao imageNoteDao){
            this.imageNoteDao = imageNoteDao;
        }

        @Override
        protected Void doInBackground(ImageNote... imageNote) {
            imageNoteDao.insert(imageNote[0]);
            return null;
        }
    }

    private static class UpdateImageNoteAsyncTask extends AsyncTask<ImageNote, Void, Void>{
        private ImageNoteDao imageNoteDao;

        private UpdateImageNoteAsyncTask(ImageNoteDao imageNoteDao){
            this.imageNoteDao = imageNoteDao;
        }

        @Override
        protected Void doInBackground(ImageNote... imageNote) {
            imageNoteDao.update(imageNote[0]);
            return null;
        }
    }

    private static class DeleteImageNoteAsyncTask extends AsyncTask<ImageNote, Void, Void>{
        private ImageNoteDao imageNoteDao;

        private DeleteImageNoteAsyncTask(ImageNoteDao imageNoteDao){
            this.imageNoteDao = imageNoteDao;
        }

        @Override
        protected Void doInBackground(ImageNote... imageNote) {
            imageNoteDao.delete(imageNote[0]);
            return null;
        }
    }

    private static class DeleteAllImageNotesAsyncTask extends AsyncTask<ImageNote, Void, Void>{
        private ImageNoteDao imageNoteDao;

        private DeleteAllImageNotesAsyncTask(ImageNoteDao imageNoteDao){
            this.imageNoteDao = imageNoteDao;
        }

        @Override
        protected Void doInBackground(ImageNote... imageNote) {
            imageNoteDao.deleteAllImageNotes();
            return null;
        }
    }
}
