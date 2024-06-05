package ninja.leaping.configurate.commented;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import ninja.leaping.configurate.ConfigurationNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CommentedConfigurationNode extends ConfigurationNode {
   @NotNull
   Optional getComment();

   @NotNull
   CommentedConfigurationNode setComment(@Nullable String var1);

   @Nullable
   CommentedConfigurationNode getParent();

   @NotNull
   List getChildrenList();

   @NotNull
   Map getChildrenMap();

   @NotNull
   CommentedConfigurationNode setValue(@Nullable Object var1);

   @NotNull
   CommentedConfigurationNode mergeValuesFrom(@NotNull ConfigurationNode var1);

   @NotNull
   CommentedConfigurationNode getAppendedNode();

   @NotNull
   CommentedConfigurationNode getNode(@NotNull Object... var1);

   @NotNull
   CommentedConfigurationNode copy();
}
