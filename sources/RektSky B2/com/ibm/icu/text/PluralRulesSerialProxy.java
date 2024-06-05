package com.ibm.icu.text;

import java.io.*;

class PluralRulesSerialProxy implements Serializable
{
    private static final long serialVersionUID = 42L;
    private final String data;
    
    PluralRulesSerialProxy(final String rules) {
        this.data = rules;
    }
    
    private Object readResolve() throws ObjectStreamException {
        return PluralRules.createRules(this.data);
    }
}
