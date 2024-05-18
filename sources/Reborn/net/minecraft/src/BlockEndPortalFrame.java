package net.minecraft.src;

import java.util.*;

public class BlockEndPortalFrame extends Block
{
    private Icon field_94400_a;
    private Icon field_94399_b;
    
    public BlockEndPortalFrame(final int par1) {
        super(par1, Material.rock);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 1) ? this.field_94400_a : ((par1 == 0) ? Block.whiteStone.getBlockTextureFromSide(par1) : this.blockIcon);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("endframe_side");
        this.field_94400_a = par1IconRegister.registerIcon("endframe_top");
        this.field_94399_b = par1IconRegister.registerIcon("endframe_eye");
    }
    
    public Icon func_94398_p() {
        return this.field_94399_b;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 26;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
    }
    
    @Override
    public void addCollisionBoxesToList(final World par1World, final int par2, final int par3, final int par4, final AxisAlignedBB par5AxisAlignedBB, final List par6List, final Entity par7Entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        final int var8 = par1World.getBlockMetadata(par2, par3, par4);
        if (isEnderEyeInserted(var8)) {
            this.setBlockBounds(0.3125f, 0.8125f, 0.3125f, 0.6875f, 1.0f, 0.6875f);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        this.setBlockBoundsForItemRender();
    }
    
    public static boolean isEnderEyeInserted(final int par0) {
        return (par0 & 0x4) != 0x0;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return 0;
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        final int var7 = ((MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) + 2) % 4;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
    }
}
