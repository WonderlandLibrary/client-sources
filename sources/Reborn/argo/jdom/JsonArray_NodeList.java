package argo.jdom;

import java.util.*;

final class JsonArray_NodeList extends ArrayList
{
    final Iterable elements;
    
    JsonArray_NodeList(final Iterable par1Iterable) {
        this.elements = par1Iterable;
        for (final JsonNode var3 : this.elements) {
            this.add(var3);
        }
    }
}
