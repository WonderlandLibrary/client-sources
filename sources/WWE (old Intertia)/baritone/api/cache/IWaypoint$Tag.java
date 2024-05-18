package baritone.api.cache;

import org.apache.commons.lang3.*;
import java.util.*;

public enum IWaypoint$Tag
{
    HOME(new String[] { "home", "base" }), 
    DEATH(new String[] { "death" }), 
    BED(new String[] { "bed", "spawn" }), 
    USER(new String[] { "user" });
    
    private static final List<IWaypoint$Tag> TAG_LIST;
    private final String[] names;
    private static final IWaypoint$Tag[] $VALUES;
    
    public static IWaypoint$Tag[] values() {
        return IWaypoint$Tag.$VALUES.clone();
    }
    
    public static IWaypoint$Tag valueOf(final String s) {
        return Enum.valueOf(IWaypoint$Tag.class, s);
    }
    
    private IWaypoint$Tag(final String[] names) {
        this.names = names;
    }
    
    public static IWaypoint$Tag fromString(final String s) {
        return IWaypoint$Tag.TAG_LIST.stream().filter(IWaypoint$Tag::lambda$fromString$0).findFirst().orElse(null);
    }
    
    private static boolean lambda$fromString$0(final String s, final IWaypoint$Tag waypoint$Tag) {
        return ArrayUtils.contains(waypoint$Tag.names, s.toLowerCase());
    }
    
    static {
        $VALUES = new IWaypoint$Tag[] { IWaypoint$Tag.HOME, IWaypoint$Tag.DEATH, IWaypoint$Tag.BED, IWaypoint$Tag.USER };
        TAG_LIST = Collections.unmodifiableList((List<? extends IWaypoint$Tag>)Arrays.asList((T[])values()));
    }
}
