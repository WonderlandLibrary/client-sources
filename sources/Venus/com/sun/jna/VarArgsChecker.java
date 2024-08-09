/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import java.lang.reflect.Method;

abstract class VarArgsChecker {
    private VarArgsChecker() {
    }

    static VarArgsChecker create() {
        try {
            Method method = Method.class.getMethod("isVarArgs", new Class[0]);
            if (method != null) {
                return new RealVarArgsChecker(null);
            }
            return new NoVarArgsChecker(null);
        } catch (NoSuchMethodException noSuchMethodException) {
            return new NoVarArgsChecker(null);
        } catch (SecurityException securityException) {
            return new NoVarArgsChecker(null);
        }
    }

    abstract boolean isVarArgs(Method var1);

    abstract int fixedArgs(Method var1);

    VarArgsChecker(1 var1_1) {
        this();
    }

    private static final class NoVarArgsChecker
    extends VarArgsChecker {
        private NoVarArgsChecker() {
            super(null);
        }

        @Override
        boolean isVarArgs(Method method) {
            return true;
        }

        @Override
        int fixedArgs(Method method) {
            return 1;
        }

        NoVarArgsChecker(1 var1_1) {
            this();
        }
    }

    private static final class RealVarArgsChecker
    extends VarArgsChecker {
        private RealVarArgsChecker() {
            super(null);
        }

        @Override
        boolean isVarArgs(Method method) {
            return method.isVarArgs();
        }

        @Override
        int fixedArgs(Method method) {
            return method.isVarArgs() ? method.getParameterTypes().length - 1 : 0;
        }

        RealVarArgsChecker(1 var1_1) {
            this();
        }
    }
}

