/*   1:    */ package net.minecraft.item.crafting;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import net.minecraft.init.Items;
/*   5:    */ import net.minecraft.inventory.InventoryCrafting;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.nbt.NBTTagCompound;
/*   8:    */ import net.minecraft.nbt.NBTTagList;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class RecipeFireworks
/*  12:    */   implements IRecipe
/*  13:    */ {
/*  14:    */   private ItemStack field_92102_a;
/*  15:    */   private static final String __OBFID = "CL_00000083";
/*  16:    */   
/*  17:    */   public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
/*  18:    */   {
/*  19: 22 */     this.field_92102_a = null;
/*  20: 23 */     int var3 = 0;
/*  21: 24 */     int var4 = 0;
/*  22: 25 */     int var5 = 0;
/*  23: 26 */     int var6 = 0;
/*  24: 27 */     int var7 = 0;
/*  25: 28 */     int var8 = 0;
/*  26: 30 */     for (int var9 = 0; var9 < par1InventoryCrafting.getSizeInventory(); var9++)
/*  27:    */     {
/*  28: 32 */       ItemStack var10 = par1InventoryCrafting.getStackInSlot(var9);
/*  29: 34 */       if (var10 != null) {
/*  30: 36 */         if (var10.getItem() == Items.gunpowder)
/*  31:    */         {
/*  32: 38 */           var4++;
/*  33:    */         }
/*  34: 40 */         else if (var10.getItem() == Items.firework_charge)
/*  35:    */         {
/*  36: 42 */           var6++;
/*  37:    */         }
/*  38: 44 */         else if (var10.getItem() == Items.dye)
/*  39:    */         {
/*  40: 46 */           var5++;
/*  41:    */         }
/*  42: 48 */         else if (var10.getItem() == Items.paper)
/*  43:    */         {
/*  44: 50 */           var3++;
/*  45:    */         }
/*  46: 52 */         else if (var10.getItem() == Items.glowstone_dust)
/*  47:    */         {
/*  48: 54 */           var7++;
/*  49:    */         }
/*  50: 56 */         else if (var10.getItem() == Items.diamond)
/*  51:    */         {
/*  52: 58 */           var7++;
/*  53:    */         }
/*  54: 60 */         else if (var10.getItem() == Items.fire_charge)
/*  55:    */         {
/*  56: 62 */           var8++;
/*  57:    */         }
/*  58: 64 */         else if (var10.getItem() == Items.feather)
/*  59:    */         {
/*  60: 66 */           var8++;
/*  61:    */         }
/*  62: 68 */         else if (var10.getItem() == Items.gold_nugget)
/*  63:    */         {
/*  64: 70 */           var8++;
/*  65:    */         }
/*  66:    */         else
/*  67:    */         {
/*  68: 74 */           if (var10.getItem() != Items.skull) {
/*  69: 76 */             return false;
/*  70:    */           }
/*  71: 79 */           var8++;
/*  72:    */         }
/*  73:    */       }
/*  74:    */     }
/*  75: 84 */     var7 += var5 + var8;
/*  76: 86 */     if ((var4 <= 3) && (var3 <= 1))
/*  77:    */     {
/*  78: 91 */       if ((var4 >= 1) && (var3 == 1) && (var7 == 0))
/*  79:    */       {
/*  80: 93 */         this.field_92102_a = new ItemStack(Items.fireworks);
/*  81: 95 */         if (var6 > 0)
/*  82:    */         {
/*  83: 97 */           NBTTagCompound var15 = new NBTTagCompound();
/*  84: 98 */           NBTTagCompound var18 = new NBTTagCompound();
/*  85: 99 */           NBTTagList var25 = new NBTTagList();
/*  86:101 */           for (int var22 = 0; var22 < par1InventoryCrafting.getSizeInventory(); var22++)
/*  87:    */           {
/*  88:103 */             ItemStack var26 = par1InventoryCrafting.getStackInSlot(var22);
/*  89:105 */             if ((var26 != null) && (var26.getItem() == Items.firework_charge) && (var26.hasTagCompound()) && (var26.getTagCompound().func_150297_b("Explosion", 10))) {
/*  90:107 */               var25.appendTag(var26.getTagCompound().getCompoundTag("Explosion"));
/*  91:    */             }
/*  92:    */           }
/*  93:111 */           var18.setTag("Explosions", var25);
/*  94:112 */           var18.setByte("Flight", (byte)var4);
/*  95:113 */           var15.setTag("Fireworks", var18);
/*  96:114 */           this.field_92102_a.setTagCompound(var15);
/*  97:    */         }
/*  98:117 */         return true;
/*  99:    */       }
/* 100:119 */       if ((var4 == 1) && (var3 == 0) && (var6 == 0) && (var5 > 0) && (var8 <= 1))
/* 101:    */       {
/* 102:121 */         this.field_92102_a = new ItemStack(Items.firework_charge);
/* 103:122 */         NBTTagCompound var15 = new NBTTagCompound();
/* 104:123 */         NBTTagCompound var18 = new NBTTagCompound();
/* 105:124 */         byte var21 = 0;
/* 106:125 */         ArrayList var12 = new ArrayList();
/* 107:127 */         for (int var13 = 0; var13 < par1InventoryCrafting.getSizeInventory(); var13++)
/* 108:    */         {
/* 109:129 */           ItemStack var14 = par1InventoryCrafting.getStackInSlot(var13);
/* 110:131 */           if (var14 != null) {
/* 111:133 */             if (var14.getItem() == Items.dye) {
/* 112:135 */               var12.add(Integer.valueOf(net.minecraft.item.ItemDye.field_150922_c[var14.getItemDamage()]));
/* 113:137 */             } else if (var14.getItem() == Items.glowstone_dust) {
/* 114:139 */               var18.setBoolean("Flicker", true);
/* 115:141 */             } else if (var14.getItem() == Items.diamond) {
/* 116:143 */               var18.setBoolean("Trail", true);
/* 117:145 */             } else if (var14.getItem() == Items.fire_charge) {
/* 118:147 */               var21 = 1;
/* 119:149 */             } else if (var14.getItem() == Items.feather) {
/* 120:151 */               var21 = 4;
/* 121:153 */             } else if (var14.getItem() == Items.gold_nugget) {
/* 122:155 */               var21 = 2;
/* 123:157 */             } else if (var14.getItem() == Items.skull) {
/* 124:159 */               var21 = 3;
/* 125:    */             }
/* 126:    */           }
/* 127:    */         }
/* 128:164 */         int[] var24 = new int[var12.size()];
/* 129:166 */         for (int var27 = 0; var27 < var24.length; var27++) {
/* 130:168 */           var24[var27] = ((Integer)var12.get(var27)).intValue();
/* 131:    */         }
/* 132:171 */         var18.setIntArray("Colors", var24);
/* 133:172 */         var18.setByte("Type", var21);
/* 134:173 */         var15.setTag("Explosion", var18);
/* 135:174 */         this.field_92102_a.setTagCompound(var15);
/* 136:175 */         return true;
/* 137:    */       }
/* 138:177 */       if ((var4 == 0) && (var3 == 0) && (var6 == 1) && (var5 > 0) && (var5 == var7))
/* 139:    */       {
/* 140:179 */         ArrayList var16 = new ArrayList();
/* 141:181 */         for (int var20 = 0; var20 < par1InventoryCrafting.getSizeInventory(); var20++)
/* 142:    */         {
/* 143:183 */           ItemStack var11 = par1InventoryCrafting.getStackInSlot(var20);
/* 144:185 */           if (var11 != null) {
/* 145:187 */             if (var11.getItem() == Items.dye)
/* 146:    */             {
/* 147:189 */               var16.add(Integer.valueOf(net.minecraft.item.ItemDye.field_150922_c[var11.getItemDamage()]));
/* 148:    */             }
/* 149:191 */             else if (var11.getItem() == Items.firework_charge)
/* 150:    */             {
/* 151:193 */               this.field_92102_a = var11.copy();
/* 152:194 */               this.field_92102_a.stackSize = 1;
/* 153:    */             }
/* 154:    */           }
/* 155:    */         }
/* 156:199 */         int[] var17 = new int[var16.size()];
/* 157:201 */         for (int var19 = 0; var19 < var17.length; var19++) {
/* 158:203 */           var17[var19] = ((Integer)var16.get(var19)).intValue();
/* 159:    */         }
/* 160:206 */         if ((this.field_92102_a != null) && (this.field_92102_a.hasTagCompound()))
/* 161:    */         {
/* 162:208 */           NBTTagCompound var23 = this.field_92102_a.getTagCompound().getCompoundTag("Explosion");
/* 163:210 */           if (var23 == null) {
/* 164:212 */             return false;
/* 165:    */           }
/* 166:216 */           var23.setIntArray("FadeColors", var17);
/* 167:217 */           return true;
/* 168:    */         }
/* 169:222 */         return false;
/* 170:    */       }
/* 171:227 */       return false;
/* 172:    */     }
/* 173:232 */     return false;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
/* 177:    */   {
/* 178:241 */     return this.field_92102_a.copy();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int getRecipeSize()
/* 182:    */   {
/* 183:249 */     return 10;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public ItemStack getRecipeOutput()
/* 187:    */   {
/* 188:254 */     return this.field_92102_a;
/* 189:    */   }
/* 190:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.RecipeFireworks
 * JD-Core Version:    0.7.0.1
 */