package net.shadersmod.client;

import java.util.Comparator;
import net.shadersmod.client.ShaderOption;

final class ShaderPackParser$1 implements Comparator {
   public int compare(ShaderOption var1, ShaderOption var2) {
      return var1.getName().compareToIgnoreCase(var2.getName());
   }
}
