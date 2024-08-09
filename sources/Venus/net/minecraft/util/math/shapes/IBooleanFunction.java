/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

public interface IBooleanFunction {
    public static final IBooleanFunction FALSE = IBooleanFunction::lambda$static$0;
    public static final IBooleanFunction NOT_OR = IBooleanFunction::lambda$static$1;
    public static final IBooleanFunction ONLY_SECOND = IBooleanFunction::lambda$static$2;
    public static final IBooleanFunction NOT_FIRST = IBooleanFunction::lambda$static$3;
    public static final IBooleanFunction ONLY_FIRST = IBooleanFunction::lambda$static$4;
    public static final IBooleanFunction NOT_SECOND = IBooleanFunction::lambda$static$5;
    public static final IBooleanFunction NOT_SAME = IBooleanFunction::lambda$static$6;
    public static final IBooleanFunction NOT_AND = IBooleanFunction::lambda$static$7;
    public static final IBooleanFunction AND = IBooleanFunction::lambda$static$8;
    public static final IBooleanFunction SAME = IBooleanFunction::lambda$static$9;
    public static final IBooleanFunction SECOND = IBooleanFunction::lambda$static$10;
    public static final IBooleanFunction CAUSES = IBooleanFunction::lambda$static$11;
    public static final IBooleanFunction FIRST = IBooleanFunction::lambda$static$12;
    public static final IBooleanFunction CAUSED_BY = IBooleanFunction::lambda$static$13;
    public static final IBooleanFunction OR = IBooleanFunction::lambda$static$14;
    public static final IBooleanFunction TRUE = IBooleanFunction::lambda$static$15;

    public boolean apply(boolean var1, boolean var2);

    private static boolean lambda$static$15(boolean bl, boolean bl2) {
        return false;
    }

    private static boolean lambda$static$14(boolean bl, boolean bl2) {
        return bl || bl2;
    }

    private static boolean lambda$static$13(boolean bl, boolean bl2) {
        return bl || !bl2;
    }

    private static boolean lambda$static$12(boolean bl, boolean bl2) {
        return bl;
    }

    private static boolean lambda$static$11(boolean bl, boolean bl2) {
        return !bl || bl2;
    }

    private static boolean lambda$static$10(boolean bl, boolean bl2) {
        return bl2;
    }

    private static boolean lambda$static$9(boolean bl, boolean bl2) {
        return bl == bl2;
    }

    private static boolean lambda$static$8(boolean bl, boolean bl2) {
        return bl && bl2;
    }

    private static boolean lambda$static$7(boolean bl, boolean bl2) {
        return !bl || !bl2;
    }

    private static boolean lambda$static$6(boolean bl, boolean bl2) {
        return bl != bl2;
    }

    private static boolean lambda$static$5(boolean bl, boolean bl2) {
        return !bl2;
    }

    private static boolean lambda$static$4(boolean bl, boolean bl2) {
        return bl && !bl2;
    }

    private static boolean lambda$static$3(boolean bl, boolean bl2) {
        return !bl;
    }

    private static boolean lambda$static$2(boolean bl, boolean bl2) {
        return bl2 && !bl;
    }

    private static boolean lambda$static$1(boolean bl, boolean bl2) {
        return !bl && !bl2;
    }

    private static boolean lambda$static$0(boolean bl, boolean bl2) {
        return true;
    }
}

