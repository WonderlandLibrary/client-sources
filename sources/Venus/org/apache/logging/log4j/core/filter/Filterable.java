/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LogEvent;

public interface Filterable
extends LifeCycle {
    public void addFilter(Filter var1);

    public void removeFilter(Filter var1);

    public Filter getFilter();

    public boolean hasFilter();

    public boolean isFiltered(LogEvent var1);
}

