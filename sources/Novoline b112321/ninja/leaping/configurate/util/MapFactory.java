package ninja.leaping.configurate.util;

import java.util.concurrent.ConcurrentMap;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MapFactory {
   @NotNull
   ConcurrentMap create();
}
