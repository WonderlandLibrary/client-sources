package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class EntityTNTPrimed extends Entity
{
    private static final String[] I;
    private EntityLivingBase tntPlacedBy;
    public int fuse;
    
    @Override
    public float getEyeHeight() {
        return 0.0f;
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.fuse = nbtTagCompound.getByte(EntityTNTPrimed.I[" ".length()]);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
    
    public EntityLivingBase getTntPlacedBy() {
        return this.tntPlacedBy;
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte(EntityTNTPrimed.I["".length()], (byte)this.fuse);
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
            if (2 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
            this.motionY *= -0.5;
        }
        final int fuse = this.fuse;
        this.fuse = fuse - " ".length();
        if (fuse <= 0) {
            this.setDead();
            if (!this.worldObj.isRemote) {
                this.explode();
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
        }
        else {
            this.handleWaterMovement();
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0, new int["".length()]);
        }
    }
    
    private void explode() {
        this.worldObj.createExplosion(this, this.posX, this.posY + this.height / 16.0f, this.posZ, 4.0f, " ".length() != 0);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("0\u0002!3", "vwRVs");
        EntityTNTPrimed.I[" ".length()] = I("\u0013\u0007\u0000\u001c", "UrsyX");
    }
    
    static {
        I();
    }
    
    public EntityTNTPrimed(final World world) {
        super(world);
        this.preventEntitySpawning = (" ".length() != 0);
        this.setSize(0.98f, 0.98f);
    }
    
    public EntityTNTPrimed(final World world, final double prevPosX, final double prevPosY, final double prevPosZ, final EntityLivingBase tntPlacedBy) {
        this(world);
        this.setPosition(prevPosX, prevPosY, prevPosZ);
        final float n = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.motionX = -(float)Math.sin(n) * 0.02f;
        this.motionY = 0.20000000298023224;
        this.motionZ = -(float)Math.cos(n) * 0.02f;
        this.fuse = (0x5 ^ 0x55);
        this.prevPosX = prevPosX;
        this.prevPosY = prevPosY;
        this.prevPosZ = prevPosZ;
        this.tntPlacedBy = tntPlacedBy;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        int n;
        if (this.isDead) {
            n = "".length();
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
}
