/*  1:   */ package me.connorm.Nodus.ui;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.module.NodusModuleManager;
/*  5:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  6:   */ import me.connorm.Nodus.module.modules.Build;
/*  7:   */ import me.connorm.Nodus.ui.gui.NodusGuiManager;
/*  8:   */ import me.connorm.Nodus.ui.gui.theme.nodus.ColorUtils;
/*  9:   */ import me.connorm.Nodus.ui.tab.ClientModules;
/* 10:   */ import me.connorm.Nodus.utils.GuiManagerDisplayScreen;
/* 11:   */ import net.minecraft.client.Minecraft;
/* 12:   */ import net.minecraft.client.gui.FontRenderer;
/* 13:   */ import net.minecraft.client.gui.Gui;
/* 14:   */ import net.minecraft.client.gui.ScaledResolution;
/* 15:   */ import net.minecraft.client.settings.GameSettings;
/* 16:   */ import org.darkstorm.minecraft.gui.component.Frame;
/* 17:   */ 
/* 18:   */ public class UIRenderer
/* 19:   */ {
/* 20:16 */   public static boolean isTabGui = true;
/* 21:18 */   public static ClientModules clientHacks = new ClientModules(Nodus.theNodus.getMinecraft());
/* 22:   */   
/* 23:   */   public static void renderUI()
/* 24:   */   {
/* 25:21 */     ScaledResolution scaledResolution = new ScaledResolution(Nodus.theNodus.getMinecraft().gameSettings, Nodus.theNodus.getMinecraft().displayWidth, Nodus.theNodus.getMinecraft().displayHeight);
/* 26:22 */     if (Nodus.theNodus.getMinecraft().gameSettings.showDebugInfo) {
/* 27:23 */       return;
/* 28:   */     }
/* 29:25 */     Gui.drawString(Nodus.theNodus.getMinecraft().fontRenderer, "Nodus 2.0", 2, 2, -1862270977);
/* 30:27 */     if (isTabGui) {
/* 31:28 */       clientHacks.drawGui(Nodus.theNodus.getMinecraft().fontRenderer);
/* 32:   */     }
/* 33:30 */     int yCount = 2;
/* 34:31 */     for (NodusModule nodusModule : Nodus.theNodus.moduleManager.getModules()) {
/* 35:33 */       if ((nodusModule.getName() != "ClickGui") && (nodusModule.getName() != "Console")) {
/* 36:35 */         if ((nodusModule.getName() == "Build") && (nodusModule.isToggled()))
/* 37:   */         {
/* 38:37 */           int xCount = scaledResolution.getScaledWidth() - Nodus.theNodus.getMinecraft().fontRenderer.getStringWidth(nodusModule.getName() + getBuildMode());
/* 39:38 */           Nodus.theNodus.getMinecraft().fontRenderer.drawStringWithShadow(nodusModule.getName() + getBuildMode(), xCount - 2, yCount, ColorUtils.secondaryColor);
/* 40:39 */           yCount += 10;
/* 41:   */         }
/* 42:   */         else
/* 43:   */         {
/* 44:42 */           int xCount = scaledResolution.getScaledWidth() - Nodus.theNodus.getMinecraft().fontRenderer.getStringWidth(nodusModule.getName());
/* 45:43 */           if (nodusModule.isToggled())
/* 46:   */           {
/* 47:45 */             Nodus.theNodus.getMinecraft().fontRenderer.drawStringWithShadow(nodusModule.getName(), xCount - 2, yCount, ColorUtils.secondaryColor);
/* 48:46 */             yCount += 10;
/* 49:   */           }
/* 50:   */         }
/* 51:   */       }
/* 52:   */     }
/* 53:50 */     for (Frame theFrame : Nodus.theNodus.guiManager.getFrames()) {
/* 54:52 */       if ((theFrame.isPinned()) || ((Nodus.theNodus.getMinecraft().currentScreen instanceof GuiManagerDisplayScreen))) {
/* 55:53 */         theFrame.render();
/* 56:   */       }
/* 57:   */     }
/* 58:56 */     for (Frame theFrame : Nodus.theNodus.guiManager.getFrames()) {
/* 59:58 */       theFrame.update();
/* 60:   */     }
/* 61:   */   }
/* 62:   */   
/* 63:   */   private static String getBuildMode()
/* 64:   */   {
/* 65:64 */     if (Nodus.theNodus.moduleManager.buildModule.buildMode == 0) {
/* 66:66 */       return " [None]";
/* 67:   */     }
/* 68:69 */     if (Nodus.theNodus.moduleManager.buildModule.buildMode == 1) {
/* 69:71 */       return " [Floor]";
/* 70:   */     }
/* 71:74 */     if (Nodus.theNodus.moduleManager.buildModule.buildMode == 2) {
/* 72:76 */       return " [Pole]";
/* 73:   */     }
/* 74:78 */     return null;
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.UIRenderer
 * JD-Core Version:    0.7.0.1
 */