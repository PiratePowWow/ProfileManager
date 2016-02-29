package com.theironyard;

/**
 * Created by PiratePowWow on 2/25/16.
 */
public class Game implements Comparable{
    String title;
    String releaseDate;

    public Game(String title, String releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
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
