package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class AnnotationNode extends AnnotationVisitor
{
    public String desc;
    public List<Object> values;
    
    public AnnotationNode(final String s) {
        this(327680, s);
        if (this.getClass() != AnnotationNode.class) {
            throw new IllegalStateException();
        }
    }
    
    public AnnotationNode(final int n, final String desc) {
        super(n);
        this.desc = desc;
    }
    
    AnnotationNode(final List<Object> values) {
        super(327680);
        this.values = values;
    }
    
    @Override
    public void visit(final String s, final Object o) {
        if (this.values == null) {
            this.values = new ArrayList<Object>((this.desc != null) ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(s);
        }
        if (o instanceof byte[]) {
            final byte[] array = (byte[])o;
            final ArrayList list = new ArrayList<Byte>(array.length);
            final byte[] array2 = array;
            for (int length = array2.length, i = 0; i < length; ++i) {
                list.add(array2[i]);
            }
            this.values.add(list);
        }
        else if (o instanceof boolean[]) {
            final boolean[] array3 = (boolean[])o;
            final ArrayList list2 = new ArrayList<Boolean>(array3.length);
            final boolean[] array4 = array3;
            for (int length2 = array4.length, j = 0; j < length2; ++j) {
                list2.add(array4[j]);
            }
            this.values.add(list2);
        }
        else if (o instanceof short[]) {
            final short[] array5 = (short[])o;
            final ArrayList list3 = new ArrayList<Short>(array5.length);
            final short[] array6 = array5;
            for (int length3 = array6.length, k = 0; k < length3; ++k) {
                list3.add(array6[k]);
            }
            this.values.add(list3);
        }
        else if (o instanceof char[]) {
            final char[] array7 = (char[])o;
            final ArrayList list4 = new ArrayList<Character>(array7.length);
            final char[] array8 = array7;
            for (int length4 = array8.length, l = 0; l < length4; ++l) {
                list4.add(array8[l]);
            }
            this.values.add(list4);
        }
        else if (o instanceof int[]) {
            final int[] array9 = (int[])o;
            final ArrayList list5 = new ArrayList<Integer>(array9.length);
            final int[] array10 = array9;
            for (int length5 = array10.length, n = 0; n < length5; ++n) {
                list5.add(array10[n]);
            }
            this.values.add(list5);
        }
        else if (o instanceof long[]) {
            final long[] array11 = (long[])o;
            final ArrayList list6 = new ArrayList<Long>(array11.length);
            final long[] array12 = array11;
            for (int length6 = array12.length, n2 = 0; n2 < length6; ++n2) {
                list6.add(array12[n2]);
            }
            this.values.add(list6);
        }
        else if (o instanceof float[]) {
            final float[] array13 = (float[])o;
            final ArrayList list7 = new ArrayList<Float>(array13.length);
            final float[] array14 = array13;
            for (int length7 = array14.length, n3 = 0; n3 < length7; ++n3) {
                list7.add(array14[n3]);
            }
            this.values.add(list7);
        }
        else if (o instanceof double[]) {
            final double[] array15 = (double[])o;
            final ArrayList list8 = new ArrayList<Double>(array15.length);
            final double[] array16 = array15;
            for (int length8 = array16.length, n4 = 0; n4 < length8; ++n4) {
                list8.add(array16[n4]);
            }
            this.values.add(list8);
        }
        else {
            this.values.add(o);
        }
    }
    
    @Override
    public void visitEnum(final String s, final String s2, final String s3) {
        if (this.values == null) {
            this.values = new ArrayList<Object>((this.desc != null) ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(s);
        }
        this.values.add(new String[] { s2, s3 });
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String s, final String s2) {
        if (this.values == null) {
            this.values = new ArrayList<Object>((this.desc != null) ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(s);
        }
        final AnnotationNode annotationNode = new AnnotationNode(s2);
        this.values.add(annotationNode);
        return annotationNode;
    }
    
    @Override
    public AnnotationVisitor visitArray(final String s) {
        if (this.values == null) {
            this.values = new ArrayList<Object>((this.desc != null) ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(s);
        }
        final ArrayList<Object> list = new ArrayList<Object>();
        this.values.add(list);
        return new AnnotationNode(list);
    }
    
    @Override
    public void visitEnd() {
    }
    
    public void check(final int n) {
    }
    
    public void accept(final AnnotationVisitor annotationVisitor) {
        if (annotationVisitor != null) {
            if (this.values != null) {
                for (int i = 0; i < this.values.size(); i += 2) {
                    accept(annotationVisitor, (String)this.values.get(i), this.values.get(i + 1));
                }
            }
            annotationVisitor.visitEnd();
        }
    }
    
    static void accept(final AnnotationVisitor annotationVisitor, final String s, final Object o) {
        if (annotationVisitor != null) {
            if (o instanceof String[]) {
                final String[] array = (String[])o;
                annotationVisitor.visitEnum(s, array[0], array[1]);
            }
            else if (o instanceof AnnotationNode) {
                final AnnotationNode annotationNode = (AnnotationNode)o;
                annotationNode.accept(annotationVisitor.visitAnnotation(s, annotationNode.desc));
            }
            else if (o instanceof List) {
                final AnnotationVisitor visitArray = annotationVisitor.visitArray(s);
                if (visitArray != null) {
                    final List list = (List)o;
                    for (int i = 0; i < list.size(); ++i) {
                        accept(visitArray, null, list.get(i));
                    }
                    visitArray.visitEnd();
                }
            }
            else {
                annotationVisitor.visit(s, o);
            }
        }
    }
}
