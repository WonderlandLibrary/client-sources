package argo.jdom;

import java.util.*;

final class JsonNodeSelectors_Element extends LeafFunctor
{
    final int index;
    
    JsonNodeSelectors_Element(final int par1) {
        this.index = par1;
    }
    
    public boolean matchesNode_(final List par1List) {
        return par1List.size() > this.index;
    }
    
    @Override
    public String shortForm() {
        return Integer.toString(this.index);
    }
    
    public JsonNode typeSafeApplyTo_(final List par1List) {
        return par1List.get(this.index);
    }
    
    @Override
    public String toString() {
        return "an element at index [" + this.index + "]";
    }
    
    public Object typeSafeApplyTo(final Object par1Obj) {
        return this.typeSafeApplyTo_((List)par1Obj);
    }
    
    @Override
    public boolean matchesNode(final Object par1Obj) {
        return this.matchesNode_((List)par1Obj);
    }
}
