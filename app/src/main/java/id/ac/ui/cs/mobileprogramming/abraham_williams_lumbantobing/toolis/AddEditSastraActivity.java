package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Locale;

public class AddEditSastraActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis.EXTRA_TITLE";
    public static final String EXTRA_SUMMARY =
            "id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis.EXTRA_SUMMARY";
    public static final String EXTRA_PRIORITY =
            "id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis.EXTRA_PRIORITY";
    public static final String EXTRA_COVER =
            "id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis.EXTRA_COVER";
    public static final String EXTRA_CATEGORY =
            "id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis.EXTRA_CATEGORY";
    public static final String EXTRA_DESCRIPTION =
            "id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis.EXTRA_DESCRIPTION";

    private ImageView coverImage;
    private EditText editTextTitle;
    private EditText editTextSummary;
    private NumberPicker numberPickerPriority;
    private Spinner spinnerCategory;
    private EditText editTextDescription;

    private TextToSpeech mTTS;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;

    private String picturePath;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sastra);

        coverImage = findViewById(R.id.edit_cover_sastra);
        editTextTitle = findViewById(R.id.edit_text_title_sastra);
        editTextSummary = findViewById(R.id.edit_text_summary_sastra);
        editTextDescription = findViewById(R.id.edit_text_description_sastra);
        numberPickerPriority = findViewById(R.id.number_picker_priority_sastra);
        spinnerCategory = (Spinner) findViewById(R.id.spinner_category_sastra);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        coverImage.setOnClickListener(arg0 -> {
            try {
                if (ActivityCompat.checkSelfPermission(AddEditSastraActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddEditSastraActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
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

        //edit sastra
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit SASTRA");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextSummary.setText(intent.getStringExtra(EXTRA_SUMMARY));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else{
            setTitle("Add Sastra");
        }

        mButtonSpeak = findViewById(R.id.button_speak_sastra);
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.e("TTS","status" + Integer.toString(status));
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);
        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void saveSastra() {
        String title = editTextTitle.getText().toString();
        String summary = editTextSummary.getText().toString();
        String description = editTextDescription.getText().toString();
        String category = spinnerCategory.getSelectedItem().toString();

        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || summary.trim().isEmpty()) { //supaya ga bisa empty, coba remove empty spaces di end dan beginning
            Toast.makeText(this, "Please insert both title and summary", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_SUMMARY, summary);
        data.putExtra(EXTRA_PRIORITY, priority);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_COVER, picturePath);
        data.putExtra(EXTRA_CATEGORY, category);

        //edit sastra
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
        menuInflater.inflate(R.menu.add_sastra_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_sastra:
                saveSastra();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void speak() {
        String text = editTextSummary.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;
        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
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

            ImageView imageView = (ImageView) findViewById(R.id.edit_cover_sastra);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            Log.e("img", picturePath);
            Toast.makeText(this, "You have pick an image",
                    Toast.LENGTH_LONG).show();

        }
    }
}