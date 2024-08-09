/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import com.ibm.icu.text.UTF16;

public class BidiTransform {
    private Bidi bidi;
    private String text;
    private int reorderingOptions;
    private int shapingOptions;

    public String transform(CharSequence charSequence, byte by, Order order, byte by2, Order order2, Mirroring mirroring, int n) {
        if (charSequence == null || order == null || order2 == null || mirroring == null) {
            throw new IllegalArgumentException();
        }
        this.text = charSequence.toString();
        byte[] byArray = new byte[]{by, by2};
        this.resolveBaseDirection(byArray);
        ReorderingScheme reorderingScheme = this.findMatchingScheme(byArray[0], order, byArray[1], order2);
        if (reorderingScheme != null) {
            this.bidi = new Bidi();
            this.reorderingOptions = Mirroring.ON.equals((Object)mirroring) ? 2 : 0;
            this.shapingOptions = n & 0xFFFFFFFB;
            reorderingScheme.doTransform(this);
        }
        return this.text;
    }

    private void resolveBaseDirection(byte[] byArray) {
        byte by;
        byArray[0] = Bidi.IsDefaultLevel(byArray[0]) ? ((by = Bidi.getBaseDirection(this.text)) != 3 ? by : (byArray[0] == 127 ? (byte)1 : 0)) : (byte)(byArray[0] & 1);
        byArray[1] = Bidi.IsDefaultLevel(byArray[1]) ? byArray[0] : (byte)(byArray[1] & 1);
    }

    private ReorderingScheme findMatchingScheme(byte by, Order order, byte by2, Order order2) {
        for (ReorderingScheme reorderingScheme : ReorderingScheme.values()) {
            if (!reorderingScheme.matches(by, order, by2, order2)) continue;
            return reorderingScheme;
        }
        return null;
    }

    private void resolve(byte by, int n) {
        this.bidi.setInverse((n & 5) != 0);
        this.bidi.setReorderingMode(n);
        this.bidi.setPara(this.text, by, null);
    }

    private void reorder() {
        this.text = this.bidi.writeReordered(this.reorderingOptions);
        this.reorderingOptions = 0;
    }

    private void reverse() {
        this.text = Bidi.writeReverse(this.text, 0);
    }

    private void mirror() {
        int n;
        if ((this.reorderingOptions & 2) == 0) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer(this.text);
        byte[] byArray = this.bidi.getLevels();
        int n2 = byArray.length;
        for (int i = 0; i < n2; i += UTF16.getCharCount(n)) {
            n = UTF16.charAt(stringBuffer, i);
            if ((byArray[i] & 1) == 0) continue;
            UTF16.setCharAt(stringBuffer, i, UCharacter.getMirror(n));
        }
        this.text = stringBuffer.toString();
        this.reorderingOptions &= 0xFFFFFFFD;
    }

    private void shapeArabic(int n, int n2) {
        if (n == n2) {
            this.shapeArabic(this.shapingOptions | n);
        } else {
            this.shapeArabic(this.shapingOptions & 0xFFFFFFE7 | n);
            this.shapeArabic(this.shapingOptions & 0xFFFFFF1F | n2);
        }
    }

    private void shapeArabic(int n) {
        if (n != 0) {
            ArabicShaping arabicShaping = new ArabicShaping(n);
            try {
                this.text = arabicShaping.shape(this.text);
            } catch (ArabicShapingException arabicShapingException) {
                // empty catch block
            }
        }
    }

    private static boolean IsLTR(byte by) {
        return (by & 1) == 0;
    }

    private static boolean IsRTL(byte by) {
        return (by & 1) == 1;
    }

    private static boolean IsLogical(Order order) {
        return Order.LOGICAL.equals((Object)order);
    }

    private static boolean IsVisual(Order order) {
        return Order.VISUAL.equals((Object)order);
    }

    static boolean access$100(byte by) {
        return BidiTransform.IsLTR(by);
    }

    static boolean access$200(Order order) {
        return BidiTransform.IsLogical(order);
    }

    static boolean access$300(Order order) {
        return BidiTransform.IsVisual(order);
    }

    static void access$400(BidiTransform bidiTransform, int n, int n2) {
        bidiTransform.shapeArabic(n, n2);
    }

    static void access$500(BidiTransform bidiTransform, byte by, int n) {
        bidiTransform.resolve(by, n);
    }

    static void access$600(BidiTransform bidiTransform) {
        bidiTransform.reorder();
    }

    static boolean access$700(byte by) {
        return BidiTransform.IsRTL(by);
    }

    static void access$800(BidiTransform bidiTransform) {
        bidiTransform.reverse();
    }

    static void access$900(BidiTransform bidiTransform) {
        bidiTransform.mirror();
    }

    private static enum ReorderingScheme {
        LOG_LTR_TO_VIS_LTR{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$100(by) && BidiTransform.access$200(order) && BidiTransform.access$100(by2) && BidiTransform.access$300(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$400(bidiTransform, 0, 0);
                BidiTransform.access$500(bidiTransform, (byte)0, 0);
                BidiTransform.access$600(bidiTransform);
            }
        }
        ,
        LOG_RTL_TO_VIS_LTR{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$700(by) && BidiTransform.access$200(order) && BidiTransform.access$100(by2) && BidiTransform.access$300(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$500(bidiTransform, (byte)1, 0);
                BidiTransform.access$600(bidiTransform);
                BidiTransform.access$400(bidiTransform, 0, 4);
            }
        }
        ,
        LOG_LTR_TO_VIS_RTL{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$100(by) && BidiTransform.access$200(order) && BidiTransform.access$700(by2) && BidiTransform.access$300(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$400(bidiTransform, 0, 0);
                BidiTransform.access$500(bidiTransform, (byte)0, 0);
                BidiTransform.access$600(bidiTransform);
                BidiTransform.access$800(bidiTransform);
            }
        }
        ,
        LOG_RTL_TO_VIS_RTL{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$700(by) && BidiTransform.access$200(order) && BidiTransform.access$700(by2) && BidiTransform.access$300(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$500(bidiTransform, (byte)1, 0);
                BidiTransform.access$600(bidiTransform);
                BidiTransform.access$400(bidiTransform, 0, 4);
                BidiTransform.access$800(bidiTransform);
            }
        }
        ,
        VIS_LTR_TO_LOG_RTL{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$100(by) && BidiTransform.access$300(order) && BidiTransform.access$700(by2) && BidiTransform.access$200(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$400(bidiTransform, 0, 4);
                BidiTransform.access$500(bidiTransform, (byte)1, 5);
                BidiTransform.access$600(bidiTransform);
            }
        }
        ,
        VIS_RTL_TO_LOG_RTL{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$700(by) && BidiTransform.access$300(order) && BidiTransform.access$700(by2) && BidiTransform.access$200(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$800(bidiTransform);
                BidiTransform.access$400(bidiTransform, 0, 4);
                BidiTransform.access$500(bidiTransform, (byte)1, 5);
                BidiTransform.access$600(bidiTransform);
            }
        }
        ,
        VIS_LTR_TO_LOG_LTR{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$100(by) && BidiTransform.access$300(order) && BidiTransform.access$100(by2) && BidiTransform.access$200(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$500(bidiTransform, (byte)0, 5);
                BidiTransform.access$600(bidiTransform);
                BidiTransform.access$400(bidiTransform, 0, 0);
            }
        }
        ,
        VIS_RTL_TO_LOG_LTR{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$700(by) && BidiTransform.access$300(order) && BidiTransform.access$100(by2) && BidiTransform.access$200(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$800(bidiTransform);
                BidiTransform.access$500(bidiTransform, (byte)0, 5);
                BidiTransform.access$600(bidiTransform);
                BidiTransform.access$400(bidiTransform, 0, 0);
            }
        }
        ,
        LOG_LTR_TO_LOG_RTL{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$100(by) && BidiTransform.access$200(order) && BidiTransform.access$700(by2) && BidiTransform.access$200(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$400(bidiTransform, 0, 0);
                BidiTransform.access$500(bidiTransform, (byte)0, 0);
                BidiTransform.access$900(bidiTransform);
                BidiTransform.access$500(bidiTransform, (byte)0, 3);
                BidiTransform.access$600(bidiTransform);
            }
        }
        ,
        LOG_RTL_TO_LOG_LTR{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$700(by) && BidiTransform.access$200(order) && BidiTransform.access$100(by2) && BidiTransform.access$200(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$500(bidiTransform, (byte)1, 0);
                BidiTransform.access$900(bidiTransform);
                BidiTransform.access$500(bidiTransform, (byte)1, 3);
                BidiTransform.access$600(bidiTransform);
                BidiTransform.access$400(bidiTransform, 0, 0);
            }
        }
        ,
        VIS_LTR_TO_VIS_RTL{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$100(by) && BidiTransform.access$300(order) && BidiTransform.access$700(by2) && BidiTransform.access$300(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$500(bidiTransform, (byte)0, 0);
                BidiTransform.access$900(bidiTransform);
                BidiTransform.access$400(bidiTransform, 0, 4);
                BidiTransform.access$800(bidiTransform);
            }
        }
        ,
        VIS_RTL_TO_VIS_LTR{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$700(by) && BidiTransform.access$300(order) && BidiTransform.access$100(by2) && BidiTransform.access$300(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$800(bidiTransform);
                BidiTransform.access$500(bidiTransform, (byte)0, 0);
                BidiTransform.access$900(bidiTransform);
                BidiTransform.access$400(bidiTransform, 0, 4);
            }
        }
        ,
        LOG_LTR_TO_LOG_LTR{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$100(by) && BidiTransform.access$200(order) && BidiTransform.access$100(by2) && BidiTransform.access$200(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$500(bidiTransform, (byte)0, 0);
                BidiTransform.access$900(bidiTransform);
                BidiTransform.access$400(bidiTransform, 0, 0);
            }
        }
        ,
        LOG_RTL_TO_LOG_RTL{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$700(by) && BidiTransform.access$200(order) && BidiTransform.access$700(by2) && BidiTransform.access$200(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$500(bidiTransform, (byte)1, 0);
                BidiTransform.access$900(bidiTransform);
                BidiTransform.access$400(bidiTransform, 4, 0);
            }
        }
        ,
        VIS_LTR_TO_VIS_LTR{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$100(by) && BidiTransform.access$300(order) && BidiTransform.access$100(by2) && BidiTransform.access$300(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$500(bidiTransform, (byte)0, 0);
                BidiTransform.access$900(bidiTransform);
                BidiTransform.access$400(bidiTransform, 0, 4);
            }
        }
        ,
        VIS_RTL_TO_VIS_RTL{

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                return BidiTransform.access$700(by) && BidiTransform.access$300(order) && BidiTransform.access$700(by2) && BidiTransform.access$300(order2);
            }

            @Override
            void doTransform(BidiTransform bidiTransform) {
                BidiTransform.access$800(bidiTransform);
                BidiTransform.access$500(bidiTransform, (byte)0, 0);
                BidiTransform.access$900(bidiTransform);
                BidiTransform.access$400(bidiTransform, 0, 4);
                BidiTransform.access$800(bidiTransform);
            }
        };


        private ReorderingScheme() {
        }

        abstract boolean matches(byte var1, Order var2, byte var3, Order var4);

        abstract void doTransform(BidiTransform var1);

        ReorderingScheme(1 var3_3) {
            this();
        }
    }

    public static enum Mirroring {
        OFF,
        ON;

    }

    public static enum Order {
        LOGICAL,
        VISUAL;

    }
}

