/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

public interface PatternConverter {
    public static final String CATEGORY = "Converter";

    public void format(Object var1, StringBuilder var2);

    public String getName();

    public String getStyleClass(Object var1);
}

