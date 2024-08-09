/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.io.Codec;
import io.jsonwebtoken.impl.lang.BigIntegerUBytesConverter;
import io.jsonwebtoken.impl.lang.CollectionConverter;
import io.jsonwebtoken.impl.lang.CompoundConverter;
import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.impl.lang.EncodedObjectConverter;
import io.jsonwebtoken.impl.lang.RequiredTypeConverter;
import io.jsonwebtoken.impl.lang.UriStringConverter;
import io.jsonwebtoken.impl.security.JwtX509StringConverter;
import java.math.BigInteger;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Set;

public final class Converters {
    public static final Converter<URI, Object> URI = Converters.forEncoded(URI.class, new UriStringConverter());
    public static final Converter<byte[], Object> BASE64URL_BYTES = Converters.forEncoded(byte[].class, Codec.BASE64URL);
    public static final Converter<X509Certificate, Object> X509_CERTIFICATE = Converters.forEncoded(X509Certificate.class, JwtX509StringConverter.INSTANCE);
    public static final Converter<BigInteger, byte[]> BIGINT_UBYTES = new BigIntegerUBytesConverter();
    public static final Converter<BigInteger, Object> BIGINT = Converters.forEncoded(BigInteger.class, Converters.compound(BIGINT_UBYTES, Codec.BASE64URL));

    private Converters() {
    }

    public static <T> Converter<T, Object> forType(Class<T> clazz) {
        return new RequiredTypeConverter<T>(clazz);
    }

    public static <T> Converter<Set<T>, Object> forSet(Converter<T, Object> converter) {
        return CollectionConverter.forSet(converter);
    }

    public static <T> Converter<List<T>, Object> forList(Converter<T, Object> converter) {
        return CollectionConverter.forList(converter);
    }

    public static <T> Converter<T, Object> forEncoded(Class<T> clazz, Converter<T, CharSequence> converter) {
        return new EncodedObjectConverter<T>(clazz, converter);
    }

    public static <A, B, C> Converter<A, C> compound(Converter<A, B> converter, Converter<B, C> converter2) {
        return new CompoundConverter<A, B, C>(converter, converter2);
    }
}

