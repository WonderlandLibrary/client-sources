package me.kaimson.melonclient.config.utils;

public enum AnchorPoint
{
    TOP_LEFT(0), 
    TOP_CENTER(1), 
    TOP_RIGHT(2), 
    BOTTOM_LEFT(3), 
    BOTTOM_CENTER(4), 
    BOTTOM_RIGHT(5), 
    CENTER_LEFT(6), 
    CENTER(7), 
    CENTER_RIGHT(8);
    
    private final int id;
    
    private AnchorPoint(final int id) {
        this.id = id;
    }
    
    public static AnchorPoint fromId(final int id) {
        for (final AnchorPoint ap : values()) {
            if (ap.getId() == id) {
                return ap;
            }
        }
        return null;
    }
    
    public int getX(final int maxX) {
        int x = 0;
        switch (this) {
            case TOP_RIGHT:
            case BOTTOM_RIGHT:
            case CENTER_RIGHT: {
                x = maxX;
                break;
            }
            case BOTTOM_CENTER:
            case CENTER:
            case TOP_CENTER: {
                x = maxX / 2;
                break;
            }
            default: {
                x = 0;
                break;
            }
        }
        return x;
    }
    
    public int getY(final int maxY) {
        int y = 0;
        switch (this) {
            case BOTTOM_RIGHT:
            case BOTTOM_CENTER:
            case BOTTOM_LEFT: {
                y = maxY;
                break;
            }
            case CENTER_RIGHT:
            case CENTER:
            case CENTER_LEFT: {
                y = maxY / 2;
                break;
            }
            default: {
                y = 0;
                break;
            }
        }
        return y;
    }
    
    public boolean isRightSide() {
        switch (this) {
            case TOP_RIGHT:
            case BOTTOM_RIGHT:
            case CENTER_RIGHT: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public int getId() {
        return this.id;
    }
}
