package ninja.leaping.configurate.transformation;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.transformation.ConfigurationTransformation$NodePath;

@FunctionalInterface
public interface TransformAction {
   Object[] visitPath(ConfigurationTransformation$NodePath var1, ConfigurationNode var2);
}
