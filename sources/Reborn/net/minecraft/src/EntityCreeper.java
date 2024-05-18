package net.minecraft.src;

public class EntityCreeper extends EntityMob
{
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime;
    private int explosionRadius;
    
    public EntityCreeper(final World par1World) {
        super(par1World);
        this.fuseTime = 30;
        this.explosionRadius = 3;
        this.texture = "/mob/creeper.png";
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAICreeperSwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0f, 0.25f, 0.3f));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 0.25f, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.2f));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0f, 0, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public int func_82143_as() {
        return (this.getAttackTarget() == null) ? 3 : (3 + (this.health - 1));
    }
    
    @Override
    protected void fall(final float par1) {
        super.fall(par1);
        this.timeSinceIgnited += (int)(par1 * 1.5f);
        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }
    
    @Override
    public int getMaxHealth() {
        return 20;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)(-1));
        this.dataWatcher.addObject(17, (byte)0);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
            par1NBTTagCompound.setBoolean("powered", true);
        }
        par1NBTTagCompound.setShort("Fuse", (short)this.fuseTime);
        par1NBTTagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.dataWatcher.updateObject(17, (byte)(byte)(par1NBTTagCompound.getBoolean("powered") ? 1 : 0));
        if (par1NBTTagCompound.hasKey("Fuse")) {
            this.fuseTime = par1NBTTagCompound.getShort("Fuse");
        }
        if (par1NBTTagCompound.hasKey("ExplosionRadius")) {
            this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
            final int var1 = this.getCreeperState();
            if (var1 > 0 && this.timeSinceIgnited == 0) {
                this.playSound("random.fuse", 1.0f, 0.5f);
            }
            this.timeSinceIgnited += var1;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }
            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                if (!this.worldObj.isRemote) {
                    final boolean var2 = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
                    if (this.getPowered()) {
                        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * 2, var2);
                    }
                    else {
                        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius, var2);
                    }
                    this.setDead();
                }
            }
        }
        super.onUpdate();
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.creeper.say";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.creeper.death";
    }
    
    @Override
    public void onDeath(final DamageSource par1DamageSource) {
        super.onDeath(par1DamageSource);
        if (par1DamageSource.getEntity() instanceof EntitySkeleton) {
            final int var2 = Item.record13.itemID + this.rand.nextInt(Item.recordWait.itemID - Item.record13.itemID + 1);
            this.dropItem(var2, 1);
        }
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity par1Entity) {
        return true;
    }
    
    public boolean getPowered() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }
    
    public float getCreeperFlashIntensity(final float par1) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * par1) / (this.fuseTime - 2);
    }
    
    @Override
    protected int getDropItemId() {
        return Item.gunpowder.itemID;
    }
    
    public int getCreeperState() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }
    
    public void setCreeperState(final int par1) {
        this.dataWatcher.updateObject(16, (byte)par1);
    }
    
    @Override
    public void onStruckByLightning(final EntityLightningBolt par1EntityLightningBolt) {
        super.onStruckByLightning(par1EntityLightningBolt);
        this.dataWatcher.updateObject(17, (byte)1);
    }
}
