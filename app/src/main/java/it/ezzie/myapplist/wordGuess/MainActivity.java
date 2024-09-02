package it.ezzie.myapplist.wordGuess;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

     String[] guess  = {
            "Apple",
            "Banana",
            "Orange",
            "Grape",
            "Strawberry",
            "Mango",
            "Pineapple",
            "Kiwi",
            "Peach",
            "Plum",
            "Blueberry",
            "Raspberry",
            "Lemon",
            "Lime",
            "Watermelon",
            "Cantaloupe",
            "Honeydew",
            "Tangerine",
            "Grapefruit",
            "Avocado"};

     String randomWord;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
         txt = findViewById(R.id.txt);
        EditText edt = findViewById(R.id.edt);
        Button btn = findViewById(R.id.btn);
        randomWord = getRandomWord();
        txt.setText(shuffleWord(randomWord));

        btn.setOnClickListener(v -> {
            String editTxt = String.valueOf(edt.getText());
            if(editTxt.equalsIgnoreCase(randomWord)){
                Toast.makeText(this,"Correct", Toast.LENGTH_SHORT).show();
                setRandomWord();
                edt.getText().clear();
            }
            else{
                Toast.makeText(this,"Incorrect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String shuffleWord(String randomWord){

        //Random Word to Char

        char[] charArray = this.randomWord.toCharArray();

        List<Character> charList = new ArrayList<>();//adding char to list
        for(char c : charArray){
            charList.add(c);
        }
        //Shuffling charList
        Collections.shuffle(charList);

        String value = "";
        for(char c : charList){
            value += c +"-";//adding char to string named value

        };

        return value;
    }

    private String getRandomWord(){
       Random random = new Random();
       int index = random.nextInt(guess.length);
       return guess[index];
    }
    private void setRandomWord(){
        randomWord = getRandomWord();
        txt.setText(shuffleWord(randomWord));
    }
}