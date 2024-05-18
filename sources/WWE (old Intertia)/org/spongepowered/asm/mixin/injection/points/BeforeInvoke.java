package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

@AtCode("INVOKE")
public class BeforeInvoke extends InjectionPoint
{
    protected final MemberInfo target;
    protected final MemberInfo permissiveTarget;
    protected final int ordinal;
    protected final String className;
    private boolean log;
    private final Logger logger;
    
    public BeforeInvoke(final InjectionPointData injectionPointData) {
        super(injectionPointData);
        this.log = false;
        this.logger = LogManager.getLogger("mixin");
        this.target = injectionPointData.getTarget();
        this.ordinal = injectionPointData.getOrdinal();
        this.log = injectionPointData.get("log", false);
        this.className = this.getClassName();
        this.permissiveTarget = (injectionPointData.getContext().getOption(MixinEnvironment.Option.REFMAP_REMAP) ? this.target.transform(null) : null);
    }
    
    private String getClassName() {
        final AtCode atCode = this.getClass().getAnnotation(AtCode.class);
        return String.format("@At(%s)", (atCode != null) ? atCode.value() : this.getClass().getSimpleName().toUpperCase());
    }
    
    public BeforeInvoke setLogging(final boolean log) {
        this.log = log;
        return this;
    }
    
    @Override
    public boolean find(final String s, final InsnList list, final Collection<AbstractInsnNode> collection) {
        this.log("{} is searching for an injection point in method with descriptor {}", this.className, s);
        return this.find(s, list, collection, this.target) || this.find(s, list, collection, this.permissiveTarget);
    }
    
    protected boolean find(final String s, final InsnList list, final Collection<AbstractInsnNode> collection, final MemberInfo memberInfo) {
        if (memberInfo == null) {
            return false;
        }
        int n = 0;
        boolean b = false;
        for (final AbstractInsnNode abstractInsnNode : list) {
            if (this.matchesInsn(abstractInsnNode)) {
                final MemberInfo memberInfo2 = new MemberInfo(abstractInsnNode);
                this.log("{} is considering insn {}", this.className, memberInfo2);
                if (memberInfo.matches(memberInfo2.owner, memberInfo2.name, memberInfo2.desc)) {
                    this.log("{} > found a matching insn, checking preconditions...", this.className);
                    if (this.matchesInsn(memberInfo2, n)) {
                        this.log("{} > > > found a matching insn at ordinal {}", this.className, n);
                        b |= this.addInsn(list, collection, abstractInsnNode);
                        if (this.ordinal == n) {
                            break;
                        }
                    }
                    ++n;
                }
            }
            this.inspectInsn(s, list, abstractInsnNode);
        }
        return b;
    }
    
    protected boolean addInsn(final InsnList list, final Collection<AbstractInsnNode> collection, final AbstractInsnNode abstractInsnNode) {
        collection.add(abstractInsnNode);
        return true;
    }
    
    protected boolean matchesInsn(final AbstractInsnNode abstractInsnNode) {
        return abstractInsnNode instanceof MethodInsnNode;
    }
    
    protected void inspectInsn(final String s, final InsnList list, final AbstractInsnNode abstractInsnNode) {
    }
    
    protected boolean matchesInsn(final MemberInfo memberInfo, final int n) {
        this.log("{} > > comparing target ordinal {} with current ordinal {}", this.className, this.ordinal, n);
        return this.ordinal == -1 || this.ordinal == n;
    }
    
    protected void log(final String s, final Object... array) {
        if (this.log) {
            this.logger.info(s, array);
        }
    }
}
