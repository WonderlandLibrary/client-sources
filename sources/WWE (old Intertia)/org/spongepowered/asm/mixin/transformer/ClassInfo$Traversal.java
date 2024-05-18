package org.spongepowered.asm.mixin.transformer;

public enum Traversal
{
    NONE((Traversal)null, false, SearchType.SUPER_CLASSES_ONLY), 
    ALL((Traversal)null, true, SearchType.ALL_CLASSES), 
    IMMEDIATE(Traversal.NONE, true, SearchType.SUPER_CLASSES_ONLY), 
    SUPER(Traversal.ALL, false, SearchType.SUPER_CLASSES_ONLY);
    
    private final Traversal next;
    private final boolean traverse;
    private final SearchType searchType;
    private static final Traversal[] $VALUES;
    
    public static Traversal[] values() {
        return Traversal.$VALUES.clone();
    }
    
    public static Traversal valueOf(final String s) {
        return Enum.valueOf(Traversal.class, s);
    }
    
    private Traversal(final Traversal traversal, final boolean traverse, final SearchType searchType) {
        this.next = ((traversal != null) ? traversal : this);
        this.traverse = traverse;
        this.searchType = searchType;
    }
    
    public Traversal next() {
        return this.next;
    }
    
    public boolean canTraverse() {
        return this.traverse;
    }
    
    public SearchType getSearchType() {
        return this.searchType;
    }
    
    static {
        $VALUES = new Traversal[] { Traversal.NONE, Traversal.ALL, Traversal.IMMEDIATE, Traversal.SUPER };
    }
}
