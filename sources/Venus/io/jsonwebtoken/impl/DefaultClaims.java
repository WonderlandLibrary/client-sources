/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.RequiredTypeException;
import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.impl.lang.JwtDateConverter;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Registry;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class DefaultClaims
extends ParameterMap
implements Claims {
    private static final String CONVERSION_ERROR_MSG = "Cannot convert existing claim value of type '%s' to desired type '%s'. JJWT only converts simple String, Date, Long, Integer, Short and Byte types automatically. Anything more complex is expected to be already converted to your desired type by the JSON Deserializer implementation. You may specify a custom Deserializer for a JwtParser with the desired conversion configuration via the JwtParserBuilder.deserializer() method. See https://github.com/jwtk/jjwt#custom-json-processor for more information. If using Jackson, you can specify custom claim POJO types as described in https://github.com/jwtk/jjwt#json-jackson-custom-types";
    static final Parameter<String> ISSUER = Parameters.string("iss", "Issuer");
    static final Parameter<String> SUBJECT = Parameters.string("sub", "Subject");
    static final Parameter<Set<String>> AUDIENCE = Parameters.stringSet("aud", "Audience");
    static final Parameter<Date> EXPIRATION = Parameters.rfcDate("exp", "Expiration Time");
    static final Parameter<Date> NOT_BEFORE = Parameters.rfcDate("nbf", "Not Before");
    static final Parameter<Date> ISSUED_AT = Parameters.rfcDate("iat", "Issued At");
    static final Parameter<String> JTI = Parameters.string("jti", "JWT ID");
    static final Registry<String, Parameter<?>> PARAMS = Parameters.registry(ISSUER, SUBJECT, AUDIENCE, EXPIRATION, NOT_BEFORE, ISSUED_AT, JTI);

    protected DefaultClaims() {
        super(PARAMS);
    }

    public DefaultClaims(ParameterMap parameterMap) {
        super(parameterMap.PARAMS, parameterMap);
    }

    public DefaultClaims(Map<String, ?> map) {
        super(PARAMS, map);
    }

    @Override
    public String getName() {
        return "JWT Claims";
    }

    @Override
    public String getIssuer() {
        return this.get(ISSUER);
    }

    @Override
    public String getSubject() {
        return this.get(SUBJECT);
    }

    @Override
    public Set<String> getAudience() {
        return this.get(AUDIENCE);
    }

    @Override
    public Date getExpiration() {
        return this.get(EXPIRATION);
    }

    @Override
    public Date getNotBefore() {
        return this.get(NOT_BEFORE);
    }

    @Override
    public Date getIssuedAt() {
        return this.get(ISSUED_AT);
    }

    @Override
    public String getId() {
        return this.get(JTI);
    }

    @Override
    public <T> T get(String string, Class<T> clazz) {
        Assert.notNull(clazz, "requiredType argument cannot be null.");
        Object object = this.idiomaticValues.get(string);
        if (clazz.isInstance(object)) {
            return clazz.cast(object);
        }
        object = this.get(string);
        if (object == null) {
            return null;
        }
        if (Date.class.equals(clazz)) {
            try {
                object = JwtDateConverter.toDate(object);
            } catch (Exception exception) {
                String string2 = "Cannot create Date from '" + string + "' value '" + object + "'. Cause: " + exception.getMessage();
                throw new IllegalArgumentException(string2, exception);
            }
        }
        return this.castClaimValue(string, object, clazz);
    }

    private <T> T castClaimValue(String string, Object object, Class<T> clazz) {
        if (object instanceof Long || object instanceof Integer || object instanceof Short || object instanceof Byte) {
            long l = ((Number)object).longValue();
            if (Long.class.equals(clazz)) {
                object = l;
            } else if (Integer.class.equals(clazz) && Integer.MIN_VALUE <= l && l <= Integer.MAX_VALUE) {
                object = (int)l;
            } else if (clazz == Short.class && -32768L <= l && l <= 32767L) {
                object = (short)l;
            } else if (clazz == Byte.class && -128L <= l && l <= 127L) {
                object = (byte)l;
            }
        }
        if (object instanceof Long && (clazz.equals(Integer.class) || clazz.equals(Short.class) || clazz.equals(Byte.class))) {
            String string2 = "Claim '" + string + "' value is too large or too small to be represented as a " + clazz.getName() + " instance (would cause numeric overflow).";
            throw new RequiredTypeException(string2);
        }
        if (!clazz.isInstance(object)) {
            throw new RequiredTypeException(String.format(CONVERSION_ERROR_MSG, object.getClass(), clazz));
        }
        return clazz.cast(object);
    }
}

