/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.nio.charset.Charset;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.impl.MutableLogEvent;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.util.StringBuilderWriter;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
abstract class AbstractJacksonLayout
extends AbstractStringLayout {
    protected static final String DEFAULT_EOL = "\r\n";
    protected static final String COMPACT_EOL = "";
    protected final String eol;
    protected final ObjectWriter objectWriter;
    protected final boolean compact;
    protected final boolean complete;

    protected AbstractJacksonLayout(Configuration configuration, ObjectWriter objectWriter, Charset charset, boolean bl, boolean bl2, boolean bl3, AbstractStringLayout.Serializer serializer, AbstractStringLayout.Serializer serializer2) {
        super(configuration, charset, serializer, serializer2);
        this.objectWriter = objectWriter;
        this.compact = bl;
        this.complete = bl2;
        this.eol = bl && !bl3 ? COMPACT_EOL : DEFAULT_EOL;
    }

    @Override
    public String toSerializable(LogEvent logEvent) {
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        try {
            this.toSerializable(logEvent, stringBuilderWriter);
            return stringBuilderWriter.toString();
        } catch (IOException iOException) {
            LOGGER.error(iOException);
            return COMPACT_EOL;
        }
    }

    private static LogEvent convertMutableToLog4jEvent(LogEvent logEvent) {
        return logEvent instanceof MutableLogEvent ? ((MutableLogEvent)logEvent).createMemento() : logEvent;
    }

    public void toSerializable(LogEvent logEvent, Writer writer) throws JsonGenerationException, JsonMappingException, IOException {
        this.objectWriter.writeValue(writer, (Object)AbstractJacksonLayout.convertMutableToLog4jEvent(logEvent));
        writer.write(this.eol);
        this.markEvent();
    }

    @Override
    public Serializable toSerializable(LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }

    public static abstract class Builder<B extends Builder<B>>
    extends AbstractStringLayout.Builder<B> {
        @PluginBuilderAttribute
        private boolean eventEol;
        @PluginBuilderAttribute
        private boolean compact;
        @PluginBuilderAttribute
        private boolean complete;

        public boolean getEventEol() {
            return this.eventEol;
        }

        public boolean isCompact() {
            return this.compact;
        }

        public boolean isComplete() {
            return this.complete;
        }

        public B setEventEol(boolean bl) {
            this.eventEol = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setCompact(boolean bl) {
            this.compact = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setComplete(boolean bl) {
            this.complete = bl;
            return (B)((Builder)this.asBuilder());
        }
    }
}

