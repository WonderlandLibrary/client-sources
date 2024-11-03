package net.augustus.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.augustus.modules.Module;
import me.jDev.xenza.files.parts.SettingPart;

public class DoubleValue extends Setting {
   @Expose
   @SerializedName("Value")
   private double value;
   private double minValue;
   private double maxValue;
   private int decimalPlaces;

   public DoubleValue(int id, String name, Module parent, double value, double minValue, double maxValue, int decimalPlaces) {
      super(id, name, parent);
      this.value = value;
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.decimalPlaces = decimalPlaces;
      sm.newSetting(this);
   }

   public double getValue() {
      return this.value;
   }

   public void setValue(double value) {
      this.value = value;
   }

   public double getMinValue() {
      return this.minValue;
   }

   public void setMinValue(double minValue) {
      this.minValue = minValue;
   }

   public double getMaxValue() {
      return this.maxValue;
   }

   public void setMaxValue(double maxValue) {
      this.maxValue = maxValue;
   }

   public int getDecimalPlaces() {
      return this.decimalPlaces;
   }

   public void setDecimalPlaces(int decimalPlaces) {
      this.decimalPlaces = decimalPlaces;
   }

   @Override
   public void readSetting(SettingPart setting) {
      super.readSetting(setting);
      this.setValue(setting.getValue());
   }

   @Override
   public void readConfigSetting(SettingPart setting) {
      super.readConfigSetting(setting);
      this.setValue(setting.getValue());
   }
}
