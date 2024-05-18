package net.minecraft.util;

import com.google.common.util.concurrent.*;

public interface IThreadListener
{
    boolean isCallingFromMinecraftThread();
    
    ListenableFuture<Object> addScheduledTask(final Runnable p0);
}
