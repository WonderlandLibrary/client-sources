/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetaIndex;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;
import java.util.UUID;

public class MetadataRewriter1_9To1_8
extends EntityRewriter<ClientboundPackets1_8, Protocol1_9To1_8> {
    public MetadataRewriter1_9To1_8(Protocol1_9To1_8 protocol1_9To1_8) {
        super(protocol1_9To1_8);
    }

    @Override
    protected void handleMetadata(int n, EntityType entityType, Metadata metadata, List<Metadata> list, UserConnection userConnection) throws Exception {
        MetaIndex metaIndex = MetaIndex.searchIndex(entityType, metadata.id());
        if (metaIndex == null) {
            throw new Exception("Could not find valid metadata");
        }
        if (metaIndex.getNewType() == null) {
            list.remove(metadata);
            return;
        }
        metadata.setId(metaIndex.getNewIndex());
        metadata.setMetaTypeUnsafe(metaIndex.getNewType());
        Object object = metadata.getValue();
        switch (1.$SwitchMap$com$viaversion$viaversion$api$minecraft$metadata$types$MetaType1_9[metaIndex.getNewType().ordinal()]) {
            case 1: {
                if (metaIndex.getOldType() == MetaType1_8.Byte) {
                    metadata.setValue(object);
                }
                if (metaIndex.getOldType() == MetaType1_8.Int) {
                    metadata.setValue(((Integer)object).byteValue());
                }
                if (metaIndex != MetaIndex.ENTITY_STATUS || entityType != Entity1_10Types.EntityType.PLAYER) break;
                Byte by = 0;
                if (((Byte)object & 0x10) == 16) {
                    by = 1;
                }
                int n2 = MetaIndex.PLAYER_HAND.getNewIndex();
                MetaType1_9 metaType1_9 = MetaIndex.PLAYER_HAND.getNewType();
                list.add(new Metadata(n2, metaType1_9, by));
                break;
            }
            case 2: {
                String string = (String)object;
                UUID uUID = null;
                if (!string.isEmpty()) {
                    try {
                        uUID = UUID.fromString(string);
                    } catch (Exception exception) {
                        // empty catch block
                    }
                }
                metadata.setValue(uUID);
                break;
            }
            case 3: {
                if (metaIndex.getOldType() == MetaType1_8.Byte) {
                    metadata.setValue(((Byte)object).intValue());
                }
                if (metaIndex.getOldType() == MetaType1_8.Short) {
                    metadata.setValue(((Short)object).intValue());
                }
                if (metaIndex.getOldType() != MetaType1_8.Int) break;
                metadata.setValue(object);
                break;
            }
            case 4: 
            case 5: {
                metadata.setValue(object);
                break;
            }
            case 6: {
                if (metaIndex == MetaIndex.AGEABLE_AGE) {
                    metadata.setValue((Byte)object < 0);
                    break;
                }
                metadata.setValue((Byte)object != 0);
                break;
            }
            case 7: {
                metadata.setValue(object);
                ItemRewriter.toClient((Item)metadata.getValue());
                break;
            }
            case 8: {
                Vector vector = (Vector)object;
                metadata.setValue(vector);
                break;
            }
            case 9: {
                EulerAngle eulerAngle = (EulerAngle)object;
                metadata.setValue(eulerAngle);
                break;
            }
            case 10: {
                object = Protocol1_9To1_8.fixJson(object.toString());
                metadata.setValue(object);
                break;
            }
            case 11: {
                metadata.setValue(((Number)object).intValue());
                break;
            }
            default: {
                list.remove(metadata);
                throw new Exception("Unhandled MetaDataType: " + metaIndex.getNewType());
            }
        }
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_10Types.getTypeFromId(n, false);
    }

    @Override
    public EntityType objectTypeFromId(int n) {
        return Entity1_10Types.getTypeFromId(n, true);
    }
}

