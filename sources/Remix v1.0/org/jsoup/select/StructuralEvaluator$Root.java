package org.jsoup.select;

import org.jsoup.nodes.*;

static class Root extends Evaluator
{
    @Override
    public boolean matches(final Element root, final Element element) {
        return root == element;
    }
}
