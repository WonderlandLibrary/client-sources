/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import java.util.Iterator;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class ItemRewriterBase<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends BackwardsProtocol<C, ?, ?, S>>
extends ItemRewriter<C, S, T> {
    protected final String nbtTagName;
    protected final boolean jsonNameFormat;

    protected ItemRewriterBase(T t, boolean bl) {
        super(t);
        this.jsonNameFormat = bl;
        this.nbtTagName = "VB|" + t.getClass().getSimpleName();
    }

    @Override
    public @Nullable Item handleItemToServer(@Nullable Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        this.restoreDisplayTag(item);
        return item;
    }

    protected boolean hasBackupTag(CompoundTag compoundTag, String string) {
        return compoundTag.contains(this.nbtTagName + "|o" + string);
    }

    protected void saveStringTag(CompoundTag compoundTag, StringTag stringTag, String string) {
        String string2 = this.nbtTagName + "|o" + string;
        if (!compoundTag.contains(string2)) {
            compoundTag.put(string2, new StringTag(stringTag.getValue()));
        }
    }

    protected void saveListTag(CompoundTag compoundTag, ListTag listTag, String string) {
        String string2 = this.nbtTagName + "|o" + string;
        if (!compoundTag.contains(string2)) {
            ListTag listTag2 = new ListTag();
            Iterator iterator2 = listTag.getValue().iterator();
            while (iterator2.hasNext()) {
                Tag tag = (Tag)iterator2.next();
                listTag2.add(tag.clone());
            }
            compoundTag.put(string2, listTag2);
        }
    }

    protected void restoreDisplayTag(Item item) {
        if (item.tag() == null) {
            return;
        }
        CompoundTag compoundTag = (CompoundTag)item.tag().get("display");
        if (compoundTag != null) {
            if (compoundTag.remove(this.nbtTagName + "|customName") != null) {
                compoundTag.remove("Name");
            } else {
                this.restoreStringTag(compoundTag, "Name");
            }
            this.restoreListTag(compoundTag, "Lore");
        }
    }

    protected void restoreStringTag(CompoundTag compoundTag, String string) {
        StringTag stringTag = (StringTag)compoundTag.remove(this.nbtTagName + "|o" + string);
        if (stringTag != null) {
            compoundTag.put(string, new StringTag(stringTag.getValue()));
        }
    }

    protected void restoreListTag(CompoundTag compoundTag, String string) {
        ListTag listTag = (ListTag)compoundTag.remove(this.nbtTagName + "|o" + string);
        if (listTag != null) {
            compoundTag.put(string, new ListTag((List<Tag>)listTag.getValue()));
        }
    }

    public String getNbtTagName() {
        return this.nbtTagName;
    }
}

