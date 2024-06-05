package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.github.steveice10.opennbt.tag.builtin.ByteTag;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.FloatTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.LongTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets$1;
import net.C4;
import net.EN;
import net.Mo;
import net.aSG;
import net.aTf;
import net.aVp;
import net.aVr;
import net.aVv;
import net.aVw;
import net.adT;
import net.axs;
import net.sM;

public class EntityPackets {
   private static final EN c = EntityPackets::lambda$static$0;
   public static final CompoundTag b = new CompoundTag("");
   private static final String[] WORLD_NAMES = new String[]{"minecraft:overworld", "minecraft:the_nether", "minecraft:the_end"};

   private static CompoundTag a() {
      CompoundTag var0 = new CompoundTag("");
      var0.put(new StringTag("name", "minecraft:overworld"));
      var0.put(new ByteTag("has_ceiling", (byte)0));
      a(var0);
      return var0;
   }

   private static CompoundTag e() {
      CompoundTag var0 = new CompoundTag("");
      var0.put(new StringTag("name", "minecraft:overworld_caves"));
      var0.put(new ByteTag("has_ceiling", (byte)1));
      a(var0);
      return var0;
   }

   private static void a(CompoundTag var0) {
      var0.put(new ByteTag("piglin_safe", (byte)0));
      var0.put(new ByteTag("natural", (byte)1));
      var0.put(new FloatTag("ambient_light", 0.0F));
      var0.put(new StringTag("infiniburn", "minecraft:infiniburn_overworld"));
      var0.put(new ByteTag("respawn_anchor_works", (byte)0));
      var0.put(new ByteTag("has_skylight", (byte)1));
      var0.put(new ByteTag("bed_works", (byte)1));
      var0.put(new ByteTag("has_raids", (byte)1));
      var0.put(new IntTag("logical_height", 256));
      var0.put(new ByteTag("shrunk", (byte)0));
      var0.put(new ByteTag("ultrawarm", (byte)0));
   }

   private static CompoundTag c() {
      CompoundTag var0 = new CompoundTag("");
      var0.put(new ByteTag("piglin_safe", (byte)1));
      var0.put(new ByteTag("natural", (byte)0));
      var0.put(new FloatTag("ambient_light", 0.1F));
      var0.put(new StringTag("infiniburn", "minecraft:infiniburn_nether"));
      var0.put(new ByteTag("respawn_anchor_works", (byte)1));
      var0.put(new ByteTag("has_skylight", (byte)0));
      var0.put(new ByteTag("bed_works", (byte)0));
      var0.put(new LongTag("fixed_time", 18000L));
      var0.put(new ByteTag("has_raids", (byte)0));
      var0.put(new StringTag("name", "minecraft:the_nether"));
      var0.put(new IntTag("logical_height", 128));
      var0.put(new ByteTag("shrunk", (byte)1));
      var0.put(new ByteTag("ultrawarm", (byte)1));
      var0.put(new ByteTag("has_ceiling", (byte)1));
      return var0;
   }

   private static CompoundTag d() {
      CompoundTag var0 = new CompoundTag("");
      var0.put(new ByteTag("piglin_safe", (byte)0));
      var0.put(new ByteTag("natural", (byte)0));
      var0.put(new FloatTag("ambient_light", 0.0F));
      var0.put(new StringTag("infiniburn", "minecraft:infiniburn_end"));
      var0.put(new ByteTag("respawn_anchor_works", (byte)0));
      var0.put(new ByteTag("has_skylight", (byte)0));
      var0.put(new ByteTag("bed_works", (byte)0));
      var0.put(new LongTag("fixed_time", 6000L));
      var0.put(new ByteTag("has_raids", (byte)1));
      var0.put(new StringTag("name", "minecraft:the_end"));
      var0.put(new IntTag("logical_height", 256));
      var0.put(new ByteTag("shrunk", (byte)0));
      var0.put(new ByteTag("ultrawarm", (byte)0));
      var0.put(new ByteTag("has_ceiling", (byte)0));
      return var0;
   }

   public static void register(Protocol1_16To1_15_2 var0) {
      aTf var2 = (aTf)var0.b(aTf.class);
      var0.a(Mo.SPAWN_GLOBAL_ENTITY, C4.SPAWN_ENTITY, new EntityPackets$1());
      var2.b(Mo.SPAWN_ENTITY, axs.FALLING_BLOCK);
      var2.a(Mo.SPAWN_MOB);
      var2.a(Mo.SPAWN_PLAYER, axs.PLAYER);
      var2.a(Mo.ENTITY_METADATA, aSG.c);
      adT.b();
      var2.b(Mo.DESTROY_ENTITIES);
      var0.a(Mo.RESPAWN, new aVv());
      var0.a(Mo.JOIN_GAME, new aVr());
      var0.a(Mo.ENTITY_PROPERTIES, new aVp(var0));
      var0.a(sM.ANIMATION, new aVw());
      if(PacketRemapper.b() == null) {
         adT.b((PacketRemapper[])(new PacketRemapper[4]));
      }

   }

   private static void lambda$static$0(PacketWrapperImpl var0) throws Exception {
      adT.b();
      int var2 = ((Integer)var0.b(Type.I)).intValue();
      switch(var2) {
      case -1:
         String var3 = "minecraft:the_nether";
      case 0:
         String var4 = "minecraft:overworld";
      case 1:
         String var5 = "minecraft:the_end";
      default:
         Via.d().getLogger().warning("Invalid dimension id: " + var2);
         String var6 = "minecraft:overworld";
         var0.a(Type.STRING, var6);
         var0.a(Type.STRING, var6);
      }
   }

   static EN f() {
      return c;
   }

   static String[] access$100() {
      return WORLD_NAMES;
   }

   static {
      ListTag var7 = new ListTag("dimension", CompoundTag.class);
      var7.add(a());
      var7.add(e());
      var7.add(c());
      var7.add(d());
      b.put(var7);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
