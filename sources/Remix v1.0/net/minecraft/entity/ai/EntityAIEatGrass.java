package net.minecraft.entity.ai;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.block.*;
import com.google.common.base.*;
import net.minecraft.block.properties.*;

public class EntityAIEatGrass extends EntityAIBase
{
    private static final Predicate field_179505_b;
    int eatingGrassTimer;
    private EntityLiving grassEaterEntity;
    private World entityWorld;
    
    public EntityAIEatGrass(final EntityLiving p_i45314_1_) {
        this.grassEaterEntity = p_i45314_1_;
        this.entityWorld = p_i45314_1_.worldObj;
        this.setMutexBits(7);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.grassEaterEntity.getRNG().nextInt(this.grassEaterEntity.isChild() ? 50 : 1000) != 0) {
            return false;
        }
        final BlockPos var1 = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);
        return EntityAIEatGrass.field_179505_b.apply((Object)this.entityWorld.getBlockState(var1)) || this.entityWorld.getBlockState(var1.offsetDown()).getBlock() == Blocks.grass;
    }
    
    @Override
    public void startExecuting() {
        this.eatingGrassTimer = 40;
        this.entityWorld.setEntityState(this.grassEaterEntity, (byte)10);
        this.grassEaterEntity.getNavigator().clearPathEntity();
    }
    
    @Override
    public void resetTask() {
        this.eatingGrassTimer = 0;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.eatingGrassTimer > 0;
    }
    
    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }
    
    @Override
    public void updateTask() {
        this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
        if (this.eatingGrassTimer == 4) {
            final BlockPos var1 = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);
            if (EntityAIEatGrass.field_179505_b.apply((Object)this.entityWorld.getBlockState(var1))) {
                if (this.entityWorld.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    this.entityWorld.destroyBlock(var1, false);
                }
                this.grassEaterEntity.eatGrassBonus();
            }
            else {
                final BlockPos var2 = var1.offsetDown();
                if (this.entityWorld.getBlockState(var2).getBlock() == Blocks.grass) {
                    if (this.entityWorld.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                        this.entityWorld.playAuxSFX(2001, var2, Block.getIdFromBlock(Blocks.grass));
                        this.entityWorld.setBlockState(var2, Blocks.dirt.getDefaultState(), 2);
                    }
                    this.grassEaterEntity.eatGrassBonus();
                }
            }
        }
    }
    
    static {
        field_179505_b = (Predicate)BlockStateHelper.forBlock(Blocks.tallgrass).func_177637_a(BlockTallGrass.field_176497_a, Predicates.equalTo((Object)BlockTallGrass.EnumType.GRASS));
    }
}
