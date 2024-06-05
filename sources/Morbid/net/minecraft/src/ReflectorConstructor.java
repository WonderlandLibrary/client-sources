package net.minecraft.src;

import java.lang.reflect.*;

public class ReflectorConstructor
{
    private ReflectorClass reflectorClass;
    private Class[] parameterTypes;
    private boolean checked;
    private Constructor targetConstructor;
    
    public ReflectorConstructor(final ReflectorClass var1, final Class[] var2) {
        this.reflectorClass = null;
        this.parameterTypes = null;
        this.checked = false;
        this.targetConstructor = null;
        this.reflectorClass = var1;
        this.parameterTypes = var2;
        final Constructor var3 = this.getTargetConstructor();
    }
    
    public Constructor getTargetConstructor() {
        if (this.checked) {
            return this.targetConstructor;
        }
        this.checked = true;
        final Class var1 = this.reflectorClass.getTargetClass();
        if (var1 == null) {
            return null;
        }
        this.targetConstructor = findConstructor(var1, this.parameterTypes);
        if (this.targetConstructor == null) {
            Config.dbg("(Reflector) Constructor not present: " + var1.getName() + ", params: " + Config.arrayToString(this.parameterTypes));
        }
        return this.targetConstructor;
    }
    
    private static Constructor findConstructor(final Class var0, final Class[] var1) {
        final Constructor[] var2 = var0.getConstructors();
        for (int var3 = 0; var3 < var2.length; ++var3) {
            final Constructor var4 = var2[var3];
            final Class[] var5 = var4.getParameterTypes();
            if (Reflector.matchesTypes(var1, var5)) {
                return var4;
            }
        }
        return null;
    }
}
