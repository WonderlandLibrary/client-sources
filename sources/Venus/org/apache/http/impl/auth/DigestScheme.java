/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.auth;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.impl.auth.HttpEntityDigester;
import org.apache.http.impl.auth.RFC2617Scheme;
import org.apache.http.impl.auth.UnsupportedDigestAlgorithmException;
import org.apache.http.message.BasicHeaderValueFormatter;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BufferedHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EncodingUtils;

public class DigestScheme
extends RFC2617Scheme {
    private static final long serialVersionUID = 3883908186234566916L;
    private static final char[] HEXADECIMAL = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private boolean complete;
    private static final int QOP_UNKNOWN = -1;
    private static final int QOP_MISSING = 0;
    private static final int QOP_AUTH_INT = 1;
    private static final int QOP_AUTH = 2;
    private String lastNonce;
    private long nounceCount;
    private String cnonce;
    private String a1;
    private String a2;

    public DigestScheme(Charset charset) {
        super(charset);
        this.complete = false;
    }

    @Deprecated
    public DigestScheme(ChallengeState challengeState) {
        super(challengeState);
    }

    public DigestScheme() {
        this(Consts.ASCII);
    }

    @Override
    public void processChallenge(Header header) throws MalformedChallengeException {
        super.processChallenge(header);
        this.complete = true;
        if (this.getParameters().isEmpty()) {
            throw new MalformedChallengeException("Authentication challenge is empty");
        }
    }

    @Override
    public boolean isComplete() {
        String string = this.getParameter("stale");
        return "true".equalsIgnoreCase(string) ? false : this.complete;
    }

    @Override
    public String getSchemeName() {
        return "digest";
    }

    @Override
    public boolean isConnectionBased() {
        return true;
    }

    public void overrideParamter(String string, String string2) {
        this.getParameters().put(string, string2);
    }

    @Override
    @Deprecated
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        return this.authenticate(credentials, httpRequest, new BasicHttpContext());
    }

    @Override
    public Header authenticate(Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) throws AuthenticationException {
        Args.notNull(credentials, "Credentials");
        Args.notNull(httpRequest, "HTTP request");
        if (this.getParameter("realm") == null) {
            throw new AuthenticationException("missing realm in challenge");
        }
        if (this.getParameter("nonce") == null) {
            throw new AuthenticationException("missing nonce in challenge");
        }
        this.getParameters().put("methodname", httpRequest.getRequestLine().getMethod());
        this.getParameters().put("uri", httpRequest.getRequestLine().getUri());
        String string = this.getParameter("charset");
        if (string == null) {
            this.getParameters().put("charset", this.getCredentialsCharset(httpRequest));
        }
        return this.createDigestHeader(credentials, httpRequest);
    }

    private static MessageDigest createMessageDigest(String string) throws UnsupportedDigestAlgorithmException {
        try {
            return MessageDigest.getInstance(string);
        } catch (Exception exception) {
            throw new UnsupportedDigestAlgorithmException("Unsupported algorithm in HTTP Digest authentication: " + string);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Header createDigestHeader(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        Object object;
        Object object2;
        String string;
        MessageDigest messageDigest;
        String string2;
        Object object3;
        String string3 = this.getParameter("uri");
        String string4 = this.getParameter("realm");
        String string5 = this.getParameter("nonce");
        String string6 = this.getParameter("opaque");
        String string7 = this.getParameter("methodname");
        String string8 = this.getParameter("algorithm");
        if (string8 == null) {
            string8 = "MD5";
        }
        HashSet<String> hashSet = new HashSet<String>(8);
        int n = -1;
        String string9 = this.getParameter("qop");
        if (string9 != null) {
            object3 = new StringTokenizer(string9, ",");
            while (((StringTokenizer)object3).hasMoreTokens()) {
                string2 = ((StringTokenizer)object3).nextToken().trim();
                hashSet.add(string2.toLowerCase(Locale.ROOT));
            }
            if (httpRequest instanceof HttpEntityEnclosingRequest && hashSet.contains("auth-int")) {
                n = 1;
            } else if (hashSet.contains("auth")) {
                n = 2;
            }
        } else {
            n = 0;
        }
        if (n == -1) {
            throw new AuthenticationException("None of the qop methods is supported: " + string9);
        }
        object3 = this.getParameter("charset");
        if (object3 == null) {
            object3 = "ISO-8859-1";
        }
        if ((string2 = string8).equalsIgnoreCase("MD5-sess")) {
            string2 = "MD5";
        }
        try {
            messageDigest = DigestScheme.createMessageDigest(string2);
        } catch (UnsupportedDigestAlgorithmException unsupportedDigestAlgorithmException) {
            throw new AuthenticationException("Unsuppported digest algorithm: " + string2);
        }
        String string10 = credentials.getUserPrincipal().getName();
        String string11 = credentials.getPassword();
        if (string5.equals(this.lastNonce)) {
            ++this.nounceCount;
        } else {
            this.nounceCount = 1L;
            this.cnonce = null;
            this.lastNonce = string5;
        }
        StringBuilder stringBuilder = new StringBuilder(256);
        Formatter formatter = new Formatter(stringBuilder, Locale.US);
        formatter.format("%08x", this.nounceCount);
        formatter.close();
        String string12 = stringBuilder.toString();
        if (this.cnonce == null) {
            this.cnonce = DigestScheme.createCnonce();
        }
        this.a1 = null;
        this.a2 = null;
        if (string8.equalsIgnoreCase("MD5-sess")) {
            stringBuilder.setLength(0);
            stringBuilder.append(string10).append(':').append(string4).append(':').append(string11);
            string = DigestScheme.encode(messageDigest.digest(EncodingUtils.getBytes(stringBuilder.toString(), (String)object3)));
            stringBuilder.setLength(0);
            stringBuilder.append(string).append(':').append(string5).append(':').append(this.cnonce);
            this.a1 = stringBuilder.toString();
        } else {
            stringBuilder.setLength(0);
            stringBuilder.append(string10).append(':').append(string4).append(':').append(string11);
            this.a1 = stringBuilder.toString();
        }
        string = DigestScheme.encode(messageDigest.digest(EncodingUtils.getBytes(this.a1, (String)object3)));
        if (n == 2) {
            this.a2 = string7 + ':' + string3;
        } else if (n == 1) {
            object2 = null;
            if (httpRequest instanceof HttpEntityEnclosingRequest) {
                object2 = ((HttpEntityEnclosingRequest)httpRequest).getEntity();
            }
            if (object2 != null && !object2.isRepeatable()) {
                if (!hashSet.contains("auth")) throw new AuthenticationException("Qop auth-int cannot be used with a non-repeatable entity");
                n = 2;
                this.a2 = string7 + ':' + string3;
            } else {
                object = new HttpEntityDigester(messageDigest);
                try {
                    if (object2 != null) {
                        object2.writeTo((OutputStream)object);
                    }
                    ((HttpEntityDigester)object).close();
                } catch (IOException iOException) {
                    throw new AuthenticationException("I/O error reading entity content", iOException);
                }
                this.a2 = string7 + ':' + string3 + ':' + DigestScheme.encode(((HttpEntityDigester)object).getDigest());
            }
        } else {
            this.a2 = string7 + ':' + string3;
        }
        object2 = DigestScheme.encode(messageDigest.digest(EncodingUtils.getBytes(this.a2, (String)object3)));
        if (n == 0) {
            stringBuilder.setLength(0);
            stringBuilder.append(string).append(':').append(string5).append(':').append((String)object2);
            object = stringBuilder.toString();
        } else {
            stringBuilder.setLength(0);
            stringBuilder.append(string).append(':').append(string5).append(':').append(string12).append(':').append(this.cnonce).append(':').append(n == 1 ? "auth-int" : "auth").append(':').append((String)object2);
            object = stringBuilder.toString();
        }
        String string13 = DigestScheme.encode(messageDigest.digest(EncodingUtils.getAsciiBytes((String)object)));
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(128);
        if (this.isProxy()) {
            charArrayBuffer.append("Proxy-Authorization");
        } else {
            charArrayBuffer.append("Authorization");
        }
        charArrayBuffer.append(": Digest ");
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>(20);
        arrayList.add(new BasicNameValuePair("username", string10));
        arrayList.add(new BasicNameValuePair("realm", string4));
        arrayList.add(new BasicNameValuePair("nonce", string5));
        arrayList.add(new BasicNameValuePair("uri", string3));
        arrayList.add(new BasicNameValuePair("response", string13));
        if (n != 0) {
            arrayList.add(new BasicNameValuePair("qop", n == 1 ? "auth-int" : "auth"));
            arrayList.add(new BasicNameValuePair("nc", string12));
            arrayList.add(new BasicNameValuePair("cnonce", this.cnonce));
        }
        arrayList.add(new BasicNameValuePair("algorithm", string8));
        if (string6 != null) {
            arrayList.add(new BasicNameValuePair("opaque", string6));
        }
        for (int i = 0; i < arrayList.size(); ++i) {
            String string14;
            BasicNameValuePair basicNameValuePair = (BasicNameValuePair)arrayList.get(i);
            if (i > 0) {
                charArrayBuffer.append(", ");
            }
            boolean bl = "nc".equals(string14 = basicNameValuePair.getName()) || "qop".equals(string14) || "algorithm".equals(string14);
            BasicHeaderValueFormatter.INSTANCE.formatNameValuePair(charArrayBuffer, basicNameValuePair, !bl);
        }
        return new BufferedHeader(charArrayBuffer);
    }

    String getCnonce() {
        return this.cnonce;
    }

    String getA1() {
        return this.a1;
    }

    String getA2() {
        return this.a2;
    }

    static String encode(byte[] byArray) {
        int n = byArray.length;
        char[] cArray = new char[n * 2];
        for (int i = 0; i < n; ++i) {
            int n2 = byArray[i] & 0xF;
            int n3 = (byArray[i] & 0xF0) >> 4;
            cArray[i * 2] = HEXADECIMAL[n3];
            cArray[i * 2 + 1] = HEXADECIMAL[n2];
        }
        return new String(cArray);
    }

    public static String createCnonce() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] byArray = new byte[8];
        secureRandom.nextBytes(byArray);
        return DigestScheme.encode(byArray);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DIGEST [complete=").append(this.complete).append(", nonce=").append(this.lastNonce).append(", nc=").append(this.nounceCount).append("]");
        return stringBuilder.toString();
    }
}

