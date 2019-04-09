package com.example.contentarticle.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.contentarticle.adapter.ContentAdapter;
import com.example.contentarticle.helper.DatabaseClient;
import com.example.contentarticle.model.room.Content;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {

    String status;
    ImageView back;
    TextView gender;
    RadioGroup radiogender;
    Button save;
    RadioButton male, female;
    EditText judul, contents, phone, tanggal;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        back = (ImageView) findViewById(R.id.backupdate);
        gender = (TextView) findViewById(R.id.genderupdate);
        radiogender = (RadioGroup) findViewById(R.id.radiogenderupdate);
        save = (Button) findViewById(R.id.saveupdate);
        male = (RadioButton) findViewById(R.id.maleupdate);
        female = (RadioButton) findViewById(R.id.femaleupdate);
        judul = (EditText) findViewById(R.id.judulupdate);
        contents = (EditText) findViewById(R.id.contentupdate);
        phone = (EditText) findViewById(R.id.phoneupdate);
        tanggal = (EditText) findViewById(R.id.tanggalupdate);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateActivity.this, HomeActivity.class));
            }
        });

        status = "";

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        final Content updatecont = (Content) getIntent().getSerializableExtra("contentEdit");
//        ContentDetail(content);

        judul.setText(updatecont.getJudul());
        contents.setText(updatecont.getMycontent());
        gender.setText(updatecont.getCategory());
        tanggal.setText(updatecont.getTanggal());
        phone.setText(updatecont.getPhone());
        status = updatecont.getCategory();

        switch (status) {
            case (String) "Male":
                male.setChecked(true);
                break;
            case (String) "Female":
                female.setChecked(true);
                break;
        }

        radiogender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.maleupdate:
                        status = "Male";
                        break;
                    case R.id.femaleupdate:
                        status = "Female";
                        break;
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(updatecont);
            }
        });

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tanggal.setText(sdf.format(myCalendar.getTime()));
    }



    private void ContentDetail(final Content content) {
        judul.setText(content.getJudul());
        contents.setText(content.getMycontent());
        gender.setText(content.getCategory());
        tanggal.setText(content.getTanggal());
        phone.setText(content.getPhone());
        status = content.getCategory();

    }

    private void update(final Content content) {
        final String mJudul = judul.getText().toString();
        final String mContent = contents.getText().toString();
        final String mGender = status;
        final String mPhone = phone.getText().toString();
        final String mTanggal = tanggal.getText().toString();


        // Ini untuk save database
        class updateContent extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                content.setJudul(mJudul);
                content.setMycontent(mContent);
                content.setCategory(mGender);
                content.setTanggal(mTanggal);
                content.setPhone(mPhone);

                DatabaseClient.getInstance(
                        getApplicationContext())
                        .getAppDatabase()
                        .contentDao()
                        .update(content);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Toast.makeText(UpdateActivity.this, "Data Berhasil Dirubah", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateActivity.this, HomeActivity.class));
                finish();
            }

        }

        updateContent updateContent = new updateContent();
        updateContent.execute();
    }
}
