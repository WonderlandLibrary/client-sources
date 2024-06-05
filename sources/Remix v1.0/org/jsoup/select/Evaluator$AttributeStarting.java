package org.jsoup.select;

import org.jsoup.helper.*;
import org.jsoup.internal.*;
import org.jsoup.nodes.*;
import java.util.*;

public static final class AttributeStarting extends Evaluator
{
    private String keyPrefix;
    
    public AttributeStarting(final String keyPrefix) {
        Validate.notEmpty(keyPrefix);
        this.keyPrefix = Normalizer.lowerCase(keyPrefix);
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        final List<org.jsoup.nodes.Attribute> values = element.attributes().asList();
        for (final org.jsoup.nodes.Attribute attribute : values) {
            if (Normalizer.lowerCase(attribute.getKey()).startsWith(this.keyPrefix)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("[^%s]", this.keyPrefix);
    }
}
