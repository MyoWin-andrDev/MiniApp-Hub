package it.ezzie.myapplist.fontConverter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityFontConverter extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton rb_zawToUni, rb_UnitoZaw;
    private EditText et_input, et_output;
    private Button btn_convert , btn_clear, btn_copy;
    private Typeface zawFont , uniFont;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        zawFont = getResources().getFont(R.font.zawgyi_one);
        uniFont = getResources().getFont(R.font.pyidaungsu_1_8_3_regular);
        initUI();
        initListeners();

    }
    private void initListeners(){
//        rb_UnitoZaw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Toast.makeText(MainActivity.this, "Zawgyi to Unicode Checked" + isChecked, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        rb_zawToUni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Toast.makeText(MainActivity.this, "Unicode to Zawgyi Checked" + isChecked, Toast.LENGTH_SHORT).show();
//            }
//        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_uniToZaw){
                    Toast.makeText(MainActivityFontConverter.this, "Unicode to Zawgyi Checked", Toast.LENGTH_SHORT).show();
                    et_input.setTypeface(uniFont);
                    et_output.setTypeface(zawFont);
                }else{
                    Toast.makeText(MainActivityFontConverter.this, "Zawgyi to Unicode Checked", Toast.LENGTH_SHORT).show();
                    et_input.setTypeface(zawFont);
                    et_output.setTypeface(uniFont);
                }
            }
        });

        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = et_input.getText().toString();
                String output ;
                if(rb_UnitoZaw.isChecked()){
                    output = Rabbit.zg2uni((inputText));
                }else{
                    output = Rabbit.uni2zg((inputText));
                }
                et_output.setText(output);

            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_input.getText().clear();
                et_output.getText().clear();
            }
        });

        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String output = et_output.getText().toString();;
                if(!output.isEmpty()){
                    //Copy Text
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Font Converter",output);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(MainActivityFontConverter.this, "Text Copied", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivityFontConverter.this, "No Text to Copy", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initUI(){
        radioGroup = findViewById(R.id.rg);
        rb_zawToUni = findViewById(R.id.rb_zawToUni);
        rb_UnitoZaw = findViewById(R.id.rb_uniToZaw);
        et_input = findViewById(R.id.input);
        et_output = findViewById(R.id.output);
        btn_convert = findViewById(R.id.btn_convert);
        btn_clear = findViewById(R.id.btn_clear);
        btn_copy = findViewById(R.id.btn_copy);
    }
}