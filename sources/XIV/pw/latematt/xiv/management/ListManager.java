package pw.latematt.xiv.management;

import pw.latematt.xiv.XIV;

import java.util.List;

/**
 * KILL YOURSELF R U D Y (HAZE BOOTH)
 *
 * @author Matthew
 */
public abstract class ListManager<T> {
    protected List<T> contents;

    public ListManager(List<T> contents) {
        this.contents = contents;
    }

    public List<T> getContents() {
        return contents;
    }

    public void setup() {
        XIV.getInstance().getLogger().info("" + getClass().getSimpleName() + " does not require to be setup.");
    }
}
