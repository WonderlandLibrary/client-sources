/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.scanners;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;
import org.reflections.Configuration;
import org.reflections.ReflectionsException;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.scanners.Scanner;
import org.reflections.vfs.Vfs;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class AbstractScanner
implements Scanner {
    private Configuration configuration;
    private Multimap<String, String> store;
    private Predicate<String> resultFilter = Predicates.alwaysTrue();

    @Override
    public boolean acceptsInput(String file) {
        return this.getMetadataAdapter().acceptsInput(file);
    }

    @Override
    public Object scan(Vfs.File file, Object classObject) {
        if (classObject == null) {
            try {
                classObject = this.configuration.getMetadataAdapter().getOfCreateClassObject(file);
            }
            catch (Exception e) {
                throw new ReflectionsException("could not create class object from file " + file.getRelativePath());
            }
        }
        this.scan(classObject);
        return classObject;
    }

    public abstract void scan(Object var1);

    public Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Multimap<String, String> getStore() {
        return this.store;
    }

    @Override
    public void setStore(Multimap<String, String> store) {
        this.store = store;
    }

    public Predicate<String> getResultFilter() {
        return this.resultFilter;
    }

    public void setResultFilter(Predicate<String> resultFilter) {
        this.resultFilter = resultFilter;
    }

    @Override
    public Scanner filterResultsBy(Predicate<String> filter) {
        this.setResultFilter(filter);
        return this;
    }

    @Override
    public boolean acceptResult(String fqn) {
        return fqn != null && this.resultFilter.apply((Object)fqn);
    }

    protected MetadataAdapter getMetadataAdapter() {
        return this.configuration.getMetadataAdapter();
    }

    public boolean equals(Object o) {
        return this == o || o != null && this.getClass() == o.getClass();
    }

    public int hashCode() {
        return this.getClass().hashCode();
    }
}

