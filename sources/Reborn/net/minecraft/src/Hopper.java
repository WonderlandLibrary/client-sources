package net.minecraft.src;

public interface Hopper extends IInventory
{
    World getWorldObj();
    
    double getXPos();
    
    double getYPos();
    
    double getZPos();
}
