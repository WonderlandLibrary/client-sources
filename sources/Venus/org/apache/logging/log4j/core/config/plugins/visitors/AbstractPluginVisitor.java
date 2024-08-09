/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.visitors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;
import org.apache.logging.log4j.core.config.plugins.visitors.PluginVisitor;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Strings;

public abstract class AbstractPluginVisitor<A extends Annotation>
implements PluginVisitor<A> {
    protected static final Logger LOGGER = StatusLogger.getLogger();
    protected final Class<A> clazz;
    protected A annotation;
    protected String[] aliases;
    protected Class<?> conversionType;
    protected StrSubstitutor substitutor;
    protected Member member;

    protected AbstractPluginVisitor(Class<A> clazz) {
        this.clazz = clazz;
    }

    @Override
    public PluginVisitor<A> setAnnotation(Annotation annotation) {
        Annotation annotation2 = Objects.requireNonNull(annotation, "No annotation was provided");
        if (this.clazz.isInstance(annotation2)) {
            this.annotation = annotation2;
        }
        return this;
    }

    @Override
    public PluginVisitor<A> setAliases(String ... stringArray) {
        this.aliases = stringArray;
        return this;
    }

    @Override
    public PluginVisitor<A> setConversionType(Class<?> clazz) {
        this.conversionType = Objects.requireNonNull(clazz, "No conversion type class was provided");
        return this;
    }

    @Override
    public PluginVisitor<A> setStrSubstitutor(StrSubstitutor strSubstitutor) {
        this.substitutor = Objects.requireNonNull(strSubstitutor, "No StrSubstitutor was provided");
        return this;
    }

    @Override
    public PluginVisitor<A> setMember(Member member) {
        this.member = member;
        return this;
    }

    protected static String removeAttributeValue(Map<String, String> map, String string, String ... stringArray) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String string2 = entry.getKey();
            String string3 = entry.getValue();
            if (string2.equalsIgnoreCase(string)) {
                map.remove(string2);
                return string3;
            }
            if (stringArray == null) continue;
            for (String string4 : stringArray) {
                if (!string2.equalsIgnoreCase(string4)) continue;
                map.remove(string2);
                return string3;
            }
        }
        return null;
    }

    protected Object convert(String string, Object object) {
        if (object instanceof String) {
            return TypeConverters.convert(string, this.conversionType, Strings.trimToNull((String)object));
        }
        return TypeConverters.convert(string, this.conversionType, object);
    }
}

