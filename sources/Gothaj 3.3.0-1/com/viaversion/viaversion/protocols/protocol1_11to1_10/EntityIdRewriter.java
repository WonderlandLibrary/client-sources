package com.viaversion.viaversion.protocols.protocol1_11to1_10;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.util.Key;

public class EntityIdRewriter {
   private static final BiMap<String, String> oldToNewNames = HashBiMap.create();

   private static void rewrite(String oldName, String newName) {
      oldToNewNames.put(oldName, Key.namespaced(newName));
   }

   public static void toClient(CompoundTag tag) {
      toClient(tag, false);
   }

   public static void toClient(CompoundTag tag, boolean backwards) {
      Tag idTag = tag.get("id");
      if (idTag instanceof StringTag) {
         StringTag id = (StringTag)idTag;
         String newName = backwards ? (String)oldToNewNames.inverse().get(id.getValue()) : (String)oldToNewNames.get(id.getValue());
         if (newName != null) {
            id.setValue(newName);
         }
      }
   }

   public static void toClientSpawner(CompoundTag tag) {
      toClientSpawner(tag, false);
   }

   public static void toClientSpawner(CompoundTag tag, boolean backwards) {
      if (tag != null) {
         Tag spawnDataTag = tag.get("SpawnData");
         if (spawnDataTag != null) {
            toClient((CompoundTag)spawnDataTag, backwards);
         }
      }
   }

   public static void toClientItem(Item item) {
      toClientItem(item, false);
   }

   public static void toClientItem(Item item, boolean backwards) {
      if (hasEntityTag(item)) {
         toClient(item.tag().get("EntityTag"), backwards);
      }

      if (item != null && item.amount() <= 0) {
         item.setAmount(1);
      }
   }

   public static void toServerItem(Item item) {
      toServerItem(item, false);
   }

   public static void toServerItem(Item item, boolean backwards) {
      if (hasEntityTag(item)) {
         CompoundTag entityTag = item.tag().get("EntityTag");
         Tag idTag = entityTag.get("id");
         if (idTag instanceof StringTag) {
            StringTag id = (StringTag)idTag;
            String newName = backwards ? (String)oldToNewNames.get(id.getValue()) : (String)oldToNewNames.inverse().get(id.getValue());
            if (newName != null) {
               id.setValue(newName);
            }
         }
      }
   }

   private static boolean hasEntityTag(Item item) {
      if (item != null && item.identifier() == 383) {
         CompoundTag tag = item.tag();
         if (tag == null) {
            return false;
         } else {
            Tag entityTag = tag.get("EntityTag");
            return entityTag instanceof CompoundTag && ((CompoundTag)entityTag).get("id") instanceof StringTag;
         }
      } else {
         return false;
      }
   }

   static {
      rewrite("AreaEffectCloud", "area_effect_cloud");
      rewrite("ArmorStand", "armor_stand");
      rewrite("Arrow", "arrow");
      rewrite("Bat", "bat");
      rewrite("Blaze", "blaze");
      rewrite("Boat", "boat");
      rewrite("CaveSpider", "cave_spider");
      rewrite("Chicken", "chicken");
      rewrite("Cow", "cow");
      rewrite("Creeper", "creeper");
      rewrite("Donkey", "donkey");
      rewrite("DragonFireball", "dragon_fireball");
      rewrite("ElderGuardian", "elder_guardian");
      rewrite("EnderCrystal", "ender_crystal");
      rewrite("EnderDragon", "ender_dragon");
      rewrite("Enderman", "enderman");
      rewrite("Endermite", "endermite");
      rewrite("EntityHorse", "horse");
      rewrite("EyeOfEnderSignal", "eye_of_ender_signal");
      rewrite("FallingSand", "falling_block");
      rewrite("Fireball", "fireball");
      rewrite("FireworksRocketEntity", "fireworks_rocket");
      rewrite("Ghast", "ghast");
      rewrite("Giant", "giant");
      rewrite("Guardian", "guardian");
      rewrite("Husk", "husk");
      rewrite("Item", "item");
      rewrite("ItemFrame", "item_frame");
      rewrite("LavaSlime", "magma_cube");
      rewrite("LeashKnot", "leash_knot");
      rewrite("MinecartChest", "chest_minecart");
      rewrite("MinecartCommandBlock", "commandblock_minecart");
      rewrite("MinecartFurnace", "furnace_minecart");
      rewrite("MinecartHopper", "hopper_minecart");
      rewrite("MinecartRideable", "minecart");
      rewrite("MinecartSpawner", "spawner_minecart");
      rewrite("MinecartTNT", "tnt_minecart");
      rewrite("Mule", "mule");
      rewrite("MushroomCow", "mooshroom");
      rewrite("Ozelot", "ocelot");
      rewrite("Painting", "painting");
      rewrite("Pig", "pig");
      rewrite("PigZombie", "zombie_pigman");
      rewrite("PolarBear", "polar_bear");
      rewrite("PrimedTnt", "tnt");
      rewrite("Rabbit", "rabbit");
      rewrite("Sheep", "sheep");
      rewrite("Shulker", "shulker");
      rewrite("ShulkerBullet", "shulker_bullet");
      rewrite("Silverfish", "silverfish");
      rewrite("Skeleton", "skeleton");
      rewrite("SkeletonHorse", "skeleton_horse");
      rewrite("Slime", "slime");
      rewrite("SmallFireball", "small_fireball");
      rewrite("Snowball", "snowball");
      rewrite("SnowMan", "snowman");
      rewrite("SpectralArrow", "spectral_arrow");
      rewrite("Spider", "spider");
      rewrite("Squid", "squid");
      rewrite("Stray", "stray");
      rewrite("ThrownEgg", "egg");
      rewrite("ThrownEnderpearl", "ender_pearl");
      rewrite("ThrownExpBottle", "xp_bottle");
      rewrite("ThrownPotion", "potion");
      rewrite("Villager", "villager");
      rewrite("VillagerGolem", "villager_golem");
      rewrite("Witch", "witch");
      rewrite("WitherBoss", "wither");
      rewrite("WitherSkeleton", "wither_skeleton");
      rewrite("WitherSkull", "wither_skull");
      rewrite("Wolf", "wolf");
      rewrite("XPOrb", "xp_orb");
      rewrite("Zombie", "zombie");
      rewrite("ZombieHorse", "zombie_horse");
      rewrite("ZombieVillager", "zombie_villager");
   }
}
