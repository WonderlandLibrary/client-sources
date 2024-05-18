package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.tree.*;
import java.util.regex.*;
import org.spongepowered.asm.util.throwables.*;

public final class ConstraintParser
{
    private ConstraintParser() {
        super();
    }
    
    public static Constraint parse(final String s) {
        if (s == null || s.length() == 0) {
            return Constraint.NONE;
        }
        final String[] split = s.replaceAll("\\s", "").toUpperCase().split(";");
        Constraint constraint = null;
        final String[] array = split;
        for (int length = array.length, i = 0; i < length; ++i) {
            final Constraint constraint2 = new Constraint(array[i]);
            if (constraint == null) {
                constraint = constraint2;
            }
            else {
                constraint.append(constraint2);
            }
        }
        return (constraint != null) ? constraint : Constraint.NONE;
    }
    
    public static Constraint parse(final AnnotationNode annotationNode) {
        return parse(Annotations.getValue(annotationNode, "constraints", ""));
    }
    
    public static class Constraint
    {
        public static final Constraint NONE;
        private static final Pattern pattern;
        private final String expr;
        private String token;
        private String[] constraint;
        private int min;
        private int max;
        private Constraint next;
        
        Constraint(final String expr) {
            super();
            this.min = Integer.MIN_VALUE;
            this.max = 0;
            this.expr = expr;
            final Matcher matcher = Constraint.pattern.matcher(expr);
            if (!matcher.matches()) {
                throw new InvalidConstraintException("Constraint syntax was invalid parsing: " + this.expr);
            }
            this.token = matcher.group(1);
            this.constraint = new String[] { matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5), matcher.group(6), matcher.group(7), matcher.group(8) };
            this.parse();
        }
        
        private Constraint() {
            super();
            this.min = Integer.MIN_VALUE;
            this.max = 0;
            this.expr = null;
            this.token = "*";
            this.constraint = new String[0];
        }
        
        private void parse() {
            if (!this.has(1)) {
                return;
            }
            final int val = this.val(1);
            this.min = val;
            this.max = val;
            final boolean has = this.has(0);
            if (this.has(4)) {
                if (has) {
                    throw new InvalidConstraintException("Unexpected modifier '" + this.elem(0) + "' in " + this.expr + " parsing range");
                }
                this.max = this.val(4);
                if (this.max < this.min) {
                    throw new InvalidConstraintException("Invalid range specified '" + this.max + "' is less than " + this.min + " in " + this.expr);
                }
            }
            else {
                if (!this.has(6)) {
                    if (has) {
                        if (this.has(3)) {
                            throw new InvalidConstraintException("Unexpected trailing modifier '" + this.elem(3) + "' in " + this.expr);
                        }
                        final String elem = this.elem(0);
                        if (">".equals(elem)) {
                            ++this.min;
                            this.max = 0;
                        }
                        else if (">=".equals(elem)) {
                            this.max = 0;
                        }
                        else if ("<".equals(elem)) {
                            final int n = this.min - 1;
                            this.min = n;
                            this.max = n;
                            this.min = Integer.MIN_VALUE;
                        }
                        else if ("<=".equals(elem)) {
                            this.max = this.min;
                            this.min = Integer.MIN_VALUE;
                        }
                    }
                    else if (this.has(2)) {
                        if ("<".equals(this.elem(2))) {
                            this.max = this.min;
                            this.min = Integer.MIN_VALUE;
                        }
                        else {
                            this.max = 0;
                        }
                    }
                    return;
                }
                if (has) {
                    throw new InvalidConstraintException("Unexpected modifier '" + this.elem(0) + "' in " + this.expr + " parsing range");
                }
                this.max = this.min + this.val(6);
            }
        }
        
        private boolean has(final int n) {
            return this.constraint[n] != null;
        }
        
        private String elem(final int n) {
            return this.constraint[n];
        }
        
        private int val(final int n) {
            return (this.constraint[n] != null) ? Integer.parseInt(this.constraint[n]) : 0;
        }
        
        void append(final Constraint next) {
            if (this.next != null) {
                this.next.append(next);
                return;
            }
            this.next = next;
        }
        
        public String getToken() {
            return this.token;
        }
        
        public int getMin() {
            return this.min;
        }
        
        public int getMax() {
            return this.max;
        }
        
        public void check(final ITokenProvider tokenProvider) throws ConstraintViolationException {
            if (this != Constraint.NONE) {
                final Integer token = tokenProvider.getToken(this.token);
                if (token == null) {
                    throw new ConstraintViolationException("The token '" + this.token + "' could not be resolved in " + tokenProvider, this);
                }
                if (token < this.min) {
                    throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + token + ") which is less than the minimum value " + this.min + " in " + tokenProvider, this, token);
                }
                if (token > this.max) {
                    throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + token + ") which is greater than the maximum value " + this.max + " in " + tokenProvider, this, token);
                }
            }
            if (this.next != null) {
                this.next.check(tokenProvider);
            }
        }
        
        public String getRangeHumanReadable() {
            if (this.min == Integer.MIN_VALUE && this.max == 0) {
                return "ANY VALUE";
            }
            if (this.min == Integer.MIN_VALUE) {
                return String.format("less than or equal to %d", this.max);
            }
            if (this.max == 0) {
                return String.format("greater than or equal to %d", this.min);
            }
            if (this.min == this.max) {
                return String.format("%d", this.min);
            }
            return String.format("between %d and %d", this.min, this.max);
        }
        
        @Override
        public String toString() {
            return String.format("Constraint(%s [%d-%d])", this.token, this.min, this.max);
        }
        
        static {
            NONE = new Constraint();
            pattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)\\((?:(<|<=|>|>=|=)?([0-9]+)(<|(-)([0-9]+)?|>|(\\+)([0-9]+)?)?)?\\)$");
        }
    }
}
