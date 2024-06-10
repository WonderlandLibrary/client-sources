/*     */ package nightmare.clickgui.component.components;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.clickgui.component.Component;
/*     */ import nightmare.clickgui.component.Frame;
/*     */ import nightmare.clickgui.component.components.sub.Checkbox;
/*     */ import nightmare.clickgui.component.components.sub.Keybind;
/*     */ import nightmare.clickgui.component.components.sub.ModeButton;
/*     */ import nightmare.clickgui.component.components.sub.Slider;
/*     */ import nightmare.clickgui.component.components.sub.VisibleButton;
/*     */ import nightmare.fonts.impl.Fonts;
/*     */ import nightmare.module.Module;
/*     */ import nightmare.settings.Setting;
/*     */ import nightmare.utils.ColorUtils;
/*     */ 
/*     */ public class Button
/*     */   extends Component
/*     */ {
/*     */   public Module module;
/*     */   public Frame frame;
/*     */   public int offset;
/*     */   private ArrayList<Component> components;
/*     */   public boolean open;
/*     */   
/*     */   public Button(Module mod, Frame parent, int offset) {
/*  28 */     this.module = mod;
/*  29 */     this.frame = parent;
/*  30 */     this.offset = offset;
/*  31 */     this.components = new ArrayList<>();
/*  32 */     this.open = false;
/*  33 */     int opY = offset + 18;
/*  34 */     if (Nightmare.instance.settingsManager.getSettingsByMod(mod) != null) {
/*  35 */       for (Setting s : Nightmare.instance.settingsManager.getSettingsByMod(mod)) {
/*  36 */         if (s.isCombo()) {
/*  37 */           this.components.add(new ModeButton(s, this, mod, opY));
/*  38 */           opY += 18;
/*     */         } 
/*  40 */         if (s.isSlider()) {
/*  41 */           this.components.add(new Slider(s, this, opY));
/*  42 */           opY += 18;
/*     */         } 
/*  44 */         if (s.isCheck()) {
/*  45 */           this.components.add(new Checkbox(s, this, opY));
/*  46 */           opY += 18;
/*     */         } 
/*     */       } 
/*     */     }
/*  50 */     this.components.add(new Keybind(this, opY));
/*  51 */     this.components.add(new VisibleButton(this, mod, opY));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOff(int newOff) {
/*  56 */     this.offset = newOff;
/*  57 */     int opY = this.offset + 18;
/*  58 */     for (Component comp : this.components) {
/*  59 */       comp.setOff(opY);
/*  60 */       opY += 18;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderComponent() {
/*  67 */     Gui.func_73734_a(this.frame.getX(), this.frame.getY() + this.offset, this.frame.getX() + this.frame.getWidth(), this.frame.getY() + 18 + this.offset, ColorUtils.getBackgroundColor());
/*     */     
/*  69 */     Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(this.module.getName(), (this.frame.getX() + 2), (this.frame.getY() + this.offset + 2 + 4), this.module.isToggled() ? ColorUtils.getClientColor() : -1);
/*     */     
/*  71 */     if (this.components.size() > 2) {
/*  72 */       Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(this.open ? "-" : "+", (this.frame.getX() + this.frame.getWidth() - 10), (this.frame.getY() + this.offset + 2 + 4), -1, true);
/*     */     }
/*     */     
/*  75 */     if (this.open && 
/*  76 */       !this.components.isEmpty()) {
/*  77 */       for (Component comp : this.components) {
/*  78 */         comp.renderComponent();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  86 */     if (this.open) {
/*  87 */       return 18 * (this.components.size() + 1);
/*     */     }
/*  89 */     return 18;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateComponent(int mouseX, int mouseY) {
/*  94 */     if (!this.components.isEmpty()) {
/*  95 */       for (Component comp : this.components) {
/*  96 */         comp.updateComponent(mouseX, mouseY);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 103 */     if (isMouseOnButton(mouseX, mouseY) && button == 0) {
/* 104 */       this.module.toggle();
/*     */     }
/* 106 */     if (isMouseOnButton(mouseX, mouseY) && button == 1) {
/* 107 */       this.open = !this.open;
/* 108 */       this.frame.refresh();
/*     */     } 
/* 110 */     for (Component comp : this.components) {
/* 111 */       comp.mouseClicked(mouseX, mouseY, button);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
/* 117 */     for (Component comp : this.components) {
/* 118 */       comp.mouseReleased(mouseX, mouseY, mouseButton);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void keyTyped(char typedChar, int key) {
/* 124 */     for (Component comp : this.components) {
/* 125 */       comp.keyTyped(typedChar, key);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isMouseOnButton(int x, int y) {
/* 130 */     if (x > this.frame.getX() && x < this.frame.getX() + this.frame.getWidth() && y > this.frame.getY() + this.offset && y < this.frame.getY() + 18 + this.offset) {
/* 131 */       return true;
/*     */     }
/* 133 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\clickgui\component\components\Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */