package me.superskidder.lune.utils.irc;

import java.awt.*;

public class IRCMessage {
    public String author = "null";
    public String text = "none";
    public Color color = new Color(200, 200, 200);

    public IRCMessage(String a, String t, Color c) {
        this.author = a;
        this.text =t;
        this.color = c;
    }


    public IRCMessage(String a, String t) {
        this.author = a;
        this.text =t;
    }
}
