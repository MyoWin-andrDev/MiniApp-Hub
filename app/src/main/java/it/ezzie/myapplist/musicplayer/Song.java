package it.ezzie.myapplist.musicplayer;

import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;

import java.io.Serializable;

public record Song(String name , @DrawableRes int image, @RawRes int song) implements Serializable {
}
