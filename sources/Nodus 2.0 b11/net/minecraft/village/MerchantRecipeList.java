/*   1:    */ package net.minecraft.village;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import net.minecraft.item.ItemStack;
/*   6:    */ import net.minecraft.nbt.NBTTagCompound;
/*   7:    */ import net.minecraft.nbt.NBTTagList;
/*   8:    */ import net.minecraft.network.PacketBuffer;
/*   9:    */ 
/*  10:    */ public class MerchantRecipeList
/*  11:    */   extends ArrayList
/*  12:    */ {
/*  13:    */   private static final String __OBFID = "CL_00000127";
/*  14:    */   
/*  15:    */   public MerchantRecipeList() {}
/*  16:    */   
/*  17:    */   public MerchantRecipeList(NBTTagCompound par1NBTTagCompound)
/*  18:    */   {
/*  19: 18 */     readRecipiesFromTags(par1NBTTagCompound);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public MerchantRecipe canRecipeBeUsed(ItemStack par1ItemStack, ItemStack par2ItemStack, int par3)
/*  23:    */   {
/*  24: 26 */     if ((par3 > 0) && (par3 < size()))
/*  25:    */     {
/*  26: 28 */       MerchantRecipe var6 = (MerchantRecipe)get(par3);
/*  27: 29 */       return (par1ItemStack.getItem() == var6.getItemToBuy().getItem()) && (((par2ItemStack == null) && (!var6.hasSecondItemToBuy())) || ((var6.hasSecondItemToBuy()) && (par2ItemStack != null) && (var6.getSecondItemToBuy().getItem() == par2ItemStack.getItem()) && (par1ItemStack.stackSize >= var6.getItemToBuy().stackSize) && ((!var6.hasSecondItemToBuy()) || (par2ItemStack.stackSize >= var6.getSecondItemToBuy().stackSize)))) ? var6 : null;
/*  28:    */     }
/*  29: 33 */     for (int var4 = 0; var4 < size(); var4++)
/*  30:    */     {
/*  31: 35 */       MerchantRecipe var5 = (MerchantRecipe)get(var4);
/*  32: 37 */       if ((par1ItemStack.getItem() == var5.getItemToBuy().getItem()) && (par1ItemStack.stackSize >= var5.getItemToBuy().stackSize) && (((!var5.hasSecondItemToBuy()) && (par2ItemStack == null)) || ((var5.hasSecondItemToBuy()) && (par2ItemStack != null) && (var5.getSecondItemToBuy().getItem() == par2ItemStack.getItem()) && (par2ItemStack.stackSize >= var5.getSecondItemToBuy().stackSize)))) {
/*  33: 39 */         return var5;
/*  34:    */       }
/*  35:    */     }
/*  36: 43 */     return null;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void addToListWithCheck(MerchantRecipe par1MerchantRecipe)
/*  40:    */   {
/*  41: 52 */     for (int var2 = 0; var2 < size(); var2++)
/*  42:    */     {
/*  43: 54 */       MerchantRecipe var3 = (MerchantRecipe)get(var2);
/*  44: 56 */       if (par1MerchantRecipe.hasSameIDsAs(var3))
/*  45:    */       {
/*  46: 58 */         if (par1MerchantRecipe.hasSameItemsAs(var3)) {
/*  47: 60 */           set(var2, par1MerchantRecipe);
/*  48:    */         }
/*  49: 63 */         return;
/*  50:    */       }
/*  51:    */     }
/*  52: 67 */     add(par1MerchantRecipe);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void func_151391_a(PacketBuffer p_151391_1_)
/*  56:    */     throws IOException
/*  57:    */   {
/*  58: 72 */     p_151391_1_.writeByte((byte)(size() & 0xFF));
/*  59: 74 */     for (int var2 = 0; var2 < size(); var2++)
/*  60:    */     {
/*  61: 76 */       MerchantRecipe var3 = (MerchantRecipe)get(var2);
/*  62: 77 */       p_151391_1_.writeItemStackToBuffer(var3.getItemToBuy());
/*  63: 78 */       p_151391_1_.writeItemStackToBuffer(var3.getItemToSell());
/*  64: 79 */       ItemStack var4 = var3.getSecondItemToBuy();
/*  65: 80 */       p_151391_1_.writeBoolean(var4 != null);
/*  66: 82 */       if (var4 != null) {
/*  67: 84 */         p_151391_1_.writeItemStackToBuffer(var4);
/*  68:    */       }
/*  69: 87 */       p_151391_1_.writeBoolean(var3.isRecipeDisabled());
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static MerchantRecipeList func_151390_b(PacketBuffer p_151390_0_)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76: 93 */     MerchantRecipeList var1 = new MerchantRecipeList();
/*  77: 94 */     int var2 = p_151390_0_.readByte() & 0xFF;
/*  78: 96 */     for (int var3 = 0; var3 < var2; var3++)
/*  79:    */     {
/*  80: 98 */       ItemStack var4 = p_151390_0_.readItemStackFromBuffer();
/*  81: 99 */       ItemStack var5 = p_151390_0_.readItemStackFromBuffer();
/*  82:100 */       ItemStack var6 = null;
/*  83:102 */       if (p_151390_0_.readBoolean()) {
/*  84:104 */         var6 = p_151390_0_.readItemStackFromBuffer();
/*  85:    */       }
/*  86:107 */       boolean var7 = p_151390_0_.readBoolean();
/*  87:108 */       MerchantRecipe var8 = new MerchantRecipe(var4, var6, var5);
/*  88:110 */       if (var7) {
/*  89:112 */         var8.func_82785_h();
/*  90:    */       }
/*  91:115 */       var1.add(var8);
/*  92:    */     }
/*  93:118 */     return var1;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void readRecipiesFromTags(NBTTagCompound par1NBTTagCompound)
/*  97:    */   {
/*  98:123 */     NBTTagList var2 = par1NBTTagCompound.getTagList("Recipes", 10);
/*  99:125 */     for (int var3 = 0; var3 < var2.tagCount(); var3++)
/* 100:    */     {
/* 101:127 */       NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/* 102:128 */       add(new MerchantRecipe(var4));
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public NBTTagCompound getRecipiesAsTags()
/* 107:    */   {
/* 108:134 */     NBTTagCompound var1 = new NBTTagCompound();
/* 109:135 */     NBTTagList var2 = new NBTTagList();
/* 110:137 */     for (int var3 = 0; var3 < size(); var3++)
/* 111:    */     {
/* 112:139 */       MerchantRecipe var4 = (MerchantRecipe)get(var3);
/* 113:140 */       var2.appendTag(var4.writeToTags());
/* 114:    */     }
/* 115:143 */     var1.setTag("Recipes", var2);
/* 116:144 */     return var1;
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.village.MerchantRecipeList
 * JD-Core Version:    0.7.0.1
 */