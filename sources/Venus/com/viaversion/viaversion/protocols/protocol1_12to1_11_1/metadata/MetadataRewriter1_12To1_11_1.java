/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_12To1_11_1
extends EntityRewriter<ClientboundPackets1_9_3, Protocol1_12To1_11_1> {
    public MetadataRewriter1_12To1_11_1(Protocol1_12To1_11_1 protocol1_12To1_11_1) {
        super(protocol1_12To1_11_1);
    }

    @Override
    protected void handleMetadata(int n, EntityType entityType, Metadata metadata, List<Metadata> list, UserConnection userConnection) {
        if (metadata.getValue() instanceof Item) {
            metadata.setValue(((Protocol1_12To1_11_1)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue()));
        }
        if (entityType == null) {
            return;
        }
        if (entityType == Entity1_12Types.EntityType.EVOCATION_ILLAGER && metadata.id() == 12) {
            metadata.setId(13);
        }
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_12Types.getTypeFromId(n, false);
    }

    @Override
    public EntityType objectTypeFromId(int n) {
        return Entity1_12Types.getTypeFromId(n, true);
    }
}

