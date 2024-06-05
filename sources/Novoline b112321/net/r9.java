package net;

import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.ValueCreator;
import viaversion.viaversion.api.type.Type;

final class r9 implements ValueCreator {
   public void write(PacketWrapper var1) {
      var1.write(Type.VAR_INT, Integer.valueOf(2));
      var1.write(Type.VAR_INT, Integer.valueOf(0));
      var1.write(Type.VAR_INT, Integer.valueOf(1));
      var1.write(Type.VAR_INT, Integer.valueOf(1));
      var1.write(Type.VAR_INT, Integer.valueOf(22));
      var1.write(Type.VAR_INT, Integer.valueOf(0));
      var1.write(Type.STRING, "args");
      var1.write(Type.STRING, "brigadier:string");
      var1.write(Type.VAR_INT, Integer.valueOf(2));
      var1.write(Type.STRING, "minecraft:ask_server");
      var1.write(Type.VAR_INT, Integer.valueOf(0));
   }
}
