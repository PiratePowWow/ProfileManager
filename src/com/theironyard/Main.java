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
    public static HashMap<String, User> allUsers = new HashMap<>();



    public static void main(String[] args) {
        //add test User, Game, and Profile objects
        allUsers.put("Jimmy",new User("Jimmy", "Pee"));
        allUsers.put("James",new User("James", "Jones"));
        allUsers.put("Martha",new User("Martha", "Stewart"));
        allGames.put("Batman",new Game("Batman", "2010"));
        allGames.put("Metal Gear Solid",new Game("Metal Gear Solid", "2012"));
        allGames.put("Super Mario",new Game("Super Mario", "1985"));
        allUsers.get("Jimmy").getProfiles().put("saltynuts",new Profile("saltynuts", "image1", "2016"));
        allUsers.get("Jimmy").getProfiles().put("scarynuts",new Profile("scarynuts", "image2", "2016"));
        allUsers.get("James").getProfiles().put("dominator",new Profile("dominator", "image3", "2016"));
        allUsers.get("James").getProfiles().put("dingleberry",new Profile("dingleberry", "image4", "2016"));
        allUsers.get("Martha").getProfiles().put("sweetdick",new Profile("sweetdick", "image5", "2016"));
        allUsers.get("Martha").getProfiles().put("sweetTits",new Profile("sweetTits", "image6", "2016"));
        allUsers.get("Jimmy").getProfiles().get("saltynuts").getGames().add(allGames.get("Batman"));
        allUsers.get("Jimmy").getProfiles().get("scarynuts").getGames().add(allGames.get("Super Mario"));
        allUsers.get("James").getProfiles().get("dominator").getGames().add(allGames.get("Batman"));
        allUsers.get("James").getProfiles().get("dingleberry").getGames().add(allGames.get("Metal Gear Solid"));
        allUsers.get("Martha").getProfiles().get("sweetdick").getGames().add(allGames.get("Metal Gear Solid"));
        allUsers.get("Martha").getProfiles().get("sweetTits").getGames().add(allGames.get("Super Mario"));











        Spark.init();
        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    int userIndexNum = 0;
                    String userIndex = request.queryParams("userIndex");
                    if (userIndex!= null){
                        userIndexNum = Integer.valueOf(userIndex);
                    }

                    m.put("previous", (userIndexNum -1 >= 0)? userIndexNum -1:null);
                    m.put("next", (userIndexNum + 1 < allUsers.size())? userIndexNum+1:null);
                    return new ModelAndView(m, "home.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/",
                ((request, response) -> {

                    response.redirect("/");
                    return "";
                }),
                new MustacheTemplateEngine()
        );
        Spark.put(
                "/",
                ((request, response) -> {


                    return "";
                }),
                new MustacheTemplateEngine()
        );
        Spark.delete(
                "/",
                ((request, response) -> {


                    return "";
                }),
                new MustacheTemplateEngine()
        );



    }
    public static User getUserFromSession(Session session){
        User user = session.attribute("userName");
        return user;
    }
}
