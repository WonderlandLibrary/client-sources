package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;

class AIHideInStone extends EntityAIWander
{
    private EnumFacing field_179483_b;
    private boolean field_179484_c;
    
    public AIHideInStone() {
        super(EntitySilverfish.this, 1.0, 10);
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (EntitySilverfish.this.getAttackTarget() != null) {
            return false;
        }
        if (!EntitySilverfish.this.getNavigator().noPath()) {
            return false;
        }
        final Random var1 = EntitySilverfish.this.getRNG();
        if (var1.nextInt(10) == 0) {
            this.field_179483_b = EnumFacing.random(var1);
            final BlockPos var2 = new BlockPos(EntitySilverfish.this.posX, EntitySilverfish.this.posY + 0.5, EntitySilverfish.this.posZ).offset(this.field_179483_b);
            final IBlockState var3 = EntitySilverfish.this.worldObj.getBlockState(var2);
            if (BlockSilverfish.func_176377_d(var3)) {
                return this.field_179484_c = true;
            }
        }
        this.field_179484_c = false;
        return super.shouldExecute();
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.field_179484_c && super.continueExecuting();
    }
    
    @Override
    public void startExecuting() {
        if (!this.field_179484_c) {
            super.startExecuting();
        }
        else {
            final World var1 = EntitySilverfish.this.worldObj;
            final BlockPos var2 = new BlockPos(EntitySilverfish.this.posX, EntitySilverfish.this.posY + 0.5, EntitySilverfish.this.posZ).offset(this.field_179483_b);
            final IBlockState var3 = var1.getBlockState(var2);
            if (BlockSilverfish.func_176377_d(var3)) {
                var1.setBlockState(var2, Blocks.monster_egg.getDefaultState().withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.func_176878_a(var3)), 3);
                EntitySilverfish.this.spawnExplosionParticle();
                EntitySilverfish.this.setDead();
            }
        }
    }
}
