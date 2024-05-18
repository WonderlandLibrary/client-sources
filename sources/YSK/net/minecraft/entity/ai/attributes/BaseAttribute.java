package net.minecraft.entity.ai.attributes;

public abstract class BaseAttribute implements IAttribute
{
    private boolean shouldWatch;
    private final double defaultValue;
    private static final String[] I;
    private final IAttribute field_180373_a;
    private final String unlocalizedName;
    
    static {
        I();
    }
    
    @Override
    public IAttribute func_180372_d() {
        return this.field_180373_a;
    }
    
    @Override
    public boolean getShouldWatch() {
        return this.shouldWatch;
    }
    
    public BaseAttribute setShouldWatch(final boolean shouldWatch) {
        this.shouldWatch = shouldWatch;
        return this;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0007\u0005\u0006\tG*\u0005\u0005\u0002\b=D\t\tG'\u0011\u0007\u0000F", "Idklg");
    }
    
    @Override
    public double getDefaultValue() {
        return this.defaultValue;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof IAttribute && this.unlocalizedName.equals(((IAttribute)o).getAttributeUnlocalizedName())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected BaseAttribute(final IAttribute field_180373_a, final String unlocalizedName, final double defaultValue) {
        this.field_180373_a = field_180373_a;
        this.unlocalizedName = unlocalizedName;
        this.defaultValue = defaultValue;
        if (unlocalizedName == null) {
            throw new IllegalArgumentException(BaseAttribute.I["".length()]);
        }
    }
    
    @Override
    public String getAttributeUnlocalizedName() {
        return this.unlocalizedName;
    }
    
    @Override
    public int hashCode() {
        return this.unlocalizedName.hashCode();
    }
}
