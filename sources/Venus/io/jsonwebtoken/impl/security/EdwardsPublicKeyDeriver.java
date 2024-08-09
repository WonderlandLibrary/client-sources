/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import io.jsonwebtoken.impl.security.KeysBridge;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.KeyPairBuilder;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

final class EdwardsPublicKeyDeriver
implements Function<PrivateKey, PublicKey> {
    public static final Function<PrivateKey, PublicKey> INSTANCE = new EdwardsPublicKeyDeriver();

    private EdwardsPublicKeyDeriver() {
    }

    @Override
    public PublicKey apply(PrivateKey privateKey) {
        EdwardsCurve edwardsCurve = EdwardsCurve.findByKey(privateKey);
        if (edwardsCurve == null) {
            String string = "Unable to derive Edwards-curve PublicKey for specified PrivateKey: " + KeysBridge.toString(privateKey);
            throw new InvalidKeyException(string);
        }
        byte[] byArray = edwardsCurve.getKeyMaterial(privateKey);
        ConstantRandom constantRandom = new ConstantRandom(byArray);
        KeyPair keyPair = (KeyPair)((KeyPairBuilder)edwardsCurve.keyPair().random(constantRandom)).build();
        Assert.stateNotNull(keyPair, "Edwards curve generated keypair cannot be null.");
        return Assert.stateNotNull(keyPair.getPublic(), "Edwards curve KeyPair must have a PublicKey");
    }

    @Override
    public Object apply(Object object) {
        return this.apply((PrivateKey)object);
    }

    private static final class ConstantRandom
    extends SecureRandom {
        private final byte[] value;

        public ConstantRandom(byte[] byArray) {
            this.value = (byte[])byArray.clone();
        }

        @Override
        public void nextBytes(byte[] byArray) {
            System.arraycopy(this.value, 0, byArray, 0, this.value.length);
        }
    }
}

