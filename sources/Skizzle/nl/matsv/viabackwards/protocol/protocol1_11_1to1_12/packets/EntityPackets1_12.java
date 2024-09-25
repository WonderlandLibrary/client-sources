/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.packets;

import java.util.Optional;
import nl.matsv.viabackwards.api.exceptions.RemovedValueException;
import nl.matsv.viabackwards.api.rewriters.LegacyEntityRewriter;
import nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.data.ParrotStorage;
import nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;
import nl.matsv.viabackwards.utils.Block;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.entities.Entity1_12Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_12;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Types1_12;
import us.myles.ViaVersion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class EntityPackets1_12
extends LegacyEntityRewriter<Protocol1_11_1To1_12> {
    public EntityPackets1_12(Protocol1_11_1To1_12 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_11_1To1_12)this.protocol).registerOutgoing(ClientboundPackets1_12.SPAWN_ENTITY, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(EntityPackets1_12.this.getObjectTrackerHandler());
                this.handler(EntityPackets1_12.this.getObjectRewriter(id -> Entity1_12Types.ObjectType.findById(id.byteValue()).orElse(null)));
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Optional<Entity1_12Types.ObjectType> type = Entity1_12Types.ObjectType.findById(wrapper.get(Type.BYTE, 0).byteValue());
                        if (type.isPresent() && type.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
                            int objectData = wrapper.get(Type.INT, 0);
                            int objType = objectData & 0xFFF;
                            int data = objectData >> 12 & 0xF;
                            Block block = ((Protocol1_11_1To1_12)EntityPackets1_12.this.getProtocol()).getBlockItemPackets().handleBlock(objType, data);
                            if (block == null) {
                                return;
                            }
                            wrapper.set(Type.INT, 0, block.getId() | block.getData() << 12);
                        }
                    }
                });
            }
        });
        this.registerExtraTracker(ClientboundPackets1_12.SPAWN_EXPERIENCE_ORB, Entity1_12Types.EntityType.EXPERIENCE_ORB);
        this.registerExtraTracker(ClientboundPackets1_12.SPAWN_GLOBAL_ENTITY, Entity1_12Types.EntityType.WEATHER);
        ((Protocol1_11_1To1_12)this.protocol).registerOutgoing(ClientboundPackets1_12.SPAWN_MOB, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_12.METADATA_LIST);
                this.handler(EntityPackets1_12.this.getTrackerHandler());
                this.handler(EntityPackets1_12.this.getMobSpawnRewriter(Types1_12.METADATA_LIST));
            }
        });
        this.registerExtraTracker(ClientboundPackets1_12.SPAWN_PAINTING, Entity1_12Types.EntityType.PAINTING);
        ((Protocol1_11_1To1_12)this.protocol).registerOutgoing(ClientboundPackets1_12.SPAWN_PLAYER, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_12.METADATA_LIST);
                this.handler(EntityPackets1_12.this.getTrackerAndMetaHandler(Types1_12.METADATA_LIST, Entity1_12Types.EntityType.PLAYER));
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerOutgoing(ClientboundPackets1_12.JOIN_GAME, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(EntityPackets1_12.this.getTrackerHandler(Entity1_12Types.EntityType.PLAYER, Type.INT));
                this.handler(EntityPackets1_12.this.getDimensionHandler(1));
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ShoulderTracker tracker = wrapper.user().get(ShoulderTracker.class);
                        tracker.setEntityId(wrapper.get(Type.INT, 0));
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        PacketWrapper wrapper = new PacketWrapper(7, null, packetWrapper.user());
                        wrapper.write(Type.VAR_INT, 1);
                        wrapper.write(Type.STRING, "achievement.openInventory");
                        wrapper.write(Type.VAR_INT, 1);
                        wrapper.send(Protocol1_11_1To1_12.class);
                    }
                });
            }
        });
        this.registerRespawn(ClientboundPackets1_12.RESPAWN);
        this.registerEntityDestroy(ClientboundPackets1_12.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_12.ENTITY_METADATA, Types1_12.METADATA_LIST);
        ((Protocol1_11_1To1_12)this.protocol).registerOutgoing(ClientboundPackets1_12.ENTITY_PROPERTIES, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    int size;
                    int newSize = size = wrapper.get(Type.INT, 0).intValue();
                    for (int i = 0; i < size; ++i) {
                        int j;
                        int modSize;
                        String key = wrapper.read(Type.STRING);
                        if (key.equals("generic.flyingSpeed")) {
                            --newSize;
                            wrapper.read(Type.DOUBLE);
                            modSize = wrapper.read(Type.VAR_INT);
                            for (j = 0; j < modSize; ++j) {
                                wrapper.read(Type.UUID);
                                wrapper.read(Type.DOUBLE);
                                wrapper.read(Type.BYTE);
                            }
                            continue;
                        }
                        wrapper.write(Type.STRING, key);
                        wrapper.passthrough(Type.DOUBLE);
                        modSize = wrapper.passthrough(Type.VAR_INT);
                        for (j = 0; j < modSize; ++j) {
                            wrapper.passthrough(Type.UUID);
                            wrapper.passthrough(Type.DOUBLE);
                            wrapper.passthrough(Type.BYTE);
                        }
                    }
                    if (newSize != size) {
                        wrapper.set(Type.INT, 0, newSize);
                    }
                });
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.mapEntity(Entity1_12Types.EntityType.PARROT, Entity1_12Types.EntityType.BAT).mobName("Parrot").spawnMetadata(storage -> storage.add(new Metadata(12, MetaType1_12.Byte, (byte)0)));
        this.mapEntity(Entity1_12Types.EntityType.ILLUSION_ILLAGER, Entity1_12Types.EntityType.EVOCATION_ILLAGER).mobName("Illusioner");
        this.registerMetaHandler().filter(Entity1_12Types.EntityType.EVOCATION_ILLAGER, true, 12).removed();
        this.registerMetaHandler().filter(Entity1_12Types.EntityType.EVOCATION_ILLAGER, true, 13).handleIndexChange(12);
        this.registerMetaHandler().filter((EntityType)Entity1_12Types.EntityType.ILLUSION_ILLAGER, 0).handle(e -> {
            byte mask = (Byte)e.getData().getValue();
            if ((mask & 0x20) == 32) {
                mask = (byte)(mask & 0xFFFFFFDF);
            }
            e.getData().setValue(mask);
            return e.getData();
        });
        this.registerMetaHandler().filter((EntityType)Entity1_12Types.EntityType.PARROT, true).handle(e -> {
            if (!e.getEntity().has(ParrotStorage.class)) {
                e.getEntity().put(new ParrotStorage());
            }
            return e.getData();
        });
        this.registerMetaHandler().filter((EntityType)Entity1_12Types.EntityType.PARROT, 12).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_12Types.EntityType.PARROT, 13).handle(e -> {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl38 : ALOAD_2 - null : trying to set 0 previously set to 1
             * org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:203)
             * org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1542)
             * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:400)
             * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:258)
             * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:192)
             * org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             * org.benf.cfr.reader.entities.Method.analyse(Method.java:521)
             * org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1030)
             * org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:922)
             * org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:253)
             * org.benf.cfr.reader.Driver.doJar(Driver.java:135)
             * org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
             * org.benf.cfr.reader.Main.main(Main.java:49)
             */
            throw new IllegalStateException(Decompilation failed);
        });
        this.registerMetaHandler().filter((EntityType)Entity1_12Types.EntityType.PARROT, 14).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_12Types.EntityType.PARROT, 15).removed();
        this.registerMetaHandler().filter((EntityType)Entity1_12Types.EntityType.PLAYER, 15).handle(e -> {
            CompoundTag tag = (CompoundTag)e.getData().getValue();
            ShoulderTracker tracker = e.getUser().get(ShoulderTracker.class);
            if (tag.isEmpty() && tracker.getLeftShoulder() != null) {
                tracker.setLeftShoulder(null);
                tracker.update();
            } else if (tag.contains("id") && e.getEntity().getEntityId() == tracker.getEntityId()) {
                String id = (String)((Tag)tag.get("id")).getValue();
                if (tracker.getLeftShoulder() == null || !tracker.getLeftShoulder().equals(id)) {
                    tracker.setLeftShoulder(id);
                    tracker.update();
                }
            }
            throw RemovedValueException.EX;
        });
        this.registerMetaHandler().filter((EntityType)Entity1_12Types.EntityType.PLAYER, 16).handle(e -> {
            CompoundTag tag = (CompoundTag)e.getData().getValue();
            ShoulderTracker tracker = e.getUser().get(ShoulderTracker.class);
            if (tag.isEmpty() && tracker.getRightShoulder() != null) {
                tracker.setRightShoulder(null);
                tracker.update();
            } else if (tag.contains("id") && e.getEntity().getEntityId() == tracker.getEntityId()) {
                String id = (String)((Tag)tag.get("id")).getValue();
                if (tracker.getRightShoulder() == null || !tracker.getRightShoulder().equals(id)) {
                    tracker.setRightShoulder(id);
                    tracker.update();
                }
            }
            throw RemovedValueException.EX;
        });
    }

    @Override
    protected EntityType getTypeFromId(int typeId) {
        return Entity1_12Types.getTypeFromId(typeId, false);
    }

    @Override
    protected EntityType getObjectTypeFromId(int typeId) {
        return Entity1_12Types.getTypeFromId(typeId, true);
    }
}

