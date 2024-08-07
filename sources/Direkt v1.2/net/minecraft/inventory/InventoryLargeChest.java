package net.minecraft.inventory;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class InventoryLargeChest implements ILockableContainer {
	/** Name of the chest. */
	private final String name;

	/** Inventory object corresponding to double chest upper part */
	private final ILockableContainer upperChest;

	/** Inventory object corresponding to double chest lower part */
	private final ILockableContainer lowerChest;

	public InventoryLargeChest(String nameIn, ILockableContainer upperChestIn, ILockableContainer lowerChestIn) {
		this.name = nameIn;

		if (upperChestIn == null) {
			upperChestIn = lowerChestIn;
		}

		if (lowerChestIn == null) {
			lowerChestIn = upperChestIn;
		}

		this.upperChest = upperChestIn;
		this.lowerChest = lowerChestIn;

		if (upperChestIn.isLocked()) {
			lowerChestIn.setLockCode(upperChestIn.getLockCode());
		} else if (lowerChestIn.isLocked()) {
			upperChestIn.setLockCode(lowerChestIn.getLockCode());
		}
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
	}

	/**
	 * Return whether the given inventory is part of this large chest.
	 */
	public boolean isPartOfLargeChest(IInventory inventoryIn) {
		return (this.upperChest == inventoryIn) || (this.lowerChest == inventoryIn);
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return this.upperChest.hasCustomName() ? this.upperChest.getName() : (this.lowerChest.hasCustomName() ? this.lowerChest.getName() : this.name);
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName() {
		return this.upperChest.hasCustomName() || this.lowerChest.hasCustomName();
	}

	/**
	 * Get the formatted ChatComponent that will be used for the sender's username in chat
	 */
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]);
	}

	@Override
	@Nullable

	/**
	 * Returns the stack in the given slot.
	 */
	public ItemStack getStackInSlot(int index) {
		return index >= this.upperChest.getSizeInventory() ? this.lowerChest.getStackInSlot(index - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlot(index);
	}

	@Override
	@Nullable

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	public ItemStack decrStackSize(int index, int count) {
		return index >= this.upperChest.getSizeInventory() ? this.lowerChest.decrStackSize(index - this.upperChest.getSizeInventory(), count) : this.upperChest.decrStackSize(index, count);
	}

	@Override
	@Nullable

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	public ItemStack removeStackFromSlot(int index) {
		return index >= this.upperChest.getSizeInventory() ? this.lowerChest.removeStackFromSlot(index - this.upperChest.getSizeInventory()) : this.upperChest.removeStackFromSlot(index);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
		if (index >= this.upperChest.getSizeInventory()) {
			this.lowerChest.setInventorySlotContents(index - this.upperChest.getSizeInventory(), stack);
		} else {
			this.upperChest.setInventorySlotContents(index, stack);
		}
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
	 */
	@Override
	public int getInventoryStackLimit() {
		return this.upperChest.getInventoryStackLimit();
	}

	/**
	 * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it hasn't changed and skip it.
	 */
	@Override
	public void markDirty() {
		this.upperChest.markDirty();
		this.lowerChest.markDirty();
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.upperChest.isUseableByPlayer(player) && this.lowerChest.isUseableByPlayer(player);
	}

	@Override
	public void openInventory(EntityPlayer player) {
		this.upperChest.openInventory(player);
		this.lowerChest.openInventory(player);
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		this.upperChest.closeInventory(player);
		this.lowerChest.closeInventory(player);
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public boolean isLocked() {
		return this.upperChest.isLocked() || this.lowerChest.isLocked();
	}

	@Override
	public void setLockCode(LockCode code) {
		this.upperChest.setLockCode(code);
		this.lowerChest.setLockCode(code);
	}

	@Override
	public LockCode getLockCode() {
		return this.upperChest.getLockCode();
	}

	@Override
	public String getGuiID() {
		return this.upperChest.getGuiID();
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerChest(playerInventory, this, playerIn);
	}

	@Override
	public void clear() {
		this.upperChest.clear();
		this.lowerChest.clear();
	}
}
