/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_18to1_18_2;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.data.CommandRewriter1_18_2;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;

public final class Protocol1_18To1_18_2
extends BackwardsProtocol<ClientboundPackets1_18, ClientboundPackets1_18, ServerboundPackets1_17, ServerboundPackets1_17> {
    public Protocol1_18To1_18_2() {
        super(ClientboundPackets1_18.class, ClientboundPackets1_18.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }

    @Override
    protected void registerPackets() {
        new CommandRewriter1_18_2(this).registerDeclareCommands(ClientboundPackets1_18.DECLARE_COMMANDS);
        PacketHandler packetHandler = Protocol1_18To1_18_2::lambda$registerPackets$0;
        this.registerClientbound(ClientboundPackets1_18.ENTITY_EFFECT, new PacketHandlers(this, packetHandler){
            final PacketHandler val$entityEffectIdHandler;
            final Protocol1_18To1_18_2 this$0;
            {
                this.this$0 = protocol1_18To1_18_2;
                this.val$entityEffectIdHandler = packetHandler;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(this.val$entityEffectIdHandler);
            }
        });
        this.registerClientbound(ClientboundPackets1_18.REMOVE_ENTITY_EFFECT, new PacketHandlers(this, packetHandler){
            final PacketHandler val$entityEffectIdHandler;
            final Protocol1_18To1_18_2 this$0;
            {
                this.this$0 = protocol1_18To1_18_2;
                this.val$entityEffectIdHandler = packetHandler;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(this.val$entityEffectIdHandler);
            }
        });
        this.registerClientbound(ClientboundPackets1_18.JOIN_GAME, new PacketHandlers(this){
            final Protocol1_18To1_18_2 this$0;
            {
                this.this$0 = protocol1_18To1_18_2;
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
                    Protocol1_18To1_18_2.access$000(this.this$0, (CompoundTag)((CompoundTag)tag).get("element"));
                }
                Protocol1_18To1_18_2.access$000(this.this$0, packetWrapper.get(Type.NBT, 1));
            }
        });
        this.registerClientbound(ClientboundPackets1_18.RESPAWN, this::lambda$registerPackets$1);
    }

    private void removeTagPrefix(CompoundTag compoundTag) {
        Object t = compoundTag.get("infiniburn");
        if (t instanceof StringTag) {
            StringTag stringTag = (StringTag)t;
            stringTag.setValue(stringTag.getValue().substring(1));
        }
    }

    private void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        this.removeTagPrefix(packetWrapper.passthrough(Type.NBT));
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        if ((byte)n != n) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
                ViaBackwards.getPlatform().getLogger().warning("Cannot send entity effect id " + n + " to old client");
            }
            packetWrapper.cancel();
            return;
        }
        packetWrapper.write(Type.BYTE, (byte)n);
    }

    static void access$000(Protocol1_18To1_18_2 protocol1_18To1_18_2, CompoundTag compoundTag) {
        protocol1_18To1_18_2.removeTagPrefix(compoundTag);
    }
}

