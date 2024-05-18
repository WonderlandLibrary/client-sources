package optfine;

import java.lang.reflect.*;

public class ReflectorField
{
    private ReflectorClass reflectorClass;
    private Field targetField;
    private String targetFieldName;
    private boolean checked;
    private static final String[] I;
    
    public Field getTargetField() {
        if (this.checked) {
            return this.targetField;
        }
        this.checked = (" ".length() != 0);
        final Class targetClass = this.reflectorClass.getTargetClass();
        if (targetClass == null) {
            return null;
        }
        try {
            this.targetField = targetClass.getDeclaredField(this.targetFieldName);
            if (!this.targetField.isAccessible()) {
                this.targetField.setAccessible(" ".length() != 0);
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
        }
        catch (SecurityException ex) {
            ex.printStackTrace();
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldException ex2) {
            Config.log(ReflectorField.I["".length()] + targetClass.getName() + ReflectorField.I[" ".length()] + this.targetFieldName);
        }
        return this.targetField;
    }
    
    public ReflectorField(final ReflectorClass reflectorClass, final String targetFieldName) {
        this.reflectorClass = null;
        this.targetFieldName = null;
        this.checked = ("".length() != 0);
        this.targetField = null;
        this.reflectorClass = reflectorClass;
        this.targetFieldName = targetFieldName;
        this.getTargetField();
    }
    
    static {
        I();
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setValue(final Object o) {
        Reflector.setFieldValue(null, this, o);
    }
    
    public boolean exists() {
        int n;
        if (this.checked) {
            if (this.targetField != null) {
                n = " ".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
        }
        else if (this.getTargetField() != null) {
            n = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("K\u0011+\u00109\u0006 :\u0019'Jc\b\u001f0\u000f'n\u0018:\u0017c>\u00040\u0010& \u0002oC", "cCNvU");
        ReflectorField.I[" ".length()] = I("T", "zLIuo");
    }
    
    public Object getValue() {
        return Reflector.getFieldValue(null, this);
    }
}
