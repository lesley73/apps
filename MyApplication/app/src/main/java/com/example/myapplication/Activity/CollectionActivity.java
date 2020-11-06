package com.example.myapplication.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.data.MyDataBaseHelper;

import org.devio.takephoto.app.TakePhoto;

public class CollectionActivity extends Activity {

    private MyDataBaseHelper dbHelper;
    private Button btn_choose_photo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        btn_choose_photo = findViewById(R.id.choose_photo);


        btn_choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = new Photo();
            }
        });
    }
}

