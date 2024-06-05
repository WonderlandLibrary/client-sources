package de.tired.api.guis.guipainting.features;

public class Text {

    public String text;

    public int x, y;

    public boolean drag;

    public Text(String text, int x, int y, boolean drag) {
        this.text = text;
        this.y = y;
        this.x = x;
        this.drag = drag;
    }

}
