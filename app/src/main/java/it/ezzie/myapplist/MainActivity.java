package it.ezzie.myapplist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

import it.ezzie.myapplist.calculator.MainActivity_Blue_Calculator;
import it.ezzie.myapplist.calendar.MainActivity_Calendar;
import it.ezzie.myapplist.databinding.ActivityMiniAppHubBinding;
import it.ezzie.myapplist.dateCounter.DateCounter;
import it.ezzie.myapplist.fontConverter.MainActivityFontConverter;
import it.ezzie.myapplist.wordGuess.MainActivityWordGuess;

public class MainActivity extends AppCompatActivity {
    private ActivityMiniAppHubBinding binding;
    private List<AppList> appList;
    private CustomListView listAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMiniAppHubBinding.inflate(getLayoutInflater());
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
                new AppList(1,"Word Guess","Puzzles", R.drawable.word_guess),
                new AppList(2,"Font Converter","Fonts", R.drawable.font_converter),
                new AppList(3,"Calculator","Maths", R.drawable.calculator),
                new AppList(4,"Calendar","Events", R.drawable.calendar),
                new AppList(5,"Date Counter","Events", R.drawable.date_counter)
        );
    }

    private void onAppClicked(){
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppList selectedApp = appList.get(position);
                String appTitle = selectedApp.appTitle();
                initAppSwitch(appTitle);
            }
        });
    }
    private void initAppSwitch(String appTitle){
        switch (appTitle){
          case  "Word Guess" :
                intent = new Intent(this, MainActivityWordGuess.class);
                initAppIntent(intent);
                break;
          case "Font Converter" :
                intent = new Intent(MainActivity.this, MainActivityFontConverter.class);
                initAppIntent(intent);
                break;
          case "Calculator" :
                intent = new Intent(MainActivity.this, MainActivity_Blue_Calculator.class);
                initAppIntent(intent);
                break;
          case "Calendar" :
                intent = new Intent(MainActivity.this, MainActivity_Calendar.class);
                initAppIntent(intent);
                break;
          case "Date Counter" :
                intent = new Intent(MainActivity.this, DateCounter.class);
                initAppIntent(intent);
                break;
        }
    }
    private void initAppIntent(Intent intent){
        startActivity(intent);
    }
}