/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JweHeaderMutator;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.io.CompressionAlgorithm;
import io.jsonwebtoken.lang.Builder;
import io.jsonwebtoken.lang.Classes;
import io.jsonwebtoken.lang.Registry;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.Password;
import io.jsonwebtoken.security.SecretKeyAlgorithm;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import io.jsonwebtoken.security.X509Builder;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import javax.crypto.SecretKey;

public final class Jwts {
    private static <T> T get(Registry<String, ?> registry, String string) {
        return (T)registry.forKey(string);
    }

    public static HeaderBuilder header() {
        return (HeaderBuilder)Classes.newInstance("io.jsonwebtoken.impl.DefaultJwtHeaderBuilder");
    }

    public static ClaimsBuilder claims() {
        return (ClaimsBuilder)Classes.newInstance("io.jsonwebtoken.impl.DefaultClaimsBuilder");
    }

    @Deprecated
    public static Claims claims(Map<String, Object> map) {
        return (Claims)((ClaimsBuilder)Jwts.claims().add(map)).build();
    }

    public static JwtBuilder builder() {
        return (JwtBuilder)Classes.newInstance("io.jsonwebtoken.impl.DefaultJwtBuilder");
    }

    public static JwtParserBuilder parser() {
        return (JwtParserBuilder)Classes.newInstance("io.jsonwebtoken.impl.DefaultJwtParserBuilder");
    }

    private Jwts() {
    }

    static Object access$000(Registry registry, String string) {
        return Jwts.get(registry, string);
    }

    public static interface HeaderBuilder
    extends JweHeaderMutator<HeaderBuilder>,
    X509Builder<HeaderBuilder>,
    Builder<Header> {
    }

    public static final class ZIP {
        private static final String IMPL_CLASSNAME = "io.jsonwebtoken.impl.io.StandardCompressionAlgorithms";
        private static final Registry<String, CompressionAlgorithm> REGISTRY = (Registry)Classes.newInstance("io.jsonwebtoken.impl.io.StandardCompressionAlgorithms");
        public static final CompressionAlgorithm DEF = ZIP.get().forKey("DEF");
        public static final CompressionAlgorithm GZIP = ZIP.get().forKey("GZIP");

        public static Registry<String, CompressionAlgorithm> get() {
            return REGISTRY;
        }

        private ZIP() {
        }
    }

    public static final class KEY {
        private static final String IMPL_CLASSNAME = "io.jsonwebtoken.impl.security.StandardKeyAlgorithms";
        private static final Registry<String, KeyAlgorithm<?, ?>> REGISTRY = (Registry)Classes.newInstance("io.jsonwebtoken.impl.security.StandardKeyAlgorithms");
        public static final KeyAlgorithm<SecretKey, SecretKey> DIRECT = (KeyAlgorithm)Jwts.access$000(REGISTRY, "dir");
        public static final SecretKeyAlgorithm A128KW = (SecretKeyAlgorithm)Jwts.access$000(REGISTRY, "A128KW");
        public static final SecretKeyAlgorithm A192KW = (SecretKeyAlgorithm)Jwts.access$000(REGISTRY, "A192KW");
        public static final SecretKeyAlgorithm A256KW = (SecretKeyAlgorithm)Jwts.access$000(REGISTRY, "A256KW");
        public static final SecretKeyAlgorithm A128GCMKW = (SecretKeyAlgorithm)Jwts.access$000(REGISTRY, "A128GCMKW");
        public static final SecretKeyAlgorithm A192GCMKW = (SecretKeyAlgorithm)Jwts.access$000(REGISTRY, "A192GCMKW");
        public static final SecretKeyAlgorithm A256GCMKW = (SecretKeyAlgorithm)Jwts.access$000(REGISTRY, "A256GCMKW");
        public static final KeyAlgorithm<Password, Password> PBES2_HS256_A128KW = (KeyAlgorithm)Jwts.access$000(REGISTRY, "PBES2-HS256+A128KW");
        public static final KeyAlgorithm<Password, Password> PBES2_HS384_A192KW = (KeyAlgorithm)Jwts.access$000(REGISTRY, "PBES2-HS384+A192KW");
        public static final KeyAlgorithm<Password, Password> PBES2_HS512_A256KW = (KeyAlgorithm)Jwts.access$000(REGISTRY, "PBES2-HS512+A256KW");
        public static final KeyAlgorithm<PublicKey, PrivateKey> RSA1_5 = (KeyAlgorithm)Jwts.access$000(REGISTRY, "RSA1_5");
        public static final KeyAlgorithm<PublicKey, PrivateKey> RSA_OAEP = (KeyAlgorithm)Jwts.access$000(REGISTRY, "RSA-OAEP");
        public static final KeyAlgorithm<PublicKey, PrivateKey> RSA_OAEP_256 = (KeyAlgorithm)Jwts.access$000(REGISTRY, "RSA-OAEP-256");
        public static final KeyAlgorithm<PublicKey, PrivateKey> ECDH_ES = (KeyAlgorithm)Jwts.access$000(REGISTRY, "ECDH-ES");
        public static final KeyAlgorithm<PublicKey, PrivateKey> ECDH_ES_A128KW = (KeyAlgorithm)Jwts.access$000(REGISTRY, "ECDH-ES+A128KW");
        public static final KeyAlgorithm<PublicKey, PrivateKey> ECDH_ES_A192KW = (KeyAlgorithm)Jwts.access$000(REGISTRY, "ECDH-ES+A192KW");
        public static final KeyAlgorithm<PublicKey, PrivateKey> ECDH_ES_A256KW = (KeyAlgorithm)Jwts.access$000(REGISTRY, "ECDH-ES+A256KW");

        public static Registry<String, KeyAlgorithm<?, ?>> get() {
            return REGISTRY;
        }

        private KEY() {
        }
    }

    public static final class SIG {
        private static final String IMPL_CLASSNAME = "io.jsonwebtoken.impl.security.StandardSecureDigestAlgorithms";
        private static final Registry<String, SecureDigestAlgorithm<?, ?>> REGISTRY = (Registry)Classes.newInstance("io.jsonwebtoken.impl.security.StandardSecureDigestAlgorithms");
        public static final SecureDigestAlgorithm<Key, Key> NONE = (SecureDigestAlgorithm)Jwts.access$000(REGISTRY, "none");
        public static final MacAlgorithm HS256 = (MacAlgorithm)Jwts.access$000(REGISTRY, "HS256");
        public static final MacAlgorithm HS384 = (MacAlgorithm)Jwts.access$000(REGISTRY, "HS384");
        public static final MacAlgorithm HS512 = (MacAlgorithm)Jwts.access$000(REGISTRY, "HS512");
        public static final SignatureAlgorithm RS256 = (SignatureAlgorithm)Jwts.access$000(REGISTRY, "RS256");
        public static final SignatureAlgorithm RS384 = (SignatureAlgorithm)Jwts.access$000(REGISTRY, "RS384");
        public static final SignatureAlgorithm RS512 = (SignatureAlgorithm)Jwts.access$000(REGISTRY, "RS512");
        public static final SignatureAlgorithm PS256 = (SignatureAlgorithm)Jwts.access$000(REGISTRY, "PS256");
        public static final SignatureAlgorithm PS384 = (SignatureAlgorithm)Jwts.access$000(REGISTRY, "PS384");
        public static final SignatureAlgorithm PS512 = (SignatureAlgorithm)Jwts.access$000(REGISTRY, "PS512");
        public static final SignatureAlgorithm ES256 = (SignatureAlgorithm)Jwts.access$000(REGISTRY, "ES256");
        public static final SignatureAlgorithm ES384 = (SignatureAlgorithm)Jwts.access$000(REGISTRY, "ES384");
        public static final SignatureAlgorithm ES512 = (SignatureAlgorithm)Jwts.access$000(REGISTRY, "ES512");
        public static final SignatureAlgorithm EdDSA = (SignatureAlgorithm)Jwts.access$000(REGISTRY, "EdDSA");

        private SIG() {
        }

        public static Registry<String, SecureDigestAlgorithm<?, ?>> get() {
            return REGISTRY;
        }
    }

    public static final class ENC {
        private static final String IMPL_CLASSNAME = "io.jsonwebtoken.impl.security.StandardEncryptionAlgorithms";
        private static final Registry<String, AeadAlgorithm> REGISTRY = (Registry)Classes.newInstance("io.jsonwebtoken.impl.security.StandardEncryptionAlgorithms");
        public static final AeadAlgorithm A128CBC_HS256 = ENC.get().forKey("A128CBC-HS256");
        public static final AeadAlgorithm A192CBC_HS384 = ENC.get().forKey("A192CBC-HS384");
        public static final AeadAlgorithm A256CBC_HS512 = ENC.get().forKey("A256CBC-HS512");
        public static final AeadAlgorithm A128GCM = ENC.get().forKey("A128GCM");
        public static final AeadAlgorithm A192GCM = ENC.get().forKey("A192GCM");
        public static final AeadAlgorithm A256GCM = ENC.get().forKey("A256GCM");

        public static Registry<String, AeadAlgorithm> get() {
            return REGISTRY;
        }

        private ENC() {
        }
    }
}

