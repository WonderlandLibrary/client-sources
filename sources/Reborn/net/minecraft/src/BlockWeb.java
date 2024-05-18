package net.minecraft.src;

import java.util.*;

public class BlockWeb extends Block
{
    public BlockWeb(final int par1) {
        super(par1, Material.web);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
        par5Entity.setInWeb();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return null;
    }
    
    @Override
    public int getRenderType() {
        return 1;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.silk.itemID;
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
}
