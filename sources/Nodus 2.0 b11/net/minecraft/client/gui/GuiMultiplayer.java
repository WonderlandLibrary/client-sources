/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Splitter;
/*   4:    */ import com.google.common.collect.Lists;
/*   5:    */ import java.util.List;
/*   6:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   7:    */ import net.minecraft.client.Minecraft;
/*   8:    */ import net.minecraft.client.multiplayer.GuiConnecting;
/*   9:    */ import net.minecraft.client.multiplayer.ServerData;
/*  10:    */ import net.minecraft.client.multiplayer.ServerList;
/*  11:    */ import net.minecraft.client.network.LanServerDetector.LanServer;
/*  12:    */ import net.minecraft.client.network.LanServerDetector.LanServerList;
/*  13:    */ import net.minecraft.client.network.LanServerDetector.ThreadLanServerFind;
/*  14:    */ import net.minecraft.client.network.OldServerPinger;
/*  15:    */ import net.minecraft.client.resources.I18n;
/*  16:    */ import org.apache.logging.log4j.LogManager;
/*  17:    */ import org.apache.logging.log4j.Logger;
/*  18:    */ import org.lwjgl.input.Keyboard;
/*  19:    */ 
/*  20:    */ public class GuiMultiplayer
/*  21:    */   extends GuiScreen
/*  22:    */ {
/*  23: 22 */   private static final Logger logger = ;
/*  24: 23 */   private final OldServerPinger field_146797_f = new OldServerPinger();
/*  25:    */   private GuiScreen field_146798_g;
/*  26:    */   private ServerSelectionList field_146803_h;
/*  27:    */   private ServerList field_146804_i;
/*  28:    */   private NodusGuiButton field_146810_r;
/*  29:    */   private NodusGuiButton field_146809_s;
/*  30:    */   private NodusGuiButton field_146808_t;
/*  31:    */   private boolean field_146807_u;
/*  32:    */   private boolean field_146806_v;
/*  33:    */   private boolean field_146805_w;
/*  34:    */   private boolean field_146813_x;
/*  35:    */   private String field_146812_y;
/*  36:    */   private ServerData field_146811_z;
/*  37:    */   private LanServerDetector.LanServerList field_146799_A;
/*  38:    */   private LanServerDetector.ThreadLanServerFind field_146800_B;
/*  39:    */   private boolean field_146801_C;
/*  40:    */   private static final String __OBFID = "CL_00000814";
/*  41:    */   
/*  42:    */   public GuiMultiplayer(GuiScreen par1GuiScreen)
/*  43:    */   {
/*  44: 43 */     this.field_146798_g = par1GuiScreen;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void initGui()
/*  48:    */   {
/*  49: 51 */     Keyboard.enableRepeatEvents(true);
/*  50: 52 */     this.buttonList.clear();
/*  51: 54 */     if (!this.field_146801_C)
/*  52:    */     {
/*  53: 56 */       this.field_146801_C = true;
/*  54: 57 */       this.field_146804_i = new ServerList(this.mc);
/*  55: 58 */       this.field_146804_i.loadServerList();
/*  56: 59 */       this.field_146799_A = new LanServerDetector.LanServerList();
/*  57:    */       try
/*  58:    */       {
/*  59: 63 */         this.field_146800_B = new LanServerDetector.ThreadLanServerFind(this.field_146799_A);
/*  60: 64 */         this.field_146800_B.start();
/*  61:    */       }
/*  62:    */       catch (Exception var2)
/*  63:    */       {
/*  64: 68 */         logger.warn("Unable to start LAN server detection: " + var2.getMessage());
/*  65:    */       }
/*  66: 71 */       this.field_146803_h = new ServerSelectionList(this, this.mc, width, height, 32, height - 64, 36);
/*  67: 72 */       this.field_146803_h.func_148195_a(this.field_146804_i);
/*  68:    */     }
/*  69:    */     else
/*  70:    */     {
/*  71: 76 */       this.field_146803_h.func_148122_a(width, height, 32, height - 64);
/*  72:    */     }
/*  73: 79 */     func_146794_g();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void func_146794_g()
/*  77:    */   {
/*  78: 84 */     this.buttonList.add(this.field_146810_r = new NodusGuiButton(7, width / 2 - 154, height - 28, 70, 20, I18n.format("selectServer.edit", new Object[0])));
/*  79: 85 */     this.buttonList.add(this.field_146808_t = new NodusGuiButton(2, width / 2 - 74, height - 28, 70, 20, I18n.format("selectServer.delete", new Object[0])));
/*  80: 86 */     this.buttonList.add(this.field_146809_s = new NodusGuiButton(1, width / 2 - 154, height - 52, 100, 20, I18n.format("selectServer.select", new Object[0])));
/*  81: 87 */     this.buttonList.add(new NodusGuiButton(4, width / 2 - 50, height - 52, 100, 20, I18n.format("selectServer.direct", new Object[0])));
/*  82: 88 */     this.buttonList.add(new NodusGuiButton(3, width / 2 + 4 + 50, height - 52, 100, 20, I18n.format("selectServer.add", new Object[0])));
/*  83: 89 */     this.buttonList.add(new NodusGuiButton(8, width / 2 + 4, height - 28, 70, 20, I18n.format("selectServer.refresh", new Object[0])));
/*  84: 90 */     this.buttonList.add(new NodusGuiButton(0, width / 2 + 4 + 76, height - 28, 75, 20, I18n.format("gui.cancel", new Object[0])));
/*  85: 91 */     func_146790_a(this.field_146803_h.func_148193_k());
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void updateScreen()
/*  89:    */   {
/*  90: 99 */     super.updateScreen();
/*  91:101 */     if (this.field_146799_A.getWasUpdated())
/*  92:    */     {
/*  93:103 */       List var1 = this.field_146799_A.getLanServers();
/*  94:104 */       this.field_146799_A.setWasNotUpdated();
/*  95:105 */       this.field_146803_h.func_148194_a(var1);
/*  96:    */     }
/*  97:108 */     this.field_146797_f.func_147223_a();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void onGuiClosed()
/* 101:    */   {
/* 102:116 */     Keyboard.enableRepeatEvents(false);
/* 103:118 */     if (this.field_146800_B != null)
/* 104:    */     {
/* 105:120 */       this.field_146800_B.interrupt();
/* 106:121 */       this.field_146800_B = null;
/* 107:    */     }
/* 108:124 */     this.field_146797_f.func_147226_b();
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 112:    */   {
/* 113:129 */     if (p_146284_1_.enabled)
/* 114:    */     {
/* 115:131 */       GuiListExtended.IGuiListEntry var2 = this.field_146803_h.func_148193_k() < 0 ? null : this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k());
/* 116:133 */       if ((p_146284_1_.id == 2) && ((var2 instanceof ServerListEntryNormal)))
/* 117:    */       {
/* 118:135 */         String var9 = ((ServerListEntryNormal)var2).func_148296_a().serverName;
/* 119:137 */         if (var9 != null)
/* 120:    */         {
/* 121:139 */           this.field_146807_u = true;
/* 122:140 */           String var4 = I18n.format("selectServer.deleteQuestion", new Object[0]);
/* 123:141 */           String var5 = "'" + var9 + "' " + I18n.format("selectServer.deleteWarning", new Object[0]);
/* 124:142 */           String var6 = I18n.format("selectServer.deleteButton", new Object[0]);
/* 125:143 */           String var7 = I18n.format("gui.cancel", new Object[0]);
/* 126:144 */           GuiYesNo var8 = new GuiYesNo(this, var4, var5, var6, var7, this.field_146803_h.func_148193_k());
/* 127:145 */           this.mc.displayGuiScreen(var8);
/* 128:    */         }
/* 129:    */       }
/* 130:148 */       else if (p_146284_1_.id == 1)
/* 131:    */       {
/* 132:150 */         func_146796_h();
/* 133:    */       }
/* 134:152 */       else if (p_146284_1_.id == 4)
/* 135:    */       {
/* 136:154 */         this.field_146813_x = true;
/* 137:155 */         this.mc.displayGuiScreen(new GuiScreenServerList(this, this.field_146811_z = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "")));
/* 138:    */       }
/* 139:157 */       else if (p_146284_1_.id == 3)
/* 140:    */       {
/* 141:159 */         this.field_146806_v = true;
/* 142:160 */         this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.field_146811_z = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "")));
/* 143:    */       }
/* 144:162 */       else if ((p_146284_1_.id == 7) && ((var2 instanceof ServerListEntryNormal)))
/* 145:    */       {
/* 146:164 */         this.field_146805_w = true;
/* 147:165 */         ServerData var3 = ((ServerListEntryNormal)var2).func_148296_a();
/* 148:166 */         this.field_146811_z = new ServerData(var3.serverName, var3.serverIP);
/* 149:167 */         this.field_146811_z.setHideAddress(var3.isHidingAddress());
/* 150:168 */         this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.field_146811_z));
/* 151:    */       }
/* 152:170 */       else if (p_146284_1_.id == 0)
/* 153:    */       {
/* 154:172 */         this.mc.displayGuiScreen(this.field_146798_g);
/* 155:    */       }
/* 156:174 */       else if (p_146284_1_.id == 8)
/* 157:    */       {
/* 158:176 */         func_146792_q();
/* 159:    */       }
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   private void func_146792_q()
/* 164:    */   {
/* 165:183 */     this.mc.displayGuiScreen(new GuiMultiplayer(this.field_146798_g));
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void confirmClicked(boolean par1, int par2)
/* 169:    */   {
/* 170:188 */     GuiListExtended.IGuiListEntry var3 = this.field_146803_h.func_148193_k() < 0 ? null : this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k());
/* 171:190 */     if (this.field_146807_u)
/* 172:    */     {
/* 173:192 */       this.field_146807_u = false;
/* 174:194 */       if ((par1) && ((var3 instanceof ServerListEntryNormal)))
/* 175:    */       {
/* 176:196 */         this.field_146804_i.removeServerData(this.field_146803_h.func_148193_k());
/* 177:197 */         this.field_146804_i.saveServerList();
/* 178:198 */         this.field_146803_h.func_148192_c(-1);
/* 179:199 */         this.field_146803_h.func_148195_a(this.field_146804_i);
/* 180:    */       }
/* 181:202 */       this.mc.displayGuiScreen(this);
/* 182:    */     }
/* 183:204 */     else if (this.field_146813_x)
/* 184:    */     {
/* 185:206 */       this.field_146813_x = false;
/* 186:208 */       if (par1) {
/* 187:210 */         func_146791_a(this.field_146811_z);
/* 188:    */       } else {
/* 189:214 */         this.mc.displayGuiScreen(this);
/* 190:    */       }
/* 191:    */     }
/* 192:217 */     else if (this.field_146806_v)
/* 193:    */     {
/* 194:219 */       this.field_146806_v = false;
/* 195:221 */       if (par1)
/* 196:    */       {
/* 197:223 */         this.field_146804_i.addServerData(this.field_146811_z);
/* 198:224 */         this.field_146804_i.saveServerList();
/* 199:225 */         this.field_146803_h.func_148192_c(-1);
/* 200:226 */         this.field_146803_h.func_148195_a(this.field_146804_i);
/* 201:    */       }
/* 202:229 */       this.mc.displayGuiScreen(this);
/* 203:    */     }
/* 204:231 */     else if (this.field_146805_w)
/* 205:    */     {
/* 206:233 */       this.field_146805_w = false;
/* 207:235 */       if ((par1) && ((var3 instanceof ServerListEntryNormal)))
/* 208:    */       {
/* 209:237 */         ServerData var4 = ((ServerListEntryNormal)var3).func_148296_a();
/* 210:238 */         var4.serverName = this.field_146811_z.serverName;
/* 211:239 */         var4.serverIP = this.field_146811_z.serverIP;
/* 212:240 */         var4.setHideAddress(this.field_146811_z.isHidingAddress());
/* 213:241 */         this.field_146804_i.saveServerList();
/* 214:242 */         this.field_146803_h.func_148195_a(this.field_146804_i);
/* 215:    */       }
/* 216:245 */       this.mc.displayGuiScreen(this);
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   protected void keyTyped(char par1, int par2)
/* 221:    */   {
/* 222:254 */     int var3 = this.field_146803_h.func_148193_k();
/* 223:255 */     GuiListExtended.IGuiListEntry var4 = var3 < 0 ? null : this.field_146803_h.func_148180_b(var3);
/* 224:257 */     if (par2 == 63) {
/* 225:259 */       func_146792_q();
/* 226:263 */     } else if (var3 >= 0)
/* 227:    */     {
/* 228:265 */       if (par2 == 200)
/* 229:    */       {
/* 230:267 */         if (isShiftKeyDown())
/* 231:    */         {
/* 232:269 */           if ((var3 > 0) && ((var4 instanceof ServerListEntryNormal)))
/* 233:    */           {
/* 234:271 */             this.field_146804_i.swapServers(var3, var3 - 1);
/* 235:272 */             func_146790_a(this.field_146803_h.func_148193_k() - 1);
/* 236:273 */             this.field_146803_h.func_148145_f(-this.field_146803_h.func_148146_j());
/* 237:274 */             this.field_146803_h.func_148195_a(this.field_146804_i);
/* 238:    */           }
/* 239:    */         }
/* 240:277 */         else if (var3 > 0)
/* 241:    */         {
/* 242:279 */           func_146790_a(this.field_146803_h.func_148193_k() - 1);
/* 243:280 */           this.field_146803_h.func_148145_f(-this.field_146803_h.func_148146_j());
/* 244:282 */           if ((this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k()) instanceof ServerListEntryLanScan)) {
/* 245:284 */             if (this.field_146803_h.func_148193_k() > 0)
/* 246:    */             {
/* 247:286 */               func_146790_a(this.field_146803_h.getSize() - 1);
/* 248:287 */               this.field_146803_h.func_148145_f(-this.field_146803_h.func_148146_j());
/* 249:    */             }
/* 250:    */             else
/* 251:    */             {
/* 252:291 */               func_146790_a(-1);
/* 253:    */             }
/* 254:    */           }
/* 255:    */         }
/* 256:    */         else
/* 257:    */         {
/* 258:297 */           func_146790_a(-1);
/* 259:    */         }
/* 260:    */       }
/* 261:300 */       else if (par2 == 208)
/* 262:    */       {
/* 263:302 */         if (isShiftKeyDown())
/* 264:    */         {
/* 265:304 */           if (var3 < this.field_146804_i.countServers() - 1)
/* 266:    */           {
/* 267:306 */             this.field_146804_i.swapServers(var3, var3 + 1);
/* 268:307 */             func_146790_a(var3 + 1);
/* 269:308 */             this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
/* 270:309 */             this.field_146803_h.func_148195_a(this.field_146804_i);
/* 271:    */           }
/* 272:    */         }
/* 273:312 */         else if (var3 < this.field_146803_h.getSize())
/* 274:    */         {
/* 275:314 */           func_146790_a(this.field_146803_h.func_148193_k() + 1);
/* 276:315 */           this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
/* 277:317 */           if ((this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k()) instanceof ServerListEntryLanScan)) {
/* 278:319 */             if (this.field_146803_h.func_148193_k() < this.field_146803_h.getSize() - 1)
/* 279:    */             {
/* 280:321 */               func_146790_a(this.field_146803_h.getSize() + 1);
/* 281:322 */               this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
/* 282:    */             }
/* 283:    */             else
/* 284:    */             {
/* 285:326 */               func_146790_a(-1);
/* 286:    */             }
/* 287:    */           }
/* 288:    */         }
/* 289:    */         else
/* 290:    */         {
/* 291:332 */           func_146790_a(-1);
/* 292:    */         }
/* 293:    */       }
/* 294:335 */       else if ((par2 != 28) && (par2 != 156)) {
/* 295:337 */         super.keyTyped(par1, par2);
/* 296:    */       } else {
/* 297:341 */         actionPerformed((NodusGuiButton)this.buttonList.get(2));
/* 298:    */       }
/* 299:    */     }
/* 300:    */     else {
/* 301:346 */       super.keyTyped(par1, par2);
/* 302:    */     }
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void drawScreen(int par1, int par2, float par3)
/* 306:    */   {
/* 307:356 */     this.field_146812_y = null;
/* 308:357 */     drawDefaultBackground();
/* 309:358 */     this.field_146803_h.func_148128_a(par1, par2, par3);
/* 310:359 */     drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.title", new Object[0]), width / 2, 20, 16777215);
/* 311:360 */     super.drawScreen(par1, par2, par3);
/* 312:362 */     if (this.field_146812_y != null) {
/* 313:364 */       func_146283_a(Lists.newArrayList(Splitter.on("\n").split(this.field_146812_y)), par1, par2);
/* 314:    */     }
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void func_146796_h()
/* 318:    */   {
/* 319:370 */     GuiListExtended.IGuiListEntry var1 = this.field_146803_h.func_148193_k() < 0 ? null : this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k());
/* 320:372 */     if ((var1 instanceof ServerListEntryNormal))
/* 321:    */     {
/* 322:374 */       func_146791_a(((ServerListEntryNormal)var1).func_148296_a());
/* 323:    */     }
/* 324:376 */     else if ((var1 instanceof ServerListEntryLanDetected))
/* 325:    */     {
/* 326:378 */       LanServerDetector.LanServer var2 = ((ServerListEntryLanDetected)var1).func_148289_a();
/* 327:379 */       func_146791_a(new ServerData(var2.getServerMotd(), var2.getServerIpPort()));
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   private void func_146791_a(ServerData p_146791_1_)
/* 332:    */   {
/* 333:385 */     this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, p_146791_1_));
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void func_146790_a(int p_146790_1_)
/* 337:    */   {
/* 338:390 */     this.field_146803_h.func_148192_c(p_146790_1_);
/* 339:391 */     GuiListExtended.IGuiListEntry var2 = p_146790_1_ < 0 ? null : this.field_146803_h.func_148180_b(p_146790_1_);
/* 340:392 */     this.field_146809_s.enabled = false;
/* 341:393 */     this.field_146810_r.enabled = false;
/* 342:394 */     this.field_146808_t.enabled = false;
/* 343:396 */     if ((var2 != null) && (!(var2 instanceof ServerListEntryLanScan)))
/* 344:    */     {
/* 345:398 */       this.field_146809_s.enabled = true;
/* 346:400 */       if ((var2 instanceof ServerListEntryNormal))
/* 347:    */       {
/* 348:402 */         this.field_146810_r.enabled = true;
/* 349:403 */         this.field_146808_t.enabled = true;
/* 350:    */       }
/* 351:    */     }
/* 352:    */   }
/* 353:    */   
/* 354:    */   public OldServerPinger func_146789_i()
/* 355:    */   {
/* 356:410 */     return this.field_146797_f;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void func_146793_a(String p_146793_1_)
/* 360:    */   {
/* 361:415 */     this.field_146812_y = p_146793_1_;
/* 362:    */   }
/* 363:    */   
/* 364:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 365:    */   {
/* 366:423 */     super.mouseClicked(par1, par2, par3);
/* 367:424 */     this.field_146803_h.func_148179_a(par1, par2, par3);
/* 368:    */   }
/* 369:    */   
/* 370:    */   protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_)
/* 371:    */   {
/* 372:429 */     super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
/* 373:430 */     this.field_146803_h.func_148181_b(p_146286_1_, p_146286_2_, p_146286_3_);
/* 374:    */   }
/* 375:    */   
/* 376:    */   public ServerList func_146795_p()
/* 377:    */   {
/* 378:435 */     return this.field_146804_i;
/* 379:    */   }
/* 380:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiMultiplayer
 * JD-Core Version:    0.7.0.1
 */