package com.google.common.collect;

import com.google.common.annotations.*;

@Beta
public interface Interner<E>
{
    E intern(final E p0);
}
