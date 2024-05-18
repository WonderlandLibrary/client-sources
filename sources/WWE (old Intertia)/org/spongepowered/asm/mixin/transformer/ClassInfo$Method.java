package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

public class Method extends Member
{
    private final List<FrameData> frames;
    private boolean isAccessor;
    final ClassInfo this$0;
    
    public Method(final ClassInfo this$0, final Member member) {
        this.this$0 = this$0;
        super(member);
        this.frames = ((member instanceof Method) ? ((Method)member).frames : null);
    }
    
    public Method(final ClassInfo classInfo, final MethodNode methodNode) {
        this(classInfo, methodNode, false);
        this.setUnique(Annotations.getVisible(methodNode, Unique.class) != null);
        this.isAccessor = (Annotations.getSingleVisible(methodNode, Accessor.class, Invoker.class) != null);
    }
    
    public Method(final ClassInfo this$0, final MethodNode methodNode, final boolean b) {
        this.this$0 = this$0;
        super(Type.METHOD, methodNode.name, methodNode.desc, methodNode.access, b);
        this.frames = this.gatherFrames(methodNode);
        this.setUnique(Annotations.getVisible(methodNode, Unique.class) != null);
        this.isAccessor = (Annotations.getSingleVisible(methodNode, Accessor.class, Invoker.class) != null);
    }
    
    public Method(final ClassInfo this$0, final String s, final String s2) {
        this.this$0 = this$0;
        super(Type.METHOD, s, s2, 1, false);
        this.frames = null;
    }
    
    public Method(final ClassInfo this$0, final String s, final String s2, final int n) {
        this.this$0 = this$0;
        super(Type.METHOD, s, s2, n, false);
        this.frames = null;
    }
    
    public Method(final ClassInfo this$0, final String s, final String s2, final int n, final boolean b) {
        this.this$0 = this$0;
        super(Type.METHOD, s, s2, n, b);
        this.frames = null;
    }
    
    private List<FrameData> gatherFrames(final MethodNode methodNode) {
        final ArrayList<FrameData> list = new ArrayList<FrameData>();
        for (final AbstractInsnNode abstractInsnNode : methodNode.instructions) {
            if (abstractInsnNode instanceof FrameNode) {
                list.add(new FrameData(methodNode.instructions.indexOf(abstractInsnNode), (FrameNode)abstractInsnNode));
            }
        }
        return list;
    }
    
    public List<FrameData> getFrames() {
        return this.frames;
    }
    
    @Override
    public ClassInfo getOwner() {
        return this.this$0;
    }
    
    public boolean isAccessor() {
        return this.isAccessor;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Method && super.equals(o);
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(final String s, final String s2) {
        return super.equals(s, s2);
    }
    
    @Override
    public String remapTo(final String s) {
        return super.remapTo(s);
    }
    
    @Override
    public String renameTo(final String s) {
        return super.renameTo(s);
    }
    
    @Override
    public int getAccess() {
        return super.getAccess();
    }
    
    @Override
    public ClassInfo getImplementor() {
        return super.getImplementor();
    }
    
    @Override
    public boolean matchesFlags(final int n) {
        return super.matchesFlags(n);
    }
    
    @Override
    public void setDecoratedFinal(final boolean b, final boolean b2) {
        super.setDecoratedFinal(b, b2);
    }
    
    @Override
    public boolean isDecoratedMutable() {
        return super.isDecoratedMutable();
    }
    
    @Override
    public boolean isDecoratedFinal() {
        return super.isDecoratedFinal();
    }
    
    @Override
    public void setUnique(final boolean unique) {
        super.setUnique(unique);
    }
    
    @Override
    public boolean isUnique() {
        return super.isUnique();
    }
    
    @Override
    public boolean isSynthetic() {
        return super.isSynthetic();
    }
    
    @Override
    public boolean isFinal() {
        return super.isFinal();
    }
    
    @Override
    public boolean isAbstract() {
        return super.isAbstract();
    }
    
    @Override
    public boolean isStatic() {
        return super.isStatic();
    }
    
    @Override
    public boolean isPrivate() {
        return super.isPrivate();
    }
    
    @Override
    public boolean isRemapped() {
        return super.isRemapped();
    }
    
    @Override
    public boolean isRenamed() {
        return super.isRenamed();
    }
    
    @Override
    public boolean isInjected() {
        return super.isInjected();
    }
    
    @Override
    public String getDesc() {
        return super.getDesc();
    }
    
    @Override
    public String getOriginalDesc() {
        return super.getOriginalDesc();
    }
    
    @Override
    public String getName() {
        return super.getName();
    }
    
    @Override
    public String getOriginalName() {
        return super.getOriginalName();
    }
}
