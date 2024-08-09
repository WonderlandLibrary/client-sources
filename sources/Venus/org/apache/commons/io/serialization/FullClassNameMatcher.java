/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.serialization;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.serialization.ClassNameMatcher;

final class FullClassNameMatcher
implements ClassNameMatcher {
    private final Set<String> classesSet;

    public FullClassNameMatcher(String ... stringArray) {
        this.classesSet = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(stringArray)));
    }

    @Override
    public boolean matches(String string) {
        return this.classesSet.contains(string);
    }
}

