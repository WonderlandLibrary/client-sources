/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.impl.lang.Functions;
import io.jsonwebtoken.impl.lang.OptionalMethodInvoker;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

public class NamedParameterSpecValueFinder
implements Function<Key, String> {
    private static final Function<Key, AlgorithmParameterSpec> EDEC_KEY_GET_PARAMS = new OptionalMethodInvoker<Key, AlgorithmParameterSpec>("java.security.interfaces.EdECKey", "getParams");
    private static final Function<Key, AlgorithmParameterSpec> XEC_KEY_GET_PARAMS = new OptionalMethodInvoker<Key, AlgorithmParameterSpec>("java.security.interfaces.XECKey", "getParams");
    private static final Function<Object, String> GET_NAME = new OptionalMethodInvoker<Object, String>("java.security.spec.NamedParameterSpec", "getName");
    private static final Function<Key, String> COMPOSED = Functions.andThen(Functions.firstResult(EDEC_KEY_GET_PARAMS, XEC_KEY_GET_PARAMS), GET_NAME);

    @Override
    public String apply(Key key) {
        return COMPOSED.apply(key);
    }

    @Override
    public Object apply(Object object) {
        return this.apply((Key)object);
    }
}

