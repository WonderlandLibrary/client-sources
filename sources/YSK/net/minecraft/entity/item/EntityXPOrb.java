package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class EntityXPOrb extends Entity
{
    private int xpOrbHealth;
    private static final String[] I;
    private int xpValue;
    public int xpOrbAge;
    public int xpColor;
    private EntityPlayer closestPlayer;
    public int delayBeforeCanPickup;
    private int xpTargetColor;
    
    private static void I() {
        (I = new String[0xCD ^ 0xC5])["".length()] = I("\u001a\u0002=>=\u0005M53(\u0012", "hcSZR");
        EntityXPOrb.I[" ".length()] = I("\u000f\u0015\u001b9=/", "GpzUI");
        EntityXPOrb.I["  ".length()] = I(")\u0001\n", "hfoPf");
        EntityXPOrb.I["   ".length()] = I("\u0010(\u0007-\u0012", "FIkXw");
        EntityXPOrb.I[0x35 ^ 0x31] = I("91\"<\u0004\u0019", "qTCPp");
        EntityXPOrb.I[0x3F ^ 0x3A] = I("9\u001f\u0012", "xxwSK");
        EntityXPOrb.I[0x7 ^ 0x1] = I("3\t\b&$", "ehdSA");
        EntityXPOrb.I[0x56 ^ 0x51] = I("$6>&\f;y?0\u0001", "VWPBc");
    }
    
    public static int getXPSplit(final int n) {
        int n2;
        if (n >= 806 + 1288 - 1111 + 1494) {
            n2 = 2189 + 1520 - 3084 + 1852;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else if (n >= 584 + 289 - 269 + 633) {
            n2 = 75 + 445 + 61 + 656;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else if (n >= 217 + 104 - 100 + 396) {
            n2 = 543 + 564 - 835 + 345;
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else if (n >= 119 + 28 + 93 + 67) {
            n2 = 135 + 154 - 27 + 45;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if (n >= 11 + 126 - 75 + 87) {
            n2 = 1 + 27 + 45 + 76;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (n >= (0xD4 ^ 0x9D)) {
            n2 = (0xFE ^ 0xB7);
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else if (n >= (0x8F ^ 0xAA)) {
            n2 = (0xA0 ^ 0x85);
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else if (n >= (0x35 ^ 0x24)) {
            n2 = (0xAD ^ 0xBC);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else if (n >= (0x55 ^ 0x52)) {
            n2 = (0xB2 ^ 0xB5);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (n >= "   ".length()) {
            n2 = "   ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        return n2;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        final float clamp_float = MathHelper.clamp_float(0.5f, 0.0f, 1.0f);
        final int brightnessForRender = super.getBrightnessForRender(n);
        final int n2 = brightnessForRender & 191 + 110 - 166 + 120;
        final int n3 = brightnessForRender >> (0x6A ^ 0x7A) & 248 + 233 - 313 + 87;
        int n4 = n2 + (int)(clamp_float * 15.0f * 16.0f);
        if (n4 > 32 + 100 + 81 + 27) {
            n4 = 51 + 104 + 51 + 34;
        }
        return n4 | n3 << (0x82 ^ 0x92);
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote && this.delayBeforeCanPickup == 0 && entityPlayer.xpCooldown == 0) {
            entityPlayer.xpCooldown = "  ".length();
            this.worldObj.playSoundAtEntity(entityPlayer, EntityXPOrb.I[0x8 ^ 0xF], 0.1f, 0.5f * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.8f));
            entityPlayer.onItemPickup(this, " ".length());
            entityPlayer.addExperience(this.xpValue);
            this.setDead();
        }
    }
    
    public int getXpValue() {
        return this.xpValue;
    }
    
    public EntityXPOrb(final World world, final double n, final double n2, final double n3, final int xpValue) {
        super(world);
        this.xpOrbHealth = (0x4B ^ 0x4E);
        this.setSize(0.5f, 0.5f);
        this.setPosition(n, n2, n3);
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.motionX = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612) * 2.0f;
        this.motionY = (float)(Math.random() * 0.2) * 2.0f;
        this.motionZ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612) * 2.0f;
        this.xpValue = xpValue;
    }
    
    @Override
    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.water, this);
    }
    
    static {
        I();
    }
    
    @Override
    protected void entityInit() {
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.xpOrbHealth = (nbtTagCompound.getShort(EntityXPOrb.I[0x50 ^ 0x54]) & 188 + 60 - 184 + 191);
        this.xpOrbAge = nbtTagCompound.getShort(EntityXPOrb.I[0x97 ^ 0x92]);
        this.xpValue = nbtTagCompound.getShort(EntityXPOrb.I[0x58 ^ 0x5E]);
    }
    
    @Override
    protected void dealFireDamage(final int n) {
        this.attackEntityFrom(DamageSource.inFire, n);
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
            if (0 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.delayBeforeCanPickup > 0) {
            this.delayBeforeCanPickup -= " ".length();
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.029999999329447746;
        if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava) {
            this.motionY = 0.20000000298023224;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.playSound(EntityXPOrb.I["".length()], 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
        }
        this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
        final double n = 8.0;
        if (this.xpTargetColor < this.xpColor - (0x2E ^ 0x3A) + this.getEntityId() % (0xA6 ^ 0xC2)) {
            if (this.closestPlayer == null || this.closestPlayer.getDistanceSqToEntity(this) > n * n) {
                this.closestPlayer = this.worldObj.getClosestPlayerToEntity(this, n);
            }
            this.xpTargetColor = this.xpColor;
        }
        if (this.closestPlayer != null && this.closestPlayer.isSpectator()) {
            this.closestPlayer = null;
        }
        if (this.closestPlayer != null) {
            final double n2 = (this.closestPlayer.posX - this.posX) / n;
            final double n3 = (this.closestPlayer.posY + this.closestPlayer.getEyeHeight() - this.posY) / n;
            final double n4 = (this.closestPlayer.posZ - this.posZ) / n;
            final double sqrt = Math.sqrt(n2 * n2 + n3 * n3 + n4 * n4);
            final double n5 = 1.0 - sqrt;
            if (n5 > 0.0) {
                final double n6 = n5 * n5;
                this.motionX += n2 / sqrt * n6 * 0.1;
                this.motionY += n3 / sqrt * n6 * 0.1;
                this.motionZ += n4 / sqrt * n6 * 0.1;
            }
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float n7 = 0.98f;
        if (this.onGround) {
            n7 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - " ".length(), MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.98f;
        }
        this.motionX *= n7;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= n7;
        if (this.onGround) {
            this.motionY *= -0.8999999761581421;
        }
        this.xpColor += " ".length();
        this.xpOrbAge += " ".length();
        if (this.xpOrbAge >= 1372 + 4908 - 4857 + 4577) {
            this.setDead();
        }
    }
    
    public int getTextureByXP() {
        int n;
        if (this.xpValue >= 1604 + 143 + 582 + 148) {
            n = (0x92 ^ 0x98);
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else if (this.xpValue >= 608 + 446 - 187 + 370) {
            n = (0x76 ^ 0x7F);
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else if (this.xpValue >= 28 + 44 + 304 + 241) {
            n = (0x32 ^ 0x3A);
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else if (this.xpValue >= 97 + 66 + 29 + 115) {
            n = (0x8F ^ 0x88);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else if (this.xpValue >= 11 + 9 + 106 + 23) {
            n = (0x70 ^ 0x76);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else if (this.xpValue >= (0x46 ^ 0xF)) {
            n = (0x28 ^ 0x2D);
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else if (this.xpValue >= (0x9 ^ 0x2C)) {
            n = (0xAC ^ 0xA8);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (this.xpValue >= (0x84 ^ 0x95)) {
            n = "   ".length();
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else if (this.xpValue >= (0x7A ^ 0x7D)) {
            n = "  ".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else if (this.xpValue >= "   ".length()) {
            n = " ".length();
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort(EntityXPOrb.I[" ".length()], (byte)this.xpOrbHealth);
        nbtTagCompound.setShort(EntityXPOrb.I["  ".length()], (short)this.xpOrbAge);
        nbtTagCompound.setShort(EntityXPOrb.I["   ".length()], (short)this.xpValue);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        this.setBeenAttacked();
        this.xpOrbHealth -= (int)n;
        if (this.xpOrbHealth <= 0) {
            this.setDead();
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean canAttackWithItem() {
        return "".length() != 0;
    }
    
    public EntityXPOrb(final World world) {
        super(world);
        this.xpOrbHealth = (0x65 ^ 0x60);
        this.setSize(0.25f, 0.25f);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
}
