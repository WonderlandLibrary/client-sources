package de.tired.notification;

import de.tired.api.util.math.TimerUtil;

import java.awt.*;

public class SuperNotification {

    private String text;
    public int x, y;
    private Color color;
    public boolean saw;
    public TimerUtil timerUtil;

    public SuperNotification(String text, int x, int y, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.timerUtil = new TimerUtil();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isSaw() {
        return saw;
    }

    public void setSaw(boolean saw) {
        this.saw = saw;
    }

    public TimerUtil getTimerUtil() {
        return timerUtil;
    }

    public void setTimerUtil(TimerUtil timerUtil) {
        this.timerUtil = timerUtil;
    }
}
