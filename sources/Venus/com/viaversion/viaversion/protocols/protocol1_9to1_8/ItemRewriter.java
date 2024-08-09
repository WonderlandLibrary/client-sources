/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ItemRewriter {
    private static final Map<String, Integer> ENTTIY_NAME_TO_ID = new HashMap<String, Integer>();
    private static final Map<Integer, String> ENTTIY_ID_TO_NAME = new HashMap<Integer, String>();
    private static final Map<String, Integer> POTION_NAME_TO_ID = new HashMap<String, Integer>();
    private static final Map<Integer, String> POTION_ID_TO_NAME = new HashMap<Integer, String>();
    private static final Int2IntMap POTION_INDEX = new Int2IntOpenHashMap(36, 0.99f);

    public static void toServer(Item item) {
        if (item != null) {
            Object object;
            Tag tag;
            int n;
            CompoundTag compoundTag;
            if (item.identifier() == 383 && item.data() == 0) {
                compoundTag = item.tag();
                n = 0;
                if (compoundTag != null && compoundTag.get("EntityTag") instanceof CompoundTag) {
                    tag = (CompoundTag)compoundTag.get("EntityTag");
                    if (((CompoundTag)tag).get("id") instanceof StringTag && ENTTIY_NAME_TO_ID.containsKey(((StringTag)(object = (StringTag)((CompoundTag)tag).get("id"))).getValue())) {
                        n = ENTTIY_NAME_TO_ID.get(((StringTag)object).getValue());
                    }
                    compoundTag.remove("EntityTag");
                }
                item.setTag(compoundTag);
                item.setData((short)n);
            }
            if (item.identifier() == 373) {
                compoundTag = item.tag();
                n = 0;
                if (compoundTag != null && compoundTag.get("Potion") instanceof StringTag) {
                    tag = (StringTag)compoundTag.get("Potion");
                    object = ((StringTag)tag).getValue().replace("minecraft:", "");
                    if (POTION_NAME_TO_ID.containsKey(object)) {
                        n = POTION_NAME_TO_ID.get(object);
                    }
                    compoundTag.remove("Potion");
                }
                item.setTag(compoundTag);
                item.setData((short)n);
            }
            if (item.identifier() == 438) {
                compoundTag = item.tag();
                n = 0;
                item.setIdentifier(373);
                if (compoundTag != null && compoundTag.get("Potion") instanceof StringTag) {
                    tag = (StringTag)compoundTag.get("Potion");
                    object = ((StringTag)tag).getValue().replace("minecraft:", "");
                    if (POTION_NAME_TO_ID.containsKey(object)) {
                        n = POTION_NAME_TO_ID.get(object) + 8192;
                    }
                    compoundTag.remove("Potion");
                }
                item.setTag(compoundTag);
                item.setData((short)n);
            }
            boolean bl = item.identifier() >= 198 && item.identifier() <= 212;
            bl |= item.identifier() == 397 && item.data() == 5;
            if (bl |= item.identifier() >= 432 && item.identifier() <= 448) {
                item.setIdentifier(1);
                item.setData((short)0);
            }
        }
    }

    public static void rewriteBookToServer(Item item) {
        int n = item.identifier();
        if (n != 387) {
            return;
        }
        CompoundTag compoundTag = item.tag();
        ListTag listTag = (ListTag)compoundTag.get("pages");
        if (listTag == null) {
            return;
        }
        for (int i = 0; i < listTag.size(); ++i) {
            Object t = listTag.get(i);
            if (!(t instanceof StringTag)) continue;
            StringTag stringTag = (StringTag)t;
            String string = stringTag.getValue();
            string = string.replaceAll(" ", "").isEmpty() ? "\"" + ItemRewriter.fixBookSpaceChars(string) + "\"" : ItemRewriter.fixBookSpaceChars(string);
            stringTag.setValue(string);
        }
    }

    private static String fixBookSpaceChars(String string) {
        if (!string.startsWith(" ")) {
            return string;
        }
        string = "\u00a7r" + string;
        return string;
    }

    public static void toClient(Item item) {
        if (item != null) {
            StringTag stringTag;
            Object object;
            Object object2;
            CompoundTag compoundTag;
            if (item.identifier() == 383 && item.data() != 0) {
                compoundTag = item.tag();
                if (compoundTag == null) {
                    compoundTag = new CompoundTag();
                }
                object2 = new CompoundTag();
                object = ENTTIY_ID_TO_NAME.get(item.data());
                if (object != null) {
                    stringTag = new StringTag((String)object);
                    ((CompoundTag)object2).put("id", stringTag);
                    compoundTag.put("EntityTag", object2);
                }
                item.setTag(compoundTag);
                item.setData((short)0);
            }
            if (item.identifier() == 373) {
                compoundTag = item.tag();
                if (compoundTag == null) {
                    compoundTag = new CompoundTag();
                }
                if (item.data() >= 16384) {
                    item.setIdentifier(438);
                    item.setData((short)(item.data() - 8192));
                }
                object2 = ItemRewriter.potionNameFromDamage(item.data());
                object = new StringTag("minecraft:" + (String)object2);
                compoundTag.put("Potion", object);
                item.setTag(compoundTag);
                item.setData((short)0);
            }
            if (item.identifier() == 387) {
                compoundTag = item.tag();
                if (compoundTag == null) {
                    compoundTag = new CompoundTag();
                }
                if ((object2 = (ListTag)compoundTag.get("pages")) == null) {
                    object2 = new ListTag(Collections.singletonList(new StringTag(Protocol1_9To1_8.fixJson("").toString())));
                    compoundTag.put("pages", object2);
                    item.setTag(compoundTag);
                    return;
                }
                for (int i = 0; i < ((ListTag)object2).size(); ++i) {
                    if (!(((ListTag)object2).get(i) instanceof StringTag)) continue;
                    stringTag = (StringTag)((ListTag)object2).get(i);
                    stringTag.setValue(Protocol1_9To1_8.fixJson(stringTag.getValue()).toString());
                }
                item.setTag(compoundTag);
            }
        }
    }

    public static String potionNameFromDamage(short s) {
        String string;
        String string2 = POTION_ID_TO_NAME.get(s);
        if (string2 != null) {
            return string2;
        }
        if (s == 0) {
            return "water";
        }
        int n = s & 0xF;
        int n2 = s & 0x3F;
        boolean bl = (s & 0x20) > 0;
        boolean bl2 = (s & 0x40) > 0;
        boolean bl3 = true;
        boolean bl4 = true;
        block0 : switch (n) {
            case 1: {
                string = "regeneration";
                break;
            }
            case 2: {
                string = "swiftness";
                break;
            }
            case 3: {
                string = "fire_resistance";
                bl3 = false;
                break;
            }
            case 4: {
                string = "poison";
                break;
            }
            case 5: {
                string = "healing";
                bl4 = false;
                break;
            }
            case 6: {
                string = "night_vision";
                bl3 = false;
                break;
            }
            case 8: {
                string = "weakness";
                bl3 = false;
                break;
            }
            case 9: {
                string = "strength";
                break;
            }
            case 10: {
                string = "slowness";
                bl3 = false;
                break;
            }
            case 11: {
                string = "leaping";
                break;
            }
            case 12: {
                string = "harming";
                bl4 = false;
                break;
            }
            case 13: {
                string = "water_breathing";
                bl3 = false;
                break;
            }
            case 14: {
                string = "invisibility";
                bl3 = false;
                break;
            }
            default: {
                bl3 = false;
                bl4 = false;
                switch (n2) {
                    case 0: {
                        string = "mundane";
                        break block0;
                    }
                    case 16: {
                        string = "awkward";
                        break block0;
                    }
                    case 32: {
                        string = "thick";
                        break block0;
                    }
                }
                string = "empty";
            }
        }
        if (n > 0) {
            if (bl3 && bl) {
                string = "strong_" + string;
            } else if (bl4 && bl2) {
                string = "long_" + string;
            }
        }
        return string;
    }

    public static int getNewEffectID(int n) {
        int n2;
        if (n >= 16384) {
            n -= 8192;
        }
        if ((n2 = POTION_INDEX.get(n)) != -1) {
            return n2;
        }
        n2 = POTION_INDEX.get(n = POTION_NAME_TO_ID.get(ItemRewriter.potionNameFromDamage((short)n)).intValue());
        return n2 != -1 ? n2 : 0;
    }

    private static void registerEntity(int n, String string) {
        ENTTIY_ID_TO_NAME.put(n, string);
        ENTTIY_NAME_TO_ID.put(string, n);
    }

    private static void registerPotion(int n, String string) {
        POTION_INDEX.put(n, POTION_ID_TO_NAME.size());
        POTION_ID_TO_NAME.put(n, string);
        POTION_NAME_TO_ID.put(string, n);
    }

    static {
        ItemRewriter.registerEntity(1, "Item");
        ItemRewriter.registerEntity(2, "XPOrb");
        ItemRewriter.registerEntity(7, "ThrownEgg");
        ItemRewriter.registerEntity(8, "LeashKnot");
        ItemRewriter.registerEntity(9, "Painting");
        ItemRewriter.registerEntity(10, "Arrow");
        ItemRewriter.registerEntity(11, "Snowball");
        ItemRewriter.registerEntity(12, "Fireball");
        ItemRewriter.registerEntity(13, "SmallFireball");
        ItemRewriter.registerEntity(14, "ThrownEnderpearl");
        ItemRewriter.registerEntity(15, "EyeOfEnderSignal");
        ItemRewriter.registerEntity(16, "ThrownPotion");
        ItemRewriter.registerEntity(17, "ThrownExpBottle");
        ItemRewriter.registerEntity(18, "ItemFrame");
        ItemRewriter.registerEntity(19, "WitherSkull");
        ItemRewriter.registerEntity(20, "PrimedTnt");
        ItemRewriter.registerEntity(21, "FallingSand");
        ItemRewriter.registerEntity(22, "FireworksRocketEntity");
        ItemRewriter.registerEntity(30, "ArmorStand");
        ItemRewriter.registerEntity(40, "MinecartCommandBlock");
        ItemRewriter.registerEntity(41, "Boat");
        ItemRewriter.registerEntity(42, "MinecartRideable");
        ItemRewriter.registerEntity(43, "MinecartChest");
        ItemRewriter.registerEntity(44, "MinecartFurnace");
        ItemRewriter.registerEntity(45, "MinecartTNT");
        ItemRewriter.registerEntity(46, "MinecartHopper");
        ItemRewriter.registerEntity(47, "MinecartSpawner");
        ItemRewriter.registerEntity(48, "Mob");
        ItemRewriter.registerEntity(49, "Monster");
        ItemRewriter.registerEntity(50, "Creeper");
        ItemRewriter.registerEntity(51, "Skeleton");
        ItemRewriter.registerEntity(52, "Spider");
        ItemRewriter.registerEntity(53, "Giant");
        ItemRewriter.registerEntity(54, "Zombie");
        ItemRewriter.registerEntity(55, "Slime");
        ItemRewriter.registerEntity(56, "Ghast");
        ItemRewriter.registerEntity(57, "PigZombie");
        ItemRewriter.registerEntity(58, "Enderman");
        ItemRewriter.registerEntity(59, "CaveSpider");
        ItemRewriter.registerEntity(60, "Silverfish");
        ItemRewriter.registerEntity(61, "Blaze");
        ItemRewriter.registerEntity(62, "LavaSlime");
        ItemRewriter.registerEntity(63, "EnderDragon");
        ItemRewriter.registerEntity(64, "WitherBoss");
        ItemRewriter.registerEntity(65, "Bat");
        ItemRewriter.registerEntity(66, "Witch");
        ItemRewriter.registerEntity(67, "Endermite");
        ItemRewriter.registerEntity(68, "Guardian");
        ItemRewriter.registerEntity(90, "Pig");
        ItemRewriter.registerEntity(91, "Sheep");
        ItemRewriter.registerEntity(92, "Cow");
        ItemRewriter.registerEntity(93, "Chicken");
        ItemRewriter.registerEntity(94, "Squid");
        ItemRewriter.registerEntity(95, "Wolf");
        ItemRewriter.registerEntity(96, "MushroomCow");
        ItemRewriter.registerEntity(97, "SnowMan");
        ItemRewriter.registerEntity(98, "Ozelot");
        ItemRewriter.registerEntity(99, "VillagerGolem");
        ItemRewriter.registerEntity(100, "EntityHorse");
        ItemRewriter.registerEntity(101, "Rabbit");
        ItemRewriter.registerEntity(120, "Villager");
        ItemRewriter.registerEntity(200, "EnderCrystal");
        ItemRewriter.registerPotion(-1, "empty");
        ItemRewriter.registerPotion(0, "water");
        ItemRewriter.registerPotion(64, "mundane");
        ItemRewriter.registerPotion(32, "thick");
        ItemRewriter.registerPotion(16, "awkward");
        ItemRewriter.registerPotion(8198, "night_vision");
        ItemRewriter.registerPotion(8262, "long_night_vision");
        ItemRewriter.registerPotion(8206, "invisibility");
        ItemRewriter.registerPotion(8270, "long_invisibility");
        ItemRewriter.registerPotion(8203, "leaping");
        ItemRewriter.registerPotion(8267, "long_leaping");
        ItemRewriter.registerPotion(8235, "strong_leaping");
        ItemRewriter.registerPotion(8195, "fire_resistance");
        ItemRewriter.registerPotion(8259, "long_fire_resistance");
        ItemRewriter.registerPotion(8194, "swiftness");
        ItemRewriter.registerPotion(8258, "long_swiftness");
        ItemRewriter.registerPotion(8226, "strong_swiftness");
        ItemRewriter.registerPotion(8202, "slowness");
        ItemRewriter.registerPotion(8266, "long_slowness");
        ItemRewriter.registerPotion(8205, "water_breathing");
        ItemRewriter.registerPotion(8269, "long_water_breathing");
        ItemRewriter.registerPotion(8261, "healing");
        ItemRewriter.registerPotion(8229, "strong_healing");
        ItemRewriter.registerPotion(8204, "harming");
        ItemRewriter.registerPotion(8236, "strong_harming");
        ItemRewriter.registerPotion(8196, "poison");
        ItemRewriter.registerPotion(8260, "long_poison");
        ItemRewriter.registerPotion(8228, "strong_poison");
        ItemRewriter.registerPotion(8193, "regeneration");
        ItemRewriter.registerPotion(8257, "long_regeneration");
        ItemRewriter.registerPotion(8225, "strong_regeneration");
        ItemRewriter.registerPotion(8201, "strength");
        ItemRewriter.registerPotion(8265, "long_strength");
        ItemRewriter.registerPotion(8233, "strong_strength");
        ItemRewriter.registerPotion(8200, "weakness");
        ItemRewriter.registerPotion(8264, "long_weakness");
    }
}

