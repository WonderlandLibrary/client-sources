/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.multiplayer.ServerData;
/*   7:    */ import net.minecraft.client.resources.I18n;
/*   8:    */ import net.minecraft.client.settings.GameSettings;
/*   9:    */ import org.lwjgl.input.Keyboard;
/*  10:    */ 
/*  11:    */ public class GuiScreenServerList
/*  12:    */   extends GuiScreen
/*  13:    */ {
/*  14:    */   private final GuiScreen field_146303_a;
/*  15:    */   private final ServerData field_146301_f;
/*  16:    */   private GuiTextField field_146302_g;
/*  17:    */   private static final String __OBFID = "CL_00000692";
/*  18:    */   
/*  19:    */   public GuiScreenServerList(GuiScreen par1GuiScreen, ServerData par2ServerData)
/*  20:    */   {
/*  21: 18 */     this.field_146303_a = par1GuiScreen;
/*  22: 19 */     this.field_146301_f = par2ServerData;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void updateScreen()
/*  26:    */   {
/*  27: 27 */     this.field_146302_g.updateCursorCounter();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void initGui()
/*  31:    */   {
/*  32: 35 */     Keyboard.enableRepeatEvents(true);
/*  33: 36 */     this.buttonList.clear();
/*  34: 37 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 100, height / 4 + 96 + 12, I18n.format("selectServer.select", new Object[0])));
/*  35: 38 */     this.buttonList.add(new NodusGuiButton(1, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/*  36: 39 */     this.field_146302_g = new GuiTextField(this.fontRendererObj, width / 2 - 100, 116, 200, 20);
/*  37: 40 */     this.field_146302_g.func_146203_f(128);
/*  38: 41 */     this.field_146302_g.setFocused(true);
/*  39: 42 */     this.field_146302_g.setText(this.mc.gameSettings.lastServer);
/*  40: 43 */     ((NodusGuiButton)this.buttonList.get(0)).enabled = ((this.field_146302_g.getText().length() > 0) && (this.field_146302_g.getText().split(":").length > 0));
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void onGuiClosed()
/*  44:    */   {
/*  45: 51 */     Keyboard.enableRepeatEvents(false);
/*  46: 52 */     this.mc.gameSettings.lastServer = this.field_146302_g.getText();
/*  47: 53 */     this.mc.gameSettings.saveOptions();
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  51:    */   {
/*  52: 58 */     if (p_146284_1_.enabled) {
/*  53: 60 */       if (p_146284_1_.id == 1)
/*  54:    */       {
/*  55: 62 */         this.field_146303_a.confirmClicked(false, 0);
/*  56:    */       }
/*  57: 64 */       else if (p_146284_1_.id == 0)
/*  58:    */       {
/*  59: 66 */         this.field_146301_f.serverIP = this.field_146302_g.getText();
/*  60: 67 */         this.field_146303_a.confirmClicked(true, 0);
/*  61:    */       }
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void keyTyped(char par1, int par2)
/*  66:    */   {
/*  67: 77 */     if (this.field_146302_g.textboxKeyTyped(par1, par2)) {
/*  68: 79 */       ((NodusGuiButton)this.buttonList.get(0)).enabled = ((this.field_146302_g.getText().length() > 0) && (this.field_146302_g.getText().split(":").length > 0));
/*  69: 81 */     } else if ((par2 == 28) || (par2 == 156)) {
/*  70: 83 */       actionPerformed((NodusGuiButton)this.buttonList.get(0));
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected void mouseClicked(int par1, int par2, int par3)
/*  75:    */   {
/*  76: 92 */     super.mouseClicked(par1, par2, par3);
/*  77: 93 */     this.field_146302_g.mouseClicked(par1, par2, par3);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void drawScreen(int par1, int par2, float par3)
/*  81:    */   {
/*  82:101 */     drawDefaultBackground();
/*  83:102 */     drawCenteredString(this.fontRendererObj, I18n.format("selectServer.direct", new Object[0]), width / 2, 20, 16777215);
/*  84:103 */     drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), width / 2 - 100, 100, 10526880);
/*  85:104 */     this.field_146302_g.drawTextBox();
/*  86:105 */     super.drawScreen(par1, par2, par3);
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenServerList
 * JD-Core Version:    0.7.0.1
 */