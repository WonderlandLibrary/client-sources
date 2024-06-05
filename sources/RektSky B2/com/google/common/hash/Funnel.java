package com.google.common.hash;

import java.io.*;
import com.google.common.annotations.*;

@Beta
public interface Funnel<T> extends Serializable
{
    void funnel(final T p0, final PrimitiveSink p1);
}
