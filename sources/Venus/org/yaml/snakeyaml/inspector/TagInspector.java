/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.inspector;

import org.yaml.snakeyaml.nodes.Tag;

public interface TagInspector {
    public boolean isGlobalTagAllowed(Tag var1);
}

