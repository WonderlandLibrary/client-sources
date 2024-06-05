package net.minecraft.src;

public class EntityIronGolem extends EntityGolem
{
    private int homeCheckTimer;
    Village villageObj;
    private int attackTimer;
    private int holdRoseTick;
    
    public EntityIronGolem(final World par1World) {
        super(par1World);
        this.homeCheckTimer = 0;
        this.villageObj = null;
        this.texture = "/mob/villager_golem.png";
        this.setSize(1.4f, 2.9f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 0.25f, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.22f, 32.0f));
        this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.16f, true));
        this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, 0.16f));
        this.tasks.addTask(5, new EntityAILookAtVillager(this));
        this.tasks.addTask(6, new EntityAIWander(this, 0.16f));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIDefendVillage(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 16.0f, 0, false, true, IMob.mobSelector));
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    protected void updateAITick() {
        final int homeCheckTimer = this.homeCheckTimer - 1;
        this.homeCheckTimer = homeCheckTimer;
        if (homeCheckTimer <= 0) {
            this.homeCheckTimer = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);
            if (this.villageObj == null) {
                this.detachHome();
            }
            else {
                final ChunkCoordinates var1 = this.villageObj.getCenter();
                this.setHomeArea(var1.posX, var1.posY, var1.posZ, (int)(this.villageObj.getVillageRadius() * 0.6f));
            }
        }
        super.updateAITick();
    }
    
    @Override
    public int getMaxHealth() {
        return 100;
    }
    
    @Override
    protected int decreaseAirSupply(final int par1) {
        return par1;
    }
    
    @Override
    protected void collideWithEntity(final Entity par1Entity) {
        if (par1Entity instanceof IMob && this.getRNG().nextInt(20) == 0) {
            this.setAttackTarget((EntityLiving)par1Entity);
        }
        super.collideWithEntity(par1Entity);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        if (this.holdRoseTick > 0) {
            --this.holdRoseTick;
        }
        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7 && this.rand.nextInt(5) == 0) {
            final int var1 = MathHelper.floor_double(this.posX);
            final int var2 = MathHelper.floor_double(this.posY - 0.20000000298023224 - this.yOffset);
            final int var3 = MathHelper.floor_double(this.posZ);
            final int var4 = this.worldObj.getBlockId(var1, var2, var3);
            if (var4 > 0) {
                this.worldObj.spawnParticle("tilecrack_" + var4 + "_" + this.worldObj.getBlockMetadata(var1, var2, var3), this.posX + (this.rand.nextFloat() - 0.5) * this.width, this.boundingBox.minY + 0.1, this.posZ + (this.rand.nextFloat() - 0.5) * this.width, 4.0 * (this.rand.nextFloat() - 0.5), 0.5, (this.rand.nextFloat() - 0.5) * 4.0);
            }
        }
    }
    
    @Override
    public boolean canAttackClass(final Class par1Class) {
        return (!this.isPlayerCreated() || !EntityPlayer.class.isAssignableFrom(par1Class)) && super.canAttackClass(par1Class);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("PlayerCreated", this.isPlayerCreated());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setPlayerCreated(par1NBTTagCompound.getBoolean("PlayerCreated"));
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity par1Entity) {
        this.attackTimer = 10;
        this.worldObj.setEntityState(this, (byte)4);
        final boolean var2 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 7 + this.rand.nextInt(15));
        if (var2) {
            par1Entity.motionY += 0.4000000059604645;
        }
        this.playSound("mob.irongolem.throw", 1.0f, 1.0f);
        return var2;
    }
    
    @Override
    public void handleHealthUpdate(final byte par1) {
        if (par1 == 4) {
            this.attackTimer = 10;
            this.playSound("mob.irongolem.throw", 1.0f, 1.0f);
        }
        else if (par1 == 11) {
            this.holdRoseTick = 400;
        }
        else {
            super.handleHealthUpdate(par1);
        }
    }
    
    public Village getVillage() {
        return this.villageObj;
    }
    
    public int getAttackTimer() {
        return this.attackTimer;
    }
    
    public void setHoldingRose(final boolean par1) {
        this.holdRoseTick = (par1 ? 400 : 0);
        this.worldObj.setEntityState(this, (byte)11);
    }
    
    @Override
    protected String getLivingSound() {
        return "none";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.irongolem.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.irongolem.death";
    }
    
    @Override
    protected void playStepSound(final int par1, final int par2, final int par3, final int par4) {
        this.playSound("mob.irongolem.walk", 1.0f, 1.0f);
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        for (int var3 = this.rand.nextInt(3), var4 = 0; var4 < var3; ++var4) {
            this.dropItem(Block.plantRed.blockID, 1);
        }
        for (int var4 = 3 + this.rand.nextInt(3), var5 = 0; var5 < var4; ++var5) {
            this.dropItem(Item.ingotIron.itemID, 1);
        }
    }
    
    public int getHoldRoseTick() {
        return this.holdRoseTick;
    }
    
    public boolean isPlayerCreated() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void setPlayerCreated(final boolean par1) {
        final byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (par1) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 0x1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFFE));
        }
    }
    
    @Override
    public void onDeath(final DamageSource par1DamageSource) {
        if (!this.isPlayerCreated() && this.attackingPlayer != null && this.villageObj != null) {
            this.villageObj.setReputationForPlayer(this.attackingPlayer.getCommandSenderName(), -5);
        }
        super.onDeath(par1DamageSource);
    }
}
