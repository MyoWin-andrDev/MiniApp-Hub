package it.ezzie.myapplist;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import it.ezzie.myapplist.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private List<AppList> appList;
    private CustomListView listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initAppData();
        initList();
        onAppClicked();
    }

    private void initList(){
        listAdapter = new CustomListView(getApplicationContext(),appList);
        binding.listView.setAdapter(listAdapter);
    }

    private void initAppData(){
        appList = List.of(
                new AppList(1,"Word Guess","Puzzle", R.drawable.word_guess)
        );
    }

    private void onAppClicked(){
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = String.valueOf(parent.getItemIdAtPosition(position));
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_LONG).show();
            }
        });
    }
}