package optfine;

public class ReflectorClass
{
    private boolean checked;
    private static final String[] I;
    private String targetClassName;
    private Class targetClass;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("o\u0016\u001d-'\"'\f$9nd;'*47X%$3d\b9.4!\u0016?qg", "GDxKK");
    }
    
    public String getTargetClassName() {
        return this.targetClassName;
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ReflectorClass(final Class targetClass) {
        this.targetClassName = null;
        this.checked = ("".length() != 0);
        this.targetClass = null;
        this.targetClass = targetClass;
        this.targetClassName = targetClass.getName();
        this.checked = (" ".length() != 0);
    }
    
    public ReflectorClass(final String targetClassName) {
        this.targetClassName = null;
        this.checked = ("".length() != 0);
        this.targetClass = null;
        this.targetClassName = targetClassName;
        this.getTargetClass();
    }
    
    public Class getTargetClass() {
        if (this.checked) {
            return this.targetClass;
        }
        this.checked = (" ".length() != 0);
        try {
            this.targetClass = Class.forName(this.targetClassName);
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        catch (ClassNotFoundException ex) {
            Config.log(ReflectorClass.I["".length()] + this.targetClassName);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        return this.targetClass;
    }
    
    static {
        I();
    }
    
    public boolean exists() {
        if (this.getTargetClass() != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
