/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.mom.jeromq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.mom.jeromq.JeroMqManager;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.util.Strings;

@Plugin(name="JeroMQ", category="Core", elementType="appender", printObject=true)
public final class JeroMqAppender
extends AbstractAppender {
    private static final int DEFAULT_BACKLOG = 100;
    private static final int DEFAULT_IVL = 100;
    private static final int DEFAULT_RCV_HWM = 1000;
    private static final int DEFAULT_SND_HWM = 1000;
    private final JeroMqManager manager;
    private final List<String> endpoints;
    private int sendRcFalse;
    private int sendRcTrue;

    private JeroMqAppender(String string, Filter filter, Layout<? extends Serializable> layout, boolean bl, List<String> list, long l, long l2, boolean bl2, byte[] byArray, boolean bl3, long l3, long l4, long l5, long l6, int n, long l7, long l8, long l9, int n2, long l10, int n3, long l11, long l12, long l13, boolean bl4) {
        super(string, filter, layout, bl);
        this.manager = JeroMqManager.getJeroMqManager(string, l, l2, bl2, byArray, bl3, l3, l4, l5, l6, n, l7, l8, l9, n2, l10, n3, l11, l12, l13, bl4, list);
        this.endpoints = list;
    }

    @PluginFactory
    public static JeroMqAppender createAppender(@Required(message="No name provided for JeroMqAppender") @PluginAttribute(value="name") String string, @PluginElement(value="Layout") Layout<?> patternLayout, @PluginElement(value="Filter") Filter filter, @PluginElement(value="Properties") Property[] propertyArray, @PluginAttribute(value="ignoreExceptions") boolean bl, @PluginAttribute(value="affinity", defaultLong=0L) long l, @PluginAttribute(value="backlog", defaultLong=100L) long l2, @PluginAttribute(value="delayAttachOnConnect") boolean bl2, @PluginAttribute(value="identity") byte[] byArray, @PluginAttribute(value="ipv4Only", defaultBoolean=true) boolean bl3, @PluginAttribute(value="linger", defaultLong=-1L) long l3, @PluginAttribute(value="maxMsgSize", defaultLong=-1L) long l4, @PluginAttribute(value="rcvHwm", defaultLong=1000L) long l5, @PluginAttribute(value="receiveBufferSize", defaultLong=0L) long l6, @PluginAttribute(value="receiveTimeOut", defaultLong=-1L) int n, @PluginAttribute(value="reconnectIVL", defaultLong=100L) long l7, @PluginAttribute(value="reconnectIVLMax", defaultLong=0L) long l8, @PluginAttribute(value="sendBufferSize", defaultLong=0L) long l9, @PluginAttribute(value="sendTimeOut", defaultLong=-1L) int n2, @PluginAttribute(value="sndHwm", defaultLong=1000L) long l10, @PluginAttribute(value="tcpKeepAlive", defaultInt=-1) int n3, @PluginAttribute(value="tcpKeepAliveCount", defaultLong=-1L) long l11, @PluginAttribute(value="tcpKeepAliveIdle", defaultLong=-1L) long l12, @PluginAttribute(value="tcpKeepAliveInterval", defaultLong=-1L) long l13, @PluginAttribute(value="xpubVerbose") boolean bl4) {
        ArrayList<String> arrayList;
        if (patternLayout == null) {
            patternLayout = PatternLayout.createDefaultLayout();
        }
        if (propertyArray == null) {
            arrayList = new ArrayList<String>(0);
        } else {
            arrayList = new ArrayList(propertyArray.length);
            for (Property property : propertyArray) {
                String string2;
                if (!"endpoint".equalsIgnoreCase(property.getName()) || !Strings.isNotEmpty(string2 = property.getValue())) continue;
                arrayList.add(string2);
            }
        }
        LOGGER.debug("Creating JeroMqAppender with name={}, filter={}, layout={}, ignoreExceptions={}, endpoints={}", (Object)string, (Object)filter, (Object)patternLayout, (Object)bl, (Object)arrayList);
        return new JeroMqAppender(string, filter, patternLayout, bl, arrayList, l, l2, bl2, byArray, bl3, l3, l4, l5, l6, n, l7, l8, l9, n2, l10, n3, l11, l12, l13, bl4);
    }

    @Override
    public synchronized void append(LogEvent logEvent) {
        Layout<? extends Serializable> layout = this.getLayout();
        byte[] byArray = layout.toByteArray(logEvent);
        if (this.manager.send(this.getLayout().toByteArray(logEvent))) {
            ++this.sendRcTrue;
        } else {
            ++this.sendRcFalse;
            LOGGER.error("Appender {} could not send message {} to JeroMQ {}", (Object)this.getName(), (Object)this.sendRcFalse, (Object)byArray);
        }
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        boolean bl = super.stop(l, timeUnit, false);
        this.setStopped();
        return bl &= this.manager.stop(l, timeUnit);
    }

    int getSendRcFalse() {
        return this.sendRcFalse;
    }

    int getSendRcTrue() {
        return this.sendRcTrue;
    }

    void resetSendRcs() {
        this.sendRcFalse = 0;
        this.sendRcTrue = 0;
    }

    @Override
    public String toString() {
        return "JeroMqAppender{name=" + this.getName() + ", state=" + (Object)((Object)this.getState()) + ", manager=" + this.manager + ", endpoints=" + this.endpoints + '}';
    }
}

