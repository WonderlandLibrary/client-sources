package com.google.common.util.concurrent;

import java.util.concurrent.*;
import com.google.common.annotations.*;

@Beta
public interface ListenableScheduledFuture<V> extends ScheduledFuture<V>, ListenableFuture<V>
{
}
