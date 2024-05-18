package org.spongepowered.asm.mixin.transformer;

abstract static class Member
{
    private final Type type;
    private final String memberName;
    private final String memberDesc;
    private final boolean isInjected;
    private final int modifiers;
    private String currentName;
    private String currentDesc;
    private boolean decoratedFinal;
    private boolean decoratedMutable;
    private boolean unique;
    
    protected Member(final Member member) {
        this(member.type, member.memberName, member.memberDesc, member.modifiers, member.isInjected);
        this.currentName = member.currentName;
        this.currentDesc = member.currentDesc;
        this.unique = member.unique;
    }
    
    protected Member(final Type type, final String s, final String s2, final int n) {
        this(type, s, s2, n, false);
    }
    
    protected Member(final Type type, final String s, final String s2, final int modifiers, final boolean isInjected) {
        super();
        this.type = type;
        this.memberName = s;
        this.memberDesc = s2;
        this.isInjected = isInjected;
        this.currentName = s;
        this.currentDesc = s2;
        this.modifiers = modifiers;
    }
    
    public String getOriginalName() {
        return this.memberName;
    }
    
    public String getName() {
        return this.currentName;
    }
    
    public String getOriginalDesc() {
        return this.memberDesc;
    }
    
    public String getDesc() {
        return this.currentDesc;
    }
    
    public boolean isInjected() {
        return this.isInjected;
    }
    
    public boolean isRenamed() {
        return !this.currentName.equals(this.memberName);
    }
    
    public boolean isRemapped() {
        return !this.currentDesc.equals(this.memberDesc);
    }
    
    public boolean isPrivate() {
        return (this.modifiers & 0x2) != 0x0;
    }
    
    public boolean isStatic() {
        return (this.modifiers & 0x8) != 0x0;
    }
    
    public boolean isAbstract() {
        return (this.modifiers & 0x400) != 0x0;
    }
    
    public boolean isFinal() {
        return (this.modifiers & 0x10) != 0x0;
    }
    
    public boolean isSynthetic() {
        return (this.modifiers & 0x1000) != 0x0;
    }
    
    public boolean isUnique() {
        return this.unique;
    }
    
    public void setUnique(final boolean unique) {
        this.unique = unique;
    }
    
    public boolean isDecoratedFinal() {
        return this.decoratedFinal;
    }
    
    public boolean isDecoratedMutable() {
        return this.decoratedMutable;
    }
    
    public void setDecoratedFinal(final boolean decoratedFinal, final boolean decoratedMutable) {
        this.decoratedFinal = decoratedFinal;
        this.decoratedMutable = decoratedMutable;
    }
    
    public boolean matchesFlags(final int n) {
        return ((~this.modifiers | (n & 0x2)) & 0x2) != 0x0 && ((~this.modifiers | (n & 0x8)) & 0x8) != 0x0;
    }
    
    public abstract ClassInfo getOwner();
    
    public ClassInfo getImplementor() {
        return this.getOwner();
    }
    
    public int getAccess() {
        return this.modifiers;
    }
    
    public String renameTo(final String currentName) {
        return this.currentName = currentName;
    }
    
    public String remapTo(final String currentDesc) {
        return this.currentDesc = currentDesc;
    }
    
    public boolean equals(final String s, final String s2) {
        return (this.memberName.equals(s) || this.currentName.equals(s)) && (this.memberDesc.equals(s2) || this.currentDesc.equals(s2));
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Member)) {
            return false;
        }
        final Member member = (Member)o;
        return (member.memberName.equals(this.memberName) || member.currentName.equals(this.currentName)) && (member.memberDesc.equals(this.memberDesc) || member.currentDesc.equals(this.currentDesc));
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    @Override
    public String toString() {
        return String.format(this.getDisplayFormat(), this.memberName, this.memberDesc);
    }
    
    protected String getDisplayFormat() {
        return "%s%s";
    }
    
    enum Type
    {
        METHOD, 
        FIELD;
        
        private static final Type[] $VALUES;
        
        public static Type[] values() {
            return Type.$VALUES.clone();
        }
        
        public static Type valueOf(final String s) {
            return Enum.valueOf(Type.class, s);
        }
        
        static {
            $VALUES = new Type[] { Type.METHOD, Type.FIELD };
        }
    }
}
