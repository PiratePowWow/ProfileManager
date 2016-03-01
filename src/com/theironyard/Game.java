package com.theironyard;

/**
 * Created by PiratePowWow on 2/25/16.
 */
public class Game implements Comparable{
    int id;
    String title;
    String releaseDate;

    public Game(int id, String title, String releaseDate) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
    }
    public Game(){

    }

    @Override
    public String toString() {
        return "Game{" +
                "title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int compareTo(Object o) {
        Game g = (Game) o;
        return title.compareTo(g.title);
    }
}
