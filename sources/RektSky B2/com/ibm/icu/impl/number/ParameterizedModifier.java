package com.ibm.icu.impl.number;

import com.ibm.icu.impl.*;

public class ParameterizedModifier
{
    private final Modifier positive;
    private final Modifier zero;
    private final Modifier negative;
    final Modifier[] mods;
    boolean frozen;
    
    public ParameterizedModifier(final Modifier positive, final Modifier zero, final Modifier negative) {
        this.positive = positive;
        this.zero = zero;
        this.negative = negative;
        this.mods = null;
        this.frozen = true;
    }
    
    public ParameterizedModifier() {
        this.positive = null;
        this.zero = null;
        this.negative = null;
        this.mods = new Modifier[3 * StandardPlural.COUNT];
        this.frozen = false;
    }
    
    public void setModifier(final int signum, final StandardPlural plural, final Modifier mod) {
        assert !this.frozen;
        this.mods[getModIndex(signum, plural)] = mod;
    }
    
    public void freeze() {
        this.frozen = true;
    }
    
    public Modifier getModifier(final int signum) {
        assert this.frozen;
        assert this.mods == null;
        return (signum == 0) ? this.zero : ((signum < 0) ? this.negative : this.positive);
    }
    
    public Modifier getModifier(final int signum, final StandardPlural plural) {
        assert this.frozen;
        assert this.positive == null;
        return this.mods[getModIndex(signum, plural)];
    }
    
    private static int getModIndex(final int signum, final StandardPlural plural) {
        return plural.ordinal() * 3 + (signum + 1);
    }
}
