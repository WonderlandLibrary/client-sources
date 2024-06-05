/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.EulerAngle;
import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetaIndex;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;
import java.util.UUID;

public class MetadataRewriter1_9To1_8
extends EntityRewriter<Protocol1_9To1_8> {
    public MetadataRewriter1_9To1_8(Protocol1_9To1_8 protocol) {
        super(protocol);
    }

    @Override
    protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
        MetaIndex metaIndex = MetaIndex.searchIndex(type, metadata.id());
        if (metaIndex == null) {
            throw new Exception("Could not find valid metadata");
        }
        if (metaIndex.getNewType() == null) {
            metadatas.remove(metadata);
            return;
        }
        metadata.setId(metaIndex.getNewIndex());
        metadata.setMetaTypeUnsafe(metaIndex.getNewType());
        Object value = metadata.getValue();
        switch (1.$SwitchMap$com$viaversion$viaversion$api$minecraft$metadata$types$MetaType1_9[metaIndex.getNewType().ordinal()]) {
            case 1: {
                if (metaIndex.getOldType() == MetaType1_8.Byte) {
                    metadata.setValue(value);
                }
                if (metaIndex.getOldType() == MetaType1_8.Int) {
                    metadata.setValue(((Integer)value).byteValue());
                }
                if (metaIndex != MetaIndex.ENTITY_STATUS) return;
                if (type != Entity1_10Types.EntityType.PLAYER) return;
                Byte val = 0;
                if (((Byte)value & 0x10) == 16) {
                    val = 1;
                }
                int newIndex = MetaIndex.PLAYER_HAND.getNewIndex();
                MetaType1_9 metaType = MetaIndex.PLAYER_HAND.getNewType();
                metadatas.add(new Metadata(newIndex, metaType, val));
                return;
            }
            case 2: {
                String owner = (String)value;
                UUID toWrite = null;
                if (!owner.isEmpty()) {
                    try {
                        toWrite = UUID.fromString(owner);
                    }
                    catch (Exception metaType) {
                        // empty catch block
                    }
                }
                metadata.setValue(toWrite);
                return;
            }
            case 3: {
                if (metaIndex.getOldType() == MetaType1_8.Byte) {
                    metadata.setValue(((Byte)value).intValue());
                }
                if (metaIndex.getOldType() == MetaType1_8.Short) {
                    metadata.setValue(((Short)value).intValue());
                }
                if (metaIndex.getOldType() != MetaType1_8.Int) return;
                metadata.setValue(value);
                return;
            }
            case 4: {
                metadata.setValue(value);
                return;
            }
            case 5: {
                metadata.setValue(value);
                return;
            }
            case 6: {
                if (metaIndex == MetaIndex.AGEABLE_AGE) {
                    metadata.setValue((Byte)value < 0);
                    return;
                }
                metadata.setValue((Byte)value != 0);
                return;
            }
            case 7: {
                metadata.setValue(value);
                ItemRewriter.toClient((Item)metadata.getValue());
                return;
            }
            case 8: {
                Vector vector = (Vector)value;
                metadata.setValue(vector);
                return;
            }
            case 9: {
                EulerAngle angle = (EulerAngle)value;
                metadata.setValue(angle);
                return;
            }
            case 10: {
                value = Protocol1_9To1_8.fixJson(value.toString());
                metadata.setValue(value);
                return;
            }
            case 11: {
                metadata.setValue(((Number)value).intValue());
                return;
            }
        }
        metadatas.remove(metadata);
        throw new Exception("Unhandled MetaDataType: " + metaIndex.getNewType());
    }

    @Override
    public EntityType typeFromId(int type) {
        return Entity1_10Types.getTypeFromId(type, false);
    }

    @Override
    public EntityType objectTypeFromId(int type) {
        return Entity1_10Types.getTypeFromId(type, true);
    }
}

