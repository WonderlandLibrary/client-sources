/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityComparator
extends TileEntity {
    private int outputSignal;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("OutputSignal", this.outputSignal);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.outputSignal = compound.getInteger("OutputSignal");
    }

    public int getOutputSignal() {
        return this.outputSignal;
    }

    public void setOutputSignal(int outputSignalIn) {
        this.outputSignal = outputSignalIn;
    }
}

