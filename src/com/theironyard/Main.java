package com.theironyard;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static HashMap<String, Game> allGames = new HashMap<>();
    //public static ArrayList<Profile> allProfiles = new ArrayList<>();
    public static HashMap<String, User> allUsersMap = new HashMap<>();



    public static void main(String[] args) {
        //add test User, Game, and Profile objects
        allUsersMap.put("Jimmy",new User("Jimmy", "Pee"));
        allUsersMap.put("James",new User("James", "Jones"));
        allUsersMap.put("Martha",new User("Martha", "Stewart"));
        allGames.put("Batman",new Game("Batman", "2010"));
        allGames.put("Metal Gear Solid",new Game("Metal Gear Solid", "2012"));
        allGames.put("Super Mario",new Game("Super Mario", "1985"));
        allUsersMap.get("Jimmy").getProfiles().put("saltynuts",new Profile("saltynuts", "image1", "2016"));
        allUsersMap.get("Jimmy").getProfiles().put("scarynuts",new Profile("scarynuts", "image2", "2016"));
        allUsersMap.get("James").getProfiles().put("dominator",new Profile("dominator", "image3", "2016"));
        allUsersMap.get("James").getProfiles().put("dingleberry",new Profile("dingleberry", "image4", "2016"));
        allUsersMap.get("Martha").getProfiles().put("sweetdick",new Profile("sweetdick", "image5", "2016"));
        allUsersMap.get("Martha").getProfiles().put("sweetTits",new Profile("sweetTits", "image6", "2016"));
        allUsersMap.get("Jimmy").getProfiles().get("saltynuts").getGames().add(allGames.get("Batman"));
        allUsersMap.get("Jimmy").getProfiles().get("scarynuts").getGames().add(allGames.get("Super Mario"));
        allUsersMap.get("James").getProfiles().get("dominator").getGames().add(allGames.get("Batman"));
        allUsersMap.get("James").getProfiles().get("dingleberry").getGames().add(allGames.get("Metal Gear Solid"));
        allUsersMap.get("Martha").getProfiles().get("sweetdick").getGames().add(allGames.get("Metal Gear Solid"));
        allUsersMap.get("Martha").getProfiles().get("sweetTits").getGames().add(allGames.get("Super Mario"));
        ArrayList<User> allUsers = new ArrayList<>();
        for(User user: allUsersMap.values()){
            allUsers.add(user);
        }



        Spark.init();
        Spark.get(
                "/",
                ((request, response) -> {
                    User user = getUserFromSession(request.session());
                    HashMap m = new HashMap();
                    int userIndexNum = 0;
                    String userIndex = request.queryParams("userIndex");
                    if (userIndex!= null){
                        userIndexNum = Integer.valueOf(userIndex);
                    }

                    User userView = allUsers.get(userIndexNum);
                    if(user!=null) {
                        m.put("userName", user.getName());
                    }
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
                            if (allUsersMap.get(userName).getPassword().equals(password)) {
                                Session session = request.session();
                                session.attribute("userName", allUsersMap.get(userName));
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
        Spark.put(
                "/modifyUser",
                ((request, response) -> {

                    response.redirect("/");
                    return "";
                })
        );




//        Spark.delete(
//                "/",
//                ((request, response) -> {
//
//
//                    return "";
//                }),
//                new MustacheTemplateEngine()
//        );



    }
    public static User getUserFromSession(Session session){
        User user = session.attribute("userName");
        return user;
    }
}
