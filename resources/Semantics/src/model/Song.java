package model;

import java.util.ArrayList;

/**
 * A class for representing songs
 *
 * Created by fredrik on 2/14/16.
 */
public class Song {
    private final String SPOTIFY_ID;
    private final String NAME;
    private final String ARTIST_NAME;
    private final String ALBUM_NAME;
    private final ArrayList<String> GENRE;
    private final int DURATION;
    private final String IMAGE;

    private static String lyrics;
    private static String[] concepts;
    private static String[] emotions;

    public Song(String ID, String NAME, String ARTIST_NAME, String ALBUM_NAME, ArrayList<String> GENRE, int DURATION, String IMAGE) {
        this.SPOTIFY_ID = ID;
        this.NAME = NAME;
        this.ARTIST_NAME = ARTIST_NAME;
        this.ALBUM_NAME = ALBUM_NAME;
        this.GENRE = GENRE;
        this.DURATION = DURATION;
        this.IMAGE = IMAGE;
    }

    public String getSPOTIFY_ID() {
        return SPOTIFY_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getARTIST_NAME() {
        return ARTIST_NAME;
    }

    public String getALBUM_NAME() {
        return ALBUM_NAME;
    }

    public ArrayList<String> getGENRE() {
        return GENRE;
    }

    public int getDURATION() {
        return DURATION;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public static String getLyrics() {
        return lyrics;
    }

    public static void setLyrics(String lyrics) {
        Song.lyrics = lyrics;
    }

    public static String[] getConcepts() {
        return concepts;
    }

    public static void setConcepts(String[] concepts) {
        Song.concepts = concepts;
    }

    public static String[] getEmotions() {
        return emotions;
    }

    public static void setEmotions(String[] emotions) {
        Song.emotions = emotions;
    }

    @Override
    public String toString() {
        return "Song{" +
                "SPOTIFY_ID='" + SPOTIFY_ID + '\'' +
                ", NAME='" + NAME + '\'' +
                ", ARTIST_NAME='" + ARTIST_NAME + '\'' +
                ", ALBUM_NAME='" + ALBUM_NAME + '\'' +
                ", GENRE=" + GENRE +
                ", DURATION=" + DURATION +
                ", IMAGE='" + IMAGE + '\'' +
                '}';
    }
}
