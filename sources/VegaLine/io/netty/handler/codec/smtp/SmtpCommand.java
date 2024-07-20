/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;
import io.netty.util.internal.ObjectUtil;
import java.util.HashMap;
import java.util.Map;

public final class SmtpCommand {
    public static final SmtpCommand EHLO = new SmtpCommand(new AsciiString("EHLO"), false);
    public static final SmtpCommand HELO = new SmtpCommand(new AsciiString("HELO"), false);
    public static final SmtpCommand MAIL = new SmtpCommand(new AsciiString("MAIL"), false);
    public static final SmtpCommand RCPT = new SmtpCommand(new AsciiString("RCPT"), false);
    public static final SmtpCommand DATA = new SmtpCommand(new AsciiString("DATA"), true);
    public static final SmtpCommand NOOP = new SmtpCommand(new AsciiString("NOOP"), false);
    public static final SmtpCommand RSET = new SmtpCommand(new AsciiString("RSET"), false);
    public static final SmtpCommand EXPN = new SmtpCommand(new AsciiString("EXPN"), false);
    public static final SmtpCommand VRFY = new SmtpCommand(new AsciiString("VRFY"), false);
    public static final SmtpCommand HELP = new SmtpCommand(new AsciiString("HELP"), false);
    public static final SmtpCommand QUIT = new SmtpCommand(new AsciiString("QUIT"), false);
    private static final CharSequence DATA_CMD = new AsciiString("DATA");
    private static final Map<CharSequence, SmtpCommand> COMMANDS = new HashMap<CharSequence, SmtpCommand>();
    private final AsciiString name;
    private final boolean contentExpected;
    private int hashCode;

    public static SmtpCommand valueOf(CharSequence commandName) {
        SmtpCommand command = COMMANDS.get(commandName);
        if (command != null) {
            return command;
        }
        return new SmtpCommand(AsciiString.of(ObjectUtil.checkNotNull(commandName, "commandName")), AsciiString.contentEqualsIgnoreCase(commandName, DATA_CMD));
    }

    private SmtpCommand(AsciiString name, boolean contentExpected) {
        this.name = name;
        this.contentExpected = contentExpected;
    }

    public AsciiString name() {
        return this.name;
    }

    void encode(ByteBuf buffer) {
        ByteBufUtil.writeAscii(buffer, (CharSequence)this.name());
    }

    boolean isContentExpected() {
        return this.contentExpected;
    }

    public int hashCode() {
        if (this.hashCode != -1) {
            this.hashCode = AsciiString.hashCode(this.name);
        }
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SmtpCommand)) {
            return false;
        }
        return this.name.contentEqualsIgnoreCase(((SmtpCommand)obj).name());
    }

    public String toString() {
        return "SmtpCommand{name=" + this.name + ", contentExpected=" + this.contentExpected + ", hashCode=" + this.hashCode + '}';
    }

    static {
        COMMANDS.put(EHLO.name(), EHLO);
        COMMANDS.put(HELO.name(), HELO);
        COMMANDS.put(MAIL.name(), MAIL);
        COMMANDS.put(RCPT.name(), RCPT);
        COMMANDS.put(DATA.name(), DATA);
        COMMANDS.put(NOOP.name(), NOOP);
        COMMANDS.put(RSET.name(), RSET);
        COMMANDS.put(EXPN.name(), EXPN);
        COMMANDS.put(VRFY.name(), VRFY);
        COMMANDS.put(HELP.name(), HELP);
        COMMANDS.put(QUIT.name(), QUIT);
    }
}

