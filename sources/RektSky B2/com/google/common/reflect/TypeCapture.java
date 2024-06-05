package com.google.common.reflect;

import java.lang.reflect.*;
import com.google.common.base.*;

abstract class TypeCapture<T>
{
    final Type capture() {
        final Type superclass = this.getClass().getGenericSuperclass();
        Preconditions.checkArgument(superclass instanceof ParameterizedType, "%s isn't parameterized", superclass);
        return ((ParameterizedType)superclass).getActualTypeArguments()[0];
    }
}
