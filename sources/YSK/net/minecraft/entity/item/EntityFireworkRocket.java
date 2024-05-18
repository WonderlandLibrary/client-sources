package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class EntityFireworkRocket extends Entity
{
    private int lifetime;
    private static final String[] I;
    private int fireworkAge;
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0x8C ^ 0x9D) && this.worldObj.isRemote) {
            final ItemStack watchableObjectItemStack = this.dataWatcher.getWatchableObjectItemStack(0x8F ^ 0x87);
            NBTTagCompound compoundTag = null;
            if (watchableObjectItemStack != null && watchableObjectItemStack.hasTagCompound()) {
                compoundTag = watchableObjectItemStack.getTagCompound().getCompoundTag(EntityFireworkRocket.I["   ".length()]);
            }
            this.worldObj.makeFireworks(this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, compoundTag);
        }
        super.handleStatusUpdate(b);
    }
    
    @Override
    public void setVelocity(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            final float n = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(MathHelper.func_181159_b(motionY, sqrt_double) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
    }
    
    @Override
    public boolean canAttackWithItem() {
        return "".length() != 0;
    }
    
    public EntityFireworkRocket(final World world, final double n, final double n2, final double n3, final ItemStack itemStack) {
        super(world);
        this.fireworkAge = "".length();
        this.setSize(0.25f, 0.25f);
        this.setPosition(n, n2, n3);
        int length = " ".length();
        if (itemStack != null && itemStack.hasTagCompound()) {
            this.dataWatcher.updateObject(0x5 ^ 0xD, itemStack);
            final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag(EntityFireworkRocket.I["".length()]);
            if (compoundTag != null) {
                length += compoundTag.getByte(EntityFireworkRocket.I[" ".length()]);
            }
        }
        this.motionX = this.rand.nextGaussian() * 0.001;
        this.motionZ = this.rand.nextGaussian() * 0.001;
        this.motionY = 0.05;
        this.lifetime = (0x82 ^ 0x88) * length + this.rand.nextInt(0x8F ^ 0x89) + this.rand.nextInt(0xB5 ^ 0xB2);
    }
    
    private static void I() {
        (I = new String[0x5B ^ 0x51])["".length()] = I("\u000f\f4\u0002\u0003&\u0017-\u0014", "IeFgt");
        EntityFireworkRocket.I[" ".length()] = I("?*\u000b\n\u0001\r", "yFbmi");
        EntityFireworkRocket.I["  ".length()] = I("\u0003\u001d4#3\n\u0006-5j\t\u00153('\r", "etFFD");
        EntityFireworkRocket.I["   ".length()] = I("3.\u0006\u000e\u0015\u001a5\u001f\u0018", "uGtkb");
        EntityFireworkRocket.I[0x7F ^ 0x7B] = I("4\"\u0001-", "xKgHH");
        EntityFireworkRocket.I[0x62 ^ 0x67] = I("5\u001e\u000f4\f\u0010\u001a\f", "ywiQX");
        EntityFireworkRocket.I[0x0 ^ 0x6] = I(".=\u001e\u0011\u0014\u0007&\u0007\u0007*\u001c1\u0001", "hTltc");
        EntityFireworkRocket.I[0x50 ^ 0x57] = I("\n?\u0014\u000e", "FVrkn");
        EntityFireworkRocket.I[0xC ^ 0x4] = I("!\b\u000f?\u000e\u0004\f\f", "maiZZ");
        EntityFireworkRocket.I[0x6F ^ 0x66] = I("\n#\u0014\f\u0003#8\r\u001a=8/\u000b", "LJfit");
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return super.getBrightnessForRender(n);
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObjectByDataType(0x33 ^ 0x3B, 0x4E ^ 0x4B);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.fireworkAge = nbtTagCompound.getInteger(EntityFireworkRocket.I[0x81 ^ 0x86]);
        this.lifetime = nbtTagCompound.getInteger(EntityFireworkRocket.I[0xB2 ^ 0xBA]);
        final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag(EntityFireworkRocket.I[0x28 ^ 0x21]);
        if (compoundTag != null) {
            final ItemStack loadItemStackFromNBT = ItemStack.loadItemStackFromNBT(compoundTag);
            if (loadItemStackFromNBT != null) {
                this.dataWatcher.updateObject(0x7F ^ 0x77, loadItemStackFromNBT);
            }
        }
    }
    
    static {
        I();
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger(EntityFireworkRocket.I[0x17 ^ 0x13], this.fireworkAge);
        nbtTagCompound.setInteger(EntityFireworkRocket.I[0x3F ^ 0x3A], this.lifetime);
        final ItemStack watchableObjectItemStack = this.dataWatcher.getWatchableObjectItemStack(0xA9 ^ 0xA1);
        if (watchableObjectItemStack != null) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            watchableObjectItemStack.writeToNBT(nbtTagCompound2);
            nbtTagCompound.setTag(EntityFireworkRocket.I[0xB2 ^ 0xB4], nbtTagCompound2);
        }
    }
    
    public EntityFireworkRocket(final World world) {
        super(world);
        this.setSize(0.25f, 0.25f);
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        this.motionX *= 1.15;
        this.motionZ *= 1.15;
        this.motionY += 0.04;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(MathHelper.func_181159_b(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
        this.rotationPitch = (float)(MathHelper.func_181159_b(this.motionY, sqrt_double) * 180.0 / 3.141592653589793);
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        "".length();
        if (true != true) {
            throw null;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
        if (this.fireworkAge == 0 && !this.isSilent()) {
            this.worldObj.playSoundAtEntity(this, EntityFireworkRocket.I["  ".length()], 3.0f, 1.0f);
        }
        this.fireworkAge += " ".length();
        if (this.worldObj.isRemote && this.fireworkAge % "  ".length() < "  ".length()) {
            this.worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY - 0.3, this.posZ, this.rand.nextGaussian() * 0.05, -this.motionY * 0.5, this.rand.nextGaussian() * 0.05, new int["".length()]);
        }
        if (!this.worldObj.isRemote && this.fireworkAge > this.lifetime) {
            this.worldObj.setEntityState(this, (byte)(0x51 ^ 0x40));
            this.setDead();
        }
    }
    
    @Override
    public float getBrightness(final float n) {
        return super.getBrightness(n);
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        if (n < 4096.0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
