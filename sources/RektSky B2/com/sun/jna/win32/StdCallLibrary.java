package com.sun.jna.win32;

import com.sun.jna.*;

public interface StdCallLibrary extends Library, StdCall
{
    public static final int STDCALL_CONVENTION = 63;
    public static final FunctionMapper FUNCTION_MAPPER = new StdCallFunctionMapper();
    
    public interface StdCallCallback extends Callback, StdCall
    {
    }
}
