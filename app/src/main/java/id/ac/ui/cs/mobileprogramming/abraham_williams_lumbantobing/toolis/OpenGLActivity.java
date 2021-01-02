package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class AddEditImageNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis.EXTRA_TITLE";
    public static final String EXTRA_IMAGE =
            "id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis.EXTRA_IMAGE";


    private EditText editTextTitle;
    private ImageView editImageImageNote;
    private Button buttonCheckImageLink;
    private EditText editImageLink;

    private String picturePath;
    private static int RESULT_LOAD_IMAGE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_note);


        editTextTitle = findViewById(R.id.edit_title_imageNote);
        editImageImageNote = findViewById(R.id.edit_image_imageNote);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        editImageImageNote.setOnClickListener(arg0 -> {
            try {
                if (ActivityCompat.checkSelfPermission(AddEditImageNoteActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddEditImageNoteActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
                } else {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        editImageLink = findViewById(R.id.edit_image_link);
        buttonCheckImageLink = findViewById(R.id.button_check_image_link);


        buttonCheckImageLink.setOnClickListener(v -> {
            ConnectivityBroadcastReceiver cbc = new ConnectivityBroadcastReceiver();
//            editImageImageNote.setImageBitmap(getBitmapFromURL(editImageLink.getText().toString()));
            Context context = getApplicationContext();
            if (!cbc.isNetworkAvailable(context)){
                Toast.makeText(context,"network not available, can't use photo", Toast.LENGTH_LONG).show();
            }
            String urlLink  = editImageLink.getText().toString();
            if (urlLink.isEmpty()){
                Toast.makeText(getApplicationContext(),"Please enter url", Toast.LENGTH_SHORT).show();
            }else {
                LoadImage loadImage = new LoadImage(editImageImageNote);
                loadImage.execute(urlLink);
            }
        });





        //edit imageNote
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle(R.string.edit_imageNote);
            editImageImageNote.setImageBitmap(BitmapFactory.decodeFile(intent.getStringExtra(EXTRA_IMAGE)));
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            //private method of your class
        } else{
            setTitle(R.string.add_imageNote);
        }

    }

    private void saveImageNote() {
        String title = editTextTitle.getText().toString();

        if (title.trim().isEmpty()) { //supaya ga bisa empty, coba remove empty spaces di end dan beginning
            Toast.makeText(this, "Please insert title", Toast.LENGTH_SHORT).show();
            return;
        }

        String urlLink = editImageLink.getText().toString();

        if (picturePath==null & urlLink!=null){
            picturePath = urlLink;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_IMAGE, picturePath);


        //edit imageNote
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_imagenote_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_imageNote:
                saveImageNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.edit_image_imageNote);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            Log.e("img", picturePath);
            Toast.makeText(this, "You have pick an image",
                    Toast.LENGTH_LONG).show();
        }
    }

}