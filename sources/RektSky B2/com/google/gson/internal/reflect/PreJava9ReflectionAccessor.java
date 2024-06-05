package com.google.gson.internal.reflect;

import java.lang.reflect.*;

final class PreJava9ReflectionAccessor extends ReflectionAccessor
{
    @Override
    public void makeAccessible(final AccessibleObject ao) {
        ao.setAccessible(true);
    }
}
