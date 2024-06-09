/*
 * Decompiled with CFR 0.143.
 */
package org.reflections;

import com.google.common.base.Predicate;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import javax.annotation.Nullable;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.scanners.Scanner;
import org.reflections.serializers.Serializer;

public interface Configuration {
    public Set<Scanner> getScanners();

    public Set<URL> getUrls();

    public MetadataAdapter getMetadataAdapter();

    @Nullable
    public Predicate<String> getInputsFilter();

    public ExecutorService getExecutorService();

    public Serializer getSerializer();

    @Nullable
    public ClassLoader[] getClassLoaders();
}

