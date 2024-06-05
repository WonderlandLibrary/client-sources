package org.jsoup.select;

import org.jsoup.nodes.*;
import java.util.*;

abstract class StructuralEvaluator extends Evaluator
{
    Evaluator evaluator;
    
    static class Root extends Evaluator
    {
        @Override
        public boolean matches(final Element root, final Element element) {
            return root == element;
        }
    }
    
    static class Has extends StructuralEvaluator
    {
        public Has(final Evaluator evaluator) {
            this.evaluator = evaluator;
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            for (final Element e : element.getAllElements()) {
                if (e != element && this.evaluator.matches(root, e)) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public String toString() {
            return String.format(":has(%s)", this.evaluator);
        }
    }
    
    static class Not extends StructuralEvaluator
    {
        public Not(final Evaluator evaluator) {
            this.evaluator = evaluator;
        }
        
        @Override
        public boolean matches(final Element root, final Element node) {
            return !this.evaluator.matches(root, node);
        }
        
        @Override
        public String toString() {
            return String.format(":not%s", this.evaluator);
        }
    }
    
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
    
    static class ImmediateParent extends StructuralEvaluator
    {
        public ImmediateParent(final Evaluator evaluator) {
            this.evaluator = evaluator;
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            if (root == element) {
                return false;
            }
            final Element parent = element.parent();
            return parent != null && this.evaluator.matches(root, parent);
        }
        
        @Override
        public String toString() {
            return String.format(":ImmediateParent%s", this.evaluator);
        }
    }
    
    static class PreviousSibling extends StructuralEvaluator
    {
        public PreviousSibling(final Evaluator evaluator) {
            this.evaluator = evaluator;
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            if (root == element) {
                return false;
            }
            for (Element prev = element.previousElementSibling(); prev != null; prev = prev.previousElementSibling()) {
                if (this.evaluator.matches(root, prev)) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public String toString() {
            return String.format(":prev*%s", this.evaluator);
        }
    }
    
    static class ImmediatePreviousSibling extends StructuralEvaluator
    {
        public ImmediatePreviousSibling(final Evaluator evaluator) {
            this.evaluator = evaluator;
        }
        
        @Override
        public boolean matches(final Element root, final Element element) {
            if (root == element) {
                return false;
            }
            final Element prev = element.previousElementSibling();
            return prev != null && this.evaluator.matches(root, prev);
        }
        
        @Override
        public String toString() {
            return String.format(":prev%s", this.evaluator);
        }
    }
}
