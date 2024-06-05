package net;

import java.util.Iterator;
import java.util.Map.Entry;
import net.q1;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.ValueCreator;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;

final class n_ implements ValueCreator {
   public void write(PacketWrapper var1) throws Exception {
      var1.write(Type.VAR_INT, Integer.valueOf(Protocol1_13To1_12_2.MAPPINGS.getBlockTags().size()));
      q1.b();
      Iterator var3 = Protocol1_13To1_12_2.MAPPINGS.getBlockTags().entrySet().iterator();
      if(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         var1.write(Type.STRING, var4.getKey());
         var1.write(Type.VAR_INT_ARRAY_PRIMITIVE, Protocol1_13To1_12_2.toPrimitive((Integer[])var4.getValue()));
      }

      var1.write(Type.VAR_INT, Integer.valueOf(Protocol1_13To1_12_2.MAPPINGS.getItemTags().size()));
      var3 = Protocol1_13To1_12_2.MAPPINGS.getItemTags().entrySet().iterator();
      if(var3.hasNext()) {
         Entry var7 = (Entry)var3.next();
         var1.write(Type.STRING, var7.getKey());
         var1.write(Type.VAR_INT_ARRAY_PRIMITIVE, Protocol1_13To1_12_2.toPrimitive((Integer[])var7.getValue()));
      }

      var1.write(Type.VAR_INT, Integer.valueOf(Protocol1_13To1_12_2.MAPPINGS.getFluidTags().size()));
      var3 = Protocol1_13To1_12_2.MAPPINGS.getFluidTags().entrySet().iterator();
      if(var3.hasNext()) {
         Entry var8 = (Entry)var3.next();
         var1.write(Type.STRING, var8.getKey());
         var1.write(Type.VAR_INT_ARRAY_PRIMITIVE, Protocol1_13To1_12_2.toPrimitive((Integer[])var8.getValue()));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
