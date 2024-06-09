package org.spongepowered.asm.mixin.transformer;

public enum SearchType
{
    ALL_CLASSES, 
    SUPER_CLASSES_ONLY;
    
    private static final SearchType[] $VALUES;
    
    public static SearchType[] values() {
        return SearchType.$VALUES.clone();
    }
    
    public static SearchType valueOf(final String s) {
        return Enum.valueOf(SearchType.class, s);
    }
    
    static {
        $VALUES = new SearchType[] { SearchType.ALL_CLASSES, SearchType.SUPER_CLASSES_ONLY };
    }
}
