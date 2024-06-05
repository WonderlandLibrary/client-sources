package ninja.leaping.configurate.util;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.transformation.NodePath;

public interface ConfigurationNodeWalker$VisitedNode {
   ConfigurationNode getNode();

   NodePath getPath();
}
