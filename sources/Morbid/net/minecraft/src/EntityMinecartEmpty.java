package net.minecraft.src;

public class EntityMinecartEmpty extends EntityMinecart
{
    public EntityMinecartEmpty(final World par1World) {
        super(par1World);
    }
    
    public EntityMinecartEmpty(final World par1, final double par2, final double par4, final double par6) {
        super(par1, par2, par4, par6);
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != par1EntityPlayer) {
            return true;
        }
        if (this.riddenByEntity != null && this.riddenByEntity != par1EntityPlayer) {
            return false;
        }
        if (!this.worldObj.isRemote) {
            par1EntityPlayer.mountEntity(this);
        }
        return true;
    }
    
    @Override
    public int getMinecartType() {
        return 0;
    }
}
