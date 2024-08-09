/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.dataformat.xml.util.DefaultXmlPrettyPrinter
 *  org.codehaus.stax2.XMLStreamWriter2
 */
package org.apache.logging.log4j.core.layout;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.xml.util.DefaultXmlPrettyPrinter;
import java.util.HashSet;
import javax.xml.stream.XMLStreamException;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.jackson.Log4jJsonObjectMapper;
import org.apache.logging.log4j.core.jackson.Log4jXmlObjectMapper;
import org.apache.logging.log4j.core.jackson.Log4jYamlObjectMapper;
import org.codehaus.stax2.XMLStreamWriter2;

abstract class JacksonFactory {
    JacksonFactory() {
    }

    protected abstract String getPropertNameForContextMap();

    protected abstract String getPropertNameForSource();

    protected abstract String getPropertNameForNanoTime();

    protected abstract PrettyPrinter newCompactPrinter();

    protected abstract ObjectMapper newObjectMapper();

    protected abstract PrettyPrinter newPrettyPrinter();

    ObjectWriter newWriter(boolean bl, boolean bl2, boolean bl3) {
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        HashSet<String> hashSet = new HashSet<String>(2);
        if (!bl) {
            hashSet.add(this.getPropertNameForSource());
        }
        if (!bl2) {
            hashSet.add(this.getPropertNameForContextMap());
        }
        hashSet.add(this.getPropertNameForNanoTime());
        simpleFilterProvider.addFilter(Log4jLogEvent.class.getName(), SimpleBeanPropertyFilter.serializeAllExcept(hashSet));
        ObjectWriter objectWriter = this.newObjectMapper().writer(bl3 ? this.newCompactPrinter() : this.newPrettyPrinter());
        return objectWriter.with(simpleFilterProvider);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static class Log4jXmlPrettyPrinter
    extends DefaultXmlPrettyPrinter {
        private static final long serialVersionUID = 1L;

        Log4jXmlPrettyPrinter(int n) {
            this._nesting = n;
        }

        public void writePrologLinefeed(XMLStreamWriter2 xMLStreamWriter2) throws XMLStreamException {
        }

        public DefaultXmlPrettyPrinter createInstance() {
            return new Log4jXmlPrettyPrinter(1);
        }

        public Object createInstance() {
            return this.createInstance();
        }
    }

    static class YAML
    extends JacksonFactory {
        private final boolean includeStacktrace;

        public YAML(boolean bl) {
            this.includeStacktrace = bl;
        }

        @Override
        protected String getPropertNameForContextMap() {
            return "contextMap";
        }

        @Override
        protected String getPropertNameForSource() {
            return "source";
        }

        @Override
        protected String getPropertNameForNanoTime() {
            return "nanoTime";
        }

        @Override
        protected PrettyPrinter newCompactPrinter() {
            return new MinimalPrettyPrinter();
        }

        @Override
        protected ObjectMapper newObjectMapper() {
            return new Log4jYamlObjectMapper(false, this.includeStacktrace);
        }

        @Override
        protected PrettyPrinter newPrettyPrinter() {
            return new DefaultPrettyPrinter();
        }
    }

    static class XML
    extends JacksonFactory {
        static final int DEFAULT_INDENT = 1;
        private final boolean includeStacktrace;

        public XML(boolean bl) {
            this.includeStacktrace = bl;
        }

        @Override
        protected String getPropertNameForContextMap() {
            return "ContextMap";
        }

        @Override
        protected String getPropertNameForSource() {
            return "Source";
        }

        @Override
        protected String getPropertNameForNanoTime() {
            return "nanoTime";
        }

        @Override
        protected PrettyPrinter newCompactPrinter() {
            return null;
        }

        @Override
        protected ObjectMapper newObjectMapper() {
            return new Log4jXmlObjectMapper(this.includeStacktrace);
        }

        @Override
        protected PrettyPrinter newPrettyPrinter() {
            return new Log4jXmlPrettyPrinter(1);
        }
    }

    static class JSON
    extends JacksonFactory {
        private final boolean encodeThreadContextAsList;
        private final boolean includeStacktrace;

        public JSON(boolean bl, boolean bl2) {
            this.encodeThreadContextAsList = bl;
            this.includeStacktrace = bl2;
        }

        @Override
        protected String getPropertNameForContextMap() {
            return "contextMap";
        }

        @Override
        protected String getPropertNameForSource() {
            return "source";
        }

        @Override
        protected String getPropertNameForNanoTime() {
            return "nanoTime";
        }

        @Override
        protected PrettyPrinter newCompactPrinter() {
            return new MinimalPrettyPrinter();
        }

        @Override
        protected ObjectMapper newObjectMapper() {
            return new Log4jJsonObjectMapper(this.encodeThreadContextAsList, this.includeStacktrace);
        }

        @Override
        protected PrettyPrinter newPrettyPrinter() {
            return new DefaultPrettyPrinter();
        }
    }
}

