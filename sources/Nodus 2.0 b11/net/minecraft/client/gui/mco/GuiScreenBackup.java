/*   1:    */ package net.minecraft.client.gui.mco;
/*   2:    */ 
/*   3:    */ import java.awt.Toolkit;
/*   4:    */ import java.awt.datatransfer.Clipboard;
/*   5:    */ import java.awt.datatransfer.StringSelection;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.net.URI;
/*   8:    */ import java.text.DateFormat;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.concurrent.atomic.AtomicInteger;
/*  13:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  14:    */ import net.minecraft.client.Minecraft;
/*  15:    */ import net.minecraft.client.gui.GuiScreen;
/*  16:    */ import net.minecraft.client.gui.GuiScreenConfigureWorld;
/*  17:    */ import net.minecraft.client.gui.GuiScreenConfirmation;
/*  18:    */ import net.minecraft.client.gui.GuiScreenConfirmation.ConfirmationType;
/*  19:    */ import net.minecraft.client.gui.GuiScreenLongRunningTask;
/*  20:    */ import net.minecraft.client.gui.GuiScreenSelectLocation;
/*  21:    */ import net.minecraft.client.gui.TaskLongRunning;
/*  22:    */ import net.minecraft.client.mco.Backup;
/*  23:    */ import net.minecraft.client.mco.BackupList;
/*  24:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*  25:    */ import net.minecraft.client.mco.McoClient;
/*  26:    */ import net.minecraft.client.renderer.Tessellator;
/*  27:    */ import net.minecraft.client.resources.I18n;
/*  28:    */ import net.minecraft.server.MinecraftServer;
/*  29:    */ import net.minecraft.util.Session;
/*  30:    */ import org.apache.logging.log4j.LogManager;
/*  31:    */ import org.apache.logging.log4j.Logger;
/*  32:    */ import org.lwjgl.input.Keyboard;
/*  33:    */ 
/*  34:    */ public class GuiScreenBackup
/*  35:    */   extends GuiScreen
/*  36:    */ {
/*  37: 36 */   private static final AtomicInteger field_146845_a = new AtomicInteger(0);
/*  38: 37 */   private static final Logger logger = LogManager.getLogger();
/*  39:    */   private final GuiScreenConfigureWorld field_146842_g;
/*  40:    */   private final long field_146846_h;
/*  41: 40 */   private List field_146847_i = Collections.emptyList();
/*  42:    */   private SelectionList field_146844_r;
/*  43: 42 */   private int field_146843_s = -1;
/*  44:    */   private static final String __OBFID = "CL_00000766";
/*  45:    */   
/*  46:    */   public GuiScreenBackup(GuiScreenConfigureWorld par1GuiScreenConfigureWorld, long par2)
/*  47:    */   {
/*  48: 47 */     this.field_146842_g = par1GuiScreenConfigureWorld;
/*  49: 48 */     this.field_146846_h = par2;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void initGui()
/*  53:    */   {
/*  54: 56 */     Keyboard.enableRepeatEvents(true);
/*  55: 57 */     this.buttonList.clear();
/*  56: 58 */     this.field_146844_r = new SelectionList();
/*  57: 59 */     new Thread("MCO Backup Requester #" + field_146845_a.incrementAndGet())
/*  58:    */     {
/*  59:    */       private static final String __OBFID = "CL_00000767";
/*  60:    */       
/*  61:    */       public void run()
/*  62:    */       {
/*  63: 64 */         Session var1 = GuiScreenBackup.this.mc.getSession();
/*  64: 65 */         McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/*  65:    */         try
/*  66:    */         {
/*  67: 69 */           GuiScreenBackup.this.field_146847_i = var2.func_148704_d(GuiScreenBackup.this.field_146846_h).theBackupList;
/*  68:    */         }
/*  69:    */         catch (ExceptionMcoService var4)
/*  70:    */         {
/*  71: 73 */           GuiScreenBackup.logger.error("Couldn't request backups", var4);
/*  72:    */         }
/*  73:    */       }
/*  74: 76 */     }.start();
/*  75: 77 */     func_146840_h();
/*  76:    */   }
/*  77:    */   
/*  78:    */   private void func_146840_h()
/*  79:    */   {
/*  80: 82 */     this.buttonList.add(new NodusGuiButton(2, width / 2 + 6, height - 52, 153, 20, I18n.format("mco.backup.button.download", new Object[0])));
/*  81: 83 */     this.buttonList.add(new NodusGuiButton(1, width / 2 - 154, height - 52, 153, 20, I18n.format("mco.backup.button.restore", new Object[0])));
/*  82: 84 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 74, height - 52 + 25, 153, 20, I18n.format("gui.back", new Object[0])));
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void updateScreen()
/*  86:    */   {
/*  87: 92 */     super.updateScreen();
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  91:    */   {
/*  92: 97 */     if (p_146284_1_.enabled) {
/*  93: 99 */       if (p_146284_1_.id == 1) {
/*  94:101 */         func_146827_i();
/*  95:103 */       } else if (p_146284_1_.id == 0) {
/*  96:105 */         this.mc.displayGuiScreen(this.field_146842_g);
/*  97:107 */       } else if (p_146284_1_.id == 2) {
/*  98:109 */         func_146826_p();
/*  99:    */       } else {
/* 100:113 */         this.field_146844_r.func_148357_a(p_146284_1_);
/* 101:    */       }
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   private void func_146827_i()
/* 106:    */   {
/* 107:120 */     if ((this.field_146843_s >= 0) && (this.field_146843_s < this.field_146847_i.size()))
/* 108:    */     {
/* 109:122 */       Date var1 = ((Backup)this.field_146847_i.get(this.field_146843_s)).field_148778_b;
/* 110:123 */       String var2 = DateFormat.getDateTimeInstance(3, 3).format(var1);
/* 111:124 */       String var3 = func_146829_a(Long.valueOf(System.currentTimeMillis() - var1.getTime()));
/* 112:125 */       String var4 = I18n.format("mco.configure.world.restore.question.line1", new Object[0]) + " '" + var2 + "' (" + var3 + ")";
/* 113:126 */       String var5 = I18n.format("mco.configure.world.restore.question.line2", new Object[0]);
/* 114:127 */       this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Warning, var4, var5, 1));
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void func_146826_p()
/* 119:    */   {
/* 120:133 */     String var1 = I18n.format("mco.configure.world.restore.download.question.line1", new Object[0]);
/* 121:134 */     String var2 = I18n.format("mco.configure.world.restore.download.question.line2", new Object[0]);
/* 122:135 */     this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Info, var1, var2, 2));
/* 123:    */   }
/* 124:    */   
/* 125:    */   private void func_146821_q()
/* 126:    */   {
/* 127:140 */     Session var1 = this.mc.getSession();
/* 128:141 */     McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 129:    */     try
/* 130:    */     {
/* 131:145 */       String var3 = var2.func_148708_h(this.field_146846_h);
/* 132:146 */       Clipboard var4 = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 133:147 */       var4.setContents(new StringSelection(var3), null);
/* 134:148 */       func_146823_a(var3);
/* 135:    */     }
/* 136:    */     catch (ExceptionMcoService var5)
/* 137:    */     {
/* 138:152 */       logger.error("Couldn't download world data");
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   private void func_146823_a(String p_146823_1_)
/* 143:    */   {
/* 144:    */     try
/* 145:    */     {
/* 146:160 */       URI var2 = new URI(p_146823_1_);
/* 147:161 */       Class var3 = Class.forName("java.awt.Desktop");
/* 148:162 */       Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 149:163 */       var3.getMethod("browse", new Class[] { URI.class }).invoke(var4, new Object[] { var2 });
/* 150:    */     }
/* 151:    */     catch (Throwable var5)
/* 152:    */     {
/* 153:167 */       logger.error("Couldn't open link");
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void confirmClicked(boolean par1, int par2)
/* 158:    */   {
/* 159:173 */     if ((par1) && (par2 == 1)) {
/* 160:175 */       func_146839_r();
/* 161:177 */     } else if ((par1) && (par2 == 2)) {
/* 162:179 */       func_146821_q();
/* 163:    */     } else {
/* 164:183 */       this.mc.displayGuiScreen(this);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   private void func_146839_r()
/* 169:    */   {
/* 170:189 */     Backup var1 = (Backup)this.field_146847_i.get(this.field_146843_s);
/* 171:190 */     RestoreTask var2 = new RestoreTask(var1, null);
/* 172:191 */     GuiScreenLongRunningTask var3 = new GuiScreenLongRunningTask(this.mc, this.field_146842_g, var2);
/* 173:192 */     var3.func_146902_g();
/* 174:193 */     this.mc.displayGuiScreen(var3);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void drawScreen(int par1, int par2, float par3)
/* 178:    */   {
/* 179:201 */     drawDefaultBackground();
/* 180:202 */     this.field_146844_r.func_148350_a(par1, par2, par3);
/* 181:203 */     drawCenteredString(this.fontRendererObj, I18n.format("mco.backup.title", new Object[0]), width / 2, 20, 16777215);
/* 182:204 */     super.drawScreen(par1, par2, par3);
/* 183:    */   }
/* 184:    */   
/* 185:    */   private String func_146829_a(Long p_146829_1_)
/* 186:    */   {
/* 187:209 */     if (p_146829_1_.longValue() < 0L) {
/* 188:211 */       return "right now";
/* 189:    */     }
/* 190:215 */     long var2 = p_146829_1_.longValue() / 1000L;
/* 191:217 */     if (var2 < 60L) {
/* 192:219 */       return (var2 == 1L ? "1 second" : new StringBuilder(String.valueOf(var2)).append(" seconds").toString()) + " ago";
/* 193:    */     }
/* 194:225 */     if (var2 < 3600L)
/* 195:    */     {
/* 196:227 */       long var4 = var2 / 60L;
/* 197:228 */       return (var4 == 1L ? "1 minute" : new StringBuilder(String.valueOf(var4)).append(" minutes").toString()) + " ago";
/* 198:    */     }
/* 199:230 */     if (var2 < 86400L)
/* 200:    */     {
/* 201:232 */       long var4 = var2 / 3600L;
/* 202:233 */       return (var4 == 1L ? "1 hour" : new StringBuilder(String.valueOf(var4)).append(" hours").toString()) + " ago";
/* 203:    */     }
/* 204:237 */     long var4 = var2 / 86400L;
/* 205:238 */     return (var4 == 1L ? "1 day" : new StringBuilder(String.valueOf(var4)).append(" days").toString()) + " ago";
/* 206:    */   }
/* 207:    */   
/* 208:    */   class RestoreTask
/* 209:    */     extends TaskLongRunning
/* 210:    */   {
/* 211:    */     private final Backup field_148424_c;
/* 212:    */     private static final String __OBFID = "CL_00000769";
/* 213:    */     
/* 214:    */     private RestoreTask(Backup par2Backup)
/* 215:    */     {
/* 216:251 */       this.field_148424_c = par2Backup;
/* 217:    */     }
/* 218:    */     
/* 219:    */     public void run()
/* 220:    */     {
/* 221:256 */       func_148417_b(I18n.format("mco.backup.restoring", new Object[0]));
/* 222:    */       try
/* 223:    */       {
/* 224:260 */         if (func_148418_c()) {
/* 225:262 */           return;
/* 226:    */         }
/* 227:265 */         Session var1 = GuiScreenBackup.this.mc.getSession();
/* 228:266 */         McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 229:267 */         var2.func_148712_c(GuiScreenBackup.this.field_146846_h, this.field_148424_c.field_148780_a);
/* 230:    */         try
/* 231:    */         {
/* 232:271 */           Thread.sleep(1000L);
/* 233:    */         }
/* 234:    */         catch (InterruptedException var4)
/* 235:    */         {
/* 236:275 */           Thread.currentThread().interrupt();
/* 237:    */         }
/* 238:278 */         if (func_148418_c()) {
/* 239:280 */           return;
/* 240:    */         }
/* 241:283 */         func_148413_b().displayGuiScreen(GuiScreenBackup.this.field_146842_g);
/* 242:    */       }
/* 243:    */       catch (ExceptionMcoService var5)
/* 244:    */       {
/* 245:287 */         if (func_148418_c()) {
/* 246:289 */           return;
/* 247:    */         }
/* 248:292 */         GuiScreenBackup.logger.error("Couldn't restore backup");
/* 249:293 */         func_148416_a(var5.toString());
/* 250:    */       }
/* 251:    */       catch (Exception var6)
/* 252:    */       {
/* 253:297 */         if (func_148418_c()) {
/* 254:299 */           return;
/* 255:    */         }
/* 256:302 */         GuiScreenBackup.logger.error("Couldn't restore backup");
/* 257:303 */         func_148416_a(var6.getLocalizedMessage());
/* 258:    */       }
/* 259:    */     }
/* 260:    */     
/* 261:    */     RestoreTask(Backup par2Backup, Object par3GuiScreenBackupDownloadThread)
/* 262:    */     {
/* 263:309 */       this(par2Backup);
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   class SelectionList
/* 268:    */     extends GuiScreenSelectLocation
/* 269:    */   {
/* 270:    */     private static final String __OBFID = "CL_00000768";
/* 271:    */     
/* 272:    */     public SelectionList()
/* 273:    */     {
/* 274:319 */       super(GuiScreenBackup.width, GuiScreenBackup.height, 32, GuiScreenBackup.height - 64, 36);
/* 275:    */     }
/* 276:    */     
/* 277:    */     protected int func_148355_a()
/* 278:    */     {
/* 279:324 */       return GuiScreenBackup.this.field_146847_i.size() + 1;
/* 280:    */     }
/* 281:    */     
/* 282:    */     protected void func_148352_a(int p_148352_1_, boolean p_148352_2_)
/* 283:    */     {
/* 284:329 */       if (p_148352_1_ < GuiScreenBackup.this.field_146847_i.size()) {
/* 285:331 */         GuiScreenBackup.this.field_146843_s = p_148352_1_;
/* 286:    */       }
/* 287:    */     }
/* 288:    */     
/* 289:    */     protected boolean func_148356_a(int p_148356_1_)
/* 290:    */     {
/* 291:337 */       return p_148356_1_ == GuiScreenBackup.this.field_146843_s;
/* 292:    */     }
/* 293:    */     
/* 294:    */     protected boolean func_148349_b(int p_148349_1_)
/* 295:    */     {
/* 296:342 */       return false;
/* 297:    */     }
/* 298:    */     
/* 299:    */     protected int func_148351_b()
/* 300:    */     {
/* 301:347 */       return func_148355_a() * 36;
/* 302:    */     }
/* 303:    */     
/* 304:    */     protected void func_148358_c()
/* 305:    */     {
/* 306:352 */       GuiScreenBackup.this.drawDefaultBackground();
/* 307:    */     }
/* 308:    */     
/* 309:    */     protected void func_148348_a(int p_148348_1_, int p_148348_2_, int p_148348_3_, int p_148348_4_, Tessellator p_148348_5_)
/* 310:    */     {
/* 311:357 */       if (p_148348_1_ < GuiScreenBackup.this.field_146847_i.size()) {
/* 312:359 */         func_148385_b(p_148348_1_, p_148348_2_, p_148348_3_, p_148348_4_, p_148348_5_);
/* 313:    */       }
/* 314:    */     }
/* 315:    */     
/* 316:    */     private void func_148385_b(int p_148385_1_, int p_148385_2_, int p_148385_3_, int p_148385_4_, Tessellator p_148385_5_)
/* 317:    */     {
/* 318:365 */       Backup var6 = (Backup)GuiScreenBackup.this.field_146847_i.get(p_148385_1_);
/* 319:366 */       GuiScreenBackup.drawString(GuiScreenBackup.this.fontRendererObj, "Backup (" + GuiScreenBackup.this.func_146829_a(Long.valueOf(MinecraftServer.getSystemTimeMillis() - var6.field_148778_b.getTime())) + ")", p_148385_2_ + 2, p_148385_3_ + 1, 16777215);
/* 320:367 */       GuiScreenBackup.drawString(GuiScreenBackup.this.fontRendererObj, func_148384_a(var6.field_148778_b), p_148385_2_ + 2, p_148385_3_ + 12, 7105644);
/* 321:    */     }
/* 322:    */     
/* 323:    */     private String func_148384_a(Date p_148384_1_)
/* 324:    */     {
/* 325:372 */       return DateFormat.getDateTimeInstance(3, 3).format(p_148384_1_);
/* 326:    */     }
/* 327:    */   }
/* 328:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.mco.GuiScreenBackup
 * JD-Core Version:    0.7.0.1
 */