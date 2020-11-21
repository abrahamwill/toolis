package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ImageNote.class}, version = 1,  exportSchema = false)
public abstract class ImageNoteDatabase extends RoomDatabase {

    private static ImageNoteDatabase instance;

    public abstract ImageNoteDao imageNoteDao();

    public static synchronized ImageNoteDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ImageNoteDatabase.class, "imageNote_database")
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
        private ImageNoteDao imageNoteDao;

        private PopulateDbAsyncTask(ImageNoteDatabase db){
            imageNoteDao = db.imageNoteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
          return null;
        }
    }
}
