package net.minecraft.item.crafting;

import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

static class RecipeAddPattern implements IRecipe
{
    private RecipeAddPattern() {
    }
    
    RecipeAddPattern(final Object p_i45780_1_) {
        this();
    }
    
    @Override
    public boolean matches(final InventoryCrafting p_77569_1_, final World worldIn) {
        boolean var3 = false;
        for (int var4 = 0; var4 < p_77569_1_.getSizeInventory(); ++var4) {
            final ItemStack var5 = p_77569_1_.getStackInSlot(var4);
            if (var5 != null && var5.getItem() == Items.banner) {
                if (var3) {
                    return false;
                }
                if (TileEntityBanner.func_175113_c(var5) >= 6) {
                    return false;
                }
                var3 = true;
            }
        }
        return var3 && this.func_179533_c(p_77569_1_) != null;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting p_77572_1_) {
        ItemStack var2 = null;
        for (int var3 = 0; var3 < p_77572_1_.getSizeInventory(); ++var3) {
            final ItemStack var4 = p_77572_1_.getStackInSlot(var3);
            if (var4 != null && var4.getItem() == Items.banner) {
                var2 = var4.copy();
                var2.stackSize = 1;
                break;
            }
        }
        final TileEntityBanner.EnumBannerPattern var5 = this.func_179533_c(p_77572_1_);
        if (var5 != null) {
            int var6 = 0;
            for (int var7 = 0; var7 < p_77572_1_.getSizeInventory(); ++var7) {
                final ItemStack var8 = p_77572_1_.getStackInSlot(var7);
                if (var8 != null && var8.getItem() == Items.dye) {
                    var6 = var8.getMetadata();
                    break;
                }
            }
            final NBTTagCompound var9 = var2.getSubCompound("BlockEntityTag", true);
            final ItemStack var8 = null;
            NBTTagList var10;
            if (var9.hasKey("Patterns", 9)) {
                var10 = var9.getTagList("Patterns", 10);
            }
            else {
                var10 = new NBTTagList();
                var9.setTag("Patterns", var10);
            }
            final NBTTagCompound var11 = new NBTTagCompound();
            var11.setString("Pattern", var5.func_177273_b());
            var11.setInteger("Color", var6);
            var10.appendTag(var11);
        }
        return var2;
    }
    
    @Override
    public int getRecipeSize() {
        return 10;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
    
    @Override
    public ItemStack[] func_179532_b(final InventoryCrafting p_179532_1_) {
        final ItemStack[] var2 = new ItemStack[p_179532_1_.getSizeInventory()];
        for (int var3 = 0; var3 < var2.length; ++var3) {
            final ItemStack var4 = p_179532_1_.getStackInSlot(var3);
            if (var4 != null && var4.getItem().hasContainerItem()) {
                var2[var3] = new ItemStack(var4.getItem().getContainerItem());
            }
        }
        return var2;
    }
    
    private TileEntityBanner.EnumBannerPattern func_179533_c(final InventoryCrafting p_179533_1_) {
        for (final TileEntityBanner.EnumBannerPattern var5 : TileEntityBanner.EnumBannerPattern.values()) {
            if (var5.func_177270_d()) {
                boolean var6 = true;
                if (var5.func_177269_e()) {
                    boolean var7 = false;
                    boolean var8 = false;
                    for (int var9 = 0; var9 < p_179533_1_.getSizeInventory() && var6; ++var9) {
                        final ItemStack var10 = p_179533_1_.getStackInSlot(var9);
                        if (var10 != null && var10.getItem() != Items.banner) {
                            if (var10.getItem() == Items.dye) {
                                if (var8) {
                                    var6 = false;
                                    break;
                                }
                                var8 = true;
                            }
                            else {
                                if (var7 || !var10.isItemEqual(var5.func_177272_f())) {
                                    var6 = false;
                                    break;
                                }
                                var7 = true;
                            }
                        }
                    }
                    if (!var7) {
                        var6 = false;
                    }
                }
                else if (p_179533_1_.getSizeInventory() != var5.func_177267_c().length * var5.func_177267_c()[0].length()) {
                    var6 = false;
                }
                else {
                    int var11 = -1;
                    for (int var12 = 0; var12 < p_179533_1_.getSizeInventory() && var6; ++var12) {
                        final int var9 = var12 / 3;
                        final int var13 = var12 % 3;
                        final ItemStack var14 = p_179533_1_.getStackInSlot(var12);
                        if (var14 != null && var14.getItem() != Items.banner) {
                            if (var14.getItem() != Items.dye) {
                                var6 = false;
                                break;
                            }
                            if (var11 != -1 && var11 != var14.getMetadata()) {
                                var6 = false;
                                break;
                            }
                            if (var5.func_177267_c()[var9].charAt(var13) == ' ') {
                                var6 = false;
                                break;
                            }
                            var11 = var14.getMetadata();
                        }
                        else if (var5.func_177267_c()[var9].charAt(var13) != ' ') {
                            var6 = false;
                            break;
                        }
                    }
                }
                if (var6) {
                    return var5;
                }
            }
        }
        return null;
    }
}
