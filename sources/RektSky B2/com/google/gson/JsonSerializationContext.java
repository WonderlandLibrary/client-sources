package com.google.gson;

import java.lang.reflect.*;

public interface JsonSerializationContext
{
    JsonElement serialize(final Object p0);
    
    JsonElement serialize(final Object p0, final Type p1);
}
