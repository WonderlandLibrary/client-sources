/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.extensions.compactnotation;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.extensions.compactnotation.CompactConstructor;

public class PackageCompactConstructor
extends CompactConstructor {
    private final String packageName;

    public PackageCompactConstructor(String packageName) {
        super(new LoaderOptions());
        this.packageName = packageName;
    }

    @Override
    protected Class<?> getClassForName(String name) throws ClassNotFoundException {
        if (name.indexOf(46) < 0) {
            try {
                Class<?> clazz = Class.forName(this.packageName + "." + name);
                return clazz;
            } catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
        return super.getClassForName(name);
    }
}

