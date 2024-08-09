/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import org.apache.logging.log4j.core.config.Order;

public class OrderComparator
implements Comparator<Class<?>>,
Serializable {
    private static final long serialVersionUID = 1L;
    private static final Comparator<Class<?>> INSTANCE = new OrderComparator();

    public static Comparator<Class<?>> getInstance() {
        return INSTANCE;
    }

    @Override
    public int compare(Class<?> clazz, Class<?> clazz2) {
        Order order = Objects.requireNonNull(clazz, "lhs").getAnnotation(Order.class);
        Order order2 = Objects.requireNonNull(clazz2, "rhs").getAnnotation(Order.class);
        if (order == null && order2 == null) {
            return 1;
        }
        if (order2 == null) {
            return 1;
        }
        if (order == null) {
            return 0;
        }
        return Integer.signum(order2.value() - order.value());
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((Class)object, (Class)object2);
    }
}

