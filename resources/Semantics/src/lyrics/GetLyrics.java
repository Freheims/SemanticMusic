package lyrics;

import model.Song;
import org.apache.log4j.BasicConfigurator;
import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.entity.track.Track;
import org.jmusixmatch.entity.track.TrackData;
import org.jmusixmatch.entity.lyrics.Lyrics;
import org.apache.log4j.Logger;


/**
 * Class for getting lyrics
 *
 */
public class GetLyrics {
    private static final String apiKey = ApiKey.getApiKey();
    private static final MusixMatch musixMatch = new MusixMatch(apiKey);
    static Logger logger = Logger.getLogger(GetLyrics.class);


    /**
     * Retrieves the lyrics for a song from MusicxMatchs
     * @param song the song to get lyrics for
     */
    public static void getLyric(Song song){
        BasicConfigurator.configure();
        try {
            Track queryTrack = musixMatch.getMatchingTrack(song.getNAME(), song.getARTIST_NAME());
            TrackData trackData = queryTrack.getTrack();
            if(trackData.getTrack_spotify_id().equals(song.getSPOTIFY_ID())) {
                int trackID = trackData.getTrackId();
                Lyrics lyricsObject  = musixMatch.getLyrics(trackID);
                String lyrics = lyricsObject.getLyricsBody();
                song.setLyrics(lyrics);
            }
        }catch (org.jmusixmatch.MusixMatchException e){
            logger.info("Lyrics not found for: " + song.getNAME() + " by " + song.getARTIST_NAME());
            //e.printStackTrace();
        }
    }

}