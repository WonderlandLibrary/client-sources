package xyz.cucumber.base.module.settings;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ColorSettings extends ModuleSettings {
   private String mode = "Static";
   private List<String> modes = Arrays.asList("Static", "Rainbow", "Mix", "Sky");
   private int mainColor;
   private int secondaryColor;
   private int alpha;

   public ColorSettings(String name, String mode, int mainColor, int secondaryColor, int alpha) {
      this.name = name;
      this.mode = mode;
      this.mainColor = mainColor;
      this.secondaryColor = secondaryColor;
      if (alpha > 100) {
         alpha = 100;
      }

      this.alpha = alpha;
      this.category = null;
   }

   public ColorSettings(String name, Supplier<Boolean> visibility, String mode, int mainColor, int secondaryColor, int alpha) {
      this.name = name;
      this.mode = mode;
      this.mainColor = mainColor;
      this.secondaryColor = secondaryColor;
      if (alpha > 100) {
         alpha = 100;
      }

      this.alpha = alpha;
      this.category = null;
      this.visibility = visibility;
   }

   public ColorSettings(String category, String name, String mode, int mainColor, int secondaryColor, int alpha) {
      this.name = name;
      this.mode = mode;
      this.mainColor = mainColor;
      this.secondaryColor = secondaryColor;
      if (alpha > 100) {
         alpha = 100;
      }

      this.alpha = alpha;
      this.category = category;
   }

   public void cycleModes() {
      if (this.modes.indexOf(this.mode) == this.modes.size() - 1) {
         this.mode = this.modes.get(0);
      } else {
         this.mode = this.modes.get(this.modes.indexOf(this.mode) + 1);
      }
   }

   public String getMode() {
      return this.mode;
   }

   public void setMode(String mode) {
      this.mode = mode;
   }

   public List<String> getModes() {
      return this.modes;
   }

   public void setModes(List<String> modes) {
      this.modes = modes;
   }

   public int getMainColor() {
      return this.mainColor;
   }

   public void setMainColor(int mainColor) {
      this.mainColor = mainColor;
   }

   public int getSecondaryColor() {
      return this.secondaryColor;
   }

   public int getFinalColor(float offset) {
      return 0;
   }

   public void setSecondaryColor(int secondaryColor) {
      this.secondaryColor = secondaryColor;
   }

   public int getAlpha() {
      return this.alpha;
   }

   public void setAlpha(int alpha) {
      this.alpha = alpha;
   }
}
