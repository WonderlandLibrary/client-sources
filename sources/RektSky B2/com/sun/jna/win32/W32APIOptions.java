package com.sun.jna.win32;

import java.util.*;
import com.sun.jna.*;

public interface W32APIOptions extends StdCallLibrary
{
    public static final Map<String, Object> UNICODE_OPTIONS = Collections.unmodifiableMap((Map<? extends String, ?>)new HashMap<String, Object>() {
        private static final long serialVersionUID = 1L;
        
        {
            ((HashMap<String, TypeMapper>)this).put("type-mapper", W32APITypeMapper.UNICODE);
            ((HashMap<String, FunctionMapper>)this).put("function-mapper", W32APIFunctionMapper.UNICODE);
        }
    });
    public static final Map<String, Object> ASCII_OPTIONS = Collections.unmodifiableMap((Map<? extends String, ?>)new HashMap<String, Object>() {
        private static final long serialVersionUID = 1L;
        
        {
            ((HashMap<String, TypeMapper>)this).put("type-mapper", W32APITypeMapper.ASCII);
            ((HashMap<String, FunctionMapper>)this).put("function-mapper", W32APIFunctionMapper.ASCII);
        }
    });
    public static final Map<String, Object> DEFAULT_OPTIONS = Boolean.getBoolean("w32.ascii") ? W32APIOptions.ASCII_OPTIONS : W32APIOptions.UNICODE_OPTIONS;
}
