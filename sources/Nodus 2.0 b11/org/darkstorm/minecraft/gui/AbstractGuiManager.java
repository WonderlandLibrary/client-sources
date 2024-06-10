/*   1:    */ package org.darkstorm.minecraft.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.concurrent.CopyOnWriteArrayList;
/*   5:    */ import org.darkstorm.minecraft.gui.component.Frame;
/*   6:    */ import org.darkstorm.minecraft.gui.theme.Theme;
/*   7:    */ 
/*   8:    */ public abstract class AbstractGuiManager
/*   9:    */   implements GuiManager
/*  10:    */ {
/*  11:    */   private final List<Frame> frames;
/*  12:    */   protected Theme theme;
/*  13:    */   
/*  14:    */   public AbstractGuiManager()
/*  15:    */   {
/*  16: 44 */     this.frames = new CopyOnWriteArrayList();
/*  17:    */   }
/*  18:    */   
/*  19:    */   public abstract void setup();
/*  20:    */   
/*  21:    */   public void addFrame(Frame frame)
/*  22:    */   {
/*  23: 52 */     frame.setTheme(this.theme);
/*  24: 53 */     this.frames.add(0, frame);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void removeFrame(Frame frame)
/*  28:    */   {
/*  29: 58 */     this.frames.remove(frame);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Frame[] getFrames()
/*  33:    */   {
/*  34: 63 */     return (Frame[])this.frames.toArray(new Frame[this.frames.size()]);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void bringForward(Frame frame)
/*  38:    */   {
/*  39: 68 */     if (this.frames.remove(frame)) {
/*  40: 69 */       this.frames.add(0, frame);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Theme getTheme()
/*  45:    */   {
/*  46: 74 */     return this.theme;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setTheme(Theme theme)
/*  50:    */   {
/*  51: 79 */     this.theme = theme;
/*  52: 80 */     for (Frame frame : this.frames) {
/*  53: 81 */       frame.setTheme(theme);
/*  54:    */     }
/*  55: 82 */     resizeComponents();
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected abstract void resizeComponents();
/*  59:    */   
/*  60:    */   public void render()
/*  61:    */   {
/*  62: 89 */     Frame[] frames = getFrames();
/*  63: 90 */     for (int i = frames.length - 1; i >= 0; i--) {
/*  64: 91 */       frames[i].render();
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void renderPinned()
/*  69:    */   {
/*  70: 96 */     Frame[] frames = getFrames();
/*  71: 97 */     for (int i = frames.length - 1; i >= 0; i--) {
/*  72: 98 */       if (frames[i].isPinned()) {
/*  73: 99 */         frames[i].render();
/*  74:    */       }
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void update()
/*  79:    */   {
/*  80:104 */     Frame[] frames = getFrames();
/*  81:105 */     for (int i = frames.length - 1; i >= 0; i--) {
/*  82:106 */       frames[i].update();
/*  83:    */     }
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.AbstractGuiManager
 * JD-Core Version:    0.7.0.1
 */