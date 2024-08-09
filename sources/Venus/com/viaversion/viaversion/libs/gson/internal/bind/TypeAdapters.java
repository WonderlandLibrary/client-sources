/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonNull;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.annotations.SerializedName;
import com.viaversion.viaversion.libs.gson.internal.LazilyParsedNumber;
import com.viaversion.viaversion.libs.gson.internal.bind.JsonTreeReader;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class TypeAdapters {
    public static final TypeAdapter<Class> CLASS = new TypeAdapter<Class>(){

        @Override
        public void write(JsonWriter jsonWriter, Class clazz) throws IOException {
            throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + clazz.getName() + ". Forgot to register a type adapter?");
        }

        @Override
        public Class read(JsonReader jsonReader) throws IOException {
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Class)object);
        }
    }.nullSafe();
    public static final TypeAdapterFactory CLASS_FACTORY = TypeAdapters.newFactory(Class.class, CLASS);
    public static final TypeAdapter<BitSet> BIT_SET = new TypeAdapter<BitSet>(){

        @Override
        public BitSet read(JsonReader jsonReader) throws IOException {
            BitSet bitSet = new BitSet();
            jsonReader.beginArray();
            int n = 0;
            JsonToken jsonToken = jsonReader.peek();
            while (jsonToken != JsonToken.END_ARRAY) {
                boolean bl;
                switch (35.$SwitchMap$com$google$gson$stream$JsonToken[jsonToken.ordinal()]) {
                    case 1: 
                    case 2: {
                        int n2 = jsonReader.nextInt();
                        if (n2 == 0) {
                            bl = false;
                            break;
                        }
                        if (n2 == 1) {
                            bl = true;
                            break;
                        }
                        throw new JsonSyntaxException("Invalid bitset value " + n2 + ", expected 0 or 1; at path " + jsonReader.getPreviousPath());
                    }
                    case 3: {
                        bl = jsonReader.nextBoolean();
                        break;
                    }
                    default: {
                        throw new JsonSyntaxException("Invalid bitset value type: " + (Object)((Object)jsonToken) + "; at path " + jsonReader.getPath());
                    }
                }
                if (bl) {
                    bitSet.set(n);
                }
                ++n;
                jsonToken = jsonReader.peek();
            }
            jsonReader.endArray();
            return bitSet;
        }

        @Override
        public void write(JsonWriter jsonWriter, BitSet bitSet) throws IOException {
            jsonWriter.beginArray();
            int n = bitSet.length();
            for (int i = 0; i < n; ++i) {
                int n2 = bitSet.get(i) ? 1 : 0;
                jsonWriter.value(n2);
            }
            jsonWriter.endArray();
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (BitSet)object);
        }
    }.nullSafe();
    public static final TypeAdapterFactory BIT_SET_FACTORY = TypeAdapters.newFactory(BitSet.class, BIT_SET);
    public static final TypeAdapter<Boolean> BOOLEAN = new TypeAdapter<Boolean>(){

        @Override
        public Boolean read(JsonReader jsonReader) throws IOException {
            JsonToken jsonToken = jsonReader.peek();
            if (jsonToken == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            if (jsonToken == JsonToken.STRING) {
                return Boolean.parseBoolean(jsonReader.nextString());
            }
            return jsonReader.nextBoolean();
        }

        @Override
        public void write(JsonWriter jsonWriter, Boolean bl) throws IOException {
            jsonWriter.value(bl);
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Boolean)object);
        }
    };
    public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING = new TypeAdapter<Boolean>(){

        @Override
        public Boolean read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return Boolean.valueOf(jsonReader.nextString());
        }

        @Override
        public void write(JsonWriter jsonWriter, Boolean bl) throws IOException {
            jsonWriter.value(bl == null ? "null" : bl.toString());
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Boolean)object);
        }
    };
    public static final TypeAdapterFactory BOOLEAN_FACTORY = TypeAdapters.newFactory(Boolean.TYPE, Boolean.class, BOOLEAN);
    public static final TypeAdapter<Number> BYTE = new TypeAdapter<Number>(){

        @Override
        public Number read(JsonReader jsonReader) throws IOException {
            int n;
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                n = jsonReader.nextInt();
            } catch (NumberFormatException numberFormatException) {
                throw new JsonSyntaxException(numberFormatException);
            }
            if (n > 255 || n < -128) {
                throw new JsonSyntaxException("Lossy conversion from " + n + " to byte; at path " + jsonReader.getPreviousPath());
            }
            return (byte)n;
        }

        @Override
        public void write(JsonWriter jsonWriter, Number number) throws IOException {
            if (number == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(number.byteValue());
            }
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Number)object);
        }
    };
    public static final TypeAdapterFactory BYTE_FACTORY = TypeAdapters.newFactory(Byte.TYPE, Byte.class, BYTE);
    public static final TypeAdapter<Number> SHORT = new TypeAdapter<Number>(){

        @Override
        public Number read(JsonReader jsonReader) throws IOException {
            int n;
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                n = jsonReader.nextInt();
            } catch (NumberFormatException numberFormatException) {
                throw new JsonSyntaxException(numberFormatException);
            }
            if (n > 65535 || n < Short.MIN_VALUE) {
                throw new JsonSyntaxException("Lossy conversion from " + n + " to short; at path " + jsonReader.getPreviousPath());
            }
            return (short)n;
        }

        @Override
        public void write(JsonWriter jsonWriter, Number number) throws IOException {
            if (number == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(number.shortValue());
            }
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Number)object);
        }
    };
    public static final TypeAdapterFactory SHORT_FACTORY = TypeAdapters.newFactory(Short.TYPE, Short.class, SHORT);
    public static final TypeAdapter<Number> INTEGER = new TypeAdapter<Number>(){

        @Override
        public Number read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                return jsonReader.nextInt();
            } catch (NumberFormatException numberFormatException) {
                throw new JsonSyntaxException(numberFormatException);
            }
        }

        @Override
        public void write(JsonWriter jsonWriter, Number number) throws IOException {
            if (number == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(number.intValue());
            }
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Number)object);
        }
    };
    public static final TypeAdapterFactory INTEGER_FACTORY = TypeAdapters.newFactory(Integer.TYPE, Integer.class, INTEGER);
    public static final TypeAdapter<AtomicInteger> ATOMIC_INTEGER = new TypeAdapter<AtomicInteger>(){

        @Override
        public AtomicInteger read(JsonReader jsonReader) throws IOException {
            try {
                return new AtomicInteger(jsonReader.nextInt());
            } catch (NumberFormatException numberFormatException) {
                throw new JsonSyntaxException(numberFormatException);
            }
        }

        @Override
        public void write(JsonWriter jsonWriter, AtomicInteger atomicInteger) throws IOException {
            jsonWriter.value(atomicInteger.get());
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (AtomicInteger)object);
        }
    }.nullSafe();
    public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY = TypeAdapters.newFactory(AtomicInteger.class, ATOMIC_INTEGER);
    public static final TypeAdapter<AtomicBoolean> ATOMIC_BOOLEAN = new TypeAdapter<AtomicBoolean>(){

        @Override
        public AtomicBoolean read(JsonReader jsonReader) throws IOException {
            return new AtomicBoolean(jsonReader.nextBoolean());
        }

        @Override
        public void write(JsonWriter jsonWriter, AtomicBoolean atomicBoolean) throws IOException {
            jsonWriter.value(atomicBoolean.get());
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (AtomicBoolean)object);
        }
    }.nullSafe();
    public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY = TypeAdapters.newFactory(AtomicBoolean.class, ATOMIC_BOOLEAN);
    public static final TypeAdapter<AtomicIntegerArray> ATOMIC_INTEGER_ARRAY = new TypeAdapter<AtomicIntegerArray>(){

        @Override
        public AtomicIntegerArray read(JsonReader jsonReader) throws IOException {
            int n;
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                try {
                    n = jsonReader.nextInt();
                    arrayList.add(n);
                } catch (NumberFormatException numberFormatException) {
                    throw new JsonSyntaxException(numberFormatException);
                }
            }
            jsonReader.endArray();
            n = arrayList.size();
            AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(n);
            for (int i = 0; i < n; ++i) {
                atomicIntegerArray.set(i, (Integer)arrayList.get(i));
            }
            return atomicIntegerArray;
        }

        @Override
        public void write(JsonWriter jsonWriter, AtomicIntegerArray atomicIntegerArray) throws IOException {
            jsonWriter.beginArray();
            int n = atomicIntegerArray.length();
            for (int i = 0; i < n; ++i) {
                jsonWriter.value(atomicIntegerArray.get(i));
            }
            jsonWriter.endArray();
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (AtomicIntegerArray)object);
        }
    }.nullSafe();
    public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY = TypeAdapters.newFactory(AtomicIntegerArray.class, ATOMIC_INTEGER_ARRAY);
    public static final TypeAdapter<Number> LONG = new TypeAdapter<Number>(){

        @Override
        public Number read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                return jsonReader.nextLong();
            } catch (NumberFormatException numberFormatException) {
                throw new JsonSyntaxException(numberFormatException);
            }
        }

        @Override
        public void write(JsonWriter jsonWriter, Number number) throws IOException {
            if (number == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(number.longValue());
            }
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Number)object);
        }
    };
    public static final TypeAdapter<Number> FLOAT = new TypeAdapter<Number>(){

        @Override
        public Number read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return Float.valueOf((float)jsonReader.nextDouble());
        }

        @Override
        public void write(JsonWriter jsonWriter, Number number) throws IOException {
            if (number == null) {
                jsonWriter.nullValue();
            } else {
                Number number2 = number instanceof Float ? (Number)number : (Number)Float.valueOf(number.floatValue());
                jsonWriter.value(number2);
            }
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Number)object);
        }
    };
    public static final TypeAdapter<Number> DOUBLE = new TypeAdapter<Number>(){

        @Override
        public Number read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return jsonReader.nextDouble();
        }

        @Override
        public void write(JsonWriter jsonWriter, Number number) throws IOException {
            if (number == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(number.doubleValue());
            }
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Number)object);
        }
    };
    public static final TypeAdapter<Character> CHARACTER = new TypeAdapter<Character>(){

        @Override
        public Character read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            String string = jsonReader.nextString();
            if (string.length() != 1) {
                throw new JsonSyntaxException("Expecting character, got: " + string + "; at " + jsonReader.getPreviousPath());
            }
            return Character.valueOf(string.charAt(0));
        }

        @Override
        public void write(JsonWriter jsonWriter, Character c) throws IOException {
            jsonWriter.value(c == null ? null : String.valueOf(c));
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Character)object);
        }
    };
    public static final TypeAdapterFactory CHARACTER_FACTORY = TypeAdapters.newFactory(Character.TYPE, Character.class, CHARACTER);
    public static final TypeAdapter<String> STRING = new TypeAdapter<String>(){

        @Override
        public String read(JsonReader jsonReader) throws IOException {
            JsonToken jsonToken = jsonReader.peek();
            if (jsonToken == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            if (jsonToken == JsonToken.BOOLEAN) {
                return Boolean.toString(jsonReader.nextBoolean());
            }
            return jsonReader.nextString();
        }

        @Override
        public void write(JsonWriter jsonWriter, String string) throws IOException {
            jsonWriter.value(string);
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (String)object);
        }
    };
    public static final TypeAdapter<BigDecimal> BIG_DECIMAL = new TypeAdapter<BigDecimal>(){

        @Override
        public BigDecimal read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            String string = jsonReader.nextString();
            try {
                return new BigDecimal(string);
            } catch (NumberFormatException numberFormatException) {
                throw new JsonSyntaxException("Failed parsing '" + string + "' as BigDecimal; at path " + jsonReader.getPreviousPath(), numberFormatException);
            }
        }

        @Override
        public void write(JsonWriter jsonWriter, BigDecimal bigDecimal) throws IOException {
            jsonWriter.value(bigDecimal);
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (BigDecimal)object);
        }
    };
    public static final TypeAdapter<BigInteger> BIG_INTEGER = new TypeAdapter<BigInteger>(){

        @Override
        public BigInteger read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            String string = jsonReader.nextString();
            try {
                return new BigInteger(string);
            } catch (NumberFormatException numberFormatException) {
                throw new JsonSyntaxException("Failed parsing '" + string + "' as BigInteger; at path " + jsonReader.getPreviousPath(), numberFormatException);
            }
        }

        @Override
        public void write(JsonWriter jsonWriter, BigInteger bigInteger) throws IOException {
            jsonWriter.value(bigInteger);
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (BigInteger)object);
        }
    };
    public static final TypeAdapter<LazilyParsedNumber> LAZILY_PARSED_NUMBER = new TypeAdapter<LazilyParsedNumber>(){

        @Override
        public LazilyParsedNumber read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return new LazilyParsedNumber(jsonReader.nextString());
        }

        @Override
        public void write(JsonWriter jsonWriter, LazilyParsedNumber lazilyParsedNumber) throws IOException {
            jsonWriter.value(lazilyParsedNumber);
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (LazilyParsedNumber)object);
        }
    };
    public static final TypeAdapterFactory STRING_FACTORY = TypeAdapters.newFactory(String.class, STRING);
    public static final TypeAdapter<StringBuilder> STRING_BUILDER = new TypeAdapter<StringBuilder>(){

        @Override
        public StringBuilder read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return new StringBuilder(jsonReader.nextString());
        }

        @Override
        public void write(JsonWriter jsonWriter, StringBuilder stringBuilder) throws IOException {
            jsonWriter.value(stringBuilder == null ? null : stringBuilder.toString());
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (StringBuilder)object);
        }
    };
    public static final TypeAdapterFactory STRING_BUILDER_FACTORY = TypeAdapters.newFactory(StringBuilder.class, STRING_BUILDER);
    public static final TypeAdapter<StringBuffer> STRING_BUFFER = new TypeAdapter<StringBuffer>(){

        @Override
        public StringBuffer read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return new StringBuffer(jsonReader.nextString());
        }

        @Override
        public void write(JsonWriter jsonWriter, StringBuffer stringBuffer) throws IOException {
            jsonWriter.value(stringBuffer == null ? null : stringBuffer.toString());
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (StringBuffer)object);
        }
    };
    public static final TypeAdapterFactory STRING_BUFFER_FACTORY = TypeAdapters.newFactory(StringBuffer.class, STRING_BUFFER);
    public static final TypeAdapter<URL> URL = new TypeAdapter<URL>(){

        @Override
        public URL read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            String string = jsonReader.nextString();
            return "null".equals(string) ? null : new URL(string);
        }

        @Override
        public void write(JsonWriter jsonWriter, URL uRL) throws IOException {
            jsonWriter.value(uRL == null ? null : uRL.toExternalForm());
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (URL)object);
        }
    };
    public static final TypeAdapterFactory URL_FACTORY = TypeAdapters.newFactory(URL.class, URL);
    public static final TypeAdapter<URI> URI = new TypeAdapter<URI>(){

        @Override
        public URI read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                String string = jsonReader.nextString();
                return "null".equals(string) ? null : new URI(string);
            } catch (URISyntaxException uRISyntaxException) {
                throw new JsonIOException(uRISyntaxException);
            }
        }

        @Override
        public void write(JsonWriter jsonWriter, URI uRI) throws IOException {
            jsonWriter.value(uRI == null ? null : uRI.toASCIIString());
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (URI)object);
        }
    };
    public static final TypeAdapterFactory URI_FACTORY = TypeAdapters.newFactory(URI.class, URI);
    public static final TypeAdapter<InetAddress> INET_ADDRESS = new TypeAdapter<InetAddress>(){

        @Override
        public InetAddress read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return InetAddress.getByName(jsonReader.nextString());
        }

        @Override
        public void write(JsonWriter jsonWriter, InetAddress inetAddress) throws IOException {
            jsonWriter.value(inetAddress == null ? null : inetAddress.getHostAddress());
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (InetAddress)object);
        }
    };
    public static final TypeAdapterFactory INET_ADDRESS_FACTORY = TypeAdapters.newTypeHierarchyFactory(InetAddress.class, INET_ADDRESS);
    public static final TypeAdapter<UUID> UUID = new TypeAdapter<UUID>(){

        @Override
        public UUID read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            String string = jsonReader.nextString();
            try {
                return java.util.UUID.fromString(string);
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new JsonSyntaxException("Failed parsing '" + string + "' as UUID; at path " + jsonReader.getPreviousPath(), illegalArgumentException);
            }
        }

        @Override
        public void write(JsonWriter jsonWriter, UUID uUID) throws IOException {
            jsonWriter.value(uUID == null ? null : uUID.toString());
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (UUID)object);
        }
    };
    public static final TypeAdapterFactory UUID_FACTORY = TypeAdapters.newFactory(UUID.class, UUID);
    public static final TypeAdapter<Currency> CURRENCY = new TypeAdapter<Currency>(){

        @Override
        public Currency read(JsonReader jsonReader) throws IOException {
            String string = jsonReader.nextString();
            try {
                return Currency.getInstance(string);
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new JsonSyntaxException("Failed parsing '" + string + "' as Currency; at path " + jsonReader.getPreviousPath(), illegalArgumentException);
            }
        }

        @Override
        public void write(JsonWriter jsonWriter, Currency currency) throws IOException {
            jsonWriter.value(currency.getCurrencyCode());
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Currency)object);
        }
    }.nullSafe();
    public static final TypeAdapterFactory CURRENCY_FACTORY = TypeAdapters.newFactory(Currency.class, CURRENCY);
    public static final TypeAdapter<Calendar> CALENDAR = new TypeAdapter<Calendar>(){
        private static final String YEAR = "year";
        private static final String MONTH = "month";
        private static final String DAY_OF_MONTH = "dayOfMonth";
        private static final String HOUR_OF_DAY = "hourOfDay";
        private static final String MINUTE = "minute";
        private static final String SECOND = "second";

        @Override
        public Calendar read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            jsonReader.beginObject();
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            while (jsonReader.peek() != JsonToken.END_OBJECT) {
                String string = jsonReader.nextName();
                int n7 = jsonReader.nextInt();
                if (YEAR.equals(string)) {
                    n = n7;
                    continue;
                }
                if (MONTH.equals(string)) {
                    n2 = n7;
                    continue;
                }
                if (DAY_OF_MONTH.equals(string)) {
                    n3 = n7;
                    continue;
                }
                if (HOUR_OF_DAY.equals(string)) {
                    n4 = n7;
                    continue;
                }
                if (MINUTE.equals(string)) {
                    n5 = n7;
                    continue;
                }
                if (!SECOND.equals(string)) continue;
                n6 = n7;
            }
            jsonReader.endObject();
            return new GregorianCalendar(n, n2, n3, n4, n5, n6);
        }

        @Override
        public void write(JsonWriter jsonWriter, Calendar calendar) throws IOException {
            if (calendar == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            jsonWriter.name(YEAR);
            jsonWriter.value(calendar.get(1));
            jsonWriter.name(MONTH);
            jsonWriter.value(calendar.get(2));
            jsonWriter.name(DAY_OF_MONTH);
            jsonWriter.value(calendar.get(5));
            jsonWriter.name(HOUR_OF_DAY);
            jsonWriter.value(calendar.get(11));
            jsonWriter.name(MINUTE);
            jsonWriter.value(calendar.get(12));
            jsonWriter.name(SECOND);
            jsonWriter.value(calendar.get(13));
            jsonWriter.endObject();
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Calendar)object);
        }
    };
    public static final TypeAdapterFactory CALENDAR_FACTORY = TypeAdapters.newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, CALENDAR);
    public static final TypeAdapter<Locale> LOCALE = new TypeAdapter<Locale>(){

        @Override
        public Locale read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            String string = jsonReader.nextString();
            StringTokenizer stringTokenizer = new StringTokenizer(string, "_");
            String string2 = null;
            String string3 = null;
            String string4 = null;
            if (stringTokenizer.hasMoreElements()) {
                string2 = stringTokenizer.nextToken();
            }
            if (stringTokenizer.hasMoreElements()) {
                string3 = stringTokenizer.nextToken();
            }
            if (stringTokenizer.hasMoreElements()) {
                string4 = stringTokenizer.nextToken();
            }
            if (string3 == null && string4 == null) {
                return new Locale(string2);
            }
            if (string4 == null) {
                return new Locale(string2, string3);
            }
            return new Locale(string2, string3, string4);
        }

        @Override
        public void write(JsonWriter jsonWriter, Locale locale) throws IOException {
            jsonWriter.value(locale == null ? null : locale.toString());
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (Locale)object);
        }
    };
    public static final TypeAdapterFactory LOCALE_FACTORY = TypeAdapters.newFactory(Locale.class, LOCALE);
    public static final TypeAdapter<JsonElement> JSON_ELEMENT = new TypeAdapter<JsonElement>(){

        private JsonElement tryBeginNesting(JsonReader jsonReader, JsonToken jsonToken) throws IOException {
            switch (35.$SwitchMap$com$google$gson$stream$JsonToken[jsonToken.ordinal()]) {
                case 4: {
                    jsonReader.beginArray();
                    return new JsonArray();
                }
                case 5: {
                    jsonReader.beginObject();
                    return new JsonObject();
                }
            }
            return null;
        }

        private JsonElement readTerminal(JsonReader jsonReader, JsonToken jsonToken) throws IOException {
            switch (35.$SwitchMap$com$google$gson$stream$JsonToken[jsonToken.ordinal()]) {
                case 2: {
                    return new JsonPrimitive(jsonReader.nextString());
                }
                case 1: {
                    String string = jsonReader.nextString();
                    return new JsonPrimitive(new LazilyParsedNumber(string));
                }
                case 3: {
                    return new JsonPrimitive(jsonReader.nextBoolean());
                }
                case 6: {
                    jsonReader.nextNull();
                    return JsonNull.INSTANCE;
                }
            }
            throw new IllegalStateException("Unexpected token: " + (Object)((Object)jsonToken));
        }

        @Override
        public JsonElement read(JsonReader jsonReader) throws IOException {
            if (jsonReader instanceof JsonTreeReader) {
                return ((JsonTreeReader)jsonReader).nextJsonElement();
            }
            JsonToken jsonToken = jsonReader.peek();
            JsonElement jsonElement = this.tryBeginNesting(jsonReader, jsonToken);
            if (jsonElement == null) {
                return this.readTerminal(jsonReader, jsonToken);
            }
            ArrayDeque<JsonElement> arrayDeque = new ArrayDeque<JsonElement>();
            while (true) {
                if (jsonReader.hasNext()) {
                    JsonElement jsonElement2;
                    boolean bl;
                    String string = null;
                    if (jsonElement instanceof JsonObject) {
                        string = jsonReader.nextName();
                    }
                    boolean bl2 = bl = (jsonElement2 = this.tryBeginNesting(jsonReader, jsonToken = jsonReader.peek())) != null;
                    if (jsonElement2 == null) {
                        jsonElement2 = this.readTerminal(jsonReader, jsonToken);
                    }
                    if (jsonElement instanceof JsonArray) {
                        ((JsonArray)jsonElement).add(jsonElement2);
                    } else {
                        ((JsonObject)jsonElement).add(string, jsonElement2);
                    }
                    if (!bl) continue;
                    arrayDeque.addLast(jsonElement);
                    jsonElement = jsonElement2;
                    continue;
                }
                if (jsonElement instanceof JsonArray) {
                    jsonReader.endArray();
                } else {
                    jsonReader.endObject();
                }
                if (arrayDeque.isEmpty()) {
                    return jsonElement;
                }
                jsonElement = (JsonElement)arrayDeque.removeLast();
            }
        }

        @Override
        public void write(JsonWriter jsonWriter, JsonElement jsonElement) throws IOException {
            if (jsonElement == null || jsonElement.isJsonNull()) {
                jsonWriter.nullValue();
            } else if (jsonElement.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
                if (jsonPrimitive.isNumber()) {
                    jsonWriter.value(jsonPrimitive.getAsNumber());
                } else if (jsonPrimitive.isBoolean()) {
                    jsonWriter.value(jsonPrimitive.getAsBoolean());
                } else {
                    jsonWriter.value(jsonPrimitive.getAsString());
                }
            } else if (jsonElement.isJsonArray()) {
                jsonWriter.beginArray();
                for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                    this.write(jsonWriter, jsonElement2);
                }
                jsonWriter.endArray();
            } else if (jsonElement.isJsonObject()) {
                jsonWriter.beginObject();
                for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                    jsonWriter.name(entry.getKey());
                    this.write(jsonWriter, entry.getValue());
                }
                jsonWriter.endObject();
            } else {
                throw new IllegalArgumentException("Couldn't write " + jsonElement.getClass());
            }
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (JsonElement)object);
        }
    };
    public static final TypeAdapterFactory JSON_ELEMENT_FACTORY = TypeAdapters.newTypeHierarchyFactory(JsonElement.class, JSON_ELEMENT);
    public static final TypeAdapterFactory ENUM_FACTORY = new TypeAdapterFactory(){

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            Class<T> clazz = typeToken.getRawType();
            if (!Enum.class.isAssignableFrom(clazz) || clazz == Enum.class) {
                return null;
            }
            if (!clazz.isEnum()) {
                clazz = clazz.getSuperclass();
            }
            EnumTypeAdapter<T> enumTypeAdapter = new EnumTypeAdapter<T>(clazz);
            return enumTypeAdapter;
        }
    };

    private TypeAdapters() {
        throw new UnsupportedOperationException();
    }

    public static <TT> TypeAdapterFactory newFactory(TypeToken<TT> typeToken, TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory(typeToken, typeAdapter){
            final TypeToken val$type;
            final TypeAdapter val$typeAdapter;
            {
                this.val$type = typeToken;
                this.val$typeAdapter = typeAdapter;
            }

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                return typeToken.equals(this.val$type) ? this.val$typeAdapter : null;
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactory(Class<TT> clazz, TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory(clazz, typeAdapter){
            final Class val$type;
            final TypeAdapter val$typeAdapter;
            {
                this.val$type = clazz;
                this.val$typeAdapter = typeAdapter;
            }

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                return typeToken.getRawType() == this.val$type ? this.val$typeAdapter : null;
            }

            public String toString() {
                return "Factory[type=" + this.val$type.getName() + ",adapter=" + this.val$typeAdapter + "]";
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactory(Class<TT> clazz, Class<TT> clazz2, TypeAdapter<? super TT> typeAdapter) {
        return new TypeAdapterFactory(clazz, clazz2, typeAdapter){
            final Class val$unboxed;
            final Class val$boxed;
            final TypeAdapter val$typeAdapter;
            {
                this.val$unboxed = clazz;
                this.val$boxed = clazz2;
                this.val$typeAdapter = typeAdapter;
            }

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                Class<T> clazz = typeToken.getRawType();
                return clazz == this.val$unboxed || clazz == this.val$boxed ? this.val$typeAdapter : null;
            }

            public String toString() {
                return "Factory[type=" + this.val$boxed.getName() + "+" + this.val$unboxed.getName() + ",adapter=" + this.val$typeAdapter + "]";
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(Class<TT> clazz, Class<? extends TT> clazz2, TypeAdapter<? super TT> typeAdapter) {
        return new TypeAdapterFactory(clazz, clazz2, typeAdapter){
            final Class val$base;
            final Class val$sub;
            final TypeAdapter val$typeAdapter;
            {
                this.val$base = clazz;
                this.val$sub = clazz2;
                this.val$typeAdapter = typeAdapter;
            }

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                Class<T> clazz = typeToken.getRawType();
                return clazz == this.val$base || clazz == this.val$sub ? this.val$typeAdapter : null;
            }

            public String toString() {
                return "Factory[type=" + this.val$base.getName() + "+" + this.val$sub.getName() + ",adapter=" + this.val$typeAdapter + "]";
            }
        };
    }

    public static <T1> TypeAdapterFactory newTypeHierarchyFactory(Class<T1> clazz, TypeAdapter<T1> typeAdapter) {
        return new TypeAdapterFactory(clazz, typeAdapter){
            final Class val$clazz;
            final TypeAdapter val$typeAdapter;
            {
                this.val$clazz = clazz;
                this.val$typeAdapter = typeAdapter;
            }

            public <T2> TypeAdapter<T2> create(Gson gson, TypeToken<T2> typeToken) {
                Class<T2> clazz = typeToken.getRawType();
                if (!this.val$clazz.isAssignableFrom(clazz)) {
                    return null;
                }
                return new TypeAdapter<T1>(this, clazz){
                    final Class val$requestedType;
                    final 34 this$0;
                    {
                        this.this$0 = var1_1;
                        this.val$requestedType = clazz;
                    }

                    @Override
                    public void write(JsonWriter jsonWriter, T1 T1) throws IOException {
                        this.this$0.val$typeAdapter.write(jsonWriter, T1);
                    }

                    @Override
                    public T1 read(JsonReader jsonReader) throws IOException {
                        Object t = this.this$0.val$typeAdapter.read(jsonReader);
                        if (t != null && !this.val$requestedType.isInstance(t)) {
                            throw new JsonSyntaxException("Expected a " + this.val$requestedType.getName() + " but was " + t.getClass().getName() + "; at path " + jsonReader.getPreviousPath());
                        }
                        return t;
                    }
                };
            }

            public String toString() {
                return "Factory[typeHierarchy=" + this.val$clazz.getName() + ",adapter=" + this.val$typeAdapter + "]";
            }
        };
    }

    private static final class EnumTypeAdapter<T extends Enum<T>>
    extends TypeAdapter<T> {
        private final Map<String, T> nameToConstant = new HashMap<String, T>();
        private final Map<String, T> stringToConstant = new HashMap<String, T>();
        private final Map<T, String> constantToName = new HashMap<T, String>();

        public EnumTypeAdapter(Class<T> clazz) {
            try {
                Field[] fieldArray;
                for (Field field : fieldArray = AccessController.doPrivileged(new PrivilegedAction<Field[]>(this, clazz){
                    final Class val$classOfT;
                    final EnumTypeAdapter this$0;
                    {
                        this.this$0 = enumTypeAdapter;
                        this.val$classOfT = clazz;
                    }

                    @Override
                    public Field[] run() {
                        AccessibleObject[] accessibleObjectArray = this.val$classOfT.getDeclaredFields();
                        ArrayList<Field> arrayList = new ArrayList<Field>(accessibleObjectArray.length);
                        for (Field field : accessibleObjectArray) {
                            if (!field.isEnumConstant()) continue;
                            arrayList.add(field);
                        }
                        AccessibleObject[] accessibleObjectArray2 = arrayList.toArray(new Field[0]);
                        AccessibleObject.setAccessible(accessibleObjectArray2, true);
                        return accessibleObjectArray2;
                    }

                    @Override
                    public Object run() {
                        return this.run();
                    }
                })) {
                    Enum enum_ = (Enum)field.get(null);
                    String string = enum_.name();
                    String string2 = enum_.toString();
                    SerializedName serializedName = field.getAnnotation(SerializedName.class);
                    if (serializedName != null) {
                        string = serializedName.value();
                        for (String string3 : serializedName.alternate()) {
                            this.nameToConstant.put(string3, enum_);
                        }
                    }
                    this.nameToConstant.put(string, enum_);
                    this.stringToConstant.put(string2, enum_);
                    this.constantToName.put(enum_, string);
                }
            } catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError((Object)illegalAccessException);
            }
        }

        @Override
        public T read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            String string = jsonReader.nextString();
            Enum enum_ = (Enum)this.nameToConstant.get(string);
            return (T)(enum_ == null ? (Enum)this.stringToConstant.get(string) : enum_);
        }

        @Override
        public void write(JsonWriter jsonWriter, T t) throws IOException {
            jsonWriter.value(t == null ? null : this.constantToName.get(t));
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (T)((Enum)object));
        }
    }
}

