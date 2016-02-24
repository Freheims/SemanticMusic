package database;

/**
 * Class for storing database credentials
 */
public class DbCredentials {
    private static String HOST ="";
    private static String USER ="";
    private static String PASSWORD ="";
    private static String DATABASE ="";

    public static String getHOST() {
        return HOST;
    }

    public static String getUSER() {
        return USER;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static String getDATABASE() {
        return DATABASE;
    }
}
