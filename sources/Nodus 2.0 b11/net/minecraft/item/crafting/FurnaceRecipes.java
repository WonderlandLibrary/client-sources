/*   1:    */ package net.minecraft.item.crafting;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.init.Items;
/*  11:    */ import net.minecraft.item.Item;
/*  12:    */ import net.minecraft.item.ItemFishFood.FishType;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ 
/*  15:    */ public class FurnaceRecipes
/*  16:    */ {
/*  17: 16 */   private static final FurnaceRecipes smeltingBase = new FurnaceRecipes();
/*  18: 19 */   private Map smeltingList = new HashMap();
/*  19: 20 */   private Map experienceList = new HashMap();
/*  20:    */   private static final String __OBFID = "CL_00000085";
/*  21:    */   
/*  22:    */   public static FurnaceRecipes smelting()
/*  23:    */   {
/*  24: 28 */     return smeltingBase;
/*  25:    */   }
/*  26:    */   
/*  27:    */   private FurnaceRecipes()
/*  28:    */   {
/*  29: 33 */     func_151393_a(Blocks.iron_ore, new ItemStack(Items.iron_ingot), 0.7F);
/*  30: 34 */     func_151393_a(Blocks.gold_ore, new ItemStack(Items.gold_ingot), 1.0F);
/*  31: 35 */     func_151393_a(Blocks.diamond_ore, new ItemStack(Items.diamond), 1.0F);
/*  32: 36 */     func_151393_a(Blocks.sand, new ItemStack(Blocks.glass), 0.1F);
/*  33: 37 */     func_151396_a(Items.porkchop, new ItemStack(Items.cooked_porkchop), 0.35F);
/*  34: 38 */     func_151396_a(Items.beef, new ItemStack(Items.cooked_beef), 0.35F);
/*  35: 39 */     func_151396_a(Items.chicken, new ItemStack(Items.cooked_chicken), 0.35F);
/*  36: 40 */     func_151393_a(Blocks.cobblestone, new ItemStack(Blocks.stone), 0.1F);
/*  37: 41 */     func_151396_a(Items.clay_ball, new ItemStack(Items.brick), 0.3F);
/*  38: 42 */     func_151393_a(Blocks.clay, new ItemStack(Blocks.hardened_clay), 0.35F);
/*  39: 43 */     func_151393_a(Blocks.cactus, new ItemStack(Items.dye, 1, 2), 0.2F);
/*  40: 44 */     func_151393_a(Blocks.log, new ItemStack(Items.coal, 1, 1), 0.15F);
/*  41: 45 */     func_151393_a(Blocks.log2, new ItemStack(Items.coal, 1, 1), 0.15F);
/*  42: 46 */     func_151393_a(Blocks.emerald_ore, new ItemStack(Items.emerald), 1.0F);
/*  43: 47 */     func_151396_a(Items.potato, new ItemStack(Items.baked_potato), 0.35F);
/*  44: 48 */     func_151393_a(Blocks.netherrack, new ItemStack(Items.netherbrick), 0.1F);
/*  45: 49 */     ItemFishFood.FishType[] var1 = ItemFishFood.FishType.values();
/*  46: 50 */     int var2 = var1.length;
/*  47: 52 */     for (int var3 = 0; var3 < var2; var3++)
/*  48:    */     {
/*  49: 54 */       ItemFishFood.FishType var4 = var1[var3];
/*  50: 56 */       if (var4.func_150973_i()) {
/*  51: 58 */         func_151394_a(new ItemStack(Items.fish, 1, var4.func_150976_a()), new ItemStack(Items.cooked_fished, 1, var4.func_150976_a()), 0.35F);
/*  52:    */       }
/*  53:    */     }
/*  54: 62 */     func_151393_a(Blocks.coal_ore, new ItemStack(Items.coal), 0.1F);
/*  55: 63 */     func_151393_a(Blocks.redstone_ore, new ItemStack(Items.redstone), 0.7F);
/*  56: 64 */     func_151393_a(Blocks.lapis_ore, new ItemStack(Items.dye, 1, 4), 0.2F);
/*  57: 65 */     func_151393_a(Blocks.quartz_ore, new ItemStack(Items.quartz), 0.2F);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void func_151393_a(Block p_151393_1_, ItemStack p_151393_2_, float p_151393_3_)
/*  61:    */   {
/*  62: 70 */     func_151396_a(Item.getItemFromBlock(p_151393_1_), p_151393_2_, p_151393_3_);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void func_151396_a(Item p_151396_1_, ItemStack p_151396_2_, float p_151396_3_)
/*  66:    */   {
/*  67: 75 */     func_151394_a(new ItemStack(p_151396_1_, 1, 32767), p_151396_2_, p_151396_3_);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void func_151394_a(ItemStack p_151394_1_, ItemStack p_151394_2_, float p_151394_3_)
/*  71:    */   {
/*  72: 80 */     this.smeltingList.put(p_151394_1_, p_151394_2_);
/*  73: 81 */     this.experienceList.put(p_151394_2_, Float.valueOf(p_151394_3_));
/*  74:    */   }
/*  75:    */   
/*  76:    */   public ItemStack func_151395_a(ItemStack p_151395_1_)
/*  77:    */   {
/*  78: 86 */     Iterator var2 = this.smeltingList.entrySet().iterator();
/*  79:    */     Map.Entry var3;
/*  80:    */     do
/*  81:    */     {
/*  82: 91 */       if (!var2.hasNext()) {
/*  83: 93 */         return null;
/*  84:    */       }
/*  85: 96 */       var3 = (Map.Entry)var2.next();
/*  86: 98 */     } while (!func_151397_a(p_151395_1_, (ItemStack)var3.getKey()));
/*  87:100 */     return (ItemStack)var3.getValue();
/*  88:    */   }
/*  89:    */   
/*  90:    */   private boolean func_151397_a(ItemStack p_151397_1_, ItemStack p_151397_2_)
/*  91:    */   {
/*  92:105 */     return (p_151397_2_.getItem() == p_151397_1_.getItem()) && ((p_151397_2_.getItemDamage() == 32767) || (p_151397_2_.getItemDamage() == p_151397_1_.getItemDamage()));
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Map getSmeltingList()
/*  96:    */   {
/*  97:110 */     return this.smeltingList;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public float func_151398_b(ItemStack p_151398_1_)
/* 101:    */   {
/* 102:115 */     Iterator var2 = this.experienceList.entrySet().iterator();
/* 103:    */     Map.Entry var3;
/* 104:    */     do
/* 105:    */     {
/* 106:120 */       if (!var2.hasNext()) {
/* 107:122 */         return 0.0F;
/* 108:    */       }
/* 109:125 */       var3 = (Map.Entry)var2.next();
/* 110:127 */     } while (!func_151397_a(p_151398_1_, (ItemStack)var3.getKey()));
/* 111:129 */     return ((Float)var3.getValue()).floatValue();
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.FurnaceRecipes
 * JD-Core Version:    0.7.0.1
 */