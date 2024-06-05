package net.minecraft.src;

import java.util.*;

public class BlockCauldron extends Block
{
    private Icon field_94378_a;
    private Icon cauldronTopIcon;
    private Icon cauldronBottomIcon;
    
    public BlockCauldron(final int par1) {
        super(par1, Material.iron);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 1) ? this.cauldronTopIcon : ((par1 == 0) ? this.cauldronBottomIcon : this.blockIcon);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.field_94378_a = par1IconRegister.registerIcon("cauldron_inner");
        this.cauldronTopIcon = par1IconRegister.registerIcon("cauldron_top");
        this.cauldronBottomIcon = par1IconRegister.registerIcon("cauldron_bottom");
        this.blockIcon = par1IconRegister.registerIcon("cauldron_side");
    }
    
    public static Icon func_94375_b(final String par0Str) {
        return (par0Str == "cauldron_inner") ? Block.cauldron.field_94378_a : ((par0Str == "cauldron_bottom") ? Block.cauldron.cauldronBottomIcon : null);
    }
    
    @Override
    public void addCollisionBoxesToList(final World par1World, final int par2, final int par3, final int par4, final AxisAlignedBB par5AxisAlignedBB, final List par6List, final Entity par7Entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.3125f, 1.0f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        final float var8 = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, var8, 1.0f, 1.0f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var8);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(1.0f - var8, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0f, 0.0f, 1.0f - var8, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBoundsForItemRender();
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 24;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        final ItemStack var10 = par5EntityPlayer.inventory.getCurrentItem();
        if (var10 == null) {
            return true;
        }
        final int var11 = par1World.getBlockMetadata(par2, par3, par4);
        if (var10.itemID == Item.bucketWater.itemID) {
            if (var11 < 3) {
                if (!par5EntityPlayer.capabilities.isCreativeMode) {
                    par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(Item.bucketEmpty));
                }
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
            }
            return true;
        }
        if (var10.itemID == Item.glassBottle.itemID) {
            if (var11 > 0) {
                final ItemStack var12 = new ItemStack(Item.potion, 1, 0);
                if (!par5EntityPlayer.inventory.addItemStackToInventory(var12)) {
                    par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + 0.5, par3 + 1.5, par4 + 0.5, var12));
                }
                else if (par5EntityPlayer instanceof EntityPlayerMP) {
                    ((EntityPlayerMP)par5EntityPlayer).sendContainerToPlayer(par5EntityPlayer.inventoryContainer);
                }
                final ItemStack itemStack = var10;
                --itemStack.stackSize;
                if (var10.stackSize <= 0) {
                    par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, null);
                }
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var11 - 1, 2);
            }
        }
        else if (var11 > 0 && var10.getItem() instanceof ItemArmor && ((ItemArmor)var10.getItem()).getArmorMaterial() == EnumArmorMaterial.CLOTH) {
            final ItemArmor var13 = (ItemArmor)var10.getItem();
            var13.removeColor(var10);
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var11 - 1, 2);
            return true;
        }
        return true;
    }
    
    @Override
    public void fillWithRain(final World par1World, final int par2, final int par3, final int par4) {
        if (par1World.rand.nextInt(20) == 1) {
            final int var5 = par1World.getBlockMetadata(par2, par3, par4);
            if (var5 < 3) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var5 + 1, 2);
            }
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.cauldron.itemID;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Item.cauldron.itemID;
    }
}
