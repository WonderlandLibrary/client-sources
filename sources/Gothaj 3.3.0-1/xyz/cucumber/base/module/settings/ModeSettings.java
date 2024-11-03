package xyz.cucumber.base.module.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModeSettings extends ModuleSettings {
   private String mode;
   private List<String> modes = new ArrayList<>();

   public ModeSettings(String category, String name, String[] modes) {
      this.name = name;
      this.category = category;
      this.mode = modes[0];
      this.modes = Arrays.asList(modes);
   }

   public ModeSettings(String name, Supplier<Boolean> visibility, String[] modes) {
      this.name = name;
      this.mode = modes[0];
      this.visibility = visibility;
      this.modes = Arrays.asList(modes);
   }

   public ModeSettings(String name, String[] modes) {
      this.name = name;
      this.category = null;
      this.mode = modes[0];
      this.modes = Arrays.asList(modes);
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
}
