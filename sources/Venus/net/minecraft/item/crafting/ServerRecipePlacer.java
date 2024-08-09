/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipePlacer;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.play.server.SPlaceGhostRecipePacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerRecipePlacer<C extends IInventory>
implements IRecipePlacer<Integer> {
    protected static final Logger LOGGER = LogManager.getLogger();
    protected final RecipeItemHelper recipeItemHelper = new RecipeItemHelper();
    protected PlayerInventory playerInventory;
    protected RecipeBookContainer<C> recipeBookContainer;

    public ServerRecipePlacer(RecipeBookContainer<C> recipeBookContainer) {
        this.recipeBookContainer = recipeBookContainer;
    }

    public void place(ServerPlayerEntity serverPlayerEntity, @Nullable IRecipe<C> iRecipe, boolean bl) {
        if (iRecipe != null && serverPlayerEntity.getRecipeBook().isUnlocked(iRecipe)) {
            this.playerInventory = serverPlayerEntity.inventory;
            if (this.placeIntoInventory() || serverPlayerEntity.isCreative()) {
                this.recipeItemHelper.clear();
                serverPlayerEntity.inventory.accountStacks(this.recipeItemHelper);
                this.recipeBookContainer.fillStackedContents(this.recipeItemHelper);
                if (this.recipeItemHelper.canCraft(iRecipe, null)) {
                    this.tryPlaceRecipe(iRecipe, bl);
                } else {
                    this.clear();
                    serverPlayerEntity.connection.sendPacket(new SPlaceGhostRecipePacket(serverPlayerEntity.openContainer.windowId, iRecipe));
                }
                serverPlayerEntity.inventory.markDirty();
            }
        }
    }

    protected void clear() {
        for (int i = 0; i < this.recipeBookContainer.getWidth() * this.recipeBookContainer.getHeight() + 1; ++i) {
            if (i == this.recipeBookContainer.getOutputSlot() && (this.recipeBookContainer instanceof WorkbenchContainer || this.recipeBookContainer instanceof PlayerContainer)) continue;
            this.giveToPlayer(i);
        }
        this.recipeBookContainer.clear();
    }

    protected void giveToPlayer(int n) {
        ItemStack itemStack = this.recipeBookContainer.getSlot(n).getStack();
        if (!itemStack.isEmpty()) {
            while (itemStack.getCount() > 0) {
                int n2 = this.playerInventory.storeItemStack(itemStack);
                if (n2 == -1) {
                    n2 = this.playerInventory.getFirstEmptyStack();
                }
                ItemStack itemStack2 = itemStack.copy();
                itemStack2.setCount(1);
                if (!this.playerInventory.add(n2, itemStack2)) {
                    LOGGER.error("Can't find any space for item in the inventory");
                }
                this.recipeBookContainer.getSlot(n).decrStackSize(1);
            }
        }
    }

    protected void tryPlaceRecipe(IRecipe<C> iRecipe, boolean bl) {
        Object object;
        int n;
        boolean bl2 = this.recipeBookContainer.matches(iRecipe);
        int n2 = this.recipeItemHelper.getBiggestCraftableStack(iRecipe, null);
        if (bl2) {
            for (n = 0; n < this.recipeBookContainer.getHeight() * this.recipeBookContainer.getWidth() + 1; ++n) {
                if (n == this.recipeBookContainer.getOutputSlot() || ((ItemStack)(object = this.recipeBookContainer.getSlot(n).getStack())).isEmpty() || Math.min(n2, ((ItemStack)object).getMaxStackSize()) >= ((ItemStack)object).getCount() + 1) continue;
                return;
            }
        }
        if (this.recipeItemHelper.canCraft(iRecipe, (IntList)(object = new IntArrayList()), n = this.getMaxAmount(bl, n2, bl2))) {
            int n3 = n;
            IntListIterator intListIterator = object.iterator();
            while (intListIterator.hasNext()) {
                int n4 = (Integer)intListIterator.next();
                int n5 = RecipeItemHelper.unpack(n4).getMaxStackSize();
                if (n5 >= n3) continue;
                n3 = n5;
            }
            if (this.recipeItemHelper.canCraft(iRecipe, (IntList)object, n3)) {
                this.clear();
                this.placeRecipe(this.recipeBookContainer.getWidth(), this.recipeBookContainer.getHeight(), this.recipeBookContainer.getOutputSlot(), iRecipe, object.iterator(), n3);
            }
        }
    }

    @Override
    public void setSlotContents(Iterator<Integer> iterator2, int n, int n2, int n3, int n4) {
        Slot slot = this.recipeBookContainer.getSlot(n);
        ItemStack itemStack = RecipeItemHelper.unpack(iterator2.next());
        if (!itemStack.isEmpty()) {
            for (int i = 0; i < n2; ++i) {
                this.consumeIngredient(slot, itemStack);
            }
        }
    }

    protected int getMaxAmount(boolean bl, int n, boolean bl2) {
        int n2 = 1;
        if (bl) {
            n2 = n;
        } else if (bl2) {
            n2 = 64;
            for (int i = 0; i < this.recipeBookContainer.getWidth() * this.recipeBookContainer.getHeight() + 1; ++i) {
                ItemStack itemStack;
                if (i == this.recipeBookContainer.getOutputSlot() || (itemStack = this.recipeBookContainer.getSlot(i).getStack()).isEmpty() || n2 <= itemStack.getCount()) continue;
                n2 = itemStack.getCount();
            }
            if (n2 < 64) {
                ++n2;
            }
        }
        return n2;
    }

    protected void consumeIngredient(Slot slot, ItemStack itemStack) {
        ItemStack itemStack2;
        int n = this.playerInventory.findSlotMatchingUnusedItem(itemStack);
        if (n != -1 && !(itemStack2 = this.playerInventory.getStackInSlot(n).copy()).isEmpty()) {
            if (itemStack2.getCount() > 1) {
                this.playerInventory.decrStackSize(n, 1);
            } else {
                this.playerInventory.removeStackFromSlot(n);
            }
            itemStack2.setCount(1);
            if (slot.getStack().isEmpty()) {
                slot.putStack(itemStack2);
            } else {
                slot.getStack().grow(1);
            }
        }
    }

    private boolean placeIntoInventory() {
        ArrayList<ItemStack> arrayList = Lists.newArrayList();
        int n = this.getEmptyPlayerSlots();
        for (int i = 0; i < this.recipeBookContainer.getWidth() * this.recipeBookContainer.getHeight() + 1; ++i) {
            ItemStack itemStack;
            if (i == this.recipeBookContainer.getOutputSlot() || (itemStack = this.recipeBookContainer.getSlot(i).getStack().copy()).isEmpty()) continue;
            int n2 = this.playerInventory.storeItemStack(itemStack);
            if (n2 == -1 && arrayList.size() <= n) {
                for (ItemStack itemStack2 : arrayList) {
                    if (!itemStack2.isItemEqual(itemStack) || itemStack2.getCount() == itemStack2.getMaxStackSize() || itemStack2.getCount() + itemStack.getCount() > itemStack2.getMaxStackSize()) continue;
                    itemStack2.grow(itemStack.getCount());
                    itemStack.setCount(0);
                    break;
                }
                if (itemStack.isEmpty()) continue;
                if (arrayList.size() >= n) {
                    return true;
                }
                arrayList.add(itemStack);
                continue;
            }
            if (n2 != -1) continue;
            return true;
        }
        return false;
    }

    private int getEmptyPlayerSlots() {
        int n = 0;
        for (ItemStack itemStack : this.playerInventory.mainInventory) {
            if (!itemStack.isEmpty()) continue;
            ++n;
        }
        return n;
    }
}

