package net.minecraft.client.particle;

import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;

public class EntityDropParticleFX extends EntityFX
{
    private Material materialType;
    private int bobTimer;
    
    @Override
    public int getBrightnessForRender(final float n) {
        int brightnessForRender;
        if (this.materialType == Material.water) {
            brightnessForRender = super.getBrightnessForRender(n);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            brightnessForRender = 221 + 140 - 166 + 62;
        }
        return brightnessForRender;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.materialType == Material.water) {
            this.particleRed = 0.2f;
            this.particleGreen = 0.3f;
            this.particleBlue = 1.0f;
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            this.particleRed = 1.0f;
            this.particleGreen = 16.0f / ((0x3D ^ 0x15) - this.bobTimer + (0x6A ^ 0x7A));
            this.particleBlue = 4.0f / ((0x16 ^ 0x3E) - this.bobTimer + (0xA ^ 0x2));
        }
        this.motionY -= this.particleGravity;
        final int bobTimer = this.bobTimer;
        this.bobTimer = bobTimer - " ".length();
        if (bobTimer > 0) {
            this.motionX *= 0.02;
            this.motionY *= 0.02;
            this.motionZ *= 0.02;
            this.setParticleTextureIndex(0xE3 ^ 0x92);
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            this.setParticleTextureIndex(0xCB ^ 0xBB);
        }
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
            if (this.materialType == Material.water) {
                this.setDead();
                this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, new int["".length()]);
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            else {
                this.setParticleTextureIndex(0xE4 ^ 0x96);
            }
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
        final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(this));
        final Material material = blockState.getBlock().getMaterial();
        if (material.isLiquid() || material.isSolid()) {
            double n = 0.0;
            if (blockState.getBlock() instanceof BlockLiquid) {
                n = BlockLiquid.getLiquidHeightPercent(blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL));
            }
            if (this.posY < MathHelper.floor_double(this.posY) + " ".length() - n) {
                this.setDead();
            }
        }
    }
    
    protected EntityDropParticleFX(final World world, final double n, final double n2, final double n3, final Material materialType) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        if (materialType == Material.water) {
            this.particleRed = 0.0f;
            this.particleGreen = 0.0f;
            this.particleBlue = 1.0f;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            this.particleRed = 1.0f;
            this.particleGreen = 0.0f;
            this.particleBlue = 0.0f;
        }
        this.setParticleTextureIndex(0x4A ^ 0x3B);
        this.setSize(0.01f, 0.01f);
        this.particleGravity = 0.06f;
        this.materialType = materialType;
        this.bobTimer = (0x18 ^ 0x30);
        this.particleMaxAge = (int)(64.0 / (Math.random() * 0.8 + 0.2));
        final double motionX2 = 0.0;
        this.motionZ = motionX2;
        this.motionY = motionX2;
        this.motionX = motionX2;
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public float getBrightness(final float n) {
        float brightness;
        if (this.materialType == Material.water) {
            brightness = super.getBrightness(n);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            brightness = 1.0f;
        }
        return brightness;
    }
    
    public static class LavaFactory implements IParticleFactory
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
                if (-1 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityDropParticleFX(world, n2, n3, n4, Material.lava);
        }
    }
    
    public static class WaterFactory implements IParticleFactory
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
                if (-1 != -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityDropParticleFX(world, n2, n3, n4, Material.water);
        }
    }
}
