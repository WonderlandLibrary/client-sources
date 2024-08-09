/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.EntityPackets;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_15To1_14_4
extends EntityRewriter<ClientboundPackets1_14_4, Protocol1_15To1_14_4> {
    public MetadataRewriter1_15To1_14_4(Protocol1_15To1_14_4 protocol1_15To1_14_4) {
        super(protocol1_15To1_14_4);
    }

    @Override
    public void handleMetadata(int n, EntityType entityType, Metadata metadata, List<Metadata> list, UserConnection userConnection) throws Exception {
        int n2;
        if (metadata.metaType() == Types1_14.META_TYPES.itemType) {
            ((Protocol1_15To1_14_4)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        } else if (metadata.metaType() == Types1_14.META_TYPES.blockStateType) {
            n2 = (Integer)metadata.getValue();
            metadata.setValue(((Protocol1_15To1_14_4)this.protocol).getMappingData().getNewBlockStateId(n2));
        } else if (metadata.metaType() == Types1_14.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
        if (entityType == null) {
            return;
        }
        if (entityType.isOrHasParent(Entity1_15Types.MINECART_ABSTRACT) && metadata.id() == 10) {
            n2 = (Integer)metadata.getValue();
            metadata.setValue(((Protocol1_15To1_14_4)this.protocol).getMappingData().getNewBlockStateId(n2));
        }
        if (metadata.id() > 11 && entityType.isOrHasParent(Entity1_15Types.LIVINGENTITY)) {
            metadata.setId(metadata.id() + 1);
        }
        if (entityType.isOrHasParent(Entity1_15Types.WOLF)) {
            if (metadata.id() == 18) {
                list.remove(metadata);
            } else if (metadata.id() > 18) {
                metadata.setId(metadata.id() - 1);
            }
        }
    }

    @Override
    public int newEntityId(int n) {
        return EntityPackets.getNewEntityId(n);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_15Types.getTypeFromId(n);
    }
}

