package net.minecraft.src;

public class EntitySquid extends EntityWaterMob
{
    public float squidPitch;
    public float prevSquidPitch;
    public float squidYaw;
    public float prevSquidYaw;
    public float field_70867_h;
    public float field_70868_i;
    public float tentacleAngle;
    public float prevTentacleAngle;
    private float randomMotionSpeed;
    private float field_70864_bA;
    private float field_70871_bB;
    private float randomMotionVecX;
    private float randomMotionVecY;
    private float randomMotionVecZ;
    
    public EntitySquid(final World par1World) {
        super(par1World);
        this.squidPitch = 0.0f;
        this.prevSquidPitch = 0.0f;
        this.squidYaw = 0.0f;
        this.prevSquidYaw = 0.0f;
        this.field_70867_h = 0.0f;
        this.field_70868_i = 0.0f;
        this.tentacleAngle = 0.0f;
        this.prevTentacleAngle = 0.0f;
        this.randomMotionSpeed = 0.0f;
        this.field_70864_bA = 0.0f;
        this.field_70871_bB = 0.0f;
        this.randomMotionVecX = 0.0f;
        this.randomMotionVecY = 0.0f;
        this.randomMotionVecZ = 0.0f;
        this.texture = "/mob/squid.png";
        this.setSize(0.95f, 0.95f);
        this.field_70864_bA = 1.0f / (this.rand.nextFloat() + 1.0f) * 0.2f;
    }
    
    @Override
    public int getMaxHealth() {
        return 10;
    }
    
    @Override
    protected String getLivingSound() {
        return null;
    }
    
    @Override
    protected String getHurtSound() {
        return null;
    }
    
    @Override
    protected String getDeathSound() {
        return null;
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }
    
    @Override
    protected int getDropItemId() {
        return 0;
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        for (int var3 = this.rand.nextInt(3 + par2) + 1, var4 = 0; var4 < var3; ++var4) {
            this.entityDropItem(new ItemStack(Item.dyePowder, 1, 0), 0.0f);
        }
    }
    
    @Override
    public boolean isInWater() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0, -0.6000000238418579, 0.0), Material.water, this);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.prevSquidPitch = this.squidPitch;
        this.prevSquidYaw = this.squidYaw;
        this.field_70868_i = this.field_70867_h;
        this.prevTentacleAngle = this.tentacleAngle;
        this.field_70867_h += this.field_70864_bA;
        if (this.field_70867_h > 6.2831855f) {
            this.field_70867_h -= 6.2831855f;
            if (this.rand.nextInt(10) == 0) {
                this.field_70864_bA = 1.0f / (this.rand.nextFloat() + 1.0f) * 0.2f;
            }
        }
        if (this.isInWater()) {
            if (this.field_70867_h < 3.1415927f) {
                final float var1 = this.field_70867_h / 3.1415927f;
                this.tentacleAngle = MathHelper.sin(var1 * var1 * 3.1415927f) * 3.1415927f * 0.25f;
                if (var1 > 0.75) {
                    this.randomMotionSpeed = 1.0f;
                    this.field_70871_bB = 1.0f;
                }
                else {
                    this.field_70871_bB *= 0.8f;
                }
            }
            else {
                this.tentacleAngle = 0.0f;
                this.randomMotionSpeed *= 0.9f;
                this.field_70871_bB *= 0.99f;
            }
            if (!this.worldObj.isRemote) {
                this.motionX = this.randomMotionVecX * this.randomMotionSpeed;
                this.motionY = this.randomMotionVecY * this.randomMotionSpeed;
                this.motionZ = this.randomMotionVecZ * this.randomMotionSpeed;
            }
            final float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.renderYawOffset += (-(float)Math.atan2(this.motionX, this.motionZ) * 180.0f / 3.1415927f - this.renderYawOffset) * 0.1f;
            this.rotationYaw = this.renderYawOffset;
            this.squidYaw += 3.1415927f * this.field_70871_bB * 1.5f;
            this.squidPitch += (-(float)Math.atan2(var1, this.motionY) * 180.0f / 3.1415927f - this.squidPitch) * 0.1f;
        }
        else {
            this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.field_70867_h)) * 3.1415927f * 0.25f;
            if (!this.worldObj.isRemote) {
                this.motionX = 0.0;
                this.motionY -= 0.08;
                this.motionY *= 0.9800000190734863;
                this.motionZ = 0.0;
            }
            this.squidPitch += (float)((-90.0f - this.squidPitch) * 0.02);
        }
    }
    
    @Override
    public void moveEntityWithHeading(final float par1, final float par2) {
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }
    
    @Override
    protected void updateEntityActionState() {
        ++this.entityAge;
        if (this.entityAge > 100) {
            final float randomMotionVecX = 0.0f;
            this.randomMotionVecZ = randomMotionVecX;
            this.randomMotionVecY = randomMotionVecX;
            this.randomMotionVecX = randomMotionVecX;
        }
        else if (this.rand.nextInt(50) == 0 || !this.inWater || (this.randomMotionVecX == 0.0f && this.randomMotionVecY == 0.0f && this.randomMotionVecZ == 0.0f)) {
            final float var1 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            this.randomMotionVecX = MathHelper.cos(var1) * 0.2f;
            this.randomMotionVecY = -0.1f + this.rand.nextFloat() * 0.2f;
            this.randomMotionVecZ = MathHelper.sin(var1) * 0.2f;
        }
        this.despawnEntity();
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.posY > 45.0 && this.posY < 63.0 && super.getCanSpawnHere();
    }
}
