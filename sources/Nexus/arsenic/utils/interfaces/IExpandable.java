package arsenic.utils.interfaces;

import java.util.Collection;

public interface IExpandable<T> {


    boolean isExpanded();
    void setExpanded(boolean expanded);
    Collection<T> getContents();

}
