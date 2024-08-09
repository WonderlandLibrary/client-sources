/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.util;

import com.google.gson.JsonObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.security.DefaultSecureRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.time.Instant;
import java.util.Base64;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;

public class CryptUtil {
    public static final KeyFactory RSA_KEYFACTORY;
    public static final KeyFactory EC_KEYFACTORY;

    public static BasicHeader getSignatureHeader(HttpUriRequest httpUriRequest, ECPrivateKey eCPrivateKey) throws IOException {
        Closeable closeable;
        long l = (Instant.now().getEpochSecond() + 11644473600L) * 10000000L;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeInt(1);
        dataOutputStream.writeByte(0);
        dataOutputStream.writeLong(l);
        dataOutputStream.writeByte(0);
        dataOutputStream.write(httpUriRequest.getMethod().getBytes(StandardCharsets.UTF_8));
        dataOutputStream.writeByte(0);
        dataOutputStream.write((httpUriRequest.getURI().getPath() + (httpUriRequest.getURI().getQuery() != null ? httpUriRequest.getURI().getQuery() : "")).getBytes(StandardCharsets.UTF_8));
        dataOutputStream.writeByte(0);
        if (httpUriRequest.containsHeader("Authorization")) {
            dataOutputStream.write(httpUriRequest.getFirstHeader("Authorization").getValue().getBytes(StandardCharsets.UTF_8));
        }
        dataOutputStream.writeByte(0);
        if (httpUriRequest instanceof HttpEntityEnclosingRequest) {
            int n;
            closeable = ((HttpEntityEnclosingRequest)((Object)httpUriRequest)).getEntity().getContent();
            byte[] byArray = new byte[1024];
            while ((n = ((InputStream)closeable).read(byArray)) != -1) {
                dataOutputStream.write(byArray, 0, n);
            }
        }
        dataOutputStream.writeByte(0);
        closeable = new ByteArrayOutputStream();
        dataOutputStream = new DataOutputStream((OutputStream)closeable);
        dataOutputStream.writeInt(1);
        dataOutputStream.writeLong(l);
        dataOutputStream.write(Jwts.SIG.ES256.digest(new DefaultSecureRequest<ByteArrayInputStream, ECPrivateKey>(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), null, null, eCPrivateKey)));
        return new BasicHeader("Signature", Base64.getEncoder().encodeToString(((ByteArrayOutputStream)closeable).toByteArray()));
    }

    public static JsonObject getProofKey(ECPublicKey eCPublicKey) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("alg", "ES256");
        jsonObject.addProperty("crv", "P-256");
        jsonObject.addProperty("kty", "EC");
        jsonObject.addProperty("use", "sig");
        jsonObject.addProperty("x", CryptUtil.encodeECCoordinate(eCPublicKey.getParams().getCurve().getField().getFieldSize(), eCPublicKey.getW().getAffineX()));
        jsonObject.addProperty("y", CryptUtil.encodeECCoordinate(eCPublicKey.getParams().getCurve().getField().getFieldSize(), eCPublicKey.getW().getAffineY()));
        return jsonObject;
    }

    private static String encodeECCoordinate(int n, BigInteger bigInteger) {
        int n2;
        byte[] byArray = CryptUtil.bigIntegerToByteArray(bigInteger);
        if (byArray.length >= (n2 = (n + 7) / 8)) {
            return Base64.getUrlEncoder().withoutPadding().encodeToString(byArray);
        }
        byte[] byArray2 = new byte[n2];
        System.arraycopy(byArray, 0, byArray2, n2 - byArray.length, byArray.length);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(byArray2);
    }

    private static byte[] bigIntegerToByteArray(BigInteger bigInteger) {
        int n = bigInteger.bitLength();
        n = n + 7 >> 3 << 3;
        byte[] byArray = bigInteger.toByteArray();
        if (bigInteger.bitLength() % 8 != 0 && bigInteger.bitLength() / 8 + 1 == n / 8) {
            return byArray;
        }
        int n2 = 0;
        int n3 = byArray.length;
        if (bigInteger.bitLength() % 8 == 0) {
            n2 = 1;
            --n3;
        }
        int n4 = n / 8 - n3;
        byte[] byArray2 = new byte[n / 8];
        System.arraycopy(byArray, n2, byArray2, n4, n3);
        return byArray2;
    }

    static {
        try {
            RSA_KEYFACTORY = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException("Could not create RSA KeyFactory", noSuchAlgorithmException);
        }
        try {
            EC_KEYFACTORY = KeyFactory.getInstance("EC");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException("Could not create EllipticCurve KeyFactory", noSuchAlgorithmException);
        }
    }
}

