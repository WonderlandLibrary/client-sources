package net.minecraft.src;

import java.util.concurrent.*;

class CallableLvl3 implements Callable
{
    final World theWorld;
    
    CallableLvl3(final World par1World) {
        this.theWorld = par1World;
    }
    
    public String getChunkProvider() {
        return this.theWorld.chunkProvider.makeString();
    }
    
    @Override
    public Object call() {
        return this.getChunkProvider();
    }
}
