package com.example.contentarticle.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contentarticle.R;
import com.example.contentarticle.helper.DatabaseClient;
import com.example.contentarticle.model.room.Content;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    String status;
    ImageView back;
    TextView gender;
    RadioGroup radiogender;
    Button save;
    RadioButton male, female;
    EditText judul, content, phone, tanggal;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        radiogender = (RadioGroup) findViewById(R.id.radiogender);
        gender = (TextView) findViewById(R.id.gender);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        judul = (EditText) findViewById(R.id.judul);
        content = (EditText) findViewById(R.id.content);
        phone = (EditText) findViewById(R.id.phone);
        tanggal = (EditText) findViewById(R.id.tanggal);
        back = (ImageView) findViewById(R.id.back);
        save = (Button) findViewById(R.id.save);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddActivity.this, HomeActivity.class));
                // Destroy Aplikasi
                finish();
                // bikin TOAST
            }
        });

        status = "";

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judul.getText().toString().equals("") || content.getText().toString().equals("") ||
                tanggal.getText().toString().equals("") || phone.getText().toString().equals("")
                 || status.equals("")) {
                    Toast.makeText(AddActivity.this, "Lengkapi Data", Toast.LENGTH_SHORT).show();
                } else {
                    save();
                    Toast.makeText(AddActivity.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        radiogender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        status = "Male";
                        break;
                    case R.id.female:
                        status = "Female";
                        break;
                }
            }
        });
    }

    // Inisialisaasi Listener Ketika Klik OK
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    // Method untuk update format dari Date Picker ke EditText
    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tanggal.setText(sdf.format(myCalendar.getTime()));
    }

    private void save() {
        final String mJudul = judul.getText().toString();
        final String mContent = content.getText().toString();
        final String mGender = status;
        final String mPhone = phone.getText().toString();
        final String mTanggal = tanggal.getText().toString();


            // Ini untuk save database
            class saveContent extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {
                    Content content = new Content();
                    content.setJudul(mJudul);
                    content.setMycontent(mContent);
                    content.setCategory(mGender);
                    content.setTanggal(mTanggal);
                    content.setPhone(mPhone);

                    DatabaseClient.getInstance(
                            getApplicationContext())
                            .getAppDatabase()
                            .contentDao()
                            .insert(content);

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    Toast.makeText(AddActivity.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddActivity.this, HomeActivity.class));
                    finish();
                }

            }

            saveContent saveContent = new saveContent();
            saveContent.execute();
    }

}
