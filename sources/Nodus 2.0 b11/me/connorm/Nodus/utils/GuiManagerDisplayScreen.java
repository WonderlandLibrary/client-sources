/*   1:    */ package me.connorm.Nodus.utils;
/*   2:    */ 
/*   3:    */ import java.awt.Rectangle;
/*   4:    */ import me.connorm.Nodus.ui.gui.theme.nodus.ColorUtils;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.gui.FontRenderer;
/*   7:    */ import net.minecraft.client.gui.GuiScreen;
/*   8:    */ import net.minecraft.util.EnumChatFormatting;
/*   9:    */ import org.darkstorm.minecraft.gui.GuiManager;
/*  10:    */ import org.darkstorm.minecraft.gui.component.Component;
/*  11:    */ import org.darkstorm.minecraft.gui.component.Frame;
/*  12:    */ import org.darkstorm.minecraft.gui.theme.ComponentUI;
/*  13:    */ import org.darkstorm.minecraft.gui.theme.Theme;
/*  14:    */ 
/*  15:    */ public class GuiManagerDisplayScreen
/*  16:    */   extends GuiScreen
/*  17:    */ {
/*  18:    */   private final GuiManager guiManager;
/*  19:    */   
/*  20:    */   public GuiManagerDisplayScreen(GuiManager guiManager)
/*  21:    */   {
/*  22: 19 */     this.guiManager = guiManager;
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected void mouseClicked(int x, int y, int button)
/*  26:    */   {
/*  27: 24 */     super.mouseClicked(x, y, button);
/*  28: 25 */     for (Frame frame : this.guiManager.getFrames()) {
/*  29: 26 */       if (frame.isVisible()) {
/*  30: 28 */         if ((!frame.isMinimized()) && (!frame.getArea().contains(x, y))) {
/*  31: 29 */           for (Component component : frame.getChildren()) {
/*  32: 30 */             for (Rectangle area : component.getTheme().getUIForComponent(component).getInteractableRegions(component)) {
/*  33: 31 */               if (area.contains(x - frame.getX() - component.getX(), y - frame.getY() - component.getY()))
/*  34:    */               {
/*  35: 32 */                 frame.onMousePress(x - frame.getX(), y - frame.getY(), button);
/*  36: 33 */                 this.guiManager.bringForward(frame);
/*  37: 34 */                 return;
/*  38:    */               }
/*  39:    */             }
/*  40:    */           }
/*  41:    */         }
/*  42:    */       }
/*  43:    */     }
/*  44: 40 */     for (Frame frame : this.guiManager.getFrames()) {
/*  45: 41 */       if (frame.isVisible())
/*  46:    */       {
/*  47: 43 */         if ((!frame.isMinimized()) && (frame.getArea().contains(x, y)))
/*  48:    */         {
/*  49: 44 */           frame.onMousePress(x - frame.getX(), y - frame.getY(), button);
/*  50: 45 */           this.guiManager.bringForward(frame);
/*  51: 46 */           break;
/*  52:    */         }
/*  53: 47 */         if (frame.isMinimized()) {
/*  54: 48 */           for (Rectangle area : frame.getTheme().getUIForComponent(frame).getInteractableRegions(frame)) {
/*  55: 49 */             if (area.contains(x - frame.getX(), y - frame.getY()))
/*  56:    */             {
/*  57: 50 */               frame.onMousePress(x - frame.getX(), y - frame.getY(), button);
/*  58: 51 */               this.guiManager.bringForward(frame);
/*  59: 52 */               return;
/*  60:    */             }
/*  61:    */           }
/*  62:    */         }
/*  63:    */       }
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void mouseMovedOrUp(int x, int y, int button)
/*  68:    */   {
/*  69: 61 */     super.mouseMovedOrUp(x, y, button);
/*  70: 62 */     for (Frame frame : this.guiManager.getFrames()) {
/*  71: 63 */       if (frame.isVisible()) {
/*  72: 65 */         if ((!frame.isMinimized()) && (!frame.getArea().contains(x, y))) {
/*  73: 66 */           for (Component component : frame.getChildren()) {
/*  74: 67 */             for (Rectangle area : component.getTheme().getUIForComponent(component).getInteractableRegions(component)) {
/*  75: 68 */               if (area.contains(x - frame.getX() - component.getX(), y - frame.getY() - component.getY()))
/*  76:    */               {
/*  77: 69 */                 frame.onMouseRelease(x - frame.getX(), y - frame.getY(), button);
/*  78: 70 */                 this.guiManager.bringForward(frame);
/*  79: 71 */                 return;
/*  80:    */               }
/*  81:    */             }
/*  82:    */           }
/*  83:    */         }
/*  84:    */       }
/*  85:    */     }
/*  86: 77 */     for (Frame frame : this.guiManager.getFrames()) {
/*  87: 78 */       if (frame.isVisible())
/*  88:    */       {
/*  89: 80 */         if ((!frame.isMinimized()) && (frame.getArea().contains(x, y)))
/*  90:    */         {
/*  91: 81 */           frame.onMouseRelease(x - frame.getX(), y - frame.getY(), button);
/*  92: 82 */           this.guiManager.bringForward(frame);
/*  93: 83 */           break;
/*  94:    */         }
/*  95: 84 */         if (frame.isMinimized()) {
/*  96: 85 */           for (Rectangle area : frame.getTheme().getUIForComponent(frame).getInteractableRegions(frame)) {
/*  97: 86 */             if (area.contains(x - frame.getX(), y - frame.getY()))
/*  98:    */             {
/*  99: 87 */               frame.onMouseRelease(x - frame.getX(), y - frame.getY(), button);
/* 100: 88 */               this.guiManager.bringForward(frame);
/* 101: 89 */               return;
/* 102:    */             }
/* 103:    */           }
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void drawScreen(int par2, int par3, float par4)
/* 110:    */   {
/* 111: 98 */     this.guiManager.render();
/* 112: 99 */     drawString(this.mc.fontRenderer, EnumChatFormatting.YELLOW + "BETA " + EnumChatFormatting.RESET + "Build 1.1", width - this.fontRendererObj.getStringWidth("BETA Build 1.1") - 2, height - 10, ColorUtils.secondaryColor);
/* 113:    */     
/* 114:101 */     super.drawScreen(par2, par3, par4);
/* 115:    */   }
/* 116:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.utils.GuiManagerDisplayScreen
 * JD-Core Version:    0.7.0.1
 */