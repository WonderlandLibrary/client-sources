package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntitySnowball extends EntityThrowable
{
    public EntitySnowball(final World world, final EntityLivingBase entityLivingBase) {
        super(world, entityLivingBase);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (movingObjectPosition.entityHit != null) {
            int n = "".length();
            if (movingObjectPosition.entityHit instanceof EntityBlaze) {
                n = "   ".length();
            }
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), n);
        }
        int i = "".length();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (i < (0xCB ^ 0xC3)) {
            this.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, new int["".length()]);
            ++i;
        }
        if (!this.worldObj.isRemote) {
            this.setDead();
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
            if (3 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntitySnowball(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    public EntitySnowball(final World world) {
        super(world);
    }
}
