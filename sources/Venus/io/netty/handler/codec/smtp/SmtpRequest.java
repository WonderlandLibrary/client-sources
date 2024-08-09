/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import io.netty.handler.codec.smtp.SmtpCommand;
import java.util.List;

public interface SmtpRequest {
    public SmtpCommand command();

    public List<CharSequence> parameters();
}

