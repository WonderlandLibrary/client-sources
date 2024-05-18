package baritone.api.utils.input;

public enum Input
{
    MOVE_FORWARD, 
    MOVE_BACK, 
    MOVE_LEFT, 
    MOVE_RIGHT, 
    CLICK_LEFT, 
    CLICK_RIGHT, 
    JUMP, 
    SNEAK, 
    SPRINT;
    
    private static final Input[] $VALUES;
    
    public static Input[] values() {
        return Input.$VALUES.clone();
    }
    
    public static Input valueOf(final String s) {
        return Enum.valueOf(Input.class, s);
    }
    
    static {
        $VALUES = new Input[] { Input.MOVE_FORWARD, Input.MOVE_BACK, Input.MOVE_LEFT, Input.MOVE_RIGHT, Input.CLICK_LEFT, Input.CLICK_RIGHT, Input.JUMP, Input.SNEAK, Input.SPRINT };
    }
}
