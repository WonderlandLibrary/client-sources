package net.minecraft.client.resources;

import java.io.*;
import com.google.common.util.concurrent.*;

class ResourcePackRepository$3 implements FutureCallback {
    final /* synthetic */ File val$var4;
    final /* synthetic */ SettableFuture val$var8;
    
    public void onSuccess(final Object p_onSuccess_1_) {
        ResourcePackRepository.this.func_177319_a(this.val$var4);
        this.val$var8.set((Object)null);
    }
    
    public void onFailure(final Throwable p_onFailure_1_) {
        this.val$var8.setException(p_onFailure_1_);
    }
}