package net.minecraft.src;

import java.util.*;

public class BlockPane extends Block
{
    private final String sideTextureIndex;
    private final boolean canDropItself;
    private final String field_94402_c;
    private Icon theIcon;
    
    protected BlockPane(final int par1, final String par2Str, final String par3Str, final Material par4Material, final boolean par5) {
        super(par1, par4Material);
        this.sideTextureIndex = par3Str;
        this.canDropItself = par5;
        this.field_94402_c = par2Str;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return this.canDropItself ? super.idDropped(par1, par2Random, par3) : 0;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 18;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
        return var6 != this.blockID && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
    
    @Override
    public void addCollisionBoxesToList(final World par1World, final int par2, final int par3, final int par4, final AxisAlignedBB par5AxisAlignedBB, final List par6List, final Entity par7Entity) {
        final boolean var8 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 - 1));
        final boolean var9 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 + 1));
        final boolean var10 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 - 1, par3, par4));
        final boolean var11 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 + 1, par3, par4));
        if ((!var10 || !var11) && (var10 || var11 || var8 || var9)) {
            if (var10 && !var11) {
                this.setBlockBounds(0.0f, 0.0f, 0.4375f, 0.5f, 1.0f, 0.5625f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
            }
            else if (!var10 && var11) {
                this.setBlockBounds(0.5f, 0.0f, 0.4375f, 1.0f, 1.0f, 0.5625f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.4375f, 1.0f, 1.0f, 0.5625f);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        if ((!var8 || !var9) && (var10 || var11 || var8 || var9)) {
            if (var8 && !var9) {
                this.setBlockBounds(0.4375f, 0.0f, 0.0f, 0.5625f, 1.0f, 0.5f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
            }
            else if (!var8 && var9) {
                this.setBlockBounds(0.4375f, 0.0f, 0.5f, 0.5625f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
            }
        }
        else {
            this.setBlockBounds(0.4375f, 0.0f, 0.0f, 0.5625f, 1.0f, 1.0f);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        float var5 = 0.4375f;
        float var6 = 0.5625f;
        float var7 = 0.4375f;
        float var8 = 0.5625f;
        final boolean var9 = this.canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2, par3, par4 - 1));
        final boolean var10 = this.canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2, par3, par4 + 1));
        final boolean var11 = this.canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2 - 1, par3, par4));
        final boolean var12 = this.canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2 + 1, par3, par4));
        if ((!var11 || !var12) && (var11 || var12 || var9 || var10)) {
            if (var11 && !var12) {
                var5 = 0.0f;
            }
            else if (!var11 && var12) {
                var6 = 1.0f;
            }
        }
        else {
            var5 = 0.0f;
            var6 = 1.0f;
        }
        if ((!var9 || !var10) && (var11 || var12 || var9 || var10)) {
            if (var9 && !var10) {
                var7 = 0.0f;
            }
            else if (!var9 && var10) {
                var8 = 1.0f;
            }
        }
        else {
            var7 = 0.0f;
            var8 = 1.0f;
        }
        this.setBlockBounds(var5, 0.0f, var7, var6, 1.0f, var8);
    }
    
    public Icon getSideTextureIndex() {
        return this.theIcon;
    }
    
    public final boolean canThisPaneConnectToThisBlockID(final int par1) {
        return Block.opaqueCubeLookup[par1] || par1 == this.blockID || par1 == Block.glass.blockID;
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @Override
    protected ItemStack createStackedBlock(final int par1) {
        return new ItemStack(this.blockID, 1, par1);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(this.field_94402_c);
        this.theIcon = par1IconRegister.registerIcon(this.sideTextureIndex);
    }
}
