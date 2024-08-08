package org.spongepowered.asm.mixin.refmap;

public interface IReferenceMapper {
   boolean isDefault();

   String getResourceName();

   String getStatus();

   String getContext();

   void setContext(String var1);

   String remap(String var1, String var2);

   String remapWithContext(String var1, String var2, String var3);
}
