/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_16_2To1_16_1
extends EntityRewriter<ClientboundPackets1_16, Protocol1_16_2To1_16_1> {
    public MetadataRewriter1_16_2To1_16_1(Protocol1_16_2To1_16_1 protocol1_16_2To1_16_1) {
        super(protocol1_16_2To1_16_1);
        this.mapTypes(Entity1_16Types.values(), Entity1_16_2Types.class);
    }

    @Override
    public void handleMetadata(int n, EntityType entityType, Metadata metadata, List<Metadata> list, UserConnection userConnection) throws Exception {
        int n2;
        if (metadata.metaType() == Types1_16.META_TYPES.itemType) {
            ((Protocol1_16_2To1_16_1)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        } else if (metadata.metaType() == Types1_16.META_TYPES.blockStateType) {
            n2 = (Integer)metadata.getValue();
            metadata.setValue(((Protocol1_16_2To1_16_1)this.protocol).getMappingData().getNewBlockStateId(n2));
        } else if (metadata.metaType() == Types1_16.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
        if (entityType == null) {
            return;
        }
        if (entityType.isOrHasParent(Entity1_16_2Types.MINECART_ABSTRACT) && metadata.id() == 10) {
            n2 = (Integer)metadata.getValue();
            metadata.setValue(((Protocol1_16_2To1_16_1)this.protocol).getMappingData().getNewBlockStateId(n2));
        }
        if (entityType.isOrHasParent(Entity1_16_2Types.ABSTRACT_PIGLIN)) {
            if (metadata.id() == 15) {
                metadata.setId(16);
            } else if (metadata.id() == 16) {
                metadata.setId(15);
            }
        }
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_16_2Types.getTypeFromId(n);
    }
}

