/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import java.lang.reflect.Method;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
abstract class VarArgsChecker {
    private VarArgsChecker() {
    }

    static VarArgsChecker create() {
        try {
            Method isVarArgsMethod = Method.class.getMethod("isVarArgs", new Class[0]);
            if (isVarArgsMethod != null) {
                return new RealVarArgsChecker();
            }
            return new NoVarArgsChecker();
        } catch (NoSuchMethodException e) {
            return new NoVarArgsChecker();
        } catch (SecurityException e) {
            return new NoVarArgsChecker();
        }
    }

    abstract boolean isVarArgs(Method var1);

    abstract int fixedArgs(Method var1);

    /*
     * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
     */
    private static final class NoVarArgsChecker
    extends VarArgsChecker {
        private NoVarArgsChecker() {
        }

        @Override
        boolean isVarArgs(Method m) {
            return false;
        }

        @Override
        int fixedArgs(Method m) {
            return 0;
        }
    }

    /*
     * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
     */
    private static final class RealVarArgsChecker
    extends VarArgsChecker {
        private RealVarArgsChecker() {
        }

        @Override
        boolean isVarArgs(Method m) {
            return m.isVarArgs();
        }

        @Override
        int fixedArgs(Method m) {
            return m.isVarArgs() ? m.getParameterTypes().length - 1 : 0;
        }
    }
}

