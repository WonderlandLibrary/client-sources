/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.math;

import java.io.Serializable;

public final class MathContext
implements Serializable {
    public static final int PLAIN = 0;
    public static final int SCIENTIFIC = 1;
    public static final int ENGINEERING = 2;
    public static final int ROUND_CEILING = 2;
    public static final int ROUND_DOWN = 1;
    public static final int ROUND_FLOOR = 3;
    public static final int ROUND_HALF_DOWN = 5;
    public static final int ROUND_HALF_EVEN = 6;
    public static final int ROUND_HALF_UP = 4;
    public static final int ROUND_UNNECESSARY = 7;
    public static final int ROUND_UP = 0;
    int digits;
    int form;
    boolean lostDigits;
    int roundingMode;
    private static final int DEFAULT_FORM = 1;
    private static final int DEFAULT_DIGITS = 9;
    private static final boolean DEFAULT_LOSTDIGITS = false;
    private static final int DEFAULT_ROUNDINGMODE = 4;
    private static final int MIN_DIGITS = 0;
    private static final int MAX_DIGITS = 999999999;
    private static final int[] ROUNDS = new int[]{4, 7, 2, 1, 3, 5, 6, 0};
    private static final String[] ROUNDWORDS = new String[]{"ROUND_HALF_UP", "ROUND_UNNECESSARY", "ROUND_CEILING", "ROUND_DOWN", "ROUND_FLOOR", "ROUND_HALF_DOWN", "ROUND_HALF_EVEN", "ROUND_UP"};
    private static final long serialVersionUID = 7163376998892515376L;
    public static final MathContext DEFAULT = new MathContext(9, 1, false, 4);

    public MathContext(int n) {
        this(n, 1, false, 4);
    }

    public MathContext(int n, int n2) {
        this(n, n2, false, 4);
    }

    public MathContext(int n, int n2, boolean bl) {
        this(n, n2, bl, 4);
    }

    public MathContext(int n, int n2, boolean bl, int n3) {
        if (n != 9) {
            if (n < 0) {
                throw new IllegalArgumentException("Digits too small: " + n);
            }
            if (n > 999999999) {
                throw new IllegalArgumentException("Digits too large: " + n);
            }
        }
        if (n2 != 1 && n2 != 2 && n2 != 0) {
            throw new IllegalArgumentException("Bad form value: " + n2);
        }
        if (!MathContext.isValidRound(n3)) {
            throw new IllegalArgumentException("Bad roundingMode value: " + n3);
        }
        this.digits = n;
        this.form = n2;
        this.lostDigits = bl;
        this.roundingMode = n3;
    }

    public int getDigits() {
        return this.digits;
    }

    public int getForm() {
        return this.form;
    }

    public boolean getLostDigits() {
        return this.lostDigits;
    }

    public int getRoundingMode() {
        return this.roundingMode;
    }

    public String toString() {
        String string = null;
        int n = 0;
        String string2 = null;
        string = this.form == 1 ? "SCIENTIFIC" : (this.form == 2 ? "ENGINEERING" : "PLAIN");
        int n2 = ROUNDS.length;
        n = 0;
        while (n2 > 0) {
            if (this.roundingMode == ROUNDS[n]) {
                string2 = ROUNDWORDS[n];
                break;
            }
            --n2;
            ++n;
        }
        return "digits=" + this.digits + " form=" + string + " lostDigits=" + (this.lostDigits ? "1" : "0") + " roundingMode=" + string2;
    }

    private static boolean isValidRound(int n) {
        int n2 = 0;
        int n3 = ROUNDS.length;
        n2 = 0;
        while (n3 > 0) {
            if (n == ROUNDS[n2]) {
                return false;
            }
            --n3;
            ++n2;
        }
        return true;
    }
}

