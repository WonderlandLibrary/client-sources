/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class SmtpUtils {
    static List<CharSequence> toUnmodifiableList(CharSequence ... charSequenceArray) {
        if (charSequenceArray == null || charSequenceArray.length == 0) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(Arrays.asList(charSequenceArray));
    }

    private SmtpUtils() {
    }
}

