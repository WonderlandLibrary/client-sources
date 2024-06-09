package org.spongepowered.asm.util;

static class TypeVar implements Comparable<TypeVar>
{
    private final String originalName;
    private String currentName;
    
    TypeVar(final String s) {
        super();
        this.originalName = s;
        this.currentName = s;
    }
    
    @Override
    public int compareTo(final TypeVar typeVar) {
        return this.currentName.compareTo(typeVar.currentName);
    }
    
    @Override
    public String toString() {
        return this.currentName;
    }
    
    String getOriginalName() {
        return this.originalName;
    }
    
    void rename(final String currentName) {
        this.currentName = currentName;
    }
    
    public boolean matches(final String s) {
        return this.originalName.equals(s);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this.currentName.equals(o);
    }
    
    @Override
    public int hashCode() {
        return this.currentName.hashCode();
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((TypeVar)o);
    }
}
