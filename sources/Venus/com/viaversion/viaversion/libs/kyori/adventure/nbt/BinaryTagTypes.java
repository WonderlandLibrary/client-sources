/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagScope;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.DoubleBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.EndBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.FloatBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ShortBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.StringBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.TrackingDataInput;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class BinaryTagTypes {
    public static final BinaryTagType<EndBinaryTag> END = BinaryTagType.register(EndBinaryTag.class, (byte)0, BinaryTagTypes::lambda$static$0, null);
    public static final BinaryTagType<ByteBinaryTag> BYTE = BinaryTagType.registerNumeric(ByteBinaryTag.class, (byte)1, BinaryTagTypes::lambda$static$1, BinaryTagTypes::lambda$static$2);
    public static final BinaryTagType<ShortBinaryTag> SHORT = BinaryTagType.registerNumeric(ShortBinaryTag.class, (byte)2, BinaryTagTypes::lambda$static$3, BinaryTagTypes::lambda$static$4);
    public static final BinaryTagType<IntBinaryTag> INT = BinaryTagType.registerNumeric(IntBinaryTag.class, (byte)3, BinaryTagTypes::lambda$static$5, BinaryTagTypes::lambda$static$6);
    public static final BinaryTagType<LongBinaryTag> LONG = BinaryTagType.registerNumeric(LongBinaryTag.class, (byte)4, BinaryTagTypes::lambda$static$7, BinaryTagTypes::lambda$static$8);
    public static final BinaryTagType<FloatBinaryTag> FLOAT = BinaryTagType.registerNumeric(FloatBinaryTag.class, (byte)5, BinaryTagTypes::lambda$static$9, BinaryTagTypes::lambda$static$10);
    public static final BinaryTagType<DoubleBinaryTag> DOUBLE = BinaryTagType.registerNumeric(DoubleBinaryTag.class, (byte)6, BinaryTagTypes::lambda$static$11, BinaryTagTypes::lambda$static$12);
    public static final BinaryTagType<ByteArrayBinaryTag> BYTE_ARRAY = BinaryTagType.register(ByteArrayBinaryTag.class, (byte)7, BinaryTagTypes::lambda$static$13, BinaryTagTypes::lambda$static$14);
    public static final BinaryTagType<StringBinaryTag> STRING = BinaryTagType.register(StringBinaryTag.class, (byte)8, BinaryTagTypes::lambda$static$15, BinaryTagTypes::lambda$static$16);
    public static final BinaryTagType<ListBinaryTag> LIST = BinaryTagType.register(ListBinaryTag.class, (byte)9, BinaryTagTypes::lambda$static$17, BinaryTagTypes::lambda$static$18);
    public static final BinaryTagType<CompoundBinaryTag> COMPOUND = BinaryTagType.register(CompoundBinaryTag.class, (byte)10, BinaryTagTypes::lambda$static$19, BinaryTagTypes::lambda$static$20);
    public static final BinaryTagType<IntArrayBinaryTag> INT_ARRAY = BinaryTagType.register(IntArrayBinaryTag.class, (byte)11, BinaryTagTypes::lambda$static$21, BinaryTagTypes::lambda$static$22);
    public static final BinaryTagType<LongArrayBinaryTag> LONG_ARRAY = BinaryTagType.register(LongArrayBinaryTag.class, (byte)12, BinaryTagTypes::lambda$static$23, BinaryTagTypes::lambda$static$24);

    private BinaryTagTypes() {
    }

    private static void lambda$static$24(LongArrayBinaryTag longArrayBinaryTag, DataOutput dataOutput) throws IOException {
        long[] lArray = LongArrayBinaryTagImpl.value(longArrayBinaryTag);
        int n = lArray.length;
        dataOutput.writeInt(n);
        for (int i = 0; i < n; ++i) {
            dataOutput.writeLong(lArray[i]);
        }
    }

    private static LongArrayBinaryTag lambda$static$23(DataInput dataInput) throws IOException {
        int n = dataInput.readInt();
        try (BinaryTagScope binaryTagScope = TrackingDataInput.enter(dataInput, (long)n * 8L);){
            long[] lArray = new long[n];
            for (int i = 0; i < n; ++i) {
                lArray[i] = dataInput.readLong();
            }
            LongArrayBinaryTag longArrayBinaryTag = LongArrayBinaryTag.of(lArray);
            return longArrayBinaryTag;
        }
    }

    private static void lambda$static$22(IntArrayBinaryTag intArrayBinaryTag, DataOutput dataOutput) throws IOException {
        int[] nArray = IntArrayBinaryTagImpl.value(intArrayBinaryTag);
        int n = nArray.length;
        dataOutput.writeInt(n);
        for (int i = 0; i < n; ++i) {
            dataOutput.writeInt(nArray[i]);
        }
    }

    private static IntArrayBinaryTag lambda$static$21(DataInput dataInput) throws IOException {
        int n = dataInput.readInt();
        try (BinaryTagScope binaryTagScope = TrackingDataInput.enter(dataInput, (long)n * 4L);){
            int[] nArray = new int[n];
            for (int i = 0; i < n; ++i) {
                nArray[i] = dataInput.readInt();
            }
            IntArrayBinaryTag intArrayBinaryTag = IntArrayBinaryTag.of(nArray);
            return intArrayBinaryTag;
        }
    }

    private static void lambda$static$20(CompoundBinaryTag compoundBinaryTag, DataOutput dataOutput) throws IOException {
        for (Map.Entry entry : compoundBinaryTag) {
            BinaryTag binaryTag = (BinaryTag)entry.getValue();
            if (binaryTag == null) continue;
            BinaryTagType<? extends BinaryTag> binaryTagType = binaryTag.type();
            dataOutput.writeByte(binaryTagType.id());
            if (binaryTagType == END) continue;
            dataOutput.writeUTF((String)entry.getKey());
            BinaryTagType.writeUntyped(binaryTagType, binaryTag, dataOutput);
        }
        dataOutput.writeByte(END.id());
    }

    private static CompoundBinaryTag lambda$static$19(DataInput dataInput) throws IOException {
        try (BinaryTagScope binaryTagScope = TrackingDataInput.enter(dataInput);){
            Object object;
            BinaryTagType<BinaryTag> binaryTagType;
            HashMap<String, BinaryTag> hashMap = new HashMap<String, BinaryTag>();
            while ((binaryTagType = BinaryTagType.of(dataInput.readByte())) != END) {
                object = dataInput.readUTF();
                BinaryTag binaryTag = binaryTagType.read(dataInput);
                hashMap.put((String)object, binaryTag);
            }
            object = new CompoundBinaryTagImpl(hashMap);
            return object;
        }
    }

    private static void lambda$static$18(ListBinaryTag listBinaryTag, DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(listBinaryTag.elementType().id());
        int n = listBinaryTag.size();
        dataOutput.writeInt(n);
        for (BinaryTag binaryTag : listBinaryTag) {
            BinaryTagType.writeUntyped(binaryTag.type(), binaryTag, dataOutput);
        }
    }

    private static ListBinaryTag lambda$static$17(DataInput dataInput) throws IOException {
        BinaryTagType<BinaryTag> binaryTagType = BinaryTagType.of(dataInput.readByte());
        int n = dataInput.readInt();
        try (BinaryTagScope binaryTagScope = TrackingDataInput.enter(dataInput, (long)n * 8L);){
            ArrayList<BinaryTag> arrayList = new ArrayList<BinaryTag>(n);
            for (int i = 0; i < n; ++i) {
                arrayList.add(binaryTagType.read(dataInput));
            }
            ListBinaryTag listBinaryTag = ListBinaryTag.of(binaryTagType, arrayList);
            return listBinaryTag;
        }
    }

    private static void lambda$static$16(StringBinaryTag stringBinaryTag, DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(stringBinaryTag.value());
    }

    private static StringBinaryTag lambda$static$15(DataInput dataInput) throws IOException {
        return StringBinaryTag.of(dataInput.readUTF());
    }

    private static void lambda$static$14(ByteArrayBinaryTag byteArrayBinaryTag, DataOutput dataOutput) throws IOException {
        byte[] byArray = ByteArrayBinaryTagImpl.value(byteArrayBinaryTag);
        dataOutput.writeInt(byArray.length);
        dataOutput.write(byArray);
    }

    private static ByteArrayBinaryTag lambda$static$13(DataInput dataInput) throws IOException {
        int n = dataInput.readInt();
        try (BinaryTagScope binaryTagScope = TrackingDataInput.enter(dataInput, n);){
            byte[] byArray = new byte[n];
            dataInput.readFully(byArray);
            ByteArrayBinaryTag byteArrayBinaryTag = ByteArrayBinaryTag.of(byArray);
            return byteArrayBinaryTag;
        }
    }

    private static void lambda$static$12(DoubleBinaryTag doubleBinaryTag, DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(doubleBinaryTag.value());
    }

    private static DoubleBinaryTag lambda$static$11(DataInput dataInput) throws IOException {
        return DoubleBinaryTag.of(dataInput.readDouble());
    }

    private static void lambda$static$10(FloatBinaryTag floatBinaryTag, DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(floatBinaryTag.value());
    }

    private static FloatBinaryTag lambda$static$9(DataInput dataInput) throws IOException {
        return FloatBinaryTag.of(dataInput.readFloat());
    }

    private static void lambda$static$8(LongBinaryTag longBinaryTag, DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(longBinaryTag.value());
    }

    private static LongBinaryTag lambda$static$7(DataInput dataInput) throws IOException {
        return LongBinaryTag.of(dataInput.readLong());
    }

    private static void lambda$static$6(IntBinaryTag intBinaryTag, DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(intBinaryTag.value());
    }

    private static IntBinaryTag lambda$static$5(DataInput dataInput) throws IOException {
        return IntBinaryTag.of(dataInput.readInt());
    }

    private static void lambda$static$4(ShortBinaryTag shortBinaryTag, DataOutput dataOutput) throws IOException {
        dataOutput.writeShort(shortBinaryTag.value());
    }

    private static ShortBinaryTag lambda$static$3(DataInput dataInput) throws IOException {
        return ShortBinaryTag.of(dataInput.readShort());
    }

    private static void lambda$static$2(ByteBinaryTag byteBinaryTag, DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(byteBinaryTag.value());
    }

    private static ByteBinaryTag lambda$static$1(DataInput dataInput) throws IOException {
        return ByteBinaryTag.of(dataInput.readByte());
    }

    private static EndBinaryTag lambda$static$0(DataInput dataInput) throws IOException {
        return EndBinaryTag.get();
    }
}

