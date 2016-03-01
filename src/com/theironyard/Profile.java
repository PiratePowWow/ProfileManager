package com.theironyard;

import java.util.ArrayList;

/**
 * Created by PiratePowWow on 2/25/16.
 */
public class Profile implements Comparable {
    int id;
    String handle;
    String avatar;
    String dateCreated;
    String dateModified;
    ArrayList<Game> games = new ArrayList<>();

    public Profile(){

    }

    public Profile(int id, String handle, String avatar, String dateCreated, String dateModified) {
        this.id = id;
        this.handle = handle;
        this.avatar = avatar;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    @Override
    public String toString() {
        return handle +" "+ avatar +" "+dateCreated+" "+dateModified;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    @Override
    public int compareTo(Object o) {
        Profile p = (Profile) o;
        return handle.compareTo(p.handle);
    }
}
