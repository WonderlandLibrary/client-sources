package cc.novoline.gui.label;

import cc.novoline.gui.AbstractElement;
import cc.novoline.gui.label.Label;
import cc.novoline.utils.fonts.api.FontRenderer;
import java.util.Objects;
import net.acE;

public abstract class AbstractLabel extends AbstractElement implements Label {
   protected String text;
   protected int color;
   protected FontRenderer fontRenderer;
   private static acE[] h;

   public AbstractLabel(String var1, int var2, FontRenderer var3, int var4, int var5) {
      super(var4, var5);
      this.text = var1;
      this.color = var2;
      a();
      this.fontRenderer = var3;
      if(acE.b() == null) {
         b(new acE[5]);
      }

   }

   public AbstractLabel(String var1, int var2, FontRenderer var3) {
      this(var1, var2, var3, 0, 0);
   }

   public void onDraw(int var1, int var2) {
      this.fontRenderer.drawString(this.text, (float)this.x, (float)this.y, this.color);
   }

   public String getText() {
      return this.text;
   }

   public void setText(String var1) {
      this.text = var1;
   }

   public int getColor() {
      return this.color;
   }

   public void setColor(int var1) {
      this.color = var1;
   }

   public FontRenderer getFontRenderer() {
      return this.fontRenderer;
   }

   public void setFontRenderer(FontRenderer var1) {
      this.fontRenderer = var1;
   }

   public boolean equals(Object var1) {
      acE[] var2 = a();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof AbstractLabel)) {
         return false;
      } else {
         AbstractLabel var3 = (AbstractLabel)var1;
         return Objects.equals(this.text, var3.text) && this.fontRenderer.equals(var3.fontRenderer);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.text, this.fontRenderer});
   }

   public String toString() {
      acE[] var1 = a();
      return "AbstractLabel{text=\'" + this.text + '\'' + ", color=" + this.color + ", fontRenderer=" + this.fontRenderer + '}';
   }

   public static void b(acE[] var0) {
      h = var0;
   }

   public static acE[] a() {
      return h;
   }

   static {
      b(new acE[4]);
   }
}
