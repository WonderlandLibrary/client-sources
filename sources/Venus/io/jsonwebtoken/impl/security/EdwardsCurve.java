/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.impl.security.AbstractCurve;
import io.jsonwebtoken.impl.security.DefaultKeyPairBuilder;
import io.jsonwebtoken.impl.security.EdwardsPublicKeyDeriver;
import io.jsonwebtoken.impl.security.JcaTemplate;
import io.jsonwebtoken.impl.security.KeysBridge;
import io.jsonwebtoken.impl.security.NamedParameterSpecValueFinder;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.KeyException;
import io.jsonwebtoken.security.KeyLengthSupplier;
import io.jsonwebtoken.security.KeyPairBuilder;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class EdwardsCurve
extends AbstractCurve
implements KeyLengthSupplier {
    private static final String OID_PREFIX = "1.3.101.";
    private static final byte[] ASN1_OID_PREFIX = new byte[]{6, 3, 43, 101};
    private static final Function<Key, String> CURVE_NAME_FINDER = new NamedParameterSpecValueFinder();
    public static final EdwardsCurve X25519 = new EdwardsCurve("X25519", 110);
    public static final EdwardsCurve X448 = new EdwardsCurve("X448", 111);
    public static final EdwardsCurve Ed25519 = new EdwardsCurve("Ed25519", 112);
    public static final EdwardsCurve Ed448 = new EdwardsCurve("Ed448", 113);
    public static final Collection<EdwardsCurve> VALUES = Collections.of(X25519, X448, Ed25519, Ed448);
    private static final Map<String, EdwardsCurve> REGISTRY = new LinkedHashMap<String, EdwardsCurve>(8);
    private static final Map<Integer, EdwardsCurve> BY_OID_TERMINAL_NODE = new LinkedHashMap<Integer, EdwardsCurve>(4);
    private final String OID;
    final byte[] ASN1_OID;
    private final int keyBitLength;
    private final int encodedKeyByteLength;
    private final byte[] PUBLIC_KEY_ASN1_PREFIX;
    private final byte[] PRIVATE_KEY_ASN1_PREFIX;
    private final byte[] PRIVATE_KEY_JDK11_PREFIX;
    private final boolean signatureCurve;

    private static byte[] publicKeyAsn1Prefix(int n, byte[] byArray) {
        return Bytes.concat({48, (byte)(n + 10), 48, 5}, byArray, {3, (byte)(n + 1), 0});
    }

    private static byte[] privateKeyPkcs8Prefix(int n, byte[] byArray, boolean bl) {
        byte[] byArray2;
        if (bl) {
            byte[] byArray3 = new byte[4];
            byArray3[0] = 4;
            byArray3[1] = (byte)(n + 2);
            byArray3[2] = 4;
            byArray2 = byArray3;
            byArray3[3] = (byte)n;
        } else {
            byte[] byArray4 = new byte[2];
            byArray4[0] = 4;
            byArray2 = byArray4;
            byArray4[1] = (byte)n;
        }
        byte[] byArray5 = byArray2;
        return Bytes.concat({48, (byte)(5 + byArray.length + byArray5.length + n), 2, 1, 0, 48, 5}, byArray, byArray5);
    }

    EdwardsCurve(String string, int n) {
        super(string, string);
        if (n < 110 || n > 113) {
            String string2 = "Invalid Edwards Curve ASN.1 OID terminal node value";
            throw new IllegalArgumentException(string2);
        }
        this.keyBitLength = n % 2 == 0 ? 255 : 448;
        int n2 = n == 113 ? this.keyBitLength + 8 : this.keyBitLength;
        this.encodedKeyByteLength = Bytes.length(n2);
        this.OID = OID_PREFIX + n;
        this.signatureCurve = n == 112 || n == 113;
        byte[] byArray = new byte[]{(byte)n};
        this.ASN1_OID = Bytes.concat(ASN1_OID_PREFIX, byArray);
        this.PUBLIC_KEY_ASN1_PREFIX = EdwardsCurve.publicKeyAsn1Prefix(this.encodedKeyByteLength, this.ASN1_OID);
        this.PRIVATE_KEY_ASN1_PREFIX = EdwardsCurve.privateKeyPkcs8Prefix(this.encodedKeyByteLength, this.ASN1_OID, true);
        this.PRIVATE_KEY_JDK11_PREFIX = EdwardsCurve.privateKeyPkcs8Prefix(this.encodedKeyByteLength, this.ASN1_OID, false);
    }

    @Override
    public int getKeyBitLength() {
        return this.keyBitLength;
    }

    public byte[] getKeyMaterial(Key key) {
        try {
            return this.doGetKeyMaterial(key);
        } catch (Throwable throwable) {
            if (throwable instanceof KeyException) {
                throw (KeyException)throwable;
            }
            String string = "Invalid " + this.getId() + " ASN.1 encoding: " + throwable.getMessage();
            throw new InvalidKeyException(string, throwable);
        }
    }

    protected byte[] doGetKeyMaterial(Key key) {
        byte by;
        byte[] byArray = KeysBridge.getEncoded(key);
        int n = Bytes.indexOf(byArray, this.ASN1_OID);
        Assert.gt(n, -1, "Missing or incorrect algorithm OID.");
        int n2 = 0;
        if (byArray[n += this.ASN1_OID.length] == 5) {
            by = byArray[++n];
            Assert.eq(Integer.valueOf(by), 0, "OID NULL terminator should indicate zero unused bytes.");
            ++n;
        }
        if (byArray[n] == 3) {
            int n3 = ++n;
            n2 = byArray[n3];
            int n4 = ++n;
            ++n;
            by = byArray[n4];
            Assert.eq(Integer.valueOf(by), 0, "BIT STREAM should not indicate unused bytes.");
            --n2;
        } else if (byArray[n] == 4) {
            int n5 = ++n;
            n2 = byArray[n5];
            if (byArray[++n] == 4) {
                int n6 = ++n;
                ++n;
                n2 = byArray[n6];
            }
        }
        Assert.eq(n2, this.encodedKeyByteLength, "Invalid key length.");
        byte[] byArray2 = Arrays.copyOfRange(byArray, n, n + n2);
        n2 = Bytes.length(byArray2);
        Assert.eq(n2, this.encodedKeyByteLength, "Invalid key length.");
        return byArray2;
    }

    private void assertLength(byte[] byArray, boolean bl) {
        int n = Bytes.length(byArray);
        if (n != this.encodedKeyByteLength) {
            String string = "Invalid " + this.getId() + " encoded " + (bl ? "PublicKey" : "PrivateKey") + " length. Should be " + Bytes.bytesMsg(this.encodedKeyByteLength) + ", found " + Bytes.bytesMsg(n) + ".";
            throw new InvalidKeyException(string);
        }
    }

    public PublicKey toPublicKey(byte[] byArray, Provider provider) {
        this.assertLength(byArray, true);
        byte[] byArray2 = Bytes.concat(this.PUBLIC_KEY_ASN1_PREFIX, byArray);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byArray2);
        JcaTemplate jcaTemplate = new JcaTemplate(this.getJcaName(), provider);
        return jcaTemplate.generatePublic(x509EncodedKeySpec);
    }

    KeySpec privateKeySpec(byte[] byArray, boolean bl) {
        byte[] byArray2 = bl ? this.PRIVATE_KEY_ASN1_PREFIX : this.PRIVATE_KEY_JDK11_PREFIX;
        byte[] byArray3 = Bytes.concat(byArray2, byArray);
        return new PKCS8EncodedKeySpec(byArray3);
    }

    public PrivateKey toPrivateKey(byte[] byArray, Provider provider) {
        this.assertLength(byArray, false);
        KeySpec keySpec = this.privateKeySpec(byArray, false);
        JcaTemplate jcaTemplate = new JcaTemplate(this.getJcaName(), provider);
        return jcaTemplate.generatePrivate(keySpec);
    }

    public boolean isSignatureCurve() {
        return this.signatureCurve;
    }

    @Override
    public KeyPairBuilder keyPair() {
        return new DefaultKeyPairBuilder(this.getJcaName(), this.keyBitLength);
    }

    public static boolean isEdwards(Key key) {
        if (key == null) {
            return true;
        }
        String string = Strings.clean(key.getAlgorithm());
        return "EdDSA".equals(string) || "XDH".equals(string) || EdwardsCurve.findByKey(key) != null;
    }

    public static PublicKey derivePublic(PrivateKey privateKey) throws KeyException {
        return EdwardsPublicKeyDeriver.INSTANCE.apply(privateKey);
    }

    public static EdwardsCurve findById(String string) {
        return REGISTRY.get(string);
    }

    public static EdwardsCurve findByKey(Key key) {
        if (key == null) {
            return null;
        }
        String string = key.getAlgorithm();
        EdwardsCurve edwardsCurve = EdwardsCurve.findById(string);
        if (edwardsCurve == null) {
            string = CURVE_NAME_FINDER.apply(key);
            edwardsCurve = EdwardsCurve.findById(string);
        }
        byte[] byArray = KeysBridge.findEncoded(key);
        if (edwardsCurve == null && !Bytes.isEmpty(byArray)) {
            int n = EdwardsCurve.findOidTerminalNode(byArray);
            edwardsCurve = BY_OID_TERMINAL_NODE.get(n);
        }
        if (edwardsCurve != null && !Bytes.isEmpty(byArray)) {
            try {
                edwardsCurve.getKeyMaterial(key);
            } catch (Throwable throwable) {
                edwardsCurve = null;
            }
        }
        return edwardsCurve;
    }

    @Override
    public boolean contains(Key key) {
        EdwardsCurve edwardsCurve = EdwardsCurve.findByKey(key);
        return edwardsCurve.equals(this);
    }

    private static int findOidTerminalNode(byte[] byArray) {
        int n = Bytes.indexOf(byArray, ASN1_OID_PREFIX);
        if (n > -1 && (n += ASN1_OID_PREFIX.length) < byArray.length) {
            return byArray[n];
        }
        return 1;
    }

    public static EdwardsCurve forKey(Key key) {
        Assert.notNull(key, "Key cannot be null.");
        EdwardsCurve edwardsCurve = EdwardsCurve.findByKey(key);
        if (edwardsCurve == null) {
            String string = "Unrecognized Edwards Curve key: [" + KeysBridge.toString(key) + "]";
            throw new InvalidKeyException(string);
        }
        return edwardsCurve;
    }

    static <K extends Key> K assertEdwards(K k) {
        EdwardsCurve.forKey(k);
        return k;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String getJcaName() {
        return super.getJcaName();
    }

    @Override
    public String getId() {
        return super.getId();
    }

    static {
        for (EdwardsCurve edwardsCurve : VALUES) {
            byte by = edwardsCurve.ASN1_OID[edwardsCurve.ASN1_OID.length - 1];
            BY_OID_TERMINAL_NODE.put(Integer.valueOf(by), edwardsCurve);
            REGISTRY.put(edwardsCurve.getId(), edwardsCurve);
            REGISTRY.put(edwardsCurve.OID, edwardsCurve);
        }
    }
}

