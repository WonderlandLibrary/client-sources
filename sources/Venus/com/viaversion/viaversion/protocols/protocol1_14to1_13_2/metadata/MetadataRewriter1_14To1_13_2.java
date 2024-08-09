/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.VillagerData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_14To1_13_2
extends EntityRewriter<ClientboundPackets1_13, Protocol1_14To1_13_2> {
    public MetadataRewriter1_14To1_13_2(Protocol1_14To1_13_2 protocol1_14To1_13_2) {
        super(protocol1_14To1_13_2);
        this.mapTypes(Entity1_13Types.EntityType.values(), Entity1_14Types.class);
        this.mapEntityType(Entity1_13Types.EntityType.OCELOT, Entity1_14Types.CAT);
    }

    @Override
    protected void handleMetadata(int n, EntityType entityType, Metadata metadata, List<Metadata> list, UserConnection userConnection) throws Exception {
        float f;
        int n2;
        metadata.setMetaType(Types1_14.META_TYPES.byId(metadata.metaType().typeId()));
        EntityTracker1_14 entityTracker1_14 = (EntityTracker1_14)this.tracker(userConnection);
        if (metadata.metaType() == Types1_14.META_TYPES.itemType) {
            ((Protocol1_14To1_13_2)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        } else if (metadata.metaType() == Types1_14.META_TYPES.blockStateType) {
            n2 = (Integer)metadata.getValue();
            metadata.setValue(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewBlockStateId(n2));
        } else if (metadata.metaType() == Types1_14.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
        if (entityType == null) {
            return;
        }
        if (metadata.id() > 5) {
            metadata.setId(metadata.id() + 1);
        }
        if (metadata.id() == 8 && entityType.isOrHasParent(Entity1_14Types.LIVINGENTITY) && Float.isNaN(f = ((Number)metadata.getValue()).floatValue()) && Via.getConfig().is1_14HealthNaNFix()) {
            metadata.setValue(Float.valueOf(1.0f));
        }
        if (metadata.id() > 11 && entityType.isOrHasParent(Entity1_14Types.LIVINGENTITY)) {
            metadata.setId(metadata.id() + 1);
        }
        if (entityType.isOrHasParent(Entity1_14Types.ABSTRACT_INSENTIENT) && metadata.id() == 13) {
            entityTracker1_14.setInsentientData(n, (byte)(((Number)metadata.getValue()).byteValue() & 0xFFFFFFFB | entityTracker1_14.getInsentientData(n) & 4));
            metadata.setValue(entityTracker1_14.getInsentientData(n));
        }
        if (entityType.isOrHasParent(Entity1_14Types.PLAYER)) {
            if (n != entityTracker1_14.clientEntityId()) {
                if (metadata.id() == 0) {
                    n2 = ((Number)metadata.getValue()).byteValue();
                    entityTracker1_14.setEntityFlags(n, (byte)n2);
                } else if (metadata.id() == 7) {
                    entityTracker1_14.setRiptide(n, (((Number)metadata.getValue()).byteValue() & 4) != 0);
                }
                if (metadata.id() == 0 || metadata.id() == 7) {
                    list.add(new Metadata(6, Types1_14.META_TYPES.poseType, MetadataRewriter1_14To1_13_2.recalculatePlayerPose(n, entityTracker1_14)));
                }
            }
        } else if (entityType.isOrHasParent(Entity1_14Types.ZOMBIE)) {
            if (metadata.id() == 16) {
                entityTracker1_14.setInsentientData(n, (byte)(entityTracker1_14.getInsentientData(n) & 0xFFFFFFFB | ((Boolean)metadata.getValue() != false ? 4 : 0)));
                list.remove(metadata);
                list.add(new Metadata(13, Types1_14.META_TYPES.byteType, entityTracker1_14.getInsentientData(n)));
            } else if (metadata.id() > 16) {
                metadata.setId(metadata.id() - 1);
            }
        }
        if (entityType.isOrHasParent(Entity1_14Types.MINECART_ABSTRACT)) {
            if (metadata.id() == 10) {
                n2 = (Integer)metadata.getValue();
                metadata.setValue(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewBlockStateId(n2));
            }
        } else if (entityType.is((EntityType)Entity1_14Types.HORSE)) {
            if (metadata.id() == 18) {
                list.remove(metadata);
                n2 = (Integer)metadata.getValue();
                DataItem dataItem = null;
                if (n2 == 1) {
                    dataItem = new DataItem(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewItemId(727), 1, 0, null);
                } else if (n2 == 2) {
                    dataItem = new DataItem(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewItemId(728), 1, 0, null);
                } else if (n2 == 3) {
                    dataItem = new DataItem(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewItemId(729), 1, 0, null);
                }
                PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_14.ENTITY_EQUIPMENT, null, userConnection);
                packetWrapper.write(Type.VAR_INT, n);
                packetWrapper.write(Type.VAR_INT, 4);
                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, dataItem);
                packetWrapper.scheduleSend(Protocol1_14To1_13_2.class);
            }
        } else if (entityType.is((EntityType)Entity1_14Types.VILLAGER)) {
            if (metadata.id() == 15) {
                metadata.setTypeAndValue(Types1_14.META_TYPES.villagerDatatType, new VillagerData(2, MetadataRewriter1_14To1_13_2.getNewProfessionId((Integer)metadata.getValue()), 0));
            }
        } else if (entityType.is((EntityType)Entity1_14Types.ZOMBIE_VILLAGER)) {
            if (metadata.id() == 18) {
                metadata.setTypeAndValue(Types1_14.META_TYPES.villagerDatatType, new VillagerData(2, MetadataRewriter1_14To1_13_2.getNewProfessionId((Integer)metadata.getValue()), 0));
            }
        } else if (entityType.isOrHasParent(Entity1_14Types.ABSTRACT_ARROW)) {
            if (metadata.id() >= 9) {
                metadata.setId(metadata.id() + 1);
            }
        } else if (entityType.is((EntityType)Entity1_14Types.FIREWORK_ROCKET)) {
            if (metadata.id() == 8) {
                metadata.setMetaType(Types1_14.META_TYPES.optionalVarIntType);
                if (metadata.getValue().equals(0)) {
                    metadata.setValue(null);
                }
            }
        } else if (entityType.isOrHasParent(Entity1_14Types.ABSTRACT_SKELETON) && metadata.id() == 14) {
            entityTracker1_14.setInsentientData(n, (byte)(entityTracker1_14.getInsentientData(n) & 0xFFFFFFFB | ((Boolean)metadata.getValue() != false ? 4 : 0)));
            list.remove(metadata);
            list.add(new Metadata(13, Types1_14.META_TYPES.byteType, entityTracker1_14.getInsentientData(n)));
        }
        if (entityType.isOrHasParent(Entity1_14Types.ABSTRACT_ILLAGER_BASE) && metadata.id() == 14) {
            entityTracker1_14.setInsentientData(n, (byte)(entityTracker1_14.getInsentientData(n) & 0xFFFFFFFB | (((Number)metadata.getValue()).byteValue() != 0 ? 4 : 0)));
            list.remove(metadata);
            list.add(new Metadata(13, Types1_14.META_TYPES.byteType, entityTracker1_14.getInsentientData(n)));
        }
        if ((entityType.is((EntityType)Entity1_14Types.WITCH) || entityType.is((EntityType)Entity1_14Types.RAVAGER) || entityType.isOrHasParent(Entity1_14Types.ABSTRACT_ILLAGER_BASE)) && metadata.id() >= 14) {
            metadata.setId(metadata.id() + 1);
        }
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_14Types.getTypeFromId(n);
    }

    private static boolean isSneaking(byte by) {
        return (by & 2) != 0;
    }

    private static boolean isSwimming(byte by) {
        return (by & 0x10) != 0;
    }

    private static int getNewProfessionId(int n) {
        switch (n) {
            case 0: {
                return 0;
            }
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 0;
            }
            case 4: {
                return 1;
            }
            case 5: {
                return 0;
            }
        }
        return 1;
    }

    private static boolean isFallFlying(int n) {
        return (n & 0x80) != 0;
    }

    public static int recalculatePlayerPose(int n, EntityTracker1_14 entityTracker1_14) {
        byte by = entityTracker1_14.getEntityFlags(n);
        int n2 = 0;
        if (MetadataRewriter1_14To1_13_2.isFallFlying(by)) {
            n2 = 1;
        } else if (entityTracker1_14.isSleeping(n)) {
            n2 = 2;
        } else if (MetadataRewriter1_14To1_13_2.isSwimming(by)) {
            n2 = 3;
        } else if (entityTracker1_14.isRiptide(n)) {
            n2 = 4;
        } else if (MetadataRewriter1_14To1_13_2.isSneaking(by)) {
            n2 = 5;
        }
        return n2;
    }
}

