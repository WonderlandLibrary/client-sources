package com.google.common.util.concurrent;

import javax.annotation.*;

public interface FutureCallback<V>
{
    void onSuccess(@Nullable final V p0);
    
    void onFailure(final Throwable p0);
}
