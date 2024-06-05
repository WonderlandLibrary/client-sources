package ninja.leaping.configurate.objectmapping.serialize;

import com.google.common.reflect.TypeToken;
import net.X9;
import ninja.leaping.configurate.ConfigurationNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TypeSerializer {
   @Nullable
   Object deserialize(@NotNull TypeToken var1, @NotNull ConfigurationNode var2) throws X9;

   void serialize(@NotNull TypeToken var1, @Nullable Object var2, @NotNull ConfigurationNode var3) throws X9;
}
