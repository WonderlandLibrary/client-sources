package argo.jdom;

abstract class LeafFunctor implements Functor
{
    @Override
    public final Object applyTo(final Object par1Obj) {
        if (!this.matchesNode(par1Obj)) {
            throw JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_74701_a(this);
        }
        return this.typeSafeApplyTo(par1Obj);
    }
    
    protected abstract Object typeSafeApplyTo(final Object p0);
}
