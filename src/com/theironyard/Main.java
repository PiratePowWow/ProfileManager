package com.theironyard;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static ArrayList<Game> allGames = new ArrayList<>();
    //public static ArrayList<Profile> allProfiles = new ArrayList<>();
    public static ArrayList<User> allUsers = new ArrayList<>();



    public static void main(String[] args) {
        //add test User, Game, and Profile objects
        allUsers.add(new User("Jimmy", "Pee"));
        allUsers.add(new User("James", "Jones"));
        allUsers.add(new User("Martha", "Stewart"));
        allGames.add(new Game("Batman", "2010"));
        allGames.add(new Game("Metal Gear Solid", "2012"));
        allGames.add(new Game("Super Mario", "1985"));




        Spark.init();
        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap m = new HashMap();

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
