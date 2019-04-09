package com.example.contentarticle.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.contentarticle.R;
import com.example.contentarticle.model.room.Content;

public class DetailContentActivity extends AppCompatActivity {

    TextView nama, contents, gender, tanggal, phone;
    ImageView imagephone, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_content);

        nama = (TextView) findViewById(R.id.viewnama);
        contents = (TextView) findViewById(R.id.viewcontent);
        gender = (TextView) findViewById(R.id.viewgender);
        tanggal = (TextView) findViewById(R.id.viewtanggal);
        phone = (TextView) findViewById(R.id.viewphone);
        imagephone = (ImageView) findViewById(R.id.imagephone);
        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailContentActivity.this, HomeActivity.class));
                finish();
            }
        });

        final Content content = (Content) getIntent().getSerializableExtra("contentData");
        ContentDetail(content);

        // Permission untuk phone call dengan versi android => M
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.CALL_PHONE},
                        110);
            }
            return;
        }
    }
    private void ContentDetail(final Content content) {
        nama.setText(content.getJudul());
        contents.setText(content.getMycontent());
        gender.setText(content.getCategory());
        tanggal.setText(content.getTanggal());
        phone.setText(content.getPhone());

        // Click no ntepl langsung menghubungi
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + content.getPhone()));

                startActivity(callIntent);
            }
        });
    }

}
