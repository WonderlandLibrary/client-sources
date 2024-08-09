/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import org.apache.commons.lang3.Validate;

public class ClassPathUtils {
    public static String toFullyQualifiedName(Class<?> clazz, String string) {
        Validate.notNull(clazz, "Parameter '%s' must not be null!", "context");
        Validate.notNull(string, "Parameter '%s' must not be null!", "resourceName");
        return ClassPathUtils.toFullyQualifiedName(clazz.getPackage(), string);
    }

    public static String toFullyQualifiedName(Package package_, String string) {
        Validate.notNull(package_, "Parameter '%s' must not be null!", "context");
        Validate.notNull(string, "Parameter '%s' must not be null!", "resourceName");
        return package_.getName() + "." + string;
    }

    public static String toFullyQualifiedPath(Class<?> clazz, String string) {
        Validate.notNull(clazz, "Parameter '%s' must not be null!", "context");
        Validate.notNull(string, "Parameter '%s' must not be null!", "resourceName");
        return ClassPathUtils.toFullyQualifiedPath(clazz.getPackage(), string);
    }

    public static String toFullyQualifiedPath(Package package_, String string) {
        Validate.notNull(package_, "Parameter '%s' must not be null!", "context");
        Validate.notNull(string, "Parameter '%s' must not be null!", "resourceName");
        return package_.getName().replace('.', '/') + "/" + string;
    }
}

