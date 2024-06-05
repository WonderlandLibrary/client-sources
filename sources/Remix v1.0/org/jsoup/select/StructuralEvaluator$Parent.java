package org.jsoup.select;

import org.jsoup.nodes.*;

static class Parent extends StructuralEvaluator
{
    public Parent(final Evaluator evaluator) {
        this.evaluator = evaluator;
    }
    
    @Override
    public boolean matches(final Element root, final Element element) {
        if (root == element) {
            return false;
        }
        for (Element parent = element.parent(); !this.evaluator.matches(root, parent); parent = parent.parent()) {
            if (parent == root) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        return String.format(":parent%s", this.evaluator);
    }
}
