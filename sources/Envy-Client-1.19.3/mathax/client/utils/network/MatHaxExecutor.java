package mathax.client.utils.network;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MatHaxExecutor {
    public static ExecutorService EXECUTOR;




    public static void init() {
        EXECUTOR = Executors.newSingleThreadExecutor();
    }

    public static void execute(Runnable task) {
        EXECUTOR.execute(task);
    }
}
