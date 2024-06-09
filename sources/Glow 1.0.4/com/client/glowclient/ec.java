package com.client.glowclient;

import net.minecraft.nbt.*;

public class Ec extends Sd
{
    @Override
    public J M(final NBTTagCompound nbtTagCompound) {
        return null;
    }
    
    @Override
    public String D() {
        return "schematica.format.classic";
    }
    
    @Override
    public boolean M(final NBTTagCompound nbtTagCompound, final J j) {
        return false;
    }
    
    public Ec() {
        super();
    }
    
    @Override
    public String M() {
        return ".schematic";
    }
}
