package it.ezzie.myapplist.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import it.ezzie.myapplist.databinding.ActivityLifeCycleBinding;


public class ActivityLifeCycle extends AppCompatActivity {
    public static final String TAG = "MyTag";
    public static final int REQUEST_CODE = 123;
    private static final int SELECT_IMAGE_REQUEST_CODE = 0;
    private ActivityLifeCycleBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        binding = ActivityLifeCycleBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        initUI();
        initListener();
    }

    private void initListener() {
        binding.logo.setOnClickListener(v -> {
            onImplicitForResult(null);
        });
    }

    private void initUI() {
        Intent intent = getIntent();
        if(intent != null){
            String name = intent.getStringExtra("name");
            binding.txt.setText(name);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    public void onExplicit(View view) {
        Intent intent = new Intent(ActivityLifeCycle.this,InputActivity.class);
        startActivity(intent);
    }

    public void onExplicitForResult(View view) {
        Intent intent = new Intent(this,InputActivity.class);
        intent.putExtra("name",binding.txt.getText().toString());
        startActivityForResult(intent,REQUEST_CODE);
    }

    public void onImplicit(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com"));
        startActivity(intent);
    }

   private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult activityResult) {
            if(activityResult.getResultCode() == RESULT_OK && activityResult.getData() != null){
               Uri uri =  activityResult.getData().getData();
               binding.logo.setImageURI(uri);
            }
        }
    });
    public void onImplicitForResult(View view) {
        if(false) {
            //First Way
            var intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            launcher.launch(intent);
        }
        //Second Way
        else{
            var intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent,"Select Image"),SELECT_IMAGE_REQUEST_CODE);//also work without chooser
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE){
                String text = data.getStringExtra("text");
                binding.txt.setText(text);
            }
            if(requestCode == SELECT_IMAGE_REQUEST_CODE){
                Uri uri = data.getData();
                binding.logo.setImageURI(uri);
            }
        }
    }
}