package org.jsoup.select;

import org.jsoup.internal.*;
import org.jsoup.nodes.*;

public static final class ContainsData extends Evaluator
{
    private String searchText;
    
    public ContainsData(final String searchText) {
        this.searchText = Normalizer.lowerCase(searchText);
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return Normalizer.lowerCase(element.data()).contains(this.searchText);
    }
    
    @Override
    public String toString() {
        return String.format(":containsData(%s)", this.searchText);
    }
}
