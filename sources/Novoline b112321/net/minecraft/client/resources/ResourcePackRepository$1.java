package net.minecraft.client.resources;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.SettableFuture;
import java.io.File;
import net.minecraft.client.resources.ResourcePackRepository;

class ResourcePackRepository$1 implements FutureCallback {
   final File val$file1;
   final SettableFuture val$settablefuture;
   final ResourcePackRepository this$0;

   ResourcePackRepository$1(ResourcePackRepository var1, File var2, SettableFuture var3) {
      this.this$0 = var1;
      this.val$file1 = var2;
      this.val$settablefuture = var3;
   }

   public void onSuccess(Object var1) {
      this.this$0.setResourcePackInstance(this.val$file1);
      this.val$settablefuture.set((Object)null);
   }

   public void onFailure(Throwable var1) {
      this.val$settablefuture.setException(var1);
   }
}
