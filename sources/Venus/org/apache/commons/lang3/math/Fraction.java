/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.math;

import java.math.BigInteger;

public final class Fraction
extends Number
implements Comparable<Fraction> {
    private static final long serialVersionUID = 65382027393090L;
    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);
    public static final Fraction ONE_HALF = new Fraction(1, 2);
    public static final Fraction ONE_THIRD = new Fraction(1, 3);
    public static final Fraction TWO_THIRDS = new Fraction(2, 3);
    public static final Fraction ONE_QUARTER = new Fraction(1, 4);
    public static final Fraction TWO_QUARTERS = new Fraction(2, 4);
    public static final Fraction THREE_QUARTERS = new Fraction(3, 4);
    public static final Fraction ONE_FIFTH = new Fraction(1, 5);
    public static final Fraction TWO_FIFTHS = new Fraction(2, 5);
    public static final Fraction THREE_FIFTHS = new Fraction(3, 5);
    public static final Fraction FOUR_FIFTHS = new Fraction(4, 5);
    private final int numerator;
    private final int denominator;
    private transient int hashCode = 0;
    private transient String toString = null;
    private transient String toProperString = null;

    private Fraction(int n, int n2) {
        this.numerator = n;
        this.denominator = n2;
    }

    public static Fraction getFraction(int n, int n2) {
        if (n2 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (n2 < 0) {
            if (n == Integer.MIN_VALUE || n2 == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: can't negate");
            }
            n = -n;
            n2 = -n2;
        }
        return new Fraction(n, n2);
    }

    public static Fraction getFraction(int n, int n2, int n3) {
        if (n3 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (n3 < 0) {
            throw new ArithmeticException("The denominator must not be negative");
        }
        if (n2 < 0) {
            throw new ArithmeticException("The numerator must not be negative");
        }
        long l = n < 0 ? (long)n * (long)n3 - (long)n2 : (long)n * (long)n3 + (long)n2;
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new ArithmeticException("Numerator too large to represent as an Integer.");
        }
        return new Fraction((int)l, n3);
    }

    public static Fraction getReducedFraction(int n, int n2) {
        if (n2 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (n == 0) {
            return ZERO;
        }
        if (n2 == Integer.MIN_VALUE && (n & 1) == 0) {
            n /= 2;
            n2 /= 2;
        }
        if (n2 < 0) {
            if (n == Integer.MIN_VALUE || n2 == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: can't negate");
            }
            n = -n;
            n2 = -n2;
        }
        int n3 = Fraction.greatestCommonDivisor(n, n2);
        return new Fraction(n /= n3, n2 /= n3);
    }

    public static Fraction getFraction(double d) {
        double d2;
        int n = d < 0.0 ? -1 : 1;
        if ((d = Math.abs(d)) > 2.147483647E9 || Double.isNaN(d)) {
            throw new ArithmeticException("The value must not be greater than Integer.MAX_VALUE or NaN");
        }
        int n2 = (int)d;
        d -= (double)n2;
        int n3 = 0;
        int n4 = 1;
        int n5 = 1;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = (int)d;
        int n10 = 0;
        double d3 = 1.0;
        double d4 = 0.0;
        double d5 = d - (double)n9;
        double d6 = 0.0;
        double d7 = Double.MAX_VALUE;
        int n11 = 1;
        do {
            d2 = d7;
            n10 = (int)(d3 / d5);
            d4 = d5;
            d6 = d3 - (double)n10 * d5;
            n7 = n9 * n5 + n3;
            n8 = n9 * n6 + n4;
            double d8 = (double)n7 / (double)n8;
            d7 = Math.abs(d - d8);
            n9 = n10;
            d3 = d4;
            d5 = d6;
            n3 = n5;
            n4 = n6;
            n5 = n7;
            n6 = n8;
        } while (d2 > d7 && n8 <= 10000 && n8 > 0 && ++n11 < 25);
        if (n11 == 25) {
            throw new ArithmeticException("Unable to convert double to fraction");
        }
        return Fraction.getReducedFraction((n3 + n2 * n4) * n, n4);
    }

    public static Fraction getFraction(String string) {
        if (string == null) {
            throw new IllegalArgumentException("The string must not be null");
        }
        int n = string.indexOf(46);
        if (n >= 0) {
            return Fraction.getFraction(Double.parseDouble(string));
        }
        n = string.indexOf(32);
        if (n > 0) {
            int n2 = Integer.parseInt(string.substring(0, n));
            if ((n = (string = string.substring(n + 1)).indexOf(47)) < 0) {
                throw new NumberFormatException("The fraction could not be parsed as the format X Y/Z");
            }
            int n3 = Integer.parseInt(string.substring(0, n));
            int n4 = Integer.parseInt(string.substring(n + 1));
            return Fraction.getFraction(n2, n3, n4);
        }
        n = string.indexOf(47);
        if (n < 0) {
            return Fraction.getFraction(Integer.parseInt(string), 1);
        }
        int n5 = Integer.parseInt(string.substring(0, n));
        int n6 = Integer.parseInt(string.substring(n + 1));
        return Fraction.getFraction(n5, n6);
    }

    public int getNumerator() {
        return this.numerator;
    }

    public int getDenominator() {
        return this.denominator;
    }

    public int getProperNumerator() {
        return Math.abs(this.numerator % this.denominator);
    }

    public int getProperWhole() {
        return this.numerator / this.denominator;
    }

    @Override
    public int intValue() {
        return this.numerator / this.denominator;
    }

    @Override
    public long longValue() {
        return (long)this.numerator / (long)this.denominator;
    }

    @Override
    public float floatValue() {
        return (float)this.numerator / (float)this.denominator;
    }

    @Override
    public double doubleValue() {
        return (double)this.numerator / (double)this.denominator;
    }

    public Fraction reduce() {
        if (this.numerator == 0) {
            return this.equals(ZERO) ? this : ZERO;
        }
        int n = Fraction.greatestCommonDivisor(Math.abs(this.numerator), this.denominator);
        if (n == 1) {
            return this;
        }
        return Fraction.getFraction(this.numerator / n, this.denominator / n);
    }

    public Fraction invert() {
        if (this.numerator == 0) {
            throw new ArithmeticException("Unable to invert zero.");
        }
        if (this.numerator == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow: can't negate numerator");
        }
        if (this.numerator < 0) {
            return new Fraction(-this.denominator, -this.numerator);
        }
        return new Fraction(this.denominator, this.numerator);
    }

    public Fraction negate() {
        if (this.numerator == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow: too large to negate");
        }
        return new Fraction(-this.numerator, this.denominator);
    }

    public Fraction abs() {
        if (this.numerator >= 0) {
            return this;
        }
        return this.negate();
    }

    public Fraction pow(int n) {
        if (n == 1) {
            return this;
        }
        if (n == 0) {
            return ONE;
        }
        if (n < 0) {
            if (n == Integer.MIN_VALUE) {
                return this.invert().pow(2).pow(-(n / 2));
            }
            return this.invert().pow(-n);
        }
        Fraction fraction = this.multiplyBy(this);
        if (n % 2 == 0) {
            return fraction.pow(n / 2);
        }
        return fraction.pow(n / 2).multiplyBy(this);
    }

    private static int greatestCommonDivisor(int n, int n2) {
        int n3;
        int n4;
        if (n == 0 || n2 == 0) {
            if (n == Integer.MIN_VALUE || n2 == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: gcd is 2^31");
            }
            return Math.abs(n) + Math.abs(n2);
        }
        if (Math.abs(n) == 1 || Math.abs(n2) == 1) {
            return 0;
        }
        if (n > 0) {
            n = -n;
        }
        if (n2 > 0) {
            n2 = -n2;
        }
        for (n4 = 0; (n & 1) == 0 && (n2 & 1) == 0 && n4 < 31; ++n4) {
            n /= 2;
            n2 /= 2;
        }
        if (n4 == 31) {
            throw new ArithmeticException("overflow: gcd is 2^31");
        }
        int n5 = n3 = (n & 1) == 1 ? n2 : -(n / 2);
        while (true) {
            if ((n3 & 1) == 0) {
                n3 /= 2;
                continue;
            }
            if (n3 > 0) {
                n = -n3;
            } else {
                n2 = n3;
            }
            if ((n3 = (n2 - n) / 2) == 0) break;
        }
        return -n * (1 << n4);
    }

    private static int mulAndCheck(int n, int n2) {
        long l = (long)n * (long)n2;
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new ArithmeticException("overflow: mul");
        }
        return (int)l;
    }

    private static int mulPosAndCheck(int n, int n2) {
        long l = (long)n * (long)n2;
        if (l > Integer.MAX_VALUE) {
            throw new ArithmeticException("overflow: mulPos");
        }
        return (int)l;
    }

    private static int addAndCheck(int n, int n2) {
        long l = (long)n + (long)n2;
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new ArithmeticException("overflow: add");
        }
        return (int)l;
    }

    private static int subAndCheck(int n, int n2) {
        long l = (long)n - (long)n2;
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new ArithmeticException("overflow: add");
        }
        return (int)l;
    }

    public Fraction add(Fraction fraction) {
        return this.addSub(fraction, true);
    }

    public Fraction subtract(Fraction fraction) {
        return this.addSub(fraction, false);
    }

    private Fraction addSub(Fraction fraction, boolean bl) {
        int n;
        int n2;
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        }
        if (this.numerator == 0) {
            return bl ? fraction : fraction.negate();
        }
        if (fraction.numerator == 0) {
            return this;
        }
        int n3 = Fraction.greatestCommonDivisor(this.denominator, fraction.denominator);
        if (n3 == 1) {
            int n4 = Fraction.mulAndCheck(this.numerator, fraction.denominator);
            int n5 = Fraction.mulAndCheck(fraction.numerator, this.denominator);
            return new Fraction(bl ? Fraction.addAndCheck(n4, n5) : Fraction.subAndCheck(n4, n5), Fraction.mulPosAndCheck(this.denominator, fraction.denominator));
        }
        BigInteger bigInteger = BigInteger.valueOf(this.numerator).multiply(BigInteger.valueOf(fraction.denominator / n3));
        BigInteger bigInteger2 = BigInteger.valueOf(fraction.numerator).multiply(BigInteger.valueOf(this.denominator / n3));
        BigInteger bigInteger3 = bl ? bigInteger.add(bigInteger2) : bigInteger.subtract(bigInteger2);
        BigInteger bigInteger4 = bigInteger3.divide(BigInteger.valueOf(n2 = (n = bigInteger3.mod(BigInteger.valueOf(n3)).intValue()) == 0 ? n3 : Fraction.greatestCommonDivisor(n, n3)));
        if (bigInteger4.bitLength() > 31) {
            throw new ArithmeticException("overflow: numerator too large after multiply");
        }
        return new Fraction(bigInteger4.intValue(), Fraction.mulPosAndCheck(this.denominator / n3, fraction.denominator / n2));
    }

    public Fraction multiplyBy(Fraction fraction) {
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        }
        if (this.numerator == 0 || fraction.numerator == 0) {
            return ZERO;
        }
        int n = Fraction.greatestCommonDivisor(this.numerator, fraction.denominator);
        int n2 = Fraction.greatestCommonDivisor(fraction.numerator, this.denominator);
        return Fraction.getReducedFraction(Fraction.mulAndCheck(this.numerator / n, fraction.numerator / n2), Fraction.mulPosAndCheck(this.denominator / n2, fraction.denominator / n));
    }

    public Fraction divideBy(Fraction fraction) {
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        }
        if (fraction.numerator == 0) {
            throw new ArithmeticException("The fraction to divide by must not be zero");
        }
        return this.multiplyBy(fraction.invert());
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof Fraction)) {
            return true;
        }
        Fraction fraction = (Fraction)object;
        return this.getNumerator() == fraction.getNumerator() && this.getDenominator() == fraction.getDenominator();
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = 37 * (629 + this.getNumerator()) + this.getDenominator();
        }
        return this.hashCode;
    }

    @Override
    public int compareTo(Fraction fraction) {
        if (this == fraction) {
            return 1;
        }
        if (this.numerator == fraction.numerator && this.denominator == fraction.denominator) {
            return 1;
        }
        long l = (long)this.numerator * (long)fraction.denominator;
        long l2 = (long)fraction.numerator * (long)this.denominator;
        if (l == l2) {
            return 1;
        }
        if (l < l2) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        if (this.toString == null) {
            this.toString = this.getNumerator() + "/" + this.getDenominator();
        }
        return this.toString;
    }

    public String toProperString() {
        if (this.toProperString == null) {
            int n;
            this.toProperString = this.numerator == 0 ? "0" : (this.numerator == this.denominator ? "1" : (this.numerator == -1 * this.denominator ? "-1" : ((this.numerator > 0 ? -this.numerator : this.numerator) < -this.denominator ? ((n = this.getProperNumerator()) == 0 ? Integer.toString(this.getProperWhole()) : this.getProperWhole() + " " + n + "/" + this.getDenominator()) : this.getNumerator() + "/" + this.getDenominator())));
        }
        return this.toProperString;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Fraction)object);
    }
}

