package com.google.common.io;

import java.io.*;

@Deprecated
public interface InputSupplier<T>
{
    T getInput() throws IOException;
}
