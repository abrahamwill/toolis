package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SastraRepository {

    private SastraDao sastraDao;
    private LiveData<List<Sastra>> allSastra;

    public SastraRepository(Application application){
        SastraDatabase database = SastraDatabase.getInstance(application);
        sastraDao = database.sastraDao();
        allSastra = sastraDao.getAllSastra();
    }

    public void insertSastra(Sastra sastra) {
        new InsertSastraAsyncTask(sastraDao).execute(sastra);
    }

    public void updateSastra(Sastra sastra) {
        new UpdateSastraAsyncTask(sastraDao).execute(sastra);
    }

    public void deleteSastra(Sastra sastra) {
        new DeleteSastraAsyncTask(sastraDao).execute(sastra);
    }

    public void deleteAllSastra() {
        new DeleteAllSastraAsyncTask(sastraDao).execute();
    }

    public LiveData<List<Sastra>> getAllSastra() {
        return allSastra;
    }

    private static class InsertSastraAsyncTask extends AsyncTask<Sastra, Void, Void>{
        private SastraDao sastraDao;

        private InsertSastraAsyncTask(SastraDao sastraDao){
            this.sastraDao = sastraDao;
        }

        @Override
        protected Void doInBackground(Sastra... sastra) {
            sastraDao.insert(sastra[0]);
            return null;
        }
    }

    private static class UpdateSastraAsyncTask extends AsyncTask<Sastra, Void, Void>{
        private SastraDao sastraDao;

        private UpdateSastraAsyncTask(SastraDao sastraDao){
            this.sastraDao = sastraDao;
        }

        @Override
        protected Void doInBackground(Sastra... sastra) {
            sastraDao.update(sastra[0]);
            return null;
        }
    }

    private static class DeleteSastraAsyncTask extends AsyncTask<Sastra, Void, Void>{
        private SastraDao sastraDao;

        private DeleteSastraAsyncTask(SastraDao sastraDao){
            this.sastraDao = sastraDao;
        }

        @Override
        protected Void doInBackground(Sastra... sastra) {
            sastraDao.delete(sastra[0]);
            return null;
        }
    }

    private static class DeleteAllSastraAsyncTask extends AsyncTask<Sastra, Void, Void>{
        private SastraDao sastraDao;

        private DeleteAllSastraAsyncTask(SastraDao sastraDao){
            this.sastraDao = sastraDao;
        }

        @Override
        protected Void doInBackground(Sastra... sastra) {
            sastraDao.deleteAllSastra();
            return null;
        }
    }
}
