package org.spongepowered.tools.agent;

import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.mixin.transformer.*;
import org.apache.logging.log4j.*;
import java.util.*;
import java.security.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import java.lang.instrument.*;
import org.spongepowered.asm.service.*;

public class MixinAgent implements IHotSwap
{
    public static final byte[] ERROR_BYTECODE;
    static final MixinAgentClassLoader classLoader;
    static final Logger logger;
    static Instrumentation instrumentation;
    private static List<MixinAgent> agents;
    final MixinTransformer classTransformer;
    
    public MixinAgent(final MixinTransformer classTransformer) {
        super();
        this.classTransformer = classTransformer;
        MixinAgent.agents.add(this);
        if (MixinAgent.instrumentation != null) {
            this.initTransformer();
        }
    }
    
    private void initTransformer() {
        MixinAgent.instrumentation.addTransformer(new Transformer(), true);
    }
    
    @Override
    public void registerMixinClass(final String s) {
        MixinAgent.classLoader.addMixinClass(s);
    }
    
    @Override
    public void registerTargetClass(final String s, final byte[] array) {
        MixinAgent.classLoader.addTargetClass(s, array);
    }
    
    public static void init(final Instrumentation instrumentation) {
        MixinAgent.instrumentation = instrumentation;
        if (!MixinAgent.instrumentation.isRedefineClassesSupported()) {
            MixinAgent.logger.error("The instrumentation doesn't support re-definition of classes");
        }
        final Iterator<MixinAgent> iterator = MixinAgent.agents.iterator();
        while (iterator.hasNext()) {
            iterator.next().initTransformer();
        }
    }
    
    public static void premain(final String s, final Instrumentation instrumentation) {
        System.setProperty("mixin.hotSwap", "true");
        init(instrumentation);
    }
    
    public static void agentmain(final String s, final Instrumentation instrumentation) {
        init(instrumentation);
    }
    
    static {
        ERROR_BYTECODE = new byte[] { 1 };
        classLoader = new MixinAgentClassLoader();
        logger = LogManager.getLogger("mixin.agent");
        MixinAgent.instrumentation = null;
        MixinAgent.agents = new ArrayList<MixinAgent>();
    }
    
    class Transformer implements ClassFileTransformer
    {
        final MixinAgent this$0;
        
        Transformer(final MixinAgent this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public byte[] transform(final ClassLoader classLoader, final String s, final Class<?> clazz, final ProtectionDomain protectionDomain, final byte[] array) throws IllegalClassFormatException {
            if (clazz == null) {
                return null;
            }
            final byte[] fakeMixinBytecode = MixinAgent.classLoader.getFakeMixinBytecode(clazz);
            if (fakeMixinBytecode != null) {
                final List<String> reloadMixin = this.reloadMixin(s, array);
                if (reloadMixin == null || !this.reApplyMixins(reloadMixin)) {
                    return MixinAgent.ERROR_BYTECODE;
                }
                return fakeMixinBytecode;
            }
            else {
                try {
                    MixinAgent.logger.info("Redefining class " + s);
                    return this.this$0.classTransformer.transformClassBytes(null, s, array);
                }
                catch (Throwable t) {
                    MixinAgent.logger.error("Error while re-transforming class " + s, t);
                    return MixinAgent.ERROR_BYTECODE;
                }
            }
        }
        
        private List<String> reloadMixin(final String s, final byte[] array) {
            MixinAgent.logger.info("Redefining mixin {}", new Object[] { s });
            try {
                return this.this$0.classTransformer.reload(s.replace('/', '.'), array);
            }
            catch (MixinReloadException ex) {
                MixinAgent.logger.error("Mixin {} cannot be reloaded, needs a restart to be applied: {} ", new Object[] { ex.getMixinInfo(), ex.getMessage() });
            }
            catch (Throwable t) {
                MixinAgent.logger.error("Error while finding targets for mixin " + s, t);
            }
            return null;
        }
        
        private boolean reApplyMixins(final List<String> list) {
            final IMixinService service = MixinService.getService();
            for (final String s : list) {
                final String replace = s.replace('/', '.');
                MixinAgent.logger.debug("Re-transforming target class {}", new Object[] { s });
                try {
                    final Class<?> class1 = service.getClassProvider().findClass(replace);
                    final byte[] originalTargetBytecode = MixinAgent.classLoader.getOriginalTargetBytecode(replace);
                    if (originalTargetBytecode == null) {
                        MixinAgent.logger.error("Target class {} bytecode is not registered", new Object[] { replace });
                        return false;
                    }
                    MixinAgent.instrumentation.redefineClasses(new ClassDefinition(class1, this.this$0.classTransformer.transformClassBytes(null, replace, originalTargetBytecode)));
                }
                catch (Throwable t) {
                    MixinAgent.logger.error("Error while re-transforming target class " + s, t);
                    return false;
                }
            }
            return true;
        }
    }
}
