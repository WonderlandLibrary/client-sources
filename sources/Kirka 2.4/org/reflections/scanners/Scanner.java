/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.scanners;

import com.google.common.base.Predicate;
import com.google.common.collect.Multimap;
import javax.annotation.Nullable;
import org.reflections.Configuration;
import org.reflections.vfs.Vfs;

public interface Scanner {
    public void setConfiguration(Configuration var1);

    public Multimap<String, String> getStore();

    public void setStore(Multimap<String, String> var1);

    public Scanner filterResultsBy(Predicate<String> var1);

    public boolean acceptsInput(String var1);

    public Object scan(Vfs.File var1, @Nullable Object var2);

    public boolean acceptResult(String var1);
}

