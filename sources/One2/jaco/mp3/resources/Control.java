// 
// Decompiled by Procyon v0.5.30
// 

package jaco.mp3.resources;

public interface Control
{
    void start();
    
    void stop();
    
    boolean isPlaying();
    
    void pause();
    
    boolean isRandomAccess();
    
    double getPosition();
    
    void setPosition(final double p0);
}
