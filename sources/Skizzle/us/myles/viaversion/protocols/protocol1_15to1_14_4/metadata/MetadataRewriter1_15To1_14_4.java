/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_15to1_14_4.metadata;

import java.util.List;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.entities.Entity1_15Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_14;
import us.myles.ViaVersion.api.rewriters.MetadataRewriter;
import us.myles.ViaVersion.api.type.types.Particle;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.packets.EntityPackets;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.packets.InventoryPackets;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.storage.EntityTracker1_15;

public class MetadataRewriter1_15To1_14_4
extends MetadataRewriter {
    public MetadataRewriter1_15To1_14_4(Protocol1_15To1_14_4 protocol) {
        super(protocol, EntityTracker1_15.class);
    }

    @Override
    public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
        if (metadata.getMetaType() == MetaType1_14.Slot) {
            InventoryPackets.toClient((Item)metadata.getValue());
        } else if (metadata.getMetaType() == MetaType1_14.BlockID) {
            int data = (Integer)metadata.getValue();
            metadata.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
        }
        if (type == null) {
            return;
        }
        if (metadata.getId() > 11 && type.isOrHasParent(Entity1_15Types.EntityType.LIVINGENTITY)) {
            metadata.setId(metadata.getId() + 1);
        }
        if (type.isOrHasParent(Entity1_15Types.EntityType.WOLF)) {
            if (metadata.getId() == 18) {
                metadatas.remove(metadata);
            } else if (metadata.getId() > 18) {
                metadata.setId(metadata.getId() - 1);
            }
        } else if (type == Entity1_15Types.EntityType.AREA_EFFECT_CLOUD && metadata.getId() == 10) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
    }

    @Override
    public int getNewEntityId(int oldId) {
        return EntityPackets.getNewEntityId(oldId);
    }

    @Override
    protected EntityType getTypeFromId(int type) {
        return Entity1_15Types.getTypeFromId(type);
    }
}

