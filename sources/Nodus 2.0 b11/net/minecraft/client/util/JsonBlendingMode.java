/*   1:    */ package net.minecraft.client.util;
/*   2:    */ 
/*   3:    */ import com.google.gson.JsonElement;
/*   4:    */ import com.google.gson.JsonObject;
/*   5:    */ import net.minecraft.util.JsonUtils;
/*   6:    */ import org.lwjgl.opengl.GL11;
/*   7:    */ import org.lwjgl.opengl.GL14;
/*   8:    */ 
/*   9:    */ public class JsonBlendingMode
/*  10:    */ {
/*  11: 10 */   private static JsonBlendingMode field_148118_a = null;
/*  12:    */   private final int field_148116_b;
/*  13:    */   private final int field_148117_c;
/*  14:    */   private final int field_148114_d;
/*  15:    */   private final int field_148115_e;
/*  16:    */   private final int field_148112_f;
/*  17:    */   private final boolean field_148113_g;
/*  18:    */   private final boolean field_148119_h;
/*  19:    */   private static final String __OBFID = "CL_00001038";
/*  20:    */   
/*  21:    */   private JsonBlendingMode(boolean p_i45084_1_, boolean p_i45084_2_, int p_i45084_3_, int p_i45084_4_, int p_i45084_5_, int p_i45084_6_, int p_i45084_7_)
/*  22:    */   {
/*  23: 22 */     this.field_148113_g = p_i45084_1_;
/*  24: 23 */     this.field_148116_b = p_i45084_3_;
/*  25: 24 */     this.field_148114_d = p_i45084_4_;
/*  26: 25 */     this.field_148117_c = p_i45084_5_;
/*  27: 26 */     this.field_148115_e = p_i45084_6_;
/*  28: 27 */     this.field_148119_h = p_i45084_2_;
/*  29: 28 */     this.field_148112_f = p_i45084_7_;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public JsonBlendingMode()
/*  33:    */   {
/*  34: 33 */     this(false, true, 1, 0, 1, 0, 32774);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public JsonBlendingMode(int p_i45085_1_, int p_i45085_2_, int p_i45085_3_)
/*  38:    */   {
/*  39: 38 */     this(false, false, p_i45085_1_, p_i45085_2_, p_i45085_1_, p_i45085_2_, p_i45085_3_);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public JsonBlendingMode(int p_i45086_1_, int p_i45086_2_, int p_i45086_3_, int p_i45086_4_, int p_i45086_5_)
/*  43:    */   {
/*  44: 43 */     this(true, false, p_i45086_1_, p_i45086_2_, p_i45086_3_, p_i45086_4_, p_i45086_5_);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void func_148109_a()
/*  48:    */   {
/*  49: 48 */     if (!equals(field_148118_a))
/*  50:    */     {
/*  51: 50 */       if ((field_148118_a == null) || (this.field_148119_h != field_148118_a.func_148111_b()))
/*  52:    */       {
/*  53: 52 */         field_148118_a = this;
/*  54: 54 */         if (this.field_148119_h)
/*  55:    */         {
/*  56: 56 */           GL11.glDisable(3042);
/*  57: 57 */           return;
/*  58:    */         }
/*  59: 60 */         GL11.glEnable(3042);
/*  60:    */       }
/*  61: 63 */       GL14.glBlendEquation(this.field_148112_f);
/*  62: 65 */       if (this.field_148113_g) {
/*  63: 67 */         GL14.glBlendFuncSeparate(this.field_148116_b, this.field_148114_d, this.field_148117_c, this.field_148115_e);
/*  64:    */       } else {
/*  65: 71 */         GL11.glBlendFunc(this.field_148116_b, this.field_148114_d);
/*  66:    */       }
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean equals(Object par1Obj)
/*  71:    */   {
/*  72: 78 */     if (this == par1Obj) {
/*  73: 80 */       return true;
/*  74:    */     }
/*  75: 82 */     if (!(par1Obj instanceof JsonBlendingMode)) {
/*  76: 84 */       return false;
/*  77:    */     }
/*  78: 88 */     JsonBlendingMode var2 = (JsonBlendingMode)par1Obj;
/*  79: 89 */     return this.field_148112_f == var2.field_148112_f;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int hashCode()
/*  83:    */   {
/*  84: 95 */     int var1 = this.field_148116_b;
/*  85: 96 */     var1 = 31 * var1 + this.field_148117_c;
/*  86: 97 */     var1 = 31 * var1 + this.field_148114_d;
/*  87: 98 */     var1 = 31 * var1 + this.field_148115_e;
/*  88: 99 */     var1 = 31 * var1 + this.field_148112_f;
/*  89:100 */     var1 = 31 * var1 + (this.field_148113_g ? 1 : 0);
/*  90:101 */     var1 = 31 * var1 + (this.field_148119_h ? 1 : 0);
/*  91:102 */     return var1;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean func_148111_b()
/*  95:    */   {
/*  96:107 */     return this.field_148119_h;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static JsonBlendingMode func_148110_a(JsonObject p_148110_0_)
/* 100:    */   {
/* 101:112 */     if (p_148110_0_ == null) {
/* 102:114 */       return new JsonBlendingMode();
/* 103:    */     }
/* 104:118 */     int var1 = 32774;
/* 105:119 */     int var2 = 1;
/* 106:120 */     int var3 = 0;
/* 107:121 */     int var4 = 1;
/* 108:122 */     int var5 = 0;
/* 109:123 */     boolean var6 = true;
/* 110:124 */     boolean var7 = false;
/* 111:126 */     if (JsonUtils.jsonObjectFieldTypeIsString(p_148110_0_, "func"))
/* 112:    */     {
/* 113:128 */       var1 = func_148108_a(p_148110_0_.get("func").getAsString());
/* 114:130 */       if (var1 != 32774) {
/* 115:132 */         var6 = false;
/* 116:    */       }
/* 117:    */     }
/* 118:136 */     if (JsonUtils.jsonObjectFieldTypeIsString(p_148110_0_, "srcrgb"))
/* 119:    */     {
/* 120:138 */       var2 = func_148107_b(p_148110_0_.get("srcrgb").getAsString());
/* 121:140 */       if (var2 != 1) {
/* 122:142 */         var6 = false;
/* 123:    */       }
/* 124:    */     }
/* 125:146 */     if (JsonUtils.jsonObjectFieldTypeIsString(p_148110_0_, "dstrgb"))
/* 126:    */     {
/* 127:148 */       var3 = func_148107_b(p_148110_0_.get("dstrgb").getAsString());
/* 128:150 */       if (var3 != 0) {
/* 129:152 */         var6 = false;
/* 130:    */       }
/* 131:    */     }
/* 132:156 */     if (JsonUtils.jsonObjectFieldTypeIsString(p_148110_0_, "srcalpha"))
/* 133:    */     {
/* 134:158 */       var4 = func_148107_b(p_148110_0_.get("srcalpha").getAsString());
/* 135:160 */       if (var4 != 1) {
/* 136:162 */         var6 = false;
/* 137:    */       }
/* 138:165 */       var7 = true;
/* 139:    */     }
/* 140:168 */     if (JsonUtils.jsonObjectFieldTypeIsString(p_148110_0_, "dstalpha"))
/* 141:    */     {
/* 142:170 */       var5 = func_148107_b(p_148110_0_.get("dstalpha").getAsString());
/* 143:172 */       if (var5 != 0) {
/* 144:174 */         var6 = false;
/* 145:    */       }
/* 146:177 */       var7 = true;
/* 147:    */     }
/* 148:180 */     return var7 ? new JsonBlendingMode(var2, var3, var4, var5, var1) : var6 ? new JsonBlendingMode() : new JsonBlendingMode(var2, var3, var1);
/* 149:    */   }
/* 150:    */   
/* 151:    */   private static int func_148108_a(String p_148108_0_)
/* 152:    */   {
/* 153:186 */     String var1 = p_148108_0_.trim().toLowerCase();
/* 154:187 */     return var1.equals("max") ? 32776 : var1.equals("min") ? 32775 : var1.equals("reverse_subtract") ? 32779 : var1.equals("reversesubtract") ? 32779 : var1.equals("subtract") ? 32778 : var1.equals("add") ? 32774 : 32774;
/* 155:    */   }
/* 156:    */   
/* 157:    */   private static int func_148107_b(String p_148107_0_)
/* 158:    */   {
/* 159:192 */     String var1 = p_148107_0_.trim().toLowerCase();
/* 160:193 */     var1 = var1.replaceAll("_", "");
/* 161:194 */     var1 = var1.replaceAll("one", "1");
/* 162:195 */     var1 = var1.replaceAll("zero", "0");
/* 163:196 */     var1 = var1.replaceAll("minus", "-");
/* 164:197 */     return var1.equals("1-dstalpha") ? 773 : var1.equals("dstalpha") ? 772 : var1.equals("1-srcalpha") ? 771 : var1.equals("srcalpha") ? 770 : var1.equals("1-dstcolor") ? 775 : var1.equals("dstcolor") ? 774 : var1.equals("1-srccolor") ? 769 : var1.equals("srccolor") ? 768 : var1.equals("1") ? 1 : var1.equals("0") ? 0 : -1;
/* 165:    */   }
/* 166:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.util.JsonBlendingMode
 * JD-Core Version:    0.7.0.1
 */