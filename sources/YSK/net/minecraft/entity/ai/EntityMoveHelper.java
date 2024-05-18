package net.minecraft.entity.ai;

import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntityMoveHelper
{
    protected EntityLiving entity;
    protected double posX;
    protected boolean update;
    protected double posZ;
    protected double speed;
    protected double posY;
    
    public boolean isUpdating() {
        return this.update;
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setMoveTo(final double posX, final double posY, final double posZ, final double speed) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.speed = speed;
        this.update = (" ".length() != 0);
    }
    
    public double getX() {
        return this.posX;
    }
    
    public double getSpeed() {
        return this.speed;
    }
    
    protected float limitAngle(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > n3) {
            wrapAngleTo180_float = n3;
        }
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        float n4 = n + wrapAngleTo180_float;
        if (n4 < 0.0f) {
            n4 += 360.0f;
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else if (n4 > 360.0f) {
            n4 -= 360.0f;
        }
        return n4;
    }
    
    public double getZ() {
        return this.posZ;
    }
    
    public EntityMoveHelper(final EntityLiving entity) {
        this.entity = entity;
        this.posX = entity.posX;
        this.posY = entity.posY;
        this.posZ = entity.posZ;
    }
    
    public double getY() {
        return this.posY;
    }
    
    public void onUpdateMoveHelper() {
        this.entity.setMoveForward(0.0f);
        if (this.update) {
            this.update = ("".length() != 0);
            final int floor_double = MathHelper.floor_double(this.entity.getEntityBoundingBox().minY + 0.5);
            final double n = this.posX - this.entity.posX;
            final double n2 = this.posZ - this.entity.posZ;
            final double n3 = this.posY - floor_double;
            if (n * n + n3 * n3 + n2 * n2 >= 2.500000277905201E-7) {
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, (float)(MathHelper.func_181159_b(n2, n) * 180.0 / 3.141592653589793) - 90.0f, 30.0f);
                this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
                if (n3 > 0.0 && n * n + n2 * n2 < 1.0) {
                    this.entity.getJumpHelper().setJumping();
                }
            }
        }
    }
}
