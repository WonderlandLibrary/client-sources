package cc.novoline.gui.button;

import cc.novoline.gui.AbstractElementWithBody;
import cc.novoline.gui.button.Button;
import cc.novoline.gui.label.Label;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.java.Checks;
import java.util.Objects;
import net.acE;

public abstract class AbstractButton extends AbstractElementWithBody implements Button {
   protected Label name;
   private static acE[] g;

   public AbstractButton(Label var1, int var2, int var3, int var4, int var5) {
      super(var2, var3, var4, var5);
      this.setName(var1);
   }

   public AbstractButton(Label var1, int var2, int var3) {
      super(var2, var3, 200, 20);
      this.setName(var1);
   }

   public void onDraw(int var1, int var2) {
      acE[] var3 = a();
      if(this.visible) {
         int var4 = !this.isHovered(var1, var2)?210:125;
         RenderUtils.drawRoundedRect((float)this.x, (float)this.y, (float)this.width, (float)this.height, 15.0F, 3158326 | var4 << 24);
         if(this.name != null) {
            this.name.draw(var1, var2);
         }
      }

   }

   public void updateNamePosition() throws NullPointerException {
      a();
      Checks.notNull(this.name, "name");
      int var2 = (int)((float)this.x + (float)(this.width - this.name.getWidth()) / 2.0F);
      int var3 = (int)((float)this.y + (float)(this.height - this.name.getFontRenderer().getHeight()) / 2.0F);
      this.name.setPosition(var2, var3);
   }

   public Label getName() {
      return this.name;
   }

   public void setName(Label var1) {
      acE[] var2 = a();
      this.name = var1;
      this.updateNamePosition();
   }

   public boolean equals(Object var1) {
      acE[] var2 = a();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof AbstractButton)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         AbstractButton var3 = (AbstractButton)var1;
         return Objects.equals(this.name, var3.name);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), this.name});
   }

   public String toString() {
      return "AbstractButton{name=" + this.name + '}';
   }

   public static void b(acE[] var0) {
      g = var0;
   }

   public static acE[] a() {
      return g;
   }

   private static NullPointerException a(NullPointerException var0) {
      return var0;
   }

   static {
      b((acE[])null);
   }
}
