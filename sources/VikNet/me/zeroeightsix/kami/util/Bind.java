package me.zeroeightsix.kami.util;

import org.lwjgl.input.Keyboard;

/**
 * Created by 086 on 9/10/2018.
 * Last Updated 28 June 2019 by hub
 */
public class Bind {

    private int key;

    public Bind(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public boolean isEmpty() {
        return key < 0;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return isEmpty() ? "None" : (key < 0 ? "None" : capitalise(Keyboard.getKeyName(key)));
    }

    public boolean isDown() {
        return !isEmpty() && Keyboard.isKeyDown(getKey());
    }

    private String capitalise(String str) {
        if (str.isEmpty()) {
            return "";
        }
        return Character.toUpperCase(str.charAt(0)) + (str.length() != 1 ? str.substring(1).toLowerCase() : "");
    }

    public static Bind none() {
        return new Bind(-1);
    }

}
