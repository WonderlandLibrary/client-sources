/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LegacyEnchantmentRewriter {
    private final Map<Short, String> enchantmentMappings = new HashMap<Short, String>();
    private final String nbtTagName;
    private Set<Short> hideLevelForEnchants;

    public LegacyEnchantmentRewriter(String string) {
        this.nbtTagName = string;
    }

    public void registerEnchantment(int n, String string) {
        this.enchantmentMappings.put((short)n, string);
    }

    public void rewriteEnchantmentsToClient(CompoundTag compoundTag, boolean bl) {
        String string = bl ? "StoredEnchantments" : "ench";
        ListTag listTag = (ListTag)compoundTag.get(string);
        ListTag listTag2 = new ListTag(CompoundTag.class);
        ArrayList<Tag> arrayList = new ArrayList<Tag>();
        for (Tag tag : listTag.clone()) {
            short s;
            String string2;
            Object t = ((CompoundTag)tag).get("id");
            if (t == null || (string2 = this.enchantmentMappings.get(s = ((NumberTag)t).asShort())) == null) continue;
            listTag.remove(tag);
            short s2 = ((NumberTag)((CompoundTag)tag).get("lvl")).asShort();
            if (this.hideLevelForEnchants != null && this.hideLevelForEnchants.contains(s)) {
                arrayList.add(new StringTag(string2));
            } else {
                arrayList.add(new StringTag(string2 + " " + EnchantmentRewriter.getRomanNumber(s2)));
            }
            listTag2.add(tag);
        }
        if (!arrayList.isEmpty()) {
            Object object;
            Tag tag;
            if (!bl && listTag.size() == 0) {
                object = new CompoundTag();
                ((CompoundTag)object).put("id", new ShortTag(0));
                ((CompoundTag)object).put("lvl", new ShortTag(0));
                listTag.add((Tag)object);
                compoundTag.put(this.nbtTagName + "|dummyEnchant", new ByteTag());
                tag = (IntTag)compoundTag.get("HideFlags");
                if (tag == null) {
                    tag = new IntTag();
                } else {
                    compoundTag.put(this.nbtTagName + "|oldHideFlags", new IntTag(((IntTag)tag).asByte()));
                }
                int n = ((IntTag)tag).asByte() | 1;
                ((IntTag)tag).setValue(n);
                compoundTag.put("HideFlags", tag);
            }
            compoundTag.put(this.nbtTagName + "|" + string, listTag2);
            object = (CompoundTag)compoundTag.get("display");
            if (object == null) {
                object = new CompoundTag();
                compoundTag.put("display", object);
            }
            if ((tag = (ListTag)((CompoundTag)object).get("Lore")) == null) {
                tag = new ListTag(StringTag.class);
                ((CompoundTag)object).put("Lore", tag);
            }
            arrayList.addAll((Collection<Tag>)((ListTag)tag).getValue());
            ((ListTag)tag).setValue(arrayList);
        }
    }

    public void rewriteEnchantmentsToServer(CompoundTag compoundTag, boolean bl) {
        Object object;
        Tag tag2;
        String string = bl ? "StoredEnchantments" : "ench";
        ListTag listTag = (ListTag)compoundTag.remove(this.nbtTagName + "|" + string);
        ListTag listTag2 = (ListTag)compoundTag.get(string);
        if (listTag2 == null) {
            listTag2 = new ListTag(CompoundTag.class);
        }
        if (!bl && compoundTag.remove(this.nbtTagName + "|dummyEnchant") != null) {
            for (Tag tag2 : listTag2.clone()) {
                short s = ((NumberTag)((CompoundTag)tag2).get("id")).asShort();
                short s2 = ((NumberTag)((CompoundTag)tag2).get("lvl")).asShort();
                if (s != 0 || s2 != 0) continue;
                listTag2.remove(tag2);
            }
            object = (IntTag)compoundTag.remove(this.nbtTagName + "|oldHideFlags");
            if (object != null) {
                compoundTag.put("HideFlags", new IntTag(((IntTag)object).asByte()));
            } else {
                compoundTag.remove("HideFlags");
            }
        }
        tag2 = (object = (CompoundTag)compoundTag.get("display")) != null ? (ListTag)((CompoundTag)object).get("Lore") : null;
        for (Tag tag3 : listTag.clone()) {
            listTag2.add(tag3);
            if (tag2 == null || ((ListTag)tag2).size() == 0) continue;
            ((ListTag)tag2).remove((Tag)((ListTag)tag2).get(0));
        }
        if (tag2 != null && ((ListTag)tag2).size() == 0) {
            ((CompoundTag)object).remove("Lore");
            if (((CompoundTag)object).isEmpty()) {
                compoundTag.remove("display");
            }
        }
        compoundTag.put(string, listTag2);
    }

    public void setHideLevelForEnchants(int ... nArray) {
        this.hideLevelForEnchants = new HashSet<Short>();
        for (int n : nArray) {
            this.hideLevelForEnchants.add((short)n);
        }
    }
}

