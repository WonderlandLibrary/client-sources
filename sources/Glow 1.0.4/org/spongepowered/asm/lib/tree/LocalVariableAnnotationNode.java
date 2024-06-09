package org.spongepowered.asm.lib.tree;

import java.util.*;
import org.spongepowered.asm.lib.*;

public class LocalVariableAnnotationNode extends TypeAnnotationNode
{
    public List<LabelNode> start;
    public List<LabelNode> end;
    public List<Integer> index;
    
    public LocalVariableAnnotationNode(final int n, final TypePath typePath, final LabelNode[] array, final LabelNode[] array2, final int[] array3, final String s) {
        this(327680, n, typePath, array, array2, array3, s);
    }
    
    public LocalVariableAnnotationNode(final int n, final int n2, final TypePath typePath, final LabelNode[] array, final LabelNode[] array2, final int[] array3, final String s) {
        super(n, n2, typePath, s);
        (this.start = new ArrayList<LabelNode>(array.length)).addAll(Arrays.asList(array));
        (this.end = new ArrayList<LabelNode>(array2.length)).addAll(Arrays.asList(array2));
        this.index = new ArrayList<Integer>(array3.length);
        for (int length = array3.length, i = 0; i < length; ++i) {
            this.index.add(array3[i]);
        }
    }
    
    public void accept(final MethodVisitor methodVisitor, final boolean b) {
        final Label[] array = new Label[this.start.size()];
        final Label[] array2 = new Label[this.end.size()];
        final int[] array3 = new int[this.index.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = this.start.get(i).getLabel();
            array2[i] = this.end.get(i).getLabel();
            array3[i] = this.index.get(i);
        }
        this.accept(methodVisitor.visitLocalVariableAnnotation(this.typeRef, this.typePath, array, array2, array3, this.desc, true));
    }
}
