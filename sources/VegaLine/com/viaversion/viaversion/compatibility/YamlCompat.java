/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.compatibility;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.representer.Representer;

public interface YamlCompat {
    public Representer createRepresenter(DumperOptions var1);

    public SafeConstructor createSafeConstructor();

    public static boolean isVersion1() {
        try {
            SafeConstructor.class.getDeclaredConstructor(new Class[0]);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}

