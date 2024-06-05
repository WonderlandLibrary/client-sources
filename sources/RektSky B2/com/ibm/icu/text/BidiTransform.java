package com.ibm.icu.text;

import com.ibm.icu.lang.*;

public class BidiTransform
{
    private Bidi bidi;
    private String text;
    private int reorderingOptions;
    private int shapingOptions;
    
    public String transform(final CharSequence text, final byte inParaLevel, final Order inOrder, final byte outParaLevel, final Order outOrder, final Mirroring doMirroring, final int shapingOptions) {
        if (text == null || inOrder == null || outOrder == null || doMirroring == null) {
            throw new IllegalArgumentException();
        }
        this.text = text.toString();
        final byte[] levels = { inParaLevel, outParaLevel };
        this.resolveBaseDirection(levels);
        final ReorderingScheme currentScheme = this.findMatchingScheme(levels[0], inOrder, levels[1], outOrder);
        if (currentScheme != null) {
            this.bidi = new Bidi();
            this.reorderingOptions = (Mirroring.ON.equals(doMirroring) ? 2 : 0);
            this.shapingOptions = (shapingOptions & 0xFFFFFFFB);
            currentScheme.doTransform(this);
        }
        return this.text;
    }
    
    private void resolveBaseDirection(final byte[] levels) {
        if (Bidi.IsDefaultLevel(levels[0])) {
            final byte level = Bidi.getBaseDirection(this.text);
            levels[0] = (byte)((level != 3) ? level : ((levels[0] == 127) ? 1 : 0));
        }
        else {
            final int n = 0;
            levels[n] &= 0x1;
        }
        if (Bidi.IsDefaultLevel(levels[1])) {
            levels[1] = levels[0];
        }
        else {
            final int n2 = 1;
            levels[n2] &= 0x1;
        }
    }
    
    private ReorderingScheme findMatchingScheme(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
        for (final ReorderingScheme scheme : ReorderingScheme.values()) {
            if (scheme.matches(inLevel, inOrder, outLevel, outOrder)) {
                return scheme;
            }
        }
        return null;
    }
    
    private void resolve(final byte level, final int options) {
        this.bidi.setInverse((options & 0x5) != 0x0);
        this.bidi.setReorderingMode(options);
        this.bidi.setPara(this.text, level, null);
    }
    
    private void reorder() {
        this.text = this.bidi.writeReordered(this.reorderingOptions);
        this.reorderingOptions = 0;
    }
    
    private void reverse() {
        this.text = Bidi.writeReverse(this.text, 0);
    }
    
    private void mirror() {
        if ((this.reorderingOptions & 0x2) == 0x0) {
            return;
        }
        final StringBuffer sb = new StringBuffer(this.text);
        final byte[] levels = this.bidi.getLevels();
        int ch;
        for (int i = 0, n = levels.length; i < n; i += UTF16.getCharCount(ch)) {
            ch = UTF16.charAt(sb, i);
            if ((levels[i] & 0x1) != 0x0) {
                UTF16.setCharAt(sb, i, UCharacter.getMirror(ch));
            }
        }
        this.text = sb.toString();
        this.reorderingOptions &= 0xFFFFFFFD;
    }
    
    private void shapeArabic(final int digitsDir, final int lettersDir) {
        if (digitsDir == lettersDir) {
            this.shapeArabic(this.shapingOptions | digitsDir);
        }
        else {
            this.shapeArabic((this.shapingOptions & 0xFFFFFFE7) | digitsDir);
            this.shapeArabic((this.shapingOptions & 0xFFFFFF1F) | lettersDir);
        }
    }
    
    private void shapeArabic(final int options) {
        if (options != 0) {
            final ArabicShaping shaper = new ArabicShaping(options);
            try {
                this.text = shaper.shape(this.text);
            }
            catch (ArabicShapingException ex) {}
        }
    }
    
    private static boolean IsLTR(final byte level) {
        return (level & 0x1) == 0x0;
    }
    
    private static boolean IsRTL(final byte level) {
        return (level & 0x1) == 0x1;
    }
    
    private static boolean IsLogical(final Order order) {
        return Order.LOGICAL.equals(order);
    }
    
    private static boolean IsVisual(final Order order) {
        return Order.VISUAL.equals(order);
    }
    
    public enum Order
    {
        LOGICAL, 
        VISUAL;
    }
    
    public enum Mirroring
    {
        OFF, 
        ON;
    }
    
    private enum ReorderingScheme
    {
        LOG_LTR_TO_VIS_LTR {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsLTR(inLevel) && IsLogical(inOrder) && IsLTR(outLevel) && IsVisual(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.shapeArabic(0, 0);
                transform.resolve((byte)0, 0);
                transform.reorder();
            }
        }, 
        LOG_RTL_TO_VIS_LTR {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsRTL(inLevel) && IsLogical(inOrder) && IsLTR(outLevel) && IsVisual(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.resolve((byte)1, 0);
                transform.reorder();
                transform.shapeArabic(0, 4);
            }
        }, 
        LOG_LTR_TO_VIS_RTL {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsLTR(inLevel) && IsLogical(inOrder) && IsRTL(outLevel) && IsVisual(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.shapeArabic(0, 0);
                transform.resolve((byte)0, 0);
                transform.reorder();
                transform.reverse();
            }
        }, 
        LOG_RTL_TO_VIS_RTL {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsRTL(inLevel) && IsLogical(inOrder) && IsRTL(outLevel) && IsVisual(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.resolve((byte)1, 0);
                transform.reorder();
                transform.shapeArabic(0, 4);
                transform.reverse();
            }
        }, 
        VIS_LTR_TO_LOG_RTL {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsLTR(inLevel) && IsVisual(inOrder) && IsRTL(outLevel) && IsLogical(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.shapeArabic(0, 4);
                transform.resolve((byte)1, 5);
                transform.reorder();
            }
        }, 
        VIS_RTL_TO_LOG_RTL {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsRTL(inLevel) && IsVisual(inOrder) && IsRTL(outLevel) && IsLogical(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.reverse();
                transform.shapeArabic(0, 4);
                transform.resolve((byte)1, 5);
                transform.reorder();
            }
        }, 
        VIS_LTR_TO_LOG_LTR {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsLTR(inLevel) && IsVisual(inOrder) && IsLTR(outLevel) && IsLogical(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.resolve((byte)0, 5);
                transform.reorder();
                transform.shapeArabic(0, 0);
            }
        }, 
        VIS_RTL_TO_LOG_LTR {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsRTL(inLevel) && IsVisual(inOrder) && IsLTR(outLevel) && IsLogical(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.reverse();
                transform.resolve((byte)0, 5);
                transform.reorder();
                transform.shapeArabic(0, 0);
            }
        }, 
        LOG_LTR_TO_LOG_RTL {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsLTR(inLevel) && IsLogical(inOrder) && IsRTL(outLevel) && IsLogical(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.shapeArabic(0, 0);
                transform.resolve((byte)0, 0);
                transform.mirror();
                transform.resolve((byte)0, 3);
                transform.reorder();
            }
        }, 
        LOG_RTL_TO_LOG_LTR {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsRTL(inLevel) && IsLogical(inOrder) && IsLTR(outLevel) && IsLogical(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.resolve((byte)1, 0);
                transform.mirror();
                transform.resolve((byte)1, 3);
                transform.reorder();
                transform.shapeArabic(0, 0);
            }
        }, 
        VIS_LTR_TO_VIS_RTL {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsLTR(inLevel) && IsVisual(inOrder) && IsRTL(outLevel) && IsVisual(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.resolve((byte)0, 0);
                transform.mirror();
                transform.shapeArabic(0, 4);
                transform.reverse();
            }
        }, 
        VIS_RTL_TO_VIS_LTR {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsRTL(inLevel) && IsVisual(inOrder) && IsLTR(outLevel) && IsVisual(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.reverse();
                transform.resolve((byte)0, 0);
                transform.mirror();
                transform.shapeArabic(0, 4);
            }
        }, 
        LOG_LTR_TO_LOG_LTR {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsLTR(inLevel) && IsLogical(inOrder) && IsLTR(outLevel) && IsLogical(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.resolve((byte)0, 0);
                transform.mirror();
                transform.shapeArabic(0, 0);
            }
        }, 
        LOG_RTL_TO_LOG_RTL {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsRTL(inLevel) && IsLogical(inOrder) && IsRTL(outLevel) && IsLogical(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.resolve((byte)1, 0);
                transform.mirror();
                transform.shapeArabic(4, 0);
            }
        }, 
        VIS_LTR_TO_VIS_LTR {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsLTR(inLevel) && IsVisual(inOrder) && IsLTR(outLevel) && IsVisual(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.resolve((byte)0, 0);
                transform.mirror();
                transform.shapeArabic(0, 4);
            }
        }, 
        VIS_RTL_TO_VIS_RTL {
            @Override
            boolean matches(final byte inLevel, final Order inOrder, final byte outLevel, final Order outOrder) {
                return IsRTL(inLevel) && IsVisual(inOrder) && IsRTL(outLevel) && IsVisual(outOrder);
            }
            
            @Override
            void doTransform(final BidiTransform transform) {
                transform.reverse();
                transform.resolve((byte)0, 0);
                transform.mirror();
                transform.shapeArabic(0, 4);
                transform.reverse();
            }
        };
        
        abstract boolean matches(final byte p0, final Order p1, final byte p2, final Order p3);
        
        abstract void doTransform(final BidiTransform p0);
    }
}
