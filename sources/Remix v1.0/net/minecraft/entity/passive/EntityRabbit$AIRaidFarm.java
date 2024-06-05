package net.minecraft.entity.passive;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

class AIRaidFarm extends EntityAIMoveToBlock
{
    private boolean field_179498_d;
    private boolean field_179499_e;
    
    public AIRaidFarm() {
        super(EntityRabbit.this, 0.699999988079071, 16);
        this.field_179499_e = false;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.field_179496_a <= 0) {
            if (!EntityRabbit.this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                return false;
            }
            this.field_179499_e = false;
            this.field_179498_d = EntityRabbit.access$000(EntityRabbit.this);
        }
        return super.shouldExecute();
    }
    
    @Override
    public boolean continueExecuting() {
        return this.field_179499_e && super.continueExecuting();
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
        EntityRabbit.this.getLookHelper().setLookPosition(this.field_179494_b.getX() + 0.5, this.field_179494_b.getY() + 1, this.field_179494_b.getZ() + 0.5, 10.0f, (float)EntityRabbit.this.getVerticalFaceSpeed());
        if (this.func_179487_f()) {
            final World var1 = EntityRabbit.this.worldObj;
            final BlockPos var2 = this.field_179494_b.offsetUp();
            final IBlockState var3 = var1.getBlockState(var2);
            final Block var4 = var3.getBlock();
            if (this.field_179499_e && var4 instanceof BlockCarrot && (int)var3.getValue(BlockCarrot.AGE) == 7) {
                var1.setBlockState(var2, Blocks.air.getDefaultState(), 2);
                var1.destroyBlock(var2, true);
                EntityRabbit.this.func_175528_cn();
            }
            this.field_179499_e = false;
            this.field_179496_a = 10;
        }
    }
    
    @Override
    protected boolean func_179488_a(final World worldIn, BlockPos p_179488_2_) {
        Block var3 = worldIn.getBlockState(p_179488_2_).getBlock();
        if (var3 == Blocks.farmland) {
            p_179488_2_ = p_179488_2_.offsetUp();
            final IBlockState var4 = worldIn.getBlockState(p_179488_2_);
            var3 = var4.getBlock();
            if (var3 instanceof BlockCarrot && (int)var4.getValue(BlockCarrot.AGE) == 7 && this.field_179498_d && !this.field_179499_e) {
                return this.field_179499_e = true;
            }
        }
        return false;
    }
}
