/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.api.rewriters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nl.matsv.viabackwards.api.rewriters.EnchantmentRewriter;
import us.myles.viaversion.libs.opennbt.tag.builtin.ByteTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ShortTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class LegacyEnchantmentRewriter {
    private final Map<Short, String> enchantmentMappings = new HashMap<Short, String>();
    private final String nbtTagName;
    private Set<Short> hideLevelForEnchants;

    public LegacyEnchantmentRewriter(String nbtTagName) {
        this.nbtTagName = nbtTagName;
    }

    public void registerEnchantment(int id, String replacementLore) {
        this.enchantmentMappings.put((short)id, replacementLore);
    }

    public void rewriteEnchantmentsToClient(CompoundTag tag, boolean storedEnchant) {
        String key = storedEnchant ? "StoredEnchantments" : "ench";
        ListTag enchantments = (ListTag)tag.get(key);
        ListTag remappedEnchantments = new ListTag(this.nbtTagName + "|" + key, CompoundTag.class);
        ArrayList<Tag> lore = new ArrayList<Tag>();
        for (Tag enchantmentEntry : enchantments.clone()) {
            Short newId = (Short)((Tag)((CompoundTag)enchantmentEntry).get("id")).getValue();
            String enchantmentName = this.enchantmentMappings.get(newId);
            if (enchantmentName == null) continue;
            enchantments.remove(enchantmentEntry);
            Number level = (Number)((Tag)((CompoundTag)enchantmentEntry).get("lvl")).getValue();
            if (this.hideLevelForEnchants != null && this.hideLevelForEnchants.contains(newId)) {
                lore.add(new StringTag("", enchantmentName));
            } else {
                lore.add(new StringTag("", enchantmentName + " " + EnchantmentRewriter.getRomanNumber(level.shortValue())));
            }
            remappedEnchantments.add(enchantmentEntry);
        }
        if (!lore.isEmpty()) {
            ListTag loreTag;
            if (!storedEnchant && enchantments.size() == 0) {
                CompoundTag dummyEnchantment = new CompoundTag("");
                dummyEnchantment.put(new ShortTag("id", 0));
                dummyEnchantment.put(new ShortTag("lvl", 0));
                enchantments.add(dummyEnchantment);
                tag.put(new ByteTag(this.nbtTagName + "|dummyEnchant"));
                IntTag hideFlags = (IntTag)tag.get("HideFlags");
                if (hideFlags == null) {
                    hideFlags = new IntTag("HideFlags");
                } else {
                    tag.put(new IntTag(this.nbtTagName + "|oldHideFlags", hideFlags.getValue()));
                }
                int flags = hideFlags.getValue() | 1;
                hideFlags.setValue(flags);
                tag.put(hideFlags);
            }
            tag.put(remappedEnchantments);
            CompoundTag display = (CompoundTag)tag.get("display");
            if (display == null) {
                display = new CompoundTag("display");
                tag.put(display);
            }
            if ((loreTag = (ListTag)display.get("Lore")) == null) {
                loreTag = new ListTag("Lore", StringTag.class);
                display.put(loreTag);
            }
            lore.addAll((Collection<Tag>)loreTag.getValue());
            loreTag.setValue(lore);
        }
    }

    public void rewriteEnchantmentsToServer(CompoundTag tag, boolean storedEnchant) {
        CompoundTag display;
        String key = storedEnchant ? "StoredEnchantments" : "ench";
        ListTag remappedEnchantments = (ListTag)tag.get(this.nbtTagName + "|" + key);
        ListTag enchantments = (ListTag)tag.get(key);
        if (enchantments == null) {
            enchantments = new ListTag(key, CompoundTag.class);
        }
        if (!storedEnchant && tag.remove(this.nbtTagName + "|dummyEnchant") != null) {
            for (Tag enchantment : enchantments.clone()) {
                Short id = (Short)((Tag)((CompoundTag)enchantment).get("id")).getValue();
                Short level = (Short)((Tag)((CompoundTag)enchantment).get("lvl")).getValue();
                if (id != 0 || level != 0) continue;
                enchantments.remove(enchantment);
            }
            IntTag hideFlags = (IntTag)tag.remove(this.nbtTagName + "|oldHideFlags");
            if (hideFlags != null) {
                tag.put(new IntTag("HideFlags", hideFlags.getValue()));
            } else {
                tag.remove("HideFlags");
            }
        }
        ListTag lore = (display = (CompoundTag)tag.get("display")) != null ? (ListTag)display.get("Lore") : null;
        for (Tag enchantment : remappedEnchantments.clone()) {
            enchantments.add(enchantment);
            if (lore == null || lore.size() == 0) continue;
            lore.remove((Tag)lore.get(0));
        }
        if (lore != null && lore.size() == 0) {
            display.remove("Lore");
            if (display.isEmpty()) {
                tag.remove("display");
            }
        }
        tag.put(enchantments);
        tag.remove(remappedEnchantments.getName());
    }

    public void setHideLevelForEnchants(int ... enchants) {
        this.hideLevelForEnchants = new HashSet<Short>();
        for (int enchant : enchants) {
            this.hideLevelForEnchants.add((short)enchant);
        }
    }
}

