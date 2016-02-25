package com.theironyard;

import java.util.ArrayList;

/**
 * Created by PiratePowWow on 2/25/16.
 */
public class Profile {
    String handle;
    String avatar;
    String dateCreated;
    String dateModified;
    ArrayList<Game> games;

    public Profile(String handle, String avatar, String dateCreated) {
        this.handle = handle;
        this.avatar = avatar;
        this.dateCreated = dateCreated;
        this.dateModified = dateCreated;
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
}