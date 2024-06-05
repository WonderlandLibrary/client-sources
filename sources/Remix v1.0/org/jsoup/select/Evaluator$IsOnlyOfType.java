package org.jsoup.select;

import org.jsoup.nodes.*;
import java.util.*;

public static final class IsOnlyOfType extends Evaluator
{
    @Override
    public boolean matches(final Element root, final Element element) {
        final Element p = element.parent();
        if (p == null || p instanceof Document) {
            return false;
        }
        int pos = 0;
        final Elements family = p.children();
        for (final Element el : family) {
            if (el.tag().equals(element.tag())) {
                ++pos;
            }
        }
        return pos == 1;
    }
    
    @Override
    public String toString() {
        return ":only-of-type";
    }
}
