package net.minecraft.src;

import java.util.concurrent.*;

class CallableEntityType implements Callable
{
    final Entity theEntity;
    
    CallableEntityType(final Entity par1Entity) {
        this.theEntity = par1Entity;
    }
    
    public String callEntityType() {
        return String.valueOf(EntityList.getEntityString(this.theEntity)) + " (" + this.theEntity.getClass().getCanonicalName() + ")";
    }
    
    @Override
    public Object call() {
        return this.callEntityType();
    }
}
