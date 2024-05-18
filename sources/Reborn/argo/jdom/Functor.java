package argo.jdom;

interface Functor
{
    boolean matchesNode(final Object p0);
    
    Object applyTo(final Object p0);
    
    String shortForm();
}
