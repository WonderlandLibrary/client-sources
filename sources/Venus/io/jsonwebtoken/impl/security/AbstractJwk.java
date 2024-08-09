/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.io.Streams;
import io.jsonwebtoken.impl.lang.Nameable;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.ParameterReadable;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.security.DefaultJwkThumbprint;
import io.jsonwebtoken.impl.security.DefaultRequest;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.impl.security.KeyOperationConverter;
import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.lang.Supplier;
import io.jsonwebtoken.security.HashAlgorithm;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.JwkThumbprint;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.KeyOperation;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractJwk<K extends Key>
implements Jwk<K>,
ParameterReadable,
Nameable {
    static final Parameter<String> ALG = Parameters.string("alg", "Algorithm");
    public static final Parameter<String> KID = Parameters.string("kid", "Key ID");
    static final Parameter<Set<KeyOperation>> KEY_OPS = (Parameter)Parameters.builder(KeyOperation.class).setConverter(KeyOperationConverter.DEFAULT).set().setId("key_ops").setName("Key Operations").build();
    static final Parameter<String> KTY = Parameters.string("kty", "Key Type");
    static final Set<Parameter<?>> PARAMS = Collections.setOf(ALG, KID, KEY_OPS, KTY);
    public static final String IMMUTABLE_MSG = "JWKs are immutable and may not be modified.";
    protected final JwkContext<K> context;
    private final List<Parameter<?>> THUMBPRINT_PARAMS;
    private final int hashCode;

    AbstractJwk(JwkContext<K> jwkContext, List<Parameter<?>> list) {
        this.context = Assert.notNull(jwkContext, "JwkContext cannot be null.");
        Assert.isTrue(!jwkContext.isEmpty(), "JwkContext cannot be empty.");
        Assert.hasText(jwkContext.getType(), "JwkContext type cannot be null or empty.");
        Assert.notNull(jwkContext.getKey(), "JwkContext key cannot be null.");
        this.THUMBPRINT_PARAMS = Assert.notEmpty(list, "JWK Thumbprint parameters cannot be null or empty.");
        HashAlgorithm hashAlgorithm = jwkContext.getIdThumbprintAlgorithm();
        if (!Strings.hasText(this.getId()) && hashAlgorithm != null) {
            JwkThumbprint jwkThumbprint = this.thumbprint(hashAlgorithm);
            String string = jwkThumbprint.toString();
            jwkContext.setId(string);
        }
        this.hashCode = this.computeHashCode();
    }

    private int computeHashCode() {
        ArrayList<String> arrayList = new ArrayList<String>(this.THUMBPRINT_PARAMS.size() + 1);
        Key key = (Key)Assert.notNull(this.toKey(), "JWK toKey() value cannot be null.");
        if (key instanceof PublicKey) {
            arrayList.add("Public");
        } else if (key instanceof PrivateKey) {
            arrayList.add("Private");
        }
        for (Parameter<?> parameter : this.THUMBPRINT_PARAMS) {
            Object obj = Assert.notNull(this.get(parameter), "computeHashCode: Parameter idiomatic value cannot be null.");
            arrayList.add((String)obj);
        }
        return Objects.nullSafeHashCode(arrayList.toArray());
    }

    private String getRequiredThumbprintValue(Parameter<?> parameter) {
        Object object = this.get(parameter.getId());
        if (object instanceof Supplier) {
            object = ((Supplier)object).get();
        }
        return Assert.isInstanceOf(String.class, object, "Parameter canonical value is not a String.");
    }

    private String toThumbprintJson() {
        StringBuilder stringBuilder = new StringBuilder().append('{');
        Iterator<Parameter<?>> iterator2 = this.THUMBPRINT_PARAMS.iterator();
        while (iterator2.hasNext()) {
            Parameter<?> parameter = iterator2.next();
            String string = this.getRequiredThumbprintValue(parameter);
            stringBuilder.append('\"').append(parameter.getId()).append("\":\"").append(string).append('\"');
            if (!iterator2.hasNext()) continue;
            stringBuilder.append(",");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public JwkThumbprint thumbprint() {
        return this.thumbprint(Jwks.HASH.SHA256);
    }

    @Override
    public JwkThumbprint thumbprint(HashAlgorithm hashAlgorithm) {
        String string = this.toThumbprintJson();
        Assert.hasText(string, "Canonical JWK Thumbprint JSON cannot be null or empty.");
        byte[] byArray = string.getBytes(StandardCharsets.UTF_8);
        InputStream inputStream = Streams.of(byArray);
        byte[] byArray2 = hashAlgorithm.digest(new DefaultRequest<InputStream>(inputStream, this.context.getProvider(), this.context.getRandom()));
        return new DefaultJwkThumbprint(byArray2, hashAlgorithm);
    }

    @Override
    public String getType() {
        return this.context.getType();
    }

    @Override
    public String getName() {
        return this.context.getName();
    }

    @Override
    public Set<KeyOperation> getOperations() {
        return Collections.immutable(this.context.getOperations());
    }

    @Override
    public String getAlgorithm() {
        return this.context.getAlgorithm();
    }

    @Override
    public String getId() {
        return this.context.getId();
    }

    @Override
    public K toKey() {
        return this.context.getKey();
    }

    @Override
    public int size() {
        return this.context.size();
    }

    @Override
    public boolean isEmpty() {
        return this.context.isEmpty();
    }

    @Override
    public boolean containsKey(Object object) {
        return this.context.containsKey(object);
    }

    @Override
    public boolean containsValue(Object object) {
        return this.context.containsValue(object);
    }

    @Override
    public Object get(Object object) {
        Object v = this.context.get(object);
        if (v instanceof Map) {
            return Collections.immutable((Map)v);
        }
        if (v instanceof Collection) {
            return Collections.immutable((Collection)v);
        }
        if (Objects.isArray(v)) {
            return Arrays.copy(v);
        }
        return v;
    }

    @Override
    public <T> T get(Parameter<T> parameter) {
        return this.context.get(parameter);
    }

    @Override
    public Set<String> keySet() {
        return Collections.immutable(this.context.keySet());
    }

    @Override
    public Collection<Object> values() {
        return Collections.immutable(this.context.values());
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return Collections.immutable(this.context.entrySet());
    }

    private static Object immutable() {
        throw new UnsupportedOperationException(IMMUTABLE_MSG);
    }

    @Override
    public Object put(String string, Object object) {
        return AbstractJwk.immutable();
    }

    @Override
    public Object remove(Object object) {
        return AbstractJwk.immutable();
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        AbstractJwk.immutable();
    }

    @Override
    public void clear() {
        AbstractJwk.immutable();
    }

    public String toString() {
        return this.context.toString();
    }

    @Override
    public final int hashCode() {
        return this.hashCode;
    }

    @Override
    public final boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof Jwk) {
            Jwk jwk = (Jwk)object;
            return this.getType().equals(jwk.getType()) && this.equals(jwk);
        }
        return true;
    }

    protected abstract boolean equals(Jwk<?> var1);

    @Override
    public Object put(Object object, Object object2) {
        return this.put((String)object, object2);
    }
}

