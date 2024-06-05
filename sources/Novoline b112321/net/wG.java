package net;

import io.netty.buffer.ByteBuf;
import java.util.Iterator;
import net.Gh;
import net.nP;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.api.type.types.Particle$ParticleData;

public class wG extends Type {
   public wG() {
      super("Particle", Gh.class);
   }

   public void a(ByteBuf var1, Gh var2) throws Exception {
      nP.b();
      Type.VAR_INT.writePrimitive(var1, var2.c());
      Iterator var4 = var2.d().iterator();
      if(var4.hasNext()) {
         Particle$ParticleData var5 = (Particle$ParticleData)var4.next();
         var5.getType().write(var1, var5.getValue());
      }

   }

   public Gh a(ByteBuf var1) throws Exception {
      int var3 = Type.VAR_INT.readPrimitive(var1);
      nP.b();
      Gh var4 = new Gh(var3);
      switch(var3) {
      case 3:
      case 23:
         var4.d().add(new Particle$ParticleData(Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(var1))));
      case 14:
         var4.d().add(new Particle$ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(var1))));
         var4.d().add(new Particle$ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(var1))));
         var4.d().add(new Particle$ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(var1))));
         var4.d().add(new Particle$ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(var1))));
      case 32:
         var4.d().add(new Particle$ParticleData(Type.FLAT_VAR_INT_ITEM, Type.FLAT_VAR_INT_ITEM.read(var1)));
      default:
         return var4;
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
