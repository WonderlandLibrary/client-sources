package me.jDev.xenza.files.parts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.augustus.modules.Module;
import net.augustus.settings.Setting;

public class SettingPart extends Setting {
   @Expose
   @SerializedName("Boolean")
   private boolean aBoolean;
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
   @Expose
   @SerializedName("Value")
   private double value;
   @Expose
   @SerializedName("MinValue")
   private double minValue;
   @Expose
   @SerializedName("MaxValue")
   private double maxValue;
   @Expose
   @SerializedName("DecimalPlaces")
   private int decimalPlaces;
   @Expose
   @SerializedName("SelectedOption")
   private String string;
   @Expose
   @SerializedName("Options")
   private String[] options;

   public SettingPart(
      int id,
      String name,
      Module parent,
      boolean aBoolean,
      int red,
      int green,
      int blue,
      int alpha,
      double value,
      double minValue,
      double maxValue,
      int decimalPlaces,
      String string,
      String[] options
   ) {
      super(id, name, parent);
      this.aBoolean = aBoolean;
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.alpha = alpha;
      this.value = value;
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.decimalPlaces = decimalPlaces;
      this.string = string;
      this.options = options;
   }

   public SettingPart(int id, String name, Module parent, String string, String[] options) {
      super(id, name, parent);
      this.string = string;
      this.options = options;
   }

   public SettingPart(int id, String name, Module parent, double value, double minValue, double maxValue, int decimalPlaces) {
      super(id, name, parent);
      this.value = value;
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.decimalPlaces = decimalPlaces;
   }

   public SettingPart(int id, String name, Module parent, int red, int green, int blue, int alpha) {
      super(id, name, parent);
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.alpha = alpha;
   }

   public SettingPart(int id, String name, Module parent, boolean aBoolean) {
      super(id, name, parent);
      this.aBoolean = aBoolean;
   }

   public boolean isaBoolean() {
      return this.aBoolean;
   }

   public void setaBoolean(boolean aBoolean) {
      this.aBoolean = aBoolean;
   }

   public int getRed() {
      return this.red;
   }

   public void setRed(int red) {
      this.red = red;
   }

   public int getGreen() {
      return this.green;
   }

   public void setGreen(int green) {
      this.green = green;
   }

   public int getBlue() {
      return this.blue;
   }

   public void setBlue(int blue) {
      this.blue = blue;
   }

   public int getAlpha() {
      return this.alpha;
   }

   public void setAlpha(int alpha) {
      this.alpha = alpha;
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

   public String getString() {
      return this.string;
   }

   public void setString(String string) {
      this.string = string;
   }

   public String[] getOptions() {
      return this.options;
   }

   public void setOptions(String[] options) {
      this.options = options;
   }
}
