package net.SliceClient.ui;

import net.SliceClient.module.Module;

public class ModuleComperator implements java.util.Comparator<Module> {
  private int referenceLength;
  
  public ModuleComperator() {}
  
  public int compare(Module s1, Module s2) { int dist1 = Math.abs(s1.getName().length() - referenceLength);
    int dist2 = Math.abs(s2.getName().length() - referenceLength);
    
    return dist1 - dist2;
  }
}
