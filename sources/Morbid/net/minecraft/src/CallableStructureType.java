package net.minecraft.src;

import java.util.concurrent.*;

class CallableStructureType implements Callable
{
    final MapGenStructure theMapStructureGenerator;
    
    CallableStructureType(final MapGenStructure par1MapGenStructure) {
        this.theMapStructureGenerator = par1MapGenStructure;
    }
    
    public String callStructureType() {
        return this.theMapStructureGenerator.getClass().getCanonicalName();
    }
    
    @Override
    public Object call() {
        return this.callStructureType();
    }
}
