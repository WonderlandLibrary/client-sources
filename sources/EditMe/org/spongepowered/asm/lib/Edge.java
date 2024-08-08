package org.spongepowered.asm.lib;

class Edge {
   static final int NORMAL = 0;
   static final int EXCEPTION = Integer.MAX_VALUE;
   int info;
   Label successor;
   Edge next;
}
