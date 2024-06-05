package net.java.games.input;

public static class Type
{
    private final String name;
    public static final Type UNKNOWN;
    public static final Type MOUSE;
    public static final Type KEYBOARD;
    public static final Type FINGERSTICK;
    public static final Type GAMEPAD;
    public static final Type HEADTRACKER;
    public static final Type RUDDER;
    public static final Type STICK;
    public static final Type TRACKBALL;
    public static final Type TRACKPAD;
    public static final Type WHEEL;
    
    protected Type(final String name) {
        this.name = name;
    }
    
    public String toString() {
        return this.name;
    }
    
    static {
        UNKNOWN = new Type("Unknown");
        MOUSE = new Type("Mouse");
        KEYBOARD = new Type("Keyboard");
        FINGERSTICK = new Type("Fingerstick");
        GAMEPAD = new Type("Gamepad");
        HEADTRACKER = new Type("Headtracker");
        RUDDER = new Type("Rudder");
        STICK = new Type("Stick");
        TRACKBALL = new Type("Trackball");
        TRACKPAD = new Type("Trackpad");
        WHEEL = new Type("Wheel");
    }
}
