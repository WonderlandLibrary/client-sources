package argo.jdom;

final class ChainedFunctor implements Functor
{
    private final JsonNodeSelector parentJsonNodeSelector;
    private final JsonNodeSelector childJsonNodeSelector;
    
    ChainedFunctor(final JsonNodeSelector par1JsonNodeSelector, final JsonNodeSelector par2JsonNodeSelector) {
        this.parentJsonNodeSelector = par1JsonNodeSelector;
        this.childJsonNodeSelector = par2JsonNodeSelector;
    }
    
    @Override
    public boolean matchesNode(final Object par1Obj) {
        return this.parentJsonNodeSelector.matches(par1Obj) && this.childJsonNodeSelector.matches(this.parentJsonNodeSelector.getValue(par1Obj));
    }
    
    @Override
    public Object applyTo(final Object par1Obj) {
        Object var2;
        try {
            var2 = this.parentJsonNodeSelector.getValue(par1Obj);
        }
        catch (JsonNodeDoesNotMatchChainedJsonNodeSelectorException var3) {
            throw JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_74698_b(var3, this.parentJsonNodeSelector);
        }
        try {
            final Object var4 = this.childJsonNodeSelector.getValue(var2);
            return var4;
        }
        catch (JsonNodeDoesNotMatchChainedJsonNodeSelectorException var5) {
            throw JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_74699_a(var5, this.parentJsonNodeSelector);
        }
    }
    
    @Override
    public String shortForm() {
        return this.childJsonNodeSelector.shortForm();
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.parentJsonNodeSelector.toString()) + ", with " + this.childJsonNodeSelector.toString();
    }
}
