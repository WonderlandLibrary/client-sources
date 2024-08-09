/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class EnderChestTileEntity
extends TileEntity
implements IChestLid,
ITickableTileEntity {
    public float lidAngle;
    public float prevLidAngle;
    public int numPlayersUsing;
    private int ticksSinceSync;

    public EnderChestTileEntity() {
        super(TileEntityType.ENDER_CHEST);
    }

    @Override
    public void tick() {
        double d;
        if (++this.ticksSinceSync % 20 * 4 == 0) {
            this.world.addBlockEvent(this.pos, Blocks.ENDER_CHEST, 1, this.numPlayersUsing);
        }
        this.prevLidAngle = this.lidAngle;
        int n = this.pos.getX();
        int n2 = this.pos.getY();
        int n3 = this.pos.getZ();
        float f = 0.1f;
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0f) {
            double d2 = (double)n + 0.5;
            d = (double)n3 + 0.5;
            this.world.playSound(null, d2, (double)n2 + 0.5, d, SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.BLOCKS, 0.5f, this.world.rand.nextFloat() * 0.1f + 0.9f);
        }
        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0f || this.numPlayersUsing > 0 && this.lidAngle < 1.0f) {
            float f2 = this.lidAngle;
            this.lidAngle = this.numPlayersUsing > 0 ? (this.lidAngle += 0.1f) : (this.lidAngle -= 0.1f);
            if (this.lidAngle > 1.0f) {
                this.lidAngle = 1.0f;
            }
            float f3 = 0.5f;
            if (this.lidAngle < 0.5f && f2 >= 0.5f) {
                d = (double)n + 0.5;
                double d3 = (double)n3 + 0.5;
                this.world.playSound(null, d, (double)n2 + 0.5, d3, SoundEvents.BLOCK_ENDER_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5f, this.world.rand.nextFloat() * 0.1f + 0.9f);
            }
            if (this.lidAngle < 0.0f) {
                this.lidAngle = 0.0f;
            }
        }
    }

    @Override
    public boolean receiveClientEvent(int n, int n2) {
        if (n == 1) {
            this.numPlayersUsing = n2;
            return false;
        }
        return super.receiveClientEvent(n, n2);
    }

    @Override
    public void remove() {
        this.updateContainingBlockInfo();
        super.remove();
    }

    public void openChest() {
        ++this.numPlayersUsing;
        this.world.addBlockEvent(this.pos, Blocks.ENDER_CHEST, 1, this.numPlayersUsing);
    }

    public void closeChest() {
        --this.numPlayersUsing;
        this.world.addBlockEvent(this.pos, Blocks.ENDER_CHEST, 1, this.numPlayersUsing);
    }

    public boolean canBeUsed(PlayerEntity playerEntity) {
        if (this.world.getTileEntity(this.pos) != this) {
            return true;
        }
        return !(playerEntity.getDistanceSq((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) > 64.0);
    }

    @Override
    public float getLidAngle(float f) {
        return MathHelper.lerp(f, this.prevLidAngle, this.lidAngle);
    }
}

