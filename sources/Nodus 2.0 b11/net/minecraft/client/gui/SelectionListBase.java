/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.Minecraft;
/*   4:    */ import net.minecraft.client.renderer.Tessellator;
/*   5:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*   6:    */ import net.minecraft.client.settings.GameSettings;
/*   7:    */ import org.lwjgl.input.Mouse;
/*   8:    */ import org.lwjgl.opengl.GL11;
/*   9:    */ 
/*  10:    */ public abstract class SelectionListBase
/*  11:    */ {
/*  12:    */   private final Minecraft field_148456_a;
/*  13:    */   private final int field_148453_e;
/*  14:    */   private final int field_148450_f;
/*  15:    */   private final int field_148451_g;
/*  16:    */   private final int field_148461_h;
/*  17:    */   protected final int field_148454_b;
/*  18:    */   protected int field_148455_c;
/*  19:    */   protected int field_148452_d;
/*  20: 18 */   private float field_148462_i = -2.0F;
/*  21:    */   private float field_148459_j;
/*  22:    */   private float field_148460_k;
/*  23: 21 */   private int field_148457_l = -1;
/*  24:    */   private long field_148458_m;
/*  25:    */   private static final String __OBFID = "CL_00000789";
/*  26:    */   
/*  27:    */   public SelectionListBase(Minecraft par1Minecraft, int par2, int par3, int par4, int par5, int par6)
/*  28:    */   {
/*  29: 27 */     this.field_148456_a = par1Minecraft;
/*  30: 28 */     this.field_148450_f = par3;
/*  31: 29 */     this.field_148461_h = (par3 + par5);
/*  32: 30 */     this.field_148454_b = par6;
/*  33: 31 */     this.field_148453_e = par2;
/*  34: 32 */     this.field_148451_g = (par2 + par4);
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected abstract int func_148443_a();
/*  38:    */   
/*  39:    */   protected abstract void func_148449_a(int paramInt, boolean paramBoolean);
/*  40:    */   
/*  41:    */   protected abstract boolean func_148444_a(int paramInt);
/*  42:    */   
/*  43:    */   protected int func_148447_b()
/*  44:    */   {
/*  45: 43 */     return func_148443_a() * this.field_148454_b;
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected abstract void func_148445_c();
/*  49:    */   
/*  50:    */   protected abstract void func_148442_a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Tessellator paramTessellator);
/*  51:    */   
/*  52:    */   private void func_148448_f()
/*  53:    */   {
/*  54: 52 */     int var1 = func_148441_d();
/*  55: 54 */     if (var1 < 0) {
/*  56: 56 */       var1 = 0;
/*  57:    */     }
/*  58: 59 */     if (this.field_148460_k < 0.0F) {
/*  59: 61 */       this.field_148460_k = 0.0F;
/*  60:    */     }
/*  61: 64 */     if (this.field_148460_k > var1) {
/*  62: 66 */       this.field_148460_k = var1;
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int func_148441_d()
/*  67:    */   {
/*  68: 72 */     return func_148447_b() - (this.field_148461_h - this.field_148450_f - 4);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void func_148446_a(int p_148446_1_, int p_148446_2_, float p_148446_3_)
/*  72:    */   {
/*  73: 77 */     this.field_148455_c = p_148446_1_;
/*  74: 78 */     this.field_148452_d = p_148446_2_;
/*  75: 79 */     func_148445_c();
/*  76: 80 */     int var4 = func_148443_a();
/*  77: 81 */     int var5 = func_148440_e();
/*  78: 82 */     int var6 = var5 + 6;
/*  79: 89 */     if (Mouse.isButtonDown(0))
/*  80:    */     {
/*  81: 91 */       if (this.field_148462_i == -1.0F)
/*  82:    */       {
/*  83: 93 */         var7 = true;
/*  84: 95 */         if ((p_148446_2_ >= this.field_148450_f) && (p_148446_2_ <= this.field_148461_h))
/*  85:    */         {
/*  86: 97 */           var8 = this.field_148453_e + 2;
/*  87: 98 */           var9 = this.field_148451_g - 2;
/*  88: 99 */           var10 = p_148446_2_ - this.field_148450_f + (int)this.field_148460_k - 4;
/*  89:100 */           var11 = var10 / this.field_148454_b;
/*  90:102 */           if ((p_148446_1_ >= var8) && (p_148446_1_ <= var9) && (var11 >= 0) && (var10 >= 0) && (var11 < var4))
/*  91:    */           {
/*  92:104 */             var12 = (var11 == this.field_148457_l) && (Minecraft.getSystemTime() - this.field_148458_m < 250L);
/*  93:105 */             func_148449_a(var11, var12);
/*  94:106 */             this.field_148457_l = var11;
/*  95:107 */             this.field_148458_m = Minecraft.getSystemTime();
/*  96:    */           }
/*  97:109 */           else if ((p_148446_1_ >= var8) && (p_148446_1_ <= var9) && (var10 < 0))
/*  98:    */           {
/*  99:111 */             var7 = false;
/* 100:    */           }
/* 101:114 */           if ((p_148446_1_ >= var5) && (p_148446_1_ <= var6))
/* 102:    */           {
/* 103:116 */             this.field_148459_j = -1.0F;
/* 104:117 */             var19 = func_148441_d();
/* 105:119 */             if (var19 < 1) {
/* 106:121 */               var19 = 1;
/* 107:    */             }
/* 108:124 */             var13 = (int)((this.field_148461_h - this.field_148450_f) * (this.field_148461_h - this.field_148450_f) / func_148447_b());
/* 109:126 */             if (var13 < 32) {
/* 110:128 */               var13 = 32;
/* 111:    */             }
/* 112:131 */             if (var13 > this.field_148461_h - this.field_148450_f - 8) {
/* 113:133 */               var13 = this.field_148461_h - this.field_148450_f - 8;
/* 114:    */             }
/* 115:136 */             this.field_148459_j /= (this.field_148461_h - this.field_148450_f - var13) / var19;
/* 116:    */           }
/* 117:    */           else
/* 118:    */           {
/* 119:140 */             this.field_148459_j = 1.0F;
/* 120:    */           }
/* 121:143 */           if (var7) {
/* 122:145 */             this.field_148462_i = p_148446_2_;
/* 123:    */           } else {
/* 124:149 */             this.field_148462_i = -2.0F;
/* 125:    */           }
/* 126:    */         }
/* 127:    */         else
/* 128:    */         {
/* 129:154 */           this.field_148462_i = -2.0F;
/* 130:    */         }
/* 131:    */       }
/* 132:157 */       else if (this.field_148462_i >= 0.0F)
/* 133:    */       {
/* 134:159 */         this.field_148460_k -= (p_148446_2_ - this.field_148462_i) * this.field_148459_j;
/* 135:160 */         this.field_148462_i = p_148446_2_;
/* 136:    */       }
/* 137:    */     }
/* 138:    */     else
/* 139:    */     {
/* 140:165 */       while ((!this.field_148456_a.gameSettings.touchscreen) && (Mouse.next()))
/* 141:    */       {
/* 142:    */         boolean var7;
/* 143:    */         int var8;
/* 144:    */         int var9;
/* 145:    */         int var10;
/* 146:    */         int var11;
/* 147:    */         boolean var12;
/* 148:    */         int var19;
/* 149:    */         int var13;
/* 150:167 */         int var16 = Mouse.getEventDWheel();
/* 151:169 */         if (var16 != 0)
/* 152:    */         {
/* 153:171 */           if (var16 > 0) {
/* 154:173 */             var16 = -1;
/* 155:175 */           } else if (var16 < 0) {
/* 156:177 */             var16 = 1;
/* 157:    */           }
/* 158:180 */           this.field_148460_k += var16 * this.field_148454_b / 2;
/* 159:    */         }
/* 160:    */       }
/* 161:184 */       this.field_148462_i = -1.0F;
/* 162:    */     }
/* 163:187 */     func_148448_f();
/* 164:188 */     GL11.glDisable(2896);
/* 165:189 */     GL11.glDisable(2912);
/* 166:190 */     Tessellator var18 = Tessellator.instance;
/* 167:191 */     this.field_148456_a.getTextureManager().bindTexture(Gui.optionsBackground);
/* 168:192 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 169:193 */     float var17 = 32.0F;
/* 170:194 */     var18.startDrawingQuads();
/* 171:195 */     var18.setColorOpaque_I(2105376);
/* 172:196 */     var18.addVertexWithUV(this.field_148453_e, this.field_148461_h, 0.0D, this.field_148453_e / var17, (this.field_148461_h + (int)this.field_148460_k) / var17);
/* 173:197 */     var18.addVertexWithUV(this.field_148451_g, this.field_148461_h, 0.0D, this.field_148451_g / var17, (this.field_148461_h + (int)this.field_148460_k) / var17);
/* 174:198 */     var18.addVertexWithUV(this.field_148451_g, this.field_148450_f, 0.0D, this.field_148451_g / var17, (this.field_148450_f + (int)this.field_148460_k) / var17);
/* 175:199 */     var18.addVertexWithUV(this.field_148453_e, this.field_148450_f, 0.0D, this.field_148453_e / var17, (this.field_148450_f + (int)this.field_148460_k) / var17);
/* 176:200 */     var18.draw();
/* 177:201 */     int var9 = this.field_148453_e + 2;
/* 178:202 */     int var10 = this.field_148450_f + 4 - (int)this.field_148460_k;
/* 179:205 */     for (int var11 = 0; var11 < var4; var11++)
/* 180:    */     {
/* 181:207 */       int var19 = var10 + var11 * this.field_148454_b;
/* 182:208 */       int var13 = this.field_148454_b - 4;
/* 183:210 */       if ((var19 + this.field_148454_b <= this.field_148461_h) && (var19 - 4 >= this.field_148450_f))
/* 184:    */       {
/* 185:212 */         if (func_148444_a(var11))
/* 186:    */         {
/* 187:214 */           int var14 = this.field_148453_e + 2;
/* 188:215 */           int var15 = this.field_148451_g - 2;
/* 189:216 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 190:217 */           GL11.glDisable(3553);
/* 191:218 */           var18.startDrawingQuads();
/* 192:219 */           var18.setColorOpaque_I(8421504);
/* 193:220 */           var18.addVertexWithUV(var14, var19 + var13 + 2, 0.0D, 0.0D, 1.0D);
/* 194:221 */           var18.addVertexWithUV(var15, var19 + var13 + 2, 0.0D, 1.0D, 1.0D);
/* 195:222 */           var18.addVertexWithUV(var15, var19 - 2, 0.0D, 1.0D, 0.0D);
/* 196:223 */           var18.addVertexWithUV(var14, var19 - 2, 0.0D, 0.0D, 0.0D);
/* 197:224 */           var18.setColorOpaque_I(0);
/* 198:225 */           var18.addVertexWithUV(var14 + 1, var19 + var13 + 1, 0.0D, 0.0D, 1.0D);
/* 199:226 */           var18.addVertexWithUV(var15 - 1, var19 + var13 + 1, 0.0D, 1.0D, 1.0D);
/* 200:227 */           var18.addVertexWithUV(var15 - 1, var19 - 1, 0.0D, 1.0D, 0.0D);
/* 201:228 */           var18.addVertexWithUV(var14 + 1, var19 - 1, 0.0D, 0.0D, 0.0D);
/* 202:229 */           var18.draw();
/* 203:230 */           GL11.glEnable(3553);
/* 204:    */         }
/* 205:233 */         func_148442_a(var11, var9, var19, var13, var18);
/* 206:    */       }
/* 207:    */     }
/* 208:237 */     GL11.glDisable(2929);
/* 209:238 */     byte var20 = 4;
/* 210:239 */     GL11.glEnable(3042);
/* 211:240 */     GL11.glBlendFunc(770, 771);
/* 212:241 */     GL11.glDisable(3008);
/* 213:242 */     GL11.glShadeModel(7425);
/* 214:243 */     GL11.glDisable(3553);
/* 215:244 */     var18.startDrawingQuads();
/* 216:245 */     var18.setColorRGBA_I(0, 0);
/* 217:246 */     var18.addVertexWithUV(this.field_148453_e, this.field_148450_f + var20, 0.0D, 0.0D, 1.0D);
/* 218:247 */     var18.addVertexWithUV(this.field_148451_g, this.field_148450_f + var20, 0.0D, 1.0D, 1.0D);
/* 219:248 */     var18.setColorRGBA_I(0, 255);
/* 220:249 */     var18.addVertexWithUV(this.field_148451_g, this.field_148450_f, 0.0D, 1.0D, 0.0D);
/* 221:250 */     var18.addVertexWithUV(this.field_148453_e, this.field_148450_f, 0.0D, 0.0D, 0.0D);
/* 222:251 */     var18.draw();
/* 223:252 */     var18.startDrawingQuads();
/* 224:253 */     var18.setColorRGBA_I(0, 255);
/* 225:254 */     var18.addVertexWithUV(this.field_148453_e, this.field_148461_h, 0.0D, 0.0D, 1.0D);
/* 226:255 */     var18.addVertexWithUV(this.field_148451_g, this.field_148461_h, 0.0D, 1.0D, 1.0D);
/* 227:256 */     var18.setColorRGBA_I(0, 0);
/* 228:257 */     var18.addVertexWithUV(this.field_148451_g, this.field_148461_h - var20, 0.0D, 1.0D, 0.0D);
/* 229:258 */     var18.addVertexWithUV(this.field_148453_e, this.field_148461_h - var20, 0.0D, 0.0D, 0.0D);
/* 230:259 */     var18.draw();
/* 231:260 */     int var19 = func_148441_d();
/* 232:262 */     if (var19 > 0)
/* 233:    */     {
/* 234:264 */       int var13 = (this.field_148461_h - this.field_148450_f) * (this.field_148461_h - this.field_148450_f) / func_148447_b();
/* 235:266 */       if (var13 < 32) {
/* 236:268 */         var13 = 32;
/* 237:    */       }
/* 238:271 */       if (var13 > this.field_148461_h - this.field_148450_f - 8) {
/* 239:273 */         var13 = this.field_148461_h - this.field_148450_f - 8;
/* 240:    */       }
/* 241:276 */       int var14 = (int)this.field_148460_k * (this.field_148461_h - this.field_148450_f - var13) / var19 + this.field_148450_f;
/* 242:278 */       if (var14 < this.field_148450_f) {
/* 243:280 */         var14 = this.field_148450_f;
/* 244:    */       }
/* 245:283 */       var18.startDrawingQuads();
/* 246:284 */       var18.setColorRGBA_I(0, 255);
/* 247:285 */       var18.addVertexWithUV(var5, this.field_148461_h, 0.0D, 0.0D, 1.0D);
/* 248:286 */       var18.addVertexWithUV(var6, this.field_148461_h, 0.0D, 1.0D, 1.0D);
/* 249:287 */       var18.addVertexWithUV(var6, this.field_148450_f, 0.0D, 1.0D, 0.0D);
/* 250:288 */       var18.addVertexWithUV(var5, this.field_148450_f, 0.0D, 0.0D, 0.0D);
/* 251:289 */       var18.draw();
/* 252:290 */       var18.startDrawingQuads();
/* 253:291 */       var18.setColorRGBA_I(8421504, 255);
/* 254:292 */       var18.addVertexWithUV(var5, var14 + var13, 0.0D, 0.0D, 1.0D);
/* 255:293 */       var18.addVertexWithUV(var6, var14 + var13, 0.0D, 1.0D, 1.0D);
/* 256:294 */       var18.addVertexWithUV(var6, var14, 0.0D, 1.0D, 0.0D);
/* 257:295 */       var18.addVertexWithUV(var5, var14, 0.0D, 0.0D, 0.0D);
/* 258:296 */       var18.draw();
/* 259:297 */       var18.startDrawingQuads();
/* 260:298 */       var18.setColorRGBA_I(12632256, 255);
/* 261:299 */       var18.addVertexWithUV(var5, var14 + var13 - 1, 0.0D, 0.0D, 1.0D);
/* 262:300 */       var18.addVertexWithUV(var6 - 1, var14 + var13 - 1, 0.0D, 1.0D, 1.0D);
/* 263:301 */       var18.addVertexWithUV(var6 - 1, var14, 0.0D, 1.0D, 0.0D);
/* 264:302 */       var18.addVertexWithUV(var5, var14, 0.0D, 0.0D, 0.0D);
/* 265:303 */       var18.draw();
/* 266:    */     }
/* 267:306 */     GL11.glEnable(3553);
/* 268:307 */     GL11.glShadeModel(7424);
/* 269:308 */     GL11.glEnable(3008);
/* 270:309 */     GL11.glDisable(3042);
/* 271:    */   }
/* 272:    */   
/* 273:    */   protected int func_148440_e()
/* 274:    */   {
/* 275:314 */     return this.field_148451_g - 8;
/* 276:    */   }
/* 277:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.SelectionListBase
 * JD-Core Version:    0.7.0.1
 */