package org.spongepowered.asm.lib.tree;

import java.util.*;
import org.spongepowered.asm.lib.*;

public class TryCatchBlockNode
{
    public LabelNode start;
    public LabelNode end;
    public LabelNode handler;
    public String type;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    
    public TryCatchBlockNode(final LabelNode start, final LabelNode end, final LabelNode handler, final String type) {
        super();
        this.start = start;
        this.end = end;
        this.handler = handler;
        this.type = type;
    }
    
    public void updateIndex(final int n) {
        final int n2 = 0x42000000 | n << 8;
        if (this.visibleTypeAnnotations != null) {
            final Iterator<TypeAnnotationNode> iterator = this.visibleTypeAnnotations.iterator();
            while (iterator.hasNext()) {
                iterator.next().typeRef = n2;
            }
        }
        if (this.invisibleTypeAnnotations != null) {
            final Iterator<TypeAnnotationNode> iterator2 = this.invisibleTypeAnnotations.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().typeRef = n2;
            }
        }
    }
    
    public void accept(final MethodVisitor methodVisitor) {
        methodVisitor.visitTryCatchBlock(this.start.getLabel(), this.end.getLabel(), (this.handler == null) ? null : this.handler.getLabel(), this.type);
        for (int n = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size(), i = 0; i < n; ++i) {
            final TypeAnnotationNode typeAnnotationNode = this.visibleTypeAnnotations.get(i);
            typeAnnotationNode.accept(methodVisitor.visitTryCatchAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, true));
        }
        for (int n2 = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size(), j = 0; j < n2; ++j) {
            final TypeAnnotationNode typeAnnotationNode2 = this.invisibleTypeAnnotations.get(j);
            typeAnnotationNode2.accept(methodVisitor.visitTryCatchAnnotation(typeAnnotationNode2.typeRef, typeAnnotationNode2.typePath, typeAnnotationNode2.desc, false));
        }
    }
}
