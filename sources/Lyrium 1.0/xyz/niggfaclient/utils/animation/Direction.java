// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.animation;

public enum Direction
{
    FORWARDS, 
    BACKWARDS;
    
    public Direction opposite() {
        if (this == Direction.FORWARDS) {
            return Direction.BACKWARDS;
        }
        return Direction.FORWARDS;
    }
}
