package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComparator extends TileEntity {
	private int outputSignal;

	@Override
	public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
		super.func_189515_b(p_189515_1_);
		p_189515_1_.setInteger("OutputSignal", this.outputSignal);
		return p_189515_1_;
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
