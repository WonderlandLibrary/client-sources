package net.minecraft.src;

public class BlockPumpkin extends BlockDirectional
{
    private boolean blockType;
    private Icon field_94474_b;
    private Icon field_94475_c;
    
    protected BlockPumpkin(final int par1, final boolean par2) {
        super(par1, Material.pumpkin);
        this.setTickRandomly(true);
        this.blockType = par2;
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 1) ? this.field_94474_b : ((par1 == 0) ? this.field_94474_b : ((par2 == 2 && par1 == 2) ? this.field_94475_c : ((par2 == 3 && par1 == 5) ? this.field_94475_c : ((par2 == 0 && par1 == 3) ? this.field_94475_c : ((par2 == 1 && par1 == 4) ? this.field_94475_c : this.blockIcon)))));
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        if (par1World.getBlockId(par2, par3 - 1, par4) == Block.blockSnow.blockID && par1World.getBlockId(par2, par3 - 2, par4) == Block.blockSnow.blockID) {
            if (!par1World.isRemote) {
                par1World.setBlock(par2, par3, par4, 0, 0, 2);
                par1World.setBlock(par2, par3 - 1, par4, 0, 0, 2);
                par1World.setBlock(par2, par3 - 2, par4, 0, 0, 2);
                final EntitySnowman var9 = new EntitySnowman(par1World);
                var9.setLocationAndAngles(par2 + 0.5, par3 - 1.95, par4 + 0.5, 0.0f, 0.0f);
                par1World.spawnEntityInWorld(var9);
                par1World.notifyBlockChange(par2, par3, par4, 0);
                par1World.notifyBlockChange(par2, par3 - 1, par4, 0);
                par1World.notifyBlockChange(par2, par3 - 2, par4, 0);
            }
            for (int var10 = 0; var10 < 120; ++var10) {
                par1World.spawnParticle("snowshovel", par2 + par1World.rand.nextDouble(), par3 - 2 + par1World.rand.nextDouble() * 2.5, par4 + par1World.rand.nextDouble(), 0.0, 0.0, 0.0);
            }
        }
        else if (par1World.getBlockId(par2, par3 - 1, par4) == Block.blockIron.blockID && par1World.getBlockId(par2, par3 - 2, par4) == Block.blockIron.blockID) {
            final boolean var11 = par1World.getBlockId(par2 - 1, par3 - 1, par4) == Block.blockIron.blockID && par1World.getBlockId(par2 + 1, par3 - 1, par4) == Block.blockIron.blockID;
            final boolean var12 = par1World.getBlockId(par2, par3 - 1, par4 - 1) == Block.blockIron.blockID && par1World.getBlockId(par2, par3 - 1, par4 + 1) == Block.blockIron.blockID;
            if (var11 || var12) {
                par1World.setBlock(par2, par3, par4, 0, 0, 2);
                par1World.setBlock(par2, par3 - 1, par4, 0, 0, 2);
                par1World.setBlock(par2, par3 - 2, par4, 0, 0, 2);
                if (var11) {
                    par1World.setBlock(par2 - 1, par3 - 1, par4, 0, 0, 2);
                    par1World.setBlock(par2 + 1, par3 - 1, par4, 0, 0, 2);
                }
                else {
                    par1World.setBlock(par2, par3 - 1, par4 - 1, 0, 0, 2);
                    par1World.setBlock(par2, par3 - 1, par4 + 1, 0, 0, 2);
                }
                final EntityIronGolem var13 = new EntityIronGolem(par1World);
                var13.setPlayerCreated(true);
                var13.setLocationAndAngles(par2 + 0.5, par3 - 1.95, par4 + 0.5, 0.0f, 0.0f);
                par1World.spawnEntityInWorld(var13);
                for (int var14 = 0; var14 < 120; ++var14) {
                    par1World.spawnParticle("snowballpoof", par2 + par1World.rand.nextDouble(), par3 - 2 + par1World.rand.nextDouble() * 3.9, par4 + par1World.rand.nextDouble(), 0.0, 0.0, 0.0);
                }
                par1World.notifyBlockChange(par2, par3, par4, 0);
                par1World.notifyBlockChange(par2, par3 - 1, par4, 0);
                par1World.notifyBlockChange(par2, par3 - 2, par4, 0);
                if (var11) {
                    par1World.notifyBlockChange(par2 - 1, par3 - 1, par4, 0);
                    par1World.notifyBlockChange(par2 + 1, par3 - 1, par4, 0);
                }
                else {
                    par1World.notifyBlockChange(par2, par3 - 1, par4 - 1, 0);
                    par1World.notifyBlockChange(par2, par3 - 1, par4 + 1, 0);
                }
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockId(par2, par3, par4);
        return (var5 == 0 || Block.blocksList[var5].blockMaterial.isReplaceable()) && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        final int var7 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0f / 360.0f + 2.5) & 0x3;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.field_94475_c = par1IconRegister.registerIcon(this.blockType ? "pumpkin_jack" : "pumpkin_face");
        this.field_94474_b = par1IconRegister.registerIcon("pumpkin_top");
        this.blockIcon = par1IconRegister.registerIcon("pumpkin_side");
    }
}
