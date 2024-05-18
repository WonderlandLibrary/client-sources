package net.minecraft.src;

import java.util.*;

public class BlockComparator extends BlockRedstoneLogic implements ITileEntityProvider
{
    public BlockComparator(final int par1, final boolean par2) {
        super(par1, par2);
        this.isBlockContainer = true;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.comparator.itemID;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Item.comparator.itemID;
    }
    
    @Override
    protected int func_94481_j_(final int par1) {
        return 2;
    }
    
    @Override
    protected BlockRedstoneLogic func_94485_e() {
        return Block.redstoneComparatorActive;
    }
    
    @Override
    protected BlockRedstoneLogic func_94484_i() {
        return Block.redstoneComparatorIdle;
    }
    
    @Override
    public int getRenderType() {
        return 37;
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        final boolean var3 = this.isRepeaterPowered || (par2 & 0x8) != 0x0;
        return (par1 == 0) ? (var3 ? Block.torchRedstoneActive.getBlockTextureFromSide(par1) : Block.torchRedstoneIdle.getBlockTextureFromSide(par1)) : ((par1 == 1) ? (var3 ? Block.redstoneComparatorActive.blockIcon : this.blockIcon) : Block.stoneDoubleSlab.getBlockTextureFromSide(1));
    }
    
    @Override
    protected boolean func_96470_c(final int par1) {
        return this.isRepeaterPowered || (par1 & 0x8) != 0x0;
    }
    
    @Override
    protected int func_94480_d(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return this.getTileEntityComparator(par1IBlockAccess, par2, par3, par4).func_96100_a();
    }
    
    private int func_94491_m(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return this.func_94490_c(par5) ? Math.max(this.getInputStrength(par1World, par2, par3, par4, par5) - this.func_94482_f(par1World, par2, par3, par4, par5), 0) : this.getInputStrength(par1World, par2, par3, par4, par5);
    }
    
    public boolean func_94490_c(final int par1) {
        return (par1 & 0x4) == 0x4;
    }
    
    @Override
    protected boolean func_94478_d(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = this.getInputStrength(par1World, par2, par3, par4, par5);
        if (var6 >= 15) {
            return true;
        }
        if (var6 == 0) {
            return false;
        }
        final int var7 = this.func_94482_f(par1World, par2, par3, par4, par5);
        return var7 == 0 || var6 >= var7;
    }
    
    @Override
    protected int getInputStrength(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        int var6 = super.getInputStrength(par1World, par2, par3, par4, par5);
        final int var7 = BlockDirectional.getDirection(par5);
        int var8 = par2 + Direction.offsetX[var7];
        int var9 = par4 + Direction.offsetZ[var7];
        int var10 = par1World.getBlockId(var8, par3, var9);
        if (var10 > 0) {
            if (Block.blocksList[var10].hasComparatorInputOverride()) {
                var6 = Block.blocksList[var10].getComparatorInputOverride(par1World, var8, par3, var9, Direction.rotateOpposite[var7]);
            }
            else if (var6 < 15 && Block.isNormalCube(var10)) {
                var8 += Direction.offsetX[var7];
                var9 += Direction.offsetZ[var7];
                var10 = par1World.getBlockId(var8, par3, var9);
                if (var10 > 0 && Block.blocksList[var10].hasComparatorInputOverride()) {
                    var6 = Block.blocksList[var10].getComparatorInputOverride(par1World, var8, par3, var9, Direction.rotateOpposite[var7]);
                }
            }
        }
        return var6;
    }
    
    public TileEntityComparator getTileEntityComparator(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return (TileEntityComparator)par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        final int var10 = par1World.getBlockMetadata(par2, par3, par4);
        final boolean var11 = this.isRepeaterPowered | (var10 & 0x8) != 0x0;
        final boolean var12 = !this.func_94490_c(var10);
        int var13 = var12 ? 4 : 0;
        var13 |= (var11 ? 8 : 0);
        par1World.playSoundEffect(par2 + 0.5, par3 + 0.5, par4 + 0.5, "random.click", 0.3f, var12 ? 0.55f : 0.5f);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var13 | (var10 & 0x3), 2);
        this.func_96476_c(par1World, par2, par3, par4, par1World.rand);
        return true;
    }
    
    @Override
    protected void func_94479_f(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!par1World.isBlockTickScheduled(par2, par3, par4, this.blockID)) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            final int var7 = this.func_94491_m(par1World, par2, par3, par4, var6);
            final int var8 = this.getTileEntityComparator(par1World, par2, par3, par4).func_96100_a();
            if (var7 != var8 || this.func_96470_c(var6) != this.func_94478_d(par1World, par2, par3, par4, var6)) {
                if (this.func_83011_d(par1World, par2, par3, par4, var6)) {
                    par1World.func_82740_a(par2, par3, par4, this.blockID, this.func_94481_j_(0), -1);
                }
                else {
                    par1World.func_82740_a(par2, par3, par4, this.blockID, this.func_94481_j_(0), 0);
                }
            }
        }
    }
    
    private void func_96476_c(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        final int var6 = par1World.getBlockMetadata(par2, par3, par4);
        final int var7 = this.func_94491_m(par1World, par2, par3, par4, var6);
        final int var8 = this.getTileEntityComparator(par1World, par2, par3, par4).func_96100_a();
        this.getTileEntityComparator(par1World, par2, par3, par4).func_96099_a(var7);
        if (var8 != var7 || !this.func_94490_c(var6)) {
            final boolean var9 = this.func_94478_d(par1World, par2, par3, par4, var6);
            final boolean var10 = this.isRepeaterPowered || (var6 & 0x8) != 0x0;
            if (var10 && !var9) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 & 0xFFFFFFF7, 2);
            }
            else if (!var10 && var9) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | 0x8, 2);
            }
            this.func_94483_i_(par1World, par2, par3, par4);
        }
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (this.isRepeaterPowered) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            par1World.setBlock(par2, par3, par4, this.func_94484_i().blockID, var6 | 0x8, 4);
        }
        this.func_96476_c(par1World, par2, par3, par4, par5Random);
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        par1World.setBlockTileEntity(par2, par3, par4, this.createNewTileEntity(par1World));
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeBlockTileEntity(par2, par3, par4);
        this.func_94483_i_(par1World, par2, par3, par4);
    }
    
    @Override
    public boolean onBlockEventReceived(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        final TileEntity var7 = par1World.getBlockTileEntity(par2, par3, par4);
        return var7 != null && var7.receiveClientEvent(par5, par6);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(this.isRepeaterPowered ? "comparator_lit" : "comparator");
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityComparator();
    }
}
