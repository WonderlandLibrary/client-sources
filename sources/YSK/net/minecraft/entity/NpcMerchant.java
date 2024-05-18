package net.minecraft.entity;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.village.*;

public class NpcMerchant implements IMerchant
{
    private MerchantRecipeList recipeList;
    private static final String[] I;
    private IChatComponent field_175548_d;
    private InventoryMerchant theMerchantInventory;
    private EntityPlayer customer;
    
    @Override
    public void verifySellingItem(final ItemStack itemStack) {
    }
    
    @Override
    public IChatComponent getDisplayName() {
        IChatComponent field_175548_d;
        if (this.field_175548_d != null) {
            field_175548_d = this.field_175548_d;
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else {
            field_175548_d = new ChatComponentTranslation(NpcMerchant.I["".length()], new Object["".length()]);
        }
        return field_175548_d;
    }
    
    public NpcMerchant(final EntityPlayer customer, final IChatComponent field_175548_d) {
        this.customer = customer;
        this.field_175548_d = field_175548_d;
        this.theMerchantInventory = new InventoryMerchant(customer, this);
    }
    
    @Override
    public void useRecipe(final MerchantRecipe merchantRecipe) {
        merchantRecipe.incrementToolUses();
    }
    
    @Override
    public void setCustomer(final EntityPlayer entityPlayer) {
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public EntityPlayer getCustomer() {
        return this.customer;
    }
    
    @Override
    public MerchantRecipeList getRecipes(final EntityPlayer entityPlayer) {
        return this.recipeList;
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0004+?!\u0016\u0018k\u001d!\u000e\r$,-\u0010O+*%\u0007", "aEKHb");
    }
    
    @Override
    public void setRecipes(final MerchantRecipeList recipeList) {
        this.recipeList = recipeList;
    }
}
