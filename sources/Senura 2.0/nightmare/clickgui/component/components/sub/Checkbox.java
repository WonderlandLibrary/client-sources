/*    */ package nightmare.clickgui.component.components.sub;
/*    */ 
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import nightmare.clickgui.component.Component;
/*    */ import nightmare.clickgui.component.components.Button;
/*    */ import nightmare.fonts.impl.Fonts;
/*    */ import nightmare.settings.Setting;
/*    */ import nightmare.utils.ColorUtils;
/*    */ 
/*    */ public class Checkbox
/*    */   extends Component {
/*    */   private Setting op;
/*    */   private Button parent;
/*    */   private int offset;
/*    */   private int x;
/*    */   private int y;
/*    */   
/*    */   public Checkbox(Setting option, Button button, int offset) {
/* 19 */     this.op = option;
/* 20 */     this.parent = button;
/* 21 */     this.x = button.frame.getX() + button.frame.getWidth();
/* 22 */     this.y = button.frame.getY() + button.offset;
/* 23 */     this.offset = offset;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderComponent() {
/* 28 */     Gui.func_73734_a(this.parent.frame.getX(), this.parent.frame.getY() + this.offset, this.parent.frame.getX() + this.parent.frame.getWidth() * 1, this.parent.frame.getY() + this.offset + 18, ColorUtils.getBackgroundColor());
/* 29 */     Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(this.op.getName(), (this.parent.frame.getX() + 17), (this.parent.frame.getY() + this.offset + 7), -1);
/*    */     
/* 31 */     Gui.func_73734_a(this.parent.frame.getX() + 4, this.parent.frame.getY() + this.offset + 5, this.parent.frame.getX() + 14, this.parent.frame.getY() + this.offset + 15, ColorUtils.getClientColor());
/*    */     
/* 33 */     if (this.op.getValBoolean()) {
/* 34 */       Fonts.ICON.ICON_20.ICON_20.drawString("A", (this.parent.frame.getX() + 4), (this.parent.frame.getY() + this.offset + 8), -1);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void setOff(int newOff) {
/* 40 */     this.offset = newOff;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateComponent(int mouseX, int mouseY) {
/* 45 */     this.y = this.parent.frame.getY() + this.offset;
/* 46 */     this.x = this.parent.frame.getX();
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 51 */     if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
/* 52 */       this.op.setValBoolean(!this.op.getValBoolean());
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isMouseOnButton(int x, int y) {
/* 57 */     if (x > this.x && x < this.x + 88 && y > this.y && y < this.y + 18) {
/* 58 */       return true;
/*    */     }
/* 60 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\clickgui\component\components\sub\Checkbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */