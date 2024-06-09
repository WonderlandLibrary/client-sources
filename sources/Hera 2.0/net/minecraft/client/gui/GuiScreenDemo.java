/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URI;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class GuiScreenDemo
/*    */   extends GuiScreen {
/* 14 */   private static final Logger logger = LogManager.getLogger();
/* 15 */   private static final ResourceLocation field_146348_f = new ResourceLocation("textures/gui/demo_background.png");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 23 */     this.buttonList.clear();
/* 24 */     int i = -16;
/* 25 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 116, this.height / 2 + 62 + i, 114, 20, I18n.format("demo.help.buy", new Object[0])));
/* 26 */     this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height / 2 + 62 + i, 114, 20, I18n.format("demo.help.later", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 34 */     switch (button.id) {
/*    */       
/*    */       case 1:
/* 37 */         button.enabled = false;
/*    */ 
/*    */         
/*    */         try {
/* 41 */           Class<?> oclass = Class.forName("java.awt.Desktop");
/* 42 */           Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 43 */           oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI("http://www.minecraft.net/store?source=demo") });
/*    */         }
/* 45 */         catch (Throwable throwable) {
/*    */           
/* 47 */           logger.error("Couldn't open link", throwable);
/*    */         } 
/*    */         break;
/*    */ 
/*    */       
/*    */       case 2:
/* 53 */         this.mc.displayGuiScreen(null);
/* 54 */         this.mc.setIngameFocus();
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 63 */     super.updateScreen();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawDefaultBackground() {
/* 71 */     super.drawDefaultBackground();
/* 72 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 73 */     this.mc.getTextureManager().bindTexture(field_146348_f);
/* 74 */     int i = (this.width - 248) / 2;
/* 75 */     int j = (this.height - 166) / 2;
/* 76 */     drawTexturedModalRect(i, j, 0, 0, 248, 166);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 84 */     drawDefaultBackground();
/* 85 */     int i = (this.width - 248) / 2 + 10;
/* 86 */     int j = (this.height - 166) / 2 + 8;
/* 87 */     this.fontRendererObj.drawString(I18n.format("demo.help.title", new Object[0]), i, j, 2039583);
/* 88 */     j += 12;
/* 89 */     GameSettings gamesettings = this.mc.gameSettings;
/* 90 */     this.fontRendererObj.drawString(I18n.format("demo.help.movementShort", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindRight.getKeyCode()) }), i, j, 5197647);
/* 91 */     this.fontRendererObj.drawString(I18n.format("demo.help.movementMouse", new Object[0]), i, j + 12, 5197647);
/* 92 */     this.fontRendererObj.drawString(I18n.format("demo.help.jump", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindJump.getKeyCode()) }), i, j + 24, 5197647);
/* 93 */     this.fontRendererObj.drawString(I18n.format("demo.help.inventory", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindInventory.getKeyCode()) }), i, j + 36, 5197647);
/* 94 */     this.fontRendererObj.drawSplitString(I18n.format("demo.help.fullWrapped", new Object[0]), i, j + 68, 218, 2039583);
/* 95 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiScreenDemo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */