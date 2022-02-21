package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyViewModel model;
    private ListViewModel listViewModel;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;
    private static final int REQ_CODE = 123;
    private static final int REQUEST_CODE_EXAMPLE = 0x9345;
    public static int index;
    public static final String KEY_SHOW_WHAT = "show_what";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        // Tạo một Intent để start DetailActivity
        final Intent intent = new Intent(this, DetailsActivity.class);

        // Start DetailActivity với request code vừa được khai báo trước đó
//        startActivityForResult(intent, REQUEST_CODE_EXAMPLE);

        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 , arrayList);
        binding.listViewCount.setAdapter(arrayAdapter);
        Parcelable state = binding.listViewCount.onSaveInstanceState();

        model = new ViewModelProvider(this).get(MyViewModel.class);
        listViewModel = new ViewModelProvider(this ).get(ListViewModel.class);

        model.getNumbers().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.txtCount.setText("" + integer);

            }
        });

        listViewModel.getArrayListNumber().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                arrayList.clear();
                arrayList.addAll(strings);
                arrayAdapter.notifyDataSetChanged();
            }
        });
        //up
        binding.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.increaseNumber();
                listViewModel.InsertNumber(binding.txtCount.getText().toString());
            }
        });
        //down
        binding.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.decreaseNumber();
                listViewModel.InsertNumber(binding.txtCount.getText().toString());
            }
        });
        binding.listViewCount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listViewModel.DeleteItem(position);
                arrayAdapter.notifyDataSetChanged();
                return false;
            }
        });
        binding.listViewCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("number" , arrayList.get(position));
                index = position;
                startActivityForResult(intent, REQUEST_CODE_EXAMPLE);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng
        if (requestCode == REQUEST_CODE_EXAMPLE) {
            // resultCode được set bởi DetailActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if (resultCode == Activity.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                final String result = data.getStringExtra(DetailsActivity.EXTRA_DATA);
                // Sử dụng kết quả result bằng cách hiện Toast
                Toast.makeText(this,  result, Toast.LENGTH_LONG).show();
                listViewModel.UpdateArrList(index ,result);
                arrayAdapter.notifyDataSetChanged();
            } else {
                // DetailActivity không thành công, không có data trả về.
            }
        }
    }
}