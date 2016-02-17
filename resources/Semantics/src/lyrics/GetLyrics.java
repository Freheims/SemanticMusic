package lyrics;

import model.Song;
import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.entity.track.Track;
import org.jmusixmatch.entity.track.TrackData;
import org.jmusixmatch.entity.lyrics.Lyrics;

/**
 * Class for getting lyrics
 *
 * Created by fredrik on 2/15/16.
 */
public class GetLyrics {
    private static String apiKey = ApiKey.getApiKey();
    private static MusixMatch musixMatch = new MusixMatch(apiKey);

    public static void getLyric(Song song){
        try {
            Track queryTrack = musixMatch.getMatchingTrack(song.getNAME(), song.getARTIST_NAME());
            TrackData trackData = queryTrack.getTrack();
            if(trackData.getTrack_spotify_id().equals(song.getSPOTIFY_ID())) {
                System.out.println(song.getSPOTIFY_ID());
                int trackID = trackData.getTrackId();
                Lyrics lyricsObject  = musixMatch.getLyrics(trackID);
                String lyrics = lyricsObject.getLyricsBody();
                song.setLyrics(lyrics);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }






}
