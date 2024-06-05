package cn.Hanabi.injection.interfaces;

public interface IC03PacketPlayer
{
    boolean isOnGround();
    
    void setOnGround(final boolean p0);
    
    void setPosY(final double p0);
    
    void setYaw(final float p0);
    
    float getYaw();
    
    void setPitch(final float p0);
    
    float getPitch();
    
    void setRotate(final boolean p0);
    
    boolean getRotate();
}
