/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.multiplayer.ServerData;
/*   6:    */ import net.minecraft.client.resources.I18n;
/*   7:    */ import org.lwjgl.input.Keyboard;
/*   8:    */ 
/*   9:    */ public class GuiScreenAddServer
/*  10:    */   extends GuiScreen
/*  11:    */ {
/*  12:    */   private GuiScreen field_146310_a;
/*  13:    */   private GuiTextField field_146308_f;
/*  14:    */   private GuiTextField field_146309_g;
/*  15:    */   private ServerData field_146311_h;
/*  16:    */   private static final String __OBFID = "CL_00000695";
/*  17:    */   
/*  18:    */   public GuiScreenAddServer(GuiScreen par1GuiScreen, ServerData par2ServerData)
/*  19:    */   {
/*  20: 19 */     this.field_146310_a = par1GuiScreen;
/*  21: 20 */     this.field_146311_h = par2ServerData;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void updateScreen()
/*  25:    */   {
/*  26: 28 */     this.field_146309_g.updateCursorCounter();
/*  27: 29 */     this.field_146308_f.updateCursorCounter();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void initGui()
/*  31:    */   {
/*  32: 37 */     Keyboard.enableRepeatEvents(true);
/*  33: 38 */     this.buttonList.clear();
/*  34: 39 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 100, height / 4 + 96 + 12, I18n.format("addServer.add", new Object[0])));
/*  35: 40 */     this.buttonList.add(new NodusGuiButton(1, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/*  36: 41 */     this.field_146309_g = new GuiTextField(this.fontRendererObj, width / 2 - 100, 66, 200, 20);
/*  37: 42 */     this.field_146309_g.setFocused(true);
/*  38: 43 */     this.field_146309_g.setText(this.field_146311_h.serverName);
/*  39: 44 */     this.field_146308_f = new GuiTextField(this.fontRendererObj, width / 2 - 100, 106, 200, 20);
/*  40: 45 */     this.field_146308_f.func_146203_f(128);
/*  41: 46 */     this.field_146308_f.setText(this.field_146311_h.serverIP);
/*  42: 47 */     ((NodusGuiButton)this.buttonList.get(0)).enabled = ((this.field_146308_f.getText().length() > 0) && (this.field_146308_f.getText().split(":").length > 0) && (this.field_146309_g.getText().length() > 0));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void onGuiClosed()
/*  46:    */   {
/*  47: 55 */     Keyboard.enableRepeatEvents(false);
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  51:    */   {
/*  52: 60 */     if (p_146284_1_.enabled) {
/*  53: 62 */       if (p_146284_1_.id == 1)
/*  54:    */       {
/*  55: 64 */         this.field_146310_a.confirmClicked(false, 0);
/*  56:    */       }
/*  57: 66 */       else if (p_146284_1_.id == 0)
/*  58:    */       {
/*  59: 68 */         this.field_146311_h.serverName = this.field_146309_g.getText();
/*  60: 69 */         this.field_146311_h.serverIP = this.field_146308_f.getText();
/*  61: 70 */         this.field_146310_a.confirmClicked(true, 0);
/*  62:    */       }
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected void keyTyped(char par1, int par2)
/*  67:    */   {
/*  68: 80 */     this.field_146309_g.textboxKeyTyped(par1, par2);
/*  69: 81 */     this.field_146308_f.textboxKeyTyped(par1, par2);
/*  70: 83 */     if (par2 == 15)
/*  71:    */     {
/*  72: 85 */       this.field_146309_g.setFocused(!this.field_146309_g.isFocused());
/*  73: 86 */       this.field_146308_f.setFocused(!this.field_146308_f.isFocused());
/*  74:    */     }
/*  75: 89 */     if ((par2 == 28) || (par2 == 156)) {
/*  76: 91 */       actionPerformed((NodusGuiButton)this.buttonList.get(0));
/*  77:    */     }
/*  78: 94 */     ((NodusGuiButton)this.buttonList.get(0)).enabled = ((this.field_146308_f.getText().length() > 0) && (this.field_146308_f.getText().split(":").length > 0) && (this.field_146309_g.getText().length() > 0));
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected void mouseClicked(int par1, int par2, int par3)
/*  82:    */   {
/*  83:102 */     super.mouseClicked(par1, par2, par3);
/*  84:103 */     this.field_146308_f.mouseClicked(par1, par2, par3);
/*  85:104 */     this.field_146309_g.mouseClicked(par1, par2, par3);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void drawScreen(int par1, int par2, float par3)
/*  89:    */   {
/*  90:112 */     drawDefaultBackground();
/*  91:113 */     drawCenteredString(this.fontRendererObj, I18n.format("addServer.title", new Object[0]), width / 2, 17, 16777215);
/*  92:114 */     drawString(this.fontRendererObj, I18n.format("addServer.enterName", new Object[0]), width / 2 - 100, 53, 10526880);
/*  93:115 */     drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), width / 2 - 100, 94, 10526880);
/*  94:116 */     this.field_146309_g.drawTextBox();
/*  95:117 */     this.field_146308_f.drawTextBox();
/*  96:118 */     super.drawScreen(par1, par2, par3);
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenAddServer
 * JD-Core Version:    0.7.0.1
 */