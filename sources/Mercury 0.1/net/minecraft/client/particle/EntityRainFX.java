/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EntityRainFX
extends EntityFX {
    private static final String __OBFID = "CL_00000934";

    protected EntityRainFX(World worldIn, double p_i1235_2_, double p_i1235_4_, double p_i1235_6_) {
        super(worldIn, p_i1235_2_, p_i1235_4_, p_i1235_6_, 0.0, 0.0, 0.0);
        this.motionX *= 0.30000001192092896;
        this.motionY = Math.random() * 0.20000000298023224 + 0.10000000149011612;
        this.motionZ *= 0.30000001192092896;
        this.particleRed = 1.0f;
        this.particleGreen = 1.0f;
        this.particleBlue = 1.0f;
        this.setParticleTextureIndex(19 + this.rand.nextInt(4));
        this.setSize(0.01f, 0.01f);
        this.particleGravity = 0.06f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= (double)this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
        if (this.onGround) {
            if (Math.random() < 0.5) {
                this.setDead();
            }
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
        BlockPos var1 = new BlockPos(this);
        IBlockState var2 = this.worldObj.getBlockState(var1);
        Block var3 = var2.getBlock();
        var3.setBlockBoundsBasedOnState(this.worldObj, var1);
        Material var4 = var2.getBlock().getMaterial();
        if (var4.isLiquid() || var4.isSolid()) {
            double var5 = 0.0;
            var5 = var2.getBlock() instanceof BlockLiquid ? (double)(1.0f - BlockLiquid.getLiquidHeightPercent((Integer)var2.getValue(BlockLiquid.LEVEL))) : var3.getBlockBoundsMaxY();
            double var7 = (double)MathHelper.floor_double(this.posY) + var5;
            if (this.posY < var7) {
                this.setDead();
            }
        }
    }

    public static class Factory
    implements IParticleFactory {
        private static final String __OBFID = "CL_00002572";

        @Override
        public EntityFX func_178902_a(int p_178902_1_, World worldIn, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int ... p_178902_15_) {
            return new EntityRainFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_);
        }
    }

}

