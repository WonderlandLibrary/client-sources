package ninja.leaping.configurate.objectmapping;

import net.X9;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import org.jetbrains.annotations.NotNull;

public interface ObjectMapperFactory {
   @NotNull
   ObjectMapper getMapper(@NotNull Class var1) throws X9;
}
