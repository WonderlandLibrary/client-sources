package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class LookupSwitchInsnNode extends AbstractInsnNode
{
    public LabelNode dflt;
    public List<Integer> keys;
    public List<LabelNode> labels;
    
    public LookupSwitchInsnNode(final LabelNode dflt, final int[] array, final LabelNode[] array2) {
        super(171);
        this.dflt = dflt;
        this.keys = new ArrayList<Integer>((array == null) ? 0 : array.length);
        this.labels = new ArrayList<LabelNode>((array2 == null) ? 0 : array2.length);
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                this.keys.add(array[i]);
            }
        }
        if (array2 != null) {
            this.labels.addAll(Arrays.asList(array2));
        }
    }
    
    @Override
    public int getType() {
        return 12;
    }
    
    @Override
    public void accept(final MethodVisitor methodVisitor) {
        final int[] array = new int[this.keys.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = this.keys.get(i);
        }
        final Label[] array2 = new Label[this.labels.size()];
        for (int j = 0; j < array2.length; ++j) {
            array2[j] = this.labels.get(j).getLabel();
        }
        methodVisitor.visitLookupSwitchInsn(this.dflt.getLabel(), array, array2);
        this.acceptAnnotations(methodVisitor);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> map) {
        final LookupSwitchInsnNode lookupSwitchInsnNode = new LookupSwitchInsnNode(AbstractInsnNode.clone(this.dflt, map), null, AbstractInsnNode.clone(this.labels, map));
        lookupSwitchInsnNode.keys.addAll(this.keys);
        return lookupSwitchInsnNode.cloneAnnotations(this);
    }
}
