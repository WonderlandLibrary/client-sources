package net.minecraft.src;

public class EntityMinecartTNT extends EntityMinecart
{
    private int minecartTNTFuse;
    
    public EntityMinecartTNT(final World par1) {
        super(par1);
        this.minecartTNTFuse = -1;
    }
    
    public EntityMinecartTNT(final World par1, final double par2, final double par4, final double par6) {
        super(par1, par2, par4, par6);
        this.minecartTNTFuse = -1;
    }
    
    @Override
    public int getMinecartType() {
        return 3;
    }
    
    @Override
    public Block getDefaultDisplayTile() {
        return Block.tnt;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.minecartTNTFuse > 0) {
            --this.minecartTNTFuse;
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
        }
        else if (this.minecartTNTFuse == 0) {
            this.explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
        }
        if (this.isCollidedHorizontally) {
            final double var1 = this.motionX * this.motionX + this.motionZ * this.motionZ;
            if (var1 >= 0.009999999776482582) {
                this.explodeCart(var1);
            }
        }
    }
    
    @Override
    public void killMinecart(final DamageSource par1DamageSource) {
        super.killMinecart(par1DamageSource);
        final double var2 = this.motionX * this.motionX + this.motionZ * this.motionZ;
        if (!par1DamageSource.isExplosion()) {
            this.entityDropItem(new ItemStack(Block.tnt, 1), 0.0f);
        }
        if (par1DamageSource.isFireDamage() || par1DamageSource.isExplosion() || var2 >= 0.009999999776482582) {
            this.explodeCart(var2);
        }
    }
    
    protected void explodeCart(final double par1) {
        if (!this.worldObj.isRemote) {
            double var3 = Math.sqrt(par1);
            if (var3 > 5.0) {
                var3 = 5.0;
            }
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(4.0 + this.rand.nextDouble() * 1.5 * var3), true);
            this.setDead();
        }
    }
    
    @Override
    protected void fall(final float par1) {
        if (par1 >= 3.0f) {
            final float var2 = par1 / 10.0f;
            this.explodeCart(var2 * var2);
        }
        super.fall(par1);
    }
    
    @Override
    public void onActivatorRailPass(final int par1, final int par2, final int par3, final boolean par4) {
        if (par4 && this.minecartTNTFuse < 0) {
            this.ignite();
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte par1) {
        if (par1 == 10) {
            this.ignite();
        }
        else {
            super.handleHealthUpdate(par1);
        }
    }
    
    public void ignite() {
        this.minecartTNTFuse = 80;
        if (!this.worldObj.isRemote) {
            this.worldObj.setEntityState(this, (byte)10);
            this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0f, 1.0f);
        }
    }
    
    public int func_94104_d() {
        return this.minecartTNTFuse;
    }
    
    public boolean isIgnited() {
        return this.minecartTNTFuse > -1;
    }
    
    @Override
    public float func_82146_a(final Explosion par1Explosion, final World par2World, final int par3, final int par4, final int par5, final Block par6Block) {
        return (this.isIgnited() && (BlockRailBase.isRailBlock(par6Block.blockID) || BlockRailBase.isRailBlockAt(par2World, par3, par4 + 1, par5))) ? 0.0f : super.func_82146_a(par1Explosion, par2World, par3, par4, par5, par6Block);
    }
    
    @Override
    public boolean func_96091_a(final Explosion par1Explosion, final World par2World, final int par3, final int par4, final int par5, final int par6, final float par7) {
        return (!this.isIgnited() || (!BlockRailBase.isRailBlock(par6) && !BlockRailBase.isRailBlockAt(par2World, par3, par4 + 1, par5))) && super.func_96091_a(par1Explosion, par2World, par3, par4, par5, par6, par7);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.hasKey("TNTFuse")) {
            this.minecartTNTFuse = par1NBTTagCompound.getInteger("TNTFuse");
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("TNTFuse", this.minecartTNTFuse);
    }
}
