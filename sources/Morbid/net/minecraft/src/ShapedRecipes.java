package net.minecraft.src;

public class ShapedRecipes implements IRecipe
{
    private int recipeWidth;
    private int recipeHeight;
    private ItemStack[] recipeItems;
    private ItemStack recipeOutput;
    public final int recipeOutputItemID;
    private boolean field_92101_f;
    
    public ShapedRecipes(final int par1, final int par2, final ItemStack[] par3ArrayOfItemStack, final ItemStack par4ItemStack) {
        this.field_92101_f = false;
        this.recipeOutputItemID = par4ItemStack.itemID;
        this.recipeWidth = par1;
        this.recipeHeight = par2;
        this.recipeItems = par3ArrayOfItemStack;
        this.recipeOutput = par4ItemStack;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }
    
    @Override
    public boolean matches(final InventoryCrafting par1InventoryCrafting, final World par2World) {
        for (int var3 = 0; var3 <= 3 - this.recipeWidth; ++var3) {
            for (int var4 = 0; var4 <= 3 - this.recipeHeight; ++var4) {
                if (this.checkMatch(par1InventoryCrafting, var3, var4, true)) {
                    return true;
                }
                if (this.checkMatch(par1InventoryCrafting, var3, var4, false)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean checkMatch(final InventoryCrafting par1InventoryCrafting, final int par2, final int par3, final boolean par4) {
        for (int var5 = 0; var5 < 3; ++var5) {
            for (int var6 = 0; var6 < 3; ++var6) {
                final int var7 = var5 - par2;
                final int var8 = var6 - par3;
                ItemStack var9 = null;
                if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth && var8 < this.recipeHeight) {
                    if (par4) {
                        var9 = this.recipeItems[this.recipeWidth - var7 - 1 + var8 * this.recipeWidth];
                    }
                    else {
                        var9 = this.recipeItems[var7 + var8 * this.recipeWidth];
                    }
                }
                final ItemStack var10 = par1InventoryCrafting.getStackInRowAndColumn(var5, var6);
                if (var10 != null || var9 != null) {
                    if ((var10 == null && var9 != null) || (var10 != null && var9 == null)) {
                        return false;
                    }
                    if (var9.itemID != var10.itemID) {
                        return false;
                    }
                    if (var9.getItemDamage() != 32767 && var9.getItemDamage() != var10.getItemDamage()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting par1InventoryCrafting) {
        final ItemStack var2 = this.getRecipeOutput().copy();
        if (this.field_92101_f) {
            for (int var3 = 0; var3 < par1InventoryCrafting.getSizeInventory(); ++var3) {
                final ItemStack var4 = par1InventoryCrafting.getStackInSlot(var3);
                if (var4 != null && var4.hasTagCompound()) {
                    var2.setTagCompound((NBTTagCompound)var4.stackTagCompound.copy());
                }
            }
        }
        return var2;
    }
    
    @Override
    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
    }
    
    public ShapedRecipes func_92100_c() {
        this.field_92101_f = true;
        return this;
    }
}
