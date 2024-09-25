/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items;

import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ReplacementRegistry1_7_6_10to1_8;
import de.gerrygames.viarewind.utils.ChatUtil;
import de.gerrygames.viarewind.utils.Enchantments;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.viaversion.libs.opennbt.tag.builtin.ByteTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ShortTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class ItemRewriter {
    public static Item toClient(Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.getTag();
        if (tag == null) {
            tag = new CompoundTag("");
            item.setTag(tag);
        }
        CompoundTag viaVersionTag = new CompoundTag("ViaRewind1_7_6_10to1_8");
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
                Tag ench = (Tag)iterator.next();
                short id = (Short)((Tag)((CompoundTag)ench).get("id")).getValue();
                short lvl = (Short)((Tag)((CompoundTag)ench).get("lvl")).getValue();
                if (id != 8) continue;
                String s = "\u00a7r\u00a77Depth Strider ";
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
        if (item.getIdentifier() == 387 && tag.contains("pages")) {
            ListTag pages = (ListTag)tag.get("pages");
            ListTag oldPages = new ListTag("pages", StringTag.class);
            viaVersionTag.put(oldPages);
            for (int i = 0; i < pages.size(); ++i) {
                StringTag page = (StringTag)pages.get(i);
                String value = page.getValue();
                oldPages.add(new StringTag(page.getName(), value));
                value = ChatUtil.jsonToLegacy(value);
                page.setValue(value);
            }
        }
        ReplacementRegistry1_7_6_10to1_8.replace(item);
        if (viaVersionTag.size() == 2 && ((Short)((Tag)viaVersionTag.get("id")).getValue()).shortValue() == item.getIdentifier() && ((Short)((Tag)viaVersionTag.get("data")).getValue()).shortValue() == item.getData()) {
            item.getTag().remove("ViaRewind1_7_6_10to1_8");
            if (item.getTag().isEmpty()) {
                item.setTag(null);
            }
        }
        return item;
    }

    public static Item toServer(Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.getTag();
        if (tag == null || !item.getTag().contains("ViaRewind1_7_6_10to1_8")) {
            return item;
        }
        CompoundTag viaVersionTag = (CompoundTag)tag.remove("ViaRewind1_7_6_10to1_8");
        item.setIdentifier(((Short)((Tag)viaVersionTag.get("id")).getValue()).shortValue());
        item.setData((Short)((Tag)viaVersionTag.get("data")).getValue());
        if (viaVersionTag.contains("noDisplay")) {
            tag.remove("display");
        }
        if (viaVersionTag.contains("displayName")) {
            StringTag name;
            CompoundTag display = (CompoundTag)tag.get("display");
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
        if (item.getIdentifier() == 387) {
            ListTag oldPages = (ListTag)viaVersionTag.get("pages");
            tag.remove("pages");
            tag.put(oldPages);
        }
        return item;
    }
}

