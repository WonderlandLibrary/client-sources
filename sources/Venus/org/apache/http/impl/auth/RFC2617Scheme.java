/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.auth;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HttpRequest;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.impl.auth.AuthSchemeBase;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.CharsetUtils;

public abstract class RFC2617Scheme
extends AuthSchemeBase
implements Serializable {
    private static final long serialVersionUID = -2845454858205884623L;
    private final Map<String, String> params = new HashMap<String, String>();
    private transient Charset credentialsCharset;

    @Deprecated
    public RFC2617Scheme(ChallengeState challengeState) {
        super(challengeState);
        this.credentialsCharset = Consts.ASCII;
    }

    public RFC2617Scheme(Charset charset) {
        this.credentialsCharset = charset != null ? charset : Consts.ASCII;
    }

    public RFC2617Scheme() {
        this(Consts.ASCII);
    }

    public Charset getCredentialsCharset() {
        return this.credentialsCharset != null ? this.credentialsCharset : Consts.ASCII;
    }

    String getCredentialsCharset(HttpRequest httpRequest) {
        String string = (String)httpRequest.getParams().getParameter("http.auth.credential-charset");
        if (string == null) {
            string = this.getCredentialsCharset().name();
        }
        return string;
    }

    @Override
    protected void parseChallenge(CharArrayBuffer charArrayBuffer, int n, int n2) throws MalformedChallengeException {
        BasicHeaderValueParser basicHeaderValueParser = BasicHeaderValueParser.INSTANCE;
        ParserCursor parserCursor = new ParserCursor(n, charArrayBuffer.length());
        HeaderElement[] headerElementArray = basicHeaderValueParser.parseElements(charArrayBuffer, parserCursor);
        this.params.clear();
        for (HeaderElement headerElement : headerElementArray) {
            this.params.put(headerElement.getName().toLowerCase(Locale.ROOT), headerElement.getValue());
        }
    }

    protected Map<String, String> getParameters() {
        return this.params;
    }

    @Override
    public String getParameter(String string) {
        if (string == null) {
            return null;
        }
        return this.params.get(string.toLowerCase(Locale.ROOT));
    }

    @Override
    public String getRealm() {
        return this.getParameter("realm");
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeUTF(this.credentialsCharset.name());
        objectOutputStream.writeObject((Object)this.challengeState);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.credentialsCharset = CharsetUtils.get(objectInputStream.readUTF());
        if (this.credentialsCharset == null) {
            this.credentialsCharset = Consts.ASCII;
        }
        this.challengeState = (ChallengeState)((Object)objectInputStream.readObject());
    }

    private void readObjectNoData() throws ObjectStreamException {
    }
}

