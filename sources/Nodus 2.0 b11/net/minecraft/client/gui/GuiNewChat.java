/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import net.minecraft.client.Minecraft;
/*   8:    */ import net.minecraft.client.settings.GameSettings;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
/*  10:    */ import net.minecraft.util.ChatComponentText;
/*  11:    */ import net.minecraft.util.ChatStyle;
/*  12:    */ import net.minecraft.util.EnumChatFormatting;
/*  13:    */ import net.minecraft.util.IChatComponent;
/*  14:    */ import net.minecraft.util.MathHelper;
/*  15:    */ import org.apache.logging.log4j.LogManager;
/*  16:    */ import org.apache.logging.log4j.Logger;
/*  17:    */ import org.lwjgl.opengl.GL11;
/*  18:    */ 
/*  19:    */ public class GuiNewChat
/*  20:    */   extends Gui
/*  21:    */ {
/*  22: 24 */   private static final Logger logger = ;
/*  23:    */   private final Minecraft field_146247_f;
/*  24: 26 */   private final List field_146248_g = new ArrayList();
/*  25: 27 */   private final List field_146252_h = new ArrayList();
/*  26: 28 */   private final List field_146253_i = new ArrayList();
/*  27:    */   private int field_146250_j;
/*  28:    */   private boolean field_146251_k;
/*  29:    */   private static final String __OBFID = "CL_00000669";
/*  30:    */   
/*  31:    */   public GuiNewChat(Minecraft par1Minecraft)
/*  32:    */   {
/*  33: 35 */     this.field_146247_f = par1Minecraft;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void func_146230_a(int p_146230_1_)
/*  37:    */   {
/*  38: 40 */     if (this.field_146247_f.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN)
/*  39:    */     {
/*  40: 42 */       int var2 = func_146232_i();
/*  41: 43 */       boolean var3 = false;
/*  42: 44 */       int var4 = 0;
/*  43: 45 */       int var5 = this.field_146253_i.size();
/*  44: 46 */       float var6 = this.field_146247_f.gameSettings.chatOpacity * 0.9F + 0.1F;
/*  45: 48 */       if (var5 > 0)
/*  46:    */       {
/*  47: 50 */         if (func_146241_e()) {
/*  48: 52 */           var3 = true;
/*  49:    */         }
/*  50: 55 */         float var7 = func_146244_h();
/*  51: 56 */         int var8 = MathHelper.ceiling_float_int(func_146228_f() / var7);
/*  52: 57 */         GL11.glPushMatrix();
/*  53: 58 */         GL11.glTranslatef(2.0F, 20.0F, 0.0F);
/*  54: 59 */         GL11.glScalef(var7, var7, 1.0F);
/*  55: 64 */         for (int var9 = 0; (var9 + this.field_146250_j < this.field_146253_i.size()) && (var9 < var2); var9++)
/*  56:    */         {
/*  57: 66 */           ChatLine var10 = (ChatLine)this.field_146253_i.get(var9 + this.field_146250_j);
/*  58: 68 */           if (var10 != null)
/*  59:    */           {
/*  60: 70 */             int var11 = p_146230_1_ - var10.getUpdatedCounter();
/*  61: 72 */             if ((var11 < 200) || (var3))
/*  62:    */             {
/*  63: 74 */               double var12 = var11 / 200.0D;
/*  64: 75 */               var12 = 1.0D - var12;
/*  65: 76 */               var12 *= 10.0D;
/*  66: 78 */               if (var12 < 0.0D) {
/*  67: 80 */                 var12 = 0.0D;
/*  68:    */               }
/*  69: 83 */               if (var12 > 1.0D) {
/*  70: 85 */                 var12 = 1.0D;
/*  71:    */               }
/*  72: 88 */               var12 *= var12;
/*  73: 89 */               int var14 = (int)(255.0D * var12);
/*  74: 91 */               if (var3) {
/*  75: 93 */                 var14 = 255;
/*  76:    */               }
/*  77: 96 */               var14 = (int)(var14 * var6);
/*  78: 97 */               var4++;
/*  79: 99 */               if (var14 > 3)
/*  80:    */               {
/*  81:101 */                 byte var15 = 0;
/*  82:102 */                 int var16 = -var9 * 9;
/*  83:103 */                 drawRect(var15, var16 - 9, var15 + var8 + 4, var16, var14 / 2 << 24);
/*  84:104 */                 String var17 = var10.func_151461_a().getFormattedText();
/*  85:105 */                 this.field_146247_f.fontRenderer.drawStringWithShadow(var17, var15, var16 - 8, 16777215 + (var14 << 24));
/*  86:106 */                 GL11.glDisable(3008);
/*  87:    */               }
/*  88:    */             }
/*  89:    */           }
/*  90:    */         }
/*  91:112 */         if (var3)
/*  92:    */         {
/*  93:114 */           var9 = this.field_146247_f.fontRenderer.FONT_HEIGHT;
/*  94:115 */           GL11.glTranslatef(-3.0F, 0.0F, 0.0F);
/*  95:116 */           int var18 = var5 * var9 + var5;
/*  96:117 */           int var11 = var4 * var9 + var4;
/*  97:118 */           int var20 = this.field_146250_j * var11 / var5;
/*  98:119 */           int var13 = var11 * var11 / var18;
/*  99:121 */           if (var18 != var11)
/* 100:    */           {
/* 101:123 */             int var14 = var20 > 0 ? 170 : 96;
/* 102:124 */             int var19 = this.field_146251_k ? 13382451 : 3355562;
/* 103:125 */             drawRect(0.0F, -var20, 2, -var20 - var13, var19 + (var14 << 24));
/* 104:126 */             drawRect(2.0F, -var20, 1, -var20 - var13, 13421772 + (var14 << 24));
/* 105:    */           }
/* 106:    */         }
/* 107:130 */         GL11.glPopMatrix();
/* 108:    */       }
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void func_146231_a()
/* 113:    */   {
/* 114:137 */     this.field_146253_i.clear();
/* 115:138 */     this.field_146252_h.clear();
/* 116:139 */     this.field_146248_g.clear();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void func_146227_a(IChatComponent p_146227_1_)
/* 120:    */   {
/* 121:144 */     func_146234_a(p_146227_1_, 0);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void func_146234_a(IChatComponent p_146234_1_, int p_146234_2_)
/* 125:    */   {
/* 126:149 */     func_146237_a(p_146234_1_, p_146234_2_, this.field_146247_f.ingameGUI.getUpdateCounter(), false);
/* 127:150 */     logger.info("[CHAT] " + p_146234_1_.getUnformattedText());
/* 128:    */   }
/* 129:    */   
/* 130:    */   private String func_146235_b(String p_146235_1_)
/* 131:    */   {
/* 132:155 */     return Minecraft.getMinecraft().gameSettings.chatColours ? p_146235_1_ : EnumChatFormatting.getTextWithoutFormattingCodes(p_146235_1_);
/* 133:    */   }
/* 134:    */   
/* 135:    */   private void func_146237_a(IChatComponent p_146237_1_, int p_146237_2_, int p_146237_3_, boolean p_146237_4_)
/* 136:    */   {
/* 137:160 */     if (p_146237_2_ != 0) {
/* 138:162 */       func_146242_c(p_146237_2_);
/* 139:    */     }
/* 140:165 */     int var5 = MathHelper.floor_float(func_146228_f() / func_146244_h());
/* 141:166 */     int var6 = 0;
/* 142:167 */     ChatComponentText var7 = new ChatComponentText("");
/* 143:168 */     ArrayList var8 = Lists.newArrayList();
/* 144:169 */     ArrayList var9 = Lists.newArrayList(p_146237_1_);
/* 145:171 */     for (int var10 = 0; var10 < var9.size(); var10++)
/* 146:    */     {
/* 147:173 */       IChatComponent var11 = (IChatComponent)var9.get(var10);
/* 148:174 */       String var12 = func_146235_b(var11.getChatStyle().getFormattingCode() + var11.getUnformattedTextForChat());
/* 149:175 */       int var13 = this.field_146247_f.fontRenderer.getStringWidth(var12);
/* 150:176 */       ChatComponentText var14 = new ChatComponentText(var12);
/* 151:177 */       var14.setChatStyle(var11.getChatStyle().createShallowCopy());
/* 152:178 */       boolean var15 = false;
/* 153:180 */       if (var6 + var13 > var5)
/* 154:    */       {
/* 155:182 */         String var16 = this.field_146247_f.fontRenderer.trimStringToWidth(var12, var5 - var6, false);
/* 156:183 */         String var17 = var16.length() < var12.length() ? var12.substring(var16.length()) : null;
/* 157:185 */         if ((var17 != null) && (var17.length() > 0))
/* 158:    */         {
/* 159:187 */           int var18 = var16.lastIndexOf(" ");
/* 160:189 */           if ((var18 >= 0) && (this.field_146247_f.fontRenderer.getStringWidth(var12.substring(0, var18)) > 0))
/* 161:    */           {
/* 162:191 */             var16 = var12.substring(0, var18);
/* 163:192 */             var17 = var12.substring(var18);
/* 164:    */           }
/* 165:195 */           ChatComponentText var19 = new ChatComponentText(var17);
/* 166:196 */           var19.setChatStyle(var11.getChatStyle().createShallowCopy());
/* 167:197 */           var9.add(var10 + 1, var19);
/* 168:    */         }
/* 169:200 */         var13 = this.field_146247_f.fontRenderer.getStringWidth(var16);
/* 170:201 */         var14 = new ChatComponentText(var16);
/* 171:202 */         var14.setChatStyle(var11.getChatStyle().createShallowCopy());
/* 172:203 */         var15 = true;
/* 173:    */       }
/* 174:206 */       if (var6 + var13 <= var5)
/* 175:    */       {
/* 176:208 */         var6 += var13;
/* 177:209 */         var7.appendSibling(var14);
/* 178:    */       }
/* 179:    */       else
/* 180:    */       {
/* 181:213 */         var15 = true;
/* 182:    */       }
/* 183:216 */       if (var15)
/* 184:    */       {
/* 185:218 */         var8.add(var7);
/* 186:219 */         var6 = 0;
/* 187:220 */         var7 = new ChatComponentText("");
/* 188:    */       }
/* 189:    */     }
/* 190:224 */     var8.add(var7);
/* 191:225 */     boolean var21 = func_146241_e();
/* 192:    */     IChatComponent var22;
/* 193:228 */     for (Iterator var20 = var8.iterator(); var20.hasNext(); this.field_146253_i.add(0, new ChatLine(p_146237_3_, var22, p_146237_2_)))
/* 194:    */     {
/* 195:230 */       var22 = (IChatComponent)var20.next();
/* 196:232 */       if ((var21) && (this.field_146250_j > 0))
/* 197:    */       {
/* 198:234 */         this.field_146251_k = true;
/* 199:235 */         func_146229_b(1);
/* 200:    */       }
/* 201:    */     }
/* 202:239 */     while (this.field_146253_i.size() > 100) {
/* 203:241 */       this.field_146253_i.remove(this.field_146253_i.size() - 1);
/* 204:    */     }
/* 205:244 */     if (!p_146237_4_)
/* 206:    */     {
/* 207:246 */       this.field_146252_h.add(0, new ChatLine(p_146237_3_, p_146237_1_, p_146237_2_));
/* 208:248 */       while (this.field_146252_h.size() > 100) {
/* 209:250 */         this.field_146252_h.remove(this.field_146252_h.size() - 1);
/* 210:    */       }
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void func_146245_b()
/* 215:    */   {
/* 216:257 */     this.field_146253_i.clear();
/* 217:258 */     resetScroll();
/* 218:260 */     for (int var1 = this.field_146252_h.size() - 1; var1 >= 0; var1--)
/* 219:    */     {
/* 220:262 */       ChatLine var2 = (ChatLine)this.field_146252_h.get(var1);
/* 221:263 */       func_146237_a(var2.func_151461_a(), var2.getChatLineID(), var2.getUpdatedCounter(), true);
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public List func_146238_c()
/* 226:    */   {
/* 227:269 */     return this.field_146248_g;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void func_146239_a(String p_146239_1_)
/* 231:    */   {
/* 232:274 */     if ((this.field_146248_g.isEmpty()) || (!((String)this.field_146248_g.get(this.field_146248_g.size() - 1)).equals(p_146239_1_))) {
/* 233:276 */       this.field_146248_g.add(p_146239_1_);
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void resetScroll()
/* 238:    */   {
/* 239:282 */     this.field_146250_j = 0;
/* 240:283 */     this.field_146251_k = false;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void func_146229_b(int p_146229_1_)
/* 244:    */   {
/* 245:288 */     this.field_146250_j += p_146229_1_;
/* 246:289 */     int var2 = this.field_146253_i.size();
/* 247:291 */     if (this.field_146250_j > var2 - func_146232_i()) {
/* 248:293 */       this.field_146250_j = (var2 - func_146232_i());
/* 249:    */     }
/* 250:296 */     if (this.field_146250_j <= 0)
/* 251:    */     {
/* 252:298 */       this.field_146250_j = 0;
/* 253:299 */       this.field_146251_k = false;
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   public IChatComponent func_146236_a(int p_146236_1_, int p_146236_2_)
/* 258:    */   {
/* 259:305 */     if (!func_146241_e()) {
/* 260:307 */       return null;
/* 261:    */     }
/* 262:311 */     ScaledResolution var3 = new ScaledResolution(this.field_146247_f.gameSettings, this.field_146247_f.displayWidth, this.field_146247_f.displayHeight);
/* 263:312 */     int var4 = var3.getScaleFactor();
/* 264:313 */     float var5 = func_146244_h();
/* 265:314 */     int var6 = p_146236_1_ / var4 - 3;
/* 266:315 */     int var7 = p_146236_2_ / var4 - 27;
/* 267:316 */     var6 = MathHelper.floor_float(var6 / var5);
/* 268:317 */     var7 = MathHelper.floor_float(var7 / var5);
/* 269:319 */     if ((var6 >= 0) && (var7 >= 0))
/* 270:    */     {
/* 271:321 */       int var8 = Math.min(func_146232_i(), this.field_146253_i.size());
/* 272:323 */       if ((var6 <= MathHelper.floor_float(func_146228_f() / func_146244_h())) && (var7 < this.field_146247_f.fontRenderer.FONT_HEIGHT * var8 + var8))
/* 273:    */       {
/* 274:325 */         int var9 = var7 / this.field_146247_f.fontRenderer.FONT_HEIGHT + this.field_146250_j;
/* 275:327 */         if ((var9 >= 0) && (var9 < this.field_146253_i.size()))
/* 276:    */         {
/* 277:329 */           ChatLine var10 = (ChatLine)this.field_146253_i.get(var9);
/* 278:330 */           int var11 = 0;
/* 279:331 */           Iterator var12 = var10.func_151461_a().iterator();
/* 280:333 */           while (var12.hasNext())
/* 281:    */           {
/* 282:335 */             IChatComponent var13 = (IChatComponent)var12.next();
/* 283:337 */             if ((var13 instanceof ChatComponentText))
/* 284:    */             {
/* 285:339 */               var11 += this.field_146247_f.fontRenderer.getStringWidth(func_146235_b(((ChatComponentText)var13).getChatComponentText_TextValue()));
/* 286:341 */               if (var11 > var6) {
/* 287:343 */                 return var13;
/* 288:    */               }
/* 289:    */             }
/* 290:    */           }
/* 291:    */         }
/* 292:349 */         return null;
/* 293:    */       }
/* 294:353 */       return null;
/* 295:    */     }
/* 296:358 */     return null;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public boolean func_146241_e()
/* 300:    */   {
/* 301:365 */     return this.field_146247_f.currentScreen instanceof GuiChat;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void func_146242_c(int p_146242_1_)
/* 305:    */   {
/* 306:370 */     Iterator var2 = this.field_146253_i.iterator();
/* 307:    */     ChatLine var3;
/* 308:    */     do
/* 309:    */     {
/* 310:375 */       if (!var2.hasNext())
/* 311:    */       {
/* 312:377 */         var2 = this.field_146252_h.iterator();
/* 313:    */         ChatLine var3;
/* 314:    */         do
/* 315:    */         {
/* 316:381 */           if (!var2.hasNext()) {
/* 317:383 */             return;
/* 318:    */           }
/* 319:386 */           var3 = (ChatLine)var2.next();
/* 320:388 */         } while (var3.getChatLineID() != p_146242_1_);
/* 321:390 */         var2.remove();
/* 322:391 */         return;
/* 323:    */       }
/* 324:394 */       var3 = (ChatLine)var2.next();
/* 325:396 */     } while (var3.getChatLineID() != p_146242_1_);
/* 326:398 */     var2.remove();
/* 327:    */   }
/* 328:    */   
/* 329:    */   public int func_146228_f()
/* 330:    */   {
/* 331:403 */     return func_146233_a(this.field_146247_f.gameSettings.chatWidth);
/* 332:    */   }
/* 333:    */   
/* 334:    */   public int func_146246_g()
/* 335:    */   {
/* 336:408 */     return func_146243_b(func_146241_e() ? this.field_146247_f.gameSettings.chatHeightFocused : this.field_146247_f.gameSettings.chatHeightUnfocused);
/* 337:    */   }
/* 338:    */   
/* 339:    */   public float func_146244_h()
/* 340:    */   {
/* 341:413 */     return this.field_146247_f.gameSettings.chatScale;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public static int func_146233_a(float p_146233_0_)
/* 345:    */   {
/* 346:418 */     short var1 = 320;
/* 347:419 */     byte var2 = 40;
/* 348:420 */     return MathHelper.floor_float(p_146233_0_ * (var1 - var2) + var2);
/* 349:    */   }
/* 350:    */   
/* 351:    */   public static int func_146243_b(float p_146243_0_)
/* 352:    */   {
/* 353:425 */     short var1 = 180;
/* 354:426 */     byte var2 = 20;
/* 355:427 */     return MathHelper.floor_float(p_146243_0_ * (var1 - var2) + var2);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public int func_146232_i()
/* 359:    */   {
/* 360:432 */     return func_146246_g() / 9;
/* 361:    */   }
/* 362:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiNewChat
 * JD-Core Version:    0.7.0.1
 */