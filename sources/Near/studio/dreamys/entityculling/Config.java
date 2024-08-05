package studio.dreamys.entityculling;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Config {
    public static final boolean renderNametagsThroughWalls = true;
    public static final Set<String> blockEntityWhitelist = new HashSet<>(Collections.singletonList("tile.beacon"));
    public static final int tracingDistance = 128;
    public static final int sleepDelay = 10;
    public static final int hitboxLimit = 50;
    public static final boolean skipMarkerArmorStands = true;
}
