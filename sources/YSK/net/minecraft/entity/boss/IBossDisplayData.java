package net.minecraft.entity.boss;

import net.minecraft.util.*;

public interface IBossDisplayData
{
    IChatComponent getDisplayName();
    
    float getMaxHealth();
    
    float getHealth();
}
