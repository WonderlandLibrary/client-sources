/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.INameable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LockCode;

public abstract class LockableTileEntity
extends TileEntity
implements IInventory,
INamedContainerProvider,
INameable {
    private LockCode code = LockCode.EMPTY_CODE;
    private ITextComponent customName;

    protected LockableTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.code = LockCode.read(compoundNBT);
        if (compoundNBT.contains("CustomName", 1)) {
            this.customName = ITextComponent.Serializer.getComponentFromJson(compoundNBT.getString("CustomName"));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        this.code.write(compoundNBT);
        if (this.customName != null) {
            compoundNBT.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }
        return compoundNBT;
    }

    public void setCustomName(ITextComponent iTextComponent) {
        this.customName = iTextComponent;
    }

    @Override
    public ITextComponent getName() {
        return this.customName != null ? this.customName : this.getDefaultName();
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.getName();
    }

    @Override
    @Nullable
    public ITextComponent getCustomName() {
        return this.customName;
    }

    protected abstract ITextComponent getDefaultName();

    public boolean canOpen(PlayerEntity playerEntity) {
        return LockableTileEntity.canUnlock(playerEntity, this.code, this.getDisplayName());
    }

    public static boolean canUnlock(PlayerEntity playerEntity, LockCode lockCode, ITextComponent iTextComponent) {
        if (!playerEntity.isSpectator() && !lockCode.func_219964_a(playerEntity.getHeldItemMainhand())) {
            playerEntity.sendStatusMessage(new TranslationTextComponent("container.isLocked", iTextComponent), false);
            playerEntity.playSound(SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0f, 1.0f);
            return true;
        }
        return false;
    }

    @Override
    @Nullable
    public Container createMenu(int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return this.canOpen(playerEntity) ? this.createMenu(n, playerInventory) : null;
    }

    protected abstract Container createMenu(int var1, PlayerInventory var2);
}

