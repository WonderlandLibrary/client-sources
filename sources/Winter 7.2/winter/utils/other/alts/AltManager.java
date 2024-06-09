/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.other.alts;

import java.util.ArrayList;
import winter.utils.other.alts.Alt;

public class AltManager {
    public static Alt lastAlt;
    public static ArrayList<Alt> registry;

    static {
        registry = new ArrayList();
    }

    public ArrayList<Alt> getRegistry() {
        return registry;
    }

    public void setLastAlt(Alt alt2) {
        lastAlt = alt2;
    }
}

