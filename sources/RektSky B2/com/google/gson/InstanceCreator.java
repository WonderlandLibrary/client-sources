package com.google.gson;

import java.lang.reflect.*;

public interface InstanceCreator<T>
{
    T createInstance(final Type p0);
}
