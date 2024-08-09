/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface IStringSerializable {
    public String getString();

    public static <E extends Enum<E>> Codec<E> createEnumCodec(Supplier<E[]> supplier, Function<? super String, ? extends E> function) {
        Enum[] enumArray = (Enum[])supplier.get();
        return IStringSerializable.createCodec(IStringSerializable::lambda$createEnumCodec$0, arg_0 -> IStringSerializable.lambda$createEnumCodec$1(enumArray, arg_0), function);
    }

    public static <E extends IStringSerializable> Codec<E> createCodec(ToIntFunction<E> toIntFunction, IntFunction<E> intFunction, Function<? super String, ? extends E> function) {
        return new Codec<E>(){
            final ToIntFunction val$elementSupplier;
            final IntFunction val$selectorFunction;
            final Function val$namingFunction;
            {
                this.val$elementSupplier = toIntFunction;
                this.val$selectorFunction = intFunction;
                this.val$namingFunction = function;
            }

            @Override
            public <T> DataResult<T> encode(E e, DynamicOps<T> dynamicOps, T t) {
                return dynamicOps.compressMaps() ? dynamicOps.mergeToPrimitive(t, dynamicOps.createInt(this.val$elementSupplier.applyAsInt(e))) : dynamicOps.mergeToPrimitive(t, dynamicOps.createString(e.getString()));
            }

            @Override
            public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> dynamicOps, T t) {
                return dynamicOps.compressMaps() ? dynamicOps.getNumberValue(t).flatMap(arg_0 -> 1.lambda$decode$1(this.val$selectorFunction, arg_0)).map(arg_0 -> 1.lambda$decode$2(dynamicOps, arg_0)) : dynamicOps.getStringValue(t).flatMap(arg_0 -> 1.lambda$decode$4(this.val$namingFunction, arg_0)).map(arg_0 -> 1.lambda$decode$5(dynamicOps, arg_0));
            }

            public String toString() {
                return "StringRepresentable[" + this.val$elementSupplier + "]";
            }

            @Override
            public DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
                return this.encode((E)((IStringSerializable)object), (DynamicOps<T>)dynamicOps, (T)object2);
            }

            private static Pair lambda$decode$5(DynamicOps dynamicOps, IStringSerializable iStringSerializable) {
                return Pair.of(iStringSerializable, dynamicOps.empty());
            }

            private static DataResult lambda$decode$4(Function function, String string) {
                return Optional.ofNullable((IStringSerializable)function.apply(string)).map(DataResult::success).orElseGet(() -> 1.lambda$decode$3(string));
            }

            private static DataResult lambda$decode$3(String string) {
                return DataResult.error("Unknown element name: " + string);
            }

            private static Pair lambda$decode$2(DynamicOps dynamicOps, IStringSerializable iStringSerializable) {
                return Pair.of(iStringSerializable, dynamicOps.empty());
            }

            private static DataResult lambda$decode$1(IntFunction intFunction, Number number) {
                return Optional.ofNullable((IStringSerializable)intFunction.apply(number.intValue())).map(DataResult::success).orElseGet(() -> 1.lambda$decode$0(number));
            }

            private static DataResult lambda$decode$0(Number number) {
                return DataResult.error("Unknown element id: " + number);
            }
        };
    }

    public static Keyable createKeyable(IStringSerializable[] iStringSerializableArray) {
        return new Keyable(iStringSerializableArray){
            final IStringSerializable[] val$serializables;
            {
                this.val$serializables = iStringSerializableArray;
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return dynamicOps.compressMaps() ? IntStream.range(0, this.val$serializables.length).mapToObj(dynamicOps::createInt) : Arrays.stream(this.val$serializables).map(IStringSerializable::getString).map(dynamicOps::createString);
            }
        };
    }

    private static Enum lambda$createEnumCodec$1(Enum[] enumArray, int n) {
        return enumArray[n];
    }

    private static int lambda$createEnumCodec$0(Object object) {
        return ((Enum)object).ordinal();
    }
}

