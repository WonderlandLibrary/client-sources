/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.alt;

import java.util.ArrayList;
import java.util.List;
import digital.rbq.alt.Alt;

public final class AltManager {
    public static String apiKey;
    private final List<Alt> alts = new ArrayList<Alt>();

    public List<Alt> getAlts() {
        return this.alts;
    }
}

