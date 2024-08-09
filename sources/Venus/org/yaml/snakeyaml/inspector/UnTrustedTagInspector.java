/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.inspector;

import org.yaml.snakeyaml.inspector.TagInspector;
import org.yaml.snakeyaml.nodes.Tag;

public final class UnTrustedTagInspector
implements TagInspector {
    @Override
    public boolean isGlobalTagAllowed(Tag tag) {
        return true;
    }
}

