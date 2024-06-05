package net.minecraft.world;

import net.minecraft.world.border.*;

class WorldProviderHell$1 extends WorldBorder {
    @Override
    public double getCenterX() {
        return super.getCenterX() / 8.0;
    }
    
    @Override
    public double getCenterZ() {
        return super.getCenterZ() / 8.0;
    }
}