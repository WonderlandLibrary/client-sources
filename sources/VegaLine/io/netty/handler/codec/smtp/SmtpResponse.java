/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import java.util.List;

public interface SmtpResponse {
    public int code();

    public List<CharSequence> details();
}

