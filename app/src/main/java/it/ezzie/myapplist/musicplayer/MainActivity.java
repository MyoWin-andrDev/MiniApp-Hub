package it.ezzie.myapplist.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.musicplayer.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static List<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
    }


    private void initData(){
        songs = List.of(
                new Song("Playing God", R.drawable.polyphia, R.raw.playing_god),
                new Song("G.O.A.T", R.drawable.polyphia, R.raw.playing_god),
                new Song("Yas", R.drawable.polyphia, R.raw.playing_god),
                new Song("Look But Don't Touch", R.drawable.polyphia, R.raw.playing_god),
                new Song("Euphoria", R.drawable.polyphia , R.raw.playing_god),
                new Song("Drown", R.drawable.polyphia , R.raw.playing_god),
                new Song("40oz", R.drawable.polyphia , R.raw.playing_god),
                new Song("Champagne", R.drawable.polyphia , R.raw.playing_god)
        );
    }

    public void onSongClicked(View view) {
        CardView card = (CardView) view;
        LinearLayout linearLayout = (LinearLayout) card.getChildAt(0);
        TextView text = (TextView) linearLayout.getChildAt(1);
        String songName = text.getText().toString();
        Song currentSong = songs.stream().filter(song -> song.name().equalsIgnoreCase(songName)).findFirst().get();
        Toast.makeText(this, currentSong.name(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DetailActivity.class);
//        intent.putExtra("name",currentSong.name());
//        intent.putExtra("image",currentSong.image());
        intent.putExtra("song",currentSong);
        startActivity(intent);
    }
}