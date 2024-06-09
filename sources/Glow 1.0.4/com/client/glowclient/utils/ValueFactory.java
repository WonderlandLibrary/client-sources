package com.client.glowclient.utils;

import com.google.gson.*;
import com.client.glowclient.*;

public class ValueFactory
{
    public static boolean M(final JsonObject jsonObject, final String s, final boolean b) {
        final JsonElement value;
        if (jsonObject.has(s) && (value = jsonObject.get(s)).isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
            return value.getAsJsonPrimitive().getAsBoolean();
        }
        return b;
    }
    
    public static BooleanValue D(final String s, final String s2, final String s3, final boolean b) {
        return new BooleanValue(s, s2, s3, b);
    }
    
    public static BooleanValue D(final String s) {
        return new BooleanValue("AntiPacketsServer", s, "Cancel SPacket" + s, false);
    }
    
    public static StringValue M(final String s, final String s2, final String s3, final String s4) {
        return new StringValue(s, s2, s3, s4);
    }
    
    public static StringValue M(final String s, final String s2, final String s3) {
        return new StringValue("Command", s, s2, s3);
    }
    
    public static int M(final JsonObject jsonObject, final String s, final int n) {
        final JsonElement value;
        if (jsonObject.has(s) && (value = jsonObject.get(s)).isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            return value.getAsJsonPrimitive().getAsNumber().intValue();
        }
        return n;
    }
    
    public ValueFactory() {
        super();
    }
    
    public static JsonArray M(final JsonObject jsonObject, final String s, final JsonArray jsonArray) {
        final JsonElement value;
        if (jsonObject.has(s) && (value = jsonObject.get(s)).isJsonArray()) {
            return value.getAsJsonArray();
        }
        return jsonArray;
    }
    
    public static NumberValue M(final String s, final String s2, final String s3, final double n, final double n2, final double n3, final double n4) {
        return new NumberValue(s, s2, s3, n, n2, n3, n4);
    }
    
    public static String M(final JsonObject jsonObject, final String s, final String s2) {
        final JsonElement value;
        if (jsonObject.has(s) && (value = jsonObject.get(s)).isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
            return value.getAsJsonPrimitive().getAsString();
        }
        return s2;
    }
    
    public static float M(final JsonObject jsonObject, final String s, final float n) {
        final JsonElement value;
        if (jsonObject.has(s) && (value = jsonObject.get(s)).isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            return value.getAsJsonPrimitive().getAsNumber().floatValue();
        }
        return n;
    }
    
    public static long M(final JsonObject jsonObject, final String s, final long n) {
        final JsonElement value;
        if (jsonObject.has(s) && (value = jsonObject.get(s)).isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            return value.getAsJsonPrimitive().getAsNumber().longValue();
        }
        return n;
    }
    
    public static short M(final JsonObject jsonObject, final String s, final short n) {
        final JsonElement value;
        if (jsonObject.has(s) && (value = jsonObject.get(s)).isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            return value.getAsJsonPrimitive().getAsNumber().shortValue();
        }
        return n;
    }
    
    public static double M(final JsonObject jsonObject, final String s, final double n) {
        final JsonElement value;
        if (jsonObject.has(s) && (value = jsonObject.get(s)).isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            return value.getAsJsonPrimitive().getAsNumber().doubleValue();
        }
        return n;
    }
    
    public static BooleanValue M(final String s, final String s2, final String s3, final boolean b) {
        return new BooleanValue(s, s2, s3, b);
    }
    
    public static CB M(final String s, final String s2, final String s3, final int n) {
        final int n2 = 0;
        return new CB(s, s2, s3, n, n2, n2, n2);
    }
    
    public static byte M(final JsonObject jsonObject, final String s, final byte b) {
        final JsonElement value;
        if (jsonObject.has(s) && (value = jsonObject.get(s)).isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            return value.getAsJsonPrimitive().getAsNumber().byteValue();
        }
        return b;
    }
    
    public static BooleanValue M(final String s) {
        return new BooleanValue("AntiPackets", s, "Cancel CPacket" + s, false);
    }
    
    public static nB M(final String s, final String s2, final String s3, final String s4, final String... array) {
        return new nB(s, s2, s3, s4, array);
    }
    
    public static NumberValue M(final String s, final String s2, final int n) {
        final String s3 = "Coordinate Value";
        final double n2 = n;
        final double n3 = 0.0;
        return new NumberValue(s, s2, s3, n2, n3, n3, n3);
    }
}
