package com.theironyard;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PiratePowWow on 2/25/16.
 */
public class User implements Comparable {
    String name;
    String password;
    HashMap<String,Profile> profiles = new HashMap<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", profiles=" + profiles +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        User u = (User) o;
        return name.compareTo(u.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String,Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(HashMap<String,Profile> profiles) {
        this.profiles = profiles;
    }

}
