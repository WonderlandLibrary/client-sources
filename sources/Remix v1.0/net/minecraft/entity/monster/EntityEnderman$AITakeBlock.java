package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

class AITakeBlock extends EntityAIBase
{
    private EntityEnderman field_179473_a;
    
    AITakeBlock() {
        this.field_179473_a = EntityEnderman.this;
    }
    
    @Override
    public boolean shouldExecute() {
        return this.field_179473_a.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") && this.field_179473_a.func_175489_ck().getBlock().getMaterial() == Material.air && this.field_179473_a.getRNG().nextInt(20) == 0;
    }
    
    @Override
    public void updateTask() {
        final Random var1 = this.field_179473_a.getRNG();
        final World var2 = this.field_179473_a.worldObj;
        final int var3 = MathHelper.floor_double(this.field_179473_a.posX - 2.0 + var1.nextDouble() * 4.0);
        final int var4 = MathHelper.floor_double(this.field_179473_a.posY + var1.nextDouble() * 3.0);
        final int var5 = MathHelper.floor_double(this.field_179473_a.posZ - 2.0 + var1.nextDouble() * 4.0);
        final BlockPos var6 = new BlockPos(var3, var4, var5);
        final IBlockState var7 = var2.getBlockState(var6);
        final Block var8 = var7.getBlock();
        if (EntityEnderman.access$300().contains(var8)) {
            this.field_179473_a.func_175490_a(var7);
            var2.setBlockState(var6, Blocks.air.getDefaultState());
        }
    }
}
