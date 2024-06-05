package com.viaversion.viaversion.protocols.protocol1_16to1_15_2;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2$1;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2$3;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2$6;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import java.util.UUID;
import net.C4;
import net.Ch;
import net.Mo;
import net.a66;
import net.aTf;
import net.aVF;
import net.aVS;
import net.aVm;
import net.a_h;
import net.adT;
import net.ahW;
import net.ayx;
import net.bgR;
import net.c4;
import net.cA;
import net.ch;
import net.g1;
import net.h0;
import net.kw;
import net.sM;
import net.y7;

public class Protocol1_16To1_15_2 extends ayx {
   private static final UUID ZERO_UUID = new UUID(0L, 0L);
   public static final MappingData MAPPINGS = new MappingData();
   private g1 j;
   private static PacketRemapper[] l;

   public Protocol1_16To1_15_2() {
      super(Mo.class, C4.class, ahW.class, sM.class);
   }

   protected void registerPackets() {
      aTf var2 = new aTf(this);
      EntityPackets.register(this);
      a_h.a(this);
      adT.a((Protocol1_16To1_15_2)this);
      var2.getClass();
      this.j = new g1(this, var2::b);
      this.j.a((y7)Mo.TAGS);
      c();
      var2.getClass();
      (new StatisticsRewriter(this, var2::b)).a((y7)Mo.STATISTICS);
      this.b(a66.LOGIN, 2, 2, new Protocol1_16To1_15_2$1(this));
      this.b(a66.STATUS, 0, 0, new aVm(this));
      kw var3 = new kw(this);
      this.a(Mo.CHAT_MESSAGE, new Protocol1_16To1_15_2$3(this, var3));
      var3.b((y7)Mo.BOSSBAR);
      var3.d(Mo.TITLE);
      var3.c(Mo.COMBAT_EVENT);
      SoundRewriter var4 = new SoundRewriter(this);
      var4.a((y7)Mo.SOUND);
      var4.a((y7)Mo.ENTITY_SOUND);
      this.a(sM.INTERACT_ENTITY, new aVS(this));
      if(Via.c().isIgnoreLong1_16ChannelNames()) {
         this.a(sM.PLUGIN_MESSAGE, new aVF(this));
      }

      this.a(sM.PLAYER_ABILITIES, new Protocol1_16To1_15_2$6(this));
      this.a((h0)sM.GENERATE_JIGSAW);
      this.a((h0)sM.UPDATE_JIGSAW_BLOCK);
      if(PacketRemapper.b() == null) {
         a(new PacketRemapper[1]);
      }

   }

   protected void onMappingDataLoaded() {
      c();
      int[] var2 = new int[47];
      int var3 = 0;
      var2[var3++] = 140;
      var2[var3++] = 179;
      var2[var3++] = 264;
      int var4 = 153;
      if(var4 <= 158) {
         var2[var3++] = var4++;
      }

      var4 = 163;
      if(var4 <= 168) {
         var2[var3++] = var4++;
      }

      var4 = 408;
      if(var4 <= 439) {
         var2[var3++] = var4++;
      }

      this.j.a(Ch.BLOCK, "minecraft:wall_post_override", var2);
      this.j.a(Ch.BLOCK, "minecraft:beacon_base_blocks", new int[]{133, 134, 148, 265});
      this.j.a(Ch.BLOCK, "minecraft:climbable", new int[]{160, 241, 658});
      this.j.a(Ch.BLOCK, "minecraft:fire", new int[]{142});
      this.j.a(Ch.BLOCK, "minecraft:campfires", new int[]{679});
      this.j.a(Ch.BLOCK, "minecraft:fence_gates", new int[]{242, 467, 468, 469, 470, 471});
      this.j.a(Ch.BLOCK, "minecraft:unstable_bottom_center", new int[]{242, 467, 468, 469, 470, 471});
      this.j.a(Ch.BLOCK, "minecraft:wooden_trapdoors", new int[]{193, 194, 195, 196, 197, 198});
      this.j.a(Ch.ITEM, "minecraft:wooden_trapdoors", new int[]{215, 216, 217, 218, 219, 220});
      this.j.a(Ch.ITEM, "minecraft:beacon_payment_items", new int[]{529, 530, 531, 760});
      this.j.a(Ch.ENTITY, "minecraft:impact_projectiles", new int[]{2, 72, 71, 37, 69, 79, 83, 15, 93});
      this.j.a(Ch.BLOCK, "minecraft:guarded_by_piglins");
      this.j.a(Ch.BLOCK, "minecraft:soul_speed_blocks");
      this.j.a(Ch.BLOCK, "minecraft:soul_fire_base_blocks");
      this.j.a(Ch.BLOCK, "minecraft:non_flammable_wood");
      this.j.a(Ch.ITEM, "minecraft:non_flammable_wood");
      this.j.a(Ch.BLOCK, new String[]{"minecraft:bamboo_plantable_on", "minecraft:beds", "minecraft:bee_growables", "minecraft:beehives", "minecraft:coral_plants", "minecraft:crops", "minecraft:dragon_immune", "minecraft:flowers", "minecraft:portals", "minecraft:shulker_boxes", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors", "minecraft:underwater_bonemeals", "minecraft:wither_immune", "minecraft:wooden_fences", "minecraft:wooden_trapdoors"});
      this.j.a(Ch.ENTITY, new String[]{"minecraft:arrows", "minecraft:beehive_inhabitors", "minecraft:raiders", "minecraft:skeletons"});
      this.j.a(Ch.ITEM, new String[]{"minecraft:beds", "minecraft:coals", "minecraft:fences", "minecraft:flowers", "minecraft:lectern_books", "minecraft:music_discs", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors", "minecraft:walls", "minecraft:wooden_fences"});
   }

   public void a(bgR var1) {
      var1.a((cA)(new c4(var1)));
      var1.a((cA)(new ch(var1)));
   }

   public MappingData getMappingData() {
      return MAPPINGS;
   }

   static UUID access$000() {
      return ZERO_UUID;
   }

   static {
      a((PacketRemapper[])null);
   }

   public static void a(PacketRemapper[] var0) {
      l = var0;
   }

   public static PacketRemapper[] c() {
      return l;
   }
}
