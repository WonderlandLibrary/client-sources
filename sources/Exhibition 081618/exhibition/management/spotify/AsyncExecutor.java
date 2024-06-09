package exhibition.management.spotify;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncExecutor {
   private ExecutorService service = Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setNameFormat("Async Executor Pool #%1$d").build());

   public void execute(Runnable runnable) {
      this.service.execute(runnable);
   }

   public void finish() {
      this.service.shutdown();
   }
}
