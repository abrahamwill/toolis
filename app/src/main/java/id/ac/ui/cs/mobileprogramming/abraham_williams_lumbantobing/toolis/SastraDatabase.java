package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Sastra.class}, version = 1,  exportSchema = false)
public abstract class SastraDatabase extends RoomDatabase {

    private static SastraDatabase instance;

    public abstract SastraDao sastraDao();

    public static synchronized SastraDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SastraDatabase.class, "sastra_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private SastraDao sastraDao;

        private PopulateDbAsyncTask(SastraDatabase db){
            sastraDao = db.sastraDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
