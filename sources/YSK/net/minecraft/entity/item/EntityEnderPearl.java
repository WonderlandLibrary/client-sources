package net.minecraft.entity.item;

import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;
import net.minecraft.world.*;

public class EntityEnderPearl extends EntityThrowable
{
    private static final String[] I;
    private EntityLivingBase field_181555_c;
    
    @Override
    public void onUpdate() {
        final EntityLivingBase thrower = this.getThrower();
        if (thrower != null && thrower instanceof EntityPlayer && !thrower.isEntityAlive()) {
            this.setDead();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            super.onUpdate();
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("/>\u000b\u0002\r\u0018!'\u001a\u0001\"?!", "KQFmo");
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        final EntityLivingBase thrower = this.getThrower();
        if (movingObjectPosition.entityHit != null) {
            if (movingObjectPosition.entityHit == this.field_181555_c) {
                return;
            }
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), 0.0f);
        }
        int i = "".length();
        "".length();
        if (2 == 4) {
            throw null;
        }
        while (i < (0x44 ^ 0x64)) {
            this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0, this.posZ, this.rand.nextGaussian(), 0.0, this.rand.nextGaussian(), new int["".length()]);
            ++i;
        }
        if (!this.worldObj.isRemote) {
            if (thrower instanceof EntityPlayerMP) {
                final EntityPlayerMP entityPlayerMP = (EntityPlayerMP)thrower;
                if (entityPlayerMP.playerNetServerHandler.getNetworkManager().isChannelOpen() && entityPlayerMP.worldObj == this.worldObj && !entityPlayerMP.isPlayerSleeping()) {
                    if (this.rand.nextFloat() < 0.05f && this.worldObj.getGameRules().getBoolean(EntityEnderPearl.I["".length()])) {
                        final EntityEndermite entityEndermite = new EntityEndermite(this.worldObj);
                        entityEndermite.setSpawnedByPlayer(" ".length() != 0);
                        entityEndermite.setLocationAndAngles(thrower.posX, thrower.posY, thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
                        this.worldObj.spawnEntityInWorld(entityEndermite);
                    }
                    if (thrower.isRiding()) {
                        thrower.mountEntity(null);
                    }
                    thrower.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                    thrower.fallDistance = 0.0f;
                    thrower.attackEntityFrom(DamageSource.fall, 5.0f);
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                }
            }
            else if (thrower != null) {
                thrower.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                thrower.fallDistance = 0.0f;
            }
            this.setDead();
        }
    }
    
    public EntityEnderPearl(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    public EntityEnderPearl(final World world, final EntityLivingBase field_181555_c) {
        super(world, field_181555_c);
        this.field_181555_c = field_181555_c;
    }
    
    public EntityEnderPearl(final World world) {
        super(world);
    }
    
    static {
        I();
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
