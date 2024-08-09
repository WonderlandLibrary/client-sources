/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.lang.Classes;
import io.jsonwebtoken.lang.Registry;
import io.jsonwebtoken.security.Curve;
import io.jsonwebtoken.security.DynamicJwkBuilder;
import io.jsonwebtoken.security.HashAlgorithm;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.JwkParserBuilder;
import io.jsonwebtoken.security.JwkSetBuilder;
import io.jsonwebtoken.security.JwkSetParserBuilder;
import io.jsonwebtoken.security.KeyOperation;
import io.jsonwebtoken.security.KeyOperationBuilder;
import io.jsonwebtoken.security.KeyOperationPolicyBuilder;
import io.jsonwebtoken.security.PublicJwk;

public final class Jwks {
    private static final String JWKS_BRIDGE_FQCN = "io.jsonwebtoken.impl.security.JwksBridge";
    private static final String BUILDER_FQCN = "io.jsonwebtoken.impl.security.DefaultDynamicJwkBuilder";
    private static final String PARSER_BUILDER_FQCN = "io.jsonwebtoken.impl.security.DefaultJwkParserBuilder";
    private static final String SET_BUILDER_FQCN = "io.jsonwebtoken.impl.security.DefaultJwkSetBuilder";
    private static final String SET_PARSER_BUILDER_FQCN = "io.jsonwebtoken.impl.security.DefaultJwkSetParserBuilder";

    private Jwks() {
    }

    public static DynamicJwkBuilder<?, ?> builder() {
        return (DynamicJwkBuilder)Classes.newInstance(BUILDER_FQCN);
    }

    public static JwkParserBuilder parser() {
        return (JwkParserBuilder)Classes.newInstance(PARSER_BUILDER_FQCN);
    }

    public static JwkSetBuilder set() {
        return (JwkSetBuilder)Classes.newInstance(SET_BUILDER_FQCN);
    }

    public static JwkSetParserBuilder setParser() {
        return (JwkSetParserBuilder)Classes.newInstance(SET_PARSER_BUILDER_FQCN);
    }

    public static String json(PublicJwk<?> publicJwk) {
        return Jwks.UNSAFE_JSON(publicJwk);
    }

    public static String UNSAFE_JSON(Jwk<?> jwk) {
        return (String)Classes.invokeStatic(JWKS_BRIDGE_FQCN, "UNSAFE_JSON", new Class[]{Jwk.class}, jwk);
    }

    public static final class OP {
        private static final String IMPL_CLASSNAME = "io.jsonwebtoken.impl.security.StandardKeyOperations";
        private static final Registry<String, KeyOperation> REGISTRY = (Registry)Classes.newInstance("io.jsonwebtoken.impl.security.StandardKeyOperations");
        private static final String BUILDER_CLASSNAME = "io.jsonwebtoken.impl.security.DefaultKeyOperationBuilder";
        private static final String POLICY_BUILDER_CLASSNAME = "io.jsonwebtoken.impl.security.DefaultKeyOperationPolicyBuilder";
        public static final KeyOperation SIGN = OP.get().forKey("sign");
        public static final KeyOperation VERIFY = OP.get().forKey("verify");
        public static final KeyOperation ENCRYPT = OP.get().forKey("encrypt");
        public static final KeyOperation DECRYPT = OP.get().forKey("decrypt");
        public static final KeyOperation WRAP_KEY = OP.get().forKey("wrapKey");
        public static final KeyOperation UNWRAP_KEY = OP.get().forKey("unwrapKey");
        public static final KeyOperation DERIVE_KEY = OP.get().forKey("deriveKey");
        public static final KeyOperation DERIVE_BITS = OP.get().forKey("deriveBits");

        public static KeyOperationBuilder builder() {
            return (KeyOperationBuilder)Classes.newInstance(BUILDER_CLASSNAME);
        }

        public static KeyOperationPolicyBuilder policy() {
            return (KeyOperationPolicyBuilder)Classes.newInstance(POLICY_BUILDER_CLASSNAME);
        }

        public static Registry<String, KeyOperation> get() {
            return REGISTRY;
        }

        private OP() {
        }
    }

    public static final class HASH {
        private static final String IMPL_CLASSNAME = "io.jsonwebtoken.impl.security.StandardHashAlgorithms";
        private static final Registry<String, HashAlgorithm> REGISTRY = (Registry)Classes.newInstance("io.jsonwebtoken.impl.security.StandardHashAlgorithms");
        public static final HashAlgorithm SHA256 = HASH.get().forKey("sha-256");
        public static final HashAlgorithm SHA384 = HASH.get().forKey("sha-384");
        public static final HashAlgorithm SHA512 = HASH.get().forKey("sha-512");
        public static final HashAlgorithm SHA3_256 = HASH.get().forKey("sha3-256");
        public static final HashAlgorithm SHA3_384 = HASH.get().forKey("sha3-384");
        public static final HashAlgorithm SHA3_512 = HASH.get().forKey("sha3-512");

        public static Registry<String, HashAlgorithm> get() {
            return REGISTRY;
        }

        private HASH() {
        }
    }

    public static final class CRV {
        private static final String IMPL_CLASSNAME = "io.jsonwebtoken.impl.security.StandardCurves";
        private static final Registry<String, Curve> REGISTRY = (Registry)Classes.newInstance("io.jsonwebtoken.impl.security.StandardCurves");
        public static final Curve P256 = CRV.get().forKey("P-256");
        public static final Curve P384 = CRV.get().forKey("P-384");
        public static final Curve P521 = CRV.get().forKey("P-521");
        public static final Curve Ed25519 = CRV.get().forKey("Ed25519");
        public static final Curve Ed448 = CRV.get().forKey("Ed448");
        public static final Curve X25519 = CRV.get().forKey("X25519");
        public static final Curve X448 = CRV.get().forKey("X448");

        public static Registry<String, Curve> get() {
            return REGISTRY;
        }

        private CRV() {
        }
    }
}

