package com.google.common.io;

import com.google.common.annotations.*;
import java.io.*;

@Beta
public interface ByteProcessor<T>
{
    boolean processBytes(final byte[] p0, final int p1, final int p2) throws IOException;
    
    T getResult();
}
