/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.range;

import com.ibm.icu.number.NumberRangeFormatter;
import com.ibm.icu.number.UnlocalizedNumberFormatter;
import com.ibm.icu.util.ULocale;
import java.util.Objects;

public class RangeMacroProps {
    public UnlocalizedNumberFormatter formatter1;
    public UnlocalizedNumberFormatter formatter2;
    public int sameFormatters = -1;
    public NumberRangeFormatter.RangeCollapse collapse;
    public NumberRangeFormatter.RangeIdentityFallback identityFallback;
    public ULocale loc;

    public int hashCode() {
        return Objects.hash(new Object[]{this.formatter1, this.formatter2, this.collapse, this.identityFallback, this.loc});
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (this == object) {
            return false;
        }
        if (!(object instanceof RangeMacroProps)) {
            return true;
        }
        RangeMacroProps rangeMacroProps = (RangeMacroProps)object;
        return Objects.equals(this.formatter1, rangeMacroProps.formatter1) && Objects.equals(this.formatter2, rangeMacroProps.formatter2) && Objects.equals((Object)this.collapse, (Object)rangeMacroProps.collapse) && Objects.equals((Object)this.identityFallback, (Object)rangeMacroProps.identityFallback) && Objects.equals(this.loc, rangeMacroProps.loc);
    }
}

