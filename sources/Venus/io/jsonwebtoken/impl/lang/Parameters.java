/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.Converters;
import io.jsonwebtoken.impl.lang.DefaultParameterBuilder;
import io.jsonwebtoken.impl.lang.IdRegistry;
import io.jsonwebtoken.impl.lang.JwtDateConverter;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.ParameterBuilder;
import io.jsonwebtoken.impl.lang.ParameterReadable;
import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.lang.Registry;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class Parameters {
    private Parameters() {
    }

    public static Parameter<String> string(String string, String string2) {
        return (Parameter)Parameters.builder(String.class).setId(string).setName(string2).build();
    }

    public static Parameter<Date> rfcDate(String string, String string2) {
        return (Parameter)Parameters.builder(Date.class).setConverter(JwtDateConverter.INSTANCE).setId(string).setName(string2).build();
    }

    public static Parameter<List<X509Certificate>> x509Chain(String string, String string2) {
        return (Parameter)Parameters.builder(X509Certificate.class).setConverter(Converters.X509_CERTIFICATE).list().setId(string).setName(string2).build();
    }

    public static <T> ParameterBuilder<T> builder(Class<T> clazz) {
        return new DefaultParameterBuilder<T>(clazz);
    }

    public static Parameter<Set<String>> stringSet(String string, String string2) {
        return (Parameter)Parameters.builder(String.class).set().setId(string).setName(string2).build();
    }

    public static Parameter<URI> uri(String string, String string2) {
        return (Parameter)Parameters.builder(URI.class).setConverter(Converters.URI).setId(string).setName(string2).build();
    }

    public static ParameterBuilder<byte[]> bytes(String string, String string2) {
        return Parameters.builder(byte[].class).setConverter(Converters.BASE64URL_BYTES).setId(string).setName(string2);
    }

    public static ParameterBuilder<BigInteger> bigInt(String string, String string2) {
        return Parameters.builder(BigInteger.class).setConverter(Converters.BIGINT).setId(string).setName(string2);
    }

    public static Parameter<BigInteger> secretBigInt(String string, String string2) {
        return (Parameter)Parameters.bigInt(string, string2).setSecret(true).build();
    }

    public static Registry<String, Parameter<?>> registry(Parameter<?> ... parameterArray) {
        return Parameters.registry(Arrays.asList(parameterArray));
    }

    public static Registry<String, Parameter<?>> registry(Collection<Parameter<?>> collection) {
        return new IdRegistry("Parameter", collection, true);
    }

    public static Registry<String, Parameter<?>> registry(Registry<String, Parameter<?>> registry, Parameter<?> ... parameterArray) {
        LinkedHashSet<Object> linkedHashSet = new LinkedHashSet<Object>(registry.size() + parameterArray.length);
        linkedHashSet.addAll(registry.values());
        linkedHashSet.addAll(Arrays.asList(parameterArray));
        return new IdRegistry("Parameter", linkedHashSet, true);
    }

    public static Registry<String, ? extends Parameter<?>> replace(Registry<String, ? extends Parameter<?>> registry, Parameter<?> parameter) {
        Assert.notEmpty(registry, "Registry cannot be null or empty.");
        Assert.notNull(parameter, "Parameter cannot be null.");
        String string = Assert.hasText(parameter.getId(), "Parameter id cannot be null or empty.");
        LinkedHashMap linkedHashMap = new LinkedHashMap(registry);
        linkedHashMap.remove(string);
        linkedHashMap.put(string, parameter);
        return Parameters.registry(linkedHashMap.values());
    }

    private static byte[] bytes(BigInteger bigInteger) {
        return bigInteger != null ? bigInteger.toByteArray() : null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean bytesEquals(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger == bigInteger2) {
            return false;
        }
        if (bigInteger == null || bigInteger2 == null) {
            return true;
        }
        byte[] byArray = Parameters.bytes(bigInteger);
        byte[] byArray2 = Parameters.bytes(bigInteger2);
        try {
            boolean bl = MessageDigest.isEqual(byArray, byArray2);
            return bl;
        } finally {
            Bytes.clear(byArray);
            Bytes.clear(byArray2);
        }
    }

    private static <T> boolean equals(T t, T t2, Parameter<T> parameter) {
        if (t == t2) {
            return false;
        }
        if (t == null || t2 == null) {
            return true;
        }
        if (parameter.isSecret()) {
            if (t instanceof byte[]) {
                return t2 instanceof byte[] && MessageDigest.isEqual((byte[])t, (byte[])t2);
            }
            if (t instanceof BigInteger) {
                return t2 instanceof BigInteger && Parameters.bytesEquals((BigInteger)t, (BigInteger)t2);
            }
        }
        return Objects.nullSafeEquals(t, t2);
    }

    public static <T> boolean equals(ParameterReadable parameterReadable, Object object, Parameter<T> parameter) {
        if (parameterReadable == object) {
            return false;
        }
        if (parameterReadable == null || !(object instanceof ParameterReadable)) {
            return true;
        }
        ParameterReadable parameterReadable2 = (ParameterReadable)object;
        return Parameters.equals(parameterReadable.get(parameter), parameterReadable2.get(parameter), parameter);
    }
}

