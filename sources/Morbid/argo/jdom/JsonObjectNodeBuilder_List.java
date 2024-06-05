package argo.jdom;

import java.util.*;

class JsonObjectNodeBuilder_List extends HashMap
{
    final JsonObjectNodeBuilder nodeBuilder;
    
    JsonObjectNodeBuilder_List(final JsonObjectNodeBuilder par1JsonObjectNodeBuilder) {
        this.nodeBuilder = par1JsonObjectNodeBuilder;
        for (final JsonFieldBuilder var3 : JsonObjectNodeBuilder.func_74607_a(this.nodeBuilder)) {
            this.put(var3.func_74724_b(), var3.buildValue());
        }
    }
}
