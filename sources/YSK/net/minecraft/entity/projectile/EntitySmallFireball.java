package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class EntitySmallFireball extends EntityFireball
{
    private static final String[] I;
    
    public EntitySmallFireball(final World world, final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        super(world, entityLivingBase, n, n2, n3);
        this.setSize(0.3125f, 0.3125f);
    }
    
    static {
        I();
    }
    
    public EntitySmallFireball(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.setSize(0.3125f, 0.3125f);
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
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        return "".length() != 0;
    }
    
    public EntitySmallFireball(final World world) {
        super(world);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return "".length() != 0;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            if (movingObjectPosition.entityHit != null) {
                if (movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 5.0f)) {
                    this.applyEnchantments(this.shootingEntity, movingObjectPosition.entityHit);
                    if (!movingObjectPosition.entityHit.isImmuneToFire()) {
                        movingObjectPosition.entityHit.setFire(0x38 ^ 0x3D);
                        "".length();
                        if (1 >= 4) {
                            throw null;
                        }
                    }
                }
            }
            else {
                int n = " ".length();
                if (this.shootingEntity != null && this.shootingEntity instanceof EntityLiving) {
                    n = (this.worldObj.getGameRules().getBoolean(EntitySmallFireball.I["".length()]) ? 1 : 0);
                }
                if (n != 0) {
                    final BlockPos offset = movingObjectPosition.getBlockPos().offset(movingObjectPosition.sideHit);
                    if (this.worldObj.isAirBlock(offset)) {
                        this.worldObj.setBlockState(offset, Blocks.fire.getDefaultState());
                    }
                }
            }
            this.setDead();
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("(&\u0007)\u001e,,\u0003\u0007\u0002\"", "EIenl");
    }
}
