package com.google.gson;

import com.google.gson.reflect.*;

public interface TypeAdapterFactory
{
     <T> TypeAdapter<T> create(final Gson p0, final TypeToken<T> p1);
}
