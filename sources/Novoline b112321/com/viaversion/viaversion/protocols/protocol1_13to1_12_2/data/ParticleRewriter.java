package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter$1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter$2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter$3;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter$NewParticle;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter$ParticleDataHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.Gh;
import net.amt;

public class ParticleRewriter {
   private static final List particles = new ArrayList();

   public static Gh a(int var0, Integer[] var1) {
      boolean var2 = amt.b();
      if(var0 >= particles.size()) {
         Via.d().getLogger().severe("Failed to transform particles with id " + var0 + " and data " + Arrays.toString(var1));
         return null;
      } else {
         ParticleRewriter$NewParticle var3 = (ParticleRewriter$NewParticle)particles.get(var0);
         return var3.a(new Gh(var3.getId()), var1);
      }
   }

   private static void add(int var0) {
      particles.add(new ParticleRewriter$NewParticle(var0, (ParticleRewriter$ParticleDataHandler)null));
   }

   private static void add(int var0, ParticleRewriter$ParticleDataHandler var1) {
      particles.add(new ParticleRewriter$NewParticle(var0, var1));
   }

   private static ParticleRewriter$ParticleDataHandler reddustHandler() {
      return new ParticleRewriter$1();
   }

   private static boolean randomBool() {
      return ThreadLocalRandom.current().nextBoolean();
   }

   private static ParticleRewriter$ParticleDataHandler iconcrackHandler() {
      return new ParticleRewriter$2();
   }

   private static ParticleRewriter$ParticleDataHandler blockHandler() {
      return new ParticleRewriter$3();
   }

   static boolean access$000() {
      return randomBool();
   }

   static {
      add(34);
      add(19);
      add(18);
      add(21);
      add(4);
      add(43);
      add(22);
      add(42);
      add(42);
      add(6);
      add(14);
      add(37);
      add(30);
      add(12);
      add(26);
      add(17);
      add(0);
      add(44);
      add(10);
      add(9);
      add(1);
      add(24);
      add(32);
      add(33);
      add(35);
      add(15);
      add(23);
      add(31);
      add(-1);
      add(5);
      add(11, reddustHandler());
      add(29);
      add(34);
      add(28);
      add(25);
      add(2);
      add(27, iconcrackHandler());
      add(3, blockHandler());
      add(3, blockHandler());
      add(36);
      add(-1);
      add(13);
      add(8);
      add(16);
      add(7);
      add(40);
      add(20, blockHandler());
      add(41);
      add(38);
   }
}
