/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_11to1_10.metadata;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.storage.EntityTracker1_11;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;
import java.util.Optional;

public class MetadataRewriter1_11To1_10
extends EntityRewriter<ClientboundPackets1_9_3, Protocol1_11To1_10> {
    public MetadataRewriter1_11To1_10(Protocol1_11To1_10 protocol1_11To1_10) {
        super(protocol1_11To1_10);
    }

    @Override
    protected void handleMetadata(int n, EntityType entityType, Metadata metadata, List<Metadata> list, UserConnection userConnection) {
        int n2;
        if (metadata.getValue() instanceof DataItem) {
            EntityIdRewriter.toClientItem((Item)metadata.getValue());
        }
        if (entityType == null) {
            return;
        }
        if ((entityType.is((EntityType)Entity1_11Types.EntityType.ELDER_GUARDIAN) || entityType.is((EntityType)Entity1_11Types.EntityType.GUARDIAN)) && (n2 = metadata.id()) == 12) {
            boolean bl = ((Byte)metadata.getValue() & 2) == 2;
            metadata.setTypeAndValue(MetaType1_9.Boolean, bl);
        }
        if (entityType.isOrHasParent(Entity1_11Types.EntityType.ABSTRACT_SKELETON)) {
            n2 = metadata.id();
            if (n2 == 12) {
                list.remove(metadata);
            }
            if (n2 == 13) {
                metadata.setId(12);
            }
        }
        if (entityType.isOrHasParent(Entity1_11Types.EntityType.ZOMBIE)) {
            if ((entityType == Entity1_11Types.EntityType.ZOMBIE || entityType == Entity1_11Types.EntityType.HUSK) && metadata.id() == 14) {
                list.remove(metadata);
            } else if (metadata.id() == 15) {
                metadata.setId(14);
            } else if (metadata.id() == 14) {
                metadata.setId(15);
            }
        }
        if (entityType.isOrHasParent(Entity1_11Types.EntityType.ABSTRACT_HORSE)) {
            n2 = metadata.id();
            if (n2 == 14) {
                list.remove(metadata);
            }
            if (n2 == 16) {
                metadata.setId(14);
            }
            if (n2 == 17) {
                metadata.setId(16);
            }
            if (!(entityType.is((EntityType)Entity1_11Types.EntityType.HORSE) || metadata.id() != 15 && metadata.id() != 16)) {
                list.remove(metadata);
            }
            if ((entityType == Entity1_11Types.EntityType.DONKEY || entityType == Entity1_11Types.EntityType.MULE) && metadata.id() == 13) {
                if (((Byte)metadata.getValue() & 8) == 8) {
                    list.add(new Metadata(15, MetaType1_9.Boolean, true));
                } else {
                    list.add(new Metadata(15, MetaType1_9.Boolean, false));
                }
            }
        }
        if (entityType.is((EntityType)Entity1_11Types.EntityType.ARMOR_STAND) && Via.getConfig().isHologramPatch()) {
            EntityTracker1_11 entityTracker1_11;
            byte by;
            Metadata metadata2 = this.metaByIndex(11, list);
            Metadata metadata3 = this.metaByIndex(2, list);
            Metadata metadata4 = this.metaByIndex(3, list);
            if (metadata.id() == 0 && metadata2 != null && metadata3 != null && metadata4 != null && ((by = ((Byte)metadata.getValue()).byteValue()) & 0x20) == 32 && ((Byte)metadata2.getValue() & 1) == 1 && !((String)metadata3.getValue()).isEmpty() && ((Boolean)metadata4.getValue()).booleanValue() && (entityTracker1_11 = (EntityTracker1_11)this.tracker(userConnection)).addHologram(n)) {
                try {
                    PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_9_3.ENTITY_POSITION, null, userConnection);
                    packetWrapper.write(Type.VAR_INT, n);
                    packetWrapper.write(Type.SHORT, (short)0);
                    packetWrapper.write(Type.SHORT, (short)(128.0 * (-Via.getConfig().getHologramYOffset() * 32.0)));
                    packetWrapper.write(Type.SHORT, (short)0);
                    packetWrapper.write(Type.BOOLEAN, true);
                    packetWrapper.send(Protocol1_11To1_10.class);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_11Types.getTypeFromId(n, false);
    }

    @Override
    public EntityType objectTypeFromId(int n) {
        return Entity1_11Types.getTypeFromId(n, true);
    }

    public static Entity1_11Types.EntityType rewriteEntityType(int n, List<Metadata> list) {
        Entity1_11Types.EntityType entityType;
        block16: {
            Optional<Entity1_11Types.EntityType> optional = Entity1_11Types.EntityType.findById(n);
            if (!optional.isPresent()) {
                Via.getManager().getPlatform().getLogger().severe("Error: could not find Entity type " + n + " with metadata: " + list);
                return null;
            }
            entityType = optional.get();
            try {
                Optional<Metadata> optional2;
                if (entityType.is((EntityType)Entity1_11Types.EntityType.GUARDIAN) && (optional2 = MetadataRewriter1_11To1_10.getById(list, 12)).isPresent() && ((Byte)optional2.get().getValue() & 4) == 4) {
                    return Entity1_11Types.EntityType.ELDER_GUARDIAN;
                }
                if (entityType.is((EntityType)Entity1_11Types.EntityType.SKELETON) && (optional2 = MetadataRewriter1_11To1_10.getById(list, 12)).isPresent()) {
                    if ((Integer)optional2.get().getValue() == 1) {
                        return Entity1_11Types.EntityType.WITHER_SKELETON;
                    }
                    if ((Integer)optional2.get().getValue() == 2) {
                        return Entity1_11Types.EntityType.STRAY;
                    }
                }
                if (entityType.is((EntityType)Entity1_11Types.EntityType.ZOMBIE) && (optional2 = MetadataRewriter1_11To1_10.getById(list, 13)).isPresent()) {
                    int n2 = (Integer)optional2.get().getValue();
                    if (n2 > 0 && n2 < 6) {
                        list.add(new Metadata(16, MetaType1_9.VarInt, n2 - 1));
                        return Entity1_11Types.EntityType.ZOMBIE_VILLAGER;
                    }
                    if (n2 == 6) {
                        return Entity1_11Types.EntityType.HUSK;
                    }
                }
                if (entityType.is((EntityType)Entity1_11Types.EntityType.HORSE) && (optional2 = MetadataRewriter1_11To1_10.getById(list, 14)).isPresent()) {
                    if ((Integer)optional2.get().getValue() == 0) {
                        return Entity1_11Types.EntityType.HORSE;
                    }
                    if ((Integer)optional2.get().getValue() == 1) {
                        return Entity1_11Types.EntityType.DONKEY;
                    }
                    if ((Integer)optional2.get().getValue() == 2) {
                        return Entity1_11Types.EntityType.MULE;
                    }
                    if ((Integer)optional2.get().getValue() == 3) {
                        return Entity1_11Types.EntityType.ZOMBIE_HORSE;
                    }
                    if ((Integer)optional2.get().getValue() == 4) {
                        return Entity1_11Types.EntityType.SKELETON_HORSE;
                    }
                }
            } catch (Exception exception) {
                if (Via.getConfig().isSuppressMetadataErrors() && !Via.getManager().isDebug()) break block16;
                Via.getPlatform().getLogger().warning("An error occurred with entity type rewriter");
                Via.getPlatform().getLogger().warning("Metadata: " + list);
                exception.printStackTrace();
            }
        }
        return entityType;
    }

    public static Optional<Metadata> getById(List<Metadata> list, int n) {
        for (Metadata metadata : list) {
            if (metadata.id() != n) continue;
            return Optional.of(metadata);
        }
        return Optional.empty();
    }
}

