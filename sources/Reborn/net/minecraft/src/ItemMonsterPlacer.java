package net.minecraft.src;

import java.util.*;

public class ItemMonsterPlacer extends Item
{
    private Icon theIcon;
    
    public ItemMonsterPlacer(final int par1) {
        super(par1);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public String getItemDisplayName(final ItemStack par1ItemStack) {
        String var2 = new StringBuilder().append(StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + ".name")).toString().trim();
        final String var3 = EntityList.getStringFromID(par1ItemStack.getItemDamage());
        if (var3 != null) {
            var2 = String.valueOf(var2) + " " + StatCollector.translateToLocal("entity." + var3 + ".name");
        }
        return var2;
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack par1ItemStack, final int par2) {
        final EntityEggInfo var3 = EntityList.entityEggs.get(par1ItemStack.getItemDamage());
        return (var3 != null) ? ((par2 == 0) ? var3.primaryColor : var3.secondaryColor) : 16777215;
    }
    
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @Override
    public Icon getIconFromDamageForRenderPass(final int par1, final int par2) {
        return (par2 > 0) ? this.theIcon : super.getIconFromDamageForRenderPass(par1, par2);
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, int par4, int par5, int par6, final int par7, final float par8, final float par9, final float par10) {
        if (par3World.isRemote) {
            return true;
        }
        final int var11 = par3World.getBlockId(par4, par5, par6);
        par4 += Facing.offsetsXForSide[par7];
        par5 += Facing.offsetsYForSide[par7];
        par6 += Facing.offsetsZForSide[par7];
        double var12 = 0.0;
        if (par7 == 1 && Block.blocksList[var11] != null && Block.blocksList[var11].getRenderType() == 11) {
            var12 = 0.5;
        }
        final Entity var13 = spawnCreature(par3World, par1ItemStack.getItemDamage(), par4 + 0.5, par5 + var12, par6 + 0.5);
        if (var13 != null) {
            if (var13 instanceof EntityLiving && par1ItemStack.hasDisplayName()) {
                ((EntityLiving)var13).func_94058_c(par1ItemStack.getDisplayName());
            }
            if (!par2EntityPlayer.capabilities.isCreativeMode) {
                --par1ItemStack.stackSize;
            }
        }
        return true;
    }
    
    public static Entity spawnCreature(final World par0World, final int par1, final double par2, final double par4, final double par6) {
        if (!EntityList.entityEggs.containsKey(par1)) {
            return null;
        }
        Entity var8 = null;
        for (int var9 = 0; var9 < 1; ++var9) {
            var8 = EntityList.createEntityByID(par1, par0World);
            if (var8 != null && var8 instanceof EntityLiving) {
                final EntityLiving var10 = (EntityLiving)var8;
                var8.setLocationAndAngles(par2, par4, par6, MathHelper.wrapAngleTo180_float(par0World.rand.nextFloat() * 360.0f), 0.0f);
                var10.rotationYawHead = var10.rotationYaw;
                var10.renderYawOffset = var10.rotationYaw;
                var10.initCreature();
                par0World.spawnEntityInWorld(var8);
                var10.playLivingSound();
            }
        }
        return var8;
    }
    
    @Override
    public void getSubItems(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (final EntityEggInfo var5 : EntityList.entityEggs.values()) {
            par3List.add(new ItemStack(par1, 1, var5.spawnedID));
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        this.theIcon = par1IconRegister.registerIcon("monsterPlacer_overlay");
    }
}
