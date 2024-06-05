package net.minecraft.src;

public class EntityEnderCrystal extends Entity
{
    public int innerRotation;
    public int health;
    
    public EntityEnderCrystal(final World par1World) {
        super(par1World);
        this.innerRotation = 0;
        this.preventEntitySpawning = true;
        this.setSize(2.0f, 2.0f);
        this.yOffset = this.height / 2.0f;
        this.health = 5;
        this.innerRotation = this.rand.nextInt(100000);
    }
    
    public EntityEnderCrystal(final World par1World, final double par2, final double par4, final double par6) {
        this(par1World);
        this.setPosition(par2, par4, par6);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(8, this.health);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.innerRotation;
        this.dataWatcher.updateObject(8, this.health);
        final int var1 = MathHelper.floor_double(this.posX);
        final int var2 = MathHelper.floor_double(this.posY);
        final int var3 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.getBlockId(var1, var2, var3) != Block.fire.blockID) {
            this.worldObj.setBlock(var1, var2, var3, Block.fire.blockID);
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (!this.isDead && !this.worldObj.isRemote) {
            this.health = 0;
            if (this.health <= 0) {
                this.setDead();
                if (!this.worldObj.isRemote) {
                    this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, 6.0f, true);
                }
            }
        }
        return true;
    }
}
