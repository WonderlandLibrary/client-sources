package net.minecraft.inventory;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryBasic implements IInventory {
	private String inventoryTitle;
	private final int slotsCount;
	private final ItemStack[] inventoryContents;
	private List<IInventoryChangedListener> changeListeners;
	private boolean hasCustomName;

	public InventoryBasic(String title, boolean customName, int slotCount) {
		this.inventoryTitle = title;
		this.hasCustomName = customName;
		this.slotsCount = slotCount;
		this.inventoryContents = new ItemStack[slotCount];
	}

	public InventoryBasic(ITextComponent title, int slotCount) {
		this(title.getUnformattedText(), true, slotCount);
	}

	/**
	 * Add a listener that will be notified when any item in this inventory is modified.
	 */
	public void addInventoryChangeListener(IInventoryChangedListener listener) {
		if (this.changeListeners == null) {
			this.changeListeners = Lists.<IInventoryChangedListener> newArrayList();
		}

		this.changeListeners.add(listener);
	}

	/**
	 * removes the specified IInvBasic from receiving further change notices
	 */
	public void removeInventoryChangeListener(IInventoryChangedListener listener) {
		this.changeListeners.remove(listener);
	}

	@Override
	@Nullable

	/**
	 * Returns the stack in the given slot.
	 */
	public ItemStack getStackInSlot(int index) {
		return (index >= 0) && (index < this.inventoryContents.length) ? this.inventoryContents[index] : null;
	}

	@Override
	@Nullable

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.inventoryContents, index, count);

		if (itemstack != null) {
			this.markDirty();
		}

		return itemstack;
	}

	@Nullable
	public ItemStack addItem(ItemStack stack) {
		ItemStack itemstack = stack.copy();

		for (int i = 0; i < this.slotsCount; ++i) {
			ItemStack itemstack1 = this.getStackInSlot(i);

			if (itemstack1 == null) {
				this.setInventorySlotContents(i, itemstack);
				this.markDirty();
				return null;
			}

			if (ItemStack.areItemsEqual(itemstack1, itemstack)) {
				int j = Math.min(this.getInventoryStackLimit(), itemstack1.getMaxStackSize());
				int k = Math.min(itemstack.stackSize, j - itemstack1.stackSize);

				if (k > 0) {
					itemstack1.stackSize += k;
					itemstack.stackSize -= k;

					if (itemstack.stackSize <= 0) {
						this.markDirty();
						return null;
					}
				}
			}
		}

		if (itemstack.stackSize != stack.stackSize) {
			this.markDirty();
		}

		return itemstack;
	}

	@Override
	@Nullable

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	public ItemStack removeStackFromSlot(int index) {
		if (this.inventoryContents[index] != null) {
			ItemStack itemstack = this.inventoryContents[index];
			this.inventoryContents[index] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
		this.inventoryContents[index] = stack;

		if ((stack != null) && (stack.stackSize > this.getInventoryStackLimit())) {
			stack.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return this.slotsCount;
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return this.inventoryTitle;
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName() {
		return this.hasCustomName;
	}

	/**
	 * Sets the name of this inventory. This is displayed to the client on opening.
	 */
	public void setCustomName(String inventoryTitleIn) {
		this.hasCustomName = true;
		this.inventoryTitle = inventoryTitleIn;
	}

	/**
	 * Get the formatted ChatComponent that will be used for the sender's username in chat
	 */
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]);
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
	 */
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	/**
	 * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it hasn't changed and skip it.
	 */
	@Override
	public void markDirty() {
		if (this.changeListeners != null) {
			for (int i = 0; i < this.changeListeners.size(); ++i) {
				this.changeListeners.get(i).onInventoryChanged(this);
			}
		}
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
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
	public void clear() {
		for (int i = 0; i < this.inventoryContents.length; ++i) {
			this.inventoryContents[i] = null;
		}
	}
}
