package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAILeapAtTarget extends EntityAIBase
{
    EntityLivingBase leapTarget;
    EntityLiving leaper;
    float leapMotionY;
    
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean shouldExecute() {
        this.leapTarget = this.leaper.getAttackTarget();
        if (this.leapTarget == null) {
            return "".length() != 0;
        }
        final double distanceSqToEntity = this.leaper.getDistanceSqToEntity(this.leapTarget);
        int n;
        if (distanceSqToEntity >= 4.0 && distanceSqToEntity <= 16.0) {
            if (!this.leaper.onGround) {
                n = "".length();
                "".length();
                if (-1 == 2) {
                    throw null;
                }
            }
            else if (this.leaper.getRNG().nextInt(0x95 ^ 0x90) == 0) {
                n = " ".length();
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public boolean continueExecuting() {
        int n;
        if (this.leaper.onGround) {
            n = "".length();
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public void startExecuting() {
        final double n = this.leapTarget.posX - this.leaper.posX;
        final double n2 = this.leapTarget.posZ - this.leaper.posZ;
        final float sqrt_double = MathHelper.sqrt_double(n * n + n2 * n2);
        final EntityLiving leaper = this.leaper;
        leaper.motionX += n / sqrt_double * 0.5 * 0.800000011920929 + this.leaper.motionX * 0.20000000298023224;
        final EntityLiving leaper2 = this.leaper;
        leaper2.motionZ += n2 / sqrt_double * 0.5 * 0.800000011920929 + this.leaper.motionZ * 0.20000000298023224;
        this.leaper.motionY = this.leapMotionY;
    }
    
    public EntityAILeapAtTarget(final EntityLiving leaper, final float leapMotionY) {
        this.leaper = leaper;
        this.leapMotionY = leapMotionY;
        this.setMutexBits(0xC7 ^ 0xC2);
    }
}
