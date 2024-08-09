/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.logging.log4j.core.config.plugins.PluginVisitorStrategy;
import org.apache.logging.log4j.core.config.plugins.visitors.PluginAttributeVisitor;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.PARAMETER, ElementType.FIELD})
@PluginVisitorStrategy(value=PluginAttributeVisitor.class)
public @interface PluginAttribute {
    public boolean defaultBoolean() default false;

    public byte defaultByte() default 0;

    public char defaultChar() default 0;

    public Class<?> defaultClass() default Object.class;

    public double defaultDouble() default 0.0;

    public float defaultFloat() default 0.0f;

    public int defaultInt() default 0;

    public long defaultLong() default 0L;

    public short defaultShort() default 0;

    public String defaultString() default "";

    public String value();

    public boolean sensitive() default false;
}

