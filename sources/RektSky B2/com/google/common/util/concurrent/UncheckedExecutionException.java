package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
public class UncheckedExecutionException extends RuntimeException
{
    private static final long serialVersionUID = 0L;
    
    protected UncheckedExecutionException() {
    }
    
    protected UncheckedExecutionException(@Nullable final String message) {
        super(message);
    }
    
    public UncheckedExecutionException(@Nullable final String message, @Nullable final Throwable cause) {
        super(message, cause);
    }
    
    public UncheckedExecutionException(@Nullable final Throwable cause) {
        super(cause);
    }
}
