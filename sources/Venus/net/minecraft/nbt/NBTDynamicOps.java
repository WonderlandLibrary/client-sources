/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.PeekingIterator;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CollectionNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.EndNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.nbt.StringNBT;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NBTDynamicOps
implements DynamicOps<INBT> {
    public static final NBTDynamicOps INSTANCE = new NBTDynamicOps();

    protected NBTDynamicOps() {
    }

    @Override
    public INBT empty() {
        return EndNBT.INSTANCE;
    }

    @Override
    public <U> U convertTo(DynamicOps<U> dynamicOps, INBT iNBT) {
        switch (iNBT.getId()) {
            case 0: {
                return dynamicOps.empty();
            }
            case 1: {
                return dynamicOps.createByte(((NumberNBT)iNBT).getByte());
            }
            case 2: {
                return dynamicOps.createShort(((NumberNBT)iNBT).getShort());
            }
            case 3: {
                return dynamicOps.createInt(((NumberNBT)iNBT).getInt());
            }
            case 4: {
                return dynamicOps.createLong(((NumberNBT)iNBT).getLong());
            }
            case 5: {
                return dynamicOps.createFloat(((NumberNBT)iNBT).getFloat());
            }
            case 6: {
                return dynamicOps.createDouble(((NumberNBT)iNBT).getDouble());
            }
            case 7: {
                return dynamicOps.createByteList(ByteBuffer.wrap(((ByteArrayNBT)iNBT).getByteArray()));
            }
            case 8: {
                return dynamicOps.createString(iNBT.getString());
            }
            case 9: {
                return this.convertList(dynamicOps, iNBT);
            }
            case 10: {
                return this.convertMap(dynamicOps, iNBT);
            }
            case 11: {
                return dynamicOps.createIntList(Arrays.stream(((IntArrayNBT)iNBT).getIntArray()));
            }
            case 12: {
                return dynamicOps.createLongList(Arrays.stream(((LongArrayNBT)iNBT).getAsLongArray()));
            }
        }
        throw new IllegalStateException("Unknown tag type: " + iNBT);
    }

    @Override
    public DataResult<Number> getNumberValue(INBT iNBT) {
        return iNBT instanceof NumberNBT ? DataResult.success(((NumberNBT)iNBT).getAsNumber()) : DataResult.error("Not a number");
    }

    @Override
    public INBT createNumeric(Number number) {
        return DoubleNBT.valueOf(number.doubleValue());
    }

    @Override
    public INBT createByte(byte by) {
        return ByteNBT.valueOf(by);
    }

    @Override
    public INBT createShort(short s) {
        return ShortNBT.valueOf(s);
    }

    @Override
    public INBT createInt(int n) {
        return IntNBT.valueOf(n);
    }

    @Override
    public INBT createLong(long l) {
        return LongNBT.valueOf(l);
    }

    @Override
    public INBT createFloat(float f) {
        return FloatNBT.valueOf(f);
    }

    @Override
    public INBT createDouble(double d) {
        return DoubleNBT.valueOf(d);
    }

    @Override
    public INBT createBoolean(boolean bl) {
        return ByteNBT.valueOf(bl);
    }

    @Override
    public DataResult<String> getStringValue(INBT iNBT) {
        return iNBT instanceof StringNBT ? DataResult.success(iNBT.getString()) : DataResult.error("Not a string");
    }

    @Override
    public INBT createString(String string) {
        return StringNBT.valueOf(string);
    }

    private static CollectionNBT<?> func_240602_a_(byte by, byte by2) {
        if (NBTDynamicOps.func_240603_a_(by, by2, (byte)4)) {
            return new LongArrayNBT(new long[0]);
        }
        if (NBTDynamicOps.func_240603_a_(by, by2, (byte)1)) {
            return new ByteArrayNBT(new byte[0]);
        }
        return NBTDynamicOps.func_240603_a_(by, by2, (byte)3) ? new IntArrayNBT(new int[0]) : new ListNBT();
    }

    private static boolean func_240603_a_(byte by, byte by2, byte by3) {
        return by == by3 && (by2 == by3 || by2 == 0);
    }

    private static <T extends INBT> void func_240609_a_(CollectionNBT<T> collectionNBT, INBT iNBT, INBT iNBT2) {
        if (iNBT instanceof CollectionNBT) {
            CollectionNBT collectionNBT2 = (CollectionNBT)iNBT;
            collectionNBT2.forEach(arg_0 -> NBTDynamicOps.lambda$func_240609_a_$0(collectionNBT, arg_0));
        }
        collectionNBT.add(iNBT2);
    }

    private static <T extends INBT> void func_240608_a_(CollectionNBT<T> collectionNBT, INBT iNBT, List<INBT> list) {
        if (iNBT instanceof CollectionNBT) {
            CollectionNBT collectionNBT2 = (CollectionNBT)iNBT;
            collectionNBT2.forEach(arg_0 -> NBTDynamicOps.lambda$func_240608_a_$1(collectionNBT, arg_0));
        }
        list.forEach(arg_0 -> NBTDynamicOps.lambda$func_240608_a_$2(collectionNBT, arg_0));
    }

    @Override
    public DataResult<INBT> mergeToList(INBT iNBT, INBT iNBT2) {
        if (!(iNBT instanceof CollectionNBT) && !(iNBT instanceof EndNBT)) {
            return DataResult.error("mergeToList called with not a list: " + iNBT, iNBT);
        }
        CollectionNBT<?> collectionNBT = NBTDynamicOps.func_240602_a_(iNBT instanceof CollectionNBT ? ((CollectionNBT)iNBT).getTagType() : (byte)0, iNBT2.getId());
        NBTDynamicOps.func_240609_a_(collectionNBT, iNBT, iNBT2);
        return DataResult.success(collectionNBT);
    }

    @Override
    public DataResult<INBT> mergeToList(INBT iNBT, List<INBT> list) {
        if (!(iNBT instanceof CollectionNBT) && !(iNBT instanceof EndNBT)) {
            return DataResult.error("mergeToList called with not a list: " + iNBT, iNBT);
        }
        CollectionNBT<?> collectionNBT = NBTDynamicOps.func_240602_a_(iNBT instanceof CollectionNBT ? ((CollectionNBT)iNBT).getTagType() : (byte)0, list.stream().findFirst().map(INBT::getId).orElse((byte)0));
        NBTDynamicOps.func_240608_a_(collectionNBT, iNBT, list);
        return DataResult.success(collectionNBT);
    }

    @Override
    public DataResult<INBT> mergeToMap(INBT iNBT, INBT iNBT2, INBT iNBT3) {
        if (!(iNBT instanceof CompoundNBT) && !(iNBT instanceof EndNBT)) {
            return DataResult.error("mergeToMap called with not a map: " + iNBT, iNBT);
        }
        if (!(iNBT2 instanceof StringNBT)) {
            return DataResult.error("key is not a string: " + iNBT2, iNBT);
        }
        CompoundNBT compoundNBT = new CompoundNBT();
        if (iNBT instanceof CompoundNBT) {
            CompoundNBT compoundNBT2 = (CompoundNBT)iNBT;
            compoundNBT2.keySet().forEach(arg_0 -> NBTDynamicOps.lambda$mergeToMap$3(compoundNBT, compoundNBT2, arg_0));
        }
        compoundNBT.put(iNBT2.getString(), iNBT3);
        return DataResult.success(compoundNBT);
    }

    @Override
    public DataResult<INBT> mergeToMap(INBT iNBT, MapLike<INBT> mapLike) {
        Object object;
        if (!(iNBT instanceof CompoundNBT) && !(iNBT instanceof EndNBT)) {
            return DataResult.error("mergeToMap called with not a map: " + iNBT, iNBT);
        }
        CompoundNBT compoundNBT = new CompoundNBT();
        if (iNBT instanceof CompoundNBT) {
            object = (CompoundNBT)iNBT;
            ((CompoundNBT)object).keySet().forEach(arg_0 -> NBTDynamicOps.lambda$mergeToMap$4(compoundNBT, (CompoundNBT)object, arg_0));
        }
        object = Lists.newArrayList();
        mapLike.entries().forEach(arg_0 -> NBTDynamicOps.lambda$mergeToMap$5((List)object, compoundNBT, arg_0));
        return !object.isEmpty() ? DataResult.error("some keys are not strings: " + (List)object, compoundNBT) : DataResult.success(compoundNBT);
    }

    @Override
    public DataResult<Stream<Pair<INBT, INBT>>> getMapValues(INBT iNBT) {
        if (!(iNBT instanceof CompoundNBT)) {
            return DataResult.error("Not a map: " + iNBT);
        }
        CompoundNBT compoundNBT = (CompoundNBT)iNBT;
        return DataResult.success(compoundNBT.keySet().stream().map(arg_0 -> this.lambda$getMapValues$6(compoundNBT, arg_0)));
    }

    @Override
    public DataResult<Consumer<BiConsumer<INBT, INBT>>> getMapEntries(INBT iNBT) {
        if (!(iNBT instanceof CompoundNBT)) {
            return DataResult.error("Not a map: " + iNBT);
        }
        CompoundNBT compoundNBT = (CompoundNBT)iNBT;
        return DataResult.success(arg_0 -> this.lambda$getMapEntries$8(compoundNBT, arg_0));
    }

    @Override
    public DataResult<MapLike<INBT>> getMap(INBT iNBT) {
        if (!(iNBT instanceof CompoundNBT)) {
            return DataResult.error("Not a map: " + iNBT);
        }
        CompoundNBT compoundNBT = (CompoundNBT)iNBT;
        return DataResult.success(new MapLike<INBT>(){
            final CompoundNBT val$compoundnbt;
            final NBTDynamicOps this$0;
            {
                this.this$0 = nBTDynamicOps;
                this.val$compoundnbt = compoundNBT;
            }

            @Override
            @Nullable
            public INBT get(INBT iNBT) {
                return this.val$compoundnbt.get(iNBT.getString());
            }

            @Override
            @Nullable
            public INBT get(String string) {
                return this.val$compoundnbt.get(string);
            }

            @Override
            public Stream<Pair<INBT, INBT>> entries() {
                return this.val$compoundnbt.keySet().stream().map(arg_0 -> this.lambda$entries$0(this.val$compoundnbt, arg_0));
            }

            public String toString() {
                return "MapLike[" + this.val$compoundnbt + "]";
            }

            @Override
            @Nullable
            public Object get(String string) {
                return this.get(string);
            }

            @Override
            @Nullable
            public Object get(Object object) {
                return this.get((INBT)object);
            }

            private Pair lambda$entries$0(CompoundNBT compoundNBT, String string) {
                return Pair.of(this.this$0.createString(string), compoundNBT.get(string));
            }
        });
    }

    @Override
    public INBT createMap(Stream<Pair<INBT, INBT>> stream) {
        CompoundNBT compoundNBT = new CompoundNBT();
        stream.forEach(arg_0 -> NBTDynamicOps.lambda$createMap$9(compoundNBT, arg_0));
        return compoundNBT;
    }

    @Override
    public DataResult<Stream<INBT>> getStream(INBT iNBT) {
        return iNBT instanceof CollectionNBT ? DataResult.success(((CollectionNBT)iNBT).stream().map(NBTDynamicOps::lambda$getStream$10)) : DataResult.error("Not a list");
    }

    @Override
    public DataResult<Consumer<Consumer<INBT>>> getList(INBT iNBT) {
        if (iNBT instanceof CollectionNBT) {
            CollectionNBT collectionNBT = (CollectionNBT)iNBT;
            return DataResult.success(collectionNBT::forEach);
        }
        return DataResult.error("Not a list: " + iNBT);
    }

    @Override
    public DataResult<ByteBuffer> getByteBuffer(INBT iNBT) {
        return iNBT instanceof ByteArrayNBT ? DataResult.success(ByteBuffer.wrap(((ByteArrayNBT)iNBT).getByteArray())) : DynamicOps.super.getByteBuffer(iNBT);
    }

    @Override
    public INBT createByteList(ByteBuffer byteBuffer) {
        return new ByteArrayNBT(DataFixUtils.toArray(byteBuffer));
    }

    @Override
    public DataResult<IntStream> getIntStream(INBT iNBT) {
        return iNBT instanceof IntArrayNBT ? DataResult.success(Arrays.stream(((IntArrayNBT)iNBT).getIntArray())) : DynamicOps.super.getIntStream(iNBT);
    }

    @Override
    public INBT createIntList(IntStream intStream) {
        return new IntArrayNBT(intStream.toArray());
    }

    @Override
    public DataResult<LongStream> getLongStream(INBT iNBT) {
        return iNBT instanceof LongArrayNBT ? DataResult.success(Arrays.stream(((LongArrayNBT)iNBT).getAsLongArray())) : DynamicOps.super.getLongStream(iNBT);
    }

    @Override
    public INBT createLongList(LongStream longStream) {
        return new LongArrayNBT(longStream.toArray());
    }

    @Override
    public INBT createList(Stream<INBT> stream) {
        PeekingIterator peekingIterator = Iterators.peekingIterator(stream.iterator());
        if (!peekingIterator.hasNext()) {
            return new ListNBT();
        }
        INBT iNBT = (INBT)peekingIterator.peek();
        if (iNBT instanceof ByteNBT) {
            ArrayList<Byte> arrayList = Lists.newArrayList(Iterators.transform(peekingIterator, NBTDynamicOps::lambda$createList$11));
            return new ByteArrayNBT(arrayList);
        }
        if (iNBT instanceof IntNBT) {
            ArrayList<Integer> arrayList = Lists.newArrayList(Iterators.transform(peekingIterator, NBTDynamicOps::lambda$createList$12));
            return new IntArrayNBT(arrayList);
        }
        if (iNBT instanceof LongNBT) {
            ArrayList<Long> arrayList = Lists.newArrayList(Iterators.transform(peekingIterator, NBTDynamicOps::lambda$createList$13));
            return new LongArrayNBT(arrayList);
        }
        ListNBT listNBT = new ListNBT();
        while (peekingIterator.hasNext()) {
            INBT iNBT2 = (INBT)peekingIterator.next();
            if (iNBT2 instanceof EndNBT) continue;
            listNBT.add(iNBT2);
        }
        return listNBT;
    }

    @Override
    public INBT remove(INBT iNBT, String string) {
        if (iNBT instanceof CompoundNBT) {
            CompoundNBT compoundNBT = (CompoundNBT)iNBT;
            CompoundNBT compoundNBT2 = new CompoundNBT();
            compoundNBT.keySet().stream().filter(arg_0 -> NBTDynamicOps.lambda$remove$14(string, arg_0)).forEach(arg_0 -> NBTDynamicOps.lambda$remove$15(compoundNBT2, compoundNBT, arg_0));
            return compoundNBT2;
        }
        return iNBT;
    }

    public String toString() {
        return "NBT";
    }

    @Override
    public RecordBuilder<INBT> mapBuilder() {
        return new NBTRecordBuilder(this);
    }

    @Override
    public Object remove(Object object, String string) {
        return this.remove((INBT)object, string);
    }

    @Override
    public Object createLongList(LongStream longStream) {
        return this.createLongList(longStream);
    }

    @Override
    public DataResult getLongStream(Object object) {
        return this.getLongStream((INBT)object);
    }

    @Override
    public Object createIntList(IntStream intStream) {
        return this.createIntList(intStream);
    }

    @Override
    public DataResult getIntStream(Object object) {
        return this.getIntStream((INBT)object);
    }

    @Override
    public Object createByteList(ByteBuffer byteBuffer) {
        return this.createByteList(byteBuffer);
    }

    @Override
    public DataResult getByteBuffer(Object object) {
        return this.getByteBuffer((INBT)object);
    }

    @Override
    public Object createList(Stream stream) {
        return this.createList(stream);
    }

    @Override
    public DataResult getList(Object object) {
        return this.getList((INBT)object);
    }

    @Override
    public DataResult getStream(Object object) {
        return this.getStream((INBT)object);
    }

    @Override
    public DataResult getMap(Object object) {
        return this.getMap((INBT)object);
    }

    @Override
    public Object createMap(Stream stream) {
        return this.createMap(stream);
    }

    @Override
    public DataResult getMapEntries(Object object) {
        return this.getMapEntries((INBT)object);
    }

    @Override
    public DataResult getMapValues(Object object) {
        return this.getMapValues((INBT)object);
    }

    @Override
    public DataResult mergeToMap(Object object, MapLike mapLike) {
        return this.mergeToMap((INBT)object, (MapLike<INBT>)mapLike);
    }

    @Override
    public DataResult mergeToMap(Object object, Object object2, Object object3) {
        return this.mergeToMap((INBT)object, (INBT)object2, (INBT)object3);
    }

    @Override
    public DataResult mergeToList(Object object, List list) {
        return this.mergeToList((INBT)object, (List<INBT>)list);
    }

    @Override
    public DataResult mergeToList(Object object, Object object2) {
        return this.mergeToList((INBT)object, (INBT)object2);
    }

    @Override
    public Object createString(String string) {
        return this.createString(string);
    }

    @Override
    public DataResult getStringValue(Object object) {
        return this.getStringValue((INBT)object);
    }

    @Override
    public Object createBoolean(boolean bl) {
        return this.createBoolean(bl);
    }

    @Override
    public Object createDouble(double d) {
        return this.createDouble(d);
    }

    @Override
    public Object createFloat(float f) {
        return this.createFloat(f);
    }

    @Override
    public Object createLong(long l) {
        return this.createLong(l);
    }

    @Override
    public Object createInt(int n) {
        return this.createInt(n);
    }

    @Override
    public Object createShort(short s) {
        return this.createShort(s);
    }

    @Override
    public Object createByte(byte by) {
        return this.createByte(by);
    }

    @Override
    public Object createNumeric(Number number) {
        return this.createNumeric(number);
    }

    @Override
    public DataResult getNumberValue(Object object) {
        return this.getNumberValue((INBT)object);
    }

    @Override
    public Object convertTo(DynamicOps dynamicOps, Object object) {
        return this.convertTo(dynamicOps, (INBT)object);
    }

    @Override
    public Object empty() {
        return this.empty();
    }

    private static void lambda$remove$15(CompoundNBT compoundNBT, CompoundNBT compoundNBT2, String string) {
        compoundNBT.put(string, compoundNBT2.get(string));
    }

    private static boolean lambda$remove$14(String string, String string2) {
        return !Objects.equals(string2, string);
    }

    private static Long lambda$createList$13(INBT iNBT) {
        return ((LongNBT)iNBT).getLong();
    }

    private static Integer lambda$createList$12(INBT iNBT) {
        return ((IntNBT)iNBT).getInt();
    }

    private static Byte lambda$createList$11(INBT iNBT) {
        return ((ByteNBT)iNBT).getByte();
    }

    private static Object lambda$getStream$10(Object object) {
        return object;
    }

    private static void lambda$createMap$9(CompoundNBT compoundNBT, Pair pair) {
        compoundNBT.put(((INBT)pair.getFirst()).getString(), (INBT)pair.getSecond());
    }

    private void lambda$getMapEntries$8(CompoundNBT compoundNBT, BiConsumer biConsumer) {
        compoundNBT.keySet().forEach(arg_0 -> this.lambda$getMapEntries$7(biConsumer, compoundNBT, arg_0));
    }

    private void lambda$getMapEntries$7(BiConsumer biConsumer, CompoundNBT compoundNBT, String string) {
        biConsumer.accept(this.createString(string), compoundNBT.get(string));
    }

    private Pair lambda$getMapValues$6(CompoundNBT compoundNBT, String string) {
        return Pair.of(this.createString(string), compoundNBT.get(string));
    }

    private static void lambda$mergeToMap$5(List list, CompoundNBT compoundNBT, Pair pair) {
        INBT iNBT = (INBT)pair.getFirst();
        if (!(iNBT instanceof StringNBT)) {
            list.add(iNBT);
        } else {
            compoundNBT.put(iNBT.getString(), (INBT)pair.getSecond());
        }
    }

    private static void lambda$mergeToMap$4(CompoundNBT compoundNBT, CompoundNBT compoundNBT2, String string) {
        compoundNBT.put(string, compoundNBT2.get(string));
    }

    private static void lambda$mergeToMap$3(CompoundNBT compoundNBT, CompoundNBT compoundNBT2, String string) {
        compoundNBT.put(string, compoundNBT2.get(string));
    }

    private static void lambda$func_240608_a_$2(CollectionNBT collectionNBT, INBT iNBT) {
        collectionNBT.add(iNBT);
    }

    private static void lambda$func_240608_a_$1(CollectionNBT collectionNBT, INBT iNBT) {
        collectionNBT.add(iNBT);
    }

    private static void lambda$func_240609_a_$0(CollectionNBT collectionNBT, INBT iNBT) {
        collectionNBT.add(iNBT);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    class NBTRecordBuilder
    extends RecordBuilder.AbstractStringBuilder<INBT, CompoundNBT> {
        final NBTDynamicOps this$0;

        protected NBTRecordBuilder(NBTDynamicOps nBTDynamicOps) {
            this.this$0 = nBTDynamicOps;
            super(nBTDynamicOps);
        }

        @Override
        protected CompoundNBT initBuilder() {
            return new CompoundNBT();
        }

        @Override
        protected CompoundNBT append(String string, INBT iNBT, CompoundNBT compoundNBT) {
            compoundNBT.put(string, iNBT);
            return compoundNBT;
        }

        @Override
        protected DataResult<INBT> build(CompoundNBT compoundNBT, INBT iNBT) {
            if (iNBT != null && iNBT != EndNBT.INSTANCE) {
                if (!(iNBT instanceof CompoundNBT)) {
                    return DataResult.error("mergeToMap called with not a map: " + iNBT, iNBT);
                }
                CompoundNBT compoundNBT2 = new CompoundNBT(Maps.newHashMap(((CompoundNBT)iNBT).getTagMap()));
                for (Map.Entry<String, INBT> entry : compoundNBT.getTagMap().entrySet()) {
                    compoundNBT2.put(entry.getKey(), entry.getValue());
                }
                return DataResult.success(compoundNBT2);
            }
            return DataResult.success(compoundNBT);
        }

        @Override
        protected Object append(String string, Object object, Object object2) {
            return this.append(string, (INBT)object, (CompoundNBT)object2);
        }

        @Override
        protected DataResult build(Object object, Object object2) {
            return this.build((CompoundNBT)object, (INBT)object2);
        }

        @Override
        protected Object initBuilder() {
            return this.initBuilder();
        }
    }
}

