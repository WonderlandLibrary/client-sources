/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.adapters.JavaReflectionAdapter;
import org.reflections.adapters.JavassistAdapter;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.serializers.Serializer;
import org.reflections.serializers.XmlSerializer;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ConfigurationBuilder
implements Configuration {
    @Nonnull
    private Set<Scanner> scanners = Sets.newHashSet((Object[])new Scanner[]{new TypeAnnotationsScanner(), new SubTypesScanner()});
    @Nonnull
    private Set<URL> urls = Sets.newHashSet();
    protected MetadataAdapter metadataAdapter;
    @Nullable
    private Predicate<String> inputsFilter;
    private Serializer serializer;
    @Nullable
    private ExecutorService executorService;
    @Nullable
    private ClassLoader[] classLoaders;

    public static ConfigurationBuilder build(@Nullable Object ... params) {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        ArrayList parameters = Lists.newArrayList();
        if (params != null) {
            for (Object param : params) {
                if (param == null) continue;
                if (param.getClass().isArray()) {
                    for (Object p : (Object[])param) {
                        if (p == null) continue;
                        parameters.add(p);
                    }
                    continue;
                }
                if (param instanceof Iterable) {
                    for (Object p : (Iterable)param) {
                        if (p == null) continue;
                        parameters.add(p);
                    }
                    continue;
                }
                parameters.add(param);
            }
        }
        ArrayList loaders = Lists.newArrayList();
        for (Object param : parameters) {
            if (!(param instanceof ClassLoader)) continue;
            loaders.add((ClassLoader)param);
        }
        ClassLoader[] classLoaders = loaders.isEmpty() ? null : loaders.toArray(new ClassLoader[loaders.size()]);
        FilterBuilder filter = new FilterBuilder();
        ArrayList scanners = Lists.newArrayList();
        for (Object param : parameters) {
            if (param instanceof String) {
                builder.addUrls(ClasspathHelper.forPackage((String)param, classLoaders));
                filter.includePackage((String)param);
                continue;
            }
            if (param instanceof Class) {
                if (Scanner.class.isAssignableFrom((Class)param)) {
                    try {
                        builder.addScanners((Scanner)((Class)param).newInstance());
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                builder.addUrls(ClasspathHelper.forClass((Class)param, classLoaders));
                filter.includePackage((Class)param);
                continue;
            }
            if (param instanceof Scanner) {
                scanners.add((Scanner)param);
                continue;
            }
            if (param instanceof URL) {
                builder.addUrls((URL)param);
                continue;
            }
            if (param instanceof ClassLoader) continue;
            if (param instanceof Predicate) {
                filter.add((Predicate<String>)((Predicate)param));
                continue;
            }
            if (param instanceof ExecutorService) {
                builder.setExecutorService((ExecutorService)param);
                continue;
            }
            if (Reflections.log == null) continue;
            throw new ReflectionsException("could not use param " + param);
        }
        if (builder.getUrls().isEmpty()) {
            if (classLoaders != null) {
                builder.addUrls(ClasspathHelper.forClassLoader(classLoaders));
            } else {
                builder.addUrls(ClasspathHelper.forClassLoader());
            }
        }
        builder.filterInputsBy(filter);
        if (!scanners.isEmpty()) {
            builder.setScanners(scanners.toArray(new Scanner[scanners.size()]));
        }
        if (!loaders.isEmpty()) {
            builder.addClassLoaders(loaders);
        }
        return builder;
    }

    public ConfigurationBuilder forPackages(String ... packages) {
        for (String pkg : packages) {
            this.addUrls(ClasspathHelper.forPackage(pkg, new ClassLoader[0]));
        }
        return this;
    }

    @Nonnull
    @Override
    public Set<Scanner> getScanners() {
        return this.scanners;
    }

    public ConfigurationBuilder setScanners(@Nonnull Scanner ... scanners) {
        this.scanners.clear();
        return this.addScanners(scanners);
    }

    public ConfigurationBuilder addScanners(Scanner ... scanners) {
        this.scanners.addAll(Sets.newHashSet((Object[])scanners));
        return this;
    }

    @Nonnull
    @Override
    public Set<URL> getUrls() {
        return this.urls;
    }

    public ConfigurationBuilder setUrls(@Nonnull Collection<URL> urls) {
        this.urls = Sets.newHashSet(urls);
        return this;
    }

    public ConfigurationBuilder setUrls(URL ... urls) {
        this.urls = Sets.newHashSet((Object[])urls);
        return this;
    }

    public ConfigurationBuilder addUrls(Collection<URL> urls) {
        this.urls.addAll(urls);
        return this;
    }

    public ConfigurationBuilder addUrls(URL ... urls) {
        this.urls.addAll(Sets.newHashSet((Object[])urls));
        return this;
    }

    @Override
    public MetadataAdapter getMetadataAdapter() {
        if (this.metadataAdapter != null) {
            return this.metadataAdapter;
        }
        try {
            this.metadataAdapter = new JavassistAdapter();
            return this.metadataAdapter;
        }
        catch (Throwable e) {
            if (Reflections.log != null) {
                Reflections.log.warn("could not create JavassistAdapter, using JavaReflectionAdapter", e);
            }
            this.metadataAdapter = new JavaReflectionAdapter();
            return this.metadataAdapter;
        }
    }

    public ConfigurationBuilder setMetadataAdapter(MetadataAdapter metadataAdapter) {
        this.metadataAdapter = metadataAdapter;
        return this;
    }

    @Nullable
    @Override
    public Predicate<String> getInputsFilter() {
        return this.inputsFilter;
    }

    public void setInputsFilter(@Nullable Predicate<String> inputsFilter) {
        this.inputsFilter = inputsFilter;
    }

    public ConfigurationBuilder filterInputsBy(Predicate<String> inputsFilter) {
        this.inputsFilter = inputsFilter;
        return this;
    }

    @Nullable
    @Override
    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public ConfigurationBuilder setExecutorService(@Nullable ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }

    public ConfigurationBuilder useParallelExecutor() {
        return this.useParallelExecutor(Runtime.getRuntime().availableProcessors());
    }

    public ConfigurationBuilder useParallelExecutor(int availableProcessors) {
        this.setExecutorService(Executors.newFixedThreadPool(availableProcessors));
        return this;
    }

    @Override
    public Serializer getSerializer() {
        return this.serializer != null ? this.serializer : (this.serializer = new XmlSerializer());
    }

    public ConfigurationBuilder setSerializer(Serializer serializer) {
        this.serializer = serializer;
        return this;
    }

    @Nullable
    @Override
    public ClassLoader[] getClassLoaders() {
        return this.classLoaders;
    }

    public void setClassLoaders(@Nullable ClassLoader[] classLoaders) {
        this.classLoaders = classLoaders;
    }

    public ConfigurationBuilder addClassLoader(ClassLoader classLoader) {
        return this.addClassLoaders(classLoader);
    }

    public ConfigurationBuilder addClassLoaders(ClassLoader ... classLoaders) {
        this.classLoaders = this.classLoaders == null ? classLoaders : (ClassLoader[])ObjectArrays.concat((Object[])this.classLoaders, (Object[])classLoaders, ClassLoader.class);
        return this;
    }

    public ConfigurationBuilder addClassLoaders(Collection<ClassLoader> classLoaders) {
        return this.addClassLoaders(classLoaders.toArray(new ClassLoader[classLoaders.size()]));
    }
}

