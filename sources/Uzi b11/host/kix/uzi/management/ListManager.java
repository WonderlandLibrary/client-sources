package host.kix.uzi.management;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myche on 2/3/2017.
 */
public class ListManager<T> {

    private List<T> contents;

    public ListManager() {
        this.contents = new ArrayList<T>();
    }

    public List<T> getContents() {
        return this.contents;
    }

    public void addContent(final T content) {
        this.contents.add(content);
    }

    public void removeContent(final T content) {
        this.contents.remove(content);
    }

    public boolean hasContent(final T content) {
        return this.contents.contains(content);
    }

}
