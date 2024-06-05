package net.minecraft.src;

import java.util.concurrent.*;

class CallableTagCompound1 implements Callable
{
    final String field_82585_a;
    final NBTTagCompound theNBTTagCompound;
    
    CallableTagCompound1(final NBTTagCompound par1NBTTagCompound, final String par2Str) {
        this.theNBTTagCompound = par1NBTTagCompound;
        this.field_82585_a = par2Str;
    }
    
    public String func_82583_a() {
        return NBTBase.NBTTypes[NBTTagCompound.getTagMap(this.theNBTTagCompound).get(this.field_82585_a).getId()];
    }
    
    @Override
    public Object call() {
        return this.func_82583_a();
    }
}
