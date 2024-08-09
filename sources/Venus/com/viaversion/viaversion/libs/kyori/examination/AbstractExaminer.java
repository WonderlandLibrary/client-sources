/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.examination;

import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.Examiner;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractExaminer<R>
implements Examiner<R> {
    @Override
    @NotNull
    public R examine(@Nullable Object object) {
        if (object == null) {
            return this.nil();
        }
        if (object instanceof String) {
            return this.examine((String)object);
        }
        if (object instanceof Examinable) {
            return this.examine((Examinable)object);
        }
        if (object instanceof Collection) {
            return this.collection((Collection)object);
        }
        if (object instanceof Map) {
            return this.map((Map)object);
        }
        if (object.getClass().isArray()) {
            Class<?> clazz = object.getClass().getComponentType();
            if (clazz.isPrimitive()) {
                if (clazz == Boolean.TYPE) {
                    return this.examine((boolean[])object);
                }
                if (clazz == Byte.TYPE) {
                    return this.examine((byte[])object);
                }
                if (clazz == Character.TYPE) {
                    return this.examine((char[])object);
                }
                if (clazz == Double.TYPE) {
                    return this.examine((double[])object);
                }
                if (clazz == Float.TYPE) {
                    return this.examine((float[])object);
                }
                if (clazz == Integer.TYPE) {
                    return this.examine((int[])object);
                }
                if (clazz == Long.TYPE) {
                    return this.examine((long[])object);
                }
                if (clazz == Short.TYPE) {
                    return this.examine((short[])object);
                }
            }
            return this.array((Object[])object);
        }
        if (object instanceof Boolean) {
            return this.examine((Boolean)object);
        }
        if (object instanceof Character) {
            return this.examine(((Character)object).charValue());
        }
        if (object instanceof Number) {
            if (object instanceof Byte) {
                return this.examine((Byte)object);
            }
            if (object instanceof Double) {
                return this.examine((Double)object);
            }
            if (object instanceof Float) {
                return this.examine(((Float)object).floatValue());
            }
            if (object instanceof Integer) {
                return this.examine((Integer)object);
            }
            if (object instanceof Long) {
                return this.examine((Long)object);
            }
            if (object instanceof Short) {
                return this.examine((Short)object);
            }
        } else if (object instanceof BaseStream) {
            if (object instanceof Stream) {
                return this.stream((Stream)object);
            }
            if (object instanceof DoubleStream) {
                return this.stream((DoubleStream)object);
            }
            if (object instanceof IntStream) {
                return this.stream((IntStream)object);
            }
            if (object instanceof LongStream) {
                return this.stream((LongStream)object);
            }
        }
        return this.scalar(object);
    }

    @NotNull
    private <E> R array(E @NotNull [] EArray) {
        return (R)this.array(EArray, Arrays.stream(EArray).map(this::examine));
    }

    @NotNull
    protected abstract <E> R array(E @NotNull [] var1, @NotNull Stream<R> var2);

    @NotNull
    private <E> R collection(@NotNull Collection<E> collection) {
        return (R)this.collection(collection, collection.stream().map(this::examine));
    }

    @NotNull
    protected abstract <E> R collection(@NotNull Collection<E> var1, @NotNull Stream<R> var2);

    @Override
    @NotNull
    public R examine(@NotNull String string, @NotNull Stream<? extends ExaminableProperty> stream) {
        return this.examinable(string, stream.map(this::lambda$examine$0));
    }

    @NotNull
    protected abstract R examinable(@NotNull String var1, @NotNull Stream<Map.Entry<String, R>> var2);

    @NotNull
    private <K, V> R map(@NotNull Map<K, V> map) {
        return this.map(map, map.entrySet().stream().map(this::lambda$map$1));
    }

    @NotNull
    protected abstract <K, V> R map(@NotNull Map<K, V> var1, @NotNull Stream<Map.Entry<R, R>> var2);

    @NotNull
    protected abstract R nil();

    @NotNull
    protected abstract R scalar(@NotNull Object var1);

    @NotNull
    protected abstract <T> R stream(@NotNull Stream<T> var1);

    @NotNull
    protected abstract R stream(@NotNull DoubleStream var1);

    @NotNull
    protected abstract R stream(@NotNull IntStream var1);

    @NotNull
    protected abstract R stream(@NotNull LongStream var1);

    @Override
    @NotNull
    public R examine(boolean @Nullable [] blArray) {
        if (blArray == null) {
            return this.nil();
        }
        return (R)this.array(blArray.length, arg_0 -> this.lambda$examine$2(blArray, arg_0));
    }

    @Override
    @NotNull
    public R examine(byte @Nullable [] byArray) {
        if (byArray == null) {
            return this.nil();
        }
        return (R)this.array(byArray.length, arg_0 -> this.lambda$examine$3(byArray, arg_0));
    }

    @Override
    @NotNull
    public R examine(char @Nullable [] cArray) {
        if (cArray == null) {
            return this.nil();
        }
        return (R)this.array(cArray.length, arg_0 -> this.lambda$examine$4(cArray, arg_0));
    }

    @Override
    @NotNull
    public R examine(double @Nullable [] dArray) {
        if (dArray == null) {
            return this.nil();
        }
        return (R)this.array(dArray.length, arg_0 -> this.lambda$examine$5(dArray, arg_0));
    }

    @Override
    @NotNull
    public R examine(float @Nullable [] fArray) {
        if (fArray == null) {
            return this.nil();
        }
        return (R)this.array(fArray.length, arg_0 -> this.lambda$examine$6(fArray, arg_0));
    }

    @Override
    @NotNull
    public R examine(int @Nullable [] nArray) {
        if (nArray == null) {
            return this.nil();
        }
        return (R)this.array(nArray.length, arg_0 -> this.lambda$examine$7(nArray, arg_0));
    }

    @Override
    @NotNull
    public R examine(long @Nullable [] lArray) {
        if (lArray == null) {
            return this.nil();
        }
        return (R)this.array(lArray.length, arg_0 -> this.lambda$examine$8(lArray, arg_0));
    }

    @Override
    @NotNull
    public R examine(short @Nullable [] sArray) {
        if (sArray == null) {
            return this.nil();
        }
        return (R)this.array(sArray.length, arg_0 -> this.lambda$examine$9(sArray, arg_0));
    }

    @NotNull
    protected abstract R array(int var1, IntFunction<R> var2);

    private Object lambda$examine$9(short[] sArray, int n) {
        return this.examine(sArray[n]);
    }

    private Object lambda$examine$8(long[] lArray, int n) {
        return this.examine(lArray[n]);
    }

    private Object lambda$examine$7(int[] nArray, int n) {
        return this.examine(nArray[n]);
    }

    private Object lambda$examine$6(float[] fArray, int n) {
        return this.examine(fArray[n]);
    }

    private Object lambda$examine$5(double[] dArray, int n) {
        return this.examine(dArray[n]);
    }

    private Object lambda$examine$4(char[] cArray, int n) {
        return this.examine(cArray[n]);
    }

    private Object lambda$examine$3(byte[] byArray, int n) {
        return this.examine(byArray[n]);
    }

    private Object lambda$examine$2(boolean[] blArray, int n) {
        return this.examine(blArray[n]);
    }

    private Map.Entry lambda$map$1(Map.Entry entry) {
        return new AbstractMap.SimpleImmutableEntry<R, R>(this.examine(entry.getKey()), this.examine(entry.getValue()));
    }

    private Map.Entry lambda$examine$0(ExaminableProperty examinableProperty) {
        return new AbstractMap.SimpleImmutableEntry(examinableProperty.name(), examinableProperty.examine(this));
    }
}

