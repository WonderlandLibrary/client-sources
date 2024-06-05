package net.minecraft.client.resources;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.SettableFuture;
import java.io.File;
import net.arf;

class ResourcePackRepository$3 implements FutureCallback {
   final File val$file1;
   final SettableFuture val$settablefuture;
   final arf b;

   ResourcePackRepository$3(arf var1, File var2, SettableFuture var3) {
      this.b = var1;
      this.val$file1 = var2;
      this.val$settablefuture = var3;
   }

   public void onSuccess(Object var1) {
      this.b.a(this.val$file1);
      this.val$settablefuture.set((Object)null);
   }

   public void onFailure(Throwable var1) {
      this.val$settablefuture.setException(var1);
   }
}
