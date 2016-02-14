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


    public Song[] getAll() {
        Song[] songs = new Song[0];
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdb:mysql://" + host + "/" + db + "?" + "user=" + user + "&password=" + psw );

            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM spotify.artist");

        } catch (Exception e) {
            e.printStackTrace();
        }


        return  songs;
    }



}
