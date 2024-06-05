package net.minecraft.src;

import java.util.*;

public class BlockLeaves extends BlockLeavesBase
{
    public static final String[] LEAF_TYPES;
    public static final String[][] field_94396_b;
    private int field_94394_cP;
    private Icon[][] iconArray;
    int[] adjacentTreeBlocks;
    
    static {
        LEAF_TYPES = new String[] { "oak", "spruce", "birch", "jungle" };
        field_94396_b = new String[][] { { "leaves", "leaves_spruce", "leaves", "leaves_jungle" }, { "leaves_opaque", "leaves_spruce_opaque", "leaves_opaque", "leaves_jungle_opaque" } };
    }
    
    protected BlockLeaves(final int par1) {
        super(par1, Material.leaves, false);
        this.iconArray = new Icon[2][];
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public int getBlockColor() {
        final double var1 = 0.5;
        final double var2 = 1.0;
        return ColorizerFoliage.getFoliageColor(var1, var2);
    }
    
    @Override
    public int getRenderColor(final int par1) {
        return ((par1 & 0x3) == 0x1) ? ColorizerFoliage.getFoliageColorPine() : (((par1 & 0x3) == 0x2) ? ColorizerFoliage.getFoliageColorBirch() : ColorizerFoliage.getFoliageColorBasic());
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        if ((var5 & 0x3) == 0x1) {
            return ColorizerFoliage.getFoliageColorPine();
        }
        if ((var5 & 0x3) == 0x2) {
            return ColorizerFoliage.getFoliageColorBirch();
        }
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        for (int var9 = -1; var9 <= 1; ++var9) {
            for (int var10 = -1; var10 <= 1; ++var10) {
                final int var11 = par1IBlockAccess.getBiomeGenForCoords(par2 + var10, par4 + var9).getBiomeFoliageColor();
                var6 += (var11 & 0xFF0000) >> 16;
                var7 += (var11 & 0xFF00) >> 8;
                var8 += (var11 & 0xFF);
            }
        }
        return (var6 / 9 & 0xFF) << 16 | (var7 / 9 & 0xFF) << 8 | (var8 / 9 & 0xFF);
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final byte var7 = 1;
        final int var8 = var7 + 1;
        if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8)) {
            for (int var9 = -var7; var9 <= var7; ++var9) {
                for (int var10 = -var7; var10 <= var7; ++var10) {
                    for (int var11 = -var7; var11 <= var7; ++var11) {
                        final int var12 = par1World.getBlockId(par2 + var9, par3 + var10, par4 + var11);
                        if (var12 == Block.leaves.blockID) {
                            final int var13 = par1World.getBlockMetadata(par2 + var9, par3 + var10, par4 + var11);
                            par1World.setBlockMetadataWithNotify(par2 + var9, par3 + var10, par4 + var11, var13 | 0x8, 4);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (!par1World.isRemote) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            if ((var6 & 0x8) != 0x0 && (var6 & 0x4) == 0x0) {
                final byte var7 = 4;
                final int var8 = var7 + 1;
                final byte var9 = 32;
                final int var10 = var9 * var9;
                final int var11 = var9 / 2;
                if (this.adjacentTreeBlocks == null) {
                    this.adjacentTreeBlocks = new int[var9 * var9 * var9];
                }
                if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8)) {
                    for (int var12 = -var7; var12 <= var7; ++var12) {
                        for (int var13 = -var7; var13 <= var7; ++var13) {
                            for (int var14 = -var7; var14 <= var7; ++var14) {
                                final int var15 = par1World.getBlockId(par2 + var12, par3 + var13, par4 + var14);
                                if (var15 == Block.wood.blockID) {
                                    this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
                                }
                                else if (var15 == Block.leaves.blockID) {
                                    this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -2;
                                }
                                else {
                                    this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -1;
                                }
                            }
                        }
                    }
                    for (int var12 = 1; var12 <= 4; ++var12) {
                        for (int var13 = -var7; var13 <= var7; ++var13) {
                            for (int var14 = -var7; var14 <= var7; ++var14) {
                                for (int var15 = -var7; var15 <= var7; ++var15) {
                                    if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11] == var12 - 1) {
                                        if (this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2) {
                                            this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                        }
                                        if (this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2) {
                                            this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                        }
                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] == -2) {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] = var12;
                                        }
                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] == -2) {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] = var12;
                                        }
                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] == -2) {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] = var12;
                                        }
                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] == -2) {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] = var12;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                int var12 = this.adjacentTreeBlocks[var11 * var10 + var11 * var9 + var11];
                if (var12 >= 0) {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 & 0xFFFFFFF7, 4);
                }
                else {
                    this.removeLeaves(par1World, par2, par3, par4);
                }
            }
        }
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (par1World.canLightningStrikeAt(par2, par3 + 1, par4) && !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && par5Random.nextInt(15) == 1) {
            final double var6 = par2 + par5Random.nextFloat();
            final double var7 = par3 - 0.05;
            final double var8 = par4 + par5Random.nextFloat();
            par1World.spawnParticle("dripWater", var6, var7, var8, 0.0, 0.0, 0.0);
        }
    }
    
    private void removeLeaves(final World par1World, final int par2, final int par3, final int par4) {
        this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
        par1World.setBlockToAir(par2, par3, par4);
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return (par1Random.nextInt(20) == 0) ? 1 : 0;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.sapling.blockID;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        if (!par1World.isRemote) {
            int var8 = 20;
            if ((par5 & 0x3) == 0x3) {
                var8 = 40;
            }
            if (par7 > 0) {
                var8 -= 2 << par7;
                if (var8 < 10) {
                    var8 = 10;
                }
            }
            if (par1World.rand.nextInt(var8) == 0) {
                final int var9 = this.idDropped(par5, par1World.rand, par7);
                this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(var9, 1, this.damageDropped(par5)));
            }
            var8 = 200;
            if (par7 > 0) {
                var8 -= 10 << par7;
                if (var8 < 40) {
                    var8 = 40;
                }
            }
            if ((par5 & 0x3) == 0x0 && par1World.rand.nextInt(var8) == 0) {
                this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Item.appleRed, 1, 0));
            }
        }
    }
    
    @Override
    public void harvestBlock(final World par1World, final EntityPlayer par2EntityPlayer, final int par3, final int par4, final int par5, final int par6) {
        if (!par1World.isRemote && par2EntityPlayer.getCurrentEquippedItem() != null && par2EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.itemID) {
            par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
            this.dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(Block.leaves.blockID, 1, par6 & 0x3));
        }
        else {
            super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
        }
    }
    
    @Override
    public int damageDropped(final int par1) {
        return par1 & 0x3;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return !this.graphicsLevel;
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return ((par2 & 0x3) == 0x1) ? this.iconArray[this.field_94394_cP][1] : (((par2 & 0x3) == 0x3) ? this.iconArray[this.field_94394_cP][3] : this.iconArray[this.field_94394_cP][0]);
    }
    
    public void setGraphicsLevel(final boolean par1) {
        this.graphicsLevel = par1;
        this.field_94394_cP = (par1 ? 0 : 1);
    }
    
    @Override
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }
    
    @Override
    protected ItemStack createStackedBlock(final int par1) {
        return new ItemStack(this.blockID, 1, par1 & 0x3);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        for (int var2 = 0; var2 < BlockLeaves.field_94396_b.length; ++var2) {
            this.iconArray[var2] = new Icon[BlockLeaves.field_94396_b[var2].length];
            for (int var3 = 0; var3 < BlockLeaves.field_94396_b[var2].length; ++var3) {
                this.iconArray[var2][var3] = par1IconRegister.registerIcon(BlockLeaves.field_94396_b[var2][var3]);
            }
        }
    }
}
