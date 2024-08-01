package wtf.diablo.client.manager;

import java.util.LinkedList;
import java.util.List;

/**
 * The AbstractManager class is an abstract class that is used to manage a list of entries.
 * To use this class, you must extend it and implement the init() and shutdown() methods.
 * The init() method is called when the manager is created and the shutdown() method is called when the manager is destroyed.
 *
 * @param <T> The type of the entries.
 * @author dev-vince
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class AbstractManager<T> {
    private final List<T> entries;

    protected AbstractManager() {
        this.entries = new LinkedList<>();
    }

    public abstract void initialize();

    public abstract void shutdown();

    public void register(final T entry) {
        this.entries.add(entry);
    }

    public void unregister(final T entry) {
        this.entries.remove(entry);
    }

    public List<T> getEntries() {
        return this.entries;
    }
}