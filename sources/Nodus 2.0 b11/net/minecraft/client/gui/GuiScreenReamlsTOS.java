/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.awt.Toolkit;
/*   4:    */ import java.awt.datatransfer.Clipboard;
/*   5:    */ import java.awt.datatransfer.StringSelection;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.net.URI;
/*   8:    */ import java.util.List;
/*   9:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  10:    */ import net.minecraft.client.Minecraft;
/*  11:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*  12:    */ import net.minecraft.client.mco.McoClient;
/*  13:    */ import net.minecraft.client.mco.McoServer;
/*  14:    */ import net.minecraft.client.resources.I18n;
/*  15:    */ import net.minecraft.util.Session;
/*  16:    */ import org.apache.logging.log4j.LogManager;
/*  17:    */ import org.apache.logging.log4j.Logger;
/*  18:    */ import org.lwjgl.input.Keyboard;
/*  19:    */ 
/*  20:    */ public class GuiScreenReamlsTOS
/*  21:    */   extends GuiScreen
/*  22:    */ {
/*  23: 23 */   private static final Logger logger = ;
/*  24:    */   private final GuiScreen field_146770_f;
/*  25:    */   private final McoServer field_146771_g;
/*  26:    */   private NodusGuiButton field_146774_h;
/*  27: 27 */   private boolean field_146775_i = false;
/*  28: 28 */   private String field_146772_r = "https://minecraft.net/realms/terms";
/*  29:    */   private static final String __OBFID = "CL_00000809";
/*  30:    */   
/*  31:    */   public GuiScreenReamlsTOS(GuiScreen p_i45045_1_, McoServer p_i45045_2_)
/*  32:    */   {
/*  33: 33 */     this.field_146770_f = p_i45045_1_;
/*  34: 34 */     this.field_146771_g = p_i45045_2_;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void updateScreen() {}
/*  38:    */   
/*  39:    */   public void initGui()
/*  40:    */   {
/*  41: 47 */     Keyboard.enableRepeatEvents(true);
/*  42: 48 */     this.buttonList.clear();
/*  43: 49 */     int var1 = width / 4;
/*  44: 50 */     int var2 = width / 4 - 2;
/*  45: 51 */     int var3 = width / 2 + 4;
/*  46: 52 */     this.buttonList.add(this.field_146774_h = new NodusGuiButton(1, var1, height / 5 + 96 + 22, var2, 20, I18n.format("mco.terms.buttons.agree", new Object[0])));
/*  47: 53 */     this.buttonList.add(new NodusGuiButton(2, var3, height / 5 + 96 + 22, var2, 20, I18n.format("mco.terms.buttons.disagree", new Object[0])));
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void onGuiClosed()
/*  51:    */   {
/*  52: 61 */     Keyboard.enableRepeatEvents(false);
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  56:    */   {
/*  57: 66 */     if (p_146284_1_.enabled) {
/*  58: 68 */       if (p_146284_1_.id == 2) {
/*  59: 70 */         this.mc.displayGuiScreen(this.field_146770_f);
/*  60: 72 */       } else if (p_146284_1_.id == 1) {
/*  61: 74 */         func_146768_g();
/*  62:    */       }
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   private void func_146768_g()
/*  67:    */   {
/*  68: 81 */     Session var1 = this.mc.getSession();
/*  69: 82 */     McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/*  70:    */     try
/*  71:    */     {
/*  72: 86 */       var2.func_148714_h();
/*  73: 87 */       GuiScreenLongRunningTask var3 = new GuiScreenLongRunningTask(this.mc, this, new TaskOnlineConnect(this, this.field_146771_g));
/*  74: 88 */       var3.func_146902_g();
/*  75: 89 */       this.mc.displayGuiScreen(var3);
/*  76:    */     }
/*  77:    */     catch (ExceptionMcoService var4)
/*  78:    */     {
/*  79: 93 */       logger.error("Couldn't agree to TOS");
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected void mouseClicked(int par1, int par2, int par3)
/*  84:    */   {
/*  85:102 */     super.mouseClicked(par1, par2, par3);
/*  86:104 */     if (this.field_146775_i)
/*  87:    */     {
/*  88:106 */       Clipboard var4 = Toolkit.getDefaultToolkit().getSystemClipboard();
/*  89:107 */       var4.setContents(new StringSelection(this.field_146772_r), null);
/*  90:108 */       func_146769_a(this.field_146772_r);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   private void func_146769_a(String p_146769_1_)
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:116 */       URI var2 = new URI(p_146769_1_);
/*  99:117 */       Class var3 = Class.forName("java.awt.Desktop");
/* 100:118 */       Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 101:119 */       var3.getMethod("browse", new Class[] { URI.class }).invoke(var4, new Object[] { var2 });
/* 102:    */     }
/* 103:    */     catch (Throwable var5)
/* 104:    */     {
/* 105:123 */       logger.error("Couldn't open link");
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void drawScreen(int par1, int par2, float par3)
/* 110:    */   {
/* 111:132 */     drawDefaultBackground();
/* 112:133 */     drawCenteredString(this.fontRendererObj, I18n.format("mco.terms.title", new Object[0]), width / 2, 17, 16777215);
/* 113:134 */     drawString(this.fontRendererObj, I18n.format("mco.terms.sentence.1", new Object[0]), width / 2 - 120, 87, 16777215);
/* 114:135 */     int var4 = this.fontRendererObj.getStringWidth(I18n.format("mco.terms.sentence.1", new Object[0]));
/* 115:136 */     int var5 = 3368635;
/* 116:137 */     int var6 = 7107012;
/* 117:138 */     int var7 = width / 2 - 121 + var4;
/* 118:139 */     byte var8 = 86;
/* 119:140 */     int var9 = var7 + this.fontRendererObj.getStringWidth("mco.terms.sentence.2") + 1;
/* 120:141 */     int var10 = 87 + this.fontRendererObj.FONT_HEIGHT;
/* 121:143 */     if ((var7 <= par1) && (par1 <= var9) && (var8 <= par2) && (par2 <= var10))
/* 122:    */     {
/* 123:145 */       this.field_146775_i = true;
/* 124:146 */       drawString(this.fontRendererObj, " " + I18n.format("mco.terms.sentence.2", new Object[0]), width / 2 - 120 + var4, 87, var6);
/* 125:    */     }
/* 126:    */     else
/* 127:    */     {
/* 128:150 */       this.field_146775_i = false;
/* 129:151 */       drawString(this.fontRendererObj, " " + I18n.format("mco.terms.sentence.2", new Object[0]), width / 2 - 120 + var4, 87, var5);
/* 130:    */     }
/* 131:154 */     super.drawScreen(par1, par2, par3);
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenReamlsTOS
 * JD-Core Version:    0.7.0.1
 */