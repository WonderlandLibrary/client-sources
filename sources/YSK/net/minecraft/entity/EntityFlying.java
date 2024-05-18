package net.minecraft.entity;

import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public abstract class EntityFlying extends EntityLiving
{
    public EntityFlying(final World world) {
        super(world);
    }
    
    @Override
    protected void updateFallState(final double n, final boolean b, final Block block, final BlockPos blockPos) {
    }
    
    @Override
    public void moveEntityWithHeading(final float n, final float n2) {
        if (this.isInWater()) {
            this.moveFlying(n, n2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.800000011920929;
            this.motionZ *= 0.800000011920929;
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (this.isInLava()) {
            this.moveFlying(n, n2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            float n3 = 0.91f;
            if (this.onGround) {
                n3 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - " ".length(), MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91f;
            }
            final float n4 = 0.16277136f / (n3 * n3 * n3);
            float n5;
            if (this.onGround) {
                n5 = 0.1f * n4;
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else {
                n5 = 0.02f;
            }
            this.moveFlying(n, n2, n5);
            float n6 = 0.91f;
            if (this.onGround) {
                n6 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - " ".length(), MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91f;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= n6;
            this.motionY *= n6;
            this.motionZ *= n6;
        }
        this.prevLimbSwingAmount = this.limbSwingAmount;
        final double n7 = this.posX - this.prevPosX;
        final double n8 = this.posZ - this.prevPosZ;
        float n9 = MathHelper.sqrt_double(n7 * n7 + n8 * n8) * 4.0f;
        if (n9 > 1.0f) {
            n9 = 1.0f;
        }
        this.limbSwingAmount += (n9 - this.limbSwingAmount) * 0.4f;
        this.limbSwing += this.limbSwingAmount;
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
            if (-1 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    public boolean isOnLadder() {
        return "".length() != 0;
    }
}
