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

    public AbstractLogger(String string) {
        this(string, AbstractLogger.createDefaultMessageFactory());
    }

    public AbstractLogger(String string, MessageFactory messageFactory) {
        this.name = string;
        this.messageFactory = messageFactory == null ? AbstractLogger.createDefaultMessageFactory() : AbstractLogger.narrow(messageFactory);
        this.flowMessageFactory = AbstractLogger.createDefaultFlowMessageFactory();
    }

    public static void checkMessageFactory(ExtendedLogger extendedLogger, MessageFactory messageFactory) {
        String string = extendedLogger.getName();
        Object MF = extendedLogger.getMessageFactory();
        if (messageFactory != null && !MF.equals(messageFactory)) {
            StatusLogger.getLogger().warn("The Logger {} was created with the message factory {} and is now requested with the message factory {}, which may create log events with unexpected formatting.", (Object)string, MF, (Object)messageFactory);
        } else if (messageFactory == null && !MF.getClass().equals(DEFAULT_MESSAGE_FACTORY_CLASS)) {
            StatusLogger.getLogger().warn("The Logger {} was created with the message factory {} and is now requested with a null message factory (defaults to {}), which may create log events with unexpected formatting.", (Object)string, MF, (Object)DEFAULT_MESSAGE_FACTORY_CLASS.getName());
        }
    }

    @Override
    public void catching(Level level, Throwable throwable) {
        this.catching(FQCN, level, throwable);
    }

    protected void catching(String string, Level level, Throwable throwable) {
        if (this.isEnabled(level, CATCHING_MARKER, (Object)null, null)) {
            this.logMessageSafely(string, level, CATCHING_MARKER, this.catchingMsg(throwable), throwable);
        }
    }

    @Override
    public void catching(Throwable throwable) {
        if (this.isEnabled(Level.ERROR, CATCHING_MARKER, (Object)null, null)) {
            this.logMessageSafely(FQCN, Level.ERROR, CATCHING_MARKER, this.catchingMsg(throwable), throwable);
        }
    }

    protected Message catchingMsg(Throwable throwable) {
        return this.messageFactory.newMessage(CATCHING);
    }

    private static Class<? extends MessageFactory> createClassForProperty(String string, Class<ReusableMessageFactory> clazz, Class<ParameterizedMessageFactory> clazz2) {
        try {
            String string2 = Constants.ENABLE_THREADLOCALS ? clazz.getName() : clazz2.getName();
            String string3 = PropertiesUtil.getProperties().getStringProperty(string, string2);
            return LoaderUtil.loadClass(string3).asSubclass(MessageFactory.class);
        } catch (Throwable throwable) {
            return clazz2;
        }
    }

    private static Class<? extends FlowMessageFactory> createFlowClassForProperty(String string, Class<DefaultFlowMessageFactory> clazz) {
        try {
            String string2 = PropertiesUtil.getProperties().getStringProperty(string, clazz.getName());
            return LoaderUtil.loadClass(string2).asSubclass(FlowMessageFactory.class);
        } catch (Throwable throwable) {
            return clazz;
        }
    }

    private static MessageFactory2 createDefaultMessageFactory() {
        try {
            MessageFactory messageFactory = DEFAULT_MESSAGE_FACTORY_CLASS.newInstance();
            return AbstractLogger.narrow(messageFactory);
        } catch (IllegalAccessException | InstantiationException reflectiveOperationException) {
            throw new IllegalStateException(reflectiveOperationException);
        }
    }

    private static MessageFactory2 narrow(MessageFactory messageFactory) {
        if (messageFactory instanceof MessageFactory2) {
            return (MessageFactory2)messageFactory;
        }
        return new MessageFactory2Adapter(messageFactory);
    }

    private static FlowMessageFactory createDefaultFlowMessageFactory() {
        try {
            return DEFAULT_FLOW_MESSAGE_FACTORY_CLASS.newInstance();
        } catch (IllegalAccessException | InstantiationException reflectiveOperationException) {
            throw new IllegalStateException(reflectiveOperationException);
        }
    }

    @Override
    public void debug(Marker marker, CharSequence charSequence) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, charSequence, null);
    }

    @Override
    public void debug(Marker marker, CharSequence charSequence, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, charSequence, throwable);
    }

    @Override
    public void debug(Marker marker, Message message) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void debug(Marker marker, Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, message, throwable);
    }

    @Override
    public void debug(Marker marker, Object object) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, object, null);
    }

    @Override
    public void debug(Marker marker, Object object, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, object, throwable);
    }

    @Override
    public void debug(Marker marker, String string) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, (Throwable)null);
    }

    @Override
    public void debug(Marker marker, String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, objectArray);
    }

    @Override
    public void debug(Marker marker, String string, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, throwable);
    }

    @Override
    public void debug(Message message) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void debug(Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, message, throwable);
    }

    @Override
    public void debug(CharSequence charSequence) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, charSequence, null);
    }

    @Override
    public void debug(CharSequence charSequence, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, charSequence, throwable);
    }

    @Override
    public void debug(Object object) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, object, null);
    }

    @Override
    public void debug(Object object, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, object, throwable);
    }

    @Override
    public void debug(String string) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, (Throwable)null);
    }

    @Override
    public void debug(String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, objectArray);
    }

    @Override
    public void debug(String string, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, throwable);
    }

    @Override
    public void debug(Supplier<?> supplier) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, supplier, (Throwable)null);
    }

    @Override
    public void debug(Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, supplier, throwable);
    }

    @Override
    public void debug(Marker marker, Supplier<?> supplier) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, supplier, (Throwable)null);
    }

    @Override
    public void debug(Marker marker, String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, supplierArray);
    }

    @Override
    public void debug(Marker marker, Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, supplier, throwable);
    }

    @Override
    public void debug(String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, supplierArray);
    }

    @Override
    public void debug(Marker marker, MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, messageSupplier, (Throwable)null);
    }

    @Override
    public void debug(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, messageSupplier, throwable);
    }

    @Override
    public void debug(MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, messageSupplier, (Throwable)null);
    }

    @Override
    public void debug(MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.DEBUG, null, messageSupplier, throwable);
    }

    @Override
    public void debug(Marker marker, String string, Object object) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, object);
    }

    @Override
    public void debug(Marker marker, String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, object, object2);
    }

    @Override
    public void debug(Marker marker, String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, object, object2, object3);
    }

    @Override
    public void debug(Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, object, object2, object3, object4);
    }

    @Override
    public void debug(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, object, object2, object3, object4, object5);
    }

    @Override
    public void debug(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void debug(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void debug(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void debug(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void debug(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, Level.DEBUG, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    @Override
    public void debug(String string, Object object) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, object);
    }

    @Override
    public void debug(String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, object, object2);
    }

    @Override
    public void debug(String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, object, object2, object3);
    }

    @Override
    public void debug(String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, object, object2, object3, object4);
    }

    @Override
    public void debug(String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, object, object2, object3, object4, object5);
    }

    @Override
    public void debug(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void debug(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void debug(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void debug(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void debug(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, Level.DEBUG, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    protected EntryMessage enter(String string, String string2, Supplier<?> ... supplierArray) {
        EntryMessage entryMessage = null;
        if (this.isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, null)) {
            entryMessage = this.entryMsg(string2, supplierArray);
            this.logMessageSafely(string, Level.TRACE, ENTRY_MARKER, entryMessage, null);
        }
        return entryMessage;
    }

    @Deprecated
    protected EntryMessage enter(String string, String string2, MessageSupplier ... messageSupplierArray) {
        EntryMessage entryMessage = null;
        if (this.isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, null)) {
            entryMessage = this.entryMsg(string2, messageSupplierArray);
            this.logMessageSafely(string, Level.TRACE, ENTRY_MARKER, entryMessage, null);
        }
        return entryMessage;
    }

    protected EntryMessage enter(String string, String string2, Object ... objectArray) {
        EntryMessage entryMessage = null;
        if (this.isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, null)) {
            entryMessage = this.entryMsg(string2, objectArray);
            this.logMessageSafely(string, Level.TRACE, ENTRY_MARKER, entryMessage, null);
        }
        return entryMessage;
    }

    @Deprecated
    protected EntryMessage enter(String string, MessageSupplier messageSupplier) {
        EntryMessage entryMessage = null;
        if (this.isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, null)) {
            entryMessage = this.flowMessageFactory.newEntryMessage(messageSupplier.get());
            this.logMessageSafely(string, Level.TRACE, ENTRY_MARKER, entryMessage, null);
        }
        return entryMessage;
    }

    protected EntryMessage enter(String string, Message message) {
        EntryMessage entryMessage = null;
        if (this.isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, null)) {
            entryMessage = this.flowMessageFactory.newEntryMessage(message);
            this.logMessageSafely(string, Level.TRACE, ENTRY_MARKER, entryMessage, null);
        }
        return entryMessage;
    }

    @Override
    public void entry() {
        this.entry(FQCN, (Object[])null);
    }

    @Override
    public void entry(Object ... objectArray) {
        this.entry(FQCN, objectArray);
    }

    protected void entry(String string, Object ... objectArray) {
        if (this.isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, null)) {
            if (objectArray == null) {
                this.logMessageSafely(string, Level.TRACE, ENTRY_MARKER, this.entryMsg((String)null, (Supplier[])null), null);
            } else {
                this.logMessageSafely(string, Level.TRACE, ENTRY_MARKER, this.entryMsg(null, objectArray), null);
            }
        }
    }

    protected EntryMessage entryMsg(String string, Object ... objectArray) {
        int n;
        int n2 = n = objectArray == null ? 0 : objectArray.length;
        if (n == 0) {
            if (Strings.isEmpty(string)) {
                return this.flowMessageFactory.newEntryMessage(null);
            }
            return this.flowMessageFactory.newEntryMessage(new SimpleMessage(string));
        }
        if (string != null) {
            return this.flowMessageFactory.newEntryMessage(new ParameterizedMessage(string, objectArray));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("params(");
        for (int i = 0; i < n; ++i) {
            Object object;
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append((object = objectArray[i]) instanceof Message ? ((Message)object).getFormattedMessage() : String.valueOf(object));
        }
        stringBuilder.append(')');
        return this.flowMessageFactory.newEntryMessage(new SimpleMessage(stringBuilder));
    }

    protected EntryMessage entryMsg(String string, MessageSupplier ... messageSupplierArray) {
        int n = messageSupplierArray == null ? 0 : messageSupplierArray.length;
        Object[] objectArray = new Object[n];
        for (int i = 0; i < n; ++i) {
            objectArray[i] = messageSupplierArray[i].get();
            objectArray[i] = objectArray[i] != null ? ((Message)objectArray[i]).getFormattedMessage() : null;
        }
        return this.entryMsg(string, objectArray);
    }

    protected EntryMessage entryMsg(String string, Supplier<?> ... supplierArray) {
        int n = supplierArray == null ? 0 : supplierArray.length;
        Object[] objectArray = new Object[n];
        for (int i = 0; i < n; ++i) {
            objectArray[i] = supplierArray[i].get();
            if (!(objectArray[i] instanceof Message)) continue;
            objectArray[i] = ((Message)objectArray[i]).getFormattedMessage();
        }
        return this.entryMsg(string, objectArray);
    }

    @Override
    public void error(Marker marker, Message message) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void error(Marker marker, Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, message, throwable);
    }

    @Override
    public void error(Marker marker, CharSequence charSequence) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, charSequence, null);
    }

    @Override
    public void error(Marker marker, CharSequence charSequence, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, charSequence, throwable);
    }

    @Override
    public void error(Marker marker, Object object) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, object, null);
    }

    @Override
    public void error(Marker marker, Object object, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, object, throwable);
    }

    @Override
    public void error(Marker marker, String string) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, (Throwable)null);
    }

    @Override
    public void error(Marker marker, String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, objectArray);
    }

    @Override
    public void error(Marker marker, String string, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, throwable);
    }

    @Override
    public void error(Message message) {
        this.logIfEnabled(FQCN, Level.ERROR, null, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void error(Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.ERROR, null, message, throwable);
    }

    @Override
    public void error(CharSequence charSequence) {
        this.logIfEnabled(FQCN, Level.ERROR, null, charSequence, null);
    }

    @Override
    public void error(CharSequence charSequence, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.ERROR, null, charSequence, throwable);
    }

    @Override
    public void error(Object object) {
        this.logIfEnabled(FQCN, Level.ERROR, null, object, null);
    }

    @Override
    public void error(Object object, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.ERROR, null, object, throwable);
    }

    @Override
    public void error(String string) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, (Throwable)null);
    }

    @Override
    public void error(String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, objectArray);
    }

    @Override
    public void error(String string, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, throwable);
    }

    @Override
    public void error(Supplier<?> supplier) {
        this.logIfEnabled(FQCN, Level.ERROR, null, supplier, (Throwable)null);
    }

    @Override
    public void error(Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.ERROR, null, supplier, throwable);
    }

    @Override
    public void error(Marker marker, Supplier<?> supplier) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, supplier, (Throwable)null);
    }

    @Override
    public void error(Marker marker, String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, supplierArray);
    }

    @Override
    public void error(Marker marker, Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, supplier, throwable);
    }

    @Override
    public void error(String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, supplierArray);
    }

    @Override
    public void error(Marker marker, MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, messageSupplier, (Throwable)null);
    }

    @Override
    public void error(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, messageSupplier, throwable);
    }

    @Override
    public void error(MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, Level.ERROR, null, messageSupplier, (Throwable)null);
    }

    @Override
    public void error(MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.ERROR, null, messageSupplier, throwable);
    }

    @Override
    public void error(Marker marker, String string, Object object) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, object);
    }

    @Override
    public void error(Marker marker, String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, object, object2);
    }

    @Override
    public void error(Marker marker, String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, object, object2, object3);
    }

    @Override
    public void error(Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, object, object2, object3, object4);
    }

    @Override
    public void error(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, object, object2, object3, object4, object5);
    }

    @Override
    public void error(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void error(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void error(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void error(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void error(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, Level.ERROR, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    @Override
    public void error(String string, Object object) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, object);
    }

    @Override
    public void error(String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, object, object2);
    }

    @Override
    public void error(String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, object, object2, object3);
    }

    @Override
    public void error(String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, object, object2, object3, object4);
    }

    @Override
    public void error(String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, object, object2, object3, object4, object5);
    }

    @Override
    public void error(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void error(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void error(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void error(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void error(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, Level.ERROR, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    @Override
    public void exit() {
        this.exit(FQCN, null);
    }

    @Override
    public <R> R exit(R r) {
        return this.exit(FQCN, r);
    }

    protected <R> R exit(String string, R r) {
        this.logIfEnabled(string, Level.TRACE, EXIT_MARKER, this.exitMsg(null, r), null);
        return r;
    }

    protected <R> R exit(String string, String string2, R r) {
        this.logIfEnabled(string, Level.TRACE, EXIT_MARKER, this.exitMsg(string2, r), null);
        return r;
    }

    protected Message exitMsg(String string, Object object) {
        if (object == null) {
            if (string == null) {
                return this.messageFactory.newMessage("Exit");
            }
            return this.messageFactory.newMessage("Exit: " + string);
        }
        if (string == null) {
            return this.messageFactory.newMessage("Exit with(" + object + ')');
        }
        return this.messageFactory.newMessage("Exit: " + string, object);
    }

    @Override
    public void fatal(Marker marker, Message message) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void fatal(Marker marker, Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, message, throwable);
    }

    @Override
    public void fatal(Marker marker, CharSequence charSequence) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, charSequence, null);
    }

    @Override
    public void fatal(Marker marker, CharSequence charSequence, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, charSequence, throwable);
    }

    @Override
    public void fatal(Marker marker, Object object) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, object, null);
    }

    @Override
    public void fatal(Marker marker, Object object, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, object, throwable);
    }

    @Override
    public void fatal(Marker marker, String string) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, (Throwable)null);
    }

    @Override
    public void fatal(Marker marker, String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, objectArray);
    }

    @Override
    public void fatal(Marker marker, String string, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, throwable);
    }

    @Override
    public void fatal(Message message) {
        this.logIfEnabled(FQCN, Level.FATAL, null, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void fatal(Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.FATAL, null, message, throwable);
    }

    @Override
    public void fatal(CharSequence charSequence) {
        this.logIfEnabled(FQCN, Level.FATAL, null, charSequence, null);
    }

    @Override
    public void fatal(CharSequence charSequence, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.FATAL, null, charSequence, throwable);
    }

    @Override
    public void fatal(Object object) {
        this.logIfEnabled(FQCN, Level.FATAL, null, object, null);
    }

    @Override
    public void fatal(Object object, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.FATAL, null, object, throwable);
    }

    @Override
    public void fatal(String string) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, (Throwable)null);
    }

    @Override
    public void fatal(String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, objectArray);
    }

    @Override
    public void fatal(String string, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, throwable);
    }

    @Override
    public void fatal(Supplier<?> supplier) {
        this.logIfEnabled(FQCN, Level.FATAL, null, supplier, (Throwable)null);
    }

    @Override
    public void fatal(Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.FATAL, null, supplier, throwable);
    }

    @Override
    public void fatal(Marker marker, Supplier<?> supplier) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, supplier, (Throwable)null);
    }

    @Override
    public void fatal(Marker marker, String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, supplierArray);
    }

    @Override
    public void fatal(Marker marker, Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, supplier, throwable);
    }

    @Override
    public void fatal(String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, supplierArray);
    }

    @Override
    public void fatal(Marker marker, MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, messageSupplier, (Throwable)null);
    }

    @Override
    public void fatal(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, messageSupplier, throwable);
    }

    @Override
    public void fatal(MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, Level.FATAL, null, messageSupplier, (Throwable)null);
    }

    @Override
    public void fatal(MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.FATAL, null, messageSupplier, throwable);
    }

    @Override
    public void fatal(Marker marker, String string, Object object) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, object);
    }

    @Override
    public void fatal(Marker marker, String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, object, object2);
    }

    @Override
    public void fatal(Marker marker, String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, object, object2, object3);
    }

    @Override
    public void fatal(Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, object, object2, object3, object4);
    }

    @Override
    public void fatal(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, object, object2, object3, object4, object5);
    }

    @Override
    public void fatal(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void fatal(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void fatal(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void fatal(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void fatal(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, Level.FATAL, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    @Override
    public void fatal(String string, Object object) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, object);
    }

    @Override
    public void fatal(String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, object, object2);
    }

    @Override
    public void fatal(String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, object, object2, object3);
    }

    @Override
    public void fatal(String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, object, object2, object3, object4);
    }

    @Override
    public void fatal(String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, object, object2, object3, object4, object5);
    }

    @Override
    public void fatal(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void fatal(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void fatal(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void fatal(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void fatal(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, Level.FATAL, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
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
    public void info(Marker marker, Message message) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void info(Marker marker, Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.INFO, marker, message, throwable);
    }

    @Override
    public void info(Marker marker, CharSequence charSequence) {
        this.logIfEnabled(FQCN, Level.INFO, marker, charSequence, null);
    }

    @Override
    public void info(Marker marker, CharSequence charSequence, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.INFO, marker, charSequence, throwable);
    }

    @Override
    public void info(Marker marker, Object object) {
        this.logIfEnabled(FQCN, Level.INFO, marker, object, null);
    }

    @Override
    public void info(Marker marker, Object object, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.INFO, marker, object, throwable);
    }

    @Override
    public void info(Marker marker, String string) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, (Throwable)null);
    }

    @Override
    public void info(Marker marker, String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, objectArray);
    }

    @Override
    public void info(Marker marker, String string, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, throwable);
    }

    @Override
    public void info(Message message) {
        this.logIfEnabled(FQCN, Level.INFO, null, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void info(Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.INFO, null, message, throwable);
    }

    @Override
    public void info(CharSequence charSequence) {
        this.logIfEnabled(FQCN, Level.INFO, null, charSequence, null);
    }

    @Override
    public void info(CharSequence charSequence, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.INFO, null, charSequence, throwable);
    }

    @Override
    public void info(Object object) {
        this.logIfEnabled(FQCN, Level.INFO, null, object, null);
    }

    @Override
    public void info(Object object, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.INFO, null, object, throwable);
    }

    @Override
    public void info(String string) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, (Throwable)null);
    }

    @Override
    public void info(String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, objectArray);
    }

    @Override
    public void info(String string, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, throwable);
    }

    @Override
    public void info(Supplier<?> supplier) {
        this.logIfEnabled(FQCN, Level.INFO, null, supplier, (Throwable)null);
    }

    @Override
    public void info(Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.INFO, null, supplier, throwable);
    }

    @Override
    public void info(Marker marker, Supplier<?> supplier) {
        this.logIfEnabled(FQCN, Level.INFO, marker, supplier, (Throwable)null);
    }

    @Override
    public void info(Marker marker, String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, supplierArray);
    }

    @Override
    public void info(Marker marker, Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.INFO, marker, supplier, throwable);
    }

    @Override
    public void info(String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, supplierArray);
    }

    @Override
    public void info(Marker marker, MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, Level.INFO, marker, messageSupplier, (Throwable)null);
    }

    @Override
    public void info(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.INFO, marker, messageSupplier, throwable);
    }

    @Override
    public void info(MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, Level.INFO, null, messageSupplier, (Throwable)null);
    }

    @Override
    public void info(MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.INFO, null, messageSupplier, throwable);
    }

    @Override
    public void info(Marker marker, String string, Object object) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, object);
    }

    @Override
    public void info(Marker marker, String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, object, object2);
    }

    @Override
    public void info(Marker marker, String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, object, object2, object3);
    }

    @Override
    public void info(Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, object, object2, object3, object4);
    }

    @Override
    public void info(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, object, object2, object3, object4, object5);
    }

    @Override
    public void info(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void info(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void info(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void info(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void info(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, Level.INFO, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    @Override
    public void info(String string, Object object) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, object);
    }

    @Override
    public void info(String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, object, object2);
    }

    @Override
    public void info(String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, object, object2, object3);
    }

    @Override
    public void info(String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, object, object2, object3, object4);
    }

    @Override
    public void info(String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, object, object2, object3, object4, object5);
    }

    @Override
    public void info(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void info(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void info(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void info(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void info(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, Level.INFO, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
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
    public void log(Level level, Marker marker, Message message) {
        this.logIfEnabled(FQCN, level, marker, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void log(Level level, Marker marker, Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, level, marker, message, throwable);
    }

    @Override
    public void log(Level level, Marker marker, CharSequence charSequence) {
        this.logIfEnabled(FQCN, level, marker, charSequence, (Throwable)null);
    }

    @Override
    public void log(Level level, Marker marker, CharSequence charSequence, Throwable throwable) {
        if (this.isEnabled(level, marker, charSequence, throwable)) {
            this.logMessage(FQCN, level, marker, charSequence, throwable);
        }
    }

    @Override
    public void log(Level level, Marker marker, Object object) {
        this.logIfEnabled(FQCN, level, marker, object, (Throwable)null);
    }

    @Override
    public void log(Level level, Marker marker, Object object, Throwable throwable) {
        if (this.isEnabled(level, marker, object, throwable)) {
            this.logMessage(FQCN, level, marker, object, throwable);
        }
    }

    @Override
    public void log(Level level, Marker marker, String string) {
        this.logIfEnabled(FQCN, level, marker, string, (Throwable)null);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, level, marker, string, objectArray);
    }

    @Override
    public void log(Level level, Marker marker, String string, Throwable throwable) {
        this.logIfEnabled(FQCN, level, marker, string, throwable);
    }

    @Override
    public void log(Level level, Message message) {
        this.logIfEnabled(FQCN, level, null, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void log(Level level, Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, level, null, message, throwable);
    }

    @Override
    public void log(Level level, CharSequence charSequence) {
        this.logIfEnabled(FQCN, level, null, charSequence, null);
    }

    @Override
    public void log(Level level, CharSequence charSequence, Throwable throwable) {
        this.logIfEnabled(FQCN, level, null, charSequence, throwable);
    }

    @Override
    public void log(Level level, Object object) {
        this.logIfEnabled(FQCN, level, null, object, null);
    }

    @Override
    public void log(Level level, Object object, Throwable throwable) {
        this.logIfEnabled(FQCN, level, null, object, throwable);
    }

    @Override
    public void log(Level level, String string) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, (Throwable)null);
    }

    @Override
    public void log(Level level, String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, objectArray);
    }

    @Override
    public void log(Level level, String string, Throwable throwable) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, throwable);
    }

    @Override
    public void log(Level level, Supplier<?> supplier) {
        this.logIfEnabled(FQCN, level, null, supplier, (Throwable)null);
    }

    @Override
    public void log(Level level, Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, level, null, supplier, throwable);
    }

    @Override
    public void log(Level level, Marker marker, Supplier<?> supplier) {
        this.logIfEnabled(FQCN, level, marker, supplier, (Throwable)null);
    }

    @Override
    public void log(Level level, Marker marker, String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, level, marker, string, supplierArray);
    }

    @Override
    public void log(Level level, Marker marker, Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, level, marker, supplier, throwable);
    }

    @Override
    public void log(Level level, String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, supplierArray);
    }

    @Override
    public void log(Level level, Marker marker, MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, level, marker, messageSupplier, (Throwable)null);
    }

    @Override
    public void log(Level level, Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, level, marker, messageSupplier, throwable);
    }

    @Override
    public void log(Level level, MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, level, null, messageSupplier, (Throwable)null);
    }

    @Override
    public void log(Level level, MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, level, null, messageSupplier, throwable);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object object) {
        this.logIfEnabled(FQCN, level, marker, string, object);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, level, marker, string, object, object2);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, level, marker, string, object, object2, object3);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, level, marker, string, object, object2, object3, object4);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, level, marker, string, object, object2, object3, object4, object5);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, level, marker, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, level, marker, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, level, marker, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, level, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, level, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    @Override
    public void log(Level level, String string, Object object) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, object);
    }

    @Override
    public void log(Level level, String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, object, object2);
    }

    @Override
    public void log(Level level, String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, object, object2, object3);
    }

    @Override
    public void log(Level level, String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, object, object2, object3, object4);
    }

    @Override
    public void log(Level level, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, object, object2, object3, object4, object5);
    }

    @Override
    public void log(Level level, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void log(Level level, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void log(Level level, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void log(Level level, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void log(Level level, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, level, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, Message message, Throwable throwable) {
        if (this.isEnabled(level, marker, message, throwable)) {
            this.logMessageSafely(string, level, marker, message, throwable);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
        if (this.isEnabled(level, marker, messageSupplier, throwable)) {
            this.logMessage(string, level, marker, messageSupplier, throwable);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, Object object, Throwable throwable) {
        if (this.isEnabled(level, marker, object, throwable)) {
            this.logMessage(string, level, marker, object, throwable);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, CharSequence charSequence, Throwable throwable) {
        if (this.isEnabled(level, marker, charSequence, throwable)) {
            this.logMessage(string, level, marker, charSequence, throwable);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, Supplier<?> supplier, Throwable throwable) {
        if (this.isEnabled(level, marker, supplier, throwable)) {
            this.logMessage(string, level, marker, supplier, throwable);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2) {
        if (this.isEnabled(level, marker, string2)) {
            this.logMessage(string, level, marker, string2);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2, Supplier<?> ... supplierArray) {
        if (this.isEnabled(level, marker, string2)) {
            this.logMessage(string, level, marker, string2, supplierArray);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2, Object ... objectArray) {
        if (this.isEnabled(level, marker, string2, objectArray)) {
            this.logMessage(string, level, marker, string2, objectArray);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2, Object object) {
        if (this.isEnabled(level, marker, string2, object)) {
            this.logMessage(string, level, marker, string2, object);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2, Object object, Object object2) {
        if (this.isEnabled(level, marker, string2, object, object2)) {
            this.logMessage(string, level, marker, string2, object, object2);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3) {
        if (this.isEnabled(level, marker, string2, object, object2, object3)) {
            this.logMessage(string, level, marker, string2, object, object2, object3);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4) {
        if (this.isEnabled(level, marker, string2, object, object2, object3, object4)) {
            this.logMessage(string, level, marker, string2, object, object2, object3, object4);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4, Object object5) {
        if (this.isEnabled(level, marker, string2, object, object2, object3, object4, object5)) {
            this.logMessage(string, level, marker, string2, object, object2, object3, object4, object5);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        if (this.isEnabled(level, marker, string2, object, object2, object3, object4, object5, object6)) {
            this.logMessage(string, level, marker, string2, object, object2, object3, object4, object5, object6);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        if (this.isEnabled(level, marker, string2, object, object2, object3, object4, object5, object6, object7)) {
            this.logMessage(string, level, marker, string2, object, object2, object3, object4, object5, object6, object7);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        if (this.isEnabled(level, marker, string2, object, object2, object3, object4, object5, object6, object7, object8)) {
            this.logMessage(string, level, marker, string2, object, object2, object3, object4, object5, object6, object7, object8);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        if (this.isEnabled(level, marker, string2, object, object2, object3, object4, object5, object6, object7, object8, object9)) {
            this.logMessage(string, level, marker, string2, object, object2, object3, object4, object5, object6, object7, object8, object9);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        if (this.isEnabled(level, marker, string2, object, object2, object3, object4, object5, object6, object7, object8, object9, object10)) {
            this.logMessage(string, level, marker, string2, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
        }
    }

    @Override
    public void logIfEnabled(String string, Level level, Marker marker, String string2, Throwable throwable) {
        if (this.isEnabled(level, marker, string2, throwable)) {
            this.logMessage(string, level, marker, string2, throwable);
        }
    }

    protected void logMessage(String string, Level level, Marker marker, CharSequence charSequence, Throwable throwable) {
        this.logMessageSafely(string, level, marker, this.messageFactory.newMessage(charSequence), throwable);
    }

    protected void logMessage(String string, Level level, Marker marker, Object object, Throwable throwable) {
        this.logMessageSafely(string, level, marker, this.messageFactory.newMessage(object), throwable);
    }

    protected void logMessage(String string, Level level, Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
        Message message = LambdaUtil.get(messageSupplier);
        this.logMessageSafely(string, level, marker, message, throwable == null && message != null ? message.getThrowable() : throwable);
    }

    protected void logMessage(String string, Level level, Marker marker, Supplier<?> supplier, Throwable throwable) {
        Message message = LambdaUtil.getMessage(supplier, this.messageFactory);
        this.logMessageSafely(string, level, marker, message, throwable == null && message != null ? message.getThrowable() : throwable);
    }

    protected void logMessage(String string, Level level, Marker marker, String string2, Throwable throwable) {
        this.logMessageSafely(string, level, marker, this.messageFactory.newMessage(string2), throwable);
    }

    protected void logMessage(String string, Level level, Marker marker, String string2) {
        Message message = this.messageFactory.newMessage(string2);
        this.logMessageSafely(string, level, marker, message, message.getThrowable());
    }

    protected void logMessage(String string, Level level, Marker marker, String string2, Object ... objectArray) {
        Message message = this.messageFactory.newMessage(string2, objectArray);
        this.logMessageSafely(string, level, marker, message, message.getThrowable());
    }

    protected void logMessage(String string, Level level, Marker marker, String string2, Object object) {
        Message message = this.messageFactory.newMessage(string2, object);
        this.logMessageSafely(string, level, marker, message, message.getThrowable());
    }

    protected void logMessage(String string, Level level, Marker marker, String string2, Object object, Object object2) {
        Message message = this.messageFactory.newMessage(string2, object, object2);
        this.logMessageSafely(string, level, marker, message, message.getThrowable());
    }

    protected void logMessage(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3) {
        Message message = this.messageFactory.newMessage(string2, object, object2, object3);
        this.logMessageSafely(string, level, marker, message, message.getThrowable());
    }

    protected void logMessage(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4) {
        Message message = this.messageFactory.newMessage(string2, object, object2, object3, object4);
        this.logMessageSafely(string, level, marker, message, message.getThrowable());
    }

    protected void logMessage(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4, Object object5) {
        Message message = this.messageFactory.newMessage(string2, object, object2, object3, object4, object5);
        this.logMessageSafely(string, level, marker, message, message.getThrowable());
    }

    protected void logMessage(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        Message message = this.messageFactory.newMessage(string2, object, object2, object3, object4, object5, object6);
        this.logMessageSafely(string, level, marker, message, message.getThrowable());
    }

    protected void logMessage(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        Message message = this.messageFactory.newMessage(string2, object, object2, object3, object4, object5, object6, object7);
        this.logMessageSafely(string, level, marker, message, message.getThrowable());
    }

    protected void logMessage(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        Message message = this.messageFactory.newMessage(string2, object, object2, object3, object4, object5, object6, object7, object8);
        this.logMessageSafely(string, level, marker, message, message.getThrowable());
    }

    protected void logMessage(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        Message message = this.messageFactory.newMessage(string2, object, object2, object3, object4, object5, object6, object7, object8, object9);
        this.logMessageSafely(string, level, marker, message, message.getThrowable());
    }

    protected void logMessage(String string, Level level, Marker marker, String string2, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        Message message = this.messageFactory.newMessage(string2, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
        this.logMessageSafely(string, level, marker, message, message.getThrowable());
    }

    protected void logMessage(String string, Level level, Marker marker, String string2, Supplier<?> ... supplierArray) {
        Message message = this.messageFactory.newMessage(string2, LambdaUtil.getAll(supplierArray));
        this.logMessageSafely(string, level, marker, message, message.getThrowable());
    }

    @Override
    public void printf(Level level, Marker marker, String string, Object ... objectArray) {
        if (this.isEnabled(level, marker, string, objectArray)) {
            StringFormattedMessage stringFormattedMessage = new StringFormattedMessage(string, objectArray);
            this.logMessageSafely(FQCN, level, marker, stringFormattedMessage, stringFormattedMessage.getThrowable());
        }
    }

    @Override
    public void printf(Level level, String string, Object ... objectArray) {
        if (this.isEnabled(level, (Marker)null, string, objectArray)) {
            StringFormattedMessage stringFormattedMessage = new StringFormattedMessage(string, objectArray);
            this.logMessageSafely(FQCN, level, null, stringFormattedMessage, stringFormattedMessage.getThrowable());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void logMessageSafely(String string, Level level, Marker marker, Message message, Throwable throwable) {
        try {
            this.logMessage(string, level, marker, message, throwable);
        } finally {
            ReusableMessageFactory.release(message);
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

    protected <T extends Throwable> T throwing(String string, Level level, T t) {
        if (this.isEnabled(level, THROWING_MARKER, (Object)null, null)) {
            this.logMessageSafely(string, level, THROWING_MARKER, this.throwingMsg(t), t);
        }
        return t;
    }

    protected Message throwingMsg(Throwable throwable) {
        return this.messageFactory.newMessage(THROWING);
    }

    @Override
    public void trace(Marker marker, Message message) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void trace(Marker marker, Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, message, throwable);
    }

    @Override
    public void trace(Marker marker, CharSequence charSequence) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, charSequence, null);
    }

    @Override
    public void trace(Marker marker, CharSequence charSequence, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, charSequence, throwable);
    }

    @Override
    public void trace(Marker marker, Object object) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, object, null);
    }

    @Override
    public void trace(Marker marker, Object object, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, object, throwable);
    }

    @Override
    public void trace(Marker marker, String string) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, (Throwable)null);
    }

    @Override
    public void trace(Marker marker, String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, objectArray);
    }

    @Override
    public void trace(Marker marker, String string, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, throwable);
    }

    @Override
    public void trace(Message message) {
        this.logIfEnabled(FQCN, Level.TRACE, null, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void trace(Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.TRACE, null, message, throwable);
    }

    @Override
    public void trace(CharSequence charSequence) {
        this.logIfEnabled(FQCN, Level.TRACE, null, charSequence, null);
    }

    @Override
    public void trace(CharSequence charSequence, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.TRACE, null, charSequence, throwable);
    }

    @Override
    public void trace(Object object) {
        this.logIfEnabled(FQCN, Level.TRACE, null, object, null);
    }

    @Override
    public void trace(Object object, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.TRACE, null, object, throwable);
    }

    @Override
    public void trace(String string) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, (Throwable)null);
    }

    @Override
    public void trace(String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, objectArray);
    }

    @Override
    public void trace(String string, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, throwable);
    }

    @Override
    public void trace(Supplier<?> supplier) {
        this.logIfEnabled(FQCN, Level.TRACE, null, supplier, (Throwable)null);
    }

    @Override
    public void trace(Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.TRACE, null, supplier, throwable);
    }

    @Override
    public void trace(Marker marker, Supplier<?> supplier) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, supplier, (Throwable)null);
    }

    @Override
    public void trace(Marker marker, String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, supplierArray);
    }

    @Override
    public void trace(Marker marker, Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, supplier, throwable);
    }

    @Override
    public void trace(String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, supplierArray);
    }

    @Override
    public void trace(Marker marker, MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, messageSupplier, (Throwable)null);
    }

    @Override
    public void trace(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, messageSupplier, throwable);
    }

    @Override
    public void trace(MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, Level.TRACE, null, messageSupplier, (Throwable)null);
    }

    @Override
    public void trace(MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.TRACE, null, messageSupplier, throwable);
    }

    @Override
    public void trace(Marker marker, String string, Object object) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, object);
    }

    @Override
    public void trace(Marker marker, String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, object, object2);
    }

    @Override
    public void trace(Marker marker, String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, object, object2, object3);
    }

    @Override
    public void trace(Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, object, object2, object3, object4);
    }

    @Override
    public void trace(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, object, object2, object3, object4, object5);
    }

    @Override
    public void trace(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void trace(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void trace(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void trace(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void trace(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, Level.TRACE, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    @Override
    public void trace(String string, Object object) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, object);
    }

    @Override
    public void trace(String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, object, object2);
    }

    @Override
    public void trace(String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, object, object2, object3);
    }

    @Override
    public void trace(String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, object, object2, object3, object4);
    }

    @Override
    public void trace(String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, object, object2, object3, object4, object5);
    }

    @Override
    public void trace(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void trace(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void trace(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void trace(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void trace(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, Level.TRACE, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    @Override
    public EntryMessage traceEntry() {
        return this.enter(FQCN, null, (Object[])null);
    }

    @Override
    public EntryMessage traceEntry(String string, Object ... objectArray) {
        return this.enter(FQCN, string, objectArray);
    }

    @Override
    public EntryMessage traceEntry(Supplier<?> ... supplierArray) {
        return this.enter(FQCN, (String)null, supplierArray);
    }

    @Override
    public EntryMessage traceEntry(String string, Supplier<?> ... supplierArray) {
        return this.enter(FQCN, string, supplierArray);
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
    public <R> R traceExit(R r) {
        return this.exit(FQCN, null, r);
    }

    @Override
    public <R> R traceExit(String string, R r) {
        return this.exit(FQCN, string, r);
    }

    @Override
    public void traceExit(EntryMessage entryMessage) {
        if (entryMessage != null && this.isEnabled(Level.TRACE, EXIT_MARKER, entryMessage, null)) {
            this.logMessageSafely(FQCN, Level.TRACE, EXIT_MARKER, this.flowMessageFactory.newExitMessage(entryMessage), null);
        }
    }

    @Override
    public <R> R traceExit(EntryMessage entryMessage, R r) {
        if (entryMessage != null && this.isEnabled(Level.TRACE, EXIT_MARKER, entryMessage, null)) {
            this.logMessageSafely(FQCN, Level.TRACE, EXIT_MARKER, this.flowMessageFactory.newExitMessage(r, entryMessage), null);
        }
        return r;
    }

    @Override
    public <R> R traceExit(Message message, R r) {
        if (message != null && this.isEnabled(Level.TRACE, EXIT_MARKER, message, null)) {
            this.logMessageSafely(FQCN, Level.TRACE, EXIT_MARKER, this.flowMessageFactory.newExitMessage(r, message), null);
        }
        return r;
    }

    @Override
    public void warn(Marker marker, Message message) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void warn(Marker marker, Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.WARN, marker, message, throwable);
    }

    @Override
    public void warn(Marker marker, CharSequence charSequence) {
        this.logIfEnabled(FQCN, Level.WARN, marker, charSequence, null);
    }

    @Override
    public void warn(Marker marker, CharSequence charSequence, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.WARN, marker, charSequence, throwable);
    }

    @Override
    public void warn(Marker marker, Object object) {
        this.logIfEnabled(FQCN, Level.WARN, marker, object, null);
    }

    @Override
    public void warn(Marker marker, Object object, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.WARN, marker, object, throwable);
    }

    @Override
    public void warn(Marker marker, String string) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, (Throwable)null);
    }

    @Override
    public void warn(Marker marker, String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, objectArray);
    }

    @Override
    public void warn(Marker marker, String string, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, throwable);
    }

    @Override
    public void warn(Message message) {
        this.logIfEnabled(FQCN, Level.WARN, null, message, message != null ? message.getThrowable() : null);
    }

    @Override
    public void warn(Message message, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.WARN, null, message, throwable);
    }

    @Override
    public void warn(CharSequence charSequence) {
        this.logIfEnabled(FQCN, Level.WARN, null, charSequence, null);
    }

    @Override
    public void warn(CharSequence charSequence, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.WARN, null, charSequence, throwable);
    }

    @Override
    public void warn(Object object) {
        this.logIfEnabled(FQCN, Level.WARN, null, object, null);
    }

    @Override
    public void warn(Object object, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.WARN, null, object, throwable);
    }

    @Override
    public void warn(String string) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, (Throwable)null);
    }

    @Override
    public void warn(String string, Object ... objectArray) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, objectArray);
    }

    @Override
    public void warn(String string, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, throwable);
    }

    @Override
    public void warn(Supplier<?> supplier) {
        this.logIfEnabled(FQCN, Level.WARN, null, supplier, (Throwable)null);
    }

    @Override
    public void warn(Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.WARN, null, supplier, throwable);
    }

    @Override
    public void warn(Marker marker, Supplier<?> supplier) {
        this.logIfEnabled(FQCN, Level.WARN, marker, supplier, (Throwable)null);
    }

    @Override
    public void warn(Marker marker, String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, supplierArray);
    }

    @Override
    public void warn(Marker marker, Supplier<?> supplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.WARN, marker, supplier, throwable);
    }

    @Override
    public void warn(String string, Supplier<?> ... supplierArray) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, supplierArray);
    }

    @Override
    public void warn(Marker marker, MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, Level.WARN, marker, messageSupplier, (Throwable)null);
    }

    @Override
    public void warn(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.WARN, marker, messageSupplier, throwable);
    }

    @Override
    public void warn(MessageSupplier messageSupplier) {
        this.logIfEnabled(FQCN, Level.WARN, null, messageSupplier, (Throwable)null);
    }

    @Override
    public void warn(MessageSupplier messageSupplier, Throwable throwable) {
        this.logIfEnabled(FQCN, Level.WARN, null, messageSupplier, throwable);
    }

    @Override
    public void warn(Marker marker, String string, Object object) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, object);
    }

    @Override
    public void warn(Marker marker, String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, object, object2);
    }

    @Override
    public void warn(Marker marker, String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, object, object2, object3);
    }

    @Override
    public void warn(Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, object, object2, object3, object4);
    }

    @Override
    public void warn(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, object, object2, object3, object4, object5);
    }

    @Override
    public void warn(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void warn(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void warn(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void warn(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void warn(Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, Level.WARN, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    @Override
    public void warn(String string, Object object) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, object);
    }

    @Override
    public void warn(String string, Object object, Object object2) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, object, object2);
    }

    @Override
    public void warn(String string, Object object, Object object2, Object object3) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, object, object2, object3);
    }

    @Override
    public void warn(String string, Object object, Object object2, Object object3, Object object4) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, object, object2, object3, object4);
    }

    @Override
    public void warn(String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, object, object2, object3, object4, object5);
    }

    @Override
    public void warn(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public void warn(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public void warn(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public void warn(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public void warn(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        this.logIfEnabled(FQCN, Level.WARN, (Marker)null, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }
}

