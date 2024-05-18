package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class ClassNode extends ClassVisitor
{
    public int version;
    public int access;
    public String name;
    public String signature;
    public String superName;
    public List<String> interfaces;
    public String sourceFile;
    public String sourceDebug;
    public String outerClass;
    public String outerMethod;
    public String outerMethodDesc;
    public List<AnnotationNode> visibleAnnotations;
    public List<AnnotationNode> invisibleAnnotations;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    public List<Attribute> attrs;
    public List<InnerClassNode> innerClasses;
    public List<FieldNode> fields;
    public List<MethodNode> methods;
    
    public ClassNode() {
        this(327680);
        if (this.getClass() != ClassNode.class) {
            throw new IllegalStateException();
        }
    }
    
    public ClassNode(final int n) {
        super(n);
        this.interfaces = new ArrayList<String>();
        this.innerClasses = new ArrayList<InnerClassNode>();
        this.fields = new ArrayList<FieldNode>();
        this.methods = new ArrayList<MethodNode>();
    }
    
    @Override
    public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] array) {
        this.version = version;
        this.access = access;
        this.name = name;
        this.signature = signature;
        this.superName = superName;
        if (array != null) {
            this.interfaces.addAll(Arrays.asList(array));
        }
    }
    
    @Override
    public void visitSource(final String sourceFile, final String sourceDebug) {
        this.sourceFile = sourceFile;
        this.sourceDebug = sourceDebug;
    }
    
    @Override
    public void visitOuterClass(final String outerClass, final String outerMethod, final String outerMethodDesc) {
        this.outerClass = outerClass;
        this.outerMethod = outerMethod;
        this.outerMethodDesc = outerMethodDesc;
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String s, final boolean b) {
        final AnnotationNode annotationNode = new AnnotationNode(s);
        if (b) {
            if (this.visibleAnnotations == null) {
                this.visibleAnnotations = new ArrayList<AnnotationNode>(1);
            }
            this.visibleAnnotations.add(annotationNode);
        }
        else {
            if (this.invisibleAnnotations == null) {
                this.invisibleAnnotations = new ArrayList<AnnotationNode>(1);
            }
            this.invisibleAnnotations.add(annotationNode);
        }
        return annotationNode;
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        final TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(n, typePath, s);
        if (b) {
            if (this.visibleTypeAnnotations == null) {
                this.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            this.visibleTypeAnnotations.add(typeAnnotationNode);
        }
        else {
            if (this.invisibleTypeAnnotations == null) {
                this.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            this.invisibleTypeAnnotations.add(typeAnnotationNode);
        }
        return typeAnnotationNode;
    }
    
    @Override
    public void visitAttribute(final Attribute attribute) {
        if (this.attrs == null) {
            this.attrs = new ArrayList<Attribute>(1);
        }
        this.attrs.add(attribute);
    }
    
    @Override
    public void visitInnerClass(final String s, final String s2, final String s3, final int n) {
        this.innerClasses.add(new InnerClassNode(s, s2, s3, n));
    }
    
    @Override
    public FieldVisitor visitField(final int n, final String s, final String s2, final String s3, final Object o) {
        final FieldNode fieldNode = new FieldNode(n, s, s2, s3, o);
        this.fields.add(fieldNode);
        return fieldNode;
    }
    
    @Override
    public MethodVisitor visitMethod(final int n, final String s, final String s2, final String s3, final String[] array) {
        final MethodNode methodNode = new MethodNode(n, s, s2, s3, array);
        this.methods.add(methodNode);
        return methodNode;
    }
    
    @Override
    public void visitEnd() {
    }
    
    public void check(final int n) {
        if (n == 262144) {
            if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            final Iterator<FieldNode> iterator = this.fields.iterator();
            while (iterator.hasNext()) {
                iterator.next().check(n);
            }
            final Iterator<MethodNode> iterator2 = this.methods.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().check(n);
            }
        }
    }
    
    public void accept(final ClassVisitor classVisitor) {
        final String[] array = new String[this.interfaces.size()];
        this.interfaces.toArray(array);
        classVisitor.visit(this.version, this.access, this.name, this.signature, this.superName, array);
        if (this.sourceFile != null || this.sourceDebug != null) {
            classVisitor.visitSource(this.sourceFile, this.sourceDebug);
        }
        if (this.outerClass != null) {
            classVisitor.visitOuterClass(this.outerClass, this.outerMethod, this.outerMethodDesc);
        }
        for (int n = (this.visibleAnnotations == null) ? 0 : this.visibleAnnotations.size(), i = 0; i < n; ++i) {
            final AnnotationNode annotationNode = this.visibleAnnotations.get(i);
            annotationNode.accept(classVisitor.visitAnnotation(annotationNode.desc, true));
        }
        for (int n2 = (this.invisibleAnnotations == null) ? 0 : this.invisibleAnnotations.size(), j = 0; j < n2; ++j) {
            final AnnotationNode annotationNode2 = this.invisibleAnnotations.get(j);
            annotationNode2.accept(classVisitor.visitAnnotation(annotationNode2.desc, false));
        }
        for (int n3 = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size(), k = 0; k < n3; ++k) {
            final TypeAnnotationNode typeAnnotationNode = this.visibleTypeAnnotations.get(k);
            typeAnnotationNode.accept(classVisitor.visitTypeAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, true));
        }
        for (int n4 = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size(), l = 0; l < n4; ++l) {
            final TypeAnnotationNode typeAnnotationNode2 = this.invisibleTypeAnnotations.get(l);
            typeAnnotationNode2.accept(classVisitor.visitTypeAnnotation(typeAnnotationNode2.typeRef, typeAnnotationNode2.typePath, typeAnnotationNode2.desc, false));
        }
        for (int n5 = (this.attrs == null) ? 0 : this.attrs.size(), n6 = 0; n6 < n5; ++n6) {
            classVisitor.visitAttribute(this.attrs.get(n6));
        }
        for (int n7 = 0; n7 < this.innerClasses.size(); ++n7) {
            this.innerClasses.get(n7).accept(classVisitor);
        }
        for (int n8 = 0; n8 < this.fields.size(); ++n8) {
            this.fields.get(n8).accept(classVisitor);
        }
        for (int n9 = 0; n9 < this.methods.size(); ++n9) {
            this.methods.get(n9).accept(classVisitor);
        }
        classVisitor.visitEnd();
    }
}
