package net.minecraft.src;

import java.util.*;
import java.io.*;

public class MerchantRecipeList extends ArrayList
{
    public MerchantRecipeList() {
    }
    
    public MerchantRecipeList(final NBTTagCompound par1NBTTagCompound) {
        this.readRecipiesFromTags(par1NBTTagCompound);
    }
    
    public MerchantRecipe canRecipeBeUsed(final ItemStack par1ItemStack, final ItemStack par2ItemStack, final int par3) {
        if (par3 > 0 && par3 < this.size()) {
            final MerchantRecipe var6 = this.get(par3);
            return (par1ItemStack.itemID == var6.getItemToBuy().itemID && ((par2ItemStack == null && !var6.hasSecondItemToBuy()) || (var6.hasSecondItemToBuy() && par2ItemStack != null && var6.getSecondItemToBuy().itemID == par2ItemStack.itemID)) && par1ItemStack.stackSize >= var6.getItemToBuy().stackSize && (!var6.hasSecondItemToBuy() || par2ItemStack.stackSize >= var6.getSecondItemToBuy().stackSize)) ? var6 : null;
        }
        for (int var7 = 0; var7 < this.size(); ++var7) {
            final MerchantRecipe var8 = this.get(var7);
            if (par1ItemStack.itemID == var8.getItemToBuy().itemID && par1ItemStack.stackSize >= var8.getItemToBuy().stackSize && ((!var8.hasSecondItemToBuy() && par2ItemStack == null) || (var8.hasSecondItemToBuy() && par2ItemStack != null && var8.getSecondItemToBuy().itemID == par2ItemStack.itemID && par2ItemStack.stackSize >= var8.getSecondItemToBuy().stackSize))) {
                return var8;
            }
        }
        return null;
    }
    
    public void addToListWithCheck(final MerchantRecipe par1MerchantRecipe) {
        for (int var2 = 0; var2 < this.size(); ++var2) {
            final MerchantRecipe var3 = this.get(var2);
            if (par1MerchantRecipe.hasSameIDsAs(var3)) {
                if (par1MerchantRecipe.hasSameItemsAs(var3)) {
                    this.set(var2, par1MerchantRecipe);
                }
                return;
            }
        }
        this.add(par1MerchantRecipe);
    }
    
    public void writeRecipiesToStream(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte((byte)(this.size() & 0xFF));
        for (int var2 = 0; var2 < this.size(); ++var2) {
            final MerchantRecipe var3 = this.get(var2);
            Packet.writeItemStack(var3.getItemToBuy(), par1DataOutputStream);
            Packet.writeItemStack(var3.getItemToSell(), par1DataOutputStream);
            final ItemStack var4 = var3.getSecondItemToBuy();
            par1DataOutputStream.writeBoolean(var4 != null);
            if (var4 != null) {
                Packet.writeItemStack(var4, par1DataOutputStream);
            }
            par1DataOutputStream.writeBoolean(var3.func_82784_g());
        }
    }
    
    public static MerchantRecipeList readRecipiesFromStream(final DataInputStream par0DataInputStream) throws IOException {
        final MerchantRecipeList var1 = new MerchantRecipeList();
        for (int var2 = par0DataInputStream.readByte() & 0xFF, var3 = 0; var3 < var2; ++var3) {
            final ItemStack var4 = Packet.readItemStack(par0DataInputStream);
            final ItemStack var5 = Packet.readItemStack(par0DataInputStream);
            ItemStack var6 = null;
            if (par0DataInputStream.readBoolean()) {
                var6 = Packet.readItemStack(par0DataInputStream);
            }
            final boolean var7 = par0DataInputStream.readBoolean();
            final MerchantRecipe var8 = new MerchantRecipe(var4, var6, var5);
            if (var7) {
                var8.func_82785_h();
            }
            var1.add(var8);
        }
        return var1;
    }
    
    public void readRecipiesFromTags(final NBTTagCompound par1NBTTagCompound) {
        final NBTTagList var2 = par1NBTTagCompound.getTagList("Recipes");
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            this.add(new MerchantRecipe(var4));
        }
    }
    
    public NBTTagCompound getRecipiesAsTags() {
        final NBTTagCompound var1 = new NBTTagCompound();
        final NBTTagList var2 = new NBTTagList("Recipes");
        for (int var3 = 0; var3 < this.size(); ++var3) {
            final MerchantRecipe var4 = this.get(var3);
            var2.appendTag(var4.writeToTags());
        }
        var1.setTag("Recipes", var2);
        return var1;
    }
}
