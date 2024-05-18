package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;

public class EntityBat extends EntityAmbientCreature
{
    private static final String[] I;
    private BlockPos spawnPosition;
    
    public EntityBat(final World world) {
        super(world);
        this.setSize(0.5f, 0.9f);
        this.setIsBatHanging(" ".length() != 0);
    }
    
    private static void I() {
        (I = new String[0x51 ^ 0x54])["".length()] = I("\u00187\u000bx\b\u0014,G?\u000e\u0019=", "uXiVj");
        EntityBat.I[" ".length()] = I("\u0000\u0004/A-\f\u001fc\u0007:\u001f\u001f", "mkMoO");
        EntityBat.I["  ".length()] = I("7\t\ta\n;\u0012E+\r;\u0012\u0003", "ZfkOh");
        EntityBat.I["   ".length()] = I(")\u0017\u00032;\n\u0011\u0004", "kvwtW");
        EntityBat.I[0x1A ^ 0x1E] = I("\b5\"-\u000f+3%", "JTVkc");
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setByte(EntityBat.I[0xC0 ^ 0xC4], this.dataWatcher.getWatchableObjectByte(0x48 ^ 0x58));
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.dataWatcher.updateObject(0x24 ^ 0x34, nbtTagCompound.getByte(EntityBat.I["   ".length()]));
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    protected void collideWithNearbyEntities() {
    }
    
    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.95f;
    }
    
    public void setIsBatHanging(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0x53 ^ 0x43);
        if (b) {
            this.dataWatcher.updateObject(0x3A ^ 0x2A, (byte)(watchableObjectByte | " ".length()));
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            this.dataWatcher.updateObject(0x51 ^ 0x41, (byte)(watchableObjectByte & -"  ".length()));
        }
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.1f;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x5A ^ 0x4A, new Byte((byte)"".length()));
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
    
    @Override
    public boolean canBePushed() {
        return "".length() != 0;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getIsBatHanging()) {
            final double motionX = 0.0;
            this.motionZ = motionX;
            this.motionY = motionX;
            this.motionX = motionX;
            this.posY = MathHelper.floor_double(this.posY) + 1.0 - this.height;
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            this.motionY *= 0.6000000238418579;
        }
    }
    
    @Override
    public boolean getCanSpawnHere() {
        final BlockPos blockPos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        if (blockPos.getY() >= this.worldObj.func_181545_F()) {
            return "".length() != 0;
        }
        final int lightFromNeighbors = this.worldObj.getLightFromNeighbors(blockPos);
        int n = 0xAA ^ 0xAE;
        if (this.isDateAroundHalloween(this.worldObj.getCurrentDate())) {
            n = (0x87 ^ 0x80);
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else if (this.rand.nextBoolean()) {
            return "".length() != 0;
        }
        int n2;
        if (lightFromNeighbors > this.rand.nextInt(n)) {
            n2 = "".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            n2 = (super.getCanSpawnHere() ? 1 : 0);
        }
        return n2 != 0;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        if (!this.worldObj.isRemote && this.getIsBatHanging()) {
            this.setIsBatHanging("".length() != 0);
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return " ".length() != 0;
    }
    
    public boolean getIsBatHanging() {
        if ((this.dataWatcher.getWatchableObjectByte(0x19 ^ 0x9) & " ".length()) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private boolean isDateAroundHalloween(final Calendar calendar) {
        if ((calendar.get("  ".length()) + " ".length() != (0x85 ^ 0x8F) || calendar.get(0x43 ^ 0x46) < (0x32 ^ 0x26)) && (calendar.get("  ".length()) + " ".length() != (0x91 ^ 0x9A) || calendar.get(0xB5 ^ 0xB0) > "   ".length())) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0);
    }
    
    @Override
    public float getEyeHeight() {
        return this.height / 2.0f;
    }
    
    @Override
    protected void collideWithEntity(final Entity entity) {
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected String getHurtSound() {
        return EntityBat.I[" ".length()];
    }
    
    @Override
    protected String getDeathSound() {
        return EntityBat.I["  ".length()];
    }
    
    static {
        I();
    }
    
    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        final BlockPos blockPos = new BlockPos(this);
        final BlockPos up = blockPos.up();
        if (this.getIsBatHanging()) {
            if (!this.worldObj.getBlockState(up).getBlock().isNormalCube()) {
                this.setIsBatHanging("".length() != 0);
                this.worldObj.playAuxSFXAtEntity(null, 753 + 950 - 757 + 69, blockPos, "".length());
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            else {
                if (this.rand.nextInt(9 + 91 - 83 + 183) == 0) {
                    this.rotationYawHead = this.rand.nextInt(99 + 351 - 205 + 115);
                }
                if (this.worldObj.getClosestPlayerToEntity(this, 4.0) != null) {
                    this.setIsBatHanging("".length() != 0);
                    this.worldObj.playAuxSFXAtEntity(null, 661 + 407 - 327 + 274, blockPos, "".length());
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                }
            }
        }
        else {
            if (this.spawnPosition != null && (!this.worldObj.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < " ".length())) {
                this.spawnPosition = null;
            }
            if (this.spawnPosition == null || this.rand.nextInt(0x46 ^ 0x58) == 0 || this.spawnPosition.distanceSq((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0) {
                this.spawnPosition = new BlockPos((int)this.posX + this.rand.nextInt(0x4F ^ 0x48) - this.rand.nextInt(0x60 ^ 0x67), (int)this.posY + this.rand.nextInt(0x5E ^ 0x58) - "  ".length(), (int)this.posZ + this.rand.nextInt(0x22 ^ 0x25) - this.rand.nextInt(0x19 ^ 0x1E));
            }
            final double n = this.spawnPosition.getX() + 0.5 - this.posX;
            final double n2 = this.spawnPosition.getY() + 0.1 - this.posY;
            final double n3 = this.spawnPosition.getZ() + 0.5 - this.posZ;
            this.motionX += (Math.signum(n) * 0.5 - this.motionX) * 0.10000000149011612;
            this.motionY += (Math.signum(n2) * 0.699999988079071 - this.motionY) * 0.10000000149011612;
            this.motionZ += (Math.signum(n3) * 0.5 - this.motionZ) * 0.10000000149011612;
            final float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float((float)(MathHelper.func_181159_b(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) - 90.0f - this.rotationYaw);
            this.moveForward = 0.5f;
            this.rotationYaw += wrapAngleTo180_float;
            if (this.rand.nextInt(0x24 ^ 0x40) == 0 && this.worldObj.getBlockState(up).getBlock().isNormalCube()) {
                this.setIsBatHanging(" ".length() != 0);
            }
        }
    }
    
    @Override
    protected String getLivingSound() {
        String s;
        if (this.getIsBatHanging() && this.rand.nextInt(0x9E ^ 0x9A) != 0) {
            s = null;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            s = EntityBat.I["".length()];
        }
        return s;
    }
    
    @Override
    protected void updateFallState(final double n, final boolean b, final Block block, final BlockPos blockPos) {
    }
}
