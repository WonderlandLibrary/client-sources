package net.java.games.input;

public interface Rumbler
{
    void rumble(final float p0);
    
    String getAxisName();
    
    Component.Identifier getAxisIdentifier();
}
