package net.minecraft.tileentity;

import net.minecraft.inventory.*;
import net.minecraft.world.*;

public interface IHopper extends IInventory
{
    double getYPos();
    
    World getWorld();
    
    double getZPos();
    
    double getXPos();
}
