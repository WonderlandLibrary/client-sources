package net.minecraft.src;

import java.util.*;

public class BlockSkull extends BlockContainer
{
    protected BlockSkull(final int par1) {
        super(par1, Material.circuits);
        this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
    }
    
    @Override
    public int getRenderType() {
        return -1;
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
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 0x7;
        switch (var5) {
            default: {
                this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
                break;
            }
            case 2: {
                this.setBlockBounds(0.25f, 0.25f, 0.5f, 0.75f, 0.75f, 1.0f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.25f, 0.25f, 0.0f, 0.75f, 0.75f, 0.5f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.5f, 0.25f, 0.25f, 1.0f, 0.75f, 0.75f);
                break;
            }
            case 5: {
                this.setBlockBounds(0.0f, 0.25f, 0.25f, 0.5f, 0.75f, 0.75f);
                break;
            }
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        final int var7 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0f / 360.0f + 2.5) & 0x3;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntitySkull();
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Item.skull.itemID;
    }
    
    @Override
    public int getDamageValue(final World par1World, final int par2, final int par3, final int par4) {
        final TileEntity var5 = par1World.getBlockTileEntity(par2, par3, par4);
        return (var5 != null && var5 instanceof TileEntitySkull) ? ((TileEntitySkull)var5).getSkullType() : super.getDamageValue(par1World, par2, par3, par4);
    }
    
    @Override
    public int damageDropped(final int par1) {
        return par1;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
    }
    
    @Override
    public void onBlockHarvested(final World par1World, final int par2, final int par3, final int par4, int par5, final EntityPlayer par6EntityPlayer) {
        if (par6EntityPlayer.capabilities.isCreativeMode) {
            par5 |= 0x8;
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par5, 4);
        }
        super.onBlockHarvested(par1World, par2, par3, par4, par5, par6EntityPlayer);
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        if (!par1World.isRemote) {
            if ((par6 & 0x8) == 0x0) {
                final ItemStack var7 = new ItemStack(Item.skull.itemID, 1, this.getDamageValue(par1World, par2, par3, par4));
                final TileEntitySkull var8 = (TileEntitySkull)par1World.getBlockTileEntity(par2, par3, par4);
                if (var8.getSkullType() == 3 && var8.getExtraType() != null && var8.getExtraType().length() > 0) {
                    var7.setTagCompound(new NBTTagCompound());
                    var7.getTagCompound().setString("SkullOwner", var8.getExtraType());
                }
                this.dropBlockAsItem_do(par1World, par2, par3, par4, var7);
            }
            super.breakBlock(par1World, par2, par3, par4, par5, par6);
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.skull.itemID;
    }
    
    public void makeWither(final World par1World, final int par2, final int par3, final int par4, final TileEntitySkull par5TileEntitySkull) {
        if (par5TileEntitySkull.getSkullType() == 1 && par3 >= 2 && par1World.difficultySetting > 0 && !par1World.isRemote) {
            final int var6 = Block.slowSand.blockID;
            for (int var7 = -2; var7 <= 0; ++var7) {
                if (par1World.getBlockId(par2, par3 - 1, par4 + var7) == var6 && par1World.getBlockId(par2, par3 - 1, par4 + var7 + 1) == var6 && par1World.getBlockId(par2, par3 - 2, par4 + var7 + 1) == var6 && par1World.getBlockId(par2, par3 - 1, par4 + var7 + 2) == var6 && this.func_82528_d(par1World, par2, par3, par4 + var7, 1) && this.func_82528_d(par1World, par2, par3, par4 + var7 + 1, 1) && this.func_82528_d(par1World, par2, par3, par4 + var7 + 2, 1)) {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4 + var7, 8, 2);
                    par1World.setBlockMetadataWithNotify(par2, par3, par4 + var7 + 1, 8, 2);
                    par1World.setBlockMetadataWithNotify(par2, par3, par4 + var7 + 2, 8, 2);
                    par1World.setBlock(par2, par3, par4 + var7, 0, 0, 2);
                    par1World.setBlock(par2, par3, par4 + var7 + 1, 0, 0, 2);
                    par1World.setBlock(par2, par3, par4 + var7 + 2, 0, 0, 2);
                    par1World.setBlock(par2, par3 - 1, par4 + var7, 0, 0, 2);
                    par1World.setBlock(par2, par3 - 1, par4 + var7 + 1, 0, 0, 2);
                    par1World.setBlock(par2, par3 - 1, par4 + var7 + 2, 0, 0, 2);
                    par1World.setBlock(par2, par3 - 2, par4 + var7 + 1, 0, 0, 2);
                    if (!par1World.isRemote) {
                        final EntityWither var8 = new EntityWither(par1World);
                        var8.setLocationAndAngles(par2 + 0.5, par3 - 1.45, par4 + var7 + 1.5, 90.0f, 0.0f);
                        var8.renderYawOffset = 90.0f;
                        var8.func_82206_m();
                        par1World.spawnEntityInWorld(var8);
                    }
                    for (int var9 = 0; var9 < 120; ++var9) {
                        par1World.spawnParticle("snowballpoof", par2 + par1World.rand.nextDouble(), par3 - 2 + par1World.rand.nextDouble() * 3.9, par4 + var7 + 1 + par1World.rand.nextDouble(), 0.0, 0.0, 0.0);
                    }
                    par1World.notifyBlockChange(par2, par3, par4 + var7, 0);
                    par1World.notifyBlockChange(par2, par3, par4 + var7 + 1, 0);
                    par1World.notifyBlockChange(par2, par3, par4 + var7 + 2, 0);
                    par1World.notifyBlockChange(par2, par3 - 1, par4 + var7, 0);
                    par1World.notifyBlockChange(par2, par3 - 1, par4 + var7 + 1, 0);
                    par1World.notifyBlockChange(par2, par3 - 1, par4 + var7 + 2, 0);
                    par1World.notifyBlockChange(par2, par3 - 2, par4 + var7 + 1, 0);
                    return;
                }
            }
            for (int var7 = -2; var7 <= 0; ++var7) {
                if (par1World.getBlockId(par2 + var7, par3 - 1, par4) == var6 && par1World.getBlockId(par2 + var7 + 1, par3 - 1, par4) == var6 && par1World.getBlockId(par2 + var7 + 1, par3 - 2, par4) == var6 && par1World.getBlockId(par2 + var7 + 2, par3 - 1, par4) == var6 && this.func_82528_d(par1World, par2 + var7, par3, par4, 1) && this.func_82528_d(par1World, par2 + var7 + 1, par3, par4, 1) && this.func_82528_d(par1World, par2 + var7 + 2, par3, par4, 1)) {
                    par1World.setBlockMetadataWithNotify(par2 + var7, par3, par4, 8, 2);
                    par1World.setBlockMetadataWithNotify(par2 + var7 + 1, par3, par4, 8, 2);
                    par1World.setBlockMetadataWithNotify(par2 + var7 + 2, par3, par4, 8, 2);
                    par1World.setBlock(par2 + var7, par3, par4, 0, 0, 2);
                    par1World.setBlock(par2 + var7 + 1, par3, par4, 0, 0, 2);
                    par1World.setBlock(par2 + var7 + 2, par3, par4, 0, 0, 2);
                    par1World.setBlock(par2 + var7, par3 - 1, par4, 0, 0, 2);
                    par1World.setBlock(par2 + var7 + 1, par3 - 1, par4, 0, 0, 2);
                    par1World.setBlock(par2 + var7 + 2, par3 - 1, par4, 0, 0, 2);
                    par1World.setBlock(par2 + var7 + 1, par3 - 2, par4, 0, 0, 2);
                    if (!par1World.isRemote) {
                        final EntityWither var8 = new EntityWither(par1World);
                        var8.setLocationAndAngles(par2 + var7 + 1.5, par3 - 1.45, par4 + 0.5, 0.0f, 0.0f);
                        var8.func_82206_m();
                        par1World.spawnEntityInWorld(var8);
                    }
                    for (int var9 = 0; var9 < 120; ++var9) {
                        par1World.spawnParticle("snowballpoof", par2 + var7 + 1 + par1World.rand.nextDouble(), par3 - 2 + par1World.rand.nextDouble() * 3.9, par4 + par1World.rand.nextDouble(), 0.0, 0.0, 0.0);
                    }
                    par1World.notifyBlockChange(par2 + var7, par3, par4, 0);
                    par1World.notifyBlockChange(par2 + var7 + 1, par3, par4, 0);
                    par1World.notifyBlockChange(par2 + var7 + 2, par3, par4, 0);
                    par1World.notifyBlockChange(par2 + var7, par3 - 1, par4, 0);
                    par1World.notifyBlockChange(par2 + var7 + 1, par3 - 1, par4, 0);
                    par1World.notifyBlockChange(par2 + var7 + 2, par3 - 1, par4, 0);
                    par1World.notifyBlockChange(par2 + var7 + 1, par3 - 2, par4, 0);
                    return;
                }
            }
        }
    }
    
    private boolean func_82528_d(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (par1World.getBlockId(par2, par3, par4) != this.blockID) {
            return false;
        }
        final TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);
        return var6 != null && var6 instanceof TileEntitySkull && ((TileEntitySkull)var6).getSkullType() == par5;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return Block.slowSand.getBlockTextureFromSide(par1);
    }
    
    @Override
    public String getItemIconName() {
        return ItemSkull.field_94587_a[0];
    }
}
