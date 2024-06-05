package cn.Hanabi.injection.interfaces;

import net.minecraft.util.*;

public interface IEntity
{
    int getNextStepDistance();
    
    void setNextStepDistance(final int p0);
    
    int getFire();
    
    void setFire(final int p0);
    
    AxisAlignedBB getBoundingBox();
}
