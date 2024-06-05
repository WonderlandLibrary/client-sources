package com.sun.jna;

import java.lang.reflect.*;

public interface InvocationMapper
{
    InvocationHandler getInvocationHandler(final NativeLibrary p0, final Method p1);
}
