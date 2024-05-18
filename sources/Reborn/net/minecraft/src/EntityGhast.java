package net.minecraft.src;

public class EntityGhast extends EntityFlying implements IMob
{
    public int courseChangeCooldown;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    private Entity targetedEntity;
    private int aggroCooldown;
    public int prevAttackCounter;
    public int attackCounter;
    private int explosionStrength;
    
    public EntityGhast(final World par1World) {
        super(par1World);
        this.courseChangeCooldown = 0;
        this.targetedEntity = null;
        this.aggroCooldown = 0;
        this.prevAttackCounter = 0;
        this.attackCounter = 0;
        this.explosionStrength = 1;
        this.texture = "/mob/ghast.png";
        this.setSize(4.0f, 4.0f);
        this.isImmuneToFire = true;
        this.experienceValue = 5;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if ("fireball".equals(par1DamageSource.getDamageType()) && par1DamageSource.getEntity() instanceof EntityPlayer) {
            super.attackEntityFrom(par1DamageSource, 1000);
            ((EntityPlayer)par1DamageSource.getEntity()).triggerAchievement(AchievementList.ghast);
            return true;
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
    }
    
    @Override
    public int getMaxHealth() {
        return 10;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        final byte var1 = this.dataWatcher.getWatchableObjectByte(16);
        this.texture = ((var1 == 1) ? "/mob/ghast_fire.png" : "/mob/ghast.png");
    }
    
    @Override
    protected void updateEntityActionState() {
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == 0) {
            this.setDead();
        }
        this.despawnEntity();
        this.prevAttackCounter = this.attackCounter;
        final double var1 = this.waypointX - this.posX;
        final double var2 = this.waypointY - this.posY;
        final double var3 = this.waypointZ - this.posZ;
        double var4 = var1 * var1 + var2 * var2 + var3 * var3;
        if (var4 < 1.0 || var4 > 3600.0) {
            this.waypointX = this.posX + (this.rand.nextFloat() * 2.0f - 1.0f) * 16.0f;
            this.waypointY = this.posY + (this.rand.nextFloat() * 2.0f - 1.0f) * 16.0f;
            this.waypointZ = this.posZ + (this.rand.nextFloat() * 2.0f - 1.0f) * 16.0f;
        }
        if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.rand.nextInt(5) + 2;
            var4 = MathHelper.sqrt_double(var4);
            if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, var4)) {
                this.motionX += var1 / var4 * 0.1;
                this.motionY += var2 / var4 * 0.1;
                this.motionZ += var3 / var4 * 0.1;
            }
            else {
                this.waypointX = this.posX;
                this.waypointY = this.posY;
                this.waypointZ = this.posZ;
            }
        }
        if (this.targetedEntity != null && this.targetedEntity.isDead) {
            this.targetedEntity = null;
        }
        if (this.targetedEntity == null || this.aggroCooldown-- <= 0) {
            this.targetedEntity = this.worldObj.getClosestVulnerablePlayerToEntity(this, 100.0);
            if (this.targetedEntity != null) {
                this.aggroCooldown = 20;
            }
        }
        final double var5 = 64.0;
        if (this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < var5 * var5) {
            final double var6 = this.targetedEntity.posX - this.posX;
            final double var7 = this.targetedEntity.boundingBox.minY + this.targetedEntity.height / 2.0f - (this.posY + this.height / 2.0f);
            final double var8 = this.targetedEntity.posZ - this.posZ;
            final float n = -(float)Math.atan2(var6, var8) * 180.0f / 3.1415927f;
            this.rotationYaw = n;
            this.renderYawOffset = n;
            if (this.canEntityBeSeen(this.targetedEntity)) {
                if (this.attackCounter == 10) {
                    this.worldObj.playAuxSFXAtEntity(null, 1007, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                }
                ++this.attackCounter;
                if (this.attackCounter == 20) {
                    this.worldObj.playAuxSFXAtEntity(null, 1008, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                    final EntityLargeFireball var9 = new EntityLargeFireball(this.worldObj, this, var6, var7, var8);
                    var9.field_92057_e = this.explosionStrength;
                    final double var10 = 4.0;
                    final Vec3 var11 = this.getLook(1.0f);
                    var9.posX = this.posX + var11.xCoord * var10;
                    var9.posY = this.posY + this.height / 2.0f + 0.5;
                    var9.posZ = this.posZ + var11.zCoord * var10;
                    this.worldObj.spawnEntityInWorld(var9);
                    this.attackCounter = -40;
                }
            }
            else if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        }
        else {
            final float n2 = -(float)Math.atan2(this.motionX, this.motionZ) * 180.0f / 3.1415927f;
            this.rotationYaw = n2;
            this.renderYawOffset = n2;
            if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        }
        if (!this.worldObj.isRemote) {
            final byte var12 = this.dataWatcher.getWatchableObjectByte(16);
            final byte var13 = (byte)((this.attackCounter > 10) ? 1 : 0);
            if (var12 != var13) {
                this.dataWatcher.updateObject(16, var13);
            }
        }
    }
    
    private boolean isCourseTraversable(final double par1, final double par3, final double par5, final double par7) {
        final double var9 = (this.waypointX - this.posX) / par7;
        final double var10 = (this.waypointY - this.posY) / par7;
        final double var11 = (this.waypointZ - this.posZ) / par7;
        final AxisAlignedBB var12 = this.boundingBox.copy();
        for (int var13 = 1; var13 < par7; ++var13) {
            var12.offset(var9, var10, var11);
            if (!this.worldObj.getCollidingBoundingBoxes(this, var12).isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.ghast.moan";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.ghast.scream";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.ghast.death";
    }
    
    @Override
    protected int getDropItemId() {
        return Item.gunpowder.itemID;
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        for (int var3 = this.rand.nextInt(2) + this.rand.nextInt(1 + par2), var4 = 0; var4 < var3; ++var4) {
            this.dropItem(Item.ghastTear.itemID, 1);
        }
        for (int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2), var4 = 0; var4 < var3; ++var4) {
            this.dropItem(Item.gunpowder.itemID, 1);
        }
    }
    
    @Override
    protected float getSoundVolume() {
        return 10.0f;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.worldObj.difficultySetting > 0;
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("ExplosionPower", this.explosionStrength);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.hasKey("ExplosionPower")) {
            this.explosionStrength = par1NBTTagCompound.getInteger("ExplosionPower");
        }
    }
}
