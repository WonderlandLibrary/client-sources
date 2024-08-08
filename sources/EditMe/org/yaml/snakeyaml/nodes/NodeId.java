package org.yaml.snakeyaml.nodes;

public enum NodeId {
   scalar,
   sequence,
   mapping,
   anchor;

   private static final NodeId[] $VALUES = new NodeId[]{scalar, sequence, mapping, anchor};
}
