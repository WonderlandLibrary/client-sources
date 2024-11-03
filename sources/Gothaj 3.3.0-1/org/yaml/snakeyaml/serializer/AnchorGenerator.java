package org.yaml.snakeyaml.serializer;

import org.yaml.snakeyaml.nodes.Node;

public interface AnchorGenerator {
   String nextAnchor(Node var1);
}
