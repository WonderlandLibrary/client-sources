package pw.latematt.xiv.management;

import pw.latematt.xiv.XIV;

import java.util.Map;

/**
 * @author Matthew
 */
public abstract class MapManager<K, V> {
    protected Map<K, V> contents;

    public MapManager(Map<K, V> contents) {
        this.contents = contents;
    }

    public Map<K, V> getContents() {
        return contents;
    }

    public void setup() {
        XIV.getInstance().getLogger().info("" + getClass().getSimpleName() + " does not require to be setup.");
    }
}
