/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import java.io.File;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.net.URI;
/*   7:    */ import java.net.URISyntaxException;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import net.minecraft.client.Minecraft;
/*  12:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  13:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*  14:    */ import net.minecraft.client.settings.GameSettings;
/*  15:    */ import net.minecraft.event.ClickEvent;
/*  16:    */ import net.minecraft.event.ClickEvent.Action;
/*  17:    */ import net.minecraft.event.HoverEvent;
/*  18:    */ import net.minecraft.event.HoverEvent.Action;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.nbt.JsonToNBT;
/*  21:    */ import net.minecraft.nbt.NBTBase;
/*  22:    */ import net.minecraft.nbt.NBTException;
/*  23:    */ import net.minecraft.nbt.NBTTagCompound;
/*  24:    */ import net.minecraft.network.play.client.C14PacketTabComplete;
/*  25:    */ import net.minecraft.stats.Achievement;
/*  26:    */ import net.minecraft.stats.StatBase;
/*  27:    */ import net.minecraft.stats.StatList;
/*  28:    */ import net.minecraft.util.ChatComponentText;
/*  29:    */ import net.minecraft.util.ChatComponentTranslation;
/*  30:    */ import net.minecraft.util.ChatStyle;
/*  31:    */ import net.minecraft.util.EnumChatFormatting;
/*  32:    */ import net.minecraft.util.IChatComponent;
/*  33:    */ import org.apache.logging.log4j.LogManager;
/*  34:    */ import org.apache.logging.log4j.Logger;
/*  35:    */ import org.lwjgl.input.Keyboard;
/*  36:    */ import org.lwjgl.input.Mouse;
/*  37:    */ import org.lwjgl.opengl.GL11;
/*  38:    */ 
/*  39:    */ public class GuiChat
/*  40:    */   extends GuiScreen
/*  41:    */ {
/*  42: 33 */   private static final Logger logger = ;
/*  43: 34 */   private String field_146410_g = "";
/*  44: 35 */   private int field_146416_h = -1;
/*  45:    */   private boolean field_146417_i;
/*  46:    */   private boolean field_146414_r;
/*  47:    */   private int field_146413_s;
/*  48: 39 */   private List field_146412_t = new ArrayList();
/*  49:    */   private URI field_146411_u;
/*  50:    */   protected GuiTextField field_146415_a;
/*  51: 42 */   private String field_146409_v = "";
/*  52:    */   private static final String __OBFID = "CL_00000682";
/*  53:    */   
/*  54:    */   public GuiChat() {}
/*  55:    */   
/*  56:    */   public GuiChat(String par1Str)
/*  57:    */   {
/*  58: 49 */     this.field_146409_v = par1Str;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void initGui()
/*  62:    */   {
/*  63: 57 */     Keyboard.enableRepeatEvents(true);
/*  64: 58 */     this.field_146416_h = this.mc.ingameGUI.getChatGUI().func_146238_c().size();
/*  65: 59 */     this.field_146415_a = new GuiTextField(this.fontRendererObj, 4, height - 12, width - 4, 12);
/*  66: 60 */     this.field_146415_a.func_146203_f(100);
/*  67: 61 */     this.field_146415_a.func_146185_a(false);
/*  68: 62 */     this.field_146415_a.setFocused(true);
/*  69: 63 */     this.field_146415_a.setText(this.field_146409_v);
/*  70: 64 */     this.field_146415_a.func_146205_d(false);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void onGuiClosed()
/*  74:    */   {
/*  75: 72 */     Keyboard.enableRepeatEvents(false);
/*  76: 73 */     this.mc.ingameGUI.getChatGUI().resetScroll();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void updateScreen()
/*  80:    */   {
/*  81: 81 */     this.field_146415_a.updateCursorCounter();
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected void keyTyped(char par1, int par2)
/*  85:    */   {
/*  86: 89 */     this.field_146414_r = false;
/*  87: 91 */     if (par2 == 15) {
/*  88: 93 */       func_146404_p_();
/*  89:    */     } else {
/*  90: 97 */       this.field_146417_i = false;
/*  91:    */     }
/*  92:100 */     if (par2 == 1)
/*  93:    */     {
/*  94:102 */       this.mc.displayGuiScreen(null);
/*  95:    */     }
/*  96:104 */     else if ((par2 != 28) && (par2 != 156))
/*  97:    */     {
/*  98:106 */       if (par2 == 200) {
/*  99:108 */         func_146402_a(-1);
/* 100:110 */       } else if (par2 == 208) {
/* 101:112 */         func_146402_a(1);
/* 102:114 */       } else if (par2 == 201) {
/* 103:116 */         this.mc.ingameGUI.getChatGUI().func_146229_b(this.mc.ingameGUI.getChatGUI().func_146232_i() - 1);
/* 104:118 */       } else if (par2 == 209) {
/* 105:120 */         this.mc.ingameGUI.getChatGUI().func_146229_b(-this.mc.ingameGUI.getChatGUI().func_146232_i() + 1);
/* 106:    */       } else {
/* 107:124 */         this.field_146415_a.textboxKeyTyped(par1, par2);
/* 108:    */       }
/* 109:    */     }
/* 110:    */     else
/* 111:    */     {
/* 112:129 */       String var3 = this.field_146415_a.getText().trim();
/* 113:131 */       if (var3.length() > 0) {
/* 114:133 */         func_146403_a(var3);
/* 115:    */       }
/* 116:136 */       this.mc.displayGuiScreen(null);
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void func_146403_a(String p_146403_1_)
/* 121:    */   {
/* 122:142 */     this.mc.ingameGUI.getChatGUI().func_146239_a(p_146403_1_);
/* 123:143 */     this.mc.thePlayer.sendChatMessage(p_146403_1_);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void handleMouseInput()
/* 127:    */   {
/* 128:151 */     super.handleMouseInput();
/* 129:152 */     int var1 = Mouse.getEventDWheel();
/* 130:154 */     if (var1 != 0)
/* 131:    */     {
/* 132:156 */       if (var1 > 1) {
/* 133:158 */         var1 = 1;
/* 134:    */       }
/* 135:161 */       if (var1 < -1) {
/* 136:163 */         var1 = -1;
/* 137:    */       }
/* 138:166 */       if (!isShiftKeyDown()) {
/* 139:168 */         var1 *= 7;
/* 140:    */       }
/* 141:171 */       this.mc.ingameGUI.getChatGUI().func_146229_b(var1);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 146:    */   {
/* 147:180 */     if ((par3 == 0) && (this.mc.gameSettings.chatLinks))
/* 148:    */     {
/* 149:182 */       IChatComponent var4 = this.mc.ingameGUI.getChatGUI().func_146236_a(Mouse.getX(), Mouse.getY());
/* 150:184 */       if (var4 != null)
/* 151:    */       {
/* 152:186 */         ClickEvent var5 = var4.getChatStyle().getChatClickEvent();
/* 153:188 */         if (var5 != null)
/* 154:    */         {
/* 155:190 */           if (isShiftKeyDown())
/* 156:    */           {
/* 157:192 */             this.field_146415_a.func_146191_b(var4.getUnformattedTextForChat());
/* 158:    */           }
/* 159:198 */           else if (var5.getAction() == ClickEvent.Action.OPEN_URL)
/* 160:    */           {
/* 161:    */             try
/* 162:    */             {
/* 163:202 */               URI var6 = new URI(var5.getValue());
/* 164:204 */               if (this.mc.gameSettings.chatLinksPrompt)
/* 165:    */               {
/* 166:206 */                 this.field_146411_u = var6;
/* 167:207 */                 this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, var5.getValue(), 0, false));
/* 168:    */               }
/* 169:    */               else
/* 170:    */               {
/* 171:211 */                 func_146407_a(var6);
/* 172:    */               }
/* 173:    */             }
/* 174:    */             catch (URISyntaxException var7)
/* 175:    */             {
/* 176:216 */               logger.error("Can't open url for " + var5, var7);
/* 177:    */             }
/* 178:    */           }
/* 179:219 */           else if (var5.getAction() == ClickEvent.Action.OPEN_FILE)
/* 180:    */           {
/* 181:221 */             URI var6 = new File(var5.getValue()).toURI();
/* 182:222 */             func_146407_a(var6);
/* 183:    */           }
/* 184:224 */           else if (var5.getAction() == ClickEvent.Action.SUGGEST_COMMAND)
/* 185:    */           {
/* 186:226 */             this.field_146415_a.setText(var5.getValue());
/* 187:    */           }
/* 188:228 */           else if (var5.getAction() == ClickEvent.Action.RUN_COMMAND)
/* 189:    */           {
/* 190:230 */             func_146403_a(var5.getValue());
/* 191:    */           }
/* 192:    */           else
/* 193:    */           {
/* 194:234 */             logger.error("Don't know how to handle " + var5);
/* 195:    */           }
/* 196:238 */           return;
/* 197:    */         }
/* 198:    */       }
/* 199:    */     }
/* 200:243 */     this.field_146415_a.mouseClicked(par1, par2, par3);
/* 201:244 */     super.mouseClicked(par1, par2, par3);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void confirmClicked(boolean par1, int par2)
/* 205:    */   {
/* 206:249 */     if (par2 == 0)
/* 207:    */     {
/* 208:251 */       if (par1) {
/* 209:253 */         func_146407_a(this.field_146411_u);
/* 210:    */       }
/* 211:256 */       this.field_146411_u = null;
/* 212:257 */       this.mc.displayGuiScreen(this);
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   private void func_146407_a(URI p_146407_1_)
/* 217:    */   {
/* 218:    */     try
/* 219:    */     {
/* 220:265 */       Class var2 = Class.forName("java.awt.Desktop");
/* 221:266 */       Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 222:267 */       var2.getMethod("browse", new Class[] { URI.class }).invoke(var3, new Object[] { p_146407_1_ });
/* 223:    */     }
/* 224:    */     catch (Throwable var4)
/* 225:    */     {
/* 226:271 */       logger.error("Couldn't open link", var4);
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void func_146404_p_()
/* 231:    */   {
/* 232:279 */     if (this.field_146417_i)
/* 233:    */     {
/* 234:281 */       this.field_146415_a.func_146175_b(this.field_146415_a.func_146197_a(-1, this.field_146415_a.func_146198_h(), false) - this.field_146415_a.func_146198_h());
/* 235:283 */       if (this.field_146413_s >= this.field_146412_t.size()) {
/* 236:285 */         this.field_146413_s = 0;
/* 237:    */       }
/* 238:    */     }
/* 239:    */     else
/* 240:    */     {
/* 241:290 */       int var1 = this.field_146415_a.func_146197_a(-1, this.field_146415_a.func_146198_h(), false);
/* 242:291 */       this.field_146412_t.clear();
/* 243:292 */       this.field_146413_s = 0;
/* 244:293 */       String var2 = this.field_146415_a.getText().substring(var1).toLowerCase();
/* 245:294 */       String var3 = this.field_146415_a.getText().substring(0, this.field_146415_a.func_146198_h());
/* 246:295 */       func_146405_a(var3, var2);
/* 247:297 */       if (this.field_146412_t.isEmpty()) {
/* 248:299 */         return;
/* 249:    */       }
/* 250:302 */       this.field_146417_i = true;
/* 251:303 */       this.field_146415_a.func_146175_b(var1 - this.field_146415_a.func_146198_h());
/* 252:    */     }
/* 253:306 */     if (this.field_146412_t.size() > 1)
/* 254:    */     {
/* 255:308 */       StringBuilder var4 = new StringBuilder();
/* 256:    */       String var3;
/* 257:310 */       for (Iterator var5 = this.field_146412_t.iterator(); var5.hasNext(); var4.append(var3))
/* 258:    */       {
/* 259:312 */         var3 = (String)var5.next();
/* 260:314 */         if (var4.length() > 0) {
/* 261:316 */           var4.append(", ");
/* 262:    */         }
/* 263:    */       }
/* 264:320 */       this.mc.ingameGUI.getChatGUI().func_146234_a(new ChatComponentText(var4.toString()), 1);
/* 265:    */     }
/* 266:323 */     this.field_146415_a.func_146191_b((String)this.field_146412_t.get(this.field_146413_s++));
/* 267:    */   }
/* 268:    */   
/* 269:    */   private void func_146405_a(String p_146405_1_, String p_146405_2_)
/* 270:    */   {
/* 271:328 */     if (p_146405_1_.length() >= 1)
/* 272:    */     {
/* 273:330 */       this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(p_146405_1_));
/* 274:331 */       this.field_146414_r = true;
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void func_146402_a(int p_146402_1_)
/* 279:    */   {
/* 280:337 */     int var2 = this.field_146416_h + p_146402_1_;
/* 281:338 */     int var3 = this.mc.ingameGUI.getChatGUI().func_146238_c().size();
/* 282:340 */     if (var2 < 0) {
/* 283:342 */       var2 = 0;
/* 284:    */     }
/* 285:345 */     if (var2 > var3) {
/* 286:347 */       var2 = var3;
/* 287:    */     }
/* 288:350 */     if (var2 != this.field_146416_h) {
/* 289:352 */       if (var2 == var3)
/* 290:    */       {
/* 291:354 */         this.field_146416_h = var3;
/* 292:355 */         this.field_146415_a.setText(this.field_146410_g);
/* 293:    */       }
/* 294:    */       else
/* 295:    */       {
/* 296:359 */         if (this.field_146416_h == var3) {
/* 297:361 */           this.field_146410_g = this.field_146415_a.getText();
/* 298:    */         }
/* 299:364 */         this.field_146415_a.setText((String)this.mc.ingameGUI.getChatGUI().func_146238_c().get(var2));
/* 300:365 */         this.field_146416_h = var2;
/* 301:    */       }
/* 302:    */     }
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void drawScreen(int par1, int par2, float par3)
/* 306:    */   {
/* 307:375 */     drawRect(2.0F, height - 14, width - 2, height - 2, -2147483648);
/* 308:376 */     this.field_146415_a.drawTextBox();
/* 309:377 */     IChatComponent var4 = this.mc.ingameGUI.getChatGUI().func_146236_a(Mouse.getX(), Mouse.getY());
/* 310:379 */     if ((var4 != null) && (var4.getChatStyle().getChatHoverEvent() != null))
/* 311:    */     {
/* 312:381 */       HoverEvent var5 = var4.getChatStyle().getChatHoverEvent();
/* 313:383 */       if (var5.getAction() == HoverEvent.Action.SHOW_ITEM)
/* 314:    */       {
/* 315:385 */         ItemStack var6 = null;
/* 316:    */         try
/* 317:    */         {
/* 318:389 */           NBTBase var7 = JsonToNBT.func_150315_a(var5.getValue().getUnformattedText());
/* 319:391 */           if ((var7 != null) && ((var7 instanceof NBTTagCompound))) {
/* 320:393 */             var6 = ItemStack.loadItemStackFromNBT((NBTTagCompound)var7);
/* 321:    */           }
/* 322:    */         }
/* 323:    */         catch (NBTException localNBTException) {}
/* 324:401 */         if (var6 != null) {
/* 325:403 */           func_146285_a(var6, par1, par2);
/* 326:    */         } else {
/* 327:407 */           func_146279_a(EnumChatFormatting.RED + "Invalid Item!", par1, par2);
/* 328:    */         }
/* 329:    */       }
/* 330:410 */       else if (var5.getAction() == HoverEvent.Action.SHOW_TEXT)
/* 331:    */       {
/* 332:412 */         func_146279_a(var5.getValue().getFormattedText(), par1, par2);
/* 333:    */       }
/* 334:414 */       else if (var5.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT)
/* 335:    */       {
/* 336:416 */         StatBase var13 = StatList.func_151177_a(var5.getValue().getUnformattedText());
/* 337:418 */         if (var13 != null)
/* 338:    */         {
/* 339:420 */           IChatComponent var12 = var13.func_150951_e();
/* 340:421 */           ChatComponentTranslation var8 = new ChatComponentTranslation("stats.tooltip.type." + (var13.isAchievement() ? "achievement" : "statistic"), new Object[0]);
/* 341:422 */           var8.getChatStyle().setItalic(Boolean.valueOf(true));
/* 342:423 */           String var9 = (var13 instanceof Achievement) ? ((Achievement)var13).getDescription() : null;
/* 343:424 */           ArrayList var10 = Lists.newArrayList(new String[] { var12.getFormattedText(), var8.getFormattedText() });
/* 344:426 */           if (var9 != null) {
/* 345:428 */             var10.addAll(this.fontRendererObj.listFormattedStringToWidth(var9, 150));
/* 346:    */           }
/* 347:431 */           func_146283_a(var10, par1, par2);
/* 348:    */         }
/* 349:    */         else
/* 350:    */         {
/* 351:435 */           func_146279_a(EnumChatFormatting.RED + "Invalid statistic/achievement!", par1, par2);
/* 352:    */         }
/* 353:    */       }
/* 354:439 */       GL11.glDisable(2896);
/* 355:    */     }
/* 356:442 */     super.drawScreen(par1, par2, par3);
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void func_146406_a(String[] p_146406_1_)
/* 360:    */   {
/* 361:447 */     if (this.field_146414_r)
/* 362:    */     {
/* 363:449 */       this.field_146417_i = false;
/* 364:450 */       this.field_146412_t.clear();
/* 365:451 */       String[] var2 = p_146406_1_;
/* 366:452 */       int var3 = p_146406_1_.length;
/* 367:454 */       for (int var4 = 0; var4 < var3; var4++)
/* 368:    */       {
/* 369:456 */         String var5 = var2[var4];
/* 370:458 */         if (var5.length() > 0) {
/* 371:460 */           this.field_146412_t.add(var5);
/* 372:    */         }
/* 373:    */       }
/* 374:464 */       if (this.field_146412_t.size() > 0)
/* 375:    */       {
/* 376:466 */         this.field_146417_i = true;
/* 377:467 */         func_146404_p_();
/* 378:    */       }
/* 379:    */     }
/* 380:    */   }
/* 381:    */   
/* 382:    */   public boolean doesGuiPauseGame()
/* 383:    */   {
/* 384:477 */     return false;
/* 385:    */   }
/* 386:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiChat
 * JD-Core Version:    0.7.0.1
 */