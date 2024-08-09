/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;

public class PlayerPackets1_11 {
    private static final ValueTransformer<Short, Float> TO_NEW_FLOAT = new ValueTransformer<Short, Float>((Type)Type.FLOAT){

        @Override
        public Float transform(PacketWrapper packetWrapper, Short s) throws Exception {
            return Float.valueOf((float)s.shortValue() / 16.0f);
        }

        @Override
        public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
            return this.transform(packetWrapper, (Short)object);
        }
    };

    public void register(Protocol1_10To1_11 protocol1_10To1_11) {
        protocol1_10To1_11.registerClientbound(ClientboundPackets1_9_3.TITLE, new PacketHandlers(this){
            final PlayerPackets1_11 this$0;
            {
                this.this$0 = playerPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == 2) {
                    JsonElement jsonElement = packetWrapper.read(Type.COMPONENT);
                    packetWrapper.clearPacket();
                    packetWrapper.setPacketType(ClientboundPackets1_9_3.CHAT_MESSAGE);
                    String string = LegacyComponentSerializer.legacySection().serialize((Component)GsonComponentSerializer.gson().deserialize(jsonElement.toString()));
                    jsonElement = new JsonObject();
                    jsonElement.getAsJsonObject().addProperty("text", string);
                    packetWrapper.write(Type.COMPONENT, jsonElement);
                    packetWrapper.write(Type.BYTE, (byte)2);
                } else if (n > 2) {
                    packetWrapper.set(Type.VAR_INT, 0, n - 1);
                }
            }
        });
        protocol1_10To1_11.registerClientbound(ClientboundPackets1_9_3.COLLECT_ITEM, new PacketHandlers(this){
            final PlayerPackets1_11 this$0;
            {
                this.this$0 = playerPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.read(Type.VAR_INT);
            }
        });
        protocol1_10To1_11.registerServerbound(ServerboundPackets1_9_3.PLAYER_BLOCK_PLACEMENT, new PacketHandlers(this){
            final PlayerPackets1_11 this$0;
            {
                this.this$0 = playerPackets1_11;
            }

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.UNSIGNED_BYTE, PlayerPackets1_11.access$000());
                this.map(Type.UNSIGNED_BYTE, PlayerPackets1_11.access$000());
                this.map(Type.UNSIGNED_BYTE, PlayerPackets1_11.access$000());
            }
        });
    }

    static ValueTransformer access$000() {
        return TO_NEW_FLOAT;
    }
}

