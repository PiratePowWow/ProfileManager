package com.theironyard;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {
    public static HashMap<String, Game> allGames = new HashMap<>();
    //public static ArrayList<Profile> allProfiles = new ArrayList<>();
    public static HashMap<String, User> allUsersMap = new HashMap<>();
    public static ArrayList<User> allUsers = new ArrayList<>();

    public static void main(String[] args) {
        Spark.staticFileLocation("/public");
        //add test User, Game, and Profile objects
        allUsersMap.put("Jimmy",new User("Jimmy", "Pee"));
        allUsersMap.put("James",new User("James", "Jones"));
        allUsersMap.put("Martha",new User("Martha", "Stewart"));
        allGames.put("Batman",new Game("Batman", "2010"));
        allGames.put("Metal Gear Solid",new Game("Metal Gear Solid", "2012"));
        allGames.put("Super Mario",new Game("Super Mario", "1985"));
        allUsersMap.get("Jimmy").getProfiles().add(new Profile("saltynuts", "image1", "2016"));
        allUsersMap.get("Jimmy").getProfiles().add(new Profile("scarynuts", "image2", "2016"));
        allUsersMap.get("James").getProfiles().add(new Profile("dominator", "image3", "2016"));
        allUsersMap.get("James").getProfiles().add(new Profile("dingleberry", "image4", "2016"));
        allUsersMap.get("Martha").getProfiles().add(new Profile("sweetdick", "image5", "2016"));
        allUsersMap.get("Martha").getProfiles().add(new Profile("sweetTits", "image6", "2016"));
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
                    if(user!=null) {
                        m.put("userName", user.getName());
                    }
                    //String profiles = "<ul> {{#.}} <li>name:{{handle}} and type:{{avatar}} </li> {{/.}} </ul>";

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
