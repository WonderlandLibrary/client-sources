package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import com.google.common.base.*;
import org.apache.logging.log4j.*;
import java.util.*;

public class MethodMapper
{
    private static final Logger logger;
    private static final List<String> classes;
    private static final Map<String, Counter> methods;
    private final ClassInfo info;
    
    public MethodMapper(final MixinEnvironment mixinEnvironment, final ClassInfo info) {
        super();
        this.info = info;
    }
    
    public ClassInfo getClassInfo() {
        return this.info;
    }
    
    public void remapHandlerMethod(final MixinInfo mixinInfo, final MethodNode methodNode, final ClassInfo.Method method) {
        if (!(methodNode instanceof MixinInfo.MixinMethodNode) || !((MixinInfo.MixinMethodNode)methodNode).isInjector()) {
            return;
        }
        if (method.isUnique()) {
            MethodMapper.logger.warn("Redundant @Unique on injector method {} in {}. Injectors are implicitly unique", new Object[] { method, mixinInfo });
        }
        if (method.isRenamed()) {
            methodNode.name = method.getName();
            return;
        }
        methodNode.name = method.renameTo(this.getHandlerName((MixinInfo.MixinMethodNode)methodNode));
    }
    
    public String getHandlerName(final MixinInfo.MixinMethodNode mixinMethodNode) {
        return String.format("%s$%s$%s%s", InjectionInfo.getInjectorPrefix(mixinMethodNode.getInjectorAnnotation()), mixinMethodNode.name, getClassUID(mixinMethodNode.getOwner().getClassRef()), getMethodUID(mixinMethodNode.name, mixinMethodNode.desc, !mixinMethodNode.isSurrogate()));
    }
    
    private static String getClassUID(final String s) {
        int n = MethodMapper.classes.indexOf(s);
        if (n < 0) {
            n = MethodMapper.classes.size();
            MethodMapper.classes.add(s);
        }
        return finagle(n);
    }
    
    private static String getMethodUID(final String s, final String s2, final boolean b) {
        final String format = String.format("%s%s", s, s2);
        Counter counter = MethodMapper.methods.get(format);
        if (counter == null) {
            counter = new Counter();
            MethodMapper.methods.put(format, counter);
        }
        else if (b) {
            final Counter counter2 = counter;
            ++counter2.value;
        }
        return String.format("%03x", counter.value);
    }
    
    private static String finagle(final int n) {
        final String hexString = Integer.toHexString(n);
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hexString.length(); ++i) {
            final char char1 = hexString.charAt(i);
            sb.append((char)(char1 + ((char1 < ':') ? 49 : 10)));
        }
        return Strings.padStart(sb.toString(), 3, 'z');
    }
    
    static {
        logger = LogManager.getLogger("mixin");
        classes = new ArrayList<String>();
        methods = new HashMap<String, Counter>();
    }
}
