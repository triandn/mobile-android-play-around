package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.myapplication.databinding.ActivityDetailsBinding;
import com.example.myapplication.databinding.ActivityMainBinding;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    private ArrayAdapter<String> arrayAdapter;
    public static final String EXTRA_DATA = "EXTRA_DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        //GET DATA
        Intent intent = getIntent();
        if (intent != null) {
            String data = intent.getStringExtra("number");
            binding.editText.setText(data);
        }
        binding.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent data = new Intent();

                // Truyền data vào intent
                data.putExtra(EXTRA_DATA, binding.editText.getText().toString());

                // Đặt resultCode là Activity.RESULT_OK to
                // thể hiện đã thành công và có chứa kết quả trả về
                setResult(Activity.RESULT_OK, data);

                // gọi hàm finish() để đóng Activity hiện tại và trở về MainActivity.
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {

        // đặt resultCode là Activity.RESULT_CANCELED thể hiện
        // đã thất bại khi người dùng click vào nút Back.
        // Khi này sẽ không trả về data.
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}