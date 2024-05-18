package optfine;

import java.lang.reflect.*;

public class ReflectorMethod
{
    private ReflectorClass reflectorClass;
    private static final String[] I;
    private Class[] targetMethodParameterTypes;
    private Method targetMethod;
    private boolean checked;
    private String targetMethodName;
    
    public boolean exists() {
        int n;
        if (this.checked) {
            if (this.targetMethod != null) {
                n = " ".length();
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
        }
        else if (this.getTargetMethod() != null) {
            n = " ".length();
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public Class getReturnType() {
        final Method targetMethod = this.getTargetMethod();
        Class<?> returnType;
        if (targetMethod == null) {
            returnType = null;
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            returnType = targetMethod.getReturnType();
        }
        return returnType;
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
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ReflectorMethod(final ReflectorClass reflectorClass, final String s) {
        this(reflectorClass, s, null);
    }
    
    public ReflectorMethod(final ReflectorClass reflectorClass, final String targetMethodName, final Class[] targetMethodParameterTypes) {
        this.reflectorClass = null;
        this.targetMethodName = null;
        this.targetMethodParameterTypes = null;
        this.checked = ("".length() != 0);
        this.targetMethod = null;
        this.reflectorClass = reflectorClass;
        this.targetMethodName = targetMethodName;
        this.targetMethodParameterTypes = targetMethodParameterTypes;
        this.getTargetMethod();
    }
    
    static {
        I();
    }
    
    public Method getTargetMethod() {
        if (this.checked) {
            return this.targetMethod;
        }
        this.checked = (" ".length() != 0);
        final Class targetClass = this.reflectorClass.getTargetClass();
        if (targetClass == null) {
            return null;
        }
        final Method[] declaredMethods = targetClass.getDeclaredMethods();
        int i = "".length();
        while (i < declaredMethods.length) {
            final Method targetMethod = declaredMethods[i];
            Label_0174: {
                if (targetMethod.getName().equals(this.targetMethodName)) {
                    if (this.targetMethodParameterTypes == null) {
                        "".length();
                        if (-1 >= 1) {
                            throw null;
                        }
                    }
                    else {
                        if (!Reflector.matchesTypes(this.targetMethodParameterTypes, targetMethod.getParameterTypes())) {
                            break Label_0174;
                        }
                        "".length();
                        if (2 == 4) {
                            throw null;
                        }
                    }
                    this.targetMethod = targetMethod;
                    if (!this.targetMethod.isAccessible()) {
                        this.targetMethod.setAccessible(" ".length() != 0);
                    }
                    return this.targetMethod;
                }
            }
            ++i;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        Config.log(ReflectorMethod.I["".length()] + targetClass.getName() + ReflectorMethod.I[" ".length()] + this.targetMethodName);
        return null;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("^\u0016'+-\u0013'6\"3_d\u000f(5\u001e+&m/\u00190b=3\u00137'#5Ld", "vDBMA");
        ReflectorMethod.I[" ".length()] = I("x", "VPfPw");
    }
    
    public void deactivate() {
        this.checked = (" ".length() != 0);
        this.targetMethod = null;
    }
}
