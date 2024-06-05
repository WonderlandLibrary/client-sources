package net;

import io.netty.buffer.ByteBuf;
import java.util.Iterator;
import java.util.UUID;
import net.aDt;
import net.aRi;
import net.afz;
import net.axZ;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage$GameProfile;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage$Property;
import viaversion.viarewind.utils.PacketUtil;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ha implements PacketHandler {
   final aDt a;

   ha(aDt var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      afz.b();
      UUID var3 = (UUID)var1.read(Type.UUID);
      var1.write(Type.STRING, var3.toString());
      GameProfileStorage var4 = (GameProfileStorage)var1.user().b(GameProfileStorage.class);
      GameProfileStorage$GameProfile var5 = var4.get(var3);
      var1.write(Type.STRING, "");
      var1.write(Type.VAR_INT, Integer.valueOf(0));
      var1.write(Type.STRING, var5.name.length() > 16?var5.name.substring(0, 16):var5.name);
      var1.write(Type.VAR_INT, Integer.valueOf(var5.properties.size()));
      Iterator var6 = var5.properties.iterator();
      if(var6.hasNext()) {
         GameProfileStorage$Property var7 = (GameProfileStorage$Property)var6.next();
         var1.write(Type.STRING, var7.name);
         var1.write(Type.STRING, var7.value);
         var1.write(Type.STRING, var7.signature == null?"":var7.signature);
      }

      if(var5 != null && var5.gamemode == 3) {
         int var9 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
         PacketWrapper var11 = new PacketWrapper(4, (ByteBuf)null, var1.user());
         var11.write(Type.INT, Integer.valueOf(var9));
         var11.write(Type.SHORT, Short.valueOf((short)4));
         var11.write(axZ.c, var5.getSkull());
         PacketUtil.sendPacket(var11, aRi.class);
         short var8 = 0;
         if(var8 < 4) {
            var11 = new PacketWrapper(4, (ByteBuf)null, var1.user());
            var11.write(Type.INT, Integer.valueOf(var9));
            var11.write(Type.SHORT, Short.valueOf(var8));
            var11.write(axZ.c, (Object)null);
            PacketUtil.sendPacket(var11, aRi.class);
            ++var8;
         }
      }

      EntityTracker var10 = (EntityTracker)var1.user().b(EntityTracker.class);
      var10.addPlayer((Integer)var1.get(Type.VAR_INT, 0), var3);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
