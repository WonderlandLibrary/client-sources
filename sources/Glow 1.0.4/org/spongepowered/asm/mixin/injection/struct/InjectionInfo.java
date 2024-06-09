package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.struct.*;
import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.refmap.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.*;
import com.google.common.base.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;

public abstract class InjectionInfo extends SpecialMethodInfo implements ISliceContext
{
    protected final boolean isStatic;
    protected final Deque<MethodNode> targets;
    protected final MethodSlices slices;
    protected final String atKey;
    protected final List<InjectionPoint> injectionPoints;
    protected final Map<Target, List<InjectionNodes.InjectionNode>> targetNodes;
    protected Injector injector;
    protected InjectorGroupInfo group;
    private final List<MethodNode> injectedMethods;
    private int expectedCallbackCount;
    private int requiredCallbackCount;
    private int maxCallbackCount;
    private int injectedCallbackCount;
    
    protected InjectionInfo(final MixinTargetContext mixinTargetContext, final MethodNode methodNode, final AnnotationNode annotationNode) {
        this(mixinTargetContext, methodNode, annotationNode, "at");
    }
    
    protected InjectionInfo(final MixinTargetContext mixinTargetContext, final MethodNode methodNode, final AnnotationNode annotationNode, final String atKey) {
        super(mixinTargetContext, methodNode, annotationNode);
        this.targets = new ArrayDeque<MethodNode>();
        this.injectionPoints = new ArrayList<InjectionPoint>();
        this.targetNodes = new LinkedHashMap<Target, List<InjectionNodes.InjectionNode>>();
        this.injectedMethods = new ArrayList<MethodNode>(0);
        this.expectedCallbackCount = 1;
        this.requiredCallbackCount = 0;
        this.maxCallbackCount = 0;
        this.injectedCallbackCount = 0;
        this.isStatic = Bytecode.methodIsStatic(methodNode);
        this.slices = MethodSlices.parse(this);
        this.atKey = atKey;
        this.readAnnotation();
    }
    
    protected void readAnnotation() {
        if (this.annotation == null) {
            return;
        }
        final String string = "@" + Bytecode.getSimpleName(this.annotation);
        final List<AnnotationNode> injectionPoints = this.readInjectionPoints(string);
        this.findMethods(this.parseTargets(string), string);
        this.parseInjectionPoints(injectionPoints);
        this.parseRequirements();
        this.injector = this.parseInjector(this.annotation);
    }
    
    protected Set<MemberInfo> parseTargets(final String s) {
        final List<String> value = Annotations.getValue(this.annotation, "method", false);
        if (value == null) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing method name", s, this.method.name));
        }
        final LinkedHashSet<MemberInfo> set = new LinkedHashSet<MemberInfo>();
        for (final String s2 : value) {
            try {
                final MemberInfo andValidate = MemberInfo.parseAndValidate(s2, this.mixin);
                if (andValidate.owner != null && !andValidate.owner.equals(this.mixin.getTargetClassRef())) {
                    throw new InvalidInjectionException(this, String.format("%s annotation on %s specifies a target class '%s', which is not supported", s, this.method.name, andValidate.owner));
                }
                set.add(andValidate);
            }
            catch (InvalidMemberDescriptorException ex) {
                throw new InvalidInjectionException(this, String.format("%s annotation on %s, has invalid target descriptor: \"%s\". %s", s, this.method.name, s2, this.mixin.getReferenceMapper().getStatus()));
            }
        }
        return set;
    }
    
    protected List<AnnotationNode> readInjectionPoints(final String s) {
        final List<AnnotationNode> value = Annotations.getValue(this.annotation, this.atKey, false);
        if (value == null) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing '%s' value(s)", s, this.method.name, this.atKey));
        }
        return value;
    }
    
    protected void parseInjectionPoints(final List<AnnotationNode> list) {
        this.injectionPoints.addAll(InjectionPoint.parse(this.mixin, this.method, this.annotation, list));
    }
    
    protected void parseRequirements() {
        this.group = this.mixin.getInjectorGroups().parseGroup(this.method, this.mixin.getDefaultInjectorGroup()).add(this);
        final Integer n = Annotations.getValue(this.annotation, "expect");
        if (n != null) {
            this.expectedCallbackCount = n;
        }
        final Integer n2 = Annotations.getValue(this.annotation, "require");
        if (n2 != null && n2 > -1) {
            this.requiredCallbackCount = n2;
        }
        else if (this.group.isDefault()) {
            this.requiredCallbackCount = this.mixin.getDefaultRequiredInjections();
        }
        final Integer n3 = Annotations.getValue(this.annotation, "allow");
        if (n3 != null) {
            this.maxCallbackCount = Math.max(Math.max(this.requiredCallbackCount, 1), n3);
        }
    }
    
    protected abstract Injector parseInjector(final AnnotationNode p0);
    
    public boolean isValid() {
        return this.targets.size() > 0 && this.injectionPoints.size() > 0;
    }
    
    public void prepare() {
        this.targetNodes.clear();
        final Iterator<MethodNode> iterator = this.targets.iterator();
        while (iterator.hasNext()) {
            final Target targetMethod = this.mixin.getTargetMethod(iterator.next());
            final InjectorTarget injectorTarget = new InjectorTarget(this, targetMethod);
            this.targetNodes.put(targetMethod, this.injector.find(injectorTarget, this.injectionPoints));
            injectorTarget.dispose();
        }
    }
    
    public void inject() {
        for (final Map.Entry<Target, List<InjectionNodes.InjectionNode>> entry : this.targetNodes.entrySet()) {
            this.injector.inject(entry.getKey(), entry.getValue());
        }
        this.targets.clear();
    }
    
    public void postInject() {
        final Iterator<MethodNode> iterator = this.injectedMethods.iterator();
        while (iterator.hasNext()) {
            this.classNode.methods.add(iterator.next());
        }
        final String description = this.getDescription();
        final String status = this.mixin.getReferenceMapper().getStatus();
        final String dynamicInfo = this.getDynamicInfo();
        if (this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_INJECTORS) && this.injectedCallbackCount < this.expectedCallbackCount) {
            throw new InvalidInjectionException(this, String.format("Injection validation failed: %s %s%s in %s expected %d invocation(s) but %d succeeded. %s%s", description, this.method.name, this.method.desc, this.mixin, this.expectedCallbackCount, this.injectedCallbackCount, status, dynamicInfo));
        }
        if (this.injectedCallbackCount < this.requiredCallbackCount) {
            throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, (%d/%d) succeeded. %s%s", description, this.method.name, this.method.desc, this.mixin, this.injectedCallbackCount, this.requiredCallbackCount, status, dynamicInfo));
        }
        if (this.injectedCallbackCount > this.maxCallbackCount) {
            throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, %d succeeded of %d allowed.%s", description, this.method.name, this.method.desc, this.mixin, this.injectedCallbackCount, this.maxCallbackCount, dynamicInfo));
        }
    }
    
    public void notifyInjected(final Target target) {
    }
    
    protected String getDescription() {
        return "Callback method";
    }
    
    @Override
    public String toString() {
        return describeInjector(this.mixin, this.annotation, this.method);
    }
    
    public Collection<MethodNode> getTargets() {
        return this.targets;
    }
    
    @Override
    public MethodSlice getSlice(final String s) {
        return this.slices.get(this.getSliceId(s));
    }
    
    public String getSliceId(final String s) {
        return "";
    }
    
    public int getInjectedCallbackCount() {
        return this.injectedCallbackCount;
    }
    
    public MethodNode addMethod(final int n, final String s, final String s2) {
        final MethodNode methodNode = new MethodNode(327680, n | 0x1000, s, s2, null, null);
        this.injectedMethods.add(methodNode);
        return methodNode;
    }
    
    public void addCallbackInvocation(final MethodNode methodNode) {
        ++this.injectedCallbackCount;
    }
    
    private void findMethods(final Set<MemberInfo> set, final String s) {
        this.targets.clear();
        final int n = this.mixin.getEnvironment().getOption(MixinEnvironment.Option.REFMAP_REMAP) ? 2 : 1;
        for (MemberInfo transform : set) {
            for (int n2 = 0, n3 = 0; n3 < n && n2 < 1; ++n3) {
                int n4 = 0;
                for (final MethodNode methodNode : this.classNode.methods) {
                    if (transform.matches(methodNode.name, methodNode.desc, n4)) {
                        final boolean b = Annotations.getVisible(methodNode, MixinMerged.class) != null;
                        if (transform.matchAll) {
                            if (Bytecode.methodIsStatic(methodNode) != this.isStatic || methodNode == this.method) {
                                continue;
                            }
                            if (b) {
                                continue;
                            }
                        }
                        this.checkTarget(methodNode);
                        this.targets.add(methodNode);
                        ++n4;
                        ++n2;
                    }
                }
                transform = transform.transform(null);
            }
        }
        if (this.targets.size() == 0) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s could not find any targets matching %s in the target class %s. %s%s", s, this.method.name, namesOf(set), this.mixin.getTarget(), this.mixin.getReferenceMapper().getStatus(), this.getDynamicInfo()));
        }
    }
    
    private void checkTarget(final MethodNode methodNode) {
        final AnnotationNode visible = Annotations.getVisible(methodNode, MixinMerged.class);
        if (visible == null) {
            return;
        }
        if (Annotations.getVisible(methodNode, Final.class) != null) {
            throw new InvalidInjectionException(this, String.format("%s cannot inject into @Final method %s::%s%s merged by %s", this, this.classNode.name, methodNode.name, methodNode.desc, Annotations.getValue(visible, "mixin")));
        }
    }
    
    protected String getDynamicInfo() {
        final AnnotationNode invisible = Annotations.getInvisible(this.method, Dynamic.class);
        String s = Strings.nullToEmpty((String)Annotations.getValue(invisible));
        final Type type = Annotations.getValue(invisible, "mixin");
        if (type != null) {
            s = String.format("{%s} %s", type.getClassName(), s).trim();
        }
        return (s.length() > 0) ? String.format(" Method is @Dynamic(%s)", s) : "";
    }
    
    public static InjectionInfo parse(final MixinTargetContext mixinTargetContext, final MethodNode methodNode) {
        final AnnotationNode injectorAnnotation = getInjectorAnnotation(mixinTargetContext.getMixin(), methodNode);
        if (injectorAnnotation == null) {
            return null;
        }
        if (injectorAnnotation.desc.endsWith(Inject.class.getSimpleName() + ";")) {
            return new CallbackInjectionInfo(mixinTargetContext, methodNode, injectorAnnotation);
        }
        if (injectorAnnotation.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
            return new ModifyArgInjectionInfo(mixinTargetContext, methodNode, injectorAnnotation);
        }
        if (injectorAnnotation.desc.endsWith(ModifyArgs.class.getSimpleName() + ";")) {
            return new ModifyArgsInjectionInfo(mixinTargetContext, methodNode, injectorAnnotation);
        }
        if (injectorAnnotation.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
            return new RedirectInjectionInfo(mixinTargetContext, methodNode, injectorAnnotation);
        }
        if (injectorAnnotation.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
            return new ModifyVariableInjectionInfo(mixinTargetContext, methodNode, injectorAnnotation);
        }
        if (injectorAnnotation.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
            return new ModifyConstantInjectionInfo(mixinTargetContext, methodNode, injectorAnnotation);
        }
        return null;
    }
    
    public static AnnotationNode getInjectorAnnotation(final IMixinInfo mixinInfo, final MethodNode methodNode) {
        AnnotationNode singleVisible;
        try {
            singleVisible = Annotations.getSingleVisible(methodNode, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
        }
        catch (IllegalArgumentException ex) {
            throw new InvalidMixinException(mixinInfo, String.format("Error parsing annotations on %s in %s: %s", methodNode.name, mixinInfo.getClassName(), ex.getMessage()));
        }
        return singleVisible;
    }
    
    public static String getInjectorPrefix(final AnnotationNode annotationNode) {
        if (annotationNode != null) {
            if (annotationNode.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
                return "modify";
            }
            if (annotationNode.desc.endsWith(ModifyArgs.class.getSimpleName() + ";")) {
                return "args";
            }
            if (annotationNode.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
                return "redirect";
            }
            if (annotationNode.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
                return "localvar";
            }
            if (annotationNode.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
                return "constant";
            }
        }
        return "handler";
    }
    
    static String describeInjector(final IMixinContext mixinContext, final AnnotationNode annotationNode, final MethodNode methodNode) {
        return String.format("%s->@%s::%s%s", mixinContext.toString(), Bytecode.getSimpleName(annotationNode), methodNode.name, methodNode.desc);
    }
    
    private static String namesOf(final Collection<MemberInfo> collection) {
        int n = 0;
        final int size = collection.size();
        final StringBuilder sb = new StringBuilder();
        for (final MemberInfo memberInfo : collection) {
            if (n > 0) {
                if (n == size - 1) {
                    sb.append(" or ");
                }
                else {
                    sb.append(", ");
                }
            }
            sb.append('\'').append(memberInfo.name).append('\'');
            ++n;
        }
        return sb.toString();
    }
}
