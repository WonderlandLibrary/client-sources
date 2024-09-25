/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.CompressedNBTType;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.IntArrayType;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.ItemArrayType;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.ItemType;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.MetadataListType;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.MetadataType;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.NBTType;
import java.util.List;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.type.Type;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class Types1_7_6_10 {
    public static final Type<CompoundTag> COMPRESSED_NBT = new CompressedNBTType();
    public static final Type<Item[]> ITEM_ARRAY = new ItemArrayType(false);
    public static final Type<Item[]> COMPRESSED_NBT_ITEM_ARRAY = new ItemArrayType(true);
    public static final Type<Item> ITEM = new ItemType(false);
    public static final Type<Item> COMPRESSED_NBT_ITEM = new ItemType(true);
    public static final Type<List<Metadata>> METADATA_LIST = new MetadataListType();
    public static final Type<Metadata> METADATA = new MetadataType();
    public static final Type<CompoundTag> NBT = new NBTType();
    public static final Type<int[]> INT_ARRAY = new IntArrayType();
}

