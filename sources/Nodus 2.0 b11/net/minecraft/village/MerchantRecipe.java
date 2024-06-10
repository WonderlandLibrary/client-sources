/*   1:    */ package net.minecraft.village;
/*   2:    */ 
/*   3:    */ import net.minecraft.item.Item;
/*   4:    */ import net.minecraft.item.ItemStack;
/*   5:    */ import net.minecraft.nbt.NBTTagCompound;
/*   6:    */ 
/*   7:    */ public class MerchantRecipe
/*   8:    */ {
/*   9:    */   private ItemStack itemToBuy;
/*  10:    */   private ItemStack secondItemToBuy;
/*  11:    */   private ItemStack itemToSell;
/*  12:    */   private int toolUses;
/*  13:    */   private int maxTradeUses;
/*  14:    */   private static final String __OBFID = "CL_00000126";
/*  15:    */   
/*  16:    */   public MerchantRecipe(NBTTagCompound par1NBTTagCompound)
/*  17:    */   {
/*  18: 29 */     readFromTags(par1NBTTagCompound);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public MerchantRecipe(ItemStack par1ItemStack, ItemStack par2ItemStack, ItemStack par3ItemStack)
/*  22:    */   {
/*  23: 34 */     this.itemToBuy = par1ItemStack;
/*  24: 35 */     this.secondItemToBuy = par2ItemStack;
/*  25: 36 */     this.itemToSell = par3ItemStack;
/*  26: 37 */     this.maxTradeUses = 7;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public MerchantRecipe(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*  30:    */   {
/*  31: 42 */     this(par1ItemStack, null, par2ItemStack);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public MerchantRecipe(ItemStack par1ItemStack, Item par2Item)
/*  35:    */   {
/*  36: 47 */     this(par1ItemStack, new ItemStack(par2Item));
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ItemStack getItemToBuy()
/*  40:    */   {
/*  41: 55 */     return this.itemToBuy;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public ItemStack getSecondItemToBuy()
/*  45:    */   {
/*  46: 63 */     return this.secondItemToBuy;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean hasSecondItemToBuy()
/*  50:    */   {
/*  51: 71 */     return this.secondItemToBuy != null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public ItemStack getItemToSell()
/*  55:    */   {
/*  56: 79 */     return this.itemToSell;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean hasSameIDsAs(MerchantRecipe par1MerchantRecipe)
/*  60:    */   {
/*  61: 87 */     return ((this.secondItemToBuy == null) && (par1MerchantRecipe.secondItemToBuy == null)) || ((this.secondItemToBuy != null) && (par1MerchantRecipe.secondItemToBuy != null) && (this.secondItemToBuy.getItem() == par1MerchantRecipe.secondItemToBuy.getItem()));
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean hasSameItemsAs(MerchantRecipe par1MerchantRecipe)
/*  65:    */   {
/*  66: 95 */     return (hasSameIDsAs(par1MerchantRecipe)) && ((this.itemToBuy.stackSize < par1MerchantRecipe.itemToBuy.stackSize) || ((this.secondItemToBuy != null) && (this.secondItemToBuy.stackSize < par1MerchantRecipe.secondItemToBuy.stackSize)));
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void incrementToolUses()
/*  70:    */   {
/*  71:100 */     this.toolUses += 1;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void func_82783_a(int par1)
/*  75:    */   {
/*  76:105 */     this.maxTradeUses += par1;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean isRecipeDisabled()
/*  80:    */   {
/*  81:110 */     return this.toolUses >= this.maxTradeUses;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void func_82785_h()
/*  85:    */   {
/*  86:115 */     this.toolUses = this.maxTradeUses;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void readFromTags(NBTTagCompound par1NBTTagCompound)
/*  90:    */   {
/*  91:120 */     NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("buy");
/*  92:121 */     this.itemToBuy = ItemStack.loadItemStackFromNBT(var2);
/*  93:122 */     NBTTagCompound var3 = par1NBTTagCompound.getCompoundTag("sell");
/*  94:123 */     this.itemToSell = ItemStack.loadItemStackFromNBT(var3);
/*  95:125 */     if (par1NBTTagCompound.func_150297_b("buyB", 10)) {
/*  96:127 */       this.secondItemToBuy = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("buyB"));
/*  97:    */     }
/*  98:130 */     if (par1NBTTagCompound.func_150297_b("uses", 99)) {
/*  99:132 */       this.toolUses = par1NBTTagCompound.getInteger("uses");
/* 100:    */     }
/* 101:135 */     if (par1NBTTagCompound.func_150297_b("maxUses", 99)) {
/* 102:137 */       this.maxTradeUses = par1NBTTagCompound.getInteger("maxUses");
/* 103:    */     } else {
/* 104:141 */       this.maxTradeUses = 7;
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public NBTTagCompound writeToTags()
/* 109:    */   {
/* 110:147 */     NBTTagCompound var1 = new NBTTagCompound();
/* 111:148 */     var1.setTag("buy", this.itemToBuy.writeToNBT(new NBTTagCompound()));
/* 112:149 */     var1.setTag("sell", this.itemToSell.writeToNBT(new NBTTagCompound()));
/* 113:151 */     if (this.secondItemToBuy != null) {
/* 114:153 */       var1.setTag("buyB", this.secondItemToBuy.writeToNBT(new NBTTagCompound()));
/* 115:    */     }
/* 116:156 */     var1.setInteger("uses", this.toolUses);
/* 117:157 */     var1.setInteger("maxUses", this.maxTradeUses);
/* 118:158 */     return var1;
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.village.MerchantRecipe
 * JD-Core Version:    0.7.0.1
 */