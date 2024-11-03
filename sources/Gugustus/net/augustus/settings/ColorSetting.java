package net.augustus.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.awt.Color;
import net.augustus.modules.Module;
import me.jDev.xenza.files.parts.SettingPart;

public class ColorSetting extends Setting {
   @Expose
   @SerializedName("Red")
   private int red;
   @Expose
   @SerializedName("Green")
   private int green;
   @Expose
   @SerializedName("Blue")
   private int blue;
   @Expose
   @SerializedName("Alpha")
   private int alpha;
   private Color color;

   public ColorSetting(int id, String name, Module parent, Color color) {
      super(id, name, parent);
      this.color = color;
      this.red = this.color.getRed();
      this.blue = this.color.getBlue();
      this.green = this.color.getGreen();
      this.alpha = this.color.getAlpha();
      sm.newSetting(this);
   }

   public Color getColor() {
      return this.color;
   }

   public void setColor(Color color) {
      this.color = color;
      this.red = this.color.getRed();
      this.blue = this.color.getBlue();
      this.green = this.color.getGreen();
      this.alpha = this.color.getAlpha();
   }

   @Override
   public void readSetting(SettingPart setting) {
      super.readSetting(setting);
   }

   @Override
   public void readConfigSetting(SettingPart setting) {
      super.readConfigSetting(setting);
      this.setColor(new Color(setting.getRed(), setting.getGreen(), setting.getBlue(), setting.getAlpha()));
   }
}
