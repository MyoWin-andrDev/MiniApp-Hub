package it.ezzie.myapplist.musicplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.RawResourceDataSource;
import androidx.media3.exoplayer.ExoPlayer;

import com.example.musicplayer.databinding.ActivityDetailBinding;
import java.io.Serializable;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private Song song;
    private ExoPlayer exoPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUI();
        initListener();
        initExoPlayer();
    }

    private void initListener() {
        binding.btnNext.setOnClickListener(v -> {
            int index = MainActivity.songs.indexOf(song);
            if(index == MainActivity.songs.size() -1){
                index = 0;
            }
            else{
                index ++;
            }
            song = MainActivity.songs.get(index);
            updateSongData(song);
            updateSongPlayer();
        });
        binding.btnPause.setOnClickListener(v -> {
            if(exoPlayer.isPlaying()){
                exoPlayer.pause();
                binding.btnPause.setImageResource(R.drawable.play);
            }
            else{
                exoPlayer.play();
                binding.btnPause.setImageResource(R.drawable.pause);
            }
        });
        binding.btnPrevious.setOnClickListener(v -> {
            int index = MainActivity.songs.indexOf(song);
            if(index == 0){
                index = MainActivity.songs.size() -1;
            }
            else{
                index --;
            }
            song = MainActivity.songs.get(index);
            updateSongData(song);
            updateSongPlayer();
        });
    }

    private void initUI() {
        Intent intent = getIntent();
        if(intent != null) {
            song = (Song) intent.getSerializableExtra("song");
            if(song != null){
                updateSongData(song);
            }
            else {
                Toast.makeText(this, "Song data is missing", Toast.LENGTH_SHORT).show();
            }
//           String name =  intent.getStringExtra("name");
//           int img = intent.getIntExtra("image", R.drawable.polyphia);
//            binding.img.setImageDrawable(AppCompatResources.getDrawable(this, img));
//            binding.txt.setText(name);

        }
    }

    private void updateSongData(Song song) {
        binding.img.setImageDrawable(AppCompatResources.getDrawable(this, song.image()));
        binding.txt.setText(song.name());
    }

    @OptIn(markerClass = UnstableApi.class)
    private void updateSongPlayer(){
        if (song!=null) {
            Uri songUri = RawResourceDataSource.buildRawResourceUri(song.song());
            exoPlayer.setMediaItem(MediaItem.fromUri(songUri));
            exoPlayer.prepare();
            exoPlayer.play();
        }
    }

    private void initExoPlayer(){
        exoPlayer = new ExoPlayer.Builder(this).build();
        updateSongPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        exoPlayer.stop();
    }
}