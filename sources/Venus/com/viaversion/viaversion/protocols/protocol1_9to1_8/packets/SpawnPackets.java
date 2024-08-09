/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
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
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import java.util.ArrayList;
import java.util.List;

public class SpawnPackets {
    public static final ValueTransformer<Integer, Double> toNewDouble = new ValueTransformer<Integer, Double>((Type)Type.DOUBLE){

        @Override
        public Double transform(PacketWrapper packetWrapper, Integer n) {
            return (double)n.intValue() / 32.0;
        }

        @Override
        public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
            return this.transform(packetWrapper, (Integer)object);
        }
    };

    public static void register(Protocol1_9To1_8 protocol1_9To1_8) {
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SPAWN_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(2::lambda$register$0);
                this.map(Type.BYTE);
                this.handler(2::lambda$register$1);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(2::lambda$register$2);
                this.handler(2::lambda$register$4);
            }

            private static void lambda$register$4(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                int n2 = packetWrapper.get(Type.INT, 0);
                byte by = packetWrapper.get(Type.BYTE, 0);
                if (Entity1_10Types.getTypeFromId(by, true) == Entity1_10Types.EntityType.SPLASH_POTION) {
                    PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_9.ENTITY_METADATA, arg_0 -> 2.lambda$null$3(n, n2, arg_0));
                    packetWrapper.send(Protocol1_9To1_8.class);
                    packetWrapper2.send(Protocol1_9To1_8.class);
                    packetWrapper.cancel();
                }
            }

            private static void lambda$null$3(int n, int n2, PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, n);
                ArrayList<Metadata> arrayList = new ArrayList<Metadata>();
                DataItem dataItem = new DataItem(373, 1, (short)n2, null);
                ItemRewriter.toClient(dataItem);
                Metadata metadata = new Metadata(5, MetaType1_9.Slot, dataItem);
                arrayList.add(metadata);
                packetWrapper.write(Types1_9.METADATA_LIST, arrayList);
            }

            private static void lambda$register$2(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                short s = 0;
                short s2 = 0;
                short s3 = 0;
                if (n > 0) {
                    s = packetWrapper.read(Type.SHORT);
                    s2 = packetWrapper.read(Type.SHORT);
                    s3 = packetWrapper.read(Type.SHORT);
                }
                packetWrapper.write(Type.SHORT, s);
                packetWrapper.write(Type.SHORT, s2);
                packetWrapper.write(Type.SHORT, s3);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                byte by = packetWrapper.get(Type.BYTE, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.addEntity(n, Entity1_10Types.getTypeFromId(by, true));
                entityTracker1_9.sendMetadataBuffer(n);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                packetWrapper.write(Type.UUID, entityTracker1_9.getEntityUUID(n));
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SPAWN_EXPERIENCE_ORB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(3::lambda$register$0);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.SHORT);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.addEntity(n, Entity1_10Types.EntityType.EXPERIENCE_ORB);
                entityTracker1_9.sendMetadataBuffer(n);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SPAWN_GLOBAL_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(4::lambda$register$0);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.addEntity(n, Entity1_10Types.EntityType.LIGHTNING);
                entityTracker1_9.sendMetadataBuffer(n);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SPAWN_MOB, new PacketHandlers(protocol1_9To1_8){
            final Protocol1_9To1_8 val$protocol;
            {
                this.val$protocol = protocol1_9To1_8;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(5::lambda$register$0);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(5::lambda$register$1);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
                this.handler(arg_0 -> 5.lambda$register$2(this.val$protocol, arg_0));
                this.handler(5::lambda$register$3);
            }

            private static void lambda$register$3(PacketWrapper packetWrapper) throws Exception {
                List<Metadata> list = packetWrapper.get(Types1_9.METADATA_LIST, 0);
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.handleMetadata(n, list);
            }

            private static void lambda$register$2(Protocol1_9To1_8 protocol1_9To1_8, PacketWrapper packetWrapper) throws Exception {
                List<Metadata> list = packetWrapper.get(Types1_9.METADATA_LIST, 0);
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                if (entityTracker1_9.hasEntity(n)) {
                    protocol1_9To1_8.get(MetadataRewriter1_9To1_8.class).handleMetadata(n, list, packetWrapper.user());
                } else {
                    Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + n);
                    list.clear();
                }
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                short s = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.addEntity(n, Entity1_10Types.getTypeFromId(s, false));
                entityTracker1_9.sendMetadataBuffer(n);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                packetWrapper.write(Type.UUID, entityTracker1_9.getEntityUUID(n));
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SPAWN_PAINTING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(6::lambda$register$0);
                this.handler(6::lambda$register$1);
                this.map(Type.STRING);
                this.map(Type.POSITION);
                this.map(Type.BYTE);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                packetWrapper.write(Type.UUID, entityTracker1_9.getEntityUUID(n));
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.addEntity(n, Entity1_10Types.EntityType.PAINTING);
                entityTracker1_9.sendMetadataBuffer(n);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SPAWN_PLAYER, new PacketHandlers(protocol1_9To1_8){
            final Protocol1_9To1_8 val$protocol;
            {
                this.val$protocol = protocol1_9To1_8;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.handler(7::lambda$register$0);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.INT, toNewDouble);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.handler(7::lambda$register$1);
                this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
                this.handler(arg_0 -> 7.lambda$register$2(this.val$protocol, arg_0));
                this.handler(7::lambda$register$3);
            }

            private static void lambda$register$3(PacketWrapper packetWrapper) throws Exception {
                List<Metadata> list = packetWrapper.get(Types1_9.METADATA_LIST, 0);
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.handleMetadata(n, list);
            }

            private static void lambda$register$2(Protocol1_9To1_8 protocol1_9To1_8, PacketWrapper packetWrapper) throws Exception {
                List<Metadata> list = packetWrapper.get(Types1_9.METADATA_LIST, 0);
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                if (entityTracker1_9.hasEntity(n)) {
                    protocol1_9To1_8.get(MetadataRewriter1_9To1_8.class).handleMetadata(n, list, packetWrapper.user());
                } else {
                    Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + n);
                    list.clear();
                }
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.read(Type.SHORT);
                if (s != 0) {
                    PacketWrapper packetWrapper2 = PacketWrapper.create(ClientboundPackets1_9.ENTITY_EQUIPMENT, null, packetWrapper.user());
                    packetWrapper2.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                    packetWrapper2.write(Type.VAR_INT, 0);
                    packetWrapper2.write(Type.ITEM, new DataItem(s, 1, 0, null));
                    try {
                        packetWrapper2.send(Protocol1_9To1_8.class);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.addEntity(n, Entity1_10Types.EntityType.PLAYER);
                entityTracker1_9.sendMetadataBuffer(n);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.DESTROY_ENTITIES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT_ARRAY_PRIMITIVE);
                this.handler(8::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int[] nArray = packetWrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0);
                Object t = packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                for (int n : nArray) {
                    t.removeEntity(n);
                }
            }
        });
    }
}

