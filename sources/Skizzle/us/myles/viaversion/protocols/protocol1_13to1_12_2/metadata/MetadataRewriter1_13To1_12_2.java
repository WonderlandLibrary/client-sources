/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.metadata;

import java.util.List;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.entities.Entity1_13Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_13;
import us.myles.ViaVersion.api.rewriters.MetadataRewriter;
import us.myles.ViaVersion.api.type.types.Particle;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ChatRewriter;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.EntityTypeRewriter;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.ParticleRewriter;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.storage.EntityTracker1_13;

public class MetadataRewriter1_13To1_12_2
extends MetadataRewriter {
    public MetadataRewriter1_13To1_12_2(Protocol1_13To1_12_2 protocol) {
        super(protocol, EntityTracker1_13.class);
    }

    @Override
    protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
        if (metadata.getMetaType().getTypeID() > 4) {
            metadata.setMetaType(MetaType1_13.byId(metadata.getMetaType().getTypeID() + 1));
        } else {
            metadata.setMetaType(MetaType1_13.byId(metadata.getMetaType().getTypeID()));
        }
        if (metadata.getId() == 2) {
            metadata.setMetaType(MetaType1_13.OptChat);
            if (metadata.getValue() != null && !((String)metadata.getValue()).isEmpty()) {
                metadata.setValue(ChatRewriter.legacyTextToJson((String)metadata.getValue()));
            } else {
                metadata.setValue(null);
            }
        }
        if (type == Entity1_13Types.EntityType.ENDERMAN && metadata.getId() == 12) {
            int stateId = (Integer)metadata.getValue();
            int id = stateId & 0xFFF;
            int data = stateId >> 12 & 0xF;
            metadata.setValue(id << 4 | data & 0xF);
        }
        if (metadata.getMetaType() == MetaType1_13.Slot) {
            metadata.setMetaType(MetaType1_13.Slot);
            InventoryPackets.toClient((Item)metadata.getValue());
        } else if (metadata.getMetaType() == MetaType1_13.BlockID) {
            metadata.setValue(WorldPackets.toNewId((Integer)metadata.getValue()));
        }
        if (type == null) {
            return;
        }
        if (type == Entity1_13Types.EntityType.WOLF && metadata.getId() == 17) {
            metadata.setValue(15 - (Integer)metadata.getValue());
        }
        if (type.isOrHasParent(Entity1_13Types.EntityType.ZOMBIE) && metadata.getId() > 14) {
            metadata.setId(metadata.getId() + 1);
        }
        if (type.isOrHasParent(Entity1_13Types.EntityType.MINECART_ABSTRACT) && metadata.getId() == 9) {
            int oldId = (Integer)metadata.getValue();
            int combined = (oldId & 0xFFF) << 4 | oldId >> 12 & 0xF;
            int newId = WorldPackets.toNewId(combined);
            metadata.setValue(newId);
        }
        if (type == Entity1_13Types.EntityType.AREA_EFFECT_CLOUD) {
            if (metadata.getId() == 9) {
                int particleId = (Integer)metadata.getValue();
                Metadata parameter1Meta = this.getMetaByIndex(10, metadatas);
                Metadata parameter2Meta = this.getMetaByIndex(11, metadatas);
                int parameter1 = parameter1Meta != null ? (Integer)parameter1Meta.getValue() : 0;
                int parameter2 = parameter2Meta != null ? (Integer)parameter2Meta.getValue() : 0;
                Particle particle = ParticleRewriter.rewriteParticle(particleId, new Integer[]{parameter1, parameter2});
                if (particle != null && particle.getId() != -1) {
                    metadatas.add(new Metadata(9, MetaType1_13.PARTICLE, particle));
                }
            }
            if (metadata.getId() >= 9) {
                metadatas.remove(metadata);
            }
        }
        if (metadata.getId() == 0) {
            metadata.setValue((byte)((Byte)metadata.getValue() & 0xFFFFFFEF));
        }
    }

    @Override
    public int getNewEntityId(int oldId) {
        return EntityTypeRewriter.getNewId(oldId);
    }

    @Override
    protected EntityType getTypeFromId(int type) {
        return Entity1_13Types.getTypeFromId(type, false);
    }

    @Override
    protected EntityType getObjectTypeFromId(int type) {
        return Entity1_13Types.getTypeFromId(type, true);
    }
}

