package net.minecraft.src;

public class EntityTNTPrimed extends Entity
{
    public int fuse;
    private EntityLiving tntPlacedBy;
    
    public EntityTNTPrimed(final World par1World) {
        super(par1World);
        this.fuse = 0;
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        this.yOffset = this.height / 2.0f;
    }
    
    public EntityTNTPrimed(final World par1World, final double par2, final double par4, final double par6, final EntityLiving par8EntityLiving) {
        this(par1World);
        this.setPosition(par2, par4, par6);
        final float var9 = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.motionX = -(float)Math.sin(var9) * 0.02f;
        this.motionY = 0.20000000298023224;
        this.motionZ = -(float)Math.cos(var9) * 0.02f;
        this.fuse = 80;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
        this.tntPlacedBy = par8EntityLiving;
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
            this.motionY *= -0.5;
        }
        if (this.fuse-- <= 0) {
            this.setDead();
            if (!this.worldObj.isRemote) {
                this.explode();
            }
        }
        else {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
        }
    }
    
    private void explode() {
        final float var1 = 4.0f;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, var1, true);
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setByte("Fuse", (byte)this.fuse);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.fuse = par1NBTTagCompound.getByte("Fuse");
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    public EntityLiving getTntPlacedBy() {
        return this.tntPlacedBy;
    }
}
