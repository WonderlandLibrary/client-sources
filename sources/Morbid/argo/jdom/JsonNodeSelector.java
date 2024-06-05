package argo.jdom;

public final class JsonNodeSelector
{
    final Functor valueGetter;
    
    JsonNodeSelector(final Functor par1Functor) {
        this.valueGetter = par1Functor;
    }
    
    public boolean matches(final Object par1Obj) {
        return this.valueGetter.matchesNode(par1Obj);
    }
    
    public Object getValue(final Object par1Obj) {
        return this.valueGetter.applyTo(par1Obj);
    }
    
    public JsonNodeSelector with(final JsonNodeSelector par1JsonNodeSelector) {
        return new JsonNodeSelector(new ChainedFunctor(this, par1JsonNodeSelector));
    }
    
    String shortForm() {
        return this.valueGetter.shortForm();
    }
    
    @Override
    public String toString() {
        return this.valueGetter.toString();
    }
}
