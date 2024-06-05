package argo.jdom;

final class JsonNodeSelectors_String extends LeafFunctor
{
    public boolean func_74645_a(final JsonNode par1JsonNode) {
        return JsonNodeType.STRING == par1JsonNode.getType();
    }
    
    @Override
    public String shortForm() {
        return "A short form string";
    }
    
    public String func_74644_b(final JsonNode par1JsonNode) {
        return par1JsonNode.getText();
    }
    
    @Override
    public String toString() {
        return "a value that is a string";
    }
    
    public Object typeSafeApplyTo(final Object par1Obj) {
        return this.func_74644_b((JsonNode)par1Obj);
    }
    
    @Override
    public boolean matchesNode(final Object par1Obj) {
        return this.func_74645_a((JsonNode)par1Obj);
    }
}
