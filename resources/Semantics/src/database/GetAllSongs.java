package database;

import model.Song;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class containing methods for getting songs from the SQL-database.
 *
 *
 * Created by fredrik on 2/14/16.
 */
public class GetAllSongs {

    private static String host = DbCredentials.getHOST();
    private static String user = DbCredentials.getUSER();
    private static String psw = DbCredentials.getPASSWORD();
    private static String db = DbCredentials.getDATABASE();
    private static Connection conn = null;
    private static Statement songStatement = null;
    private static Statement albumStatement = null;
    private static Statement artistStatement = null;
    private static Statement genreStatement = null;
    private static ResultSet songResultSet = null;
    private static ResultSet artistResultSet = null;
    private static ResultSet albumResultSet = null;
    private static ResultSet genreResultSet = null;


    /**
     * Gets all un-annotated songs from the database
     * @return An array of songs.
     */
    public static ArrayList<Song> getAll() {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db + "?" + "user=" + user + "&password=" + psw );

            songStatement = conn.createStatement();
            songResultSet = songStatement.executeQuery("SELECT * FROM spotify.track WHERE annotated = 0 LIMIT 0, 15");

            songs = createSongs(songResultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return  songs;
    }

    /**
     * Creates an array of Song() objects from a query-result
     * @param resultSet The result from the SQL-query
     * @return An array of Song() objects
     * @throws Exception
     */
    private static ArrayList<Song> createSongs(ResultSet resultSet) throws Exception{
        ArrayList<Song> songs = new ArrayList<>();

        while(resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            int duration = resultSet.getInt("duration");
            String image = resultSet.getString("image");
            String artistName = getArtist(id);
            String albumName = getAlbum(id);
            ArrayList<String> genre = getGenre(id);
            songs.add(new Song(id, name, artistName, albumName, genre, duration, image));
        }
        return songs;
    }

    /**
     * Method for getting the name of the album
     * @param songID The spotify-id for the song
     * @return The name of the album
     */
    private static String getAlbum(String songID) throws SQLException {
        albumStatement = conn.createStatement();
        albumResultSet = albumStatement.executeQuery("SELECT * FROM album, trackAlbum WHERE trackAlbum.track_id = '" + songID + "' AND trackAlbum.album_id = album.id;");
        albumResultSet.next();
        return albumResultSet.getString("name");
    }

    /**
     * Method for getting the name of the artist
     * @param songID The spotify-id for the song
     * @return The name of the artist
     */
    private static String getArtist(String songID) throws SQLException {
        artistStatement = conn.createStatement();
        artistResultSet = artistStatement.executeQuery("SELECT * FROM artist, trackArtist WHERE trackArtist.track_id = '" + songID + "' AND trackArtist.artist_id = artist.id;");
        artistResultSet.next();
        return artistResultSet.getString("name");
    }

    /**
     * Method for getting the genres for a song
     * @param songID The spotify-id for the song
     * @return An array of genres
     */
    private static ArrayList<String> getGenre(String songID) throws SQLException {
        ArrayList<String> genres = new ArrayList<>();
        genreStatement = conn.createStatement();
        genreResultSet = genreStatement.executeQuery("SELECT * FROM genre, trackGenre WHERE trackGenre.track_id = '" + songID + "' AND trackGenre.genre = genre.name;");
        while (genreResultSet.next()){
            genres.add(genreResultSet.getString("name"));
        }
        return genres;
    }

    /**
     * Method for closing all open connections
     */
    private static void close() {
        try {
            if (songResultSet != null) {
                songResultSet.close();
            }
            if (artistResultSet != null) {
                artistResultSet.close();
            }
            if (albumResultSet != null) {
                albumResultSet.close();
            }
            if (genreResultSet != null) {
                genreResultSet.close();
            }

            if (songStatement != null) {
                songStatement.close();
            }

            if (albumStatement != null) {
                albumStatement.close();
            }

            if (artistStatement != null) {
                artistStatement.close();
            }
            if (genreStatement != null) {
                genreStatement.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
