package net.minecraft.src;

public class ItemDoor extends Item
{
    private Material doorMaterial;
    
    public ItemDoor(final int par1, final Material par2Material) {
        super(par1);
        this.doorMaterial = par2Material;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        if (par7 != 1) {
            return false;
        }
        ++par5;
        Block var11;
        if (this.doorMaterial == Material.wood) {
            var11 = Block.doorWood;
        }
        else {
            var11 = Block.doorIron;
        }
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) || !par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack)) {
            return false;
        }
        if (!var11.canPlaceBlockAt(par3World, par4, par5, par6)) {
            return false;
        }
        final int var12 = MathHelper.floor_double((par2EntityPlayer.rotationYaw + 180.0f) * 4.0f / 360.0f - 0.5) & 0x3;
        placeDoorBlock(par3World, par4, par5, par6, var12, var11);
        --par1ItemStack.stackSize;
        return true;
    }
    
    public static void placeDoorBlock(final World par0World, final int par1, final int par2, final int par3, final int par4, final Block par5Block) {
        byte var6 = 0;
        byte var7 = 0;
        if (par4 == 0) {
            var7 = 1;
        }
        if (par4 == 1) {
            var6 = -1;
        }
        if (par4 == 2) {
            var7 = -1;
        }
        if (par4 == 3) {
            var6 = 1;
        }
        final int var8 = (par0World.isBlockNormalCube(par1 - var6, par2, par3 - var7) + par0World.isBlockNormalCube(par1 - var6, par2 + 1, par3 - var7)) ? 1 : 0;
        final int var9 = (par0World.isBlockNormalCube(par1 + var6, par2, par3 + var7) + par0World.isBlockNormalCube(par1 + var6, par2 + 1, par3 + var7)) ? 1 : 0;
        final boolean var10 = par0World.getBlockId(par1 - var6, par2, par3 - var7) == par5Block.blockID || par0World.getBlockId(par1 - var6, par2 + 1, par3 - var7) == par5Block.blockID;
        final boolean var11 = par0World.getBlockId(par1 + var6, par2, par3 + var7) == par5Block.blockID || par0World.getBlockId(par1 + var6, par2 + 1, par3 + var7) == par5Block.blockID;
        boolean var12 = false;
        if (var10 && !var11) {
            var12 = true;
        }
        else if (var9 > var8) {
            var12 = true;
        }
        par0World.setBlock(par1, par2, par3, par5Block.blockID, par4, 2);
        par0World.setBlock(par1, par2 + 1, par3, par5Block.blockID, 0x8 | (var12 ? 1 : 0), 2);
        par0World.notifyBlocksOfNeighborChange(par1, par2, par3, par5Block.blockID);
        par0World.notifyBlocksOfNeighborChange(par1, par2 + 1, par3, par5Block.blockID);
    }
}
