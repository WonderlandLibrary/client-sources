package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets$6;
import net.S3;
import net.a66;
import net.a93;
import net.a98;
import net.a9B;
import net.a9E;
import net.a9F;
import net.a9H;
import net.a9M;
import net.a9S;
import net.a9V;
import net.a9W;
import net.a9Z;
import net.a9f;
import net.a9i;
import net.a9l;
import net.a9u;
import net.ayx;

public class a_V {
   public static void a(ayx var0) {
      var0.a(a66.PLAY, 16, 26, new a93((a9W)null));
      var0.b(a66.PLAY, 27, 26, new a9W());
      var0.b(a66.PLAY, 37, 21, new a9E());
      var0.b(a66.PLAY, 38, 23, new a9l());
      var0.b(a66.PLAY, 39, 22, new a98());
      var0.c(a66.PLAY, 40, 20);
      var0.b(a66.PLAY, 41, 24, new a9B());
      var0.c(a66.PLAY, 47, 10);
      var0.b(a66.PLAY, 48, 19, new EntityPackets$6());
      var0.b(a66.PLAY, 49, 30, new a9u());
      var0.b(a66.PLAY, 52, 25, new a9S());
      var0.b(a66.PLAY, 57, 28, new a9F());
      S3.b();
      var0.b(a66.PLAY, 58, 27, new a9V());
      var0.c(a66.PLAY, 59, 18);
      var0.b(a66.PLAY, 60, 4, new a9M());
      var0.b(a66.PLAY, 64, 27, new a9Z());
      var0.c(a66.PLAY, 73, 13);
      var0.b(a66.PLAY, 74, 24, new a9H());
      var0.b(a66.PLAY, 75, 32, new a9f());
      var0.b(a66.PLAY, 76, 29, new a9i());
      if(PacketRemapper.b() == null) {
         S3.b(new PacketRemapper[2]);
      }

   }
}
