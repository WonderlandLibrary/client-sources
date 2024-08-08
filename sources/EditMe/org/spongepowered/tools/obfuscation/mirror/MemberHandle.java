package org.spongepowered.tools.obfuscation.mirror;

import org.spongepowered.asm.obfuscation.mapping.IMapping;

public abstract class MemberHandle {
   private final String owner;
   private final String name;
   private final String desc;

   protected MemberHandle(String var1, String var2, String var3) {
      this.owner = var1;
      this.name = var2;
      this.desc = var3;
   }

   public final String getOwner() {
      return this.owner;
   }

   public final String getName() {
      return this.name;
   }

   public final String getDesc() {
      return this.desc;
   }

   public abstract Visibility getVisibility();

   public abstract IMapping asMapping(boolean var1);
}
