package net.minecraft.world;

import net.minecraft.util.*;

public interface IWorldNameable
{
    IChatComponent getDisplayName();
    
    boolean hasCustomName();
    
    String getName();
}
