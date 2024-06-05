package com.google.common.collect;

import java.util.*;
import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
public interface ClassToInstanceMap<B> extends Map<Class<? extends B>, B>
{
     <T extends B> T getInstance(final Class<T> p0);
    
     <T extends B> T putInstance(final Class<T> p0, @Nullable final T p1);
}
