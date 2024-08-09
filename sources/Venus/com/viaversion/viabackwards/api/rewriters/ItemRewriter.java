/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.MappedItem;
import com.viaversion.viabackwards.api.rewriters.ItemRewriterBase;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.Iterator;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ItemRewriter<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends BackwardsProtocol<C, ?, ?, S>>
extends ItemRewriterBase<C, S, T> {
    public ItemRewriter(T t) {
        super(t, true);
    }

    @Override
    public @Nullable Item handleItemToClient(@Nullable Item item) {
        Object object;
        CompoundTag compoundTag;
        if (item == null) {
            return null;
        }
        CompoundTag compoundTag2 = compoundTag = item.tag() != null ? (CompoundTag)item.tag().get("display") : null;
        if (((BackwardsProtocol)this.protocol).getTranslatableRewriter() != null && compoundTag != null) {
            Object object2;
            StringTag stringTag;
            object = compoundTag.get("Name");
            if (object instanceof StringTag) {
                stringTag = (StringTag)object;
                object2 = ((BackwardsProtocol)this.protocol).getTranslatableRewriter().processText(stringTag.getValue()).toString();
                if (!((String)object2).equals(((Tag)object).getValue())) {
                    this.saveStringTag(compoundTag, stringTag, "Name");
                }
                stringTag.setValue((String)object2);
            }
            if ((stringTag = compoundTag.get("Lore")) instanceof ListTag) {
                object2 = (ListTag)((Object)stringTag);
                boolean bl = false;
                Iterator<Tag> iterator2 = ((ListTag)object2).iterator();
                while (iterator2.hasNext()) {
                    Tag tag = iterator2.next();
                    if (!(tag instanceof StringTag)) continue;
                    StringTag stringTag2 = (StringTag)tag;
                    String string = ((BackwardsProtocol)this.protocol).getTranslatableRewriter().processText(stringTag2.getValue()).toString();
                    if (!bl && !string.equals(stringTag2.getValue())) {
                        bl = true;
                        this.saveListTag(compoundTag, (ListTag)object2, "Lore");
                    }
                    stringTag2.setValue(string);
                }
            }
        }
        if ((object = ((BackwardsProtocol)this.protocol).getMappingData().getMappedItem(item.identifier())) == null) {
            return super.handleItemToClient(item);
        }
        if (item.tag() == null) {
            item.setTag(new CompoundTag());
        }
        item.tag().put(this.nbtTagName + "|id", new IntTag(item.identifier()));
        item.setIdentifier(((MappedItem)object).getId());
        if (((MappedItem)object).customModelData() != null && !item.tag().contains("CustomModelData")) {
            item.tag().put("CustomModelData", new IntTag(((MappedItem)object).customModelData()));
        }
        if (compoundTag == null) {
            compoundTag = new CompoundTag();
            item.tag().put("display", compoundTag);
        }
        if (!compoundTag.contains("Name")) {
            compoundTag.put("Name", new StringTag(((MappedItem)object).getJsonName()));
            compoundTag.put(this.nbtTagName + "|customName", new ByteTag());
        }
        return item;
    }

    @Override
    public @Nullable Item handleItemToServer(@Nullable Item item) {
        IntTag intTag;
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        if (item.tag() != null && (intTag = (IntTag)item.tag().remove(this.nbtTagName + "|id")) != null) {
            item.setIdentifier(intTag.asInt());
        }
        return item;
    }

    @Override
    public void registerAdvancements(C c, Type<Item> type) {
        ((BackwardsProtocol)this.protocol).registerClientbound(c, new PacketHandlers(this, type){
            final Type val$type;
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
                this.val$type = type;
            }

            @Override
            public void register() {
                this.handler(arg_0 -> this.lambda$register$0(this.val$type, arg_0));
            }

            private void lambda$register$0(Type type, PacketWrapper packetWrapper) throws Exception {
                packetWrapper.passthrough(Type.BOOLEAN);
                int n = packetWrapper.passthrough(Type.VAR_INT);
                for (int i = 0; i < n; ++i) {
                    packetWrapper.passthrough(Type.STRING);
                    if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                        packetWrapper.passthrough(Type.STRING);
                    }
                    if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                        JsonElement jsonElement = packetWrapper.passthrough(Type.COMPONENT);
                        JsonElement jsonElement2 = packetWrapper.passthrough(Type.COMPONENT);
                        TranslatableRewriter translatableRewriter = ((BackwardsProtocol)ItemRewriter.access$000(this.this$0)).getTranslatableRewriter();
                        if (translatableRewriter != null) {
                            translatableRewriter.processText(jsonElement);
                            translatableRewriter.processText(jsonElement2);
                        }
                        this.this$0.handleItemToClient((Item)packetWrapper.passthrough(type));
                        packetWrapper.passthrough(Type.VAR_INT);
                        int n2 = packetWrapper.passthrough(Type.INT);
                        if ((n2 & 1) != 0) {
                            packetWrapper.passthrough(Type.STRING);
                        }
                        packetWrapper.passthrough(Type.FLOAT);
                        packetWrapper.passthrough(Type.FLOAT);
                    }
                    packetWrapper.passthrough(Type.STRING_ARRAY);
                    int n3 = packetWrapper.passthrough(Type.VAR_INT);
                    for (int j = 0; j < n3; ++j) {
                        packetWrapper.passthrough(Type.STRING_ARRAY);
                    }
                }
            }
        });
    }

    static Protocol access$000(ItemRewriter itemRewriter) {
        return itemRewriter.protocol;
    }
}

