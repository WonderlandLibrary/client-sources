package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
public class ExecutionError extends Error
{
    private static final long serialVersionUID = 0L;
    
    protected ExecutionError() {
    }
    
    protected ExecutionError(@Nullable final String message) {
        super(message);
    }
    
    public ExecutionError(@Nullable final String message, @Nullable final Error cause) {
        super(message, cause);
    }
    
    public ExecutionError(@Nullable final Error cause) {
        super(cause);
    }
}
