package org.spongepowered.asm.mixin.extensibility;

public interface IRemapper {
   String mapMethodName(String var1, String var2, String var3);

   String mapFieldName(String var1, String var2, String var3);

   String map(String var1);

   String unmap(String var1);

   String mapDesc(String var1);

   String unmapDesc(String var1);
}
