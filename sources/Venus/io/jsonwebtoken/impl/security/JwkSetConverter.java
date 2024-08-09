/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.security.DefaultJwkSet;
import io.jsonwebtoken.impl.security.JwkBuilderSupplier;
import io.jsonwebtoken.impl.security.JwkConverter;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Supplier;
import io.jsonwebtoken.security.DynamicJwkBuilder;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.JwkSet;
import io.jsonwebtoken.security.KeyException;
import io.jsonwebtoken.security.MalformedKeySetException;
import io.jsonwebtoken.security.UnsupportedKeyException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class JwkSetConverter
implements Converter<JwkSet, Object> {
    private final Converter<Jwk<?>, Object> JWK_CONVERTER;
    private final Parameter<Set<Jwk<?>>> PARAM;
    private final boolean ignoreUnsupported;

    public JwkSetConverter() {
        this(JwkBuilderSupplier.DEFAULT, true);
    }

    public JwkSetConverter(boolean bl) {
        this(JwkBuilderSupplier.DEFAULT, bl);
    }

    public JwkSetConverter(Supplier<DynamicJwkBuilder<?, ?>> supplier, boolean bl) {
        this(new JwkConverter(supplier), bl);
    }

    public JwkSetConverter(Converter<Jwk<?>, Object> converter, boolean bl) {
        this.JWK_CONVERTER = Assert.notNull(converter, "JWK converter cannot be null.");
        this.PARAM = DefaultJwkSet.param(converter);
        this.ignoreUnsupported = bl;
    }

    public boolean isIgnoreUnsupported() {
        return this.ignoreUnsupported;
    }

    @Override
    public Object applyTo(JwkSet jwkSet) {
        return jwkSet;
    }

    @Override
    public JwkSet applyFrom(Object object) {
        Assert.notNull(object, "Value cannot be null.");
        if (object instanceof JwkSet) {
            return (JwkSet)object;
        }
        if (!(object instanceof Map)) {
            String string = "Value must be a Map<String,?> (JSON Object). Type found: " + object.getClass().getName() + ".";
            throw new IllegalArgumentException(string);
        }
        Map map = Collections.immutable((Map)object);
        if (Collections.isEmpty(map) || !map.containsKey(this.PARAM.getId())) {
            String string = "Missing required " + this.PARAM + " parameter.";
            throw new MalformedKeySetException(string);
        }
        Object object2 = map.get(this.PARAM.getId());
        if (object2 == null) {
            String string = "JWK Set " + this.PARAM + " value cannot be null.";
            throw new MalformedKeySetException(string);
        }
        if (object2 instanceof Supplier) {
            object2 = ((Supplier)object2).get();
        }
        if (!(object2 instanceof Collection)) {
            String string = "JWK Set " + this.PARAM + " value must be a Collection (JSON Array). Type found: " + object2.getClass().getName();
            throw new MalformedKeySetException(string);
        }
        int n = Collections.size((Collection)object2);
        if (n == 0) {
            String string = "JWK Set " + this.PARAM + " collection cannot be empty.";
            throw new MalformedKeySetException(string);
        }
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>(Collections.size((Map)object));
        for (Map.Entry entry : ((Map)object).entrySet()) {
            String string;
            Object object3 = Assert.notNull(entry.getKey(), "JWK Set map key cannot be null.");
            if (!(object3 instanceof String)) {
                string = "JWK Set map keys must be Strings. Encountered key '" + object3 + "' of type " + object3.getClass().getName();
                throw new IllegalArgumentException(string);
            }
            string = (String)object3;
            linkedHashMap.put(string, entry.getValue());
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(n);
        int n2 = 0;
        for (String string : (Collection)object2) {
            block14: {
                try {
                    Jwk<?> jwk = this.JWK_CONVERTER.applyFrom(string);
                    linkedHashSet.add(jwk);
                } catch (UnsupportedKeyException unsupportedKeyException) {
                    if (!this.ignoreUnsupported) {
                        String string2 = "JWK Set keys[" + n2 + "]: " + unsupportedKeyException.getMessage();
                        throw new UnsupportedKeyException(string2, unsupportedKeyException);
                    }
                } catch (KeyException | IllegalArgumentException runtimeException) {
                    if (this.ignoreUnsupported) break block14;
                    String string3 = "JWK Set keys[" + n2 + "]: " + runtimeException.getMessage();
                    throw new MalformedKeySetException(string3, runtimeException);
                }
            }
            ++n2;
        }
        linkedHashMap.remove(this.PARAM.getId());
        linkedHashMap.put(this.PARAM.getId(), linkedHashSet);
        return new DefaultJwkSet(this.PARAM, linkedHashMap);
    }

    @Override
    public Object applyFrom(Object object) {
        return this.applyFrom(object);
    }

    @Override
    public Object applyTo(Object object) {
        return this.applyTo((JwkSet)object);
    }
}

