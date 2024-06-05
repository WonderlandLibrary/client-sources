package com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata;

import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.Optional;
import net.aR5;
import net.aTi;
import net.t4;

public enum MetaIndex {
   ENTITY_STATUS(t4.ENTITY, 0, MetaType1_8.Byte, aR5.Byte),
   ENTITY_AIR(t4.ENTITY, 1, MetaType1_8.Short, aR5.VarInt),
   ENTITY_NAMETAG(t4.ENTITY, 2, MetaType1_8.String, aR5.String),
   ENTITY_ALWAYS_SHOW_NAMETAG(t4.ENTITY, 3, MetaType1_8.Byte, aR5.Boolean),
   ENTITY_SILENT(t4.ENTITY, 4, MetaType1_8.Byte, aR5.Boolean),
   LIVINGENTITY_HEALTH(t4.ENTITY_LIVING, 6, MetaType1_8.Float, aR5.Float),
   LIVINGENTITY_POTION_EFFECT_COLOR(t4.ENTITY_LIVING, 7, MetaType1_8.Int, aR5.VarInt),
   LIVINGENTITY_IS_POTION_AMBIENT(t4.ENTITY_LIVING, 8, MetaType1_8.Byte, aR5.Boolean),
   LIVINGENTITY_NUMBER_OF_ARROWS_IN(t4.ENTITY_LIVING, 9, MetaType1_8.Byte, aR5.VarInt),
   LIVINGENTITY_NO_AI(t4.ENTITY_LIVING, 15, MetaType1_8.Byte, 10, aR5.Byte),
   AGEABLE_AGE(t4.ENTITY_AGEABLE, 12, MetaType1_8.Byte, 11, aR5.Boolean),
   STAND_INFO(t4.ARMOR_STAND, 10, MetaType1_8.Byte, aR5.Byte),
   STAND_HEAD_POS(t4.ARMOR_STAND, 11, MetaType1_8.Rotation, aR5.Vector3F),
   STAND_BODY_POS(t4.ARMOR_STAND, 12, MetaType1_8.Rotation, aR5.Vector3F),
   STAND_LA_POS(t4.ARMOR_STAND, 13, MetaType1_8.Rotation, aR5.Vector3F),
   STAND_RA_POS(t4.ARMOR_STAND, 14, MetaType1_8.Rotation, aR5.Vector3F),
   STAND_LL_POS(t4.ARMOR_STAND, 15, MetaType1_8.Rotation, aR5.Vector3F),
   STAND_RL_POS(t4.ARMOR_STAND, 16, MetaType1_8.Rotation, aR5.Vector3F),
   PLAYER_SKIN_FLAGS(t4.ENTITY_HUMAN, 10, MetaType1_8.Byte, 12, aR5.Byte),
   PLAYER_HUMAN_BYTE(t4.ENTITY_HUMAN, 16, MetaType1_8.Byte, aR5.Discontinued),
   PLAYER_ADDITIONAL_HEARTS(t4.ENTITY_HUMAN, 17, MetaType1_8.Float, 10, aR5.Float),
   PLAYER_SCORE(t4.ENTITY_HUMAN, 18, MetaType1_8.Int, 11, aR5.VarInt),
   PLAYER_HAND(t4.ENTITY_HUMAN, -1, MetaType1_8.NonExistent, 5, aR5.Byte),
   SOMETHING_ANTICHEAT_PLUGINS_FOR_SOME_REASON_USE(t4.ENTITY_HUMAN, 11, MetaType1_8.Byte, aR5.Discontinued),
   HORSE_INFO(t4.HORSE, 16, MetaType1_8.Int, 12, aR5.Byte),
   HORSE_TYPE(t4.HORSE, 19, MetaType1_8.Byte, 13, aR5.VarInt),
   HORSE_SUBTYPE(t4.HORSE, 20, MetaType1_8.Int, 14, aR5.VarInt),
   HORSE_OWNER(t4.HORSE, 21, MetaType1_8.String, 15, aR5.OptUUID),
   HORSE_ARMOR(t4.HORSE, 22, MetaType1_8.Int, 16, aR5.VarInt),
   BAT_ISHANGING(t4.BAT, 16, MetaType1_8.Byte, 11, aR5.Byte),
   TAMING_INFO(t4.ENTITY_TAMEABLE_ANIMAL, 16, MetaType1_8.Byte, 12, aR5.Byte),
   TAMING_OWNER(t4.ENTITY_TAMEABLE_ANIMAL, 17, MetaType1_8.String, 13, aR5.OptUUID),
   OCELOT_TYPE(t4.OCELOT, 18, MetaType1_8.Byte, 14, aR5.VarInt),
   WOLF_HEALTH(t4.WOLF, 18, MetaType1_8.Float, 14, aR5.Float),
   WOLF_BEGGING(t4.WOLF, 19, MetaType1_8.Byte, 15, aR5.Boolean),
   WOLF_COLLAR(t4.WOLF, 20, MetaType1_8.Byte, 16, aR5.VarInt),
   PIG_SADDLE(t4.PIG, 16, MetaType1_8.Byte, 12, aR5.Boolean),
   RABBIT_TYPE(t4.RABBIT, 18, MetaType1_8.Byte, 12, aR5.VarInt),
   SHEEP_COLOR(t4.SHEEP, 16, MetaType1_8.Byte, 12, aR5.Byte),
   VILLAGER_PROFESSION(t4.VILLAGER, 16, MetaType1_8.Int, 12, aR5.VarInt),
   ENDERMAN_BLOCKSTATE(t4.ENDERMAN, 16, MetaType1_8.Short, 11, aR5.BlockID),
   ENDERMAN_BLOCKDATA(t4.ENDERMAN, 17, MetaType1_8.Byte, aR5.Discontinued),
   ENDERMAN_ISSCREAMING(t4.ENDERMAN, 18, MetaType1_8.Byte, 12, aR5.Boolean),
   ZOMBIE_ISCHILD(t4.ZOMBIE, 12, MetaType1_8.Byte, 11, aR5.Boolean),
   ZOMBIE_ISVILLAGER(t4.ZOMBIE, 13, MetaType1_8.Byte, 12, aR5.VarInt),
   ZOMBIE_ISCONVERTING(t4.ZOMBIE, 14, MetaType1_8.Byte, 13, aR5.Boolean),
   BLAZE_ONFIRE(t4.BLAZE, 16, MetaType1_8.Byte, 11, aR5.Byte),
   SPIDER_CIMBING(t4.SPIDER, 16, MetaType1_8.Byte, 11, aR5.Byte),
   CREEPER_FUSE(t4.CREEPER, 16, MetaType1_8.Byte, 11, aR5.VarInt),
   CREEPER_ISPOWERED(t4.CREEPER, 17, MetaType1_8.Byte, 12, aR5.Boolean),
   CREEPER_ISIGNITED(t4.CREEPER, 18, MetaType1_8.Byte, 13, aR5.Boolean),
   GHAST_ISATTACKING(t4.GHAST, 16, MetaType1_8.Byte, 11, aR5.Boolean),
   SLIME_SIZE(t4.SLIME, 16, MetaType1_8.Byte, 11, aR5.VarInt),
   SKELETON_TYPE(t4.SKELETON, 13, MetaType1_8.Byte, 11, aR5.VarInt),
   WITCH_AGGRO(t4.WITCH, 21, MetaType1_8.Byte, 11, aR5.Boolean),
   IRON_PLAYERMADE(t4.IRON_GOLEM, 16, MetaType1_8.Byte, 11, aR5.Byte),
   WITHER_TARGET1(t4.WITHER, 17, MetaType1_8.Int, 11, aR5.VarInt),
   WITHER_TARGET2(t4.WITHER, 18, MetaType1_8.Int, 12, aR5.VarInt),
   WITHER_TARGET3(t4.WITHER, 19, MetaType1_8.Int, 13, aR5.VarInt),
   WITHER_INVULN_TIME(t4.WITHER, 20, MetaType1_8.Int, 14, aR5.VarInt),
   WITHER_PROPERTIES(t4.WITHER, 10, MetaType1_8.Byte, aR5.Byte),
   WITHER_UNKNOWN(t4.WITHER, 11, MetaType1_8.Byte, aR5.Discontinued),
   WITHERSKULL_INVULN(t4.WITHER_SKULL, 10, MetaType1_8.Byte, 5, aR5.Boolean),
   GUARDIAN_INFO(t4.GUARDIAN, 16, MetaType1_8.Int, 11, aR5.Byte),
   GUARDIAN_TARGET(t4.GUARDIAN, 17, MetaType1_8.Int, 12, aR5.VarInt),
   BOAT_SINCEHIT(t4.BOAT, 17, MetaType1_8.Int, 5, aR5.VarInt),
   BOAT_FORWARDDIR(t4.BOAT, 18, MetaType1_8.Int, 6, aR5.VarInt),
   BOAT_DMGTAKEN(t4.BOAT, 19, MetaType1_8.Float, 7, aR5.Float),
   MINECART_SHAKINGPOWER(t4.MINECART_ABSTRACT, 17, MetaType1_8.Int, 5, aR5.VarInt),
   MINECART_SHAKINGDIRECTION(t4.MINECART_ABSTRACT, 18, MetaType1_8.Int, 6, aR5.VarInt),
   MINECART_DAMAGETAKEN(t4.MINECART_ABSTRACT, 19, MetaType1_8.Float, 7, aR5.Float),
   MINECART_BLOCK(t4.MINECART_ABSTRACT, 20, MetaType1_8.Int, 8, aR5.VarInt),
   MINECART_BLOCK_Y(t4.MINECART_ABSTRACT, 21, MetaType1_8.Int, 9, aR5.VarInt),
   MINECART_SHOWBLOCK(t4.MINECART_ABSTRACT, 22, MetaType1_8.Byte, 10, aR5.Boolean),
   MINECART_COMMANDBLOCK_COMMAND(t4.MINECART_ABSTRACT, 23, MetaType1_8.String, 11, aR5.String),
   MINECART_COMMANDBLOCK_OUTPUT(t4.MINECART_ABSTRACT, 24, MetaType1_8.String, 12, aR5.Chat),
   FURNACECART_ISPOWERED(t4.MINECART_ABSTRACT, 16, MetaType1_8.Byte, 11, aR5.Boolean),
   ITEM_ITEM(t4.DROPPED_ITEM, 10, MetaType1_8.Slot, 5, aR5.Slot),
   ARROW_ISCRIT(t4.ARROW, 16, MetaType1_8.Byte, 5, aR5.Byte),
   FIREWORK_INFO(t4.FIREWORK, 8, MetaType1_8.Slot, 5, aR5.Slot),
   ITEMFRAME_ITEM(t4.ITEM_FRAME, 8, MetaType1_8.Slot, 5, aR5.Slot),
   ITEMFRAME_ROTATION(t4.ITEM_FRAME, 9, MetaType1_8.Byte, 6, aR5.VarInt),
   ENDERCRYSTAL_HEALTH(t4.ENDER_CRYSTAL, 8, MetaType1_8.Int, aR5.Discontinued),
   ENDERDRAGON_UNKNOWN(t4.ENDER_DRAGON, 5, MetaType1_8.Byte, aR5.Discontinued),
   ENDERDRAGON_NAME(t4.ENDER_DRAGON, 10, MetaType1_8.String, aR5.Discontinued),
   ENDERDRAGON_FLAG(t4.ENDER_DRAGON, 15, MetaType1_8.Byte, aR5.Discontinued),
   ENDERDRAGON_PHASE(t4.ENDER_DRAGON, 11, MetaType1_8.Byte, aR5.VarInt);

   private static final HashMap metadataRewrites = new HashMap();
   private final t4 a;
   private final int newIndex;
   private final aR5 c;
   private final MetaType1_8 oldType;
   private final int index;
   private static final MetaIndex[] $VALUES = new MetaIndex[]{ENTITY_STATUS, ENTITY_AIR, ENTITY_NAMETAG, ENTITY_ALWAYS_SHOW_NAMETAG, ENTITY_SILENT, LIVINGENTITY_HEALTH, LIVINGENTITY_POTION_EFFECT_COLOR, LIVINGENTITY_IS_POTION_AMBIENT, LIVINGENTITY_NUMBER_OF_ARROWS_IN, LIVINGENTITY_NO_AI, AGEABLE_AGE, STAND_INFO, STAND_HEAD_POS, STAND_BODY_POS, STAND_LA_POS, STAND_RA_POS, STAND_LL_POS, STAND_RL_POS, PLAYER_SKIN_FLAGS, PLAYER_HUMAN_BYTE, PLAYER_ADDITIONAL_HEARTS, PLAYER_SCORE, PLAYER_HAND, SOMETHING_ANTICHEAT_PLUGINS_FOR_SOME_REASON_USE, HORSE_INFO, HORSE_TYPE, HORSE_SUBTYPE, HORSE_OWNER, HORSE_ARMOR, BAT_ISHANGING, TAMING_INFO, TAMING_OWNER, OCELOT_TYPE, WOLF_HEALTH, WOLF_BEGGING, WOLF_COLLAR, PIG_SADDLE, RABBIT_TYPE, SHEEP_COLOR, VILLAGER_PROFESSION, ENDERMAN_BLOCKSTATE, ENDERMAN_BLOCKDATA, ENDERMAN_ISSCREAMING, ZOMBIE_ISCHILD, ZOMBIE_ISVILLAGER, ZOMBIE_ISCONVERTING, BLAZE_ONFIRE, SPIDER_CIMBING, CREEPER_FUSE, CREEPER_ISPOWERED, CREEPER_ISIGNITED, GHAST_ISATTACKING, SLIME_SIZE, SKELETON_TYPE, WITCH_AGGRO, IRON_PLAYERMADE, WITHER_TARGET1, WITHER_TARGET2, WITHER_TARGET3, WITHER_INVULN_TIME, WITHER_PROPERTIES, WITHER_UNKNOWN, WITHERSKULL_INVULN, GUARDIAN_INFO, GUARDIAN_TARGET, BOAT_SINCEHIT, BOAT_FORWARDDIR, BOAT_DMGTAKEN, MINECART_SHAKINGPOWER, MINECART_SHAKINGDIRECTION, MINECART_DAMAGETAKEN, MINECART_BLOCK, MINECART_BLOCK_Y, MINECART_SHOWBLOCK, MINECART_COMMANDBLOCK_COMMAND, MINECART_COMMANDBLOCK_OUTPUT, FURNACECART_ISPOWERED, ITEM_ITEM, ARROW_ISCRIT, FIREWORK_INFO, ITEMFRAME_ITEM, ITEMFRAME_ROTATION, ENDERCRYSTAL_HEALTH, ENDERDRAGON_UNKNOWN, ENDERDRAGON_NAME, ENDERDRAGON_FLAG, ENDERDRAGON_PHASE};

   private MetaIndex(t4 var3, int var4, MetaType1_8 var5, aR5 var6) {
      this.a = var3;
      this.index = var4;
      this.newIndex = var4;
      this.oldType = var5;
      this.c = var6;
   }

   private MetaIndex(t4 var3, int var4, MetaType1_8 var5, int var6, aR5 var7) {
      this.a = var3;
      this.index = var4;
      this.oldType = var5;
      this.newIndex = var6;
      this.c = var7;
   }

   public t4 c() {
      return this.a;
   }

   public int getNewIndex() {
      return this.newIndex;
   }

   public aR5 e() {
      return this.c;
   }

   public MetaType1_8 getOldType() {
      return this.oldType;
   }

   public int getIndex() {
      return this.index;
   }

   private static Optional a(ConnectionManager var0, int var1) {
      Pair var2 = new Pair(var0, Integer.valueOf(var1));
      return Optional.ofNullable(metadataRewrites.get(var2));
   }

   public static MetaIndex b(ConnectionManager var0, int var1) {
      PacketRemapper[] var2 = aTi.a();
      Optional var4 = a(var0, var1);
      if(var4.isPresent()) {
         return (MetaIndex)var4.get();
      } else {
         ConnectionManager var3 = var0.b();
         return null;
      }
   }

   static {
      for(MetaIndex var11 : values()) {
         metadataRewrites.put(new Pair(var11.a, Integer.valueOf(var11.index)), var11);
      }

   }
}
