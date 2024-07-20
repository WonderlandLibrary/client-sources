/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class SmtpUtils {
    static List<CharSequence> toUnmodifiableList(CharSequence ... sequences) {
        if (sequences == null || sequences.length == 0) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(Arrays.asList(sequences));
    }

    private SmtpUtils() {
    }
}

