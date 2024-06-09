package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

@AtCode("INVOKE")
public class BeforeInvoke extends InjectionPoint
{
    protected final MemberInfo target;
    protected final boolean allowPermissive;
    protected final int ordinal;
    protected final String className;
    protected final IMixinContext context;
    protected final Logger logger;
    private boolean log;
    
    public BeforeInvoke(final InjectionPointData injectionPointData) {
        super(injectionPointData);
        this.logger = LogManager.getLogger("mixin");
        this.log = false;
        this.target = injectionPointData.getTarget();
        this.ordinal = injectionPointData.getOrdinal();
        this.log = injectionPointData.get("log", false);
        this.className = this.getClassName();
        this.context = injectionPointData.getContext();
        this.allowPermissive = (this.context.getOption(MixinEnvironment.Option.REFMAP_REMAP) && this.context.getOption(MixinEnvironment.Option.REFMAP_REMAP_ALLOW_PERMISSIVE) && !this.context.getReferenceMapper().isDefault());
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
        if (!this.find(s, list, collection, this.target, SearchType.STRICT) && this.target.desc != null && this.allowPermissive) {
            this.logger.warn("STRICT match for {} using \"{}\" in {} returned 0 results, attempting permissive search. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[] { this.className, this.target, this.context });
            return this.find(s, list, collection, this.target, SearchType.PERMISSIVE);
        }
        return true;
    }
    
    protected boolean find(final String s, final InsnList list, final Collection<AbstractInsnNode> collection, final MemberInfo memberInfo, final SearchType searchType) {
        if (memberInfo == null) {
            return false;
        }
        final MemberInfo memberInfo2 = (searchType == SearchType.PERMISSIVE) ? memberInfo.transform(null) : memberInfo;
        int n = 0;
        int n2 = 0;
        for (final AbstractInsnNode abstractInsnNode : list) {
            if (this.matchesInsn(abstractInsnNode)) {
                final MemberInfo memberInfo3 = new MemberInfo(abstractInsnNode);
                this.log("{} is considering insn {}", this.className, memberInfo3);
                if (memberInfo2.matches(memberInfo3.owner, memberInfo3.name, memberInfo3.desc)) {
                    this.log("{} > found a matching insn, checking preconditions...", this.className);
                    if (this.matchesInsn(memberInfo3, n)) {
                        this.log("{} > > > found a matching insn at ordinal {}", this.className, n);
                        if (this.addInsn(list, collection, abstractInsnNode)) {
                            ++n2;
                        }
                        if (this.ordinal == n) {
                            break;
                        }
                    }
                    ++n;
                }
            }
            this.inspectInsn(s, list, abstractInsnNode);
        }
        if (searchType == SearchType.PERMISSIVE && n2 > 1) {
            this.logger.warn("A permissive match for {} using \"{}\" in {} matched {} instructions, this may cause unexpected behaviour. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[] { this.className, memberInfo, this.context, n2 });
        }
        return n2 > 0;
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
    
    public enum SearchType
    {
        STRICT, 
        PERMISSIVE;
        
        private static final SearchType[] $VALUES;
        
        public static SearchType[] values() {
            return SearchType.$VALUES.clone();
        }
        
        public static SearchType valueOf(final String s) {
            return Enum.valueOf(SearchType.class, s);
        }
        
        static {
            $VALUES = new SearchType[] { SearchType.STRICT, SearchType.PERMISSIVE };
        }
    }
}
