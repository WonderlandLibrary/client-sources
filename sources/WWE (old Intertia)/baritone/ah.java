package baritone;

import baritone.api.pathing.movement.*;
import baritone.api.utils.input.*;
import java.util.*;

public final class ah
{
    public MovementStatus a;
    public ai a;
    final Map<Input, Boolean> a;
    
    public ah() {
        super();
        this.a = new ai();
        this.a = new HashMap<Input, Boolean>();
    }
    
    public final ah a(final Input input, final boolean b) {
        this.a.put(input, b);
        return this;
    }
}
