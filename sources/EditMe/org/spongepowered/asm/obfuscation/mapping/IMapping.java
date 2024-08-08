package org.spongepowered.asm.obfuscation.mapping;

public interface IMapping {
   IMapping.Type getType();

   Object move(String var1);

   Object remap(String var1);

   Object transform(String var1);

   Object copy();

   String getName();

   String getSimpleName();

   String getOwner();

   String getDesc();

   Object getSuper();

   String serialise();

   public static enum Type {
      FIELD,
      METHOD,
      CLASS,
      PACKAGE;

      private static final IMapping.Type[] $VALUES = new IMapping.Type[]{FIELD, METHOD, CLASS, PACKAGE};
   }
}
