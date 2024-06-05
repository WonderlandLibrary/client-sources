package com.google.common.reflect;

import java.lang.reflect.*;
import com.google.common.annotations.*;
import com.google.common.collect.*;
import java.lang.annotation.*;
import com.google.common.base.*;
import java.util.*;
import javax.annotation.*;

@Beta
public final class Parameter implements AnnotatedElement
{
    private final Invokable<?, ?> declaration;
    private final int position;
    private final TypeToken<?> type;
    private final ImmutableList<Annotation> annotations;
    
    Parameter(final Invokable<?, ?> declaration, final int position, final TypeToken<?> type, final Annotation[] annotations) {
        this.declaration = declaration;
        this.position = position;
        this.type = type;
        this.annotations = ImmutableList.copyOf(annotations);
    }
    
    public TypeToken<?> getType() {
        return this.type;
    }
    
    public Invokable<?, ?> getDeclaringInvokable() {
        return this.declaration;
    }
    
    @Override
    public boolean isAnnotationPresent(final Class<? extends Annotation> annotationType) {
        return this.getAnnotation(annotationType) != null;
    }
    
    @Nullable
    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        Preconditions.checkNotNull(annotationType);
        for (final Annotation annotation : this.annotations) {
            if (annotationType.isInstance(annotation)) {
                return annotationType.cast(annotation);
            }
        }
        return null;
    }
    
    @Override
    public Annotation[] getAnnotations() {
        return this.getDeclaredAnnotations();
    }
    
    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.annotations.toArray(new Annotation[this.annotations.size()]);
    }
    
    @Override
    public boolean equals(@Nullable final Object obj) {
        if (obj instanceof Parameter) {
            final Parameter that = (Parameter)obj;
            return this.position == that.position && this.declaration.equals(that.declaration);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.position;
    }
    
    @Override
    public String toString() {
        return this.type + " arg" + this.position;
    }
}
