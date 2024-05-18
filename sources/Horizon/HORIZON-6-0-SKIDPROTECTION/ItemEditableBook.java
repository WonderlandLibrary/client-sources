package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class ItemEditableBook extends Item_1028566121
{
    private static final String à = "CL_00000077";
    
    public ItemEditableBook() {
        this.Â(1);
    }
    
    public static boolean Â(final NBTTagCompound p_77828_0_) {
        if (!ItemWritableBook.Â(p_77828_0_)) {
            return false;
        }
        if (!p_77828_0_.Â("title", 8)) {
            return false;
        }
        final String var1 = p_77828_0_.áˆºÑ¢Õ("title");
        return var1 != null && var1.length() <= 32 && p_77828_0_.Â("author", 8);
    }
    
    public static int ÂµÈ(final ItemStack p_179230_0_) {
        return p_179230_0_.Å().Ó("generation");
    }
    
    @Override
    public String à(final ItemStack stack) {
        if (stack.£á()) {
            final NBTTagCompound var2 = stack.Å();
            final String var3 = var2.áˆºÑ¢Õ("title");
            if (!StringUtils.Â(var3)) {
                return var3;
            }
        }
        return super.à(stack);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final List tooltip, final boolean advanced) {
        if (stack.£á()) {
            final NBTTagCompound var5 = stack.Å();
            final String var6 = var5.áˆºÑ¢Õ("author");
            if (!StringUtils.Â(var6)) {
                tooltip.add(EnumChatFormatting.Ø + StatCollector.HorizonCode_Horizon_È("book.byAuthor", var6));
            }
            tooltip.add(EnumChatFormatting.Ø + StatCollector.HorizonCode_Horizon_È("book.generation." + var5.Ó("generation")));
        }
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        if (!worldIn.ŠÄ) {
            this.HorizonCode_Horizon_È(itemStackIn, playerIn);
        }
        playerIn.HorizonCode_Horizon_È(itemStackIn);
        playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
        return itemStackIn;
    }
    
    private void HorizonCode_Horizon_È(final ItemStack p_179229_1_, final EntityPlayer p_179229_2_) {
        if (p_179229_1_ != null && p_179229_1_.Å() != null) {
            final NBTTagCompound var3 = p_179229_1_.Å();
            if (!var3.£á("resolved")) {
                var3.HorizonCode_Horizon_È("resolved", true);
                if (Â(var3)) {
                    final NBTTagList var4 = var3.Ý("pages", 8);
                    for (int var5 = 0; var5 < var4.Âµá€(); ++var5) {
                        final String var6 = var4.Ó(var5);
                        Object var8;
                        try {
                            final IChatComponent var7 = IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var6);
                            var8 = ChatComponentProcessor.HorizonCode_Horizon_È(p_179229_2_, var7, p_179229_2_);
                        }
                        catch (Exception var10) {
                            var8 = new ChatComponentText(var6);
                        }
                        var4.HorizonCode_Horizon_È(var5, new NBTTagString(IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È((IChatComponent)var8)));
                    }
                    var3.HorizonCode_Horizon_È("pages", var4);
                    if (p_179229_2_ instanceof EntityPlayerMP && p_179229_2_.áŒŠá() == p_179229_1_) {
                        final Slot var9 = p_179229_2_.Ï­Ï.HorizonCode_Horizon_È(p_179229_2_.Ø­Ñ¢Ï­Ø­áˆº, p_179229_2_.Ø­Ñ¢Ï­Ø­áˆº.Ý);
                        ((EntityPlayerMP)p_179229_2_).HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2FPacketSetSlot(0, var9.Ý, p_179229_1_));
                    }
                }
            }
        }
    }
    
    @Override
    public boolean Ø(final ItemStack stack) {
        return true;
    }
}
