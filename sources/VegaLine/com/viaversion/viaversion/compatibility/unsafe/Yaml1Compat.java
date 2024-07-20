/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.compatibility.unsafe;

import com.viaversion.viaversion.compatibility.YamlCompat;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

public final class Yaml1Compat
implements YamlCompat {
    @Override
    public Representer createRepresenter(DumperOptions dumperOptions) {
        return new Representer();
    }

    @Override
    public SafeConstructor createSafeConstructor() {
        return new CustomSafeConstructor();
    }

    private static final class CustomSafeConstructor
    extends SafeConstructor {
        public CustomSafeConstructor() {
            this.yamlClassConstructors.put(NodeId.mapping, new SafeConstructor.ConstructYamlMap());
            this.yamlConstructors.put(Tag.OMAP, new SafeConstructor.ConstructYamlOmap());
        }
    }
}

