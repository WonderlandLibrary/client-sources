package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.projectile.*;

public class EntityMinecartTNT extends EntityMinecart
{
    private int minecartTNTFuse;
    private static final String[] I;
    
    public void ignite() {
        this.minecartTNTFuse = (0xF2 ^ 0xA2);
        if (!this.worldObj.isRemote) {
            this.worldObj.setEntityState(this, (byte)(0x44 ^ 0x4E));
            if (!this.isSilent()) {
                this.worldObj.playSoundAtEntity(this, EntityMinecartTNT.I[" ".length()], 1.0f, 1.0f);
            }
        }
    }
    
    @Override
    public boolean verifyExplosion(final Explosion explosion, final World world, final BlockPos blockPos, final IBlockState blockState, final float n) {
        int n2;
        if (!this.isIgnited() || (!BlockRailBase.isRailBlock(blockState) && !BlockRailBase.isRailBlock(world, blockPos.up()))) {
            n2 = (super.verifyExplosion(explosion, world, blockPos, blockState, n) ? 1 : 0);
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n2 != 0;
    }
    
    @Override
    public void killMinecart(final DamageSource damageSource) {
        super.killMinecart(damageSource);
        final double n = this.motionX * this.motionX + this.motionZ * this.motionZ;
        if (!damageSource.isExplosion() && this.worldObj.getGameRules().getBoolean(EntityMinecartTNT.I["".length()])) {
            this.entityDropItem(new ItemStack(Blocks.tnt, " ".length()), 0.0f);
        }
        if (damageSource.isFireDamage() || damageSource.isExplosion() || n >= 0.009999999776482582) {
            this.explodeCart(n);
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
            if (3 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getFuseTicks() {
        return this.minecartTNTFuse;
    }
    
    public boolean isIgnited() {
        if (this.minecartTNTFuse > -" ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0x6F ^ 0x65)) {
            this.ignite();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    public EntityMinecartTNT(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.minecartTNTFuse = -" ".length();
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.minecartTNTFuse > 0) {
            this.minecartTNTFuse -= " ".length();
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0, new int["".length()]);
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else if (this.minecartTNTFuse == 0) {
            this.explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
        }
        if (this.isCollidedHorizontally) {
            final double n = this.motionX * this.motionX + this.motionZ * this.motionZ;
            if (n >= 0.009999999776482582) {
                this.explodeCart(n);
            }
        }
    }
    
    @Override
    public IBlockState getDefaultDisplayTile() {
        return Blocks.tnt.getDefaultState();
    }
    
    @Override
    public float getExplosionResistance(final Explosion explosion, final World world, final BlockPos blockPos, final IBlockState blockState) {
        float explosionResistance;
        if (!this.isIgnited() || (!BlockRailBase.isRailBlock(blockState) && !BlockRailBase.isRailBlock(world, blockPos.up()))) {
            explosionResistance = super.getExplosionResistance(explosion, world, blockPos, blockState);
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            explosionResistance = 0.0f;
        }
        return explosionResistance;
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(EntityMinecartTNT.I[0x1C ^ 0x18], this.minecartTNTFuse);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        final Entity sourceOfDamage = damageSource.getSourceOfDamage();
        if (sourceOfDamage instanceof EntityArrow) {
            final EntityArrow entityArrow = (EntityArrow)sourceOfDamage;
            if (entityArrow.isBurning()) {
                this.explodeCart(entityArrow.motionX * entityArrow.motionX + entityArrow.motionY * entityArrow.motionY + entityArrow.motionZ * entityArrow.motionZ);
            }
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    private static void I() {
        (I = new String[0x35 ^ 0x30])["".length()] = I("\u0014+\u001c9\r\u00190 \u0013\u000b\u001f4*", "pDYWy");
        EntityMinecartTNT.I[" ".length()] = I("\u000b\b+\"~\u0018\u00072i \u001e\u0000+\"4", "liFGP");
        EntityMinecartTNT.I["  ".length()] = I("\u0016\u0007=4\u001e1,", "BIirk");
        EntityMinecartTNT.I["   ".length()] = I("0\n\u0007%0\u0017!", "dDScE");
        EntityMinecartTNT.I[0x48 ^ 0x4C] = I("\u0019?5\u0010\u001e>\u0014", "MqaVk");
    }
    
    static {
        I();
    }
    
    public EntityMinecartTNT(final World world) {
        super(world);
        this.minecartTNTFuse = -" ".length();
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(EntityMinecartTNT.I["  ".length()], 0x40 ^ 0x23)) {
            this.minecartTNTFuse = nbtTagCompound.getInteger(EntityMinecartTNT.I["   ".length()]);
        }
    }
    
    @Override
    public void fall(final float n, final float n2) {
        if (n >= 3.0f) {
            final float n3 = n / 10.0f;
            this.explodeCart(n3 * n3);
        }
        super.fall(n, n2);
    }
    
    @Override
    public EnumMinecartType getMinecartType() {
        return EnumMinecartType.TNT;
    }
    
    @Override
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
        if (b && this.minecartTNTFuse < 0) {
            this.ignite();
        }
    }
    
    protected void explodeCart(final double n) {
        if (!this.worldObj.isRemote) {
            double sqrt = Math.sqrt(n);
            if (sqrt > 5.0) {
                sqrt = 5.0;
            }
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(4.0 + this.rand.nextDouble() * 1.5 * sqrt), " ".length() != 0);
            this.setDead();
        }
    }
}
