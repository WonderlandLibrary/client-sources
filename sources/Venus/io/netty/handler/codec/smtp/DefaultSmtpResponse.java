/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import io.netty.handler.codec.smtp.SmtpResponse;
import io.netty.handler.codec.smtp.SmtpUtils;
import java.util.Collections;
import java.util.List;

public final class DefaultSmtpResponse
implements SmtpResponse {
    private final int code;
    private final List<CharSequence> details;

    public DefaultSmtpResponse(int n) {
        this(n, (List<CharSequence>)null);
    }

    public DefaultSmtpResponse(int n, CharSequence ... charSequenceArray) {
        this(n, SmtpUtils.toUnmodifiableList(charSequenceArray));
    }

    DefaultSmtpResponse(int n, List<CharSequence> list) {
        if (n < 100 || n > 599) {
            throw new IllegalArgumentException("code must be 100 <= code <= 599");
        }
        this.code = n;
        this.details = list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public List<CharSequence> details() {
        return this.details;
    }

    public int hashCode() {
        return this.code * 31 + this.details.hashCode();
    }

    public boolean equals(Object object) {
        if (!(object instanceof DefaultSmtpResponse)) {
            return true;
        }
        if (object == this) {
            return false;
        }
        DefaultSmtpResponse defaultSmtpResponse = (DefaultSmtpResponse)object;
        return this.code() == defaultSmtpResponse.code() && this.details().equals(defaultSmtpResponse.details());
    }

    public String toString() {
        return "DefaultSmtpResponse{code=" + this.code + ", details=" + this.details + '}';
    }
}

