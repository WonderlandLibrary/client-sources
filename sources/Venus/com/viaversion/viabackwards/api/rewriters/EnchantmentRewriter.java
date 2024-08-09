/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EnchantmentRewriter {
    private final Map<String, String> enchantmentMappings = new HashMap<String, String>();
    private final ItemRewriter<?, ?, ?> itemRewriter;
    private final boolean jsonFormat;

    public EnchantmentRewriter(ItemRewriter<?, ?, ?> itemRewriter, boolean bl) {
        this.itemRewriter = itemRewriter;
        this.jsonFormat = bl;
    }

    public EnchantmentRewriter(ItemRewriter<?, ?, ?> itemRewriter) {
        this(itemRewriter, true);
    }

    public void registerEnchantment(String string, String string2) {
        this.enchantmentMappings.put(string, string2);
    }

    public void handleToClient(Item item) {
        CompoundTag compoundTag = item.tag();
        if (compoundTag == null) {
            return;
        }
        if (compoundTag.get("Enchantments") instanceof ListTag) {
            this.rewriteEnchantmentsToClient(compoundTag, true);
        }
        if (compoundTag.get("StoredEnchantments") instanceof ListTag) {
            this.rewriteEnchantmentsToClient(compoundTag, false);
        }
    }

    public void handleToServer(Item item) {
        CompoundTag compoundTag = item.tag();
        if (compoundTag == null) {
            return;
        }
        if (compoundTag.contains(this.itemRewriter.getNbtTagName() + "|Enchantments")) {
            this.rewriteEnchantmentsToServer(compoundTag, true);
        }
        if (compoundTag.contains(this.itemRewriter.getNbtTagName() + "|StoredEnchantments")) {
            this.rewriteEnchantmentsToServer(compoundTag, false);
        }
    }

    public void rewriteEnchantmentsToClient(CompoundTag compoundTag, boolean bl) {
        Object object;
        CompoundTag compoundTag2;
        String string = bl ? "StoredEnchantments" : "Enchantments";
        ListTag listTag = (ListTag)compoundTag.get(string);
        ArrayList<Tag> arrayList = new ArrayList<Tag>();
        boolean bl2 = false;
        Iterator<Tag> iterator2 = listTag.iterator();
        while (iterator2.hasNext()) {
            String string2;
            String string3;
            compoundTag2 = (CompoundTag)iterator2.next();
            object = compoundTag2.get("id");
            if (!(object instanceof StringTag) || (string3 = this.enchantmentMappings.get(string2 = ((StringTag)object).getValue())) == null) continue;
            if (!bl2) {
                this.itemRewriter.saveListTag(compoundTag, listTag, string);
                bl2 = true;
            }
            iterator2.remove();
            int n = ((NumberTag)compoundTag2.get("lvl")).asInt();
            String string4 = string3 + " " + EnchantmentRewriter.getRomanNumber(n);
            if (this.jsonFormat) {
                string4 = ChatRewriter.legacyTextToJsonString(string4);
            }
            arrayList.add(new StringTag(string4));
        }
        if (!arrayList.isEmpty()) {
            if (!bl && listTag.size() == 0) {
                compoundTag2 = new CompoundTag();
                compoundTag2.put("id", new StringTag());
                compoundTag2.put("lvl", new ShortTag(0));
                listTag.add(compoundTag2);
            }
            if ((compoundTag2 = (CompoundTag)compoundTag.get("display")) == null) {
                compoundTag2 = new CompoundTag();
                compoundTag.put("display", compoundTag2);
            }
            if ((object = (ListTag)compoundTag2.get("Lore")) == null) {
                object = new ListTag(StringTag.class);
                compoundTag2.put("Lore", object);
            } else {
                this.itemRewriter.saveListTag(compoundTag2, (ListTag)object, "Lore");
            }
            arrayList.addAll((Collection<Tag>)((ListTag)object).getValue());
            ((ListTag)object).setValue(arrayList);
        }
    }

    public void rewriteEnchantmentsToServer(CompoundTag compoundTag, boolean bl) {
        String string = bl ? "StoredEnchantments" : "Enchantments";
        this.itemRewriter.restoreListTag(compoundTag, string);
    }

    public static String getRomanNumber(int n) {
        switch (n) {
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
        return Integer.toString(n);
    }
}

