package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
public class ComputationException extends RuntimeException
{
    private static final long serialVersionUID = 0L;
    
    public ComputationException(@Nullable final Throwable cause) {
        super(cause);
    }
}
