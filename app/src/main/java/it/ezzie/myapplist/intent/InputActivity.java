package it.ezzie.myapplist.intent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import it.ezzie.myapplist.databinding.ActivityInputBinding;


public class InputActivity extends AppCompatActivity {

    private ActivityInputBinding binding;

    private static final String TAG02 = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String name = getIntent().getStringExtra("name");
        binding.etInput.setText(name);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ActivityLifeCycle.TAG,"onDestroy InputActivity");
    }

    public void OnCancelClick(View view) {
        finish();
    }

    public void OnSaveClick(View view) {
        String value = binding.etInput.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("text",value);
        setResult(RESULT_OK,intent);
        finish();
    }
}