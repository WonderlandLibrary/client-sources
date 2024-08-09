/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.security.AbstractCurve;
import io.jsonwebtoken.impl.security.DefaultKeyPairBuilder;
import io.jsonwebtoken.impl.security.JcaTemplate;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.KeyPairBuilder;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.interfaces.ECKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECFieldFp;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ECCurve
extends AbstractCurve {
    private static final BigInteger TWO = BigInteger.valueOf(2L);
    private static final BigInteger THREE = BigInteger.valueOf(3L);
    static final String KEY_PAIR_GENERATOR_JCA_NAME = "EC";
    public static final ECCurve P256 = new ECCurve("P-256", "secp256r1");
    public static final ECCurve P384 = new ECCurve("P-384", "secp384r1");
    public static final ECCurve P521 = new ECCurve("P-521", "secp521r1");
    public static final Collection<ECCurve> VALUES = Collections.setOf(P256, P384, P521);
    private static final Map<String, ECCurve> BY_ID = new LinkedHashMap<String, ECCurve>(3);
    private static final Map<EllipticCurve, ECCurve> BY_JCA_CURVE = new LinkedHashMap<EllipticCurve, ECCurve>(3);
    private final ECParameterSpec spec;

    static EllipticCurve assertJcaCurve(ECKey eCKey) {
        Assert.notNull(eCKey, "ECKey cannot be null.");
        ECParameterSpec eCParameterSpec = Assert.notNull(eCKey.getParams(), "ECKey params() cannot be null.");
        return Assert.notNull(eCParameterSpec.getCurve(), "ECKey params().getCurve() cannot be null.");
    }

    static ECCurve findById(String string) {
        return BY_ID.get(string);
    }

    static ECCurve findByJcaCurve(EllipticCurve ellipticCurve) {
        return BY_JCA_CURVE.get(ellipticCurve);
    }

    static ECCurve findByKey(Key key) {
        ECPublicKey eCPublicKey;
        ECPoint eCPoint;
        if (!(key instanceof ECKey)) {
            return null;
        }
        ECKey eCKey = (ECKey)((Object)key);
        ECParameterSpec eCParameterSpec = eCKey.getParams();
        if (eCParameterSpec == null) {
            return null;
        }
        EllipticCurve ellipticCurve = eCParameterSpec.getCurve();
        ECCurve eCCurve = BY_JCA_CURVE.get(ellipticCurve);
        if (eCCurve != null && key instanceof ECPublicKey && ((eCPoint = (eCPublicKey = (ECPublicKey)key).getW()) == null || !eCCurve.contains(eCPoint))) {
            eCCurve = null;
        }
        return eCCurve;
    }

    static ECPublicKeySpec publicKeySpec(ECPrivateKey eCPrivateKey) throws IllegalArgumentException {
        EllipticCurve ellipticCurve = ECCurve.assertJcaCurve(eCPrivateKey);
        ECCurve eCCurve = BY_JCA_CURVE.get(ellipticCurve);
        Assert.notNull(eCCurve, "There is no JWA-standard Elliptic Curve for specified ECPrivateKey.");
        ECPoint eCPoint = eCCurve.multiply(eCPrivateKey.getS());
        return new ECPublicKeySpec(eCPoint, eCCurve.spec);
    }

    public ECCurve(String string, String string2) {
        super(string, string2);
        JcaTemplate jcaTemplate = new JcaTemplate(KEY_PAIR_GENERATOR_JCA_NAME);
        this.spec = jcaTemplate.withAlgorithmParameters(new CheckedFunction<AlgorithmParameters, ECParameterSpec>(this){
            final ECCurve this$0;
            {
                this.this$0 = eCCurve;
            }

            @Override
            public ECParameterSpec apply(AlgorithmParameters algorithmParameters) throws Exception {
                algorithmParameters.init(new ECGenParameterSpec(this.this$0.getJcaName()));
                return algorithmParameters.getParameterSpec(ECParameterSpec.class);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((AlgorithmParameters)object);
            }
        });
    }

    public ECParameterSpec toParameterSpec() {
        return this.spec;
    }

    @Override
    public KeyPairBuilder keyPair() {
        return new DefaultKeyPairBuilder(KEY_PAIR_GENERATOR_JCA_NAME, this.toParameterSpec());
    }

    @Override
    public boolean contains(Key key) {
        if (key instanceof ECPublicKey) {
            ECPublicKey eCPublicKey = (ECPublicKey)key;
            ECParameterSpec eCParameterSpec = eCPublicKey.getParams();
            return eCParameterSpec != null && this.spec.getCurve().equals(eCParameterSpec.getCurve()) && this.contains(eCPublicKey.getW());
        }
        return true;
    }

    boolean contains(ECPoint eCPoint) {
        return ECCurve.contains(this.spec.getCurve(), eCPoint);
    }

    static boolean contains(EllipticCurve ellipticCurve, ECPoint eCPoint) {
        if (eCPoint == null || ECPoint.POINT_INFINITY.equals(eCPoint)) {
            return true;
        }
        BigInteger bigInteger = ellipticCurve.getA();
        BigInteger bigInteger2 = ellipticCurve.getB();
        BigInteger bigInteger3 = eCPoint.getAffineX();
        BigInteger bigInteger4 = eCPoint.getAffineY();
        BigInteger bigInteger5 = ((ECFieldFp)ellipticCurve.getField()).getP();
        if (bigInteger3.compareTo(BigInteger.ZERO) < 0 || bigInteger3.compareTo(bigInteger5) >= 0 || bigInteger4.compareTo(BigInteger.ZERO) < 0 || bigInteger4.compareTo(bigInteger5) >= 0) {
            return true;
        }
        BigInteger bigInteger6 = bigInteger4.modPow(TWO, bigInteger5);
        BigInteger bigInteger7 = bigInteger3.modPow(THREE, bigInteger5).add(bigInteger.multiply(bigInteger3)).add(bigInteger2).mod(bigInteger5);
        return bigInteger6.equals(bigInteger7);
    }

    private ECPoint multiply(BigInteger bigInteger) {
        return this.multiply(this.spec.getGenerator(), bigInteger);
    }

    private ECPoint multiply(ECPoint eCPoint, BigInteger bigInteger) {
        if (ECPoint.POINT_INFINITY.equals(eCPoint)) {
            return eCPoint;
        }
        BigInteger bigInteger2 = this.spec.getOrder();
        BigInteger bigInteger3 = bigInteger.mod(bigInteger2);
        ECPoint eCPoint2 = ECPoint.POINT_INFINITY;
        ECPoint eCPoint3 = eCPoint;
        for (int i = bigInteger3.bitLength() - 1; i >= 0; --i) {
            if (bigInteger3.testBit(i)) {
                eCPoint2 = this.add(eCPoint2, eCPoint3);
                eCPoint3 = this.doublePoint(eCPoint3);
                continue;
            }
            eCPoint3 = this.add(eCPoint2, eCPoint3);
            eCPoint2 = this.doublePoint(eCPoint2);
        }
        return eCPoint2;
    }

    private ECPoint add(ECPoint eCPoint, ECPoint eCPoint2) {
        if (ECPoint.POINT_INFINITY.equals(eCPoint)) {
            return eCPoint2;
        }
        if (ECPoint.POINT_INFINITY.equals(eCPoint2)) {
            return eCPoint;
        }
        if (eCPoint.equals(eCPoint2)) {
            return this.doublePoint(eCPoint);
        }
        EllipticCurve ellipticCurve = this.spec.getCurve();
        BigInteger bigInteger = eCPoint.getAffineX();
        BigInteger bigInteger2 = eCPoint.getAffineY();
        BigInteger bigInteger3 = eCPoint2.getAffineX();
        BigInteger bigInteger4 = eCPoint2.getAffineY();
        BigInteger bigInteger5 = ((ECFieldFp)ellipticCurve.getField()).getP();
        BigInteger bigInteger6 = bigInteger4.subtract(bigInteger2).multiply(bigInteger3.subtract(bigInteger).modInverse(bigInteger5)).mod(bigInteger5);
        BigInteger bigInteger7 = bigInteger6.pow(2).subtract(bigInteger).subtract(bigInteger3).mod(bigInteger5);
        BigInteger bigInteger8 = bigInteger6.multiply(bigInteger.subtract(bigInteger7)).subtract(bigInteger2).mod(bigInteger5);
        return new ECPoint(bigInteger7, bigInteger8);
    }

    private ECPoint doublePoint(ECPoint eCPoint) {
        if (ECPoint.POINT_INFINITY.equals(eCPoint)) {
            return eCPoint;
        }
        EllipticCurve ellipticCurve = this.spec.getCurve();
        BigInteger bigInteger = eCPoint.getAffineX();
        BigInteger bigInteger2 = eCPoint.getAffineY();
        BigInteger bigInteger3 = ((ECFieldFp)ellipticCurve.getField()).getP();
        BigInteger bigInteger4 = ellipticCurve.getA();
        BigInteger bigInteger5 = THREE.multiply(bigInteger.pow(2)).add(bigInteger4).mod(bigInteger3).multiply(TWO.multiply(bigInteger2).modInverse(bigInteger3)).mod(bigInteger3);
        BigInteger bigInteger6 = bigInteger5.pow(2).subtract(TWO.multiply(bigInteger)).mod(bigInteger3);
        BigInteger bigInteger7 = bigInteger5.multiply(bigInteger.subtract(bigInteger6)).subtract(bigInteger2).mod(bigInteger3);
        return new ECPoint(bigInteger6, bigInteger7);
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
        for (ECCurve eCCurve : VALUES) {
            BY_ID.put(eCCurve.getId(), eCCurve);
        }
        for (ECCurve eCCurve : VALUES) {
            BY_JCA_CURVE.put(eCCurve.spec.getCurve(), eCCurve);
        }
    }
}

