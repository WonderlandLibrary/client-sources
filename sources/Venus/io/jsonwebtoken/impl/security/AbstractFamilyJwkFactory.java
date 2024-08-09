/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.security.DefaultJwkContext;
import io.jsonwebtoken.impl.security.FamilyJwkFactory;
import io.jsonwebtoken.impl.security.JcaTemplate;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.impl.security.KeysBridge;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.KeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.util.Set;

abstract class AbstractFamilyJwkFactory<K extends Key, J extends Jwk<K>>
implements FamilyJwkFactory<K, J> {
    private final String ktyValue;
    private final Class<K> keyType;
    private final Set<Parameter<?>> params;

    protected static <T> void put(JwkContext<?> jwkContext, Parameter<T> parameter, T t) {
        jwkContext.put(parameter.getId(), parameter.applyTo(t));
    }

    AbstractFamilyJwkFactory(String string, Class<K> clazz, Set<Parameter<?>> set) {
        this.ktyValue = Assert.hasText(string, "keyType argument cannot be null or empty.");
        this.keyType = Assert.notNull(clazz, "keyType class cannot be null.");
        this.params = Assert.notEmpty(set, "Parameters collection cannot be null or empty.");
    }

    @Override
    public String getId() {
        return this.ktyValue;
    }

    @Override
    public boolean supports(Key key) {
        return this.keyType.isInstance(key);
    }

    @Override
    public JwkContext<K> newContext(JwkContext<?> jwkContext, K k) {
        Assert.notNull(jwkContext, "Source JwkContext cannot be null.");
        return k != null ? new DefaultJwkContext<K>(this.params, jwkContext, k) : new DefaultJwkContext(this.params, jwkContext, false);
    }

    @Override
    public boolean supports(JwkContext<?> jwkContext) {
        return this.supports((Key)jwkContext.getKey()) || this.supportsKeyValues(jwkContext);
    }

    protected boolean supportsKeyValues(JwkContext<?> jwkContext) {
        return this.ktyValue.equals(jwkContext.getType());
    }

    protected K generateKey(JwkContext<K> jwkContext, CheckedFunction<KeyFactory, K> checkedFunction) {
        return this.generateKey(jwkContext, this.keyType, checkedFunction);
    }

    protected String getKeyFactoryJcaName(JwkContext<?> jwkContext) {
        String string = KeysBridge.findAlgorithm(jwkContext.getKey());
        return Strings.hasText(string) ? string : this.getId();
    }

    protected <T extends Key> T generateKey(JwkContext<?> jwkContext, Class<T> clazz, CheckedFunction<KeyFactory, T> checkedFunction) {
        String string = this.getKeyFactoryJcaName(jwkContext);
        JcaTemplate jcaTemplate = new JcaTemplate(string, jwkContext.getProvider(), jwkContext.getRandom());
        return (T)((Key)jcaTemplate.withKeyFactory(new CheckedFunction<KeyFactory, T>(this, checkedFunction, clazz, jwkContext){
            final CheckedFunction val$fn;
            final Class val$type;
            final JwkContext val$ctx;
            final AbstractFamilyJwkFactory this$0;
            {
                this.this$0 = abstractFamilyJwkFactory;
                this.val$fn = checkedFunction;
                this.val$type = clazz;
                this.val$ctx = jwkContext;
            }

            @Override
            public T apply(KeyFactory keyFactory) {
                try {
                    return (Key)this.val$fn.apply(keyFactory);
                } catch (KeyException keyException) {
                    throw keyException;
                } catch (Exception exception) {
                    String string = "Unable to create " + this.val$type.getSimpleName() + " from JWK " + this.val$ctx + ": " + exception.getMessage();
                    throw new InvalidKeyException(string, exception);
                }
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyFactory)object);
            }
        }));
    }

    @Override
    public final J createJwk(JwkContext<K> jwkContext) {
        Assert.notNull(jwkContext, "JwkContext argument cannot be null.");
        if (!this.supports(jwkContext)) {
            String string = "Unsupported JwkContext.";
            throw new IllegalArgumentException(string);
        }
        K k = jwkContext.getKey();
        if (k != null) {
            jwkContext.setType(this.ktyValue);
            return this.createJwkFromKey(jwkContext);
        }
        return this.createJwkFromValues(jwkContext);
    }

    protected abstract J createJwkFromKey(JwkContext<K> var1);

    protected abstract J createJwkFromValues(JwkContext<K> var1);
}

