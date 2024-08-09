/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.impl.number.ModifierStore;

public class AdoptingModifierStore
implements ModifierStore {
    private final Modifier positive;
    private final Modifier zero;
    private final Modifier negative;
    final Modifier[] mods;
    boolean frozen;
    static final boolean $assertionsDisabled = !AdoptingModifierStore.class.desiredAssertionStatus();

    public AdoptingModifierStore(Modifier modifier, Modifier modifier2, Modifier modifier3) {
        this.positive = modifier;
        this.zero = modifier2;
        this.negative = modifier3;
        this.mods = null;
        this.frozen = true;
    }

    public AdoptingModifierStore() {
        this.positive = null;
        this.zero = null;
        this.negative = null;
        this.mods = new Modifier[3 * StandardPlural.COUNT];
        this.frozen = false;
    }

    public void setModifier(int n, StandardPlural standardPlural, Modifier modifier) {
        if (!$assertionsDisabled && this.frozen) {
            throw new AssertionError();
        }
        this.mods[AdoptingModifierStore.getModIndex((int)n, (StandardPlural)standardPlural)] = modifier;
    }

    public void freeze() {
        this.frozen = true;
    }

    public Modifier getModifierWithoutPlural(int n) {
        if (!$assertionsDisabled && !this.frozen) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mods != null) {
            throw new AssertionError();
        }
        return n == 0 ? this.zero : (n < 0 ? this.negative : this.positive);
    }

    @Override
    public Modifier getModifier(int n, StandardPlural standardPlural) {
        if (!$assertionsDisabled && !this.frozen) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.positive != null) {
            throw new AssertionError();
        }
        return this.mods[AdoptingModifierStore.getModIndex(n, standardPlural)];
    }

    private static int getModIndex(int n, StandardPlural standardPlural) {
        if (!($assertionsDisabled || n >= -1 && n <= 1)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && standardPlural == null) {
            throw new AssertionError();
        }
        return standardPlural.ordinal() * 3 + (n + 1);
    }
}

