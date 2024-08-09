/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.ToNumberStrategy;
import com.viaversion.viaversion.libs.gson.internal.LazilyParsedNumber;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.math.BigDecimal;

public enum ToNumberPolicy implements ToNumberStrategy
{
    DOUBLE{

        @Override
        public Double readNumber(JsonReader jsonReader) throws IOException {
            return jsonReader.nextDouble();
        }

        @Override
        public Number readNumber(JsonReader jsonReader) throws IOException {
            return this.readNumber(jsonReader);
        }
    }
    ,
    LAZILY_PARSED_NUMBER{

        @Override
        public Number readNumber(JsonReader jsonReader) throws IOException {
            return new LazilyParsedNumber(jsonReader.nextString());
        }
    }
    ,
    LONG_OR_DOUBLE{

        @Override
        public Number readNumber(JsonReader jsonReader) throws IOException, JsonParseException {
            String string = jsonReader.nextString();
            try {
                return Long.parseLong(string);
            } catch (NumberFormatException numberFormatException) {
                try {
                    Double d = Double.valueOf(string);
                    if ((d.isInfinite() || d.isNaN()) && !jsonReader.isLenient()) {
                        throw new MalformedJsonException("JSON forbids NaN and infinities: " + d + "; at path " + jsonReader.getPreviousPath());
                    }
                    return d;
                } catch (NumberFormatException numberFormatException2) {
                    throw new JsonParseException("Cannot parse " + string + "; at path " + jsonReader.getPreviousPath(), numberFormatException2);
                }
            }
        }
    }
    ,
    BIG_DECIMAL{

        @Override
        public BigDecimal readNumber(JsonReader jsonReader) throws IOException {
            String string = jsonReader.nextString();
            try {
                return new BigDecimal(string);
            } catch (NumberFormatException numberFormatException) {
                throw new JsonParseException("Cannot parse " + string + "; at path " + jsonReader.getPreviousPath(), numberFormatException);
            }
        }

        @Override
        public Number readNumber(JsonReader jsonReader) throws IOException {
            return this.readNumber(jsonReader);
        }
    };


    private ToNumberPolicy() {
    }

    ToNumberPolicy(1 var3_3) {
        this();
    }
}

