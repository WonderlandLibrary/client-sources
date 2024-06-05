package net.minecraft.src;

import java.util.*;

public class BlockTNT extends Block
{
    private Icon field_94393_a;
    private Icon field_94392_b;
    
    public BlockTNT(final int par1) {
        super(par1, Material.tnt);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 0) ? this.field_94392_b : ((par1 == 1) ? this.field_94393_a : this.blockIcon);
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
            this.onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
            this.onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 1;
    }
    
    @Override
    public void onBlockDestroyedByExplosion(final World par1World, final int par2, final int par3, final int par4, final Explosion par5Explosion) {
        if (!par1World.isRemote) {
            final EntityTNTPrimed var6 = new EntityTNTPrimed(par1World, par2 + 0.5f, par3 + 0.5f, par4 + 0.5f, par5Explosion.func_94613_c());
            var6.fuse = par1World.rand.nextInt(var6.fuse / 4) + var6.fuse / 8;
            par1World.spawnEntityInWorld(var6);
        }
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        this.func_94391_a(par1World, par2, par3, par4, par5, null);
    }
    
    public void func_94391_a(final World par1World, final int par2, final int par3, final int par4, final int par5, final EntityLiving par6EntityLiving) {
        if (!par1World.isRemote && (par5 & 0x1) == 0x1) {
            final EntityTNTPrimed var7 = new EntityTNTPrimed(par1World, par2 + 0.5f, par3 + 0.5f, par4 + 0.5f, par6EntityLiving);
            par1World.spawnEntityInWorld(var7);
            par1World.playSoundAtEntity(var7, "random.fuse", 1.0f, 1.0f);
        }
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par5EntityPlayer.getCurrentEquippedItem() != null && par5EntityPlayer.getCurrentEquippedItem().itemID == Item.flintAndSteel.itemID) {
            this.func_94391_a(par1World, par2, par3, par4, 1, par5EntityPlayer);
            par1World.setBlockToAir(par2, par3, par4);
            return true;
        }
        return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
        if (par5Entity instanceof EntityArrow && !par1World.isRemote) {
            final EntityArrow var6 = (EntityArrow)par5Entity;
            if (var6.isBurning()) {
                this.func_94391_a(par1World, par2, par3, par4, 1, (var6.shootingEntity instanceof EntityLiving) ? ((EntityLiving)var6.shootingEntity) : null);
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
    }
    
    @Override
    public boolean canDropFromExplosion(final Explosion par1Explosion) {
        return false;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("tnt_side");
        this.field_94393_a = par1IconRegister.registerIcon("tnt_top");
        this.field_94392_b = par1IconRegister.registerIcon("tnt_bottom");
    }
}
