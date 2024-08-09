/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor7;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;
import org.apache.logging.log4j.core.config.plugins.processor.PluginCache;
import org.apache.logging.log4j.core.config.plugins.processor.PluginEntry;

@SupportedAnnotationTypes(value={"org.apache.logging.log4j.core.config.plugins.*"})
public class PluginProcessor
extends AbstractProcessor {
    public static final String PLUGIN_CACHE_FILE = "META-INF/org/apache/logging/log4j/core/config/plugins/Log4j2Plugins.dat";
    private final PluginCache pluginCache = new PluginCache();

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        try {
            Set<? extends Element> set2 = roundEnvironment.getElementsAnnotatedWith(Plugin.class);
            if (set2.isEmpty()) {
                return false;
            }
            this.collectPlugins(set2);
            this.writeCacheFile(set2.toArray(new Element[set2.size()]));
            return true;
        } catch (IOException iOException) {
            this.error(iOException.getMessage());
            return true;
        }
    }

    private void error(CharSequence charSequence) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, charSequence);
    }

    private void collectPlugins(Iterable<? extends Element> iterable) {
        Elements elements = this.processingEnv.getElementUtils();
        PluginElementVisitor pluginElementVisitor = new PluginElementVisitor(elements, null);
        PluginAliasesElementVisitor pluginAliasesElementVisitor = new PluginAliasesElementVisitor(elements, null);
        for (Element element : iterable) {
            Plugin plugin = element.getAnnotation(Plugin.class);
            if (plugin == null) continue;
            PluginEntry pluginEntry = element.accept(pluginElementVisitor, plugin);
            Map<String, PluginEntry> map = this.pluginCache.getCategory(pluginEntry.getCategory());
            map.put(pluginEntry.getKey(), pluginEntry);
            Collection<PluginEntry> collection = element.accept(pluginAliasesElementVisitor, plugin);
            for (PluginEntry pluginEntry2 : collection) {
                map.put(pluginEntry2.getKey(), pluginEntry2);
            }
        }
    }

    private void writeCacheFile(Element ... elementArray) throws IOException {
        FileObject fileObject = this.processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", PLUGIN_CACHE_FILE, elementArray);
        try (OutputStream outputStream = fileObject.openOutputStream();){
            this.pluginCache.writeCache(outputStream);
        }
    }

    static class 1 {
    }

    private static class PluginAliasesElementVisitor
    extends SimpleElementVisitor7<Collection<PluginEntry>, Plugin> {
        private final Elements elements;

        private PluginAliasesElementVisitor(Elements elements) {
            super(Collections.emptyList());
            this.elements = elements;
        }

        @Override
        public Collection<PluginEntry> visitType(TypeElement typeElement, Plugin plugin) {
            PluginAliases pluginAliases = typeElement.getAnnotation(PluginAliases.class);
            if (pluginAliases == null) {
                return (Collection)this.DEFAULT_VALUE;
            }
            ArrayList<PluginEntry> arrayList = new ArrayList<PluginEntry>(pluginAliases.value().length);
            for (String string : pluginAliases.value()) {
                PluginEntry pluginEntry = new PluginEntry();
                pluginEntry.setKey(string.toLowerCase(Locale.US));
                pluginEntry.setClassName(this.elements.getBinaryName(typeElement).toString());
                pluginEntry.setName("".equals(plugin.elementType()) ? string : plugin.elementType());
                pluginEntry.setPrintable(plugin.printObject());
                pluginEntry.setDefer(plugin.deferChildren());
                pluginEntry.setCategory(plugin.category());
                arrayList.add(pluginEntry);
            }
            return arrayList;
        }

        @Override
        public Object visitType(TypeElement typeElement, Object object) {
            return this.visitType(typeElement, (Plugin)object);
        }

        PluginAliasesElementVisitor(Elements elements, 1 var2_2) {
            this(elements);
        }
    }

    private static class PluginElementVisitor
    extends SimpleElementVisitor7<PluginEntry, Plugin> {
        private final Elements elements;

        private PluginElementVisitor(Elements elements) {
            this.elements = elements;
        }

        @Override
        public PluginEntry visitType(TypeElement typeElement, Plugin plugin) {
            Objects.requireNonNull(plugin, "Plugin annotation is null.");
            PluginEntry pluginEntry = new PluginEntry();
            pluginEntry.setKey(plugin.name().toLowerCase(Locale.US));
            pluginEntry.setClassName(this.elements.getBinaryName(typeElement).toString());
            pluginEntry.setName("".equals(plugin.elementType()) ? plugin.name() : plugin.elementType());
            pluginEntry.setPrintable(plugin.printObject());
            pluginEntry.setDefer(plugin.deferChildren());
            pluginEntry.setCategory(plugin.category());
            return pluginEntry;
        }

        @Override
        public Object visitType(TypeElement typeElement, Object object) {
            return this.visitType(typeElement, (Plugin)object);
        }

        PluginElementVisitor(Elements elements, 1 var2_2) {
            this(elements);
        }
    }
}

