/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.EntityTypeRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_13To1_12_2
extends EntityRewriter<ClientboundPackets1_12_1, Protocol1_13To1_12_2> {
    public MetadataRewriter1_13To1_12_2(Protocol1_13To1_12_2 protocol1_13To1_12_2) {
        super(protocol1_13To1_12_2);
    }

    @Override
    protected void handleMetadata(int n, EntityType entityType, Metadata metadata, List<Metadata> list, UserConnection userConnection) throws Exception {
        int n2;
        int n3;
        int n4;
        if (metadata.metaType().typeId() > 4) {
            metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId() + 1));
        } else {
            metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
        }
        if (metadata.id() == 2) {
            if (metadata.getValue() != null && !((String)metadata.getValue()).isEmpty()) {
                metadata.setTypeAndValue(Types1_13.META_TYPES.optionalComponentType, ChatRewriter.legacyTextToJson((String)metadata.getValue()));
            } else {
                metadata.setTypeAndValue(Types1_13.META_TYPES.optionalComponentType, null);
            }
        }
        if (entityType == Entity1_13Types.EntityType.ENDERMAN && metadata.id() == 12) {
            n4 = (Integer)metadata.getValue();
            n3 = n4 & 0xFFF;
            n2 = n4 >> 12 & 0xF;
            metadata.setValue(n3 << 4 | n2 & 0xF);
        }
        if (metadata.metaType() == Types1_13.META_TYPES.itemType) {
            metadata.setMetaType(Types1_13.META_TYPES.itemType);
            ((Protocol1_13To1_12_2)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        } else if (metadata.metaType() == Types1_13.META_TYPES.blockStateType) {
            metadata.setValue(WorldPackets.toNewId((Integer)metadata.getValue()));
        }
        if (entityType == null) {
            return;
        }
        if (entityType == Entity1_13Types.EntityType.WOLF && metadata.id() == 17) {
            metadata.setValue(15 - (Integer)metadata.getValue());
        }
        if (entityType.isOrHasParent(Entity1_13Types.EntityType.ZOMBIE) && metadata.id() > 14) {
            metadata.setId(metadata.id() + 1);
        }
        if (entityType.isOrHasParent(Entity1_13Types.EntityType.MINECART_ABSTRACT) && metadata.id() == 9) {
            n4 = (Integer)metadata.getValue();
            n3 = (n4 & 0xFFF) << 4 | n4 >> 12 & 0xF;
            n2 = WorldPackets.toNewId(n3);
            metadata.setValue(n2);
        }
        if (entityType == Entity1_13Types.EntityType.AREA_EFFECT_CLOUD) {
            if (metadata.id() == 9) {
                n4 = (Integer)metadata.getValue();
                Metadata metadata2 = this.metaByIndex(10, list);
                Metadata metadata3 = this.metaByIndex(11, list);
                int n5 = metadata2 != null ? (Integer)metadata2.getValue() : 0;
                int n6 = metadata3 != null ? (Integer)metadata3.getValue() : 0;
                Particle particle = ParticleRewriter.rewriteParticle(n4, new Integer[]{n5, n6});
                if (particle != null && particle.getId() != -1) {
                    list.add(new Metadata(9, Types1_13.META_TYPES.particleType, particle));
                }
            }
            if (metadata.id() >= 9) {
                list.remove(metadata);
            }
        }
        if (metadata.id() == 0) {
            metadata.setValue((byte)((Byte)metadata.getValue() & 0xFFFFFFEF));
        }
    }

    @Override
    public int newEntityId(int n) {
        return EntityTypeRewriter.getNewId(n);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_13Types.getTypeFromId(n, false);
    }

    @Override
    public EntityType objectTypeFromId(int n) {
        return Entity1_13Types.getTypeFromId(n, true);
    }
}

