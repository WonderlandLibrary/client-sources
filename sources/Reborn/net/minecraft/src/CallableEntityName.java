package net.minecraft.src;

import java.util.concurrent.*;

class CallableEntityName implements Callable
{
    final Entity theEntity;
    
    CallableEntityName(final Entity par1Entity) {
        this.theEntity = par1Entity;
    }
    
    public String callEntityName() {
        return this.theEntity.getEntityName();
    }
    
    @Override
    public Object call() {
        return this.callEntityName();
    }
}
