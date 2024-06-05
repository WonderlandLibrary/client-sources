package cc.novoline.gui.button;

import cc.novoline.gui.button.AbstractButton;
import cc.novoline.gui.label.Label;
import java.util.function.Consumer;
import net.acE;

public final class FunctionalButton extends AbstractButton {
   private final Consumer click;

   public FunctionalButton(Label var1, int var2, int var3, int var4, int var5, Consumer var6) {
      super(var1, var2, var3, var4, var5);
      this.click = var6;
   }

   public FunctionalButton(Label var1, int var2, int var3, Consumer var4) {
      super(var1, var2, var3);
      this.click = var4;
   }

   public FunctionalButton(Label var1, int var2, int var3, int var4, int var5) {
      this(var1, var2, var3, var4, var5, (Consumer)null);
   }

   public FunctionalButton(Label var1, int var2, int var3) {
      this(var1, var2, var3, (Consumer)null);
   }

   public void click(int var1) {
      acE[] var2 = AbstractButton.a();
      if(this.click != null) {
         this.click.accept(Integer.valueOf(var1));
      }

   }
}
