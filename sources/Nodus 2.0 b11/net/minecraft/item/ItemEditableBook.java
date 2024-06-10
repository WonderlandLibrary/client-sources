/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.nbt.NBTTagCompound;
/*  6:   */ import net.minecraft.util.EnumChatFormatting;
/*  7:   */ import net.minecraft.util.StatCollector;
/*  8:   */ import net.minecraft.util.StringUtils;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class ItemEditableBook
/* 12:   */   extends Item
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00000077";
/* 15:   */   
/* 16:   */   public ItemEditableBook()
/* 17:   */   {
/* 18:17 */     setMaxStackSize(1);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static boolean validBookTagContents(NBTTagCompound par0NBTTagCompound)
/* 22:   */   {
/* 23:22 */     if (!ItemWritableBook.func_150930_a(par0NBTTagCompound)) {
/* 24:24 */       return false;
/* 25:   */     }
/* 26:26 */     if (!par0NBTTagCompound.func_150297_b("title", 8)) {
/* 27:28 */       return false;
/* 28:   */     }
/* 29:32 */     String var1 = par0NBTTagCompound.getString("title");
/* 30:33 */     return (var1 != null) && (var1.length() <= 16) ? par0NBTTagCompound.func_150297_b("author", 8) : false;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getItemStackDisplayName(ItemStack par1ItemStack)
/* 34:   */   {
/* 35:39 */     if (par1ItemStack.hasTagCompound())
/* 36:   */     {
/* 37:41 */       NBTTagCompound var2 = par1ItemStack.getTagCompound();
/* 38:42 */       String var3 = var2.getString("title");
/* 39:44 */       if (!StringUtils.isNullOrEmpty(var3)) {
/* 40:46 */         return var3;
/* 41:   */       }
/* 42:   */     }
/* 43:50 */     return super.getItemStackDisplayName(par1ItemStack);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 47:   */   {
/* 48:58 */     if (par1ItemStack.hasTagCompound())
/* 49:   */     {
/* 50:60 */       NBTTagCompound var5 = par1ItemStack.getTagCompound();
/* 51:61 */       String var6 = var5.getString("author");
/* 52:63 */       if (!StringUtils.isNullOrEmpty(var6)) {
/* 53:65 */         par3List.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("book.byAuthor", new Object[] { var6 }));
/* 54:   */       }
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 59:   */   {
/* 60:75 */     par3EntityPlayer.displayGUIBook(par1ItemStack);
/* 61:76 */     return par1ItemStack;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public boolean getShareTag()
/* 65:   */   {
/* 66:84 */     return true;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public boolean hasEffect(ItemStack par1ItemStack)
/* 70:   */   {
/* 71:89 */     return true;
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemEditableBook
 * JD-Core Version:    0.7.0.1
 */