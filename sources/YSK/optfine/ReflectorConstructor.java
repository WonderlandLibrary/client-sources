package optfine;

import java.lang.reflect.*;

public class ReflectorConstructor
{
    private static final String[] I;
    private Class[] parameterTypes;
    private boolean checked;
    private Constructor targetConstructor;
    private ReflectorClass reflectorClass;
    
    public Constructor getTargetConstructor() {
        if (this.checked) {
            return this.targetConstructor;
        }
        this.checked = (" ".length() != 0);
        final Class targetClass = this.reflectorClass.getTargetClass();
        if (targetClass == null) {
            return null;
        }
        this.targetConstructor = findConstructor(targetClass, this.parameterTypes);
        if (this.targetConstructor == null) {
            Config.dbg(ReflectorConstructor.I["".length()] + targetClass.getName() + ReflectorConstructor.I[" ".length()] + Config.arrayToString(this.parameterTypes));
        }
        if (this.targetConstructor != null && !this.targetConstructor.isAccessible()) {
            this.targetConstructor.setAccessible(" ".length() != 0);
        }
        return this.targetConstructor;
    }
    
    static {
        I();
    }
    
    public void deactivate() {
        this.checked = (" ".length() != 0);
        this.targetConstructor = null;
    }
    
    private static Constructor findConstructor(final Class clazz, final Class[] array) {
        final Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        int i = "".length();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (i < declaredConstructors.length) {
            final Constructor constructor = declaredConstructors[i];
            if (Reflector.matchesTypes(array, constructor.getParameterTypes())) {
                return constructor;
            }
            ++i;
        }
        return null;
    }
    
    public ReflectorConstructor(final ReflectorClass reflectorClass, final Class[] parameterTypes) {
        this.reflectorClass = null;
        this.parameterTypes = null;
        this.checked = ("".length() != 0);
        this.targetConstructor = null;
        this.reflectorClass = reflectorClass;
        this.parameterTypes = parameterTypes;
        this.getTargetConstructor();
    }
    
    public boolean exists() {
        int n;
        if (this.checked) {
            if (this.targetConstructor != null) {
                n = " ".length();
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
        }
        else if (this.getTargetConstructor() != null) {
            n = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\\\u0010'\u00015\u0011!6\b+]b\u0001\b7\u000760\u0012:\u0000-0G7\u001b6b\u0017+\u00111'\t-Nb", "tBBgY");
        ReflectorConstructor.I[" ".length()] = I("xS;-45\u001e8vf", "TsKLF");
    }
}
