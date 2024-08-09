/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import io.netty.handler.codec.smtp.DefaultSmtpRequest;
import io.netty.handler.codec.smtp.SmtpCommand;
import io.netty.handler.codec.smtp.SmtpRequest;
import io.netty.util.AsciiString;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayList;

public final class SmtpRequests {
    private static final SmtpRequest DATA = new DefaultSmtpRequest(SmtpCommand.DATA);
    private static final SmtpRequest NOOP = new DefaultSmtpRequest(SmtpCommand.NOOP);
    private static final SmtpRequest RSET = new DefaultSmtpRequest(SmtpCommand.RSET);
    private static final SmtpRequest HELP_NO_ARG = new DefaultSmtpRequest(SmtpCommand.HELP);
    private static final SmtpRequest QUIT = new DefaultSmtpRequest(SmtpCommand.QUIT);
    private static final AsciiString FROM_NULL_SENDER = AsciiString.cached("FROM:<>");

    public static SmtpRequest helo(CharSequence charSequence) {
        return new DefaultSmtpRequest(SmtpCommand.HELO, charSequence);
    }

    public static SmtpRequest ehlo(CharSequence charSequence) {
        return new DefaultSmtpRequest(SmtpCommand.EHLO, charSequence);
    }

    public static SmtpRequest noop() {
        return NOOP;
    }

    public static SmtpRequest data() {
        return DATA;
    }

    public static SmtpRequest rset() {
        return RSET;
    }

    public static SmtpRequest help(String string) {
        return string == null ? HELP_NO_ARG : new DefaultSmtpRequest(SmtpCommand.HELP, string);
    }

    public static SmtpRequest quit() {
        return QUIT;
    }

    public static SmtpRequest mail(CharSequence charSequence, CharSequence ... charSequenceArray) {
        if (charSequenceArray == null || charSequenceArray.length == 0) {
            return new DefaultSmtpRequest(SmtpCommand.MAIL, charSequence != null ? "FROM:<" + charSequence + '>' : FROM_NULL_SENDER);
        }
        ArrayList<CharSequence> arrayList = new ArrayList<CharSequence>(charSequenceArray.length + 1);
        arrayList.add(charSequence != null ? "FROM:<" + charSequence + '>' : FROM_NULL_SENDER);
        for (CharSequence charSequence2 : charSequenceArray) {
            arrayList.add(charSequence2);
        }
        return new DefaultSmtpRequest(SmtpCommand.MAIL, arrayList);
    }

    public static SmtpRequest rcpt(CharSequence charSequence, CharSequence ... charSequenceArray) {
        ObjectUtil.checkNotNull(charSequence, "recipient");
        if (charSequenceArray == null || charSequenceArray.length == 0) {
            return new DefaultSmtpRequest(SmtpCommand.RCPT, "TO:<" + charSequence + '>');
        }
        ArrayList<CharSequence> arrayList = new ArrayList<CharSequence>(charSequenceArray.length + 1);
        arrayList.add("TO:<" + charSequence + '>');
        for (CharSequence charSequence2 : charSequenceArray) {
            arrayList.add(charSequence2);
        }
        return new DefaultSmtpRequest(SmtpCommand.RCPT, arrayList);
    }

    public static SmtpRequest expn(CharSequence charSequence) {
        return new DefaultSmtpRequest(SmtpCommand.EXPN, ObjectUtil.checkNotNull(charSequence, "mailingList"));
    }

    public static SmtpRequest vrfy(CharSequence charSequence) {
        return new DefaultSmtpRequest(SmtpCommand.VRFY, ObjectUtil.checkNotNull(charSequence, "user"));
    }

    private SmtpRequests() {
    }
}

