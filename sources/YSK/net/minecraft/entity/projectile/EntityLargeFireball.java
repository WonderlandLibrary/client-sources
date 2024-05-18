package net.minecraft.entity.projectile;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;

public class EntityLargeFireball extends EntityFireball
{
    private static final String[] I;
    public int explosionPower;
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            if (movingObjectPosition.entityHit != null) {
                movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6.0f);
                this.applyEnchantments(this.shootingEntity, movingObjectPosition.entityHit);
            }
            final boolean boolean1 = this.worldObj.getGameRules().getBoolean(EntityLargeFireball.I["".length()]);
            this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, this.explosionPower, boolean1, boolean1);
            this.setDead();
        }
    }
    
    public EntityLargeFireball(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.explosionPower = " ".length();
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
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(EntityLargeFireball.I["  ".length()], 0xD5 ^ 0xB6)) {
            this.explosionPower = nbtTagCompound.getInteger(EntityLargeFireball.I["   ".length()]);
        }
    }
    
    static {
        I();
    }
    
    public EntityLargeFireball(final World world) {
        super(world);
        this.explosionPower = " ".length();
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(EntityLargeFireball.I[" ".length()], this.explosionPower);
    }
    
    private static void I() {
        (I = new String[0x8D ^ 0x89])["".length()] = I("7\u0015\b$\"3\u001f\f\n>=", "ZzjcP");
        EntityLargeFireball.I[" ".length()] = I("6.7#\u0003\u0000?(!<\u001c!\"=", "sVGOl");
        EntityLargeFireball.I["  ".length()] = I("0!%\u001e5\u00060:\u001c\n\u001a.0\u0000", "uYUrZ");
        EntityLargeFireball.I["   ".length()] = I("=.\u0016!\u0000\u000b?\t#?\u0017!\u0003?", "xVfMo");
    }
    
    public EntityLargeFireball(final World world, final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        super(world, entityLivingBase, n, n2, n3);
        this.explosionPower = " ".length();
    }
}
