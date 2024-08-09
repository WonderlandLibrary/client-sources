/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.RequiredParameterReader;
import io.jsonwebtoken.impl.security.AbstractEcJwkFactory;
import io.jsonwebtoken.impl.security.DefaultEcPublicJwk;
import io.jsonwebtoken.impl.security.ECCurve;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.EcPublicJwk;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Jwk;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class EcPublicJwkFactory
extends AbstractEcJwkFactory<ECPublicKey, EcPublicJwk> {
    private static final String UNSUPPORTED_CURVE_MSG = "The specified ECKey curve does not match a JWA standard curve id.";
    static final EcPublicJwkFactory INSTANCE = new EcPublicJwkFactory();

    EcPublicJwkFactory() {
        super(ECPublicKey.class, DefaultEcPublicJwk.PARAMS);
    }

    protected static String keyContainsErrorMessage(String string) {
        Assert.hasText(string, "curveId cannot be null or empty.");
        String string2 = "ECPublicKey's ECPoint does not exist on elliptic curve '%s' and may not be used to create '%s' JWKs.";
        return String.format(string2, string, string);
    }

    protected static String jwkContainsErrorMessage(String string, Map<String, ?> map) {
        Assert.hasText(string, "curveId cannot be null or empty.");
        String string2 = "EC JWK x,y coordinates do not exist on elliptic curve '%s'. This could be due simply to an incorrectly-created JWK or possibly an attempted Invalid Curve Attack (see https://safecurves.cr.yp.to/twist.html for more information).";
        return String.format(string2, string, map);
    }

    protected static String getJwaIdByCurve(EllipticCurve ellipticCurve) {
        ECCurve eCCurve = ECCurve.findByJcaCurve(ellipticCurve);
        if (eCCurve == null) {
            throw new InvalidKeyException(UNSUPPORTED_CURVE_MSG);
        }
        return eCCurve.getId();
    }

    @Override
    protected EcPublicJwk createJwkFromKey(JwkContext<ECPublicKey> jwkContext) {
        ECPublicKey eCPublicKey = jwkContext.getKey();
        ECParameterSpec eCParameterSpec = eCPublicKey.getParams();
        EllipticCurve ellipticCurve = eCParameterSpec.getCurve();
        ECPoint eCPoint = eCPublicKey.getW();
        String string = EcPublicJwkFactory.getJwaIdByCurve(ellipticCurve);
        if (!ECCurve.contains(ellipticCurve, eCPoint)) {
            String string2 = EcPublicJwkFactory.keyContainsErrorMessage(string);
            throw new InvalidKeyException(string2);
        }
        jwkContext.put((ECPublicKey)((Object)DefaultEcPublicJwk.CRV.getId()), string);
        int n = ellipticCurve.getField().getFieldSize();
        String string3 = EcPublicJwkFactory.toOctetString(n, eCPoint.getAffineX());
        jwkContext.put((ECPublicKey)((Object)DefaultEcPublicJwk.X.getId()), string3);
        String string4 = EcPublicJwkFactory.toOctetString(n, eCPoint.getAffineY());
        jwkContext.put((ECPublicKey)((Object)DefaultEcPublicJwk.Y.getId()), string4);
        return new DefaultEcPublicJwk(jwkContext);
    }

    @Override
    protected EcPublicJwk createJwkFromValues(JwkContext<ECPublicKey> jwkContext) {
        ECPoint eCPoint;
        RequiredParameterReader requiredParameterReader = new RequiredParameterReader(jwkContext);
        String string = requiredParameterReader.get(DefaultEcPublicJwk.CRV);
        BigInteger bigInteger = requiredParameterReader.get(DefaultEcPublicJwk.X);
        BigInteger bigInteger2 = requiredParameterReader.get(DefaultEcPublicJwk.Y);
        ECCurve eCCurve = EcPublicJwkFactory.getCurveByJwaId(string);
        if (!eCCurve.contains(eCPoint = new ECPoint(bigInteger, bigInteger2))) {
            String string2 = EcPublicJwkFactory.jwkContainsErrorMessage(string, jwkContext);
            throw new InvalidKeyException(string2);
        }
        ECPublicKeySpec eCPublicKeySpec = new ECPublicKeySpec(eCPoint, eCCurve.toParameterSpec());
        ECPublicKey eCPublicKey = this.generateKey(jwkContext, new CheckedFunction<KeyFactory, ECPublicKey>(this, eCPublicKeySpec){
            final ECPublicKeySpec val$pubSpec;
            final EcPublicJwkFactory this$0;
            {
                this.this$0 = ecPublicJwkFactory;
                this.val$pubSpec = eCPublicKeySpec;
            }

            @Override
            public ECPublicKey apply(KeyFactory keyFactory) throws Exception {
                return (ECPublicKey)keyFactory.generatePublic(this.val$pubSpec);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyFactory)object);
            }
        });
        jwkContext.setKey(eCPublicKey);
        return new DefaultEcPublicJwk(jwkContext);
    }

    @Override
    protected Jwk createJwkFromValues(JwkContext jwkContext) {
        return this.createJwkFromValues(jwkContext);
    }

    @Override
    protected Jwk createJwkFromKey(JwkContext jwkContext) {
        return this.createJwkFromKey(jwkContext);
    }
}

