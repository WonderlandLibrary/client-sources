package net.java.games.input;

public static class Axis extends Identifier
{
    public static final Axis X;
    public static final Axis Y;
    public static final Axis Z;
    public static final Axis RX;
    public static final Axis RY;
    public static final Axis RZ;
    public static final Axis SLIDER;
    public static final Axis SLIDER_ACCELERATION;
    public static final Axis SLIDER_FORCE;
    public static final Axis SLIDER_VELOCITY;
    public static final Axis X_ACCELERATION;
    public static final Axis X_FORCE;
    public static final Axis X_VELOCITY;
    public static final Axis Y_ACCELERATION;
    public static final Axis Y_FORCE;
    public static final Axis Y_VELOCITY;
    public static final Axis Z_ACCELERATION;
    public static final Axis Z_FORCE;
    public static final Axis Z_VELOCITY;
    public static final Axis RX_ACCELERATION;
    public static final Axis RX_FORCE;
    public static final Axis RX_VELOCITY;
    public static final Axis RY_ACCELERATION;
    public static final Axis RY_FORCE;
    public static final Axis RY_VELOCITY;
    public static final Axis RZ_ACCELERATION;
    public static final Axis RZ_FORCE;
    public static final Axis RZ_VELOCITY;
    public static final Axis POV;
    public static final Axis UNKNOWN;
    
    protected Axis(final String name) {
        super(name);
    }
    
    static {
        X = new Axis("x");
        Y = new Axis("y");
        Z = new Axis("z");
        RX = new Axis("rx");
        RY = new Axis("ry");
        RZ = new Axis("rz");
        SLIDER = new Axis("slider");
        SLIDER_ACCELERATION = new Axis("slider-acceleration");
        SLIDER_FORCE = new Axis("slider-force");
        SLIDER_VELOCITY = new Axis("slider-velocity");
        X_ACCELERATION = new Axis("x-acceleration");
        X_FORCE = new Axis("x-force");
        X_VELOCITY = new Axis("x-velocity");
        Y_ACCELERATION = new Axis("y-acceleration");
        Y_FORCE = new Axis("y-force");
        Y_VELOCITY = new Axis("y-velocity");
        Z_ACCELERATION = new Axis("z-acceleration");
        Z_FORCE = new Axis("z-force");
        Z_VELOCITY = new Axis("z-velocity");
        RX_ACCELERATION = new Axis("rx-acceleration");
        RX_FORCE = new Axis("rx-force");
        RX_VELOCITY = new Axis("rx-velocity");
        RY_ACCELERATION = new Axis("ry-acceleration");
        RY_FORCE = new Axis("ry-force");
        RY_VELOCITY = new Axis("ry-velocity");
        RZ_ACCELERATION = new Axis("rz-acceleration");
        RZ_FORCE = new Axis("rz-force");
        RZ_VELOCITY = new Axis("rz-velocity");
        POV = new Axis("pov");
        UNKNOWN = new Axis("unknown");
    }
}
