package org.jsoup.select;

public static final class IsFirstOfType extends IsNthOfType
{
    public IsFirstOfType() {
        super(0, 1);
    }
    
    @Override
    public String toString() {
        return ":first-of-type";
    }
}
