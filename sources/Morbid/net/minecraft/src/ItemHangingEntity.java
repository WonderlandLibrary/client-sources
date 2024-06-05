package net.minecraft.src;

public class ItemHangingEntity extends Item
{
    private final Class hangingEntityClass;
    
    public ItemHangingEntity(final int par1, final Class par2Class) {
        super(par1);
        this.hangingEntityClass = par2Class;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        if (par7 == 0) {
            return false;
        }
        if (par7 == 1) {
            return false;
        }
        final int var11 = Direction.facingToDirection[par7];
        final EntityHanging var12 = this.createHangingEntity(par3World, par4, par5, par6, var11);
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        if (var12 != null && var12.onValidSurface()) {
            if (!par3World.isRemote) {
                par3World.spawnEntityInWorld(var12);
            }
            --par1ItemStack.stackSize;
        }
        return true;
    }
    
    private EntityHanging createHangingEntity(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return (this.hangingEntityClass == EntityPainting.class) ? new EntityPainting(par1World, par2, par3, par4, par5) : ((this.hangingEntityClass == EntityItemFrame.class) ? new EntityItemFrame(par1World, par2, par3, par4, par5) : null);
    }
}
