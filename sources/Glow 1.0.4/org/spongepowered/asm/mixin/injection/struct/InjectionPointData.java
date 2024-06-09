package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.*;
import java.util.regex.*;
import java.util.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.modify.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import com.google.common.base.*;

public class InjectionPointData
{
    private static final Pattern AT_PATTERN;
    private final Map<String, String> args;
    private final IMixinContext context;
    private final MethodNode method;
    private final AnnotationNode parent;
    private final String at;
    private final String type;
    private final InjectionPoint.Selector selector;
    private final String target;
    private final String slice;
    private final int ordinal;
    private final int opcode;
    private final String id;
    
    public InjectionPointData(final IMixinContext context, final MethodNode method, final AnnotationNode parent, final String at, final List<String> list, final String target, final String s, final int n, final int opcode, final String id) {
        super();
        this.args = new HashMap<String, String>();
        this.context = context;
        this.method = method;
        this.parent = parent;
        this.at = at;
        this.target = target;
        this.slice = Strings.nullToEmpty(s);
        this.ordinal = Math.max(-1, n);
        this.opcode = opcode;
        this.id = id;
        this.parseArgs(list);
        this.args.put("target", target);
        this.args.put("ordinal", String.valueOf(n));
        this.args.put("opcode", String.valueOf(opcode));
        final Matcher matcher = InjectionPointData.AT_PATTERN.matcher(at);
        this.type = parseType(matcher, at);
        this.selector = parseSelector(matcher);
    }
    
    private void parseArgs(final List<String> list) {
        if (list == null) {
            return;
        }
        for (final String s : list) {
            if (s != null) {
                final int index = s.indexOf(61);
                if (index > -1) {
                    this.args.put(s.substring(0, index), s.substring(index + 1));
                }
                else {
                    this.args.put(s, "");
                }
            }
        }
    }
    
    public String getAt() {
        return this.at;
    }
    
    public String getType() {
        return this.type;
    }
    
    public InjectionPoint.Selector getSelector() {
        return this.selector;
    }
    
    public IMixinContext getContext() {
        return this.context;
    }
    
    public MethodNode getMethod() {
        return this.method;
    }
    
    public Type getMethodReturnType() {
        return Type.getReturnType(this.method.desc);
    }
    
    public AnnotationNode getParent() {
        return this.parent;
    }
    
    public String getSlice() {
        return this.slice;
    }
    
    public LocalVariableDiscriminator getLocalVariableDiscriminator() {
        return LocalVariableDiscriminator.parse(this.parent);
    }
    
    public String get(final String s, final String s2) {
        final String s3 = this.args.get(s);
        return (s3 != null) ? s3 : s2;
    }
    
    public int get(final String s, final int n) {
        return parseInt(this.get(s, String.valueOf(n)), n);
    }
    
    public boolean get(final String s, final boolean b) {
        return parseBoolean(this.get(s, String.valueOf(b)), b);
    }
    
    public MemberInfo get(final String s) {
        try {
            return MemberInfo.parseAndValidate(this.get(s, ""), this.context);
        }
        catch (InvalidMemberDescriptorException ex) {
            throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\").%s descriptor \"%s\" on %s", new Object[] { this.at, s, this.target, InjectionInfo.describeInjector(this.context, this.parent, this.method) });
        }
    }
    
    public MemberInfo getTarget() {
        try {
            return MemberInfo.parseAndValidate(this.target, this.context);
        }
        catch (InvalidMemberDescriptorException ex) {
            throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\") descriptor \"%s\" on %s", new Object[] { this.at, this.target, InjectionInfo.describeInjector(this.context, this.parent, this.method) });
        }
    }
    
    public int getOrdinal() {
        return this.ordinal;
    }
    
    public int getOpcode() {
        return this.opcode;
    }
    
    public int getOpcode(final int n) {
        return (this.opcode > 0) ? this.opcode : n;
    }
    
    public int getOpcode(final int n, final int... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            if (this.opcode == array[i]) {
                return this.opcode;
            }
        }
        return n;
    }
    
    public String getId() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return this.type;
    }
    
    private static Pattern createPattern() {
        return Pattern.compile(String.format("^([^:]+):?(%s)?$", Joiner.on('|').join((Object[])InjectionPoint.Selector.values())));
    }
    
    public static String parseType(final String s) {
        return parseType(InjectionPointData.AT_PATTERN.matcher(s), s);
    }
    
    private static String parseType(final Matcher matcher, final String s) {
        return matcher.matches() ? matcher.group(1) : s;
    }
    
    private static InjectionPoint.Selector parseSelector(final Matcher matcher) {
        return (matcher.matches() && matcher.group(2) != null) ? InjectionPoint.Selector.valueOf(matcher.group(2)) : InjectionPoint.Selector.DEFAULT;
    }
    
    private static int parseInt(final String s, final int n) {
        try {
            return Integer.parseInt(s);
        }
        catch (Exception ex) {
            return n;
        }
    }
    
    private static boolean parseBoolean(final String s, final boolean b) {
        try {
            return Boolean.parseBoolean(s);
        }
        catch (Exception ex) {
            return b;
        }
    }
    
    static {
        AT_PATTERN = createPattern();
    }
}
