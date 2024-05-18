package net.minecraft.src;

public class ItemWritableBook extends Item
{
    public ItemWritableBook(final int par1) {
        super(par1);
        this.setMaxStackSize(1);
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
    
    public static boolean validBookTagPages(final NBTTagCompound par0NBTTagCompound) {
        if (par0NBTTagCompound == null) {
            return false;
        }
        if (!par0NBTTagCompound.hasKey("pages")) {
            return false;
        }
        final NBTTagList var1 = (NBTTagList)par0NBTTagCompound.getTag("pages");
        for (int var2 = 0; var2 < var1.tagCount(); ++var2) {
            final NBTTagString var3 = (NBTTagString)var1.tagAt(var2);
            if (var3.data == null) {
                return false;
            }
            if (var3.data.length() > 256) {
                return false;
            }
        }
        return true;
    }
}
