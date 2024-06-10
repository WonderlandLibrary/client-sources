/*  1:   */ package me.connorm.Nodus.manager;
/*  2:   */ 
/*  3:   */ import java.awt.Desktop;
/*  4:   */ import java.net.URI;
/*  5:   */ import java.util.List;
/*  6:   */ import me.connorm.Nodus.Nodus;
/*  7:   */ import me.connorm.Nodus.account.GuiAccount;
/*  8:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  9:   */ import me.connorm.Nodus.utils.RenderUtils;
/* 10:   */ import net.minecraft.client.Minecraft;
/* 11:   */ import net.minecraft.client.gui.GuiScreen;
/* 12:   */ 
/* 13:   */ public class GuiNodusManager
/* 14:   */   extends GuiScreen
/* 15:   */ {
/* 16:   */   private GuiScreen parentScreen;
/* 17:   */   
/* 18:   */   public GuiNodusManager(GuiScreen parentScreen)
/* 19:   */   {
/* 20:18 */     this.parentScreen = parentScreen;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void initGui()
/* 24:   */   {
/* 25:24 */     this.buttonList.add(new NodusGuiButton(1, width / 2 - 100, height / 4, "Account"));
/* 26:25 */     this.buttonList.add(new NodusGuiButton(2, width / 2 - 100, height / 4 + 34, "Suggest"));
/* 27:26 */     this.buttonList.add(new NodusGuiButton(4, width / 2 - 100, height / 4 + 64, "Change Version"));
/* 28:27 */     this.buttonList.add(new NodusGuiButton(3, width / 2 - 100, height / 4 + 94, "Back"));
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void drawScreen(int xPos, int yPos, float zPos)
/* 32:   */   {
/* 33:33 */     RenderUtils.drawRect(0.0F, 0, Nodus.theNodus.getMinecraft().displayWidth, Nodus.theNodus.getMinecraft().displayHeight, -11184811);
/* 34:34 */     drawString(this.fontRendererObj, "Minecraft Version: " + (Nodus.theNodus.getMinecraftVersion() == 4 ? "1.7.2 - 1.7.5" : "1.7.6 - 1.7.10"), 2, 2, -1);
/* 35:35 */     super.drawScreen(xPos, yPos, zPos);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void actionPerformed(NodusGuiButton guiButton)
/* 39:   */   {
/* 40:41 */     if (guiButton.id == 1) {
/* 41:43 */       Nodus.theNodus.getMinecraft().displayGuiScreen(new GuiAccount(this));
/* 42:   */     }
/* 43:46 */     if (guiButton.id == 2) {
/* 44:48 */       openURL("http://www.connorm.me/nodus_2.0/suggest");
/* 45:   */     }
/* 46:51 */     if (guiButton.id == 3) {
/* 47:53 */       Nodus.theNodus.getMinecraft().displayGuiScreen(this.parentScreen);
/* 48:   */     }
/* 49:56 */     if (guiButton.id == 4)
/* 50:   */     {
/* 51:58 */       if (Nodus.theNodus.getMinecraftVersion() == 4)
/* 52:   */       {
/* 53:59 */         Nodus.theNodus.setMinecraftVersion(5);
/* 54:60 */         return;
/* 55:   */       }
/* 56:62 */       if (Nodus.theNodus.getMinecraftVersion() == 5)
/* 57:   */       {
/* 58:63 */         Nodus.theNodus.setMinecraftVersion(4);
/* 59:64 */         return;
/* 60:   */       }
/* 61:   */     }
/* 62:   */   }
/* 63:   */   
/* 64:   */   private void openURL(String URL)
/* 65:   */   {
/* 66:   */     try
/* 67:   */     {
/* 68:73 */       Desktop userDesktop = Desktop.getDesktop();
/* 69:74 */       URI browseURL = new URI(URL);
/* 70:75 */       userDesktop.browse(browseURL);
/* 71:   */     }
/* 72:   */     catch (Exception urlException)
/* 73:   */     {
/* 74:78 */       urlException.printStackTrace();
/* 75:   */     }
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.manager.GuiNodusManager
 * JD-Core Version:    0.7.0.1
 */