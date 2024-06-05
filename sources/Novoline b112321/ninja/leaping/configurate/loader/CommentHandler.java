package ninja.leaping.configurate.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface CommentHandler {
   @NotNull
   Optional extractHeader(@NotNull BufferedReader var1) throws IOException;

   @NotNull
   Collection toComment(@NotNull Collection var1);
}
