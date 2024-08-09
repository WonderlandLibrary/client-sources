/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.google.common.collect.ImmutableList;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viaversion.util.Pair;
import com.viaversion.viaversion.util.Triple;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EntityPackets {
    public static final ValueTransformer<Byte, Short> toNewShort = new ValueTransformer<Byte, Short>((Type)Type.SHORT){

        @Override
        public Short transform(PacketWrapper packetWrapper, Byte by) {
            return (short)(by * 128);
        }

        @Override
        public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
            return this.transform(packetWrapper, (Byte)object);
        }
    };

    public static void register(Protocol1_9To1_8 protocol1_9To1_8) {
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ATTACH_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE, new ValueTransformer<Short, Void>(this, (Type)Type.NOTHING){
                    final 2 this$0;
                    {
                        this.this$0 = var1_1;
                        super(type);
                    }

                    @Override
                    public Void transform(PacketWrapper packetWrapper, Short s) throws Exception {
                        EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        if (s == 0) {
                            int n = packetWrapper.get(Type.INT, 0);
                            int n2 = packetWrapper.get(Type.INT, 1);
                            packetWrapper.cancel();
                            PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_9.SET_PASSENGERS);
                            if (n2 == -1) {
                                if (!entityTracker1_9.getVehicleMap().containsKey(n)) {
                                    return null;
                                }
                                packetWrapper2.write(Type.VAR_INT, entityTracker1_9.getVehicleMap().remove(n));
                                packetWrapper2.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
                            } else {
                                packetWrapper2.write(Type.VAR_INT, n2);
                                packetWrapper2.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{n});
                                entityTracker1_9.getVehicleMap().put(n, n2);
                            }
                            packetWrapper2.send(Protocol1_9To1_8.class);
                        }
                        return null;
                    }

                    @Override
                    public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
                        return this.transform(packetWrapper, (Short)object);
                    }
                });
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_TELEPORT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                EntityTracker1_9 entityTracker1_9;
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (Via.getConfig().isHologramPatch() && (entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).getKnownHolograms().contains(n)) {
                    Double d = packetWrapper.get(Type.DOUBLE, 1);
                    d = d + Via.getConfig().getHologramYOffset();
                    packetWrapper.set(Type.DOUBLE, 1, d);
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_POSITION_AND_ROTATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE, toNewShort);
                this.map(Type.BYTE, toNewShort);
                this.map(Type.BYTE, toNewShort);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE, toNewShort);
                this.map(Type.BYTE, toNewShort);
                this.map(Type.BYTE, toNewShort);
                this.map(Type.BOOLEAN);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_EQUIPMENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.SHORT, new ValueTransformer<Short, Integer>(this, (Type)Type.VAR_INT){
                    final 6 this$0;
                    {
                        this.this$0 = var1_1;
                        super(type);
                    }

                    @Override
                    public Integer transform(PacketWrapper packetWrapper, Short s) throws Exception {
                        int n;
                        int n2 = packetWrapper.get(Type.VAR_INT, 0);
                        if (n2 == (n = packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class).clientEntityId())) {
                            return s.intValue() + 2;
                        }
                        return s > 0 ? s.intValue() + 1 : s.intValue();
                    }

                    @Override
                    public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
                        return this.transform(packetWrapper, (Short)object);
                    }
                });
                this.map(Type.ITEM);
                this.handler(6::lambda$register$0);
                this.handler(6::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                int n = packetWrapper.get(Type.VAR_INT, 0);
                Item item = packetWrapper.get(Type.ITEM, 0);
                if (item != null && Protocol1_9To1_8.isSword(item.identifier())) {
                    entityTracker1_9.getValidBlocking().add(n);
                    return;
                }
                entityTracker1_9.getValidBlocking().remove(n);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Item item = packetWrapper.get(Type.ITEM, 0);
                ItemRewriter.toClient(item);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_METADATA, new PacketHandlers(protocol1_9To1_8){
            final Protocol1_9To1_8 val$protocol;
            {
                this.val$protocol = protocol1_9To1_8;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
                this.handler(arg_0 -> 7.lambda$register$0(this.val$protocol, arg_0));
                this.handler(7::lambda$register$1);
                this.handler(7::lambda$register$2);
            }

            private static void lambda$register$2(PacketWrapper packetWrapper) throws Exception {
                List<Metadata> list = packetWrapper.get(Types1_9.METADATA_LIST, 0);
                if (list.isEmpty()) {
                    packetWrapper.cancel();
                }
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                List<Metadata> list = packetWrapper.get(Types1_9.METADATA_LIST, 0);
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.handleMetadata(n, list);
            }

            private static void lambda$register$0(Protocol1_9To1_8 protocol1_9To1_8, PacketWrapper packetWrapper) throws Exception {
                List<Metadata> list = packetWrapper.get(Types1_9.METADATA_LIST, 0);
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                if (entityTracker1_9.hasEntity(n)) {
                    protocol1_9To1_8.get(MetadataRewriter1_9To1_8.class).handleMetadata(n, list, packetWrapper.user());
                } else {
                    entityTracker1_9.addMetadataToBuffer(n, list);
                    packetWrapper.cancel();
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_EFFECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(8::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                boolean bl = packetWrapper.read(Type.BOOLEAN);
                boolean bl2 = Via.getConfig().isNewEffectIndicator();
                packetWrapper.write(Type.BYTE, (byte)(bl ? (bl2 ? 2 : 1) : 0));
            }
        });
        protocol1_9To1_8.cancelClientbound(ClientboundPackets1_8.UPDATE_ENTITY_NBT);
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.COMBAT_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(9::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.get(Type.VAR_INT, 0) == 2) {
                    packetWrapper.passthrough(Type.VAR_INT);
                    packetWrapper.passthrough(Type.INT);
                    Protocol1_9To1_8.FIX_JSON.write(packetWrapper, packetWrapper.read(Type.STRING));
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_PROPERTIES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(10::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (!Via.getConfig().isMinimizeCooldown()) {
                    return;
                }
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                if (packetWrapper.get(Type.VAR_INT, 0).intValue() != entityTracker1_9.getProvidedEntityId()) {
                    return;
                }
                int n = packetWrapper.read(Type.INT);
                HashMap<String, Pair<Double, AbstractCollection>> hashMap = new HashMap<String, Pair<Double, AbstractCollection>>(n);
                for (int i = 0; i < n; ++i) {
                    String object = packetWrapper.read(Type.STRING);
                    Double d = packetWrapper.read(Type.DOUBLE);
                    int n2 = packetWrapper.read(Type.VAR_INT);
                    ArrayList<Triple<UUID, Double, Byte>> arrayList = new ArrayList<Triple<UUID, Double, Byte>>(n2);
                    for (int j = 0; j < n2; ++j) {
                        arrayList.add(new Triple<UUID, Double, Byte>(packetWrapper.read(Type.UUID), packetWrapper.read(Type.DOUBLE), packetWrapper.read(Type.BYTE)));
                    }
                    hashMap.put(object, new Pair(d, arrayList));
                }
                hashMap.put("generic.attackSpeed", new Pair<Double, ImmutableList<Triple<UUID, Double, Byte>>>(15.9, ImmutableList.of(new Triple<UUID, Double, Byte>(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), 0.0, (byte)0), new Triple<UUID, Double, Byte>(UUID.fromString("AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3"), 0.0, (byte)2), new Triple<UUID, Double, Byte>(UUID.fromString("55FCED67-E92A-486E-9800-B47F202C4386"), 0.0, (byte)2))));
                packetWrapper.write(Type.INT, hashMap.size());
                for (Map.Entry entry : hashMap.entrySet()) {
                    packetWrapper.write(Type.STRING, entry.getKey());
                    packetWrapper.write(Type.DOUBLE, ((Pair)entry.getValue()).key());
                    packetWrapper.write(Type.VAR_INT, ((List)((Pair)entry.getValue()).value()).size());
                    for (Triple triple : (List)((Pair)entry.getValue()).value()) {
                        packetWrapper.write(Type.UUID, triple.first());
                        packetWrapper.write(Type.DOUBLE, triple.second());
                        packetWrapper.write(Type.BYTE, triple.third());
                    }
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_ANIMATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(11::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 3) {
                    packetWrapper.cancel();
                }
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.ENTITY_ACTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(12::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 1);
                if (n == 6 || n == 8) {
                    packetWrapper.cancel();
                }
                if (n == 7) {
                    packetWrapper.set(Type.VAR_INT, 1, 6);
                }
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.INTERACT_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(13::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n;
                int n2 = packetWrapper.get(Type.VAR_INT, 1);
                if (n2 == 2) {
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.FLOAT);
                }
                if ((n2 == 0 || n2 == 2) && (n = packetWrapper.read(Type.VAR_INT).intValue()) == 1) {
                    packetWrapper.cancel();
                }
            }
        });
    }
}

