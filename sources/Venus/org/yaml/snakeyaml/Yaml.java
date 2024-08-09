/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.emitter.Emitable;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.parser.Parser;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.reader.UnicodeReader;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;
import org.yaml.snakeyaml.serializer.Serializer;

public class Yaml {
    protected final Resolver resolver;
    private String name;
    protected BaseConstructor constructor;
    protected Representer representer;
    protected DumperOptions dumperOptions;
    protected LoaderOptions loadingConfig;

    public Yaml() {
        this(new Constructor(new LoaderOptions()), new Representer(new DumperOptions()));
    }

    public Yaml(DumperOptions dumperOptions) {
        this(new Constructor(new LoaderOptions()), new Representer(dumperOptions), dumperOptions);
    }

    public Yaml(LoaderOptions loaderOptions) {
        this((BaseConstructor)new Constructor(loaderOptions), new Representer(new DumperOptions()), new DumperOptions(), loaderOptions);
    }

    public Yaml(Representer representer) {
        this(new Constructor(new LoaderOptions()), representer);
    }

    public Yaml(BaseConstructor baseConstructor) {
        this(baseConstructor, new Representer(new DumperOptions()));
    }

    public Yaml(BaseConstructor baseConstructor, Representer representer) {
        this(baseConstructor, representer, Yaml.initDumperOptions(representer));
    }

    private static DumperOptions initDumperOptions(Representer representer) {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(representer.getDefaultFlowStyle());
        dumperOptions.setDefaultScalarStyle(representer.getDefaultScalarStyle());
        dumperOptions.setAllowReadOnlyProperties(representer.getPropertyUtils().isAllowReadOnlyProperties());
        dumperOptions.setTimeZone(representer.getTimeZone());
        return dumperOptions;
    }

    public Yaml(Representer representer, DumperOptions dumperOptions) {
        this(new Constructor(new LoaderOptions()), representer, dumperOptions);
    }

    public Yaml(BaseConstructor baseConstructor, Representer representer, DumperOptions dumperOptions) {
        this(baseConstructor, representer, dumperOptions, baseConstructor.getLoadingConfig(), new Resolver());
    }

    public Yaml(BaseConstructor baseConstructor, Representer representer, DumperOptions dumperOptions, LoaderOptions loaderOptions) {
        this(baseConstructor, representer, dumperOptions, loaderOptions, new Resolver());
    }

    public Yaml(BaseConstructor baseConstructor, Representer representer, DumperOptions dumperOptions, Resolver resolver) {
        this(baseConstructor, representer, dumperOptions, new LoaderOptions(), resolver);
    }

    public Yaml(BaseConstructor baseConstructor, Representer representer, DumperOptions dumperOptions, LoaderOptions loaderOptions, Resolver resolver) {
        if (baseConstructor == null) {
            throw new NullPointerException("Constructor must be provided");
        }
        if (representer == null) {
            throw new NullPointerException("Representer must be provided");
        }
        if (dumperOptions == null) {
            throw new NullPointerException("DumperOptions must be provided");
        }
        if (loaderOptions == null) {
            throw new NullPointerException("LoaderOptions must be provided");
        }
        if (resolver == null) {
            throw new NullPointerException("Resolver must be provided");
        }
        if (!baseConstructor.isExplicitPropertyUtils()) {
            baseConstructor.setPropertyUtils(representer.getPropertyUtils());
        } else if (!representer.isExplicitPropertyUtils()) {
            representer.setPropertyUtils(baseConstructor.getPropertyUtils());
        }
        this.constructor = baseConstructor;
        this.constructor.setAllowDuplicateKeys(loaderOptions.isAllowDuplicateKeys());
        this.constructor.setWrappedToRootException(loaderOptions.isWrappedToRootException());
        if (!dumperOptions.getIndentWithIndicator() && dumperOptions.getIndent() <= dumperOptions.getIndicatorIndent()) {
            throw new YAMLException("Indicator indent must be smaller then indent.");
        }
        representer.setDefaultFlowStyle(dumperOptions.getDefaultFlowStyle());
        representer.setDefaultScalarStyle(dumperOptions.getDefaultScalarStyle());
        representer.getPropertyUtils().setAllowReadOnlyProperties(dumperOptions.isAllowReadOnlyProperties());
        representer.setTimeZone(dumperOptions.getTimeZone());
        this.representer = representer;
        this.dumperOptions = dumperOptions;
        this.loadingConfig = loaderOptions;
        this.resolver = resolver;
        this.name = "Yaml:" + System.identityHashCode(this);
    }

    public String dump(Object object) {
        ArrayList<Object> arrayList = new ArrayList<Object>(1);
        arrayList.add(object);
        return this.dumpAll(arrayList.iterator());
    }

    public Node represent(Object object) {
        return this.representer.represent(object);
    }

    public String dumpAll(Iterator<? extends Object> iterator2) {
        StringWriter stringWriter = new StringWriter();
        this.dumpAll(iterator2, stringWriter, null);
        return stringWriter.toString();
    }

    public void dump(Object object, Writer writer) {
        ArrayList<Object> arrayList = new ArrayList<Object>(1);
        arrayList.add(object);
        this.dumpAll(arrayList.iterator(), writer, null);
    }

    public void dumpAll(Iterator<? extends Object> iterator2, Writer writer) {
        this.dumpAll(iterator2, writer, null);
    }

    private void dumpAll(Iterator<? extends Object> iterator2, Writer writer, Tag tag) {
        Serializer serializer = new Serializer(new Emitter(writer, this.dumperOptions), this.resolver, this.dumperOptions, tag);
        try {
            serializer.open();
            while (iterator2.hasNext()) {
                Node node = this.representer.represent(iterator2.next());
                serializer.serialize(node);
            }
            serializer.close();
        } catch (IOException iOException) {
            throw new YAMLException(iOException);
        }
    }

    public String dumpAs(Object object, Tag tag, DumperOptions.FlowStyle flowStyle) {
        DumperOptions.FlowStyle flowStyle2 = this.representer.getDefaultFlowStyle();
        if (flowStyle != null) {
            this.representer.setDefaultFlowStyle(flowStyle);
        }
        ArrayList<Object> arrayList = new ArrayList<Object>(1);
        arrayList.add(object);
        StringWriter stringWriter = new StringWriter();
        this.dumpAll(arrayList.iterator(), stringWriter, tag);
        this.representer.setDefaultFlowStyle(flowStyle2);
        return stringWriter.toString();
    }

    public String dumpAsMap(Object object) {
        return this.dumpAs(object, Tag.MAP, DumperOptions.FlowStyle.BLOCK);
    }

    public void serialize(Node node, Writer writer) {
        Serializer serializer = new Serializer(new Emitter(writer, this.dumperOptions), this.resolver, this.dumperOptions, null);
        try {
            serializer.open();
            serializer.serialize(node);
            serializer.close();
        } catch (IOException iOException) {
            throw new YAMLException(iOException);
        }
    }

    public List<Event> serialize(Node node) {
        SilentEmitter silentEmitter = new SilentEmitter(null);
        Serializer serializer = new Serializer(silentEmitter, this.resolver, this.dumperOptions, null);
        try {
            serializer.open();
            serializer.serialize(node);
            serializer.close();
        } catch (IOException iOException) {
            throw new YAMLException(iOException);
        }
        return silentEmitter.getEvents();
    }

    public <T> T load(String string) {
        return (T)this.loadFromReader(new StreamReader(string), Object.class);
    }

    public <T> T load(InputStream inputStream) {
        return (T)this.loadFromReader(new StreamReader(new UnicodeReader(inputStream)), Object.class);
    }

    public <T> T load(Reader reader) {
        return (T)this.loadFromReader(new StreamReader(reader), Object.class);
    }

    public <T> T loadAs(Reader reader, Class<? super T> clazz) {
        return (T)this.loadFromReader(new StreamReader(reader), clazz);
    }

    public <T> T loadAs(String string, Class<? super T> clazz) {
        return (T)this.loadFromReader(new StreamReader(string), clazz);
    }

    public <T> T loadAs(InputStream inputStream, Class<? super T> clazz) {
        return (T)this.loadFromReader(new StreamReader(new UnicodeReader(inputStream)), clazz);
    }

    private Object loadFromReader(StreamReader streamReader, Class<?> clazz) {
        Composer composer = new Composer(new ParserImpl(streamReader, this.loadingConfig), this.resolver, this.loadingConfig);
        this.constructor.setComposer(composer);
        return this.constructor.getSingleData(clazz);
    }

    public Iterable<Object> loadAll(Reader reader) {
        Composer composer = new Composer(new ParserImpl(new StreamReader(reader), this.loadingConfig), this.resolver, this.loadingConfig);
        this.constructor.setComposer(composer);
        Iterator<Object> iterator2 = new Iterator<Object>(this){
            final Yaml this$0;
            {
                this.this$0 = yaml;
            }

            @Override
            public boolean hasNext() {
                return this.this$0.constructor.checkData();
            }

            @Override
            public Object next() {
                return this.this$0.constructor.getData();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return new YamlIterable(iterator2);
    }

    public Iterable<Object> loadAll(String string) {
        return this.loadAll(new StringReader(string));
    }

    public Iterable<Object> loadAll(InputStream inputStream) {
        return this.loadAll(new UnicodeReader(inputStream));
    }

    public Node compose(Reader reader) {
        Composer composer = new Composer(new ParserImpl(new StreamReader(reader), this.loadingConfig), this.resolver, this.loadingConfig);
        return composer.getSingleNode();
    }

    public Iterable<Node> composeAll(Reader reader) {
        Composer composer = new Composer(new ParserImpl(new StreamReader(reader), this.loadingConfig), this.resolver, this.loadingConfig);
        Iterator<Node> iterator2 = new Iterator<Node>(this, composer){
            final Composer val$composer;
            final Yaml this$0;
            {
                this.this$0 = yaml;
                this.val$composer = composer;
            }

            @Override
            public boolean hasNext() {
                return this.val$composer.checkNode();
            }

            @Override
            public Node next() {
                Node node = this.val$composer.getNode();
                if (node != null) {
                    return node;
                }
                throw new NoSuchElementException("No Node is available.");
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
        return new NodeIterable(iterator2);
    }

    public void addImplicitResolver(Tag tag, Pattern pattern, String string) {
        this.resolver.addImplicitResolver(tag, pattern, string);
    }

    public void addImplicitResolver(Tag tag, Pattern pattern, String string, int n) {
        this.resolver.addImplicitResolver(tag, pattern, string, n);
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public Iterable<Event> parse(Reader reader) {
        ParserImpl parserImpl = new ParserImpl(new StreamReader(reader), this.loadingConfig);
        Iterator<Event> iterator2 = new Iterator<Event>(this, (Parser)parserImpl){
            final Parser val$parser;
            final Yaml this$0;
            {
                this.this$0 = yaml;
                this.val$parser = parser;
            }

            @Override
            public boolean hasNext() {
                return this.val$parser.peekEvent() != null;
            }

            @Override
            public Event next() {
                Event event = this.val$parser.getEvent();
                if (event != null) {
                    return event;
                }
                throw new NoSuchElementException("No Event is available.");
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
        return new EventIterable(iterator2);
    }

    public void setBeanAccess(BeanAccess beanAccess) {
        this.constructor.getPropertyUtils().setBeanAccess(beanAccess);
        this.representer.getPropertyUtils().setBeanAccess(beanAccess);
    }

    public void addTypeDescription(TypeDescription typeDescription) {
        this.constructor.addTypeDescription(typeDescription);
        this.representer.addTypeDescription(typeDescription);
    }

    private static class EventIterable
    implements Iterable<Event> {
        private final Iterator<Event> iterator;

        public EventIterable(Iterator<Event> iterator2) {
            this.iterator = iterator2;
        }

        @Override
        public Iterator<Event> iterator() {
            return this.iterator;
        }
    }

    private static class NodeIterable
    implements Iterable<Node> {
        private final Iterator<Node> iterator;

        public NodeIterable(Iterator<Node> iterator2) {
            this.iterator = iterator2;
        }

        @Override
        public Iterator<Node> iterator() {
            return this.iterator;
        }
    }

    private static class YamlIterable
    implements Iterable<Object> {
        private final Iterator<Object> iterator;

        public YamlIterable(Iterator<Object> iterator2) {
            this.iterator = iterator2;
        }

        @Override
        public Iterator<Object> iterator() {
            return this.iterator;
        }
    }

    private static class SilentEmitter
    implements Emitable {
        private final List<Event> events = new ArrayList<Event>(100);

        private SilentEmitter() {
        }

        public List<Event> getEvents() {
            return this.events;
        }

        @Override
        public void emit(Event event) throws IOException {
            this.events.add(event);
        }

        SilentEmitter(1 var1_1) {
            this();
        }
    }
}

