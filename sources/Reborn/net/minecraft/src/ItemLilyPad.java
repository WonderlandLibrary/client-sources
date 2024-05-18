package net.minecraft.src;

public class ItemLilyPad extends ItemColored
{
    public ItemLilyPad(final int par1) {
        super(par1, false);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        final MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
        if (var4 == null) {
            return par1ItemStack;
        }
        if (var4.typeOfHit == EnumMovingObjectType.TILE) {
            final int var5 = var4.blockX;
            final int var6 = var4.blockY;
            final int var7 = var4.blockZ;
            if (!par2World.canMineBlock(par3EntityPlayer, var5, var6, var7)) {
                return par1ItemStack;
            }
            if (!par3EntityPlayer.canPlayerEdit(var5, var6, var7, var4.sideHit, par1ItemStack)) {
                return par1ItemStack;
            }
            if (par2World.getBlockMaterial(var5, var6, var7) == Material.water && par2World.getBlockMetadata(var5, var6, var7) == 0 && par2World.isAirBlock(var5, var6 + 1, var7)) {
                par2World.setBlock(var5, var6 + 1, var7, Block.waterlily.blockID);
                if (!par3EntityPlayer.capabilities.isCreativeMode) {
                    --par1ItemStack.stackSize;
                }
            }
        }
        return par1ItemStack;
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack par1ItemStack, final int par2) {
        return Block.waterlily.getRenderColor(par1ItemStack.getItemDamage());
    }
}
