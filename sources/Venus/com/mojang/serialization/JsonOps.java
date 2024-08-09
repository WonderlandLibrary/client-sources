/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class JsonOps
implements DynamicOps<JsonElement> {
    public static final JsonOps INSTANCE = new JsonOps(false);
    public static final JsonOps COMPRESSED = new JsonOps(true);
    private final boolean compressed;

    protected JsonOps(boolean bl) {
        this.compressed = bl;
    }

    @Override
    public JsonElement empty() {
        return JsonNull.INSTANCE;
    }

    @Override
    public <U> U convertTo(DynamicOps<U> dynamicOps, JsonElement jsonElement) {
        if (jsonElement instanceof JsonObject) {
            return this.convertMap(dynamicOps, jsonElement);
        }
        if (jsonElement instanceof JsonArray) {
            return this.convertList(dynamicOps, jsonElement);
        }
        if (jsonElement instanceof JsonNull) {
            return dynamicOps.empty();
        }
        JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
        if (jsonPrimitive.isString()) {
            return dynamicOps.createString(jsonPrimitive.getAsString());
        }
        if (jsonPrimitive.isBoolean()) {
            return dynamicOps.createBoolean(jsonPrimitive.getAsBoolean());
        }
        BigDecimal bigDecimal = jsonPrimitive.getAsBigDecimal();
        try {
            long l = bigDecimal.longValueExact();
            if ((long)((byte)l) == l) {
                return dynamicOps.createByte((byte)l);
            }
            if ((long)((short)l) == l) {
                return dynamicOps.createShort((short)l);
            }
            if ((long)((int)l) == l) {
                return dynamicOps.createInt((int)l);
            }
            return dynamicOps.createLong(l);
        } catch (ArithmeticException arithmeticException) {
            double d = bigDecimal.doubleValue();
            if ((double)((float)d) == d) {
                return dynamicOps.createFloat((float)d);
            }
            return dynamicOps.createDouble(d);
        }
    }

    @Override
    public DataResult<Number> getNumberValue(JsonElement jsonElement) {
        if (jsonElement instanceof JsonPrimitive) {
            if (jsonElement.getAsJsonPrimitive().isNumber()) {
                return DataResult.success(jsonElement.getAsNumber());
            }
            if (jsonElement.getAsJsonPrimitive().isBoolean()) {
                return DataResult.success(jsonElement.getAsBoolean() ? 1 : 0);
            }
            if (this.compressed && jsonElement.getAsJsonPrimitive().isString()) {
                try {
                    return DataResult.success(Integer.parseInt(jsonElement.getAsString()));
                } catch (NumberFormatException numberFormatException) {
                    return DataResult.error("Not a number: " + numberFormatException + " " + jsonElement);
                }
            }
        }
        if (jsonElement instanceof JsonPrimitive && jsonElement.getAsJsonPrimitive().isBoolean()) {
            return DataResult.success(jsonElement.getAsJsonPrimitive().getAsBoolean() ? 1 : 0);
        }
        return DataResult.error("Not a number: " + jsonElement);
    }

    @Override
    public JsonElement createNumeric(Number number) {
        return new JsonPrimitive(number);
    }

    @Override
    public DataResult<Boolean> getBooleanValue(JsonElement jsonElement) {
        if (jsonElement instanceof JsonPrimitive) {
            if (jsonElement.getAsJsonPrimitive().isBoolean()) {
                return DataResult.success(jsonElement.getAsBoolean());
            }
            if (jsonElement.getAsJsonPrimitive().isNumber()) {
                return DataResult.success(jsonElement.getAsNumber().byteValue() != 0);
            }
        }
        return DataResult.error("Not a boolean: " + jsonElement);
    }

    @Override
    public JsonElement createBoolean(boolean bl) {
        return new JsonPrimitive(bl);
    }

    @Override
    public DataResult<String> getStringValue(JsonElement jsonElement) {
        if (jsonElement instanceof JsonPrimitive && (jsonElement.getAsJsonPrimitive().isString() || jsonElement.getAsJsonPrimitive().isNumber() && this.compressed)) {
            return DataResult.success(jsonElement.getAsString());
        }
        return DataResult.error("Not a string: " + jsonElement);
    }

    @Override
    public JsonElement createString(String string) {
        return new JsonPrimitive(string);
    }

    @Override
    public DataResult<JsonElement> mergeToList(JsonElement jsonElement, JsonElement jsonElement2) {
        if (!(jsonElement instanceof JsonArray) && jsonElement != this.empty()) {
            return DataResult.error("mergeToList called with not a list: " + jsonElement, jsonElement);
        }
        JsonArray jsonArray = new JsonArray();
        if (jsonElement != this.empty()) {
            jsonArray.addAll(jsonElement.getAsJsonArray());
        }
        jsonArray.add(jsonElement2);
        return DataResult.success(jsonArray);
    }

    @Override
    public DataResult<JsonElement> mergeToList(JsonElement jsonElement, List<JsonElement> list) {
        if (!(jsonElement instanceof JsonArray) && jsonElement != this.empty()) {
            return DataResult.error("mergeToList called with not a list: " + jsonElement, jsonElement);
        }
        JsonArray jsonArray = new JsonArray();
        if (jsonElement != this.empty()) {
            jsonArray.addAll(jsonElement.getAsJsonArray());
        }
        list.forEach(jsonArray::add);
        return DataResult.success(jsonArray);
    }

    @Override
    public DataResult<JsonElement> mergeToMap(JsonElement jsonElement, JsonElement jsonElement2, JsonElement jsonElement3) {
        if (!(jsonElement instanceof JsonObject) && jsonElement != this.empty()) {
            return DataResult.error("mergeToMap called with not a map: " + jsonElement, jsonElement);
        }
        if (!(jsonElement2 instanceof JsonPrimitive) || !jsonElement2.getAsJsonPrimitive().isString() && !this.compressed) {
            return DataResult.error("key is not a string: " + jsonElement2, jsonElement);
        }
        JsonObject jsonObject = new JsonObject();
        if (jsonElement != this.empty()) {
            jsonElement.getAsJsonObject().entrySet().forEach(arg_0 -> JsonOps.lambda$mergeToMap$0(jsonObject, arg_0));
        }
        jsonObject.add(jsonElement2.getAsString(), jsonElement3);
        return DataResult.success(jsonObject);
    }

    @Override
    public DataResult<JsonElement> mergeToMap(JsonElement jsonElement, MapLike<JsonElement> mapLike) {
        if (!(jsonElement instanceof JsonObject) && jsonElement != this.empty()) {
            return DataResult.error("mergeToMap called with not a map: " + jsonElement, jsonElement);
        }
        JsonObject jsonObject = new JsonObject();
        if (jsonElement != this.empty()) {
            jsonElement.getAsJsonObject().entrySet().forEach(arg_0 -> JsonOps.lambda$mergeToMap$1(jsonObject, arg_0));
        }
        ArrayList arrayList = Lists.newArrayList();
        mapLike.entries().forEach(arg_0 -> this.lambda$mergeToMap$2(arrayList, jsonObject, arg_0));
        if (!arrayList.isEmpty()) {
            return DataResult.error("some keys are not strings: " + arrayList, jsonObject);
        }
        return DataResult.success(jsonObject);
    }

    @Override
    public DataResult<Stream<Pair<JsonElement, JsonElement>>> getMapValues(JsonElement jsonElement) {
        if (!(jsonElement instanceof JsonObject)) {
            return DataResult.error("Not a JSON object: " + jsonElement);
        }
        return DataResult.success(jsonElement.getAsJsonObject().entrySet().stream().map(JsonOps::lambda$getMapValues$3));
    }

    @Override
    public DataResult<Consumer<BiConsumer<JsonElement, JsonElement>>> getMapEntries(JsonElement jsonElement) {
        if (!(jsonElement instanceof JsonObject)) {
            return DataResult.error("Not a JSON object: " + jsonElement);
        }
        return DataResult.success(arg_0 -> this.lambda$getMapEntries$4(jsonElement, arg_0));
    }

    @Override
    public DataResult<MapLike<JsonElement>> getMap(JsonElement jsonElement) {
        if (!(jsonElement instanceof JsonObject)) {
            return DataResult.error("Not a JSON object: " + jsonElement);
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return DataResult.success(new MapLike<JsonElement>(this, jsonObject){
            final JsonObject val$object;
            final JsonOps this$0;
            {
                this.this$0 = jsonOps;
                this.val$object = jsonObject;
            }

            @Override
            @Nullable
            public JsonElement get(JsonElement jsonElement) {
                JsonElement jsonElement2 = this.val$object.get(jsonElement.getAsString());
                if (jsonElement2 instanceof JsonNull) {
                    return null;
                }
                return jsonElement2;
            }

            @Override
            @Nullable
            public JsonElement get(String string) {
                JsonElement jsonElement = this.val$object.get(string);
                if (jsonElement instanceof JsonNull) {
                    return null;
                }
                return jsonElement;
            }

            @Override
            public Stream<Pair<JsonElement, JsonElement>> entries() {
                return this.val$object.entrySet().stream().map(1::lambda$entries$0);
            }

            public String toString() {
                return "MapLike[" + this.val$object + "]";
            }

            @Override
            @Nullable
            public Object get(String string) {
                return this.get(string);
            }

            @Override
            @Nullable
            public Object get(Object object) {
                return this.get((JsonElement)object);
            }

            private static Pair lambda$entries$0(Map.Entry entry) {
                return Pair.of(new JsonPrimitive((String)entry.getKey()), entry.getValue());
            }
        });
    }

    @Override
    public JsonElement createMap(Stream<Pair<JsonElement, JsonElement>> stream) {
        JsonObject jsonObject = new JsonObject();
        stream.forEach(arg_0 -> JsonOps.lambda$createMap$5(jsonObject, arg_0));
        return jsonObject;
    }

    @Override
    public DataResult<Stream<JsonElement>> getStream(JsonElement jsonElement) {
        if (jsonElement instanceof JsonArray) {
            return DataResult.success(StreamSupport.stream(jsonElement.getAsJsonArray().spliterator(), false).map(JsonOps::lambda$getStream$6));
        }
        return DataResult.error("Not a json array: " + jsonElement);
    }

    @Override
    public DataResult<Consumer<Consumer<JsonElement>>> getList(JsonElement jsonElement) {
        if (jsonElement instanceof JsonArray) {
            return DataResult.success(arg_0 -> JsonOps.lambda$getList$7(jsonElement, arg_0));
        }
        return DataResult.error("Not a json array: " + jsonElement);
    }

    @Override
    public JsonElement createList(Stream<JsonElement> stream) {
        JsonArray jsonArray = new JsonArray();
        stream.forEach(jsonArray::add);
        return jsonArray;
    }

    @Override
    public JsonElement remove(JsonElement jsonElement, String string) {
        if (jsonElement instanceof JsonObject) {
            JsonObject jsonObject = new JsonObject();
            jsonElement.getAsJsonObject().entrySet().stream().filter(arg_0 -> JsonOps.lambda$remove$8(string, arg_0)).forEach(arg_0 -> JsonOps.lambda$remove$9(jsonObject, arg_0));
            return jsonObject;
        }
        return jsonElement;
    }

    public String toString() {
        return "JSON";
    }

    @Override
    public ListBuilder<JsonElement> listBuilder() {
        return new ArrayBuilder(null);
    }

    @Override
    public boolean compressMaps() {
        return this.compressed;
    }

    @Override
    public RecordBuilder<JsonElement> mapBuilder() {
        return new JsonRecordBuilder(this);
    }

    @Override
    public Object remove(Object object, String string) {
        return this.remove((JsonElement)object, string);
    }

    @Override
    public Object createList(Stream stream) {
        return this.createList(stream);
    }

    @Override
    public DataResult getList(Object object) {
        return this.getList((JsonElement)object);
    }

    @Override
    public DataResult getStream(Object object) {
        return this.getStream((JsonElement)object);
    }

    @Override
    public DataResult getMap(Object object) {
        return this.getMap((JsonElement)object);
    }

    @Override
    public Object createMap(Stream stream) {
        return this.createMap(stream);
    }

    @Override
    public DataResult getMapEntries(Object object) {
        return this.getMapEntries((JsonElement)object);
    }

    @Override
    public DataResult getMapValues(Object object) {
        return this.getMapValues((JsonElement)object);
    }

    @Override
    public DataResult mergeToMap(Object object, MapLike mapLike) {
        return this.mergeToMap((JsonElement)object, (MapLike<JsonElement>)mapLike);
    }

    @Override
    public DataResult mergeToMap(Object object, Object object2, Object object3) {
        return this.mergeToMap((JsonElement)object, (JsonElement)object2, (JsonElement)object3);
    }

    @Override
    public DataResult mergeToList(Object object, List list) {
        return this.mergeToList((JsonElement)object, (List<JsonElement>)list);
    }

    @Override
    public DataResult mergeToList(Object object, Object object2) {
        return this.mergeToList((JsonElement)object, (JsonElement)object2);
    }

    @Override
    public Object createString(String string) {
        return this.createString(string);
    }

    @Override
    public DataResult getStringValue(Object object) {
        return this.getStringValue((JsonElement)object);
    }

    @Override
    public Object createBoolean(boolean bl) {
        return this.createBoolean(bl);
    }

    @Override
    public DataResult getBooleanValue(Object object) {
        return this.getBooleanValue((JsonElement)object);
    }

    @Override
    public Object createNumeric(Number number) {
        return this.createNumeric(number);
    }

    @Override
    public DataResult getNumberValue(Object object) {
        return this.getNumberValue((JsonElement)object);
    }

    @Override
    public Object convertTo(DynamicOps dynamicOps, Object object) {
        return this.convertTo(dynamicOps, (JsonElement)object);
    }

    @Override
    public Object empty() {
        return this.empty();
    }

    private static void lambda$remove$9(JsonObject jsonObject, Map.Entry entry) {
        jsonObject.add((String)entry.getKey(), (JsonElement)entry.getValue());
    }

    private static boolean lambda$remove$8(String string, Map.Entry entry) {
        return !Objects.equals(entry.getKey(), string);
    }

    private static void lambda$getList$7(JsonElement jsonElement, Consumer consumer) {
        for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
            consumer.accept(jsonElement2 instanceof JsonNull ? null : jsonElement2);
        }
    }

    private static JsonElement lambda$getStream$6(JsonElement jsonElement) {
        return jsonElement instanceof JsonNull ? null : jsonElement;
    }

    private static void lambda$createMap$5(JsonObject jsonObject, Pair pair) {
        jsonObject.add(((JsonElement)pair.getFirst()).getAsString(), (JsonElement)pair.getSecond());
    }

    private void lambda$getMapEntries$4(JsonElement jsonElement, BiConsumer biConsumer) {
        for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
            biConsumer.accept(this.createString(entry.getKey()), entry.getValue() instanceof JsonNull ? null : entry.getValue());
        }
    }

    private static Pair lambda$getMapValues$3(Map.Entry entry) {
        return Pair.of(new JsonPrimitive((String)entry.getKey()), entry.getValue() instanceof JsonNull ? null : (JsonElement)entry.getValue());
    }

    private void lambda$mergeToMap$2(List list, JsonObject jsonObject, Pair pair) {
        JsonElement jsonElement = (JsonElement)pair.getFirst();
        if (!(jsonElement instanceof JsonPrimitive) || !jsonElement.getAsJsonPrimitive().isString() && !this.compressed) {
            list.add(jsonElement);
            return;
        }
        jsonObject.add(jsonElement.getAsString(), (JsonElement)pair.getSecond());
    }

    private static void lambda$mergeToMap$1(JsonObject jsonObject, Map.Entry entry) {
        jsonObject.add((String)entry.getKey(), (JsonElement)entry.getValue());
    }

    private static void lambda$mergeToMap$0(JsonObject jsonObject, Map.Entry entry) {
        jsonObject.add((String)entry.getKey(), (JsonElement)entry.getValue());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class JsonRecordBuilder
    extends RecordBuilder.AbstractStringBuilder<JsonElement, JsonObject> {
        final JsonOps this$0;

        protected JsonRecordBuilder(JsonOps jsonOps) {
            this.this$0 = jsonOps;
            super(jsonOps);
        }

        @Override
        protected JsonObject initBuilder() {
            return new JsonObject();
        }

        @Override
        protected JsonObject append(String string, JsonElement jsonElement, JsonObject jsonObject) {
            jsonObject.add(string, jsonElement);
            return jsonObject;
        }

        @Override
        protected DataResult<JsonElement> build(JsonObject jsonObject, JsonElement jsonElement) {
            if (jsonElement == null || jsonElement instanceof JsonNull) {
                return DataResult.success(jsonObject);
            }
            if (jsonElement instanceof JsonObject) {
                JsonObject jsonObject2 = new JsonObject();
                for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                    jsonObject2.add(entry.getKey(), entry.getValue());
                }
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    jsonObject2.add(entry.getKey(), entry.getValue());
                }
                return DataResult.success(jsonObject2);
            }
            return DataResult.error("mergeToMap called with not a map: " + jsonElement, jsonElement);
        }

        @Override
        protected Object append(String string, Object object, Object object2) {
            return this.append(string, (JsonElement)object, (JsonObject)object2);
        }

        @Override
        protected DataResult build(Object object, Object object2) {
            return this.build((JsonObject)object, (JsonElement)object2);
        }

        @Override
        protected Object initBuilder() {
            return this.initBuilder();
        }
    }

    private static final class ArrayBuilder
    implements ListBuilder<JsonElement> {
        private DataResult<JsonArray> builder = DataResult.success(new JsonArray(), Lifecycle.stable());

        private ArrayBuilder() {
        }

        @Override
        public DynamicOps<JsonElement> ops() {
            return INSTANCE;
        }

        @Override
        public ListBuilder<JsonElement> add(JsonElement jsonElement) {
            this.builder = this.builder.map(arg_0 -> ArrayBuilder.lambda$add$0(jsonElement, arg_0));
            return this;
        }

        @Override
        public ListBuilder<JsonElement> add(DataResult<JsonElement> dataResult) {
            this.builder = this.builder.apply2stable(ArrayBuilder::lambda$add$1, dataResult);
            return this;
        }

        @Override
        public ListBuilder<JsonElement> withErrorsFrom(DataResult<?> dataResult) {
            this.builder = this.builder.flatMap(arg_0 -> ArrayBuilder.lambda$withErrorsFrom$3(dataResult, arg_0));
            return this;
        }

        @Override
        public ListBuilder<JsonElement> mapError(UnaryOperator<String> unaryOperator) {
            this.builder = this.builder.mapError(unaryOperator);
            return this;
        }

        @Override
        public DataResult<JsonElement> build(JsonElement jsonElement) {
            DataResult<JsonElement> dataResult = this.builder.flatMap(arg_0 -> this.lambda$build$4(jsonElement, arg_0));
            this.builder = DataResult.success(new JsonArray(), Lifecycle.stable());
            return dataResult;
        }

        @Override
        public ListBuilder add(Object object) {
            return this.add((JsonElement)object);
        }

        @Override
        public DataResult build(Object object) {
            return this.build((JsonElement)object);
        }

        private DataResult lambda$build$4(JsonElement jsonElement, JsonArray jsonArray) {
            if (!(jsonElement instanceof JsonArray) && jsonElement != this.ops().empty()) {
                return DataResult.error("Cannot append a list to not a list: " + jsonElement, jsonElement);
            }
            JsonArray jsonArray2 = new JsonArray();
            if (jsonElement != this.ops().empty()) {
                jsonArray2.addAll(jsonElement.getAsJsonArray());
            }
            jsonArray2.addAll(jsonArray);
            return DataResult.success(jsonArray2, Lifecycle.stable());
        }

        private static DataResult lambda$withErrorsFrom$3(DataResult dataResult, JsonArray jsonArray) {
            return dataResult.map(arg_0 -> ArrayBuilder.lambda$null$2(jsonArray, arg_0));
        }

        private static JsonArray lambda$null$2(JsonArray jsonArray, Object object) {
            return jsonArray;
        }

        private static JsonArray lambda$add$1(JsonArray jsonArray, JsonElement jsonElement) {
            jsonArray.add(jsonElement);
            return jsonArray;
        }

        private static JsonArray lambda$add$0(JsonElement jsonElement, JsonArray jsonArray) {
            jsonArray.add(jsonElement);
            return jsonArray;
        }

        ArrayBuilder(1 var1_1) {
            this();
        }
    }
}

