/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  javax.mail.Authenticator
 *  javax.mail.BodyPart
 *  javax.mail.Message
 *  javax.mail.Message$RecipientType
 *  javax.mail.MessagingException
 *  javax.mail.Multipart
 *  javax.mail.PasswordAuthentication
 *  javax.mail.Session
 *  javax.mail.Transport
 *  javax.mail.internet.InternetHeaders
 *  javax.mail.internet.MimeBodyPart
 *  javax.mail.internet.MimeMessage
 *  javax.mail.internet.MimeMultipart
 *  javax.mail.internet.MimeUtility
 *  javax.mail.util.ByteArrayDataSource
 */
package org.apache.logging.log4j.core.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import org.apache.logging.log4j.LoggingException;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.impl.MutableLogEvent;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.net.MimeMessageBuilder;
import org.apache.logging.log4j.core.util.CyclicBuffer;
import org.apache.logging.log4j.core.util.NameUtil;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.Strings;

public class SmtpManager
extends AbstractManager {
    private static final SMTPManagerFactory FACTORY = new SMTPManagerFactory(null);
    private final Session session;
    private final CyclicBuffer<LogEvent> buffer;
    private volatile MimeMessage message;
    private final FactoryData data;

    private static MimeMessage createMimeMessage(FactoryData factoryData, Session session, LogEvent logEvent) throws MessagingException {
        return new MimeMessageBuilder(session).setFrom(FactoryData.access$600(factoryData)).setReplyTo(FactoryData.access$500(factoryData)).setRecipients(Message.RecipientType.TO, FactoryData.access$400(factoryData)).setRecipients(Message.RecipientType.CC, FactoryData.access$300(factoryData)).setRecipients(Message.RecipientType.BCC, FactoryData.access$200(factoryData)).setSubject(FactoryData.access$100(factoryData).toSerializable(logEvent)).build();
    }

    protected SmtpManager(String string, Session session, MimeMessage mimeMessage, FactoryData factoryData) {
        super(null, string);
        this.session = session;
        this.message = mimeMessage;
        this.data = factoryData;
        this.buffer = new CyclicBuffer<LogEvent>(LogEvent.class, FactoryData.access$700(factoryData));
    }

    public void add(LogEvent logEvent) {
        if (logEvent instanceof Log4jLogEvent && logEvent.getMessage() instanceof ReusableMessage) {
            ((Log4jLogEvent)logEvent).makeMessageImmutable();
        } else if (logEvent instanceof MutableLogEvent) {
            logEvent = ((MutableLogEvent)logEvent).createMemento();
        }
        this.buffer.add(logEvent);
    }

    public static SmtpManager getSmtpManager(Configuration configuration, String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, int n, String string9, String string10, boolean bl, String string11, int n2) {
        if (Strings.isEmpty(string7)) {
            string7 = "smtp";
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (string != null) {
            stringBuilder.append(string);
        }
        stringBuilder.append(':');
        if (string2 != null) {
            stringBuilder.append(string2);
        }
        stringBuilder.append(':');
        if (string3 != null) {
            stringBuilder.append(string3);
        }
        stringBuilder.append(':');
        if (string4 != null) {
            stringBuilder.append(string4);
        }
        stringBuilder.append(':');
        if (string5 != null) {
            stringBuilder.append(string5);
        }
        stringBuilder.append(':');
        if (string6 != null) {
            stringBuilder.append(string6);
        }
        stringBuilder.append(':');
        stringBuilder.append(string7).append(':').append(string8).append(':').append("port").append(':');
        if (string9 != null) {
            stringBuilder.append(string9);
        }
        stringBuilder.append(':');
        if (string10 != null) {
            stringBuilder.append(string10);
        }
        stringBuilder.append(bl ? ":debug:" : "::");
        stringBuilder.append(string11);
        String string12 = "SMTP:" + NameUtil.md5(stringBuilder.toString());
        AbstractStringLayout.Serializer serializer = PatternLayout.newSerializerBuilder().setConfiguration(configuration).setPattern(string6).build();
        return SmtpManager.getManager(string12, FACTORY, new FactoryData(string, string2, string3, string4, string5, serializer, string7, string8, n, string9, string10, bl, n2));
    }

    public void sendEvents(Layout<?> layout, LogEvent logEvent) {
        if (this.message == null) {
            this.connect(logEvent);
        }
        try {
            LogEvent[] logEventArray = this.buffer.removeAll();
            byte[] byArray = this.formatContentToBytes(logEventArray, logEvent, layout);
            String string = layout.getContentType();
            String string2 = this.getEncoding(byArray, string);
            byte[] byArray2 = this.encodeContentToBytes(byArray, string2);
            InternetHeaders internetHeaders = this.getHeaders(string, string2);
            MimeMultipart mimeMultipart = this.getMimeMultipart(byArray2, internetHeaders);
            this.sendMultipartMessage(this.message, mimeMultipart);
        } catch (IOException | RuntimeException | MessagingException throwable) {
            this.logError("Caught exception while sending e-mail notification.", throwable);
            throw new LoggingException("Error occurred while sending email", throwable);
        }
    }

    protected byte[] formatContentToBytes(LogEvent[] logEventArray, LogEvent logEvent, Layout<?> layout) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.writeContent(logEventArray, logEvent, layout, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void writeContent(LogEvent[] logEventArray, LogEvent logEvent, Layout<?> layout, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        this.writeHeader(layout, byteArrayOutputStream);
        this.writeBuffer(logEventArray, logEvent, layout, byteArrayOutputStream);
        this.writeFooter(layout, byteArrayOutputStream);
    }

    protected void writeHeader(Layout<?> layout, OutputStream outputStream) throws IOException {
        byte[] byArray = layout.getHeader();
        if (byArray != null) {
            outputStream.write(byArray);
        }
    }

    protected void writeBuffer(LogEvent[] logEventArray, LogEvent logEvent, Layout<?> layout, OutputStream outputStream) throws IOException {
        for (LogEvent logEvent2 : logEventArray) {
            byte[] byArray = layout.toByteArray(logEvent2);
            outputStream.write(byArray);
        }
        byte[] byArray = layout.toByteArray(logEvent);
        outputStream.write(byArray);
    }

    protected void writeFooter(Layout<?> layout, OutputStream outputStream) throws IOException {
        byte[] byArray = layout.getFooter();
        if (byArray != null) {
            outputStream.write(byArray);
        }
    }

    protected String getEncoding(byte[] byArray, String string) {
        ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(byArray, string);
        return MimeUtility.getEncoding((DataSource)byteArrayDataSource);
    }

    protected byte[] encodeContentToBytes(byte[] byArray, String string) throws MessagingException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.encodeContent(byArray, string, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    protected void encodeContent(byte[] byArray, String string, ByteArrayOutputStream byteArrayOutputStream) throws MessagingException, IOException {
        try (OutputStream outputStream = MimeUtility.encode((OutputStream)byteArrayOutputStream, (String)string);){
            outputStream.write(byArray);
        }
    }

    protected InternetHeaders getHeaders(String string, String string2) {
        InternetHeaders internetHeaders = new InternetHeaders();
        internetHeaders.setHeader("Content-Type", string + "; charset=UTF-8");
        internetHeaders.setHeader("Content-Transfer-Encoding", string2);
        return internetHeaders;
    }

    protected MimeMultipart getMimeMultipart(byte[] byArray, InternetHeaders internetHeaders) throws MessagingException {
        MimeMultipart mimeMultipart = new MimeMultipart();
        MimeBodyPart mimeBodyPart = new MimeBodyPart(internetHeaders, byArray);
        mimeMultipart.addBodyPart((BodyPart)mimeBodyPart);
        return mimeMultipart;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void sendMultipartMessage(MimeMessage mimeMessage, MimeMultipart mimeMultipart) throws MessagingException {
        MimeMessage mimeMessage2 = mimeMessage;
        synchronized (mimeMessage2) {
            mimeMessage.setContent((Multipart)mimeMultipart);
            mimeMessage.setSentDate(new Date());
            Transport.send((Message)mimeMessage);
        }
    }

    private synchronized void connect(LogEvent logEvent) {
        if (this.message != null) {
            return;
        }
        try {
            this.message = SmtpManager.createMimeMessage(this.data, this.session, logEvent);
        } catch (MessagingException messagingException) {
            this.logError("Could not set SmtpAppender message options", messagingException);
            this.message = null;
        }
    }

    static class 1 {
    }

    private static class SMTPManagerFactory
    implements ManagerFactory<SmtpManager, FactoryData> {
        private SMTPManagerFactory() {
        }

        @Override
        public SmtpManager createManager(String string, FactoryData factoryData) {
            Authenticator authenticator;
            String string2 = "mail." + FactoryData.access$800(factoryData);
            Properties properties = PropertiesUtil.getSystemProperties();
            properties.put("mail.transport.protocol", FactoryData.access$800(factoryData));
            if (properties.getProperty("mail.host") == null) {
                properties.put("mail.host", NetUtils.getLocalHostname());
            }
            if (null != FactoryData.access$900(factoryData)) {
                properties.put(string2 + ".host", FactoryData.access$900(factoryData));
            }
            if (FactoryData.access$1000(factoryData) > 0) {
                properties.put(string2 + ".port", String.valueOf(FactoryData.access$1000(factoryData)));
            }
            if (null != (authenticator = this.buildAuthenticator(FactoryData.access$1100(factoryData), FactoryData.access$1200(factoryData)))) {
                properties.put(string2 + ".auth", "true");
            }
            Session session = Session.getInstance((Properties)properties, (Authenticator)authenticator);
            session.setProtocolForAddress("rfc822", FactoryData.access$800(factoryData));
            session.setDebug(FactoryData.access$1300(factoryData));
            return new SmtpManager(string, session, null, factoryData);
        }

        private Authenticator buildAuthenticator(String string, String string2) {
            if (null != string2 && null != string) {
                return new Authenticator(this, string, string2){
                    private final PasswordAuthentication passwordAuthentication;
                    final String val$username;
                    final String val$password;
                    final SMTPManagerFactory this$0;
                    {
                        this.this$0 = sMTPManagerFactory;
                        this.val$username = string;
                        this.val$password = string2;
                        this.passwordAuthentication = new PasswordAuthentication(this.val$username, this.val$password);
                    }

                    protected PasswordAuthentication getPasswordAuthentication() {
                        return this.passwordAuthentication;
                    }
                };
            }
            return null;
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (FactoryData)object);
        }

        SMTPManagerFactory(1 var1_1) {
            this();
        }
    }

    private static class FactoryData {
        private final String to;
        private final String cc;
        private final String bcc;
        private final String from;
        private final String replyto;
        private final AbstractStringLayout.Serializer subject;
        private final String protocol;
        private final String host;
        private final int port;
        private final String username;
        private final String password;
        private final boolean isDebug;
        private final int numElements;

        public FactoryData(String string, String string2, String string3, String string4, String string5, AbstractStringLayout.Serializer serializer, String string6, String string7, int n, String string8, String string9, boolean bl, int n2) {
            this.to = string;
            this.cc = string2;
            this.bcc = string3;
            this.from = string4;
            this.replyto = string5;
            this.subject = serializer;
            this.protocol = string6;
            this.host = string7;
            this.port = n;
            this.username = string8;
            this.password = string9;
            this.isDebug = bl;
            this.numElements = n2;
        }

        static AbstractStringLayout.Serializer access$100(FactoryData factoryData) {
            return factoryData.subject;
        }

        static String access$200(FactoryData factoryData) {
            return factoryData.bcc;
        }

        static String access$300(FactoryData factoryData) {
            return factoryData.cc;
        }

        static String access$400(FactoryData factoryData) {
            return factoryData.to;
        }

        static String access$500(FactoryData factoryData) {
            return factoryData.replyto;
        }

        static String access$600(FactoryData factoryData) {
            return factoryData.from;
        }

        static int access$700(FactoryData factoryData) {
            return factoryData.numElements;
        }

        static String access$800(FactoryData factoryData) {
            return factoryData.protocol;
        }

        static String access$900(FactoryData factoryData) {
            return factoryData.host;
        }

        static int access$1000(FactoryData factoryData) {
            return factoryData.port;
        }

        static String access$1100(FactoryData factoryData) {
            return factoryData.username;
        }

        static String access$1200(FactoryData factoryData) {
            return factoryData.password;
        }

        static boolean access$1300(FactoryData factoryData) {
            return factoryData.isDebug;
        }
    }
}

