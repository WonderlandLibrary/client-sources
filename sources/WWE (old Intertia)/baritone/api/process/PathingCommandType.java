package baritone.api.process;

public enum PathingCommandType
{
    SET_GOAL_AND_PATH, 
    REQUEST_PAUSE, 
    CANCEL_AND_SET_GOAL, 
    REVALIDATE_GOAL_AND_PATH, 
    FORCE_REVALIDATE_GOAL_AND_PATH;
    
    private static final PathingCommandType[] $VALUES;
    
    public static PathingCommandType[] values() {
        return PathingCommandType.$VALUES.clone();
    }
    
    public static PathingCommandType valueOf(final String s) {
        return Enum.valueOf(PathingCommandType.class, s);
    }
    
    static {
        $VALUES = new PathingCommandType[] { PathingCommandType.SET_GOAL_AND_PATH, PathingCommandType.REQUEST_PAUSE, PathingCommandType.CANCEL_AND_SET_GOAL, PathingCommandType.REVALIDATE_GOAL_AND_PATH, PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH };
    }
}
