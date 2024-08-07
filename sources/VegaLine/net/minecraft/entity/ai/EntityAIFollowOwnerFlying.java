/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.BlockPos;

public class EntityAIFollowOwnerFlying
extends EntityAIFollowOwner {
    public EntityAIFollowOwnerFlying(EntityTameable p_i47416_1_, double p_i47416_2_, float p_i47416_4_, float p_i47416_5_) {
        super(p_i47416_1_, p_i47416_2_, p_i47416_4_, p_i47416_5_);
    }

    @Override
    protected boolean func_192381_a(int p_192381_1_, int p_192381_2_, int p_192381_3_, int p_192381_4_, int p_192381_5_) {
        IBlockState iblockstate = this.theWorld.getBlockState(new BlockPos(p_192381_1_ + p_192381_4_, p_192381_3_ - 1, p_192381_2_ + p_192381_5_));
        return (iblockstate.isFullyOpaque() || iblockstate.getMaterial() == Material.LEAVES) && this.theWorld.isAirBlock(new BlockPos(p_192381_1_ + p_192381_4_, p_192381_3_, p_192381_2_ + p_192381_5_)) && this.theWorld.isAirBlock(new BlockPos(p_192381_1_ + p_192381_4_, p_192381_3_ + 1, p_192381_2_ + p_192381_5_));
    }
}

