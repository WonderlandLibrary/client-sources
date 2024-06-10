/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.inventory.IInventory;
/*   5:    */ import net.minecraft.item.Item;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.tileentity.TileEntityDispenser;
/*   8:    */ 
/*   9:    */ public class WeightedRandomChestContent
/*  10:    */   extends WeightedRandom.Item
/*  11:    */ {
/*  12:    */   private ItemStack theItemId;
/*  13:    */   private int theMinimumChanceToGenerateItem;
/*  14:    */   private int theMaximumChanceToGenerateItem;
/*  15:    */   private static final String __OBFID = "CL_00001505";
/*  16:    */   
/*  17:    */   public WeightedRandomChestContent(Item p_i45311_1_, int p_i45311_2_, int p_i45311_3_, int p_i45311_4_, int p_i45311_5_)
/*  18:    */   {
/*  19: 23 */     super(p_i45311_5_);
/*  20: 24 */     this.theItemId = new ItemStack(p_i45311_1_, 1, p_i45311_2_);
/*  21: 25 */     this.theMinimumChanceToGenerateItem = p_i45311_3_;
/*  22: 26 */     this.theMaximumChanceToGenerateItem = p_i45311_4_;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public WeightedRandomChestContent(ItemStack par1ItemStack, int par2, int par3, int par4)
/*  26:    */   {
/*  27: 31 */     super(par4);
/*  28: 32 */     this.theItemId = par1ItemStack;
/*  29: 33 */     this.theMinimumChanceToGenerateItem = par2;
/*  30: 34 */     this.theMaximumChanceToGenerateItem = par3;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static void generateChestContents(Random par0Random, WeightedRandomChestContent[] par1ArrayOfWeightedRandomChestContent, IInventory par2IInventory, int par3)
/*  34:    */   {
/*  35: 42 */     for (int var4 = 0; var4 < par3; var4++)
/*  36:    */     {
/*  37: 44 */       WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.getRandomItem(par0Random, par1ArrayOfWeightedRandomChestContent);
/*  38: 45 */       int var6 = var5.theMinimumChanceToGenerateItem + par0Random.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);
/*  39: 47 */       if (var5.theItemId.getMaxStackSize() >= var6)
/*  40:    */       {
/*  41: 49 */         ItemStack var7 = var5.theItemId.copy();
/*  42: 50 */         var7.stackSize = var6;
/*  43: 51 */         par2IInventory.setInventorySlotContents(par0Random.nextInt(par2IInventory.getSizeInventory()), var7);
/*  44:    */       }
/*  45:    */       else
/*  46:    */       {
/*  47: 55 */         for (int var9 = 0; var9 < var6; var9++)
/*  48:    */         {
/*  49: 57 */           ItemStack var8 = var5.theItemId.copy();
/*  50: 58 */           var8.stackSize = 1;
/*  51: 59 */           par2IInventory.setInventorySlotContents(par0Random.nextInt(par2IInventory.getSizeInventory()), var8);
/*  52:    */         }
/*  53:    */       }
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static void func_150706_a(Random p_150706_0_, WeightedRandomChestContent[] p_150706_1_, TileEntityDispenser p_150706_2_, int p_150706_3_)
/*  58:    */   {
/*  59: 67 */     for (int var4 = 0; var4 < p_150706_3_; var4++)
/*  60:    */     {
/*  61: 69 */       WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.getRandomItem(p_150706_0_, p_150706_1_);
/*  62: 70 */       int var6 = var5.theMinimumChanceToGenerateItem + p_150706_0_.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);
/*  63: 72 */       if (var5.theItemId.getMaxStackSize() >= var6)
/*  64:    */       {
/*  65: 74 */         ItemStack var7 = var5.theItemId.copy();
/*  66: 75 */         var7.stackSize = var6;
/*  67: 76 */         p_150706_2_.setInventorySlotContents(p_150706_0_.nextInt(p_150706_2_.getSizeInventory()), var7);
/*  68:    */       }
/*  69:    */       else
/*  70:    */       {
/*  71: 80 */         for (int var9 = 0; var9 < var6; var9++)
/*  72:    */         {
/*  73: 82 */           ItemStack var8 = var5.theItemId.copy();
/*  74: 83 */           var8.stackSize = 1;
/*  75: 84 */           p_150706_2_.setInventorySlotContents(p_150706_0_.nextInt(p_150706_2_.getSizeInventory()), var8);
/*  76:    */         }
/*  77:    */       }
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static WeightedRandomChestContent[] func_92080_a(WeightedRandomChestContent[] par0ArrayOfWeightedRandomChestContent, WeightedRandomChestContent... par1ArrayOfWeightedRandomChestContent)
/*  82:    */   {
/*  83: 92 */     WeightedRandomChestContent[] var2 = new WeightedRandomChestContent[par0ArrayOfWeightedRandomChestContent.length + par1ArrayOfWeightedRandomChestContent.length];
/*  84: 93 */     int var3 = 0;
/*  85: 95 */     for (int var4 = 0; var4 < par0ArrayOfWeightedRandomChestContent.length; var4++) {
/*  86: 97 */       var2[(var3++)] = par0ArrayOfWeightedRandomChestContent[var4];
/*  87:    */     }
/*  88:100 */     WeightedRandomChestContent[] var8 = par1ArrayOfWeightedRandomChestContent;
/*  89:101 */     int var5 = par1ArrayOfWeightedRandomChestContent.length;
/*  90:103 */     for (int var6 = 0; var6 < var5; var6++)
/*  91:    */     {
/*  92:105 */       WeightedRandomChestContent var7 = var8[var6];
/*  93:106 */       var2[(var3++)] = var7;
/*  94:    */     }
/*  95:109 */     return var2;
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.WeightedRandomChestContent
 * JD-Core Version:    0.7.0.1
 */