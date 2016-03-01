package com.theironyard;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by PiratePowWow on 3/1/16.
 */
public class MainTest {

    public Connection startConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./test");
        Main.createTables(conn);
        return conn;
    }
    public void endConnection(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("DROP TABLE users");
        stmt.execute("DROP TABLE games");
        stmt.execute("DROP TABLE profiles");
        conn.close();
    }
    @Test
    public void testUser() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "James", "");
        User user = Main.selectUser(conn, "James");
        endConnection(conn);
        assertTrue(user!=null);
    }
    @Test
    public void testProfile() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "James", "");
        User user = Main.selectUser(conn, "James");
        Main.insertProfile(conn, user, "handle", "avatar", "dateCreated", "dateModified");
        Main.insertProfile(conn, user, "handles", "avatars", "datesCreated", "datesModified");
        ArrayList<Profile> profiles = Main.selectProfiles(conn, user.id);
        endConnection(conn);
        assertTrue(profiles.size()==2);
    }
    @Test
    public void testGame() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "James", "");
        User user = Main.selectUser(conn, "James");
        Main.insertProfile(conn, user, "handle", "avatar", "dateCreated", "dateModified");
        ArrayList<Profile> profiles = Main.selectProfiles(conn, user.id);
        Main.insertGame(conn, profiles.get(0), "title", "releaseDate");
        Main.insertGame(conn, profiles.get(0), "titles", "releaseDates");
        ArrayList<Game> games = Main.selectGames(conn, profiles.get(0).id);
        endConnection(conn);
        assertTrue(games.size()==2);
    }
}
