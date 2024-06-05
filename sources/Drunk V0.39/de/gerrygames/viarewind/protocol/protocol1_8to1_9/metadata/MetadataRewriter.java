/*
 * Decompiled with CFR 0.152.
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata;

import com.viaversion.viaversion.api.minecraft.EulerAngle;
import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetaIndex;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.MetaIndex1_8to1_9;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class MetadataRewriter {
    public static void transform(Entity1_10Types.EntityType type, List<Metadata> list) {
        Iterator<Metadata> iterator = new ArrayList<Metadata>(list).iterator();
        while (iterator.hasNext()) {
            Metadata entry = iterator.next();
            MetaIndex metaIndex = MetaIndex1_8to1_9.searchIndex(type, entry.id());
            try {
                if (metaIndex == null) throw new Exception("Could not find valid metadata");
                if (metaIndex.getOldType() == MetaType1_8.NonExistent || metaIndex.getNewType() == null) {
                    list.remove(entry);
                    continue;
                }
                Object value = entry.getValue();
                entry.setMetaTypeUnsafe(metaIndex.getOldType());
                entry.setId(metaIndex.getIndex());
                switch (1.$SwitchMap$com$viaversion$viaversion$api$minecraft$metadata$types$MetaType1_9[metaIndex.getNewType().ordinal()]) {
                    case 1: {
                        if (metaIndex.getOldType() == MetaType1_8.Byte) {
                            entry.setValue(value);
                        }
                        if (metaIndex.getOldType() != MetaType1_8.Int) break;
                        entry.setValue(((Byte)value).intValue());
                        break;
                    }
                    case 2: {
                        if (metaIndex.getOldType() != MetaType1_8.String) {
                            list.remove(entry);
                            break;
                        }
                        UUID owner = (UUID)value;
                        if (owner == null) {
                            entry.setValue("");
                            break;
                        }
                        entry.setValue(owner.toString());
                        break;
                    }
                    case 3: {
                        list.remove(entry);
                        list.add(new Metadata(metaIndex.getIndex(), MetaType1_8.Short, ((Integer)value).shortValue()));
                        break;
                    }
                    case 4: {
                        if (metaIndex.getOldType() == MetaType1_8.Byte) {
                            entry.setValue(((Integer)value).byteValue());
                        }
                        if (metaIndex.getOldType() == MetaType1_8.Short) {
                            entry.setValue(((Integer)value).shortValue());
                        }
                        if (metaIndex.getOldType() != MetaType1_8.Int) break;
                        entry.setValue(value);
                        break;
                    }
                    case 5: {
                        entry.setValue(value);
                        break;
                    }
                    case 6: {
                        entry.setValue(value);
                        break;
                    }
                    case 7: {
                        if (metaIndex == MetaIndex.AGEABLE_AGE) {
                            entry.setValue((byte)((Boolean)value != false ? -1 : 0));
                            break;
                        }
                        entry.setValue((byte)((Boolean)value != false ? 1 : 0));
                        break;
                    }
                    case 8: {
                        entry.setValue(ItemRewriter.toClient((Item)value));
                        break;
                    }
                    case 9: {
                        Vector vector = (Vector)value;
                        entry.setValue(vector);
                        break;
                    }
                    case 10: {
                        EulerAngle angle = (EulerAngle)value;
                        entry.setValue(angle);
                        break;
                    }
                    case 11: {
                        entry.setValue(value);
                        break;
                    }
                    default: {
                        ViaRewind.getPlatform().getLogger().warning("[Out] Unhandled MetaDataType: " + metaIndex.getNewType());
                        list.remove(entry);
                    }
                }
                if (metaIndex.getOldType().type().getOutputClass().isAssignableFrom(entry.getValue().getClass())) continue;
                list.remove(entry);
            }
            catch (Exception e) {
                list.remove(entry);
            }
        }
    }
}

