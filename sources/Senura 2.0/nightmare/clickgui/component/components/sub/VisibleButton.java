/*    */ package nightmare.clickgui.component.components.sub;
/*    */ 
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import nightmare.clickgui.component.Component;
/*    */ import nightmare.clickgui.component.components.Button;
/*    */ import nightmare.fonts.impl.Fonts;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.utils.ColorUtils;
/*    */ 
/*    */ public class VisibleButton
/*    */   extends Component {
/*    */   private Button parent;
/*    */   private int offset;
/*    */   private int x;
/*    */   private int y;
/*    */   private Module mod;
/*    */   
/*    */   public VisibleButton(Button button, Module mod, int offset) {
/* 19 */     this.parent = button;
/* 20 */     this.mod = mod;
/* 21 */     this.x = button.frame.getX() + button.frame.getWidth();
/* 22 */     this.y = button.frame.getY() + button.offset;
/* 23 */     this.offset = offset;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setOff(int newOff) {
/* 28 */     this.offset = newOff;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderComponent() {
/* 33 */     Gui.func_73734_a(this.parent.frame.getX(), this.parent.frame.getY() + this.offset, this.parent.frame.getX() + this.parent.frame.getWidth() * 1, this.parent.frame.getY() + this.offset + 18, ColorUtils.getBackgroundColor());
/* 34 */     Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString("Visible: " + this.mod.visible, (this.parent.frame.getX() + 7), (this.parent.frame.getY() + this.offset + 1 + 5), -1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateComponent(int mouseX, int mouseY) {
/* 39 */     this.y = this.parent.frame.getY() + this.offset;
/* 40 */     this.x = this.parent.frame.getX();
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 45 */     if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
/* 46 */       this.mod.visible = !this.mod.visible;
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isMouseOnButton(int x, int y) {
/* 51 */     if (x > this.x && x < this.x + 99 && y > this.y && y < this.y + 18) {
/* 52 */       return true;
/*    */     }
/* 54 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\clickgui\component\components\sub\VisibleButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */