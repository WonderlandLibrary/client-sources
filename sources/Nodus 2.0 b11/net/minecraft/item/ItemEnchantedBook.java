/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.enchantment.Enchantment;
/*   6:    */ import net.minecraft.enchantment.EnchantmentData;
/*   7:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.init.Items;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraft.nbt.NBTTagList;
/*  12:    */ import net.minecraft.util.WeightedRandomChestContent;
/*  13:    */ 
/*  14:    */ public class ItemEnchantedBook
/*  15:    */   extends Item
/*  16:    */ {
/*  17:    */   private static final String __OBFID = "CL_00000025";
/*  18:    */   
/*  19:    */   public boolean hasEffect(ItemStack par1ItemStack)
/*  20:    */   {
/*  21: 20 */     return true;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public boolean isItemTool(ItemStack par1ItemStack)
/*  25:    */   {
/*  26: 28 */     return false;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public EnumRarity getRarity(ItemStack par1ItemStack)
/*  30:    */   {
/*  31: 36 */     return func_92110_g(par1ItemStack).tagCount() > 0 ? EnumRarity.uncommon : super.getRarity(par1ItemStack);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public NBTTagList func_92110_g(ItemStack par1ItemStack)
/*  35:    */   {
/*  36: 41 */     return (par1ItemStack.stackTagCompound != null) && (par1ItemStack.stackTagCompound.func_150297_b("StoredEnchantments", 9)) ? (NBTTagList)par1ItemStack.stackTagCompound.getTag("StoredEnchantments") : new NBTTagList();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/*  40:    */   {
/*  41: 49 */     super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
/*  42: 50 */     NBTTagList var5 = func_92110_g(par1ItemStack);
/*  43: 52 */     if (var5 != null) {
/*  44: 54 */       for (int var6 = 0; var6 < var5.tagCount(); var6++)
/*  45:    */       {
/*  46: 56 */         short var7 = var5.getCompoundTagAt(var6).getShort("id");
/*  47: 57 */         short var8 = var5.getCompoundTagAt(var6).getShort("lvl");
/*  48: 59 */         if (Enchantment.enchantmentsList[var7] != null) {
/*  49: 61 */           par3List.add(Enchantment.enchantmentsList[var7].getTranslatedName(var8));
/*  50:    */         }
/*  51:    */       }
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void addEnchantment(ItemStack par1ItemStack, EnchantmentData par2EnchantmentData)
/*  56:    */   {
/*  57: 72 */     NBTTagList var3 = func_92110_g(par1ItemStack);
/*  58: 73 */     boolean var4 = true;
/*  59: 75 */     for (int var5 = 0; var5 < var3.tagCount(); var5++)
/*  60:    */     {
/*  61: 77 */       NBTTagCompound var6 = var3.getCompoundTagAt(var5);
/*  62: 79 */       if (var6.getShort("id") == par2EnchantmentData.enchantmentobj.effectId)
/*  63:    */       {
/*  64: 81 */         if (var6.getShort("lvl") < par2EnchantmentData.enchantmentLevel) {
/*  65: 83 */           var6.setShort("lvl", (short)par2EnchantmentData.enchantmentLevel);
/*  66:    */         }
/*  67: 86 */         var4 = false;
/*  68: 87 */         break;
/*  69:    */       }
/*  70:    */     }
/*  71: 91 */     if (var4)
/*  72:    */     {
/*  73: 93 */       NBTTagCompound var7 = new NBTTagCompound();
/*  74: 94 */       var7.setShort("id", (short)par2EnchantmentData.enchantmentobj.effectId);
/*  75: 95 */       var7.setShort("lvl", (short)par2EnchantmentData.enchantmentLevel);
/*  76: 96 */       var3.appendTag(var7);
/*  77:    */     }
/*  78: 99 */     if (!par1ItemStack.hasTagCompound()) {
/*  79:101 */       par1ItemStack.setTagCompound(new NBTTagCompound());
/*  80:    */     }
/*  81:104 */     par1ItemStack.getTagCompound().setTag("StoredEnchantments", var3);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public ItemStack getEnchantedItemStack(EnchantmentData par1EnchantmentData)
/*  85:    */   {
/*  86:112 */     ItemStack var2 = new ItemStack(this);
/*  87:113 */     addEnchantment(var2, par1EnchantmentData);
/*  88:114 */     return var2;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void func_92113_a(Enchantment par1Enchantment, List par2List)
/*  92:    */   {
/*  93:119 */     for (int var3 = par1Enchantment.getMinLevel(); var3 <= par1Enchantment.getMaxLevel(); var3++) {
/*  94:121 */       par2List.add(getEnchantedItemStack(new EnchantmentData(par1Enchantment, var3)));
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public WeightedRandomChestContent func_92114_b(Random par1Random)
/*  99:    */   {
/* 100:127 */     return func_92112_a(par1Random, 1, 1, 1);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public WeightedRandomChestContent func_92112_a(Random par1Random, int par2, int par3, int par4)
/* 104:    */   {
/* 105:132 */     ItemStack var5 = new ItemStack(Items.book, 1, 0);
/* 106:133 */     EnchantmentHelper.addRandomEnchantment(par1Random, var5, 30);
/* 107:134 */     return new WeightedRandomChestContent(var5, par2, par3, par4);
/* 108:    */   }
/* 109:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemEnchantedBook
 * JD-Core Version:    0.7.0.1
 */