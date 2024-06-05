package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle$ParticleData;
import io.netty.buffer.ByteBuf;
import java.util.Iterator;
import net.Gh;
import shadersmod.client.Shaders;

public class wt extends Type {
   public wt() {
      super("Particle", Gh.class);
   }

   public void a(ByteBuf var1, Gh var2) throws Exception {
      Shaders.v();
      Type.VAR_INT.writePrimitive(var1, var2.c());
      Iterator var4 = var2.d().iterator();
      if(var4.hasNext()) {
         Particle$ParticleData var5 = (Particle$ParticleData)var4.next();
         var5.getType().write(var1, var5.getValue());
      }

      if(PacketRemapper.b() == null) {
         Shaders.b(new String[3]);
      }

   }

   public Gh a(ByteBuf var1) throws Exception {
      Shaders.v();
      int var3 = Type.VAR_INT.readPrimitive(var1);
      Gh var4 = new Gh(var3);
      switch(var3) {
      case 3:
      case 20:
         var4.d().add(new Particle$ParticleData(Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(var1))));
      case 11:
         var4.d().add(new Particle$ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(var1))));
         var4.d().add(new Particle$ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(var1))));
         var4.d().add(new Particle$ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(var1))));
         var4.d().add(new Particle$ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(var1))));
      case 27:
         var4.d().add(new Particle$ParticleData(Type.FLAT_VAR_INT_ITEM, Type.FLAT_VAR_INT_ITEM.read(var1)));
      default:
         return var4;
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
