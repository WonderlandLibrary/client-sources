/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CampfireTileEntity
extends TileEntity
implements IClearable,
ITickableTileEntity {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    private final int[] cookingTimes = new int[4];
    private final int[] cookingTotalTimes = new int[4];

    public CampfireTileEntity() {
        super(TileEntityType.CAMPFIRE);
    }

    @Override
    public void tick() {
        boolean bl = this.getBlockState().get(CampfireBlock.LIT);
        boolean bl2 = this.world.isRemote;
        if (bl2) {
            if (bl) {
                this.addParticles();
            }
        } else if (bl) {
            this.cookAndDrop();
        } else {
            for (int i = 0; i < this.inventory.size(); ++i) {
                if (this.cookingTimes[i] <= 0) continue;
                this.cookingTimes[i] = MathHelper.clamp(this.cookingTimes[i] - 2, 0, this.cookingTotalTimes[i]);
            }
        }
    }

    private void cookAndDrop() {
        for (int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemStack = this.inventory.get(i);
            if (itemStack.isEmpty()) continue;
            int n = i;
            this.cookingTimes[n] = this.cookingTimes[n] + 1;
            if (this.cookingTimes[i] < this.cookingTotalTimes[i]) continue;
            Inventory inventory = new Inventory(itemStack);
            ItemStack itemStack2 = this.world.getRecipeManager().getRecipe(IRecipeType.CAMPFIRE_COOKING, inventory, this.world).map(arg_0 -> CampfireTileEntity.lambda$cookAndDrop$0(inventory, arg_0)).orElse(itemStack);
            BlockPos blockPos = this.getPos();
            InventoryHelper.spawnItemStack(this.world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack2);
            this.inventory.set(i, ItemStack.EMPTY);
            this.inventoryChanged();
        }
    }

    private void addParticles() {
        World world = this.getWorld();
        if (world != null) {
            int n;
            BlockPos blockPos = this.getPos();
            Random random2 = world.rand;
            if (random2.nextFloat() < 0.11f) {
                for (n = 0; n < random2.nextInt(2) + 2; ++n) {
                    CampfireBlock.spawnSmokeParticles(world, blockPos, this.getBlockState().get(CampfireBlock.SIGNAL_FIRE), false);
                }
            }
            n = this.getBlockState().get(CampfireBlock.FACING).getHorizontalIndex();
            for (int i = 0; i < this.inventory.size(); ++i) {
                if (this.inventory.get(i).isEmpty() || !(random2.nextFloat() < 0.2f)) continue;
                Direction direction = Direction.byHorizontalIndex(Math.floorMod(i + n, 4));
                float f = 0.3125f;
                double d = (double)blockPos.getX() + 0.5 - (double)((float)direction.getXOffset() * 0.3125f) + (double)((float)direction.rotateY().getXOffset() * 0.3125f);
                double d2 = (double)blockPos.getY() + 0.5;
                double d3 = (double)blockPos.getZ() + 0.5 - (double)((float)direction.getZOffset() * 0.3125f) + (double)((float)direction.rotateY().getZOffset() * 0.3125f);
                for (int j = 0; j < 4; ++j) {
                    world.addParticle(ParticleTypes.SMOKE, d, d2, d3, 0.0, 5.0E-4, 0.0);
                }
            }
        }
    }

    public NonNullList<ItemStack> getInventory() {
        return this.inventory;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        int[] nArray;
        super.read(blockState, compoundNBT);
        this.inventory.clear();
        ItemStackHelper.loadAllItems(compoundNBT, this.inventory);
        if (compoundNBT.contains("CookingTimes", 0)) {
            nArray = compoundNBT.getIntArray("CookingTimes");
            System.arraycopy(nArray, 0, this.cookingTimes, 0, Math.min(this.cookingTotalTimes.length, nArray.length));
        }
        if (compoundNBT.contains("CookingTotalTimes", 0)) {
            nArray = compoundNBT.getIntArray("CookingTotalTimes");
            System.arraycopy(nArray, 0, this.cookingTotalTimes, 0, Math.min(this.cookingTotalTimes.length, nArray.length));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        this.writeItems(compoundNBT);
        compoundNBT.putIntArray("CookingTimes", this.cookingTimes);
        compoundNBT.putIntArray("CookingTotalTimes", this.cookingTotalTimes);
        return compoundNBT;
    }

    private CompoundNBT writeItems(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        ItemStackHelper.saveAllItems(compoundNBT, this.inventory, true);
        return compoundNBT;
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 13, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.writeItems(new CompoundNBT());
    }

    public Optional<CampfireCookingRecipe> findMatchingRecipe(ItemStack itemStack) {
        return this.inventory.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.world.getRecipeManager().getRecipe(IRecipeType.CAMPFIRE_COOKING, new Inventory(itemStack), this.world);
    }

    public boolean addItem(ItemStack itemStack, int n) {
        for (int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemStack2 = this.inventory.get(i);
            if (!itemStack2.isEmpty()) continue;
            this.cookingTotalTimes[i] = n;
            this.cookingTimes[i] = 0;
            this.inventory.set(i, itemStack.split(1));
            this.inventoryChanged();
            return false;
        }
        return true;
    }

    private void inventoryChanged() {
        this.markDirty();
        this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    public void dropAllItems() {
        if (this.world != null) {
            if (!this.world.isRemote) {
                InventoryHelper.dropItems(this.world, this.getPos(), this.getInventory());
            }
            this.inventoryChanged();
        }
    }

    private static ItemStack lambda$cookAndDrop$0(IInventory iInventory, CampfireCookingRecipe campfireCookingRecipe) {
        return campfireCookingRecipe.getCraftingResult(iInventory);
    }
}

