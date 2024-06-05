package net.minecraft.src;

import java.util.*;

public class ItemDye extends Item
{
    public static final String[] dyeColorNames;
    public static final String[] field_94595_b;
    public static final int[] dyeColors;
    private Icon[] field_94594_d;
    
    static {
        dyeColorNames = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white" };
        field_94595_b = new String[] { "dyePowder_black", "dyePowder_red", "dyePowder_green", "dyePowder_brown", "dyePowder_blue", "dyePowder_purple", "dyePowder_cyan", "dyePowder_silver", "dyePowder_gray", "dyePowder_pink", "dyePowder_lime", "dyePowder_yellow", "dyePowder_lightBlue", "dyePowder_magenta", "dyePowder_orange", "dyePowder_white" };
        dyeColors = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
    }
    
    public ItemDye(final int par1) {
        super(par1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public Icon getIconFromDamage(final int par1) {
        final int var2 = MathHelper.clamp_int(par1, 0, 15);
        return this.field_94594_d[var2];
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        final int var2 = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
        return String.valueOf(super.getUnlocalizedName()) + "." + ItemDye.dyeColorNames[var2];
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, int par4, final int par5, int par6, final int par7, final float par8, final float par9, final float par10) {
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        if (par1ItemStack.getItemDamage() == 15) {
            if (func_96604_a(par1ItemStack, par3World, par4, par5, par6)) {
                if (!par3World.isRemote) {
                    par3World.playAuxSFX(2005, par4, par5, par6, 0);
                }
                return true;
            }
        }
        else if (par1ItemStack.getItemDamage() == 3) {
            final int var11 = par3World.getBlockId(par4, par5, par6);
            final int var12 = par3World.getBlockMetadata(par4, par5, par6);
            if (var11 == Block.wood.blockID && BlockLog.limitToValidMetadata(var12) == 3) {
                if (par7 == 0) {
                    return false;
                }
                if (par7 == 1) {
                    return false;
                }
                if (par7 == 2) {
                    --par6;
                }
                if (par7 == 3) {
                    ++par6;
                }
                if (par7 == 4) {
                    --par4;
                }
                if (par7 == 5) {
                    ++par4;
                }
                if (par3World.isAirBlock(par4, par5, par6)) {
                    final int var13 = Block.blocksList[Block.cocoaPlant.blockID].onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, 0);
                    par3World.setBlock(par4, par5, par6, Block.cocoaPlant.blockID, var13, 2);
                    if (!par2EntityPlayer.capabilities.isCreativeMode) {
                        --par1ItemStack.stackSize;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    public static boolean func_96604_a(final ItemStack par0ItemStack, final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockId(par2, par3, par4);
        if (var5 == Block.sapling.blockID) {
            if (!par1World.isRemote) {
                if (par1World.rand.nextFloat() < 0.45) {
                    ((BlockSapling)Block.sapling).markOrGrowMarked(par1World, par2, par3, par4, par1World.rand);
                }
                --par0ItemStack.stackSize;
            }
            return true;
        }
        if (var5 == Block.mushroomBrown.blockID || var5 == Block.mushroomRed.blockID) {
            if (!par1World.isRemote) {
                if (par1World.rand.nextFloat() < 0.4) {
                    ((BlockMushroom)Block.blocksList[var5]).fertilizeMushroom(par1World, par2, par3, par4, par1World.rand);
                }
                --par0ItemStack.stackSize;
            }
            return true;
        }
        if (var5 != Block.melonStem.blockID && var5 != Block.pumpkinStem.blockID) {
            if (var5 > 0 && Block.blocksList[var5] instanceof BlockCrops) {
                if (par1World.getBlockMetadata(par2, par3, par4) == 7) {
                    return false;
                }
                if (!par1World.isRemote) {
                    ((BlockCrops)Block.blocksList[var5]).fertilize(par1World, par2, par3, par4);
                    --par0ItemStack.stackSize;
                }
                return true;
            }
            else if (var5 == Block.cocoaPlant.blockID) {
                final int var6 = par1World.getBlockMetadata(par2, par3, par4);
                final int var7 = BlockDirectional.getDirection(var6);
                int var8 = BlockCocoa.func_72219_c(var6);
                if (var8 >= 2) {
                    return false;
                }
                if (!par1World.isRemote) {
                    ++var8;
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var8 << 2 | var7, 2);
                    --par0ItemStack.stackSize;
                }
                return true;
            }
            else {
                if (var5 != Block.grass.blockID) {
                    return false;
                }
                if (!par1World.isRemote) {
                    --par0ItemStack.stackSize;
                    int var6 = 0;
                Label_0564_Outer:
                    while (var6 < 128) {
                        int var7 = par2;
                        int var8 = par3 + 1;
                        int var9 = par4;
                        int var10 = 0;
                        while (true) {
                            while (var10 < var6 / 16) {
                                var7 += ItemDye.itemRand.nextInt(3) - 1;
                                var8 += (ItemDye.itemRand.nextInt(3) - 1) * ItemDye.itemRand.nextInt(3) / 2;
                                var9 += ItemDye.itemRand.nextInt(3) - 1;
                                if (par1World.getBlockId(var7, var8 - 1, var9) == Block.grass.blockID) {
                                    if (!par1World.isBlockNormalCube(var7, var8, var9)) {
                                        ++var10;
                                        continue Label_0564_Outer;
                                    }
                                }
                                ++var6;
                                continue Label_0564_Outer;
                            }
                            if (par1World.getBlockId(var7, var8, var9) != 0) {
                                continue;
                            }
                            if (ItemDye.itemRand.nextInt(10) != 0) {
                                if (Block.tallGrass.canBlockStay(par1World, var7, var8, var9)) {
                                    par1World.setBlock(var7, var8, var9, Block.tallGrass.blockID, 1, 3);
                                }
                                continue;
                            }
                            else if (ItemDye.itemRand.nextInt(3) != 0) {
                                if (Block.plantYellow.canBlockStay(par1World, var7, var8, var9)) {
                                    par1World.setBlock(var7, var8, var9, Block.plantYellow.blockID);
                                }
                                continue;
                            }
                            else {
                                if (Block.plantRed.canBlockStay(par1World, var7, var8, var9)) {
                                    par1World.setBlock(var7, var8, var9, Block.plantRed.blockID);
                                }
                                continue;
                            }
                            break;
                        }
                    }
                }
                return true;
            }
        }
        else {
            if (par1World.getBlockMetadata(par2, par3, par4) == 7) {
                return false;
            }
            if (!par1World.isRemote) {
                ((BlockStem)Block.blocksList[var5]).fertilizeStem(par1World, par2, par3, par4);
                --par0ItemStack.stackSize;
            }
            return true;
        }
    }
    
    public static void func_96603_a(final World par0World, final int par1, final int par2, final int par3, int par4) {
        final int var5 = par0World.getBlockId(par1, par2, par3);
        if (par4 == 0) {
            par4 = 15;
        }
        final Block var6 = (var5 > 0 && var5 < Block.blocksList.length) ? Block.blocksList[var5] : null;
        if (var6 != null) {
            var6.setBlockBoundsBasedOnState(par0World, par1, par2, par3);
            for (int var7 = 0; var7 < par4; ++var7) {
                final double var8 = ItemDye.itemRand.nextGaussian() * 0.02;
                final double var9 = ItemDye.itemRand.nextGaussian() * 0.02;
                final double var10 = ItemDye.itemRand.nextGaussian() * 0.02;
                par0World.spawnParticle("happyVillager", par1 + ItemDye.itemRand.nextFloat(), par2 + ItemDye.itemRand.nextFloat() * var6.getBlockBoundsMaxY(), par3 + ItemDye.itemRand.nextFloat(), var8, var9, var10);
            }
        }
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack par1ItemStack, final EntityLiving par2EntityLiving) {
        if (par2EntityLiving instanceof EntitySheep) {
            final EntitySheep var3 = (EntitySheep)par2EntityLiving;
            final int var4 = BlockCloth.getBlockFromDye(par1ItemStack.getItemDamage());
            if (!var3.getSheared() && var3.getFleeceColor() != var4) {
                var3.setFleeceColor(var4);
                --par1ItemStack.stackSize;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void getSubItems(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int var4 = 0; var4 < 16; ++var4) {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.field_94594_d = new Icon[ItemDye.field_94595_b.length];
        for (int var2 = 0; var2 < ItemDye.field_94595_b.length; ++var2) {
            this.field_94594_d[var2] = par1IconRegister.registerIcon(ItemDye.field_94595_b[var2]);
        }
    }
}
