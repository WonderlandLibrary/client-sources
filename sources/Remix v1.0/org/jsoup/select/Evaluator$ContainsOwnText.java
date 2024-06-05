package org.jsoup.select;

import org.jsoup.internal.*;
import org.jsoup.nodes.*;

public static final class ContainsOwnText extends Evaluator
{
    private String searchText;
    
    public ContainsOwnText(final String searchText) {
        this.searchText = Normalizer.lowerCase(searchText);
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return Normalizer.lowerCase(element.ownText()).contains(this.searchText);
    }
    
    @Override
    public String toString() {
        return String.format(":containsOwn(%s)", this.searchText);
    }
}
