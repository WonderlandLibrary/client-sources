package baritone.api.process;

import java.util.function.*;
import java.util.*;

public interface IFollowProcess extends IBaritoneProcess
{
    void follow(final Predicate<vg> p0);
    
    List<vg> following();
    
    Predicate<vg> currentFilter();
    
    default void cancel() {
        this.onLostControl();
    }
}
