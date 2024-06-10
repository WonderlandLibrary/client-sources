/*     */ package nightmare.clickgui.component.components.sub;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import nightmare.clickgui.component.Component;
/*     */ import nightmare.clickgui.component.components.Button;
/*     */ import nightmare.fonts.impl.Fonts;
/*     */ import nightmare.settings.Setting;
/*     */ import nightmare.utils.ColorUtils;
/*     */ 
/*     */ 
/*     */ public class Slider
/*     */   extends Component
/*     */ {
/*     */   private Setting set;
/*     */   private Button parent;
/*     */   private int offset;
/*     */   private int x;
/*     */   private int y;
/*     */   private boolean dragging = false;
/*     */   private double renderWidth;
/*     */   
/*     */   public Slider(Setting value, Button button, int offset) {
/*  25 */     this.set = value;
/*  26 */     this.parent = button;
/*  27 */     this.x = button.frame.getX() + button.frame.getWidth();
/*  28 */     this.y = button.frame.getY() + button.offset;
/*  29 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderComponent() {
/*  35 */     Gui.func_73734_a(this.parent.frame.getX(), this.parent.frame.getY() + this.offset, this.parent.frame.getX() + this.parent.frame.getWidth(), this.parent.frame.getY() + this.offset + 18, ColorUtils.getBackgroundColor());
/*  36 */     Gui.func_73734_a(this.parent.frame.getX(), this.parent.frame.getY() + this.offset + 2, this.parent.frame.getX() + (int)this.renderWidth, this.parent.frame.getY() + this.offset + 18, ColorUtils.getClientColor());
/*  37 */     Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(this.set.getName() + ": " + this.set.getValDouble(), (this.parent.frame.getX() + 5), (this.parent.frame.getY() + this.offset + 2 + 5), -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOff(int newOff) {
/*  42 */     this.offset = newOff;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateComponent(int mouseX, int mouseY) {
/*  47 */     this.y = this.parent.frame.getY() + this.offset;
/*  48 */     this.x = this.parent.frame.getX();
/*     */     
/*  50 */     double diff = Math.min(99, Math.max(0, mouseX - this.x));
/*     */     
/*  52 */     double min = this.set.getMin();
/*  53 */     double max = this.set.getMax();
/*     */     
/*  55 */     this.renderWidth = 99.0D * (this.set.getValDouble() - min) / (max - min);
/*     */     
/*  57 */     if (this.dragging) {
/*  58 */       if (diff == 0.0D) {
/*  59 */         this.set.setValDouble(this.set.getMin());
/*     */       } else {
/*     */         
/*  62 */         double newValue = roundToPlace(diff / 99.0D * (max - min) + min, 2);
/*  63 */         this.set.setValDouble(newValue);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static double roundToPlace(double value, int places) {
/*  69 */     if (places < 0) {
/*  70 */       throw new IllegalArgumentException();
/*     */     }
/*  72 */     BigDecimal bd = new BigDecimal(value);
/*  73 */     bd = bd.setScale(places, RoundingMode.HALF_UP);
/*  74 */     return bd.doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) {
/*  79 */     if (isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
/*  80 */       this.dragging = true;
/*     */     }
/*  82 */     if (isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
/*  83 */       this.dragging = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
/*  89 */     this.dragging = false;
/*     */   }
/*     */   
/*     */   public boolean isMouseOnButtonD(int x, int y) {
/*  93 */     if (x > this.x && x < this.x + this.parent.frame.getWidth() / 2 + 1 && y > this.y && y < this.y + 18) {
/*  94 */       return true;
/*     */     }
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isMouseOnButtonI(int x, int y) {
/* 100 */     if (x > this.x + this.parent.frame.getWidth() / 2 && x < this.x + this.parent.frame.getWidth() && y > this.y && y < this.y + 18) {
/* 101 */       return true;
/*     */     }
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\clickgui\component\components\sub\Slider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */