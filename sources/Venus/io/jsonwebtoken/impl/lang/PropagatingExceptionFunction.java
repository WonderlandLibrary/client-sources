/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.ConstantFunction;
import io.jsonwebtoken.impl.lang.DelegatingCheckedFunction;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Classes;
import io.jsonwebtoken.lang.Supplier;
import java.lang.reflect.Constructor;

public class PropagatingExceptionFunction<T, R, E extends RuntimeException>
implements Function<T, R> {
    private final CheckedFunction<T, R> function;
    private final Function<T, String> msgFunction;
    private final Class<E> clazz;

    public PropagatingExceptionFunction(Function<T, R> function, Class<E> clazz, String string) {
        this(new DelegatingCheckedFunction<T, R>(function), clazz, new ConstantFunction(string));
    }

    public PropagatingExceptionFunction(CheckedFunction<T, R> checkedFunction, Class<E> clazz, String string) {
        this(checkedFunction, clazz, new ConstantFunction(string));
    }

    public PropagatingExceptionFunction(CheckedFunction<T, R> checkedFunction, Class<E> clazz, Supplier<String> supplier) {
        this(checkedFunction, clazz, new Function<T, String>(supplier){
            final Supplier val$msgSupplier;
            {
                this.val$msgSupplier = supplier;
            }

            @Override
            public String apply(T t) {
                return (String)this.val$msgSupplier.get();
            }

            @Override
            public Object apply(Object object) {
                return this.apply(object);
            }
        });
    }

    public PropagatingExceptionFunction(CheckedFunction<T, R> checkedFunction, Class<E> clazz, Function<T, String> function) {
        this.clazz = Assert.notNull(clazz, "Exception class cannot be null.");
        this.msgFunction = Assert.notNull(function, "msgFunction cannot be null.");
        this.function = Assert.notNull(checkedFunction, "Function cannot be null");
    }

    @Override
    public R apply(T t) {
        try {
            return this.function.apply(t);
        } catch (Exception exception) {
            if (this.clazz.isAssignableFrom(exception.getClass())) {
                throw (RuntimeException)this.clazz.cast(exception);
            }
            String string = this.msgFunction.apply(t);
            if (!string.endsWith(".")) {
                string = string + ".";
            }
            string = string + " Cause: " + exception.getMessage();
            Class<E> clazz = this.clazz;
            Constructor<E> constructor = Classes.getConstructor(clazz, String.class, Throwable.class);
            throw (RuntimeException)Classes.instantiate(constructor, string, exception);
        }
    }
}

