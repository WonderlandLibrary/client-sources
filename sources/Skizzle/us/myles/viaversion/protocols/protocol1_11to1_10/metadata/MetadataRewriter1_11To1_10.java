/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_11to1_10.metadata;

import java.util.List;
import java.util.Optional;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.entities.Entity1_11Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_9;
import us.myles.ViaVersion.api.rewriters.MetadataRewriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_11to1_10.EntityIdRewriter;
import us.myles.ViaVersion.protocols.protocol1_11to1_10.Protocol1_11To1_10;
import us.myles.ViaVersion.protocols.protocol1_11to1_10.storage.EntityTracker1_11;

public class MetadataRewriter1_11To1_10
extends MetadataRewriter {
    public MetadataRewriter1_11To1_10(Protocol1_11To1_10 protocol) {
        super(protocol, EntityTracker1_11.class);
    }

    @Override
    protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) {
        int oldid;
        if (metadata.getValue() instanceof Item) {
            EntityIdRewriter.toClientItem((Item)metadata.getValue());
        }
        if (type == null) {
            return;
        }
        if ((type.is((EntityType)Entity1_11Types.EntityType.ELDER_GUARDIAN) || type.is((EntityType)Entity1_11Types.EntityType.GUARDIAN)) && (oldid = metadata.getId()) == 12) {
            metadata.setMetaType(MetaType1_9.Boolean);
            boolean val = ((Byte)metadata.getValue() & 2) == 2;
            metadata.setValue(val);
        }
        if (type.isOrHasParent(Entity1_11Types.EntityType.ABSTRACT_SKELETON)) {
            oldid = metadata.getId();
            if (oldid == 12) {
                metadatas.remove(metadata);
            }
            if (oldid == 13) {
                metadata.setId(12);
            }
        }
        if (type.isOrHasParent(Entity1_11Types.EntityType.ZOMBIE)) {
            if (type.is(Entity1_11Types.EntityType.ZOMBIE, Entity1_11Types.EntityType.HUSK) && metadata.getId() == 14) {
                metadatas.remove(metadata);
            } else if (metadata.getId() == 15) {
                metadata.setId(14);
            } else if (metadata.getId() == 14) {
                metadata.setId(15);
            }
        }
        if (type.isOrHasParent(Entity1_11Types.EntityType.ABSTRACT_HORSE)) {
            oldid = metadata.getId();
            if (oldid == 14) {
                metadatas.remove(metadata);
            }
            if (oldid == 16) {
                metadata.setId(14);
            }
            if (oldid == 17) {
                metadata.setId(16);
            }
            if (!(type.is((EntityType)Entity1_11Types.EntityType.HORSE) || metadata.getId() != 15 && metadata.getId() != 16)) {
                metadatas.remove(metadata);
            }
            if (type.is(Entity1_11Types.EntityType.DONKEY, Entity1_11Types.EntityType.MULE) && metadata.getId() == 13) {
                if (((Byte)metadata.getValue() & 8) == 8) {
                    metadatas.add(new Metadata(15, MetaType1_9.Boolean, true));
                } else {
                    metadatas.add(new Metadata(15, MetaType1_9.Boolean, false));
                }
            }
        }
        if (type.is((EntityType)Entity1_11Types.EntityType.ARMOR_STAND) && Via.getConfig().isHologramPatch()) {
            EntityTracker1_11 tracker;
            byte data;
            Metadata flags = this.getMetaByIndex(11, metadatas);
            Metadata customName = this.getMetaByIndex(2, metadatas);
            Metadata customNameVisible = this.getMetaByIndex(3, metadatas);
            if (metadata.getId() == 0 && flags != null && customName != null && customNameVisible != null && ((data = ((Byte)metadata.getValue()).byteValue()) & 0x20) == 32 && ((Byte)flags.getValue() & 1) == 1 && !((String)customName.getValue()).isEmpty() && ((Boolean)customNameVisible.getValue()).booleanValue() && !(tracker = connection.get(EntityTracker1_11.class)).isHologram(entityId)) {
                tracker.addHologram(entityId);
                try {
                    PacketWrapper wrapper = new PacketWrapper(37, null, connection);
                    wrapper.write(Type.VAR_INT, entityId);
                    wrapper.write(Type.SHORT, (short)0);
                    wrapper.write(Type.SHORT, (short)(128.0 * (-Via.getConfig().getHologramYOffset() * 32.0)));
                    wrapper.write(Type.SHORT, (short)0);
                    wrapper.write(Type.BOOLEAN, true);
                    wrapper.send(Protocol1_11To1_10.class);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected EntityType getTypeFromId(int type) {
        return Entity1_11Types.getTypeFromId(type, false);
    }

    @Override
    protected EntityType getObjectTypeFromId(int type) {
        return Entity1_11Types.getTypeFromId(type, true);
    }

    public static Entity1_11Types.EntityType rewriteEntityType(int numType, List<Metadata> metadata) {
        Entity1_11Types.EntityType type;
        block16: {
            Optional<Entity1_11Types.EntityType> optType = Entity1_11Types.EntityType.findById(numType);
            if (!optType.isPresent()) {
                Via.getManager().getPlatform().getLogger().severe("Error: could not find Entity type " + numType + " with metadata: " + metadata);
                return null;
            }
            type = optType.get();
            try {
                Optional<Metadata> options;
                if (type.is((EntityType)Entity1_11Types.EntityType.GUARDIAN) && (options = MetadataRewriter1_11To1_10.getById(metadata, 12)).isPresent() && ((Byte)options.get().getValue() & 4) == 4) {
                    return Entity1_11Types.EntityType.ELDER_GUARDIAN;
                }
                if (type.is((EntityType)Entity1_11Types.EntityType.SKELETON) && (options = MetadataRewriter1_11To1_10.getById(metadata, 12)).isPresent()) {
                    if ((Integer)options.get().getValue() == 1) {
                        return Entity1_11Types.EntityType.WITHER_SKELETON;
                    }
                    if ((Integer)options.get().getValue() == 2) {
                        return Entity1_11Types.EntityType.STRAY;
                    }
                }
                if (type.is((EntityType)Entity1_11Types.EntityType.ZOMBIE) && (options = MetadataRewriter1_11To1_10.getById(metadata, 13)).isPresent()) {
                    int value = (Integer)options.get().getValue();
                    if (value > 0 && value < 6) {
                        metadata.add(new Metadata(16, MetaType1_9.VarInt, value - 1));
                        return Entity1_11Types.EntityType.ZOMBIE_VILLAGER;
                    }
                    if (value == 6) {
                        return Entity1_11Types.EntityType.HUSK;
                    }
                }
                if (type.is((EntityType)Entity1_11Types.EntityType.HORSE) && (options = MetadataRewriter1_11To1_10.getById(metadata, 14)).isPresent()) {
                    if ((Integer)options.get().getValue() == 0) {
                        return Entity1_11Types.EntityType.HORSE;
                    }
                    if ((Integer)options.get().getValue() == 1) {
                        return Entity1_11Types.EntityType.DONKEY;
                    }
                    if ((Integer)options.get().getValue() == 2) {
                        return Entity1_11Types.EntityType.MULE;
                    }
                    if ((Integer)options.get().getValue() == 3) {
                        return Entity1_11Types.EntityType.ZOMBIE_HORSE;
                    }
                    if ((Integer)options.get().getValue() == 4) {
                        return Entity1_11Types.EntityType.SKELETON_HORSE;
                    }
                }
            }
            catch (Exception e) {
                if (Via.getConfig().isSuppressMetadataErrors() && !Via.getManager().isDebug()) break block16;
                Via.getPlatform().getLogger().warning("An error occurred with entity type rewriter");
                Via.getPlatform().getLogger().warning("Metadata: " + metadata);
                e.printStackTrace();
            }
        }
        return type;
    }

    public static Optional<Metadata> getById(List<Metadata> metadatas, int id) {
        for (Metadata metadata : metadatas) {
            if (metadata.getId() != id) continue;
            return Optional.of(metadata);
        }
        return Optional.empty();
    }
}

