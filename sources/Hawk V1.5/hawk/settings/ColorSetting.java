package hawk.settings;

import hawk.modules.Module;
import hawk.util.JColor;
import java.awt.Color;

public class ColorSetting extends Setting implements com.lukflug.panelstudio.settings.ColorSetting {
   private JColor value;
   private Module parent;
   private boolean rainbow;

   public void setValue(Color var1) {
      this.setValue(this.getRainbow(), new JColor(var1));
   }

   public Color getColor() {
      return this.getColor();
   }

   public JColor getValue() {
      return this.rainbow ? JColor.fromHSB((float)(System.currentTimeMillis() % 7200L) / 7200.0F, 0.5F, 1.0F) : this.value;
   }

   public Color getValue() {
      return this.getValue();
   }

   public void setRainbow(boolean var1) {
      this.rainbow = var1;
   }

   public long toInteger() {
      return (long)(this.value.getRGB() & -1);
   }

   public static int rainbow(int var0) {
      double var1 = Math.ceil((double)(System.currentTimeMillis() + (long)var0) / 20.0D);
      var1 %= 360.0D;
      return Color.getHSBColor((float)(var1 / 360.0D), 0.5F, 1.0F).getRGB();
   }

   public boolean getRainbow() {
      return this.rainbow;
   }

   public void fromInteger(long var1) {
      this.value = new JColor(Math.toIntExact(var1 & -1L), true);
   }

   public JColor getColor() {
      return this.value;
   }

   public void setValue(boolean var1, JColor var2) {
      this.rainbow = var1;
      this.value = var2;
   }

   public ColorSetting(String var1, JColor var2) {
      this.name = var1;
      this.value = var2;
   }
}
