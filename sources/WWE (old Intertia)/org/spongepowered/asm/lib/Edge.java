package org.spongepowered.asm.lib;

class Edge
{
    static final int NORMAL = 0;
    static final int EXCEPTION = 0;
    int info;
    Label successor;
    Edge next;
    
    Edge() {
        super();
    }
}
