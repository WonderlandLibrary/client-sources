/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.IdRegistry;
import io.jsonwebtoken.impl.security.AesGcmKeyAlgorithm;
import io.jsonwebtoken.impl.security.AesWrapKeyAlgorithm;
import io.jsonwebtoken.impl.security.DefaultRsaKeyAlgorithm;
import io.jsonwebtoken.impl.security.DirectKeyAlgorithm;
import io.jsonwebtoken.impl.security.EcdhKeyAlgorithm;
import io.jsonwebtoken.impl.security.Pbes2HsAkwAlgorithm;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.KeyAlgorithm;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public final class StandardKeyAlgorithms
extends IdRegistry<KeyAlgorithm<?, ?>> {
    public static final String NAME = "JWE Key Management Algorithm";
    private static final String RSA1_5_ID = "RSA1_5";
    private static final String RSA1_5_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static final String RSA_OAEP_ID = "RSA-OAEP";
    private static final String RSA_OAEP_TRANSFORMATION = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
    private static final String RSA_OAEP_256_ID = "RSA-OAEP-256";
    private static final String RSA_OAEP_256_TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final AlgorithmParameterSpec RSA_OAEP_256_SPEC = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);

    public StandardKeyAlgorithms() {
        super(NAME, Collections.of(new DirectKeyAlgorithm(), new AesWrapKeyAlgorithm(128), new AesWrapKeyAlgorithm(192), new AesWrapKeyAlgorithm(256), new AesGcmKeyAlgorithm(128), new AesGcmKeyAlgorithm(192), new AesGcmKeyAlgorithm(256), new Pbes2HsAkwAlgorithm(128), new Pbes2HsAkwAlgorithm(192), new Pbes2HsAkwAlgorithm(256), new EcdhKeyAlgorithm(), new EcdhKeyAlgorithm(new AesWrapKeyAlgorithm(128)), new EcdhKeyAlgorithm(new AesWrapKeyAlgorithm(192)), new EcdhKeyAlgorithm(new AesWrapKeyAlgorithm(256)), new DefaultRsaKeyAlgorithm(RSA1_5_ID, RSA1_5_TRANSFORMATION), new DefaultRsaKeyAlgorithm(RSA_OAEP_ID, RSA_OAEP_TRANSFORMATION), new DefaultRsaKeyAlgorithm(RSA_OAEP_256_ID, RSA_OAEP_256_TRANSFORMATION, RSA_OAEP_256_SPEC)));
    }
}

