package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class EntityEnderEye extends Entity
{
    private double targetX;
    private int despawnTimer;
    private double targetZ;
    private boolean shatterOrDrop;
    private double targetY;
    
    @Override
    public void setVelocity(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            final float n = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(MathHelper.func_181159_b(motionY, sqrt_double) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
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
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityEnderEye(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.despawnTimer = "".length();
        this.setSize(0.25f, 0.25f);
        this.setPosition(n, n2, n3);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(MathHelper.func_181159_b(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
        this.rotationPitch = (float)(MathHelper.func_181159_b(this.motionY, sqrt_double) * 180.0 / 3.141592653589793);
        "".length();
        if (1 <= -1) {
            throw null;
        }
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        "".length();
        if (2 == 0) {
            throw null;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        "".length();
        if (3 == 0) {
            throw null;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
        if (!this.worldObj.isRemote) {
            final double n = this.targetX - this.posX;
            final double n2 = this.targetZ - this.posZ;
            final float n3 = (float)Math.sqrt(n * n + n2 * n2);
            final float n4 = (float)MathHelper.func_181159_b(n2, n);
            double n5 = sqrt_double + (n3 - sqrt_double) * 0.0025;
            if (n3 < 1.0f) {
                n5 *= 0.8;
                this.motionY *= 0.8;
            }
            this.motionX = Math.cos(n4) * n5;
            this.motionZ = Math.sin(n4) * n5;
            if (this.posY < this.targetY) {
                this.motionY += (1.0 - this.motionY) * 0.014999999664723873;
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
            else {
                this.motionY += (-1.0 - this.motionY) * 0.014999999664723873;
            }
        }
        final float n6 = 0.25f;
        if (this.isInWater()) {
            int i = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
            while (i < (0xBD ^ 0xB9)) {
                this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * n6, this.posY - this.motionY * n6, this.posZ - this.motionZ * n6, this.motionX, this.motionY, this.motionZ, new int["".length()]);
                ++i;
            }
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX - this.motionX * n6 + this.rand.nextDouble() * 0.6 - 0.3, this.posY - this.motionY * n6 - 0.5, this.posZ - this.motionZ * n6 + this.rand.nextDouble() * 0.6 - 0.3, this.motionX, this.motionY, this.motionZ, new int["".length()]);
        }
        if (!this.worldObj.isRemote) {
            this.setPosition(this.posX, this.posY, this.posZ);
            this.despawnTimer += " ".length();
            if (this.despawnTimer > (0x90 ^ 0xC0) && !this.worldObj.isRemote) {
                this.setDead();
                if (this.shatterOrDrop) {
                    this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.ender_eye)));
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                }
                else {
                    this.worldObj.playAuxSFX(1979 + 1643 - 2261 + 642, new BlockPos(this), "".length());
                }
            }
        }
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        double n2 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        if (Double.isNaN(n2)) {
            n2 = 4.0;
        }
        final double n3 = n2 * 64.0;
        if (n < n3 * n3) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public EntityEnderEye(final World world) {
        super(world);
        this.setSize(0.25f, 0.25f);
    }
    
    @Override
    protected void entityInit() {
    }
    
    public void moveTowards(final BlockPos blockPos) {
        final double targetX = blockPos.getX();
        final int y = blockPos.getY();
        final double targetZ = blockPos.getZ();
        final double n = targetX - this.posX;
        final double n2 = targetZ - this.posZ;
        final float sqrt_double = MathHelper.sqrt_double(n * n + n2 * n2);
        if (sqrt_double > 12.0f) {
            this.targetX = this.posX + n / sqrt_double * 12.0;
            this.targetZ = this.posZ + n2 / sqrt_double * 12.0;
            this.targetY = this.posY + 8.0;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            this.targetX = targetX;
            this.targetY = y;
            this.targetZ = targetZ;
        }
        this.despawnTimer = "".length();
        int shatterOrDrop;
        if (this.rand.nextInt(0x29 ^ 0x2C) > 0) {
            shatterOrDrop = " ".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            shatterOrDrop = "".length();
        }
        this.shatterOrDrop = (shatterOrDrop != 0);
    }
    
    @Override
    public boolean canAttackWithItem() {
        return "".length() != 0;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 4395454 + 11584504 - 12852353 + 12601275;
    }
}
