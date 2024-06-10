/*   1:    */ package net.minecraft.client.gui.achievement;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.Comparator;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  11:    */ import net.minecraft.client.Minecraft;
/*  12:    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*  13:    */ import net.minecraft.client.audio.SoundHandler;
/*  14:    */ import net.minecraft.client.gui.FontRenderer;
/*  15:    */ import net.minecraft.client.gui.GuiScreen;
/*  16:    */ import net.minecraft.client.gui.GuiSlot;
/*  17:    */ import net.minecraft.client.gui.IProgressMeter;
/*  18:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*  19:    */ import net.minecraft.client.renderer.RenderHelper;
/*  20:    */ import net.minecraft.client.renderer.Tessellator;
/*  21:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  22:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  23:    */ import net.minecraft.client.resources.I18n;
/*  24:    */ import net.minecraft.entity.EntityList;
/*  25:    */ import net.minecraft.entity.EntityList.EntityEggInfo;
/*  26:    */ import net.minecraft.item.Item;
/*  27:    */ import net.minecraft.item.ItemStack;
/*  28:    */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*  29:    */ import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
/*  30:    */ import net.minecraft.stats.StatBase;
/*  31:    */ import net.minecraft.stats.StatCrafting;
/*  32:    */ import net.minecraft.stats.StatFileWriter;
/*  33:    */ import net.minecraft.stats.StatList;
/*  34:    */ import net.minecraft.util.IChatComponent;
/*  35:    */ import net.minecraft.util.ResourceLocation;
/*  36:    */ import org.lwjgl.input.Mouse;
/*  37:    */ import org.lwjgl.opengl.GL11;
/*  38:    */ 
/*  39:    */ public class GuiStats
/*  40:    */   extends GuiScreen
/*  41:    */   implements IProgressMeter
/*  42:    */ {
/*  43: 35 */   private static RenderItem field_146544_g = new RenderItem();
/*  44:    */   protected GuiScreen field_146549_a;
/*  45: 37 */   protected String field_146542_f = "Select world";
/*  46:    */   private StatsGeneral field_146550_h;
/*  47:    */   private StatsItem field_146551_i;
/*  48:    */   private StatsBlock field_146548_r;
/*  49:    */   private StatsMobsList field_146547_s;
/*  50:    */   private StatFileWriter field_146546_t;
/*  51:    */   private GuiSlot field_146545_u;
/*  52: 44 */   private boolean field_146543_v = true;
/*  53:    */   private static final String __OBFID = "CL_00000723";
/*  54:    */   
/*  55:    */   public GuiStats(GuiScreen par1GuiScreen, StatFileWriter par2StatFileWriter)
/*  56:    */   {
/*  57: 49 */     this.field_146549_a = par1GuiScreen;
/*  58: 50 */     this.field_146546_t = par2StatFileWriter;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void initGui()
/*  62:    */   {
/*  63: 58 */     this.field_146542_f = I18n.format("gui.stats", new Object[0]);
/*  64: 59 */     this.mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void func_146541_h()
/*  68:    */   {
/*  69: 64 */     this.buttonList.add(new NodusGuiButton(0, width / 2 + 4, height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
/*  70: 65 */     this.buttonList.add(new NodusGuiButton(1, width / 2 - 160, height - 52, 80, 20, I18n.format("stat.generalButton", new Object[0])));
/*  71:    */     NodusGuiButton var1;
/*  72: 67 */     this.buttonList.add(var1 = new NodusGuiButton(2, width / 2 - 80, height - 52, 80, 20, I18n.format("stat.blocksButton", new Object[0])));
/*  73:    */     NodusGuiButton var2;
/*  74: 69 */     this.buttonList.add(var2 = new NodusGuiButton(3, width / 2, height - 52, 80, 20, I18n.format("stat.itemsButton", new Object[0])));
/*  75:    */     NodusGuiButton var3;
/*  76: 71 */     this.buttonList.add(var3 = new NodusGuiButton(4, width / 2 + 80, height - 52, 80, 20, I18n.format("stat.mobsButton", new Object[0])));
/*  77: 73 */     if (this.field_146548_r.getSize() == 0) {
/*  78: 75 */       var1.enabled = false;
/*  79:    */     }
/*  80: 78 */     if (this.field_146551_i.getSize() == 0) {
/*  81: 80 */       var2.enabled = false;
/*  82:    */     }
/*  83: 83 */     if (this.field_146547_s.getSize() == 0) {
/*  84: 85 */       var3.enabled = false;
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  89:    */   {
/*  90: 91 */     if (p_146284_1_.enabled) {
/*  91: 93 */       if (p_146284_1_.id == 0) {
/*  92: 95 */         this.mc.displayGuiScreen(this.field_146549_a);
/*  93: 97 */       } else if (p_146284_1_.id == 1) {
/*  94: 99 */         this.field_146545_u = this.field_146550_h;
/*  95:101 */       } else if (p_146284_1_.id == 3) {
/*  96:103 */         this.field_146545_u = this.field_146551_i;
/*  97:105 */       } else if (p_146284_1_.id == 2) {
/*  98:107 */         this.field_146545_u = this.field_146548_r;
/*  99:109 */       } else if (p_146284_1_.id == 4) {
/* 100:111 */         this.field_146545_u = this.field_146547_s;
/* 101:    */       } else {
/* 102:115 */         this.field_146545_u.func_148147_a(p_146284_1_);
/* 103:    */       }
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void drawScreen(int par1, int par2, float par3)
/* 108:    */   {
/* 109:125 */     if (this.field_146543_v)
/* 110:    */     {
/* 111:127 */       drawDefaultBackground();
/* 112:128 */       drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), width / 2, height / 2, 16777215);
/* 113:129 */       drawCenteredString(this.fontRendererObj, field_146510_b_[((int)(Minecraft.getSystemTime() / 150L % field_146510_b_.length))], width / 2, height / 2 + this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
/* 114:    */     }
/* 115:    */     else
/* 116:    */     {
/* 117:133 */       this.field_146545_u.func_148128_a(par1, par2, par3);
/* 118:134 */       drawCenteredString(this.fontRendererObj, this.field_146542_f, width / 2, 20, 16777215);
/* 119:135 */       super.drawScreen(par1, par2, par3);
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void func_146509_g()
/* 124:    */   {
/* 125:141 */     if (this.field_146543_v)
/* 126:    */     {
/* 127:143 */       this.field_146550_h = new StatsGeneral();
/* 128:144 */       this.field_146550_h.func_148134_d(1, 1);
/* 129:145 */       this.field_146551_i = new StatsItem();
/* 130:146 */       this.field_146551_i.func_148134_d(1, 1);
/* 131:147 */       this.field_146548_r = new StatsBlock();
/* 132:148 */       this.field_146548_r.func_148134_d(1, 1);
/* 133:149 */       this.field_146547_s = new StatsMobsList();
/* 134:150 */       this.field_146547_s.func_148134_d(1, 1);
/* 135:151 */       this.field_146545_u = this.field_146550_h;
/* 136:152 */       func_146541_h();
/* 137:153 */       this.field_146543_v = false;
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean doesGuiPauseGame()
/* 142:    */   {
/* 143:162 */     return !this.field_146543_v;
/* 144:    */   }
/* 145:    */   
/* 146:    */   private void func_146521_a(int p_146521_1_, int p_146521_2_, Item p_146521_3_)
/* 147:    */   {
/* 148:167 */     func_146531_b(p_146521_1_ + 1, p_146521_2_ + 1);
/* 149:168 */     GL11.glEnable(32826);
/* 150:169 */     RenderHelper.enableGUIStandardItemLighting();
/* 151:170 */     field_146544_g.renderItemIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), new ItemStack(p_146521_3_, 1, 0), p_146521_1_ + 2, p_146521_2_ + 2);
/* 152:171 */     RenderHelper.disableStandardItemLighting();
/* 153:172 */     GL11.glDisable(32826);
/* 154:    */   }
/* 155:    */   
/* 156:    */   private void func_146531_b(int p_146531_1_, int p_146531_2_)
/* 157:    */   {
/* 158:177 */     func_146527_c(p_146531_1_, p_146531_2_, 0, 0);
/* 159:    */   }
/* 160:    */   
/* 161:    */   private void func_146527_c(int p_146527_1_, int p_146527_2_, int p_146527_3_, int p_146527_4_)
/* 162:    */   {
/* 163:182 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 164:183 */     this.mc.getTextureManager().bindTexture(statIcons);
/* 165:184 */     float var5 = 0.007813F;
/* 166:185 */     float var6 = 0.007813F;
/* 167:186 */     boolean var7 = true;
/* 168:187 */     boolean var8 = true;
/* 169:188 */     Tessellator var9 = Tessellator.instance;
/* 170:189 */     var9.startDrawingQuads();
/* 171:190 */     var9.addVertexWithUV(p_146527_1_ + 0, p_146527_2_ + 18, zLevel, (p_146527_3_ + 0) * 0.007813F, (p_146527_4_ + 18) * 0.007813F);
/* 172:191 */     var9.addVertexWithUV(p_146527_1_ + 18, p_146527_2_ + 18, zLevel, (p_146527_3_ + 18) * 0.007813F, (p_146527_4_ + 18) * 0.007813F);
/* 173:192 */     var9.addVertexWithUV(p_146527_1_ + 18, p_146527_2_ + 0, zLevel, (p_146527_3_ + 18) * 0.007813F, (p_146527_4_ + 0) * 0.007813F);
/* 174:193 */     var9.addVertexWithUV(p_146527_1_ + 0, p_146527_2_ + 0, zLevel, (p_146527_3_ + 0) * 0.007813F, (p_146527_4_ + 0) * 0.007813F);
/* 175:194 */     var9.draw();
/* 176:    */   }
/* 177:    */   
/* 178:    */   class StatsMobsList
/* 179:    */     extends GuiSlot
/* 180:    */   {
/* 181:199 */     private final List field_148222_l = new ArrayList();
/* 182:    */     private static final String __OBFID = "CL_00000729";
/* 183:    */     
/* 184:    */     public StatsMobsList()
/* 185:    */     {
/* 186:204 */       super(GuiStats.width, GuiStats.height, 32, GuiStats.height - 64, GuiStats.this.fontRendererObj.FONT_HEIGHT * 4);
/* 187:205 */       func_148130_a(false);
/* 188:206 */       Iterator var2 = EntityList.entityEggs.values().iterator();
/* 189:208 */       while (var2.hasNext())
/* 190:    */       {
/* 191:210 */         EntityList.EntityEggInfo var3 = (EntityList.EntityEggInfo)var2.next();
/* 192:212 */         if ((GuiStats.this.field_146546_t.writeStat(var3.field_151512_d) > 0) || (GuiStats.this.field_146546_t.writeStat(var3.field_151513_e) > 0)) {
/* 193:214 */           this.field_148222_l.add(var3);
/* 194:    */         }
/* 195:    */       }
/* 196:    */     }
/* 197:    */     
/* 198:    */     protected int getSize()
/* 199:    */     {
/* 200:221 */       return this.field_148222_l.size();
/* 201:    */     }
/* 202:    */     
/* 203:    */     protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {}
/* 204:    */     
/* 205:    */     protected boolean isSelected(int p_148131_1_)
/* 206:    */     {
/* 207:228 */       return false;
/* 208:    */     }
/* 209:    */     
/* 210:    */     protected int func_148138_e()
/* 211:    */     {
/* 212:233 */       return getSize() * GuiStats.this.fontRendererObj.FONT_HEIGHT * 4;
/* 213:    */     }
/* 214:    */     
/* 215:    */     protected void drawBackground()
/* 216:    */     {
/* 217:238 */       GuiStats.this.drawDefaultBackground();
/* 218:    */     }
/* 219:    */     
/* 220:    */     protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_)
/* 221:    */     {
/* 222:243 */       EntityList.EntityEggInfo var8 = (EntityList.EntityEggInfo)this.field_148222_l.get(p_148126_1_);
/* 223:244 */       String var9 = I18n.format("entity." + EntityList.getStringFromID(var8.spawnedID) + ".name", new Object[0]);
/* 224:245 */       int var10 = GuiStats.this.field_146546_t.writeStat(var8.field_151512_d);
/* 225:246 */       int var11 = GuiStats.this.field_146546_t.writeStat(var8.field_151513_e);
/* 226:247 */       String var12 = I18n.format("stat.entityKills", new Object[] { Integer.valueOf(var10), var9 });
/* 227:248 */       String var13 = I18n.format("stat.entityKilledBy", new Object[] { var9, Integer.valueOf(var11) });
/* 228:250 */       if (var10 == 0) {
/* 229:252 */         var12 = I18n.format("stat.entityKills.none", new Object[] { var9 });
/* 230:    */       }
/* 231:255 */       if (var11 == 0) {
/* 232:257 */         var13 = I18n.format("stat.entityKilledBy.none", new Object[] { var9 });
/* 233:    */       }
/* 234:260 */       GuiStats.drawString(GuiStats.this.fontRendererObj, var9, p_148126_2_ + 2 - 10, p_148126_3_ + 1, 16777215);
/* 235:261 */       GuiStats.drawString(GuiStats.this.fontRendererObj, var12, p_148126_2_ + 2, p_148126_3_ + 1 + GuiStats.this.fontRendererObj.FONT_HEIGHT, var10 == 0 ? 6316128 : 9474192);
/* 236:262 */       GuiStats.drawString(GuiStats.this.fontRendererObj, var13, p_148126_2_ + 2, p_148126_3_ + 1 + GuiStats.this.fontRendererObj.FONT_HEIGHT * 2, var11 == 0 ? 6316128 : 9474192);
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   class StatsBlock
/* 241:    */     extends GuiStats.Stats
/* 242:    */   {
/* 243:    */     private static final String __OBFID = "CL_00000724";
/* 244:    */     
/* 245:    */     public StatsBlock()
/* 246:    */     {
/* 247:270 */       super();
/* 248:    */       
/* 249:272 */       this.field_148219_m = new ArrayList();
/* 250:273 */       Iterator var2 = StatList.objectMineStats.iterator();
/* 251:275 */       while (var2.hasNext())
/* 252:    */       {
/* 253:277 */         StatCrafting var3 = (StatCrafting)var2.next();
/* 254:278 */         boolean var4 = false;
/* 255:279 */         int var5 = Item.getIdFromItem(var3.func_150959_a());
/* 256:281 */         if (GuiStats.this.field_146546_t.writeStat(var3) > 0) {
/* 257:283 */           var4 = true;
/* 258:285 */         } else if ((StatList.objectUseStats[var5] != null) && (GuiStats.this.field_146546_t.writeStat(StatList.objectUseStats[var5]) > 0)) {
/* 259:287 */           var4 = true;
/* 260:289 */         } else if ((StatList.objectCraftStats[var5] != null) && (GuiStats.this.field_146546_t.writeStat(StatList.objectCraftStats[var5]) > 0)) {
/* 261:291 */           var4 = true;
/* 262:    */         }
/* 263:294 */         if (var4) {
/* 264:296 */           this.field_148219_m.add(var3);
/* 265:    */         }
/* 266:    */       }
/* 267:300 */       this.field_148216_n = new Comparator()
/* 268:    */       {
/* 269:    */         private static final String __OBFID = "CL_00000725";
/* 270:    */         
/* 271:    */         public int compare(StatCrafting p_148339_1_, StatCrafting p_148339_2_)
/* 272:    */         {
/* 273:305 */           int var3 = Item.getIdFromItem(p_148339_1_.func_150959_a());
/* 274:306 */           int var4 = Item.getIdFromItem(p_148339_2_.func_150959_a());
/* 275:307 */           StatBase var5 = null;
/* 276:308 */           StatBase var6 = null;
/* 277:310 */           if (GuiStats.StatsBlock.this.field_148217_o == 2)
/* 278:    */           {
/* 279:312 */             var5 = StatList.mineBlockStatArray[var3];
/* 280:313 */             var6 = StatList.mineBlockStatArray[var4];
/* 281:    */           }
/* 282:315 */           else if (GuiStats.StatsBlock.this.field_148217_o == 0)
/* 283:    */           {
/* 284:317 */             var5 = StatList.objectCraftStats[var3];
/* 285:318 */             var6 = StatList.objectCraftStats[var4];
/* 286:    */           }
/* 287:320 */           else if (GuiStats.StatsBlock.this.field_148217_o == 1)
/* 288:    */           {
/* 289:322 */             var5 = StatList.objectUseStats[var3];
/* 290:323 */             var6 = StatList.objectUseStats[var4];
/* 291:    */           }
/* 292:326 */           if ((var5 != null) || (var6 != null))
/* 293:    */           {
/* 294:328 */             if (var5 == null) {
/* 295:330 */               return 1;
/* 296:    */             }
/* 297:333 */             if (var6 == null) {
/* 298:335 */               return -1;
/* 299:    */             }
/* 300:338 */             int var7 = GuiStats.this.field_146546_t.writeStat(var5);
/* 301:339 */             int var8 = GuiStats.this.field_146546_t.writeStat(var6);
/* 302:341 */             if (var7 != var8) {
/* 303:343 */               return (var7 - var8) * GuiStats.StatsBlock.this.field_148215_p;
/* 304:    */             }
/* 305:    */           }
/* 306:347 */           return var3 - var4;
/* 307:    */         }
/* 308:    */         
/* 309:    */         public int compare(Object par1Obj, Object par2Obj)
/* 310:    */         {
/* 311:351 */           return compare((StatCrafting)par1Obj, (StatCrafting)par2Obj);
/* 312:    */         }
/* 313:    */       };
/* 314:    */     }
/* 315:    */     
/* 316:    */     protected void func_148129_a(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_)
/* 317:    */     {
/* 318:358 */       super.func_148129_a(p_148129_1_, p_148129_2_, p_148129_3_);
/* 319:360 */       if (this.field_148218_l == 0) {
/* 320:362 */         GuiStats.this.func_146527_c(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
/* 321:    */       } else {
/* 322:366 */         GuiStats.this.func_146527_c(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 18, 18);
/* 323:    */       }
/* 324:369 */       if (this.field_148218_l == 1) {
/* 325:371 */         GuiStats.this.func_146527_c(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
/* 326:    */       } else {
/* 327:375 */         GuiStats.this.func_146527_c(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 36, 18);
/* 328:    */       }
/* 329:378 */       if (this.field_148218_l == 2) {
/* 330:380 */         GuiStats.this.func_146527_c(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 54, 18);
/* 331:    */       } else {
/* 332:384 */         GuiStats.this.func_146527_c(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 54, 18);
/* 333:    */       }
/* 334:    */     }
/* 335:    */     
/* 336:    */     protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_)
/* 337:    */     {
/* 338:390 */       StatCrafting var8 = func_148211_c(p_148126_1_);
/* 339:391 */       Item var9 = var8.func_150959_a();
/* 340:392 */       GuiStats.this.func_146521_a(p_148126_2_ + 40, p_148126_3_, var9);
/* 341:393 */       int var10 = Item.getIdFromItem(var9);
/* 342:394 */       func_148209_a(StatList.objectCraftStats[var10], p_148126_2_ + 115, p_148126_3_, p_148126_1_ % 2 == 0);
/* 343:395 */       func_148209_a(StatList.objectUseStats[var10], p_148126_2_ + 165, p_148126_3_, p_148126_1_ % 2 == 0);
/* 344:396 */       func_148209_a(var8, p_148126_2_ + 215, p_148126_3_, p_148126_1_ % 2 == 0);
/* 345:    */     }
/* 346:    */     
/* 347:    */     protected String func_148210_b(int p_148210_1_)
/* 348:    */     {
/* 349:401 */       return p_148210_1_ == 1 ? "stat.used" : p_148210_1_ == 0 ? "stat.crafted" : "stat.mined";
/* 350:    */     }
/* 351:    */   }
/* 352:    */   
/* 353:    */   class StatsItem
/* 354:    */     extends GuiStats.Stats
/* 355:    */   {
/* 356:    */     private static final String __OBFID = "CL_00000727";
/* 357:    */     
/* 358:    */     public StatsItem()
/* 359:    */     {
/* 360:409 */       super();
/* 361:    */       
/* 362:411 */       this.field_148219_m = new ArrayList();
/* 363:412 */       Iterator var2 = StatList.itemStats.iterator();
/* 364:414 */       while (var2.hasNext())
/* 365:    */       {
/* 366:416 */         StatCrafting var3 = (StatCrafting)var2.next();
/* 367:417 */         boolean var4 = false;
/* 368:418 */         int var5 = Item.getIdFromItem(var3.func_150959_a());
/* 369:420 */         if (GuiStats.this.field_146546_t.writeStat(var3) > 0) {
/* 370:422 */           var4 = true;
/* 371:424 */         } else if ((StatList.objectBreakStats[var5] != null) && (GuiStats.this.field_146546_t.writeStat(StatList.objectBreakStats[var5]) > 0)) {
/* 372:426 */           var4 = true;
/* 373:428 */         } else if ((StatList.objectCraftStats[var5] != null) && (GuiStats.this.field_146546_t.writeStat(StatList.objectCraftStats[var5]) > 0)) {
/* 374:430 */           var4 = true;
/* 375:    */         }
/* 376:433 */         if (var4) {
/* 377:435 */           this.field_148219_m.add(var3);
/* 378:    */         }
/* 379:    */       }
/* 380:439 */       this.field_148216_n = new Comparator()
/* 381:    */       {
/* 382:    */         private static final String __OBFID = "CL_00000728";
/* 383:    */         
/* 384:    */         public int compare(StatCrafting p_148342_1_, StatCrafting p_148342_2_)
/* 385:    */         {
/* 386:444 */           int var3 = Item.getIdFromItem(p_148342_1_.func_150959_a());
/* 387:445 */           int var4 = Item.getIdFromItem(p_148342_2_.func_150959_a());
/* 388:446 */           StatBase var5 = null;
/* 389:447 */           StatBase var6 = null;
/* 390:449 */           if (GuiStats.StatsItem.this.field_148217_o == 0)
/* 391:    */           {
/* 392:451 */             var5 = StatList.objectBreakStats[var3];
/* 393:452 */             var6 = StatList.objectBreakStats[var4];
/* 394:    */           }
/* 395:454 */           else if (GuiStats.StatsItem.this.field_148217_o == 1)
/* 396:    */           {
/* 397:456 */             var5 = StatList.objectCraftStats[var3];
/* 398:457 */             var6 = StatList.objectCraftStats[var4];
/* 399:    */           }
/* 400:459 */           else if (GuiStats.StatsItem.this.field_148217_o == 2)
/* 401:    */           {
/* 402:461 */             var5 = StatList.objectUseStats[var3];
/* 403:462 */             var6 = StatList.objectUseStats[var4];
/* 404:    */           }
/* 405:465 */           if ((var5 != null) || (var6 != null))
/* 406:    */           {
/* 407:467 */             if (var5 == null) {
/* 408:469 */               return 1;
/* 409:    */             }
/* 410:472 */             if (var6 == null) {
/* 411:474 */               return -1;
/* 412:    */             }
/* 413:477 */             int var7 = GuiStats.this.field_146546_t.writeStat(var5);
/* 414:478 */             int var8 = GuiStats.this.field_146546_t.writeStat(var6);
/* 415:480 */             if (var7 != var8) {
/* 416:482 */               return (var7 - var8) * GuiStats.StatsItem.this.field_148215_p;
/* 417:    */             }
/* 418:    */           }
/* 419:486 */           return var3 - var4;
/* 420:    */         }
/* 421:    */         
/* 422:    */         public int compare(Object par1Obj, Object par2Obj)
/* 423:    */         {
/* 424:490 */           return compare((StatCrafting)par1Obj, (StatCrafting)par2Obj);
/* 425:    */         }
/* 426:    */       };
/* 427:    */     }
/* 428:    */     
/* 429:    */     protected void func_148129_a(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_)
/* 430:    */     {
/* 431:497 */       super.func_148129_a(p_148129_1_, p_148129_2_, p_148129_3_);
/* 432:499 */       if (this.field_148218_l == 0) {
/* 433:501 */         GuiStats.this.func_146527_c(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 72, 18);
/* 434:    */       } else {
/* 435:505 */         GuiStats.this.func_146527_c(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 72, 18);
/* 436:    */       }
/* 437:508 */       if (this.field_148218_l == 1) {
/* 438:510 */         GuiStats.this.func_146527_c(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
/* 439:    */       } else {
/* 440:514 */         GuiStats.this.func_146527_c(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 18, 18);
/* 441:    */       }
/* 442:517 */       if (this.field_148218_l == 2) {
/* 443:519 */         GuiStats.this.func_146527_c(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
/* 444:    */       } else {
/* 445:523 */         GuiStats.this.func_146527_c(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 36, 18);
/* 446:    */       }
/* 447:    */     }
/* 448:    */     
/* 449:    */     protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_)
/* 450:    */     {
/* 451:529 */       StatCrafting var8 = func_148211_c(p_148126_1_);
/* 452:530 */       Item var9 = var8.func_150959_a();
/* 453:531 */       GuiStats.this.func_146521_a(p_148126_2_ + 40, p_148126_3_, var9);
/* 454:532 */       int var10 = Item.getIdFromItem(var9);
/* 455:533 */       func_148209_a(StatList.objectBreakStats[var10], p_148126_2_ + 115, p_148126_3_, p_148126_1_ % 2 == 0);
/* 456:534 */       func_148209_a(StatList.objectCraftStats[var10], p_148126_2_ + 165, p_148126_3_, p_148126_1_ % 2 == 0);
/* 457:535 */       func_148209_a(var8, p_148126_2_ + 215, p_148126_3_, p_148126_1_ % 2 == 0);
/* 458:    */     }
/* 459:    */     
/* 460:    */     protected String func_148210_b(int p_148210_1_)
/* 461:    */     {
/* 462:540 */       return p_148210_1_ == 2 ? "stat.used" : p_148210_1_ == 1 ? "stat.crafted" : "stat.depleted";
/* 463:    */     }
/* 464:    */   }
/* 465:    */   
/* 466:    */   class StatsGeneral
/* 467:    */     extends GuiSlot
/* 468:    */   {
/* 469:    */     private static final String __OBFID = "CL_00000726";
/* 470:    */     
/* 471:    */     public StatsGeneral()
/* 472:    */     {
/* 473:550 */       super(GuiStats.width, GuiStats.height, 32, GuiStats.height - 64, 10);
/* 474:551 */       func_148130_a(false);
/* 475:    */     }
/* 476:    */     
/* 477:    */     protected int getSize()
/* 478:    */     {
/* 479:556 */       return StatList.generalStats.size();
/* 480:    */     }
/* 481:    */     
/* 482:    */     protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {}
/* 483:    */     
/* 484:    */     protected boolean isSelected(int p_148131_1_)
/* 485:    */     {
/* 486:563 */       return false;
/* 487:    */     }
/* 488:    */     
/* 489:    */     protected int func_148138_e()
/* 490:    */     {
/* 491:568 */       return getSize() * 10;
/* 492:    */     }
/* 493:    */     
/* 494:    */     protected void drawBackground()
/* 495:    */     {
/* 496:573 */       GuiStats.this.drawDefaultBackground();
/* 497:    */     }
/* 498:    */     
/* 499:    */     protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_)
/* 500:    */     {
/* 501:578 */       StatBase var8 = (StatBase)StatList.generalStats.get(p_148126_1_);
/* 502:579 */       GuiStats.drawString(GuiStats.this.fontRendererObj, var8.func_150951_e().getUnformattedText(), p_148126_2_ + 2, p_148126_3_ + 1, p_148126_1_ % 2 == 0 ? 16777215 : 9474192);
/* 503:580 */       String var9 = var8.func_75968_a(GuiStats.this.field_146546_t.writeStat(var8));
/* 504:581 */       GuiStats.drawString(GuiStats.this.fontRendererObj, var9, p_148126_2_ + 2 + 213 - GuiStats.this.fontRendererObj.getStringWidth(var9), p_148126_3_ + 1, p_148126_1_ % 2 == 0 ? 16777215 : 9474192);
/* 505:    */     }
/* 506:    */   }
/* 507:    */   
/* 508:    */   abstract class Stats
/* 509:    */     extends GuiSlot
/* 510:    */   {
/* 511:587 */     protected int field_148218_l = -1;
/* 512:    */     protected List field_148219_m;
/* 513:    */     protected Comparator field_148216_n;
/* 514:590 */     protected int field_148217_o = -1;
/* 515:    */     protected int field_148215_p;
/* 516:    */     private static final String __OBFID = "CL_00000730";
/* 517:    */     
/* 518:    */     protected Stats()
/* 519:    */     {
/* 520:596 */       super(GuiStats.width, GuiStats.height, 32, GuiStats.height - 64, 20);
/* 521:597 */       func_148130_a(false);
/* 522:598 */       func_148133_a(true, 20);
/* 523:    */     }
/* 524:    */     
/* 525:    */     protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {}
/* 526:    */     
/* 527:    */     protected boolean isSelected(int p_148131_1_)
/* 528:    */     {
/* 529:605 */       return false;
/* 530:    */     }
/* 531:    */     
/* 532:    */     protected void drawBackground()
/* 533:    */     {
/* 534:610 */       GuiStats.this.drawDefaultBackground();
/* 535:    */     }
/* 536:    */     
/* 537:    */     protected void func_148129_a(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_)
/* 538:    */     {
/* 539:615 */       if (!Mouse.isButtonDown(0)) {
/* 540:617 */         this.field_148218_l = -1;
/* 541:    */       }
/* 542:620 */       if (this.field_148218_l == 0) {
/* 543:622 */         GuiStats.this.func_146527_c(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 0);
/* 544:    */       } else {
/* 545:626 */         GuiStats.this.func_146527_c(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 18);
/* 546:    */       }
/* 547:629 */       if (this.field_148218_l == 1) {
/* 548:631 */         GuiStats.this.func_146527_c(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 0);
/* 549:    */       } else {
/* 550:635 */         GuiStats.this.func_146527_c(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 18);
/* 551:    */       }
/* 552:638 */       if (this.field_148218_l == 2) {
/* 553:640 */         GuiStats.this.func_146527_c(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 0);
/* 554:    */       } else {
/* 555:644 */         GuiStats.this.func_146527_c(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 18);
/* 556:    */       }
/* 557:647 */       if (this.field_148217_o != -1)
/* 558:    */       {
/* 559:649 */         short var4 = 79;
/* 560:650 */         byte var5 = 18;
/* 561:652 */         if (this.field_148217_o == 1) {
/* 562:654 */           var4 = 129;
/* 563:656 */         } else if (this.field_148217_o == 2) {
/* 564:658 */           var4 = 179;
/* 565:    */         }
/* 566:661 */         if (this.field_148215_p == 1) {
/* 567:663 */           var5 = 36;
/* 568:    */         }
/* 569:666 */         GuiStats.this.func_146527_c(p_148129_1_ + var4, p_148129_2_ + 1, var5, 0);
/* 570:    */       }
/* 571:    */     }
/* 572:    */     
/* 573:    */     protected void func_148132_a(int p_148132_1_, int p_148132_2_)
/* 574:    */     {
/* 575:672 */       this.field_148218_l = -1;
/* 576:674 */       if ((p_148132_1_ >= 79) && (p_148132_1_ < 115)) {
/* 577:676 */         this.field_148218_l = 0;
/* 578:678 */       } else if ((p_148132_1_ >= 129) && (p_148132_1_ < 165)) {
/* 579:680 */         this.field_148218_l = 1;
/* 580:682 */       } else if ((p_148132_1_ >= 179) && (p_148132_1_ < 215)) {
/* 581:684 */         this.field_148218_l = 2;
/* 582:    */       }
/* 583:687 */       if (this.field_148218_l >= 0)
/* 584:    */       {
/* 585:689 */         func_148212_h(this.field_148218_l);
/* 586:690 */         GuiStats.this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/* 587:    */       }
/* 588:    */     }
/* 589:    */     
/* 590:    */     protected final int getSize()
/* 591:    */     {
/* 592:696 */       return this.field_148219_m.size();
/* 593:    */     }
/* 594:    */     
/* 595:    */     protected final StatCrafting func_148211_c(int p_148211_1_)
/* 596:    */     {
/* 597:701 */       return (StatCrafting)this.field_148219_m.get(p_148211_1_);
/* 598:    */     }
/* 599:    */     
/* 600:    */     protected abstract String func_148210_b(int paramInt);
/* 601:    */     
/* 602:    */     protected void func_148209_a(StatBase p_148209_1_, int p_148209_2_, int p_148209_3_, boolean p_148209_4_)
/* 603:    */     {
/* 604:710 */       if (p_148209_1_ != null)
/* 605:    */       {
/* 606:712 */         String var5 = p_148209_1_.func_75968_a(GuiStats.this.field_146546_t.writeStat(p_148209_1_));
/* 607:713 */         GuiStats.drawString(GuiStats.this.fontRendererObj, var5, p_148209_2_ - GuiStats.this.fontRendererObj.getStringWidth(var5), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
/* 608:    */       }
/* 609:    */       else
/* 610:    */       {
/* 611:717 */         String var5 = "-";
/* 612:718 */         GuiStats.drawString(GuiStats.this.fontRendererObj, var5, p_148209_2_ - GuiStats.this.fontRendererObj.getStringWidth(var5), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
/* 613:    */       }
/* 614:    */     }
/* 615:    */     
/* 616:    */     protected void func_148142_b(int p_148142_1_, int p_148142_2_)
/* 617:    */     {
/* 618:724 */       if ((p_148142_2_ >= this.field_148153_b) && (p_148142_2_ <= this.field_148154_c))
/* 619:    */       {
/* 620:726 */         int var3 = func_148124_c(p_148142_1_, p_148142_2_);
/* 621:727 */         int var4 = this.field_148155_a / 2 - 92 - 16;
/* 622:729 */         if (var3 >= 0)
/* 623:    */         {
/* 624:731 */           if ((p_148142_1_ < var4 + 40) || (p_148142_1_ > var4 + 40 + 20)) {
/* 625:733 */             return;
/* 626:    */           }
/* 627:736 */           StatCrafting var5 = func_148211_c(var3);
/* 628:737 */           func_148213_a(var5, p_148142_1_, p_148142_2_);
/* 629:    */         }
/* 630:    */         else
/* 631:    */         {
/* 632:741 */           String var9 = "";
/* 633:743 */           if ((p_148142_1_ >= var4 + 115 - 18) && (p_148142_1_ <= var4 + 115))
/* 634:    */           {
/* 635:745 */             var9 = func_148210_b(0);
/* 636:    */           }
/* 637:747 */           else if ((p_148142_1_ >= var4 + 165 - 18) && (p_148142_1_ <= var4 + 165))
/* 638:    */           {
/* 639:749 */             var9 = func_148210_b(1);
/* 640:    */           }
/* 641:    */           else
/* 642:    */           {
/* 643:753 */             if ((p_148142_1_ < var4 + 215 - 18) || (p_148142_1_ > var4 + 215)) {
/* 644:755 */               return;
/* 645:    */             }
/* 646:758 */             var9 = func_148210_b(2);
/* 647:    */           }
/* 648:761 */           var9 = I18n.format(var9, new Object[0]).trim();
/* 649:763 */           if (var9.length() > 0)
/* 650:    */           {
/* 651:765 */             int var6 = p_148142_1_ + 12;
/* 652:766 */             int var7 = p_148142_2_ - 12;
/* 653:767 */             int var8 = GuiStats.this.fontRendererObj.getStringWidth(var9);
/* 654:768 */             GuiStats.this.drawGradientRect(var6 - 3, var7 - 3, var6 + var8 + 3, var7 + 8 + 3, -1073741824, -1073741824);
/* 655:769 */             GuiStats.this.fontRendererObj.drawStringWithShadow(var9, var6, var7, -1);
/* 656:    */           }
/* 657:    */         }
/* 658:    */       }
/* 659:    */     }
/* 660:    */     
/* 661:    */     protected void func_148213_a(StatCrafting p_148213_1_, int p_148213_2_, int p_148213_3_)
/* 662:    */     {
/* 663:777 */       if (p_148213_1_ != null)
/* 664:    */       {
/* 665:779 */         Item var4 = p_148213_1_.func_150959_a();
/* 666:780 */         String var5 = I18n.format(new StringBuilder(String.valueOf(var4.getUnlocalizedName())).append(".name").toString(), new Object[0]).trim();
/* 667:782 */         if (var5.length() > 0)
/* 668:    */         {
/* 669:784 */           int var6 = p_148213_2_ + 12;
/* 670:785 */           int var7 = p_148213_3_ - 12;
/* 671:786 */           int var8 = GuiStats.this.fontRendererObj.getStringWidth(var5);
/* 672:787 */           GuiStats.this.drawGradientRect(var6 - 3, var7 - 3, var6 + var8 + 3, var7 + 8 + 3, -1073741824, -1073741824);
/* 673:788 */           GuiStats.this.fontRendererObj.drawStringWithShadow(var5, var6, var7, -1);
/* 674:    */         }
/* 675:    */       }
/* 676:    */     }
/* 677:    */     
/* 678:    */     protected void func_148212_h(int p_148212_1_)
/* 679:    */     {
/* 680:795 */       if (p_148212_1_ != this.field_148217_o)
/* 681:    */       {
/* 682:797 */         this.field_148217_o = p_148212_1_;
/* 683:798 */         this.field_148215_p = -1;
/* 684:    */       }
/* 685:800 */       else if (this.field_148215_p == -1)
/* 686:    */       {
/* 687:802 */         this.field_148215_p = 1;
/* 688:    */       }
/* 689:    */       else
/* 690:    */       {
/* 691:806 */         this.field_148217_o = -1;
/* 692:807 */         this.field_148215_p = 0;
/* 693:    */       }
/* 694:810 */       Collections.sort(this.field_148219_m, this.field_148216_n);
/* 695:    */     }
/* 696:    */   }
/* 697:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.achievement.GuiStats
 * JD-Core Version:    0.7.0.1
 */