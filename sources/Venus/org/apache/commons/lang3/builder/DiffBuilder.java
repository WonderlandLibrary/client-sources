/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.Builder;
import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ToStringStyle;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DiffBuilder
implements Builder<DiffResult> {
    private final List<Diff<?>> diffs;
    private final boolean objectsTriviallyEqual;
    private final Object left;
    private final Object right;
    private final ToStringStyle style;

    public DiffBuilder(Object object, Object object2, ToStringStyle toStringStyle, boolean bl) {
        if (object == null) {
            throw new IllegalArgumentException("lhs cannot be null");
        }
        if (object2 == null) {
            throw new IllegalArgumentException("rhs cannot be null");
        }
        this.diffs = new ArrayList();
        this.left = object;
        this.right = object2;
        this.style = toStringStyle;
        this.objectsTriviallyEqual = bl && (object == object2 || object.equals(object2));
    }

    public DiffBuilder(Object object, Object object2, ToStringStyle toStringStyle) {
        this(object, object2, toStringStyle, true);
    }

    public DiffBuilder append(String string, boolean bl, boolean bl2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (bl != bl2) {
            this.diffs.add(new Diff<Boolean>(this, string, bl, bl2){
                private static final long serialVersionUID = 1L;
                final boolean val$lhs;
                final boolean val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = bl;
                    this.val$rhs = bl2;
                    super(string);
                }

                @Override
                public Boolean getLeft() {
                    return this.val$lhs;
                }

                @Override
                public Boolean getRight() {
                    return this.val$rhs;
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, boolean[] blArray, boolean[] blArray2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(blArray, blArray2)) {
            this.diffs.add(new Diff<Boolean[]>(this, string, blArray, blArray2){
                private static final long serialVersionUID = 1L;
                final boolean[] val$lhs;
                final boolean[] val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = blArray;
                    this.val$rhs = blArray2;
                    super(string);
                }

                @Override
                public Boolean[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }

                @Override
                public Boolean[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, byte by, byte by2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (by != by2) {
            this.diffs.add(new Diff<Byte>(this, string, by, by2){
                private static final long serialVersionUID = 1L;
                final byte val$lhs;
                final byte val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = by;
                    this.val$rhs = by2;
                    super(string);
                }

                @Override
                public Byte getLeft() {
                    return this.val$lhs;
                }

                @Override
                public Byte getRight() {
                    return this.val$rhs;
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, byte[] byArray, byte[] byArray2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(byArray, byArray2)) {
            this.diffs.add(new Diff<Byte[]>(this, string, byArray, byArray2){
                private static final long serialVersionUID = 1L;
                final byte[] val$lhs;
                final byte[] val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = byArray;
                    this.val$rhs = byArray2;
                    super(string);
                }

                @Override
                public Byte[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }

                @Override
                public Byte[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, char c, char c2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (c != c2) {
            this.diffs.add(new Diff<Character>(this, string, c, c2){
                private static final long serialVersionUID = 1L;
                final char val$lhs;
                final char val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = c;
                    this.val$rhs = c2;
                    super(string);
                }

                @Override
                public Character getLeft() {
                    return Character.valueOf(this.val$lhs);
                }

                @Override
                public Character getRight() {
                    return Character.valueOf(this.val$rhs);
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, char[] cArray, char[] cArray2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(cArray, cArray2)) {
            this.diffs.add(new Diff<Character[]>(this, string, cArray, cArray2){
                private static final long serialVersionUID = 1L;
                final char[] val$lhs;
                final char[] val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = cArray;
                    this.val$rhs = cArray2;
                    super(string);
                }

                @Override
                public Character[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }

                @Override
                public Character[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, double d, double d2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2)) {
            this.diffs.add(new Diff<Double>(this, string, d, d2){
                private static final long serialVersionUID = 1L;
                final double val$lhs;
                final double val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = d;
                    this.val$rhs = d2;
                    super(string);
                }

                @Override
                public Double getLeft() {
                    return this.val$lhs;
                }

                @Override
                public Double getRight() {
                    return this.val$rhs;
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, double[] dArray, double[] dArray2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(dArray, dArray2)) {
            this.diffs.add(new Diff<Double[]>(this, string, dArray, dArray2){
                private static final long serialVersionUID = 1L;
                final double[] val$lhs;
                final double[] val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = dArray;
                    this.val$rhs = dArray2;
                    super(string);
                }

                @Override
                public Double[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }

                @Override
                public Double[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, float f, float f2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (Float.floatToIntBits(f) != Float.floatToIntBits(f2)) {
            this.diffs.add(new Diff<Float>(this, string, f, f2){
                private static final long serialVersionUID = 1L;
                final float val$lhs;
                final float val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = f;
                    this.val$rhs = f2;
                    super(string);
                }

                @Override
                public Float getLeft() {
                    return Float.valueOf(this.val$lhs);
                }

                @Override
                public Float getRight() {
                    return Float.valueOf(this.val$rhs);
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, float[] fArray, float[] fArray2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(fArray, fArray2)) {
            this.diffs.add(new Diff<Float[]>(this, string, fArray, fArray2){
                private static final long serialVersionUID = 1L;
                final float[] val$lhs;
                final float[] val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = fArray;
                    this.val$rhs = fArray2;
                    super(string);
                }

                @Override
                public Float[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }

                @Override
                public Float[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, int n, int n2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (n != n2) {
            this.diffs.add(new Diff<Integer>(this, string, n, n2){
                private static final long serialVersionUID = 1L;
                final int val$lhs;
                final int val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = n;
                    this.val$rhs = n2;
                    super(string);
                }

                @Override
                public Integer getLeft() {
                    return this.val$lhs;
                }

                @Override
                public Integer getRight() {
                    return this.val$rhs;
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, int[] nArray, int[] nArray2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(nArray, nArray2)) {
            this.diffs.add(new Diff<Integer[]>(this, string, nArray, nArray2){
                private static final long serialVersionUID = 1L;
                final int[] val$lhs;
                final int[] val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = nArray;
                    this.val$rhs = nArray2;
                    super(string);
                }

                @Override
                public Integer[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }

                @Override
                public Integer[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, long l, long l2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (l != l2) {
            this.diffs.add(new Diff<Long>(this, string, l, l2){
                private static final long serialVersionUID = 1L;
                final long val$lhs;
                final long val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = l;
                    this.val$rhs = l2;
                    super(string);
                }

                @Override
                public Long getLeft() {
                    return this.val$lhs;
                }

                @Override
                public Long getRight() {
                    return this.val$rhs;
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, long[] lArray, long[] lArray2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(lArray, lArray2)) {
            this.diffs.add(new Diff<Long[]>(this, string, lArray, lArray2){
                private static final long serialVersionUID = 1L;
                final long[] val$lhs;
                final long[] val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = lArray;
                    this.val$rhs = lArray2;
                    super(string);
                }

                @Override
                public Long[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }

                @Override
                public Long[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, short s, short s2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (s != s2) {
            this.diffs.add(new Diff<Short>(this, string, s, s2){
                private static final long serialVersionUID = 1L;
                final short val$lhs;
                final short val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = s;
                    this.val$rhs = s2;
                    super(string);
                }

                @Override
                public Short getLeft() {
                    return this.val$lhs;
                }

                @Override
                public Short getRight() {
                    return this.val$rhs;
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, short[] sArray, short[] sArray2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(sArray, sArray2)) {
            this.diffs.add(new Diff<Short[]>(this, string, sArray, sArray2){
                private static final long serialVersionUID = 1L;
                final short[] val$lhs;
                final short[] val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = sArray;
                    this.val$rhs = sArray2;
                    super(string);
                }

                @Override
                public Short[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }

                @Override
                public Short[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, Object object, Object object2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (object == object2) {
            return this;
        }
        Object object3 = object != null ? object : object2;
        if (object3.getClass().isArray()) {
            if (object3 instanceof boolean[]) {
                return this.append(string, (boolean[])object, (boolean[])object2);
            }
            if (object3 instanceof byte[]) {
                return this.append(string, (byte[])object, (byte[])object2);
            }
            if (object3 instanceof char[]) {
                return this.append(string, (char[])object, (char[])object2);
            }
            if (object3 instanceof double[]) {
                return this.append(string, (double[])object, (double[])object2);
            }
            if (object3 instanceof float[]) {
                return this.append(string, (float[])object, (float[])object2);
            }
            if (object3 instanceof int[]) {
                return this.append(string, (int[])object, (int[])object2);
            }
            if (object3 instanceof long[]) {
                return this.append(string, (long[])object, (long[])object2);
            }
            if (object3 instanceof short[]) {
                return this.append(string, (short[])object, (short[])object2);
            }
            return this.append(string, (Object[])object, (Object[])object2);
        }
        if (object != null && object.equals(object2)) {
            return this;
        }
        this.diffs.add(new Diff<Object>(this, string, object, object2){
            private static final long serialVersionUID = 1L;
            final Object val$lhs;
            final Object val$rhs;
            final DiffBuilder this$0;
            {
                this.this$0 = diffBuilder;
                this.val$lhs = object;
                this.val$rhs = object2;
                super(string);
            }

            @Override
            public Object getLeft() {
                return this.val$lhs;
            }

            @Override
            public Object getRight() {
                return this.val$rhs;
            }
        });
        return this;
    }

    public DiffBuilder append(String string, Object[] objectArray, Object[] objectArray2) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(objectArray, objectArray2)) {
            this.diffs.add(new Diff<Object[]>(this, string, objectArray, objectArray2){
                private static final long serialVersionUID = 1L;
                final Object[] val$lhs;
                final Object[] val$rhs;
                final DiffBuilder this$0;
                {
                    this.this$0 = diffBuilder;
                    this.val$lhs = objectArray;
                    this.val$rhs = objectArray2;
                    super(string);
                }

                @Override
                public Object[] getLeft() {
                    return this.val$lhs;
                }

                @Override
                public Object[] getRight() {
                    return this.val$rhs;
                }

                @Override
                public Object getRight() {
                    return this.getRight();
                }

                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }

    public DiffBuilder append(String string, DiffResult diffResult) {
        if (string == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (diffResult == null) {
            throw new IllegalArgumentException("Diff result cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        for (Diff<?> diff : diffResult.getDiffs()) {
            this.append(string + "." + diff.getFieldName(), diff.getLeft(), diff.getRight());
        }
        return this;
    }

    @Override
    public DiffResult build() {
        return new DiffResult(this.left, this.right, this.diffs, this.style);
    }

    @Override
    public Object build() {
        return this.build();
    }
}

