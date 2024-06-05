package net.minecraft.client.renderer;

import net.minecraft.util.*;

public class DestroyBlockProgress
{
    private final int miningPlayerEntId;
    private final BlockPos field_180247_b;
    private int partialBlockProgress;
    private int createdAtCloudUpdateTick;
    
    public DestroyBlockProgress(final int p_i45925_1_, final BlockPos p_i45925_2_) {
        this.miningPlayerEntId = p_i45925_1_;
        this.field_180247_b = p_i45925_2_;
    }
    
    public BlockPos func_180246_b() {
        return this.field_180247_b;
    }
    
    public int getPartialBlockDamage() {
        return this.partialBlockProgress;
    }
    
    public void setPartialBlockDamage(int damage) {
        if (damage > 10) {
            damage = 10;
        }
        this.partialBlockProgress = damage;
    }
    
    public void setCloudUpdateTick(final int p_82744_1_) {
        this.createdAtCloudUpdateTick = p_82744_1_;
    }
    
    public int getCreationCloudUpdateTick() {
        return this.createdAtCloudUpdateTick;
    }
}
