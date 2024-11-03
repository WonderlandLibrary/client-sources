package viamcp.utils;

import com.viaversion.viaversion.api.platform.PlatformTask;
import java.util.concurrent.Future;

public class FutureTaskId implements PlatformTask<Future<?>> {
   private final Future<?> object;

   public FutureTaskId(Future<?> object) {
      this.object = object;
   }

   public Future<?> getObject() {
      return this.object;
   }

   @Override
   public void cancel() {
      this.object.cancel(false);
   }
}
