/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultProtectedHeader;
import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.impl.lang.Converters;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.lang.PositiveIntegerConverter;
import io.jsonwebtoken.impl.lang.RequiredBitLengthConverter;
import io.jsonwebtoken.impl.security.JwkConverter;
import io.jsonwebtoken.lang.Registry;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.PublicJwk;
import java.util.Map;

public class DefaultJweHeader
extends DefaultProtectedHeader
implements JweHeader {
    static final Parameter<String> ENCRYPTION_ALGORITHM = Parameters.string("enc", "Encryption Algorithm");
    public static final Parameter<PublicJwk<?>> EPK = (Parameter)Parameters.builder(JwkConverter.PUBLIC_JWK_CLASS).setId("epk").setName("Ephemeral Public Key").setConverter(JwkConverter.PUBLIC_JWK).build();
    static final Parameter<byte[]> APU = (Parameter)Parameters.bytes("apu", "Agreement PartyUInfo").build();
    static final Parameter<byte[]> APV = (Parameter)Parameters.bytes("apv", "Agreement PartyVInfo").build();
    public static final Parameter<byte[]> IV = (Parameter)Parameters.bytes("iv", "Initialization Vector").setConverter(new RequiredBitLengthConverter(Converters.BASE64URL_BYTES, 96)).build();
    public static final Parameter<byte[]> TAG = (Parameter)Parameters.bytes("tag", "Authentication Tag").setConverter(new RequiredBitLengthConverter(Converters.BASE64URL_BYTES, 128)).build();
    public static final Parameter<byte[]> P2S = (Parameter)Parameters.bytes("p2s", "PBES2 Salt Input").setConverter(new RequiredBitLengthConverter(Converters.BASE64URL_BYTES, 64, false)).build();
    public static final Parameter<Integer> P2C = (Parameter)Parameters.builder(Integer.class).setConverter(PositiveIntegerConverter.INSTANCE).setId("p2c").setName("PBES2 Count").build();
    static final Registry<String, Parameter<?>> PARAMS = Parameters.registry(DefaultProtectedHeader.PARAMS, ENCRYPTION_ALGORITHM, EPK, APU, APV, IV, TAG, P2S, P2C);

    static boolean isCandidate(ParameterMap parameterMap) {
        String string = parameterMap.get(DefaultHeader.ALGORITHM);
        return Strings.hasText(string) && !string.equalsIgnoreCase(Jwts.SIG.NONE.getId()) && Strings.hasText(parameterMap.get(ENCRYPTION_ALGORITHM));
    }

    public DefaultJweHeader(Map<String, ?> map) {
        super(PARAMS, map);
    }

    @Override
    public String getName() {
        return "JWE header";
    }

    @Override
    public String getEncryptionAlgorithm() {
        return this.get(ENCRYPTION_ALGORITHM);
    }

    @Override
    public PublicJwk<?> getEphemeralPublicKey() {
        return this.get(EPK);
    }

    @Override
    public byte[] getAgreementPartyUInfo() {
        return this.get(APU);
    }

    @Override
    public byte[] getAgreementPartyVInfo() {
        return this.get(APV);
    }

    @Override
    public byte[] getInitializationVector() {
        return this.get(IV);
    }

    @Override
    public byte[] getAuthenticationTag() {
        return this.get(TAG);
    }

    @Override
    public byte[] getPbes2Salt() {
        return this.get(P2S);
    }

    @Override
    public Integer getPbes2Count() {
        return this.get(P2C);
    }
}

