package com.google.common.io;

import com.google.common.annotations.*;
import java.io.*;

@Beta
public interface LineProcessor<T>
{
    boolean processLine(final String p0) throws IOException;
    
    T getResult();
}
