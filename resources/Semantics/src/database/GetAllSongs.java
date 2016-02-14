package database;

import model.Song;

import java.sql.*;

/**
 * Created by fredrik on 2/14/16.
 */
public class GetAllSongs {

    private String host = DbCredentials.getHOST();
    private String user = DbCredentials.getUSER();
    private String psw = DbCredentials.getPASSWORD();
    private String db = DbCredentials.getDATABASE();
    private Connection conn = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    /**
     * Gets all un-annotated songs from the database
     * @return An array of songs.
     */
    public Song[] getAll() {
        Song[] songs = new Song[0];
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdb:mysql://" + host + "/" + db + "?" + "user=" + user + "&password=" + psw );

            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM spotify.artist WHERE annotated == FALSE");

        } catch (Exception e) {
            e.printStackTrace();
        }


        return  songs;
    }

    /**
     * Creates a single Song() object from a query-result
     * @param resultSet The result from the SQL-query
     * @return A Song() object
     * @throws Exception
     */
    private Song createSong(ResultSet resultSet) throws Exception{
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        String artistName = getArtist(id);
        String albumName = getAlbum(id);
        String[] genre = getGenre(id);
        Song song = new Song(id, name, artistName, albumName, genre);
        return song;
    }

    /**
     * Method for getting the name of the album
     * @param songID The spotify-id for the song
     * @return The name of the album
     */
    private String getAlbum(String songID){
        return null; //TODO implement
    }

    /**
     * Method for getting the name of the artist
     * @param songID The spotify-id for the song
     * @return The name of the artist
     */
    private String getArtist(String songID){
        return null; //TODO implement
    }

    /**
     * Method for getting the genres for a song
     * @param songID The spotify-id for the song
     * @return An array of genres
     */
    private String[] getGenre(String songID){
        return null; //TODO implement
    }

}
