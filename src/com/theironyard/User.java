package com.theironyard;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PiratePowWow on 2/25/16.
 */
public class User implements Comparable {
    int id;
    String name;
    String password;
    ArrayList<Profile> profiles = new ArrayList<>();

    public User(int id, String name, String password) {
        this.id = id;
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

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }

}
