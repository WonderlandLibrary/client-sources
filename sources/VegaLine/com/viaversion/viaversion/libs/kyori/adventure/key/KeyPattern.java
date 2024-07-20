/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.intellij.lang.annotations.Pattern
 */
package com.viaversion.viaversion.libs.kyori.adventure.key;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.intellij.lang.annotations.Pattern;

@Documented
@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
@Pattern(value="(?:([a-z0-9_\\-.]+:)?|:)[a-z0-9_\\-./]+")
public @interface KeyPattern {

    @Documented
    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
    @Pattern(value="[a-z0-9_\\-./]+")
    public static @interface Value {
    }

    @Documented
    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
    @Pattern(value="[a-z0-9_\\-.]+")
    public static @interface Namespace {
    }
}

