package net.minecraft.src;

import java.util.*;

public class BlockBed extends BlockDirectional
{
    public static final int[][] footBlockToHeadBlockMap;
    private Icon[] field_94472_b;
    private Icon[] bedSideIcons;
    private Icon[] bedTopIcons;
    
    static {
        footBlockToHeadBlockMap = new int[][] { { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 0 } };
    }
    
    public BlockBed(final int par1) {
        super(par1, Material.cloth);
        this.setBounds();
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, int par2, final int par3, int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        int var10 = par1World.getBlockMetadata(par2, par3, par4);
        if (!isBlockHeadOfBed(var10)) {
            final int var11 = BlockDirectional.getDirection(var10);
            par2 += BlockBed.footBlockToHeadBlockMap[var11][0];
            par4 += BlockBed.footBlockToHeadBlockMap[var11][1];
            if (par1World.getBlockId(par2, par3, par4) != this.blockID) {
                return true;
            }
            var10 = par1World.getBlockMetadata(par2, par3, par4);
        }
        if (!par1World.provider.canRespawnHere() || par1World.getBiomeGenForCoords(par2, par4) == BiomeGenBase.hell) {
            double var12 = par2 + 0.5;
            double var13 = par3 + 0.5;
            double var14 = par4 + 0.5;
            par1World.setBlockToAir(par2, par3, par4);
            final int var15 = BlockDirectional.getDirection(var10);
            par2 += BlockBed.footBlockToHeadBlockMap[var15][0];
            par4 += BlockBed.footBlockToHeadBlockMap[var15][1];
            if (par1World.getBlockId(par2, par3, par4) == this.blockID) {
                par1World.setBlockToAir(par2, par3, par4);
                var12 = (var12 + par2 + 0.5) / 2.0;
                var13 = (var13 + par3 + 0.5) / 2.0;
                var14 = (var14 + par4 + 0.5) / 2.0;
            }
            par1World.newExplosion(null, par2 + 0.5f, par3 + 0.5f, par4 + 0.5f, 5.0f, true, true);
            return true;
        }
        if (isBedOccupied(var10)) {
            EntityPlayer var16 = null;
            for (final EntityPlayer var18 : par1World.playerEntities) {
                if (var18.isPlayerSleeping()) {
                    final ChunkCoordinates var19 = var18.playerLocation;
                    if (var19.posX != par2 || var19.posY != par3 || var19.posZ != par4) {
                        continue;
                    }
                    var16 = var18;
                }
            }
            if (var16 != null) {
                par5EntityPlayer.addChatMessage("tile.bed.occupied");
                return true;
            }
            setBedOccupied(par1World, par2, par3, par4, false);
        }
        final EnumStatus var20 = par5EntityPlayer.sleepInBedAt(par2, par3, par4);
        if (var20 == EnumStatus.OK) {
            setBedOccupied(par1World, par2, par3, par4, true);
            return true;
        }
        if (var20 == EnumStatus.NOT_POSSIBLE_NOW) {
            par5EntityPlayer.addChatMessage("tile.bed.noSleep");
        }
        else if (var20 == EnumStatus.NOT_SAFE) {
            par5EntityPlayer.addChatMessage("tile.bed.notSafe");
        }
        return true;
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        if (par1 == 0) {
            return Block.planks.getBlockTextureFromSide(par1);
        }
        final int var3 = BlockDirectional.getDirection(par2);
        final int var4 = Direction.bedDirection[var3][par1];
        final int var5 = isBlockHeadOfBed(par2) ? 1 : 0;
        return ((var5 != 1 || var4 != 2) && (var5 != 0 || var4 != 3)) ? ((var4 != 5 && var4 != 4) ? this.bedTopIcons[var5] : this.bedSideIcons[var5]) : this.field_94472_b[var5];
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.bedTopIcons = new Icon[] { par1IconRegister.registerIcon("bed_feet_top"), par1IconRegister.registerIcon("bed_head_top") };
        this.field_94472_b = new Icon[] { par1IconRegister.registerIcon("bed_feet_end"), par1IconRegister.registerIcon("bed_head_end") };
        this.bedSideIcons = new Icon[] { par1IconRegister.registerIcon("bed_feet_side"), par1IconRegister.registerIcon("bed_head_side") };
    }
    
    @Override
    public int getRenderType() {
        return 14;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        this.setBounds();
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = par1World.getBlockMetadata(par2, par3, par4);
        final int var7 = BlockDirectional.getDirection(var6);
        if (isBlockHeadOfBed(var6)) {
            if (par1World.getBlockId(par2 - BlockBed.footBlockToHeadBlockMap[var7][0], par3, par4 - BlockBed.footBlockToHeadBlockMap[var7][1]) != this.blockID) {
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
        else if (par1World.getBlockId(par2 + BlockBed.footBlockToHeadBlockMap[var7][0], par3, par4 + BlockBed.footBlockToHeadBlockMap[var7][1]) != this.blockID) {
            par1World.setBlockToAir(par2, par3, par4);
            if (!par1World.isRemote) {
                this.dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
            }
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return isBlockHeadOfBed(par1) ? 0 : Item.bed.itemID;
    }
    
    private void setBounds() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5625f, 1.0f);
    }
    
    public static boolean isBlockHeadOfBed(final int par0) {
        return (par0 & 0x8) != 0x0;
    }
    
    public static boolean isBedOccupied(final int par0) {
        return (par0 & 0x4) != 0x0;
    }
    
    public static void setBedOccupied(final World par0World, final int par1, final int par2, final int par3, final boolean par4) {
        int var5 = par0World.getBlockMetadata(par1, par2, par3);
        if (par4) {
            var5 |= 0x4;
        }
        else {
            var5 &= 0xFFFFFFFB;
        }
        par0World.setBlockMetadataWithNotify(par1, par2, par3, var5, 4);
    }
    
    public static ChunkCoordinates getNearestEmptyChunkCoordinates(final World par0World, final int par1, final int par2, final int par3, int par4) {
        final int var5 = par0World.getBlockMetadata(par1, par2, par3);
        final int var6 = BlockDirectional.getDirection(var5);
        for (int var7 = 0; var7 <= 1; ++var7) {
            final int var8 = par1 - BlockBed.footBlockToHeadBlockMap[var6][0] * var7 - 1;
            final int var9 = par3 - BlockBed.footBlockToHeadBlockMap[var6][1] * var7 - 1;
            final int var10 = var8 + 2;
            final int var11 = var9 + 2;
            for (int var12 = var8; var12 <= var10; ++var12) {
                for (int var13 = var9; var13 <= var11; ++var13) {
                    if (par0World.doesBlockHaveSolidTopSurface(var12, par2 - 1, var13) && par0World.isAirBlock(var12, par2, var13) && par0World.isAirBlock(var12, par2 + 1, var13)) {
                        if (par4 <= 0) {
                            return new ChunkCoordinates(var12, par2, var13);
                        }
                        --par4;
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        if (!isBlockHeadOfBed(par5)) {
            super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
        }
    }
    
    @Override
    public int getMobilityFlag() {
        return 1;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Item.bed.itemID;
    }
    
    @Override
    public void onBlockHarvested(final World par1World, int par2, final int par3, int par4, final int par5, final EntityPlayer par6EntityPlayer) {
        if (par6EntityPlayer.capabilities.isCreativeMode && isBlockHeadOfBed(par5)) {
            final int var7 = BlockDirectional.getDirection(par5);
            par2 -= BlockBed.footBlockToHeadBlockMap[var7][0];
            par4 -= BlockBed.footBlockToHeadBlockMap[var7][1];
            if (par1World.getBlockId(par2, par3, par4) == this.blockID) {
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
    }
}
