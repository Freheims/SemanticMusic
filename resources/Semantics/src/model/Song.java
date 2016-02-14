package model;

/**
 * Created by fredrik on 2/14/16.
 */
public class Song {
    private final String SpotifyID;
    private final String NAME;
    private final String ARTIST_NAME;
    private final String ALBUM_NAME;
    private final String[] GENRE;
    //private final int DURATION; TODO Do we want this?

    private static String lyrics;
    private static String[] concepts;
    private static String[] emotions;

    public Song(String ID, String NAME, String ARTIST_NAME, String ALBUM_NAME, String[] GENRE) {
        this.SpotifyID = ID;
        this.NAME = NAME;
        this.ARTIST_NAME = ARTIST_NAME;
        this.ALBUM_NAME = ALBUM_NAME;
        this.GENRE = GENRE;
        //this.DURATION = DURATION;
    }

    public String getSpotifyID() {
        return SpotifyID;
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

    public String[] getGENRE() {
        return GENRE;
    }

    /*
    public int getDURATION() {
        return DURATION;
    }
*/
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




}
