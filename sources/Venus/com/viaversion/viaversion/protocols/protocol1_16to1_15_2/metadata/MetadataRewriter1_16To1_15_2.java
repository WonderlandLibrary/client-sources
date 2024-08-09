/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_16To1_15_2
extends EntityRewriter<ClientboundPackets1_15, Protocol1_16To1_15_2> {
    public MetadataRewriter1_16To1_15_2(Protocol1_16To1_15_2 protocol1_16To1_15_2) {
        super(protocol1_16To1_15_2);
        this.mapEntityType(Entity1_15Types.ZOMBIE_PIGMAN, Entity1_16Types.ZOMBIFIED_PIGLIN);
        this.mapTypes(Entity1_15Types.values(), Entity1_16Types.class);
    }

    @Override
    public void handleMetadata(int n, EntityType entityType, Metadata metadata, List<Metadata> list, UserConnection userConnection) throws Exception {
        int n2;
        metadata.setMetaType(Types1_16.META_TYPES.byId(metadata.metaType().typeId()));
        if (metadata.metaType() == Types1_16.META_TYPES.itemType) {
            ((Protocol1_16To1_15_2)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        } else if (metadata.metaType() == Types1_16.META_TYPES.blockStateType) {
            n2 = (Integer)metadata.getValue();
            metadata.setValue(((Protocol1_16To1_15_2)this.protocol).getMappingData().getNewBlockStateId(n2));
        } else if (metadata.metaType() == Types1_16.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
        if (entityType == null) {
            return;
        }
        if (entityType.isOrHasParent(Entity1_16Types.MINECART_ABSTRACT) && metadata.id() == 10) {
            n2 = (Integer)metadata.getValue();
            metadata.setValue(((Protocol1_16To1_15_2)this.protocol).getMappingData().getNewBlockStateId(n2));
        }
        if (entityType.isOrHasParent(Entity1_16Types.ABSTRACT_ARROW)) {
            if (metadata.id() == 8) {
                list.remove(metadata);
            } else if (metadata.id() > 8) {
                metadata.setId(metadata.id() - 1);
            }
        }
        if (entityType == Entity1_16Types.WOLF && metadata.id() == 16) {
            n2 = ((Byte)metadata.value()).byteValue();
            int n3 = (n2 & 2) != 0 ? Integer.MAX_VALUE : 0;
            list.add(new Metadata(20, Types1_16.META_TYPES.varIntType, n3));
        }
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_16Types.getTypeFromId(n);
    }
}

