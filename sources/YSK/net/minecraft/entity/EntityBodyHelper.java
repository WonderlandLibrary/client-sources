package net.minecraft.entity;

import net.minecraft.util.*;

public class EntityBodyHelper
{
    private int rotationTickCounter;
    private float prevRenderYawHead;
    private EntityLivingBase theLiving;
    
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityBodyHelper(final EntityLivingBase theLiving) {
        this.theLiving = theLiving;
    }
    
    private float computeAngleWithBound(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n - n2);
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        if (wrapAngleTo180_float >= n3) {
            wrapAngleTo180_float = n3;
        }
        return n - wrapAngleTo180_float;
    }
    
    public void updateRenderAngles() {
        final double n = this.theLiving.posX - this.theLiving.prevPosX;
        final double n2 = this.theLiving.posZ - this.theLiving.prevPosZ;
        if (n * n + n2 * n2 > 2.500000277905201E-7) {
            this.theLiving.renderYawOffset = this.theLiving.rotationYaw;
            this.theLiving.rotationYawHead = this.computeAngleWithBound(this.theLiving.renderYawOffset, this.theLiving.rotationYawHead, 75.0f);
            this.prevRenderYawHead = this.theLiving.rotationYawHead;
            this.rotationTickCounter = "".length();
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else {
            float n3 = 75.0f;
            if (Math.abs(this.theLiving.rotationYawHead - this.prevRenderYawHead) > 15.0f) {
                this.rotationTickCounter = "".length();
                this.prevRenderYawHead = this.theLiving.rotationYawHead;
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            else {
                this.rotationTickCounter += " ".length();
                if (this.rotationTickCounter > (0x9E ^ 0x94)) {
                    n3 = Math.max(1.0f - (this.rotationTickCounter - (0x5F ^ 0x55)) / 10.0f, 0.0f) * 75.0f;
                }
            }
            this.theLiving.renderYawOffset = this.computeAngleWithBound(this.theLiving.rotationYawHead, this.theLiving.renderYawOffset, n3);
        }
    }
}
