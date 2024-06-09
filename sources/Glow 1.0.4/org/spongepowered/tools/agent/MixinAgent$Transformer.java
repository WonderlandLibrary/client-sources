package org.spongepowered.tools.agent;

import java.security.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import java.lang.instrument.*;
import org.spongepowered.asm.service.*;
import java.util.*;

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
