package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.remapper.ValueWriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.kH;
import org.jetbrains.annotations.Nullable;

public abstract class ValueTransformer implements ValueWriter {
   private final Type inputType;
   private final Type outputType;
   private static boolean c;

   public ValueTransformer(@Nullable Type var1, Type var2) {
      this.inputType = var1;
      this.outputType = var2;
   }

   public ValueTransformer(Type var1) {
      this((Type)null, var1);
   }

   public abstract Object a(PacketWrapperImpl var1, Object var2) throws Exception;

   public void a(PacketWrapperImpl var1, Object var2) throws Exception {
      try {
         var1.a(this.outputType, this.a(var1, var2));
      } catch (kH var4) {
         var4.a(this.getClass());
         throw var4;
      }
   }

   @Nullable
   public Type getInputType() {
      return this.inputType;
   }

   public Type getOutputType() {
      return this.outputType;
   }

   public static void b(boolean var0) {
      c = var0;
   }

   public static boolean b() {
      return c;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!b()) {
         b(true);
      }

   }
}
