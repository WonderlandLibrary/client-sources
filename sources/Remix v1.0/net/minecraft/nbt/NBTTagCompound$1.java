package net.minecraft.nbt;

import java.util.concurrent.*;

class NBTTagCompound$1 implements Callable {
    final /* synthetic */ String val$key;
    
    @Override
    public String call() {
        return NBTBase.NBT_TYPES[NBTTagCompound.access$000(NBTTagCompound.this).get(this.val$key).getId()];
    }
}