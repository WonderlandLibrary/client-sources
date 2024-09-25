/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package nl.matsv.viabackwards.api.rewriters;

import java.util.List;
import nl.matsv.viabackwards.api.BackwardsProtocol;
import nl.matsv.viabackwards.api.rewriters.Rewriter;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.viaversion.libs.opennbt.conversion.builtin.CompoundTagConverter;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public abstract class ItemRewriterBase<T extends BackwardsProtocol>
extends Rewriter<T> {
    protected static final CompoundTagConverter CONVERTER = new CompoundTagConverter();
    protected final String nbtTagName;
    protected final boolean jsonNameFormat;

    protected ItemRewriterBase(T protocol, boolean jsonNameFormat) {
        super(protocol);
        this.jsonNameFormat = jsonNameFormat;
        this.nbtTagName = "VB|" + protocol.getClass().getSimpleName();
    }

    @Nullable
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        if (this.protocol.getMappingData() != null) {
            item.setIdentifier(this.protocol.getMappingData().getNewItemId(item.getIdentifier()));
        }
        return item;
    }

    @Nullable
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        if (this.protocol.getMappingData() != null) {
            item.setIdentifier(this.protocol.getMappingData().getOldItemId(item.getIdentifier()));
        }
        this.restoreDisplayTag(item);
        return item;
    }

    protected void saveNameTag(CompoundTag displayTag, StringTag original) {
        displayTag.put(new StringTag(this.nbtTagName + "|o" + original.getName(), original.getValue()));
    }

    protected void saveLoreTag(CompoundTag displayTag, ListTag original) {
        displayTag.put(new ListTag(this.nbtTagName + "|o" + original.getName(), (List<Tag>)original.getValue()));
    }

    protected void restoreDisplayTag(Item item) {
        if (item.getTag() == null) {
            return;
        }
        CompoundTag display = (CompoundTag)item.getTag().get("display");
        if (display != null) {
            if (display.remove(this.nbtTagName + "|customName") != null) {
                display.remove("Name");
            } else {
                this.restoreDisplayTag(display, "Name");
            }
            this.restoreDisplayTag(display, "Lore");
        }
    }

    protected void restoreDisplayTag(CompoundTag displayTag, String tagName) {
        Object original = displayTag.remove(this.nbtTagName + "|o" + tagName);
        if (original != null) {
            displayTag.put(original);
        }
    }
}

