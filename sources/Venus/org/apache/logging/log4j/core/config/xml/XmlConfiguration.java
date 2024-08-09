/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.ConfiguratonFileWatcher;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.Reconfigurable;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;
import org.apache.logging.log4j.core.config.status.StatusConfiguration;
import org.apache.logging.log4j.core.util.Closer;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.core.util.Patterns;
import org.apache.logging.log4j.core.util.Throwables;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlConfiguration
extends AbstractConfiguration
implements Reconfigurable {
    private static final String XINCLUDE_FIXUP_LANGUAGE = "http://apache.org/xml/features/xinclude/fixup-language";
    private static final String XINCLUDE_FIXUP_BASE_URIS = "http://apache.org/xml/features/xinclude/fixup-base-uris";
    private static final String[] VERBOSE_CLASSES = new String[]{ResolverUtil.class.getName()};
    private static final String LOG4J_XSD = "Log4j-config.xsd";
    private final List<Status> status;
    private Element rootElement;
    private boolean strict;
    private String schemaResource;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public XmlConfiguration(LoggerContext loggerContext, ConfigurationSource configurationSource) {
        block42: {
            Object object;
            Object object2;
            Object object3;
            Object object4;
            Object object5;
            InputStream inputStream;
            super(loggerContext, configurationSource);
            this.status = new ArrayList<Status>();
            File file = configurationSource.getFile();
            byte[] byArray = null;
            try {
                inputStream = configurationSource.getInputStream();
                try {
                    byArray = XmlConfiguration.toByteArray(inputStream);
                } finally {
                    Closer.closeSilently(inputStream);
                }
                object5 = new InputSource(new ByteArrayInputStream(byArray));
                ((InputSource)object5).setSystemId(configurationSource.getLocation());
                object4 = XmlConfiguration.newDocumentBuilder(true);
                try {
                    object3 = ((DocumentBuilder)object4).parse((InputSource)object5);
                } catch (Exception exception) {
                    object2 = Throwables.getRootCause(exception);
                    if (object2 instanceof UnsupportedOperationException) {
                        LOGGER.warn("The DocumentBuilder {} does not support an operation: {}.Trying again without XInclude...", object4, (Object)exception);
                        object3 = XmlConfiguration.newDocumentBuilder(false).parse((InputSource)object5);
                    }
                    throw exception;
                }
                this.rootElement = object3.getDocumentElement();
                object = this.processAttributes(this.rootNode, this.rootElement);
                object2 = new StatusConfiguration().withVerboseClasses(VERBOSE_CLASSES).withStatus(this.getDefaultStatus());
                for (Map.Entry entry : object.entrySet()) {
                    String string = (String)entry.getKey();
                    String string2 = this.getStrSubstitutor().replace((String)entry.getValue());
                    if ("status".equalsIgnoreCase(string)) {
                        ((StatusConfiguration)object2).withStatus(string2);
                        continue;
                    }
                    if ("dest".equalsIgnoreCase(string)) {
                        ((StatusConfiguration)object2).withDestination(string2);
                        continue;
                    }
                    if ("shutdownHook".equalsIgnoreCase(string)) {
                        this.isShutdownHookEnabled = !"disable".equalsIgnoreCase(string2);
                        continue;
                    }
                    if ("shutdownTimeout".equalsIgnoreCase(string)) {
                        this.shutdownTimeoutMillis = Long.parseLong(string2);
                        continue;
                    }
                    if ("verbose".equalsIgnoreCase(string)) {
                        ((StatusConfiguration)object2).withVerbosity(string2);
                        continue;
                    }
                    if ("packages".equalsIgnoreCase(string)) {
                        this.pluginPackages.addAll(Arrays.asList(string2.split(Patterns.COMMA_SEPARATOR)));
                        continue;
                    }
                    if ("name".equalsIgnoreCase(string)) {
                        this.setName(string2);
                        continue;
                    }
                    if ("strict".equalsIgnoreCase(string)) {
                        this.strict = Boolean.parseBoolean(string2);
                        continue;
                    }
                    if ("schema".equalsIgnoreCase(string)) {
                        this.schemaResource = string2;
                        continue;
                    }
                    if ("monitorInterval".equalsIgnoreCase(string)) {
                        int n = Integer.parseInt(string2);
                        if (n <= 0) continue;
                        this.getWatchManager().setIntervalSeconds(n);
                        if (file == null) continue;
                        ConfiguratonFileWatcher configuratonFileWatcher = new ConfiguratonFileWatcher(this, this.listeners);
                        this.getWatchManager().watchFile(file, configuratonFileWatcher);
                        continue;
                    }
                    if (!"advertiser".equalsIgnoreCase(string)) continue;
                    this.createAdvertiser(string2, configurationSource, byArray, "text/xml");
                }
                ((StatusConfiguration)object2).initialize();
            } catch (IOException | ParserConfigurationException | SAXException exception) {
                LOGGER.error("Error parsing " + configurationSource.getLocation(), (Throwable)exception);
            }
            if (this.strict && this.schemaResource != null && byArray != null) {
                try {
                    inputStream = Loader.getResourceAsStream(this.schemaResource, XmlConfiguration.class.getClassLoader());
                    object5 = null;
                    try {
                        if (inputStream == null) break block42;
                        object4 = new StreamSource(inputStream, LOG4J_XSD);
                        object3 = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
                        object = null;
                        try {
                            object = ((SchemaFactory)object3).newSchema((Source)object4);
                        } catch (SAXException sAXException) {
                            LOGGER.error("Error parsing Log4j schema", (Throwable)sAXException);
                        }
                        if (object != null) {
                            object2 = ((Schema)object).newValidator();
                            try {
                                ((Validator)object2).validate(new StreamSource(new ByteArrayInputStream(byArray)));
                            } catch (IOException iOException) {
                                LOGGER.error("Error reading configuration for validation", (Throwable)iOException);
                            } catch (SAXException sAXException) {
                                LOGGER.error("Error validating configuration", (Throwable)sAXException);
                            }
                        }
                    } catch (Throwable throwable) {
                        object5 = throwable;
                        throw throwable;
                    } finally {
                        if (inputStream != null) {
                            if (object5 != null) {
                                try {
                                    inputStream.close();
                                } catch (Throwable throwable) {
                                    ((Throwable)object5).addSuppressed(throwable);
                                }
                            } else {
                                inputStream.close();
                            }
                        }
                    }
                } catch (Exception exception) {
                    LOGGER.error("Unable to access schema {}", (Object)this.schemaResource, (Object)exception);
                }
            }
        }
        if (this.getName() == null) {
            this.setName(configurationSource.getLocation());
        }
    }

    static DocumentBuilder newDocumentBuilder(boolean bl) throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(false);
        if (bl) {
            XmlConfiguration.enableXInclude(documentBuilderFactory);
        }
        return documentBuilderFactory.newDocumentBuilder();
    }

    private static void enableXInclude(DocumentBuilderFactory documentBuilderFactory) {
        try {
            documentBuilderFactory.setXIncludeAware(false);
        } catch (UnsupportedOperationException unsupportedOperationException) {
            LOGGER.warn("The DocumentBuilderFactory [{}] does not support XInclude: {}", (Object)documentBuilderFactory, (Object)unsupportedOperationException);
        } catch (AbstractMethodError | NoSuchMethodError incompatibleClassChangeError) {
            LOGGER.warn("The DocumentBuilderFactory [{}] is out of date and does not support XInclude: {}", (Object)documentBuilderFactory, (Object)incompatibleClassChangeError);
        }
        try {
            documentBuilderFactory.setFeature(XINCLUDE_FIXUP_BASE_URIS, false);
        } catch (ParserConfigurationException parserConfigurationException) {
            LOGGER.warn("The DocumentBuilderFactory [{}] does not support the feature [{}]: {}", (Object)documentBuilderFactory, (Object)XINCLUDE_FIXUP_BASE_URIS, (Object)parserConfigurationException);
        } catch (AbstractMethodError abstractMethodError) {
            LOGGER.warn("The DocumentBuilderFactory [{}] is out of date and does not support setFeature: {}", (Object)documentBuilderFactory, (Object)abstractMethodError);
        }
        try {
            documentBuilderFactory.setFeature(XINCLUDE_FIXUP_LANGUAGE, false);
        } catch (ParserConfigurationException parserConfigurationException) {
            LOGGER.warn("The DocumentBuilderFactory [{}] does not support the feature [{}]: {}", (Object)documentBuilderFactory, (Object)XINCLUDE_FIXUP_LANGUAGE, (Object)parserConfigurationException);
        } catch (AbstractMethodError abstractMethodError) {
            LOGGER.warn("The DocumentBuilderFactory [{}] is out of date and does not support setFeature: {}", (Object)documentBuilderFactory, (Object)abstractMethodError);
        }
    }

    @Override
    public void setup() {
        if (this.rootElement == null) {
            LOGGER.error("No logging configuration");
            return;
        }
        this.constructHierarchy(this.rootNode, this.rootElement);
        if (this.status.size() > 0) {
            for (Status status2 : this.status) {
                LOGGER.error("Error processing element {} ({}): {}", (Object)Status.access$000(status2), (Object)Status.access$100(status2), (Object)Status.access$200(status2));
            }
            return;
        }
        this.rootElement = null;
    }

    @Override
    public Configuration reconfigure() {
        try {
            ConfigurationSource configurationSource = this.getConfigurationSource().resetInputStream();
            if (configurationSource == null) {
                return null;
            }
            XmlConfiguration xmlConfiguration = new XmlConfiguration(this.getLoggerContext(), configurationSource);
            return xmlConfiguration.rootElement == null ? null : xmlConfiguration;
        } catch (IOException iOException) {
            LOGGER.error("Cannot locate file {}", (Object)this.getConfigurationSource(), (Object)iOException);
            return null;
        }
    }

    private void constructHierarchy(Node node, Element element) {
        this.processAttributes(node, element);
        StringBuilder stringBuilder = new StringBuilder();
        NodeList nodeList = element.getChildNodes();
        List<Node> list = node.getChildren();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            org.w3c.dom.Node node2;
            org.w3c.dom.Node node3 = nodeList.item(i);
            if (node3 instanceof Element) {
                node2 = (Element)node3;
                String string = this.getType((Element)node2);
                PluginType<?> pluginType = this.pluginManager.getPluginType(string);
                Node node4 = new Node(node, string, pluginType);
                this.constructHierarchy(node4, (Element)node2);
                if (pluginType == null) {
                    String string2 = node4.getValue();
                    if (!node4.hasChildren() && string2 != null) {
                        node.getAttributes().put(string, string2);
                        continue;
                    }
                    this.status.add(new Status(string, element, ErrorType.CLASS_NOT_FOUND));
                    continue;
                }
                list.add(node4);
                continue;
            }
            if (!(node3 instanceof Text)) continue;
            node2 = (Text)node3;
            stringBuilder.append(node2.getData());
        }
        String string = stringBuilder.toString().trim();
        if (string.length() > 0 || !node.hasChildren() && !node.isRoot()) {
            node.setValue(string);
        }
    }

    private String getType(Element element) {
        if (this.strict) {
            NamedNodeMap namedNodeMap = element.getAttributes();
            for (int i = 0; i < namedNodeMap.getLength(); ++i) {
                Attr attr;
                org.w3c.dom.Node node = namedNodeMap.item(i);
                if (!(node instanceof Attr) || !(attr = (Attr)node).getName().equalsIgnoreCase("type")) continue;
                String string = attr.getValue();
                namedNodeMap.removeNamedItem(attr.getName());
                return string;
            }
        }
        return element.getTagName();
    }

    private Map<String, String> processAttributes(Node node, Element element) {
        NamedNodeMap namedNodeMap = element.getAttributes();
        Map<String, String> map = node.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); ++i) {
            Attr attr;
            org.w3c.dom.Node node2 = namedNodeMap.item(i);
            if (!(node2 instanceof Attr) || (attr = (Attr)node2).getName().equals("xml:base")) continue;
            map.put(attr.getName(), attr.getValue());
        }
        return map;
    }

    public String toString() {
        return this.getClass().getSimpleName() + "[location=" + this.getConfigurationSource() + "]";
    }

    private static class Status {
        private final Element element;
        private final String name;
        private final ErrorType errorType;

        public Status(String string, Element element, ErrorType errorType) {
            this.name = string;
            this.element = element;
            this.errorType = errorType;
        }

        public String toString() {
            return "Status [name=" + this.name + ", element=" + this.element + ", errorType=" + (Object)((Object)this.errorType) + "]";
        }

        static String access$000(Status status2) {
            return status2.name;
        }

        static Element access$100(Status status2) {
            return status2.element;
        }

        static ErrorType access$200(Status status2) {
            return status2.errorType;
        }
    }

    private static enum ErrorType {
        CLASS_NOT_FOUND;

    }
}

