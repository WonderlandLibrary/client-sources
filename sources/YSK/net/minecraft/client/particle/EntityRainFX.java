package net.minecraft.client.particle;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;

public class EntityRainFX extends EntityFX
{
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected EntityRainFX(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.motionX *= 0.30000001192092896;
        this.motionY = Math.random() * 0.20000000298023224 + 0.10000000149011612;
        this.motionZ *= 0.30000001192092896;
        this.particleRed = 1.0f;
        this.particleGreen = 1.0f;
        this.particleBlue = 1.0f;
        this.setParticleTextureIndex((0x55 ^ 0x46) + this.rand.nextInt(0xB6 ^ 0xB2));
        this.setSize(0.01f, 0.01f);
        this.particleGravity = 0.06f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        final int particleMaxAge = this.particleMaxAge;
        this.particleMaxAge = particleMaxAge - " ".length();
        if (particleMaxAge <= 0) {
            this.setDead();
        }
        if (this.onGround) {
            if (Math.random() < 0.5) {
                this.setDead();
            }
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
        final BlockPos blockPos = new BlockPos(this);
        final IBlockState blockState = this.worldObj.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        block.setBlockBoundsBasedOnState(this.worldObj, blockPos);
        final Material material = blockState.getBlock().getMaterial();
        if (material.isLiquid() || material.isSolid()) {
            double blockBoundsMaxY;
            if (blockState.getBlock() instanceof BlockLiquid) {
                blockBoundsMaxY = 1.0f - BlockLiquid.getLiquidHeightPercent(blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL));
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else {
                blockBoundsMaxY = block.getBlockBoundsMaxY();
            }
            if (this.posY < MathHelper.floor_double(this.posY) + blockBoundsMaxY) {
                this.setDead();
            }
        }
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityRainFX(world, n2, n3, n4);
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
