package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    public static final int ADD_SASTRA_REQUEST = 3;
    public static final int EDIT_SASTRA_REQUEST = 4;
    private NoteViewModel noteViewModel;
    private SastraViewModel sastraViewModel;

    private Boolean isFABOpen = false;
    FloatingActionButton fab, addNoteFab, addSastraFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //expandable fab
        fab = findViewById(R.id.button_add);
        addNoteFab = findViewById(R.id.button_add_note);
        addSastraFab = findViewById(R.id.button_add_sastra);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });
        //end of expandable fab

        addNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        addSastraFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditSastraActivity.class);
                startActivityForResult(intent, ADD_SASTRA_REQUEST);
            }
        });

        RecyclerView sastraRecyclerView = findViewById(R.id.sastra_recycler_view);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            sastraRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }else{
            sastraRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }
        //sastra


        sastraRecyclerView.setHasFixedSize(true);

        final SastraAdapter sastraAdapter = new SastraAdapter();
        sastraRecyclerView.setAdapter(sastraAdapter);

        sastraViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(SastraViewModel.class);
        sastraViewModel.getAllSastra().observe(this, new Observer<List<Sastra>>() {
            @Override
            public void onChanged(List<Sastra> sastras) {
//                adapter.setNotes(notes); deprecated
                sastraAdapter.submitList(sastras); // punya listAdapter
            }
        });


        //note
        RecyclerView noteRecyclerView = findViewById(R.id.recycler_view);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setHasFixedSize(true);

        final NoteAdapter noteAdapter = new NoteAdapter();
        noteRecyclerView.setAdapter(noteAdapter);

        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
//                adapter.setNotes(notes); deprecated
                noteAdapter.submitList(notes); // punya listAdapter
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(noteRecyclerView);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.UP) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                sastraViewModel.delete(sastraAdapter.getSastraAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Sastra Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(sastraRecyclerView);

        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        sastraAdapter.setOnItemClickListener(new SastraAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Sastra sastra) {
                Intent intent = new Intent(MainActivity.this, AddEditSastraActivity.class);
                intent.putExtra(AddEditSastraActivity.EXTRA_ID, sastra.getId());
                intent.putExtra(AddEditSastraActivity.EXTRA_TITLE, sastra.getTitle());
                intent.putExtra(AddEditSastraActivity.EXTRA_COVER, sastra.getCover());
                intent.putExtra(AddEditSastraActivity.EXTRA_SUMMARY, sastra.getSummary());
                intent.putExtra(AddEditSastraActivity.EXTRA_DESCRIPTION, sastra.getDescription());
                intent.putExtra(AddEditSastraActivity.EXTRA_PRIORITY, sastra.getPriority());
                intent.putExtra(AddEditSastraActivity.EXTRA_CATEGORY, sastra.getCategory());
                startActivityForResult(intent, EDIT_SASTRA_REQUEST);
            }
        });

        ConnectivityBroadcastReceiver connReceiver = new ConnectivityBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        registerReceiver(connReceiver, intentFilter);

    }

    private void showFABMenu(){
        isFABOpen=true;
        addNoteFab.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        addSastraFab.animate().translationY(-getResources().getDimension(R.dimen.standard_125));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        addNoteFab.animate().translationY(0);
        addSastraFab.animate().translationY(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra((AddEditNoteActivity.EXTRA_DESCRIPTION));
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Error, can't update note", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra((AddEditNoteActivity.EXTRA_DESCRIPTION));
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title, description, priority);
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else if (requestCode == ADD_SASTRA_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditSastraActivity.EXTRA_TITLE);
            String description = data.getStringExtra((AddEditSastraActivity.EXTRA_DESCRIPTION));
            int priority = data.getIntExtra(AddEditSastraActivity.EXTRA_PRIORITY, 1);
            String summary = data.getStringExtra((AddEditSastraActivity.EXTRA_SUMMARY));
            String cover = data.getStringExtra((AddEditSastraActivity.EXTRA_COVER));
            String category = data.getStringExtra((AddEditSastraActivity.EXTRA_CATEGORY));

            Sastra sastra = new Sastra(title,description,priority,cover,summary,category);
            sastraViewModel.insert(sastra);

            Toast.makeText(this, "Sastra saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_SASTRA_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditSastraActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Error, can't update sastra", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditSastraActivity.EXTRA_TITLE);
            String description = data.getStringExtra((AddEditSastraActivity.EXTRA_DESCRIPTION));
            int priority = data.getIntExtra(AddEditSastraActivity.EXTRA_PRIORITY, 1);
            String summary = data.getStringExtra((AddEditSastraActivity.EXTRA_SUMMARY));
            String cover = data.getStringExtra((AddEditSastraActivity.EXTRA_COVER));
            String category = data.getStringExtra((AddEditSastraActivity.EXTRA_CATEGORY));

            Sastra sastra = new Sastra(title, description, priority,cover,summary,category);
            sastra.setId(id);
            sastraViewModel.update(sastra);

            Toast.makeText(this, "Sastra saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sastra Unsaved", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete_all_sastra:
                sastraViewModel.deleteAllSastra();
                Toast.makeText(this, "All sastra deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}