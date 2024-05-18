package net.minecraft.entity.ai;

import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntityLookHelper
{
    private double posY;
    private double posX;
    private double posZ;
    private EntityLiving entity;
    private float deltaLookYaw;
    private float deltaLookPitch;
    private boolean isLooking;
    
    public boolean getIsLooking() {
        return this.isLooking;
    }
    
    public double getLookPosY() {
        return this.posY;
    }
    
    private float updateRotation(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > n3) {
            wrapAngleTo180_float = n3;
        }
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        return n + wrapAngleTo180_float;
    }
    
    public void setLookPositionWithEntity(final Entity entity, final float deltaLookYaw, final float deltaLookPitch) {
        this.posX = entity.posX;
        if (entity instanceof EntityLivingBase) {
            this.posY = entity.posY + entity.getEyeHeight();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            this.posY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0;
        }
        this.posZ = entity.posZ;
        this.deltaLookYaw = deltaLookYaw;
        this.deltaLookPitch = deltaLookPitch;
        this.isLooking = (" ".length() != 0);
    }
    
    public double getLookPosX() {
        return this.posX;
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
    
    public EntityLookHelper(final EntityLiving entity) {
        this.entity = entity;
    }
    
    public double getLookPosZ() {
        return this.posZ;
    }
    
    public void setLookPosition(final double posX, final double posY, final double posZ, final float deltaLookYaw, final float deltaLookPitch) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.deltaLookYaw = deltaLookYaw;
        this.deltaLookPitch = deltaLookPitch;
        this.isLooking = (" ".length() != 0);
    }
    
    public void onUpdateLook() {
        this.entity.rotationPitch = 0.0f;
        if (this.isLooking) {
            this.isLooking = ("".length() != 0);
            final double n = this.posX - this.entity.posX;
            final double n2 = this.posY - (this.entity.posY + this.entity.getEyeHeight());
            final double n3 = this.posZ - this.entity.posZ;
            final double n4 = MathHelper.sqrt_double(n * n + n3 * n3);
            final float n5 = (float)(MathHelper.func_181159_b(n3, n) * 180.0 / 3.141592653589793) - 90.0f;
            this.entity.rotationPitch = this.updateRotation(this.entity.rotationPitch, (float)(-(MathHelper.func_181159_b(n2, n4) * 180.0 / 3.141592653589793)), this.deltaLookPitch);
            this.entity.rotationYawHead = this.updateRotation(this.entity.rotationYawHead, n5, this.deltaLookYaw);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            this.entity.rotationYawHead = this.updateRotation(this.entity.rotationYawHead, this.entity.renderYawOffset, 10.0f);
        }
        final float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(this.entity.rotationYawHead - this.entity.renderYawOffset);
        if (!this.entity.getNavigator().noPath()) {
            if (wrapAngleTo180_float < -75.0f) {
                this.entity.rotationYawHead = this.entity.renderYawOffset - 75.0f;
            }
            if (wrapAngleTo180_float > 75.0f) {
                this.entity.rotationYawHead = this.entity.renderYawOffset + 75.0f;
            }
        }
    }
}
