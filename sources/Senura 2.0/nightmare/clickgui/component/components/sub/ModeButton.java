/*    */ package nightmare.clickgui.component.components.sub;
/*    */ 
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import nightmare.clickgui.component.Component;
/*    */ import nightmare.clickgui.component.components.Button;
/*    */ import nightmare.fonts.impl.Fonts;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ import nightmare.utils.ColorUtils;
/*    */ 
/*    */ public class ModeButton
/*    */   extends Component
/*    */ {
/*    */   private Button parent;
/*    */   private Setting set;
/*    */   private int offset;
/*    */   private int x;
/*    */   private int y;
/*    */   private int modeIndex;
/*    */   
/*    */   public ModeButton(Setting set, Button button, Module mod, int offset) {
/* 22 */     this.set = set;
/* 23 */     this.parent = button;
/* 24 */     this.x = button.frame.getX() + button.frame.getWidth();
/* 25 */     this.y = button.frame.getY() + button.offset;
/* 26 */     this.offset = offset;
/* 27 */     this.modeIndex = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setOff(int newOff) {
/* 32 */     this.offset = newOff;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderComponent() {
/* 37 */     Gui.func_73734_a(this.parent.frame.getX(), this.parent.frame.getY() + this.offset, this.parent.frame.getX() + this.parent.frame.getWidth() * 1, this.parent.frame.getY() + this.offset + 18, ColorUtils.getBackgroundColor());
/*    */     
/* 39 */     Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(this.set.getName() + ": " + this.set.getValString(), (this.parent.frame.getX() + 7), (this.parent.frame.getY() + this.offset + 2 + 5), -1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateComponent(int mouseX, int mouseY) {
/* 44 */     this.y = this.parent.frame.getY() + this.offset;
/* 45 */     this.x = this.parent.frame.getX();
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 50 */     if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
/* 51 */       int maxIndex = this.set.getOptions().size();
/*    */       
/* 53 */       if (this.modeIndex + 1 >= maxIndex) {
/* 54 */         this.modeIndex = 0;
/*    */       } else {
/* 56 */         this.modeIndex++;
/*    */       } 
/* 58 */       this.set.setValString(this.set.getOptions().get(this.modeIndex));
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isMouseOnButton(int x, int y) {
/* 63 */     if (x > this.x && x < this.x + 88 && y > this.y && y < this.y + 18) {
/* 64 */       return true;
/*    */     }
/* 66 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\clickgui\component\components\sub\ModeButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */