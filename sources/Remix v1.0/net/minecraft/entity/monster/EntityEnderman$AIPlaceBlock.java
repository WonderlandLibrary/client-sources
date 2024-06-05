package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

class AIPlaceBlock extends EntityAIBase
{
    private EntityEnderman field_179475_a;
    
    AIPlaceBlock() {
        this.field_179475_a = EntityEnderman.this;
    }
    
    @Override
    public boolean shouldExecute() {
        return this.field_179475_a.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") && this.field_179475_a.func_175489_ck().getBlock().getMaterial() != Material.air && this.field_179475_a.getRNG().nextInt(2000) == 0;
    }
    
    @Override
    public void updateTask() {
        final Random var1 = this.field_179475_a.getRNG();
        final World var2 = this.field_179475_a.worldObj;
        final int var3 = MathHelper.floor_double(this.field_179475_a.posX - 1.0 + var1.nextDouble() * 2.0);
        final int var4 = MathHelper.floor_double(this.field_179475_a.posY + var1.nextDouble() * 2.0);
        final int var5 = MathHelper.floor_double(this.field_179475_a.posZ - 1.0 + var1.nextDouble() * 2.0);
        final BlockPos var6 = new BlockPos(var3, var4, var5);
        final Block var7 = var2.getBlockState(var6).getBlock();
        final Block var8 = var2.getBlockState(var6.offsetDown()).getBlock();
        if (this.func_179474_a(var2, var6, this.field_179475_a.func_175489_ck().getBlock(), var7, var8)) {
            var2.setBlockState(var6, this.field_179475_a.func_175489_ck(), 3);
            this.field_179475_a.func_175490_a(Blocks.air.getDefaultState());
        }
    }
    
    private boolean func_179474_a(final World worldIn, final BlockPos p_179474_2_, final Block p_179474_3_, final Block p_179474_4_, final Block p_179474_5_) {
        return p_179474_3_.canPlaceBlockAt(worldIn, p_179474_2_) && p_179474_4_.getMaterial() == Material.air && p_179474_5_.getMaterial() != Material.air && p_179474_5_.isFullCube();
    }
}
