package com.ibm.icu.impl;

import java.text.*;

public final class DontCareFieldPosition extends FieldPosition
{
    public static final DontCareFieldPosition INSTANCE;
    
    private DontCareFieldPosition() {
        super(-913028704);
    }
    
    @Override
    public void setBeginIndex(final int i) {
    }
    
    @Override
    public void setEndIndex(final int i) {
    }
    
    static {
        INSTANCE = new DontCareFieldPosition();
    }
}
