package net.minecraft.src;

import java.util.concurrent.*;

class CallableLvl2 implements Callable
{
    final World theWorld;
    
    CallableLvl2(final World par1World) {
        this.theWorld = par1World;
    }
    
    public String getPlayerEntities() {
        return String.valueOf(this.theWorld.playerEntities.size()) + " total; " + this.theWorld.playerEntities.toString();
    }
    
    @Override
    public Object call() {
        return this.getPlayerEntities();
    }
}
