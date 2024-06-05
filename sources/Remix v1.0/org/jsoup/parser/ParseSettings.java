package org.jsoup.parser;

import org.jsoup.internal.*;
import org.jsoup.nodes.*;
import java.util.*;

public class ParseSettings
{
    public static final ParseSettings htmlDefault;
    public static final ParseSettings preserveCase;
    private final boolean preserveTagCase;
    private final boolean preserveAttributeCase;
    
    public ParseSettings(final boolean tag, final boolean attribute) {
        this.preserveTagCase = tag;
        this.preserveAttributeCase = attribute;
    }
    
    String normalizeTag(String name) {
        name = name.trim();
        if (!this.preserveTagCase) {
            name = Normalizer.lowerCase(name);
        }
        return name;
    }
    
    String normalizeAttribute(String name) {
        name = name.trim();
        if (!this.preserveAttributeCase) {
            name = Normalizer.lowerCase(name);
        }
        return name;
    }
    
    Attributes normalizeAttributes(final Attributes attributes) {
        if (!this.preserveAttributeCase) {
            for (final Attribute attr : attributes) {
                attr.setKey(Normalizer.lowerCase(attr.getKey()));
            }
        }
        return attributes;
    }
    
    static {
        htmlDefault = new ParseSettings(false, false);
        preserveCase = new ParseSettings(true, true);
    }
}
