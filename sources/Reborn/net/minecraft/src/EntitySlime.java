package net.minecraft.src;

public class EntitySlime extends EntityLiving implements IMob
{
    private static final float[] spawnChances;
    public float field_70813_a;
    public float field_70811_b;
    public float field_70812_c;
    private int slimeJumpDelay;
    
    static {
        spawnChances = new float[] { 1.0f, 0.75f, 0.5f, 0.25f, 0.0f, 0.25f, 0.5f, 0.75f };
    }
    
    public EntitySlime(final World par1World) {
        super(par1World);
        this.slimeJumpDelay = 0;
        this.texture = "/mob/slime.png";
        final int var2 = 1 << this.rand.nextInt(3);
        this.yOffset = 0.0f;
        this.slimeJumpDelay = this.rand.nextInt(20) + 10;
        this.setSlimeSize(var2);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)1));
    }
    
    protected void setSlimeSize(final int par1) {
        this.dataWatcher.updateObject(16, new Byte((byte)par1));
        this.setSize(0.6f * par1, 0.6f * par1);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setEntityHealth(this.getMaxHealth());
        this.experienceValue = par1;
    }
    
    @Override
    public int getMaxHealth() {
        final int var1 = this.getSlimeSize();
        return var1 * var1;
    }
    
    public int getSlimeSize() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Size", this.getSlimeSize() - 1);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setSlimeSize(par1NBTTagCompound.getInteger("Size") + 1);
    }
    
    protected String getSlimeParticle() {
        return "slime";
    }
    
    protected String getJumpSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    @Override
    public void onUpdate() {
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == 0 && this.getSlimeSize() > 0) {
            this.isDead = true;
        }
        this.field_70811_b += (this.field_70813_a - this.field_70811_b) * 0.5f;
        this.field_70812_c = this.field_70811_b;
        final boolean var1 = this.onGround;
        super.onUpdate();
        if (this.onGround && !var1) {
            for (int var2 = this.getSlimeSize(), var3 = 0; var3 < var2 * 8; ++var3) {
                final float var4 = this.rand.nextFloat() * 3.1415927f * 2.0f;
                final float var5 = this.rand.nextFloat() * 0.5f + 0.5f;
                final float var6 = MathHelper.sin(var4) * var2 * 0.5f * var5;
                final float var7 = MathHelper.cos(var4) * var2 * 0.5f * var5;
                this.worldObj.spawnParticle(this.getSlimeParticle(), this.posX + var6, this.boundingBox.minY, this.posZ + var7, 0.0, 0.0, 0.0);
            }
            if (this.makesSoundOnLand()) {
                this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) / 0.8f);
            }
            this.field_70813_a = -0.5f;
        }
        else if (!this.onGround && var1) {
            this.field_70813_a = 1.0f;
        }
        this.func_70808_l();
        if (this.worldObj.isRemote) {
            final int var2 = this.getSlimeSize();
            this.setSize(0.6f * var2, 0.6f * var2);
        }
    }
    
    @Override
    protected void updateEntityActionState() {
        this.despawnEntity();
        final EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0);
        if (var1 != null) {
            this.faceEntity(var1, 10.0f, 20.0f);
        }
        if (this.onGround && this.slimeJumpDelay-- <= 0) {
            this.slimeJumpDelay = this.getJumpDelay();
            if (var1 != null) {
                this.slimeJumpDelay /= 3;
            }
            this.isJumping = true;
            if (this.makesSoundOnJump()) {
                this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 0.8f);
            }
            this.moveStrafing = 1.0f - this.rand.nextFloat() * 2.0f;
            this.moveForward = 1 * this.getSlimeSize();
        }
        else {
            this.isJumping = false;
            if (this.onGround) {
                final float n = 0.0f;
                this.moveForward = n;
                this.moveStrafing = n;
            }
        }
    }
    
    protected void func_70808_l() {
        this.field_70813_a *= 0.6f;
    }
    
    protected int getJumpDelay() {
        return this.rand.nextInt(20) + 10;
    }
    
    protected EntitySlime createInstance() {
        return new EntitySlime(this.worldObj);
    }
    
    @Override
    public void setDead() {
        final int var1 = this.getSlimeSize();
        if (!this.worldObj.isRemote && var1 > 1 && this.getHealth() <= 0) {
            for (int var2 = 2 + this.rand.nextInt(3), var3 = 0; var3 < var2; ++var3) {
                final float var4 = (var3 % 2 - 0.5f) * var1 / 4.0f;
                final float var5 = (var3 / 2 - 0.5f) * var1 / 4.0f;
                final EntitySlime var6 = this.createInstance();
                var6.setSlimeSize(var1 / 2);
                var6.setLocationAndAngles(this.posX + var4, this.posY + 0.5, this.posZ + var5, this.rand.nextFloat() * 360.0f, 0.0f);
                this.worldObj.spawnEntityInWorld(var6);
            }
        }
        super.setDead();
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer par1EntityPlayer) {
        if (this.canDamagePlayer()) {
            final int var2 = this.getSlimeSize();
            if (this.canEntityBeSeen(par1EntityPlayer) && this.getDistanceSqToEntity(par1EntityPlayer) < 0.6 * var2 * 0.6 * var2 && par1EntityPlayer.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackStrength())) {
                this.playSound("mob.attack", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
        }
    }
    
    protected boolean canDamagePlayer() {
        return this.getSlimeSize() > 1;
    }
    
    protected int getAttackStrength() {
        return this.getSlimeSize();
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    @Override
    protected int getDropItemId() {
        return (this.getSlimeSize() == 1) ? Item.slimeBall.itemID : 0;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        final Chunk var1 = this.worldObj.getChunkFromBlockCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
        if (this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT && this.rand.nextInt(4) != 1) {
            return false;
        }
        if (this.getSlimeSize() == 1 || this.worldObj.difficultySetting > 0) {
            final BiomeGenBase var2 = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
            if (var2 == BiomeGenBase.swampland && this.posY > 50.0 && this.posY < 70.0 && this.rand.nextFloat() < 0.5f && this.rand.nextFloat() < EntitySlime.spawnChances[this.worldObj.getMoonPhase()] && this.worldObj.getBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) <= this.rand.nextInt(8)) {
                return super.getCanSpawnHere();
            }
            if (this.rand.nextInt(10) == 0 && var1.getRandomWithSeed(987234911L).nextInt(10) == 0 && this.posY < 40.0) {
                return super.getCanSpawnHere();
            }
        }
        return false;
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f * this.getSlimeSize();
    }
    
    @Override
    public int getVerticalFaceSpeed() {
        return 0;
    }
    
    protected boolean makesSoundOnJump() {
        return this.getSlimeSize() > 0;
    }
    
    protected boolean makesSoundOnLand() {
        return this.getSlimeSize() > 2;
    }
}
