/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   5:    */ import net.minecraft.entity.player.EntityPlayer;
/*   6:    */ import net.minecraft.nbt.NBTBase;
/*   7:    */ import net.minecraft.nbt.NBTTagCompound;
/*   8:    */ import net.minecraft.nbt.NBTTagIntArray;
/*   9:    */ import net.minecraft.util.IIcon;
/*  10:    */ import net.minecraft.util.StatCollector;
/*  11:    */ 
/*  12:    */ public class ItemFireworkCharge
/*  13:    */   extends Item
/*  14:    */ {
/*  15:    */   private IIcon field_150904_a;
/*  16:    */   private static final String __OBFID = "CL_00000030";
/*  17:    */   
/*  18:    */   public IIcon getIconFromDamageForRenderPass(int par1, int par2)
/*  19:    */   {
/*  20: 22 */     return par2 > 0 ? this.field_150904_a : super.getIconFromDamageForRenderPass(par1, par2);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
/*  24:    */   {
/*  25: 27 */     if (par2 != 1) {
/*  26: 29 */       return super.getColorFromItemStack(par1ItemStack, par2);
/*  27:    */     }
/*  28: 33 */     NBTBase var3 = func_150903_a(par1ItemStack, "Colors");
/*  29: 35 */     if ((var3 != null) && ((var3 instanceof NBTTagIntArray)))
/*  30:    */     {
/*  31: 37 */       NBTTagIntArray var4 = (NBTTagIntArray)var3;
/*  32: 38 */       int[] var5 = var4.func_150302_c();
/*  33: 40 */       if (var5.length == 1) {
/*  34: 42 */         return var5[0];
/*  35:    */       }
/*  36: 46 */       int var6 = 0;
/*  37: 47 */       int var7 = 0;
/*  38: 48 */       int var8 = 0;
/*  39: 49 */       int[] var9 = var5;
/*  40: 50 */       int var10 = var5.length;
/*  41: 52 */       for (int var11 = 0; var11 < var10; var11++)
/*  42:    */       {
/*  43: 54 */         int var12 = var9[var11];
/*  44: 55 */         var6 += ((var12 & 0xFF0000) >> 16);
/*  45: 56 */         var7 += ((var12 & 0xFF00) >> 8);
/*  46: 57 */         var8 += ((var12 & 0xFF) >> 0);
/*  47:    */       }
/*  48: 60 */       var6 /= var5.length;
/*  49: 61 */       var7 /= var5.length;
/*  50: 62 */       var8 /= var5.length;
/*  51: 63 */       return var6 << 16 | var7 << 8 | var8;
/*  52:    */     }
/*  53: 68 */     return 9079434;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean requiresMultipleRenderPasses()
/*  57:    */   {
/*  58: 75 */     return true;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static NBTBase func_150903_a(ItemStack p_150903_0_, String p_150903_1_)
/*  62:    */   {
/*  63: 80 */     if (p_150903_0_.hasTagCompound())
/*  64:    */     {
/*  65: 82 */       NBTTagCompound var2 = p_150903_0_.getTagCompound().getCompoundTag("Explosion");
/*  66: 84 */       if (var2 != null) {
/*  67: 86 */         return var2.getTag(p_150903_1_);
/*  68:    */       }
/*  69:    */     }
/*  70: 90 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/*  74:    */   {
/*  75: 98 */     if (par1ItemStack.hasTagCompound())
/*  76:    */     {
/*  77:100 */       NBTTagCompound var5 = par1ItemStack.getTagCompound().getCompoundTag("Explosion");
/*  78:102 */       if (var5 != null) {
/*  79:104 */         func_150902_a(var5, par3List);
/*  80:    */       }
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static void func_150902_a(NBTTagCompound p_150902_0_, List p_150902_1_)
/*  85:    */   {
/*  86:111 */     byte var2 = p_150902_0_.getByte("Type");
/*  87:113 */     if ((var2 >= 0) && (var2 <= 4)) {
/*  88:115 */       p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.type." + var2).trim());
/*  89:    */     } else {
/*  90:119 */       p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
/*  91:    */     }
/*  92:122 */     int[] var3 = p_150902_0_.getIntArray("Colors");
/*  93:126 */     if (var3.length > 0)
/*  94:    */     {
/*  95:128 */       boolean var4 = true;
/*  96:129 */       String var5 = "";
/*  97:130 */       int[] var6 = var3;
/*  98:131 */       int var7 = var3.length;
/*  99:133 */       for (int var8 = 0; var8 < var7; var8++)
/* 100:    */       {
/* 101:135 */         int var9 = var6[var8];
/* 102:137 */         if (!var4) {
/* 103:139 */           var5 = var5 + ", ";
/* 104:    */         }
/* 105:142 */         var4 = false;
/* 106:143 */         boolean var10 = false;
/* 107:145 */         for (int var11 = 0; var11 < 16; var11++) {
/* 108:147 */           if (var9 == ItemDye.field_150922_c[var11])
/* 109:    */           {
/* 110:149 */             var10 = true;
/* 111:150 */             var5 = var5 + StatCollector.translateToLocal(new StringBuilder("item.fireworksCharge.").append(ItemDye.field_150923_a[var11]).toString());
/* 112:151 */             break;
/* 113:    */           }
/* 114:    */         }
/* 115:155 */         if (!var10) {
/* 116:157 */           var5 = var5 + StatCollector.translateToLocal("item.fireworksCharge.customColor");
/* 117:    */         }
/* 118:    */       }
/* 119:161 */       p_150902_1_.add(var5);
/* 120:    */     }
/* 121:164 */     int[] var13 = p_150902_0_.getIntArray("FadeColors");
/* 122:167 */     if (var13.length > 0)
/* 123:    */     {
/* 124:169 */       boolean var16 = true;
/* 125:170 */       String var14 = StatCollector.translateToLocal("item.fireworksCharge.fadeTo") + " ";
/* 126:171 */       int[] var15 = var13;
/* 127:172 */       int var8 = var13.length;
/* 128:174 */       for (int var9 = 0; var9 < var8; var9++)
/* 129:    */       {
/* 130:176 */         int var18 = var15[var9];
/* 131:178 */         if (!var16) {
/* 132:180 */           var14 = var14 + ", ";
/* 133:    */         }
/* 134:183 */         var16 = false;
/* 135:184 */         boolean var19 = false;
/* 136:186 */         for (int var12 = 0; var12 < 16; var12++) {
/* 137:188 */           if (var18 == ItemDye.field_150922_c[var12])
/* 138:    */           {
/* 139:190 */             var19 = true;
/* 140:191 */             var14 = var14 + StatCollector.translateToLocal(new StringBuilder("item.fireworksCharge.").append(ItemDye.field_150923_a[var12]).toString());
/* 141:192 */             break;
/* 142:    */           }
/* 143:    */         }
/* 144:196 */         if (!var19) {
/* 145:198 */           var14 = var14 + StatCollector.translateToLocal("item.fireworksCharge.customColor");
/* 146:    */         }
/* 147:    */       }
/* 148:202 */       p_150902_1_.add(var14);
/* 149:    */     }
/* 150:205 */     boolean var16 = p_150902_0_.getBoolean("Trail");
/* 151:207 */     if (var16) {
/* 152:209 */       p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
/* 153:    */     }
/* 154:212 */     boolean var17 = p_150902_0_.getBoolean("Flicker");
/* 155:214 */     if (var17) {
/* 156:216 */       p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void registerIcons(IIconRegister par1IconRegister)
/* 161:    */   {
/* 162:222 */     super.registerIcons(par1IconRegister);
/* 163:223 */     this.field_150904_a = par1IconRegister.registerIcon(getIconString() + "_overlay");
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemFireworkCharge
 * JD-Core Version:    0.7.0.1
 */