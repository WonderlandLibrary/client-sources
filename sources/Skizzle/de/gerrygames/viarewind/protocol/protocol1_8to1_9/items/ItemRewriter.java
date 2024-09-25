/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.items;

import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ReplacementRegistry1_8to1_9;
import de.gerrygames.viarewind.utils.Enchantments;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.viaversion.libs.opennbt.tag.builtin.ByteTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ShortTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class ItemRewriter {
    private static Map<String, Integer> ENTTIY_NAME_TO_ID;
    private static Map<Integer, String> ENTTIY_ID_TO_NAME;
    private static Map<String, Integer> POTION_NAME_TO_ID;
    private static Map<Integer, String> POTION_ID_TO_NAME;
    private static Map<String, String> POTION_NAME_INDEX;

    public static Item toClient(Item item) {
        ByteTag unbreakable;
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.getTag();
        if (tag == null) {
            tag = new CompoundTag("");
            item.setTag(tag);
        }
        CompoundTag viaVersionTag = new CompoundTag("ViaRewind1_8to1_9");
        tag.put(viaVersionTag);
        viaVersionTag.put(new ShortTag("id", (short)item.getIdentifier()));
        viaVersionTag.put(new ShortTag("data", item.getData()));
        CompoundTag display = (CompoundTag)tag.get("display");
        if (display != null && display.contains("Name")) {
            viaVersionTag.put(new StringTag("displayName", (String)((Tag)display.get("Name")).getValue()));
        }
        if (display != null && display.contains("Lore")) {
            viaVersionTag.put(new ListTag("lore", (List<Tag>)((ListTag)display.get("Lore")).getValue()));
        }
        if (tag.contains("ench") || tag.contains("StoredEnchantments")) {
            ListTag enchTag = tag.contains("ench") ? (ListTag)tag.get("ench") : (ListTag)tag.get("StoredEnchantments");
            Object enchants = enchTag.getValue();
            ArrayList<Tag> lore = new ArrayList<Tag>();
            Iterator iterator = enchants.iterator();
            while (iterator.hasNext()) {
                String s;
                Tag ench = (Tag)iterator.next();
                short id = (Short)((Tag)((CompoundTag)ench).get("id")).getValue();
                short lvl = (Short)((Tag)((CompoundTag)ench).get("lvl")).getValue();
                if (id == 70) {
                    s = "\u00a7r\u00a77Mending ";
                } else {
                    if (id != 9) continue;
                    s = "\u00a7r\u00a77Frost Walker ";
                }
                enchTag.remove(ench);
                s = s + Enchantments.ENCHANTMENTS.getOrDefault(lvl, "enchantment.level." + lvl);
                lore.add(new StringTag("", s));
            }
            if (!lore.isEmpty()) {
                ListTag loreTag;
                if (display == null) {
                    display = new CompoundTag("display");
                    tag.put(display);
                    viaVersionTag.put(new ByteTag("noDisplay"));
                }
                if ((loreTag = (ListTag)display.get("Lore")) == null) {
                    loreTag = new ListTag("Lore", StringTag.class);
                    display.put(loreTag);
                }
                lore.addAll((Collection<Tag>)loreTag.getValue());
                loreTag.setValue(lore);
            }
        }
        if (item.getData() != 0 && tag.contains("Unbreakable") && (unbreakable = (ByteTag)tag.get("Unbreakable")).getValue() != 0) {
            ListTag loreTag;
            viaVersionTag.put(new ByteTag("Unbreakable", unbreakable.getValue()));
            tag.remove("Unbreakable");
            if (display == null) {
                display = new CompoundTag("display");
                tag.put(display);
                viaVersionTag.put(new ByteTag("noDisplay"));
            }
            if ((loreTag = (ListTag)display.get("Lore")) == null) {
                loreTag = new ListTag("Lore", StringTag.class);
                display.put(loreTag);
            }
            loreTag.add(new StringTag("", "\u00a79Unbreakable"));
        }
        if (tag.contains("AttributeModifiers")) {
            viaVersionTag.put(((Tag)tag.get("AttributeModifiers")).clone());
        }
        if (item.getIdentifier() == 383 && item.getData() == 0) {
            CompoundTag entityTag;
            int data = 0;
            if (tag.contains("EntityTag") && (entityTag = (CompoundTag)tag.get("EntityTag")).contains("id")) {
                StringTag id = (StringTag)entityTag.get("id");
                if (ENTTIY_NAME_TO_ID.containsKey(id.getValue())) {
                    data = ENTTIY_NAME_TO_ID.get(id.getValue());
                } else if (display == null) {
                    display = new CompoundTag("display");
                    tag.put(display);
                    viaVersionTag.put(new ByteTag("noDisplay"));
                    display.put(new StringTag("Name", "\u00a7rSpawn " + id.getValue()));
                }
            }
            item.setData((short)data);
        }
        ReplacementRegistry1_8to1_9.replace(item);
        if (item.getIdentifier() == 373 || item.getIdentifier() == 438 || item.getIdentifier() == 441) {
            int data = 0;
            if (tag.contains("Potion")) {
                StringTag potion = (StringTag)tag.get("Potion");
                String potionName = potion.getValue().replace("minecraft:", "");
                if (POTION_NAME_TO_ID.containsKey(potionName)) {
                    data = POTION_NAME_TO_ID.get(potionName);
                }
                if (item.getIdentifier() == 438) {
                    potionName = potionName + "_splash";
                } else if (item.getIdentifier() == 441) {
                    potionName = potionName + "_lingering";
                }
                if ((display == null || !display.contains("Name")) && POTION_NAME_INDEX.containsKey(potionName)) {
                    if (display == null) {
                        display = new CompoundTag("display");
                        tag.put(display);
                        viaVersionTag.put(new ByteTag("noDisplay"));
                    }
                    display.put(new StringTag("Name", POTION_NAME_INDEX.get(potionName)));
                }
            }
            if (item.getIdentifier() == 438 || item.getIdentifier() == 441) {
                item.setIdentifier(373);
                data += 8192;
            }
            item.setData((short)data);
        }
        if (tag.contains("AttributeModifiers")) {
            ListTag attributes = (ListTag)tag.get("AttributeModifiers");
            for (int i = 0; i < attributes.size(); ++i) {
                CompoundTag attribute = (CompoundTag)attributes.get(i);
                String name = (String)((Tag)attribute.get("AttributeName")).getValue();
                if (Protocol1_8TO1_9.VALID_ATTRIBUTES.contains((Object)attribute)) continue;
                attributes.remove(attribute);
                --i;
            }
        }
        if (viaVersionTag.size() == 2 && ((Short)((Tag)viaVersionTag.get("id")).getValue()).shortValue() == item.getIdentifier() && ((Short)((Tag)viaVersionTag.get("data")).getValue()).shortValue() == item.getData()) {
            item.getTag().remove("ViaRewind1_8to1_9");
            if (item.getTag().isEmpty()) {
                item.setTag(null);
            }
        }
        return item;
    }

    public static Item toServer(Item item) {
        CompoundTag display;
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.getTag();
        if (item.getIdentifier() == 383 && item.getData() != 0) {
            if (tag == null) {
                tag = new CompoundTag("");
                item.setTag(tag);
            }
            if (!tag.contains("EntityTag") && ENTTIY_ID_TO_NAME.containsKey(item.getData())) {
                CompoundTag entityTag = new CompoundTag("EntityTag");
                entityTag.put(new StringTag("id", ENTTIY_ID_TO_NAME.get(item.getData())));
                tag.put(entityTag);
            }
            item.setData((short)0);
        }
        if (!(item.getIdentifier() != 373 || tag != null && tag.contains("Potion"))) {
            if (tag == null) {
                tag = new CompoundTag("");
                item.setTag(tag);
            }
            if (item.getData() >= 16384) {
                item.setIdentifier(438);
                item.setData((short)(item.getData() - 8192));
            }
            String name = item.getData() == 8192 ? "water" : us.myles.ViaVersion.protocols.protocol1_9to1_8.ItemRewriter.potionNameFromDamage(item.getData());
            tag.put(new StringTag("Potion", "minecraft:" + name));
            item.setData((short)0);
        }
        if (tag == null || !item.getTag().contains("ViaRewind1_8to1_9")) {
            return item;
        }
        CompoundTag viaVersionTag = (CompoundTag)tag.remove("ViaRewind1_8to1_9");
        item.setIdentifier(((Short)((Tag)viaVersionTag.get("id")).getValue()).shortValue());
        item.setData((Short)((Tag)viaVersionTag.get("data")).getValue());
        if (viaVersionTag.contains("noDisplay")) {
            tag.remove("display");
        }
        if (viaVersionTag.contains("Unbreakable")) {
            tag.put(((Tag)viaVersionTag.get("Unbreakable")).clone());
        }
        if (viaVersionTag.contains("displayName")) {
            StringTag name;
            display = (CompoundTag)tag.get("display");
            if (display == null) {
                display = new CompoundTag("display");
                tag.put(display);
            }
            if ((name = (StringTag)display.get("Name")) == null) {
                display.put(new StringTag("Name", (String)((Tag)viaVersionTag.get("displayName")).getValue()));
            } else {
                name.setValue((String)((Tag)viaVersionTag.get("displayName")).getValue());
            }
        } else if (tag.contains("display")) {
            ((CompoundTag)tag.get("display")).remove("Name");
        }
        if (viaVersionTag.contains("lore")) {
            ListTag lore;
            display = (CompoundTag)tag.get("display");
            if (display == null) {
                display = new CompoundTag("display");
                tag.put(display);
            }
            if ((lore = (ListTag)display.get("Lore")) == null) {
                display.put(new ListTag("Lore", (List)((Tag)viaVersionTag.get("lore")).getValue()));
            } else {
                lore.setValue((List)((Tag)viaVersionTag.get("lore")).getValue());
            }
        } else if (tag.contains("display")) {
            ((CompoundTag)tag.get("display")).remove("Lore");
        }
        tag.remove("AttributeModifiers");
        if (viaVersionTag.contains("AttributeModifiers")) {
            tag.put(viaVersionTag.get("AttributeModifiers"));
        }
        return item;
    }

    static {
        POTION_NAME_INDEX = new HashMap<String, String>();
        for (Field field : ItemRewriter.class.getDeclaredFields()) {
            try {
                Field other = us.myles.ViaVersion.protocols.protocol1_9to1_8.ItemRewriter.class.getDeclaredField(field.getName());
                other.setAccessible(true);
                field.setAccessible(true);
                field.set(null, other.get(null));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        POTION_NAME_TO_ID.put("luck", 8203);
        POTION_NAME_INDEX.put("water", "\u00a7rWater Bottle");
        POTION_NAME_INDEX.put("mundane", "\u00a7rMundane Potion");
        POTION_NAME_INDEX.put("thick", "\u00a7rThick Potion");
        POTION_NAME_INDEX.put("awkward", "\u00a7rAwkward Potion");
        POTION_NAME_INDEX.put("water_splash", "\u00a7rSplash Water Bottle");
        POTION_NAME_INDEX.put("mundane_splash", "\u00a7rMundane Splash Potion");
        POTION_NAME_INDEX.put("thick_splash", "\u00a7rThick Splash Potion");
        POTION_NAME_INDEX.put("awkward_splash", "\u00a7rAwkward Splash Potion");
        POTION_NAME_INDEX.put("water_lingering", "\u00a7rLingering Water Bottle");
        POTION_NAME_INDEX.put("mundane_lingering", "\u00a7rMundane Lingering Potion");
        POTION_NAME_INDEX.put("thick_lingering", "\u00a7rThick Lingering Potion");
        POTION_NAME_INDEX.put("awkward_lingering", "\u00a7rAwkward Lingering Potion");
        POTION_NAME_INDEX.put("night_vision_lingering", "\u00a7rLingering Potion of Night Vision");
        POTION_NAME_INDEX.put("long_night_vision_lingering", "\u00a7rLingering Potion of Night Vision");
        POTION_NAME_INDEX.put("invisibility_lingering", "\u00a7rLingering Potion of Invisibility");
        POTION_NAME_INDEX.put("long_invisibility_lingering", "\u00a7rLingering Potion of Invisibility");
        POTION_NAME_INDEX.put("leaping_lingering", "\u00a7rLingering Potion of Leaping");
        POTION_NAME_INDEX.put("long_leaping_lingering", "\u00a7rLingering Potion of Leaping");
        POTION_NAME_INDEX.put("strong_leaping_lingering", "\u00a7rLingering Potion of Leaping");
        POTION_NAME_INDEX.put("fire_resistance_lingering", "\u00a7rLingering Potion of Fire Resistance");
        POTION_NAME_INDEX.put("long_fire_resistance_lingering", "\u00a7rLingering Potion of Fire Resistance");
        POTION_NAME_INDEX.put("swiftness_lingering", "\u00a7rLingering Potion of Swiftness");
        POTION_NAME_INDEX.put("long_swiftness_lingering", "\u00a7rLingering Potion of Swiftness");
        POTION_NAME_INDEX.put("strong_swiftness_lingering", "\u00a7rLingering Potion of Swiftness");
        POTION_NAME_INDEX.put("slowness_lingering", "\u00a7rLingering Potion of Slowness");
        POTION_NAME_INDEX.put("long_slowness_lingering", "\u00a7rLingering Potion of Slowness");
        POTION_NAME_INDEX.put("water_breathing_lingering", "\u00a7rLingering Potion of Water Breathing");
        POTION_NAME_INDEX.put("long_water_breathing_lingering", "\u00a7rLingering Potion of Water Breathing");
        POTION_NAME_INDEX.put("healing_lingering", "\u00a7rLingering Potion of Healing");
        POTION_NAME_INDEX.put("strong_healing_lingering", "\u00a7rLingering Potion of Healing");
        POTION_NAME_INDEX.put("harming_lingering", "\u00a7rLingering Potion of Harming");
        POTION_NAME_INDEX.put("strong_harming_lingering", "\u00a7rLingering Potion of Harming");
        POTION_NAME_INDEX.put("poison_lingering", "\u00a7rLingering Potion of Poisen");
        POTION_NAME_INDEX.put("long_poison_lingering", "\u00a7rLingering Potion of Poisen");
        POTION_NAME_INDEX.put("strong_poison_lingering", "\u00a7rLingering Potion of Poisen");
        POTION_NAME_INDEX.put("regeneration_lingering", "\u00a7rLingering Potion of Regeneration");
        POTION_NAME_INDEX.put("long_regeneration_lingering", "\u00a7rLingering Potion of Regeneration");
        POTION_NAME_INDEX.put("strong_regeneration_lingering", "\u00a7rLingering Potion of Regeneration");
        POTION_NAME_INDEX.put("strength_lingering", "\u00a7rLingering Potion of Strength");
        POTION_NAME_INDEX.put("long_strength_lingering", "\u00a7rLingering Potion of Strength");
        POTION_NAME_INDEX.put("strong_strength_lingering", "\u00a7rLingering Potion of Strength");
        POTION_NAME_INDEX.put("weakness_lingering", "\u00a7rLingering Potion of Weakness");
        POTION_NAME_INDEX.put("long_weakness_lingering", "\u00a7rLingering Potion of Weakness");
        POTION_NAME_INDEX.put("luck_lingering", "\u00a7rLingering Potion of Luck");
        POTION_NAME_INDEX.put("luck", "\u00a7rPotion of Luck");
        POTION_NAME_INDEX.put("luck_splash", "\u00a7rSplash Potion of Luck");
    }
}

