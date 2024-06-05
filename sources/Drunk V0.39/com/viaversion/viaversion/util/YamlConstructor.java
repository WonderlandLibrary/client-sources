/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import java.util.concurrent.ConcurrentSkipListMap;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;

public class YamlConstructor
extends SafeConstructor {
    public YamlConstructor() {
        this.yamlClassConstructors.put(NodeId.mapping, new SafeConstructor.ConstructYamlMap(this));
        this.yamlConstructors.put(Tag.OMAP, new ConstructYamlOmap());
    }

    class ConstructYamlOmap
    extends SafeConstructor.ConstructYamlOmap {
        ConstructYamlOmap() {
            super(YamlConstructor.this);
        }

        @Override
        public Object construct(Node node) {
            Object o = super.construct(node);
            if (!(o instanceof Map)) return o;
            if (o instanceof ConcurrentSkipListMap) return o;
            return new ConcurrentSkipListMap((java.util.Map)o);
        }
    }

    class Map
    extends SafeConstructor.ConstructYamlMap {
        Map() {
            super(YamlConstructor.this);
        }

        @Override
        public Object construct(Node node) {
            Object o = super.construct(node);
            if (!(o instanceof Map)) return o;
            if (o instanceof ConcurrentSkipListMap) return o;
            return new ConcurrentSkipListMap((java.util.Map)o);
        }
    }
}

