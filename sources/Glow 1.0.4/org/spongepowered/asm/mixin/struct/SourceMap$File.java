package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

public static class File
{
    public final int id;
    public final int lineOffset;
    public final int size;
    public final String sourceFileName;
    public final String sourceFilePath;
    
    public File(final int n, final int n2, final int n3, final String s) {
        this(n, n2, n3, s, null);
    }
    
    public File(final int id, final int lineOffset, final int size, final String sourceFileName, final String sourceFilePath) {
        super();
        this.id = id;
        this.lineOffset = lineOffset;
        this.size = size;
        this.sourceFileName = sourceFileName;
        this.sourceFilePath = sourceFilePath;
    }
    
    public void applyOffset(final ClassNode classNode) {
        final Iterator<MethodNode> iterator = classNode.methods.iterator();
        while (iterator.hasNext()) {
            this.applyOffset(iterator.next());
        }
    }
    
    public void applyOffset(final MethodNode methodNode) {
        for (final AbstractInsnNode abstractInsnNode : methodNode.instructions) {
            if (abstractInsnNode instanceof LineNumberNode) {
                final LineNumberNode lineNumberNode = (LineNumberNode)abstractInsnNode;
                lineNumberNode.line += this.lineOffset - 1;
            }
        }
    }
    
    void appendFile(final StringBuilder sb) {
        if (this.sourceFilePath != null) {
            sb.append("+ ").append(this.id).append(" ").append(this.sourceFileName).append("\n");
            sb.append(this.sourceFilePath).append("\n");
        }
        else {
            sb.append(this.id).append(" ").append(this.sourceFileName).append("\n");
        }
    }
    
    public void appendLines(final StringBuilder sb) {
        sb.append("1#").append(this.id).append(",").append(this.size).append(":").append(this.lineOffset).append("\n");
    }
}
