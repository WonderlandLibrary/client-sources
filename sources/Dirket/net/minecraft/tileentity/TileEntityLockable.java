package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public abstract class TileEntityLockable extends TileEntity implements IInteractionObject, ILockableContainer {
	private LockCode code = LockCode.EMPTY_CODE;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.code = LockCode.fromNBT(compound);
	}

	@Override
	public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
		super.func_189515_b(p_189515_1_);

		if (this.code != null) {
			this.code.toNBT(p_189515_1_);
		}

		return p_189515_1_;
	}

	@Override
	public boolean isLocked() {
		return (this.code != null) && !this.code.isEmpty();
	}

	@Override
	public LockCode getLockCode() {
		return this.code;
	}

	@Override
	public void setLockCode(LockCode code) {
		this.code = code;
	}

	/**
	 * Get the formatted ChatComponent that will be used for the sender's username in chat
	 */
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]);
	}
}
