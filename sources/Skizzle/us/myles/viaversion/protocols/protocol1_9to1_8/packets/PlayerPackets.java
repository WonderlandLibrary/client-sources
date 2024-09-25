/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_9to1_8.packets;

import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.entities.Entity1_10Types;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueCreator;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_8.ClientboundPackets1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.ItemRewriter;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.PlayerMovementMapper;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.chat.ChatRewriter;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.chat.GameMode;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.MainHandProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.ClientChunks;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import us.myles.viaversion.libs.gson.JsonObject;

public class PlayerPackets {
    public static void register(Protocol1_9To1_8 protocol) {
        protocol.registerOutgoing(ClientboundPackets1_8.CHAT_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        try {
                            JsonObject obj = (JsonObject)wrapper.get(Type.COMPONENT, 0);
                            ChatRewriter.toClient(obj, wrapper.user());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.TAB_LIST, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.DISCONNECT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.TITLE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int action = wrapper.get(Type.VAR_INT, 0);
                        if (action == 0 || action == 1) {
                            Protocol1_9To1_8.FIX_JSON.write(wrapper, wrapper.read(Type.STRING));
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.PLAYER_POSITION, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BYTE);
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper wrapper) {
                        wrapper.write(Type.VAR_INT, 0);
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.TEAMS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        byte mode = wrapper.get(Type.BYTE, 0);
                        if (mode == 0 || mode == 2) {
                            wrapper.passthrough(Type.STRING);
                            wrapper.passthrough(Type.STRING);
                            wrapper.passthrough(Type.STRING);
                            wrapper.passthrough(Type.BYTE);
                            wrapper.passthrough(Type.STRING);
                            wrapper.write(Type.STRING, Via.getConfig().isPreventCollision() ? "never" : "");
                            wrapper.passthrough(Type.BYTE);
                        }
                        if (mode == 0 || mode == 3 || mode == 4) {
                            String[] players = wrapper.passthrough(Type.STRING_ARRAY);
                            EntityTracker1_9 entityTracker = wrapper.user().get(EntityTracker1_9.class);
                            String myName = wrapper.user().getProtocolInfo().getUsername();
                            String teamName = wrapper.get(Type.STRING, 0);
                            for (String player : players) {
                                if (!entityTracker.isAutoTeam() || !player.equalsIgnoreCase(myName)) continue;
                                if (mode == 4) {
                                    wrapper.send(Protocol1_9To1_8.class, true, true);
                                    wrapper.cancel();
                                    entityTracker.sendTeamPacket(true, true);
                                    entityTracker.setCurrentTeam("viaversion");
                                    continue;
                                }
                                entityTracker.sendTeamPacket(false, true);
                                entityTracker.setCurrentTeam(teamName);
                            }
                        }
                        if (mode == 1) {
                            EntityTracker1_9 entityTracker = wrapper.user().get(EntityTracker1_9.class);
                            String teamName = wrapper.get(Type.STRING, 0);
                            if (entityTracker.isAutoTeam() && teamName.equals(entityTracker.getCurrentTeam())) {
                                wrapper.send(Protocol1_9To1_8.class, true, true);
                                wrapper.cancel();
                                entityTracker.sendTeamPacket(true, true);
                                entityTracker.setCurrentTeam("viaversion");
                            }
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.JOIN_GAME, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = wrapper.get(Type.INT, 0);
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        tracker.addEntity(entityId, Entity1_10Types.EntityType.PLAYER);
                        tracker.setClientEntityId(entityId);
                    }
                });
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        tracker.setGameMode(GameMode.getById(wrapper.get(Type.UNSIGNED_BYTE, 0).shortValue()));
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        CommandBlockProvider provider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                        provider.sendPermission(wrapper.user());
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        EntityTracker1_9 entityTracker = wrapper.user().get(EntityTracker1_9.class);
                        if (Via.getConfig().isAutoTeam()) {
                            entityTracker.setAutoTeam(true);
                            wrapper.send(Protocol1_9To1_8.class, true, true);
                            wrapper.cancel();
                            entityTracker.sendTeamPacket(true, true);
                            entityTracker.setCurrentTeam("viaversion");
                        } else {
                            entityTracker.setAutoTeam(false);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.PLAYER_INFO, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    /*
                     * Exception decompiling
                     */
                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        /*
                         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl117 : IINC - null : trying to set 2 previously set to 0
                         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:203)
                         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1542)
                         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:400)
                         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:258)
                         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:192)
                         * org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
                         * org.benf.cfr.reader.entities.Method.analyse(Method.java:521)
                         * org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
                         * org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:903)
                         * org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1015)
                         * org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:903)
                         * org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1015)
                         * org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:922)
                         * org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:253)
                         * org.benf.cfr.reader.Driver.doJar(Driver.java:135)
                         * org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
                         * org.benf.cfr.reader.Main.main(Main.java:49)
                         */
                        throw new IllegalStateException(Decompilation failed);
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String name = wrapper.get(Type.STRING, 0);
                        if (name.equalsIgnoreCase("MC|BOpen")) {
                            wrapper.read(Type.REMAINING_BYTES);
                            wrapper.write(Type.VAR_INT, 0);
                        }
                        if (name.equalsIgnoreCase("MC|TrList")) {
                            wrapper.passthrough(Type.INT);
                            Short size = wrapper.passthrough(Type.UNSIGNED_BYTE);
                            for (int i = 0; i < size; ++i) {
                                Item item1 = wrapper.passthrough(Type.ITEM);
                                ItemRewriter.toClient(item1);
                                Item item2 = wrapper.passthrough(Type.ITEM);
                                ItemRewriter.toClient(item2);
                                boolean present = wrapper.passthrough(Type.BOOLEAN);
                                if (present) {
                                    Item item3 = wrapper.passthrough(Type.ITEM);
                                    ItemRewriter.toClient(item3);
                                }
                                wrapper.passthrough(Type.BOOLEAN);
                                wrapper.passthrough(Type.INT);
                                wrapper.passthrough(Type.INT);
                            }
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.UPDATE_HEALTH, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        float health = wrapper.get(Type.FLOAT, 0).floatValue();
                        if (health <= 0.0f) {
                            ClientChunks cc = wrapper.user().get(ClientChunks.class);
                            cc.getBulkChunks().clear();
                            cc.getLoadedChunks().clear();
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.RESPAWN, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientChunks cc = wrapper.user().get(ClientChunks.class);
                        cc.getBulkChunks().clear();
                        cc.getLoadedChunks().clear();
                        short gamemode = wrapper.get(Type.UNSIGNED_BYTE, 0);
                        wrapper.user().get(EntityTracker1_9.class).setGameMode(GameMode.getById(gamemode));
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        CommandBlockProvider provider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                        provider.sendPermission(wrapper.user());
                        provider.unloadChunks(wrapper.user());
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.GAME_EVENT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLOAT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 3) {
                            int gamemode = wrapper.get(Type.FLOAT, 0).intValue();
                            wrapper.user().get(EntityTracker1_9.class).setGameMode(GameMode.getById(gamemode));
                        }
                    }
                });
            }
        });
        protocol.cancelOutgoing(ClientboundPackets1_8.SET_COMPRESSION);
        protocol.registerIncoming(ServerboundPackets1_9.TAB_COMPLETE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BOOLEAN, Type.NOTHING);
            }
        });
        protocol.registerIncoming(ServerboundPackets1_9.CLIENT_SETTINGS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map((Type)Type.VAR_INT, Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int hand = wrapper.read(Type.VAR_INT);
                        if (Via.getConfig().isLeftHandedHandling() && hand == 0) {
                            wrapper.set(Type.UNSIGNED_BYTE, 0, (short)(wrapper.get(Type.UNSIGNED_BYTE, 0).intValue() | 0x80));
                        }
                        wrapper.sendToServer(Protocol1_9To1_8.class, true, true);
                        wrapper.cancel();
                        Via.getManager().getProviders().get(MainHandProvider.class).setMainHand(wrapper.user(), hand);
                    }
                });
            }
        });
        protocol.registerIncoming(ServerboundPackets1_9.ANIMATION, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.NOTHING);
            }
        });
        protocol.cancelIncoming(ServerboundPackets1_9.TELEPORT_CONFIRM);
        protocol.cancelIncoming(ServerboundPackets1_9.VEHICLE_MOVE);
        protocol.cancelIncoming(ServerboundPackets1_9.STEER_BOAT);
        protocol.registerIncoming(ServerboundPackets1_9.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Item item;
                        String name = wrapper.get(Type.STRING, 0);
                        if (name.equalsIgnoreCase("MC|BSign") && (item = wrapper.passthrough(Type.ITEM)) != null) {
                            item.setIdentifier(387);
                            ItemRewriter.rewriteBookToServer(item);
                        }
                        if (name.equalsIgnoreCase("MC|AutoCmd")) {
                            wrapper.set(Type.STRING, 0, "MC|AdvCdm");
                            wrapper.write(Type.BYTE, (byte)0);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.STRING);
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.clearInputBuffer();
                        }
                        if (name.equalsIgnoreCase("MC|AdvCmd")) {
                            wrapper.set(Type.STRING, 0, "MC|AdvCdm");
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(ServerboundPackets1_9.CLIENT_STATUS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        EntityTracker1_9 tracker;
                        int action = wrapper.get(Type.VAR_INT, 0);
                        if (action == 2 && (tracker = wrapper.user().get(EntityTracker1_9.class)).isBlocking()) {
                            tracker.setSecondHand(null);
                            tracker.setBlocking(false);
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(ServerboundPackets1_9.PLAYER_POSITION, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BOOLEAN);
                this.handler(new PlayerMovementMapper());
            }
        });
        protocol.registerIncoming(ServerboundPackets1_9.PLAYER_POSITION_AND_ROTATION, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(new PlayerMovementMapper());
            }
        });
        protocol.registerIncoming(ServerboundPackets1_9.PLAYER_ROTATION, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(new PlayerMovementMapper());
            }
        });
        protocol.registerIncoming(ServerboundPackets1_9.PLAYER_MOVEMENT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BOOLEAN);
                this.handler(new PlayerMovementMapper());
            }
        });
    }
}

