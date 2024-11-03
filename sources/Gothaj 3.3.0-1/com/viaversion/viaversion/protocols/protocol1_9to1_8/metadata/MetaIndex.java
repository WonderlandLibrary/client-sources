package com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;

public enum MetaIndex {
   ENTITY_STATUS(EntityTypes1_10.EntityType.ENTITY, 0, MetaType1_8.Byte, MetaType1_9.Byte),
   ENTITY_AIR(EntityTypes1_10.EntityType.ENTITY, 1, MetaType1_8.Short, MetaType1_9.VarInt),
   ENTITY_NAMETAG(EntityTypes1_10.EntityType.ENTITY, 2, MetaType1_8.String, MetaType1_9.String),
   ENTITY_ALWAYS_SHOW_NAMETAG(EntityTypes1_10.EntityType.ENTITY, 3, MetaType1_8.Byte, MetaType1_9.Boolean),
   ENTITY_SILENT(EntityTypes1_10.EntityType.ENTITY, 4, MetaType1_8.Byte, MetaType1_9.Boolean),
   LIVINGENTITY_HEALTH(EntityTypes1_10.EntityType.ENTITY_LIVING, 6, MetaType1_8.Float, MetaType1_9.Float),
   LIVINGENTITY_POTION_EFFECT_COLOR(EntityTypes1_10.EntityType.ENTITY_LIVING, 7, MetaType1_8.Int, MetaType1_9.VarInt),
   LIVINGENTITY_IS_POTION_AMBIENT(EntityTypes1_10.EntityType.ENTITY_LIVING, 8, MetaType1_8.Byte, MetaType1_9.Boolean),
   LIVINGENTITY_NUMBER_OF_ARROWS_IN(EntityTypes1_10.EntityType.ENTITY_LIVING, 9, MetaType1_8.Byte, MetaType1_9.VarInt),
   LIVINGENTITY_NO_AI(EntityTypes1_10.EntityType.ENTITY_LIVING, 15, MetaType1_8.Byte, 10, MetaType1_9.Byte),
   AGEABLE_AGE(EntityTypes1_10.EntityType.ENTITY_AGEABLE, 12, MetaType1_8.Byte, 11, MetaType1_9.Boolean),
   STAND_INFO(EntityTypes1_10.EntityType.ARMOR_STAND, 10, MetaType1_8.Byte, MetaType1_9.Byte),
   STAND_HEAD_POS(EntityTypes1_10.EntityType.ARMOR_STAND, 11, MetaType1_8.Rotation, MetaType1_9.Vector3F),
   STAND_BODY_POS(EntityTypes1_10.EntityType.ARMOR_STAND, 12, MetaType1_8.Rotation, MetaType1_9.Vector3F),
   STAND_LA_POS(EntityTypes1_10.EntityType.ARMOR_STAND, 13, MetaType1_8.Rotation, MetaType1_9.Vector3F),
   STAND_RA_POS(EntityTypes1_10.EntityType.ARMOR_STAND, 14, MetaType1_8.Rotation, MetaType1_9.Vector3F),
   STAND_LL_POS(EntityTypes1_10.EntityType.ARMOR_STAND, 15, MetaType1_8.Rotation, MetaType1_9.Vector3F),
   STAND_RL_POS(EntityTypes1_10.EntityType.ARMOR_STAND, 16, MetaType1_8.Rotation, MetaType1_9.Vector3F),
   PLAYER_SKIN_FLAGS(EntityTypes1_10.EntityType.ENTITY_HUMAN, 10, MetaType1_8.Byte, 12, MetaType1_9.Byte),
   PLAYER_HUMAN_BYTE(EntityTypes1_10.EntityType.ENTITY_HUMAN, 16, MetaType1_8.Byte, null),
   PLAYER_ADDITIONAL_HEARTS(EntityTypes1_10.EntityType.ENTITY_HUMAN, 17, MetaType1_8.Float, 10, MetaType1_9.Float),
   PLAYER_SCORE(EntityTypes1_10.EntityType.ENTITY_HUMAN, 18, MetaType1_8.Int, 11, MetaType1_9.VarInt),
   PLAYER_HAND(EntityTypes1_10.EntityType.ENTITY_HUMAN, -1, MetaType1_8.NonExistent, 5, MetaType1_9.Byte),
   SOMETHING_ANTICHEAT_PLUGINS_FOR_SOME_REASON_USE(EntityTypes1_10.EntityType.ENTITY_HUMAN, 11, MetaType1_8.Byte, null),
   HORSE_INFO(EntityTypes1_10.EntityType.HORSE, 16, MetaType1_8.Int, 12, MetaType1_9.Byte),
   HORSE_TYPE(EntityTypes1_10.EntityType.HORSE, 19, MetaType1_8.Byte, 13, MetaType1_9.VarInt),
   HORSE_SUBTYPE(EntityTypes1_10.EntityType.HORSE, 20, MetaType1_8.Int, 14, MetaType1_9.VarInt),
   HORSE_OWNER(EntityTypes1_10.EntityType.HORSE, 21, MetaType1_8.String, 15, MetaType1_9.OptUUID),
   HORSE_ARMOR(EntityTypes1_10.EntityType.HORSE, 22, MetaType1_8.Int, 16, MetaType1_9.VarInt),
   BAT_ISHANGING(EntityTypes1_10.EntityType.BAT, 16, MetaType1_8.Byte, 11, MetaType1_9.Byte),
   TAMING_INFO(EntityTypes1_10.EntityType.ENTITY_TAMEABLE_ANIMAL, 16, MetaType1_8.Byte, 12, MetaType1_9.Byte),
   TAMING_OWNER(EntityTypes1_10.EntityType.ENTITY_TAMEABLE_ANIMAL, 17, MetaType1_8.String, 13, MetaType1_9.OptUUID),
   OCELOT_TYPE(EntityTypes1_10.EntityType.OCELOT, 18, MetaType1_8.Byte, 14, MetaType1_9.VarInt),
   WOLF_HEALTH(EntityTypes1_10.EntityType.WOLF, 18, MetaType1_8.Float, 14, MetaType1_9.Float),
   WOLF_BEGGING(EntityTypes1_10.EntityType.WOLF, 19, MetaType1_8.Byte, 15, MetaType1_9.Boolean),
   WOLF_COLLAR(EntityTypes1_10.EntityType.WOLF, 20, MetaType1_8.Byte, 16, MetaType1_9.VarInt),
   PIG_SADDLE(EntityTypes1_10.EntityType.PIG, 16, MetaType1_8.Byte, 12, MetaType1_9.Boolean),
   RABBIT_TYPE(EntityTypes1_10.EntityType.RABBIT, 18, MetaType1_8.Byte, 12, MetaType1_9.VarInt),
   SHEEP_COLOR(EntityTypes1_10.EntityType.SHEEP, 16, MetaType1_8.Byte, 12, MetaType1_9.Byte),
   VILLAGER_PROFESSION(EntityTypes1_10.EntityType.VILLAGER, 16, MetaType1_8.Int, 12, MetaType1_9.VarInt),
   ENDERMAN_BLOCKSTATE(EntityTypes1_10.EntityType.ENDERMAN, 16, MetaType1_8.Short, 11, MetaType1_9.BlockID),
   ENDERMAN_BLOCKDATA(EntityTypes1_10.EntityType.ENDERMAN, 17, MetaType1_8.Byte, null),
   ENDERMAN_ISSCREAMING(EntityTypes1_10.EntityType.ENDERMAN, 18, MetaType1_8.Byte, 12, MetaType1_9.Boolean),
   ZOMBIE_ISCHILD(EntityTypes1_10.EntityType.ZOMBIE, 12, MetaType1_8.Byte, 11, MetaType1_9.Boolean),
   ZOMBIE_ISVILLAGER(EntityTypes1_10.EntityType.ZOMBIE, 13, MetaType1_8.Byte, 12, MetaType1_9.VarInt),
   ZOMBIE_ISCONVERTING(EntityTypes1_10.EntityType.ZOMBIE, 14, MetaType1_8.Byte, 13, MetaType1_9.Boolean),
   BLAZE_ONFIRE(EntityTypes1_10.EntityType.BLAZE, 16, MetaType1_8.Byte, 11, MetaType1_9.Byte),
   SPIDER_CIMBING(EntityTypes1_10.EntityType.SPIDER, 16, MetaType1_8.Byte, 11, MetaType1_9.Byte),
   CREEPER_FUSE(EntityTypes1_10.EntityType.CREEPER, 16, MetaType1_8.Byte, 11, MetaType1_9.VarInt),
   CREEPER_ISPOWERED(EntityTypes1_10.EntityType.CREEPER, 17, MetaType1_8.Byte, 12, MetaType1_9.Boolean),
   CREEPER_ISIGNITED(EntityTypes1_10.EntityType.CREEPER, 18, MetaType1_8.Byte, 13, MetaType1_9.Boolean),
   GHAST_ISATTACKING(EntityTypes1_10.EntityType.GHAST, 16, MetaType1_8.Byte, 11, MetaType1_9.Boolean),
   SLIME_SIZE(EntityTypes1_10.EntityType.SLIME, 16, MetaType1_8.Byte, 11, MetaType1_9.VarInt),
   SKELETON_TYPE(EntityTypes1_10.EntityType.SKELETON, 13, MetaType1_8.Byte, 11, MetaType1_9.VarInt),
   WITCH_AGGRO(EntityTypes1_10.EntityType.WITCH, 21, MetaType1_8.Byte, 11, MetaType1_9.Boolean),
   IRON_PLAYERMADE(EntityTypes1_10.EntityType.IRON_GOLEM, 16, MetaType1_8.Byte, 11, MetaType1_9.Byte),
   WITHER_TARGET1(EntityTypes1_10.EntityType.WITHER, 17, MetaType1_8.Int, 11, MetaType1_9.VarInt),
   WITHER_TARGET2(EntityTypes1_10.EntityType.WITHER, 18, MetaType1_8.Int, 12, MetaType1_9.VarInt),
   WITHER_TARGET3(EntityTypes1_10.EntityType.WITHER, 19, MetaType1_8.Int, 13, MetaType1_9.VarInt),
   WITHER_INVULN_TIME(EntityTypes1_10.EntityType.WITHER, 20, MetaType1_8.Int, 14, MetaType1_9.VarInt),
   WITHER_PROPERTIES(EntityTypes1_10.EntityType.WITHER, 10, MetaType1_8.Byte, null),
   WITHER_UNKNOWN(EntityTypes1_10.EntityType.WITHER, 11, MetaType1_8.NonExistent, null),
   WITHERSKULL_INVULN(EntityTypes1_10.EntityType.WITHER_SKULL, 10, MetaType1_8.Byte, 5, MetaType1_9.Boolean),
   GUARDIAN_INFO(EntityTypes1_10.EntityType.GUARDIAN, 16, MetaType1_8.Int, 11, MetaType1_9.Byte),
   GUARDIAN_TARGET(EntityTypes1_10.EntityType.GUARDIAN, 17, MetaType1_8.Int, 12, MetaType1_9.VarInt),
   BOAT_SINCEHIT(EntityTypes1_10.EntityType.BOAT, 17, MetaType1_8.Int, 5, MetaType1_9.VarInt),
   BOAT_FORWARDDIR(EntityTypes1_10.EntityType.BOAT, 18, MetaType1_8.Int, 6, MetaType1_9.VarInt),
   BOAT_DMGTAKEN(EntityTypes1_10.EntityType.BOAT, 19, MetaType1_8.Float, 7, MetaType1_9.Float),
   MINECART_SHAKINGPOWER(EntityTypes1_10.EntityType.MINECART_ABSTRACT, 17, MetaType1_8.Int, 5, MetaType1_9.VarInt),
   MINECART_SHAKINGDIRECTION(EntityTypes1_10.EntityType.MINECART_ABSTRACT, 18, MetaType1_8.Int, 6, MetaType1_9.VarInt),
   MINECART_DAMAGETAKEN(EntityTypes1_10.EntityType.MINECART_ABSTRACT, 19, MetaType1_8.Float, 7, MetaType1_9.Float),
   MINECART_BLOCK(EntityTypes1_10.EntityType.MINECART_ABSTRACT, 20, MetaType1_8.Int, 8, MetaType1_9.VarInt),
   MINECART_BLOCK_Y(EntityTypes1_10.EntityType.MINECART_ABSTRACT, 21, MetaType1_8.Int, 9, MetaType1_9.VarInt),
   MINECART_SHOWBLOCK(EntityTypes1_10.EntityType.MINECART_ABSTRACT, 22, MetaType1_8.Byte, 10, MetaType1_9.Boolean),
   MINECART_COMMANDBLOCK_COMMAND(EntityTypes1_10.EntityType.MINECART_ABSTRACT, 23, MetaType1_8.String, 11, MetaType1_9.String),
   MINECART_COMMANDBLOCK_OUTPUT(EntityTypes1_10.EntityType.MINECART_ABSTRACT, 24, MetaType1_8.String, 12, MetaType1_9.Chat),
   FURNACECART_ISPOWERED(EntityTypes1_10.EntityType.MINECART_ABSTRACT, 16, MetaType1_8.Byte, 11, MetaType1_9.Boolean),
   ITEM_ITEM(EntityTypes1_10.EntityType.DROPPED_ITEM, 10, MetaType1_8.Slot, 5, MetaType1_9.Slot),
   ARROW_ISCRIT(EntityTypes1_10.EntityType.ARROW, 16, MetaType1_8.Byte, 5, MetaType1_9.Byte),
   FIREWORK_INFO(EntityTypes1_10.EntityType.FIREWORK, 8, MetaType1_8.Slot, 5, MetaType1_9.Slot),
   ITEMFRAME_ITEM(EntityTypes1_10.EntityType.ITEM_FRAME, 8, MetaType1_8.Slot, 5, MetaType1_9.Slot),
   ITEMFRAME_ROTATION(EntityTypes1_10.EntityType.ITEM_FRAME, 9, MetaType1_8.Byte, 6, MetaType1_9.VarInt),
   ENDERCRYSTAL_HEALTH(EntityTypes1_10.EntityType.ENDER_CRYSTAL, 8, MetaType1_8.Int, null),
   ENDERDRAGON_UNKNOWN(EntityTypes1_10.EntityType.ENDER_DRAGON, 5, MetaType1_8.Byte, null),
   ENDERDRAGON_NAME(EntityTypes1_10.EntityType.ENDER_DRAGON, 10, MetaType1_8.String, null),
   ENDERDRAGON_FLAG(EntityTypes1_10.EntityType.ENDER_DRAGON, 15, MetaType1_8.Byte, null),
   ENDERDRAGON_PHASE(EntityTypes1_10.EntityType.ENDER_DRAGON, 11, MetaType1_8.Byte, MetaType1_9.VarInt);

   private static final HashMap<Pair<EntityTypes1_10.EntityType, Integer>, MetaIndex> metadataRewrites = new HashMap<>();
   private final EntityTypes1_10.EntityType clazz;
   private final int newIndex;
   private final MetaType1_9 newType;
   private final MetaType1_8 oldType;
   private final int index;

   private MetaIndex(EntityTypes1_10.EntityType type, int index, MetaType1_8 oldType, @Nullable MetaType1_9 newType) {
      this.clazz = type;
      this.index = index;
      this.newIndex = index;
      this.oldType = oldType;
      this.newType = newType;
   }

   private MetaIndex(EntityTypes1_10.EntityType type, int index, MetaType1_8 oldType, int newIndex, @Nullable MetaType1_9 newType) {
      this.clazz = type;
      this.index = index;
      this.oldType = oldType;
      this.newIndex = newIndex;
      this.newType = newType;
   }

   public EntityTypes1_10.EntityType getClazz() {
      return this.clazz;
   }

   public int getNewIndex() {
      return this.newIndex;
   }

   @Nullable
   public MetaType1_9 getNewType() {
      return this.newType;
   }

   public MetaType1_8 getOldType() {
      return this.oldType;
   }

   public int getIndex() {
      return this.index;
   }

   private static Optional<MetaIndex> getIndex(EntityType type, int index) {
      Pair pair = new Pair<>(type, index);
      return Optional.ofNullable(metadataRewrites.get(pair));
   }

   public static MetaIndex searchIndex(EntityType type, int index) {
      EntityType currentType = type;

      do {
         Optional<MetaIndex> optMeta = getIndex(currentType, index);
         if (optMeta.isPresent()) {
            return optMeta.get();
         }

         currentType = currentType.getParent();
      } while (currentType != null);

      return null;
   }

   static {
      for (MetaIndex index : values()) {
         metadataRewrites.put(new Pair<>(index.clazz, index.index), index);
      }
   }
}
