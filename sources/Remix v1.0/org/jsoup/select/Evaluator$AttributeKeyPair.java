package org.jsoup.select;

import org.jsoup.helper.*;
import org.jsoup.internal.*;

public abstract static class AttributeKeyPair extends Evaluator
{
    String key;
    String value;
    
    public AttributeKeyPair(final String key, String value) {
        Validate.notEmpty(key);
        Validate.notEmpty(value);
        this.key = Normalizer.normalize(key);
        if ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'"))) {
            value = value.substring(1, value.length() - 1);
        }
        this.value = Normalizer.normalize(value);
    }
}
