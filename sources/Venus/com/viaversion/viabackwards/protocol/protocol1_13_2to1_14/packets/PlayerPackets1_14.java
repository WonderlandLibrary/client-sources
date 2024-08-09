/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.DifficultyStorage;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;

public class PlayerPackets1_14
extends RewriterBase<Protocol1_13_2To1_14> {
    public PlayerPackets1_14(Protocol1_13_2To1_14 protocol1_13_2To1_14) {
        super(protocol1_13_2To1_14);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SERVER_DIFFICULTY, new PacketHandlers(this){
            final PlayerPackets1_14 this$0;
            {
                this.this$0 = playerPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map((Type)Type.BOOLEAN, Type.NOTHING);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte by = packetWrapper.get(Type.UNSIGNED_BYTE, 0).byteValue();
                packetWrapper.user().get(DifficultyStorage.class).setDifficulty(by);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_SIGN_EDITOR, new PacketHandlers(this){
            final PlayerPackets1_14 this$0;
            {
                this.this$0 = playerPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.QUERY_BLOCK_NBT, new PacketHandlers(this){
            final PlayerPackets1_14 this$0;
            {
                this.this$0 = playerPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.PLAYER_DIGGING, new PacketHandlers(this){
            final PlayerPackets1_14 this$0;
            {
                this.this$0 = playerPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.RECIPE_BOOK_DATA, new PacketHandlers(this){
            final PlayerPackets1_14 this$0;
            {
                this.this$0 = playerPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == 0) {
                    packetWrapper.passthrough(Type.STRING);
                } else if (n == 1) {
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.write(Type.BOOLEAN, false);
                    packetWrapper.write(Type.BOOLEAN, false);
                    packetWrapper.write(Type.BOOLEAN, false);
                    packetWrapper.write(Type.BOOLEAN, false);
                }
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK, new PacketHandlers(this){
            final PlayerPackets1_14 this$0;
            {
                this.this$0 = playerPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.UPDATE_STRUCTURE_BLOCK, new PacketHandlers(this){
            final PlayerPackets1_14 this$0;
            {
                this.this$0 = playerPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.UPDATE_SIGN, new PacketHandlers(this){
            final PlayerPackets1_14 this$0;
            {
                this.this$0 = playerPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.PLAYER_BLOCK_PLACEMENT, PlayerPackets1_14::lambda$registerPackets$0);
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        Position position = packetWrapper.read(Type.POSITION);
        int n = packetWrapper.read(Type.VAR_INT);
        int n2 = packetWrapper.read(Type.VAR_INT);
        float f = packetWrapper.read(Type.FLOAT).floatValue();
        float f2 = packetWrapper.read(Type.FLOAT).floatValue();
        float f3 = packetWrapper.read(Type.FLOAT).floatValue();
        packetWrapper.write(Type.VAR_INT, n2);
        packetWrapper.write(Type.POSITION1_14, position);
        packetWrapper.write(Type.VAR_INT, n);
        packetWrapper.write(Type.FLOAT, Float.valueOf(f));
        packetWrapper.write(Type.FLOAT, Float.valueOf(f2));
        packetWrapper.write(Type.FLOAT, Float.valueOf(f3));
        packetWrapper.write(Type.BOOLEAN, false);
    }
}

