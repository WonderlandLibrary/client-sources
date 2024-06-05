package net.minecraft.src;

public class EntityEnderman extends EntityMob
{
    private static boolean[] carriableBlocks;
    private int teleportDelay;
    private int field_70826_g;
    private boolean field_104003_g;
    
    static {
        (EntityEnderman.carriableBlocks = new boolean[256])[Block.grass.blockID] = true;
        EntityEnderman.carriableBlocks[Block.dirt.blockID] = true;
        EntityEnderman.carriableBlocks[Block.sand.blockID] = true;
        EntityEnderman.carriableBlocks[Block.gravel.blockID] = true;
        EntityEnderman.carriableBlocks[Block.plantYellow.blockID] = true;
        EntityEnderman.carriableBlocks[Block.plantRed.blockID] = true;
        EntityEnderman.carriableBlocks[Block.mushroomBrown.blockID] = true;
        EntityEnderman.carriableBlocks[Block.mushroomRed.blockID] = true;
        EntityEnderman.carriableBlocks[Block.tnt.blockID] = true;
        EntityEnderman.carriableBlocks[Block.cactus.blockID] = true;
        EntityEnderman.carriableBlocks[Block.blockClay.blockID] = true;
        EntityEnderman.carriableBlocks[Block.pumpkin.blockID] = true;
        EntityEnderman.carriableBlocks[Block.melon.blockID] = true;
        EntityEnderman.carriableBlocks[Block.mycelium.blockID] = true;
    }
    
    public EntityEnderman(final World par1World) {
        super(par1World);
        this.teleportDelay = 0;
        this.field_70826_g = 0;
        this.texture = "/mob/enderman.png";
        this.moveSpeed = 0.2f;
        this.setSize(0.6f, 2.9f);
        this.stepHeight = 1.0f;
    }
    
    @Override
    public int getMaxHealth() {
        return 40;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
        this.dataWatcher.addObject(17, new Byte((byte)0));
        this.dataWatcher.addObject(18, new Byte((byte)0));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("carried", (short)this.getCarried());
        par1NBTTagCompound.setShort("carriedData", (short)this.getCarryingData());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setCarried(par1NBTTagCompound.getShort("carried"));
        this.setCarryingData(par1NBTTagCompound.getShort("carriedData"));
    }
    
    @Override
    protected Entity findPlayerToAttack() {
        final EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 64.0);
        if (var1 != null) {
            if (this.shouldAttackPlayer(var1)) {
                this.field_104003_g = true;
                if (this.field_70826_g == 0) {
                    this.worldObj.playSoundAtEntity(var1, "mob.endermen.stare", 1.0f, 1.0f);
                }
                if (this.field_70826_g++ == 5) {
                    this.field_70826_g = 0;
                    this.setScreaming(true);
                    return var1;
                }
            }
            else {
                this.field_70826_g = 0;
            }
        }
        return null;
    }
    
    private boolean shouldAttackPlayer(final EntityPlayer par1EntityPlayer) {
        final ItemStack var2 = par1EntityPlayer.inventory.armorInventory[3];
        if (var2 != null && var2.itemID == Block.pumpkin.blockID) {
            return false;
        }
        final Vec3 var3 = par1EntityPlayer.getLook(1.0f).normalize();
        Vec3 var4 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX - par1EntityPlayer.posX, this.boundingBox.minY + this.height / 2.0f - (par1EntityPlayer.posY + par1EntityPlayer.getEyeHeight()), this.posZ - par1EntityPlayer.posZ);
        final double var5 = var4.lengthVector();
        var4 = var4.normalize();
        final double var6 = var3.dotProduct(var4);
        return var6 > 1.0 - 0.025 / var5 && par1EntityPlayer.canEntityBeSeen(this);
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1);
        }
        this.moveSpeed = ((this.entityToAttack != null) ? 6.5f : 0.3f);
        if (!this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
            if (this.getCarried() == 0) {
                if (this.rand.nextInt(20) == 0) {
                    final int var1 = MathHelper.floor_double(this.posX - 2.0 + this.rand.nextDouble() * 4.0);
                    final int var2 = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 3.0);
                    final int var3 = MathHelper.floor_double(this.posZ - 2.0 + this.rand.nextDouble() * 4.0);
                    final int var4 = this.worldObj.getBlockId(var1, var2, var3);
                    if (EntityEnderman.carriableBlocks[var4]) {
                        this.setCarried(this.worldObj.getBlockId(var1, var2, var3));
                        this.setCarryingData(this.worldObj.getBlockMetadata(var1, var2, var3));
                        this.worldObj.setBlock(var1, var2, var3, 0);
                    }
                }
            }
            else if (this.rand.nextInt(2000) == 0) {
                final int var1 = MathHelper.floor_double(this.posX - 1.0 + this.rand.nextDouble() * 2.0);
                final int var2 = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 2.0);
                final int var3 = MathHelper.floor_double(this.posZ - 1.0 + this.rand.nextDouble() * 2.0);
                final int var4 = this.worldObj.getBlockId(var1, var2, var3);
                final int var5 = this.worldObj.getBlockId(var1, var2 - 1, var3);
                if (var4 == 0 && var5 > 0 && Block.blocksList[var5].renderAsNormalBlock()) {
                    this.worldObj.setBlock(var1, var2, var3, this.getCarried(), this.getCarryingData(), 3);
                    this.setCarried(0);
                }
            }
        }
        for (int var1 = 0; var1 < 2; ++var1) {
            this.worldObj.spawnParticle("portal", this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, (this.rand.nextDouble() - 0.5) * 2.0, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5) * 2.0);
        }
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote) {
            final float var6 = this.getBrightness(1.0f);
            if (var6 > 0.5f && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0f < (var6 - 0.4f) * 2.0f) {
                this.entityToAttack = null;
                this.setScreaming(false);
                this.field_104003_g = false;
                this.teleportRandomly();
            }
        }
        if (this.isWet() || this.isBurning()) {
            this.entityToAttack = null;
            this.setScreaming(false);
            this.field_104003_g = false;
            this.teleportRandomly();
        }
        if (this.isScreaming() && !this.field_104003_g && this.rand.nextInt(100) == 0) {
            this.setScreaming(false);
        }
        this.isJumping = false;
        if (this.entityToAttack != null) {
            this.faceEntity(this.entityToAttack, 100.0f, 100.0f);
        }
        if (!this.worldObj.isRemote && this.isEntityAlive()) {
            if (this.entityToAttack != null) {
                if (this.entityToAttack instanceof EntityPlayer && this.shouldAttackPlayer((EntityPlayer)this.entityToAttack)) {
                    final float n = 0.0f;
                    this.moveForward = n;
                    this.moveStrafing = n;
                    this.moveSpeed = 0.0f;
                    if (this.entityToAttack.getDistanceSqToEntity(this) < 16.0) {
                        this.teleportRandomly();
                    }
                    this.teleportDelay = 0;
                }
                else if (this.entityToAttack.getDistanceSqToEntity(this) > 256.0 && this.teleportDelay++ >= 30 && this.teleportToEntity(this.entityToAttack)) {
                    this.teleportDelay = 0;
                }
            }
            else {
                this.setScreaming(false);
                this.teleportDelay = 0;
            }
        }
        super.onLivingUpdate();
    }
    
    protected boolean teleportRandomly() {
        final double var1 = this.posX + (this.rand.nextDouble() - 0.5) * 64.0;
        final double var2 = this.posY + (this.rand.nextInt(64) - 32);
        final double var3 = this.posZ + (this.rand.nextDouble() - 0.5) * 64.0;
        return this.teleportTo(var1, var2, var3);
    }
    
    protected boolean teleportToEntity(final Entity par1Entity) {
        Vec3 var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX - par1Entity.posX, this.boundingBox.minY + this.height / 2.0f - par1Entity.posY + par1Entity.getEyeHeight(), this.posZ - par1Entity.posZ);
        var2 = var2.normalize();
        final double var3 = 16.0;
        final double var4 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - var2.xCoord * var3;
        final double var5 = this.posY + (this.rand.nextInt(16) - 8) - var2.yCoord * var3;
        final double var6 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - var2.zCoord * var3;
        return this.teleportTo(var4, var5, var6);
    }
    
    protected boolean teleportTo(final double par1, final double par3, final double par5) {
        final double var7 = this.posX;
        final double var8 = this.posY;
        final double var9 = this.posZ;
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;
        boolean var10 = false;
        final int var11 = MathHelper.floor_double(this.posX);
        int var12 = MathHelper.floor_double(this.posY);
        final int var13 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.blockExists(var11, var12, var13)) {
            boolean var14 = false;
            while (!var14 && var12 > 0) {
                final int var15 = this.worldObj.getBlockId(var11, var12 - 1, var13);
                if (var15 != 0 && Block.blocksList[var15].blockMaterial.blocksMovement()) {
                    var14 = true;
                }
                else {
                    --this.posY;
                    --var12;
                }
            }
            if (var14) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox)) {
                    var10 = true;
                }
            }
        }
        if (!var10) {
            this.setPosition(var7, var8, var9);
            return false;
        }
        int var15;
        short var16;
        double var17;
        float var18;
        float var19;
        float var20;
        double var21;
        double var22;
        double var23;
        for (var16 = 128, var15 = 0; var15 < var16; ++var15) {
            var17 = var15 / (var16 - 1.0);
            var18 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            var19 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            var20 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            var21 = var7 + (this.posX - var7) * var17 + (this.rand.nextDouble() - 0.5) * this.width * 2.0;
            var22 = var8 + (this.posY - var8) * var17 + this.rand.nextDouble() * this.height;
            var23 = var9 + (this.posZ - var9) * var17 + (this.rand.nextDouble() - 0.5) * this.width * 2.0;
            this.worldObj.spawnParticle("portal", var21, var22, var23, var18, var19, var20);
        }
        this.worldObj.playSoundEffect(var7, var8, var9, "mob.endermen.portal", 1.0f, 1.0f);
        this.playSound("mob.endermen.portal", 1.0f, 1.0f);
        return true;
    }
    
    @Override
    protected String getLivingSound() {
        return this.isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.endermen.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.endermen.death";
    }
    
    @Override
    protected int getDropItemId() {
        return Item.enderPearl.itemID;
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        final int var3 = this.getDropItemId();
        if (var3 > 0) {
            for (int var4 = this.rand.nextInt(2 + par2), var5 = 0; var5 < var4; ++var5) {
                this.dropItem(var3, 1);
            }
        }
    }
    
    public void setCarried(final int par1) {
        this.dataWatcher.updateObject(16, (byte)(par1 & 0xFF));
    }
    
    public int getCarried() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }
    
    public void setCarryingData(final int par1) {
        this.dataWatcher.updateObject(17, (byte)(par1 & 0xFF));
    }
    
    public int getCarryingData() {
        return this.dataWatcher.getWatchableObjectByte(17);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setScreaming(true);
        if (par1DamageSource instanceof EntityDamageSource && par1DamageSource.getEntity() instanceof EntityPlayer) {
            this.field_104003_g = true;
        }
        if (par1DamageSource instanceof EntityDamageSourceIndirect) {
            this.field_104003_g = false;
            for (int var3 = 0; var3 < 64; ++var3) {
                if (this.teleportRandomly()) {
                    return true;
                }
            }
            return false;
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    public boolean isScreaming() {
        return this.dataWatcher.getWatchableObjectByte(18) > 0;
    }
    
    public void setScreaming(final boolean par1) {
        this.dataWatcher.updateObject(18, (byte)(byte)(par1 ? 1 : 0));
    }
    
    @Override
    public int getAttackStrength(final Entity par1Entity) {
        return 7;
    }
}
