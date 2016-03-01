package com.theironyard;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {
    public static HashMap<String, Game> allGames = new HashMap<>();
    //public static ArrayList<Profile> allProfiles = new ArrayList<>();
    public static HashMap<String, User> allUsersMap = new HashMap<>();
    public static ArrayList<User> allUsers = new ArrayList<>();

    public static Connection startConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        return conn;
    }
    public void endConnection(Connection conn) throws SQLException {
        conn.close();
    }

    public static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY , name VARCHAR UNIQUE , password VARCHAR )");
        stmt.execute("CREATE TABLE IF NOT EXISTS profiles (id IDENTITY , user_id INT , handle VARCHAR , avatar VARCHAR , date_created VARCHAR , date_modified VARCHAR )");
        stmt.execute("CREATE TABLE IF NOT EXISTS games (id IDENTITY , profile_id INT , title VARCHAR , release_date VARCHAR )");
    }
    public static void insertUser(Connection conn, String name, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, password);
        stmt.execute();
    }
    public static void insertProfile(Connection conn, User user, String handle, String avatar, String dateCreated, String dateModified) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO profiles VALUES (NULL, ?, ?, ?, ?, ?)");
        stmt.setInt(1, user.id);
        stmt.setString(2, handle);
        stmt.setString(3, avatar);
        stmt.setString(4, dateCreated);
        stmt.setString(5, dateModified);
        stmt.execute();
    }
    public static void insertGame(Connection conn, Profile profile, String title, String releaseDate) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO games VALUES (NULL, ?, ?, ?)");
        stmt.setInt(1, profile.id);
        stmt.setString(2, title);
        stmt.setString(3, releaseDate);
        stmt.execute();
    }
    public static void updateUser(Connection conn, String userName, String password, User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE users SET name = ?, password = ? WHERE id = ?");
        stmt.setString(1, (!userName.isEmpty()?userName:user.name));
        stmt.setString(2, (!password.isEmpty()?password:user.password));
        stmt.setInt(3, user.id);
        stmt.execute();
    }
    public static void updateProfile(Connection conn, String handle, String avatar, String dateModified, Profile profile) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE profiles SET handle = ?, avatar = ?, date_modified = ? WHERE id = ?");
        stmt.setString(1, (!handle.isEmpty()?handle:profile.handle));
        stmt.setString(2, (!avatar.isEmpty()?avatar:profile.avatar));
        stmt.setString(3, (!dateModified.isEmpty()?dateModified:profile.dateModified));
        stmt.setInt(4, profile.id);
        stmt.execute();
    }
    public static void updateGame(Connection conn, String title, String releaseDate, Game game) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE games SET title = ?, release_date = ? WHERE id = ?");
        stmt.setString(1, (!title.isEmpty()?title:game.title));
        stmt.setString(2, (!releaseDate.isEmpty()?releaseDate:game.releaseDate));
        stmt.setInt(3, game.id);
        stmt.execute();
    }
    public static User selectUser(Connection conn, String name) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE name = ?");
        stmt.setString(1, name);
        ResultSet results = stmt.executeQuery();
        if(results.next()){
            int id = results.getInt("id");
            String password = results.getString("password");
            User user = new User(id, name, password);
            return user;
        }
        return null;
    }
    public static ArrayList<User> selectUsers(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
        ResultSet results = stmt.executeQuery();
        ArrayList<User> users = new ArrayList<>();
        while(results.next()){
            int id = results.getInt("id");
            String name = results.getString("name");
            String password = results.getString("password");
            User user = new User(id, name, password);
            users.add(user);
        }
        Collections.sort(users);
        return users;
    }
    public static ArrayList<Profile> selectProfiles(Connection conn, int userId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM profiles WHERE user_id=?");
        stmt.setInt(1, userId);
        ResultSet results = stmt.executeQuery();
        ArrayList<Profile> profiles = new ArrayList<>();
        while(results.next()){
            int id = results.getInt("id");
            String handle = results.getString("handle");
            String avatar = results.getString("avatar");
            String dateCreated = results.getString("date_created");
            String dateModified = results.getString("date_modified");
            Profile profile = new Profile(id, handle, avatar,dateCreated, dateModified);
            profiles.add(profile);
        }
        Collections.sort(profiles);
        return profiles;
    }
    public static ArrayList<Game> selectGames(Connection conn, int profileId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM games WHERE profile_id = ?");
        stmt.setInt(1, profileId);
        ResultSet results = stmt.executeQuery();
        ArrayList<Game> games = new ArrayList<>();
        while(results.next()){
            int id = results.getInt("id");
            String title = results.getString("title");
            String releaseDate = results.getString("release_date");
            Game game = new Game(id, title, releaseDate);
            games.add(game);
        }
        Collections.sort(games);
        return games;
    }



    public static void main(String[] args) {

        Spark.staticFileLocation("/public");
        //add test User, Game, and Profile objects
        allUsersMap.put("Jimmy",new User("Jimmy", "Pee"));
        allUsersMap.put("James",new User("James", "Jones"));
        allUsersMap.put("Martha",new User("Martha", "Stewart"));
        allGames.put("Batman",new Game("Batman", "2010"));
        allGames.put("Metal Gear Solid",new Game("Metal Gear Solid", "2012"));
        allGames.put("Super Mario",new Game("Super Mario", "1985"));
        allUsersMap.get("Jimmy").getProfiles().add(new Profile("salty", "image1", "2016"));
        allUsersMap.get("Jimmy").getProfiles().add(new Profile("scary", "image2", "2016"));
        allUsersMap.get("James").getProfiles().add(new Profile("dominator", "image3", "2016"));
        allUsersMap.get("James").getProfiles().add(new Profile("twinkletoes", "image4", "2016"));
        allUsersMap.get("Martha").getProfiles().add(new Profile("sweetie", "image5", "2016"));
        allUsersMap.get("Martha").getProfiles().add(new Profile("saucy", "image6", "2016"));
        allUsersMap.get("Jimmy").getProfiles().get(0).getGames().add(allGames.get("Batman"));
        allUsersMap.get("Jimmy").getProfiles().get(0).getGames().add(allGames.get("Super Mario"));
        allUsersMap.get("Jimmy").getProfiles().get(1).getGames().add(allGames.get("Super Mario"));
        allUsersMap.get("Jimmy").getProfiles().get(1).getGames().add(allGames.get("Metal Gear Solid"));
        allUsersMap.get("James").getProfiles().get(0).getGames().add(allGames.get("Batman"));
        allUsersMap.get("James").getProfiles().get(0).getGames().add(allGames.get("Super Mario"));
        allUsersMap.get("James").getProfiles().get(1).getGames().add(allGames.get("Metal Gear Solid"));
        allUsersMap.get("James").getProfiles().get(1).getGames().add(allGames.get("Batman"));
        allUsersMap.get("Martha").getProfiles().get(0).getGames().add(allGames.get("Metal Gear Solid"));
        allUsersMap.get("Martha").getProfiles().get(1).getGames().add(allGames.get("Super Mario"));
        for(User user: allUsersMap.values()){
            allUsers.add(user);
        }
        Collections.sort(allUsers);

        Spark.init();
        Spark.get(
                "/",
                ((request, response) -> {
                    User user = getUserFromSession(request.session());
                    HashMap m = new HashMap();
                    int userIndexNum = 0;
                    boolean modifyAccountButton = false;
                    boolean modifyUser = false;
                    boolean modifyProfile = false;
                    boolean editProfile = false;
                    String editProfileStr = request.queryParams("editProfile");
                    if (editProfileStr!=null){
                        editProfile = true;
                    }
                    String modifyUserStr = request.queryParams("modifyUser");
                    if (modifyUserStr != null){
                        modifyUser = Boolean.valueOf(modifyUserStr);
                    }
                    String userIndex = request.queryParams("userIndex");
                    if (userIndex!= null){
                        userIndexNum = Integer.valueOf(userIndex);
                    }
                    if (user!=null){
                        modifyAccountButton = true;
                    }
                    if (modifyUser){
                        modifyAccountButton = false;
                    }
                    User userView = allUsers.get(userIndexNum);
                    if(user!=null) {
                        if (userView.getName().equals(user.getName())) {
                            modifyProfile = true;
                        }
                    }
                    //userView.profi
                    if(user!=null) {
                        m.put("userName", user.getName());
                    }
                    m.put("editProfile", editProfile);
                    m.put("modifyAccountButton", modifyAccountButton);
                    m.put("profiles", userView.getProfiles());
                    m.put("modifyProfile", modifyProfile);
                    m.put("modifyUser", modifyUser);
                    m.put("userView", userView);
                    m.put("previous", (userIndexNum -1 >= 0)? userIndexNum -1:null);
                    m.put("next", (userIndexNum + 1 < allUsers.size())? userIndexNum+1:null);
                    return new ModelAndView(m, "home.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/login",
                ((request, response) -> {
                        String userName = request.queryParams("userName");
                        String password = request.queryParams("password");
                        if (userName != null && password != null) {
                            if (allUsersMap.get(userName)!=null) {
                                if (allUsersMap.get(userName).getPassword().equals(password)) {
                                    Session session = request.session();
                                    session.attribute("userName", allUsersMap.get(userName));
                                } else {
                                    Spark.halt(401, "User not authenticated");
                                }
                            }else{
                                Spark.halt(400, "User not found");
                            }
                        }
                        response.redirect("/");
                        return "";
                })
        );
        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/modifyUser",
                ((request, response) -> {
                    User user = getUserFromSession(request.session());
                    String userName = request.queryParams("userName");
                    String password = request.queryParams("password");
                    if (!userName.isEmpty()){
                        allUsersMap.remove(user.getName());
                        user.setName(userName);
                        allUsersMap.put(user.getName(), user);
                        allUsers = new ArrayList<User>();
                        for(User each:allUsersMap.values()){
                            allUsers.add(each);
                        }
                        Collections.sort(allUsers);
                        Session session = request.session();
                        session.attribute("userName", allUsersMap.get(userName));
                    }
                    if (!password.isEmpty()){
                        User newPass = getUserFromSession(request.session());
                        newPass.setPassword(password);
                    }
                    response.redirect("/");
                    return "";
                })
        );
    }
    public static User getUserFromSession(Session session){
        User user = session.attribute("userName");
        return user;
    }
}
