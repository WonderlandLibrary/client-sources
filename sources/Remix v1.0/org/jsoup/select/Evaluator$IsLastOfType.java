package org.jsoup.select;

public static final class IsLastOfType extends IsNthLastOfType
{
    public IsLastOfType() {
        super(0, 1);
    }
    
    @Override
    public String toString() {
        return ":last-of-type";
    }
}
