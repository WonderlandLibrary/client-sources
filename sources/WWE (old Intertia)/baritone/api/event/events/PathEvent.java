package baritone.api.event.events;

public enum PathEvent
{
    CALC_STARTED, 
    CALC_FINISHED_NOW_EXECUTING, 
    CALC_FAILED, 
    NEXT_SEGMENT_CALC_STARTED, 
    NEXT_SEGMENT_CALC_FINISHED, 
    CONTINUING_ONTO_PLANNED_NEXT, 
    SPLICING_ONTO_NEXT_EARLY, 
    AT_GOAL, 
    PATH_FINISHED_NEXT_STILL_CALCULATING, 
    NEXT_CALC_FAILED, 
    DISCARD_NEXT, 
    CANCELED;
    
    private static final PathEvent[] $VALUES;
    
    public static PathEvent[] values() {
        return PathEvent.$VALUES.clone();
    }
    
    public static PathEvent valueOf(final String s) {
        return Enum.valueOf(PathEvent.class, s);
    }
    
    static {
        $VALUES = new PathEvent[] { PathEvent.CALC_STARTED, PathEvent.CALC_FINISHED_NOW_EXECUTING, PathEvent.CALC_FAILED, PathEvent.NEXT_SEGMENT_CALC_STARTED, PathEvent.NEXT_SEGMENT_CALC_FINISHED, PathEvent.CONTINUING_ONTO_PLANNED_NEXT, PathEvent.SPLICING_ONTO_NEXT_EARLY, PathEvent.AT_GOAL, PathEvent.PATH_FINISHED_NEXT_STILL_CALCULATING, PathEvent.NEXT_CALC_FAILED, PathEvent.DISCARD_NEXT, PathEvent.CANCELED };
    }
}
