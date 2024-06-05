package org.jsoup.select;

import org.jsoup.nodes.*;
import java.util.*;

public static final class IsEmpty extends Evaluator
{
    @Override
    public boolean matches(final Element root, final Element element) {
        final List<Node> family = element.childNodes();
        for (final Node n : family) {
            if (!(n instanceof Comment) && !(n instanceof XmlDeclaration) && !(n instanceof DocumentType)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        return ":empty";
    }
}
