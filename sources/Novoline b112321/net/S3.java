package net;

import net.a66;
import net.a91;
import net.a92;
import net.a94;
import net.a9G;
import net.a9N;
import net.a9O;
import net.a9R;
import net.a9U;
import net.a9X;
import net.a9Y;
import net.a9b;
import net.a9d;
import net.a9e;
import net.a9h;
import net.a9q;
import net.a9x;
import net.acE;
import viaversion.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets$15;
import viaversion.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets$16;
import viaversion.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets$20;
import viaversion.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets$21;
import viaversion.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets$22;
import viaversion.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets$5;
import viaversion.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets$7;
import viaversion.viaversion.api.protocol.Protocol;

public class S3 {
   private static acE[] b;

   public static void a(Protocol var0) {
      b();
      var0.c(a66.PLAY, 6, 11);
      var0.c(a66.PLAY, 7, 55);
      var0.b(a66.PLAY, 12, -1, new a9b());
      var0.c(a66.PLAY, 14, 58);
      var0.c(a66.PLAY, 15, 2);
      var0.b(a66.PLAY, 23, -1, new a9h());
      var0.b(a66.PLAY, 24, 63, new a9Y());
      var0.c(a66.PLAY, 26, 64);
      var0.b(a66.PLAY, 30, 43, new a92());
      var0.b(a66.PLAY, 35, 1, new PlayerPackets$5());
      var0.c(a66.PLAY, 42, 54);
      var0.c(a66.PLAY, 43, 57);
      var0.c(a66.PLAY, 45, 56);
      var0.b(a66.PLAY, 46, 8, new a9O());
      var0.c(a66.PLAY, 50, 72);
      var0.b(a66.PLAY, 51, 7, new PlayerPackets$7());
      var0.c(a66.PLAY, 54, 67);
      var0.c(a66.PLAY, 55, 9);
      var0.c(a66.PLAY, 61, 31);
      var0.c(a66.PLAY, 62, 6);
      var0.c(a66.PLAY, 67, 5);
      var0.c(a66.PLAY, 69, 69);
      var0.c(a66.PLAY, 72, 71);
      var0.a(a66.PLAY, 2, 1, new a9x());
      var0.a(a66.PLAY, 5, 15);
      var0.a(a66.PLAY, 10, 2, new a9d());
      var0.a(a66.PLAY, 15, 3, new a9N());
      var0.a(a66.PLAY, 12, 4, new a9U());
      var0.a(a66.PLAY, 14, 5, new a91());
      var0.a(a66.PLAY, 13, 6, new a94());
      var0.a(a66.PLAY, 19, 7, new a9q());
      var0.a(a66.PLAY, 28, 8, new PlayerPackets$15());
      var0.a(a66.PLAY, 23, 9, new PlayerPackets$16());
      var0.a(a66.PLAY, 26, 10, new a9e());
      var0.a(a66.PLAY, 20, 11, new a9X());
      var0.a(a66.PLAY, 21, 12, new a9G());
      var0.a(a66.PLAY, 25, 18, new PlayerPackets$20());
      var0.a(a66.PLAY, 18, 19);
      var0.a(a66.PLAY, 1, 20, new PlayerPackets$21());
      var0.a(a66.PLAY, 4, 21, new PlayerPackets$22());
      var0.a(a66.PLAY, 3, 22);
      var0.a(a66.PLAY, 9, 23, new a9R());
      var0.a(a66.PLAY, 27, 24);
      var0.a(a66.PLAY, 22, 25);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[1]);
      }

   }
}
