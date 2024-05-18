package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.*;
import java.util.*;
import java.lang.annotation.*;
import javax.lang.model.element.*;

public final class AnnotationHandle
{
    public static final AnnotationHandle MISSING;
    private final AnnotationMirror annotation;
    
    private AnnotationHandle(final AnnotationMirror annotation) {
        super();
        this.annotation = annotation;
    }
    
    public AnnotationMirror asMirror() {
        return this.annotation;
    }
    
    public boolean exists() {
        return this.annotation != null;
    }
    
    @Override
    public String toString() {
        if (this.annotation == null) {
            return "@{UnknownAnnotation}";
        }
        return "@" + (Object)this.annotation.getAnnotationType().asElement().getSimpleName();
    }
    
    public <T> T getValue(final String s, final T t) {
        if (this.annotation == null) {
            return t;
        }
        final AnnotationValue annotationValue = this.getAnnotationValue(s);
        if (!(t instanceof Enum) || annotationValue == null) {
            return (T)((annotationValue != null) ? annotationValue.getValue() : t);
        }
        final VariableElement variableElement = (VariableElement)annotationValue.getValue();
        if (variableElement == null) {
            return t;
        }
        return Enum.valueOf(t.getClass(), variableElement.getSimpleName().toString());
    }
    
    public <T> T getValue() {
        return this.getValue("value", (T)null);
    }
    
    public <T> T getValue(final String s) {
        return this.getValue(s, (T)null);
    }
    
    public boolean getBoolean(final String s, final boolean b) {
        return this.getValue(s, b);
    }
    
    public AnnotationHandle getAnnotation(final String s) {
        final AnnotationValue value = this.getValue(s);
        if (value instanceof AnnotationMirror) {
            return of((AnnotationMirror)value);
        }
        if (value instanceof AnnotationValue) {
            final Object value2 = value.getValue();
            if (value2 instanceof AnnotationMirror) {
                return of((AnnotationMirror)value2);
            }
        }
        return null;
    }
    
    public <T> List<T> getList() {
        return this.getList("value");
    }
    
    public <T> List<T> getList(final String s) {
        return unwrapAnnotationValueList((List<AnnotationValue>)this.getValue(s, Collections.emptyList()));
    }
    
    public List<AnnotationHandle> getAnnotationList(final String s) {
        final AnnotationMirror value = this.getValue(s, (AnnotationMirror)null);
        if (value == null) {
            return Collections.emptyList();
        }
        if (value instanceof AnnotationMirror) {
            return ImmutableList.of(of(value));
        }
        final List<AnnotationValue> list = (List<AnnotationValue>)value;
        final ArrayList list2 = new ArrayList<AnnotationHandle>(list.size());
        final Iterator<AnnotationValue> iterator = list.iterator();
        while (iterator.hasNext()) {
            list2.add(new AnnotationHandle((AnnotationMirror)iterator.next().getValue()));
        }
        return Collections.unmodifiableList((List<? extends AnnotationHandle>)list2);
    }
    
    protected AnnotationValue getAnnotationValue(final String s) {
        for (final ExecutableElement executableElement : this.annotation.getElementValues().keySet()) {
            if (executableElement.getSimpleName().contentEquals(s)) {
                return (AnnotationValue)this.annotation.getElementValues().get(executableElement);
            }
        }
        return null;
    }
    
    protected static <T> List<T> unwrapAnnotationValueList(final List<AnnotationValue> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        final ArrayList<T> list2 = new ArrayList<T>(list.size());
        final Iterator<AnnotationValue> iterator = list.iterator();
        while (iterator.hasNext()) {
            list2.add((T)iterator.next().getValue());
        }
        return list2;
    }
    
    protected static AnnotationMirror getAnnotation(final Element element, final Class<? extends Annotation> clazz) {
        if (element == null) {
            return null;
        }
        final List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        if (annotationMirrors == null) {
            return null;
        }
        for (final AnnotationMirror annotationMirror : annotationMirrors) {
            final Element element2 = annotationMirror.getAnnotationType().asElement();
            if (!(element2 instanceof TypeElement)) {
                continue;
            }
            if (((TypeElement)element2).getQualifiedName().contentEquals(clazz.getName())) {
                return annotationMirror;
            }
        }
        return null;
    }
    
    public static AnnotationHandle of(final AnnotationMirror annotationMirror) {
        return new AnnotationHandle(annotationMirror);
    }
    
    public static AnnotationHandle of(final Element element, final Class<? extends Annotation> clazz) {
        return new AnnotationHandle(getAnnotation(element, clazz));
    }
    
    static {
        MISSING = new AnnotationHandle(null);
    }
}
