/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.type.Type;
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

    protected ItemRewriterBase(T protocol, boolean jsonNameFormat) {
        this(protocol, Type.FLAT_VAR_INT_ITEM, Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, jsonNameFormat);
    }

    public ItemRewriterBase(T protocol, Type<Item> itemType, Type<Item[]> itemArrayType, boolean jsonNameFormat) {
        super(protocol, itemType, itemArrayType);
        this.jsonNameFormat = jsonNameFormat;
        this.nbtTagName = "VB|" + protocol.getClass().getSimpleName();
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

    protected boolean hasBackupTag(CompoundTag displayTag, String tagName) {
        return displayTag.contains(this.nbtTagName + "|o" + tagName);
    }

    protected void saveStringTag(CompoundTag displayTag, StringTag original, String name) {
        String backupName = this.nbtTagName + "|o" + name;
        if (!displayTag.contains(backupName)) {
            displayTag.put(backupName, new StringTag(original.getValue()));
        }
    }

    protected void saveListTag(CompoundTag displayTag, ListTag original, String name) {
        String backupName = this.nbtTagName + "|o" + name;
        if (!displayTag.contains(backupName)) {
            ListTag listTag = new ListTag();
            Iterator iterator = original.getValue().iterator();
            while (iterator.hasNext()) {
                Tag tag = (Tag)iterator.next();
                listTag.add(tag.clone());
            }
            displayTag.put(backupName, listTag);
        }
    }

    protected void restoreDisplayTag(Item item) {
        if (item.tag() == null) {
            return;
        }
        CompoundTag display = (CompoundTag)item.tag().get("display");
        if (display != null) {
            if (display.remove(this.nbtTagName + "|customName") != null) {
                display.remove("Name");
            } else {
                this.restoreStringTag(display, "Name");
            }
            this.restoreListTag(display, "Lore");
        }
    }

    protected void restoreStringTag(CompoundTag tag, String tagName) {
        StringTag original = (StringTag)tag.remove(this.nbtTagName + "|o" + tagName);
        if (original != null) {
            tag.put(tagName, new StringTag(original.getValue()));
        }
    }

    protected void restoreListTag(CompoundTag tag, String tagName) {
        ListTag original = (ListTag)tag.remove(this.nbtTagName + "|o" + tagName);
        if (original != null) {
            tag.put(tagName, new ListTag((List<Tag>)original.getValue()));
        }
    }

    public String getNbtTagName() {
        return this.nbtTagName;
    }
}

