/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.pattern.TextRenderer;

public final class PlainTextRenderer
implements TextRenderer {
    private static final PlainTextRenderer INSTANCE = new PlainTextRenderer();

    public static PlainTextRenderer getInstance() {
        return INSTANCE;
    }

    @Override
    public void render(String string, StringBuilder stringBuilder, String string2) {
        stringBuilder.append(string);
    }

    @Override
    public void render(StringBuilder stringBuilder, StringBuilder stringBuilder2) {
        stringBuilder2.append((CharSequence)stringBuilder);
    }
}

