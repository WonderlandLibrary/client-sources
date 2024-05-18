package baritone.api.utils;

public enum PathCalculationResult$Type
{
    SUCCESS_TO_GOAL, 
    SUCCESS_SEGMENT, 
    FAILURE, 
    CANCELLATION, 
    EXCEPTION;
    
    private static final PathCalculationResult$Type[] $VALUES;
    
    public static PathCalculationResult$Type[] values() {
        return PathCalculationResult$Type.$VALUES.clone();
    }
    
    public static PathCalculationResult$Type valueOf(final String s) {
        return Enum.valueOf(PathCalculationResult$Type.class, s);
    }
    
    static {
        $VALUES = new PathCalculationResult$Type[] { PathCalculationResult$Type.SUCCESS_TO_GOAL, PathCalculationResult$Type.SUCCESS_SEGMENT, PathCalculationResult$Type.FAILURE, PathCalculationResult$Type.CANCELLATION, PathCalculationResult$Type.EXCEPTION };
    }
}
