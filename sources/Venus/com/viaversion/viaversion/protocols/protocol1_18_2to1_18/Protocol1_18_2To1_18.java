/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_18_2to1_18;

import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.rewriter.TagRewriter;

public final class Protocol1_18_2To1_18
extends AbstractProtocol<ClientboundPackets1_18, ClientboundPackets1_18, ServerboundPackets1_17, ServerboundPackets1_17> {
    public Protocol1_18_2To1_18() {
        super(ClientboundPackets1_18.class, ClientboundPackets1_18.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }

    @Override
    protected void registerPackets() {
        TagRewriter<ClientboundPackets1_18> tagRewriter = new TagRewriter<ClientboundPackets1_18>(this);
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:fall_damage_resetting");
        tagRewriter.registerGeneric(ClientboundPackets1_18.TAGS);
        this.registerClientbound(ClientboundPackets1_18.ENTITY_EFFECT, new PacketHandlers(this){
            final Protocol1_18_2To1_18 this$0;
            {
                this.this$0 = protocol1_18_2To1_18;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map((Type)Type.BYTE, Type.VAR_INT);
            }
        });
        this.registerClientbound(ClientboundPackets1_18.REMOVE_ENTITY_EFFECT, new PacketHandlers(this){
            final Protocol1_18_2To1_18 this$0;
            {
                this.this$0 = protocol1_18_2To1_18;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map((Type)Type.BYTE, Type.VAR_INT);
            }
        });
        this.registerClientbound(ClientboundPackets1_18.JOIN_GAME, new PacketHandlers(this){
            final Protocol1_18_2To1_18 this$0;
            {
                this.this$0 = protocol1_18_2To1_18;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.map(Type.NBT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("minecraft:dimension_type");
                ListTag listTag = (ListTag)compoundTag2.get("value");
                for (Tag tag : listTag) {
                    Protocol1_18_2To1_18.access$000(this.this$0, (CompoundTag)((CompoundTag)tag).get("element"));
                }
                Protocol1_18_2To1_18.access$000(this.this$0, packetWrapper.get(Type.NBT, 1));
            }
        });
        this.registerClientbound(ClientboundPackets1_18.RESPAWN, this::lambda$registerPackets$0);
    }

    private void addTagPrefix(CompoundTag compoundTag) {
        Object t = compoundTag.get("infiniburn");
        if (t instanceof StringTag) {
            StringTag stringTag = (StringTag)t;
            stringTag.setValue("#" + stringTag.getValue());
        }
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        this.addTagPrefix(packetWrapper.passthrough(Type.NBT));
    }

    static void access$000(Protocol1_18_2To1_18 protocol1_18_2To1_18, CompoundTag compoundTag) {
        protocol1_18_2To1_18.addTagPrefix(compoundTag);
    }
}

