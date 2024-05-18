package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.util.*;

public class ConstraintViolationException extends Exception
{
    private static final String MISSING_VALUE = "UNRESOLVED";
    private static final long serialVersionUID = 1L;
    private final ConstraintParser.Constraint constraint;
    private final String badValue;
    
    public ConstraintViolationException(final ConstraintParser.Constraint constraint) {
        super();
        this.constraint = constraint;
        this.badValue = "UNRESOLVED";
    }
    
    public ConstraintViolationException(final ConstraintParser.Constraint constraint, final int n) {
        super();
        this.constraint = constraint;
        this.badValue = String.valueOf(n);
    }
    
    public ConstraintViolationException(final String s, final ConstraintParser.Constraint constraint) {
        super(s);
        this.constraint = constraint;
        this.badValue = "UNRESOLVED";
    }
    
    public ConstraintViolationException(final String s, final ConstraintParser.Constraint constraint, final int n) {
        super(s);
        this.constraint = constraint;
        this.badValue = String.valueOf(n);
    }
    
    public ConstraintViolationException(final Throwable t, final ConstraintParser.Constraint constraint) {
        super(t);
        this.constraint = constraint;
        this.badValue = "UNRESOLVED";
    }
    
    public ConstraintViolationException(final Throwable t, final ConstraintParser.Constraint constraint, final int n) {
        super(t);
        this.constraint = constraint;
        this.badValue = String.valueOf(n);
    }
    
    public ConstraintViolationException(final String s, final Throwable t, final ConstraintParser.Constraint constraint) {
        super(s, t);
        this.constraint = constraint;
        this.badValue = "UNRESOLVED";
    }
    
    public ConstraintViolationException(final String s, final Throwable t, final ConstraintParser.Constraint constraint, final int n) {
        super(s, t);
        this.constraint = constraint;
        this.badValue = String.valueOf(n);
    }
    
    public ConstraintParser.Constraint getConstraint() {
        return this.constraint;
    }
    
    public String getBadValue() {
        return this.badValue;
    }
}
