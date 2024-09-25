/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package nl.matsv.viabackwards.api.rewriters;

import nl.matsv.viabackwards.api.BackwardsProtocol;
import nl.matsv.viabackwards.api.data.MappedItem;
import nl.matsv.viabackwards.api.rewriters.ItemRewriterBase;
import nl.matsv.viabackwards.api.rewriters.TranslatableRewriter;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.viaversion.libs.opennbt.tag.builtin.ByteTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public abstract class ItemRewriter<T extends BackwardsProtocol>
extends ItemRewriterBase<T> {
    private final TranslatableRewriter translatableRewriter;

    protected ItemRewriter(T protocol, @Nullable TranslatableRewriter translatableRewriter) {
        super(protocol, true);
        this.translatableRewriter = translatableRewriter;
    }

    @Override
    @Nullable
    public Item handleItemToClient(Item item) {
        MappedItem data;
        CompoundTag display;
        if (item == null) {
            return null;
        }
        CompoundTag compoundTag = display = item.getTag() != null ? (CompoundTag)item.getTag().get("display") : null;
        if (this.translatableRewriter != null && display != null) {
            ListTag lore;
            StringTag name = (StringTag)display.get("Name");
            if (name != null) {
                String newValue = this.translatableRewriter.processText(name.getValue()).toString();
                if (!newValue.equals(name.getValue())) {
                    this.saveNameTag(display, name);
                }
                name.setValue(newValue);
            }
            if ((lore = (ListTag)display.get("Lore")) != null) {
                ListTag original = null;
                boolean changed = false;
                for (Tag loreEntryTag : lore) {
                    if (!(loreEntryTag instanceof StringTag)) continue;
                    StringTag loreEntry = (StringTag)loreEntryTag;
                    String newValue = this.translatableRewriter.processText(loreEntry.getValue()).toString();
                    if (!changed && !newValue.equals(loreEntry.getValue())) {
                        changed = true;
                        original = lore.clone();
                    }
                    loreEntry.setValue(newValue);
                }
                if (changed) {
                    this.saveLoreTag(display, original);
                }
            }
        }
        if ((data = this.protocol.getMappingData().getMappedItem(item.getIdentifier())) == null) {
            return super.handleItemToClient(item);
        }
        if (item.getTag() == null) {
            item.setTag(new CompoundTag(""));
        }
        item.getTag().put(new IntTag(this.nbtTagName + "|id", item.getIdentifier()));
        item.setIdentifier(data.getId());
        if (display == null) {
            display = new CompoundTag("display");
            item.getTag().put(display);
        }
        if (!display.contains("Name")) {
            display.put(new StringTag("Name", data.getJsonName()));
            display.put(new ByteTag(this.nbtTagName + "|customName"));
        }
        return item;
    }

    @Override
    @Nullable
    public Item handleItemToServer(Item item) {
        IntTag originalId;
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        if (item.getTag() != null && (originalId = (IntTag)item.getTag().remove(this.nbtTagName + "|id")) != null) {
            item.setIdentifier(originalId.getValue());
        }
        return item;
    }
}

