package cn.Hanabi.injection.interfaces;

import cn.Hanabi.events.*;

public interface IEntityPlayerSP
{
    boolean moving();
    
    float getSpeed();
    
    void setSpeed(final double p0);
    
    void setMoveSpeed(final EventMove p0, final double p1);
    
    void setYaw(final double p0);
    
    void setPitch(final double p0);
    
    float getDirection();
    
    void setLastReportedPosY(final double p0);
}
