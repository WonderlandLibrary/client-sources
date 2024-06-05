package net.minecraft.client.renderer.block.statemap;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.statemap.StateMap;

public class StateMap$Builder {
   private IProperty name;
   private String suffix;
   private final List ignored = Lists.newArrayList();

   public StateMap$Builder withName(IProperty var1) {
      this.name = var1;
      return this;
   }

   public StateMap$Builder withSuffix(String var1) {
      this.suffix = var1;
      return this;
   }

   public StateMap$Builder ignore(IProperty... var1) {
      Collections.addAll(this.ignored, var1);
      return this;
   }

   public StateMap build() {
      return new StateMap(this.name, this.suffix, this.ignored);
   }
}
