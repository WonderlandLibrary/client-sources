package net.minecraft.nbt;

import java.util.concurrent.*;

class NBTTagCompound$2 implements Callable {
    final /* synthetic */ int val$expectedType;
    
    @Override
    public String call() {
        return NBTBase.NBT_TYPES[this.val$expectedType];
    }
}