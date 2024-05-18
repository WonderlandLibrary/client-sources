package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public abstract class EntityMinecart extends Entity
{
    private boolean isInReverse;
    private final IUpdatePlayerListBox field_82344_g;
    private String entityName;
    private static final int[][][] matrix;
    private int turnProgress;
    private double minecartX;
    private double minecartY;
    private double minecartZ;
    private double minecartYaw;
    private double minecartPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    
    static {
        matrix = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };
    }
    
    public EntityMinecart(final World par1World) {
        super(par1World);
        this.isInReverse = false;
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.7f);
        this.yOffset = this.height / 2.0f;
        this.field_82344_g = ((par1World != null) ? par1World.func_82735_a(this) : null);
    }
    
    public static EntityMinecart createMinecart(final World par0World, final double par1, final double par3, final double par5, final int par7) {
        switch (par7) {
            case 1: {
                return new EntityMinecartChest(par0World, par1, par3, par5);
            }
            case 2: {
                return new EntityMinecartFurnace(par0World, par1, par3, par5);
            }
            case 3: {
                return new EntityMinecartTNT(par0World, par1, par3, par5);
            }
            case 4: {
                return new EntityMinecartMobSpawner(par0World, par1, par3, par5);
            }
            case 5: {
                return new EntityMinecartHopper(par0World, par1, par3, par5);
            }
            default: {
                return new EntityMinecartEmpty(par0World, par1, par3, par5);
            }
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(1));
        this.dataWatcher.addObject(19, new Integer(0));
        this.dataWatcher.addObject(20, new Integer(0));
        this.dataWatcher.addObject(21, new Integer(6));
        this.dataWatcher.addObject(22, (byte)0);
    }
    
    @Override
    public AxisAlignedBB getCollisionBox(final Entity par1Entity) {
        return par1Entity.canBePushed() ? par1Entity.boundingBox : null;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
    
    @Override
    public boolean canBePushed() {
        return true;
    }
    
    public EntityMinecart(final World par1World, final double par2, final double par4, final double par6) {
        this(par1World);
        this.setPosition(par2, par4 + this.yOffset, par6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }
    
    @Override
    public double getMountedYOffset() {
        return this.height * 0.0 - 0.30000001192092896;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.worldObj.isRemote || this.isDead) {
            return true;
        }
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(10);
        this.setBeenAttacked();
        this.setDamage(this.getDamage() + par2 * 10);
        final boolean var3 = par1DamageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)par1DamageSource.getEntity()).capabilities.isCreativeMode;
        if (var3 || this.getDamage() > 40) {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity(this);
            }
            if (var3 && !this.isInvNameLocalized()) {
                this.setDead();
            }
            else {
                this.killMinecart(par1DamageSource);
            }
        }
        return true;
    }
    
    public void killMinecart(final DamageSource par1DamageSource) {
        this.setDead();
        final ItemStack var2 = new ItemStack(Item.minecartEmpty, 1);
        if (this.entityName != null) {
            var2.setItemName(this.entityName);
        }
        this.entityDropItem(var2, 0.0f);
    }
    
    @Override
    public void performHurtAnimation() {
        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(10);
        this.setDamage(this.getDamage() + this.getDamage() * 10);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    public void setDead() {
        super.setDead();
        if (this.field_82344_g != null) {
            this.field_82344_g.update();
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.field_82344_g != null) {
            this.field_82344_g.update();
        }
        if (this.getRollingAmplitude() > 0) {
            this.setRollingAmplitude(this.getRollingAmplitude() - 1);
        }
        if (this.getDamage() > 0) {
            this.setDamage(this.getDamage() - 1);
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
            this.worldObj.theProfiler.startSection("portal");
            final MinecraftServer var2 = ((WorldServer)this.worldObj).getMinecraftServer();
            final int var3 = this.getMaxInPortalTime();
            if (this.inPortal) {
                if (var2.getAllowNether()) {
                    if (this.ridingEntity == null && this.field_82153_h++ >= var3) {
                        this.field_82153_h = var3;
                        this.timeUntilPortal = this.getPortalCooldown();
                        byte var4;
                        if (this.worldObj.provider.dimensionId == -1) {
                            var4 = 0;
                        }
                        else {
                            var4 = -1;
                        }
                        this.travelToDimension(var4);
                    }
                    this.inPortal = false;
                }
            }
            else {
                if (this.field_82153_h > 0) {
                    this.field_82153_h -= 4;
                }
                if (this.field_82153_h < 0) {
                    this.field_82153_h = 0;
                }
            }
            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }
            this.worldObj.theProfiler.endSection();
        }
        if (this.worldObj.isRemote) {
            if (this.turnProgress > 0) {
                final double var5 = this.posX + (this.minecartX - this.posX) / this.turnProgress;
                final double var6 = this.posY + (this.minecartY - this.posY) / this.turnProgress;
                final double var7 = this.posZ + (this.minecartZ - this.posZ) / this.turnProgress;
                final double var8 = MathHelper.wrapAngleTo180_double(this.minecartYaw - this.rotationYaw);
                this.rotationYaw += (float)(var8 / this.turnProgress);
                this.rotationPitch += (float)((this.minecartPitch - this.rotationPitch) / this.turnProgress);
                --this.turnProgress;
                this.setPosition(var5, var6, var7);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
            else {
                this.setPosition(this.posX, this.posY, this.posZ);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
        }
        else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= 0.03999999910593033;
            final int var9 = MathHelper.floor_double(this.posX);
            int var3 = MathHelper.floor_double(this.posY);
            final int var10 = MathHelper.floor_double(this.posZ);
            if (BlockRailBase.isRailBlockAt(this.worldObj, var9, var3 - 1, var10)) {
                --var3;
            }
            final double var6 = 0.4;
            final double var7 = 0.0078125;
            final int var11 = this.worldObj.getBlockId(var9, var3, var10);
            if (BlockRailBase.isRailBlock(var11)) {
                final int var12 = this.worldObj.getBlockMetadata(var9, var3, var10);
                this.updateOnTrack(var9, var3, var10, var6, var7, var11, var12);
                if (var11 == Block.railActivator.blockID) {
                    this.onActivatorRailPass(var9, var3, var10, (var12 & 0x8) != 0x0);
                }
            }
            else {
                this.func_94088_b(var6);
            }
            this.doBlockCollisions();
            this.rotationPitch = 0.0f;
            final double var13 = this.prevPosX - this.posX;
            final double var14 = this.prevPosZ - this.posZ;
            if (var13 * var13 + var14 * var14 > 0.001) {
                this.rotationYaw = (float)(Math.atan2(var14, var13) * 180.0 / 3.141592653589793);
                if (this.isInReverse) {
                    this.rotationYaw += 180.0f;
                }
            }
            final double var15 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.prevRotationYaw);
            if (var15 < -170.0 || var15 >= 170.0) {
                this.rotationYaw += 180.0f;
                this.isInReverse = !this.isInReverse;
            }
            this.setRotation(this.rotationYaw, this.rotationPitch);
            final List var16 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224, 0.0, 0.20000000298023224));
            if (var16 != null && !var16.isEmpty()) {
                for (int var17 = 0; var17 < var16.size(); ++var17) {
                    final Entity var18 = var16.get(var17);
                    if (var18 != this.riddenByEntity && var18.canBePushed() && var18 instanceof EntityMinecart) {
                        var18.applyEntityCollision(this);
                    }
                }
            }
            if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                if (this.riddenByEntity.ridingEntity == this) {
                    this.riddenByEntity.ridingEntity = null;
                }
                this.riddenByEntity = null;
            }
        }
    }
    
    public void onActivatorRailPass(final int par1, final int par2, final int par3, final boolean par4) {
    }
    
    protected void func_94088_b(final double par1) {
        if (this.motionX < -par1) {
            this.motionX = -par1;
        }
        if (this.motionX > par1) {
            this.motionX = par1;
        }
        if (this.motionZ < -par1) {
            this.motionZ = -par1;
        }
        if (this.motionZ > par1) {
            this.motionZ = par1;
        }
        if (this.onGround) {
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (!this.onGround) {
            this.motionX *= 0.949999988079071;
            this.motionY *= 0.949999988079071;
            this.motionZ *= 0.949999988079071;
        }
    }
    
    protected void updateOnTrack(final int par1, final int par2, final int par3, final double par4, final double par6, final int par8, int par9) {
        this.fallDistance = 0.0f;
        final Vec3 var10 = this.func_70489_a(this.posX, this.posY, this.posZ);
        this.posY = par2;
        boolean var11 = false;
        boolean var12 = false;
        if (par8 == Block.railPowered.blockID) {
            var11 = ((par9 & 0x8) != 0x0);
            var12 = !var11;
        }
        if (((BlockRailBase)Block.blocksList[par8]).isPowered()) {
            par9 &= 0x7;
        }
        if (par9 >= 2 && par9 <= 5) {
            this.posY = par2 + 1;
        }
        if (par9 == 2) {
            this.motionX -= par6;
        }
        if (par9 == 3) {
            this.motionX += par6;
        }
        if (par9 == 4) {
            this.motionZ += par6;
        }
        if (par9 == 5) {
            this.motionZ -= par6;
        }
        final int[][] var13 = EntityMinecart.matrix[par9];
        double var14 = var13[1][0] - var13[0][0];
        double var15 = var13[1][2] - var13[0][2];
        final double var16 = Math.sqrt(var14 * var14 + var15 * var15);
        final double var17 = this.motionX * var14 + this.motionZ * var15;
        if (var17 < 0.0) {
            var14 = -var14;
            var15 = -var15;
        }
        double var18 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (var18 > 2.0) {
            var18 = 2.0;
        }
        this.motionX = var18 * var14 / var16;
        this.motionZ = var18 * var15 / var16;
        if (this.riddenByEntity != null) {
            final double var19 = this.riddenByEntity.motionX * this.riddenByEntity.motionX + this.riddenByEntity.motionZ * this.riddenByEntity.motionZ;
            final double var20 = this.motionX * this.motionX + this.motionZ * this.motionZ;
            if (var19 > 1.0E-4 && var20 < 0.01) {
                this.motionX += this.riddenByEntity.motionX * 0.1;
                this.motionZ += this.riddenByEntity.motionZ * 0.1;
                var12 = false;
            }
        }
        if (var12) {
            final double var19 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var19 < 0.03) {
                this.motionX *= 0.0;
                this.motionY *= 0.0;
                this.motionZ *= 0.0;
            }
            else {
                this.motionX *= 0.5;
                this.motionY *= 0.0;
                this.motionZ *= 0.5;
            }
        }
        double var19 = 0.0;
        final double var20 = par1 + 0.5 + var13[0][0] * 0.5;
        final double var21 = par3 + 0.5 + var13[0][2] * 0.5;
        final double var22 = par1 + 0.5 + var13[1][0] * 0.5;
        final double var23 = par3 + 0.5 + var13[1][2] * 0.5;
        var14 = var22 - var20;
        var15 = var23 - var21;
        if (var14 == 0.0) {
            this.posX = par1 + 0.5;
            var19 = this.posZ - par3;
        }
        else if (var15 == 0.0) {
            this.posZ = par3 + 0.5;
            var19 = this.posX - par1;
        }
        else {
            final double var24 = this.posX - var20;
            final double var25 = this.posZ - var21;
            var19 = (var24 * var14 + var25 * var15) * 2.0;
        }
        this.posX = var20 + var14 * var19;
        this.posZ = var21 + var15 * var19;
        this.setPosition(this.posX, this.posY + this.yOffset, this.posZ);
        double var24 = this.motionX;
        double var25 = this.motionZ;
        if (this.riddenByEntity != null) {
            var24 *= 0.75;
            var25 *= 0.75;
        }
        if (var24 < -par4) {
            var24 = -par4;
        }
        if (var24 > par4) {
            var24 = par4;
        }
        if (var25 < -par4) {
            var25 = -par4;
        }
        if (var25 > par4) {
            var25 = par4;
        }
        this.moveEntity(var24, 0.0, var25);
        if (var13[0][1] != 0 && MathHelper.floor_double(this.posX) - par1 == var13[0][0] && MathHelper.floor_double(this.posZ) - par3 == var13[0][2]) {
            this.setPosition(this.posX, this.posY + var13[0][1], this.posZ);
        }
        else if (var13[1][1] != 0 && MathHelper.floor_double(this.posX) - par1 == var13[1][0] && MathHelper.floor_double(this.posZ) - par3 == var13[1][2]) {
            this.setPosition(this.posX, this.posY + var13[1][1], this.posZ);
        }
        this.applyDrag();
        final Vec3 var26 = this.func_70489_a(this.posX, this.posY, this.posZ);
        if (var26 != null && var10 != null) {
            final double var27 = (var10.yCoord - var26.yCoord) * 0.05;
            var18 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var18 > 0.0) {
                this.motionX = this.motionX / var18 * (var18 + var27);
                this.motionZ = this.motionZ / var18 * (var18 + var27);
            }
            this.setPosition(this.posX, var26.yCoord, this.posZ);
        }
        final int var28 = MathHelper.floor_double(this.posX);
        final int var29 = MathHelper.floor_double(this.posZ);
        if (var28 != par1 || var29 != par3) {
            var18 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.motionX = var18 * (var28 - par1);
            this.motionZ = var18 * (var29 - par3);
        }
        if (var11) {
            final double var30 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var30 > 0.01) {
                final double var31 = 0.06;
                this.motionX += this.motionX / var30 * var31;
                this.motionZ += this.motionZ / var30 * var31;
            }
            else if (par9 == 1) {
                if (this.worldObj.isBlockNormalCube(par1 - 1, par2, par3)) {
                    this.motionX = 0.02;
                }
                else if (this.worldObj.isBlockNormalCube(par1 + 1, par2, par3)) {
                    this.motionX = -0.02;
                }
            }
            else if (par9 == 0) {
                if (this.worldObj.isBlockNormalCube(par1, par2, par3 - 1)) {
                    this.motionZ = 0.02;
                }
                else if (this.worldObj.isBlockNormalCube(par1, par2, par3 + 1)) {
                    this.motionZ = -0.02;
                }
            }
        }
    }
    
    protected void applyDrag() {
        if (this.riddenByEntity != null) {
            this.motionX *= 0.996999979019165;
            this.motionY *= 0.0;
            this.motionZ *= 0.996999979019165;
        }
        else {
            this.motionX *= 0.9599999785423279;
            this.motionY *= 0.0;
            this.motionZ *= 0.9599999785423279;
        }
    }
    
    public Vec3 func_70495_a(double par1, double par3, double par5, final double par7) {
        final int var9 = MathHelper.floor_double(par1);
        int var10 = MathHelper.floor_double(par3);
        final int var11 = MathHelper.floor_double(par5);
        if (BlockRailBase.isRailBlockAt(this.worldObj, var9, var10 - 1, var11)) {
            --var10;
        }
        final int var12 = this.worldObj.getBlockId(var9, var10, var11);
        if (!BlockRailBase.isRailBlock(var12)) {
            return null;
        }
        int var13 = this.worldObj.getBlockMetadata(var9, var10, var11);
        if (((BlockRailBase)Block.blocksList[var12]).isPowered()) {
            var13 &= 0x7;
        }
        par3 = var10;
        if (var13 >= 2 && var13 <= 5) {
            par3 = var10 + 1;
        }
        final int[][] var14 = EntityMinecart.matrix[var13];
        double var15 = var14[1][0] - var14[0][0];
        double var16 = var14[1][2] - var14[0][2];
        final double var17 = Math.sqrt(var15 * var15 + var16 * var16);
        var15 /= var17;
        var16 /= var17;
        par1 += var15 * par7;
        par5 += var16 * par7;
        if (var14[0][1] != 0 && MathHelper.floor_double(par1) - var9 == var14[0][0] && MathHelper.floor_double(par5) - var11 == var14[0][2]) {
            par3 += var14[0][1];
        }
        else if (var14[1][1] != 0 && MathHelper.floor_double(par1) - var9 == var14[1][0] && MathHelper.floor_double(par5) - var11 == var14[1][2]) {
            par3 += var14[1][1];
        }
        return this.func_70489_a(par1, par3, par5);
    }
    
    public Vec3 func_70489_a(double par1, double par3, double par5) {
        final int var7 = MathHelper.floor_double(par1);
        int var8 = MathHelper.floor_double(par3);
        final int var9 = MathHelper.floor_double(par5);
        if (BlockRailBase.isRailBlockAt(this.worldObj, var7, var8 - 1, var9)) {
            --var8;
        }
        final int var10 = this.worldObj.getBlockId(var7, var8, var9);
        if (BlockRailBase.isRailBlock(var10)) {
            int var11 = this.worldObj.getBlockMetadata(var7, var8, var9);
            par3 = var8;
            if (((BlockRailBase)Block.blocksList[var10]).isPowered()) {
                var11 &= 0x7;
            }
            if (var11 >= 2 && var11 <= 5) {
                par3 = var8 + 1;
            }
            final int[][] var12 = EntityMinecart.matrix[var11];
            double var13 = 0.0;
            final double var14 = var7 + 0.5 + var12[0][0] * 0.5;
            final double var15 = var8 + 0.5 + var12[0][1] * 0.5;
            final double var16 = var9 + 0.5 + var12[0][2] * 0.5;
            final double var17 = var7 + 0.5 + var12[1][0] * 0.5;
            final double var18 = var8 + 0.5 + var12[1][1] * 0.5;
            final double var19 = var9 + 0.5 + var12[1][2] * 0.5;
            final double var20 = var17 - var14;
            final double var21 = (var18 - var15) * 2.0;
            final double var22 = var19 - var16;
            if (var20 == 0.0) {
                par1 = var7 + 0.5;
                var13 = par5 - var9;
            }
            else if (var22 == 0.0) {
                par5 = var9 + 0.5;
                var13 = par1 - var7;
            }
            else {
                final double var23 = par1 - var14;
                final double var24 = par5 - var16;
                var13 = (var23 * var20 + var24 * var22) * 2.0;
            }
            par1 = var14 + var20 * var13;
            par3 = var15 + var21 * var13;
            par5 = var16 + var22 * var13;
            if (var21 < 0.0) {
                ++par3;
            }
            if (var21 > 0.0) {
                par3 += 0.5;
            }
            return this.worldObj.getWorldVec3Pool().getVecFromPool(par1, par3, par5);
        }
        return null;
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        if (par1NBTTagCompound.getBoolean("CustomDisplayTile")) {
            this.setDisplayTile(par1NBTTagCompound.getInteger("DisplayTile"));
            this.setDisplayTileData(par1NBTTagCompound.getInteger("DisplayData"));
            this.setDisplayTileOffset(par1NBTTagCompound.getInteger("DisplayOffset"));
        }
        if (par1NBTTagCompound.hasKey("CustomName") && par1NBTTagCompound.getString("CustomName").length() > 0) {
            this.entityName = par1NBTTagCompound.getString("CustomName");
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        if (this.hasDisplayTile()) {
            par1NBTTagCompound.setBoolean("CustomDisplayTile", true);
            par1NBTTagCompound.setInteger("DisplayTile", (this.getDisplayTile() == null) ? 0 : this.getDisplayTile().blockID);
            par1NBTTagCompound.setInteger("DisplayData", this.getDisplayTileData());
            par1NBTTagCompound.setInteger("DisplayOffset", this.getDisplayTileOffset());
        }
        if (this.entityName != null && this.entityName.length() > 0) {
            par1NBTTagCompound.setString("CustomName", this.entityName);
        }
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public void applyEntityCollision(final Entity par1Entity) {
        if (!this.worldObj.isRemote && par1Entity != this.riddenByEntity) {
            if (par1Entity instanceof EntityLiving && !(par1Entity instanceof EntityPlayer) && !(par1Entity instanceof EntityIronGolem) && this.getMinecartType() == 0 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01 && this.riddenByEntity == null && par1Entity.ridingEntity == null) {
                par1Entity.mountEntity(this);
            }
            double var2 = par1Entity.posX - this.posX;
            double var3 = par1Entity.posZ - this.posZ;
            double var4 = var2 * var2 + var3 * var3;
            if (var4 >= 9.999999747378752E-5) {
                var4 = MathHelper.sqrt_double(var4);
                var2 /= var4;
                var3 /= var4;
                double var5 = 1.0 / var4;
                if (var5 > 1.0) {
                    var5 = 1.0;
                }
                var2 *= var5;
                var3 *= var5;
                var2 *= 0.10000000149011612;
                var3 *= 0.10000000149011612;
                var2 *= 1.0f - this.entityCollisionReduction;
                var3 *= 1.0f - this.entityCollisionReduction;
                var2 *= 0.5;
                var3 *= 0.5;
                if (par1Entity instanceof EntityMinecart) {
                    final double var6 = par1Entity.posX - this.posX;
                    final double var7 = par1Entity.posZ - this.posZ;
                    final Vec3 var8 = this.worldObj.getWorldVec3Pool().getVecFromPool(var6, 0.0, var7).normalize();
                    final Vec3 var9 = this.worldObj.getWorldVec3Pool().getVecFromPool(MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f), 0.0, MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f)).normalize();
                    final double var10 = Math.abs(var8.dotProduct(var9));
                    if (var10 < 0.800000011920929) {
                        return;
                    }
                    double var11 = par1Entity.motionX + this.motionX;
                    double var12 = par1Entity.motionZ + this.motionZ;
                    if (((EntityMinecart)par1Entity).getMinecartType() == 2 && this.getMinecartType() != 2) {
                        this.motionX *= 0.20000000298023224;
                        this.motionZ *= 0.20000000298023224;
                        this.addVelocity(par1Entity.motionX - var2, 0.0, par1Entity.motionZ - var3);
                        par1Entity.motionX *= 0.949999988079071;
                        par1Entity.motionZ *= 0.949999988079071;
                    }
                    else if (((EntityMinecart)par1Entity).getMinecartType() != 2 && this.getMinecartType() == 2) {
                        par1Entity.motionX *= 0.20000000298023224;
                        par1Entity.motionZ *= 0.20000000298023224;
                        par1Entity.addVelocity(this.motionX + var2, 0.0, this.motionZ + var3);
                        this.motionX *= 0.949999988079071;
                        this.motionZ *= 0.949999988079071;
                    }
                    else {
                        var11 /= 2.0;
                        var12 /= 2.0;
                        this.motionX *= 0.20000000298023224;
                        this.motionZ *= 0.20000000298023224;
                        this.addVelocity(var11 - var2, 0.0, var12 - var3);
                        par1Entity.motionX *= 0.20000000298023224;
                        par1Entity.motionZ *= 0.20000000298023224;
                        par1Entity.addVelocity(var11 + var2, 0.0, var12 + var3);
                    }
                }
                else {
                    this.addVelocity(-var2, 0.0, -var3);
                    par1Entity.addVelocity(var2 / 4.0, 0.0, var3 / 4.0);
                }
            }
        }
    }
    
    @Override
    public void setPositionAndRotation2(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        this.minecartX = par1;
        this.minecartY = par3;
        this.minecartZ = par5;
        this.minecartYaw = par7;
        this.minecartPitch = par8;
        this.turnProgress = par9 + 2;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }
    
    @Override
    public void setVelocity(final double par1, final double par3, final double par5) {
        this.motionX = par1;
        this.velocityX = par1;
        this.motionY = par3;
        this.velocityY = par3;
        this.motionZ = par5;
        this.velocityZ = par5;
    }
    
    public void setDamage(final int par1) {
        this.dataWatcher.updateObject(19, par1);
    }
    
    public int getDamage() {
        return this.dataWatcher.getWatchableObjectInt(19);
    }
    
    public void setRollingAmplitude(final int par1) {
        this.dataWatcher.updateObject(17, par1);
    }
    
    public int getRollingAmplitude() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }
    
    public void setRollingDirection(final int par1) {
        this.dataWatcher.updateObject(18, par1);
    }
    
    public int getRollingDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    public abstract int getMinecartType();
    
    public Block getDisplayTile() {
        if (!this.hasDisplayTile()) {
            return this.getDefaultDisplayTile();
        }
        final int var1 = this.getDataWatcher().getWatchableObjectInt(20) & 0xFFFF;
        return (var1 > 0 && var1 < Block.blocksList.length) ? Block.blocksList[var1] : null;
    }
    
    public Block getDefaultDisplayTile() {
        return null;
    }
    
    public int getDisplayTileData() {
        return this.hasDisplayTile() ? (this.getDataWatcher().getWatchableObjectInt(20) >> 16) : this.getDefaultDisplayTileData();
    }
    
    public int getDefaultDisplayTileData() {
        return 0;
    }
    
    public int getDisplayTileOffset() {
        return this.hasDisplayTile() ? this.getDataWatcher().getWatchableObjectInt(21) : this.getDefaultDisplayTileOffset();
    }
    
    public int getDefaultDisplayTileOffset() {
        return 6;
    }
    
    public void setDisplayTile(final int par1) {
        this.getDataWatcher().updateObject(20, (par1 & 0xFFFF) | this.getDisplayTileData() << 16);
        this.setHasDisplayTile(true);
    }
    
    public void setDisplayTileData(final int par1) {
        final Block var2 = this.getDisplayTile();
        final int var3 = (var2 == null) ? 0 : var2.blockID;
        this.getDataWatcher().updateObject(20, (var3 & 0xFFFF) | par1 << 16);
        this.setHasDisplayTile(true);
    }
    
    public void setDisplayTileOffset(final int par1) {
        this.getDataWatcher().updateObject(21, par1);
        this.setHasDisplayTile(true);
    }
    
    public boolean hasDisplayTile() {
        return this.getDataWatcher().getWatchableObjectByte(22) == 1;
    }
    
    public void setHasDisplayTile(final boolean par1) {
        this.getDataWatcher().updateObject(22, (byte)(byte)(par1 ? 1 : 0));
    }
    
    public void func_96094_a(final String par1Str) {
        this.entityName = par1Str;
    }
    
    @Override
    public String getEntityName() {
        return (this.entityName != null) ? this.entityName : super.getEntityName();
    }
    
    public boolean isInvNameLocalized() {
        return this.entityName != null;
    }
    
    public String func_95999_t() {
        return this.entityName;
    }
}
