package testing;

import database.GetAllSongs;
import lyrics.GetLyrics;
import model.Song;
import semanticData.GetSemantics;

import java.util.ArrayList;

/**
 *
 * Created by fredrik on 2/15/16.
 */
public class Test {

    public static void main(String[] args) {
        ArrayList<Song> songs = GetAllSongs.getAll();


        for (Song song : songs) {
            GetLyrics.getLyric(song);
            GetSemantics.getConcepts(song);
            GetSemantics.getEmotions(song);
            System.out.println(song.toString());
        }

    }
}
