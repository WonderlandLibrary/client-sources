/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.io.Serializable;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.DefaultFlowMessageFactory;
import org.apache.logging.log4j.message.EntryMessage;
import org.apache.logging.log4j.message.FlowMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.MessageFactory2;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;
import org.apache.logging.log4j.message.ReusableMessageFactory;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.MessageFactory2Adapter;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Constants;
import org.apache.logging.log4j.util.LambdaUtil;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.MessageSupplier;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.Strings;
import org.apache.logging.log4j.util.Supplier;

public abstract class AbstractLogger
implements ExtendedLogger,
Serializable {
    public static final Marker FLOW_MARKER = MarkerManager.getMarker("FLOW");
    public static final Marker ENTRY_MARKER = MarkerManager.getMarker("ENTER").setParents(FLOW_MARKER);
    public static final Marker EXIT_MARKER = MarkerManager.getMarker("EXIT").setParents(FLOW_MARKER);
    public static final Marker EXCEPTION_MARKER = MarkerManager.getMarker("EXCEPTION");
    public static final Marker THROWING_MARKER = MarkerManager.getMarker("THROWING").setParents(EXCEPTION_MARKER);
    public static final Marker CATCHING_MARKER = MarkerManager.getMarker("CATCHING").setParents(EXCEPTION_MARKER);
    public static final Class<? extends MessageFactory> DEFAULT_MESSAGE_FACTORY_CLASS = AbstractLogger.createClassForProperty("log4j2.messageFactory", ReusableMessageFactory.class, ParameterizedMessageFactory.class);
    public static final Class<? extends FlowMessageFactory> DEFAULT_FLOW_MESSAGE_FACTORY_CLASS = AbstractLogger.createFlowClassForProperty("log4j2.flowMessageFactory", DefaultFlowMessageFactory.class);
    private static final long serialVersionUID = 2L;
    private static final String FQCN = AbstractLogger.class.getName();
    private static final String THROWING = "Throwing";
    private static final String CATCHING = "Catching";
    protected final String name;
    private final MessageFactory2 messageFactory;
    private final FlowMessageFactory flowMessageFactory;

    public AbstractLogger() {
        this.name = this.getClass().getName();
        this.messageFactory = AbstractLogger.createDefaultMessageFactory();
        this.flowMessageFactory = AbstractLogger.createDefaultFlowMessageFactory();
    }

    public AbstractLogger(String name) {
        this(name, AbstractLogger.createDefaultMessageFactory());
    }

    public AbstractLogger(String name, MessageFactory messageFactory) {
        this.name = name;
        this.messageFactory = messageFactory == null ? AbstractLogger.createDefaultMessageFactory() : AbstractLogger.narrow(messageFactory);
        this.flowMessageFactory = AbstractLogger.createDefaultFlowMessageFactory();
    }

    public static void checkMessageFactory(ExtendedLogger logger, MessageFactory messageFactory) {
        String name = logger.getName();
        Object loggerMessageFactory = logger.getMessageFactory();
        if (messageFactory != null && !loggerMessageFactory.equals(messageFactory)) {
            StatusLogger.getLogger().warn("The Logger {} was created with the message factory {} and is now requested with the message factory {}, which may create log events with unexpected formatting.", (Object)name, loggerMessageFactory, (Object)messageFactory);
        } else if (messageFactory == null && !loggerMessageFactory.getClass().equals(DEFAULT_MESSAGE_FACTORY_CLASS)) {
            StatusLogger.getLogger().warn("The Logger {} was created with the message factory {} and is now requested with a null message factory (defaults to {}), which may create log events with unexpected formatting.", (Object)name, loggerMessageFactory, (Object)DEFAULT_MESSAGE_FACTORY_CLASS.getName());
        }
    }

    @Override
    public void catching(Level level, Throwable t) {
        this.catching(FQCN, level, t);
    }

    protected void catching(String fqcn, Level level, Throwable t) {
        if (this.isEnabled(level, CATCHING_MARKER, (Object)null, null)) {
            this.logMessageSafely(fqcn, level, CATCHING_MARKER, this.catchingMsg(t), t);
        }
    }

    @Override
    public void catching(Throwable t) {
        if (this.isEnabled(Level.ERROR, CATCHING_MARKER, (Object)null, null)) {
            this.logMessageSafely(FQCN, Level.ERROR, CATCHING_MARKER, this.catchingMsg(t), t);
        }
    }

    protected Message catchingMsg(Throwable t) {
        return this.messageFactory.newMessage(CATCHING);
    }

    private static Class<? extends MessageFactory> createClassForProperty(String property, Class<ReusableMessageFactory> reusableParameterizedMessageFactoryClass, Class<ParameterizedMessageFactory> parameterizedMessageFactoryClass) {
        try {
            String fallback = Constants.ENABLE_THREADLOCALS ? reusableParameterizedMessageFactoryClass.getName() : parameterizedMessageFactoryClass.getName();
            String clsName = PropertiesUtil.getProperties().getStringProperty(property, fallback);
            return LoaderUtil.loadClass(clsName).asSubclass(MessageFactory.class);
        } catch (Throwable t) {
            return parameterizedMessageFactoryClass;
        }
    }

    private static Class<? extends FlowMessageFactory> createFlowClassForProperty(String property, Class<DefaultFlowMessageFactory> defaultFlowMessageFactoryClass) {
        try {
            String clsName = PropertiesUtil.getProperties().getStringProperty(property, defaultFlowMessageFactoryClass.getName());
            return LoaderUtil.loadClass(clsName).asSubclass(FlowMessageFactory.class);
        } catch (Throwable t) {
            return defaultFlowMessageFactoryClass;
        }
    }

    private static MessageFactory2 createDefaultMessageFactory() {
        try {
            MessageFactory result = DEFAULT_MESSAGE_FACTORY_CLASS.newInstance();
            return AbstractLogger.narrow(result);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static MessageFactory2 narrow(MessageFactory result) {
        if (result instanceof MessageFactory2) {
            return (MessageFactory2)result;
        }
        return new MessageFactory2Adapter(result);
    }

    private static FlowMessageFactory createDefaultFlowMessageFactory() {
        try {
            return DEFAULT_FLOW_MESSAGE_FACTORY_CLASS.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void debug(Marker marker, CharSequence message) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, null);
    }

    @Override
    public void debug(Marker marker, CharSequence message, Throwable t) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, t);
    }

    @Override
    public void debug(Marker marker, Message msg) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void debug(Marker marker, Message msg, Throwable t) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, msg, t);
    }

    @Override
    public void debug(Marker marker, Object message) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, null);
    }

    @Override
    public void debug(Marker marker, Object message, Throwable t) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, t);
    }

    @Override
    public void debug(Marker marker, String message) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, (Throwable)null);
    }

    @Override
    public void debug(Marker marker, String message, Object ... params) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, params);
    }

    @Override
    public void debug(Marker marker, String message, Throwable t) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, t);
    }

    @Override
    public void debug(Message msg) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void debug(Message msg, Throwable t) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, msg, t);
    }

    @Override
    public void debug(CharSequence message) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, message, null);
    }

    @Override
    public void debug(CharSequence message, Throwable t) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, message, t);
    }

    @Override
    public void debug(Object message) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, message, null);
    }

    @Override
    public void debug(Object message, Throwable t) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, message, t);
    }

    @Override
    public void debug(String message) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, (Throwable)null);
    }

    @Override
    public void debug(String message, Object ... params) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, params);
    }

    @Override
    public void debug(String message, Throwable t) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, t);
    }

    @Override
    public void debug(Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void debug(Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, msgSupplier, t);
    }

    @Override
    public void debug(Marker marker, Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void debug(Marker marker, String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, paramSuppliers);
    }

    @Override
    public void debug(Marker marker, Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, msgSupplier, t);
    }

    @Override
    public void debug(String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, paramSuppliers);
    }

    @Override
    public void debug(Marker marker, MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void debug(Marker marker, MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, msgSupplier, t);
    }

    @Override
    public void debug(MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void debug(MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, msgSupplier, t);
    }

    @Override
    public void debug(Marker marker, String message, Object p0) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, p0);
    }

    @Override
    public void debug(Marker marker, String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, p0, p1);
    }

    @Override
    public void debug(Marker marker, String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, p0, p1, p2);
    }

    @Override
    public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, p0, p1, p2, p3);
    }

    @Override
    public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override
    public void debug(String message, Object p0) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, p0);
    }

    @Override
    public void debug(String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, p0, p1);
    }

    @Override
    public void debug(String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, p0, p1, p2);
    }

    @Override
    public void debug(String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, p0, p1, p2, p3);
    }

    @Override
    public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    protected EntryMessage enter(String fqcn, String format, Supplier<?> ... paramSuppliers) {
        EntryMessage entryMsg = null;
        if (this.isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, null)) {
            entryMsg = this.entryMsg(format, paramSuppliers);
            this.logMessageSafely(fqcn, Level.TRACE, ENTRY_MARKER, entryMsg, null);
        }
        return entryMsg;
    }

    @Deprecated
    protected EntryMessage enter(String fqcn, String format, MessageSupplier ... paramSuppliers) {
        EntryMessage entryMsg = null;
        if (this.isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, null)) {
            entryMsg = this.entryMsg(format, paramSuppliers);
            this.logMessageSafely(fqcn, Level.TRACE, ENTRY_MARKER, entryMsg, null);
        }
        return entryMsg;
    }

    protected EntryMessage enter(String fqcn, String format, Object ... params) {
        EntryMessage entryMsg = null;
        if (this.isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, null)) {
            entryMsg = this.entryMsg(format, params);
            this.logMessageSafely(fqcn, Level.TRACE, ENTRY_MARKER, entryMsg, null);
        }
        return entryMsg;
    }

    @Deprecated
    protected EntryMessage enter(String fqcn, MessageSupplier msgSupplier) {
        EntryMessage message = null;
        if (this.isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, null)) {
            message = this.flowMessageFactory.newEntryMessage(msgSupplier.get());
            this.logMessageSafely(fqcn, Level.TRACE, ENTRY_MARKER, message, null);
        }
        return message;
    }

    protected EntryMessage enter(String fqcn, Message message) {
        EntryMessage flowMessage = null;
        if (this.isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, null)) {
            flowMessage = this.flowMessageFactory.newEntryMessage(message);
            this.logMessageSafely(fqcn, Level.TRACE, ENTRY_MARKER, flowMessage, null);
        }
        return flowMessage;
    }

    @Override
    public void entry() {
        this.entry(FQCN, (Object[])null);
    }

    @Override
    public void entry(Object ... params) {
        this.entry(FQCN, params);
    }

    protected void entry(String fqcn, Object ... params) {
        if (this.isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, null)) {
            if (params == null) {
                this.logMessageSafely(fqcn, Level.TRACE, ENTRY_MARKER, this.entryMsg((String)null, (Supplier[])null), null);
            } else {
                this.logMessageSafely(fqcn, Level.TRACE, ENTRY_MARKER, this.entryMsg(null, params), null);
            }
        }
    }

    protected EntryMessage entryMsg(String format, Object ... params) {
        int count;
        int n = count = params == null ? 0 : params.length;
        if (count == 0) {
            if (Strings.isEmpty(format)) {
                return this.flowMessageFactory.newEntryMessage(null);
            }
            return this.flowMessageFactory.newEntryMessage(new SimpleMessage(format));
        }
        if (format != null) {
            return this.flowMessageFactory.newEntryMessage(new ParameterizedMessage(format, params));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("params(");
        for (int i = 0; i < count; ++i) {
            Object parm;
            if (i > 0) {
                sb.append(", ");
            }
            sb.append((parm = params[i]) instanceof Message ? ((Message)parm).getFormattedMessage() : String.valueOf(parm));
        }
        sb.append(')');
        return this.flowMessageFactory.newEntryMessage(new SimpleMessage(sb));
    }

    protected EntryMessage entryMsg(String format, MessageSupplier ... paramSuppliers) {
        int count = paramSuppliers == null ? 0 : paramSuppliers.length;
        Object[] params = new Object[count];
        for (int i = 0; i < count; ++i) {
            params[i] = paramSuppliers[i].get();
            params[i] = params[i] != null ? ((Message)params[i]).getFormattedMessage() : null;
        }
        return this.entryMsg(format, params);
    }

    protected EntryMessage entryMsg(String format, Supplier<?> ... paramSuppliers) {
        int count = paramSuppliers == null ? 0 : paramSuppliers.length;
        Object[] params = new Object[count];
        for (int i = 0; i < count; ++i) {
            params[i] = paramSuppliers[i].get();
            if (!(params[i] instanceof Message)) continue;
            params[i] = ((Message)params[i]).getFormattedMessage();
        }
        return this.entryMsg(format, params);
    }

    @Override
    public void error(Marker marker, Message msg) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void error(Marker marker, Message msg, Throwable t) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, msg, t);
    }

    @Override
    public void error(Marker marker, CharSequence message) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, null);
    }

    @Override
    public void error(Marker marker, CharSequence message, Throwable t) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, t);
    }

    @Override
    public void error(Marker marker, Object message) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, null);
    }

    @Override
    public void error(Marker marker, Object message, Throwable t) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, t);
    }

    @Override
    public void error(Marker marker, String message) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, (Throwable)null);
    }

    @Override
    public void error(Marker marker, String message, Object ... params) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, params);
    }

    @Override
    public void error(Marker marker, String message, Throwable t) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, t);
    }

    @Override
    public void error(Message msg) {
        this.logIfEnabled(FQCN, Level.ERROR, null, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void error(Message msg, Throwable t) {
        this.logIfEnabled(FQCN, Level.ERROR, null, msg, t);
    }

    @Override
    public void error(CharSequence message) {
        this.logIfEnabled(FQCN, Level.ERROR, null, message, null);
    }

    @Override
    public void error(CharSequence message, Throwable t) {
        this.logIfEnabled(FQCN, Level.ERROR, null, message, t);
    }

    @Override
    public void error(Object message) {
        this.logIfEnabled(FQCN, Level.ERROR, null, message, null);
    }

    @Override
    public void error(Object message, Throwable t) {
        this.logIfEnabled(FQCN, Level.ERROR, null, message, t);
    }

    @Override
    public void error(String message) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, (Throwable)null);
    }

    @Override
    public void error(String message, Object ... params) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, params);
    }

    @Override
    public void error(String message, Throwable t) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, t);
    }

    @Override
    public void error(Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, Level.ERROR, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void error(Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.ERROR, null, msgSupplier, t);
    }

    @Override
    public void error(Marker marker, Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void error(Marker marker, String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, paramSuppliers);
    }

    @Override
    public void error(Marker marker, Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, msgSupplier, t);
    }

    @Override
    public void error(String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, paramSuppliers);
    }

    @Override
    public void error(Marker marker, MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void error(Marker marker, MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, msgSupplier, t);
    }

    @Override
    public void error(MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, Level.ERROR, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void error(MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.ERROR, null, msgSupplier, t);
    }

    @Override
    public void error(Marker marker, String message, Object p0) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, p0);
    }

    @Override
    public void error(Marker marker, String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, p0, p1);
    }

    @Override
    public void error(Marker marker, String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, p0, p1, p2);
    }

    @Override
    public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, p0, p1, p2, p3);
    }

    @Override
    public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override
    public void error(String message, Object p0) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, p0);
    }

    @Override
    public void error(String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, p0, p1);
    }

    @Override
    public void error(String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, p0, p1, p2);
    }

    @Override
    public void error(String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, p0, p1, p2, p3);
    }

    @Override
    public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override
    public void exit() {
        this.exit(FQCN, null);
    }

    @Override
    public <R> R exit(R result) {
        return this.exit(FQCN, result);
    }

    protected <R> R exit(String fqcn, R result) {
        this.logIfEnabled(fqcn, Level.TRACE, EXIT_MARKER, this.exitMsg(null, result), null);
        return result;
    }

    protected <R> R exit(String fqcn, String format, R result) {
        this.logIfEnabled(fqcn, Level.TRACE, EXIT_MARKER, this.exitMsg(format, result), null);
        return result;
    }

    protected Message exitMsg(String format, Object result) {
        if (result == null) {
            if (format == null) {
                return this.messageFactory.newMessage("Exit");
            }
            return this.messageFactory.newMessage("Exit: " + format);
        }
        if (format == null) {
            return this.messageFactory.newMessage("Exit with(" + result + ')');
        }
        return this.messageFactory.newMessage("Exit: " + format, result);
    }

    @Override
    public void fatal(Marker marker, Message msg) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void fatal(Marker marker, Message msg, Throwable t) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, msg, t);
    }

    @Override
    public void fatal(Marker marker, CharSequence message) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, null);
    }

    @Override
    public void fatal(Marker marker, CharSequence message, Throwable t) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, t);
    }

    @Override
    public void fatal(Marker marker, Object message) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, null);
    }

    @Override
    public void fatal(Marker marker, Object message, Throwable t) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, t);
    }

    @Override
    public void fatal(Marker marker, String message) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, (Throwable)null);
    }

    @Override
    public void fatal(Marker marker, String message, Object ... params) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, params);
    }

    @Override
    public void fatal(Marker marker, String message, Throwable t) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, t);
    }

    @Override
    public void fatal(Message msg) {
        this.logIfEnabled(FQCN, Level.FATAL, null, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void fatal(Message msg, Throwable t) {
        this.logIfEnabled(FQCN, Level.FATAL, null, msg, t);
    }

    @Override
    public void fatal(CharSequence message) {
        this.logIfEnabled(FQCN, Level.FATAL, null, message, null);
    }

    @Override
    public void fatal(CharSequence message, Throwable t) {
        this.logIfEnabled(FQCN, Level.FATAL, null, message, t);
    }

    @Override
    public void fatal(Object message) {
        this.logIfEnabled(FQCN, Level.FATAL, null, message, null);
    }

    @Override
    public void fatal(Object message, Throwable t) {
        this.logIfEnabled(FQCN, Level.FATAL, null, message, t);
    }

    @Override
    public void fatal(String message) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, (Throwable)null);
    }

    @Override
    public void fatal(String message, Object ... params) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, params);
    }

    @Override
    public void fatal(String message, Throwable t) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, t);
    }

    @Override
    public void fatal(Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, Level.FATAL, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void fatal(Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.FATAL, null, msgSupplier, t);
    }

    @Override
    public void fatal(Marker marker, Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void fatal(Marker marker, String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, paramSuppliers);
    }

    @Override
    public void fatal(Marker marker, Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, msgSupplier, t);
    }

    @Override
    public void fatal(String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, paramSuppliers);
    }

    @Override
    public void fatal(Marker marker, MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void fatal(Marker marker, MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, msgSupplier, t);
    }

    @Override
    public void fatal(MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, Level.FATAL, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void fatal(MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.FATAL, null, msgSupplier, t);
    }

    @Override
    public void fatal(Marker marker, String message, Object p0) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, p0);
    }

    @Override
    public void fatal(Marker marker, String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, p0, p1);
    }

    @Override
    public void fatal(Marker marker, String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, p0, p1, p2);
    }

    @Override
    public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, p0, p1, p2, p3);
    }

    @Override
    public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override
    public void fatal(String message, Object p0) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, p0);
    }

    @Override
    public void fatal(String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, p0, p1);
    }

    @Override
    public void fatal(String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, p0, p1, p2);
    }

    @Override
    public void fatal(String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, p0, p1, p2, p3);
    }

    @Override
    public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override
    public <MF extends MessageFactory> MF getMessageFactory() {
        return (MF)this.messageFactory;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void info(Marker marker, Message msg) {
        this.logIfEnabled(FQCN, Level.INFO, marker, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void info(Marker marker, Message msg, Throwable t) {
        this.logIfEnabled(FQCN, Level.INFO, marker, msg, t);
    }

    @Override
    public void info(Marker marker, CharSequence message) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, null);
    }

    @Override
    public void info(Marker marker, CharSequence message, Throwable t) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, t);
    }

    @Override
    public void info(Marker marker, Object message) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, null);
    }

    @Override
    public void info(Marker marker, Object message, Throwable t) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, t);
    }

    @Override
    public void info(Marker marker, String message) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, (Throwable)null);
    }

    @Override
    public void info(Marker marker, String message, Object ... params) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, params);
    }

    @Override
    public void info(Marker marker, String message, Throwable t) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, t);
    }

    @Override
    public void info(Message msg) {
        this.logIfEnabled(FQCN, Level.INFO, null, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void info(Message msg, Throwable t) {
        this.logIfEnabled(FQCN, Level.INFO, null, msg, t);
    }

    @Override
    public void info(CharSequence message) {
        this.logIfEnabled(FQCN, Level.INFO, null, message, null);
    }

    @Override
    public void info(CharSequence message, Throwable t) {
        this.logIfEnabled(FQCN, Level.INFO, null, message, t);
    }

    @Override
    public void info(Object message) {
        this.logIfEnabled(FQCN, Level.INFO, null, message, null);
    }

    @Override
    public void info(Object message, Throwable t) {
        this.logIfEnabled(FQCN, Level.INFO, null, message, t);
    }

    @Override
    public void info(String message) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, (Throwable)null);
    }

    @Override
    public void info(String message, Object ... params) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, params);
    }

    @Override
    public void info(String message, Throwable t) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, t);
    }

    @Override
    public void info(Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, Level.INFO, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void info(Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.INFO, null, msgSupplier, t);
    }

    @Override
    public void info(Marker marker, Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, Level.INFO, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void info(Marker marker, String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, paramSuppliers);
    }

    @Override
    public void info(Marker marker, Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.INFO, marker, msgSupplier, t);
    }

    @Override
    public void info(String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, paramSuppliers);
    }

    @Override
    public void info(Marker marker, MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, Level.INFO, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void info(Marker marker, MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.INFO, marker, msgSupplier, t);
    }

    @Override
    public void info(MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, Level.INFO, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void info(MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.INFO, null, msgSupplier, t);
    }

    @Override
    public void info(Marker marker, String message, Object p0) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, p0);
    }

    @Override
    public void info(Marker marker, String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, p0, p1);
    }

    @Override
    public void info(Marker marker, String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, p0, p1, p2);
    }

    @Override
    public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, p0, p1, p2, p3);
    }

    @Override
    public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override
    public void info(String message, Object p0) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, p0);
    }

    @Override
    public void info(String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, p0, p1);
    }

    @Override
    public void info(String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, p0, p1, p2);
    }

    @Override
    public void info(String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, p0, p1, p2, p3);
    }

    @Override
    public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override
    public boolean isDebugEnabled() {
        return this.isEnabled(Level.DEBUG, null, null);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return this.isEnabled(Level.DEBUG, marker, (Object)null, null);
    }

    @Override
    public boolean isEnabled(Level level) {
        return this.isEnabled(level, null, (Object)null, null);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker) {
        return this.isEnabled(level, marker, (Object)null, null);
    }

    @Override
    public boolean isErrorEnabled() {
        return this.isEnabled(Level.ERROR, null, (Object)null, null);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return this.isEnabled(Level.ERROR, marker, (Object)null, null);
    }

    @Override
    public boolean isFatalEnabled() {
        return this.isEnabled(Level.FATAL, null, (Object)null, null);
    }

    @Override
    public boolean isFatalEnabled(Marker marker) {
        return this.isEnabled(Level.FATAL, marker, (Object)null, null);
    }

    @Override
    public boolean isInfoEnabled() {
        return this.isEnabled(Level.INFO, null, (Object)null, null);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return this.isEnabled(Level.INFO, marker, (Object)null, null);
    }

    @Override
    public boolean isTraceEnabled() {
        return this.isEnabled(Level.TRACE, null, (Object)null, null);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return this.isEnabled(Level.TRACE, marker, (Object)null, null);
    }

    @Override
    public boolean isWarnEnabled() {
        return this.isEnabled(Level.WARN, null, (Object)null, null);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return this.isEnabled(Level.WARN, marker, (Object)null, null);
    }

    @Override
    public void log(Level level, Marker marker, Message msg) {
        this.logIfEnabled(FQCN, level, marker, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void log(Level level, Marker marker, Message msg, Throwable t) {
        this.logIfEnabled(FQCN, level, marker, msg, t);
    }

    @Override
    public void log(Level level, Marker marker, CharSequence message) {
        this.logIfEnabled(FQCN, level, marker, message, (Throwable)null);
    }

    @Override
    public void log(Level level, Marker marker, CharSequence message, Throwable t) {
        if (this.isEnabled(level, marker, message, t)) {
            this.logMessage(FQCN, level, marker, message, t);
        }
    }

    @Override
    public void log(Level level, Marker marker, Object message) {
        this.logIfEnabled(FQCN, level, marker, message, (Throwable)null);
    }

    @Override
    public void log(Level level, Marker marker, Object message, Throwable t) {
        if (this.isEnabled(level, marker, message, t)) {
            this.logMessage(FQCN, level, marker, message, t);
        }
    }

    @Override
    public void log(Level level, Marker marker, String message) {
        this.logIfEnabled(FQCN, level, marker, message, (Throwable)null);
    }

    @Override
    public void log(Level level, Marker marker, String message, Object ... params) {
        this.logIfEnabled(FQCN, level, marker, message, params);
    }

    @Override
    public void log(Level level, Marker marker, String message, Throwable t) {
        this.logIfEnabled(FQCN, level, marker, message, t);
    }

    @Override
    public void log(Level level, Message msg) {
        this.logIfEnabled(FQCN, level, null, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void log(Level level, Message msg, Throwable t) {
        this.logIfEnabled(FQCN, level, null, msg, t);
    }

    @Override
    public void log(Level level, CharSequence message) {
        this.logIfEnabled(FQCN, level, null, message, null);
    }

    @Override
    public void log(Level level, CharSequence message, Throwable t) {
        this.logIfEnabled(FQCN, level, null, message, t);
    }

    @Override
    public void log(Level level, Object message) {
        this.logIfEnabled(FQCN, level, null, message, null);
    }

    @Override
    public void log(Level level, Object message, Throwable t) {
        this.logIfEnabled(FQCN, level, null, message, t);
    }

    @Override
    public void log(Level level, String message) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, (Throwable)null);
    }

    @Override
    public void log(Level level, String message, Object ... params) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, params);
    }

    @Override
    public void log(Level level, String message, Throwable t) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, t);
    }

    @Override
    public void log(Level level, Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, level, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void log(Level level, Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, level, null, msgSupplier, t);
    }

    @Override
    public void log(Level level, Marker marker, Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, level, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void log(Level level, Marker marker, String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, level, marker, message, paramSuppliers);
    }

    @Override
    public void log(Level level, Marker marker, Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, level, marker, msgSupplier, t);
    }

    @Override
    public void log(Level level, String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, paramSuppliers);
    }

    @Override
    public void log(Level level, Marker marker, MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, level, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void log(Level level, Marker marker, MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, level, marker, msgSupplier, t);
    }

    @Override
    public void log(Level level, MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, level, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void log(Level level, MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, level, null, msgSupplier, t);
    }

    @Override
    public void log(Level level, Marker marker, String message, Object p0) {
        this.logIfEnabled(FQCN, level, marker, message, p0);
    }

    @Override
    public void log(Level level, Marker marker, String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, level, marker, message, p0, p1);
    }

    @Override
    public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, level, marker, message, p0, p1, p2);
    }

    @Override
    public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, level, marker, message, p0, p1, p2, p3);
    }

    @Override
    public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, level, marker, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, level, marker, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, level, marker, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override
    public void log(Level level, String message, Object p0) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, p0);
    }

    @Override
    public void log(Level level, String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, p0, p1);
    }

    @Override
    public void log(Level level, String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, p0, p1, p2);
    }

    @Override
    public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, p0, p1, p2, p3);
    }

    @Override
    public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, level, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, Message msg, Throwable t) {
        if (this.isEnabled(level, marker, msg, t)) {
            this.logMessageSafely(fqcn, level, marker, msg, t);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, MessageSupplier msgSupplier, Throwable t) {
        if (this.isEnabled(level, marker, msgSupplier, t)) {
            this.logMessage(fqcn, level, marker, msgSupplier, t);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, Object message, Throwable t) {
        if (this.isEnabled(level, marker, message, t)) {
            this.logMessage(fqcn, level, marker, message, t);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, CharSequence message, Throwable t) {
        if (this.isEnabled(level, marker, message, t)) {
            this.logMessage(fqcn, level, marker, message, t);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, Supplier<?> msgSupplier, Throwable t) {
        if (this.isEnabled(level, marker, msgSupplier, t)) {
            this.logMessage(fqcn, level, marker, msgSupplier, t);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message) {
        if (this.isEnabled(level, marker, message)) {
            this.logMessage(fqcn, level, marker, message);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message, Supplier<?> ... paramSuppliers) {
        if (this.isEnabled(level, marker, message)) {
            this.logMessage(fqcn, level, marker, message, paramSuppliers);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message, Object ... params) {
        if (this.isEnabled(level, marker, message, params)) {
            this.logMessage(fqcn, level, marker, message, params);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message, Object p0) {
        if (this.isEnabled(level, marker, message, p0)) {
            this.logMessage(fqcn, level, marker, message, p0);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message, Object p0, Object p1) {
        if (this.isEnabled(level, marker, message, p0, p1)) {
            this.logMessage(fqcn, level, marker, message, p0, p1);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
        if (this.isEnabled(level, marker, message, p0, p1, p2)) {
            this.logMessage(fqcn, level, marker, message, p0, p1, p2);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        if (this.isEnabled(level, marker, message, p0, p1, p2, p3)) {
            this.logMessage(fqcn, level, marker, message, p0, p1, p2, p3);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        if (this.isEnabled(level, marker, message, p0, p1, p2, p3, p4)) {
            this.logMessage(fqcn, level, marker, message, p0, p1, p2, p3, p4);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        if (this.isEnabled(level, marker, message, p0, p1, p2, p3, p4, p5)) {
            this.logMessage(fqcn, level, marker, message, p0, p1, p2, p3, p4, p5);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        if (this.isEnabled(level, marker, message, p0, p1, p2, p3, p4, p5, p6)) {
            this.logMessage(fqcn, level, marker, message, p0, p1, p2, p3, p4, p5, p6);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        if (this.isEnabled(level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7)) {
            this.logMessage(fqcn, level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        if (this.isEnabled(level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8)) {
            this.logMessage(fqcn, level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        if (this.isEnabled(level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9)) {
            this.logMessage(fqcn, level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }

    @Override
    public void logIfEnabled(String fqcn, Level level, Marker marker, String message, Throwable t) {
        if (this.isEnabled(level, marker, message, t)) {
            this.logMessage(fqcn, level, marker, message, t);
        }
    }

    protected void logMessage(String fqcn, Level level, Marker marker, CharSequence message, Throwable t) {
        this.logMessageSafely(fqcn, level, marker, this.messageFactory.newMessage(message), t);
    }

    protected void logMessage(String fqcn, Level level, Marker marker, Object message, Throwable t) {
        this.logMessageSafely(fqcn, level, marker, this.messageFactory.newMessage(message), t);
    }

    protected void logMessage(String fqcn, Level level, Marker marker, MessageSupplier msgSupplier, Throwable t) {
        Message message = LambdaUtil.get(msgSupplier);
        this.logMessageSafely(fqcn, level, marker, message, t == null && message != null ? message.getThrowable() : t);
    }

    protected void logMessage(String fqcn, Level level, Marker marker, Supplier<?> msgSupplier, Throwable t) {
        Message message = LambdaUtil.getMessage(msgSupplier, this.messageFactory);
        this.logMessageSafely(fqcn, level, marker, message, t == null && message != null ? message.getThrowable() : t);
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message, Throwable t) {
        this.logMessageSafely(fqcn, level, marker, this.messageFactory.newMessage(message), t);
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message) {
        Message msg = this.messageFactory.newMessage(message);
        this.logMessageSafely(fqcn, level, marker, msg, msg.getThrowable());
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message, Object ... params) {
        Message msg = this.messageFactory.newMessage(message, params);
        this.logMessageSafely(fqcn, level, marker, msg, msg.getThrowable());
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message, Object p0) {
        Message msg = this.messageFactory.newMessage(message, p0);
        this.logMessageSafely(fqcn, level, marker, msg, msg.getThrowable());
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message, Object p0, Object p1) {
        Message msg = this.messageFactory.newMessage(message, p0, p1);
        this.logMessageSafely(fqcn, level, marker, msg, msg.getThrowable());
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
        Message msg = this.messageFactory.newMessage(message, p0, p1, p2);
        this.logMessageSafely(fqcn, level, marker, msg, msg.getThrowable());
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        Message msg = this.messageFactory.newMessage(message, p0, p1, p2, p3);
        this.logMessageSafely(fqcn, level, marker, msg, msg.getThrowable());
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        Message msg = this.messageFactory.newMessage(message, p0, p1, p2, p3, p4);
        this.logMessageSafely(fqcn, level, marker, msg, msg.getThrowable());
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        Message msg = this.messageFactory.newMessage(message, p0, p1, p2, p3, p4, p5);
        this.logMessageSafely(fqcn, level, marker, msg, msg.getThrowable());
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        Message msg = this.messageFactory.newMessage(message, p0, p1, p2, p3, p4, p5, p6);
        this.logMessageSafely(fqcn, level, marker, msg, msg.getThrowable());
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        Message msg = this.messageFactory.newMessage(message, p0, p1, p2, p3, p4, p5, p6, p7);
        this.logMessageSafely(fqcn, level, marker, msg, msg.getThrowable());
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        Message msg = this.messageFactory.newMessage(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        this.logMessageSafely(fqcn, level, marker, msg, msg.getThrowable());
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        Message msg = this.messageFactory.newMessage(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        this.logMessageSafely(fqcn, level, marker, msg, msg.getThrowable());
    }

    protected void logMessage(String fqcn, Level level, Marker marker, String message, Supplier<?> ... paramSuppliers) {
        Message msg = this.messageFactory.newMessage(message, LambdaUtil.getAll(paramSuppliers));
        this.logMessageSafely(fqcn, level, marker, msg, msg.getThrowable());
    }

    @Override
    public void printf(Level level, Marker marker, String format, Object ... params) {
        if (this.isEnabled(level, marker, format, params)) {
            StringFormattedMessage msg = new StringFormattedMessage(format, params);
            this.logMessageSafely(FQCN, level, marker, msg, msg.getThrowable());
        }
    }

    @Override
    public void printf(Level level, String format, Object ... params) {
        if (this.isEnabled(level, (Marker)null, format, params)) {
            StringFormattedMessage msg = new StringFormattedMessage(format, params);
            this.logMessageSafely(FQCN, level, null, msg, msg.getThrowable());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void logMessageSafely(String fqcn, Level level, Marker marker, Message msg, Throwable throwable) {
        try {
            this.logMessage(fqcn, level, marker, msg, throwable);
        } finally {
            ReusableMessageFactory.release(msg);
        }
    }

    @Override
    public <T extends Throwable> T throwing(T t) {
        return this.throwing(FQCN, Level.ERROR, t);
    }

    @Override
    public <T extends Throwable> T throwing(Level level, T t) {
        return this.throwing(FQCN, level, t);
    }

    protected <T extends Throwable> T throwing(String fqcn, Level level, T t) {
        if (this.isEnabled(level, THROWING_MARKER, (Object)null, null)) {
            this.logMessageSafely(fqcn, level, THROWING_MARKER, this.throwingMsg(t), t);
        }
        return t;
    }

    protected Message throwingMsg(Throwable t) {
        return this.messageFactory.newMessage(THROWING);
    }

    @Override
    public void trace(Marker marker, Message msg) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void trace(Marker marker, Message msg, Throwable t) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, msg, t);
    }

    @Override
    public void trace(Marker marker, CharSequence message) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, null);
    }

    @Override
    public void trace(Marker marker, CharSequence message, Throwable t) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, t);
    }

    @Override
    public void trace(Marker marker, Object message) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, null);
    }

    @Override
    public void trace(Marker marker, Object message, Throwable t) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, t);
    }

    @Override
    public void trace(Marker marker, String message) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, (Throwable)null);
    }

    @Override
    public void trace(Marker marker, String message, Object ... params) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, params);
    }

    @Override
    public void trace(Marker marker, String message, Throwable t) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, t);
    }

    @Override
    public void trace(Message msg) {
        this.logIfEnabled(FQCN, Level.TRACE, null, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void trace(Message msg, Throwable t) {
        this.logIfEnabled(FQCN, Level.TRACE, null, msg, t);
    }

    @Override
    public void trace(CharSequence message) {
        this.logIfEnabled(FQCN, Level.TRACE, null, message, null);
    }

    @Override
    public void trace(CharSequence message, Throwable t) {
        this.logIfEnabled(FQCN, Level.TRACE, null, message, t);
    }

    @Override
    public void trace(Object message) {
        this.logIfEnabled(FQCN, Level.TRACE, null, message, null);
    }

    @Override
    public void trace(Object message, Throwable t) {
        this.logIfEnabled(FQCN, Level.TRACE, null, message, t);
    }

    @Override
    public void trace(String message) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, (Throwable)null);
    }

    @Override
    public void trace(String message, Object ... params) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, params);
    }

    @Override
    public void trace(String message, Throwable t) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, t);
    }

    @Override
    public void trace(Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, Level.TRACE, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void trace(Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.TRACE, null, msgSupplier, t);
    }

    @Override
    public void trace(Marker marker, Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void trace(Marker marker, String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, paramSuppliers);
    }

    @Override
    public void trace(Marker marker, Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, msgSupplier, t);
    }

    @Override
    public void trace(String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, paramSuppliers);
    }

    @Override
    public void trace(Marker marker, MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void trace(Marker marker, MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, msgSupplier, t);
    }

    @Override
    public void trace(MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, Level.TRACE, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void trace(MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.TRACE, null, msgSupplier, t);
    }

    @Override
    public void trace(Marker marker, String message, Object p0) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, p0);
    }

    @Override
    public void trace(Marker marker, String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, p0, p1);
    }

    @Override
    public void trace(Marker marker, String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, p0, p1, p2);
    }

    @Override
    public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, p0, p1, p2, p3);
    }

    @Override
    public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override
    public void trace(String message, Object p0) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, p0);
    }

    @Override
    public void trace(String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, p0, p1);
    }

    @Override
    public void trace(String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, p0, p1, p2);
    }

    @Override
    public void trace(String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, p0, p1, p2, p3);
    }

    @Override
    public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override
    public EntryMessage traceEntry() {
        return this.enter(FQCN, null, (Object[])null);
    }

    @Override
    public EntryMessage traceEntry(String format, Object ... params) {
        return this.enter(FQCN, format, params);
    }

    @Override
    public EntryMessage traceEntry(Supplier<?> ... paramSuppliers) {
        return this.enter(FQCN, (String)null, paramSuppliers);
    }

    @Override
    public EntryMessage traceEntry(String format, Supplier<?> ... paramSuppliers) {
        return this.enter(FQCN, format, paramSuppliers);
    }

    @Override
    public EntryMessage traceEntry(Message message) {
        return this.enter(FQCN, message);
    }

    @Override
    public void traceExit() {
        this.exit(FQCN, null, null);
    }

    @Override
    public <R> R traceExit(R result) {
        return this.exit(FQCN, null, result);
    }

    @Override
    public <R> R traceExit(String format, R result) {
        return this.exit(FQCN, format, result);
    }

    @Override
    public void traceExit(EntryMessage message) {
        if (message != null && this.isEnabled(Level.TRACE, EXIT_MARKER, message, null)) {
            this.logMessageSafely(FQCN, Level.TRACE, EXIT_MARKER, this.flowMessageFactory.newExitMessage(message), null);
        }
    }

    @Override
    public <R> R traceExit(EntryMessage message, R result) {
        if (message != null && this.isEnabled(Level.TRACE, EXIT_MARKER, message, null)) {
            this.logMessageSafely(FQCN, Level.TRACE, EXIT_MARKER, this.flowMessageFactory.newExitMessage(result, message), null);
        }
        return result;
    }

    @Override
    public <R> R traceExit(Message message, R result) {
        if (message != null && this.isEnabled(Level.TRACE, EXIT_MARKER, message, null)) {
            this.logMessageSafely(FQCN, Level.TRACE, EXIT_MARKER, this.flowMessageFactory.newExitMessage(result, message), null);
        }
        return result;
    }

    @Override
    public void warn(Marker marker, Message msg) {
        this.logIfEnabled(FQCN, Level.WARN, marker, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void warn(Marker marker, Message msg, Throwable t) {
        this.logIfEnabled(FQCN, Level.WARN, marker, msg, t);
    }

    @Override
    public void warn(Marker marker, CharSequence message) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, null);
    }

    @Override
    public void warn(Marker marker, CharSequence message, Throwable t) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, t);
    }

    @Override
    public void warn(Marker marker, Object message) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, null);
    }

    @Override
    public void warn(Marker marker, Object message, Throwable t) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, t);
    }

    @Override
    public void warn(Marker marker, String message) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, (Throwable)null);
    }

    @Override
    public void warn(Marker marker, String message, Object ... params) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, params);
    }

    @Override
    public void warn(Marker marker, String message, Throwable t) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, t);
    }

    @Override
    public void warn(Message msg) {
        this.logIfEnabled(FQCN, Level.WARN, null, msg, msg != null ? msg.getThrowable() : null);
    }

    @Override
    public void warn(Message msg, Throwable t) {
        this.logIfEnabled(FQCN, Level.WARN, null, msg, t);
    }

    @Override
    public void warn(CharSequence message) {
        this.logIfEnabled(FQCN, Level.WARN, null, message, null);
    }

    @Override
    public void warn(CharSequence message, Throwable t) {
        this.logIfEnabled(FQCN, Level.WARN, null, message, t);
    }

    @Override
    public void warn(Object message) {
        this.logIfEnabled(FQCN, Level.WARN, null, message, null);
    }

    @Override
    public void warn(Object message, Throwable t) {
        this.logIfEnabled(FQCN, Level.WARN, null, message, t);
    }

    @Override
    public void warn(String message) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, (Throwable)null);
    }

    @Override
    public void warn(String message, Object ... params) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, params);
    }

    @Override
    public void warn(String message, Throwable t) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, t);
    }

    @Override
    public void warn(Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, Level.WARN, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void warn(Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.WARN, null, msgSupplier, t);
    }

    @Override
    public void warn(Marker marker, Supplier<?> msgSupplier) {
        this.logIfEnabled(FQCN, Level.WARN, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void warn(Marker marker, String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, paramSuppliers);
    }

    @Override
    public void warn(Marker marker, Supplier<?> msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.WARN, marker, msgSupplier, t);
    }

    @Override
    public void warn(String message, Supplier<?> ... paramSuppliers) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, paramSuppliers);
    }

    @Override
    public void warn(Marker marker, MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, Level.WARN, marker, msgSupplier, (Throwable)null);
    }

    @Override
    public void warn(Marker marker, MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.WARN, marker, msgSupplier, t);
    }

    @Override
    public void warn(MessageSupplier msgSupplier) {
        this.logIfEnabled(FQCN, Level.WARN, null, msgSupplier, (Throwable)null);
    }

    @Override
    public void warn(MessageSupplier msgSupplier, Throwable t) {
        this.logIfEnabled(FQCN, Level.WARN, null, msgSupplier, t);
    }

    @Override
    public void warn(Marker marker, String message, Object p0) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, p0);
    }

    @Override
    public void warn(Marker marker, String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, p0, p1);
    }

    @Override
    public void warn(Marker marker, String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, p0, p1, p2);
    }

    @Override
    public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, p0, p1, p2, p3);
    }

    @Override
    public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override
    public void warn(String message, Object p0) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, p0);
    }

    @Override
    public void warn(String message, Object p0, Object p1) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, p0, p1);
    }

    @Override
    public void warn(String message, Object p0, Object p1, Object p2) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, p0, p1, p2);
    }

    @Override
    public void warn(String message, Object p0, Object p1, Object p2, Object p3) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, p0, p1, p2, p3);
    }

    @Override
    public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, p0, p1, p2, p3, p4);
    }

    @Override
    public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
}

