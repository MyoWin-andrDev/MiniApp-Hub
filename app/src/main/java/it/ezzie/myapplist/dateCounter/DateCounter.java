package it.ezzie.myapplist.dateCounter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import it.ezzie.myapplist.databinding.ActivityDateCounterBinding;
import it.ezzie.myapplist.databinding.DialogInputDateCounterBinding;

public class DateCounter extends AppCompatActivity {

    private ActivityDateCounterBinding binding;
    private DatePickerDialog datePickerDialog;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private  AlertDialog alertDialog;
    private String dialogName;
    private SharedPreferences shared ;
    private static final String TOM = "Tom";
    private static final String JERRY = "Jerry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDateCounterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUI();
        initDatePickerDialog();
        initDialog();
        initListener();
    }

    private void initUI(){
        shared = getSharedPreferences("date_counter",MODE_PRIVATE);
        binding.tvTom.setText(readData(TOM));
        binding.tvJerry.setText(readData(JERRY));
    }


    private void initDatePickerDialog() {
        //dd/MM/Year
        datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            var selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
            binding.btnDate.setText(formatter.format(selectedDate));
            updateDate();
        });
        updateDate();
    }

    private void initDialog(){
        var dialogBinding = DialogInputDateCounterBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        alertDialog = builder
                .setView(dialogBinding.getRoot())
                //This is Title and message dialog
//                .setTitle("This is a dialog")
//                .setMessage("I am a custom dialog for date counter")

                //This is to another view
                .setCancelable(false)

                //Commenting toast message of dialog
//                .setPositiveButton("OK", (dialog1, which) -> Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show())
//                .setNegativeButton("Cancel",((dialog1, which) -> {Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();}))
                .create();

        dialogBinding.btnOK.setOnClickListener(v -> {
            // Toast.makeText(this, dialogBinding.etInput.getText().toString(), Toast.LENGTH_LONG).show();
           // binding.tvJerry.setText(dialogBinding.etInput.getText().toString());
            String text = dialogBinding.etInput.getText().toString();
            switch(dialogName){
               case TOM -> {
                   binding.tvTom.setText(text);
                   saveData(TOM,text);
               }
               case JERRY ->{
                   binding.tvJerry.setText(text);
                   saveData(JERRY,text);
               }
            }
            alertDialog.cancel();
        });

        dialogBinding.btnCancel.setOnClickListener(v -> {
            alertDialog.cancel();
        });

    }

    private void updateDate(){
        var date = LocalDate.parse(binding.btnDate.getText().toString() , formatter);
        var todayDate = LocalDate.now();
        var differenceDate = todayDate.toEpochDay() - date.toEpochDay();
        binding.tvDate.setText(String.valueOf(differenceDate + "Days"));
    }

    private void initListener() {
        binding.btnDate.setOnClickListener(v -> {
           datePickerDialog.show();
        });

        class OnTextClickListener implements View.OnClickListener{

            @Override
            public void onClick(View v) {
                showCustomDialog(dialogName);
            }
        }


        binding.tvTom.setOnClickListener( v ->  showCustomDialog(TOM));
        binding.tvJerry.setOnClickListener(v ->  showCustomDialog(JERRY));
    }

    private void showCustomDialog(String name) {
        dialogName = name;
        alertDialog.show();
    }

    //Save data in shared preference
    private void saveData(String key , String value){
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(key,value);
        editor.apply();
    }
    //Read date from shared preferences
    private String readData(String key){
        return shared.getString(key,key);
    }
}