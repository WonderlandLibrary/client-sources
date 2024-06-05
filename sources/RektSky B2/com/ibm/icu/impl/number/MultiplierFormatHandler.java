package com.ibm.icu.impl.number;

import com.ibm.icu.number.*;

public class MultiplierFormatHandler implements MicroPropsGenerator
{
    final Scale multiplier;
    final MicroPropsGenerator parent;
    
    public MultiplierFormatHandler(final Scale multiplier, final MicroPropsGenerator parent) {
        this.multiplier = multiplier;
        this.parent = parent;
    }
    
    @Override
    public MicroProps processQuantity(final DecimalQuantity quantity) {
        final MicroProps micros = this.parent.processQuantity(quantity);
        this.multiplier.applyTo(quantity);
        return micros;
    }
}
