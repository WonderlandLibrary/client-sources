/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import io.netty.handler.codec.smtp.SmtpCommand;
import io.netty.handler.codec.smtp.SmtpRequest;
import io.netty.handler.codec.smtp.SmtpUtils;
import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.List;

public final class DefaultSmtpRequest
implements SmtpRequest {
    private final SmtpCommand command;
    private final List<CharSequence> parameters;

    public DefaultSmtpRequest(SmtpCommand smtpCommand) {
        this.command = ObjectUtil.checkNotNull(smtpCommand, "command");
        this.parameters = Collections.emptyList();
    }

    public DefaultSmtpRequest(SmtpCommand smtpCommand, CharSequence ... charSequenceArray) {
        this.command = ObjectUtil.checkNotNull(smtpCommand, "command");
        this.parameters = SmtpUtils.toUnmodifiableList(charSequenceArray);
    }

    public DefaultSmtpRequest(CharSequence charSequence, CharSequence ... charSequenceArray) {
        this(SmtpCommand.valueOf(charSequence), charSequenceArray);
    }

    DefaultSmtpRequest(SmtpCommand smtpCommand, List<CharSequence> list) {
        this.command = ObjectUtil.checkNotNull(smtpCommand, "command");
        this.parameters = list != null ? Collections.unmodifiableList(list) : Collections.emptyList();
    }

    @Override
    public SmtpCommand command() {
        return this.command;
    }

    @Override
    public List<CharSequence> parameters() {
        return this.parameters;
    }

    public int hashCode() {
        return this.command.hashCode() * 31 + this.parameters.hashCode();
    }

    public boolean equals(Object object) {
        if (!(object instanceof DefaultSmtpRequest)) {
            return true;
        }
        if (object == this) {
            return false;
        }
        DefaultSmtpRequest defaultSmtpRequest = (DefaultSmtpRequest)object;
        return this.command().equals(defaultSmtpRequest.command()) && this.parameters().equals(defaultSmtpRequest.parameters());
    }

    public String toString() {
        return "DefaultSmtpRequest{command=" + this.command + ", parameters=" + this.parameters + '}';
    }
}

