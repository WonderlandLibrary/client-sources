/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.api.rewriters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ChatRewriter;
import us.myles.viaversion.libs.opennbt.tag.builtin.ByteTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ShortTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class EnchantmentRewriter {
    private final Map<String, String> enchantmentMappings = new HashMap<String, String>();
    private final String nbtTagName;
    private final boolean jsonFormat;

    public EnchantmentRewriter(String nbtTagName, boolean jsonFormat) {
        this.nbtTagName = nbtTagName;
        this.jsonFormat = jsonFormat;
    }

    public EnchantmentRewriter(String nbtTagName) {
        this(nbtTagName, true);
    }

    public void registerEnchantment(String key, String replacementLore) {
        this.enchantmentMappings.put(key, replacementLore);
    }

    public void handleToClient(Item item) {
        CompoundTag tag = item.getTag();
        if (tag == null) {
            return;
        }
        if (tag.get("Enchantments") instanceof ListTag) {
            this.rewriteEnchantmentsToClient(tag, false);
        }
        if (tag.get("StoredEnchantments") instanceof ListTag) {
            this.rewriteEnchantmentsToClient(tag, true);
        }
    }

    public void handleToServer(Item item) {
        CompoundTag tag = item.getTag();
        if (tag == null) {
            return;
        }
        if (tag.contains(this.nbtTagName + "|Enchantments")) {
            this.rewriteEnchantmentsToServer(tag, false);
        }
        if (tag.contains(this.nbtTagName + "|StoredEnchantments")) {
            this.rewriteEnchantmentsToServer(tag, true);
        }
    }

    public void rewriteEnchantmentsToClient(CompoundTag tag, boolean storedEnchant) {
        String key = storedEnchant ? "StoredEnchantments" : "Enchantments";
        ListTag enchantments = (ListTag)tag.get(key);
        ListTag remappedEnchantments = new ListTag(this.nbtTagName + "|" + key, CompoundTag.class);
        ArrayList<Tag> lore = new ArrayList<Tag>();
        for (Tag enchantmentEntry : enchantments.clone()) {
            String newId = (String)((Tag)((CompoundTag)enchantmentEntry).get("id")).getValue();
            String enchantmentName = this.enchantmentMappings.get(newId);
            if (enchantmentName == null) continue;
            enchantments.remove(enchantmentEntry);
            Number level = (Number)((Tag)((CompoundTag)enchantmentEntry).get("lvl")).getValue();
            String loreValue = enchantmentName + " " + EnchantmentRewriter.getRomanNumber(level.intValue());
            if (this.jsonFormat) {
                loreValue = ChatRewriter.legacyTextToJson(loreValue).toString();
            }
            lore.add(new StringTag("", loreValue));
            remappedEnchantments.add(enchantmentEntry);
        }
        if (!lore.isEmpty()) {
            ListTag loreTag;
            if (!storedEnchant && enchantments.size() == 0) {
                CompoundTag dummyEnchantment = new CompoundTag("");
                dummyEnchantment.put(new StringTag("id", ""));
                dummyEnchantment.put(new ShortTag("lvl", 0));
                enchantments.add(dummyEnchantment);
                tag.put(new ByteTag(this.nbtTagName + "|dummyEnchant"));
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
        String key = storedEnchant ? "StoredEnchantments" : "Enchantments";
        ListTag remappedEnchantments = (ListTag)tag.get(this.nbtTagName + "|" + key);
        ListTag enchantments = (ListTag)tag.get(key);
        if (enchantments == null) {
            enchantments = new ListTag(key, CompoundTag.class);
        }
        if (!storedEnchant && tag.remove(this.nbtTagName + "|dummyEnchant") != null) {
            for (Tag enchantment : enchantments.clone()) {
                String id = (String)((Tag)((CompoundTag)enchantment).get("id")).getValue();
                if (!id.isEmpty()) continue;
                enchantments.remove(enchantment);
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

    public static String getRomanNumber(int number) {
        switch (number) {
            case 1: {
                return "I";
            }
            case 2: {
                return "II";
            }
            case 3: {
                return "III";
            }
            case 4: {
                return "IV";
            }
            case 5: {
                return "V";
            }
            case 6: {
                return "VI";
            }
            case 7: {
                return "VII";
            }
            case 8: {
                return "VIII";
            }
            case 9: {
                return "IX";
            }
            case 10: {
                return "X";
            }
        }
        return Integer.toString(number);
    }
}

