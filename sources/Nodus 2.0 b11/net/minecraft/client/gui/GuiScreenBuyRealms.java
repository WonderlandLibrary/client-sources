/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*   7:    */ import net.minecraft.client.mco.McoClient;
/*   8:    */ import net.minecraft.client.resources.I18n;
/*   9:    */ import net.minecraft.util.Session;
/*  10:    */ import org.apache.logging.log4j.LogManager;
/*  11:    */ import org.apache.logging.log4j.Logger;
/*  12:    */ import org.lwjgl.input.Keyboard;
/*  13:    */ 
/*  14:    */ public class GuiScreenBuyRealms
/*  15:    */   extends GuiScreen
/*  16:    */ {
/*  17: 16 */   private static final Logger logger = ;
/*  18:    */   private GuiScreen field_146817_f;
/*  19: 18 */   private static int field_146818_g = 111;
/*  20: 19 */   private volatile String field_146820_h = "";
/*  21:    */   private static final String __OBFID = "CL_00000770";
/*  22:    */   
/*  23:    */   public GuiScreenBuyRealms(GuiScreen p_i45035_1_)
/*  24:    */   {
/*  25: 24 */     this.field_146817_f = p_i45035_1_;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void updateScreen() {}
/*  29:    */   
/*  30:    */   public void initGui()
/*  31:    */   {
/*  32: 37 */     Keyboard.enableRepeatEvents(true);
/*  33: 38 */     this.buttonList.clear();
/*  34: 39 */     short var1 = 212;
/*  35: 40 */     this.buttonList.add(new NodusGuiButton(field_146818_g, width / 2 - var1 / 2, 180, var1, 20, I18n.format("gui.back", new Object[0])));
/*  36: 41 */     func_146816_h();
/*  37:    */   }
/*  38:    */   
/*  39:    */   private void func_146816_h()
/*  40:    */   {
/*  41: 46 */     Session var1 = this.mc.getSession();
/*  42: 47 */     final McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/*  43: 48 */     new Thread()
/*  44:    */     {
/*  45:    */       private static final String __OBFID = "CL_00000771";
/*  46:    */       
/*  47:    */       public void run()
/*  48:    */       {
/*  49:    */         try
/*  50:    */         {
/*  51: 55 */           GuiScreenBuyRealms.this.field_146820_h = var2.func_148690_i();
/*  52:    */         }
/*  53:    */         catch (ExceptionMcoService var2x)
/*  54:    */         {
/*  55: 59 */           GuiScreenBuyRealms.logger.error("Could not get stat");
/*  56:    */         }
/*  57:    */       }
/*  58:    */     }.start();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void onGuiClosed()
/*  62:    */   {
/*  63: 70 */     Keyboard.enableRepeatEvents(false);
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  67:    */   {
/*  68: 75 */     if (p_146284_1_.enabled) {
/*  69: 77 */       if (p_146284_1_.id == field_146818_g) {
/*  70: 79 */         this.mc.displayGuiScreen(this.field_146817_f);
/*  71:    */       }
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected void keyTyped(char par1, int par2) {}
/*  76:    */   
/*  77:    */   protected void mouseClicked(int par1, int par2, int par3)
/*  78:    */   {
/*  79: 94 */     super.mouseClicked(par1, par2, par3);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void drawScreen(int par1, int par2, float par3)
/*  83:    */   {
/*  84:102 */     drawDefaultBackground();
/*  85:103 */     drawCenteredString(this.fontRendererObj, I18n.format("mco.buy.realms.title", new Object[0]), width / 2, 11, 16777215);
/*  86:104 */     String[] var4 = this.field_146820_h.split("\n");
/*  87:105 */     int var5 = 52;
/*  88:106 */     String[] var6 = var4;
/*  89:107 */     int var7 = var4.length;
/*  90:109 */     for (int var8 = 0; var8 < var7; var8++)
/*  91:    */     {
/*  92:111 */       String var9 = var6[var8];
/*  93:112 */       drawCenteredString(this.fontRendererObj, var9, width / 2, var5, 10526880);
/*  94:113 */       var5 += 18;
/*  95:    */     }
/*  96:116 */     super.drawScreen(par1, par2, par3);
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenBuyRealms
 * JD-Core Version:    0.7.0.1
 */