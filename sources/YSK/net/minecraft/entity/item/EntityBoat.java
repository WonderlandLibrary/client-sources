package net.minecraft.entity.item;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;

public class EntityBoat extends Entity
{
    private boolean isBoatEmpty;
    private double speedMultiplier;
    private static final String[] I;
    private double velocityY;
    private double boatX;
    private double boatYaw;
    private double velocityX;
    private double boatPitch;
    private double boatZ;
    private double boatY;
    private double velocityZ;
    private int boatPosRotationIncrements;
    
    @Override
    public boolean canBePushed() {
        return " ".length() != 0;
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
    
    @Override
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.setPosition(this.posX + Math.cos(this.rotationYaw * 3.141592653589793 / 180.0) * 0.4, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + Math.sin(this.rotationYaw * 3.141592653589793 / 180.0) * 0.4);
        }
    }
    
    @Override
    protected void updateFallState(final double n, final boolean b, final Block block, final BlockPos blockPos) {
        if (b) {
            if (this.fallDistance > 3.0f) {
                this.fall(this.fallDistance, 1.0f);
                if (!this.worldObj.isRemote && !this.isDead) {
                    this.setDead();
                    if (this.worldObj.getGameRules().getBoolean(EntityBoat.I["  ".length()])) {
                        int i = "".length();
                        "".length();
                        if (1 < 1) {
                            throw null;
                        }
                        while (i < "   ".length()) {
                            this.dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), " ".length(), 0.0f);
                            ++i;
                        }
                        int j = "".length();
                        "".length();
                        if (1 == 4) {
                            throw null;
                        }
                        while (j < "  ".length()) {
                            this.dropItemWithOffset(Items.stick, " ".length(), 0.0f);
                            ++j;
                        }
                    }
                }
                this.fallDistance = 0.0f;
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
        }
        else if (this.worldObj.getBlockState(new BlockPos(this).down()).getBlock().getMaterial() != Material.water && n < 0.0) {
            this.fallDistance -= (float)n;
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(0x3B ^ 0x2A, new Integer("".length()));
        this.dataWatcher.addObject(0x13 ^ 0x1, new Integer(" ".length()));
        this.dataWatcher.addObject(0x96 ^ 0x85, new Float(0.0f));
    }
    
    @Override
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(0x81 ^ 0x8B);
        this.setDamageTaken(this.getDamageTaken() * 11.0f);
    }
    
    @Override
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        return entity.getEntityBoundingBox();
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
    
    public int getTimeSinceHit() {
        return this.dataWatcher.getWatchableObjectInt(0x85 ^ 0x94);
    }
    
    public void setIsBoatEmpty(final boolean isBoatEmpty) {
        this.isBoatEmpty = isBoatEmpty;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        int n;
        if (this.isDead) {
            n = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public void setPositionAndRotation2(final double boatX, final double boatY, final double boatZ, final float rotationYaw, final float rotationPitch, final int n, final boolean b) {
        if (b && this.riddenByEntity != null) {
            this.posX = boatX;
            this.prevPosX = boatX;
            this.posY = boatY;
            this.prevPosY = boatY;
            this.posZ = boatZ;
            this.prevPosZ = boatZ;
            this.rotationYaw = rotationYaw;
            this.rotationPitch = rotationPitch;
            this.boatPosRotationIncrements = "".length();
            this.setPosition(boatX, boatY, boatZ);
            final double n2 = 0.0;
            this.velocityX = n2;
            this.motionX = n2;
            final double n3 = 0.0;
            this.velocityY = n3;
            this.motionY = n3;
            final double n4 = 0.0;
            this.velocityZ = n4;
            this.motionZ = n4;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            if (this.isBoatEmpty) {
                this.boatPosRotationIncrements = n + (0x9E ^ 0x9B);
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                final double n5 = boatX - this.posX;
                final double n6 = boatY - this.posY;
                final double n7 = boatZ - this.posZ;
                if (n5 * n5 + n6 * n6 + n7 * n7 <= 1.0) {
                    return;
                }
                this.boatPosRotationIncrements = "   ".length();
            }
            this.boatX = boatX;
            this.boatY = boatY;
            this.boatZ = boatZ;
            this.boatYaw = rotationYaw;
            this.boatPitch = rotationPitch;
            this.motionX = this.velocityX;
            this.motionY = this.velocityY;
            this.motionZ = this.velocityZ;
        }
    }
    
    public EntityBoat(final World world) {
        super(world);
        this.isBoatEmpty = (" ".length() != 0);
        this.speedMultiplier = 0.07;
        this.preventEntitySpawning = (" ".length() != 0);
        this.setSize(1.5f, 0.6f);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public void setTimeSinceHit(final int n) {
        this.dataWatcher.updateObject(0x88 ^ 0x99, n);
    }
    
    public int getForwardDirection() {
        return this.dataWatcher.getWatchableObjectInt(0x7E ^ 0x6C);
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != entityPlayer) {
            return " ".length() != 0;
        }
        if (!this.worldObj.isRemote) {
            entityPlayer.mountEntity(this);
        }
        return " ".length() != 0;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - " ".length());
        }
        if (this.getDamageTaken() > 0.0f) {
            this.setDamageTaken(this.getDamageTaken() - 1.0f);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final int n = 0x8A ^ 0x8F;
        double n2 = 0.0;
        int i = "".length();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (i < n) {
            if (this.worldObj.isAABBInMaterial(new AxisAlignedBB(this.getEntityBoundingBox().minX, this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (i + "".length()) / n - 0.125, this.getEntityBoundingBox().minZ, this.getEntityBoundingBox().maxX, this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (i + " ".length()) / n - 0.125, this.getEntityBoundingBox().maxZ), Material.water)) {
                n2 += 1.0 / n;
            }
            ++i;
        }
        final double sqrt = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (sqrt > 0.2975) {
            final double cos = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0);
            final double sin = Math.sin(this.rotationYaw * 3.141592653589793 / 180.0);
            int length = "".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
            while (length < 1.0 + sqrt * 60.0) {
                final double n3 = this.rand.nextFloat() * 2.0f - 1.0f;
                final double n4 = (this.rand.nextInt("  ".length()) * "  ".length() - " ".length()) * 0.7;
                if (this.rand.nextBoolean()) {
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX - cos * n3 * 0.8 + sin * n4, this.posY - 0.125, this.posZ - sin * n3 * 0.8 - cos * n4, this.motionX, this.motionY, this.motionZ, new int["".length()]);
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
                else {
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + cos + sin * n3 * 0.7, this.posY - 0.125, this.posZ + sin - cos * n3 * 0.7, this.motionX, this.motionY, this.motionZ, new int["".length()]);
                }
                ++length;
            }
        }
        if (this.worldObj.isRemote && this.isBoatEmpty) {
            if (this.boatPosRotationIncrements > 0) {
                final double n5 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
                final double n6 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
                final double n7 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
                this.rotationYaw += (float)(MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw) / this.boatPosRotationIncrements);
                this.rotationPitch += (float)((this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
                this.boatPosRotationIncrements -= " ".length();
                this.setPosition(n5, n6, n7);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
                if (this.onGround) {
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                }
                this.motionX *= 0.9900000095367432;
                this.motionY *= 0.949999988079071;
                this.motionZ *= 0.9900000095367432;
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
        }
        else {
            if (n2 < 1.0) {
                this.motionY += 0.03999999910593033 * (n2 * 2.0 - 1.0);
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else {
                if (this.motionY < 0.0) {
                    this.motionY /= 2.0;
                }
                this.motionY += 0.007000000216066837;
            }
            if (this.riddenByEntity instanceof EntityLivingBase) {
                final EntityLivingBase entityLivingBase = (EntityLivingBase)this.riddenByEntity;
                final float n8 = this.riddenByEntity.rotationYaw + -entityLivingBase.moveStrafing * 90.0f;
                this.motionX += -Math.sin(n8 * 3.1415927f / 180.0f) * this.speedMultiplier * entityLivingBase.moveForward * 0.05000000074505806;
                this.motionZ += Math.cos(n8 * 3.1415927f / 180.0f) * this.speedMultiplier * entityLivingBase.moveForward * 0.05000000074505806;
            }
            double sqrt2 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (sqrt2 > 0.35) {
                final double n9 = 0.35 / sqrt2;
                this.motionX *= n9;
                this.motionZ *= n9;
                sqrt2 = 0.35;
            }
            if (sqrt2 > sqrt && this.speedMultiplier < 0.35) {
                this.speedMultiplier += (0.35 - this.speedMultiplier) / 35.0;
                if (this.speedMultiplier > 0.35) {
                    this.speedMultiplier = 0.35;
                    "".length();
                    if (1 < 1) {
                        throw null;
                    }
                }
            }
            else {
                this.speedMultiplier -= (this.speedMultiplier - 0.07) / 35.0;
                if (this.speedMultiplier < 0.07) {
                    this.speedMultiplier = 0.07;
                }
            }
            int j = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (j < (0x32 ^ 0x36)) {
                final int floor_double = MathHelper.floor_double(this.posX + (j % "  ".length() - 0.5) * 0.8);
                final int floor_double2 = MathHelper.floor_double(this.posZ + (j / "  ".length() - 0.5) * 0.8);
                int k = "".length();
                "".length();
                if (0 < 0) {
                    throw null;
                }
                while (k < "  ".length()) {
                    final BlockPos blockToAir = new BlockPos(floor_double, MathHelper.floor_double(this.posY) + k, floor_double2);
                    final Block block = this.worldObj.getBlockState(blockToAir).getBlock();
                    if (block == Blocks.snow_layer) {
                        this.worldObj.setBlockToAir(blockToAir);
                        this.isCollidedHorizontally = ("".length() != 0);
                        "".length();
                        if (4 <= 1) {
                            throw null;
                        }
                    }
                    else if (block == Blocks.waterlily) {
                        this.worldObj.destroyBlock(blockToAir, " ".length() != 0);
                        this.isCollidedHorizontally = ("".length() != 0);
                    }
                    ++k;
                }
                ++j;
            }
            if (this.onGround) {
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.isCollidedHorizontally && sqrt > 0.2975) {
                if (!this.worldObj.isRemote && !this.isDead) {
                    this.setDead();
                    if (this.worldObj.getGameRules().getBoolean(EntityBoat.I[" ".length()])) {
                        int l = "".length();
                        "".length();
                        if (0 == -1) {
                            throw null;
                        }
                        while (l < "   ".length()) {
                            this.dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), " ".length(), 0.0f);
                            ++l;
                        }
                        int length2 = "".length();
                        "".length();
                        if (0 < 0) {
                            throw null;
                        }
                        while (length2 < "  ".length()) {
                            this.dropItemWithOffset(Items.stick, " ".length(), 0.0f);
                            ++length2;
                        }
                        "".length();
                        if (3 < 2) {
                            throw null;
                        }
                    }
                }
            }
            else {
                this.motionX *= 0.9900000095367432;
                this.motionY *= 0.949999988079071;
                this.motionZ *= 0.9900000095367432;
            }
            this.rotationPitch = 0.0f;
            double n10 = this.rotationYaw;
            final double n11 = this.prevPosX - this.posX;
            final double n12 = this.prevPosZ - this.posZ;
            if (n11 * n11 + n12 * n12 > 0.001) {
                n10 = (float)(MathHelper.func_181159_b(n12, n11) * 180.0 / 3.141592653589793);
            }
            double wrapAngleTo180_double = MathHelper.wrapAngleTo180_double(n10 - this.rotationYaw);
            if (wrapAngleTo180_double > 20.0) {
                wrapAngleTo180_double = 20.0;
            }
            if (wrapAngleTo180_double < -20.0) {
                wrapAngleTo180_double = -20.0;
            }
            this.setRotation(this.rotationYaw += (float)wrapAngleTo180_double, this.rotationPitch);
            if (!this.worldObj.isRemote) {
                final List<Entity> entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.20000000298023224, 0.0, 0.20000000298023224));
                if (entitiesWithinAABBExcludingEntity != null && !entitiesWithinAABBExcludingEntity.isEmpty()) {
                    int length3 = "".length();
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                    while (length3 < entitiesWithinAABBExcludingEntity.size()) {
                        final Entity entity = entitiesWithinAABBExcludingEntity.get(length3);
                        if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityBoat) {
                            entity.applyEntityCollision(this);
                        }
                        ++length3;
                    }
                }
                if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                    this.riddenByEntity = null;
                }
            }
        }
    }
    
    public float getDamageTaken() {
        return this.dataWatcher.getWatchableObjectFloat(0xA0 ^ 0xB3);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u001e\u0015\u0014&\r\u0013\u000e(\f\u000b\u0015\n\"", "zzQHy");
        EntityBoat.I[" ".length()] = I("#\u0001\u0007&\u0004.\u001a;\f\u0002(\u001e1", "GnBHp");
        EntityBoat.I["  ".length()] = I("'#\u000e\u001b\u0006*821\u0000,<8", "CLKur");
    }
    
    @Override
    public void setVelocity(final double n, final double n2, final double n3) {
        this.motionX = n;
        this.velocityX = n;
        this.motionY = n2;
        this.velocityY = n2;
        this.motionZ = n3;
        this.velocityZ = n3;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getEntityBoundingBox();
    }
    
    public void setForwardDirection(final int n) {
        this.dataWatcher.updateObject(0x67 ^ 0x75, n);
    }
    
    public void setDamageTaken(final float n) {
        this.dataWatcher.updateObject(0x7A ^ 0x69, n);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        if (this.worldObj.isRemote || this.isDead) {
            return " ".length() != 0;
        }
        if (this.riddenByEntity != null && this.riddenByEntity == damageSource.getEntity() && damageSource instanceof EntityDamageSourceIndirect) {
            return "".length() != 0;
        }
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(0x54 ^ 0x5E);
        this.setDamageTaken(this.getDamageTaken() + n * 10.0f);
        this.setBeenAttacked();
        int n2;
        if (damageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)damageSource.getEntity()).capabilities.isCreativeMode) {
            n2 = " ".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        if (n3 != 0 || this.getDamageTaken() > 40.0f) {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity(this);
            }
            if (n3 == 0 && this.worldObj.getGameRules().getBoolean(EntityBoat.I["".length()])) {
                this.dropItemWithOffset(Items.boat, " ".length(), 0.0f);
            }
            this.setDead();
        }
        return " ".length() != 0;
    }
    
    @Override
    public double getMountedYOffset() {
        return -0.3;
    }
    
    public EntityBoat(final World world, final double prevPosX, final double prevPosY, final double prevPosZ) {
        this(world);
        this.setPosition(prevPosX, prevPosY, prevPosZ);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = prevPosX;
        this.prevPosY = prevPosY;
        this.prevPosZ = prevPosZ;
    }
    
    static {
        I();
    }
}
