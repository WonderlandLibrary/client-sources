package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.api.Via;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EntityTypes1_13 {
   public static EntityTypes1_13.EntityType getTypeFromId(int typeID, boolean isObject) {
      Optional<EntityTypes1_13.EntityType> type;
      if (isObject) {
         type = EntityTypes1_13.ObjectType.getPCEntity(typeID);
      } else {
         type = EntityTypes1_13.EntityType.findById(typeID);
      }

      if (!type.isPresent()) {
         Via.getPlatform().getLogger().severe("Could not find 1.13 type id " + typeID + " isObject=" + isObject);
         return EntityTypes1_13.EntityType.ENTITY;
      } else {
         return type.get();
      }
   }

   public static enum EntityType implements com.viaversion.viaversion.api.minecraft.entities.EntityType {
      ENTITY(-1),
      AREA_EFFECT_CLOUD(0, ENTITY),
      END_CRYSTAL(16, ENTITY),
      EVOKER_FANGS(20, ENTITY),
      EXPERIENCE_ORB(22, ENTITY),
      EYE_OF_ENDER(23, ENTITY),
      FALLING_BLOCK(24, ENTITY),
      FIREWORK_ROCKET(25, ENTITY),
      ITEM(32, ENTITY),
      LLAMA_SPIT(37, ENTITY),
      TNT(55, ENTITY),
      SHULKER_BULLET(60, ENTITY),
      FISHING_BOBBER(93, ENTITY),
      LIVINGENTITY(-1, ENTITY),
      ARMOR_STAND(1, LIVINGENTITY),
      PLAYER(92, LIVINGENTITY),
      ABSTRACT_INSENTIENT(-1, LIVINGENTITY),
      ENDER_DRAGON(17, ABSTRACT_INSENTIENT),
      ABSTRACT_CREATURE(-1, ABSTRACT_INSENTIENT),
      ABSTRACT_AGEABLE(-1, ABSTRACT_CREATURE),
      VILLAGER(79, ABSTRACT_AGEABLE),
      ABSTRACT_ANIMAL(-1, ABSTRACT_AGEABLE),
      CHICKEN(7, ABSTRACT_ANIMAL),
      COW(9, ABSTRACT_ANIMAL),
      MOOSHROOM(47, COW),
      PIG(51, ABSTRACT_ANIMAL),
      POLAR_BEAR(54, ABSTRACT_ANIMAL),
      RABBIT(56, ABSTRACT_ANIMAL),
      SHEEP(58, ABSTRACT_ANIMAL),
      TURTLE(73, ABSTRACT_ANIMAL),
      ABSTRACT_TAMEABLE_ANIMAL(-1, ABSTRACT_ANIMAL),
      OCELOT(48, ABSTRACT_TAMEABLE_ANIMAL),
      WOLF(86, ABSTRACT_TAMEABLE_ANIMAL),
      ABSTRACT_PARROT(-1, ABSTRACT_TAMEABLE_ANIMAL),
      PARROT(50, ABSTRACT_PARROT),
      ABSTRACT_HORSE(-1, ABSTRACT_ANIMAL),
      CHESTED_HORSE(-1, ABSTRACT_HORSE),
      DONKEY(11, CHESTED_HORSE),
      MULE(46, CHESTED_HORSE),
      LLAMA(36, CHESTED_HORSE),
      HORSE(29, ABSTRACT_HORSE),
      SKELETON_HORSE(63, ABSTRACT_HORSE),
      ZOMBIE_HORSE(88, ABSTRACT_HORSE),
      ABSTRACT_GOLEM(-1, ABSTRACT_CREATURE),
      SNOW_GOLEM(66, ABSTRACT_GOLEM),
      IRON_GOLEM(80, ABSTRACT_GOLEM),
      SHULKER(59, ABSTRACT_GOLEM),
      ABSTRACT_FISHES(-1, ABSTRACT_CREATURE),
      COD(8, ABSTRACT_FISHES),
      PUFFERFISH(52, ABSTRACT_FISHES),
      SALMON(57, ABSTRACT_FISHES),
      TROPICAL_FISH(72, ABSTRACT_FISHES),
      ABSTRACT_MONSTER(-1, ABSTRACT_CREATURE),
      BLAZE(4, ABSTRACT_MONSTER),
      CREEPER(10, ABSTRACT_MONSTER),
      ENDERMITE(19, ABSTRACT_MONSTER),
      ENDERMAN(18, ABSTRACT_MONSTER),
      GIANT(27, ABSTRACT_MONSTER),
      SILVERFISH(61, ABSTRACT_MONSTER),
      VEX(78, ABSTRACT_MONSTER),
      WITCH(82, ABSTRACT_MONSTER),
      WITHER(83, ABSTRACT_MONSTER),
      ABSTRACT_ILLAGER_BASE(-1, ABSTRACT_MONSTER),
      ABSTRACT_EVO_ILLU_ILLAGER(-1, ABSTRACT_ILLAGER_BASE),
      EVOKER(21, ABSTRACT_EVO_ILLU_ILLAGER),
      ILLUSIONER(31, ABSTRACT_EVO_ILLU_ILLAGER),
      VINDICATOR(81, ABSTRACT_ILLAGER_BASE),
      ABSTRACT_SKELETON(-1, ABSTRACT_MONSTER),
      SKELETON(62, ABSTRACT_SKELETON),
      STRAY(71, ABSTRACT_SKELETON),
      WITHER_SKELETON(84, ABSTRACT_SKELETON),
      GUARDIAN(28, ABSTRACT_MONSTER),
      ELDER_GUARDIAN(15, GUARDIAN),
      SPIDER(69, ABSTRACT_MONSTER),
      CAVE_SPIDER(6, SPIDER),
      ZOMBIE(87, ABSTRACT_MONSTER),
      DROWNED(14, ZOMBIE),
      HUSK(30, ZOMBIE),
      ZOMBIE_PIGMAN(53, ZOMBIE),
      ZOMBIE_VILLAGER(89, ZOMBIE),
      ABSTRACT_FLYING(-1, ABSTRACT_INSENTIENT),
      GHAST(26, ABSTRACT_FLYING),
      PHANTOM(90, ABSTRACT_FLYING),
      ABSTRACT_AMBIENT(-1, ABSTRACT_INSENTIENT),
      BAT(3, ABSTRACT_AMBIENT),
      ABSTRACT_WATERMOB(-1, ABSTRACT_INSENTIENT),
      SQUID(70, ABSTRACT_WATERMOB),
      DOLPHIN(12, ABSTRACT_WATERMOB),
      SLIME(64, ABSTRACT_INSENTIENT),
      MAGMA_CUBE(38, SLIME),
      ABSTRACT_HANGING(-1, ENTITY),
      LEASH_KNOT(35, ABSTRACT_HANGING),
      ITEM_FRAME(33, ABSTRACT_HANGING),
      PAINTING(49, ABSTRACT_HANGING),
      ABSTRACT_LIGHTNING(-1, ENTITY),
      LIGHTNING_BOLT(91, ABSTRACT_LIGHTNING),
      ABSTRACT_ARROW(-1, ENTITY),
      ARROW(2, ABSTRACT_ARROW),
      SPECTRAL_ARROW(68, ABSTRACT_ARROW),
      TRIDENT(94, ABSTRACT_ARROW),
      ABSTRACT_FIREBALL(-1, ENTITY),
      DRAGON_FIREBALL(13, ABSTRACT_FIREBALL),
      FIREBALL(34, ABSTRACT_FIREBALL),
      SMALL_FIREBALL(65, ABSTRACT_FIREBALL),
      WITHER_SKULL(85, ABSTRACT_FIREBALL),
      PROJECTILE_ABSTRACT(-1, ENTITY),
      SNOWBALL(67, PROJECTILE_ABSTRACT),
      ENDER_PEARL(75, PROJECTILE_ABSTRACT),
      EGG(74, PROJECTILE_ABSTRACT),
      POTION(77, PROJECTILE_ABSTRACT),
      EXPERIENCE_BOTTLE(76, PROJECTILE_ABSTRACT),
      MINECART_ABSTRACT(-1, ENTITY),
      CHESTED_MINECART_ABSTRACT(-1, MINECART_ABSTRACT),
      CHEST_MINECART(40, CHESTED_MINECART_ABSTRACT),
      HOPPER_MINECART(43, CHESTED_MINECART_ABSTRACT),
      MINECART(39, MINECART_ABSTRACT),
      FURNACE_MINECART(42, MINECART_ABSTRACT),
      COMMAND_BLOCK_MINECART(41, MINECART_ABSTRACT),
      TNT_MINECART(45, MINECART_ABSTRACT),
      SPAWNER_MINECART(44, MINECART_ABSTRACT),
      BOAT(5, ENTITY);

      private static final Map<Integer, EntityTypes1_13.EntityType> TYPES = new HashMap<>();
      private final int id;
      private final EntityTypes1_13.EntityType parent;

      private EntityType(int id) {
         this.id = id;
         this.parent = null;
      }

      private EntityType(int id, EntityTypes1_13.EntityType parent) {
         this.id = id;
         this.parent = parent;
      }

      @Override
      public int getId() {
         return this.id;
      }

      public EntityTypes1_13.EntityType getParent() {
         return this.parent;
      }

      @Override
      public String identifier() {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean isAbstractType() {
         return this.id != -1;
      }

      public static Optional<EntityTypes1_13.EntityType> findById(int id) {
         return id == -1 ? Optional.empty() : Optional.ofNullable(TYPES.get(id));
      }

      static {
         for (EntityTypes1_13.EntityType type : values()) {
            TYPES.put(type.id, type);
         }
      }
   }

   public static enum ObjectType implements com.viaversion.viaversion.api.minecraft.entities.ObjectType {
      BOAT(1, EntityTypes1_13.EntityType.BOAT),
      ITEM(2, EntityTypes1_13.EntityType.ITEM),
      AREA_EFFECT_CLOUD(3, EntityTypes1_13.EntityType.AREA_EFFECT_CLOUD),
      MINECART(10, EntityTypes1_13.EntityType.MINECART),
      TNT_PRIMED(50, EntityTypes1_13.EntityType.TNT),
      ENDER_CRYSTAL(51, EntityTypes1_13.EntityType.END_CRYSTAL),
      TIPPED_ARROW(60, EntityTypes1_13.EntityType.ARROW),
      SNOWBALL(61, EntityTypes1_13.EntityType.SNOWBALL),
      EGG(62, EntityTypes1_13.EntityType.EGG),
      FIREBALL(63, EntityTypes1_13.EntityType.FIREBALL),
      SMALL_FIREBALL(64, EntityTypes1_13.EntityType.SMALL_FIREBALL),
      ENDER_PEARL(65, EntityTypes1_13.EntityType.ENDER_PEARL),
      WITHER_SKULL(66, EntityTypes1_13.EntityType.WITHER_SKULL),
      SHULKER_BULLET(67, EntityTypes1_13.EntityType.SHULKER_BULLET),
      LLAMA_SPIT(68, EntityTypes1_13.EntityType.LLAMA_SPIT),
      FALLING_BLOCK(70, EntityTypes1_13.EntityType.FALLING_BLOCK),
      ITEM_FRAME(71, EntityTypes1_13.EntityType.ITEM_FRAME),
      EYE_OF_ENDER(72, EntityTypes1_13.EntityType.EYE_OF_ENDER),
      POTION(73, EntityTypes1_13.EntityType.POTION),
      EXPERIENCE_BOTTLE(75, EntityTypes1_13.EntityType.EXPERIENCE_BOTTLE),
      FIREWORK_ROCKET(76, EntityTypes1_13.EntityType.FIREWORK_ROCKET),
      LEASH(77, EntityTypes1_13.EntityType.LEASH_KNOT),
      ARMOR_STAND(78, EntityTypes1_13.EntityType.ARMOR_STAND),
      EVOKER_FANGS(79, EntityTypes1_13.EntityType.EVOKER_FANGS),
      FISHIHNG_HOOK(90, EntityTypes1_13.EntityType.FISHING_BOBBER),
      SPECTRAL_ARROW(91, EntityTypes1_13.EntityType.SPECTRAL_ARROW),
      DRAGON_FIREBALL(93, EntityTypes1_13.EntityType.DRAGON_FIREBALL),
      TRIDENT(94, EntityTypes1_13.EntityType.TRIDENT);

      private static final Map<Integer, EntityTypes1_13.ObjectType> TYPES = new HashMap<>();
      private final int id;
      private final EntityTypes1_13.EntityType type;

      private ObjectType(int id, EntityTypes1_13.EntityType type) {
         this.id = id;
         this.type = type;
      }

      @Override
      public int getId() {
         return this.id;
      }

      public EntityTypes1_13.EntityType getType() {
         return this.type;
      }

      public static Optional<EntityTypes1_13.ObjectType> findById(int id) {
         return id == -1 ? Optional.empty() : Optional.ofNullable(TYPES.get(id));
      }

      public static Optional<EntityTypes1_13.EntityType> getPCEntity(int id) {
         Optional<EntityTypes1_13.ObjectType> output = findById(id);
         return !output.isPresent() ? Optional.empty() : Optional.of(output.get().type);
      }

      public static Optional<EntityTypes1_13.ObjectType> fromEntityType(EntityTypes1_13.EntityType type) {
         for (EntityTypes1_13.ObjectType ent : values()) {
            if (ent.type == type) {
               return Optional.of(ent);
            }
         }

         return Optional.empty();
      }

      static {
         for (EntityTypes1_13.ObjectType type : values()) {
            TYPES.put(type.id, type);
         }
      }
   }
}
