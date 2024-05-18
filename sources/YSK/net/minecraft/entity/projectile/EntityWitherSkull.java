package net.minecraft.entity.projectile;

import net.minecraft.block.state.*;
import net.minecraft.entity.boss.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;

public class EntityWitherSkull extends EntityFireball
{
    private static final String[] I;
    
    public EntityWitherSkull(final World world, final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        super(world, entityLivingBase, n, n2, n3);
        this.setSize(0.3125f, 0.3125f);
    }
    
    public void setInvulnerable(final boolean b) {
        final DataWatcher dataWatcher = this.dataWatcher;
        final int n = 0x9F ^ 0x95;
        int n2;
        if (b) {
            n2 = " ".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        dataWatcher.updateObject(n, (byte)n2);
    }
    
    public EntityWitherSkull(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        return "".length() != 0;
    }
    
    @Override
    public boolean isBurning() {
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\"\u0007(\u0002'&\r,,;(", "OhJEU");
    }
    
    @Override
    public float getExplosionResistance(final Explosion explosion, final World world, final BlockPos blockPos, final IBlockState blockState) {
        float n = super.getExplosionResistance(explosion, world, blockPos, blockState);
        final Block block = blockState.getBlock();
        if (this.isInvulnerable() && EntityWither.func_181033_a(block)) {
            n = Math.min(0.8f, n);
        }
        return n;
    }
    
    public boolean isInvulnerable() {
        if (this.dataWatcher.getWatchableObjectByte(0xCF ^ 0xC5) == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            if (movingObjectPosition.entityHit != null) {
                if (this.shootingEntity != null) {
                    if (movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8.0f)) {
                        if (!movingObjectPosition.entityHit.isEntityAlive()) {
                            this.shootingEntity.heal(5.0f);
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                        }
                        else {
                            this.applyEnchantments(this.shootingEntity, movingObjectPosition.entityHit);
                            "".length();
                            if (0 == 4) {
                                throw null;
                            }
                        }
                    }
                }
                else {
                    movingObjectPosition.entityHit.attackEntityFrom(DamageSource.magic, 5.0f);
                }
                if (movingObjectPosition.entityHit instanceof EntityLivingBase) {
                    int length = "".length();
                    if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
                        length = (0x9E ^ 0x94);
                        "".length();
                        if (1 >= 2) {
                            throw null;
                        }
                    }
                    else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                        length = (0x81 ^ 0xA9);
                    }
                    if (length > 0) {
                        ((EntityLivingBase)movingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, (0x62 ^ 0x76) * length, " ".length()));
                    }
                }
            }
            this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0f, "".length() != 0, this.worldObj.getGameRules().getBoolean(EntityWitherSkull.I["".length()]));
            this.setDead();
        }
    }
    
    public EntityWitherSkull(final World world) {
        super(world);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(0x41 ^ 0x4B, (byte)"".length());
    }
    
    @Override
    protected float getMotionFactor() {
        float motionFactor;
        if (this.isInvulnerable()) {
            motionFactor = 0.73f;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            motionFactor = super.getMotionFactor();
        }
        return motionFactor;
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
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return "".length() != 0;
    }
    
    static {
        I();
    }
}
