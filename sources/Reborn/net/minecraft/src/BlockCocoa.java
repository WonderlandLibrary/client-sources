package net.minecraft.src;

import java.util.*;

public class BlockCocoa extends BlockDirectional
{
    public static final String[] cocoaIcons;
    private Icon[] iconArray;
    
    static {
        cocoaIcons = new String[] { "cocoa_0", "cocoa_1", "cocoa_2" };
    }
    
    public BlockCocoa(final int par1) {
        super(par1, Material.plants);
        this.setTickRandomly(true);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return this.iconArray[2];
    }
    
    public Icon func_94468_i_(int par1) {
        if (par1 < 0 || par1 >= this.iconArray.length) {
            par1 = this.iconArray.length - 1;
        }
        return this.iconArray[par1];
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (!this.canBlockStay(par1World, par2, par3, par4)) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
        else if (par1World.rand.nextInt(5) == 0) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            int var7 = func_72219_c(var6);
            if (var7 < 2) {
                ++var7;
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 << 2 | BlockDirectional.getDirection(var6), 2);
            }
        }
    }
    
    @Override
    public boolean canBlockStay(final World par1World, int par2, final int par3, int par4) {
        final int var5 = BlockDirectional.getDirection(par1World.getBlockMetadata(par2, par3, par4));
        par2 += Direction.offsetX[var5];
        par4 += Direction.offsetZ[var5];
        final int var6 = par1World.getBlockId(par2, par3, par4);
        return var6 == Block.wood.blockID && BlockLog.limitToValidMetadata(par1World.getBlockMetadata(par2, par3, par4)) == 3;
    }
    
    @Override
    public int getRenderType() {
        return 28;
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
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        final int var6 = BlockDirectional.getDirection(var5);
        final int var7 = func_72219_c(var5);
        final int var8 = 4 + var7 * 2;
        final int var9 = 5 + var7 * 2;
        final float var10 = var8 / 2.0f;
        switch (var6) {
            case 0: {
                this.setBlockBounds((8.0f - var10) / 16.0f, (12.0f - var9) / 16.0f, (15.0f - var8) / 16.0f, (8.0f + var10) / 16.0f, 0.75f, 0.9375f);
                break;
            }
            case 1: {
                this.setBlockBounds(0.0625f, (12.0f - var9) / 16.0f, (8.0f - var10) / 16.0f, (1.0f + var8) / 16.0f, 0.75f, (8.0f + var10) / 16.0f);
                break;
            }
            case 2: {
                this.setBlockBounds((8.0f - var10) / 16.0f, (12.0f - var9) / 16.0f, 0.0625f, (8.0f + var10) / 16.0f, 0.75f, (1.0f + var8) / 16.0f);
                break;
            }
            case 3: {
                this.setBlockBounds((15.0f - var8) / 16.0f, (12.0f - var9) / 16.0f, (8.0f - var10) / 16.0f, 0.9375f, 0.75f, (8.0f + var10) / 16.0f);
                break;
            }
        }
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        final int var7 = ((MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) + 0) % 4;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
    }
    
    @Override
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, int par5, final float par6, final float par7, final float par8, final int par9) {
        if (par5 == 1 || par5 == 0) {
            par5 = 2;
        }
        return Direction.rotateOpposite[Direction.facingToDirection[par5]];
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!this.canBlockStay(par1World, par2, par3, par4)) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    public static int func_72219_c(final int par0) {
        return (par0 & 0xC) >> 2;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        final int var8 = func_72219_c(par5);
        byte var9 = 1;
        if (var8 >= 2) {
            var9 = 3;
        }
        for (int var10 = 0; var10 < var9; ++var10) {
            this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Item.dyePowder, 1, 3));
        }
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Item.dyePowder.itemID;
    }
    
    @Override
    public int getDamageValue(final World par1World, final int par2, final int par3, final int par4) {
        return 3;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.iconArray = new Icon[BlockCocoa.cocoaIcons.length];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon(BlockCocoa.cocoaIcons[var2]);
        }
    }
}
