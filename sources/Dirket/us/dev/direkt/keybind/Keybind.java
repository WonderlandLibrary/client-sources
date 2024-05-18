package us.dev.direkt.keybind;

/**
 * Created by Foundry on 11/15/2015.
 */
public abstract class Keybind {
    private int code;

    public Keybind(final int code) {
        this.code = code;
    }

    public abstract void keyPressed();

    public int getKey() {
        return this.code;
    }

    public void setKey(final int code) {
        this.code = code;
    }
}
