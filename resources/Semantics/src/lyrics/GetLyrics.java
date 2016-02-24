package lyrics;

import model.Song;
import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.entity.track.Track;
import org.jmusixmatch.entity.track.TrackData;
import org.jmusixmatch.entity.lyrics.Lyrics;

/**
 * Class for getting lyrics
 *
 */
public class GetLyrics {
    private static final String apiKey = ApiKey.getApiKey();
    private static final MusixMatch musixMatch = new MusixMatch(apiKey);

    /**
     * Retrieves the lyrics for a song from MusicxMatchs
     * @param song the song to get lyrics for
     */
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
