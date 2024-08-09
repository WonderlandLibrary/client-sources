/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.reflect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.optifine.reflect.IResolvable;

public class ReflectorResolver {
    private static final List<IResolvable> RESOLVABLES = Collections.synchronizedList(new ArrayList());
    private static boolean resolved = false;

    protected static void register(IResolvable iResolvable) {
        if (!resolved) {
            RESOLVABLES.add(iResolvable);
        } else {
            iResolvable.resolve();
        }
    }

    public static void resolve() {
        if (!resolved) {
            for (IResolvable iResolvable : RESOLVABLES) {
                iResolvable.resolve();
            }
            resolved = true;
        }
    }
}

