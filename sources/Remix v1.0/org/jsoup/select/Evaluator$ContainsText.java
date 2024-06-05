package org.jsoup.select;

import org.jsoup.internal.*;
import org.jsoup.nodes.*;

public static final class ContainsText extends Evaluator
{
    private String searchText;
    
    public ContainsText(final String searchText) {
        this.searchText = Normalizer.lowerCase(searchText);
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        return Normalizer.lowerCase(element.text()).contains(this.searchText);
    }
    
    @Override
    public String toString() {
        return String.format(":contains(%s)", this.searchText);
    }
}
