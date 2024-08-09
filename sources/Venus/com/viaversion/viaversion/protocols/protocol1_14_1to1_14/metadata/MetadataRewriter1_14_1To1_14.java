/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_14_1to1_14.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.Protocol1_14_1To1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_14_1To1_14
extends EntityRewriter<ClientboundPackets1_14, Protocol1_14_1To1_14> {
    public MetadataRewriter1_14_1To1_14(Protocol1_14_1To1_14 protocol1_14_1To1_14) {
        super(protocol1_14_1To1_14);
    }

    @Override
    public void handleMetadata(int n, EntityType entityType, Metadata metadata, List<Metadata> list, UserConnection userConnection) {
        if (entityType == null) {
            return;
        }
        if ((entityType == Entity1_14Types.VILLAGER || entityType == Entity1_14Types.WANDERING_TRADER) && metadata.id() >= 15) {
            metadata.setId(metadata.id() + 1);
        }
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_14Types.getTypeFromId(n);
    }
}

