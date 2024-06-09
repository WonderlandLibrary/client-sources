package me.finz0.osiris.util;

public abstract class Configurable {

    private String path;

    public Configurable(String path) {
        this.path = path;
    }

    public abstract void load();

    public abstract void save();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
