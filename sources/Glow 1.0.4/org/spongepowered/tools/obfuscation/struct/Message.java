package org.spongepowered.tools.obfuscation.struct;

import javax.tools.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.annotation.processing.*;

public class Message
{
    private Diagnostic.Kind kind;
    private CharSequence msg;
    private final Element element;
    private final AnnotationMirror annotation;
    private final AnnotationValue value;
    
    public Message(final Diagnostic.Kind kind, final CharSequence charSequence) {
        this(kind, charSequence, null, (AnnotationMirror)null, null);
    }
    
    public Message(final Diagnostic.Kind kind, final CharSequence charSequence, final Element element) {
        this(kind, charSequence, element, (AnnotationMirror)null, null);
    }
    
    public Message(final Diagnostic.Kind kind, final CharSequence charSequence, final Element element, final AnnotationHandle annotationHandle) {
        this(kind, charSequence, element, annotationHandle.asMirror(), null);
    }
    
    public Message(final Diagnostic.Kind kind, final CharSequence charSequence, final Element element, final AnnotationMirror annotationMirror) {
        this(kind, charSequence, element, annotationMirror, null);
    }
    
    public Message(final Diagnostic.Kind kind, final CharSequence charSequence, final Element element, final AnnotationHandle annotationHandle, final AnnotationValue annotationValue) {
        this(kind, charSequence, element, annotationHandle.asMirror(), annotationValue);
    }
    
    public Message(final Diagnostic.Kind kind, final CharSequence msg, final Element element, final AnnotationMirror annotation, final AnnotationValue value) {
        super();
        this.kind = kind;
        this.msg = msg;
        this.element = element;
        this.annotation = annotation;
        this.value = value;
    }
    
    public Message sendTo(final Messager messager) {
        if (this.value != null) {
            messager.printMessage(this.kind, this.msg, this.element, this.annotation, this.value);
        }
        else if (this.annotation != null) {
            messager.printMessage(this.kind, this.msg, this.element, this.annotation);
        }
        else if (this.element != null) {
            messager.printMessage(this.kind, this.msg, this.element);
        }
        else {
            messager.printMessage(this.kind, this.msg);
        }
        return this;
    }
    
    public Diagnostic.Kind getKind() {
        return this.kind;
    }
    
    public Message setKind(final Diagnostic.Kind kind) {
        this.kind = kind;
        return this;
    }
    
    public CharSequence getMsg() {
        return this.msg;
    }
    
    public Message setMsg(final CharSequence msg) {
        this.msg = msg;
        return this;
    }
    
    public Element getElement() {
        return this.element;
    }
    
    public AnnotationMirror getAnnotation() {
        return this.annotation;
    }
    
    public AnnotationValue getValue() {
        return this.value;
    }
}
