/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.List;
/*   5:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   6:    */ import net.minecraft.client.Minecraft;
/*   7:    */ import net.minecraft.client.gui.mco.GuiScreenBackup;
/*   8:    */ import net.minecraft.client.gui.mco.GuiScreenResetWorld;
/*   9:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*  10:    */ import net.minecraft.client.mco.McoClient;
/*  11:    */ import net.minecraft.client.mco.McoServer;
/*  12:    */ import net.minecraft.client.renderer.Tessellator;
/*  13:    */ import net.minecraft.client.resources.I18n;
/*  14:    */ import net.minecraft.util.Session;
/*  15:    */ import org.apache.logging.log4j.LogManager;
/*  16:    */ import org.apache.logging.log4j.Logger;
/*  17:    */ import org.lwjgl.input.Keyboard;
/*  18:    */ 
/*  19:    */ public class GuiScreenConfigureWorld
/*  20:    */   extends GuiScreen
/*  21:    */ {
/*  22: 22 */   private static final Logger logger = ;
/*  23:    */   private final GuiScreen field_146884_f;
/*  24:    */   private McoServer field_146885_g;
/*  25:    */   private SelectionListInvited field_146890_h;
/*  26:    */   private int field_146891_i;
/*  27:    */   private int field_146897_r;
/*  28:    */   private int field_146896_s;
/*  29: 29 */   private int field_146895_t = -1;
/*  30:    */   private String field_146894_u;
/*  31:    */   private NodusGuiButton field_146893_v;
/*  32:    */   private NodusGuiButton field_146892_w;
/*  33:    */   private NodusGuiButton field_146900_x;
/*  34:    */   private NodusGuiButton field_146899_y;
/*  35:    */   private NodusGuiButton field_146898_z;
/*  36:    */   private NodusGuiButton field_146886_A;
/*  37:    */   private NodusGuiButton field_146887_B;
/*  38:    */   private NodusGuiButton field_146888_C;
/*  39:    */   private boolean field_146883_D;
/*  40:    */   private static final String __OBFID = "CL_00000773";
/*  41:    */   
/*  42:    */   public GuiScreenConfigureWorld(GuiScreen par1GuiScreen, McoServer par2McoServer)
/*  43:    */   {
/*  44: 44 */     this.field_146884_f = par1GuiScreen;
/*  45: 45 */     this.field_146885_g = par2McoServer;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void updateScreen() {}
/*  49:    */   
/*  50:    */   public void initGui()
/*  51:    */   {
/*  52: 58 */     this.field_146891_i = (width / 2 - 200);
/*  53: 59 */     this.field_146897_r = 180;
/*  54: 60 */     this.field_146896_s = (width / 2);
/*  55: 61 */     Keyboard.enableRepeatEvents(true);
/*  56: 62 */     this.buttonList.clear();
/*  57: 64 */     if (this.field_146885_g.field_148808_d.equals("CLOSED"))
/*  58:    */     {
/*  59: 66 */       this.buttonList.add(this.field_146893_v = new NodusGuiButton(0, this.field_146891_i, func_146873_a(12), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.open", new Object[0])));
/*  60: 67 */       this.field_146893_v.enabled = (!this.field_146885_g.field_148819_h);
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64: 71 */       this.buttonList.add(this.field_146892_w = new NodusGuiButton(1, this.field_146891_i, func_146873_a(12), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.close", new Object[0])));
/*  65: 72 */       this.field_146892_w.enabled = (!this.field_146885_g.field_148819_h);
/*  66:    */     }
/*  67: 75 */     this.buttonList.add(this.field_146887_B = new NodusGuiButton(7, this.field_146891_i + this.field_146897_r / 2 + 2, func_146873_a(12), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.subscription", new Object[0])));
/*  68: 76 */     this.buttonList.add(this.field_146900_x = new NodusGuiButton(5, this.field_146891_i, func_146873_a(10), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.edit", new Object[0])));
/*  69: 77 */     this.buttonList.add(this.field_146899_y = new NodusGuiButton(6, this.field_146891_i + this.field_146897_r / 2 + 2, func_146873_a(10), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.reset", new Object[0])));
/*  70: 78 */     this.buttonList.add(this.field_146898_z = new NodusGuiButton(4, this.field_146896_s, func_146873_a(10), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.invite", new Object[0])));
/*  71: 79 */     this.buttonList.add(this.field_146886_A = new NodusGuiButton(3, this.field_146896_s + this.field_146897_r / 2 + 2, func_146873_a(10), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.uninvite", new Object[0])));
/*  72: 80 */     this.buttonList.add(this.field_146888_C = new NodusGuiButton(8, this.field_146896_s, func_146873_a(12), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.backup", new Object[0])));
/*  73: 81 */     this.buttonList.add(new NodusGuiButton(10, this.field_146896_s + this.field_146897_r / 2 + 2, func_146873_a(12), this.field_146897_r / 2 - 2, 20, I18n.format("gui.back", new Object[0])));
/*  74: 82 */     this.field_146890_h = new SelectionListInvited();
/*  75: 83 */     this.field_146900_x.enabled = (!this.field_146885_g.field_148819_h);
/*  76: 84 */     this.field_146899_y.enabled = (!this.field_146885_g.field_148819_h);
/*  77: 85 */     this.field_146898_z.enabled = (!this.field_146885_g.field_148819_h);
/*  78: 86 */     this.field_146886_A.enabled = (!this.field_146885_g.field_148819_h);
/*  79: 87 */     this.field_146888_C.enabled = (!this.field_146885_g.field_148819_h);
/*  80:    */   }
/*  81:    */   
/*  82:    */   private int func_146873_a(int p_146873_1_)
/*  83:    */   {
/*  84: 92 */     return 40 + p_146873_1_ * 13;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void onGuiClosed()
/*  88:    */   {
/*  89:100 */     Keyboard.enableRepeatEvents(false);
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  93:    */   {
/*  94:105 */     if (p_146284_1_.enabled) {
/*  95:107 */       if (p_146284_1_.id == 10)
/*  96:    */       {
/*  97:109 */         if (this.field_146883_D) {
/*  98:111 */           ((GuiScreenOnlineServers)this.field_146884_f).func_146670_h();
/*  99:    */         }
/* 100:114 */         this.mc.displayGuiScreen(this.field_146884_f);
/* 101:    */       }
/* 102:116 */       else if (p_146284_1_.id == 5)
/* 103:    */       {
/* 104:118 */         this.mc.displayGuiScreen(new GuiScreenEditOnlineWorld(this, this.field_146884_f, this.field_146885_g));
/* 105:    */       }
/* 106:120 */       else if (p_146284_1_.id == 1)
/* 107:    */       {
/* 108:122 */         String var2 = I18n.format("mco.configure.world.close.question.line1", new Object[0]);
/* 109:123 */         String var3 = I18n.format("mco.configure.world.close.question.line2", new Object[0]);
/* 110:124 */         this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Info, var2, var3, 1));
/* 111:    */       }
/* 112:126 */       else if (p_146284_1_.id == 0)
/* 113:    */       {
/* 114:128 */         func_146876_g();
/* 115:    */       }
/* 116:130 */       else if (p_146284_1_.id == 4)
/* 117:    */       {
/* 118:132 */         this.mc.displayGuiScreen(new GuiScreenInvite(this.field_146884_f, this, this.field_146885_g));
/* 119:    */       }
/* 120:134 */       else if (p_146284_1_.id == 3)
/* 121:    */       {
/* 122:136 */         func_146877_i();
/* 123:    */       }
/* 124:138 */       else if (p_146284_1_.id == 6)
/* 125:    */       {
/* 126:140 */         this.mc.displayGuiScreen(new GuiScreenResetWorld(this, this.field_146885_g));
/* 127:    */       }
/* 128:142 */       else if (p_146284_1_.id == 7)
/* 129:    */       {
/* 130:144 */         this.mc.displayGuiScreen(new GuiScreenSubscription(this, this.field_146885_g));
/* 131:    */       }
/* 132:146 */       else if (p_146284_1_.id == 8)
/* 133:    */       {
/* 134:148 */         this.mc.displayGuiScreen(new GuiScreenBackup(this, this.field_146885_g.field_148812_a));
/* 135:    */       }
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   private void func_146876_g()
/* 140:    */   {
/* 141:155 */     Session var1 = this.mc.getSession();
/* 142:156 */     McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 143:    */     try
/* 144:    */     {
/* 145:160 */       Boolean var3 = var2.func_148692_e(this.field_146885_g.field_148812_a);
/* 146:162 */       if (var3.booleanValue())
/* 147:    */       {
/* 148:164 */         this.field_146883_D = true;
/* 149:165 */         this.field_146885_g.field_148808_d = "OPEN";
/* 150:166 */         initGui();
/* 151:    */       }
/* 152:    */     }
/* 153:    */     catch (ExceptionMcoService var4)
/* 154:    */     {
/* 155:171 */       logger.error("Couldn't open world");
/* 156:    */     }
/* 157:    */     catch (IOException var5)
/* 158:    */     {
/* 159:175 */       logger.error("Could not parse response opening world");
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   private void func_146882_h()
/* 164:    */   {
/* 165:181 */     Session var1 = this.mc.getSession();
/* 166:182 */     McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 167:    */     try
/* 168:    */     {
/* 169:186 */       boolean var3 = var2.func_148700_f(this.field_146885_g.field_148812_a).booleanValue();
/* 170:188 */       if (var3)
/* 171:    */       {
/* 172:190 */         this.field_146883_D = true;
/* 173:191 */         this.field_146885_g.field_148808_d = "CLOSED";
/* 174:192 */         initGui();
/* 175:    */       }
/* 176:    */     }
/* 177:    */     catch (ExceptionMcoService var4)
/* 178:    */     {
/* 179:197 */       logger.error("Couldn't close world");
/* 180:    */     }
/* 181:    */     catch (IOException var5)
/* 182:    */     {
/* 183:201 */       logger.error("Could not parse response closing world");
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   private void func_146877_i()
/* 188:    */   {
/* 189:207 */     if ((this.field_146895_t >= 0) && (this.field_146895_t < this.field_146885_g.field_148806_f.size()))
/* 190:    */     {
/* 191:209 */       this.field_146894_u = ((String)this.field_146885_g.field_148806_f.get(this.field_146895_t));
/* 192:210 */       GuiYesNo var1 = new GuiYesNo(this, "Question", I18n.format("mco.configure.world.uninvite.question", new Object[0]) + " '" + this.field_146894_u + "'", 3);
/* 193:211 */       this.mc.displayGuiScreen(var1);
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void confirmClicked(boolean par1, int par2)
/* 198:    */   {
/* 199:217 */     if (par2 == 3)
/* 200:    */     {
/* 201:219 */       if (par1)
/* 202:    */       {
/* 203:221 */         Session var3 = this.mc.getSession();
/* 204:222 */         McoClient var4 = new McoClient(var3.getSessionID(), var3.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 205:    */         try
/* 206:    */         {
/* 207:226 */           var4.func_148694_a(this.field_146885_g.field_148812_a, this.field_146894_u);
/* 208:    */         }
/* 209:    */         catch (ExceptionMcoService var6)
/* 210:    */         {
/* 211:230 */           logger.error("Couldn't uninvite user");
/* 212:    */         }
/* 213:233 */         func_146875_d(this.field_146895_t);
/* 214:    */       }
/* 215:236 */       this.mc.displayGuiScreen(new GuiScreenConfigureWorld(this.field_146884_f, this.field_146885_g));
/* 216:    */     }
/* 217:239 */     if (par2 == 1)
/* 218:    */     {
/* 219:241 */       if (par1) {
/* 220:243 */         func_146882_h();
/* 221:    */       }
/* 222:246 */       this.mc.displayGuiScreen(this);
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   private void func_146875_d(int p_146875_1_)
/* 227:    */   {
/* 228:252 */     this.field_146885_g.field_148806_f.remove(p_146875_1_);
/* 229:    */   }
/* 230:    */   
/* 231:    */   protected void keyTyped(char par1, int par2) {}
/* 232:    */   
/* 233:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 234:    */   {
/* 235:265 */     super.mouseClicked(par1, par2, par3);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void drawScreen(int par1, int par2, float par3)
/* 239:    */   {
/* 240:273 */     drawDefaultBackground();
/* 241:274 */     this.field_146890_h.func_148446_a(par1, par2, par3);
/* 242:275 */     drawCenteredString(this.fontRendererObj, I18n.format("mco.configure.world.title", new Object[0]), width / 2, 17, 16777215);
/* 243:276 */     drawString(this.fontRendererObj, I18n.format("mco.configure.world.name", new Object[0]), this.field_146891_i, func_146873_a(1), 10526880);
/* 244:277 */     drawString(this.fontRendererObj, this.field_146885_g.func_148801_b(), this.field_146891_i, func_146873_a(2), 16777215);
/* 245:278 */     drawString(this.fontRendererObj, I18n.format("mco.configure.world.description", new Object[0]), this.field_146891_i, func_146873_a(4), 10526880);
/* 246:279 */     drawString(this.fontRendererObj, this.field_146885_g.func_148800_a(), this.field_146891_i, func_146873_a(5), 16777215);
/* 247:280 */     drawString(this.fontRendererObj, I18n.format("mco.configure.world.status", new Object[0]), this.field_146891_i, func_146873_a(7), 10526880);
/* 248:281 */     drawString(this.fontRendererObj, func_146870_p(), this.field_146891_i, func_146873_a(8), 16777215);
/* 249:282 */     drawString(this.fontRendererObj, I18n.format("mco.configure.world.invited", new Object[0]), this.field_146896_s, func_146873_a(1), 10526880);
/* 250:283 */     super.drawScreen(par1, par2, par3);
/* 251:    */   }
/* 252:    */   
/* 253:    */   private String func_146870_p()
/* 254:    */   {
/* 255:288 */     if (this.field_146885_g.field_148819_h) {
/* 256:290 */       return "Expired";
/* 257:    */     }
/* 258:294 */     String var1 = this.field_146885_g.field_148808_d.toLowerCase();
/* 259:295 */     return Character.toUpperCase(var1.charAt(0)) + var1.substring(1);
/* 260:    */   }
/* 261:    */   
/* 262:    */   class SelectionListInvited
/* 263:    */     extends SelectionListBase
/* 264:    */   {
/* 265:    */     private static final String __OBFID = "CL_00000775";
/* 266:    */     
/* 267:    */     public SelectionListInvited()
/* 268:    */     {
/* 269:305 */       super(GuiScreenConfigureWorld.this.field_146896_s, GuiScreenConfigureWorld.this.func_146873_a(2), GuiScreenConfigureWorld.this.field_146897_r, GuiScreenConfigureWorld.this.func_146873_a(9) - GuiScreenConfigureWorld.this.func_146873_a(2), 12);
/* 270:    */     }
/* 271:    */     
/* 272:    */     protected int func_148443_a()
/* 273:    */     {
/* 274:310 */       return GuiScreenConfigureWorld.this.field_146885_g.field_148806_f.size() + 1;
/* 275:    */     }
/* 276:    */     
/* 277:    */     protected void func_148449_a(int p_148449_1_, boolean p_148449_2_)
/* 278:    */     {
/* 279:315 */       if (p_148449_1_ < GuiScreenConfigureWorld.this.field_146885_g.field_148806_f.size()) {
/* 280:317 */         GuiScreenConfigureWorld.this.field_146895_t = p_148449_1_;
/* 281:    */       }
/* 282:    */     }
/* 283:    */     
/* 284:    */     protected boolean func_148444_a(int p_148444_1_)
/* 285:    */     {
/* 286:323 */       return p_148444_1_ == GuiScreenConfigureWorld.this.field_146895_t;
/* 287:    */     }
/* 288:    */     
/* 289:    */     protected int func_148447_b()
/* 290:    */     {
/* 291:328 */       return func_148443_a() * 12;
/* 292:    */     }
/* 293:    */     
/* 294:    */     protected void func_148445_c() {}
/* 295:    */     
/* 296:    */     protected void func_148442_a(int p_148442_1_, int p_148442_2_, int p_148442_3_, int p_148442_4_, Tessellator p_148442_5_)
/* 297:    */     {
/* 298:335 */       if (p_148442_1_ < GuiScreenConfigureWorld.this.field_146885_g.field_148806_f.size()) {
/* 299:337 */         func_148463_b(p_148442_1_, p_148442_2_, p_148442_3_, p_148442_4_, p_148442_5_);
/* 300:    */       }
/* 301:    */     }
/* 302:    */     
/* 303:    */     private void func_148463_b(int p_148463_1_, int p_148463_2_, int p_148463_3_, int p_148463_4_, Tessellator p_148463_5_)
/* 304:    */     {
/* 305:343 */       String var6 = (String)GuiScreenConfigureWorld.this.field_146885_g.field_148806_f.get(p_148463_1_);
/* 306:344 */       GuiScreenConfigureWorld.drawString(GuiScreenConfigureWorld.this.fontRendererObj, var6, p_148463_2_ + 2, p_148463_3_ + 1, 16777215);
/* 307:    */     }
/* 308:    */   }
/* 309:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenConfigureWorld
 * JD-Core Version:    0.7.0.1
 */