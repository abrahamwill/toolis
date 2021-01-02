package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import android.Manifest;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Locale;

import static id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis.App.CHANNEL_1_ID;



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
    private Button countWordsButton;
    private String picturePath;
    private static int RESULT_LOAD_IMAGE = 1;

    //notif
    private NotificationManagerCompat notificationManager;


    public void sendOnChannel1() {
        Log.e("notif", "masuk channel 1");
        Log.e("channel id", CHANNEL_1_ID);
        String title = "Sastra telah selesai dibacakan!";
        String message = "Silahkan masuk ke aplikasi dan pilih sastra lain untuk mulai membaca";
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_menu_book_16)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
        Log.e("notif", "selesai di notify");
    }
    //notif

    static {
        System.loadLibrary("cpp_code");
    }

    public native String simplefun(String text);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sastra);

        notificationManager = NotificationManagerCompat.from(this);

        coverImage = findViewById(R.id.edit_cover_sastra);
        editTextTitle = findViewById(R.id.edit_text_title_sastra);
        editTextSummary = findViewById(R.id.edit_text_summary_sastra);
        editTextDescription = findViewById(R.id.edit_text_description_sastra);
        numberPickerPriority = findViewById(R.id.number_picker_priority_sastra);
        spinnerCategory = (Spinner) findViewById(R.id.spinner_category_sastra);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch_sastra);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed_sastra);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        countWordsButton = findViewById(R.id.button_count_words);
        countWordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView word_count= findViewById(R.id.word_count);
                word_count.setText(simplefun(editTextDescription.getText().toString()));
            }
        });

        coverImage.setOnClickListener(arg0 -> {
            try {
                if (ActivityCompat.checkSelfPermission(AddEditSastraActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestStoragePermission();
//                    ActivityCompat.requestPermissions(AddEditSastraActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
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
            setTitle("Edit Sastra");
            coverImage.setImageBitmap(BitmapFactory.decodeFile(intent.getStringExtra(EXTRA_COVER)));
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextSummary.setText(intent.getStringExtra(EXTRA_SUMMARY));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            String compareValue = intent.getStringExtra(EXTRA_CATEGORY);
            spinnerCategory.setSelection(getIndex(spinnerCategory, compareValue));

            //private method of your class
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
                    mTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onDone(String utteranceId) {
                            Log.e("TTS", "masuk ondone");
                            sendOnChannel1();
                            Log.e("SastraActivity", "TTS finished");
                        }

                        @Override
                        public void onError(String utteranceId) {
                        }

                        @Override
                        public void onStart(String utteranceId) {
                        }
                    });
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });


        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showPermissionDialog();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RESULT_LOAD_IMAGE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                showPermissionDialog();
            }
        }
    }

    private void showPermissionDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Permission needed")
                .setMessage("Kami membutuhkan permission ini untuk mengakses gambar yang akan digunakan sebagai cover sastra")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(AddEditSastraActivity.this,
                                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
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
        String text = editTextDescription.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;
        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
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