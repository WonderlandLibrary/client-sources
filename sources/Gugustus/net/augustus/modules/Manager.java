package net.augustus.modules;

import java.util.ArrayList;
import java.util.List;

public class Manager {
   private List<Module> modules = new ArrayList<>();
   private ArrayList<Module> activeModules = new ArrayList<>();

   public List<Module> getModules() {
      return this.modules;
   }

   public void setModules(List<Module> modules) {
      this.modules = modules;
   }

   public ArrayList<Module> getActiveModules() {
      return this.activeModules;
   }

   public void setActiveModules(ArrayList<Module> activeModules) {
      this.activeModules = activeModules;
   }
}
