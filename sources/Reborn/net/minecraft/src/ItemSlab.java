package net.minecraft.src;

public class ItemSlab extends ItemBlock
{
    private final boolean isFullBlock;
    private final BlockHalfSlab theHalfSlab;
    private final BlockHalfSlab doubleSlab;
    
    public ItemSlab(final int par1, final BlockHalfSlab par2BlockHalfSlab, final BlockHalfSlab par3BlockHalfSlab, final boolean par4) {
        super(par1);
        this.theHalfSlab = par2BlockHalfSlab;
        this.doubleSlab = par3BlockHalfSlab;
        this.isFullBlock = par4;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public Icon getIconFromDamage(final int par1) {
        return Block.blocksList[this.itemID].getIcon(2, par1);
    }
    
    @Override
    public int getMetadata(final int par1) {
        return par1;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        return this.theHalfSlab.getFullSlabName(par1ItemStack.getItemDamage());
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        if (this.isFullBlock) {
            return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
        }
        if (par1ItemStack.stackSize == 0) {
            return false;
        }
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        final int var11 = par3World.getBlockId(par4, par5, par6);
        final int var12 = par3World.getBlockMetadata(par4, par5, par6);
        final int var13 = var12 & 0x7;
        final boolean var14 = (var12 & 0x8) != 0x0;
        if (((par7 == 1 && !var14) || (par7 == 0 && var14)) && var11 == this.theHalfSlab.blockID && var13 == par1ItemStack.getItemDamage()) {
            if (par3World.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlock(par4, par5, par6, this.doubleSlab.blockID, var13, 3)) {
                par3World.playSoundEffect(par4 + 0.5f, par5 + 0.5f, par6 + 0.5f, this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0f) / 2.0f, this.doubleSlab.stepSound.getPitch() * 0.8f);
                --par1ItemStack.stackSize;
            }
            return true;
        }
        return this.func_77888_a(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7) || super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
    }
    
    @Override
    public boolean canPlaceItemBlockOnSide(final World par1World, int par2, int par3, int par4, final int par5, final EntityPlayer par6EntityPlayer, final ItemStack par7ItemStack) {
        final int var8 = par2;
        final int var9 = par3;
        final int var10 = par4;
        int var11 = par1World.getBlockId(par2, par3, par4);
        int var12 = par1World.getBlockMetadata(par2, par3, par4);
        int var13 = var12 & 0x7;
        boolean var14 = (var12 & 0x8) != 0x0;
        if (((par5 == 1 && !var14) || (par5 == 0 && var14)) && var11 == this.theHalfSlab.blockID && var13 == par7ItemStack.getItemDamage()) {
            return true;
        }
        if (par5 == 0) {
            --par3;
        }
        if (par5 == 1) {
            ++par3;
        }
        if (par5 == 2) {
            --par4;
        }
        if (par5 == 3) {
            ++par4;
        }
        if (par5 == 4) {
            --par2;
        }
        if (par5 == 5) {
            ++par2;
        }
        var11 = par1World.getBlockId(par2, par3, par4);
        var12 = par1World.getBlockMetadata(par2, par3, par4);
        var13 = (var12 & 0x7);
        var14 = ((var12 & 0x8) != 0x0);
        return (var11 == this.theHalfSlab.blockID && var13 == par7ItemStack.getItemDamage()) || super.canPlaceItemBlockOnSide(par1World, var8, var9, var10, par5, par6EntityPlayer, par7ItemStack);
    }
    
    private boolean func_77888_a(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, int par4, int par5, int par6, final int par7) {
        if (par7 == 0) {
            --par5;
        }
        if (par7 == 1) {
            ++par5;
        }
        if (par7 == 2) {
            --par6;
        }
        if (par7 == 3) {
            ++par6;
        }
        if (par7 == 4) {
            --par4;
        }
        if (par7 == 5) {
            ++par4;
        }
        final int var8 = par3World.getBlockId(par4, par5, par6);
        final int var9 = par3World.getBlockMetadata(par4, par5, par6);
        final int var10 = var9 & 0x7;
        if (var8 == this.theHalfSlab.blockID && var10 == par1ItemStack.getItemDamage()) {
            if (par3World.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlock(par4, par5, par6, this.doubleSlab.blockID, var10, 3)) {
                par3World.playSoundEffect(par4 + 0.5f, par5 + 0.5f, par6 + 0.5f, this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0f) / 2.0f, this.doubleSlab.stepSound.getPitch() * 0.8f);
                --par1ItemStack.stackSize;
            }
            return true;
        }
        return false;
    }
}
