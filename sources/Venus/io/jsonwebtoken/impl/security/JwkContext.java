/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.Identifiable;
import io.jsonwebtoken.impl.X509Context;
import io.jsonwebtoken.impl.lang.Nameable;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.ParameterReadable;
import io.jsonwebtoken.security.HashAlgorithm;
import io.jsonwebtoken.security.KeyOperation;
import java.security.Key;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface JwkContext<K extends Key>
extends Identifiable,
Map<String, Object>,
ParameterReadable,
Nameable,
X509Context<JwkContext<K>> {
    public JwkContext<K> parameter(Parameter<?> var1);

    public JwkContext<K> setId(String var1);

    public JwkContext<K> setIdThumbprintAlgorithm(HashAlgorithm var1);

    public HashAlgorithm getIdThumbprintAlgorithm();

    public String getType();

    public JwkContext<K> setType(String var1);

    public Set<KeyOperation> getOperations();

    public JwkContext<K> setOperations(Collection<? extends KeyOperation> var1);

    public String getAlgorithm();

    public JwkContext<K> setAlgorithm(String var1);

    public String getPublicKeyUse();

    public JwkContext<K> setPublicKeyUse(String var1);

    public boolean isSigUse();

    public K getKey();

    public JwkContext<K> setKey(K var1);

    public PublicKey getPublicKey();

    public JwkContext<K> setPublicKey(PublicKey var1);

    public Provider getProvider();

    public JwkContext<K> setProvider(Provider var1);

    public SecureRandom getRandom();

    public JwkContext<K> setRandom(SecureRandom var1);
}

