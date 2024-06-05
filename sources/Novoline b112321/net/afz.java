package net;

import net.a66;
import net.aD0;
import net.aD1;
import net.aD3;
import net.aD6;
import net.aD8;
import net.aDB;
import net.aDE;
import net.aDF;
import net.aDG;
import net.aDI;
import net.aDL;
import net.aDN;
import net.aDR;
import net.aDS;
import net.aDT;
import net.aDU;
import net.aDX;
import net.aDe;
import net.aDh;
import net.aDl;
import net.aDm;
import net.aDp;
import net.aDq;
import net.aDr;
import net.aDs;
import net.aDu;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets$19;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets$21;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets$23;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets$24;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets$28;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets$8;
import viaversion.viaversion.api.protocol.Protocol;

public class afz {
   private static int b;

   public static void a(Protocol var0) {
      var0.b(a66.PLAY, 1, 1, new aDE());
      b();
      var0.b(a66.PLAY, 2, 2, new aDr());
      var0.b(a66.PLAY, 5, 5, new aDI());
      var0.b(a66.PLAY, 6, 6, new aDG());
      var0.b(a66.PLAY, 7, 7, new aDh());
      var0.b(a66.PLAY, 8, 8, new aDT());
      var0.b(a66.PLAY, 31, 31, new aD0());
      var0.b(a66.PLAY, 43, 43, new PlayerPackets$8());
      var0.b(a66.PLAY, 54, 54, new aDR());
      var0.b(a66.PLAY, 56, 56, new aDl());
      var0.b(a66.PLAY, 57, 57, new aD8());
      var0.b(a66.PLAY, 63, 63, new aDB());
      var0.b(a66.PLAY, 67, -1, new aDm());
      var0.b(a66.PLAY, 69, -1, new aDu());
      var0.b(a66.PLAY, 71, -1, new aDS());
      var0.b(a66.PLAY, 72, -1, new aDF());
      var0.a(a66.PLAY, 1, 1, new aD3());
      var0.a(a66.PLAY, 2, 2, new aDs());
      var0.a(a66.PLAY, 3, 3, new PlayerPackets$19());
      var0.a(a66.PLAY, 4, 4, new aDp());
      var0.a(a66.PLAY, 5, 5, new PlayerPackets$21());
      var0.a(a66.PLAY, 6, 6, new aDL());
      var0.a(a66.PLAY, 7, 7, new PlayerPackets$23());
      var0.a(a66.PLAY, 8, 8, new PlayerPackets$24());
      var0.a(a66.PLAY, 10, 10, new aDN());
      var0.a(a66.PLAY, 11, 11, new aDU());
      var0.a(a66.PLAY, 12, 12, new aD1());
      var0.a(a66.PLAY, 18, 18, new PlayerPackets$28());
      var0.a(a66.PLAY, 19, 19, new aDq());
      var0.a(a66.PLAY, 20, 20, new aD6());
      var0.a(a66.PLAY, 21, 21, new aDe());
      var0.a(a66.PLAY, 23, 23, new aDX());
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 5;
   }

   static {
      if(b() != 0) {
         b(110);
      }

   }
}
