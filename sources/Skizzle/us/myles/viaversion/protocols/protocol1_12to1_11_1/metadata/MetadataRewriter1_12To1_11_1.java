/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_12to1_11_1.metadata;

import java.util.List;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.entities.Entity1_12Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.rewriters.MetadataRewriter;
import us.myles.ViaVersion.protocols.protocol1_12to1_11_1.BedRewriter;
import us.myles.ViaVersion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1;
import us.myles.ViaVersion.protocols.protocol1_12to1_11_1.storage.EntityTracker1_12;

public class MetadataRewriter1_12To1_11_1
extends MetadataRewriter {
    public MetadataRewriter1_12To1_11_1(Protocol1_12To1_11_1 protocol) {
        super(protocol, EntityTracker1_12.class);
    }

    @Override
    protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) {
        if (metadata.getValue() instanceof Item) {
            BedRewriter.toClientItem((Item)metadata.getValue());
        }
        if (type == null) {
            return;
        }
        if (type == Entity1_12Types.EntityType.EVOCATION_ILLAGER && metadata.getId() == 12) {
            metadata.setId(13);
        }
    }

    @Override
    protected EntityType getTypeFromId(int type) {
        return Entity1_12Types.getTypeFromId(type, false);
    }

    @Override
    protected EntityType getObjectTypeFromId(int type) {
        return Entity1_12Types.getTypeFromId(type, true);
    }
}

