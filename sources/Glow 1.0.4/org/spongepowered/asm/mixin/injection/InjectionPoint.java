package org.spongepowered.asm.mixin.injection;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.collect.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.util.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.injection.modify.*;
import org.spongepowered.asm.mixin.injection.points.*;
import com.google.common.base.*;
import java.lang.reflect.*;
import java.util.*;
import java.lang.annotation.*;

public abstract class InjectionPoint
{
    public static final int DEFAULT_ALLOWED_SHIFT_BY = 0;
    public static final int MAX_ALLOWED_SHIFT_BY = 5;
    private static Map<String, Class<? extends InjectionPoint>> types;
    private final String slice;
    private final Selector selector;
    private final String id;
    
    protected InjectionPoint() {
        this("", Selector.DEFAULT, null);
    }
    
    protected InjectionPoint(final InjectionPointData injectionPointData) {
        this(injectionPointData.getSlice(), injectionPointData.getSelector(), injectionPointData.getId());
    }
    
    public InjectionPoint(final String slice, final Selector selector, final String id) {
        super();
        this.slice = slice;
        this.selector = selector;
        this.id = id;
    }
    
    public String getSlice() {
        return this.slice;
    }
    
    public Selector getSelector() {
        return this.selector;
    }
    
    public String getId() {
        return this.id;
    }
    
    public boolean checkPriority(final int n, final int n2) {
        return n < n2;
    }
    
    public abstract boolean find(final String p0, final InsnList p1, final Collection<AbstractInsnNode> p2);
    
    @Override
    public String toString() {
        return String.format("@At(\"%s\")", this.getAtCode());
    }
    
    protected static AbstractInsnNode nextNode(final InsnList list, final AbstractInsnNode abstractInsnNode) {
        final int n = list.indexOf(abstractInsnNode) + 1;
        if (n > 0 && n < list.size()) {
            return list.get(n);
        }
        return abstractInsnNode;
    }
    
    public static InjectionPoint and(final InjectionPoint... array) {
        return new Intersection(array);
    }
    
    public static InjectionPoint or(final InjectionPoint... array) {
        return new Union(array);
    }
    
    public static InjectionPoint after(final InjectionPoint injectionPoint) {
        return new Shift(injectionPoint, 1);
    }
    
    public static InjectionPoint before(final InjectionPoint injectionPoint) {
        return new Shift(injectionPoint, -1);
    }
    
    public static InjectionPoint shift(final InjectionPoint injectionPoint, final int n) {
        return new Shift(injectionPoint, n);
    }
    
    public static List<InjectionPoint> parse(final IInjectionPointContext injectionPointContext, final List<AnnotationNode> list) {
        return parse(injectionPointContext.getContext(), injectionPointContext.getMethod(), injectionPointContext.getAnnotation(), list);
    }
    
    public static List<InjectionPoint> parse(final IMixinContext mixinContext, final MethodNode methodNode, final AnnotationNode annotationNode, final List<AnnotationNode> list) {
        final ImmutableList.Builder builder = ImmutableList.builder();
        final Iterator<AnnotationNode> iterator = list.iterator();
        while (iterator.hasNext()) {
            final InjectionPoint parse = parse(mixinContext, methodNode, annotationNode, iterator.next());
            if (parse != null) {
                builder.add(parse);
            }
        }
        return builder.build();
    }
    
    public static InjectionPoint parse(final IInjectionPointContext injectionPointContext, final At at) {
        return parse(injectionPointContext.getContext(), injectionPointContext.getMethod(), injectionPointContext.getAnnotation(), at.value(), at.shift(), at.by(), Arrays.asList(at.args()), at.target(), at.slice(), at.ordinal(), at.opcode(), at.id());
    }
    
    public static InjectionPoint parse(final IMixinContext mixinContext, final MethodNode methodNode, final AnnotationNode annotationNode, final At at) {
        return parse(mixinContext, methodNode, annotationNode, at.value(), at.shift(), at.by(), Arrays.asList(at.args()), at.target(), at.slice(), at.ordinal(), at.opcode(), at.id());
    }
    
    public static InjectionPoint parse(final IInjectionPointContext injectionPointContext, final AnnotationNode annotationNode) {
        return parse(injectionPointContext.getContext(), injectionPointContext.getMethod(), injectionPointContext.getAnnotation(), annotationNode);
    }
    
    public static InjectionPoint parse(final IMixinContext mixinContext, final MethodNode methodNode, final AnnotationNode annotationNode, final AnnotationNode annotationNode2) {
        final String s = Annotations.getValue(annotationNode2, "value");
        Object of = Annotations.getValue(annotationNode2, "args");
        final String s2 = Annotations.getValue(annotationNode2, "target", "");
        final String s3 = Annotations.getValue(annotationNode2, "slice", "");
        final At.Shift shift = Annotations.getValue(annotationNode2, "shift", At.Shift.class, At.Shift.NONE);
        final int intValue = Annotations.getValue(annotationNode2, "by", 0);
        final int intValue2 = Annotations.getValue(annotationNode2, "ordinal", -1);
        final int intValue3 = Annotations.getValue(annotationNode2, "opcode", 0);
        final String s4 = Annotations.getValue(annotationNode2, "id");
        if (of == null) {
            of = ImmutableList.of();
        }
        return parse(mixinContext, methodNode, annotationNode, s, shift, intValue, (List<String>)of, s2, s3, intValue2, intValue3, s4);
    }
    
    public static InjectionPoint parse(final IMixinContext mixinContext, final MethodNode methodNode, final AnnotationNode annotationNode, final String s, final At.Shift shift, final int n, final List<String> list, final String s2, final String s3, final int n2, final int n3, final String s4) {
        final InjectionPointData injectionPointData = new InjectionPointData(mixinContext, methodNode, annotationNode, s, list, s2, s3, n2, n3, s4);
        return shift(mixinContext, methodNode, annotationNode, create(mixinContext, injectionPointData, findClass(mixinContext, injectionPointData)), shift, n);
    }
    
    private static Class<? extends InjectionPoint> findClass(final IMixinContext mixinContext, final InjectionPointData injectionPointData) {
        final String type = injectionPointData.getType();
        Class<?> forName = InjectionPoint.types.get(type);
        if (forName == null) {
            if (type.matches("^([A-Za-z_][A-Za-z0-9_]*\\.)+[A-Za-z_][A-Za-z0-9_]*$")) {
                try {
                    forName = Class.forName(type);
                    InjectionPoint.types.put(type, (Class<? extends InjectionPoint>)forName);
                    return (Class<? extends InjectionPoint>)forName;
                }
                catch (Exception ex) {
                    throw new InvalidInjectionException(mixinContext, injectionPointData + " could not be loaded or is not a valid InjectionPoint", ex);
                }
            }
            throw new InvalidInjectionException(mixinContext, injectionPointData + " is not a valid injection point specifier");
        }
        return (Class<? extends InjectionPoint>)forName;
    }
    
    private static InjectionPoint create(final IMixinContext mixinContext, final InjectionPointData injectionPointData, final Class<? extends InjectionPoint> clazz) {
        Constructor<? extends InjectionPoint> declaredConstructor;
        try {
            declaredConstructor = clazz.getDeclaredConstructor(InjectionPointData.class);
            declaredConstructor.setAccessible(true);
        }
        catch (NoSuchMethodException ex) {
            throw new InvalidInjectionException(mixinContext, clazz.getName() + " must contain a constructor which accepts an InjectionPointData", ex);
        }
        InjectionPoint injectionPoint;
        try {
            injectionPoint = (InjectionPoint)declaredConstructor.newInstance(injectionPointData);
        }
        catch (Exception ex2) {
            throw new InvalidInjectionException(mixinContext, "Error whilst instancing injection point " + clazz.getName() + " for " + injectionPointData.getAt(), ex2);
        }
        return injectionPoint;
    }
    
    private static InjectionPoint shift(final IMixinContext mixinContext, final MethodNode methodNode, final AnnotationNode annotationNode, final InjectionPoint injectionPoint, final At.Shift shift, final int n) {
        if (injectionPoint != null) {
            if (shift == At.Shift.BEFORE) {
                return before(injectionPoint);
            }
            if (shift == At.Shift.AFTER) {
                return after(injectionPoint);
            }
            if (shift == At.Shift.BY) {
                validateByValue(mixinContext, methodNode, annotationNode, injectionPoint, n);
                return shift(injectionPoint, n);
            }
        }
        return injectionPoint;
    }
    
    private static void validateByValue(final IMixinContext mixinContext, final MethodNode methodNode, final AnnotationNode annotationNode, final InjectionPoint injectionPoint, final int n) {
        final ShiftByViolationBehaviour shiftByViolationBehaviour = mixinContext.getMixin().getConfig().getEnvironment().getOption(MixinEnvironment.Option.SHIFT_BY_VIOLATION_BEHAVIOUR, ShiftByViolationBehaviour.WARN);
        if (shiftByViolationBehaviour == ShiftByViolationBehaviour.IGNORE) {
            return;
        }
        String s = "the maximum allowed value: ";
        String s2 = "Increase the value of maxShiftBy to suppress this warning.";
        int maxShiftByValue = 0;
        if (mixinContext instanceof MixinTargetContext) {
            maxShiftByValue = ((MixinTargetContext)mixinContext).getMaxShiftByValue();
        }
        if (n <= maxShiftByValue) {
            return;
        }
        if (n > 5) {
            s = "MAX_ALLOWED_SHIFT_BY=";
            s2 = "You must use an alternate query or a custom injection point.";
            maxShiftByValue = 5;
        }
        final String format = String.format("@%s(%s) Shift.BY=%d on %s::%s exceeds %s%d. %s", Bytecode.getSimpleName(annotationNode), injectionPoint, n, mixinContext, methodNode.name, s, maxShiftByValue, s2);
        if (shiftByViolationBehaviour == ShiftByViolationBehaviour.WARN && maxShiftByValue < 5) {
            LogManager.getLogger("mixin").warn(format);
            return;
        }
        throw new InvalidInjectionException(mixinContext, format);
    }
    
    protected String getAtCode() {
        final AtCode atCode = this.getClass().getAnnotation(AtCode.class);
        return (atCode == null) ? this.getClass().getName() : atCode.value();
    }
    
    public static void register(final Class<? extends InjectionPoint> clazz) {
        final AtCode atCode = clazz.getAnnotation(AtCode.class);
        if (atCode == null) {
            throw new IllegalArgumentException("Injection point class " + clazz + " is not annotated with @AtCode");
        }
        final Class<? extends InjectionPoint> clazz2 = InjectionPoint.types.get(atCode.value());
        if (clazz2 != null && !clazz2.equals(clazz)) {
            LogManager.getLogger("mixin").debug("Overriding InjectionPoint {} with {} (previously {})", new Object[] { atCode.value(), clazz.getName(), clazz2.getName() });
        }
        InjectionPoint.types.put(atCode.value(), clazz);
    }
    
    static {
        InjectionPoint.types = new HashMap<String, Class<? extends InjectionPoint>>();
        register(BeforeFieldAccess.class);
        register(BeforeInvoke.class);
        register(BeforeNew.class);
        register(BeforeReturn.class);
        register(BeforeStringInvoke.class);
        register(JumpInsnPoint.class);
        register(MethodHead.class);
        register(AfterInvoke.class);
        register(BeforeLoadLocal.class);
        register(AfterStoreLocal.class);
        register(BeforeFinalReturn.class);
        register(BeforeConstant.class);
    }
    
    public enum Selector
    {
        FIRST, 
        LAST, 
        ONE;
        
        public static final Selector DEFAULT;
        private static final Selector[] $VALUES;
        
        public static Selector[] values() {
            return Selector.$VALUES.clone();
        }
        
        public static Selector valueOf(final String s) {
            return Enum.valueOf(Selector.class, s);
        }
        
        static {
            $VALUES = new Selector[] { Selector.FIRST, Selector.LAST, Selector.ONE };
            DEFAULT = Selector.FIRST;
        }
    }
    
    enum ShiftByViolationBehaviour
    {
        IGNORE, 
        WARN, 
        ERROR;
        
        private static final ShiftByViolationBehaviour[] $VALUES;
        
        public static ShiftByViolationBehaviour[] values() {
            return ShiftByViolationBehaviour.$VALUES.clone();
        }
        
        public static ShiftByViolationBehaviour valueOf(final String s) {
            return Enum.valueOf(ShiftByViolationBehaviour.class, s);
        }
        
        static {
            $VALUES = new ShiftByViolationBehaviour[] { ShiftByViolationBehaviour.IGNORE, ShiftByViolationBehaviour.WARN, ShiftByViolationBehaviour.ERROR };
        }
    }
    
    abstract static class CompositeInjectionPoint extends InjectionPoint
    {
        protected final InjectionPoint[] components;
        
        protected CompositeInjectionPoint(final InjectionPoint... components) {
            super();
            if (components == null || components.length < 2) {
                throw new IllegalArgumentException("Must supply two or more component injection points for composite point!");
            }
            this.components = components;
        }
        
        @Override
        public String toString() {
            return "CompositeInjectionPoint(" + this.getClass().getSimpleName() + ")[" + Joiner.on(',').join((Object[])this.components) + "]";
        }
    }
    
    static final class Intersection extends CompositeInjectionPoint
    {
        public Intersection(final InjectionPoint... array) {
            super(array);
        }
        
        @Override
        public boolean find(final String s, final InsnList list, final Collection<AbstractInsnNode> collection) {
            boolean b = false;
            final ArrayList[] array = (ArrayList[])Array.newInstance(ArrayList.class, this.components.length);
            for (int i = 0; i < this.components.length; ++i) {
                array[i] = new ArrayList();
                this.components[i].find(s, list, array[i]);
            }
            final ArrayList list2 = array[0];
            for (int j = 0; j < list2.size(); ++j) {
                final AbstractInsnNode abstractInsnNode = list2.get(j);
                final boolean b2 = true;
                for (int n = 1; n < array.length && array[n].contains(abstractInsnNode); ++n) {}
                if (b2) {
                    collection.add(abstractInsnNode);
                    b = true;
                }
            }
            return b;
        }
    }
    
    static final class Union extends CompositeInjectionPoint
    {
        public Union(final InjectionPoint... array) {
            super(array);
        }
        
        @Override
        public boolean find(final String s, final InsnList list, final Collection<AbstractInsnNode> collection) {
            final LinkedHashSet<AbstractInsnNode> set = new LinkedHashSet<AbstractInsnNode>();
            for (int i = 0; i < this.components.length; ++i) {
                this.components[i].find(s, list, set);
            }
            collection.addAll(set);
            return set.size() > 0;
        }
    }
    
    static final class Shift extends InjectionPoint
    {
        private final InjectionPoint input;
        private final int shift;
        
        public Shift(final InjectionPoint input, final int shift) {
            super();
            if (input == null) {
                throw new IllegalArgumentException("Must supply an input injection point for SHIFT");
            }
            this.input = input;
            this.shift = shift;
        }
        
        @Override
        public String toString() {
            return "InjectionPoint(" + this.getClass().getSimpleName() + ")[" + this.input + "]";
        }
        
        @Override
        public boolean find(final String s, final InsnList list, final Collection<AbstractInsnNode> collection) {
            final List<AbstractInsnNode> list2 = (collection instanceof List) ? collection : new ArrayList<AbstractInsnNode>(collection);
            this.input.find(s, list, collection);
            for (int i = 0; i < list2.size(); ++i) {
                list2.set(i, list.get(list.indexOf(list2.get(i)) + this.shift));
            }
            if (collection != list2) {
                collection.clear();
                collection.addAll((Collection<?>)list2);
            }
            return collection.size() > 0;
        }
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    public @interface AtCode {
        String value();
    }
}
