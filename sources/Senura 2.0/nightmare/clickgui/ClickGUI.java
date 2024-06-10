/*     */ package nightmare.clickgui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.clickgui.component.Component;
/*     */ import nightmare.clickgui.component.Frame;
/*     */ import nightmare.fonts.impl.Fonts;
/*     */ import nightmare.gui.window.Window;
/*     */ import nightmare.module.Category;
/*     */ import nightmare.utils.ColorUtils;
/*     */ import nightmare.utils.MouseUtils;
/*     */ 
/*     */ public class ClickGUI
/*     */   extends GuiScreen
/*     */ {
/*     */   public static ArrayList<Frame> frames;
/*     */   
/*     */   public ClickGUI() {
/*  22 */     frames = new ArrayList<>();
/*  23 */     int frameX = 20;
/*  24 */     for (Category category : Category.values()) {
/*  25 */       Frame frame = new Frame(category);
/*  26 */       frame.setX(frameX);
/*  27 */       frame.setY(25);
/*  28 */       frames.add(frame);
/*  29 */       frameX += frame.getWidth() + 20;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  35 */     if (Nightmare.instance.fileManager != null) {
/*  36 */       Nightmare.instance.fileManager.getPositionManager().save();
/*     */     }
/*     */     
/*  39 */     for (Window window : Nightmare.instance.windowManager.getWindows()) {
/*  40 */       window.setDragging(false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/*  47 */     for (Frame frame : frames) {
/*  48 */       frame.renderFrame(this.field_146289_q);
/*  49 */       frame.updatePosition(mouseX, mouseY);
/*  50 */       for (Component comp : frame.getComponents()) {
/*  51 */         comp.updateComponent(mouseX, mouseY);
/*     */       }
/*     */     } 
/*     */     
/*  55 */     for (Window window : Nightmare.instance.windowManager.getWindows()) {
/*  56 */       if (Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), window.getName()).getValBoolean()) {
/*  57 */         Nightmare.instance.windowManager.getWindowByName(window.getName()).onRender();
/*     */       }
/*     */     } 
/*     */     
/*  61 */     for (Window window : Nightmare.instance.windowManager.getWindows()) {
/*     */       
/*  63 */       if (!window.isShowWindow()) {
/*  64 */         Gui.func_73734_a(window.getX(), window.getY() - 14, window.getWidth(), window.getY(), ColorUtils.getClientColor());
/*     */         
/*  66 */         Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(window.getName(), (window.getX() + 4), (window.getY() - 10), -1, false);
/*     */       } 
/*     */       
/*  69 */       if (window.isDrag() && window.isDragging() && Nightmare.instance.moduleManager.getModuleByName("HUD").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), window.getName()).getValBoolean()) {
/*  70 */         window.setX(mouseX + window.getDraggingX());
/*  71 */         window.setY(mouseY + window.getDraggingY());
/*  72 */         window.setWidth(mouseX + window.getDraggingWidth());
/*  73 */         window.setHeight(mouseY + window.getDraggingHeight());
/*     */       } 
/*     */     } 
/*     */     
/*  77 */     super.func_73863_a(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/*  82 */     super.func_73876_c();
/*  83 */     if (!this.field_146297_k.field_71439_g.func_70089_S() || this.field_146297_k.field_71439_g.field_70128_L)
/*     */     {
/*  85 */       this.field_146297_k.field_71439_g.func_71053_j();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  92 */     super.func_73864_a(mouseX, mouseY, mouseButton);
/*     */     
/*  94 */     for (Frame frame : frames) {
/*  95 */       if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
/*  96 */         frame.setDrag(true);
/*  97 */         frame.dragX = mouseX - frame.getX();
/*  98 */         frame.dragY = mouseY - frame.getY();
/*     */       } 
/* 100 */       if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
/* 101 */         frame.setOpen(!frame.isOpen());
/*     */       }
/* 103 */       if (frame.isOpen() && 
/* 104 */         !frame.getComponents().isEmpty()) {
/* 105 */         for (Component component : frame.getComponents()) {
/* 106 */           component.mouseClicked(mouseX, mouseY, mouseButton);
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 112 */     for (Window window : Nightmare.instance.windowManager.getWindows()) {
/* 113 */       if (MouseUtils.isInside(mouseX, mouseY, window.getX(), (window.getY() - 14), window.getWidth(), window.getY()) && Nightmare.instance.moduleManager.getModuleByName("HUD").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), window.getName()).getValBoolean() && mouseButton == 0) {
/* 114 */         window.setDragging(true);
/* 115 */         window.setDraggingX(window.getX() - mouseX);
/* 116 */         window.setDraggingY(window.getY() - mouseY);
/* 117 */         window.setDraggingWidth(window.getWidth() - mouseX);
/* 118 */         window.setDraggingHeight(window.getHeight() - mouseY);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char typedChar, int keyCode) {
/* 125 */     for (Frame frame : frames) {
/* 126 */       if (frame.isOpen() && keyCode != 1 && 
/* 127 */         !frame.getComponents().isEmpty()) {
/* 128 */         for (Component component : frame.getComponents()) {
/* 129 */           component.keyTyped(typedChar, keyCode);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 134 */     if (keyCode == 1) {
/* 135 */       this.field_146297_k.func_147108_a(null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146286_b(int mouseX, int mouseY, int state) {
/* 142 */     for (Frame frame : frames) {
/* 143 */       frame.setDrag(false);
/*     */     }
/* 145 */     for (Frame frame : frames) {
/* 146 */       if (frame.isOpen() && 
/* 147 */         !frame.getComponents().isEmpty()) {
/* 148 */         for (Component component : frame.getComponents()) {
/* 149 */           component.mouseReleased(mouseX, mouseY, state);
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 155 */     for (Window window : Nightmare.instance.windowManager.getWindows()) {
/* 156 */       window.setDragging(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146281_b() {
/* 162 */     for (Window window : Nightmare.instance.windowManager.getWindows()) {
/* 163 */       window.setDragging(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/* 169 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\clickgui\ClickGUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */