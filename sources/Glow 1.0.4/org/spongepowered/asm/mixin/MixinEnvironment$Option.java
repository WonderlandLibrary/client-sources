package org.spongepowered.asm.mixin;

public enum Option
{
    DEBUG_ALL("debug"), 
    DEBUG_EXPORT(Option.DEBUG_ALL, "export"), 
    DEBUG_EXPORT_FILTER(Option.DEBUG_EXPORT, "filter", false), 
    DEBUG_EXPORT_DECOMPILE(Option.DEBUG_EXPORT, Inherit.ALLOW_OVERRIDE, "decompile"), 
    DEBUG_EXPORT_DECOMPILE_THREADED(Option.DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, "async"), 
    DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES(Option.DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, "mergeGenericSignatures"), 
    DEBUG_VERIFY(Option.DEBUG_ALL, "verify"), 
    DEBUG_VERBOSE(Option.DEBUG_ALL, "verbose"), 
    DEBUG_INJECTORS(Option.DEBUG_ALL, "countInjections"), 
    DEBUG_STRICT(Option.DEBUG_ALL, Inherit.INDEPENDENT, "strict"), 
    DEBUG_UNIQUE(Option.DEBUG_STRICT, "unique"), 
    DEBUG_TARGETS(Option.DEBUG_STRICT, "targets"), 
    DEBUG_PROFILER(Option.DEBUG_ALL, Inherit.ALLOW_OVERRIDE, "profiler"), 
    DUMP_TARGET_ON_FAILURE("dumpTargetOnFailure"), 
    CHECK_ALL("checks"), 
    CHECK_IMPLEMENTS(Option.CHECK_ALL, "interfaces"), 
    CHECK_IMPLEMENTS_STRICT(Option.CHECK_IMPLEMENTS, Inherit.ALLOW_OVERRIDE, "strict"), 
    IGNORE_CONSTRAINTS("ignoreConstraints"), 
    HOT_SWAP("hotSwap"), 
    ENVIRONMENT(Inherit.ALWAYS_FALSE, "env"), 
    OBFUSCATION_TYPE(Option.ENVIRONMENT, Inherit.ALWAYS_FALSE, "obf"), 
    DISABLE_REFMAP(Option.ENVIRONMENT, Inherit.INDEPENDENT, "disableRefMap"), 
    REFMAP_REMAP(Option.ENVIRONMENT, Inherit.INDEPENDENT, "remapRefMap"), 
    REFMAP_REMAP_RESOURCE(Option.ENVIRONMENT, Inherit.INDEPENDENT, "refMapRemappingFile", ""), 
    REFMAP_REMAP_SOURCE_ENV(Option.ENVIRONMENT, Inherit.INDEPENDENT, "refMapRemappingEnv", "searge"), 
    REFMAP_REMAP_ALLOW_PERMISSIVE(Option.ENVIRONMENT, Inherit.INDEPENDENT, "allowPermissiveMatch", true, "true"), 
    IGNORE_REQUIRED(Option.ENVIRONMENT, Inherit.INDEPENDENT, "ignoreRequired"), 
    DEFAULT_COMPATIBILITY_LEVEL(Option.ENVIRONMENT, Inherit.INDEPENDENT, "compatLevel"), 
    SHIFT_BY_VIOLATION_BEHAVIOUR(Option.ENVIRONMENT, Inherit.INDEPENDENT, "shiftByViolation", "warn"), 
    INITIALISER_INJECTION_MODE("initialiserInjectionMode", "default");
    
    private static final String PREFIX = "mixin";
    final Option parent;
    final Inherit inheritance;
    final String property;
    final String defaultValue;
    final boolean isFlag;
    final int depth;
    private static final Option[] $VALUES;
    
    public static Option[] values() {
        return Option.$VALUES.clone();
    }
    
    public static Option valueOf(final String s) {
        return Enum.valueOf(Option.class, s);
    }
    
    private Option(final String s2) {
        this(null, s2, true);
    }
    
    private Option(final Inherit inherit, final String s2) {
        this(null, inherit, s2, true);
    }
    
    private Option(final String s2, final boolean b) {
        this(null, s2, b);
    }
    
    private Option(final String s2, final String s3) {
        this(null, Inherit.INDEPENDENT, s2, false, s3);
    }
    
    private Option(final Option option, final String s2) {
        this(option, Inherit.INHERIT, s2, true);
    }
    
    private Option(final Option option, final Inherit inherit, final String s2) {
        this(option, inherit, s2, true);
    }
    
    private Option(final Option option, final String s2, final boolean b) {
        this(option, Inherit.INHERIT, s2, b, null);
    }
    
    private Option(final Option option, final Inherit inherit, final String s2, final boolean b) {
        this(option, inherit, s2, b, null);
    }
    
    private Option(final Option option, final String s2, final String s3) {
        this(option, Inherit.INHERIT, s2, false, s3);
    }
    
    private Option(final Option option, final Inherit inherit, final String s2, final String s3) {
        this(option, inherit, s2, false, s3);
    }
    
    private Option(Option parent, final Inherit inheritance, final String s2, final boolean isFlag, final String defaultValue) {
        this.parent = parent;
        this.inheritance = inheritance;
        this.property = ((parent != null) ? parent.property : "mixin") + "." + s2;
        this.defaultValue = defaultValue;
        this.isFlag = isFlag;
        int depth;
        for (depth = 0; parent != null; parent = parent.parent, ++depth) {}
        this.depth = depth;
    }
    
    Option getParent() {
        return this.parent;
    }
    
    String getProperty() {
        return this.property;
    }
    
    @Override
    public String toString() {
        return this.isFlag ? String.valueOf(this.getBooleanValue()) : this.getStringValue();
    }
    
    private boolean getLocalBooleanValue(final boolean b) {
        return Boolean.parseBoolean(System.getProperty(this.property, Boolean.toString(b)));
    }
    
    private boolean getInheritedBooleanValue() {
        return this.parent != null && this.parent.getBooleanValue();
    }
    
    final boolean getBooleanValue() {
        if (this.inheritance == Inherit.ALWAYS_FALSE) {
            return false;
        }
        final boolean localBooleanValue = this.getLocalBooleanValue(false);
        if (this.inheritance == Inherit.INDEPENDENT) {
            return localBooleanValue;
        }
        final boolean b = localBooleanValue || this.getInheritedBooleanValue();
        return (this.inheritance == Inherit.INHERIT) ? b : this.getLocalBooleanValue(b);
    }
    
    final String getStringValue() {
        return (this.inheritance == Inherit.INDEPENDENT || this.parent == null || this.parent.getBooleanValue()) ? System.getProperty(this.property, this.defaultValue) : this.defaultValue;
    }
    
     <E extends Enum<E>> E getEnumValue(final E e) {
        final String property = System.getProperty(this.property, e.name());
        try {
            return Enum.valueOf(e.getClass(), property.toUpperCase());
        }
        catch (IllegalArgumentException ex) {
            return e;
        }
    }
    
    static {
        $VALUES = new Option[] { Option.DEBUG_ALL, Option.DEBUG_EXPORT, Option.DEBUG_EXPORT_FILTER, Option.DEBUG_EXPORT_DECOMPILE, Option.DEBUG_EXPORT_DECOMPILE_THREADED, Option.DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES, Option.DEBUG_VERIFY, Option.DEBUG_VERBOSE, Option.DEBUG_INJECTORS, Option.DEBUG_STRICT, Option.DEBUG_UNIQUE, Option.DEBUG_TARGETS, Option.DEBUG_PROFILER, Option.DUMP_TARGET_ON_FAILURE, Option.CHECK_ALL, Option.CHECK_IMPLEMENTS, Option.CHECK_IMPLEMENTS_STRICT, Option.IGNORE_CONSTRAINTS, Option.HOT_SWAP, Option.ENVIRONMENT, Option.OBFUSCATION_TYPE, Option.DISABLE_REFMAP, Option.REFMAP_REMAP, Option.REFMAP_REMAP_RESOURCE, Option.REFMAP_REMAP_SOURCE_ENV, Option.REFMAP_REMAP_ALLOW_PERMISSIVE, Option.IGNORE_REQUIRED, Option.DEFAULT_COMPATIBILITY_LEVEL, Option.SHIFT_BY_VIOLATION_BEHAVIOUR, Option.INITIALISER_INJECTION_MODE };
    }
    
    private enum Inherit
    {
        INHERIT, 
        ALLOW_OVERRIDE, 
        INDEPENDENT, 
        ALWAYS_FALSE;
        
        private static final Inherit[] $VALUES;
        
        public static Inherit[] values() {
            return Inherit.$VALUES.clone();
        }
        
        public static Inherit valueOf(final String s) {
            return Enum.valueOf(Inherit.class, s);
        }
        
        static {
            $VALUES = new Inherit[] { Inherit.INHERIT, Inherit.ALLOW_OVERRIDE, Inherit.INDEPENDENT, Inherit.ALWAYS_FALSE };
        }
    }
}
