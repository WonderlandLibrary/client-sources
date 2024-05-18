package net.minecraft.src;

public class BlockRailPowered extends BlockRailBase
{
    protected Icon theIcon;
    
    protected BlockRailPowered(final int par1) {
        super(par1, true);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return ((par2 & 0x8) == 0x0) ? this.blockIcon : this.theIcon;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        this.theIcon = par1IconRegister.registerIcon(String.valueOf(this.getUnlocalizedName2()) + "_powered");
    }
    
    protected boolean func_94360_a(final World par1World, int par2, int par3, int par4, final int par5, final boolean par6, final int par7) {
        if (par7 >= 8) {
            return false;
        }
        int var8 = par5 & 0x7;
        boolean var9 = true;
        switch (var8) {
            case 0: {
                if (par6) {
                    ++par4;
                    break;
                }
                --par4;
                break;
            }
            case 1: {
                if (par6) {
                    --par2;
                    break;
                }
                ++par2;
                break;
            }
            case 2: {
                if (par6) {
                    --par2;
                }
                else {
                    ++par2;
                    ++par3;
                    var9 = false;
                }
                var8 = 1;
                break;
            }
            case 3: {
                if (par6) {
                    --par2;
                    ++par3;
                    var9 = false;
                }
                else {
                    ++par2;
                }
                var8 = 1;
                break;
            }
            case 4: {
                if (par6) {
                    ++par4;
                }
                else {
                    --par4;
                    ++par3;
                    var9 = false;
                }
                var8 = 0;
                break;
            }
            case 5: {
                if (par6) {
                    ++par4;
                    ++par3;
                    var9 = false;
                }
                else {
                    --par4;
                }
                var8 = 0;
                break;
            }
        }
        return this.func_94361_a(par1World, par2, par3, par4, par6, par7, var8) || (var9 && this.func_94361_a(par1World, par2, par3 - 1, par4, par6, par7, var8));
    }
    
    protected boolean func_94361_a(final World par1World, final int par2, final int par3, final int par4, final boolean par5, final int par6, final int par7) {
        final int var8 = par1World.getBlockId(par2, par3, par4);
        if (var8 == this.blockID) {
            final int var9 = par1World.getBlockMetadata(par2, par3, par4);
            final int var10 = var9 & 0x7;
            if (par7 == 1 && (var10 == 0 || var10 == 4 || var10 == 5)) {
                return false;
            }
            if (par7 == 0 && (var10 == 1 || var10 == 2 || var10 == 3)) {
                return false;
            }
            if ((var9 & 0x8) != 0x0) {
                return par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || this.func_94360_a(par1World, par2, par3, par4, var9, par5, par6 + 1);
            }
        }
        return false;
    }
    
    @Override
    protected void func_94358_a(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7) {
        boolean var8 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
        var8 = (var8 || this.func_94360_a(par1World, par2, par3, par4, par5, true, 0) || this.func_94360_a(par1World, par2, par3, par4, par5, false, 0));
        boolean var9 = false;
        if (var8 && (par5 & 0x8) == 0x0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par6 | 0x8, 3);
            var9 = true;
        }
        else if (!var8 && (par5 & 0x8) != 0x0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par6, 3);
            var9 = true;
        }
        if (var9) {
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
            if (par6 == 2 || par6 == 3 || par6 == 4 || par6 == 5) {
                par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
            }
        }
    }
}
