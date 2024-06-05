package com.google.common.io;

import java.io.*;

@Deprecated
public interface OutputSupplier<T>
{
    T getOutput() throws IOException;
}
