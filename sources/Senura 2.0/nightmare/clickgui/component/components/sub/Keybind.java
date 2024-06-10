/*    */ package nightmare.clickgui.component.components.sub;
/*    */ 
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import nightmare.clickgui.component.Component;
/*    */ import nightmare.clickgui.component.components.Button;
/*    */ import nightmare.fonts.impl.Fonts;
/*    */ import nightmare.utils.ColorUtils;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ public class Keybind
/*    */   extends Component
/*    */ {
/*    */   private boolean binding;
/*    */   private Button parent;
/*    */   private int offset;
/*    */   private int x;
/*    */   private int y;
/*    */   
/*    */   public Keybind(Button button, int offset) {
/* 20 */     this.parent = button;
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
/*    */     
/* 35 */     Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(this.binding ? "Press a key..." : ("Key: " + Keyboard.getKeyName(this.parent.module.getKey())), (this.parent.frame.getX() + 7), (this.parent.frame.getY() + this.offset + 1 + 5), -1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateComponent(int mouseX, int mouseY) {
/* 40 */     this.y = this.parent.frame.getY() + this.offset;
/* 41 */     this.x = this.parent.frame.getX();
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 46 */     if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
/* 47 */       this.binding = !this.binding;
/*    */     }
/*    */     
/* 50 */     if (this.binding && 
/* 51 */       button == 2) {
/* 52 */       this.parent.module.setKey(0);
/* 53 */       this.binding = false;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void keyTyped(char typedChar, int key) {
/* 60 */     if (this.binding) {
/* 61 */       this.parent.module.setKey(key);
/* 62 */       this.binding = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isMouseOnButton(int x, int y) {
/* 67 */     if (x > this.x && x < this.x + 99 && y > this.y && y < this.y + 18) {
/* 68 */       return true;
/*    */     }
/* 70 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\clickgui\component\components\sub\Keybind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */