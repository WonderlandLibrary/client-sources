package net.minecraft.src;

import java.util.*;

public class ItemEditableBook extends Item
{
    public ItemEditableBook(final int par1) {
        super(par1);
        this.setMaxStackSize(1);
    }
    
    public static boolean validBookTagContents(final NBTTagCompound par0NBTTagCompound) {
        if (!ItemWritableBook.validBookTagPages(par0NBTTagCompound)) {
            return false;
        }
        if (!par0NBTTagCompound.hasKey("title")) {
            return false;
        }
        final String var1 = par0NBTTagCompound.getString("title");
        return var1 != null && var1.length() <= 16 && par0NBTTagCompound.hasKey("author");
    }
    
    @Override
    public String getItemDisplayName(final ItemStack par1ItemStack) {
        if (par1ItemStack.hasTagCompound()) {
            final NBTTagCompound var2 = par1ItemStack.getTagCompound();
            final NBTTagString var3 = (NBTTagString)var2.getTag("title");
            if (var3 != null) {
                return var3.toString();
            }
        }
        return super.getItemDisplayName(par1ItemStack);
    }
    
    @Override
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
        if (par1ItemStack.hasTagCompound()) {
            final NBTTagCompound var5 = par1ItemStack.getTagCompound();
            final NBTTagString var6 = (NBTTagString)var5.getTag("author");
            if (var6 != null) {
                par3List.add(EnumChatFormatting.GRAY + String.format(StatCollector.translateToLocalFormatted("book.byAuthor", var6.data), new Object[0]));
            }
        }
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.displayGUIBook(par1ItemStack);
        return par1ItemStack;
    }
    
    @Override
    public boolean getShareTag() {
        return true;
    }
    
    @Override
    public boolean hasEffect(final ItemStack par1ItemStack) {
        return true;
    }
}
