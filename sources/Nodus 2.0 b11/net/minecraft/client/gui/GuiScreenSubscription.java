/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.awt.Toolkit;
/*   4:    */ import java.awt.datatransfer.Clipboard;
/*   5:    */ import java.awt.datatransfer.StringSelection;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.net.URI;
/*   9:    */ import java.text.DateFormat;
/*  10:    */ import java.text.SimpleDateFormat;
/*  11:    */ import java.util.GregorianCalendar;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.TimeZone;
/*  14:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  15:    */ import net.minecraft.client.Minecraft;
/*  16:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*  17:    */ import net.minecraft.client.mco.McoClient;
/*  18:    */ import net.minecraft.client.mco.McoServer;
/*  19:    */ import net.minecraft.client.mco.ValueObjectSubscription;
/*  20:    */ import net.minecraft.client.resources.I18n;
/*  21:    */ import net.minecraft.util.Session;
/*  22:    */ import org.apache.logging.log4j.LogManager;
/*  23:    */ import org.apache.logging.log4j.Logger;
/*  24:    */ import org.lwjgl.input.Keyboard;
/*  25:    */ 
/*  26:    */ public class GuiScreenSubscription
/*  27:    */   extends GuiScreen
/*  28:    */ {
/*  29: 28 */   private static final Logger logger = ;
/*  30:    */   private final GuiScreen field_146780_f;
/*  31:    */   private final McoServer field_146781_g;
/*  32: 31 */   private final int field_146787_h = 0;
/*  33: 32 */   private final int field_146788_i = 1;
/*  34:    */   private int field_146785_r;
/*  35:    */   private String field_146784_s;
/*  36: 35 */   private final String field_146783_t = "https://account.mojang.com";
/*  37: 36 */   private final String field_146782_u = "/buy/realms";
/*  38:    */   private static final String __OBFID = "CL_00000813";
/*  39:    */   
/*  40:    */   public GuiScreenSubscription(GuiScreen par1GuiScreen, McoServer par2McoServer)
/*  41:    */   {
/*  42: 41 */     this.field_146780_f = par1GuiScreen;
/*  43: 42 */     this.field_146781_g = par2McoServer;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void updateScreen() {}
/*  47:    */   
/*  48:    */   public void initGui()
/*  49:    */   {
/*  50: 55 */     func_146778_a(this.field_146781_g.field_148812_a);
/*  51: 56 */     Keyboard.enableRepeatEvents(true);
/*  52: 57 */     this.buttonList.add(new NodusGuiButton(1, width / 2 - 100, height / 4 + 96 + 12, I18n.format("mco.configure.world.subscription.extend", new Object[0])));
/*  53: 58 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void func_146778_a(long p_146778_1_)
/*  57:    */   {
/*  58: 63 */     Session var3 = this.mc.getSession();
/*  59: 64 */     McoClient var4 = new McoClient(var3.getSessionID(), var3.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/*  60:    */     try
/*  61:    */     {
/*  62: 68 */       ValueObjectSubscription var5 = var4.func_148705_g(p_146778_1_);
/*  63: 69 */       this.field_146785_r = var5.field_148789_b;
/*  64: 70 */       this.field_146784_s = func_146776_b(var5.field_148790_a);
/*  65:    */     }
/*  66:    */     catch (ExceptionMcoService var6)
/*  67:    */     {
/*  68: 74 */       logger.error("Couldn't get subscription");
/*  69:    */     }
/*  70:    */     catch (IOException var7)
/*  71:    */     {
/*  72: 78 */       logger.error("Couldn't parse response subscribing");
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   private String func_146776_b(long p_146776_1_)
/*  77:    */   {
/*  78: 84 */     GregorianCalendar var3 = new GregorianCalendar(TimeZone.getDefault());
/*  79: 85 */     var3.setTimeInMillis(p_146776_1_);
/*  80: 86 */     return SimpleDateFormat.getDateTimeInstance().format(var3.getTime());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void onGuiClosed()
/*  84:    */   {
/*  85: 94 */     Keyboard.enableRepeatEvents(false);
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  89:    */   {
/*  90: 99 */     if (p_146284_1_.enabled) {
/*  91:101 */       if (p_146284_1_.id == 0)
/*  92:    */       {
/*  93:103 */         this.mc.displayGuiScreen(this.field_146780_f);
/*  94:    */       }
/*  95:105 */       else if (p_146284_1_.id == 1)
/*  96:    */       {
/*  97:107 */         String var2 = "https://account.mojang.com/buy/realms?wid=" + this.field_146781_g.field_148812_a + "?pid=" + func_146777_g();
/*  98:108 */         Clipboard var3 = Toolkit.getDefaultToolkit().getSystemClipboard();
/*  99:109 */         var3.setContents(new StringSelection(var2), null);
/* 100:110 */         func_146779_a(var2);
/* 101:    */       }
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   private String func_146777_g()
/* 106:    */   {
/* 107:117 */     String var1 = this.mc.getSession().getSessionID();
/* 108:118 */     String[] var2 = var1.split(":");
/* 109:119 */     return var2.length == 3 ? var2[2] : "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   private void func_146779_a(String p_146779_1_)
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:126 */       URI var2 = new URI(p_146779_1_);
/* 117:127 */       Class var3 = Class.forName("java.awt.Desktop");
/* 118:128 */       Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 119:129 */       var3.getMethod("browse", new Class[] { URI.class }).invoke(var4, new Object[] { var2 });
/* 120:    */     }
/* 121:    */     catch (Throwable var5)
/* 122:    */     {
/* 123:133 */       logger.error("Couldn't open link");
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected void keyTyped(char par1, int par2) {}
/* 128:    */   
/* 129:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 130:    */   {
/* 131:147 */     super.mouseClicked(par1, par2, par3);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void drawScreen(int par1, int par2, float par3)
/* 135:    */   {
/* 136:155 */     drawDefaultBackground();
/* 137:156 */     drawCenteredString(this.fontRendererObj, I18n.format("mco.configure.world.subscription.title", new Object[0]), width / 2, 17, 16777215);
/* 138:157 */     drawString(this.fontRendererObj, I18n.format("mco.configure.world.subscription.start", new Object[0]), width / 2 - 100, 53, 10526880);
/* 139:158 */     drawString(this.fontRendererObj, this.field_146784_s, width / 2 - 100, 66, 16777215);
/* 140:159 */     drawString(this.fontRendererObj, I18n.format("mco.configure.world.subscription.daysleft", new Object[0]), width / 2 - 100, 85, 10526880);
/* 141:160 */     drawString(this.fontRendererObj, String.valueOf(this.field_146785_r), width / 2 - 100, 98, 16777215);
/* 142:161 */     super.drawScreen(par1, par2, par3);
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenSubscription
 * JD-Core Version:    0.7.0.1
 */