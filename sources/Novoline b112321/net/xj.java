package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.aR0;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.minecraft.Position;
import viaversion.viaversion.api.type.Type;

public class xj {
   private static final Map a = new HashMap();
   private static boolean b;

   public static void a(List param0, UserConnection param1) {
      // $FF: Couldn't be decompiled
   }

   private static void a(Position var0, short var1, CompoundTag var2, UserConnection var3) throws Exception {
      PacketWrapper var4 = new PacketWrapper(9, (ByteBuf)null, var3);
      var4.write(Type.POSITION, var0);
      var4.write(Type.UNSIGNED_BYTE, Short.valueOf(var1));
      var4.write(Type.NBT, var2);
      var4.send(aR0.class, false);
   }

   static {
      b(false);
      a.put("MobSpawner", Integer.valueOf(1));
      a.put("Control", Integer.valueOf(2));
      a.put("Beacon", Integer.valueOf(3));
      a.put("Skull", Integer.valueOf(4));
      a.put("FlowerPot", Integer.valueOf(5));
      a.put("Banner", Integer.valueOf(6));
      a.put("UNKNOWN", Integer.valueOf(7));
      a.put("EndGateway", Integer.valueOf(8));
      a.put("Sign", Integer.valueOf(9));
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
