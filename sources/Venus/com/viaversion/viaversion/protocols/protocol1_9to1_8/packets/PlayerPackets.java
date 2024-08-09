/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.PlayerMovementMapper;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.chat.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.chat.GameMode;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CompressionProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;

public class PlayerPackets {
    public static void register(Protocol1_9To1_8 protocol1_9To1_8) {
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.CHAT_MESSAGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.BYTE);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                try {
                    JsonObject jsonObject = (JsonObject)packetWrapper.get(Type.COMPONENT, 0);
                    ChatRewriter.toClient(jsonObject, packetWrapper.user());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.TAB_LIST, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.DISCONNECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.TITLE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == 0 || n == 1) {
                    Protocol1_9To1_8.FIX_JSON.write(packetWrapper, packetWrapper.read(Type.STRING));
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.PLAYER_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BYTE);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, 0);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.TEAMS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(6::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Object object;
                Object object2;
                byte by = packetWrapper.get(Type.BYTE, 0);
                if (by == 0 || by == 2) {
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.BYTE);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.write(Type.STRING, Via.getConfig().isPreventCollision() ? "never" : "");
                    packetWrapper.passthrough(Type.BYTE);
                }
                if (by == 0 || by == 3 || by == 4) {
                    object2 = packetWrapper.passthrough(Type.STRING_ARRAY);
                    object = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                    String string = packetWrapper.user().getProtocolInfo().getUsername();
                    String string2 = packetWrapper.get(Type.STRING, 0);
                    for (Object object3 : object2) {
                        if (!((EntityTracker1_9)object).isAutoTeam() || !((String)object3).equalsIgnoreCase(string)) continue;
                        if (by == 4) {
                            packetWrapper.send(Protocol1_9To1_8.class);
                            packetWrapper.cancel();
                            ((EntityTracker1_9)object).sendTeamPacket(true, false);
                            ((EntityTracker1_9)object).setCurrentTeam("viaversion");
                            continue;
                        }
                        ((EntityTracker1_9)object).sendTeamPacket(false, false);
                        ((EntityTracker1_9)object).setCurrentTeam(string2);
                    }
                }
                if (by == 1) {
                    object2 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                    object = packetWrapper.get(Type.STRING, 0);
                    if (((EntityTracker1_9)object2).isAutoTeam() && ((String)object).equals(((EntityTracker1_9)object2).getCurrentTeam())) {
                        packetWrapper.send(Protocol1_9To1_8.class);
                        packetWrapper.cancel();
                        ((EntityTracker1_9)object2).sendTeamPacket(true, false);
                        ((EntityTracker1_9)object2).setCurrentTeam("viaversion");
                    }
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.JOIN_GAME, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(7::lambda$register$0);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.BOOLEAN);
                this.handler(7::lambda$register$1);
                this.handler(7::lambda$register$2);
                this.handler(7::lambda$register$3);
                this.handler(7::lambda$register$4);
            }

            private static void lambda$register$4(PacketWrapper packetWrapper) throws Exception {
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                if (Via.getConfig().isAutoTeam()) {
                    entityTracker1_9.setAutoTeam(false);
                    packetWrapper.send(Protocol1_9To1_8.class);
                    packetWrapper.cancel();
                    entityTracker1_9.sendTeamPacket(true, false);
                    entityTracker1_9.setCurrentTeam("viaversion");
                } else {
                    entityTracker1_9.setAutoTeam(true);
                }
            }

            private static void lambda$register$3(PacketWrapper packetWrapper) throws Exception {
                CommandBlockProvider commandBlockProvider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                commandBlockProvider.sendPermission(packetWrapper.user());
            }

            private static void lambda$register$2(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                byte by = packetWrapper.get(Type.BYTE, 0);
                clientWorld.setEnvironment(by);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.setGameMode(GameMode.getById(packetWrapper.get(Type.UNSIGNED_BYTE, 0).shortValue()));
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.addEntity(n, Entity1_10Types.EntityType.PLAYER);
                entityTracker1_9.setClientEntityId(n);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.PLAYER_INFO, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(8::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                int n2 = packetWrapper.get(Type.VAR_INT, 1);
                for (int i = 0; i < n2; ++i) {
                    packetWrapper.passthrough(Type.UUID);
                    if (n == 0) {
                        packetWrapper.passthrough(Type.STRING);
                        int n3 = packetWrapper.passthrough(Type.VAR_INT);
                        for (int j = 0; j < n3; ++j) {
                            packetWrapper.passthrough(Type.STRING);
                            packetWrapper.passthrough(Type.STRING);
                            packetWrapper.passthrough(Type.OPTIONAL_STRING);
                        }
                        packetWrapper.passthrough(Type.VAR_INT);
                        packetWrapper.passthrough(Type.VAR_INT);
                        String string = packetWrapper.read(Type.OPTIONAL_STRING);
                        packetWrapper.write(Type.OPTIONAL_COMPONENT, string != null ? Protocol1_9To1_8.FIX_JSON.transform(packetWrapper, string) : null);
                        continue;
                    }
                    if (n == 1 || n == 2) {
                        packetWrapper.passthrough(Type.VAR_INT);
                        continue;
                    }
                    if (n == 3) {
                        String string = packetWrapper.read(Type.OPTIONAL_STRING);
                        packetWrapper.write(Type.OPTIONAL_COMPONENT, string != null ? Protocol1_9To1_8.FIX_JSON.transform(packetWrapper, string) : null);
                        continue;
                    }
                    if (n != 4) continue;
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.PLUGIN_MESSAGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(9::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.get(Type.STRING, 0);
                if (string.equalsIgnoreCase("MC|BOpen")) {
                    packetWrapper.read(Type.REMAINING_BYTES);
                    packetWrapper.write(Type.VAR_INT, 0);
                }
                if (string.equalsIgnoreCase("MC|TrList")) {
                    packetWrapper.passthrough(Type.INT);
                    Short s = packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                    for (int i = 0; i < s; ++i) {
                        Item item = packetWrapper.passthrough(Type.ITEM);
                        ItemRewriter.toClient(item);
                        Item item2 = packetWrapper.passthrough(Type.ITEM);
                        ItemRewriter.toClient(item2);
                        boolean bl = packetWrapper.passthrough(Type.BOOLEAN);
                        if (bl) {
                            Item item3 = packetWrapper.passthrough(Type.ITEM);
                            ItemRewriter.toClient(item3);
                        }
                        packetWrapper.passthrough(Type.BOOLEAN);
                        packetWrapper.passthrough(Type.INT);
                        packetWrapper.passthrough(Type.INT);
                    }
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(10::lambda$register$0);
                this.handler(10::lambda$register$1);
                this.handler(10::lambda$register$2);
            }

            private static void lambda$register$2(PacketWrapper packetWrapper) throws Exception {
                CommandBlockProvider commandBlockProvider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                commandBlockProvider.sendPermission(packetWrapper.user());
                commandBlockProvider.unloadChunks(packetWrapper.user());
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.user().get(ClientChunks.class).getLoadedChunks().clear();
                short s = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.setGameMode(GameMode.getById(s));
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 0);
                clientWorld.setEnvironment(n);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.GAME_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLOAT);
                this.handler(11::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                if (s == 3) {
                    int n = packetWrapper.get(Type.FLOAT, 0).intValue();
                    EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                    entityTracker1_9.setGameMode(GameMode.getById(n));
                } else if (s == 4) {
                    packetWrapper.set(Type.FLOAT, 0, Float.valueOf(1.0f));
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SET_COMPRESSION, null, PlayerPackets::lambda$register$0);
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.TAB_COMPLETE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map((Type)Type.BOOLEAN, Type.NOTHING);
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.CLIENT_SETTINGS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map((Type)Type.VAR_INT, Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(13::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                if (Via.getConfig().isLeftHandedHandling() && n == 0) {
                    packetWrapper.set(Type.UNSIGNED_BYTE, 0, (short)(packetWrapper.get(Type.UNSIGNED_BYTE, 0).intValue() | 0x80));
                }
                packetWrapper.sendToServer(Protocol1_9To1_8.class);
                packetWrapper.cancel();
                Via.getManager().getProviders().get(MainHandProvider.class).setMainHand(packetWrapper.user(), n);
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.ANIMATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.NOTHING);
            }
        });
        protocol1_9To1_8.cancelServerbound(ServerboundPackets1_9.TELEPORT_CONFIRM);
        protocol1_9To1_8.cancelServerbound(ServerboundPackets1_9.VEHICLE_MOVE);
        protocol1_9To1_8.cancelServerbound(ServerboundPackets1_9.STEER_BOAT);
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.PLUGIN_MESSAGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(15::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Item item;
                String string = packetWrapper.get(Type.STRING, 0);
                if (string.equalsIgnoreCase("MC|BSign") && (item = packetWrapper.passthrough(Type.ITEM)) != null) {
                    item.setIdentifier(387);
                    ItemRewriter.rewriteBookToServer(item);
                }
                if (string.equalsIgnoreCase("MC|AutoCmd")) {
                    packetWrapper.set(Type.STRING, 0, "MC|AdvCdm");
                    packetWrapper.write(Type.BYTE, (byte)0);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.clearInputBuffer();
                }
                if (string.equalsIgnoreCase("MC|AdvCmd")) {
                    packetWrapper.set(Type.STRING, 0, "MC|AdvCdm");
                }
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.CLIENT_STATUS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(16::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                EntityTracker1_9 entityTracker1_9;
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == 2 && (entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).isBlocking()) {
                    if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
                        entityTracker1_9.setSecondHand(null);
                    }
                    entityTracker1_9.setBlocking(true);
                }
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.PLAYER_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BOOLEAN);
                this.handler(new PlayerMovementMapper());
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.PLAYER_POSITION_AND_ROTATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(new PlayerMovementMapper());
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.PLAYER_ROTATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(new PlayerMovementMapper());
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.PLAYER_MOVEMENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.BOOLEAN);
                this.handler(new PlayerMovementMapper());
            }
        });
    }

    private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.cancel();
        CompressionProvider compressionProvider = Via.getManager().getProviders().get(CompressionProvider.class);
        compressionProvider.handlePlayCompression(packetWrapper.user(), packetWrapper.read(Type.VAR_INT));
    }
}

