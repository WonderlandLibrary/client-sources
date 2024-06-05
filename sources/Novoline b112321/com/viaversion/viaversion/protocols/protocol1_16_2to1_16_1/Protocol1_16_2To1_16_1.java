package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1$2;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import net.C4;
import net.Ch;
import net.UN;
import net.UV;
import net.aTH;
import net.aVV;
import net.aiq;
import net.ayx;
import net.bgR;
import net.c1;
import net.cA;
import net.dJ;
import net.g1;
import net.lH;
import net.lx;
import net.sM;
import net.y7;

public class Protocol1_16_2To1_16_1 extends ayx {
   public static final aiq j = new aiq();
   private g1 i;
   private static int[] k;

   public Protocol1_16_2To1_16_1() {
      super(C4.class, UN.class, sM.class, lx.class);
   }

   protected void registerPackets() {
      aTH var2 = new aTH(this);
      a();
      dJ.a((Protocol1_16_2To1_16_1)this);
      lH.a(this);
      UV.a(this);
      var2.getClass();
      this.i = new g1(this, var2::b);
      this.i.a((y7)C4.TAGS);
      var2.getClass();
      (new StatisticsRewriter(this, var2::b)).a((y7)C4.STATISTICS);
      SoundRewriter var3 = new SoundRewriter(this);
      var3.a((y7)C4.SOUND);
      var3.a((y7)C4.ENTITY_SOUND);
      this.a(lx.RECIPE_BOOK_DATA, new aVV(this));
      this.a(lx.SEEN_RECIPE, sM.RECIPE_BOOK_DATA, new Protocol1_16_2To1_16_1$2(this));
      if(PacketRemapper.b() == null) {
         b(new int[5]);
      }

   }

   protected void onMappingDataLoaded() {
      a();
      this.i.a(Ch.ITEM, "minecraft:stone_crafting_materials", new int[]{14, 962});
      this.i.a(Ch.BLOCK, "minecraft:mushroom_grow_block");
      this.i.a(Ch.ITEM, new String[]{"minecraft:soul_fire_base_blocks", "minecraft:furnace_materials", "minecraft:crimson_stems", "minecraft:gold_ores", "minecraft:piglin_loved", "minecraft:piglin_repellents", "minecraft:creeper_drop_music_discs", "minecraft:logs_that_burn", "minecraft:stone_tool_materials", "minecraft:warped_stems"});
      this.i.a(Ch.BLOCK, new String[]{"minecraft:infiniburn_nether", "minecraft:crimson_stems", "minecraft:wither_summon_base_blocks", "minecraft:infiniburn_overworld", "minecraft:piglin_repellents", "minecraft:hoglin_repellents", "minecraft:prevent_mob_spawning_inside", "minecraft:wart_blocks", "minecraft:stone_pressure_plates", "minecraft:nylium", "minecraft:gold_ores", "minecraft:pressure_plates", "minecraft:logs_that_burn", "minecraft:strider_warm_blocks", "minecraft:warped_stems", "minecraft:infiniburn_end", "minecraft:base_stone_nether", "minecraft:base_stone_overworld"});
   }

   public void a(bgR var1) {
      var1.a((cA)(new c1(var1)));
   }

   public aiq b() {
      return j;
   }

   static {
      b(new int[1]);
   }

   public static void b(int[] var0) {
      k = var0;
   }

   public static int[] a() {
      return k;
   }
}
