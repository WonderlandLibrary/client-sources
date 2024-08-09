/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  javax.mail.Address
 *  javax.mail.Message$RecipientType
 *  javax.mail.MessagingException
 *  javax.mail.Session
 *  javax.mail.internet.AddressException
 *  javax.mail.internet.InternetAddress
 *  javax.mail.internet.MimeMessage
 */
package org.apache.logging.log4j.core.net;

import java.nio.charset.StandardCharsets;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.core.util.Builder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MimeMessageBuilder
implements Builder<MimeMessage> {
    private final MimeMessage message;

    public MimeMessageBuilder(Session session) {
        this.message = new MimeMessage(session);
    }

    public MimeMessageBuilder setFrom(String string) throws MessagingException {
        InternetAddress internetAddress = MimeMessageBuilder.parseAddress(string);
        if (null != internetAddress) {
            this.message.setFrom((Address)internetAddress);
        } else {
            try {
                this.message.setFrom();
            } catch (Exception exception) {
                this.message.setFrom((Address)((InternetAddress)null));
            }
        }
        return this;
    }

    public MimeMessageBuilder setReplyTo(String string) throws MessagingException {
        InternetAddress[] internetAddressArray = MimeMessageBuilder.parseAddresses(string);
        if (null != internetAddressArray) {
            this.message.setReplyTo((Address[])internetAddressArray);
        }
        return this;
    }

    public MimeMessageBuilder setRecipients(Message.RecipientType recipientType, String string) throws MessagingException {
        InternetAddress[] internetAddressArray = MimeMessageBuilder.parseAddresses(string);
        if (null != internetAddressArray) {
            this.message.setRecipients(recipientType, (Address[])internetAddressArray);
        }
        return this;
    }

    public MimeMessageBuilder setSubject(String string) throws MessagingException {
        if (string != null) {
            this.message.setSubject(string, StandardCharsets.UTF_8.name());
        }
        return this;
    }

    @Deprecated
    public MimeMessage getMimeMessage() {
        return this.build();
    }

    @Override
    public MimeMessage build() {
        return this.message;
    }

    private static InternetAddress parseAddress(String string) throws AddressException {
        return string == null ? null : new InternetAddress(string);
    }

    private static InternetAddress[] parseAddresses(String string) throws AddressException {
        return string == null ? null : InternetAddress.parse((String)string, (boolean)true);
    }

    @Override
    public Object build() {
        return this.build();
    }
}

