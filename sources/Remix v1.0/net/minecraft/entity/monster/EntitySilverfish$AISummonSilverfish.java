package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.state.*;

class AISummonSilverfish extends EntityAIBase
{
    private EntitySilverfish field_179464_a;
    private int field_179463_b;
    
    AISummonSilverfish() {
        this.field_179464_a = EntitySilverfish.this;
    }
    
    public void func_179462_f() {
        if (this.field_179463_b == 0) {
            this.field_179463_b = 20;
        }
    }
    
    @Override
    public boolean shouldExecute() {
        return this.field_179463_b > 0;
    }
    
    @Override
    public void updateTask() {
        --this.field_179463_b;
        if (this.field_179463_b <= 0) {
            final World var1 = this.field_179464_a.worldObj;
            final Random var2 = this.field_179464_a.getRNG();
            final BlockPos var3 = new BlockPos(this.field_179464_a);
            for (int var4 = 0; var4 <= 5 && var4 >= -5; var4 = ((var4 <= 0) ? (1 - var4) : (0 - var4))) {
                for (int var5 = 0; var5 <= 10 && var5 >= -10; var5 = ((var5 <= 0) ? (1 - var5) : (0 - var5))) {
                    for (int var6 = 0; var6 <= 10 && var6 >= -10; var6 = ((var6 <= 0) ? (1 - var6) : (0 - var6))) {
                        final BlockPos var7 = var3.add(var5, var4, var6);
                        final IBlockState var8 = var1.getBlockState(var7);
                        if (var8.getBlock() == Blocks.monster_egg) {
                            if (var1.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                                var1.destroyBlock(var7, true);
                            }
                            else {
                                var1.setBlockState(var7, ((BlockSilverfish.EnumType)var8.getValue(BlockSilverfish.VARIANT_PROP)).func_176883_d(), 3);
                            }
                            if (var2.nextBoolean()) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
