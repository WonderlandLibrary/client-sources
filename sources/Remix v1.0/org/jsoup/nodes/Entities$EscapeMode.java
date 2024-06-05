package org.jsoup.nodes;

import java.util.*;

public enum EscapeMode
{
    xhtml("entities-xhtml.properties", 4), 
    base("entities-base.properties", 106), 
    extended("entities-full.properties", 2125);
    
    private String[] nameKeys;
    private int[] codeVals;
    private int[] codeKeys;
    private String[] nameVals;
    
    private EscapeMode(final String file, final int size) {
        Entities.access$000(this, file, size);
    }
    
    int codepointForName(final String name) {
        final int index = Arrays.binarySearch(this.nameKeys, name);
        return (index >= 0) ? this.codeVals[index] : -1;
    }
    
    String nameForCodepoint(final int codepoint) {
        final int index = Arrays.binarySearch(this.codeKeys, codepoint);
        if (index >= 0) {
            return (index < this.nameVals.length - 1 && this.codeKeys[index + 1] == codepoint) ? this.nameVals[index + 1] : this.nameVals[index];
        }
        return "";
    }
    
    private int size() {
        return this.nameKeys.length;
    }
}
